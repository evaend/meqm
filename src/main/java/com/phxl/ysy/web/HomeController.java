package com.phxl.ysy.web;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.util.BaseUtils;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.core.base.util.SHAUtil;
import com.phxl.core.base.util.SystemConfig;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.service.weixin.WeixinAPIInterface;
import com.phxl.ysy.entity.UserInfo;
import com.phxl.ysy.entity.WecatRegister;
import com.phxl.ysy.entity.WeixinOpenUser;
import com.phxl.ysy.service.IMessageService;
import com.phxl.ysy.service.UserService;


/**
 * ysynet门户网站跳转Controller
 * 
 * @author Administrator
 * @Create Date	2016-1-11
 * @Modified By
 * @Modified Date
 * @version
 * */
@Controller
@RequestMapping("/home")
public class HomeController {
	@Autowired
	UserService userService;
	@Autowired
	WeixinAPIInterface weixinAPIInterface;
	@Autowired
	HttpSession session;
	@Autowired
	TestController testController;
	@Autowired
	IMessageService imessageService;
	
	/**
	 * 微信跳转
	 * */
	@RequestMapping("/wechatBinding")
	@ResponseBody
	public ModelAndView forwardBinging(HttpServletRequest request, HttpServletResponse response) 
			throws Exception{
		String code = request.getParameter("code");
		String EventKey = request.getParameter("state");
		
		String appid = SystemConfig.getProperty("wechat.config.appid");// appid
		String secret = SystemConfig.getProperty("wechat.config.secret");// secret
		//获取用户的openid
		String openid = weixinAPIInterface.getOpenid(appid, secret, code);
		//将openid存在session中
		session.setAttribute("openid", openid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openid", openid);
		
		UserInfo u = new UserInfo();
		u.setWechatOpenid(openid);
		//当前微信用户，是否已经是系统中的用户
		UserInfo user = userService.searchEntity(u);
		//如果当前登录用户系统中已存在，则登录
		if (user != null) {
			// 在这时重新登录之前的session可能还存在
			HttpSession previousSession = request.getSession();
			if (previousSession != null) {
				previousSession.invalidate();
			}
			session = request.getSession(true);

			//跨域session同步 begin
			String JSESSIONID = request.getSession().getId();	
			Cookie cookie = new Cookie("JSESSIONID", JSESSIONID);
			cookie.setPath("/");
			response.addCookie(cookie);
			String[] strArray = { String.valueOf(System.currentTimeMillis()), String.valueOf(Math.random()) };
			String shastr = BaseUtils.sort(strArray);// 将待加密的字符组排序并组成一个字符串
			String newToken = SHAUtil.shaEncode(shastr);// 字符串加密
			Cookie cookieToken = new Cookie("token", newToken);// 新的token返回到token
			cookieToken.setPath("/");
			response.addCookie(cookieToken);
			Queue<String> tokenQueue = new LinkedList<String>();
		    tokenQueue.offer(newToken);
			session.setAttribute("token",tokenQueue);
			// 跨域session同步 end
			String result = null;
			Map<String, Object> userLogin = userService.weixinLogin(user.getUserNo(), openid);
			result = userLogin.get("result").toString();

			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("loginResult", result);
			//如果用户正常登录，则将用户信息存在session中
			if (!userLogin.get("loginStatus").equals(false)) {
				UserInfo userInfo = (UserInfo) userLogin.get("userInfo");
				LocalAssert.notEmpty(userInfo.getOrgType(), "登录: 用户的机构类型未知!");
				LocalAssert.notEmpty(userInfo.getUserLevel(), "登录: 用户类型（级别），未知!");
				session.setAttribute(LoginUser.SESSION_USERNAME, userInfo.getUserName());
				session.setAttribute(LoginUser.SESSION_USER_ORGID, userInfo.getOrgId());
				session.setAttribute(LoginUser.SESSISON_USER_LEVEL, userInfo.getUserLevel());
				session.setAttribute(LoginUser.SESSION_USER_ORG_TYPE, userInfo.getOrgType());
				session.setAttribute(LoginUser.SESSION_USER_INFO, userInfo);
				session.setAttribute(LoginUser.SESSION_USERID, userInfo.getUserId());
				session.setAttribute(LoginUser.SESSION_USERNO, userInfo.getUserNo());
				session.setAttribute(LoginUser.SESSION_USER_ORGNAME, userInfo.getOrgName());
				System.out.println(userLogin.get("menuList"));
				String userMenuList = (String) userLogin.get("menuList") == null ? ""
						: userLogin.get("menuList").toString();
				session.setAttribute(LoginUser.CUR_USER_MENULIST, userMenuList);
				resultMap.put("userInfo", userInfo);
			}
			ModelAndView mav = new ModelAndView(new RedirectView("http://192.168.31.224:3001/#/workplace?userId="+session.getAttribute(LoginUser.SESSION_USERID)));
			mav.addObject("resultMap", resultMap);
			return mav;
		}
		//如果当前微信用户不是项目中的用户，则自动注册
		else {
			if (EventKey==null) {
				throw new ValidationException("你当前没有权限，不能成为我们的用户");
			}
			//根据用户的openid获取用户的基本信息
			WeixinOpenUser wxUser = weixinAPIInterface.getUserInfo(openid);
			//根据扫码传过来的用户角色的key值，查询对应的角色权限
			WecatRegister wecatRegister = imessageService.find(WecatRegister.class, EventKey);
			if (wecatRegister==null) {
				throw new ValidationException("当前机构或者组信息有误");
			}
			//微信自动注册用户，并将用户信息存在session中
			userService.insertWxUser(wecatRegister, wxUser);
			ModelAndView mav = new ModelAndView(new RedirectView("http://192.168.31.224:3001/#/workplace?userId="+session.getAttribute(LoginUser.SESSION_USERID)));
			return mav;
		}	
	}
}

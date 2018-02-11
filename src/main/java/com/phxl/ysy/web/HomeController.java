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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.phxl.core.base.util.BaseUtils;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.core.base.util.SHAUtil;
import com.phxl.core.base.util.SystemConfig;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.service.weixin.WeixinAPIInterface;
import com.phxl.ysy.entity.UserInfo;
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
	
	/**
	 * 微信跳转
	 * */
	@RequestMapping("/wechatBinding")
	@ResponseBody
	public ModelAndView forwardBinging(String EventKey, HttpServletRequest request, HttpServletResponse response) 
			throws Exception{
	
		String code = request.getParameter("code");
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaa"+request.getParameter("EventKey"));
		System.out.println("bbbbbbbbbbbbbbbbbb"+EventKey);
		System.out.println("bbbbbbbbbbbbbbbbbb"+request.getAttribute("EventKey"));
		UserInfo u = new UserInfo();
		String appid = SystemConfig.getProperty("wechat.config.appid");// appid
		String secret = SystemConfig.getProperty("wechat.config.secret");// secret
		String openid = weixinAPIInterface.getOpenid(appid, secret, code);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openid", openid);
		u.setWechatOpenid(openid);
		UserInfo user = userService.searchEntity(u);
		session.setAttribute("openid", openid);
		if (user != null) {
			// 在这时重新登录之前的session可能还存在
			HttpSession previousSession = request.getSession();
			if (previousSession != null) {
				previousSession.invalidate();
			}
			session = request.getSession(true);

			// 跨域session同步 begin
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
			if (!userLogin.get("loginStatus").equals(false)) {
				UserInfo userInfo = (UserInfo) userLogin.get("userInfo");
				LocalAssert.notEmpty(userInfo.getUserNo(), "登录: 用户登录名，未知!");
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
			ModelAndView mav = new ModelAndView(new RedirectView("http://182.254.152.181:8080/ysynet/#/"));
			mav.addObject("resultMap", resultMap);
			return mav;
		} else {
			System.out.println("跳转到绑定微信用户的页面");
			//如果用户session没有过期，则直接进入
			if (request.getSession(false)!=null) {
				ModelAndView mav = new ModelAndView(new RedirectView("http://182.254.152.181:8080/ysynet/#/"));
				return mav;
			}else{
				//如果用户登录的session失效，则跳转到登录页面，重新登录
				return new ModelAndView(new RedirectView("http://182.254.152.181:8080/ysynet/#/proName/#/login"));
			}
			
		}
	}
	
	
}

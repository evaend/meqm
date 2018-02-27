package com.phxl.ysy.web;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.util.BaseUtils;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.core.base.util.MD5Util;
import com.phxl.core.base.util.SHAUtil;
import com.phxl.core.base.util.SystemConfig;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.entity.Group;
import com.phxl.ysy.entity.GroupUserKey;
import com.phxl.ysy.entity.Message;
import com.phxl.ysy.entity.WeixinOpenUser;
import com.phxl.ysy.service.weixin.WeixinAPIInterface;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.service.UserService;
import com.phxl.ysy.entity.UserInfo;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	UserService userService;

	@Autowired
	WeixinAPIInterface weixinAPIInterface;
	
	@Autowired
	HttpSession session;
	
	/**
	 * @author taoyou 用户登录
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/userLogin", produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> userLogin(String userNo, String pwd, String token, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) throws Exception {
		String result = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LocalAssert.notBlank(userNo, "无账号输入");
		LocalAssert.notBlank(pwd, "无密码输入");
		// 在这时重新登录之前的session可能还存在
		HttpSession previousSession = request.getSession();
		if (previousSession != null) {
			previousSession.invalidate();
		}
		session = request.getSession(true);

		// 跨域session同步 begin
		String JSESSIONID = request.getSession().getId();	
		Cookie cookie = new Cookie("JSESSIONID", JSESSIONID);
//		cookie.setDomain("192.168.1.116");// 留着跨域验证
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

		Map<String, Object> userLogin = userService.checkLoginInfo(userNo, pwd, token);
		result = userLogin.get("result").toString();
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
			String userMenuList = (String) userLogin.get("menuList") == null ? ""
					: userLogin.get("menuList").toString();
			session.setAttribute(LoginUser.CUR_USER_MENULIST, userMenuList);
			// resultMap.put("userMenuList",userMenuList);

			GroupUserKey groupUserKey = new GroupUserKey();
			groupUserKey.setUserId(userInfo.getUserId());
			GroupUserKey group = userService.searchEntity(groupUserKey);
			if (group!=null) {
				Group g = userService.find(Group.class, group.getGroupId());
				userInfo.setGroupName(g.getGroupName());
				session.setAttribute("getUserGroupName", g.getGroupName());
			}
			
			resultMap.put("userInfo", userInfo);
		}
		return resultMap;
	}

	/**
	 * @author taoyou 获取用户模块和权限json
	 * @param userId
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserM", produces = { "application/json;charset=UTF-8" })
	public List<Map<String, Object>> getUserM(HttpServletRequest request, HttpServletResponse response)
			throws ValidationException {
		// String result = null;
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		if (StringUtils.isBlank(userId)) {
			throw new ValidationException("无登录信息");
		}
		// result =
		// (String)request.getSession().getAttribute(LoginUser.CUR_USER_MENULIST);
		return userService.selectUserMenu(userId);
		// return result;
	}
	/**
	 * @author 获取微信用户模块和权限json
	 * @param userId
	 */
	@ResponseBody
	@RequestMapping(value = "/getWeiXinUserM", produces = { "application/json;charset=UTF-8" })
	public List<Map<String, Object>> getWeiXinUserM(@RequestParam(value = "userId", required = false) String userId,
			HttpServletRequest request, HttpServletResponse response)
			throws ValidationException {
		System.out.println("userId==========="+userId);
		if (StringUtils.isBlank(userId)) {
			userId = (String)session.getAttribute(LoginUser.SESSION_USERID);
		}
		if (StringUtils.isBlank(userId)) {
			throw new ValidationException("无登录信息");
		}
		return userService.selectWeiXinUserMenu(userId);
	}
	

	/**
	 * @author taoyou 获取用户信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", produces = { "application/json;charset=UTF-8" })
	public UserInfo getUserInfo(@RequestParam(value = "userId", required = false) String userId,
			HttpServletRequest request, HttpServletResponse response) throws ValidationException {
		UserInfo result = null;
		System.out.println("userId==========="+userId);
		if (StringUtils.isBlank(userId)) {
			userId = (String)session.getAttribute(LoginUser.SESSION_USERID);
		}
		
		if (StringUtils.isBlank(userId)) {
			throw new ValidationException("无登录信息");
		}
		
		if (session.getAttribute(LoginUser.SESSION_USER_INFO)==null) {
			result = userService.findUserInfoById(userId);
		}else {
			result = (UserInfo) session.getAttribute(LoginUser.SESSION_USER_INFO);
			if (result == null) {
				throw new ValidationException("无用户信息");
			}
		}
		
		GroupUserKey groupUserKey = new GroupUserKey();
		groupUserKey.setUserId(userId);
		GroupUserKey group = userService.searchEntity(groupUserKey);
		if (group!=null) {
			Group g = userService.find(Group.class, group.getGroupId());
			result.setGroupName(g.getGroupName());
		}
		
		//计算登录用户的未读消息数
		Integer unread = 0;
		Message message = new Message();
		message.setMiReceiveUserid(userId);
		message.setMiSendType("message");
		message.setMessageSendfstate("00");
		message.setMessageReadfstate("00");
		message.setMiReceiveDelete("00");
		List<Message> unreadList = userService.searchList(message);
		if(unreadList!=null && !unreadList.isEmpty()){
		    unread = unreadList.size();
		}
		return result;
	}
	
	
	//查看微信用户是否被绑定
	@ResponseBody
	@RequestMapping("/weValidation")
	public Map<String, Object> weValidation(@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "openid", required = false) String openid, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserInfo u = new UserInfo();
			if (StringUtils.isBlank(openid)) {
				String appid = SystemConfig.getProperty("wechat.config.appid");// appid
				String secret = SystemConfig.getProperty("wechat.config.secret");// secret
				openid = weixinAPIInterface.getOpenid(appid, secret, code);
			}
			map.put("openid", openid);
			u.setWechatOpenid(openid);
			UserInfo user = userService.searchEntity(u);
			if (user != null) {
				map.put("name", user.getUserNo());
				map.put("result", "binded");// 已绑定过
			} else {
				map.put("result", "unBinded");// 未绑定
			}
		} catch (Exception e) {
			map.put("result", "error");
			logger.error(e.getMessage());
		}
		return map;
	}

	// 绑定医商云用户的微信
	@ResponseBody
	@RequestMapping("/weBind")
	public ModelAndView weBind(@RequestParam(value = "name") String name, @RequestParam(value = "pwd") String pwd,
			@RequestParam(value = "openid", required = false) String openid, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("http://182.254.152.181:8080/ysynet/#/");
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		if (StringUtils.isBlank(openid)) {
			if (request.getSession()!= null && request.getSession().getAttribute("openid")!=null) {
				openid = request.getSession().getAttribute("openid").toString();
			}else{
				mav.addObject("message", "session已经过期");
			}
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUserNo(name);
		UserInfo newUserInfo = userService.searchEntity(userInfo);
		boolean bindSuccess = true;

		if (newUserInfo == null) {
			bindSuccess = false;
			mav.addObject("message", "用户名不存在");
		} else {
			try {
				pwd = MD5Util.MD5Encrypt(pwd);
			} catch (Exception e) {
			}
			if (!pwd.toUpperCase().equals(newUserInfo.getPwd().toUpperCase())) {
				bindSuccess = false;
				mav.addObject("message", "用户名或者密码错误");
			} else if (!newUserInfo.getFstate().equals("" + CustomConst.Fstate.USABLE)) {
				bindSuccess = false;
				mav.addObject("message", "用户已停用");
			} else if (newUserInfo.getOrgId() == null) {
				bindSuccess = false;
				mav.addObject("message", "该用户没有机构信息");
			} else if (StringUtils.isNotBlank(newUserInfo.getWechatOpenid())) {
				bindSuccess = false;
				mav.addObject("message", "该用户已绑定过微信");
			} else {
				newUserInfo.setWechatOpenid(openid);
				WeixinOpenUser weiUser = weixinAPIInterface.getUserInfo(openid);
				newUserInfo.setWechatNo(weiUser.getUserName());
				newUserInfo.setModifyTime(new Date());
				newUserInfo.setModifyUserid(userId);
				userService.updateInfo(newUserInfo);
				mav.addObject("message", "绑定成功");
			}
		}
		mav.addObject("bindMessage", bindSuccess);
		mav.addObject("openid", openid);
		return mav;
	}

	// 解除绑定
	@ResponseBody
	@RequestMapping("/weUnBind")
	public String weUnBind(@RequestParam(value = "openid", required = false) String openid, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		String result = "success";
		try {
			UserInfo userInfo = new UserInfo();
			userInfo.setWechatOpenid(openid);
			UserInfo sUserInfo = userService.searchEntity(userInfo);
			if (sUserInfo != null) {
				sUserInfo.setWechatOpenid(null);
				sUserInfo.setWechatNo(null);
				sUserInfo.setModifyUserid(userId);
				sUserInfo.setModifyTime(new Date());
				userService.updateInfoCover(sUserInfo);
			} else {
				result = "error";
			}
		} catch (Exception e) {
			result = "error";
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 验证session是否过期
	 * @param request  
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sessionIsExists")
	public boolean sessionIsExists(HttpServletRequest request, HttpServletResponse response){
		if (request.getSession(false)!=null) {
			System.out.println(true);
			return true;
		}else{
			System.out.println(false);
			return false;
		}
	}

	/**
	 * 使session过期
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sessionTimeout")
	public boolean sessionTimeout(HttpServletRequest request, HttpServletResponse response){
		if (request.getSession(false)!=null) {
			request.getSession().setMaxInactiveInterval(10); 
		}
		return true;
	}
}

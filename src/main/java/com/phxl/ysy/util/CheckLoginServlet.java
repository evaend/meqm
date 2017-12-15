package com.phxl.ysy.util;

import com.phxl.core.base.util.BaseUtils;
import com.phxl.core.base.util.SHAUtil;
import com.phxl.core.base.util.SysContent;
import com.phxl.core.base.util.SystemConfig;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.constant.CustomConst.LoginUser;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Queue;

/**
 * 除了特意要放过的，如果请求里有session且token正确，直接访问链接，否则到登录页面
 * 
 * @author taoyou
 *
 */

public class CheckLoginServlet implements Filter {

	// 不用登录验证的接口
	String[] Reg = { "/orgRegistController/findOrgNameExist", // 注册时，验证机构名称唯一性
			"/orgRegistController/findOrgCodeExist", // 注册时，验证组织机构代码唯一性
			"/orgRegistController/findUserNoExist", // 注册时，验证用户登录账号唯一性
			"/orgRegistController/insertRegistInfo", // 新增注册信息
			"/storage/searchOrgList",//放开前端做下拉框测试
			"/staticData/[^\\s]*", // 基础数据
			"/home/[^\\s]*", // 微信页面
			"/ftp/post", // 上传前
			"/WxServlet", // 微信测试接口
			"/login/check", // 用户验证码
			"/login/userLogin", // 用户登录
			"/login/weValidation", // 微信绑定-
			"/login/weBind", // 微信绑定-
			"/login/weUnBind",// 微信解绑定-
	};

	/**
	 * 实现过滤方法
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		SysContent.setCurrentLocal(request, response);

		String targetURL = request.getRequestURI();
		String contextPath = request.getContextPath();
		String serviceName = request.getServerName();

		if(request.getParameter("currentUserid")!=null){filterChain.doFilter(request, response);return;}

		// String JSESSIONID = request.getSession().getId();
		// Cookie cookie = new Cookie("JSESSIONID",
		// JSESSIONID);//保存session并传回到前端，便于下一次验证
		// cookie.setDomain("");//留着跨域验证
		// cookie.setPath("/");
		// response.addCookie(cookie);

		if (!targetURL.matches("[^\\s]+\\.(jpg|jpeg|png|bmp|gif|js|html|htm|css|ico|svg|woff|woff2|map|json)")) {

			// 添加到白名单的URL不判断登录情况
			for (String r : Reg) {
				if (targetURL.matches(contextPath + r)
						|| (serviceName.matches(r) && targetURL.matches(contextPath + "/+"))) {
					filterChain.doFilter(request, response);
					return;
				}
			}

			// 其它请求判断session和token
			// 请求里的cookies信息
			Cookie[] cookies = request.getCookies();
			String token = null;// cookie中的token
			HttpSession session = null;
			if (cookies != null) {
				for (Cookie tmpCookie : cookies) {
					if (tmpCookie.getName().equals("token")) {
						token = tmpCookie.getValue();
					}
					if (tmpCookie.getName().equals("JSESSIONID")) {
						session = request.getSession(false);
					}
				}

				if (session == null || session.getAttribute(LoginUser.SESSION_USERID) == null) {
					// session不存在且session里没有用户id时，跳转到登录页面
					// session.setAttribute(LoginUser.SESSION_USERID, "252680");
					response.setStatus(CustomConst.ResponseStatus.UNLOGIN);
					return;
				} else if (token == null) {
					// 没有带token，跳转到登录页面
					response.setStatus(CustomConst.ResponseStatus.TOKENFAIL);
					return;
				} else {// 有session，也有token，开始验证
					Queue<String> tokenQueue = (Queue<String>) session.getAttribute("token");
					// 验证token
					// 从cookie里带出来的token和session里的token比对
					if (tokenQueue == null || !tokenQueue.contains(token)) {
						// token没有验证通过，返回到登录页面
						response.setStatus(CustomConst.ResponseStatus.TOKENFAIL);
						return;
					}
				}
			} else {
				// 无cookie信息，返回到登录页面
				// request.getSession(false).setAttribute(LoginUser.SESSION_USERID,
				// "252680");
				response.setStatus(CustomConst.ResponseStatus.UNLOGIN);
				return;
			}
			// 访问成功，更新token
			if (session != null) {
				String newToken = updateSessionToken(request, response);
				//目前系统中的token是队列，同时不是一个token有效，防止出现前端异步请求，token无效的情况
				Queue<String> tokenQueue = (Queue<String>) session.getAttribute("token");
				if (tokenQueue.size() < Integer.valueOf(SystemConfig.getProperty("tokenNumber"))) {
					tokenQueue.offer(newToken);
				} else {
					tokenQueue.poll();
					tokenQueue.offer(newToken);
				}
				session.setAttribute("token", tokenQueue);
			}
		}
		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	public String updateSessionToken(HttpServletRequest request, HttpServletResponse response) {
		try {
			String[] strArray = { String.valueOf(System.currentTimeMillis()), String.valueOf(Math.random()) };
			String shastr = BaseUtils.sort(strArray);// 将待加密的字符组排序并组成一个字符串
			String newToken = SHAUtil.shaEncode(shastr);// 字符串加密
			Cookie cookieToken = new Cookie("token", newToken);// 新的token返回到token
			cookieToken.setPath("/");
			response.addCookie(cookieToken);
			return newToken;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

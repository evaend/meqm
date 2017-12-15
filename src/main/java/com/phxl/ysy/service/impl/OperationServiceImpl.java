package com.phxl.ysy.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.core.base.util.IdentifieUtil;
import com.phxl.core.base.util.SysContent;
import com.phxl.ysy.annotation.OperationInfo;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.dao.UserOperationMapper;
import com.phxl.ysy.entity.UserLogin;
import com.phxl.ysy.entity.UserOperation;
import com.phxl.ysy.service.OperationService;

import net.sf.json.JSONObject;

/**
 * 
 * 2017年4月25日 上午11:18:07
 * 
 * @author 陶悠
 *
 */
@Service
public class OperationServiceImpl extends BaseService implements OperationService {

	private final static Logger operationLogInfo = LoggerFactory.getLogger(OperationServiceImpl.class);
    
	@Autowired
    UserOperationMapper userOperationMapper;
	
	// 后置AOP
	public void operationArgAndReturn(JoinPoint point, Object returnObj) {
		try {
			HttpServletRequest request = SysContent.getRequest();
			HttpServletResponse response = SysContent.getResponse();
			if (request == null || response == null || point == null)
				return;

			//方法名
			String method = point.getSignature().getName();
			//获取到这个类上面的方法全名
		    Method methods[] = point.getSignature().getDeclaringType().getMethods();

			OperationInfo operationInfo = null;
			for (Method m : methods) {
				if (m.getName().equals(method)) {
					Annotation[] annotations = m.getAnnotations();
					for (Annotation annotation : annotations) {
						if (annotation.annotationType() == OperationInfo.class) {
							operationInfo = m.getAnnotation(OperationInfo.class);
							break;
						}
					}	
				}
			}

			String targetUrl = request.getRequestURI();
			// 访问登录的,这里创建一个登录日志
			if (targetUrl != null && (targetUrl.contains("/login/userLogin") || targetUrl.endsWith("/login/login"))) {
				userLogin(request);
			}
			if (operationInfo != null) {// 有操作日志注解，即记录操作日志
				// 创建一个操作日志,记录注解的值
				UserOperation userOperation = new UserOperation();
				userOperation.setOperationCode("200");
				userOperation.setOperationRes("操作成功");
				String requestParam = request.getParameterMap().isEmpty() ? ""
						: JSONObject.fromObject(request.getParameterMap()).toString();
				parseAnnotation(userOperation,operationInfo,requestParam);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 后置方法出现异常
	public void operationThrowing(JoinPoint point, Exception exc) {
		try {
			HttpServletRequest request = SysContent.getRequest();
			HttpServletResponse response = SysContent.getResponse();
			if (request == null || response == null || point == null)
				return;

			//方法名
			String method = point.getSignature().getName();
			//获取到这个类上面的方法全名
		    Method methods[] = point.getSignature().getDeclaringType().getMethods();

			OperationInfo operationInfo = null;
			for (Method m : methods) {
				if (m.getName().equals(method)) {
					Annotation[] annotations = m.getAnnotations();
					for (Annotation annotation : annotations) {
						if (annotation.annotationType() == OperationInfo.class) {
							operationInfo = m.getAnnotation(OperationInfo.class);
							break;
						}
					}	
				}
			}

			String targetUrl = request.getRequestURI();
			if (operationInfo != null) {// 有操作日志注解，即记录操作日志
				// 创建一个操作日志,记录错误信息
				UserOperation userOperation = new UserOperation();
				userOperation.setOperationCode("500");
				userOperation.setOperationRes("操作失败");
				StackTraceElement[] ste = exc.getStackTrace();
				String errorInfo = exc.toString().replace("\r", "").replace("\n", " ");
				if (errorInfo.length() > 500) {
					userOperation.setErrorReason(errorInfo.substring(0, 500));
				} else {
					userOperation.setErrorReason(errorInfo);
				}
				String requestParam = request.getParameterMap().isEmpty() ? ""
						: JSONObject.fromObject(request.getParameterMap()).toString();
				parseAnnotation(userOperation,operationInfo,requestParam);			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parseAnnotation(UserOperation userOperation,OperationInfo operationInfo,String requestParam){
		//操作名称
		String operationName = operationInfo.operationName();
		userOperation.setOperationName(operationName);
		//模块id
		String moduleId = operationInfo.moduleId();
		userOperation.setModuleId(moduleId);
		//模块名称
		String moduleName = operationInfo.moduleName();
		userOperation.setModuleName(moduleName);
		//需要记录参数keys，逗号分隔
		String keys = operationInfo.keys();
		//需要记录的参数values，是keys对应的中文名称
		String values = operationInfo.values();

		String[] params = requestParam.split(",");
		//解析keys和values，拼装参数
		String operationArgs = "";
		if(StringUtils.isNotBlank(keys)){
			String[] keyArgs = keys.split(",");
			if(StringUtils.isNotBlank(values)){
				String[] valueArgs = values.split(",");		
				for(int index = 0;index < keyArgs.length;index ++){
					if(index < valueArgs.length){
						for(String param:params){
							if(param.indexOf(keyArgs[index]) != -1){
								param = param.replaceAll(keyArgs[index], valueArgs[index]);
								operationArgs += param + ",";
							}
						}
					}
					else
						operationArgs +=  "";
				}
			}					
		}
		operationArgs = operationArgs.substring(1, operationArgs.length()-1);
		userOperation.setOperationArgs(operationArgs);
		userOperation(userOperation);
	}
	public void userLogin(HttpServletRequest request) {
		
		String login_userid = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		String login_username = (String) request.getSession().getAttribute(LoginUser.SESSION_USERNAME);
		String login_userno = (String) request.getSession().getAttribute(LoginUser.SESSION_USERNO);
		Long login_orgid = (Long) request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID);
		String login_orgname = (String) request.getSession().getAttribute(LoginUser.SESSION_USER_ORGNAME);

		if (StringUtils.isNotBlank(login_userid)) {// session里有用户ID说明已经登录了,创建一个登录日志创建访问日志打开时
			String userAgent = request.getHeader("User-Agent");
			UserLogin userLogin = new UserLogin();
			userLogin.setLoginGuid(IdentifieUtil.getGuId());
			userLogin.setLoginUserid(login_userid);
			userLogin.setLoginUserno(login_userno);
			userLogin.setLoginUsername(login_username);
			userLogin.setOrgId(login_orgid);
			userLogin.setOrgName(login_orgname);
			userLogin.setLoginIp(request.getRemoteAddr());
			userLogin.setLoginBrowser(getLogBowser(userAgent));
			userLogin.setLoginOs(getLogOS(userAgent));
			userLogin.setLoginDate(new Date());
			this.insertInfo(userLogin);
			request.getSession().setAttribute("loginLogid", userLogin.getLoginGuid());
		}
	}

	public void userOperation(UserOperation userOperation) {
		// TODO Auto-generated method stub
		HttpServletRequest request = SysContent.getRequest();
		HttpServletResponse response = SysContent.getResponse();

		String targetUrl = request.getRequestURI();

		String loginLogid = (String) request.getSession().getAttribute("loginLogid");
		// 创建一个操作日志
		userOperation.setOperationGuid(IdentifieUtil.getGuId());
		userOperation.setLoginGuid(loginLogid);
		userOperation.setOperationUrl(targetUrl);
		userOperation.setOperationDate(new Date());
		if (StringUtils.isBlank(userOperation.getOperationArgs())) {
			// 如果没有封装参数，默认的参数是request过来的参数串，最大只能存储500个
			String requestParam = request.getParameterMap().isEmpty() ? ""
					: JSONObject.fromObject(request.getParameterMap()).toString();
			if (requestParam.length() > 500) {
				userOperation.setOperationArgs(requestParam.substring(0, 500));
			} else {
				userOperation.setOperationArgs(requestParam);
			}
		}

		this.insertInfo(userOperation);

	}

	// 获取浏览器
	public String getLogBowser(String userAgent) {
		String logBowser = null;
		String firefox = "FIREFOX";
		String chrome = "CHROME";
		String safari = "SAFARI";
		String ie11 = "LIKE GECKO";
		String ie = "MSIE";
		String edge = "EDGE";
		userAgent = userAgent.toUpperCase();
		if (userAgent == null || userAgent.trim().equals("")) {
			logBowser = "未知浏览器";
		} else if (userAgent.contains(firefox)) {
			logBowser = userAgent.substring(userAgent.indexOf(firefox));
		} else if (userAgent.contains(chrome)) {
			logBowser = userAgent.substring(userAgent.indexOf(chrome));
			if (logBowser.contains(edge)) {
				logBowser = "Microsoft " + userAgent.substring(userAgent.indexOf(edge));
			}
		} else if (userAgent.contains(safari)) {
			logBowser = userAgent.substring(userAgent.indexOf(safari));
		}else if (userAgent.contains(ie11)) {
			logBowser = "Internet Explorer 11";
		} else if (userAgent.contains(ie)) {
			logBowser = "Internet Explorer" + userAgent.substring(userAgent.indexOf(ie) + 4, userAgent.indexOf(ie) + 8);
		} else {
			logBowser = "未知浏览器";
		}
		return logBowser;
	}

	// 获取当前使用系统
	public String getLogOS(String userAgent) {

		String logOS = null;
		String windows_xp = "WINDOWS NT 5.1";
		String windows_7 = "WINDOWS NT 6.1";
		String windows_8 = "WINDOWS NT 6.2";
		String windows_8_1 = "WINDOWS NT 6.3";
		String windows_10 = "WINDOWS NT 10";
		String mac_os = "MAC OS";
		userAgent = userAgent.toUpperCase();

		if (userAgent == null || userAgent.trim().equals("")) {
			logOS = "未知系统";
		} else if (userAgent.contains(windows_xp)) {
			logOS = "windows xp";
		} else if (userAgent.contains(windows_7)) {
			logOS = "windows 7";
		} else if (userAgent.contains(windows_8)) {
			logOS = "windows 8";
		} else if (userAgent.contains(windows_8_1)) {
			logOS = "windows 8.1";
		} else if (userAgent.contains(windows_10)) {
			logOS = "windows 10";
		} else if (userAgent.contains(mac_os)) {
			logOS = userAgent.substring(userAgent.indexOf(mac_os),userAgent.indexOf(mac_os)+16);
		}else {
			logOS = "未知系统";
		}
		return logOS;
	}

	public List<Map<String, Object>> searchOpRecords(Pager pager) {
		return userOperationMapper.searchOpRecords(pager);
	}
}

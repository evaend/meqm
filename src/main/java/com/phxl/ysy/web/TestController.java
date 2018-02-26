package com.phxl.ysy.web;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.util.IdentifieUtil;
import com.phxl.core.base.util.SystemConfig;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.entity.Group;
import com.phxl.ysy.entity.UserInfo;
import com.phxl.ysy.entity.WecatRegister;
import com.phxl.ysy.entity.WeixinOpenUser;
import com.phxl.ysy.service.IMessageService;
import com.phxl.ysy.service.UserService;
import com.phxl.ysy.service.weixin.WeixinAPIInterface;
import com.phxl.ysy.util.WebConnect;
import com.phxl.ysy.util.WxJsUtils;
import com.phxl.ysy.web.weixin.AccessToken;
import com.phxl.ysy.web.weixin.AccessTokenInfo;
import com.phxl.ysy.web.weixin.AccessTokenServlet;
import com.phxl.ysy.web.weixin.ticket.JsapiTicket;
import com.phxl.ysy.web.weixin.ticket.JsapiTicketInfo;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	WeixinAPIInterface weixinAPIInterface;
	
	@Autowired
	IMessageService imessageService;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UserService userService;
	
	/**
	 * 维修扫一扫 Test
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/test.html", method = RequestMethod.GET)
    public void testPage(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {

        String url = "http://vupv29.natappfree.cc/test/test.html";
        WxJsUtils jsUtils = new WxJsUtils();
		final String appId = SystemConfig.getProperty("wechat.config.appid");
        Map<String, String> ret = jsUtils.sign(url);
        ret.put("appId", appId);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
            request.setAttribute(entry.getKey().toString(), entry.getValue());
            session.setAttribute(entry.getKey().toString(), entry.getValue());
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
	
	
	@RequestMapping(value = "/testScan", method = RequestMethod.GET)
	@ResponseBody
    public Map<String, String> testScan(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		System.out.println("..............");
        String url = "http://hsms.com.cn/test/testScan";
        WxJsUtils jsUtils = new WxJsUtils();
		final String appId = SystemConfig.getProperty("wechat.config.appid");
        Map<String, String> ret = jsUtils.sign(url);
        ret.put("appId", appId);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
            request.setAttribute(entry.getKey().toString(), entry.getValue());
            session.setAttribute(entry.getKey().toString(), entry.getValue());
        }
        return ret;
    }
	
	/**
	 * 微信录音
	 * @param serverId
	 * @param luyintime
	 * @param request
	 * @param response
	 */
	@RequestMapping("/luyinTest")
	@ResponseBody
	public void luyinTest(String serverId , String luyintime , HttpServletRequest request , HttpServletResponse response){
		InputStream is = null;  
	    File amrPath = null;  
	    File mp3Path = null;  
	    String access_token = AccessTokenInfo.accessToken.getAccessToken(); 
		String url = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token="    
	               + access_token + "&media_id=" + serverId;  
	       try {  
	           URL urlGet = new URL(url);    
	           HttpURLConnection http = (HttpURLConnection) urlGet    
	                   .openConnection();    
	           http.setRequestMethod("GET"); // 必须是get方式请求    
	           http.setRequestProperty("Content-Type","audio/mp3");    
	           http.setDoOutput(true);    
	           http.setDoInput(true);    
	           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒    
	           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒    
	           http.connect();    
	           // 获取文件转化为byte流    
	           is = http.getInputStream();  
	             
	           //获取项目路径  
	           String path = Thread.currentThread().getContextClassLoader().getResource("").toString();   
	        path = path.replace('/', '\\'); // 将/换成\    
	        path = path.replace("file:", ""); //去掉file:    
	        path = path.replace("classes\\", ""); //去掉classes\    
	        path = path.replace("target\\", ""); //去掉target\    
	        path = path.replace("WEB-INF\\", "");//去掉web-inf\  
	        path = path.substring(1); //去掉第一个\,如 \D:\JavaWeb...   
	        //文件添加下级目录地址  
	        path += "static"+File.separator +"common" + File.separator +"voice";  
	           UUID uuid = UUID.randomUUID();  
	           String fileName = uuid.toString().replace("-", "");  
	           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	           File file = new File(path);  
	           File todayFile = new File(path + "\\" + sdf.format(new Date()));  
	           amrPath = new File(todayFile + "\\" + fileName + ".amr");  
	           mp3Path = new File(amrPath.toString().replace(".amr", ".mp3"));  
	        //如果文件夹不存在则创建      
	        if  (!file.exists()  && !file.isDirectory()){  
	            file.mkdir();  
	        }  
	        if  (!todayFile.exists()  && !todayFile.isDirectory()){  
	            todayFile.mkdir();  
	        }  
	          
	           BufferedInputStream in = new BufferedInputStream(is);  
	           BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(amrPath));  
	           byte[] by = new byte[1024];  
	           int lend = 0;  
	           while((lend = in.read(by)) != -1){  
	               out.write(by,0,lend);  
	           }  
	           in.close();  
	           out.close();  
	           //转码  
//	           changeToMp3(amrPath.toString(),mp3Path.toString());  
	           //删除amr文件  
	           if(amrPath.isFile() && amrPath.exists()){  
	            amrPath.delete();  
	           }  
	       } catch (Exception e) {  
	           e.printStackTrace();    
	       }  
	         
	       //返回一个数据库存储的格式 ：wgBank\static\common\voice\2017-10-23\3d1043b7dafe44a78cf4f2e45047d999.mp3  
	       String newPath = mp3Path.toString().replace("\\", "/");  
	       newPath = newPath.substring(newPath.toString().lastIndexOf("/wgBank"));  
//	       return newPath;  
	}
	
	
	@RequestMapping("/decoderQRCode")
	@ResponseBody
	public void decoderQRCode(HttpServletRequest request ,HttpServletResponse response) throws Exception{  
		if (AccessTokenInfo.accessToken != null) {
			System.out.println(AccessTokenInfo.accessToken);
		}else{
			AccessTokenServlet ser = new AccessTokenServlet();
			ser.init();
			System.out.println(AccessTokenInfo.accessToken);
		}
		if (JsapiTicketInfo.jsapiTicket == null) {
			WebConnect webConnect = new WebConnect();
		    webConnect.initWebClient();
			String url = String.format(
		               	"https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+AccessTokenInfo.accessToken.getAccessToken()+"&type=jsapi");
			String jsapi_ticket = webConnect.executeHttpGet(url);
			JSONObject json = JSONObject.fromObject(jsapi_ticket);
			System.out.println(jsapi_ticket);
	        JsapiTicket ticket = new JsapiTicket();
	        ticket.setErrcode(json.getString("errcode"));	       
	        ticket.setErrmsg(json.getString("errmsg"));	       
	        ticket.setTicket(json.getString("ticket"));	       
	        ticket.setExpires_in(json.getInt("expires_in"));
	        JsapiTicketInfo.jsapiTicket = ticket;
		}else{
			System.out.println("111111111111111111111111");
		}
	}
	
	@RequestMapping("/getTest")
	@ResponseBody
	public void getTest(HttpServletRequest request ,HttpServletResponse response) {
		final String appId = SystemConfig.getProperty("wechat.config.appid");
        final String appSecret = SystemConfig.getProperty("wechat.config.secret");
        final String nonce = SystemConfig.getProperty("wechat.config.nonce");
        final String echostr = SystemConfig.getProperty("wechat.config.echostr");
        System.out.println("appId = "+appId);
        System.out.println("appSecret = "+appSecret);
        System.out.println("nonce = "+nonce);
        System.out.println("echostr = "+echostr);
	}
	

	@RequestMapping("/permission")
	@ResponseBody
	public String permission(HttpServletRequest request ,HttpServletResponse response) throws Exception{
		Long EventKey = 1L;
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe1ba6ec9765d99ac&redirect_uri=http%3A%2F%2Fvupv29.natappfree.cc%2Ftest%2FgetPermission&response_type=code&scope=snsapi_base&state="+EventKey+"#wechat_redirect";
	}

	/**
	 * 如果用户存在，获取用户信息，如果用户不存在，创建用户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPermission")
	@ResponseBody
	public ModelAndView getPermission(HttpServletRequest request ,HttpServletResponse response) throws Exception{
		String code = request.getParameter("code");
		System.out.println("code"+code);
		String EventKey = request.getParameter("state");
		String appid = SystemConfig.getProperty("wechat.config.appid");// appid
		String secret = SystemConfig.getProperty("wechat.config.secret");// secret
		String openid = weixinAPIInterface.getOpenid(appid, secret, code);
		System.out.println("openid = "+openid);
		session.setAttribute("openid", openid);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		if (request.getSession().getAttribute("wxUser")==null) {
			WeixinOpenUser wxUser = weixinAPIInterface.getUserInfo(openid);
			
			System.out.println("getOpenUserId"+wxUser.getOpenUserId());
			System.out.println(wxUser.getUserName());
			System.out.println(wxUser.getHeadimgurl());
			UserInfo u = new UserInfo();
			u.setWechatOpenid(wxUser.getOpenUserId());
			UserInfo userInfo = imessageService.searchEntity(u);
			if(userInfo != null){
				userInfo.setUserName(wxUser.getUserName());
				userInfo.setWechatNo(wxUser.getUserName());
				userInfo.setHeadImgUrl(wxUser.getHeadimgurl());
				imessageService.updateInfo(userInfo);
			}else{
				WecatRegister wecatRegister = imessageService.find(WecatRegister.class, EventKey);
				if (wecatRegister==null) {
					throw new ValidationException("当前机构或者组信息有误");
				}
				userService.insertWxUser(wecatRegister, wxUser);
			}
//			request.getSession().setAttribute("wxUser", wxUser);
			ModelAndView m = new ModelAndView(new RedirectView("http://mobile.medqcc.com/#/equipment?openid="+wxUser.getOpenUserId()
//					+ "?userName="+wxUser.getUserName()+
//					"&headimgurl="+wxUser.getHeadimgurl()
					));
			return m;
		}else{
			WeixinOpenUser wxUser = (WeixinOpenUser)request.getSession().getAttribute("wxUser");
			ModelAndView m = new ModelAndView(new RedirectView("http://mobile.medqcc.com/#/equipment?openid="+wxUser.getOpenUserId()
//					+ "?userName="+wxUser.getUserName()+
//					"&headimgurl="+wxUser.getHeadimgurl()
					));
			return m;
		}
	}
	
	/**
	 * 获取微信用户信息（用户昵称、用户头像）
	 * @param openid
	 * @param request
	 * @param response
	 * @return
	 * @throws ValidationException
	 */
	@RequestMapping("/getWxUser")
	@ResponseBody
	public WeixinOpenUser getWxUser(@RequestParam(value="openid",required = false) String openid,
			HttpServletRequest request ,HttpServletResponse response) throws ValidationException {
		UserInfo u = new UserInfo();
		u.setWechatOpenid(openid);
		UserInfo userInfo = imessageService.searchEntity(u);
		WeixinOpenUser wxUser = null;
		if(userInfo != null){
			request.getSession().setAttribute("userId", userInfo.getUserId());
			wxUser = new WeixinOpenUser();
			wxUser.setUserName(userInfo.getWechatNo());
			wxUser.setHeadimgurl(userInfo.getHeadImgUrl());
		}		
		return wxUser;
	}
	
	/**
	 * 向用户推送消息
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/pushMessage")
	@ResponseBody
	public void pushMessage(
			@RequestParam(value="id",required = false) String id,
			HttpServletRequest request ,HttpServletResponse response) {
		Map<String,Object> argument = new HashMap<String,Object>(); 
        argument.put("first", id);
        argument.put("keyword1", "IT78922");
        argument.put("keyword2","维修中");
        argument.put("keyword3",new Date());
        argument.put("keyword4","陶悠");
        argument.put("keyword5","维修中");
        argument.put("remark","所属科室：设备科");
//		System.out.println("resultStr = "+resultStr);
//		System.out.println("errMsg = "+errMsg);
		System.out.println("1111");

        String message = imessageService.getMessageJsonContent(argument,
        		"A6C68D5EFF5E4D55B5D8396CB3232DE0","www.baidu.com ","1");
        imessageService.pushMessages(message);
	}
	
	/**
	 * 生成用户二维码
	 * @param orgId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getWeixinTicket")
	@ResponseBody
	public ModelAndView getWeixinTicket(
			@RequestParam(value="orgId",required = false) Long orgId,
			@RequestParam(value="groupId",required = false) String groupId,
			@RequestParam(value="deptId",required = false) String deptId,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
	    String access_token = AccessTokenInfo.accessToken.getAccessToken();
	    Map<String, Object> m = new HashMap<String, Object>();
	    Map<String, Object> map = new HashMap<String, Object>();
		WecatRegister wecatRegister = new WecatRegister();
		wecatRegister.setGroupId(groupId);
		wecatRegister.setOrgId(orgId);
		wecatRegister.setDeptGuid(deptId);
		WecatRegister we = imessageService.searchEntity(wecatRegister);
		Long scene_id = we.getWecatRegisterId();
	    map.put("scene_id", scene_id);
	    m.put("scene", map);
	    String ticket = weixinAPIInterface.getWeixinTicket(access_token, m);
	    return new ModelAndView(new RedirectView("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket));
	}
}

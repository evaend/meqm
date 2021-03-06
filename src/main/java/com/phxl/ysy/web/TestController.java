package com.phxl.ysy.web;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.constant.MySessionContext;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.entity.Group;
import com.phxl.ysy.entity.RrpairOrder;
import com.phxl.ysy.entity.RrpairOrderAcce;
import com.phxl.ysy.entity.UserInfo;
import com.phxl.ysy.entity.WecatRegister;
import com.phxl.ysy.entity.WeixinOpenUser;
import com.phxl.ysy.service.IMessageService;
import com.phxl.ysy.service.OrgDeptService;
import com.phxl.ysy.service.UserService;
import com.phxl.ysy.service.weixin.WeixinAPIInterface;
import com.phxl.ysy.util.WebConnect;
import com.phxl.ysy.util.WxJsUtils;
import com.phxl.ysy.web.weixin.AccessToken;
import com.phxl.ysy.web.weixin.AccessTokenInfo;
import com.phxl.ysy.web.weixin.AccessTokenServlet;
import com.phxl.ysy.web.weixin.ticket.JsapiTicket;
import com.phxl.ysy.web.weixin.ticket.JsapiTicketInfo;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

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
	
	@Autowired
	OrgDeptService orgDeptService;
	
	/**
	 * 维修扫一扫 Test
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/test.html", method = RequestMethod.GET)
	@ResponseBody
    public void testPage(@RequestParam(value="sessionId",required = false) String sessionId,
    		Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		System.out.println(sessionId);
        String url = "http://hsms.com.cn/meqm/test/test.html?sessionId="+sessionId;
        WxJsUtils jsUtils = new WxJsUtils();
		final String appId = SystemConfig.getProperty("wechat.config.appid");
        Map<String, String> ret = jsUtils.sign(url);
        ret.put("appId", appId);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
            request.setAttribute(entry.getKey().toString(), entry.getValue());
        }
        if (StringUtils.isNotBlank(sessionId)) {
			if (session!=null ) {
				if (!sessionId.equals(session.getId())) {
					request.setAttribute("sessionId", sessionId);
				}else {
					request.setAttribute("sessionId", session.getId());
				}
			}else{
				session = MySessionContext.getSession(sessionId);
			}
		}
        request.getRequestDispatcher("/scan.jsp").forward(request, response);
    }
	
	/**
	 * 打开微信录音
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/testScan", method = RequestMethod.GET)
    public void testScan(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        String url = "http://cd7if2.natappfree.cc/test/testScan";
        WxJsUtils jsUtils = new WxJsUtils();
		final String appId = SystemConfig.getProperty("wechat.config.appid");
        Map<String, String> ret = jsUtils.sign(url);
        ret.put("appId", appId);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
            request.setAttribute(entry.getKey().toString(), entry.getValue());
            session.setAttribute(entry.getKey().toString(), entry.getValue());
        }
        request.getRequestDispatcher("/test.jsp").forward(request, response);
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
	public String luyinTest(String serverId , String luyintime , HttpServletRequest request , HttpServletResponse response){
		InputStream is = null;  
	    File amrPath = null;  
	    File mp3Path = null;  
	    String access_token = AccessTokenInfo.accessToken.getAccessToken(); 
		String url = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token="    
	               + access_token + "&media_id=" + serverId;  
		System.out.println("url================"+url);
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
           String path = "E:\\test";
//         Thread.currentThread().getContextClassLoader().getResource("").toString();   
//	       path = path.replace('/', '\\'); // 将/换成\    
//	       path = path.replace("file:", ""); //去掉file:    
//	       path = path.replace("classes\\", ""); //去掉classes\    
//	       path = path.replace("target\\", ""); //去掉target\    
//	       path = path.replace("WEB-INF\\", "");//去掉web-inf\  
//	       path = path.substring(1); //去掉第一个\,如 \D:\JavaWeb...   
	       //文件添加下级目录地址  
//	       path += "static"+File.separator +"common" + File.separator +"voice";  
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
           change(amrPath.toString(),mp3Path.toString());  
           //删除amr文件  
//           if(amrPath.isFile() && amrPath.exists()){  
//            amrPath.delete();  
//           }  
       } catch (Exception e) {  
           e.printStackTrace();    
       }  
         
       //返回一个数据库存储的格式 ：wgBank\static\common\voice\2017-10-23\3d1043b7dafe44a78cf4f2e45047d999.mp3  
       String newPath = mp3Path.toString().replace("\\", "/");  
//       newPath = newPath.substring(newPath.toString().lastIndexOf("/wgBank"));  
	   return newPath;  
	}
	
	/**
	 * 文件类型转换
	 * @param amrPath
	 * @param mp3Path
	 */
	public static void change(String amrPath,String mp3Path) {
		File source = new File(amrPath);
		File target = new File(mp3Path);
		AudioAttributes audio = new AudioAttributes();
		Encoder encoder = new Encoder();
	
		audio.setCodec("libmp3lame");
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
	
		try {
			encoder.encode(source, target, attrs);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
//	/**
//	 * 音频转为Mp3
//	 * @param source                需要转换的音频源文件
//	 * @param desFileName           转换为mp3文件的路径
//	 * @return  返回是否转换成功
//	 */
//	public static void audioToMp3(File source, String desFileName){
//		File target = new File(desFileName);
//		AudioAttributes audio = new AudioAttributes();
//		audio.setCodec("libmp3lame");
//		audio.setBitRate(new Integer(128000));
//		audio.setChannels(new Integer(2));
//		audio.setSamplingRate(new Integer(44100));
//		EncodingAttributes attrs = new EncodingAttributes();
//		attrs.setFormat("mp3");
//		attrs.setAudioAttributes(audio);
//		Encoder encoder = new Encoder();
//		
//		try {
//			encoder.encode(source, target, attrs);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		    
//	}
	
	/**
	 * 单独获取jsapiTicket
	 * @param request
	 * @param response
	 * @throws Exception
	 */
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
	
	/**
	 * 获取配置config里面的基本信息
	 * @param request
	 * @param response
	 */
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
	

	/**
	 * 获取用户权限，跳转到程序中获取用户信息的接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/permission")
	@ResponseBody
	public String permission(HttpServletRequest request ,HttpServletResponse response) throws Exception{
		Long EventKey = 1L;
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa0d673d35aab0631&redirect_uri=http%3A%2F%2Fhsms.com.cn%2Fmeqm%2Ftest%2FgetPermission&response_type=code&scope=snsapi_base&state="+EventKey+"#wechat_redirect";
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
	public void getPermission(HttpServletRequest request ,HttpServletResponse response) throws Exception{
		String code = request.getParameter("code");
		String EventKey = request.getParameter("state");
		String appid = SystemConfig.getProperty("wechat.config.appid");// appid
		String secret = SystemConfig.getProperty("wechat.config.secret");// secret
		String openid = weixinAPIInterface.getOpenid(appid, secret, code);
		System.out.println("openid = "+openid);
		session.setAttribute("openid", openid);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//如果当前session中已经有了微信用户信息
		if (request.getSession().getAttribute("wxUser")==null) {
			WeixinOpenUser wxUser = weixinAPIInterface.getUserInfo(openid);
			UserInfo u = new UserInfo();
			u.setWechatOpenid(wxUser.getOpenUserId());
			UserInfo userInfo = imessageService.searchEntity(u);
			if(userInfo != null){
				response.sendRedirect(CustomConst.MeqmMobile+"/#/workplace/"+userInfo.getUserId()+"/"+session.getId());
			}else{
				throw new ValidationException("当前未注册");
				//自动注册
//				userService.insertWxUser(wecatRegister, wxUser);
			}
		}else{
			response.sendRedirect(CustomConst.MeqmMobile+"/#/workplace/"+session.getAttribute(LoginUser.SESSION_USERID)+"/"+session.getId());
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
	 * @throws ValidationException 
	 */
	@RequestMapping("/pushMessage")
	@ResponseBody
	public void pushMessage(
			@RequestParam(value="rrpairOrderGuid",required = false) String rrpairOrderGuid,
			HttpServletRequest request ,HttpServletResponse response) throws ValidationException {
		if (StringUtils.isBlank(rrpairOrderGuid)) {
			throw new ValidationException("当前维修单id不允许为空");
		}
		RrpairOrder rrpairOrder = userService.find(RrpairOrder.class, rrpairOrderGuid);
		if (rrpairOrder==null) {
			throw new ValidationException("当前维修工单不存在");
		}
		imessageService.selectBDeptUser(rrpairOrderGuid);
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
	public void getWeixinTicket(
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
		if (we==null) {
			throw new ValidationException("没有配置该机构和该用户组的连接");
		}
		Long scene_id = we.getWecatRegisterId();
	    map.put("scene_id", scene_id);
	    m.put("scene", map);
	    String ticket = weixinAPIInterface.getWeixinTicket(access_token, m);
	    response.sendRedirect("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket);
	}
	
	@RequestMapping("/getSession")
	@ResponseBody
	public void getSession(HttpServletRequest request,HttpServletResponse response) throws IOException{
		System.out.println("11111111111111111111118888");
		String id = request.getSession().getId();
		System.out.println(id+"+++++++++++++++++++");
//		 Cookie[] cookies = request.getCookies();
//			String token = null;// cookie中的token
//			HttpSession session = null;
//			if (cookies != null) {
//				for (Cookie tmpCookie : cookies) {
//					System.out.println(tmpCookie.getName());
//				}
//			}
//		 
	}
}

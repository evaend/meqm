package com.phxl.ysy.web;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.phxl.core.base.util.SystemConfig;
import com.phxl.ysy.entity.WeixinOpenUser;
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
	HttpSession session;
	
	@RequestMapping(value = "/test.html", method = RequestMethod.GET)
    public void testPage(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {

        String url = "http://69pbn9.natappfree.cc/test/test.html";
        WxJsUtils jsUtils = new WxJsUtils();
		final String appId = SystemConfig.getProperty("wechat.config.appid");
        Map<String, String> ret = jsUtils.sign(url);
        ret.put("appId", appId);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
            request.setAttribute(entry.getKey().toString(), entry.getValue());
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
	
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

	@RequestMapping("/getPermission")
	@ResponseBody
	public void getPermission(String code,HttpServletRequest request ,HttpServletResponse response) throws Exception{
		WeixinOpenUser wxUser = weixinAPIInterface.getUserInfo(
//				weixinAPIInterface.getOpenid("wxe1ba6ec9765d99ac",
//						"d8ee2dc6444cdada67cb903f1d828780",code) );
				session.getAttribute("openid").toString());
//		if (wxUser.getUserName()==null) {
//			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
//					+ "appid=wx9a3d0c9c3170978c&"
//					+ "redirect_uri=http%3a%2f%2fwx.dizaozhe.cc%2fwechatconfig%2fdesc"
//					+ "&response_type=code"
//					+ "&scope=snsapi_userinfo"
//					+ "&state=ssaweqeqew#wechat_redirect";
//			WebConnect webConnect = new WebConnect();//发起http请求的对象 
//	        webConnect.initWebClient();
//	        String resp = (String)webConnect.executeHttpGet(url);
//		}
		System.out.println(wxUser.getCity());
		System.out.println(wxUser.getLanguage());
		System.out.println(wxUser.getOpenUserId());
		System.out.println(wxUser.getSex());
		System.out.println(wxUser.getState());
		System.out.println(wxUser.getUserName());
		System.out.println(wxUser.getHeadimgurl());
		request.getSession().setAttribute("wxUser", wxUser);
	}
	
}

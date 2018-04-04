package com.phxl.ysy.web.weixin;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.phxl.core.base.util.SystemConfig;
import com.phxl.ysy.util.WebConnect;
import com.phxl.ysy.web.weixin.ticket.JsapiTicket;
import com.phxl.ysy.web.weixin.ticket.JsapiTicketInfo;

/**
 * * 用于获取accessToken的Servlet
 * @author taoyou
 * Created Date 2016/10/20.
 */
//wxa0d673d35aab0631
//af614fe662a31083f198dfb245e74b82
//wx554fbaf63a3f7fe8
//9198736b4bf40b0018e975565909e054
@WebServlet(
        name = "AccessTokenServlet",
        urlPatterns = {"/AccessTokenServlet"},
        loadOnStartup = 1//,
       // initParams = {
       //         @WebInitParam(name = "appId", value = "wx554fbaf63a3f7fe8"),
       //         @WebInitParam(name = "appSecret", value = "9198736b4bf40b0018e975565909e054")
        //}
        )
public class AccessTokenServlet extends HttpServlet {
	
	    private Log log = LogFactory.getLog(getClass());
	    
        private String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";

        private String getTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";

        /**
         * 微信自定义菜单
         */
        private String setMenu = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={0}";
        
	    @Override
	    public void init() throws ServletException {
	        log.info("启动WebServlet");
	        super.init();

	        //final String appId = getInitParameter("appId");
	        //final String appSecret = getInitParameter("appSecret");

	        final String appId = SystemConfig.getProperty("wechat.config.appid");
	        final String appSecret = SystemConfig.getProperty("wechat.config.secret");
	        //开启一个新的线程
	        new Thread(new Runnable() {
	            public void run() {
	                while (true) {
	                    try {
	                        //获取accessToken
	                        AccessTokenInfo.accessToken = getAccessToken(appId, appSecret);
	                        //获取成功
	                        if (AccessTokenInfo.accessToken != null) {//获取jsapiTicket
	                            JsapiTicketInfo.jsapiTicket = getJsapiTicket(AccessTokenInfo.accessToken.getAccessToken());
	                            
	                            System.out.println(AccessTokenInfo.accessToken.getAccessToken()+"---------------accessToken");
	                            System.out.println(JsapiTicketInfo.jsapiTicket.getTicket()+"+++++++++++++++ticket");
	                            String url = MessageFormat.format(setMenu,AccessTokenInfo.accessToken.getAccessToken());
	                            WebConnect webConnect = new WebConnect();//发起http请求的对象 
	                        	webConnect.initWebClient();
	                            HttpPost post = new HttpPost(url);
	                            ResponseHandler<?> responseHandler = new BasicResponseHandler();
	                            //生成临时二维码需要的json数据
	                            JSONObject json = new JSONObject();
	                            json.put("button", "[ {'type': 'view', 'name': '单据查询', 'url': 'http://admin.ysynet.com/webApp/login'}, "+
        "{'type': 'view', 'name': '设备管理', 'url': 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa0d673d35aab0631&redirect_uri=http%3A%2F%2Fhsms.com.cn%2Fmeqm%2Fhome%2FwechatBinding&response_type=code&scope=snsapi_userinfo&state=null#wechat_redirect'}]");
	                            post.setHeader("Content-Type", "application/json");
	                            //将json数据封装
	                            StringEntity s = new StringEntity(json.toString(), "utf-8");
	                            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
	                            post.setEntity(s);
	                            //响应数据
	                            JSONObject j = JSONObject.fromObject(webConnect.getWebClient().execute(post, responseHandler));
	                            System.out.println(j);
	                            
	                            if (JsapiTicketInfo.jsapiTicket != null) {
		                            //获取到access_token 休眠7000秒,大约2个小时左右
		                            Thread.sleep(7000 * 1000);
		                            //Thread.sleep(10 * 1000);//10秒钟获取一次
								}
	                           
	                        } else {
	                            //获取失败
	                            Thread.sleep(1000 * 7000); //获取的access_token为空 休眠3秒
	                        }
	                    } catch (Exception e) {
	                        log.error("发生异常：" + e.getMessage());
	                        e.printStackTrace();
	                        try {
	                            Thread.sleep(1000 * 7000); //发生异常休眠1秒
	                        } catch (Exception e1) {

	                        }
	                    }
	                }

	            }
	        }).start();
	    }

	    /**
	     * 获取access_token
	     *
	     * @return AccessToken
	     */
	    private AccessToken getAccessToken(String appId, String appSecret) {
	        WebConnect webConnect = new WebConnect();
	        AccessToken token = new AccessToken();
	        webConnect.initWebClient();
	        String Url = MessageFormat.format(getTokenUrl, appId, appSecret);
	        //此请求为https的get请求，返回的数据格式为{"access_token":"ACCESS_TOKEN","expires_in":7200}
	        String result;
			try {
				result = webConnect.executeHttpGet(Url);
				log.info("刷新的access_token="+result);
		        //使用FastJson将Json字符串解析成Json对象
		        JSONObject json = JSONObject.fromObject(result);		       
		        token.setAccessToken(json.getString("access_token"));
		        token.setExpiresin(json.getInt("expires_in"));
			} catch (ClientProtocolException e) {
				log.error("获取accesss_token，HTTP协议异常");
				e.printStackTrace();
			} catch (IOException e) {
				log.error("获取accesss_token，I/O异常");
				e.printStackTrace();
			}	        
	        return token;
	    }
	    
	    /**
	     * 获取JsapiTicket
	     *
	     * @return JsapiTicket
	     */
	    private JsapiTicket getJsapiTicket(String accessToken) {
	        WebConnect webConnect = new WebConnect();
	        JsapiTicket ticket = new JsapiTicket();
	        webConnect.initWebClient();
	        String Url = MessageFormat.format(getTicketUrl, accessToken);
	        //此请求为https的get请求，返回的数据格式为
	        //{"errcode":0,"errmsg":"ok","ticket":"HoagFKDcsGMVCIY2vOjf9r6xLqiVxkx1R_hB_2sDnrQjmSrMuo-wgibll6EWVTyNXfHITEf6hLErnGCkNuZ7Lw","expires_in":7200}
	        String result;
			try {
				result = webConnect.executeHttpGet(Url);
				log.info("刷新的=JsapiTicket"+result);
		        //使用FastJson将Json字符串解析成Json对象
		        JSONObject json = JSONObject.fromObject(result);		       
		        ticket.setErrcode(json.getString("errcode"));	       
		        ticket.setErrmsg(json.getString("errmsg"));	       
		        ticket.setTicket(json.getString("ticket"));	       
		        ticket.setExpires_in(json.getInt("expires_in"));
			} catch (ClientProtocolException e) {
				log.error("获取JsapiTicket，HTTP协议异常");
				e.printStackTrace();
			} catch (IOException e) {
				log.error("获取JsapiTicket，I/O异常");
				e.printStackTrace();
			}	        
	        return ticket;
	    }
}

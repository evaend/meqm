package com.phxl.ysy.web.weixin.ticket;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.phxl.core.base.util.SystemConfig;
import com.phxl.ysy.util.WebConnect;
import com.phxl.ysy.web.weixin.AccessToken;
import com.phxl.ysy.web.weixin.AccessTokenInfo;
import com.phxl.ysy.web.weixin.AccessTokenServlet;

/**
 * * 用于获取JsapiTicket的Servlet
 * @author 张艳丽
 * Created Date 2017/12/13
 */
@WebServlet(
        name = "JsapiTicketServlet",
        urlPatterns = {"/JsapiTicketServlet"}
        )
public class JsapiTicketServlet extends HttpServlet {

    private Log log = LogFactory.getLog(getClass());
    
    private String getTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";

    public void init() throws ServletException {
        log.info("启动WebServlet");
        super.init();
    	if (AccessTokenInfo.accessToken == null) {
			AccessTokenServlet ser = new AccessTokenServlet();
			ser.init();
		}
        //获取jsapiTicket
        JsapiTicketInfo.jsapiTicket = getJsapiTicket(AccessTokenInfo.accessToken.getAccessToken());
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

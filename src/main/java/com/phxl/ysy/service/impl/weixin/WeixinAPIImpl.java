package com.phxl.ysy.service.impl.weixin;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import org.apache.http.RequestLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phxl.ysy.entity.WeixinOpenUser;
import com.phxl.ysy.service.weixin.WeixinAPIInterface;
import com.phxl.ysy.util.WebConnect;
import com.phxl.ysy.web.weixin.AccessTokenInfo;

import net.sf.json.JSONObject;

@Component
public class WeixinAPIImpl implements WeixinAPIInterface
 {
    /**
     * 获取token接口
     */
    private String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
    /**
     * 拉微信用户信息接口
     */
    private String getUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}";
    /**
     * 获取用户openid接口
     */
    private String getUserOpenIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
    /**
     * 主动推送信息接口
     */
    //private String sendMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/send?access_token={0}";
    //private String sendMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token={0}";
    private String templateMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
    
 
    private Log log = LogFactory.getLog(getClass());   
    
    /**
     * @desc 获取授权token
     * @param appid
     * @param secret
     * @return
     */
    public String getAccessToken(String appid,String secret) {
        String accessToken = null;
        WebConnect webConnect = new WebConnect();//发起http请求的对象    
        webConnect.initWebClient();
        try {
//            log.info("getAccessToken start.{appid=" + appid + ",secret:" + secret + "}");
            String url = MessageFormat.format(this.getTokenUrl, appid, secret);
            String response = webConnect.executeHttpGet(url);
            JSONObject obj = JSONObject.fromObject(response);  
            accessToken = obj.getString("access_token"); 
        } catch (Exception e) {
            log.error("get access toekn exception", e);
        }
        return accessToken;
    }
    
    /**
     * @desc 推送信息
     * @param token
     * @param msg
     * @return
     */
    public String sendMessage(String token,String msg){
        try{
            log.info("sendMessage start.token:"+token+",msg:"+msg);
            WebConnect webConnect = new WebConnect();//发起http请求的对象 
        	webConnect.initWebClient();
            String url = MessageFormat.format(this.templateMsgUrl, token);
            HttpPost post = new HttpPost(url);
            ResponseHandler<?> responseHandler = new BasicResponseHandler();
            StringEntity entity = new StringEntity(msg,HTTP.UTF_8);
            post.setEntity(entity);
            String response = (String) webConnect.getWebClient().execute(post, responseHandler);
            return response;
             
        }catch (Exception e) {
            log.error("get user info exception", e);
            return null;
        }   
    }
    /**
     * @desc 拉取用户信息
     * @param token
     * @param openid
     * @return
     */
    public WeixinOpenUser getUserInfo(String openid) {
        try {
            String url = MessageFormat.format(this.getUserInfoUrl, AccessTokenInfo.accessToken.getAccessToken(), openid);
            WebConnect webConnect = new WebConnect();//发起http请求的对象 
            webConnect.initWebClient();
            String response = (String)webConnect.executeHttpGet(url);
            response = new String(response.getBytes(Charset.forName(HTTP.ISO_8859_1)));
            ObjectMapper mapper = new ObjectMapper();  
            JsonNode json = mapper.readTree(response);
            if (json.get("openid") != null) {
                WeixinOpenUser user = new WeixinOpenUser();
                user.setOpenUserId(json.get("openid").asText());
                user.setState(json.get("subscribe").asText());
                if ("1".equals(user.getState())) {
                    user.setUserName(json.get("nickname").asText());
                    user.setSex(json.get("sex").asText());
                    user.setCity(json.get("city").asText());
                    user.setLanguage(json.get("language").asText());
                    user.setHeadimgurl(json.get("headimgurl").asText());
                }
                return user;
            }
        } catch (Exception e) {
            log.error("get user info exception", e);
        }
        return null;
    }
    
    /**
     * 获取关注者的openid
     */
    public String getOpenid(String appid, String secret, String code) {
		String userOpenId = null;
		WebConnect webConnect = new WebConnect();//发起http请求的对象 
		webConnect.initWebClient();
        try {
            //log.info("getOpenId start.{appid=" + appid + ",secret:" + secret + ",code:" + code + "}");
            String url = MessageFormat.format(this.getUserOpenIdUrl, appid, secret,code);
            String response = webConnect.executeHttpGet(url);
            JSONObject obj = JSONObject.fromObject(response);  
            userOpenId = obj.getString("openid"); 
        } catch (Exception e) {
            log.error("get openid exception", e);
        }
        return userOpenId;
	}
 
    /**
  	 * 初始化或重启时，自动执行一次更新token的任务
  	 * @param event
  	 */
  	/*@Override
  	public void onApplicationEvent(ContextRefreshedEvent event) {
  		this.getAccessToken();
  	}
    */
    public static void main(String[] args) {
    	
    	String msg = "  { \"touser\":\"oKGyvw3bm7sBG_gv4r5ezhghsrc4\","+
           "\"template_id\":\"7ZCM085E7DyvxJl1g8TTGK6cTnJDwhrnZad6EfiSWmY\","+      
           "\"data\":{"+
                   "\"first\": {"+
                       "\"value\":\"密码重置提醒\","+
                       "\"color\":\"#173177\""+
                   "},"+
                   "\"keyword1\":{"+
                       "\"value\":\"1235\","+
                       "\"color\":\"#173177\""+
                   "},"+
                   "\"keyword2\": {"+
                    "   \"value\":\"2016-08-22 14:32:34\","+
                    "   \"color\":\"#173177\""+
                  " },"+
                   "\"remark\":{"+
                    "   \"value\":\"请尽快登陆系统，并及时保存修改密码\","+
                   "    \"color\":\"#173177\""+
                  " }"+
           "}}";
    	
		//String accTo = wxa.getAccessToken("wxa0d673d35aab0631","af614fe662a31083f198dfb245e74b82");
		//System.out.println(accTo);
    	//String msg = "{   \"filter\":{\"is_to_all\":true},"+
   //"\"text\":{\"content\":\"CONTENT\"}, \"msgtype\":\"text\";}";
		//System.out.println(wxa.sendMessage(accTo,msg));
    } 
}


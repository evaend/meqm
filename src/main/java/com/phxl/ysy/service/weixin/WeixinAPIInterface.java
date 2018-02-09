package com.phxl.ysy.service.weixin;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.phxl.core.base.exception.ValidationException;
import com.phxl.ysy.entity.WeixinOpenUser;

public interface WeixinAPIInterface {
	
	//获取AccessToken,appid和secret都是公众号里带的
	//AccessToken是有两个小时的有效期，而且一天不能获取太多次AccessToken，建议两个小时刷新一次
	String getAccessToken(String appid,String secret);
	
	//获取公众号关注者的openid,
	//appid和secret都是公众号里带的,code是动态变化的，从微信跳转的网页带的
	String getOpenid(String appid, String secret,String code)  throws ClientProtocolException, IOException, ValidationException;
	
	//利用获取的AccessToken,推送消息，msg为json串，包含模板id和接受者的openid
	String sendMessage(String token,String msg);
	
	//根据token和用户的openid获取用户的其他信息
	WeixinOpenUser getUserInfo(String openid);
	
	//微信生成临时二维码的ticket
	String getWeixinTicket(String token,Map<String, Object> actionInfo) throws Exception;
}

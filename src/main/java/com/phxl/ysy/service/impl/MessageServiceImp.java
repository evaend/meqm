package com.phxl.ysy.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.core.base.util.JSONUtils;
import com.phxl.ysy.entity.Messagewechat;
import com.phxl.ysy.entity.UserInfo;
import com.phxl.ysy.service.IMessageService;
import com.phxl.ysy.service.weixin.WeixinAPIInterface;
import com.phxl.ysy.web.weixin.AccessTokenInfo;

/**
 * 消息实现类
 */
@Service
public class MessageServiceImp extends BaseService implements IMessageService{
    
	public final static Logger logger = LoggerFactory.getLogger("message");
	@Autowired
	WeixinAPIInterface weixinAPIInterface;
	
	/**
	 * 生成微信JSON串
	 * @throws JsonProcessingException 
	 */
	public String getMessageJsonContent(Map<String, Object> argument,String messageWechatId,String url,String userid){
		String WeChatContent = "";
		JSONUtils json = new JSONUtils();
		/** 查询微信模板信息 **/
		Messagewechat mwc = this.find(Messagewechat.class, messageWechatId);
		Map<String,Object> map = new HashMap<String, Object>();
		UserInfo userInfo = this.find(UserInfo.class, userid);
		if(StringUtils.isNotBlank(userInfo.getWechatOpenid())){
			map.put("touser", userInfo.getWechatOpenid());//openid
			map.put("template_id", mwc.getMcTemplateid());//template_id
			map.put("url", url);
			//map.put("url", "");//url,做详情的，应该可以不要
			/**	需要传输的参数内容，参数key从数据库中取	 */
			Map<String,Object> mapdata = new HashMap<String, Object>();
			//假设数据库中是这样存储的
			//{{first.DATA}}单据类型：{{keyword1.DATA}}
			//单据状态：{{keyword2.DATA}}更新时间：{{keyword3.DATA}}{{remark.DATA}}
	        //从这里取出{{}}之间的参数
			String content = mwc.getMcTemplatecontent();
			String[] contentCodes = getInArgumentCode(content);
			for(String contentCode:contentCodes){
				if(argument.get(contentCode) != null){
					Map<String,Object> datadetail = new HashMap<String, Object>();
					datadetail.put("value",argument.get(contentCode).toString());
					mapdata.put(contentCode, datadetail);
				}
			}		
			map.put("data", mapdata);
			try {
				WeChatContent = json.toPrettyJson(map);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("WeChatContent="+WeChatContent);
		return WeChatContent;
	}
	
	/**  解析字符串获取其中存在的参数**/
	public String[] getInArgumentCode(String checkStr){
		String[] strs = checkStr.split(".DATA}}");
		String[] returnAttr = new String[strs.length];
		for(int i = 0; i < strs.length ; i++){
			returnAttr[i] = strs[i].substring(strs[i].indexOf("{{")+2);
		}
		return returnAttr;
	}
	/**
	 * 推送消息	 */
	@Override
	public void pushMessages(String WeChatContent) {
		// TODO Auto-generated method stub
		String returnCode = "0";//返回状态，微信需要返回，站内信默认是成功的
		//微信类型的、有效消息会调用这个微信发送接口
		//accessToken定时刷新，频率大概约等于2小时
		returnCode = weixinAPIInterface.sendMessage(AccessTokenInfo.accessToken.getAccessToken(), WeChatContent);		
		
	}	

}

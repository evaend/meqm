package com.phxl.ysy.service;

import java.util.Map;

import com.phxl.core.base.service.IBaseService;

public interface IMessageService extends IBaseService {
	//封装消息
	String getMessageJsonContent(Map<String, Object> argument,String messageWechatId,String url,String userid);
	
	//推送消息,如果有多条消息微信推送，以逗号分割
	void pushMessages(String messages);
}

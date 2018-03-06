package com.phxl.ysy.service;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.service.IBaseService;
import com.phxl.ysy.entity.RrpairOrder;

public interface IMessageService extends IBaseService {
	//封装消息
	String getMessageJsonContent(Map<String, Object> argument,String messageWechatId,String url,String userid);
	
	//推送消息,如果有多条消息微信推送，以逗号分割
	void pushMessages(String messages);
	
    //根据维修单ID查询对应资产的管理科室维修员(分别推送消息)
    void selectBDeptUser(String rrpairOrderGuid);
    
    //向多人推送消息（用于报修）
    void userListPush(List<Map<String, Object>> resultMaps,RrpairOrder rrpairOrder);
}

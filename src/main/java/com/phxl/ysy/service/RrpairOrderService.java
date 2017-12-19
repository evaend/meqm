package com.phxl.ysy.service;

import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.IBaseService;

public interface RrpairOrderService extends IBaseService {
	//查询设备维修列表
	List<Map<String, Object>> selectRrpairList(Pager pager);

	//（移动端）查询设备维修各状态数量
	List<Map<String, Object>> selectRrpairFstateNum();

	//查询备注/评论
	String selectRrpairContent(String rrpairOrder,Integer type);

	//修改备注/评论
	void updateRrpairContent(String rrpairOrder,Integer type,String value);
	
	//生成单号
	String callSpGetBill(Long billOrgId, String billName, String billPrefix, Integer asLen) throws ValidationException ;
	
	//微信推送消息(修改维修信息)
	void pushMessage(String rrpairOrder);
	
}

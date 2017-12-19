package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;



import com.phxl.core.base.entity.Pager;

public interface RrpairOrderMapper {
	//查询设备维修列表
	List<Map<String, Object>> selectRrpairList(Pager pager);
	
	//（移动端）查询设备维修各状态数量
	Map<String, Object> selectRrpairFstateNum(Map<String, Object> map);
	
	//（移动端）查询设备维修记录全部
	Map<String, Object> selectRrpairFstateCount();
	
	//查询备注/评论
	String selectRrpairContent(Map<String, Object> map);

	//修改备注/评论
	void updateRrpairContent(Map<String, Object> map);
	
	/**
	 * 获取单号
	 * @author	黄文君
	 * @date	2017年6月21日 上午10:44:00
	 * @param	params
	 * @return	void
	 */
	void SP_GET_BILL_NOSTORAGE(Map<String, Object> params);
}
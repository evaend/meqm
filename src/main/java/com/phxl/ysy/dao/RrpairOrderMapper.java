package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.RrpairOrder;
import com.phxl.ysy.entity.StaticData;

public interface RrpairOrderMapper {
	//查询维修工单信息——（验收信息）
	Map<String, Object> selectRrpairDetailIsAcce(Pager pager);
	
	//查询维修工单信息——（维修信息）
	Map<String, Object> selectRrpairDetailIsRrpair(Pager pager);
	
	//查询维修工单信息——（指派信息）
	Map<String, Object> selectRrpairDetailIsCall(Pager pager);
	
	//查询维修工单信息——（报修信息）
	Map<String, Object> selectRrpairDetailIsOrder(Pager pager);
	
	//查询维修工单信息——（资产信息）
	Map<String, Object> selectRrpairDetailIsAssets(Pager pager);
	
	//查询维修工单信息——（维修单）
	Map<String, Object> selectRrpairDetail(Pager pager);
	
	//查询设备维修列表
	List<Map<String, Object>> selectRrpairDetailList(Pager pager);
	
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

	/**
	 * 查询维修操作记录
	 * @param pager
	 * @return
	 */
	List<Map<String,Object>> selectEqOperationList(Pager pager);

	//查询设备维修列表
	List<Map<String, Object>> selectRrpairList(Pager pager);
	
	//查询当前资产是否正在维修
    List<Map<String, Object>> selectAssetsIsUsable(Map<String, Object> map);
}
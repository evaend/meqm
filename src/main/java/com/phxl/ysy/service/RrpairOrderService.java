package com.phxl.ysy.service;

import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.IBaseService;
import com.phxl.ysy.entity.AssetsExtend;
import com.phxl.ysy.entity.AssetsRecord;
import com.phxl.ysy.entity.Equipment;
import com.phxl.ysy.entity.RrpairOrder;
import com.phxl.ysy.entity.RrpairOrderAcce;

public interface RrpairOrderService extends IBaseService {
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
	List<Map<String, Object>> selectRrpairFstateNum();

	/*//查询备注/评论
	String selectRrpairContent(String rrpairOrder,Integer type);

	//修改备注/评论
	void updateRrpairContent(String rrpairOrder,Integer type,String value);
*/	
	//生成单号
	String callSpGetBill(Long billOrgId, String billName, String billPrefix, Integer asLen) throws ValidationException ;
	
	//微信推送消息(修改维修信息)
	void pushMessage(String rrpairOrder);

	/**
	 * 查询维修操作记录
	 * @param pager
	 * @return
	 */
	List<Map<String,Object>> selectEqOperationList(Pager pager);

	//查询设备维修列表
	List<Map<String, Object>> selectRrpairList(Pager pager);
	
	/**
	 * 添加维修记录
	 * @param rrpairOrder
	 */
	void insertRrpairOrder(RrpairOrder rrpairOrder,AssetsRecord assetsRecord,List<String> assetsExtendGuid,List<Integer> acceNum) throws Exception;
	
	//查询当前维修记录的维修配件使用列表
	List<Map<String, Object>> selectRrpairFittingList(Pager pager);
	
	void insertRrpairOrderAcce(RrpairOrderAcce rrpairOrderAcce, RrpairOrder rrpairOrder);
	
	void insertRrpairFitting(RrpairOrder rrpairOrder,String rrpairOrderGuid, List<String> assetsExtendGuid, List<Integer> acceNum)throws ValidationException ;
}

package com.phxl.ysy.dao;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.Delivery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DeliveryMapper {

	List<Map<String, Object>> searchDeliveryList(Pager<Map<String, Object>> pager);

	//sendId可以是id也可以是sendNo
	List<Delivery> getDelivery(String sendId);

	void batchUpdateDeliveryFstate(@Param("sendIds")Set<String> sendIds, @Param("checkFstate")String checkFstate, @Param("rejectReason")String rejectReason);

	Set<String> searchOrderIdsBySendIds(@Param("sendIds")String[] sendIds);
	/**
	 * 判断指定订单的产品是否已经全部验收通过（true:全部验收完成；false：未全部验收完成）
	 */
	Boolean isOrderFullThrough(String orderId);

	void batchUpdateOrderFstate(@Param("orderIds")Set<String> oderIds, @Param("fstate")String fstate);

	/**
	 * 生成送货单：更新送货单相关的数据保存标识
	 * @param sendId
	 */
	void updateSaveFlagBySendId(String sendId);

	/**
	 * 获取指定送货单手术包的连续的序号
	 * @param sendId
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> findNewPackageIdMapping(String sendId);

	/**
	 * 更新指定送货单手术包的连续的序号
	 * @param sendId
	 * @param newPackageIdMapping
	 */
	void updatePackageId(@Param("sendId")String sendId, @Param("pp") List<Map<String, Object>> newPackageIdMapping);

}
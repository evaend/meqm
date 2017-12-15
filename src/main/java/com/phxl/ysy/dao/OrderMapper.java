package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.Order;
import com.phxl.ysy.entity.OrderDetail;
import com.phxl.ysy.web.dto.DeliveryDetailDto;

public interface OrderMapper {
	
	/**
	 * 查询订单列表
	 * @author	黄文君
	 * @date	2017年6月16日 上午10:58:08
	 * @param	pager
	 * @return	List<Order>
	 */
	List<Order> findOrderList(Pager<Order> pager);
	
	/**
	 * 根据需方库房，明确强势方
	 * @author	黄文君
	 * @date	2017年6月23日 上午10:26:21
	 * @param	storageGuid		需方库房guid
	 * @param	fOrgId			供方机构id
	 * @return	Long
	 */
	Long findKingOrgIdByStorageGuid(@Param("storageGuid")String storageGuid, @Param("fOrgId")Long fOrgId);
	
	/**
	 * 批量插入订单信息
	 * @author	黄文君
	 * @date	2017年6月15日 下午4:56:57
	 * @param	orderList
	 * @return	int
	 */
	int insertOrderBatch(@Param("orderList")List<Order> orderList);
	
	/**
	 * 确定产品列表由哪几个供应商供货
	 * @author	黄文君
	 * @date	2017年6月15日 下午2:44:14
	 * @param	products	产品列表
	 * @return	Long[]
	 */
	Long[] findSuppliersByDetails(@Param("products")List<OrderDetail> products);

	/**
	 * 判断指定订单是否已经备货完成（true:全部已发货；false：未发货或部分发货）
	 * @author	黄文君
	 * @date	2017年6月18日 下午3:00:52
	 * @param	orderId
	 * @return	Integer
	 */
	Integer judgeFullReceiveToBuyer(String orderId);
	
	/**
	 * 指定订单:过滤出非法备货的备货产品
	 * @author	黄文君
	 * @date	2017年6月19日 下午4:36:22
	 * @param	orderId			订单id
	 * @param	details			产品备货明细
	 * @return	List<Map<String,Object>>
	 */
	List<Map<String, Object>> filterSettleGoods(@Param("orderId")String orderId, @Param("details")List<DeliveryDetailDto> details);

	/**
	 * 某一个订单备货之前，清理临时包工具数据
	 * @author	黄文君
	 * @date	2017年9月4日 下午4:49:19
	 * @param	orderId
	 * @return	int
	 */
	int clearByOrderId(String orderId);

}
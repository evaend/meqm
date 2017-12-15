package com.phxl.ysy.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.Order;
import com.phxl.ysy.entity.OrderDetail;
import com.phxl.ysy.web.dto.DeliveryDetailDto;

public interface OrderDetailMapper {
	
	/**
	 * 查询指定订单的产品明细列表
	 * @author	黄文君
	 * @date	2017年6月16日 下午5:34:46
	 * @param	pager
	 * @return	List<OrderDetail>
	 */
	List<OrderDetail> findDetailListByOrderId(Pager<OrderDetail> pager);

	/**
	 * 批量按供应商拆分产品明细
	 * @author	黄文君
	 * @date	2017年6月15日 下午4:15:46
	 * @param	orderList		多个订单
	 * @param	products		产品列表
	 * @return	void
	 */
	public int insertBatchByFOrgids(@Param("orderList")List<Order> orderList, @Param("products")List<OrderDetail> products);
	
	/**
	 * 批量插入草稿产品明细列表
	 * @author	黄文君
	 * @date	2017年6月26日 下午1:55:27
	 * @param	orderId			订单id
	 * @param	products		产品列表
	 * @return	void
	 */
	public int insertDraftDetailList(@Param("orderId")String orderId, @Param("products")List<OrderDetail> products);
	
	/**
	 * 计算指定订单的产品总金额
	 * @author	黄文君
	 * @date	2017年6月19日 下午7:04:48
	 * @param	orderId			订单id
	 * @return	BigDecimal
	 */
	BigDecimal evalTotalPriceByOrderId(String orderId);
	
	/**
	 * 统计汇总:产品列表各产品属性分布
	 * @author	黄文君
	 * @date	2017年9月6日 上午11:58:31
	 * @param	gkAttributes
	 * @param	materialList
	 * @return	Map<String, BigDecimal>
	 */
	Map<String, BigDecimal> subtotalOfAttributeIds(
			@Param("gkAttributes")List<Map<String, String>> gkAttributes, 
			@Param("materialList")List<DeliveryDetailDto> materialList);
	
}
package com.phxl.ysy.service;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.IBaseService;
import com.phxl.ysy.entity.StorageInfo;
import com.phxl.ysy.entity.Order;
import com.phxl.ysy.entity.OrderDetail;
import com.phxl.ysy.web.dto.DeliveryDetailDto;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderService extends IBaseService {

	/**
	 * 查询订单列表
	 * @author	黄文君
	 * @date	2017年6月16日 上午10:58:08
	 * @param	pager
	 * @return	List<Order>
	 */
	public abstract List<Order> findOrderList(Pager<Order> pager);

	/**
	 * 查询指定订单的产品明细列表
	 * @author	黄文君
	 * @date	2017年6月16日 下午5:34:46
	 * @param	pager
	 * @return	List<OrderDetail>
	 */
	public abstract List<OrderDetail> findDetailListByOrderId(Pager<OrderDetail> pager);

	/**
	 * 确定产品列表由哪几个供应商供货
	 * @author	黄文君
	 * @date	2017年6月15日 下午2:44:14
	 * @param	products	产品列表
	 * @return	Long[]
	 */
	public abstract Long[] findSuppliersByDetails(List<OrderDetail> products);

	/**
	 * 拆分并提交订单（拆单到各个供应商）
	 * @author	黄文君
	 * @date	2017年6月15日 下午4:12:46
	 * @param	currentStorage	当前库房
	 * @param	draftOrderId	草稿orderId
	 * @param	orderList		多个订单
	 * @param	products		产品列表
	 * @return	void
	 */
	public abstract void summitSplitOrder(StorageInfo currentStorage, String draftOrderId, List<Order> orderList, List<OrderDetail> products);

	/**
	 * 更新订单状态，并添加订单状态更新记录（单个与批量同时支持）
	 * @author	黄文君
	 * @date	2017年6月15日 下午2:03:01
	 * @param	orderIds	订单id列表
	 * @param	fstate		状态
	 * @param	session		会话
	 * @return	void
	 */
	public abstract void updateOrderFstate(String[] orderIds, String fstate, String cancleReason, HttpSession session) throws Exception ;

	/**
	 * 提交订单（共用）
	 * @author	黄文君
	 * @date	2017年6月26日 下午3:24:24
	 * @param	order			订单信息
	 * @param	session
	 * @throws	Exception
	 * @return	void
	 */
	public void summitOrderInternal(Order order, HttpSession session) throws Exception;

	/**
	 * 判断指定订单是否已经备货完成（true:全部已发货；false：未发货或部分发货）
	 * @author	黄文君
	 * @date	2017年6月18日 下午3:00:52
	 * @param	orderId
	 * @return	Integer
	 */
	public abstract Integer judgeFullReceiveToBuyer(String orderId);

	/**
	 * 指定订单:过滤出非法备货的备货产品
	 * @author	黄文君
	 * @date	2017年6月19日 下午4:36:22
	 * @param	orderId			订单id
	 * @param	details			产品备货明细
	 * @return	List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> filterSettleGoods(String orderId, List<DeliveryDetailDto> details);

	/**
	 * 某一个订单备货之前，清理临时包工具数据
	 * @author	黄文君
	 * @date	2017年9月4日 下午4:49:19
	 * @param	orderId
	 * @return	int
	 */
	public abstract void clearByOrderId(String orderId);

	/**
	 * 根据需方库房，明确强势方
	 * @author	黄文君
	 * @date	2017年6月23日 上午10:26:21
	 * @param	rStorageGuid	需方库房guid
	 * @param	fOrgId			供方机构id
	 * @return	Long
	 */
	public Long findKingOrgIdByStorageGuid(String rStorageGuid, Long fOrgId);

	/**
	 * 保存草稿信息及其产品列表
	 * @author	黄文君
	 * @date	2017年6月26日 下午2:05:03
	 * @param	isEdit		是否草稿编辑操作
	 * @param	order		草稿主信息
	 * @param	products	产品列表
	 * @return	void
	 * @throws	ValidationException 
	 */
	public abstract void saveDraft(Boolean isEdit, Order order, List<OrderDetail> products) throws ValidationException;

	/**
	 * 删除草稿及其产品列表
	 * @author	黄文君
	 * @date	2017年6月27日 下午3:36:10
	 * @param	orderId		订单id
	 * @return	void
	 * @throws	ValidationException 
	 */
	public abstract void deleteDraftWithAsso(String orderId) throws ValidationException;

	/**
	 * 统计汇总:产品列表各产品属性分布
	 * @author	黄文君
	 * @date	2017年9月6日 上午11:58:31
	 * @param	gkAttributes
	 * @param	materialList
	 * @return	Map<String, BigDecimal>
	 */
	public abstract Map<String, BigDecimal> subtotalOfAttributeIds(List<Map<String, String>> gkAttributes, List<DeliveryDetailDto> materialList);

}
package com.phxl.ysy.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.core.base.util.IdentifieUtil;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.ysy.entity.Address;
import com.phxl.ysy.entity.StorageInfo;
import com.phxl.ysy.service.ProcedureService;
import com.phxl.ysy.service.StorageService;
import com.phxl.ysy.dao.DeliveryDetailMapper;
import com.phxl.ysy.dao.DeliveryMapper;
import com.phxl.ysy.dao.DeliveryPackageMapper;
import com.phxl.ysy.dao.OrderDetailMapper;
import com.phxl.ysy.dao.OrderMapper;
import com.phxl.ysy.dao.PackageToolMapper;
import com.phxl.ysy.entity.ChangeFstate;
import com.phxl.ysy.entity.Order;
import com.phxl.ysy.entity.OrderDetail;
import com.phxl.ysy.service.OrderService;
import com.phxl.ysy.web.dto.DeliveryDetailDto;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.constant.CustomConst.ChangeFstateType;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.constant.CustomConst.OrderFstate;
import com.phxl.ysy.util.BillAdapter;

@Service
public class OrderServiceImpl extends BaseService implements OrderService {
	
	@Autowired
	OrderMapper				orderMapper;
	@Autowired
	OrderDetailMapper		orderDetailMapper;
	@Autowired
	DeliveryDetailMapper	deliveryDetailMapper;
	@Autowired
	DeliveryPackageMapper	deliveryPackageMapper;
	@Autowired
	PackageToolMapper		packageToolMapper;
	@Autowired
	ProcedureService        procedureService;
	@Autowired
	StorageService          storageService;
	@Autowired
	DeliveryMapper          deliveryMapper;

	/**
	 * 查询订单列表
	 * @author	黄文君
	 * @date	2017年6月16日 上午10:58:08
	 * @param	pager
	 * @return	List<Order>
	 */
	
	public List<Order> findOrderList(Pager<Order> pager) {
		return orderMapper.findOrderList(pager);
	}

	/**
	 * 根据需方库房，明确强势方
	 * @author	黄文君
	 * @date	2017年6月23日 上午10:26:21
	 * @param	rStorageGuid	需方库房guid
	 * @param	fOrgId			供方机构id
	 * @return	Long
	 */
	
	public Long findKingOrgIdByStorageGuid(String rStorageGuid, Long fOrgId) {
		return orderMapper.findKingOrgIdByStorageGuid(rStorageGuid, fOrgId);
	}

	/**
	 * 查询指定订单的产品明细列表
	 * @author	黄文君
	 * @date	2017年6月16日 下午5:34:46
	 * @param	pager
	 * @return	List<OrderDetail>
	 */
	
	public List<OrderDetail> findDetailListByOrderId(Pager<OrderDetail> pager) {
		return orderDetailMapper.findDetailListByOrderId(pager);
	}

	/**
	 * 判断指定订单是否已经备货完成（true:全部已发货；false：未发货或部分发货）
	 * @author	黄文君
	 * @date	2017年6月18日 下午3:00:52
	 * @param	orderId
	 * @return	Integer
	 */
	
	public Integer judgeFullReceiveToBuyer(String orderId) {
		return orderMapper.judgeFullReceiveToBuyer(orderId);
	}

	/**
	 * 指定订单:过滤出非法备货的备货产品
	 * @author	黄文君
	 * @date	2017年6月19日 下午4:36:22
	 * @param	orderId			订单id
	 * @param	details			产品备货明细
	 * @return	ListMap<String,Object>>
	 */
	
	public List<Map<String, Object>> filterSettleGoods(String orderId, List<DeliveryDetailDto> details) {
		return orderMapper.filterSettleGoods(orderId, details);
	}

	/**
	 * 确定产品列表由哪几个供应商供货
	 * @author	黄文君
	 * @date	2017年6月15日 下午2:44:14
	 * @param	products	产品列表
	 * @return	List<SourceInfo>
	 */
	
	public Long[] findSuppliersByDetails(List<OrderDetail> products) {
		return orderMapper.findSuppliersByDetails(products);
	}
	
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
	
	public void summitSplitOrder(StorageInfo currentStorage, String draftOrderId, List<Order> orderList, List<OrderDetail> products){
		//批量按供应商拆分产品明细
		int num2 = orderDetailMapper.insertBatchByFOrgids(orderList, products);
		logger.debug("插入{}条订单明细", num2);
		//批量按供应商拆分订单
		int num1 = orderMapper.insertOrderBatch(orderList);
		logger.debug("新建{}个订单", num1);
		//清理原先的草稿数据
		if(draftOrderId!=null){
			//清理旧的草稿明细
			OrderDetail condition = new OrderDetail();
			condition.setOrderId(draftOrderId);
			int num3 = super.deleteInfo(condition);
			if(num3>0){logger.debug("清理草稿: 已清理{}条订单明细", num3);}
			//清理旧的草稿主信息
			int num = super.deleteInfoById(Order.class, draftOrderId);
			if(num>0){logger.debug("清理草稿: 已清理{}个草稿", num);}
		}
		
		Boolean isNeedSyncIntoHospital = false;//库房属性:是否同步标识
		if(isNeedSyncIntoHospital){//需要同步
			boolean isSyncSuccess = true;
			//later 添加同步任务
			//String orderFstate = isSyncSuccess?OrderFstate.AWAIT_AGGREE:OrderFstate.SEND_FAIL;
			//later 同时更新多个订单的状态, 发送时间+发送人id
		}
	}
	
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
	
	public void saveDraft(Boolean isEdit, Order order, List<OrderDetail> products) throws ValidationException {
		if(isEdit){
			//删除旧草稿及其产品列表
			deleteDraftWithAsso(order.getOrderId());
		}
		//批量插入草稿产品明细列表
		int num = orderDetailMapper.insertDraftDetailList(order.getOrderId(), products);
		logger.debug("插入{}条草稿明细", num);
		//计算:总金额
		order.setTotalPrice(orderDetailMapper.evalTotalPriceByOrderId(order.getOrderId()));
		//添加草稿主信息
		int num2 = super.insertInfo(order);
		logger.debug("保存{}个草稿主信息", num2);
	}
	
	/**
	 * 统计汇总:产品列表各产品属性分布
	 * @author	黄文君
	 * @date	2017年9月6日 上午11:58:31
	 * @param	gkAttributes
	 * @param	materialList
	 * @return	Map<String, Integer>
	 */
	
	public Map<String, BigDecimal> subtotalOfAttributeIds(List<Map<String, String>> gkAttributes, List<DeliveryDetailDto> materialList) {
		return orderDetailMapper.subtotalOfAttributeIds(gkAttributes, materialList);
	}

	/**
	 * 某一个订单备货之前，清理临时包工具数据
	 * @author	黄文君
	 * @date	2017年9月4日 下午4:49:19
	 * @param	orderId
	 * @return	int
	 */
	
	public void clearByOrderId(String orderId) {
		orderMapper.clearByOrderId(orderId);
	}

	/**
	 * 更新订单状态，并添加订单状态更新记录（单个与批量同时支持）
	 * @author	黄文君
	 * @date	2017年6月15日 下午2:03:01
	 * @param	orderIds 	订单id列表
	 * @param	fstate		状态
	 * @param	session		会话
	 * @return	void
	 */
	
	public void updateOrderFstate(String[] orderIds, String fstate, String cancleReason, HttpSession session) throws Exception {
		String sessionUserId = (String)session.getAttribute(LoginUser.SESSION_USERID);
		String sessionUserName = (String)session.getAttribute(LoginUser.SESSION_USERNAME);
		Long sessionOrgId = (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID);
		String sessionOrgType = (String)session.getAttribute(LoginUser.SESSION_USER_ORG_TYPE);

		int successCount = 0;//成功个数
		for(String orderId: orderIds) {
			Order origin = find(Order.class, orderId);
			Assert.notNull(origin, "订单（" + orderId + "）不存在");
			long rOrgId = origin.getrOrgId().longValue();//需方机构
			String rStorageGuid = origin.getStorageGuid();//需方一级库房
			Long fOrgId = origin.getfOrgId();//供方机构

			if (!origin.getFstate().equals(CustomConst.OrderFstate.DRAFT)) {//非草稿
				if (rOrgId != sessionOrgId.longValue() && fOrgId != sessionOrgId.longValue()) {
					throw new ValidationException("不支持处理其他机构的订单");
				}
			}

			Order order = new Order();
			order.setOrderId(orderId);
			order.setFstate(fstate);
			if (CustomConst.OrderFstate.AWAIT_SEND.equals(fstate)) {//已提交（待发送，需方）
				LocalAssert.equals(origin.getFstate(), OrderFstate.DRAFT, "只能提交草稿");

				OrderDetail condition = new OrderDetail();
				condition.setOrderId(order.getOrderId());
				origin.setDetailList(searchList(condition));
				//提交订单（共用）
				summitOrderInternal(origin, session);
			} else if (OrderFstate.AWAIT_AGGREE.equals(fstate)) {//订单发送失败，可以重新发送
				LocalAssert.equals(sessionOrgId, rOrgId, "只有需求方才能重新发送该订单");
				LocalAssert.equals(origin.getFstate(), OrderFstate.SEND_FAIL, "只能发送失败的订单，才能重新发送");

				//later 院内->院外:发送这个订单到医商云的供应商
				boolean isSyncSuccess = true;//调接口，直接将订单同步到医商云
				if (!isSyncSuccess) {
					throw new ValidationException("订单同步失败，原因: xxx");
				}
			} else if ("-1".equals(fstate)) {//删除草稿
				LocalAssert.equals(origin.getFstate(), OrderFstate.DRAFT, "只能删除草稿");
				//删除草稿订单及其产品列表
				deleteDraftWithAsso(orderId);
				return;
			} else if (OrderFstate.CANCEL.equals(fstate)) {//取消订单（需方）
				LocalAssert.equals(sessionOrgId, rOrgId, "只有需求方才能取消该订单");
				LocalAssert.contain(
						new String[]{
								OrderFstate.AWAIT_SEND,
								OrderFstate.SEND_FAIL,
								OrderFstate.AWAIT_AGGREE
						},
						origin.getFstate(), "需方：只能取消已提交并且供方未确认的订单");

				//根据需方库房，明确强势方
				Long kingOrgId = findKingOrgIdByStorageGuid(rStorageGuid, fOrgId);
				Assert.notNull(kingOrgId, "该机构是否有权取消订单的配置项未知!");
				if (kingOrgId.longValue() != sessionOrgId) {
					throw new ValidationException("你无权取消该订单，请联系供方机构");
				}
				order.setCancleReason(cancleReason);//取消理由
			} else if (OrderFstate.REJECT.equals(fstate)) {//撤销订单、拒绝订单（供方）
				LocalAssert.equals(sessionOrgId, fOrgId, "只有供方才能撤销该订单");
				LocalAssert.equals(origin.getFstate(), OrderFstate.AWAIT_AGGREE, "供方：只能撤销待确认的订单");

				//根据需方一级库房，明确强势方
				Long kingOrgId = findKingOrgIdByStorageGuid(origin.getStorageGuid(), origin.getfOrgId());
				Assert.notNull(kingOrgId, "该机构是否有权取消订单的配置项未知!");
				if (kingOrgId.longValue() != sessionOrgId) {
					throw new ValidationException("你无权取消该订单，请联系需方机构");
				}
				order.setCancleReason(cancleReason);//取消理由
			} else if (OrderFstate.PREPARE_GOODS.equals(fstate)) {//确认订单，准备备货（供方）
				LocalAssert.equals(sessionOrgType, CustomConst.OrgType.SUPPLIER, "只有供应商才能确认订单");
				LocalAssert.equals(origin.getFstate(), OrderFstate.AWAIT_AGGREE, "只有待确认的订单才能作确认操作");
			} else if (OrderFstate.CLOSED.equals(fstate)) {//关闭（供方）
				LocalAssert.equals(sessionOrgType, CustomConst.OrgType.SUPPLIER, "只有供应商才能关闭订单");
				LocalAssert.equals(origin.getFstate(), OrderFstate.PREPARE_GOODS, "只能关闭备货中的订单");
			} else {
				throw new ValidationException("不支持的订单操作（" + fstate + "）！");
			}

			ChangeFstate cf = new ChangeFstate();
			cf.setChangeGuid(IdentifieUtil.getGuId());
			cf.setTbId(orderId);
			cf.setType(ChangeFstateType.ORDER);//订单
			cf.setBeforFstate(origin.getFstate());
			cf.setAfterFstate(fstate);
			cf.setModifyTime(new Date());
			cf.setModifyUserid(sessionUserId);
			cf.setModifyUsername(sessionUserName);

			//更新订单状态
			super.updateInfo(order);
			//添加订单状态更新记录
			super.insertInfo(cf);
			successCount++;
		}
		LocalAssert.isTrue(successCount==orderIds.length, "操作失败");
	}

	/**
	 * 提交订单（共用）
	 * @author	黄文君
	 * @date	2017年6月26日 下午3:24:24
	 * @param	order			订单信息
	 * @param	session
	 * @throws	Exception
	 * @return	void
	 */
	
	public void summitOrderInternal(Order order, HttpSession session) throws Exception {
		//获取会话中的信息
		String sessionUserId = (String)session.getAttribute(LoginUser.SESSION_USERID);
		String sessionUserName = (String)session.getAttribute(LoginUser.SESSION_USERNAME);
		Long sessionOrgId = (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID);

		Assert.notNull(order, "请上送订单的信息!");
		LocalAssert.notBlank(order.getOrderType(), "单据类型，不能为空");
		LocalAssert.notBlank(order.getFsource(), "订单来源，不能为空");
		LocalAssert.notBlank(order.getStorageGuid(), "请选择库房");
		LocalAssert.notBlank(order.getAddrGuid(), "地址主键，不能为空");
		//Assert.notNull(order.getExpectDate(), "请指定：期望到货日期");

		//送货地址（库房地址）
		Address addr = find(Address.class, order.getAddrGuid());
		Assert.notNull(addr, "地址不存在（"+order.getAddrGuid()+"）");

		//当前库房（必需明确）
		StorageInfo currentStorage = find(StorageInfo.class, order.getStorageGuid());
		Assert.notNull(currentStorage, "库房不存在（"+order.getStorageGuid()+"）");

		//订单产品明细列表
		Assert.notEmpty(order.getDetailList(), "请上送订单的产品明细!");
		//检查产品明细上送项
		for(OrderDetail d: order.getDetailList()){
			LocalAssert.notBlank(d.getTenderDetailGuid(), "供应产品guid，不能为空");
			Assert.notNull(d.getAmount(), "采购数量，不能为空");
			if(d.getAmount() <= 0){
				throw new ValidationException("采购数量，必需是正整数");
			}
		}

		Long[] fOrgIds = findSuppliersByDetails(order.getDetailList());
		Assert.notEmpty(fOrgIds, "未明确产品供应商");
		if(logger.isDebugEnabled()){
			logger.debug("该订单涉及{}个供应商:{}", fOrgIds.length, Arrays.toString(fOrgIds));
		}

		List<Order> orderList = new ArrayList<Order>();//多个订单
		Boolean isNeedSyncIntoHospital = false;//库房属性:是否同步标识
		Date currentTime = new Date();//当前时间
		for(int i=0; i<fOrgIds.length; i++){
			//确定关联：供应商id - 订单id - 订单号
			Order od = new Order();
			od.setOrderId(IdentifieUtil.getGuId());//订单id
			od.setrOrgId(sessionOrgId);//需方机构id: sessionOrgId
			od.setfOrgId(fOrgIds[i]);//供应商id
			od.setStorageGuid(order.getStorageGuid());//需求库房
			od.setbStorageGuid(storageService.findRootByStorageGuid(order.getStorageGuid()));//采购库房
			od.setFstate(isNeedSyncIntoHospital ? OrderFstate.AWAIT_SEND : OrderFstate.AWAIT_AGGREE);//订单状态:已提交/待发送
			od.setOrderNo(procedureService.callSpGetBill(sessionOrgId, "普耗订单", BillAdapter.prefixAdapter(order.getOrderType()), 5));//订单号
			od.setOrderDate(currentTime);//制单日期
			od.setSendDate(currentTime);//提交日期（发送日期）
			od.setOrderUserid(sessionUserId);//制单人id
			od.setOrderUsername(sessionUserName);//制单人姓名
			od.setTfAddress(addr.getTfAddress());//送货地址
			od.setLxr(addr.getLinkman());//联系人
			od.setLxdh(addr.getLinktel());//联系电话
			od.setDeptGuid(null);//科室guid
			od.setDeptName(null);//科室名称
			od.setExpectDate(order.getExpectDate());//期望到货日期
			od.setSendUserid(sessionUserId);//发送人id
			od.setOrderType(order.getOrderType());//普耗订单
			od.setFsource(order.getFsource());//订单来源
			od.setAddrGuid(order.getAddrGuid());//地址guid
			orderList.add(od);
		}

		//task6 ed 拆分并提交订单（拆单到各个供应商）
		summitSplitOrder(currentStorage, order.getOrderId(), orderList, order.getDetailList());
	}
	
	/**
	 * 删除草稿及其产品列表
	 * @author	黄文君
	 * @date	2017年6月27日 下午3:36:10
	 * @param	orderId		订单id
	 * @return	void
	 * @throws	ValidationException 
	 */
	
	public void deleteDraftWithAsso(String orderId) throws ValidationException {
		LocalAssert.notBlank(orderId, "orderId，不能为空");
		//清理旧的草稿明细
		OrderDetail condition = new OrderDetail();
		condition.setOrderId(orderId);
		int num1 = super.deleteInfo(condition);
		logger.debug("删除{}条旧的草稿明细", num1);
		//删除旧的草稿主信息
		int num2 = super.deleteInfoById(Order.class, orderId);
		logger.debug("删除{}个旧的草稿主信息", num2);
	}

}

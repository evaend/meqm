package com.phxl.ysy.service.impl;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.core.base.util.IdentifieUtil;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.ysy.dao.StorageInfoMapper;
import com.phxl.ysy.entity.StorageInfo;
import com.phxl.ysy.service.ProcedureService;
import com.phxl.ysy.dao.ChangeFstateMapper;
import com.phxl.ysy.dao.DeliveryDetailMapper;
import com.phxl.ysy.dao.DeliveryMapper;
import com.phxl.ysy.dao.DeliveryPackageMapper;
import com.phxl.ysy.dto.QrcodeDto;
import com.phxl.ysy.entity.ChangeFstate;
import com.phxl.ysy.entity.Delivery;
import com.phxl.ysy.entity.DeliveryDetail;
import com.phxl.ysy.entity.Order;
import com.phxl.ysy.service.DeliveryService;
import com.phxl.ysy.service.OrderService;
import com.phxl.ysy.web.dto.DeliveryDetailDto;
import com.phxl.ysy.web.dto.DeliveryPackageDto;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.constant.CustomConst.DeliveryFstate;
import com.phxl.ysy.constant.CustomConst.DeliveryType;
import com.phxl.ysy.constant.CustomConst.OrderType;
import com.phxl.ysy.constant.CustomConst.YesOrNo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DeliveryServiceImpl extends BaseService implements DeliveryService {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	DeliveryMapper deliveryMapper;
	@Autowired
	DeliveryDetailMapper deliveryDetailMapper;
	@Autowired
	ChangeFstateMapper changeFstateMapper;
	@Autowired
	DeliveryPackageMapper deliveryPackageMapper;
	@Autowired
	OrderService orderService;
	@Autowired
	StorageInfoMapper storageInfoMapper;
	@Autowired
	ProcedureService procedureService;

	public List<Map<String, Object>> searchDeliveryList(Pager<Map<String, Object>> pager) {
		return deliveryMapper.searchDeliveryList(pager);
	}

	
	public List<Map<String, Object>> selectdeliveryDetails(Pager<Map<String, Object>> pager) {
		return deliveryDetailMapper.selectdeliveryDetails(pager);
	}

	/**
	 * 根据手术送货单id查询产品明细列表（手术送货单专用）
	 * @author 黄文君
	 * @param pager
	 * @return List<Map<String, Object>>
	 */
	
	public List<Map<String, Object>> findDetailList4OperBySendId(Pager<Map<String, Object>> pager) {
		return deliveryDetailMapper.findDetailList4OperBySendId(pager);
	}

	
	public void updateDeliveryFstate(Delivery deliver, ChangeFstate changeFstate) throws ValidationException {
		// 更新送货单
		this.updateInfo(deliver);
		// 新增状态变更信息
		changeFstate.setChangeGuid(IdentifieUtil.getGuId());
		changeFstate.setTbId(deliver.getSendId());
		changeFstate.setType(CustomConst.ChangeFstateType.DELIVERY);
		changeFstate.setModifyTime(new Date());
		this.insertInfo(changeFstate);

		// 根据送货单信息做一些操作
		Delivery deliverInfo = this.find(Delivery.class, deliver.getSendId());
		// 手术送货单发货要清除临时数据
		if (DeliveryType.OPER_DELIVERY.equalsIgnoreCase(deliverInfo.getOrderType())
				&& DeliveryFstate.AWAIT_CHECK.equals(deliverInfo.getFstate())) {
			orderService.clearByOrderId(deliver.getSendId());
		}
		// 发货时根据送货单库房判断是否需要生成二维码
		StorageInfo storageInfo = storageInfoMapper.findStorageWithConfig(deliverInfo.getrStorageGuid());
		// 库房是一物一码库房
		if (storageInfo != null && YesOrNo.YES.equals(storageInfo.getQrFlag())) {
			DeliveryDetail dd = new DeliveryDetail();
			dd.setSendId(deliverInfo.getSendId());
			List<DeliveryDetail> deliveryDetails = this.searchList(dd);
			if (CollectionUtils.isNotEmpty(deliveryDetails)) {
				List<QrcodeDto> qrs = new ArrayList<QrcodeDto>();
				for (DeliveryDetail deliveryDetail : deliveryDetails) {// 循环送货单明细
					if (deliveryDetail != null && deliveryDetail.getAmount() > 0) {
						for (int i = 1; i <= deliveryDetail.getAmount(); i++) {
							QrcodeDto qr = new QrcodeDto();
							String qrBillNo = procedureService.callSpGetQrBill(deliverInfo.getrOrgId(), "QR", null);// 调用生成二维码的存储过程
							qr.setQrcode(qrBillNo);
							qr.setSendDetailGuid(deliveryDetail.getSendDetailGuid());
							qrs.add(qr);
						}
					}
				}
				if(CollectionUtils.isNotEmpty(qrs)){
					// 根据所有的送货单明细，生成送货单明细的二维码
					deliveryDetailMapper.insertQrcodes(qrs);
				}				
			}			
		}
	}

	
	public Delivery getDelivery(String sendId, String storageGuid) throws ValidationException {
		// 1、送货单是否存在
		// 2、送货单是否属于此库房
		// 3、送货单状态不是待验收的状态
		List<Delivery> deliverys = deliveryMapper.getDelivery(sendId);
		if (deliverys == null || deliverys.size() == 0) {
			throw new ValidationException("送货单不存在");
		}
		for (Delivery delivery : deliverys) {
			if (delivery.getrStorageGuid().equals(storageGuid)) {// 送货单-库房匹配正确
				if (CustomConst.DeliveryFstate.AWAIT_CHECK.equals(delivery.getFstate())) {
					return delivery;
				} else {
					throw new ValidationException("送货单状态不是待验收的状态!");
				}
			}
		}
		throw new ValidationException("此送货单不属于选择的库房");
	}

	
	public void batchUpdateDeliveryFstate(String[] sendIds, String checkFstate, String rejectReason, String userId,
			String userName) {
		// 批量更新送货单状态
		deliveryMapper.batchUpdateDeliveryFstate(new HashSet(Arrays.asList(sendIds)), checkFstate, rejectReason);
		// 记录状态变更
		ChangeFstate changeFstate = new ChangeFstate();
		changeFstate.setType(CustomConst.ChangeFstateType.DELIVERY);
		changeFstate.setBeforFstate(CustomConst.DeliveryFstate.AWAIT_CHECK);
		changeFstate.setAfterFstate(checkFstate);
		changeFstate.setModifyUserid(userId);
		changeFstate.setModifyUsername(userName);
		changeFstate.setModifyTime(new Date());
		changeFstateMapper.batchInsertChangeFsate(new HashSet(Arrays.asList(sendIds)), changeFstate);

		// 是否全部验收通过，是-更改订单的状态
		Set<String> orderIds = deliveryMapper.searchOrderIdsBySendIds(sendIds);
		Set<String> fullOrderIds = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(orderIds)) {
			Iterator<String> orderIdIt = orderIds.iterator();
			while (orderIdIt.hasNext()) {
				String orderId = orderIdIt.next();
				if (deliveryMapper.isOrderFullThrough(orderId)) {
					// 该订单下的送货单已全部验收，更改订单的状态为“交易完成”
					fullOrderIds.add(orderId);
				}
				Order order = this.find(Order.class, orderId);
				if (order != null) {
					// 送货单类型是手术类型、且状态是验收通过的时候，订单状态直接改变成“交易完成”
					if (CustomConst.DeliveryFstate.CHECK_PASSED.equals(checkFstate)
							&& OrderType.OPER_ORDER.equalsIgnoreCase(order.getOrderType())) {
						fullOrderIds.add(orderId);
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(fullOrderIds)) {
			// 批量更新订单状态
			deliveryMapper.batchUpdateOrderFstate(fullOrderIds, CustomConst.OrderFstate.FINISH);
			// 记录状态变更
			changeFstate.setType(CustomConst.ChangeFstateType.ORDER);
			changeFstate.setBeforFstate(CustomConst.OrderFstate.PREPARE_GOODS);
			changeFstate.setAfterFstate(CustomConst.OrderFstate.FINISH);
			changeFstateMapper.batchInsertChangeFsate(fullOrderIds, changeFstate);
		}

	}

	/**
	 * 查询指定手术送货单的产品列表
	 * 
	 * @param sendId
	 * @return List<Map<String, Object>>
	 */
	
	public List<Map<String, Object>> findMaterials4OperBySendId(String sendId) {
		return deliveryDetailMapper.findMaterials4OperBySendId(sendId);
	}

	/**
	 * 订单备货生成送货单
	 * @author 黄文君
	 * @date 2017年6月19日 下午5:28:43
	 * @param delivery 送货单信息
	 * @param details 产品列表
	 * @return void
	 */
	
	public void settleGoods(Delivery delivery, List<DeliveryDetailDto> details) {
		// 普耗、高值、结算备货: 批量插入送货单明细列表
		deliveryDetailMapper.insertDetailListBatch(delivery.getSendId(), details);
		// 计算:总金额
		delivery.setTotalPrice(deliveryDetailMapper.evalTotalPriceBySendId(delivery.getSendId()));
		// 新增送货单信息
		insertInfo(delivery);
	}

	/**
	 * 手术订单备货生成送货单
	 * 
	 * @author 黄文君
	 * @date 2017年6月19日 下午5:28:43
	 * @param isFirstSettleGoos
	 *            是否首次备货
	 * @param delivery
	 *            送货单信息
	 * @param detailList
	 *            产品列表
	 * @param packageList
	 *            包列表
	 * @return void
	 */
	public void settleGoods4Oper(Boolean isFirstSettleGoos, Delivery delivery, List<DeliveryDetailDto> detailList, List<DeliveryPackageDto> packageList) {
		deliveryDetailMapper.insertDetailList4Oper(delivery.getSendId(), detailList);// 手术备货:批量添加送货单明细列表
		BigDecimal totalPrice = deliveryDetailMapper.evalTotalPriceBySendId(delivery.getSendId());// 计算:总金额
		delivery.setTotalPrice(totalPrice);
		if (isFirstSettleGoos) {
			super.insertInfo(delivery);// 新增:送货单信息
		} else {
			super.updateInfoCover(delivery);// 更新:送货单信息
		}

		if (CollectionUtils.isNotEmpty(packageList)) {
			deliveryPackageMapper.insertPackageListBatch(delivery.getSendId(), packageList);// 批量添加手术包列表
		}

		// 回填订单:总金额
		Order order = new Order();
		order.setOrderId(delivery.getOrderId());
		order.setTotalPrice(totalPrice);
		super.updateInfo(order);
	}

	/**
	 * 手术送货单：临时保存备货数据
	 * 
	 * @author 黄文君
	 * @param sendId 送货单id
	 * @param detailList 产品列表
	 * @param packageList 手术包列表
	 * @param materialScope 数据范围
	 */
	public void saveSettleGoodsAsDraft(String sendId, List<DeliveryDetailDto> detailList,
			List<DeliveryPackageDto> packageList, Map<String, Object> materialScope) throws ValidationException {
		LocalAssert.notBlank(sendId, "sendId，不能为空");
		materialScope = MapUtils.isNotEmpty(materialScope) ? materialScope : null;
		if (detailList != null) {// 产品列表
			deliveryDetailMapper.mergeDetailList4Oper(sendId, detailList, materialScope);// 手术备货:批量添加送货单明细列表
		}
		if (packageList != null) {// 手术包列表
			deliveryPackageMapper.mergePackageList4Oper(sendId, packageList);// 批量添加手术包列表
		}
	}

	/**
	 * 手术订单备货生成送货单
	 * @author 黄文君
	 * @param isFirstSettleGoos 是否首次备货
	 * @param delivery 送货单信息
	 */
	public void settleGoods4Oper(Boolean isFirstSettleGoos, Delivery delivery) {
		deliveryMapper.updateSaveFlagBySendId(delivery.getSendId());// 更新送货单相关的数据保存标识
		List<Map<String, Object>> newPackageIdMapping = deliveryMapper.findNewPackageIdMapping(delivery.getSendId());
		if (CollectionUtils.isNotEmpty(newPackageIdMapping)) {
			// 更新指定送货单手术包序号为连续的序号
			deliveryMapper.updatePackageId(delivery.getSendId(), newPackageIdMapping);
		}

		BigDecimal totalPrice = deliveryDetailMapper.evalTotalPriceBySendId(delivery.getSendId());// 计算:总金额
		delivery.setFstate(CustomConst.DeliveryFstate.AWAIT_SEND);// 备货中/待发货
		delivery.setTotalPrice(totalPrice);
		if (isFirstSettleGoos) {
			super.insertInfo(delivery);// 新增:送货单信息
		} else {
			super.updateInfoCover(delivery);// 更新:送货单信息
		}

		Order order = new Order();
		order.setOrderId(delivery.getOrderId());
		order.setTotalPrice(totalPrice);
		super.updateInfo(order);// 回填订单:总金额
	}

	public List<Map<String, Object>> searchQrCodesBySendDetail(Pager<Map<String, Object>> pager) {
		// TODO Auto-generated method stub
		return deliveryDetailMapper.searchQrCodesBySendDetail(pager);
	}

}

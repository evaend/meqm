package com.phxl.ysy.service;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.IBaseService;
import com.phxl.ysy.entity.ChangeFstate;
import com.phxl.ysy.entity.Delivery;
import com.phxl.ysy.web.dto.DeliveryDetailDto;
import com.phxl.ysy.web.dto.DeliveryPackageDto;

import java.util.List;
import java.util.Map;

/**
 * 
 * 2017年6月15日 下午3:01:56
 * @author 陶悠
 * 送货单管理服务层
 */
public interface DeliveryService extends IBaseService {

	List<Map<String, Object>> searchDeliveryList(Pager<Map<String, Object>> pager);

	List<Map<String, Object>> selectdeliveryDetails(Pager<Map<String, Object>> pager);

	/**
	 * 根据手术送货单id查询产品明细列表（手术送货单专用）
	 * @author	黄文君
	 * @param pager
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> findDetailList4OperBySendId(Pager<Map<String, Object>> pager);

	void updateDeliveryFstate(Delivery deliver, ChangeFstate changeFstate) throws ValidationException;

	Delivery getDelivery(String sendId, String storageGuid) throws ValidationException;

	void batchUpdateDeliveryFstate(String[] sendIds, String checkFstate, String rejectReason, String userId, String userName);

	/**
	 * 订单备货生成送货单
	 * @author	黄文君
	 * @date	2017年6月19日 下午5:28:43
	 * @param	delivery		送货单信息
	 * @param	details			产品列表
	 * @return	void
	 */
	public abstract void settleGoods(Delivery delivery, List<DeliveryDetailDto> details);

	/**
	 * 手术订单备货生成送货单
	 * @author	黄文君
	 * @date	2017年6月19日 下午5:28:43
	 * @param	isFirstSettleGoos	是否首次备货
	 * @param	delivery			送货单信息
	 * @param	detailList				产品列表
	 * @param	packageList			包列表
	 * @return	void
	 */
	public abstract void settleGoods4Oper(Boolean isFirstSettleGoos, Delivery delivery, List<DeliveryDetailDto> detailList, List<DeliveryPackageDto> packageList);

	/**
	 * 手术订单备货生成送货单
	 * @param	isFirstSettleGoos	是否首次备货
	 * @param	delivery			送货单信息
	 */
	public void settleGoods4Oper(Boolean isFirstSettleGoos, Delivery delivery);

	/**
	 * 查询指定手术送货单的产品列表
	 * @param sendId
	 * @return List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> findMaterials4OperBySendId(String sendId);

	/**
	 * 手术送货单：临时保存备货数据
	 * @author	黄文君
	 * @param sendId            送货单id
	 * @param detailList        产品列表
	 * @param packageList       手术包列表
	 * @param	materialScope   数据范围
	 */
	public void saveSettleGoodsAsDraft(String sendId, List<DeliveryDetailDto> detailList, List<DeliveryPackageDto> packageList, Map<String, Object> materialScope) throws ValidationException;

	//查询某个送货单明细的二维码列表
	List<Map<String, Object>> searchQrCodesBySendDetail(Pager<Map<String, Object>> pager);

}

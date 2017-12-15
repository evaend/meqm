package com.phxl.ysy.dao;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.Delivery;
import com.phxl.ysy.web.dto.DeliveryDetailDto;
import com.phxl.ysy.dto.QrcodeDto;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DeliveryDetailMapper {
	
	/**
	 * 普耗、高值、结算备货: 批量插入送货单明细列表
	 * @author	黄文君
	 * @date	2017年6月19日 下午6:06:47
	 * @param	sendId		送货单id
	 * @param	details		产品明细列表
	 * @return	int
	 */
	int insertDetailListBatch(@Param("sendId")String sendId, @Param("details")List<DeliveryDetailDto> details);

	/**
	 * 手术备货: 批量插入送货单明细列表
	 * @author	黄文君
	 * @date	2017年6月19日 下午6:06:47
	 * @param	sendId		送货单id
	 * @param	materials	产品明细列表
	 * @return	int
	 */
	int insertDetailList4Oper(@Param("sendId")String sendId, @Param("materials")List<DeliveryDetailDto> materials);

	/**
	 * 手术备货: 批量插入送货单明细列表
	 * @author	黄文君
	 * @date	2017年6月19日 下午6:06:47
	 * @param	sendId		送货单id
	 * @param	materialList	产品明细列表
	 * @param	materialScope	数据范围
	 * @return	int
	 */
	int mergeDetailList4Oper(@Param("sendId")String sendId, @Param("materialList")List<DeliveryDetailDto> materialList, @Param("materialScope") Map<String, Object> materialScope);

	List<Map<String, Object>> selectdeliveryDetails(Pager<Map<String, Object>> pager);

	/**
	 * 根据手术送货单id查询产品明细列表（手术送货单专用）
	 * @param pager
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> findDetailList4OperBySendId(Pager<Map<String, Object>> pager);

	/**
	 * 计算指定送货单的产品总金额
	 * @author	黄文君
	 * @date	2017年6月19日 下午7:04:48
	 * @param	sendId		送货单id
	 * @return	BigDecimal
	 */
	BigDecimal evalTotalPriceBySendId(String sendId);

	/**
	 * 查询指定手术送货单的产品列表
	 * @param   sendId
	 * @return  List<DeliveryDetailDto>
	 */
	List<Map<String, Object>> findMaterials4OperBySendId(String sendId);

	//生成送货单明细的二维码
	void insertQrcodes(@Param("qrs")List<QrcodeDto> qrs);

	//查询送货单明细的二维码列表
	List<Map<String, Object>> searchQrCodesBySendDetail(Pager<Map<String, Object>> pager);
}
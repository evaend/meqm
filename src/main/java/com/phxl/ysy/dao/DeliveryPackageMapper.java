package com.phxl.ysy.dao;

import com.phxl.ysy.web.dto.DeliveryPackageDto;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DeliveryPackageMapper {

	/**
	 * 手术包产品类型数量比较
	 * @param   sendId
	 * @return  List<Map<String, Object>>
	 */
	List<Map<String, Object>> checkAttributeAmountOfOperPackage(String sendId);

	/**
	 * 手术包手术器械数量比较
	 * @param   sendId
	 * @return  List<Map<String, Object>>
	 */
	List<Map<String, Object>> checkToolAmountOfOperPackage(String sendId);

	/**
	 * 查看指定送货单是否有手术包
	 * @param sendId
	 * @return Boolean
	 */
	Boolean countPackageListBySendId(String sendId);

	/**
	 * 查找某个送货单的手术包的编号列表
	 * @author	黄文君
	 * @date	2017年8月31日 下午4:51:39
	 * @param	sendId			送货单id
	 * @param	submitFlag     数据提交标识（S已提交，D草稿）
	 * @return	List<String>
	 */
	List<String> findPackageIdsBySendId(
			@Param("sendId")String sendId,
			@Param("submitFlag")String submitFlag);
	
	/**
	 * 查询指定单据的手术包列表（横表型式）
	 * @author	黄文君
	 * @date	2017年8月31日 下午5:18:55
	 * @param	sendId			送货单id
	 * @param	gkAttributes	骨科产品属性编码列表
	 * @param	packageIds		包序号列表
	 * @param	headerStyle	表头样式
	 * @param	submitFlag     数据提交标识（S已提交，D草稿）
	 * @return	List<Map<String, Object>>
	 */
	List<Map<String, Object>> findPackageListBySendId(
			@Param("sendId")String sendId,
			@Param("gkAttributes")List<Map<String, String>> gkAttributes,
			@Param("packageIds")List<String> packageIds,
			@Param("headerStyle")Integer headerStyle,
			@Param("submitFlag")String submitFlag);

	/**
	 * 查询手术包列表的表头一行（竖表）
	 * @author	黄文君
	 * @date	2017年8月31日 下午5:18:55
	 * @param	sendId			送货单id
	 * @param	gkAttributes	骨科产品属性编码列表
	 * @param	submitFlag     数据提交标识（S已提交，D草稿）
	 * @return	List<Map<String, Object>>
	 */
	List<Map<String, Object>> findHeaderForPackageListBySendId(
			@Param("sendId")String sendId,
			@Param("gkAttributes")List<Map<String, String>> gkAttributes,
			@Param("submitFlag")String submitFlag);

	/**
	 * 批量添加手术包
	 * @author	黄文君
	 * @date	2017年9月4日 下午1:57:44
	 * @param	packageList		手术包列表
	 * @return	int
	 */
	int insertPackageListBatch(@Param("sendId")String sendId, @Param("packageList")List<DeliveryPackageDto> packageList);

	/**
	 * 批量添加手术包
	 * @author	黄文君
	 * @date	2017年9月21日
	 * @param	packageList		手术包列表
	 * @return	int
	 */
	int mergePackageList4Oper(@Param("sendId")String sendId, @Param("packageList")List<DeliveryPackageDto> packageList);

	/**
	 * 查询指定送货单的手术包统计信息
	 * @author	黄文君
	 * @date	2017年9月4日 下午1:57:41
	 * @param	sendId			送货单id
	 * @param	submitFlag     数据提交标识（S已提交，D草稿）
	 * @return	Map<String,Object>
	 */
	Map<String, Object> subtotalPackageBySendId(@Param("sendId")String sendId, @Param("submitFlag") String submitFlag);

}
package com.phxl.ysy.dao;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.PackageTool;
import com.phxl.ysy.web.dto.PackageToolDto;
import com.phxl.ysy.web.dto.TemplatePackagesDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PackageToolMapper {
	
	/**
	 * 查询指定包的工具列表（已选、未选）
	 * @author	黄文君
	 * @date	2017年9月5日 下午3:24:14
	 * @param	pager
	 * @return	List<PackageTool>
	 */
	List<PackageTool> findAllToolsByPackageId(Pager<PackageTool> pager);

	/**
	 * 查找指定手术包的工具列表
	 * @param pager
	 * @return List<PackageTool>
	 */
	List<PackageTool> findToolsByPackageId(Pager<PackageTool> pager);

	/**
	 * 指定手术包批量添加工具
	 * @author	黄文君
	 * @date	2017年9月1日 下午3:49:54
	 * @param	rOrgId
	 * @param	sendId
	 * @param	toolList
	 * @return	int
	 */
	int mergeToolList4Oper(@Param("rOrgId")Long rOrgId, @Param("sendId") String sendId, @Param("packageId") Integer packageId, @Param("toolList")List<PackageToolDto> toolList);

	/**
	 * 指定手术包批量添加工具（从指定的模板手术包）
	 * @author	黄文君
	 * @date	2017年9月1日 下午3:49:54
	 * @param	rOrgId
	 * @param	sendId
	 * @param	templatePackageList
	 * @return	int
	 */
	int mergeToolListFromTemplatePackages(@Param("rOrgId")Long rOrgId, @Param("sendId") String sendId, @Param("templatePackageList")List<TemplatePackagesDto> templatePackageList);

	/**
	 * 查询指定手术包的工具数量
	 * @author	黄文君
	 * @date	2017年9月4日 下午5:45:21
	 * @param	sendId		订单id
	 * @param	packageId	包序号
	 * @return	int
	 */
	Integer countToolsByPackageId(@Param("sendId")String sendId, @Param("packageId")Integer packageId, @Param("submitFlag") String submitFlag);
	
	/**
	 * 指定手术包删除多个工具
	 * @author	黄文君
	 * @date	2017年9月5日 下午1:56:01
	 * @param	sendId
	 * @param	packageId
	 * @param	toolCodes
	 * @return	void
	 */
	void deleteToolsFromPackage(@Param("sendId")String sendId, @Param("packageId")Integer packageId, @Param("toolCodes")String[] toolCodes);

	/**
	 * 清理指定一个手术包内手术器械
	 * @param sendId
	 * @param packageId
	 * @return void
	 */
	void clearToolsFromPackage(@Param("sendId")String sendId, @Param("packageId")Integer packageId);

	/**
	 * 查看指定送货单是否有手术包工具明细
	 * @param sendId
	 * @return Boolean
	 */
	Boolean countPackageToolListBySendId(String sendId);

	/**
	 * 核对每个手术包手术器械的数量
	 * @author	黄文君
	 * @date	2017年10月10日
	 * @param	orderId
	 * @param	amountByPackage
	 * @return	List<Map<Integer, Integer>>
	 */
	List<Map<Integer, Integer>> checkToolAmountOfOperPerPackage(@Param("orderId")String orderId, @Param("amountByPackage") List<Map<String, Integer>> amountByPackage);

}
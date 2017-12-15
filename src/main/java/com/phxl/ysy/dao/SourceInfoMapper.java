package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.phxl.core.base.entity.Pager;

public interface SourceInfoMapper {

	/**
	 * 查询跟指定机构没有供需关系的供应商的列表
	 * @author	黄文君
	 * @date	2017年3月24日 下午4:06:15
	 * @param	pager
	 * @return	List<Map<String, Object>>
	 */
	List<Map<String, Object>> findUnsourceOrgList(Pager pager);
	
	/**
	 * 查询指定机构的供应商列表
	 * @author	黄文君
	 * @date	2017年3月24日 下午4:36:47
	 * @param	pager
	 * @return	List<Map<String,Object>>
	 */
	List<Map<String, Object>> findMySupplierList(Pager pager);
	
	/**
	 * 根据sourceId查询指定机构的供应商信息
	 * @author	黄文君
	 * @date	2017年3月24日 下午4:52:54
	 * @param	sessionOrgId
	 * @param	sourceId
	 * @return	Map<String,Object>
	 */
	Map<String, Object> findMySupplierBySourceId(@Param("sessionOrgId")Long sessionOrgId, @Param("sourceId")String sourceId);

	/**
	 * 查询指定机构的医院列表
	 */
	List<Map<String, Object>> findMyHospitalList(Pager pager);
	
	/**
	 * 检查供应商编码是否已占用
	 * @author	黄文君
	 * @date	2017年4月12日 下午7:09:15
	 * @param	supplierCode
	 * @param	excludeSourceId
	 * @return	int
	 */
	int existSupplierCode(@Param("rOrgId")Long rOrgId, @Param("supplierCode")String supplierCode, @Param("excludeSourceId")String excludeSourceId);
	
	/**
	 * 
	 * updateFstateBatch:(批量修改供需关系). <br/> 
	 * 
	 * @Title: updateFstateBatch
	 * @Description: TODO
	 * @param orgId
	 * @param fstate
	 * @param modifyUserid
	 * @return    设定参数
	 * @return int    返回类型
	 * @throws
	 */
	int updateFstateBatch(@Param("orgId")Long orgId,@Param("fstate")String fstate,@Param("modifyUserid")String modifyUserid);

	List<Map<String, Object>> findSourceListForSelect(Pager pager);

	/**
	 * 
	 * findMySourceInfoList:(根据当前需方机构查询供需关系列表). <br/> 
	 * 
	 * @Title: findMySourceInfoList
	 * @Description: TODO
	 * @param pager
	 * @return    设定参数
	 * @return List<Map<String,Object>>    返回类型
	 * @throws
	 */
    List<Map<String, Object>> findMySourceInfoList(Pager pager);

	List<Map<String, Object>> queryRSourceStorageForSelect(Pager pager);
	
}
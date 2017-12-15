package com.phxl.ysy.service;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.OrgInfo;
import com.phxl.ysy.entity.SourceInfo;

public interface SourceInfoService {

	/**
	 * 根据sourceId查询指定机构的供应商信息
	 * @author	黄文君
	 * @date	2017年3月24日 下午4:52:54
	 * @param	sessionOrgId
	 * @param	sourceId
	 * @return	Map<String,Object>
	 */
	public abstract Map<String, Object> findMySupplierBySourceId(Long sessionOrgId, String sourceId);

	/**
	 * 查询指定机构的供应商列表
	 * @author	黄文君
	 * @date	2017年3月24日 下午4:36:47
	 * @param	pager
	 * @return	List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> findMySupplierList(Pager pager);
	
	/**
	 * 
	 * findMySupplierList:(根据当前需方机构查询供需关系列表). <br/> 
	 * 
	 * @Title: findMySupplierList
	 * @Description: TODO
	 * @param pager
	 * @return    设定参数
	 * @return List<Map<String,Object>>    返回类型
	 * @throws
	 */
    public abstract List<Map<String, Object>> findMySourceInfoList(Pager pager);

	/**
	 * 查询跟指定机构没有供需关系的供应商的列表
	 * @author	黄文君
	 * @date	2017年3月24日 下午4:06:15
	 * @param	pager
	 * @return	List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> findUnsourceOrgList(Pager pager);

	/**
	 * 业务校验: 是否可以停用或移除指定的供需关系
	 * @author	黄文君
	 * @date	2017年3月25日 下午11:33:44
	 * @param sourceInfo
	 * @return	void
	 */
	public abstract void whetherCastOffSourceInfo(SourceInfo sourceInfo);
	
	/**
	 * 查询指定机构的医院列表
	 */
	public abstract List<Map<String, Object>> findMyHospitalList(Pager pager);

	/**
	 * 检查供应商编码是否已占用
	 * @author	黄文君
	 * @date	2017年4月12日 下午8:23:29
	 * @param	rOrgId
	 * @param	supplierCode
	 * @param	excludeSourceId
	 * @return	int
	 */
	public abstract int existSupplierCode(Long rOrgId, String supplierCode, String excludeSourceId);

	/**
	 *  根据一方查询供应关系里的另一方的列表（下拉框）
	 */
	public List<Map<String, Object>> findSourceListForSelect(Pager pager);
	
	/**
	 *  查询供方的对应供需关系中的需求库房（下拉框）
	 */
	public List<Map<String, Object>> queryRSourceStorageForSelect(Pager pager);
}
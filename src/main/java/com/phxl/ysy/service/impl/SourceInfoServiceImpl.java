package com.phxl.ysy.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.ysy.dao.SourceInfoMapper;
import com.phxl.ysy.entity.SourceInfo;
import com.phxl.ysy.service.SourceInfoService;

@Service
public class SourceInfoServiceImpl extends BaseService implements SourceInfoService {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SourceInfoMapper sourceInfoMapper;

	/**
	 * 根据sourceId查询指定机构的供应商信息
	 * @author	黄文君
	 * @date	2017年3月24日 下午4:52:54
	 * @param	sessionOrgId
	 * @param	sourceId
	 * @return	Map<String,Object>
	 */
	public Map<String, Object> findMySupplierBySourceId(Long sessionOrgId, String sourceId) {
		return sourceInfoMapper.findMySupplierBySourceId(sessionOrgId, sourceId);
	}

	/**
	 * 查询指定机构的供应商列表
	 * @author	黄文君
	 * @date	2017年3月24日 下午4:36:47
	 * @param	pager
	 * @return	List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findMySupplierList(Pager pager) {
		return sourceInfoMapper.findMySupplierList(pager);
	}

	/**
	 * 查询跟指定机构没有供需关系的供应商的列表
	 * @author	黄文君
	 * @date	2017年3月24日 下午4:06:15
	 * @param	pager
	 * @return	List<Map<String, Object>>
	 */
	public List<Map<String, Object>> findUnsourceOrgList(Pager pager) {
		return sourceInfoMapper.findUnsourceOrgList(pager);
	}
	
	/**
	 * 检查供应商编码是否已占用
	 * @author	黄文君
	 * @date	2017年4月12日 下午7:09:15
	 * @param	rOrgId
	 * @param	supplierCode
	 * @param	excludeSourceId
	 * @return	int
	 */
	
	public int existSupplierCode(Long rOrgId, String supplierCode, String excludeSourceId) {
		return sourceInfoMapper.existSupplierCode(rOrgId, supplierCode, excludeSourceId);
	}

	/**
	 * 业务校验: 是否可以停用或移除指定的供需关系
	 * @author	黄文君
	 * @date	2017年3月25日 下午11:33:44
	 * @param sourceInfo
	 * @return	void
	 */
	public void whetherCastOffSourceInfo(SourceInfo sourceInfo){
		
		logger.info("******** 业务校验: 是否可以停用或移除指定的供需关系    *******");
		
	}

	/**
	 * 查询指定机构的医院列表
	 */
	public List<Map<String, Object>> findMyHospitalList(Pager pager) {
		// TODO Auto-generated method stub
		return sourceInfoMapper.findMyHospitalList(pager);
	}

	public List<Map<String, Object>> findSourceListForSelect(Pager pager) {
		return sourceInfoMapper.findSourceListForSelect(pager);
	}

    
    public List<Map<String, Object>> findMySourceInfoList(Pager pager) {
        return sourceInfoMapper.findMySourceInfoList(pager);
    }

	public List<Map<String, Object>> queryRSourceStorageForSelect(Pager pager) {
		return sourceInfoMapper.queryRSourceStorageForSelect(pager);
	}
	
}

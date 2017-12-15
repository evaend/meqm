package com.phxl.ysy.dao;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.StorageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StorageInfoMapper {
	
	/**
	 * 根据条件，查询库房列表
	 * @author	黄文君
	 * @date	2017年4月25日 下午4:12:35
	 * @param	pager
	 * @return	List<StorageInfo>
	 */
	List<StorageInfo> queryStorageList(Pager<?> pager);

	/**
	 * 查询库房列表（联想下拉搜索）
	 * @author	黄文君
	 * @date	2017年4月26日 上午11:14:32
	 * @param	pager
	 * @return	List<Map<String,Object>>
	 */
	List<Map<String, Object>> findStorageListForSelector(Pager<?> pager);
	
	/**
	 * 判断某机构是否存在某个库房编号
	 * @author	黄文君
	 * @date	2017年4月28日 下午2:34:33
	 * @param	orgId
	 * @param	storageCode
	 * @return	Boolean
	 */
	Boolean existStorageCode(@Param("orgId")Long orgId, @Param("storageCode")String storageCode);
	
	/**
	 *  查询当前登录用户的机构下的所有的库房
	 * @param orgId 机构ID
	 * @return 库房ID，库房名称
	 */
	List<Map<String, Object>> selectLoginOrgStorage(Pager pager);

	/**
	 * 查找指定库房所属的大库房
	 * @author	黄文君
	 * @date	2017年9月6日 下午
	 * @param	storageGuid
	 * @return	Boolean
	 */
	String findRootByStorageGuid(String storageGuid);

	/**
	 * 查询带参数的库房
	 */
	StorageInfo findStorageWithConfig(String storageGuid);
}
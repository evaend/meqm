package com.phxl.ysy.dao;

import com.phxl.core.base.entity.Pager;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StorageDeptMapper {
	
	/**
	 * 查询库房开放科室列表
	 * @author	黄文君
	 * @date	2017年4月26日 下午2:57:29
	 * @param	storageGuid		库房guid
	 * @return	List<Map<String,Object>>
	 */
	List<Map<String, Object>> queryOpenDeptsByStorageGuid(String storageGuid);

	/**
	 * 判断某科室是否是指定库房的关联科室
	 * @author	黄文君
	 * @date	2017年11月2日
	 * @param  storageGuid   库房guid
	 * @param deptGuid      科室guid
	 * @return Boolean
	 */
	Boolean whetherDeptOfSpecStorage(@Param("storageGuid") String storageGuid, @Param("deptGuid")String deptGuid);

	/**
	 * 查询指定库房的开放科室列表（联想下拉搜索）
	 * @author	黄文君
	 * @date	2017年10月17日
	 * @param	pager
	 * @return	List<Map<String,Object>>
	 */
	List<Map<String, Object>> searchOpenDeptsByStorageId(Pager<?> pager);

	/**
	 * 根据库房guid删除库房开放科室
	 * @author	黄文君
	 * @date	2017年4月27日 上午10:25:58
	 * @param	storageGuid		库房guid
	 * @return	int
	 */
	int deleteDeptsByStorageGuid(String storageGuid);
	
	/**
	 * 批量添加: 库房开放科室
	 * @author	黄文君
	 * @date	2017年4月27日 上午10:37:30
	 * @param	storageGuid		库房guid
	 * @param	deptGuids		科室guid列表
	 * @return	int
	 */
	int insertDeptsByStorageGuid(@Param("storageGuid")String storageGuid, @Param("deptGuids")String[] deptGuids);

	/**
	 * 根据库房guid删除库房开放科室
	 * @param deptGuid
	 */
	void deleteStoragesByDeptGuid(String deptGuid);
	
}
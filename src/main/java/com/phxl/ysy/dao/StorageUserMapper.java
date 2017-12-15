package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.phxl.core.base.entity.Pager;

public interface StorageUserMapper {
	
	/**
	 * 查询库房工作人员列表
	 * @author	黄文君
	 * @date	2017年4月26日 下午3:53:19
	 * @param	storageGuid		库房guid
	 * @return	List<Map<String,Object>>
	 */
	List<Map<String, Object>> queryUsersByStorageGuid(String storageGuid);
	
	/**
	 * 根据库房guid删除库房工作人员
	 * @author	黄文君
	 * @date	2017年4月27日 上午10:25:58
	 * @param	storageGuid		库房guid
	 * @return	int
	 */
	int deleteUsersByStorageGuid(String storageGuid);
	
	/**
	 * 批量添加: 库房工作人员
	 * @author	黄文君
	 * @date	2017年4月27日 上午10:37:30
	 * @param	storageGuid		库房guid
	 * @param	userIds			用户id列表
	 * @return	int
	 */
	int insertUsersByStorageGuid(@Param("storageGuid")String storageGuid, @Param("userIds")String[] userIds);

	/**
	 * 【库房产品】查询当前登录用户所有的库房列表（下拉框）
	 */
	List<Map<String, Object>> findStoragesByUser(Pager pager);

	/**
	 * 检查当前用户是不是指定库房的工作人员
	 * @author	黄文君
	 * @date	2017年10月25日
	 * @param storageGuid   库房guid
	 * @param userId        用户id
	 * @return Boolean
	 */
	Boolean whetherUserOfSpecStorage(@Param("storageGuid") String storageGuid, @Param("userId")String userId);
	
	/**
     * 判断某库房是否属于当前用户所属的库房
     * @author  fenghui
     * @date    2017年11月1日
     * @param storageGuid   库房guid
     * @param userId        用户id
     * @param orgId         机构id
     * @return Boolean
     */
    Boolean whetherStoragesUserByStorageGuid(@Param("orgId")Long orgId, @Param("storageGuid") String storageGuid, @Param("userId")String userId);

	/**
     * 【库房产品】查询当前登录用户所有的一级库房列表（下拉框）
     */
	List<Map<String, Object>> findTopStorageByUser(Pager pager);
	
	/**
     * 【库房】查询某一级库房下所有的子库房信息（下拉框）
     */
    List<Map<String, Object>> findStorageByTop(Pager pager);

	/**
	 * 【科室产品】查询当前登录的用户所属的科室对应的库房列表（下拉框）
	 */
	List<Map<String, Object>> findStorageByUserDept(Pager pager);

	/**
	 * 查询库房工作人员下拉框
	 */
	List<Map<String, Object>> queryUsersByStorageGuidForSelector(Pager<Map<String, Object>> pager);

}
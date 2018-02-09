package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.phxl.core.base.entity.Pager;

public interface MessageItemOrgMapper {

	List<Map<String, Object>> selectMessageItemOrgList(Pager pager);

	void deleteOldOrgItem(@Param("miGlobalGuid")String miGlobalGuid);

	Set<String> selectOrgsByModuleIds(@Param("moduleId")String moduleId);

	void insertmessageItemOrgBatch( @Param("orgIds")Set<String> orgIds, @Param("miGlobalGuid")String miGlobalGuid);

	List<Map<String, Object>> findMiUsers(Pager pager);
	
	String selectWechatIdByUserId(@Param("userId")String userId);
	
	/**
	 * 自动匹配机构的消息接收人，目前是机构管理员，后期会扩展到消息项模块对应的人，但是这样也不是很准确
	 * 因为人员是和菜单关联的
	 */
	Set<String> selectMRceByOrgId(@Param("orgId")Long OrgId);

}
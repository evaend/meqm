package com.phxl.ysy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.phxl.ysy.entity.ConfigInfo;

 
public interface ConfigInfoMapper {

	/**
	 * 新增或更新配置信息
	 * @param configInfos 配置信息，code、value
	 * @param flag 不同配置类型，00科室，01库房，03机构
	 * @param flagGuid 不同类型的配置的源guid
	 */
	void insertUpdateConfigInfo(@Param("configInfos")List<ConfigInfo> configInfos,@Param("flag")String flag,@Param("flagGuid")String flagGuid);

	/**
	 * 删除科室之后，需要联动删除库房所属科室的配置
	 * 所属科室的code是deptGuid value等于参数的值 flag是01库房
	 */
	void deleteStorageDeptGuid(@Param("deptGuid")String deptGuid);
}
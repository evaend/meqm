package com.phxl.ysy.service;

import java.util.List;

import com.phxl.core.base.exception.ValidationException;
import com.phxl.ysy.entity.ConfigInfo;

public interface ConfigService {
	/**
	 * 新增或更新配置信息
	 * @param configInfos 配置信息，code、value
	 * @param flag 不同配置类型，00科室，01库房，03机构
	 * @param flagGuid 不同类型的配置的源guid
	 * @throws ValidationException 
	 */
	void insertUpdateConfigInfo(List<ConfigInfo> configInfos,String flag,String flagGuid) throws ValidationException;

}

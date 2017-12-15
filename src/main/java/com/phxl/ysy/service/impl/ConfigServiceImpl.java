package com.phxl.ysy.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.ysy.dao.ConfigInfoMapper;
import com.phxl.ysy.entity.ConfigInfo;
import com.phxl.ysy.service.ConfigService;

@Service
public class ConfigServiceImpl extends BaseService implements ConfigService {
	
	@Autowired
	ConfigInfoMapper configInfoMapper;

	public void insertUpdateConfigInfo(List<ConfigInfo> configInfos, String flag, String flagGuid) throws ValidationException {
		LocalAssert.notBlank(flag, "请传递配置类型");
		LocalAssert.notBlank(flagGuid, "请传递配置guid");
		configInfoMapper.insertUpdateConfigInfo(configInfos, flag, flagGuid);
	}

}

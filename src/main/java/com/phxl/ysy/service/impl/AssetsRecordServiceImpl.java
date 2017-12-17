package com.phxl.ysy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.ysy.dao.AssetsExtendMapper;
import com.phxl.ysy.dao.AssetsRecordMapper;
import com.phxl.ysy.service.AssetsRecordService;

@Service
public class AssetsRecordServiceImpl extends BaseService implements AssetsRecordService {
	@Autowired
	AssetsRecordMapper assetsRecordMapper;

	public Integer selectAssetsRecordCount() {
		return assetsRecordMapper.selectAssetsRecordCount();
	}

}

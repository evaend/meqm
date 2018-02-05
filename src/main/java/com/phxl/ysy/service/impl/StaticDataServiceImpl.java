package com.phxl.ysy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.dao.StaticDataMapper;
import com.phxl.ysy.service.StaticDataService;

@Service
public class StaticDataServiceImpl implements StaticDataService {

	@Autowired
	StaticDataMapper staticDataMapper;
	
	@Override
	public List<Map<String, Object>> selectStaticDataList(Pager pager) {
		return staticDataMapper.selectStaticDataList(pager);
	}

}

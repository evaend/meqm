package com.phxl.ysy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.ysy.dao.OrgDeptMapper;
import com.phxl.ysy.service.OrgDeptService;

@Service
public class OrgDeptServiceImpl extends BaseService implements OrgDeptService{

	@Autowired
	OrgDeptMapper orgDeptMapper;
	
	@Override
	public List<Map<String, Object>> selectUseDeptList(Pager pager) {
		return orgDeptMapper.selectUseDeptList(pager);
	}

	@Override
	public List<Map<String, Object>> selectDeptOfUseList(Pager pager) {
		return orgDeptMapper.selectDeptOfUseList(pager);
	}
}

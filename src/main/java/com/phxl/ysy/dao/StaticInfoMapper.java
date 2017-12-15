package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.StaticInfo;

public interface StaticInfoMapper {
    StaticInfo selectByPrimaryKey(String staticId);

	List<Map<String, Object>> searchStaticInfo(Pager pager);

	List<Map<String, Object>> searchStaticByOrgId(Pager pager);

	int countStaticInfo(StaticInfo staticInfo);

	List<Map<String, Object>> orgStaticInfo(Pager pager);
}
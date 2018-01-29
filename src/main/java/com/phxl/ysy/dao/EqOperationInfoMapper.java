package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;

public interface EqOperationInfoMapper {
	//查询操作记录
	List<Map<String, Object>> selectEqOperation(Pager pager);
}
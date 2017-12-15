package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;

public interface UserOperationMapper {

	//根据参数查询操作记录
	List<Map<String, Object>> searchOpRecords(Pager pager);

}
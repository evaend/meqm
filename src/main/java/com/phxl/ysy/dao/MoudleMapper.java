package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

public interface MoudleMapper {

	/**
	 * 获取模块名称
	 */
	List<Map<String, Object>> getDistinctModules();

}
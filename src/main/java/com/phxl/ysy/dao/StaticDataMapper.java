package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;

public interface StaticDataMapper {
	/**
	 * 查询基础数据列表
	 * @param pager
	 * @return
	 */
    List<Map<String, Object>> selectStaticDataList(Pager pager);
}
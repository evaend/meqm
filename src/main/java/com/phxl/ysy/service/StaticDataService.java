package com.phxl.ysy.service;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;

public interface StaticDataService {
	/**
	 * 查询基础数据列表
	 * @param pager
	 * @return
	 */
    List<Map<String, Object>> selectStaticDataList(Pager pager);

}

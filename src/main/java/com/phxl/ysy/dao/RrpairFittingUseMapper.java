package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;

public interface RrpairFittingUseMapper {
	/**
	 * 查询当前维修记录的维修配件使用列表
	 * @param pager
	 * @return
	 */
    List<Map<String, Object>> selectRrpairFittingList(Pager pager);
}
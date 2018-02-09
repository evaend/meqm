package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;

public interface MessageItemGlobalMapper {

	List<Map<String, Object>> selectMessageItemGlobalList(Pager pager);
}
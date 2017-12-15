package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.GkTemplate;

public interface GkTemplateMapper {

	List<Map<String, Object>> searchGkTemplateList(Pager pager);
}
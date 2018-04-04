package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;

public interface MaintainOrderMapper {
	List<Map<String, Object>> selectMaintainOrderList(Pager pager);
	
	Map<String, Object> selectMaintainOrderDetail(Pager pager);
	
	List<Map<String, Object>> selectMaintainDetailList(Pager pager);	
}
package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.MaintainTemplate;

public interface MaintainTemplateMapper {
	List<Map<String, Object>> selectMaintainTemplate(Pager pager);

	List<Map<String, Object>> selectMaintainTemplateDetail(Pager pager);
	
	List<Map<String, Object>> selectMaintainTemplateEquipment(Pager pager);
	
	List<Map<String, Object>> selectEquipmentInTemplate(Pager pager);
	
	Integer selectTempDetailIsRepetition(Map<String, Object> map);
}
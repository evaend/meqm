package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.AssetsRecord;
import com.phxl.ysy.web.dto.EquipmentDto;

public interface AssetsRecordMapper {
	//查询资产总量
	Integer selectAssetsRecordCount();
	
	//查询资产档案信息列表
	List<Map<String, Object>> selectAssetsList(Pager pager);

	int importAssets(List<EquipmentDto> list);

	int importEquipments(List<EquipmentDto> list);
}
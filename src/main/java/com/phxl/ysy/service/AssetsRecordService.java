package com.phxl.ysy.service;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.service.IBaseService;

public interface AssetsRecordService extends IBaseService {

    //查询资产总量
	Integer selectAssetsRecordCount();

	//查询资产档案信息列表
	List<Map<String, Object>> selectAssetsList(Pager pager);
}

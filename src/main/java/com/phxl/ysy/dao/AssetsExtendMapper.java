package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.AssetsExtend;
import com.phxl.ysy.entity.AssetsExtendKey;

public interface AssetsExtendMapper {
	//查询资产配件列表
    List<Map<String , Object >> selectAssetsExtendList(Pager pager);
}
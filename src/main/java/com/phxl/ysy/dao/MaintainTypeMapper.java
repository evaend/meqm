package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.annotation.BaseSql;
import com.phxl.core.base.entity.Pager;

@BaseSql(tableName = "TD_MAINTAIN_TYPE", resultName = "com.phxl.ysy.dao.MaintainTypeMapper.BaseResultMap")
public interface MaintainTypeMapper {
    public List<Map<String, Object>> searchMaintainType(Pager pager);
}
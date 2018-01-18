package com.phxl.ysy.dao;

import com.phxl.ysy.entity.OrgModuleKey;

public interface OrgModuleMapper {
    int deleteByPrimaryKey(OrgModuleKey key);

    int insert(OrgModuleKey record);

    int insertSelective(OrgModuleKey record);
}
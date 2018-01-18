package com.phxl.ysy.dao;

import com.phxl.ysy.entity.OrgInfo;

public interface OrgInfoMapper {
    int insert(OrgInfo record);

    int insertSelective(OrgInfo record);
}
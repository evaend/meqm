package com.phxl.ysy.dao;

import com.phxl.ysy.entity.StaticInfo;

public interface StaticInfoMapper {
    int insert(StaticInfo record);

    int insertSelective(StaticInfo record);
}
package com.phxl.ysy.dao;

import com.phxl.ysy.entity.StaticData;

public interface StaticDataMapper {
    int insert(StaticData record);

    int insertSelective(StaticData record);
}
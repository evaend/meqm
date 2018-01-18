package com.phxl.ysy.dao;

import com.phxl.ysy.entity.ModuleMenuKey;

public interface ModuleMenuMapper {
    int deleteByPrimaryKey(ModuleMenuKey key);

    int insert(ModuleMenuKey record);

    int insertSelective(ModuleMenuKey record);
}
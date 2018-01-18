package com.phxl.ysy.dao;

import com.phxl.ysy.entity.Module;

public interface ModuleMapper {
    int deleteByPrimaryKey(String moduleId);

    int insert(Module record);

    int insertSelective(Module record);

    Module selectByPrimaryKey(String moduleId);

    int updateByPrimaryKeySelective(Module record);

    int updateByPrimaryKey(Module record);
}
package com.phxl.ysy.dao;

import com.phxl.ysy.entity.DeptInfo;

public interface DeptInfoMapper {
    int insert(DeptInfo record);

    int insertSelective(DeptInfo record);
}
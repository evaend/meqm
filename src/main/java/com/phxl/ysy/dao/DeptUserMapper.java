package com.phxl.ysy.dao;

import com.phxl.ysy.entity.DeptUser;
import com.phxl.ysy.entity.DeptUserKey;

public interface DeptUserMapper {
    int deleteByPrimaryKey(DeptUserKey key);

    int insert(DeptUser record);

    int insertSelective(DeptUser record);

    DeptUser selectByPrimaryKey(DeptUserKey key);

    int updateByPrimaryKeySelective(DeptUser record);

    int updateByPrimaryKey(DeptUser record);
}
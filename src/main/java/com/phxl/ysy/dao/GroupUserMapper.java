package com.phxl.ysy.dao;

import com.phxl.ysy.entity.GroupUserKey;

public interface GroupUserMapper {
    int deleteByPrimaryKey(GroupUserKey key);

    int insert(GroupUserKey record);

    int insertSelective(GroupUserKey record);
}
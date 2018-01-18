package com.phxl.ysy.dao;

import com.phxl.ysy.entity.GroupMenuKey;

public interface GroupMenuMapper {
    int deleteByPrimaryKey(GroupMenuKey key);

    int insert(GroupMenuKey record);

    int insertSelective(GroupMenuKey record);
}
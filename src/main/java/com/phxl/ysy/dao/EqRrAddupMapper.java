package com.phxl.ysy.dao;

import com.phxl.ysy.entity.EqRrAddup;
import com.phxl.ysy.entity.EqRrAddupKey;

public interface EqRrAddupMapper {
    int deleteByPrimaryKey(EqRrAddupKey key);

    int insert(EqRrAddup record);

    int insertSelective(EqRrAddup record);

    EqRrAddup selectByPrimaryKey(EqRrAddupKey key);

    int updateByPrimaryKeySelective(EqRrAddup record);

    int updateByPrimaryKey(EqRrAddup record);
}
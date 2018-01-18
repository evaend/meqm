package com.phxl.ysy.dao;

import com.phxl.ysy.entity.RrpairOrderAcce;

public interface RrpairOrderAcceMapper {
    int deleteByPrimaryKey(String rrpairOrderAcce);

    int insert(RrpairOrderAcce record);

    int insertSelective(RrpairOrderAcce record);

    RrpairOrderAcce selectByPrimaryKey(String rrpairOrderAcce);

    int updateByPrimaryKeySelective(RrpairOrderAcce record);

    int updateByPrimaryKey(RrpairOrderAcce record);
}
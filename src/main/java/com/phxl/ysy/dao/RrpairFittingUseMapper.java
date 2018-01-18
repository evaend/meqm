package com.phxl.ysy.dao;

import com.phxl.ysy.entity.RrpairFittingUse;

public interface RrpairFittingUseMapper {
    int deleteByPrimaryKey(String rrpairFittingUseGuid);

    int insert(RrpairFittingUse record);

    int insertSelective(RrpairFittingUse record);

    RrpairFittingUse selectByPrimaryKey(String rrpairFittingUseGuid);

    int updateByPrimaryKeySelective(RrpairFittingUse record);

    int updateByPrimaryKey(RrpairFittingUse record);
}
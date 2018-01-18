package com.phxl.ysy.dao;

import com.phxl.ysy.entity.EqOperationInfo;

public interface EqOperationInfoMapper {
    int deleteByPrimaryKey(String tsOpId);

    int insert(EqOperationInfo record);

    int insertSelective(EqOperationInfo record);

    EqOperationInfo selectByPrimaryKey(String tsOpId);

    int updateByPrimaryKeySelective(EqOperationInfo record);

    int updateByPrimaryKey(EqOperationInfo record);
}
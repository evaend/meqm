package com.phxl.ysy.dao;

import com.phxl.ysy.entity.EqOperation;

public interface EqOperationMapper {
    int deleteByPrimaryKey(String tsEqOperationGuId);

    int insert(EqOperation record);

    int insertSelective(EqOperation record);

    EqOperation selectByPrimaryKey(String tsEqOperationGuId);

    int updateByPrimaryKeySelective(EqOperation record);

    int updateByPrimaryKey(EqOperation record);
}
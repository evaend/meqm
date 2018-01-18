package com.phxl.ysy.dao;

import com.phxl.ysy.entity.AssetsExtend;
import com.phxl.ysy.entity.AssetsExtendKey;

public interface AssetsExtendMapper {
    int deleteByPrimaryKey(AssetsExtendKey key);

    int insert(AssetsExtend record);

    int insertSelective(AssetsExtend record);

    AssetsExtend selectByPrimaryKey(AssetsExtendKey key);

    int updateByPrimaryKeySelective(AssetsExtend record);

    int updateByPrimaryKey(AssetsExtend record);
}
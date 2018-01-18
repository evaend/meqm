package com.phxl.ysy.dao;

import com.phxl.ysy.entity.Label;
import com.phxl.ysy.entity.LabelKey;

public interface LabelMapper {
    int deleteByPrimaryKey(LabelKey key);

    int insert(Label record);

    int insertSelective(Label record);

    Label selectByPrimaryKey(LabelKey key);

    int updateByPrimaryKeySelective(Label record);

    int updateByPrimaryKey(Label record);
}
package com.phxl.ysy.dao;

import com.phxl.ysy.entity.WorkerInfo;

public interface WorkerInfoMapper {
    int insert(WorkerInfo record);

    int insertSelective(WorkerInfo record);
}
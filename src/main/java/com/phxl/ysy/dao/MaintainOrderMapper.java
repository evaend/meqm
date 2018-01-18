package com.phxl.ysy.dao;

import com.phxl.ysy.entity.MaintainOrder;

public interface MaintainOrderMapper {
    int insert(MaintainOrder record);

    int insertSelective(MaintainOrder record);
}
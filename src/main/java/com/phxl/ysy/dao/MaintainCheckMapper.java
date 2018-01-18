package com.phxl.ysy.dao;

import com.phxl.ysy.entity.MaintainCheck;

public interface MaintainCheckMapper {
    int insert(MaintainCheck record);

    int insertSelective(MaintainCheck record);
}
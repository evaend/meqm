package com.phxl.ysy.dao;

import com.phxl.ysy.entity.Equipment;

public interface EquipmentMapper {
    int insert(Equipment record);

    int insertSelective(Equipment record);
}
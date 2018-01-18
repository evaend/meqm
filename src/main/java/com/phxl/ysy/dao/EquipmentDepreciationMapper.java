package com.phxl.ysy.dao;

import com.phxl.ysy.entity.EquipmentDepreciation;

public interface EquipmentDepreciationMapper {
    int insert(EquipmentDepreciation record);

    int insertSelective(EquipmentDepreciation record);
}
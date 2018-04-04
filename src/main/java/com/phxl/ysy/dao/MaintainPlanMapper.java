package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.MaintainPlan;

public interface MaintainPlanMapper {
    List<Map<String, Object>> selectMaintainPlanList(Pager pager);
    
    Map<String, Object> selectMaintainPlanDetail(Pager pager);
    
    List<Map<String, Object>> selectPlanMaintainType(String maintainPlanId);
}
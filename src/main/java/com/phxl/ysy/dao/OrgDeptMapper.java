package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.OrgDept;

public interface OrgDeptMapper {
	//查询所属机构所有管理科室
    List<Map<String, Object>> selectUseDeptList(Pager pager);

    //查询科室用户
    List<Map<String, Object>> selectDeptOfUseList(Pager pager);
    
    //根据维修单ID查询对应资产的管理科室维修员
    List<Map<String, Object>> selectBDeptUser(@Param("rrpairOrderGuid")String rrpairOrderGuid);
}
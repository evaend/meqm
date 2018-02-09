package com.phxl.ysy.dao;

import com.phxl.ysy.entity.OrgDept;

public interface OrgDeptMapper {
    int insert(OrgDept record);

    int insertSelective(OrgDept record);
}
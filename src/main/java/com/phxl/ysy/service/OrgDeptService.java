package com.phxl.ysy.service;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.service.IBaseService;

public interface OrgDeptService extends IBaseService{
	//查询所属机构所有管理科室
    List<Map<String, Object>> selectUseDeptList(Pager pager);

    //查询科室用户
    List<Map<String, Object>> selectDeptOfUseList(Pager pager);

}

package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.CertInfoZc;

public interface CertInfoZcMapper {
    //查询产品证件列表
	List<Map<String, Object>> selectCertInfoList(Pager pager);
}
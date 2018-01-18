package com.phxl.ysy.dao;

import com.phxl.ysy.entity.CertInfo;

public interface CertInfoMapper {
    int deleteByPrimaryKey(String certId);

    int insert(CertInfo record);

    int insertSelective(CertInfo record);

    CertInfo selectByPrimaryKey(String certId);

    int updateByPrimaryKeySelective(CertInfo record);

    int updateByPrimaryKey(CertInfo record);
}
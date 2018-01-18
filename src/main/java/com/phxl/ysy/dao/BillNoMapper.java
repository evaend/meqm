package com.phxl.ysy.dao;

import com.phxl.ysy.entity.BillNo;

public interface BillNoMapper {
    int deleteByPrimaryKey(String billGuid);

    int insert(BillNo record);

    int insertSelective(BillNo record);

    BillNo selectByPrimaryKey(String billGuid);

    int updateByPrimaryKeySelective(BillNo record);

    int updateByPrimaryKey(BillNo record);
}
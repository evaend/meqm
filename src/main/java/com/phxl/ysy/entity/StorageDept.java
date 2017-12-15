package com.phxl.ysy.entity;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TD_STORAGE_OPEN_DEPT", resultName="com.phxl.ysy.dao.StorageDeptMapper.BaseResultMap")
public class StorageDept extends StorageDeptKey {
    private String deptName;

    private Long orgId;

    private String orgName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
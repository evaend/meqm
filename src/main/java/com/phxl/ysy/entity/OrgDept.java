package com.phxl.ysy.entity;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TD_ORG_DEPT", resultName="com.phxl.ysy.dao.OrgDeptMapper.BaseResultMap")
public class OrgDept {
    private String deptGuid;

    private Long orgId;

    private String deptCode;

    private String deptName;

    private String fqun;

    private String parentDeptGuid;

    private String fflag;

    private String fstate;

    private String deptTypeCode;

    private String deptTypeName;

    private String tfRemark;

    private String eqMFstate;

    private String eqPFstate;

    public String getDeptGuid() {
        return deptGuid;
    }

    public void setDeptGuid(String deptGuid) {
        this.deptGuid = deptGuid == null ? null : deptGuid.trim();
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getFqun() {
        return fqun;
    }

    public void setFqun(String fqun) {
        this.fqun = fqun == null ? null : fqun.trim();
    }

    public String getParentDeptGuid() {
        return parentDeptGuid;
    }

    public void setParentDeptGuid(String parentDeptGuid) {
        this.parentDeptGuid = parentDeptGuid == null ? null : parentDeptGuid.trim();
    }

    public String getFflag() {
        return fflag;
    }

    public void setFflag(String fflag) {
        this.fflag = fflag == null ? null : fflag.trim();
    }

    public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate == null ? null : fstate.trim();
    }

    public String getDeptTypeCode() {
        return deptTypeCode;
    }

    public void setDeptTypeCode(String deptTypeCode) {
        this.deptTypeCode = deptTypeCode == null ? null : deptTypeCode.trim();
    }

    public String getDeptTypeName() {
        return deptTypeName;
    }

    public void setDeptTypeName(String deptTypeName) {
        this.deptTypeName = deptTypeName == null ? null : deptTypeName.trim();
    }

    public String getTfRemark() {
        return tfRemark;
    }

    public void setTfRemark(String tfRemark) {
        this.tfRemark = tfRemark == null ? null : tfRemark.trim();
    }

    public String getEqMFstate() {
        return eqMFstate;
    }

    public void setEqMFstate(String eqMFstate) {
        this.eqMFstate = eqMFstate == null ? null : eqMFstate.trim();
    }

    public String getEqPFstate() {
        return eqPFstate;
    }

    public void setEqPFstate(String eqPFstate) {
        this.eqPFstate = eqPFstate == null ? null : eqPFstate.trim();
    }
}
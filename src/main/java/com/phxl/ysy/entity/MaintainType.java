package com.phxl.ysy.entity;

import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName = "TD_MAINTAIN_TYPE", resultName = "com.phxl.ysy.dao.MaintainTypeMapper.BaseResultMap")
public class MaintainType {
    private String guId;

    private String maintainTypeId;

    private String maintainTypeName;

    private String fqun;

    private String parentId;

    private Integer fevel;

    private Integer digit;

    private String createUserid;

    private Date createTime;

    private Date modifyTime;

    private Short styleFlag;

    private Long orgId;

    public String getGuId() {
        return guId;
    }

    public void setGuId(String guId) {
        this.guId = guId == null ? null : guId.trim();
    }

    public String getMaintainTypeId() {
        return maintainTypeId;
    }

    public void setMaintainTypeId(String maintainTypeId) {
        this.maintainTypeId = maintainTypeId == null ? null : maintainTypeId.trim();
    }

    public String getMaintainTypeName() {
        return maintainTypeName;
    }

    public void setMaintainTypeName(String maintainTypeName) {
        this.maintainTypeName = maintainTypeName == null ? null : maintainTypeName.trim();
    }

    public String getFqun() {
        return fqun;
    }

    public void setFqun(String fqun) {
        this.fqun = fqun == null ? null : fqun.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer getFevel() {
        return fevel;
    }

    public void setFevel(Integer fevel) {
        this.fevel = fevel;
    }

    public Integer getDigit() {
        return digit;
    }

    public void setDigit(Integer digit) {
        this.digit = digit;
    }

    public String getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid == null ? null : createUserid.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Short getStyleFlag() {
        return styleFlag;
    }

    public void setStyleFlag(Short styleFlag) {
        this.styleFlag = styleFlag;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
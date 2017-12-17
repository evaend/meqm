package com.phxl.ysy.entity;

import java.util.Date;

public class StaticData {
    private String staticDataGuid;

    private String tfCloCode;

    private String tfCloName;

    private String createUserid;

    private Date createTime;

    private String tfRemark;

    private String staticId;

    private Short fsort;

    public String getStaticDataGuid() {
        return staticDataGuid;
    }

    public void setStaticDataGuid(String staticDataGuid) {
        this.staticDataGuid = staticDataGuid == null ? null : staticDataGuid.trim();
    }

    public String getTfCloCode() {
        return tfCloCode;
    }

    public void setTfCloCode(String tfCloCode) {
        this.tfCloCode = tfCloCode == null ? null : tfCloCode.trim();
    }

    public String getTfCloName() {
        return tfCloName;
    }

    public void setTfCloName(String tfCloName) {
        this.tfCloName = tfCloName == null ? null : tfCloName.trim();
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

    public String getTfRemark() {
        return tfRemark;
    }

    public void setTfRemark(String tfRemark) {
        this.tfRemark = tfRemark == null ? null : tfRemark.trim();
    }

    public String getStaticId() {
        return staticId;
    }

    public void setStaticId(String staticId) {
        this.staticId = staticId == null ? null : staticId.trim();
    }

    public Short getFsort() {
        return fsort;
    }

    public void setFsort(Short fsort) {
        this.fsort = fsort;
    }
}
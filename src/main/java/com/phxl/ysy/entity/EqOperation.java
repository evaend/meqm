package com.phxl.ysy.entity;

import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TS_EQ_OPERATION", resultName="com.phxl.ysy.dao.EqOperationMapper.BaseResultMap")
public class EqOperation {
    private String tsEqOperationGuId;

    private String userId;

    private String opType;

    private Date createtime;

    private String tfRemark;

    public String getTsEqOperationGuId() {
        return tsEqOperationGuId;
    }

    public void setTsEqOperationGuId(String tsEqOperationGuId) {
        this.tsEqOperationGuId = tsEqOperationGuId == null ? null : tsEqOperationGuId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType == null ? null : opType.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getTfRemark() {
        return tfRemark;
    }

    public void setTfRemark(String tfRemark) {
        this.tfRemark = tfRemark == null ? null : tfRemark.trim();
    }
}
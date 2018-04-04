package com.phxl.ysy.entity;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TD_MAINTAIN_ORDER_DETAIL", resultName="com.phxl.ysy.dao.MaintainOrderDetailMapper.BaseResultMap")
public class MaintainOrderDetail {
    private String maintainOrderDetailGuid;

    private String maintainGuid;

    private String maintainTypeId;

    private String maintainResult;

    private String tfRemark;

    public String getMaintainOrderDetailGuid() {
        return maintainOrderDetailGuid;
    }

    public void setMaintainOrderDetailGuid(String maintainOrderDetailGuid) {
        this.maintainOrderDetailGuid = maintainOrderDetailGuid == null ? null : maintainOrderDetailGuid.trim();
    }

    public String getMaintainGuid() {
        return maintainGuid;
    }

    public void setMaintainGuid(String maintainGuid) {
        this.maintainGuid = maintainGuid == null ? null : maintainGuid.trim();
    }

    public String getMaintainTypeId() {
        return maintainTypeId;
    }

    public void setMaintainTypeId(String maintainTypeId) {
        this.maintainTypeId = maintainTypeId == null ? null : maintainTypeId.trim();
    }

    public String getMaintainResult() {
        return maintainResult;
    }

    public void setMaintainResult(String maintainResult) {
        this.maintainResult = maintainResult == null ? null : maintainResult.trim();
    }

    public String getTfRemark() {
        return tfRemark;
    }

    public void setTfRemark(String tfRemark) {
        this.tfRemark = tfRemark == null ? null : tfRemark.trim();
    }
}
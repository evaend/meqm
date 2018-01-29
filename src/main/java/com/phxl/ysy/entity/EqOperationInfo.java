package com.phxl.ysy.entity;

import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TS_EQ_OPERATION_INFO", resultName="com.phxl.ysy.dao.EqOperationInfoMapper.BaseResultMap")
public class EqOperationInfo {
    private String tsOpId;

    private String opId;

    private String opText;

    private String opType;

    private String opUserId;

    private Date opTime;

    private String opA;

    private String opB;

    private String assetsRecord;

    private String equipmentCode;
    
    private String tfRemark;

    public String getTsOpId() {
        return tsOpId;
    }

    public void setTsOpId(String tsOpId) {
        this.tsOpId = tsOpId == null ? null : tsOpId.trim();
    }

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId == null ? null : opId.trim();
    }

    public String getOpText() {
        return opText;
    }

    public void setOpText(String opText) {
        this.opText = opText == null ? null : opText.trim();
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType == null ? null : opType.trim();
    }

    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId == null ? null : opUserId.trim();
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public String getOpA() {
        return opA;
    }

    public void setOpA(String opA) {
        this.opA = opA == null ? null : opA.trim();
    }

    public String getOpB() {
        return opB;
    }

    public void setOpB(String opB) {
        this.opB = opB == null ? null : opB.trim();
    }

    public String getAssetsRecord() {
        return assetsRecord;
    }

    public void setAssetsRecord(String assetsRecord) {
        this.assetsRecord = assetsRecord == null ? null : assetsRecord.trim();
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode == null ? null : equipmentCode.trim();
    }

	public String getTfRemark() {
		return tfRemark;
	}

	public void setTfRemark(String tfRemark) {
		this.tfRemark = tfRemark;
	}
}
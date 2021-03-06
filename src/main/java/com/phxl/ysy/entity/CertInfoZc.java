package com.phxl.ysy.entity;

import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TD_CERT_INFO_ZC", resultName="com.phxl.ysy.dao.CertInfoZcMapper.BaseResultMap")
public class CertInfoZc {
    private String certId;

    private Long orgId;

    private String certNo;

    private String tfAccessory;

    private String tfRemark;

    private String certCode;

    private String tfAccessoryFile;

    private String createUserid;

    private Date createTime;
    
    private String assetsRecord;
    
    private String equipmentCode;

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId == null ? null : certId.trim();
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo == null ? null : certNo.trim();
    }

    public String getTfAccessory() {
        return tfAccessory;
    }

    public void setTfAccessory(String tfAccessory) {
        this.tfAccessory = tfAccessory == null ? null : tfAccessory.trim();
    }

    public String getTfRemark() {
        return tfRemark;
    }

    public void setTfRemark(String tfRemark) {
        this.tfRemark = tfRemark == null ? null : tfRemark.trim();
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode == null ? null : certCode.trim();
    }

    public String getTfAccessoryFile() {
        return tfAccessoryFile;
    }

    public void setTfAccessoryFile(String tfAccessoryFile) {
        this.tfAccessoryFile = tfAccessoryFile == null ? null : tfAccessoryFile.trim();
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

	public String getAssetsRecord() {
		return assetsRecord;
	}

	public void setAssetsRecord(String assetsRecord) {
		this.assetsRecord = assetsRecord;
	}

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
}
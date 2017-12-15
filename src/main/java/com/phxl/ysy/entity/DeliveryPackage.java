package com.phxl.ysy.entity;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TB_DELIVERY_PACKAGE", resultName="com.phxl.ysy.dao.DeliveryPackageMapper.BaseResultMap")
public class DeliveryPackage {
	
    private String packageAttrGuid;

    private String sendId;

    private Integer packageId;

    private String attributeId;

    private Long amount;

    private String isImplantFlag;

    private String isToolFlag;

	private String saveFlag;

    public String getPackageAttrGuid() {
		return packageAttrGuid;
	}

	public void setPackageAttrGuid(String packageAttrGuid) {
		this.packageAttrGuid = packageAttrGuid;
	}

	public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getIsImplantFlag() {
        return isImplantFlag;
    }

    public void setIsImplantFlag(String isImplantFlag) {
        this.isImplantFlag = isImplantFlag;
    }

    public String getIsToolFlag() {
        return isToolFlag;
    }

    public void setIsToolFlag(String isToolFlag) {
        this.isToolFlag = isToolFlag;
    }

	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}
}
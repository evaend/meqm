package com.phxl.ysy.web.dto;

import java.io.Serializable;

public class DeliveryPackageDto implements Serializable {

    /**包序号*/
    private Integer packageId;

    /**产品属性编码*/
    private String attributeId;

    /**数量*/
    private Integer tfAmount;

    /**是否包含植入物*/
    private String isImplantFlag;

    private String isToolFlag;

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

    public Integer getTfAmount() {
        return tfAmount;
    }

    public void setTfAmount(Integer tfAmount) {
        this.tfAmount = tfAmount;
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

}
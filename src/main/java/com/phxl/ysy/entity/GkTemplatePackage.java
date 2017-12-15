package com.phxl.ysy.entity;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TB_GK_TEMPLATE_PACKAGE", resultName="com.phxl.ysy.dao.GkTemplatePackageMapper.BaseResultMap")
public class GkTemplatePackage {
    private String gkTemplatePackageGuid;

    private String attributeId;

    private String gkTemplateGuid;

    private Integer tfAmount;

    private String isImplantFlag;

    private String isToolFlag;

    private String saveFlag;
    
    private Integer packageId;

    public String getGkTemplatePackageGuid() {
        return gkTemplatePackageGuid;
    }

    public void setGkTemplatePackageGuid(String gkTemplatePackageGuid) {
        this.gkTemplatePackageGuid = gkTemplatePackageGuid == null ? null : gkTemplatePackageGuid.trim();
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId == null ? null : attributeId.trim();
    }

    public String getGkTemplateGuid() {
        return gkTemplateGuid;
    }

    public void setGkTemplateGuid(String gkTemplateGuid) {
        this.gkTemplateGuid = gkTemplateGuid == null ? null : gkTemplateGuid.trim();
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
        this.isImplantFlag = isImplantFlag == null ? null : isImplantFlag.trim();
    }

    public String getIsToolFlag() {
        return isToolFlag;
    }

    public void setIsToolFlag(String isToolFlag) {
        this.isToolFlag = isToolFlag == null ? null : isToolFlag.trim();
    }

    public String getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(String saveFlag) {
        this.saveFlag = saveFlag == null ? null : saveFlag.trim();
    }

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
}
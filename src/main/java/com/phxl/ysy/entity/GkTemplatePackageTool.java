package com.phxl.ysy.entity;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TB_GK_TEMPLATE_PACKAGE_TOOL", resultName="com.phxl.ysy.dao.GkTemplatePackageToolMapper.BaseResultMap")
public class GkTemplatePackageTool {
    private String gkPackageToolGuid;

    private Integer packageId;

    private Long tfAmount;

    private String toolCode;

    private String toolName;

    private String gkTemplateGuid;

    private Boolean isSelected;

    public String getGkPackageToolGuid() {
        return gkPackageToolGuid;
    }

    public void setGkPackageToolGuid(String gkPackageToolGuid) {
        this.gkPackageToolGuid = gkPackageToolGuid == null ? null : gkPackageToolGuid.trim();
    }

    public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public Long getTfAmount() {
        return tfAmount;
    }

    public void setTfAmount(Long tfAmount) {
        this.tfAmount = tfAmount;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode == null ? null : toolCode.trim();
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName == null ? null : toolName.trim();
    }

    public String getGkTemplateGuid() {
        return gkTemplateGuid;
    }

    public void setGkTemplateGuid(String gkTemplateGuid) {
        this.gkTemplateGuid = gkTemplateGuid == null ? null : gkTemplateGuid.trim();
    }

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
}
package com.phxl.ysy.entity;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TB_DELIVERY_PACKAGE_TOOL", resultName="com.phxl.ysy.dao.PackageToolMapper.BaseResultMap")
public class PackageTool {
    private String packageToolGuid;

    private String sendId;

    private Integer packageId;

    private Long amount;

    private String toolCode;

    private String toolName;

	private String saveFlag;

	private Boolean isSelected;

    public String getPackageToolGuid() {
        return packageToolGuid;
    }

    public void setPackageToolGuid(String packageToolGuid) {
        this.packageToolGuid = packageToolGuid;
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

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}
}
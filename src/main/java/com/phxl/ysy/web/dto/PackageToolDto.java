package com.phxl.ysy.web.dto;

import java.io.Serializable;

public class PackageToolDto implements Serializable {

	private String packageToolGuid;

	/**包序号*/
    private Integer packageId;

    /**工具编码*/
    private String toolCode;

    /**数量*/
    private Long tfAmount;

	public String getPackageToolGuid() {
		return packageToolGuid;
	}

	public void setPackageToolGuid(String packageToolGuid) {
		this.packageToolGuid = packageToolGuid;
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
        this.toolCode = toolCode;
    }

}
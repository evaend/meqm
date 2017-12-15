package com.phxl.ysy.entity;

import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TB_GK_TEMPLATE_DETAIL", resultName="com.phxl.ysy.dao.GkTemplateDetailMapper.BaseResultMap")
public class GkTemplateDetail {
    private String gkTemplateDetailGuid;

    private String gkTemplateGuid;

    private String tenderDetailGuid;

    private Long tfAmount;

    private String flot;

    private Date prodDate;

    private Date usefulDate;

    private String saveFlag;

    public String getGkTemplateDetailGuid() {
        return gkTemplateDetailGuid;
    }

    public void setGkTemplateDetailGuid(String gkTemplateDetailGuid) {
        this.gkTemplateDetailGuid = gkTemplateDetailGuid == null ? null : gkTemplateDetailGuid.trim();
    }

    public String getGkTemplateGuid() {
        return gkTemplateGuid;
    }

    public void setGkTemplateGuid(String gkTemplateGuid) {
        this.gkTemplateGuid = gkTemplateGuid == null ? null : gkTemplateGuid.trim();
    }

    public String getTenderDetailGuid() {
		return tenderDetailGuid;
	}

	public void setTenderDetailGuid(String tenderDetailGuid) {
		this.tenderDetailGuid = tenderDetailGuid;
	}

	public Long getTfAmount() {
        return tfAmount;
    }

    public void setTfAmount(Long tfAmount) {
        this.tfAmount = tfAmount;
    }

    public String getFlot() {
        return flot;
    }

    public void setFlot(String flot) {
        this.flot = flot == null ? null : flot.trim();
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Date getUsefulDate() {
        return usefulDate;
    }

    public void setUsefulDate(Date usefulDate) {
        this.usefulDate = usefulDate;
    }

    public String getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(String saveFlag) {
        this.saveFlag = saveFlag == null ? null : saveFlag.trim();
    }
}
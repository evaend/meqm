package com.phxl.ysy.entity;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName = "TD_MAINTAIN_TEMPLATE_DETAIL", resultName = "com.phxl.ysy.dao.MaintainTemplateDetailMapper.BaseResultMap")
public class MaintainTemplateDetail {
    private String templateDetailGuid;

    private String templateId;

    private String maintainTypeId;

    public String getTemplateDetailGuid() {
        return templateDetailGuid;
    }

    public void setTemplateDetailGuid(String templateDetailGuid) {
        this.templateDetailGuid = templateDetailGuid == null ? null : templateDetailGuid.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getMaintainTypeId() {
        return maintainTypeId;
    }

    public void setMaintainTypeId(String maintainTypeId) {
        this.maintainTypeId = maintainTypeId == null ? null : maintainTypeId.trim();
    }
}
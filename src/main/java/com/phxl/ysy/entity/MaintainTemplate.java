package com.phxl.ysy.entity;

import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName = "TD_MAINTAIN_TEMPLATE", resultName = "com.phxl.ysy.dao.MaintainTemplateMapper.BaseResultMap")
public class MaintainTemplate {
    private String maintainTemplateId;

    private String maintainTemplateName;

    private Long orgId;

    private String useDeptGuid;

    private String createUserid;

    private Date createTime;

    private String modifyUserid;

    private Date modifiyTime;

    private Long fsort;

    public String getMaintainTemplateId() {
		return maintainTemplateId;
	}

	public void setMaintainTemplateId(String maintainTemplateId) {
		this.maintainTemplateId = maintainTemplateId;
	}

	public String getMaintainTemplateName() {
		return maintainTemplateName;
	}

	public void setMaintainTemplateName(String maintainTemplateName) {
		this.maintainTemplateName = maintainTemplateName;
	}

	public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getUseDeptGuid() {
        return useDeptGuid;
    }

    public void setUseDeptGuid(String useDeptGuid) {
        this.useDeptGuid = useDeptGuid == null ? null : useDeptGuid.trim();
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

    public String getModifyUserid() {
        return modifyUserid;
    }

    public void setModifyUserid(String modifyUserid) {
        this.modifyUserid = modifyUserid == null ? null : modifyUserid.trim();
    }

    public Date getModifiyTime() {
        return modifiyTime;
    }

    public void setModifiyTime(Date modifiyTime) {
        this.modifiyTime = modifiyTime;
    }

    public Long getFsort() {
        return fsort;
    }

    public void setFsort(Long fsort) {
        this.fsort = fsort;
    }
}
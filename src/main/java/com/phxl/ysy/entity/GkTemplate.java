package com.phxl.ysy.entity;

import java.util.Date;
import java.util.List;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TB_GK_TEMPLATE", resultName="com.phxl.ysy.dao.GkTemplateMapper.BaseResultMap")
public class GkTemplate {
    private String gkTemplateGuid;

    private String gkTemplateName;

    private Long fOrgId;

    private Long rOrgId;

    private String fStorageGuid;

    private String rStorageGuid;

    private String userId;

    private Date createtime;

    private Date lastmodify;

    private String tfRemark;

    private String ftype;
    
    List<GkTemplateDetail> gkTemplateDetails;
    String searchName;
    String attributeId;
    String tfBrand;

    public String getGkTemplateGuid() {
        return gkTemplateGuid;
    }

    public void setGkTemplateGuid(String gkTemplateGuid) {
        this.gkTemplateGuid = gkTemplateGuid == null ? null : gkTemplateGuid.trim();
    }

    public String getGkTemplateName() {
        return gkTemplateName;
    }

    public void setGkTemplateName(String gkTemplateName) {
        this.gkTemplateName = gkTemplateName == null ? null : gkTemplateName.trim();
    }

    public Long getfOrgId() {
        return fOrgId;
    }

    public void setfOrgId(Long fOrgId) {
        this.fOrgId = fOrgId;
    }

    public Long getrOrgId() {
        return rOrgId;
    }

    public void setrOrgId(Long rOrgId) {
        this.rOrgId = rOrgId;
    }

    public String getfStorageGuid() {
        return fStorageGuid;
    }

    public void setfStorageGuid(String fStorageGuid) {
        this.fStorageGuid = fStorageGuid == null ? null : fStorageGuid.trim();
    }

    public String getrStorageGuid() {
        return rStorageGuid;
    }

    public void setrStorageGuid(String rStorageGuid) {
        this.rStorageGuid = rStorageGuid == null ? null : rStorageGuid.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getLastmodify() {
        return lastmodify;
    }

    public void setLastmodify(Date lastmodify) {
        this.lastmodify = lastmodify;
    }

    public String getTfRemark() {
        return tfRemark;
    }

    public void setTfRemark(String tfRemark) {
        this.tfRemark = tfRemark == null ? null : tfRemark.trim();
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype == null ? null : ftype.trim();
    }

	public List<GkTemplateDetail> getGkTemplateDetails() {
		return gkTemplateDetails;
	}

	public void setGkTemplateDetails(List<GkTemplateDetail> gkTemplateDetails) {
		this.gkTemplateDetails = gkTemplateDetails;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}

	public String getTfBrand() {
		return tfBrand;
	}

	public void setTfBrand(String tfBrand) {
		this.tfBrand = tfBrand;
	}
	
    
}
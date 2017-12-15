package com.phxl.ysy.entity;

import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TB_SOURCE_INFO", resultName="com.phxl.ysy.basicDataModule.dao.SourceInfoMapper.BaseResultMap")
public class SourceInfo {
	
    private String sourceGuid;

    private Long fOrgId;

    private Long rOrgId;
    
    private String rStorageGuid;

    private String fstate;

    private String createUserid;

    private Date createTime;
    
    private String modifyUserid;

	private Date modifyTime;

    private Long kingOrgId;
    
    private String supplierCode;
    
    private String tfRemark;
    
    private String lxr;
    
    private String lxdh;

    
    public String getrStorageGuid() {
        return rStorageGuid;
    }
    
    public void setrStorageGuid(String rStorageGuid) {
        this.rStorageGuid = rStorageGuid;
    }
    
    public String getLxr() {
        return lxr;
    }
    
    public void setLxr(String lxr) {
        this.lxr = lxr;
    }
    
    public String getLxdh() {
        return lxdh;
    }
    
    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getSourceGuid() {
        return sourceGuid;
    }

    public void setSourceGuid(String sourceGuid) {
        this.sourceGuid = sourceGuid;
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

    public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate;
    }

	public Long getKingOrgId() {
		return kingOrgId;
	}

	public void setKingOrgId(Long kingOrgId) {
		this.kingOrgId = kingOrgId;
	}

	public String getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUserid() {
		return modifyUserid;
	}

	public void setModifyUserid(String modifyUserid) {
		this.modifyUserid = modifyUserid;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getTfRemark() {
		return tfRemark;
	}

	public void setTfRemark(String tfRemark) {
		this.tfRemark = tfRemark;
	}
	
}
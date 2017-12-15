package com.phxl.ysy.entity;

import java.util.List;

import com.phxl.core.base.annotation.BaseSql;
import com.phxl.ysy.entity.ConfigInfo;

@BaseSql(tableName="TD_ORG_DEPT", resultName="com.phxl.ysy.dao.DeptMapper.BaseResultMap")
public class Dept {
    private String deptGuid;

    private Long orgId;

    private String deptCode;

    private String deptName;

    private String fqun;

    private String parentDeptGuid;

    private String fflag;

    private String fstate;

    private String deptTypeCode;

    private String deptTypeName;

    private String tfRemark;
    
    private String sourceDept;
    private String auditApply;
    private String auditGzApply;
    private String auditSsApply;
    
    private String defaultAddress;
    
    private List<ConfigInfo> configInfos;

    public String getDeptGuid() {
        return deptGuid;
    }

    public void setDeptGuid(String deptGuid) {
        this.deptGuid = deptGuid;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getFqun() {
        return fqun;
    }

    public void setFqun(String fqun) {
        this.fqun = fqun;
    }

    public String getParentDeptGuid() {
        return parentDeptGuid;
    }

    public void setParentDeptGuid(String parentDeptGuid) {
        this.parentDeptGuid = parentDeptGuid;
    }

    public String getFflag() {
        return fflag;
    }

    public void setFflag(String fflag) {
        this.fflag = fflag;
    }

    public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate;
    }

    public String getDeptTypeCode() {
        return deptTypeCode;
    }

    public void setDeptTypeCode(String deptTypeCode) {
        this.deptTypeCode = deptTypeCode;
    }

    public String getDeptTypeName() {
        return deptTypeName;
    }

    public void setDeptTypeName(String deptTypeName) {
        this.deptTypeName = deptTypeName;
    }

    public String getSourceDept() {
        return sourceDept;
    }

    public void setSourceDept(String sourceDept) {
        this.sourceDept = sourceDept;
    }

    public String getTfRemark() {
        return tfRemark;
    }

    public void setTfRemark(String tfRemark) {
        this.tfRemark = tfRemark;
    }

	public List<ConfigInfo> getConfigInfos() {
		return configInfos;
	}

	public void setConfigInfos(List<ConfigInfo> configInfos) {
		this.configInfos = configInfos;
	}

	public String getAuditApply() {
		return auditApply;
	}

	public void setAuditApply(String auditApply) {
		this.auditApply = auditApply;
	}

	public String getAuditGzApply() {
		return auditGzApply;
	}

	public void setAuditGzApply(String auditGzApply) {
		this.auditGzApply = auditGzApply;
	}

	public String getAuditSsApply() {
		return auditSsApply;
	}

	public void setAuditSsApply(String auditSsApply) {
		this.auditSsApply = auditSsApply;
	}

	public String getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(String defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
    
}
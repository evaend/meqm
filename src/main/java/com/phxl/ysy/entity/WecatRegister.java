package com.phxl.ysy.entity;

import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TD_WECAT_REGISTER", resultName="com.phxl.ysy.dao.WecatRegisterMapper.BaseResultMap")
public class WecatRegister {
    private Long wecatRegisterId;

    private Long orgId;

    private String groupId;

    private Date createTime;
    
    private String deptGuid;

    public Long getWecatRegisterId() {
        return wecatRegisterId;
    }

    public void setWecatRegisterId(Long wecatRegisterId) {
        this.wecatRegisterId = wecatRegisterId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getDeptGuid() {
		return deptGuid;
	}

	public void setDeptGuid(String deptGuid) {
		this.deptGuid = deptGuid;
	}
}
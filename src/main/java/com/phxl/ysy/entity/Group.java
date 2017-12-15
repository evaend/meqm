package com.phxl.ysy.entity;

import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TS_GROUP", resultName="com.phxl.ysy.dao.GroupMapper.BaseResultMap")
public class Group {
    private String groupId;
    
    private String groupName;
    
    private Long orgId;

    private String tfRemark;

    private Date modifyTime;

    private Date createTime;

    private String createUserid;

    private String modifyUserid;

    private String groupType;

    private String groupCode;
    
    private String sourceGroupId;

    /**是否机构核心组*/
    private Boolean isOrgCoreGroup;
    
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTfRemark() {
        return tfRemark;
    }

    public void setTfRemark(String tfRemark) {
        this.tfRemark = tfRemark;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }

    public String getModifyUserid() {
        return modifyUserid;
    }

    public void setModifyUserid(String modifyUserid) {
        this.modifyUserid = modifyUserid;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getSourceGroupId() {
		return sourceGroupId;
	}

	public void setSourceGroupId(String sourceGroupId) {
		this.sourceGroupId = sourceGroupId;
	}

	public Boolean getIsOrgCoreGroup() {
		return isOrgCoreGroup;
	}

	public void setIsOrgCoreGroup(Boolean isOrgCoreGroup) {
		this.isOrgCoreGroup = isOrgCoreGroup;
	}

}
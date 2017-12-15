package com.phxl.ysy.entity;

import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TB_CHANGE_FSTATE", resultName="com.phxl.ysy.dao.ChangeFstateMapper.BaseResultMap")
public class ChangeFstate {
    private String changeGuid;

    private String beforFstate;

    private String afterFstate;

    private String type;

    private String tbId;

    private Date modifyTime;

    private String modifyUserid;

    private String modifyUsername;

    public String getChangeGuid() {
        return changeGuid;
    }

    public void setChangeGuid(String changeGuid) {
        this.changeGuid = changeGuid;
    }

    public String getBeforFstate() {
		return beforFstate;
	}

	public void setBeforFstate(String beforFstate) {
		this.beforFstate = beforFstate;
	}

	public String getAfterFstate() {
		return afterFstate;
	}

	public void setAfterFstate(String afterFstate) {
		this.afterFstate = afterFstate;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTbId() {
        return tbId;
    }

    public void setTbId(String tbId) {
        this.tbId = tbId;
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

    public String getModifyUsername() {
        return modifyUsername;
    }

    public void setModifyUsername(String modifyUsername) {
        this.modifyUsername = modifyUsername;
    }
}
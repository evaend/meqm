package com.phxl.ysy.entity;

import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName = "TD_MAINTAIN_PLAN_DETAIL", resultName = "com.phxl.ysy.dao.MaintainPlanDetailMapper.BaseResultMap")
public class MaintainPlanDetail {
    private String maintainPlanDetailId;

    private String maintainPlanId;

    private String maintainPlanNo;

    private String assetsRecordGuid;

    private String fstate;

    private String equipmentCode;

    private Date maintainDate;

    private Date executeDate;
    
    private String executeUserid;
    
    private String executeUsername;
    
    private Date createTime;

    public String getMaintainPlanDetailId() {
        return maintainPlanDetailId;
    }

    public void setMaintainPlanDetailId(String maintainPlanDetailId) {
        this.maintainPlanDetailId = maintainPlanDetailId == null ? null : maintainPlanDetailId.trim();
    }

    public String getMaintainPlanId() {
        return maintainPlanId;
    }

    public void setMaintainPlanId(String maintainPlanId) {
        this.maintainPlanId = maintainPlanId == null ? null : maintainPlanId.trim();
    }

	public String getMaintainPlanNo() {
        return maintainPlanNo;
    }

    public void setMaintainPlanNo(String maintainPlanNo) {
        this.maintainPlanNo = maintainPlanNo == null ? null : maintainPlanNo.trim();
    }

    public String getAssetsRecordGuid() {
		return assetsRecordGuid;
	}

	public void setAssetsRecordGuid(String assetsRecordGuid) {
		this.assetsRecordGuid = assetsRecordGuid;
	}

	public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate == null ? null : fstate.trim();
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode == null ? null : equipmentCode.trim();
    }

    public Date getMaintainDate() {
        return maintainDate;
    }

    public void setMaintainDate(Date maintainDate) {
        this.maintainDate = maintainDate;
    }

    public Date getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(Date executeDate) {
        this.executeDate = executeDate;
    }

	public String getExecuteUserid() {
		return executeUserid;
	}

	public void setExecuteUserid(String executeUserid) {
		this.executeUserid = executeUserid;
	}

	public String getExecuteUsername() {
		return executeUsername;
	}

	public void setExecuteUsername(String executeUsername) {
		this.executeUsername = executeUsername;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
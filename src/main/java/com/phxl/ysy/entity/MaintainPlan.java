package com.phxl.ysy.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName = "TD_MAINTAIN_PLAN", resultName = "com.phxl.ysy.dao.MaintainPlanMapper.BaseResultMap")
public class MaintainPlan {
    private String maintainPlanId;

    private String maintainPlanName;

    private String useDeptGuid;

    private Date planTime;

    private String planUserid;

    private String planUsername;

    private String tfRemark;

    private String lxr;

    private String lxrId;

    private String lxdh;

    private BigDecimal totalPrice;

    private Long orgId;

    private String loopFlag;

    private String tfCycle;

    private Date maintainDate;

    private Date endMaintainDate;

    private String maintainType;

    private String clinicalRisk;

    private Short advancePlan;

    private String maintainTypeId;
    
    private String assetsRecordGuid;
    
    private String equipmentCode;
    
    public String getMaintainPlanId() {
		return maintainPlanId;
	}

	public void setMaintainPlanId(String maintainPlanId) {
		this.maintainPlanId = maintainPlanId;
	}

    public String getMaintainPlanName() {
        return maintainPlanName;
    }

    public void setMaintainPlanName(String maintainPlanName) {
        this.maintainPlanName = maintainPlanName == null ? null : maintainPlanName.trim();
    }

    public String getUseDeptGuid() {
        return useDeptGuid;
    }

    public void setUseDeptGuid(String useDeptGuid) {
        this.useDeptGuid = useDeptGuid == null ? null : useDeptGuid.trim();
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public String getPlanUserid() {
        return planUserid;
    }

    public void setPlanUserid(String planUserid) {
        this.planUserid = planUserid == null ? null : planUserid.trim();
    }

    public String getPlanUsername() {
        return planUsername;
    }

    public void setPlanUsername(String planUsername) {
        this.planUsername = planUsername == null ? null : planUsername.trim();
    }

    public String getTfRemark() {
        return tfRemark;
    }

    public void setTfRemark(String tfRemark) {
        this.tfRemark = tfRemark == null ? null : tfRemark.trim();
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr == null ? null : lxr.trim();
    }

    public String getLxrId() {
        return lxrId;
    }

    public void setLxrId(String lxrId) {
        this.lxrId = lxrId == null ? null : lxrId.trim();
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh == null ? null : lxdh.trim();
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getLoopFlag() {
        return loopFlag;
    }

    public void setLoopFlag(String loopFlag) {
        this.loopFlag = loopFlag == null ? null : loopFlag.trim();
    }

    public String getTfCycle() {
        return tfCycle;
    }

    public void setTfCycle(String tfCycle) {
        this.tfCycle = tfCycle == null ? null : tfCycle.trim();
    }

    public Date getMaintainDate() {
        return maintainDate;
    }

    public void setMaintainDate(Date maintainDate) {
        this.maintainDate = maintainDate;
    }

    public Date getEndMaintainDate() {
        return endMaintainDate;
    }

    public void setEndMaintainDate(Date endMaintainDate) {
        this.endMaintainDate = endMaintainDate;
    }

    public String getMaintainType() {
        return maintainType;
    }

    public void setMaintainType(String maintainType) {
        this.maintainType = maintainType == null ? null : maintainType.trim();
    }

    public String getClinicalRisk() {
        return clinicalRisk;
    }

    public void setClinicalRisk(String clinicalRisk) {
        this.clinicalRisk = clinicalRisk == null ? null : clinicalRisk.trim();
    }

    public Short getAdvancePlan() {
        return advancePlan;
    }

    public void setAdvancePlan(Short advancePlan) {
        this.advancePlan = advancePlan;
    }

    public String getMaintainTypeId() {
        return maintainTypeId;
    }

    public void setMaintainTypeId(String maintainTypeId) {
        this.maintainTypeId = maintainTypeId == null ? null : maintainTypeId.trim();
    }

	public String getAssetsRecordGuid() {
		return assetsRecordGuid;
	}

	public void setAssetsRecordGuid(String assetsRecordGuid) {
		this.assetsRecordGuid = assetsRecordGuid;
	}

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
}
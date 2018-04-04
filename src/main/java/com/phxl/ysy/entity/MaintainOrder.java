package com.phxl.ysy.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TD_MAINTAIN_ORDER", resultName="com.phxl.ysy.dao.MaintainOrderMapper.BaseResultMap")
public class MaintainOrder {
    private String rrpairOrder;

    private String assetsRecordGuid;

    private String equipmentCode;

    private String uOrg;

    private String useDeptGuid;

    private Date lastMaintainDate;

    private Date maintainDate;

    private String engineerUserid;

    private String engineerName;

    private BigDecimal maintainPrice;

    private String maintainMethod;

    private String equipmentFstate;

    private String maintainReason;

    private String maintainCycle;

    private String remark;

    private String tfAccessory;

    private String maintainType;

    private String clinicalRisk;

    private Date nextMaintainDate;

    private Date endMaintainDate;

    private String fstate;

    private String maintainNo;

    private String maintainGuid;

    private String createUserid;

    private Date createTime;

    private String modifyUserid;

    private Date modifiyTime;
    
    private List<String> tfAccessoryList;
    
    private List<MaintainOrderDetail> maintainOrderDetailList;

    public String getRrpairOrder() {
        return rrpairOrder;
    }

    public void setRrpairOrder(String rrpairOrder) {
        this.rrpairOrder = rrpairOrder == null ? null : rrpairOrder.trim();
    }

    public String getAssetsRecordGuid() {
        return assetsRecordGuid;
    }

    public void setAssetsRecordGuid(String assetsRecordGuid) {
        this.assetsRecordGuid = assetsRecordGuid == null ? null : assetsRecordGuid.trim();
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode == null ? null : equipmentCode.trim();
    }

    public String getuOrg() {
        return uOrg;
    }

    public void setuOrg(String uOrg) {
        this.uOrg = uOrg == null ? null : uOrg.trim();
    }

    public String getUseDeptGuid() {
        return useDeptGuid;
    }

    public void setUseDeptGuid(String useDeptGuid) {
        this.useDeptGuid = useDeptGuid == null ? null : useDeptGuid.trim();
    }

    public Date getLastMaintainDate() {
        return lastMaintainDate;
    }

    public void setLastMaintainDate(Date lastMaintainDate) {
        this.lastMaintainDate = lastMaintainDate;
    }

    public Date getMaintainDate() {
        return maintainDate;
    }

    public void setMaintainDate(Date maintainDate) {
        this.maintainDate = maintainDate;
    }

    public String getEngineerUserid() {
        return engineerUserid;
    }

    public void setEngineerUserid(String engineerUserid) {
        this.engineerUserid = engineerUserid == null ? null : engineerUserid.trim();
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName == null ? null : engineerName.trim();
    }

    public BigDecimal getMaintainPrice() {
        return maintainPrice;
    }

    public void setMaintainPrice(BigDecimal maintainPrice) {
        this.maintainPrice = maintainPrice;
    }

    public String getMaintainMethod() {
        return maintainMethod;
    }

    public void setMaintainMethod(String maintainMethod) {
        this.maintainMethod = maintainMethod == null ? null : maintainMethod.trim();
    }

    public String getEquipmentFstate() {
        return equipmentFstate;
    }

    public void setEquipmentFstate(String equipmentFstate) {
        this.equipmentFstate = equipmentFstate == null ? null : equipmentFstate.trim();
    }

    public String getMaintainReason() {
        return maintainReason;
    }

    public void setMaintainReason(String maintainReason) {
        this.maintainReason = maintainReason == null ? null : maintainReason.trim();
    }

    public String getMaintainCycle() {
        return maintainCycle;
    }

    public void setMaintainCycle(String maintainCycle) {
        this.maintainCycle = maintainCycle == null ? null : maintainCycle.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getTfAccessory() {
        return tfAccessory;
    }

    public void setTfAccessory(String tfAccessory) {
        this.tfAccessory = tfAccessory == null ? null : tfAccessory.trim();
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

    public Date getNextMaintainDate() {
        return nextMaintainDate;
    }

    public void setNextMaintainDate(Date nextMaintainDate) {
        this.nextMaintainDate = nextMaintainDate;
    }

    public Date getEndMaintainDate() {
        return endMaintainDate;
    }

    public void setEndMaintainDate(Date endMaintainDate) {
        this.endMaintainDate = endMaintainDate;
    }

    public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate == null ? null : fstate.trim();
    }

    public String getMaintainNo() {
        return maintainNo;
    }

    public void setMaintainNo(String maintainNo) {
        this.maintainNo = maintainNo == null ? null : maintainNo.trim();
    }

    public String getMaintainGuid() {
        return maintainGuid;
    }

    public void setMaintainGuid(String maintainGuid) {
        this.maintainGuid = maintainGuid == null ? null : maintainGuid.trim();
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

	public List<String> getTfAccessoryList() {
		return tfAccessoryList;
	}

	public void setTfAccessoryList(List<String> tfAccessoryList) {
		this.tfAccessoryList = tfAccessoryList;
	}

	public List<MaintainOrderDetail> getMaintainOrderDetailList() {
		return maintainOrderDetailList;
	}

	public void setMaintainOrderDetailList(
			List<MaintainOrderDetail> maintainOrderDetailList) {
		this.maintainOrderDetailList = maintainOrderDetailList;
	}

}
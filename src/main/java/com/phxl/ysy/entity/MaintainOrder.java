package com.phxl.ysy.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MaintainOrder {
    private String rrpairOrder;

    private String assetsRecord;

    private String equipmentCode;

    private String uOrg;

    private String useDeptCode;

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

    public String getRrpairOrder() {
        return rrpairOrder;
    }

    public void setRrpairOrder(String rrpairOrder) {
        this.rrpairOrder = rrpairOrder == null ? null : rrpairOrder.trim();
    }

    public String getAssetsRecord() {
        return assetsRecord;
    }

    public void setAssetsRecord(String assetsRecord) {
        this.assetsRecord = assetsRecord == null ? null : assetsRecord.trim();
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

    public String getUseDeptCode() {
        return useDeptCode;
    }

    public void setUseDeptCode(String useDeptCode) {
        this.useDeptCode = useDeptCode == null ? null : useDeptCode.trim();
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
}
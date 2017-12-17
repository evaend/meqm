package com.phxl.ysy.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RrpairOrder {
    private String rrpairOrder;

    private String assetsRecord;

    private String equipmentCode;

    private String uOrg;

    private String useDeptCode;

    private Date acctDate;

    private Date guaranteeDate;

    private String orderType;

    private Date modifyTime;

    private String add;

    private String orderFstate;

    private String urgentFlag;

    private String guaranteeFlag;

    private String rrpairType;

    private String labelGuid;

    private String spare;

    private String rrpairFlag;

    private String rrpairUserid;

    private String rrpairUsername;

    private String rrpairPhone;

    private Date completTime;

    private Date rrpairDate;

    private String inRrpairUserid;

    private String inRrpairUsername;

    private String outRrpairUsername;

    private String outRrpairPhone;

    private String faultDescribe;

    private String faultAccessory;

    private String faultAccessoryAudio;

    private String faultWords;

    private String repairContentType;

    private String repairContentTyp;

    private BigDecimal quotedPrice;

    private BigDecimal actualPrice;

    private String costDetail;

    private String evaluate;

    private String tfRemark;

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

    public Date getAcctDate() {
        return acctDate;
    }

    public void setAcctDate(Date acctDate) {
        this.acctDate = acctDate;
    }

    public Date getGuaranteeDate() {
        return guaranteeDate;
    }

    public void setGuaranteeDate(Date guaranteeDate) {
        this.guaranteeDate = guaranteeDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add == null ? null : add.trim();
    }

    public String getOrderFstate() {
        return orderFstate;
    }

    public void setOrderFstate(String orderFstate) {
        this.orderFstate = orderFstate == null ? null : orderFstate.trim();
    }

    public String getUrgentFlag() {
        return urgentFlag;
    }

    public void setUrgentFlag(String urgentFlag) {
        this.urgentFlag = urgentFlag == null ? null : urgentFlag.trim();
    }

    public String getGuaranteeFlag() {
        return guaranteeFlag;
    }

    public void setGuaranteeFlag(String guaranteeFlag) {
        this.guaranteeFlag = guaranteeFlag == null ? null : guaranteeFlag.trim();
    }

    public String getRrpairType() {
        return rrpairType;
    }

    public void setRrpairType(String rrpairType) {
        this.rrpairType = rrpairType == null ? null : rrpairType.trim();
    }

    public String getLabelGuid() {
        return labelGuid;
    }

    public void setLabelGuid(String labelGuid) {
        this.labelGuid = labelGuid == null ? null : labelGuid.trim();
    }

    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare == null ? null : spare.trim();
    }

    public String getRrpairFlag() {
        return rrpairFlag;
    }

    public void setRrpairFlag(String rrpairFlag) {
        this.rrpairFlag = rrpairFlag == null ? null : rrpairFlag.trim();
    }

    public String getRrpairUserid() {
        return rrpairUserid;
    }

    public void setRrpairUserid(String rrpairUserid) {
        this.rrpairUserid = rrpairUserid == null ? null : rrpairUserid.trim();
    }

    public String getRrpairUsername() {
        return rrpairUsername;
    }

    public void setRrpairUsername(String rrpairUsername) {
        this.rrpairUsername = rrpairUsername == null ? null : rrpairUsername.trim();
    }

    public String getRrpairPhone() {
        return rrpairPhone;
    }

    public void setRrpairPhone(String rrpairPhone) {
        this.rrpairPhone = rrpairPhone == null ? null : rrpairPhone.trim();
    }

    public Date getCompletTime() {
        return completTime;
    }

    public void setCompletTime(Date completTime) {
        this.completTime = completTime;
    }

    public Date getRrpairDate() {
        return rrpairDate;
    }

    public void setRrpairDate(Date rrpairDate) {
        this.rrpairDate = rrpairDate;
    }

    public String getInRrpairUserid() {
        return inRrpairUserid;
    }

    public void setInRrpairUserid(String inRrpairUserid) {
        this.inRrpairUserid = inRrpairUserid == null ? null : inRrpairUserid.trim();
    }

    public String getInRrpairUsername() {
        return inRrpairUsername;
    }

    public void setInRrpairUsername(String inRrpairUsername) {
        this.inRrpairUsername = inRrpairUsername == null ? null : inRrpairUsername.trim();
    }

    public String getOutRrpairUsername() {
        return outRrpairUsername;
    }

    public void setOutRrpairUsername(String outRrpairUsername) {
        this.outRrpairUsername = outRrpairUsername == null ? null : outRrpairUsername.trim();
    }

    public String getOutRrpairPhone() {
        return outRrpairPhone;
    }

    public void setOutRrpairPhone(String outRrpairPhone) {
        this.outRrpairPhone = outRrpairPhone == null ? null : outRrpairPhone.trim();
    }

    public String getFaultDescribe() {
        return faultDescribe;
    }

    public void setFaultDescribe(String faultDescribe) {
        this.faultDescribe = faultDescribe == null ? null : faultDescribe.trim();
    }

    public String getFaultAccessory() {
        return faultAccessory;
    }

    public void setFaultAccessory(String faultAccessory) {
        this.faultAccessory = faultAccessory == null ? null : faultAccessory.trim();
    }

    public String getFaultAccessoryAudio() {
        return faultAccessoryAudio;
    }

    public void setFaultAccessoryAudio(String faultAccessoryAudio) {
        this.faultAccessoryAudio = faultAccessoryAudio == null ? null : faultAccessoryAudio.trim();
    }

    public String getFaultWords() {
        return faultWords;
    }

    public void setFaultWords(String faultWords) {
        this.faultWords = faultWords == null ? null : faultWords.trim();
    }

    public String getRepairContentType() {
        return repairContentType;
    }

    public void setRepairContentType(String repairContentType) {
        this.repairContentType = repairContentType == null ? null : repairContentType.trim();
    }

    public String getRepairContentTyp() {
        return repairContentTyp;
    }

    public void setRepairContentTyp(String repairContentTyp) {
        this.repairContentTyp = repairContentTyp == null ? null : repairContentTyp.trim();
    }

    public BigDecimal getQuotedPrice() {
        return quotedPrice;
    }

    public void setQuotedPrice(BigDecimal quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getCostDetail() {
        return costDetail;
    }

    public void setCostDetail(String costDetail) {
        this.costDetail = costDetail == null ? null : costDetail.trim();
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate == null ? null : evaluate.trim();
    }

    public String getTfRemark() {
        return tfRemark;
    }

    public void setTfRemark(String tfRemark) {
        this.tfRemark = tfRemark == null ? null : tfRemark.trim();
    }
}
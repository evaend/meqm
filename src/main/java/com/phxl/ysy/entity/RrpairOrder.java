package com.phxl.ysy.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TD_RRPAIR_ORDER", resultName="com.phxl.ysy.dao.RrpairOrderMapper.BaseResultMap")
public class RrpairOrder {

    private String rrpairOrderGuid;

    private String assetsRecord;

    private String equipmentCode;

    private String uOrg;

    private String useDeptCode;

    private Date acctDate;

    private Date guaranteeDate;

    private String orderType;

    private Date modifyTime;

    private String address;

    private String orderFstate;

    private String equipmentRrpairRecordGuid;

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

    private String faultAccessoryAudio;

    private String faultWords;

    private String repairContentType;

    private String repairContentTyp;

    private BigDecimal quotedPrice;

    private BigDecimal actualPrice;

    private String costDetail;

    private String tfComment;

    private String tfRemark;

    private String faultAccessory;

    private String tfAccessory;

    private String rrpairOrderNo;

    private String rrpairSend;

    private String callDeptCode;

    private String outOrg;

    private String failCause;

    private Date createDate;
    
    private String callDeptName;
    
    private Date callTime;
    
    private String repairResult;
    
    private String tfRemarkWx;
    
    private String tfRemarkZp;
    
    private String refuseCause;
    
    private String otherCause;
    
    private String tfRemarkJj;
    
    private String tfRemarkGb;
    
    private String followupTreatment;
    
    private String offCause;
    
    private String inRrpairPhone;
    
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getOrderFstate() {
        return orderFstate;
    }

    public void setOrderFstate(String orderFstate) {
        this.orderFstate = orderFstate == null ? null : orderFstate.trim();
    }

    public String getEquipmentRrpairRecordGuid() {
        return equipmentRrpairRecordGuid;
    }

    public void setEquipmentRrpairRecordGuid(String equipmentRrpairRecordGuid) {
        this.equipmentRrpairRecordGuid = equipmentRrpairRecordGuid == null ? null : equipmentRrpairRecordGuid.trim();
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

    public String getTfComment() {
        return tfComment;
    }

    public void setTfComment(String tfComment) {
        this.tfComment = tfComment == null ? null : tfComment.trim();
    }

    public String getTfRemark() {
        return tfRemark;
    }

    public void setTfRemark(String tfRemark) {
        this.tfRemark = tfRemark == null ? null : tfRemark.trim();
    }

    public String getRrpairOrderGuid() {
        return rrpairOrderGuid;
    }

    public void setRrpairOrderGuid(String rrpairOrderGuid) {
        this.rrpairOrderGuid = rrpairOrderGuid == null ? null : rrpairOrderGuid.trim();
    }

    public String getFaultAccessory() {
        return faultAccessory;
    }

    public void setFaultAccessory(String faultAccessory) {
        this.faultAccessory = faultAccessory == null ? null : faultAccessory.trim();
    }

    public String getTfAccessory() {
        return tfAccessory;
    }

    public void setTfAccessory(String tfAccessory) {
        this.tfAccessory = tfAccessory == null ? null : tfAccessory.trim();
    }

    public String getRrpairOrderNo() {
        return rrpairOrderNo;
    }

    public void setRrpairOrderNo(String rrpairOrderNo) {
        this.rrpairOrderNo = rrpairOrderNo == null ? null : rrpairOrderNo.trim();
    }

    public String getRrpairSend() {
        return rrpairSend;
    }

    public void setRrpairSend(String rrpairSend) {
        this.rrpairSend = rrpairSend == null ? null : rrpairSend.trim();
    }

    public String getOutOrg() {
        return outOrg;
    }

    public void setOutOrg(String outOrg) {
        this.outOrg = outOrg == null ? null : outOrg.trim();
    }

    public String getFailCause() {
        return failCause;
    }

    public void setFailCause(String failCause) {
        this.failCause = failCause == null ? null : failCause.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public String getCallDeptName() {
		return callDeptName;
	}

	public void setCallDeptName(String callDeptName) {
		this.callDeptName = callDeptName;
	}

	public Date getCallTime() {
		return callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	public String getRepairResult() {
		return repairResult;
	}

	public void setRepairResult(String repairResult) {
		this.repairResult = repairResult;
	}

	public String getTfRemarkWx() {
		return tfRemarkWx;
	}

	public void setTfRemarkWx(String tfRemarkWx) {
		this.tfRemarkWx = tfRemarkWx;
	}

	public String getTfRemarkZp() {
		return tfRemarkZp;
	}

	public void setTfRemarkZp(String tfRemarkZp) {
		this.tfRemarkZp = tfRemarkZp;
	}

	public String getRefuseCause() {
		return refuseCause;
	}

	public void setRefuseCause(String refuseCause) {
		this.refuseCause = refuseCause;
	}

	public String getOtherCause() {
		return otherCause;
	}

	public void setOtherCause(String otherCause) {
		this.otherCause = otherCause;
	}

	public String getTfRemarkJj() {
		return tfRemarkJj;
	}

	public void setTfRemarkJj(String tfRemarkJj) {
		this.tfRemarkJj = tfRemarkJj;
	}

	public String getTfRemarkGb() {
		return tfRemarkGb;
	}

	public void setTfRemarkGb(String tfRemarkGb) {
		this.tfRemarkGb = tfRemarkGb;
	}

	public String getFollowupTreatment() {
		return followupTreatment;
	}

	public void setFollowupTreatment(String followupTreatment) {
		this.followupTreatment = followupTreatment;
	}

	public String getOffCause() {
		return offCause;
	}

	public void setOffCause(String offCause) {
		this.offCause = offCause;
	}

	public String getInRrpairPhone() {
		return inRrpairPhone;
	}

	public void setInRrpairPhone(String inRrpairPhone) {
		this.inRrpairPhone = inRrpairPhone;
	}

	public String getCallDeptCode() {
		return callDeptCode;
	}

	public void setCallDeptCode(String callDeptCode) {
		this.callDeptCode = callDeptCode;
	}
}
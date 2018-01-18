package com.phxl.ysy.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RrpairFittingUse {
    private String rrpairFittingUseGuid;

    private String rrpairOrderGuid;

    private Integer seqNum;

    private String acceName;

    private String acceType;

    private BigDecimal acceNum;

    private BigDecimal unitPrice;

    private Integer returnNum;

    private String tfRemark;

    private Date createDate;

    private String acceFmodel;

    public String getRrpairFittingUseGuid() {
        return rrpairFittingUseGuid;
    }

    public void setRrpairFittingUseGuid(String rrpairFittingUseGuid) {
        this.rrpairFittingUseGuid = rrpairFittingUseGuid == null ? null : rrpairFittingUseGuid.trim();
    }

    public String getRrpairOrderGuid() {
        return rrpairOrderGuid;
    }

    public void setRrpairOrderGuid(String rrpairOrderGuid) {
        this.rrpairOrderGuid = rrpairOrderGuid == null ? null : rrpairOrderGuid.trim();
    }

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    public String getAcceName() {
        return acceName;
    }

    public void setAcceName(String acceName) {
        this.acceName = acceName == null ? null : acceName.trim();
    }

    public String getAcceType() {
        return acceType;
    }

    public void setAcceType(String acceType) {
        this.acceType = acceType == null ? null : acceType.trim();
    }

    public BigDecimal getAcceNum() {
        return acceNum;
    }

    public void setAcceNum(BigDecimal acceNum) {
        this.acceNum = acceNum;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(Integer returnNum) {
        this.returnNum = returnNum;
    }

    public String getTfRemark() {
        return tfRemark;
    }

    public void setTfRemark(String tfRemark) {
        this.tfRemark = tfRemark == null ? null : tfRemark.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAcceFmodel() {
        return acceFmodel;
    }

    public void setAcceFmodel(String acceFmodel) {
        this.acceFmodel = acceFmodel == null ? null : acceFmodel.trim();
    }
}
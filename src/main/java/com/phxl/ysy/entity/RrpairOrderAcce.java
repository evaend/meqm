package com.phxl.ysy.entity;

import java.util.Date;

public class RrpairOrderAcce {
    private String rrpairOrderAcce;

    private String rrpairOrder;

    private String rrAcceUserid;

    private String rrAcceUsername;

    private String rrAccePhone;

    private String deptCode;

    private Date rrAcceDate;

    private String rrAcceFstate;

    private String tfRemark;

    private Date createDate;

    public String getRrpairOrderAcce() {
        return rrpairOrderAcce;
    }

    public void setRrpairOrderAcce(String rrpairOrderAcce) {
        this.rrpairOrderAcce = rrpairOrderAcce == null ? null : rrpairOrderAcce.trim();
    }

    public String getRrpairOrder() {
        return rrpairOrder;
    }

    public void setRrpairOrder(String rrpairOrder) {
        this.rrpairOrder = rrpairOrder == null ? null : rrpairOrder.trim();
    }

    public String getRrAcceUserid() {
        return rrAcceUserid;
    }

    public void setRrAcceUserid(String rrAcceUserid) {
        this.rrAcceUserid = rrAcceUserid == null ? null : rrAcceUserid.trim();
    }

    public String getRrAcceUsername() {
        return rrAcceUsername;
    }

    public void setRrAcceUsername(String rrAcceUsername) {
        this.rrAcceUsername = rrAcceUsername == null ? null : rrAcceUsername.trim();
    }

    public String getRrAccePhone() {
        return rrAccePhone;
    }

    public void setRrAccePhone(String rrAccePhone) {
        this.rrAccePhone = rrAccePhone == null ? null : rrAccePhone.trim();
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    public Date getRrAcceDate() {
        return rrAcceDate;
    }

    public void setRrAcceDate(Date rrAcceDate) {
        this.rrAcceDate = rrAcceDate;
    }

    public String getRrAcceFstate() {
        return rrAcceFstate;
    }

    public void setRrAcceFstate(String rrAcceFstate) {
        this.rrAcceFstate = rrAcceFstate == null ? null : rrAcceFstate.trim();
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
}
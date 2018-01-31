package com.phxl.ysy.entity;

import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TB_RRPAIR_ORDER_ACCE", resultName="com.phxl.ysy.dao.RrpairOrderAcceMapper.BaseResultMap")
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
    
    private String notCause;
    
    private String evaluate;

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

	public String getNotCause() {
		return notCause;
	}

	public void setNotCause(String notCause) {
		this.notCause = notCause;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
}
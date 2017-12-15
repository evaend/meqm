package com.phxl.ysy.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TB_INVOICE", resultName="com.phxl.ysy.dao.InvoiceMapper.BaseResultMap")
public class Invoice {
    private String invoiceId;

    private String invoiceNo;

    private Long rOrgId;

    private String rStorageGuid;

    private Long fOrgId;

    private String fStorageGuid;

    private Date invoiceDate;

    private BigDecimal accountPayed;

    private String acctFlag;

    private String fstate;

    private String acctUserid;

    private Date acctDate;

    private String createUserid;

    private Date createTime;

    private Date modifyTime;

    private String invoiceQrcode;

    private String invoiceCode;

    private String cwno;

    private String ysr;

    private Date yssj;

    private String acctYh;
    
    private String rejecReason;

    
    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Long getrOrgId() {
        return rOrgId;
    }

    public void setrOrgId(Long rOrgId) {
        this.rOrgId = rOrgId;
    }

    public String getrStorageGuid() {
        return rStorageGuid;
    }

    public void setrStorageGuid(String rStorageGuid) {
        this.rStorageGuid = rStorageGuid;
    }

    public Long getfOrgId() {
        return fOrgId;
    }

    public void setfOrgId(Long fOrgId) {
        this.fOrgId = fOrgId;
    }

    public String getfStorageGuid() {
        return fStorageGuid;
    }

    public void setfStorageGuid(String fStorageGuid) {
        this.fStorageGuid = fStorageGuid;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public BigDecimal getAccountPayed() {
        return accountPayed;
    }

    public void setAccountPayed(BigDecimal accountPayed) {
        this.accountPayed = accountPayed;
    }

    public String getAcctFlag() {
        return acctFlag;
    }

    public void setAcctFlag(String acctFlag) {
        this.acctFlag = acctFlag;
    }

    public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate;
    }

    public String getAcctUserid() {
        return acctUserid;
    }

    public void setAcctUserid(String acctUserid) {
        this.acctUserid = acctUserid;
    }

    public Date getAcctDate() {
        return acctDate;
    }

    public void setAcctDate(Date acctDate) {
        this.acctDate = acctDate;
    }

    public String getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getInvoiceQrcode() {
        return invoiceQrcode;
    }

    public void setInvoiceQrcode(String invoiceQrcode) {
        this.invoiceQrcode = invoiceQrcode;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getCwno() {
        return cwno;
    }

    public void setCwno(String cwno) {
        this.cwno = cwno;
    }

    public String getYsr() {
        return ysr;
    }

    public void setYsr(String ysr) {
        this.ysr = ysr;
    }

    public Date getYssj() {
        return yssj;
    }

    public void setYssj(Date yssj) {
        this.yssj = yssj;
    }

    public String getAcctYh() {
        return acctYh;
    }

    public void setAcctYh(String acctYh) {
        this.acctYh = acctYh;
    }
    
    public String getRejecReason() {
        return rejecReason;
    }

    public void setRejecReason(String rejecReason) {
        this.rejecReason = rejecReason;
    }
}
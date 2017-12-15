package com.phxl.ysy.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName = "TD_ORG_INFO", resultName = "com.phxl.ysy.dao.OrgInfoMapper.BaseResultMap")
public class OrgInfo {
    private Long orgId;

    private String orgCode;

    private String orgName;

    private String fqun;

    private String orgAlias;

    private String tfProvince;

    private String tfCity;

    private String tfDistrict;

    private String tfAddress;

    private String tfBank;

    private String bankAccount;

    private String website;

    private String fstate;

    private String tfLogo;

    private String tfProfile;

    private String orgType;

    private String createUserid;

    private Date createTime;

    private Date modifyTime;

    private String tfRemark;

    private Long parentOrgid;

    private String flag;

    private String lxr;

    private String lxdh;

    private String orgStatus;

    private String tfAccessory;

    private String badNewsFirst;

    private String badNewsSecond;

    private String vipLevel;
    
    private String legalMan;
    
    /*医疗机构信息*/   
    private String taxNo;

    private String hospitalLevel;

    private String hospitalProperty;

    private Long fsort;
    
    /*供应商信息*/
    private String registeredCapital;

    private String incomeCapital;

    private String corporationType;

    private BigDecimal rmbAmount;
    
    /*附件信息*/
    private String yyzzfirstTime;

    private String yyzzlastTime;
    
    private String zyxkfirstTime;

    private String zyxklastTime;
    
    private String jyxkfirstTime;

    private String jyxklastTime;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getFqun() {
        return fqun;
    }

    public void setFqun(String fqun) {
        this.fqun = fqun == null ? null : fqun.trim();
    }

    public String getOrgAlias() {
        return orgAlias;
    }

    public void setOrgAlias(String orgAlias) {
        this.orgAlias = orgAlias == null ? null : orgAlias.trim();
    }

    public String getTfProvince() {
        return tfProvince;
    }

    public void setTfProvince(String tfProvince) {
        this.tfProvince = tfProvince == null ? null : tfProvince.trim();
    }

    public String getTfCity() {
        return tfCity;
    }

    public void setTfCity(String tfCity) {
        this.tfCity = tfCity == null ? null : tfCity.trim();
    }

    public String getTfDistrict() {
        return tfDistrict;
    }

    public void setTfDistrict(String tfDistrict) {
        this.tfDistrict = tfDistrict == null ? null : tfDistrict.trim();
    }

    public String getTfAddress() {
        return tfAddress;
    }

    public void setTfAddress(String tfAddress) {
        this.tfAddress = tfAddress == null ? null : tfAddress.trim();
    }

    public String getTfBank() {
        return tfBank;
    }

    public void setTfBank(String tfBank) {
        this.tfBank = tfBank == null ? null : tfBank.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate == null ? null : fstate.trim();
    }

    public String getTfLogo() {
        return tfLogo;
    }

    public void setTfLogo(String tfLogo) {
        this.tfLogo = tfLogo == null ? null : tfLogo.trim();
    }

    public String getTfProfile() {
        return tfProfile;
    }

    public void setTfProfile(String tfProfile) {
        this.tfProfile = tfProfile == null ? null : tfProfile.trim();
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType == null ? null : orgType.trim();
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getTfRemark() {
        return tfRemark;
    }

    public void setTfRemark(String tfRemark) {
        this.tfRemark = tfRemark == null ? null : tfRemark.trim();
    }

    public Long getParentOrgid() {
        return parentOrgid;
    }

    public void setParentOrgid(Long parentOrgid) {
        this.parentOrgid = parentOrgid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr == null ? null : lxr.trim();
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh == null ? null : lxdh.trim();
    }

    public String getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(String orgStatus) {
        this.orgStatus = orgStatus == null ? null : orgStatus.trim();
    }

    public String getTfAccessory() {
        return tfAccessory;
    }

    public void setTfAccessory(String tfAccessory) {
        this.tfAccessory = tfAccessory == null ? null : tfAccessory.trim();
    }

    public String getBadNewsFirst() {
        return badNewsFirst;
    }

    public void setBadNewsFirst(String badNewsFirst) {
        this.badNewsFirst = badNewsFirst == null ? null : badNewsFirst.trim();
    }

    public String getBadNewsSecond() {
        return badNewsSecond;
    }

    public void setBadNewsSecond(String badNewsSecond) {
        this.badNewsSecond = badNewsSecond == null ? null : badNewsSecond.trim();
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel == null ? null : vipLevel.trim();
    }
    
    public String getLegalMan() {
        return legalMan;
    }

    public void setLegalMan(String legalMan) {
        this.legalMan = legalMan;
    }
    
    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getHospitalLevel() {
        return hospitalLevel;
    }

    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    public String getHospitalProperty() {
        return hospitalProperty;
    }

    public void setHospitalProperty(String hospitalProperty) {
        this.hospitalProperty = hospitalProperty;
    }

    public Long getFsort() {
        return fsort;
    }

    public void setFsort(Long fsort) {
        this.fsort = fsort;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getIncomeCapital() {
        return incomeCapital;
    }

    public void setIncomeCapital(String incomeCapital) {
        this.incomeCapital = incomeCapital;
    }

    public String getCorporationType() {
        return corporationType;
    }

    public void setCorporationType(String corporationType) {
        this.corporationType = corporationType;
    }

    public BigDecimal getRmbAmount() {
        return rmbAmount;
    }

    public void setRmbAmount(BigDecimal rmbAmount) {
        this.rmbAmount = rmbAmount;
    }

    public String getYyzzfirstTime() {
        return yyzzfirstTime;
    }

    public void setYyzzfirstTime(String yyzzfirstTime) {
        this.yyzzfirstTime = yyzzfirstTime;
    }

    public String getYyzzlastTime() {
        return yyzzlastTime;
    }

    public void setYyzzlastTime(String yyzzlastTime) {
        this.yyzzlastTime = yyzzlastTime;
    }

    public String getZyxkfirstTime() {
        return zyxkfirstTime;
    }

    public void setZyxkfirstTime(String zyxkfirstTime) {
        this.zyxkfirstTime = zyxkfirstTime;
    }

    public String getZyxklastTime() {
        return zyxklastTime;
    }

    public void setZyxklastTime(String zyxklastTime) {
        this.zyxklastTime = zyxklastTime;
    }

    public String getJyxkfirstTime() {
        return jyxkfirstTime;
    }

    public void setJyxkfirstTime(String jyxkfirstTime) {
        this.jyxkfirstTime = jyxkfirstTime;
    }

    public String getJyxklastTime() {
        return jyxklastTime;
    }

    public void setJyxklastTime(String jyxklastTime) {
        this.jyxklastTime = jyxklastTime;
    }
    
}
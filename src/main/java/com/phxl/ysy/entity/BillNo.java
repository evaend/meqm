package com.phxl.ysy.entity;

public class BillNo {
    private String billGuid;

    private Long orgId;

    private String storageGuid;

    private String billName;

    private String billPrefix;

    private String yearMonth;

    private Integer nowId;

    private Short idLen;

    public String getBillGuid() {
        return billGuid;
    }

    public void setBillGuid(String billGuid) {
        this.billGuid = billGuid == null ? null : billGuid.trim();
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getStorageGuid() {
        return storageGuid;
    }

    public void setStorageGuid(String storageGuid) {
        this.storageGuid = storageGuid == null ? null : storageGuid.trim();
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName == null ? null : billName.trim();
    }

    public String getBillPrefix() {
        return billPrefix;
    }

    public void setBillPrefix(String billPrefix) {
        this.billPrefix = billPrefix == null ? null : billPrefix.trim();
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth == null ? null : yearMonth.trim();
    }

    public Integer getNowId() {
        return nowId;
    }

    public void setNowId(Integer nowId) {
        this.nowId = nowId;
    }

    public Short getIdLen() {
        return idLen;
    }

    public void setIdLen(Short idLen) {
        this.idLen = idLen;
    }
}
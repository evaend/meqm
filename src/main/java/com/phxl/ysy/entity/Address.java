package com.phxl.ysy.entity;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TD_ADDRESSES", resultName="com.phxl.ysy.dao.AddressMapper.BaseResultMap")
public class Address {
    private String addrGuid;

    private String tfProvince;

    private String tfCity;

    private String tfDistrict;

    private String tfStreet;

    private String tfAddress;

    private String addrTable;

    private String tfCloGuid;

    private String isDefault;

    private String linkman;

    private String linktel;

    public String getAddrGuid() {
        return addrGuid;
    }

    public void setAddrGuid(String addrGuid) {
        this.addrGuid = addrGuid;
    }

    public String getTfProvince() {
        return tfProvince;
    }

    public void setTfProvince(String tfProvince) {
        this.tfProvince = tfProvince;
    }

    public String getTfCity() {
        return tfCity;
    }

    public void setTfCity(String tfCity) {
        this.tfCity = tfCity;
    }

    public String getTfDistrict() {
        return tfDistrict;
    }

    public void setTfDistrict(String tfDistrict) {
        this.tfDistrict = tfDistrict;
    }

    public String getTfStreet() {
        return tfStreet;
    }

    public void setTfStreet(String tfStreet) {
        this.tfStreet = tfStreet;
    }

    public String getTfAddress() {
        return tfAddress;
    }

    public void setTfAddress(String tfAddress) {
        this.tfAddress = tfAddress;
    }

    public String getAddrTable() {
        return addrTable;
    }

    public void setAddrTable(String addrTable) {
        this.addrTable = addrTable;
    }

    public String getTfCloGuid() {
        return tfCloGuid;
    }

    public void setTfCloGuid(String tfCloGuid) {
        this.tfCloGuid = tfCloGuid;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinktel() {
        return linktel;
    }

    public void setLinktel(String linktel) {
        this.linktel = linktel;
    }
}
package com.phxl.ysy.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AssetsExtend extends AssetsExtendKey {
    private String equipmentCode;

    private String equipmentSpec;

    private BigDecimal price;

    private String createUserid;

    private String createUsername;

    private Date createDate;

    private BigDecimal extendSum;

    private String qrcode;

    private String assetsRecordM;

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode == null ? null : equipmentCode.trim();
    }

    public String getEquipmentSpec() {
        return equipmentSpec;
    }

    public void setEquipmentSpec(String equipmentSpec) {
        this.equipmentSpec = equipmentSpec == null ? null : equipmentSpec.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid == null ? null : createUserid.trim();
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername == null ? null : createUsername.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getExtendSum() {
        return extendSum;
    }

    public void setExtendSum(BigDecimal extendSum) {
        this.extendSum = extendSum;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode == null ? null : qrcode.trim();
    }

    public String getAssetsRecordM() {
        return assetsRecordM;
    }

    public void setAssetsRecordM(String assetsRecordM) {
        this.assetsRecordM = assetsRecordM == null ? null : assetsRecordM.trim();
    }
}
package com.phxl.ysy.entity;

public class EqRrAddupKey {
    private String equipmentCode;

    private String assetsRecord;

    private String yearNo;

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode == null ? null : equipmentCode.trim();
    }

    public String getAssetsRecord() {
        return assetsRecord;
    }

    public void setAssetsRecord(String assetsRecord) {
        this.assetsRecord = assetsRecord == null ? null : assetsRecord.trim();
    }

    public String getYearNo() {
        return yearNo;
    }

    public void setYearNo(String yearNo) {
        this.yearNo = yearNo == null ? null : yearNo.trim();
    }
}
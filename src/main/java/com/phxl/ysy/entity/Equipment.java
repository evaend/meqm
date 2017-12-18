package com.phxl.ysy.entity;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TD_EQUIPMENT", resultName="com.phxl.ysy.dao.EquipmentMapper.BaseResultMap")
public class Equipment {
    private String equipmentCode;

    private String equipmentName;

    private String equipmentStandardCode;

    private String equipmentStandardName;

    private String englishName;

    private String pyCode;

    private String internationalCode;

    private String standardAssetsFlag;

    private String meteringUnit;

    private String useLimit;

    private String typeCode;
    
    private String certGuid;
    private String qrcode;

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode == null ? null : equipmentCode.trim();
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName == null ? null : equipmentName.trim();
    }

    public String getEquipmentStandardCode() {
        return equipmentStandardCode;
    }

    public void setEquipmentStandardCode(String equipmentStandardCode) {
        this.equipmentStandardCode = equipmentStandardCode == null ? null : equipmentStandardCode.trim();
    }

    public String getEquipmentStandardName() {
        return equipmentStandardName;
    }

    public void setEquipmentStandardName(String equipmentStandardName) {
        this.equipmentStandardName = equipmentStandardName == null ? null : equipmentStandardName.trim();
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName == null ? null : englishName.trim();
    }

    public String getPyCode() {
        return pyCode;
    }

    public void setPyCode(String pyCode) {
        this.pyCode = pyCode == null ? null : pyCode.trim();
    }

    public String getInternationalCode() {
        return internationalCode;
    }

    public void setInternationalCode(String internationalCode) {
        this.internationalCode = internationalCode == null ? null : internationalCode.trim();
    }

    public String getStandardAssetsFlag() {
        return standardAssetsFlag;
    }

    public void setStandardAssetsFlag(String standardAssetsFlag) {
        this.standardAssetsFlag = standardAssetsFlag == null ? null : standardAssetsFlag.trim();
    }

    public String getMeteringUnit() {
        return meteringUnit;
    }

    public void setMeteringUnit(String meteringUnit) {
        this.meteringUnit = meteringUnit == null ? null : meteringUnit.trim();
    }

    public String getUseLimit() {
        return useLimit;
    }

    public void setUseLimit(String useLimit) {
        this.useLimit = useLimit == null ? null : useLimit.trim();
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

	public String getCertGuid() {
		return certGuid;
	}

	public void setCertGuid(String certGuid) {
		this.certGuid = certGuid;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
}
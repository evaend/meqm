package com.phxl.ysy.web.dto;

import com.phxl.ysy.entity.AssetsRecord;

/**
 * 资产导入数据行实体对象
 */
public class EquipmentDto extends AssetsRecord{
	
	private String equipmentCode;

    private String equipmentName;

    private String equipmentStandardCode;

    private String equipmentStandardName;

    private String englishName;

    private String pyCode;

    private String internationalCode;

    private String standardAssetsFlag;

    private String meteringUnit;

    private String typeCode;

    private String certGuid;

    private String registerNo;
    
    private String spec;
    
    private String fmodel;
    
	private String sheetName;
	
	private int rownum;
	
	private String qrCode;
	
	private String forgName;
	
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
        this.certGuid = certGuid == null ? null : certGuid.trim();
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo == null ? null : registerNo.trim();
    }
    
	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	
	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getFmodel() {
		return fmodel;
	}

	public void setFmodel(String fmodel) {
		this.fmodel = fmodel;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getForgName() {
		return forgName;
	}

	public void setForgName(String forgName) {
		this.forgName = forgName;
	}

}

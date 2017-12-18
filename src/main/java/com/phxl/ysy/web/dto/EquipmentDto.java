package com.phxl.ysy.web.dto;

import com.phxl.ysy.entity.AssetsRecord;

/**
 * 资产导入数据行实体对象
 */
public class EquipmentDto extends AssetsRecord{
	private String sheetName;
	
	private int rownum;
	
	private String equipmentName;
	private String qrCode;

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

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
}

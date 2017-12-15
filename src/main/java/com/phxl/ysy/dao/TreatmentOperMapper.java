package com.phxl.ysy.dao;

import com.phxl.ysy.dto.PatientOperDto;

public interface TreatmentOperMapper {

	/**
	 * 查询指定患者手术信息
	 * @param   operRecordGuid
	 * @return  PatientOperDto
	 */
	PatientOperDto  findPatientOperInfo(String operRecordGuid);

	/**
	 * 根据申请单id，查询关联的患者手术信息
	 * @param   applyId     申请单id
	 * @return  PatientOperDto
	 */
	PatientOperDto  findPatientOperByApplyId(String applyId);

}
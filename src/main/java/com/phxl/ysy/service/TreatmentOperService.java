package com.phxl.ysy.service;

import com.phxl.ysy.dto.PatientOperDto;

/**
 * 患者手术信息服务
 * @author  黄文君
 * @version 1.0
 * @project ysycontain
 * @date    2017年09月08日 17:18
 * @since   JDK 1.6
 */
public interface TreatmentOperService {

	/**
	 * 查询指定患者手术信息
	 * @param   operRecordGuid      患者手术guid
	 * @return  PatientOperDto
	 */
	public PatientOperDto findPatientOperInfo(String operRecordGuid);

	/**
	 * 根据申请单id，查询关联的患者手术信息
	 * @param   applyId     申请单id
	 * @return  PatientOperDto
	 */
	public PatientOperDto findPatientOperByApplyId(String applyId);

}

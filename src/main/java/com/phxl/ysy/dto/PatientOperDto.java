package com.phxl.ysy.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phxl.core.base.adapter.CustomDateSerializer;
import com.phxl.core.base.adapter.CustomDateTimeSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * 患者手术信息
 * @author  黄文君
 * @version 1.0
 * @date    2017年09月07日 17:27
 * @since   JDK 1.6
 */
public class PatientOperDto implements Serializable {

	private String treatmentNo;

	private String name;

	private String gender;

	private Short age;

	private String rOrgId;

	private String patientid;

	private String visitid;

	private String zyh;

	@JsonSerialize(using = CustomDateSerializer.class)
	private Date birthDate;

	private String sfzh;

	private String lxr;

	private String lxdh;

	private String lxadd;

	private String deptGuid;

	private String deptName;

	private String bedNo;

	@JsonSerialize(using = CustomDateSerializer.class)
	private Date zyrq;

	private String operRecordGuid;

	private String operName;

	private String operDoctor;

	private String operRoom;

	private String circuitNurse;

	private String bedNum;

	private String operExplain;

	private String operNo;

	private String operBm;

	@JsonSerialize(using = CustomDateSerializer.class)
	private Date operDate;

	private String mzff;

	private String dyzs;

	private String dezs;

	private String mzys;

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	private Date fdate;

	private String treatmentRecordGuid;

	private Short patientFsource;

	private String zxDeptGuid;

	private String tfRemark;

	public String getTreatmentNo() {
		return treatmentNo;
	}

	public void setTreatmentNo(String treatmentNo) {
		this.treatmentNo = treatmentNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Short getAge() {
		return age;
	}

	public void setAge(Short age) {
		this.age = age;
	}

	public String getrOrgId() {
		return rOrgId;
	}

	public void setrOrgId(String rOrgId) {
		this.rOrgId = rOrgId;
	}

	public String getPatientid() {
		return patientid;
	}

	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}

	public String getVisitid() {
		return visitid;
	}

	public void setVisitid(String visitid) {
		this.visitid = visitid;
	}

	public String getZyh() {
		return zyh;
	}

	public void setZyh(String zyh) {
		this.zyh = zyh;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getLxr() {
		return lxr;
	}

	public void setLxr(String lxr) {
		this.lxr = lxr;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getLxadd() {
		return lxadd;
	}

	public void setLxadd(String lxadd) {
		this.lxadd = lxadd;
	}

	public String getDeptGuid() {
		return deptGuid;
	}

	public void setDeptGuid(String deptGuid) {
		this.deptGuid = deptGuid;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getBedNo() {
		return bedNo;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

	public Date getZyrq() {
		return zyrq;
	}

	public void setZyrq(Date zyrq) {
		this.zyrq = zyrq;
	}

	public String getOperRecordGuid() {
		return operRecordGuid;
	}

	public void setOperRecordGuid(String operRecordGuid) {
		this.operRecordGuid = operRecordGuid;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getOperDoctor() {
		return operDoctor;
	}

	public void setOperDoctor(String operDoctor) {
		this.operDoctor = operDoctor;
	}

	public String getOperRoom() {
		return operRoom;
	}

	public void setOperRoom(String operRoom) {
		this.operRoom = operRoom;
	}

	public String getCircuitNurse() {
		return circuitNurse;
	}

	public void setCircuitNurse(String circuitNurse) {
		this.circuitNurse = circuitNurse;
	}

	public String getBedNum() {
		return bedNum;
	}

	public void setBedNum(String bedNum) {
		this.bedNum = bedNum;
	}

	public String getOperExplain() {
		return operExplain;
	}

	public void setOperExplain(String operExplain) {
		this.operExplain = operExplain;
	}

	public String getOperNo() {
		return operNo;
	}

	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	public String getOperBm() {
		return operBm;
	}

	public void setOperBm(String operBm) {
		this.operBm = operBm;
	}

	public Date getOperDate() {
		return operDate;
	}

	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}

	public String getMzff() {
		return mzff;
	}

	public void setMzff(String mzff) {
		this.mzff = mzff;
	}

	public String getDyzs() {
		return dyzs;
	}

	public void setDyzs(String dyzs) {
		this.dyzs = dyzs;
	}

	public String getDezs() {
		return dezs;
	}

	public void setDezs(String dezs) {
		this.dezs = dezs;
	}

	public String getMzys() {
		return mzys;
	}

	public void setMzys(String mzys) {
		this.mzys = mzys;
	}

	public Date getFdate() {
		return fdate;
	}

	public void setFdate(Date fdate) {
		this.fdate = fdate;
	}

	public String getTreatmentRecordGuid() {
		return treatmentRecordGuid;
	}

	public void setTreatmentRecordGuid(String treatmentRecordGuid) {
		this.treatmentRecordGuid = treatmentRecordGuid;
	}

	public Short getPatientFsource() {
		return patientFsource;
	}

	public void setPatientFsource(Short patientFsource) {
		this.patientFsource = patientFsource;
	}

	public String getZxDeptGuid() {
		return zxDeptGuid;
	}

	public void setZxDeptGuid(String zxDeptGuid) {
		this.zxDeptGuid = zxDeptGuid;
	}

	public String getTfRemark() {
		return tfRemark;
	}

	public void setTfRemark(String tfRemark) {
		this.tfRemark = tfRemark;
	}

}

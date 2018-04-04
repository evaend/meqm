package com.phxl.ysy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phxl.core.base.adapter.CustomDateSerializer;
import com.phxl.core.base.adapter.CustomDateTimeSerializer;
import com.phxl.core.base.annotation.BaseSql;
import com.phxl.core.base.annotation.SearchField;

import java.util.Date;

@BaseSql(tableName = "TD_REGISTER", resultName = "com.phxl.ysy.dao.RegisterMapper.BaseResultMap")
public class Register {
	private String certGuid;

	@SearchField
	private String materialName;

	private String fqun;

	private String type;

	@SearchField
	private String registerNo;

	private String instrumentCode;

	private String instrumentName;

	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date firstTime;

	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date lastTime;

	@SearchField
	private String produceName;

	private String enterpriseRegAddr;

	private String produceAddr;

	private String agentName;

	private String agentAddr;

	private String productStructure;

	private String productScope;

	private String productStandard;

	@SearchField
	private String tfBrand;

	private String tfBrandName;

	private String rCertGuid;

	private String flag;

	private String tfAccessoryFile;
	private String tfAccessory;

	private String createUserid;

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date createTime;

	private String modifyUserid;

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date modifyTime;

	private String createUserName;
	private String modifyUserName;

	private String taboo;

	private String afterService;

	private String tfRemark;

	private String isImport;

	private String value;

	private String text;

	public String getCertGuid() {
		return certGuid;
	}

	public void setCertGuid(String certGuid) {
		this.certGuid = certGuid;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getFqun() {
		return fqun;
	}

	public void setFqun(String fqun) {
		this.fqun = fqun;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getInstrumentCode() {
		return instrumentCode;
	}

	public void setInstrumentCode(String instrumentCode) {
		this.instrumentCode = instrumentCode;
	}

	public Date getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getProduceName() {
		return produceName;
	}

	public void setProduceName(String produceName) {
		this.produceName = produceName;
	}

	public String getEnterpriseRegAddr() {
		return enterpriseRegAddr;
	}

	public void setEnterpriseRegAddr(String enterpriseRegAddr) {
		this.enterpriseRegAddr = enterpriseRegAddr;
	}

	public String getProduceAddr() {
		return produceAddr;
	}

	public void setProduceAddr(String produceAddr) {
		this.produceAddr = produceAddr;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentAddr() {
		return agentAddr;
	}

	public void setAgentAddr(String agentAddr) {
		this.agentAddr = agentAddr;
	}

	public String getProductStructure() {
		return productStructure;
	}

	public void setProductStructure(String productStructure) {
		this.productStructure = productStructure;
	}

	public String getProductScope() {
		return productScope;
	}

	public void setProductScope(String productScope) {
		this.productScope = productScope;
	}

	public String getProductStandard() {
		return productStandard;
	}

	public void setProductStandard(String productStandard) {
		this.productStandard = productStandard;
	}

	public String getTfBrand() {
		return tfBrand;
	}

	public void setTfBrand(String tfBrand) {
		this.tfBrand = tfBrand;
	}

	public String getrCertGuid() {
		return rCertGuid;
	}

	public void setrCertGuid(String rCertGuid) {
		this.rCertGuid = rCertGuid;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTfAccessoryFile() {
		return tfAccessoryFile;
	}

	public void setTfAccessoryFile(String tfAccessoryFile) {
		this.tfAccessoryFile = tfAccessoryFile;
	}

	public String getTfAccessory() {
		return tfAccessory;
	}

	public Register setTfAccessory(String tfAccessory) {
		this.tfAccessory = tfAccessory;
		return this;
	}

	public String getCreateUserid() {
		return createUserid;
	}

	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyUserid() {
		return modifyUserid;
	}

	public void setModifyUserid(String modifyUserid) {
		this.modifyUserid = modifyUserid;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getInstrumentName() {
		return instrumentName;
	}

	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}

	public String getTfBrandName() {
		return tfBrandName;
	}

	public void setTfBrandName(String tfBrandName) {
		this.tfBrandName = tfBrandName;
	}

	public String getTaboo() {
		return taboo;
	}

	public void setTaboo(String taboo) {
		this.taboo = taboo;
	}

	public String getAfterService() {
		return afterService;
	}

	public void setAfterService(String afterService) {
		this.afterService = afterService;
	}

	public String getTfRemark() {
		return tfRemark;
	}

	public void setTfRemark(String tfRemark) {
		this.tfRemark = tfRemark;
	}

	public String getIsImport() {
		return isImport;
	}

	public Register setIsImport(String isImport) {
		this.isImport = isImport;
		return this;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
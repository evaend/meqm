package com.phxl.ysy.entity;

import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TS_USER_OPERATION", resultName="com.phxl.ysy.dao.UserOperationMapper.BaseResultMap")
public class UserOperation {
    private String operationGuid;

    private String errorReason;

    private String moduleId;

    private Date operationDate;

    private String operationUrl;

    private String operationName;

    private String operationRes;

    private String operationCode;

    private String loginGuid;

    private String moduleName;
    
    private String operationArgs;

    public String getOperationGuid() {
        return operationGuid;
    }

    public void setOperationGuid(String operationGuid) {
        this.operationGuid = operationGuid;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationUrl() {
        return operationUrl;
    }

    public void setOperationUrl(String operationUrl) {
        this.operationUrl = operationUrl;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationRes() {
        return operationRes;
    }

    public void setOperationRes(String operationRes) {
        this.operationRes = operationRes;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getLoginGuid() {
        return loginGuid;
    }

    public void setLoginGuid(String loginGuid) {
        this.loginGuid = loginGuid;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

	public String getOperationArgs() {
		return operationArgs;
	}

	public void setOperationArgs(String operationArgs) {
		this.operationArgs = operationArgs;
	}  
}
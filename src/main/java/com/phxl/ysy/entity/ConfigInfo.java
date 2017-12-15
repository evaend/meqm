package com.phxl.ysy.entity;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TS_CONFIG_INFO", resultName="com.phxl.ysy.dao.ConfigInfoMapper.BaseResultMap")
public class ConfigInfo {
    private String configGuid;

    private Long orgId;

    private String storageGuid;

    private String deptGuid;

    private String configName;

    private String configValue;

    private String tfRemark;

    private String flag;

    private String configCode;

    public String getConfigGuid() {
        return configGuid;
    }

    public void setConfigGuid(String configGuid) {
        this.configGuid = configGuid == null ? null : configGuid.trim();
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getStorageGuid() {
        return storageGuid;
    }

    public void setStorageGuid(String storageGuid) {
        this.storageGuid = storageGuid == null ? null : storageGuid.trim();
    }

    public String getDeptGuid() {
        return deptGuid;
    }

    public void setDeptGuid(String deptGuid) {
        this.deptGuid = deptGuid == null ? null : deptGuid.trim();
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName == null ? null : configName.trim();
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
    }

    public String getTfRemark() {
        return tfRemark;
    }

    public void setTfRemark(String tfRemark) {
        this.tfRemark = tfRemark == null ? null : tfRemark.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode == null ? null : configCode.trim();
    }
}
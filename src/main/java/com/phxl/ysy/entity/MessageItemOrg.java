package com.phxl.ysy.entity;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TM_MESSAGEITEM_ORG", resultName="com.phxl.ysy.dao.MessageItemOrgMapper.BaseResultMap")
public class MessageItemOrg {
    private String miLocalGuid;

    private Long orgId;

    private String miGlobalGuid;

    private String miSendType;

    private String miIsreceiveAuto;

    private String miReceiveUserid;

    private String fstate;

    public String getMiLocalGuid() {
        return miLocalGuid;
    }

    public void setMiLocalGuid(String miLocalGuid) {
        this.miLocalGuid = miLocalGuid;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getMiGlobalGuid() {
        return miGlobalGuid;
    }

    public void setMiGlobalGuid(String miGlobalGuid) {
        this.miGlobalGuid = miGlobalGuid;
    }

    public String getMiSendType() {
        return miSendType;
    }

    public void setMiSendType(String miSendType) {
        this.miSendType = miSendType;
    }

    public String getMiIsreceiveAuto() {
        return miIsreceiveAuto;
    }

    public void setMiIsreceiveAuto(String miIsreceiveAuto) {
        this.miIsreceiveAuto = miIsreceiveAuto;
    }

    public String getMiReceiveUserid() {
        return miReceiveUserid;
    }

    public void setMiReceiveUserid(String miReceiveUserid) {
        this.miReceiveUserid = miReceiveUserid;
    }

    public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate;
    }
}
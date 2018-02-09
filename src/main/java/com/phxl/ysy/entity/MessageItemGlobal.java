package com.phxl.ysy.entity;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TM_MESSAGEITEM_GLOBAL", resultName="com.phxl.ysy.dao.MessageItemGlobalMapper.BaseResultMap")
public class MessageItemGlobal {
    private String miGlobalGuid;

    private String miCode;

    private String moduleId;

    private String miSendType;

    private String miTitle;

    private String miContent;

    private String wechatTemplateid;

    private String wechatTemplatecontent;

    private String miSysType;

    private Integer miFsort;

    private String miSaveDate;

    private String fstate;

    public String getMiGlobalGuid() {
        return miGlobalGuid;
    }

    public void setMiGlobalGuid(String miGlobalGuid) {
        this.miGlobalGuid = miGlobalGuid;
    }

    public String getMiCode() {
        return miCode;
    }

    public void setMiCode(String miCode) {
        this.miCode = miCode;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getMiSendType() {
        return miSendType;
    }

    public void setMiSendType(String miSendType) {
        this.miSendType = miSendType;
    }

    public String getMiTitle() {
        return miTitle;
    }

    public void setMiTitle(String miTitle) {
        this.miTitle = miTitle;
    }

    public String getMiContent() {
        return miContent;
    }

    public void setMiContent(String miContent) {
        this.miContent = miContent;
    }

    public String getWechatTemplateid() {
        return wechatTemplateid;
    }

    public void setWechatTemplateid(String wechatTemplateid) {
        this.wechatTemplateid = wechatTemplateid;
    }

    public String getWechatTemplatecontent() {
        return wechatTemplatecontent;
    }

    public void setWechatTemplatecontent(String wechatTemplatecontent) {
        this.wechatTemplatecontent = wechatTemplatecontent;
    }

    public String getMiSysType() {
        return miSysType;
    }

    public void setMiSysType(String miSysType) {
        this.miSysType = miSysType;
    }

    public Integer getMiFsort() {
        return miFsort;
    }

    public void setMiFsort(Integer miFsort) {
        this.miFsort = miFsort;
    }

    public String getMiSaveDate() {
        return miSaveDate;
    }

    public void setMiSaveDate(String miSaveDate) {
        this.miSaveDate = miSaveDate;
    }

    public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate;
    }
}
package com.phxl.ysy.entity;

import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TM_MESSAGE", resultName="com.phxl.ysy.dao.MessageMapper.BaseResultMap")
public class Message {
    private String messageGuid;

    private String miSendType;

    private String messageTitle;

    private String messageContent;

    private String messageSendfstate;

    private Date miCreateDate;

    private Date miLastSendDate;

    private Long miSendOrgId;

    private String miSendUserid;

    private String miSendUsername;

    private String miReceiveOrgId;

    private String miReceiveUsername;

    private String miReceiveUserid;

    private String messageReadfstate;

    private Date miReadDate;

    private String miSendDelete;

    private String miReceiveDelete;

    private String messageCode;

    private Short miSendNum;

    private String miCode;
    
    private String miSysType;
    
    private String miSaveDate;

    public String getMessageGuid() {
        return messageGuid;
    }

    public void setMessageGuid(String messageGuid) {
        this.messageGuid = messageGuid;
    }

    public String getMiSendType() {
        return miSendType;
    }

    public void setMiSendType(String miSendType) {
        this.miSendType = miSendType;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageSendfstate() {
        return messageSendfstate;
    }

    public void setMessageSendfstate(String messageSendfstate) {
        this.messageSendfstate = messageSendfstate;
    }

    public Date getMiCreateDate() {
        return miCreateDate;
    }

    public void setMiCreateDate(Date miCreateDate) {
        this.miCreateDate = miCreateDate;
    }

    public Date getMiLastSendDate() {
        return miLastSendDate;
    }

    public void setMiLastSendDate(Date miLastSendDate) {
        this.miLastSendDate = miLastSendDate;
    }

    public Long getMiSendOrgId() {
        return miSendOrgId;
    }

    public void setMiSendOrgId(Long miSendOrgId) {
        this.miSendOrgId = miSendOrgId;
    }

    public String getMiSendUserid() {
        return miSendUserid;
    }

    public void setMiSendUserid(String miSendUserid) {
        this.miSendUserid = miSendUserid;
    }

    public String getMiSendUsername() {
        return miSendUsername;
    }

    public void setMiSendUsername(String miSendUsername) {
        this.miSendUsername = miSendUsername;
    }

    public String getMiReceiveOrgId() {
        return miReceiveOrgId;
    }

    public void setMiReceiveOrgId(String miReceiveOrgId) {
        this.miReceiveOrgId = miReceiveOrgId;
    }

    public String getMiReceiveUsername() {
        return miReceiveUsername;
    }

    public void setMiReceiveUsername(String miReceiveUsername) {
        this.miReceiveUsername = miReceiveUsername;
    }

    public String getMiReceiveUserid() {
        return miReceiveUserid;
    }

    public void setMiReceiveUserid(String miReceiveUserid) {
        this.miReceiveUserid = miReceiveUserid;
    }

    public String getMessageReadfstate() {
        return messageReadfstate;
    }

    public void setMessageReadfstate(String messageReadfstate) {
        this.messageReadfstate = messageReadfstate;
    }

    public Date getMiReadDate() {
        return miReadDate;
    }

    public void setMiReadDate(Date miReadDate) {
        this.miReadDate = miReadDate;
    }

    public String getMiSendDelete() {
        return miSendDelete;
    }

    public void setMiSendDelete(String miSendDelete) {
        this.miSendDelete = miSendDelete;
    }

    public String getMiReceiveDelete() {
        return miReceiveDelete;
    }

    public void setMiReceiveDelete(String miReceiveDelete) {
        this.miReceiveDelete = miReceiveDelete;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public Short getMiSendNum() {
        return miSendNum;
    }

    public void setMiSendNum(Short miSendNum) {
        this.miSendNum = miSendNum;
    }

    public String getMiCode() {
        return miCode;
    }

    public void setMiCode(String miCode) {
        this.miCode = miCode;
    }
    
    public String getMiSysType() {
        return miSysType;
    }

    public void setMiSysType(String miSysType) {
        this.miSysType = miSysType;
    }
    
    public String getMiSaveDate() {
        return miSaveDate;
    }

    public void setMiSaveDate(String miSaveDate) {
        this.miSaveDate = miSaveDate;
    }
}
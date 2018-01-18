package com.phxl.ysy.entity;

import java.util.Date;

public class Messagewechat {
    private String messagewechatGuid;

    private Long orgId;

    private String mcName;

    private String mcTemplateid;

    private String mcTemplatecontent;

    private String mcRemark;

    private Date createDate;

    private Date modifyDate;

    private String fstate;

    public String getMessagewechatGuid() {
        return messagewechatGuid;
    }

    public void setMessagewechatGuid(String messagewechatGuid) {
        this.messagewechatGuid = messagewechatGuid == null ? null : messagewechatGuid.trim();
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName == null ? null : mcName.trim();
    }

    public String getMcTemplateid() {
        return mcTemplateid;
    }

    public void setMcTemplateid(String mcTemplateid) {
        this.mcTemplateid = mcTemplateid == null ? null : mcTemplateid.trim();
    }

    public String getMcTemplatecontent() {
        return mcTemplatecontent;
    }

    public void setMcTemplatecontent(String mcTemplatecontent) {
        this.mcTemplatecontent = mcTemplatecontent == null ? null : mcTemplatecontent.trim();
    }

    public String getMcRemark() {
        return mcRemark;
    }

    public void setMcRemark(String mcRemark) {
        this.mcRemark = mcRemark == null ? null : mcRemark.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate == null ? null : fstate.trim();
    }
}
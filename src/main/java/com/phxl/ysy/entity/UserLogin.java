package com.phxl.ysy.entity;

import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TS_USER_LOGIN", resultName="com.phxl.ysy.dao.UserLoginMapper.BaseResultMap")
public class UserLogin {
    private String loginGuid;

    private String loginUserid;

    private String loginUserno;

    private String loginUsername;

    private Date loginDate;

    private String loginBrowser;

    private Long orgId;

    private String orgName;

    private String loginIp;

    private String loginOs;

    public String getLoginGuid() {
        return loginGuid;
    }

    public void setLoginGuid(String loginGuid) {
        this.loginGuid = loginGuid;
    }

    public String getLoginUserid() {
        return loginUserid;
    }

    public void setLoginUserid(String loginUserid) {
        this.loginUserid = loginUserid;
    }

    public String getLoginUserno() {
        return loginUserno;
    }

    public void setLoginUserno(String loginUserno) {
        this.loginUserno = loginUserno;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginBrowser() {
        return loginBrowser;
    }

    public void setLoginBrowser(String loginBrowser) {
        this.loginBrowser = loginBrowser;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginOs() {
        return loginOs;
    }

    public void setLoginOs(String loginOs) {
        this.loginOs = loginOs;
    }
}
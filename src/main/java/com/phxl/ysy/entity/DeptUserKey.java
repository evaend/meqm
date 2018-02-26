package com.phxl.ysy.entity;

public class DeptUserKey {
    private String userId;

    private String deptGuid;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getDeptGuid() {
        return deptGuid;
    }

    public void setDeptGuid(String deptGuid) {
        this.deptGuid = deptGuid == null ? null : deptGuid.trim();
    }
}
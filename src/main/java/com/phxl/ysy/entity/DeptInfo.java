package com.phxl.ysy.entity;

public class DeptInfo {
    private String deptCode;

    private String deptNam;

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    public String getDeptNam() {
        return deptNam;
    }

    public void setDeptNam(String deptNam) {
        this.deptNam = deptNam == null ? null : deptNam.trim();
    }
}
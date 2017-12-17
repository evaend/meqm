package com.phxl.ysy.entity;

public class MaintainCheck {
    private String rrpairOrder;

    private String resistanceCheck;

    private String exteriorCheck;

    private String surfaceCheck;

    private String calibration;

    private String currentCheck;

    public String getRrpairOrder() {
        return rrpairOrder;
    }

    public void setRrpairOrder(String rrpairOrder) {
        this.rrpairOrder = rrpairOrder == null ? null : rrpairOrder.trim();
    }

    public String getResistanceCheck() {
        return resistanceCheck;
    }

    public void setResistanceCheck(String resistanceCheck) {
        this.resistanceCheck = resistanceCheck == null ? null : resistanceCheck.trim();
    }

    public String getExteriorCheck() {
        return exteriorCheck;
    }

    public void setExteriorCheck(String exteriorCheck) {
        this.exteriorCheck = exteriorCheck == null ? null : exteriorCheck.trim();
    }

    public String getSurfaceCheck() {
        return surfaceCheck;
    }

    public void setSurfaceCheck(String surfaceCheck) {
        this.surfaceCheck = surfaceCheck == null ? null : surfaceCheck.trim();
    }

    public String getCalibration() {
        return calibration;
    }

    public void setCalibration(String calibration) {
        this.calibration = calibration == null ? null : calibration.trim();
    }

    public String getCurrentCheck() {
        return currentCheck;
    }

    public void setCurrentCheck(String currentCheck) {
        this.currentCheck = currentCheck == null ? null : currentCheck.trim();
    }
}
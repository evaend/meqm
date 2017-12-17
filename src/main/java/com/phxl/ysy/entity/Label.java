package com.phxl.ysy.entity;

public class Label extends LabelKey {
    private String multiName;

    private String labelType;

    public String getMultiName() {
        return multiName;
    }

    public void setMultiName(String multiName) {
        this.multiName = multiName == null ? null : multiName.trim();
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType == null ? null : labelType.trim();
    }
}
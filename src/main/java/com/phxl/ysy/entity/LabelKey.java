package com.phxl.ysy.entity;

public class LabelKey {
    private String labelGuid;

    private String multiId;

    public String getLabelGuid() {
        return labelGuid;
    }

    public void setLabelGuid(String labelGuid) {
        this.labelGuid = labelGuid == null ? null : labelGuid.trim();
    }

    public String getMultiId() {
        return multiId;
    }

    public void setMultiId(String multiId) {
        this.multiId = multiId == null ? null : multiId.trim();
    }
}
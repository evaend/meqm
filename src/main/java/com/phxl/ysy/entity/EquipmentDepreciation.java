package com.phxl.ysy.entity;

import java.math.BigDecimal;
import java.util.Date;

public class EquipmentDepreciation {
    private String assetsRecord;

    private String deptCode;

    private Date depreciationDate;

    private BigDecimal depreciationPrice;

    private String createUserguid;

    private String modifyUserguid;

    private String depreciationType;

    public String getAssetsRecord() {
        return assetsRecord;
    }

    public void setAssetsRecord(String assetsRecord) {
        this.assetsRecord = assetsRecord == null ? null : assetsRecord.trim();
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    public Date getDepreciationDate() {
        return depreciationDate;
    }

    public void setDepreciationDate(Date depreciationDate) {
        this.depreciationDate = depreciationDate;
    }

    public BigDecimal getDepreciationPrice() {
        return depreciationPrice;
    }

    public void setDepreciationPrice(BigDecimal depreciationPrice) {
        this.depreciationPrice = depreciationPrice;
    }

    public String getCreateUserguid() {
        return createUserguid;
    }

    public void setCreateUserguid(String createUserguid) {
        this.createUserguid = createUserguid == null ? null : createUserguid.trim();
    }

    public String getModifyUserguid() {
        return modifyUserguid;
    }

    public void setModifyUserguid(String modifyUserguid) {
        this.modifyUserguid = modifyUserguid == null ? null : modifyUserguid.trim();
    }

    public String getDepreciationType() {
        return depreciationType;
    }

    public void setDepreciationType(String depreciationType) {
        this.depreciationType = depreciationType == null ? null : depreciationType.trim();
    }
}
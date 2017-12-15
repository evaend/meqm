package com.phxl.ysy.dto;

import java.math.BigDecimal;
import java.util.Date;

public class QrcodeDto {
    private String qrcode;

    private Long fitemid;

    private String tenderDetailGuid;

    private String fbarcode;

    private BigDecimal tenderPrice;

    private String tenderUnit;

    private BigDecimal conversion;

    private BigDecimal ckdj;

    private String flot;

    private Date prodDate;

    private Date usefulDate;

    private Long rOrgId;

    private String rStorageGuid;

    private Long fOrgId;

    private String fStorageGuid;

    private String tfBrand;

    private String fstate;

    private String importDetailGuid;

    private String operRecordGuid;

    private String treatmentRecordGuid;

    private Date createTime;

    private Date modifyTime;

    private String sendId;

    private String sendDetailGuid;

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode == null ? null : qrcode.trim();
    }

    public Long getFitemid() {
        return fitemid;
    }

    public void setFitemid(Long fitemid) {
        this.fitemid = fitemid;
    }

    public String getTenderDetailGuid() {
        return tenderDetailGuid;
    }

    public void setTenderDetailGuid(String tenderDetailGuid) {
        this.tenderDetailGuid = tenderDetailGuid == null ? null : tenderDetailGuid.trim();
    }

    public String getFbarcode() {
        return fbarcode;
    }

    public void setFbarcode(String fbarcode) {
        this.fbarcode = fbarcode == null ? null : fbarcode.trim();
    }

    public BigDecimal getTenderPrice() {
        return tenderPrice;
    }

    public void setTenderPrice(BigDecimal tenderPrice) {
        this.tenderPrice = tenderPrice;
    }

    public String getTenderUnit() {
        return tenderUnit;
    }

    public void setTenderUnit(String tenderUnit) {
        this.tenderUnit = tenderUnit == null ? null : tenderUnit.trim();
    }

    public BigDecimal getConversion() {
        return conversion;
    }

    public void setConversion(BigDecimal conversion) {
        this.conversion = conversion;
    }

    public BigDecimal getCkdj() {
        return ckdj;
    }

    public void setCkdj(BigDecimal ckdj) {
        this.ckdj = ckdj;
    }

    public String getFlot() {
        return flot;
    }

    public void setFlot(String flot) {
        this.flot = flot == null ? null : flot.trim();
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Date getUsefulDate() {
        return usefulDate;
    }

    public void setUsefulDate(Date usefulDate) {
        this.usefulDate = usefulDate;
    }

    public Long getrOrgId() {
        return rOrgId;
    }

    public void setrOrgId(Long rOrgId) {
        this.rOrgId = rOrgId;
    }

    public String getrStorageGuid() {
        return rStorageGuid;
    }

    public void setrStorageGuid(String rStorageGuid) {
        this.rStorageGuid = rStorageGuid == null ? null : rStorageGuid.trim();
    }

    public Long getfOrgId() {
        return fOrgId;
    }

    public void setfOrgId(Long fOrgId) {
        this.fOrgId = fOrgId;
    }

    public String getfStorageGuid() {
        return fStorageGuid;
    }

    public void setfStorageGuid(String fStorageGuid) {
        this.fStorageGuid = fStorageGuid == null ? null : fStorageGuid.trim();
    }

    public String getTfBrand() {
        return tfBrand;
    }

    public void setTfBrand(String tfBrand) {
        this.tfBrand = tfBrand == null ? null : tfBrand.trim();
    }

    public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate == null ? null : fstate.trim();
    }

    public String getImportDetailGuid() {
        return importDetailGuid;
    }

    public void setImportDetailGuid(String importDetailGuid) {
        this.importDetailGuid = importDetailGuid == null ? null : importDetailGuid.trim();
    }

    public String getOperRecordGuid() {
        return operRecordGuid;
    }

    public void setOperRecordGuid(String operRecordGuid) {
        this.operRecordGuid = operRecordGuid == null ? null : operRecordGuid.trim();
    }

    public String getTreatmentRecordGuid() {
        return treatmentRecordGuid;
    }

    public void setTreatmentRecordGuid(String treatmentRecordGuid) {
        this.treatmentRecordGuid = treatmentRecordGuid == null ? null : treatmentRecordGuid.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId == null ? null : sendId.trim();
    }

    public String getSendDetailGuid() {
        return sendDetailGuid;
    }

    public void setSendDetailGuid(String sendDetailGuid) {
        this.sendDetailGuid = sendDetailGuid == null ? null : sendDetailGuid.trim();
    }
}
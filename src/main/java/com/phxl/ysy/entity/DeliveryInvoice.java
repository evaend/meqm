package com.phxl.ysy.entity;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TB_DELIVERY_INVOICE", resultName="com.phxl.ysy.dao.DeliveryInvoiceMapper.BaseResultMap")
public class DeliveryInvoice {
    private String sendId;

    private String invoiceId;

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
}
package com.phxl.ysy.entity;

import java.math.BigDecimal;

public class EqRrAddup extends EqRrAddupKey {
    private Short rrDaysYear;

    private Short rrFreqYear;

    private BigDecimal acceAmou;

    public Short getRrDaysYear() {
        return rrDaysYear;
    }

    public void setRrDaysYear(Short rrDaysYear) {
        this.rrDaysYear = rrDaysYear;
    }

    public Short getRrFreqYear() {
        return rrFreqYear;
    }

    public void setRrFreqYear(Short rrFreqYear) {
        this.rrFreqYear = rrFreqYear;
    }

    public BigDecimal getAcceAmou() {
        return acceAmou;
    }

    public void setAcceAmou(BigDecimal acceAmou) {
        this.acceAmou = acceAmou;
    }
}
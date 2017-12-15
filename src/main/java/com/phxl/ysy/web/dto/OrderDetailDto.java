package com.phxl.ysy.web.dto;

import java.io.Serializable;

public class OrderDetailDto implements Serializable {
	
	private String tenderDetailGuid;
	
	private Integer fsort;
	
	private Long amount;
	
    public String getTenderDetailGuid() {
        return tenderDetailGuid;
    }

    public void setTenderDetailGuid(String tenderDetailGuid) {
        this.tenderDetailGuid = tenderDetailGuid;
    }

    public Integer getFsort() {
		return fsort;
	}

	public void setFsort(Integer fsort) {
		this.fsort = fsort;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

}

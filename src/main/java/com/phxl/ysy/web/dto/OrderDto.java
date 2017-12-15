package com.phxl.ysy.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phxl.core.base.adapter.CustomDateSerializer;

public class OrderDto implements Serializable {
	
	private String orderId;
	
	private String storageGuid;
	
	private String addrGuid;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date expectDate;
	
	private String fstate;
	
	private List<OrderDetailDto> detailList;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStorageGuid() {
		return storageGuid;
	}

	public void setStorageGuid(String storageGuid) {
		this.storageGuid = storageGuid;
	}

	public String getAddrGuid() {
		return addrGuid;
	}

	public void setAddrGuid(String addrGuid) {
		this.addrGuid = addrGuid;
	}

	public Date getExpectDate() {
		return expectDate;
	}

	public void setExpectDate(Date expectDate) {
		this.expectDate = expectDate;
	}

	public List<OrderDetailDto> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<OrderDetailDto> detailList) {
		this.detailList = detailList;
	}

	public String getFstate() {
		return fstate;
	}

	public void setFstate(String fstate) {
		this.fstate = fstate;
	}
	
}

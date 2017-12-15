package com.phxl.ysy.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phxl.core.base.adapter.CustomDateSerializer;

import java.io.Serializable;
import java.util.Date;

public class DeliveryDetailDto implements Serializable {
	
	private String sendDetailGuid;

	private String orderDetailGuid;

	private String tenderDetailGuid;
	
	private Integer fsort;
	
	private Integer amount;
	
	private String flot;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date prodDate;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date usefulDate;

	public String getSendDetailGuid() {
		return sendDetailGuid;
	}

	public void setSendDetailGuid(String sendDetailGuid) {
		this.sendDetailGuid = sendDetailGuid;
	}

	public String getTenderDetailGuid() {
		return tenderDetailGuid;
	}

	public void setTenderDetailGuid(String tenderDetailGuid) {
		this.tenderDetailGuid = tenderDetailGuid;
	}

	public String getOrderDetailGuid() {
		return orderDetailGuid;
	}

	public void setOrderDetailGuid(String orderDetailGuid) {
		this.orderDetailGuid = orderDetailGuid;
	}

	public Integer getFsort() {
		return fsort;
	}

	public void setFsort(Integer fsort) {
		this.fsort = fsort;
	}

	public String getFlot() {
		return flot;
	}

	public void setFlot(String flot) {
		this.flot = flot;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
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

}

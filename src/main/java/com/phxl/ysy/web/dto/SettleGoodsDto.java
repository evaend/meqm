package com.phxl.ysy.web.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SettleGoodsDto implements Serializable {

	private String orderId;

	private List<DeliveryDetailDto> detailList;
	
	private List<Map<String, Integer>> packageList;

	private Map<String, Object> materialScope;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<DeliveryDetailDto> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<DeliveryDetailDto> detailList) {
		this.detailList = detailList;
	}

	public List<Map<String, Integer>> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<Map<String, Integer>> packageList) {
		this.packageList = packageList;
	}

	public Map<String, Object> getMaterialScope() {
		return materialScope;
	}

	public void setMaterialScope(Map<String, Object> materialScope) {
		this.materialScope = materialScope;
	}
}

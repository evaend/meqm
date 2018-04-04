package com.phxl.ysy.web.dto;

import java.util.List;

import com.phxl.ysy.entity.MaintainPlan;
import com.phxl.ysy.entity.MaintainType;

public class MaintainPlanDto {
	MaintainPlan maintainPlan;
	
	List<String> assetsRecordGuidList;
	
	List<String> maintainTypes;

	public MaintainPlan getMaintainPlan() {
		return maintainPlan;
	}

	public void setMaintainPlan(MaintainPlan maintainPlan) {
		this.maintainPlan = maintainPlan;
	}

	public List<String> getAssetsRecordGuidList() {
		return assetsRecordGuidList;
	}

	public void setAssetsRecordGuidList(List<String> assetsRecordGuidList) {
		this.assetsRecordGuidList = assetsRecordGuidList;
	}

	public List<String> getMaintainTypes() {
		return maintainTypes;
	}

	public void setMaintainTypes(List<String> maintainTypes) {
		this.maintainTypes = maintainTypes;
	}
}

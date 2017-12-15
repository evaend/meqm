package com.phxl.ysy.web.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.phxl.ysy.entity.GkTemplateDetail;

public class GkTemplateDto implements Serializable {

	private String gkTemplateGuid;
	
	private List<GkTemplateDetail> gkTemplateDetails;
	
	private List<Map<String, String>> packageList;

	public String getGkTemplateGuid() {
		return gkTemplateGuid;
	}

	public void setGkTemplateGuid(String gkTemplateGuid) {
		this.gkTemplateGuid = gkTemplateGuid;
	}

	public List<GkTemplateDetail> getGkTemplateDetails() {
		return gkTemplateDetails;
	}

	public void setGkTemplateDetails(List<GkTemplateDetail> gkTemplateDetails) {
		this.gkTemplateDetails = gkTemplateDetails;
	}

	public List<Map<String, String>> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<Map<String, String>> packageList) {
		this.packageList = packageList;
	}

}

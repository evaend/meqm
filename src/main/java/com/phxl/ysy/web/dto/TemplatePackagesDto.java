package com.phxl.ysy.web.dto;

import java.io.Serializable;

/**
 * 功能描述
 *
 * @author 黄文君
 * @version 1.0
 * @project ysycontain
 * @date 2017年09月11日 11:18
 * @since JDK 1.6
 */
public class TemplatePackagesDto implements Serializable {

	/**模板guid*/
	private String templateGuid;
	/**目标包序号*/
	private Integer toPackageId;
	/**数据源包序号*/
	private Integer fromPackageId;

	public String getTemplateGuid() {
		return templateGuid;
	}

	public Integer getToPackageId() {
		return toPackageId;
	}

	public void setToPackageId(Integer toPackageId) {
		this.toPackageId = toPackageId;
	}

	public void setTemplateGuid(String templateGuid) {
		this.templateGuid = templateGuid;
	}

	public Integer getFromPackageId() {
		return fromPackageId;
	}

	public void setFromPackageId(Integer fromPackageId) {
		this.fromPackageId = fromPackageId;
	}
}

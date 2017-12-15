package com.phxl.ysy.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 指定手术包添加工具（从指定的模板手术包）接口请求数据结构<p/>
 * @author  黄文君
 * @version 1.0
 * @project ysycontain
 * @date    2017年09月11日 11:14
 * @since   JDK 1.6
 */
public class AddToolsFromTemplatePackagesDto implements Serializable {

	/**订单id*/
	private String orderId;
	/**手术包列表*/
	private List<TemplatePackagesDto> fromPackageList;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<TemplatePackagesDto> getFromPackageList() {
		return fromPackageList;
	}

	public void setFromPackageList(List<TemplatePackagesDto> fromPackageList) {
		this.fromPackageList = fromPackageList;
	}

}



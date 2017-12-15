/**
* 普华信联公司源代码，版权归普华信联公司所有。
*
* 项目名称	：	businessModule
* 创建日期	：	2017年9月5日
* 所在包		：	com.phxl.ysy.businessModule.web.dto
* 文件名称	：	AddToolsByPackageDto.java
* 修改历史	：
* 1.[2017年9月5日]创建文件 by 黄文君
*/

package com.phxl.ysy.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * [功能说明]
 * @Date	2017年9月5日 上午9:48:23
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
public class AddToolsByPackageDto implements Serializable {
	
	private String orderId;
	private Integer packageId;
	private List<PackageToolDto> toolList;
	
	private String gkTemplateGuid;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getPackageId() {
		return packageId;
	}
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
	public List<PackageToolDto> getToolList() {
		return toolList;
	}
	public void setToolList(List<PackageToolDto> toolList) {
		this.toolList = toolList;
	}
	public String getGkTemplateGuid() {
		return gkTemplateGuid;
	}
	public void setGkTemplateGuid(String gkTemplateGuid) {
		this.gkTemplateGuid = gkTemplateGuid;
	}
}

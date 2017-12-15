/**
* 普华信联公司源代码，版权归普华信联公司所有。
*
* 项目名称	：	commonModule
* 创建日期	：	2017年9月6日
* 所在包		：	com.phxl.ysy.commonModule.constant
* 文件名称	：	GkAttribute.java
* 修改历史	：
* 1.[2017年9月6日]创建文件 by 黄文君
*/

package com.phxl.ysy.constant;

/**
 * 产品属性字典
 * @Date	2017年9月6日 下午2:53:32
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
public enum GkAttribute {
	
	TOOL("10", "工具"),
	STERILIZE("12", "灭菌");
	
	private final String code;
	private final  String name;
	private GkAttribute(String code, String name) {
		this.code = code;
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	
}

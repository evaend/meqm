/**
* 普华信联公司源代码，版权归普华信联公司所有。
*
* 项目名称	：	businessModule
* 创建日期	：	2017年9月4日
* 所在包		：	com.phxl.ysy.businessModule.service
* 文件名称	：	PackageService.java
* 修改历史	：
* 1.[2017年9月4日]创建文件 by 黄文君
*/

package com.phxl.ysy.service;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.IBaseService;
import com.phxl.ysy.entity.PackageTool;
import com.phxl.ysy.web.dto.PackageToolDto;
import com.phxl.ysy.web.dto.TemplatePackagesDto;

import java.util.List;
import java.util.Map;

/**
 * 手术包服务
 * @Date	2017年9月4日 上午11:48:04
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
public interface PackageService extends IBaseService {

	/**
	 * 手术包产品类型数量比较
	 * @param   sendId
	 * @return  List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> checkAttributeAmountOfOperPackage(String sendId);

	/**
	 * 手术包手术器械数量比较
	 * @param   sendId
	 * @return  List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> checkToolAmountOfOperPackage(String sendId);

	/**
	 * 核对每个手术包手术器械的数量
	 * @author	黄文君
	 * @date	2017年10月10日
	 * @param	orderId
	 * @param	amountByPackage
	 * @return	List<Map<Integer, Integer>>
	 */
	public abstract List<Map<Integer, Integer>> checkToolAmountOfOperPerPackage(String orderId, List<Map<String, Integer>> amountByPackage);

	/**
	 * 查询指定单据的手术包列表（横表型式）
	 * @author	黄文君
	 * @date	2017年8月31日 下午5:21:04
	 * @param	rOrgId			需方机构id
	 * @param	sendId			送货单id
	 * @param	headerStyle	表头样式
	 * @param	submitFlag     数据提交标识（submit已提交，draft草稿）
	 * @throws	ValidationException
	 * @return	List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> findPackageListBySendId(Long rOrgId, String sendId, Integer headerStyle, String submitFlag) throws ValidationException;

	/**
	 * 查询手术包列表的表头一行（竖表）
	 * @author	黄文君
	 * @date	2017年8月31日 下午5:18:55
	 * @param	rOrgId			需方机构id
	 * @param	sendId			送货单id
	 * @param	submitFlag     数据提交标识（S已提交，D草稿）
	 * @return	List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> findHeaderForPackageListBySendId(Long rOrgId, String sendId, String submitFlag) throws ValidationException ;

	/**
	 * 查询指定送货单的手术包统计信息
	 * @author	黄文君
	 * @date	2017年9月4日 下午1:57:41
	 * @param	sendId			送货单id
	 * @param	submitFlag     数据提交标识（S已提交，D草稿）
	 * @return	Map<String,Object>
	 */
	public abstract Map<String, Object> subtotalPackageBySendId(String sendId, String submitFlag);

	/**
	 * 查询指定手术包的工具数量
	 * @author	黄文君
	 * @date	2017年9月4日 下午5:45:21
	 * @param	orderId		订单id
	 * @param	packageId	包序号
	 * @return	Integer
	 */
	public abstract Integer countToolsByPackageId(String orderId, Integer packageId, String submitFlag);

	/**
	 * 指定手术包批量添加工具
	 * @author	黄文君
	 * @date	2017年9月1日 下午3:49:54
	 * @param	rOrgId
	 * @param	sendId
	 * @param	toolList
	 * @return	int
	 */
	public abstract int mergeToolList4Oper(Long rOrgId, String sendId, Integer packageId, List<PackageToolDto> toolList);

	/**
	 * 指定手术包批量添加工具（从指定的模板手术包）
	 * @author	黄文君
	 * @date	2017年9月1日 下午3:49:54
	 * @param	rOrgId
	 * @param	sendId
	 * @param	templatePackagesList
	 * @return	int
	 */
	public abstract int mergeToolListFromTemplatePackages(Long rOrgId, String sendId, List<TemplatePackagesDto> templatePackagesList);

	/**
	 * 指定手术包删除多个工具
	 * @author	黄文君
	 * @date	2017年9月5日 下午1:56:01
	 * @param	orderId
	 * @param	packageId
	 * @param	toolCodes
	 * @return	void
	 */
	public abstract void deleteToolsFromPackage(String orderId, Integer packageId, String[] toolCodes);

	/**
	 * 查询指定包的工具列表（已选、未选）
	 * @author	黄文君
	 * @date	2017年9月5日 下午3:24:14
	 * @param	pager
	 * @return	List<PackageTool>
	 */
	public abstract List<PackageTool> findAllToolsByPackageId(Pager<PackageTool> pager);

	/**
	 * 查找指定手术包的工具列表
	 * @param pager
	 * @return List<PackageTool>
	 */
	public abstract List<PackageTool> findToolsOfPackageId(Pager<PackageTool> pager);

	/**
	 * 清理指定手术包内手术器械,支持多个包清理
	 * @param sendId
	 * @param packageIds
	 * @return void
	 */
	public abstract void clearToolsFromPackage(String sendId, Integer[] packageIds);

}

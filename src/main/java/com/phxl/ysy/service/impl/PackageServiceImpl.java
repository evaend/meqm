/**
* 普华信联公司源代码，版权归普华信联公司所有。
*
* 项目名称	：	businessModule
* 创建日期	：	2017年9月4日
* 所在包		：	com.phxl.ysy.businessModule.service.impl
* 文件名称	：	PackageServiceImpl.java
* 修改历史	：
* 1.[2017年9月4日]创建文件 by 黄文君
*/

package com.phxl.ysy.service.impl;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.ysy.dao.DeliveryMapper;
import com.phxl.ysy.dao.DeliveryPackageMapper;
import com.phxl.ysy.dao.PackageToolMapper;
import com.phxl.ysy.entity.PackageTool;
import com.phxl.ysy.service.PackageService;
import com.phxl.ysy.web.dto.PackageToolDto;
import com.phxl.ysy.web.dto.TemplatePackagesDto;
import com.phxl.ysy.annotation.Evaluate;
import com.phxl.ysy.constant.CustomConst.DictName;
import com.phxl.ysy.dao.StaticDataMapper;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 手术包服务
 * @Date	2017年9月4日 上午11:45:37
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
@Service
public class PackageServiceImpl extends BaseService implements PackageService {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	DeliveryMapper			deliveryMapper;
	@Autowired
	StaticDataMapper		staticDataMapper;
	@Autowired
	PackageToolMapper		packageToolMapper;
	@Autowired
	DeliveryPackageMapper	deliveryPackageMapper;

	/**
	 * 查询指定单据的手术包列表
	 * @author	黄文君
	 * @date	2017年8月31日 下午5:21:04
	 * @param	rOrgId			需方机构id
	 * @param	sendId			送货单id
	 * @param	headerStyle	表头样式
	 * @param	submitFlag     数据提交标识（S已提交，D草稿）
	 * @throws	ValidationException
	 * @return	List<Map<String, Object>>
	 */
	@Evaluate
	
	public List<Map<String, Object>> findPackageListBySendId(Long rOrgId, String sendId, Integer headerStyle, String submitFlag) throws ValidationException {
		LocalAssert.notNull(rOrgId, "需方机构id，不能为空");
		LocalAssert.notEmpty(sendId, "送货单id，不能为空");

		Pager<Map<String, Object>> coditions = new Pager<Map<String, Object>>(false);
		coditions.addQueryParam("orgId", rOrgId);
		coditions.addQueryParam("tfClo", DictName.GK_ATTRIBUTE);
		//查询全部骨科产品属性
		List<Map<String, String>> gkAttributes = staticDataMapper.findStaticDataList(coditions);
		if(CollectionUtils.isNotEmpty(gkAttributes)){
			//查找某个送货单的手术包的编号列表
			List<String> packageIds = deliveryPackageMapper.findPackageIdsBySendId(sendId, submitFlag);
			//查询指定单据的手术包列表（横表型式）
			return deliveryPackageMapper.findPackageListBySendId(sendId, gkAttributes, packageIds, headerStyle, submitFlag);
		}
		return null;
	}

	/**
	 * 查询手术包列表的表头一行（竖表）
	 * @author	黄文君
	 * @date	2017年8月31日 下午5:18:55
	 * @param	rOrgId			需方机构id
	 * @param	sendId			送货单id
	 * @param	submitFlag     数据提交标识（S已提交，D草稿）
	 * @return	List<Map<String, Object>>
	 */
	
	public List<Map<String, Object>> findHeaderForPackageListBySendId(Long rOrgId, String sendId, String submitFlag) throws ValidationException {
		LocalAssert.notNull(rOrgId, "需方机构id，不能为空");
		LocalAssert.notEmpty(sendId, "送货单id，不能为空");

		Pager<Map<String, Object>> coditions = new Pager<Map<String, Object>>(false);
		coditions.addQueryParam("orgId", rOrgId);
		coditions.addQueryParam("tfClo", DictName.GK_ATTRIBUTE);
		//查询全部骨科产品属性
		List<Map<String, String>> gkAttributes = staticDataMapper.findStaticDataList(coditions);
		if(CollectionUtils.isNotEmpty(gkAttributes)) {
			//查询手术包列表的表头一行（竖表）
			return deliveryPackageMapper.findHeaderForPackageListBySendId(sendId, gkAttributes, submitFlag);
		}
		return null;
	}

	/**
	 * 查询指定送货单的手术包统计信息
	 * @author	黄文君
	 * @date	2017年9月4日 下午1:57:41
	 * @param	sendId			送货单id
	 * @param	submitFlag     数据提交标识（S已提交，D草稿）
	 * @return	Map<String,Object>
	 */
	
	public Map<String, Object> subtotalPackageBySendId(String sendId, String submitFlag) {
		return deliveryPackageMapper.subtotalPackageBySendId(sendId, submitFlag);
	}

	/**
	 * 查询指定手术包的工具数量
	 * @author	黄文君
	 * @date	2017年9月4日 下午5:45:21
	 * @param	orderId		订单id
	 * @param	packageId	包序号
	 * @return	Integer
	 */
	
	public Integer countToolsByPackageId(String orderId, Integer packageId, String submitFlag) {
		Integer count = packageToolMapper.countToolsByPackageId(orderId, packageId, submitFlag);
		return count==null ? 0 : count;
	}
	
	/**
	 * 指定手术包删除多个工具
	 * @author	黄文君
	 * @date	2017年9月5日 下午1:56:01
	 * @param	orderId
	 * @param	packageId
	 * @param	toolCodes
	 * @return	void
	 */
	
	public void deleteToolsFromPackage(String orderId, Integer packageId, String[] toolCodes) {
		packageToolMapper.deleteToolsFromPackage(orderId, packageId, toolCodes);
	}

	/**
	 * 指定手术包批量添加工具
	 * @author	黄文君
	 * @date	2017年9月1日 下午3:49:54
	 * @param	sendId
	 * @param	toolList
	 * @return	int
	 */
	
	public int mergeToolList4Oper(Long rOrgId, String sendId, Integer packageId, List<PackageToolDto> toolList) {
		return packageToolMapper.mergeToolList4Oper(rOrgId, sendId, packageId, toolList);
	}

	/**
	 * 指定手术包批量添加工具（从指定的模板手术包）
	 * @author	黄文君
	 * @date	2017年9月1日 下午3:49:54
	 * @param	rOrgId
	 * @param	sendId
	 * @param	templatePackagesList
	 * @return	int
	 */
	
	public int mergeToolListFromTemplatePackages(Long rOrgId, String sendId, List<TemplatePackagesDto> templatePackagesList) {
		return packageToolMapper.mergeToolListFromTemplatePackages(rOrgId, sendId, templatePackagesList);
	}

	/**
	 * 查询指定包的工具列表（已选、未选）
	 * @author	黄文君
	 * @date	2017年9月5日 下午3:24:14
	 * @param	pager
	 * @return	List<PackageTool>
	 */
	
	public List<PackageTool> findAllToolsByPackageId(Pager<PackageTool> pager) {
		return packageToolMapper.findAllToolsByPackageId(pager);
	}

	/**
	 * 查找指定手术包的工具列表
	 * @param pager
	 * @return List<PackageTool>
	 */
	
	public List<PackageTool> findToolsOfPackageId(Pager<PackageTool> pager){
		return packageToolMapper.findToolsByPackageId(pager);
	}

	/**
	 * 手术包产品类型数量比较
	 * @param   sendId
	 * @return  List<Map<String, Object>>
	 */
	public List<Map<String, Object>> checkAttributeAmountOfOperPackage(String sendId){
		if(deliveryPackageMapper.countPackageListBySendId(sendId)) {
			return deliveryPackageMapper.checkAttributeAmountOfOperPackage(sendId);
		} else {
			return null;
		}
	}

	/**
	 * 手术包手术器械数量比较
	 * @param   sendId
	 * @return  List<Map<String, Object>>
	 */
	public List<Map<String, Object>> checkToolAmountOfOperPackage(String sendId){
		if(packageToolMapper.countPackageToolListBySendId(sendId)) {
			return deliveryPackageMapper.checkToolAmountOfOperPackage(sendId);
		} else {
			return null;
		}
	}

	/**
	 * 核对每个手术包手术器械的数量
	 * @author	黄文君
	 * @date	2017年10月10日
	 * @param	sendId
	 * @param	amountByPackage
	 * @return	List<Map<Integer, Integer>>
	 */
	
	public List<Map<Integer, Integer>> checkToolAmountOfOperPerPackage(String sendId, List<Map<String, Integer>> amountByPackage) {
		return packageToolMapper.checkToolAmountOfOperPerPackage(sendId, amountByPackage);
	}

	/**
	 * 清理指定手术包内手术器械,支持多个包清理
	 * @param sendId
	 * @param packageIds
	 * @return void
	 */
	
	public void clearToolsFromPackage(String sendId, Integer[] packageIds) {
		for(Integer packageId: packageIds) {
			packageToolMapper.clearToolsFromPackage(sendId, packageId);
			logger.debug("送货单（{}）第{}号包已经清理。", sendId, packageId);
		}
	}
}

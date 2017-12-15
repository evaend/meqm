/**
* 普华信联公司源代码，版权归普华信联公司所有。
*
* 项目名称	：	businessModule
* 创建日期	：	2017年9月1日
* 所在包		：	com.phxl.ysy.web
* 文件名称	：	PackageController.java
* 修改历史	：
* 1.[2017年9月1日]创建文件 by 黄文君
*/

package com.phxl.ysy.web;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.ysy.entity.Delivery;
import com.phxl.ysy.entity.Order;
import com.phxl.ysy.entity.PackageTool;
import com.phxl.ysy.service.OrderService;
import com.phxl.ysy.service.PackageService;
import com.phxl.ysy.web.dto.AddToolsByPackageDto;
import com.phxl.ysy.web.dto.AddToolsFromTemplatePackagesDto;
import com.phxl.ysy.web.dto.PackageToolDto;
import com.phxl.ysy.web.dto.TemplatePackagesDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 手术包接口
 * @Date	2017年9月1日 下午3:52:26
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
@Controller
@RequestMapping("/delivery/package")
public class PackageController extends DeliveryController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	PackageService		packageService;
	@Autowired
	OrderService		orderService;
	@Autowired
	OperOrderController operOrderController;

	/**
	 * [订单模块]查询指定订单的手术包列表
	 * @author	黄文君
	 * @date	2017年8月31日 下午1:47:14
	 * @param	orderId		    订单id
	 * @param	headerStyle	表头样式(1：列名称+数量, 0：列名称)
	 * @param	submitFlag     数据提交标识（S已提交，D草稿）
	 * @throws	ValidationException
	 * @return	List<Map<String,Object>>
	 */
	@ResponseBody
	@RequestMapping("/findPackageListByOrderId")
	public List<Map<String, Object>> findPackageListByOrderId(String orderId, Integer headerStyle, String submitFlag) throws ValidationException {
		LocalAssert.notBlank(orderId, "订单Id，不能为空");
		LocalAssert.notBlank(submitFlag, "数据提交标识，不能为空");
		LocalAssert.contain(new String[]{"S","D"}, submitFlag, "数据提交标识:只能是S或D");

		Order order = packageService.find(Order.class, orderId);
		LocalAssert.notNull(order, "指定的订单（"+orderId+"）不存在!");
		
		//查询指定订单的手术包列表
		return packageService.findPackageListBySendId(order.getrOrgId(), orderId, headerStyle, submitFlag);
	}
	
	/**
	 * [送货单模块]查询指定送货单的手术包列表
	 * @author	黄文君
	 * @date	2017年8月31日 下午1:47:14
	 * @param	sendId		送货单id
	 * @throws	ValidationException
	 * @return	List<Map<String,Object>>
	 */
	@ResponseBody
	@RequestMapping("/findPackageListBySendId")
	public List<Map<String, Object>> findPackageListBySendId(String sendId) throws ValidationException {
		LocalAssert.notBlank(sendId, "送货单Id，不能为空");

		Delivery delivery = packageService.find(Delivery.class, sendId);
		LocalAssert.notNull(delivery, "指定的送货单（"+sendId+"）不存在!");
		
		//查询指定送货单的手术包列表
		return packageService.findPackageListBySendId(delivery.getrOrgId(), sendId, null, "S");
	}

	/**
	 * 查询手术包列表的表头一行（竖表）
	 * @author	黄文君
	 * @date	2017年8月31日 下午1:47:14
	 * @param	orderId		送货单id
	 * @throws	ValidationException
	 * @return	List<Map<String, Object>>
	 */
	@ResponseBody
	@RequestMapping("/findHeaderForPackageListBySendId")
	public List<Map<String, Object>> findHeaderForPackageListBySendId(String orderId) throws ValidationException {
		LocalAssert.notBlank(orderId, "订单Id，不能为空");

		Order order = packageService.find(Order.class, orderId);
		LocalAssert.notNull(order, "指定的订单（"+orderId+"）不存在!");

		//查询手术包列表的表头一行（竖表）
		return packageService.findHeaderForPackageListBySendId(order.getrOrgId(), orderId, "D");//数据提交标识:D
	}
	
	/**
	 * [订单模块]查询指定订单的手术包统计信息<p>
	 * @author	黄文君
	 * @date	2017年9月4日 上午11:20:16
	 * @param	orderId     订单id
	 * @param	submitFlag  数据提交标识（S已提交，D草稿）
	 * @throws	ValidationException
	 * @return	Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping("/subtotalPackageByOrderId")
	public Map<String, Object> subtotalPackageByOrderId(String orderId, String submitFlag) throws ValidationException {
		LocalAssert.notBlank(orderId, "订单Id，不能为空");
		LocalAssert.notBlank(submitFlag, "数据提交标识，不能为空");
		LocalAssert.contain(new String[]{"S","D"}, submitFlag, "数据提交标识:只能是S或D");

		return packageService.subtotalPackageBySendId(orderId, submitFlag);
	}
	
	/**
	 * [送货单模块]查询指定送货单的手术包统计信息<p>
	 * @author	黄文君
	 * @date	2017年9月4日 上午11:20:16
	 * @param	sendId
	 * @throws	ValidationException
	 * @return	Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping("/subtotalPackageBySendId")
	public Map<String, Object> subtoalPackageBySendId(String sendId) throws ValidationException {
		LocalAssert.notBlank(sendId, "送货单Id，不能为空");
		return packageService.subtotalPackageBySendId(sendId, "S");
	}
	
	/**
	 * 指定手术包批量添加工具
	 * @author	黄文君
	 * @date	2017年9月1日 下午3:56:15
	 * @param	dto		    手术包工具信息
	 * @return	void
	 * @throws	ValidationException  
	 */
	@RequestMapping("/addToolsByPackage")
	@ResponseBody
	public void mergeToolList4Oper(@RequestBody AddToolsByPackageDto dto, HttpSession session) throws ValidationException {
		LocalAssert.notNull(dto, "请求参数，不能为空");
		LocalAssert.notBlank(dto.getOrderId(), "订单id，不能为空");
		LocalAssert.notNull(dto.getPackageId(), "包序号，不能为空");
		LocalAssert.notEmpty(dto.getToolList(), "工具列表，不能为空");
		//检查:工具编码，数量
		for(PackageToolDto tool: dto.getToolList()){
			LocalAssert.notBlank(tool.getToolCode(), "包工具：工具编码，不能为空");
			LocalAssert.notNull(tool.getTfAmount(), "包工具：数量，不能为空");
			LocalAssert.intGreaterThan(tool.getTfAmount().intValue(), 0, "手术包：数量，必需大于0");
			tool.setPackageId(dto.getPackageId()); 
		}
		Order order = orderService.find(Order.class, dto.getOrderId());
		Assert.notNull(order, "订单（"+dto.getOrderId()+"）不存在");
		//手术订单备货期间的基本检查
		operOrderController.checkPermissionSettleGoodsForOper(session, order, "批量添加包工具");
		//指定手术包添加工具
		packageService.mergeToolList4Oper(order.getrOrgId(), dto.getOrderId(), dto.getPackageId(), dto.getToolList());
	}

	/**
	 * 指定手术包批量添加工具（从指定的模板手术包）
	 * @author	黄文君
	 * @date	2017年9月11日 下午
	 * @param	dto		    包工具列表
	 * @return	void
	 * @throws	ValidationException
	 */
	@RequestMapping("/addToolsFromTemplatePackages")
	@ResponseBody
	public void mergeToolListFromTemplatePackages(@RequestBody AddToolsFromTemplatePackagesDto dto, HttpSession session) throws ValidationException {
		LocalAssert.notNull(dto, "请求参数，不能为空");
		LocalAssert.notBlank(dto.getOrderId(), "订单id，不能为空");
		LocalAssert.notEmpty(dto.getFromPackageList(), "模板包列表，不能为空");

		for(TemplatePackagesDto tp: dto.getFromPackageList()){
			LocalAssert.notNull(tp, "不允许空包");
			LocalAssert.notBlank(tp.getTemplateGuid(), "模板guid，不能为空");
			LocalAssert.notNull(tp.getToPackageId(), "手术包序号，不能为空");
			LocalAssert.notNull(tp.getFromPackageId(), "fromPackageId，不能为空");
		}
		Order order = orderService.find(Order.class, dto.getOrderId());
		Assert.notNull(order, "订单（"+dto.getOrderId()+"）不存在");
		//手术订单备货期间的基本检查
		operOrderController.checkPermissionSettleGoodsForOper(session, order, "批量添加包工具（从模板）");
		//指定手术包批量添加工具（从指定的模板手术包）
		packageService.mergeToolListFromTemplatePackages(order.getrOrgId(), dto.getOrderId(), dto.getFromPackageList());
	}
	
	/**
	 * 指定手术包删除多个工具
	 * @author	黄文君
	 * @date	2017年9月1日 下午3:56:15
	 * @param	orderId		订单id
	 * @param	packageId	包序号
	 * @param	toolCodes	工具列表
	 * @return	void
	 * @throws	ValidationException  
	 */
	@RequestMapping("/deleteToolsFromPackage")
	@ResponseBody
	public void deleteToolsFromPackage(String orderId, Integer packageId, String toolCodes, HttpSession session) throws ValidationException {
		LocalAssert.notBlank(orderId, "订单id，不能为空");
		LocalAssert.notNull(packageId, "包序号，不能为空");
		LocalAssert.notBlank(toolCodes, "工具，不能为空");
		String[] toolCodeList = toolCodes.split(",");
		LocalAssert.notEmpty(toolCodeList, "工具列表，不能为空");
		Order order = orderService.find(Order.class, orderId);
		Assert.notNull(order, "订单（"+orderId+"）不存在");
		//手术订单备货期间的基本检查
		operOrderController.checkPermissionSettleGoodsForOper(session, order, "删除指定包的工具");
		//指定手术包删除多个工具
		packageService.deleteToolsFromPackage(orderId, packageId, toolCodeList);
	}
	
	/**
	 * 清理指定一个手术包内的工具<p>
	 * 删除一个手术包行的时候调用。
	 * @author	黄文君
	 * @date	2017年9月1日 下午3:56:15
	 * @param	orderId		订单id
	 * @param	packageId	包序号
	 * @return	void
	 * @throws	ValidationException  
	 */
	@RequestMapping("/clearToolsByPackage")
	@ResponseBody
	public void clearToolsFromPackage(String orderId, Integer[] packageId, HttpSession session) throws ValidationException {
		LocalAssert.notBlank(orderId, "订单id，不能为空");
		LocalAssert.notEmpty(packageId, "包序号，不能为空");
		Order order = orderService.find(Order.class, orderId);
		Assert.notNull(order, "订单（"+orderId+"）不存在");
		//手术订单备货期间的基本检查
		operOrderController.checkPermissionSettleGoodsForOper(session, order, "清空指定包的工具");
		//清理指定一个手术包内手术器械
		packageService.clearToolsFromPackage(orderId, packageId);
	}
	
	/**
	 * 查询指定手术包的工具数量
	 * @author	黄文君
	 * @date	2017年9月4日 下午1:38:54
	 * @param	orderId		订单id
	 * @param	packageId	包序号
	 * @throws	ValidationException
	 * @return	Integer
	 */
	@RequestMapping("/countToolsByPackage")
	@ResponseBody
	public Integer countToolsByPackageId(String orderId, Integer packageId, String submitFlag) throws ValidationException {
		LocalAssert.notBlank(orderId, "订单id，不能为空");
		LocalAssert.notNull(packageId, "包序号，不能为空");
		LocalAssert.notBlank(submitFlag, "数据提交标识，不能为空");
		LocalAssert.contain(new String[]{"S","D"}, submitFlag, "数据提交标识:只能是S或D");

		//查询指定手术包的工具数量
		return packageService.countToolsByPackageId(orderId, packageId, submitFlag);
	}
	
	/**
	 * 查询指定包的工具列表（已选）
	 * @author	黄文君
	 * @date	2017年9月4日 上午11:33:52
	 * @param	orderId			订单id
	 * @param	packageId		包序号
	 * @throws	ValidationException
	 * @return	void
	 */
	@RequestMapping("/findToolsOfPackageId")
	@ResponseBody
	public List<PackageTool> findToolsOfPackageId(String orderId,
	                                              Integer packageId,
	                                              String submitFlag) throws ValidationException {
		LocalAssert.notBlank(orderId, "订单id，不能为空");
		LocalAssert.notNull(packageId, "包序号，不能为空");
		LocalAssert.notBlank(submitFlag, "数据提交标识，不能为空");
		LocalAssert.contain(new String[]{"S","D"}, submitFlag, "数据提交标识:只能是S或D");

		PackageTool condition = new PackageTool();
		condition.setSendId(orderId);
		condition.setPackageId(packageId);

		Pager<PackageTool> pager = new Pager<PackageTool>(false);
		pager.addQueryParam("sendId", orderId);
		pager.addQueryParam("packageId", packageId);
		pager.addQueryParam("submitFlag", submitFlag);
		return packageService.findToolsOfPackageId(pager);
	}
	
	/**
	 * 查询指定包的工具列表（已选、未选）
	 * @author	黄文君
	 * @date	2017年9月4日 上午11:33:52
	 * @param	orderId			订单id
	 * @param	packageId		包序号
	 * @throws	ValidationException
	 * @return	void
	 */
	@RequestMapping("/findAllTools")
	@ResponseBody
	public List<PackageTool> findAllToolsByPackageId(String orderId,
	                                                 String packageId,
	                                                 String toolNameLike,
	                                                 String submitFlag,
	                                                 Integer pagesize,
	                                                 Integer page) throws ValidationException {
		LocalAssert.notBlank(orderId, "订单id，不能为空");
		LocalAssert.notBlank(packageId, "包序号，不能为空");
		LocalAssert.notBlank(submitFlag, "数据提交标识，不能为空");
		LocalAssert.contain(new String[]{"S","D"}, submitFlag, "数据提交标识:只能是S或D");
		
		Order order = packageService.find(Order.class, orderId);
		LocalAssert.notNull(order, "订单（"+orderId+"）不存在");
		
		Pager<PackageTool> pager = new Pager<PackageTool>(false);
		//pager.setPageSize(pagesize);
		//pager.setPageNum(page);
		
		pager.addQueryParam("rOrgId", order.getrOrgId());
		pager.addQueryParam("sendId", orderId);
		pager.addQueryParam("packageId", packageId);
		pager.addQueryParam("toolNameLike", toolNameLike);
		pager.addQueryParam("submitFlag", submitFlag);
		//查询指定包的工具列表（已选、未选）
		List<PackageTool> toolList = packageService.findAllToolsByPackageId(pager);
		return toolList;
	}

}

package com.phxl.ysy.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.util.DateUtils;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.ysy.entity.OrgInfo;
import com.phxl.ysy.entity.StorageInfo;
import com.phxl.ysy.dto.PatientOperDto;
import com.phxl.ysy.entity.ChangeFstate;
import com.phxl.ysy.entity.Delivery;
import com.phxl.ysy.entity.DeliveryDetail;
import com.phxl.ysy.service.DeliveryService;
import com.phxl.ysy.service.PackageService;
import com.phxl.ysy.service.TreatmentOperService;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.constant.CustomConst.DeliveryFstate;
import com.phxl.ysy.constant.CustomConst.DeliveryType;
import com.phxl.ysy.constant.CustomConst.DictName;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.util.CreateHtml;
import com.phxl.ysy.util.ExportUtil;
import com.phxl.ysy.service.StaticDataService;

/**
 * 送货单管理，送货单导出和打印 2017年6月15日 下午2:56:47
 * 
 * @author 陶悠
 *
 */
@Controller
@RequestMapping("delivery")
public class DeliveryController {
	@Autowired
	DeliveryService deliveryService;
	@Autowired
	TreatmentOperService treatmentOperService;
	@Autowired
	StaticDataService staticDataService;
	@Autowired
	PackageService packageService;

	/**
	 * 采购模块查询送货单列表
	 */
	@RequestMapping("rSearchDeliveryList")
	@ResponseBody
	public Pager<Map<String, Object>> rSearchDeliveryList(
			@RequestParam(value = "fOrgId", required = false) String fOrgId,
			@RequestParam(value = "rStorageGuid", required = false) String rStorageGuid,
			@RequestParam(value = "orderType", required = false) String orderType,
			@RequestParam(value = "sendFstates", required = false) String[] sendFstates,
			@RequestParam(value = "sendStartDate", required = false) String sendStartDate,
			@RequestParam(value = "sendEndDate", required = false) String sendEndDate,
			@RequestParam(value = "orderNo", required = false) String orderNo,
			@RequestParam(value = "sendNo", required = false) String sendNo,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, HttpServletRequest request) {
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(true);
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.setPageNum(page == null ? 1 : page);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 年-月-日格式化
		// 采购模块当前登录机构是需求机构
		Long orgId = (Long) request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID);
		if (StringUtils.isNotBlank(sendStartDate)) {
			pager.addQueryParam("sendStartDate", sendStartDate);
		} else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			pager.addQueryParam("sendStartDate", formatter.format(cal.getTime()));// 默认上个月开始
		}

		if (StringUtils.isNotBlank(sendEndDate)) {
			pager.addQueryParam("sendEndDate", sendEndDate);
		} else {
			Calendar cal1 = Calendar.getInstance();
			pager.addQueryParam("sendEndDate", formatter.format(cal1.getTime()));// 默认当前时间
		}

		pager.addQueryParam("rOrgId", orgId);// 采购模块当前登录机构是需求机构
		pager.addQueryParam("fOrgId", fOrgId);// 供方机构
		pager.addQueryParam("rStorageGuid", rStorageGuid);// 需方库房
		pager.addQueryParam("orderType", orderType);// 送货单类型
		if (sendFstates != null && sendFstates.length != 0) {
			if (StringUtils.isNotBlank(sendFstates[0]))
				pager.addQueryParam("sendFstates", sendFstates);// 送货单状态
		}
		pager.addQueryParam("excludeFstates", new String[] { DeliveryFstate.AWAIT_SEND });// 排除:待发货
		pager.addQueryParam("orderNo", orderNo);// 订单号
		pager.addQueryParam("sendNo", sendNo);// 送货单号
		pager.addQueryParam("orderId", orderId);// 订单id

		List<Map<String, Object>> deliveryList = deliveryService.searchDeliveryList(pager);
		pager.setRows(deliveryList);

		return pager;
	}

	/**
	 * 销售模块查询送货单列表
	 */
	@RequestMapping("fSearchDeliveryList")
	@ResponseBody
	public Pager<Map<String, Object>> fSearchDeliveryList(
			@RequestParam(value = "rOrgId", required = false) String rOrgId,
			@RequestParam(value = "fStorageGuid", required = false) String fStorageGuid,
			@RequestParam(value = "orderType", required = false) String orderType,
			@RequestParam(value = "sendFstates", required = false) String[] sendFstates,
			@RequestParam(value = "sendStartDate", required = false) String sendStartDate,
			@RequestParam(value = "sendEndDate", required = false) String sendEndDate,
			@RequestParam(value = "orderNo", required = false) String orderNo,
			@RequestParam(value = "sendNo", required = false) String sendNo,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, HttpServletRequest request) {
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(true);
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.setPageNum(page == null ? 1 : page);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 年-月-日格式化
		// 销售模块当前登录机构是供应机构
		Long orgId = (Long) request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID);
		if (StringUtils.isNotBlank(sendStartDate)) {
			pager.addQueryParam("sendStartDate", sendStartDate);
		} else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			pager.addQueryParam("sendStartDate", formatter.format(cal.getTime()));// 默认上个月开始
		}

		if (StringUtils.isNotBlank(sendEndDate)) {
			pager.addQueryParam("sendEndDate", sendEndDate);
		} else {
			Calendar cal1 = Calendar.getInstance();
			pager.addQueryParam("sendEndDate", formatter.format(cal1.getTime()));// 默认当前时间
		}

		pager.addQueryParam("fOrgId", orgId);// 销售模块当前登录机构是供应机构
		pager.addQueryParam("rOrgId", rOrgId);// 需方机构，默认是全部
		pager.addQueryParam("fStorageGuid", fStorageGuid);// 供方库房
		pager.addQueryParam("orderType", orderType);// 送货单类型

		if (sendFstates != null && sendFstates.length != 0) {
			if (StringUtils.isNotBlank(sendFstates[0]))
				pager.addQueryParam("sendFstates", sendFstates);// 送货单状态
		}
		pager.addQueryParam("orderNo", orderNo);// 订单号
		pager.addQueryParam("sendNo", sendNo);// 送货单号
		pager.addQueryParam("orderId", orderId);// 订单id

		List<Map<String, Object>> deliveryList = deliveryService.searchDeliveryList(pager);
		pager.setRows(deliveryList);

		return pager;
	}

	/**
	 * 根据送货单id查询送货单产品列表
	 * 
	 * @throws ValidationException
	 */
	@ResponseBody
	@RequestMapping("/deliveryDetails")
	public Pager<Map<String, Object>> deliveryDetails(@RequestParam(value = "sendId", required = false) String sendId,
			@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, HttpServletRequest request)
			throws ValidationException {

		LocalAssert.notBlank(sendId, "送货单Id不能为空");

		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(false);
		// pager.setPageSize(pagesize==null?15:pagesize);//现在不分页
		// pager.setPageNum(page==null?1:page);//现在不分页

		pager.addQueryParam("sendId", sendId);
		// pager.addQueryParam("searchParams", searchParams);//查询参数目前没有

		List<Map<String, Object>> details = deliveryService.selectdeliveryDetails(pager);
		pager.setRows(details);
		return pager;
	}

	/**
	 * 根据送货单id查询送货单产品列表(手术送货单专用)
	 * 
	 * @author 黄文君
	 * @param sendId
	 *            送货单
	 * @param submitFlag
	 *            数据提交标识（S已提交，D草稿）
	 * @param attributeId
	 *            产品分类编码
	 * @param tfBrand
	 *            品牌编码
	 * @param searchName
	 *            模糊搜索关键字
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findDetailList4OperBySendId")
	public List<Map<String, Object>> findDetailList4OperBySendId(String sendId, String submitFlag, String attributeId,
			String tfBrand, String searchName) throws Exception {
		LocalAssert.notBlank(sendId, "送货单Id，不能为空");
		LocalAssert.notBlank(submitFlag, "数据提交标识，不能为空");
		LocalAssert.contain(new String[] { "S", "D" }, submitFlag, "数据提交标识:只能是S或D");

		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(false);
		pager.addQueryParam("sendId", sendId);
		pager.addQueryParam("submitFlag", submitFlag);
		pager.addQueryParam("attributeId", attributeId);
		pager.addQueryParam("tfBrand", tfBrand);
		pager.addQueryParam("searchName", searchName);
		return deliveryService.findDetailList4OperBySendId(pager);
	}

	/**
	 * 送货单发货 待发货->待验收
	 * 
	 * @throws ValidationException
	 */
	@ResponseBody
	@RequestMapping(value = "shipmentDelivery")
	public void shipmentDelivery(@RequestParam(value = "sendId", required = false) String sendId,
			HttpServletRequest request) throws ValidationException {

		LocalAssert.notBlank(sendId, "送货单Id不能为空");
		// 操作人id
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		// 操作人name
		String userName = (String) request.getSession().getAttribute(LoginUser.SESSION_USERNAME);

		// 送货单状态变更
		Delivery deliver = new Delivery();
		deliver.setSendId(sendId);
		deliver.setFstate(DeliveryFstate.AWAIT_CHECK);
		// 状态变更信息
		ChangeFstate changeFstate = new ChangeFstate();
		changeFstate.setBeforFstate(DeliveryFstate.AWAIT_SEND);
		changeFstate.setAfterFstate(DeliveryFstate.AWAIT_CHECK);
		changeFstate.setModifyUserid(userId);
		changeFstate.setModifyUsername(userName);
		// 更改状态并记录状态变更信息
		deliveryService.updateDeliveryFstate(deliver, changeFstate);
	}

	/**
	 * 采购模块送货单列表导出（需求方）
	 * 
	 * @throws Exception
	 */
	@RequestMapping("rExportDeliveryList")
	@ResponseBody
	public void rExportDeliveryList(@RequestParam(value = "fOrgId", required = false) String fOrgId,
			@RequestParam(value = "rStorageGuid", required = false) String rStorageGuid,
			@RequestParam(value = "rStorageName", required = false) String rStorageName,
			@RequestParam(value = "orderType", required = false) String orderType,
			@RequestParam(value = "sendFstates", required = false) String[] sendFstates,
			@RequestParam(value = "sendStartDate", required = false) String sendStartDate,
			@RequestParam(value = "sendEndDate", required = false) String sendEndDate,
			@RequestParam(value = "orderNo", required = false) String orderNo,
			@RequestParam(value = "sendNo", required = false) String sendNo,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "total", required = false) String total, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(false);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 年-月-日格式化
		List<String> fieldName = Arrays.asList("sendNo", "orderNo", "tfAddress", "fOrgName", "sendUsername", "sendDate",
				"fstateName", "checkUserName", "checkTime");

		// 采购模块当前登录机构是需求机构
		Long orgId = (Long) request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID);
		if (StringUtils.isNotBlank(sendStartDate)) {
			pager.addQueryParam("sendStartDate", sendStartDate);
		} else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			sendStartDate = formatter.format(cal.getTime());
			pager.addQueryParam("sendStartDate", sendStartDate);// 默认上个月开始
		}

		if (StringUtils.isNotBlank(sendEndDate)) {
			pager.addQueryParam("sendEndDate", sendEndDate);
		} else {
			Calendar cal1 = Calendar.getInstance();
			sendEndDate = formatter.format(cal1.getTime());
			pager.addQueryParam("sendEndDate", sendEndDate);// 默认当前时间
		}

		pager.addQueryParam("rOrgId", orgId);// 采购模块当前登录机构是需求机构
		pager.addQueryParam("fOrgId", fOrgId);// 供方机构
		pager.addQueryParam("rStorageGuid", rStorageGuid);// 需方库房
		pager.addQueryParam("orderType", orderType);// 送货单类型
		if (sendFstates != null && sendFstates.length != 0) {
			if (StringUtils.isNotBlank(sendFstates[0]))
				pager.addQueryParam("sendFstates", sendFstates);// 送货单状态
		}
		pager.addQueryParam("orderNo", orderNo);// 订单号
		pager.addQueryParam("sendNo", sendNo);// 送货单号
		pager.addQueryParam("orderId", orderId);// 订单id

		List<Map<String, Object>> delivery = deliveryService.searchDeliveryList(pager);

		String nowDay = DateUtils.DateToStr(new Date(), "yyyy-MM-dd");
		String fileName = nowDay + "-送货单";
		List<String> conditionBefore = Arrays.asList(
				"库房：" + (rStorageName == null ? "全部" : rStorageName) + ",制单时间：" + sendStartDate + " 至 " + sendEndDate
						+ ",导出时间：" + DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
				"送货单总数量：" + (delivery == null ? "" : delivery.size()));

		ExportUtil.exportExcel(response, fieldName, delivery, null, "送  货  单  记  录", conditionBefore, null, fileName,
				CustomConst.DeliveryExcelMap);
	}

	/**
	 * 销售模块送货单列表导出
	 * 
	 * @throws Exception
	 */
	@RequestMapping("fExportDeliveryList")
	@ResponseBody
	public void fExportDeliveryList(@RequestParam(value = "rOrgId", required = false) String rOrgId,
			@RequestParam(value = "fStorageGuid", required = false) String fStorageGuid,
			@RequestParam(value = "rStorageName", required = false) String rStorageName,
			@RequestParam(value = "orderType", required = false) String orderType,
			@RequestParam(value = "sendFstates", required = false) String[] sendFstates,
			@RequestParam(value = "sendStartDate", required = false) String sendStartDate,
			@RequestParam(value = "sendEndDate", required = false) String sendEndDate,
			@RequestParam(value = "orderNo", required = false) String orderNo,
			@RequestParam(value = "sendNo", required = false) String sendNo,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "total", required = false) String total, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(false);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 年-月-日格式化
		List<String> fieldName = Arrays.asList("sendNo", "orderNo", "tfAddress", "rOrgName", "sendUsername", "sendDate",
				"fstateName", "checkUserName", "checkTime");
		// 销售模块当前登录机构是供应机构
		Long orgId = (Long) request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID);
		if (StringUtils.isNotBlank(sendStartDate)) {
			pager.addQueryParam("sendStartDate", sendStartDate);
		} else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			sendStartDate = formatter.format(cal.getTime());
			pager.addQueryParam("sendStartDate", sendStartDate);// 默认上个月开始
		}

		if (StringUtils.isNotBlank(sendEndDate)) {
			pager.addQueryParam("sendEndDate", sendEndDate);
		} else {
			Calendar cal1 = Calendar.getInstance();
			sendEndDate = formatter.format(cal1.getTime());
			pager.addQueryParam("sendEndDate", sendEndDate);// 默认当前时间
		}

		pager.addQueryParam("fOrgId", orgId);// 销售模块当前登录机构是供应机构
		pager.addQueryParam("rOrgId", rOrgId);// 需方机构，默认是全部
		pager.addQueryParam("fStorageGuid", fStorageGuid);// 供方库房
		pager.addQueryParam("orderType", orderType);// 送货单类型
		if (sendFstates != null && sendFstates.length != 0) {
			if (StringUtils.isNotBlank(sendFstates[0]))
				pager.addQueryParam("sendFstates", sendFstates);// 送货单状态
		}
		pager.addQueryParam("orderNo", orderNo);// 订单号
		pager.addQueryParam("sendNo", sendNo);// 送货单号
		pager.addQueryParam("orderId", orderId);// 订单id
		List<Map<String, Object>> delivery = deliveryService.searchDeliveryList(pager);

		String nowDay = DateUtils.DateToStr(new Date(), "yyyy-MM-dd");
		String fileName = nowDay + "-送货单";

		List<String> conditionBefore = Arrays.asList(
				"库房：" + (rStorageName == null ? "全部" : rStorageName) + ",制单时间：" + sendStartDate + " 至 " + sendEndDate
						+ ",导出时间：" + DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
				"送货单总数量：" + (delivery == null ? "" : delivery.size()));

		ExportUtil.exportExcel(response, fieldName, delivery, null, "送  货  单  记 录", conditionBefore, null, fileName,
				CustomConst.DeliveryExcelMap);

	}

	/**
	 * 送货单详情导出
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/exportDeliveryDetail")
	public void exportDeliveryDetail(@RequestParam(value = "sendId", required = false) String sendId,
			@RequestParam(value = "buyerOrSeller", required = false) String buyerOrSeller, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		LocalAssert.notBlank(sendId, "送货单Id不能为空");
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(false);
		pager.addQueryParam("sendId", sendId);
		// 导出的excel列头，和数据库查询的结果一致
		List<String> fieldName = Arrays.asList("materialName", "geName", "spec", "fmodel", "tenderUnit", "tfPacking",
				"tenderPrice", "amount", "amountMoney", "flot", "prodDate", "usefulDate");

		String nowDay = DateUtils.DateToStr(new Date(), "yyyy-MM-dd");
		String fileName = nowDay + "-送货单详情";

		List<Map<String, Object>> delivery = deliveryService.searchDeliveryList(pager);
		if (delivery == null || delivery.size() == 0) {
			throw new ValidationException("该送货单不存在");
		}
		Map<String, Object> deliveryOne = delivery.get(0);// 送货单主表
		String orgName = "需求机构：," + (deliveryOne.get("rOrgName") == null ? "" : deliveryOne.get("rOrgName").toString());
		if (StringUtils.isNotBlank(buyerOrSeller) && buyerOrSeller.equals("B")) {// 需方导出送货单详情时，显示的是供方机构的名称
			orgName = "供应商：," + (deliveryOne.get("fOrgName") == null ? "" : deliveryOne.get("fOrgName").toString());
		}
		List<String> conditionBefore = Arrays.asList(
				"送货单号：," + (deliveryOne.get("sendNo") == null ? "" : deliveryOne.get("sendNo")) + ",订单号：,"
						+ (deliveryOne.get("orderNo") == null ? "" : deliveryOne.get("orderNo")) + ",收货地址：,"
						+ (deliveryOne.get("tfAddress") == null ? "" : deliveryOne.get("tfAddress")),
				orgName + ",制单人：," + (deliveryOne.get("sendUsername") == null ? "" : deliveryOne.get("sendUsername"))
						+ ",制单时间：," + (deliveryOne.get("sendDate") == null ? "" : deliveryOne.get("sendDate")),
				"送货单状态：," + (deliveryOne.get("fstateName") == null ? "" : deliveryOne.get("fstateName")) + ",验收人：,"
						+ (deliveryOne.get("checkUserName") == null ? "" : deliveryOne.get("checkUserName")) + ",验收时间：,"
						+ (deliveryOne.get("checkTime") == null ? "" : deliveryOne.get("checkTime")) + ",备注：,"
						+ (deliveryOne.get("rejectReson") == null ? "" : deliveryOne.get("rejectReson")));
		// 普耗详情
		List<Map<String, Object>> deliveryDetails = deliveryService.selectdeliveryDetails(pager);

		// 手术信息
		if (DeliveryType.OPER_DELIVERY.equalsIgnoreCase(deliveryOne.get("orderType").toString())) {
			String applyId = deliveryOne.get("applyId") == null ? "" : deliveryOne.get("applyId").toString();
			if (StringUtils.isNotBlank(applyId)) {
				PatientOperDto dto = treatmentOperService.findPatientOperByApplyId(applyId);
				if (dto != null) {
					String operDate = dto.getOperDate() != null ? DateUtils.format(dto.getOperDate(), "yyyy-MM-dd")
							: "";
					String brand = deliveryOne.get("tfBrand") == null ? ""
							: deliveryOne.get("tfBrand").toString().replaceAll(",", "，");
					conditionBefore = new ArrayList<String>(conditionBefore);
					conditionBefore.add("就诊号:," + dto.getTreatmentNo() + ",患者姓名:," + dto.getName() + ",性别:,"
							+ dto.getGender() + ",年龄:," + dto.getAge());
					conditionBefore.add("手术名称:," + dto.getOperName() + ",手术日期:," + operDate + ",品牌:," + brand + ",备注:,"
							+ dto.getTfRemark());
				}
			}
			pager.addQueryParam("submitFlag", "S");
			List<Map<String, Object>> operDetails = deliveryService.findDetailList4OperBySendId(pager);
			deliveryDetails = new ArrayList<Map<String, Object>>();
			for (Object operDetail : operDetails) {
				Map<String, Object> r = new HashMap<String, Object>();
				DeliveryDetail entity = (DeliveryDetail) operDetail;
				r.put("materialName", entity.getMaterialName());
				r.put("geName", entity.getGeName());
				r.put("spec", entity.getSpec());
				r.put("fmodel", entity.getFmodel());
				r.put("tenderUnit", entity.getTenderUnit());
				r.put("tenderPrice", ObjectUtils.defaultIfNull(entity.getTenderPrice(), ""));
				r.put("tfPacking", entity.getTfPacking());
				r.put("amount", entity.getAmount());
				r.put("amountMoney", ObjectUtils.defaultIfNull(entity.getAmountMoney(), ""));
				r.put("flot", entity.getFlot());
				r.put("prodDate", entity.getProdDate());
				r.put("usefulDate", entity.getUsefulDate());
				deliveryDetails.add(r);
			}
		}

		List<String> conditionAfter = Arrays
				.asList("送货单总金额：" + (deliveryOne.get("totalPrice") == null ? "0" : deliveryOne.get("totalPrice")));

		ExportUtil.exportExcel(response, fieldName, deliveryDetails, null, "送  货  单  详 情", conditionBefore,
				conditionAfter, fileName, CustomConst.DeliveryDetailExcelMap);
	}

	/**
	 * 送货单详情打印
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/printDeliveryDetail")
	public void printDeliveryDetail(@RequestParam(value = "sendId", required = false) String sendId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		LocalAssert.notBlank(sendId, "送货单Id不能为空");
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(false);
		pager.addQueryParam("sendId", sendId);
		Map<String, Object> data = new HashMap<String, Object>();

		List<Map<String, Object>> delivery = deliveryService.searchDeliveryList(pager);
		if (delivery == null || delivery.size() == 0) {
			throw new ValidationException("该送货单不存在");
		}
		Map<String, Object> deliveryOne = delivery.get(0);// 送货单主表
		if (DeliveryType.OPER_DELIVERY.equalsIgnoreCase(deliveryOne.get("orderType").toString())) {
			// 手术送货单打印
			Pager<Map<String, Object>> coditions = new Pager<Map<String, Object>>(false);
			coditions.addQueryParam("orgId", deliveryOne.get("rOrgId").toString());
			coditions.addQueryParam("tfClo", DictName.GK_ATTRIBUTE);
			// 查询全部骨科产品属性
			List<Map<String, String>> danymicFiledName = staticDataService.findStaticDataList(coditions);
			List<String> beforeTable = Arrays.asList("fOrgName", "rOrgName", "orderNo", "sendNo", "tfAddress",
					"sendUsername", "sendDate", "expectDate", "treatmentNo", "name", "gender", "age", "operName",
					"operDate", "tfBrand", "tfRemark");
			List<String> afterTable = Arrays.asList("materailAmount", "outerImpAmount", "sterilizeAmount",
					"toolAmount");

			String applyId = deliveryOne.get("applyId") == null ? "" : deliveryOne.get("applyId").toString();
			if (StringUtils.isNotBlank(applyId)) {
				PatientOperDto dto = treatmentOperService.findPatientOperByApplyId(applyId);
				String operDate = dto.getOperDate() != null ? DateUtils.format(dto.getOperDate(), "yyyy-MM-dd") : "";
				deliveryOne.put("treatmentNo", dto.getTreatmentNo());
				deliveryOne.put("name", dto.getName());
				deliveryOne.put("gender", dto.getGender());
				deliveryOne.put("age", dto.getAge());
				deliveryOne.put("operName", dto.getOperName());
				deliveryOne.put("operDate", operDate);
				deliveryOne.put("tfRemark", dto.getTfRemark());
			}
			Map<String, Object> subtotalPackage = packageService.subtotalPackageBySendId(sendId, "S");
			deliveryOne.putAll(subtotalPackage);
			data.put("delivery", deliveryOne);
			List<Map<String, Object>> pacDetails = packageService
					.findPackageListBySendId(Long.valueOf(deliveryOne.get("rOrgId").toString()), sendId, null, "S");
			pacDetails.remove(0);
			data.put("details", pacDetails);
			CreateHtml.createA4DeliveryPDF(response, request, data, null, danymicFiledName, beforeTable, afterTable,
					"DeliveryGkA4HtmlPath", 4);
		} else {
			List<String> beforeTable = Arrays.asList("fOrgName", "rOrgName", "orderNo", "sendNo", "tfAddress",
					"sendUsername", "sendDate");
			List<String> fieldName = Arrays.asList("materialName", "spec", "fmodel", "tenderUnit", "tfPacking",
					"tenderPrice", "amount", "amountMoney", "flot", "prodDate", "usefulDate");
			List<String> afterTable = Arrays.asList("totalPrice");
			// 普耗送货单打印
			data.put("delivery", deliveryOne);
			List<Map<String, Object>> deliveryDetails = deliveryService.selectdeliveryDetails(pager);
			data.put("details", deliveryDetails);
			CreateHtml.createA4DeliveryPDF(response, request, data, fieldName, null, beforeTable, afterTable,
					"DeliveryA4HtmlPath", 10);
		}
	}

	/**
	 * 
	 * searchQrCodesBySendDetail(查询某个送货单明细的二维码列表).
	 */
	@ResponseBody
	@RequestMapping("/searchQrCodesBySendDetail")
	public Pager<Map<String, Object>> searchQrCodesBySendDetail(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "searchParams", required = false) String searchParams,
			@RequestParam(value = "sendDetailGuid", required = false) String sendDetailGuid, HttpServletRequest request)
			throws ValidationException {
		LocalAssert.notBlank(sendDetailGuid, "请选择要查询的送货单产品");
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(false);
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.setPageNum(page == null ? 1 : page);
		pager.addQueryParam("sendDetailGuid", sendDetailGuid);
		pager.addQueryParam("searchParams", searchParams);
		List<Map<String, Object>> qrdetailList = deliveryService.searchQrCodesBySendDetail(pager);
		pager.setRows(qrdetailList);
		return pager;
	}

	/**
	 * 
	 * printQrcode:(打印一物一码库房的产品的二维码,支持初始整个送货单打印和根据选择的二维码补打)
	 * sendId:送货单Id／qrcodes:二维码数组
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/printQrcode")
	public void printQrcode(@RequestParam(value = "sendId", required = false) String sendId,
			@RequestParam(value = "qrcodes", required = false) String[] qrcodes, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		if (StringUtils.isBlank(sendId) && org.springframework.util.ObjectUtils.isEmpty(qrcodes)) {
			throw new ValidationException("请传入送货单或者二维码信息");
		}
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(false);
		pager.addQueryParam("sendId", sendId);
		pager.addQueryParam("qrcodes", qrcodes);
		List<Map<String, Object>> listData = deliveryService.searchQrCodesBySendDetail(pager);

		List<String> titleFileds = Arrays.asList("rOrgName");
		List<String> tableFileds = Arrays.asList("qrcode", "materialName", "fmodel", "spec", "flot", "prodDate",
				"usefulDate", "registerNo");
		String title = "${rOrgName}医用耗材";
		Map<String, String> formatMap = new HashMap<String, String>();
		formatMap.put("materialName", "20");// 名称保留20个字符的长度，目前先支持取长度
		formatMap.put("registerNo", "20");// 注册证名称保留20个字符的长度，目前先支持取长度
		CreateHtml.createQRCodePDF(response, request, listData, tableFileds, titleFileds,title, formatMap);
	}
}

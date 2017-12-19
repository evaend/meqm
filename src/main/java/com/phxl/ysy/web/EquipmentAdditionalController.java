package com.phxl.ysy.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.interceptor.ResResultBindingInterceptor;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.entity.Register;
import com.phxl.ysy.service.AssetsRecordService;
import com.phxl.ysy.service.EquipmentAdditionalService;
import com.phxl.ysy.service.IMessageService;
import com.phxl.ysy.util.CreateHtml;
import com.phxl.ysy.util.ExcelUtils;
import com.phxl.ysy.util.ExcelUtils.EntityHandler;
import com.phxl.ysy.web.dto.EquipmentDto;
import com.phxl.ysy.web.dto.EquipmentRepeatCountDto;

@Controller
@RequestMapping("equipmentAdd")
public class EquipmentAdditionalController {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	EquipmentAdditionalService equipmentAdditionalService;
	@Autowired
	AssetsRecordService assetsRecordService;
	@Autowired
	IMessageService imessageService;

	/**
	 * 1、由机构管理员或由本机构其他人员，导入本机构的资产信息
	 * @param excelFile
	 */
	@RequestMapping("/importEquipments")
	@ResponseBody
	public synchronized void importEquipments(@RequestParam(value = "file", required = false) MultipartFile excelFile, HttpSession session) throws Exception {
		long startTime = System.currentTimeMillis();
		if (excelFile == null || excelFile.isEmpty()) {
			throw new ValidationException("请上传excel文件");
		}

		final StringBuffer msg = new StringBuffer();
		final Set<String> units = new HashSet<String>();
		final List<EquipmentRepeatCountDto> repeatMaterialCount = new ArrayList<EquipmentRepeatCountDto>();
		List<EquipmentDto> entityList = ExcelUtils.readExcel(
				excelFile.getInputStream(),
				1,
				EquipmentDto.class,
				new EntityHandler<EquipmentDto>() {
					@Override
					public EquipmentDto process(String sheetName, int rownum, final EquipmentDto entity) {
						entity.setSheetName(sheetName);//表格名称
						entity.setRownum(rownum);//行号（从0开始
						//数据格式检查
						String error = validLengthOfEquipment(entity);
						if (StringUtils.isNotEmpty(error)) {
							msg.append("[").append(sheetName).append("]-[第").append(rownum + 1).append("行]").append(error).append("<br/>");
						}
						return entity;
					}
				},
				new String[]{"资产名称[equipmentName]", "型号[fmodel]", "规格[spec]", "资产分类[productType]", "生产商[product]", "使用科室[useDeptCode]", "存放地址[deposit]", "管理科室[bDeptCode]", "责任人[custodian]"
						, "购置金额[buyPrice]", "安装费[installPrice]", "经费来源[sourceFunds]", "出厂日期[productionDate]", "购买日期原始[aa]", "使用年限[useLimit]", "折旧年限[depreciationLimit]", "折旧方法[depreciationType]", "残值[residualValue]", "质量等级[qaLevel]", "保养周期[maintainDay]", "维修商[aaOrg]", "维修标志[repairFlag]",
						"购买日期[buyDate]"}
		);

		//检查: 
		//导入检查: Excel表格内产品不重复，过滤出重复出现的产品
		final List<EquipmentRepeatCountDto> repeatMaterials = (List<EquipmentRepeatCountDto>) CollectionUtils.select(repeatMaterialCount, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				return ((EquipmentRepeatCountDto) object).getTimes() > 1;
			}
		});
		if (CollectionUtils.isNotEmpty(repeatMaterials)) {
			StringBuffer repeatTips = new StringBuffer();
			for (EquipmentRepeatCountDto m : repeatMaterials) {
				repeatTips.append("[表格产品重复性检查] ")
						.append("<font color='blue'>").append(m.getFmodel()).append("</font>").append("，").append("<font color='#FF890D'>").append(m.getSpec()).append("</font>").append(": 在[");
				for (Iterator<String> ite = m.getPositions().iterator(); ite.hasNext(); ) {
					String position = ite.next();
					repeatTips.append("<font style='text-decoration: underline; color:#1E7DD2;'>").append(position).append("</font>").append(ite.hasNext() ? "，" : "");
				}
				repeatTips.append("] 重复出现 ").append("<font color='red'>").append(m.getTimes()).append("</font>").append(" 次<br/>");
			}
			msg.append(repeatTips.toString());
		}

		String message = msg.toString();
		logger.debug("导入资产信息: errer-msg={}", message);
		if (StringUtils.isNotEmpty(message)) {
			throw new ValidationException("<b>数据格式检查不通过，请完善数据:</b><p>" + message);
		}

		Assert.notEmpty(entityList, "没有发现资产信息，请检查文档中的资产");
		String userId = (String) session.getAttribute(LoginUser.SESSION_USERID);
		String orgId = (String) session.getAttribute(LoginUser.SESSION_USER_ORGID);
		if(StringUtils.isBlank(orgId)){
			orgId = "212";
		}
		//导入资产
		equipmentAdditionalService.importEquipments(entityList,userId,orgId );
		logger.info("导入资产信息->导入成功: 共{}个信息，使用时间{}（秒）", entityList.size(), BigDecimal.valueOf((System.currentTimeMillis() - startTime) / 1000d).setScale(3));
	}

	/**
	 * 2、打印资产的二维码
	 */
	@ResponseBody
	@RequestMapping("/printEquipmentQrcode")
	public void printEquipmentQrcode(@RequestParam(value = "assetsRecord", required = false) String assetsRecord,
			HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		if (StringUtils.isBlank(assetsRecord)) {
			throw new ValidationException("请选择要打印的资产");
		}
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(false);
		pager.addQueryParam("assetsRecordOne", assetsRecord);
		
		List<Map<String, Object>> listData = assetsRecordService.selectAssetsList(pager);

		List<String> titleFileds = Arrays.asList("equipmentName");
		List<String> tableFileds = Arrays.asList("qrcode", "fmodel","buyDate", "useDept");
		String title = "${equipmentName}";
		Map<String, String> formatMap = new HashMap<String, String>();
		formatMap.put("fmodel", "20");// 名称保留20个字符的长度，目前先支持取长度
		formatMap.put("useDept", "20");// 注册证名称保留20个字符的长度，目前先支持取长度
		
//		Map<String,Object> argument = new HashMap<String,Object>();                 
//        argument.put("first", "123456");
//        argument.put("keyword1", "IT78922");
//        argument.put("keyword2","维修中");
//        argument.put("keyword3",new Date());
//        argument.put("keyword4","陶悠");
//        argument.put("keyword5","维修中");
//        argument.put("remark","所属科室：设备科");
//
//        String message = imessageService.getMessageJsonContent(argument,"A6C68D5EFF5E4D55B5D8396CB3232DE0","www.baidu.com","1");
//        imessageService.pushMessages(message);
		CreateHtml.createQRCodePDF(response, request, listData, tableFileds, titleFileds,title, formatMap);
	}
	
	/**
	 * 3、更改资产的注册证信息
	 */
	@RequestMapping("/changCertByEGuid")
	@ResponseBody
	public void changCertByEGuid(String assetsRecord, String newCertGuid, HttpSession session) throws Exception {
		LocalAssert.notBlank(assetsRecord, "请选择将变更的资产");
		LocalAssert.notBlank(newCertGuid, "请选择要变更到的注册证");
		//变更证件
		equipmentAdditionalService.bindingCertByEGuid(assetsRecord, newCertGuid, (String) session.getAttribute(LoginUser.SESSION_USERID));
	}
	
	/**
	 * 4、查询证件列表，来源医商云的库
	 */
	@RequestMapping("/searchCertList")
	@ResponseBody
	public List<Map<String, Object>> searchCertList(String searchName,
	                                     String specCertGuid,
	                                     String excludeCertGuid,
	                                     Integer pagesize,
	                                     HttpServletRequest request) throws Exception {
		Pager<Register> pager = new Pager<Register>(true, false);
		pager.setPageSize(pagesize);
		pager.addQueryParam("searchName", searchName);
		pager.addQueryParam("specCertGuid", specCertGuid);
		pager.addQueryParam("excludeCertGuid", excludeCertGuid);

		request.setAttribute(ResResultBindingInterceptor.IGNORE_STD_RESULT, true);//忽略标准结果
		return equipmentAdditionalService.searchCertList(pager);
	}

	private String validLengthOfEquipment(EquipmentDto entity) {
		String error = "";
		//非空检查
		if (StringUtils.isBlank(entity.getEquipmentName())) {
			error += "资产名称，不能为空；";
		} else if (entity.getEquipmentName() != null && entity.getEquipmentName().length() > 100) {
			error += "资产名称，长度不能超过100个字符；";
		}
		if (entity.getFmodel() != null && entity.getFmodel().length() > 50) {
			error += "型号，长度不能超过50个字符；";
		}
		if (entity.getSpec() != null && entity.getSpec().length() > 50) {
			error += "规格，长度不能超过50个字符；";
		}
		return error;
	}
}

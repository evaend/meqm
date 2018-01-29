package com.phxl.ysy.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.ysy.entity.CertInfo;
import com.phxl.ysy.entity.DeptInfo;
import com.phxl.ysy.entity.EqOperationInfo;
import com.phxl.ysy.service.AssetsRecordService;

@Controller
@RequestMapping("/assetsRecordController")
public class AssetsRecordController {
	@Autowired
	AssetsRecordService assetsRecordService;
	
	/**
	 * 查询资产总量
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectAssetsRecordCount")
	@ResponseBody
	public Integer selectAssetsRecordCount(
			HttpServletRequest request,HttpServletResponse response) {
		Integer count = assetsRecordService.selectAssetsRecordCount();
		return count;
	}
	
	/**
	 * 查询资产档案列表（包含资产详情基本信息）
	 * @param assetsRecord 资产编号
	 * @param equipmetStandarName 资产名称
	 * @param useFstate 状态
	 * @param productType 设备分类
	 * @param spec 设备型号
	 * @param product 厂商
	 * @param useDeptCode 使用科室
	 * @param bDept 管理科室
	 * @param custodian 责任人
	 * @param mobile 资产名称/编号
	 * @param assetsRecordOne 资产编号（查询详情传值）
	 * @param pagesize
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyanli
	 */
	@RequestMapping("/selectAssetsList")
	@ResponseBody
	public Pager<Map<String, Object>> selectAssetsList(
//			@RequestParam(value="assetsRecord",required = false) String assetsRecord,
//			@RequestParam(value="equipmetStandarName",required = false) String equipmetStandarName,
//			@RequestParam(value="useFstate",required = false) String useFstate,
//			@RequestParam(value="productType",required = false) String productType,
//			@RequestParam(value="spec",required = false) String spec,
//			@RequestParam(value="product",required = false) String product,
//			@RequestParam(value="useDeptCode",required = false) String useDeptCode,
//			@RequestParam(value="bDept",required = false) String bDept,
//			@RequestParam(value="custodian",required = false) String custodian,
//			@RequestParam(value="assetsRecordOne",required = false) String assetsRecordOne,
			@RequestParam(value="mobile",required = false) String mobile,
			@RequestParam(value="pagesize",required = false) Integer pagesize,
			@RequestParam(value="page",required = false) Integer page,
			@RequestParam(value="sortField",required = false) String sortField,
			@RequestParam(value="sortOrder",required = false) String sortOrder,
			HttpServletRequest request,HttpServletResponse response
			){
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(true);
		//如果没有设置当前页和每页数量，则默认第一页，每页十五条数据
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		if(StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortOrder)){
//			pager.addQueryParam("resultMapName", "com.phxl.ysy.systemModule.dao.GroupMapper.BaseResultMap");//设置ResultMap映射关系
			pager.addQueryParam("orderField", sortField);
			pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sortOrder)?"asc":"desc");
		}
		pager.setPageNum(page == null ? 1 : page);
		
//		pager.addQueryParam("assetsRecord", assetsRecord);
//		pager.addQueryParam("equipmetStandarName", equipmetStandarName);
//		pager.addQueryParam("useFstate", useFstate);
//		pager.addQueryParam("productType", productType);
//		pager.addQueryParam("spec", spec);
//		pager.addQueryParam("product", product);
//		pager.addQueryParam("useDeptCode", useDeptCode);
//		pager.addQueryParam("bDept", bDept);
//		pager.addQueryParam("custodian", custodian);
//		pager.addQueryParam("assetsRecordOne", assetsRecordOne);
		pager.addQueryParam("mobile", mobile);
		
		List<Map<String, Object>> list = assetsRecordService.selectAssetsList(pager);
		pager.setRows(list);
		return pager;
	}

	/**
	 * 查询资产档案详情
	 * @param assetsRecordGuid 资产档案GUID
	 * @return
	 * @throws ValidationException 
	 */
	@RequestMapping("/selectAssetsRecordDetail")
	@ResponseBody
	public Map<String, Object> selectAssetsRecordDetail(
			@RequestParam(value="assetsRecordGuid",required = false) String assetsRecordGuid) throws ValidationException{
		if (StringUtils.isBlank(assetsRecordGuid)) {
			throw new ValidationException("资产id不允许为空");
		}
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(false);
		pager.addQueryParam("assetsRecordGuid", assetsRecordGuid);
		Map<String, Object> map = assetsRecordService.selectAssetsRecordDetail(pager);
		if (map == null ) {
			throw new ValidationException("当前资产信息不存在");
		}
		return map;
	}
	/**
	 * 查询设备附件列表
	 * @param assetsRecordGuid 资产档案GUID
	 * @param page 当前页数
	 * @param pagesize 每页条数
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyanli
	 * @throws ValidationException 
	 */
	@RequestMapping("/selectAssetsExtendList")
	@ResponseBody
	public Pager<Map<String, Object>> selectAssetsExtendList(
			@RequestParam(value="assetsRecordGuid",required = false) String assetsRecordGuid,
			@RequestParam(value="page",required = false) Integer page,
			@RequestParam(value="pagesize",required = false) Integer pagesize,
			@RequestParam(value="sortField",required = false) String sortField,
			@RequestParam(value="sortOrder",required = false) String sortOrder,
			HttpServletRequest request , HttpServletResponse response
			) throws ValidationException{
		if (StringUtils.isBlank(assetsRecordGuid)) {
			throw new ValidationException("资产id不允许为空");
		}
		
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(true);
		pager.setPageNum(page == null ? 1 : page);
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.addQueryParam("assetsRecordGuid", assetsRecordGuid);
		
		if(StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortOrder)){
			pager.addQueryParam("orderField", sortField);
			pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sortOrder)?"asc":"desc");
		}
		List<Map<String, Object>> list = assetsRecordService.selectAssetsExtendList(pager);
		pager.setRows(list);
		return pager;
	}
	
	/**
	 * 查询产品证件列表
	 * @param assetsRecord 资产档案编号
	 * @param page 当前页数
	 * @param pagesize 每页条数
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyanli
	 * @throws ValidationException 
	 */
	@RequestMapping("/selectCertInfoList")
	@ResponseBody
	public Pager<Map<String, Object>> selectCertInfoList(
			@RequestParam(value="assetsRecord",required = false) String assetsRecord,
			@RequestParam(value="page",required = false) Integer page,
			@RequestParam(value="pagesize",required = false) Integer pagesize,
			@RequestParam(value="sortField",required = false) String sortField,
			@RequestParam(value="sortOrder",required = false) String sortOrder,
			HttpServletRequest request , HttpServletResponse response
			) throws ValidationException{
		if (StringUtils.isBlank(assetsRecord)) {
			throw new ValidationException("资产档案编号不允许为空");
		}
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(true);
		pager.setPageNum(page == null ? 1 : page);
		pager.setPageSize(pagesize == null ? 15 : pagesize);

		if(StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortOrder)){
			pager.addQueryParam("orderField", sortField);
			pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sortOrder)?"asc":"desc");
		}
		pager.addQueryParam("assetsRecord", assetsRecord);
		
		List<Map<String, Object>> list = assetsRecordService.selectCertInfoList(pager);
		pager.setRows(list);
		return pager;
	}

	/**
	 * 删除证件信息
	 * @param certId 证件ID
	 * @author zhangyanli
	 * @throws ValidationException 
	 */
	@RequestMapping("/deleteAssetsExtend")
	@ResponseBody
	public void deleteAssetsExtend(
			@RequestParam(value="certId",required = false) String certId) throws ValidationException{
		if (StringUtils.isBlank(certId)) {
			throw new ValidationException("证件ID不允许为空");
		}
		CertInfo certInfo = assetsRecordService.find(CertInfo.class, certId);
		if (certInfo==null) {
			throw new ValidationException("当前证件不存在");
		}
		assetsRecordService.deleteInfo(certInfo);
	}
	
	/**
	 * 查询操作记录
	 * @param opType 操作分类/操作员
	 * @param page 当前页数
	 * @param pagesize 每页条数
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyanli
	 */
	@RequestMapping("/selectEqOperationInfoList")
	@ResponseBody
	public Pager<Map<String, Object>> selectEqOperationInfoList(
			@RequestParam(value="opType",required = false) String opType,
			@RequestParam(value="page",required = false) Integer page,
			@RequestParam(value="pagesize",required = false) Integer pagesize,
			@RequestParam(value="sortField",required = false) String sortField,
			@RequestParam(value="sortOrder",required = false) String sortOrder,
			HttpServletRequest request , HttpServletResponse response){
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(true);
		pager.setPageNum(page == null ? 1 : page);
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.addQueryParam("opType", opType);

		if(StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortOrder)){
			pager.addQueryParam("orderField", sortField);
			pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sortOrder)?"asc":"desc");
		}
		List<Map<String, Object>> list = assetsRecordService.selectEqOperation(pager);
		pager.setRows(list);
		return pager;
	}
	
	/**
	 * 修改资产档案信息
	 * @param assetsRecordGuid 资产档案GUID
	 * @param value 当前要修改的字段名称
	 * @param text 当前要修改的字段的值
	 * @throws ValidationException 
	 * @author zhangyanli
	 */
	@RequestMapping("/updateAssetsRecordInfo")
	@ResponseBody
	public void updateAssetsRecordInfo(
			@RequestParam(value="assetsRecordGuid",required = false) String assetsRecordGuid,
			@RequestParam(value="value",required = false) String value,
			@RequestParam(value="text",required = false) String text) throws ValidationException{
		if (StringUtils.isBlank(assetsRecordGuid)) {
			throw new ValidationException("资产档案GUID不允许为空");
		}
		if (StringUtils.isBlank(value)) {
			throw new ValidationException("当前要修改的字段名称不允许为空");
		}
		if (StringUtils.isBlank(text)) {
			throw new ValidationException("当前要修改的字段的值不允许为空");
		}
		assetsRecordService.updateAssetsRecordInfo(assetsRecordGuid,value, text);
	}
	
	@RequestMapping("/selectdept")
	@ResponseBody
	public void selectdept(
			@RequestParam(value="deptGuid",required = false) String deptGuid) throws ValidationException{
		DeptInfo deptInfo = new DeptInfo();
		deptInfo.setDeptCode(deptGuid);
		DeptInfo newDept = assetsRecordService.searchEntity(deptInfo);
		System.out.println(newDept.getDeptCode());
	}
}

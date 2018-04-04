package com.phxl.ysy.web;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.tools.FileObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.util.DateUtils;
import com.phxl.core.base.util.FTPUtils;
import com.phxl.core.base.util.JSONUtils;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.core.base.util.SystemConfig;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.constant.MySessionContext;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.entity.AssetsRecord;
import com.phxl.ysy.entity.CertInfoZc;
import com.phxl.ysy.entity.Group;
import com.phxl.ysy.entity.GroupUserKey;
import com.phxl.ysy.entity.OrgDept;
import com.phxl.ysy.entity.EqOperationInfo;
import com.phxl.ysy.service.AssetsRecordService;

@Controller
@RequestMapping("/assetsRecordController")
public class AssetsRecordController {
	@Autowired
	AssetsRecordService assetsRecordService;
	
	@Autowired
	HttpSession session;
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
	 * @param mobile 资产名称/编号
	 * @param pagesize
	 * @param page
	 * @param sortField
	 * @param sortOrder
	 * @return
	 * @author zhangyanli
	 */
	@RequestMapping("/selectAssetsList")
	@ResponseBody
	public Pager<Map<String, Object>> selectAssetsList(
			@RequestParam(value="mobile",required = false) String mobile,
			@RequestParam(value="productType",required = false) String productType,
			@RequestParam(value="useDeptGuid",required = false) String useDeptGuid,
			@RequestParam(value="pagesize",required = false) Integer pagesize,
			@RequestParam(value="page",required = false) Integer page,
			@RequestParam(value="sortField",required = false) String sortField,
			@RequestParam(value="sortOrder",required = false) String sortOrder,
			HttpServletRequest request,HttpServletResponse response
			){
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(true);
		//如果没有设置当前页和每页数量，则默认第一页，每页十五条数据
		pager.setPageNum(page == null ? 1 : page);
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		if(StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortOrder)){
			pager.addQueryParam("orderField", sortField);
			pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sortOrder)?"asc":"desc");
		}
		
		pager.addQueryParam("mobile", mobile);
		pager.addQueryParam("productType", productType);
		pager.addQueryParam("useDeptGuid", useDeptGuid);
		
		List<Map<String, Object>> list = assetsRecordService.selectAssetsList(pager);
		pager.setRows(list);
		return pager;
	}

	/**
	 * 查询资产档案详情
	 * @param assetsRecordGuid 资产档案GUID
	 * @param assetsRecord 资产编号/二维码
	 * @return
	 * @throws ValidationException 
	 */
	@RequestMapping("/selectAssetsRecordDetail")
	@ResponseBody
	public Map<String, Object> selectAssetsRecordDetail(
			@RequestParam(value="assetsRecordGuid",required = false) String assetsRecordGuid,
			@RequestParam(value="assetsRecord",required = false) String assetsRecord) throws ValidationException{
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(false);
		pager.addQueryParam("assetsRecordGuid", assetsRecordGuid);
		pager.addQueryParam("assetsRecord", assetsRecord);
		Map<String, Object> map = assetsRecordService.selectAssetsRecordDetail(pager);
		if (map == null ) {
			throw new ValidationException("当前资产信息不存在");
		}
		return map;
	}
	/**
	 * 查询设备配件列表
	 * @param assetsRecordGuid 资产档案GUID
	 * @param equipmetName 配件名称/编号
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
			@RequestParam(value="rrpairOrderGuid",required = false) String rrpairOrderGuid,
			@RequestParam(value="equipmetName",required = false) String equipmetName,
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
		pager.addQueryParam("rrpairOrderGuid", rrpairOrderGuid);
		pager.addQueryParam("equipmetName", equipmetName);
		
		if(StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortOrder)){
			pager.addQueryParam("orderField", sortField);
			pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sortOrder)?"asc":"desc");
		}
		List<Map<String, Object>> list = assetsRecordService.selectAssetsExtendList(pager);
		pager.setRows(list);
		return pager;
	}
	
	/**
	 * 查询资产附件列表
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
	 * 删除资产附件信息
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
		CertInfoZc certInfo = assetsRecordService.find(CertInfoZc.class, certId);
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
	 * @param assetsRecordGuid 资产档案guid
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
			@RequestParam(value="assetsRecordGuid",required = false) String assetsRecordGuid,
			HttpServletRequest request , HttpServletResponse response){
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(true);
		pager.setPageNum(page == null ? 1 : page);
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.addQueryParam("opType", opType);
		pager.addQueryParam("assetsRecordGuid", assetsRecordGuid);
		
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
		OrgDept deptInfo = new OrgDept();
		deptInfo.setDeptCode(deptGuid);
		OrgDept newDept = assetsRecordService.searchEntity(deptInfo);
		System.out.println(newDept.getDeptCode());
	}
	
	/**
	 * 上传资产附件
	 * @param assetsRecordGuid 资产附件guid
	 * @param certCode 资产附件类型
	 * @param file 文件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/assetsFileUpLoad")
	@ResponseBody
	public void assetsFileUpLoad(
			@RequestParam(value="assetsRecordGuid",required = false) String assetsRecordGuid,
			@RequestParam(value="certCode",required = false) String certCode,
			@RequestParam(value="file",required = false) MultipartFile file,
			HttpServletRequest request,HttpServletResponse response) throws Exception{

		LocalAssert.notBlank(assetsRecordGuid, "请传入id");
		if(file == null){
			throw new ValidationException("请上传附件");
		}
        assetsRecordService.insertAssetsFile(assetsRecordGuid, certCode,file);
	}

	/**
	 * 删除资产附件信息
	 * @param certId 资产附件id
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteAssetsFile")
	@ResponseBody
	public void deleteAssetsFile(
			@RequestParam(value="certId",required = false) String certId,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (StringUtils.isBlank(certId)) {
			throw new ValidationException("附件ID不允许为空");
		}
		assetsRecordService.deleteAssetsFile(certId);
	}
	
	/**
	 * 判断维修单不同的状态，跳转到不同的页面
	 * @param assetsRecordGuid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectAssetsRecordFstate")
	@ResponseBody
	public ModelAndView selectAssetsRecordFstate(
			@RequestParam(value="qrcode",required = false) String qrcode,
//			@RequestParam(value="sessionId",required = false) String sessionId,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		System.out.println("aaaaaaaaaaaaaaa+"+request.getSession().getId());
		if (StringUtils.isBlank(qrcode)) {
			ModelAndView mod = new ModelAndView(
					new RedirectView(CustomConst.MeqmMobile+"/#/error?errorValue=请扫描资产二维码"));
			return mod;
//			response.sendRedirect(CustomConst.MeqmMobile+"/#/error?errorValue=请扫描资产二维码");
//			throw new ValidationException("请扫描资产二维码");
		}
//		if (StringUtils.isNotBlank(sessionId)) {
//			if (session!=null ) {
//				if (!sessionId.equals(session.getId())) {
//					session = MySessionContext.getSession(sessionId);
//				}
//			}else{
//				session = MySessionContext.getSession(sessionId);
//			}
//		}
		AssetsRecord aRecord = new AssetsRecord();
		aRecord.setQrcode(qrcode);
		AssetsRecord assetsRecord = assetsRecordService.searchEntity(aRecord);
		if (assetsRecord==null) {
			ModelAndView mod = new ModelAndView(
					new RedirectView(CustomConst.MeqmMobile+"#/error?errorValue=当前资产信息不存在"));
			return mod;
//			response.sendRedirect(CustomConst.MeqmMobile+"/#/error?errorValue=当前资产信息不存在");
//			throw new ValidationException("当前资产信息不存在");
		}
		if (session.getAttribute(LoginUser.SESSION_USERID)==null) {
			ModelAndView mod = new ModelAndView(
					new RedirectView(CustomConst.MeqmMobile+"#/error?errorValue=当前session失效，请重新登录"));
			return mod;
//			response.sendRedirect(CustomConst.MeqmMobile+"/#/error?errorValue=当前session失效，请重新登录");
//			throw new ValidationException("当前session失效，请重新登录");
		}
		String groupName = "";
		GroupUserKey groupUserKey = new GroupUserKey();
		groupUserKey.setUserId(session.getAttribute(LoginUser.SESSION_USERID).toString());
		GroupUserKey group = assetsRecordService.searchEntity(groupUserKey);
		if (group!=null) {
			Group g = assetsRecordService.find(Group.class, group.getGroupId());
			groupName = g.getGroupName();
			session.setAttribute("getUserGroupName", g.getGroupName());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("assetsRecord", assetsRecord.getAssetsRecord());
		map.put("orgId", session.getAttribute(LoginUser.SESSION_USER_ORGID));
		List<Map<String, Object>> list = assetsRecordService.selectAssetsRecordFstate(map);
		if (list==null || list.size()==0) {
			ModelAndView mod = new ModelAndView(
					new RedirectView(CustomConst.MeqmMobile+"#/repair/repairReg/"
						+session.getAttribute(LoginUser.SESSION_USERID)+"/"+ assetsRecord.getAssetsRecordGuid()+"/"
						+groupName+"/1"));
			return mod;
		}else {
			Map<String, Object> rrpairMap = list.get(0);
			if (CustomConst.RrpairOrderFstate.AWAITING_REPAIR.equals(rrpairMap.get("orderFstate"))) {
				ModelAndView mod = new ModelAndView(
						new RedirectView(CustomConst.MeqmMobile+"#/waitForRepair/detail/"
							+session.getAttribute(LoginUser.SESSION_USERID)+"/"
							+rrpairMap.get("rrpairOrderGuid").toString()+"/"
							+groupName+"/1"));
				return mod;
			}else {
				ModelAndView mod = new ModelAndView(
						new RedirectView(CustomConst.MeqmMobile+"#/check/detail/"
							+session.getAttribute(LoginUser.SESSION_USERID)+"/"
							+rrpairMap.get("orderFstate").toString()+"/"
							+rrpairMap.get("rrpairOrderGuid").toString()+"/"
							+groupName+"/1"));
				return mod;
			}
		}
	}
	
//	@RequestMapping("/selectEquipmentList")
//	@ResponseBody
//	public Pager<Map<String, Object>> selectEquipmentList(
//			@RequestParam(value="productType",required = false) String productType,
//			@RequestParam(value="deptGuid",required = false) String deptGuid,
//			@RequestParam(value="equipmentStandardName",required = false) String equipmentStandardName,
//			@RequestParam(value="page",required = false) Integer page,
//			@RequestParam(value="pagesize",required = false) Integer pagesize,
//			HttpServletRequest request,HttpServletResponse response) {
//		Pager<Map<String, Object>> pager = new Pager(true);
//		pager.setPageNum(page == null ? 1 : page);
//		pager.setPageSize(pagesize == null ? 15 : pagesize);
//		pager.addQueryParam("productType", productType);
//		pager.addQueryParam("deptGuid", deptGuid);
//		pager.addQueryParam("equipmentStandardName", equipmentStandardName);
//		return pager;
//	}
	
}

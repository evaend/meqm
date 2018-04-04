package com.phxl.ysy.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.util.FTPUtils;
import com.phxl.core.base.util.IdentifieUtil;
import com.phxl.core.base.util.SystemConfig;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.entity.AssetsRecord;
import com.phxl.ysy.entity.Equipment;
import com.phxl.ysy.entity.MaintainOrder;
import com.phxl.ysy.entity.MaintainOrderDetail;
import com.phxl.ysy.entity.MaintainPlan;
import com.phxl.ysy.entity.MaintainPlanDetail;
import com.phxl.ysy.entity.MaintainTemplate;
import com.phxl.ysy.entity.MaintainTemplateDetail;
import com.phxl.ysy.entity.MaintainType;
import com.phxl.ysy.service.MaintainOrderService;
import com.phxl.ysy.service.RrpairOrderService;
import com.phxl.ysy.web.dto.MaintainOrderDto;
import com.phxl.ysy.web.dto.MaintainPlanDto;

@Controller
@RequestMapping("/maintainOrderController")
public class MaintainOrderController {
	@Autowired
	MaintainOrderService maintainOrderService;
	
	@Autowired
	RrpairOrderService rrpairOrderService;
	
	@Autowired
	HttpSession session;
	
	/**
	 * 保养登记/编辑保养
	  String maintainGuid;  //保养单guid（如果是编辑则传值，如果新增则为空）
	  String assetsRecordGuid; //资产档案guid
	  String maintainType;  //保养类型
	  String engineerUserid;  //保养人guid(暂时没有就不传)（多个保养人以分号;分隔）
	  String engineerName;  //保养人姓名（多个保养人以分号;分隔）
	  String clinicalRisk;  //临床风险等级
	  Date maintainDate;  //开始保养时间
	  Date enMaintainDate;  //结束保养时间
	  Date nextMaintainDate;  //下次保养时间
	  String remark;  //备注
	  List<String> tfAccessory; //附件（可以支持多个附件）
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/insertMaintainOrder")
	@ResponseBody
	public void insertMaintainOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//配置项：日期格式
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项：忽略未知属性
		MaintainOrder dto = objectMapper.readValue(request.getReader(), MaintainOrder.class);
		if (StringUtils.isBlank(dto.getFstate())) {
			throw new ValidationException("当前保养状态不允许为空");
		}
		String maintainOrderNo = null;
		if (StringUtils.isNotBlank(dto.getMaintainGuid())) {
			MaintainOrder maintainOrder = maintainOrderService.find(MaintainOrder.class, dto.getMaintainGuid());
			if (maintainOrder==null) {
				throw new ValidationException("当前保养记录不存在");
			}
			maintainOrderNo = maintainOrder.getMaintainNo();
		}else {
			dto.setMaintainNo(rrpairOrderService.callSpGetBill(212L, "保养单", "BY", 6));
			maintainOrderNo = dto.getMaintainNo();
		}
		//保养附件
		String fault = "";
		if (dto.getTfAccessoryList()!=null && dto.getTfAccessoryList().size()!=0) {
			int i = 0;
			for (String str : dto.getTfAccessoryList()) {
				 //获取文件类型
		        int beginIndex = str.indexOf("/");
		        int endIndex = str.indexOf(";");
		        String filename = str.substring(beginIndex+1,endIndex==-1 ? str.length() : endIndex);
		        StringBuffer buf = new StringBuffer(".");
		        if(StringUtils.isNotBlank(filename)){
		            filename = buf.append(filename).toString();
		        }else{
		            throw new ValidationException("未知的上传文件类型!");
		        }
		        //获取文件
		        String file = str;
		        file = file.replaceAll("data:image/jpeg;base64,", "");  
		        file = file.replaceAll("data:image/jpg;base64,", "");
		        file = file.replaceAll("data:image/png;base64,", ""); 
		        file = file.replaceAll("data:image/gif;base64,", ""); 
		        file = file.replaceAll("data:image/bmp;base64,", "");
		        file = file.replaceAll("data:application/pdf;base64,", "");
		        Base64 decoder = new Base64();  
		        byte[] buffer = decoder.decodeBase64(file);
		        ByteArrayInputStream in = new ByteArrayInputStream(buffer);
		        
		        String configKey="resource.ftp.ysyFile.organization.cert.product";
		        String rCertGuid = maintainOrderNo+i;
		        System.out.println("rCertGuid = "+rCertGuid);
		        String[] args=new String[]{rCertGuid};//证件注册GUID

		        System.out.println("args = "+args);
		        String directory=MessageFormat.format(SystemConfig.getProperty(configKey), args);//目录位置
				System.out.println("configKey = "+SystemConfig.getProperty(configKey));
				System.out.println("directory = "+directory);
		        //确定存储文件名
		        int index = filename.lastIndexOf(".");
		        if(index<0){
		            throw new ValidationException("未知的上传文件类型!");
		        }
		        String fileName=rCertGuid+filename.substring(index);
		        System.out.println("filename = "+filename);
		        String filePath=directory+fileName;
		        FTPUtils.upload(directory, fileName, in);
		        fault += filePath+";";
		        i++;
			}

		}
		dto.setTfAccessory(fault);
		maintainOrderService.insertMaintainOrder(dto);
	}
	
	/**
	 * 查询保养记录列表
	 * @param maintainNo 保养单号/资产名称/资产编码
	 * @param page 当前页
	 * @param pagesize 每页条数
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectMaintainOrderList")
	@ResponseBody
	public Pager<Map<String, Object>> selectMaintainOrderList(
			@RequestParam(value="maintainNo" , required = false) String maintainNo,
			@RequestParam(value="page" , required = false) Integer page,
			@RequestParam(value="pagesize" , required = false) Integer pagesize,
			HttpServletRequest request,HttpServletResponse response) {
		Pager<Map<String, Object>> pager = new Pager(true);
		pager.addQueryParam("maintainNo", maintainNo);
		pager.addQueryParam("orgId", session.getAttribute(LoginUser.SESSION_USER_ORGID));
		pager.setPageSize(pagesize==null ? 15 : pagesize);
		pager.setPageNum(page == null ? 1 : page);
		List<Map<String, Object>> list = maintainOrderService.selectMaintainOrderList(pager);
		pager.setRows(list);
		return pager;
	}
	
	/**
	 * 查询保养单详情
	 * @param maintainGuid 保养单id
	 * @param request
	 * @param response
	 * @return
	 * @throws ValidationException
	 */
	@RequestMapping("/selectMaintainOrderDetail")
	@ResponseBody
	public Map<String, Object> selectMaintainOrderDetail(
			@RequestParam(value="maintainGuid" , required = false) String maintainGuid,
			HttpServletRequest request,HttpServletResponse response) throws ValidationException {
		if (StringUtils.isBlank(maintainGuid)) {
			throw new ValidationException("保养记录guid不允许为空");
		}
		Pager<Map<String, Object>> pager = new Pager(false);
		pager.addQueryParam("maintainGuid", maintainGuid);
		Map<String, Object> map = maintainOrderService.selectMaintainOrderDetail(pager);
		if (map == null) {
			throw new ValidationException("当前保养记录不存在");
		}
		map.put("maintainDetailList",  maintainOrderService.selectMaintainDetailList(pager));
		return map;
	}
	
	/**
	 * 查询保养工单的保养项目列表（保养明细）
	 * @param maintainGuid 保养工单id
	 * @return
	 * @throws ValidationException 
	 */
	@RequestMapping("/selectMaintainDetailList")
	@ResponseBody
	public List<Map<String, Object>> selectMaintainDetailList(
			@RequestParam(value="maintainGuid" , required = false) String maintainGuid) throws ValidationException {
		if (StringUtils.isBlank(maintainGuid)) {
			throw new ValidationException("保养记录guid不允许为空");
		}
		Pager<Map<String, Object>> pager = new Pager(false);
		pager.addQueryParam("maintainGuid", maintainGuid);
		return maintainOrderService.selectMaintainDetailList(pager);
	}
	
	/**
	 * 新增保养明细
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/insertMaintainOrderDetail")
	@ResponseBody
	public void insertMaintainOrderDetail(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//配置项：日期格式
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项：忽略未知属性
		Map<String, Object> dto = objectMapper.readValue(request.getReader(), HashMap.class);
		String maintainGuid = (String)dto.get("maintainGuid");
		List<String> maintainTypes = (List<String>)dto.get("maintainTypes");
		maintainOrderService.insertMaintainOrderDetail(maintainGuid, maintainTypes);
	}
	
	/**
	 * 删除保养明细
	 * @param maintainOrderDetailGuid 保养明细id
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteMaintainOrderDetail")
	@ResponseBody
	public void deleteMaintainOrderDetail(
			@RequestParam(value="maintainOrderDetailGuid" , required = false) String maintainOrderDetailGuid) throws Exception {
		MaintainOrderDetail maintainOrderDetail = maintainOrderService.find(MaintainOrderDetail.class, maintainOrderDetailGuid);
		if (maintainOrderDetail == null) {
			throw new ValidationException("当前保养工单不存在");
		}
		maintainOrderService.deleteInfo(maintainOrderDetail);
	}

	/**
	 * 修改保养明细
	 * @param maintainOrderDetailGuid 保养明细id
	 * @param maintainResult 结果
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updateMaintainOrderDetailFstate")
	@ResponseBody
	public void updateMaintainOrderDetailFstate(
			@RequestParam(value="maintainOrderDetailGuid" , required = false) String maintainOrderDetailGuid,
			@RequestParam(value="maintainResult" , required = false) String maintainResult) throws Exception {
		MaintainOrderDetail maintainOrderDetail = maintainOrderService.find(MaintainOrderDetail.class, maintainOrderDetailGuid);
		if (maintainOrderDetail == null) {
			throw new ValidationException("当前保养工单不存在");
		}
		maintainOrderDetail.setMaintainResult(maintainResult);
		maintainOrderService.updateInfo(maintainOrderDetail);
	}

	/**
	 * 查询保养模板和项目（树形）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectTemplateAndTypeList")
	@ResponseBody
	public List<Map<String, Object>> selectTemplateAndTypeList(HttpServletRequest request,HttpServletResponse response) {
		Pager<Map<String, Object>> pager = new Pager(false);
		pager.addQueryParam("orgId", session.getAttribute(LoginUser.SESSION_USER_ORGID));
		return maintainOrderService.selectTemplateAndTypeList(pager);
	}
	
	/**
	 * 查询保养项目基础数据
	 * @param maintainTypeName 保养项目名称（模糊匹配）
	 * @param page 当前页
	 * @param pagesize 每页条数
	 * @return
	 */
	@RequestMapping("/searchMaintainType")
	@ResponseBody
	public Pager<Map<String, Object>> searchMaintainType(
			@RequestParam(value="maintainTypeName" , required = false) String maintainTypeName,
			@RequestParam(value="page" , required = false) Integer page,
			@RequestParam(value="pagesize" , required = false) Integer pagesize) {
		Pager<Map<String, Object>> pager = new Pager(true);
		pager.setPageSize(pagesize==null ? 15 : pagesize);
		pager.setPageNum(page == null ? 1 : page);
		pager.addQueryParam("orgId", session.getAttribute(LoginUser.SESSION_USER_ORGID));
		pager.addQueryParam("maintainTypeName", maintainTypeName);
		pager.setRows(maintainOrderService.searchMaintainType(pager));
		return pager;
	}

	/**
	 * 查询保养模板
	 * @param templateName 模板名称
	 * @return
	 */
	@RequestMapping("/selectMaintainTemplate")
	@ResponseBody
	public List<Map<String, Object>> selectMaintainTemplate(
			@RequestParam(value="maintainTemplateName" , required = false) String maintainTemplateName) {
		Pager<Map<String, Object>> pager = new Pager(false);
		pager.addQueryParam("maintainTemplateName", maintainTemplateName);
		pager.addQueryParam("orgId", session.getAttribute(LoginUser.SESSION_USER_ORGID));
		return maintainOrderService.selectMaintainTemplate(pager);
	}
	
	/**
	 * 查询保养模板里的保养项目
	 * @param maintainTemplateId 保养模板id
	 * @return
	 * @throws ValidationException 
	 */
	@RequestMapping("/selectMaintainTemplateDetail")
	@ResponseBody
	public List<Map<String, Object>> selectMaintainTemplateDetail(
			@RequestParam(value="maintainTemplateId" , required = false) String maintainTemplateId) throws ValidationException {
		Pager<Map<String, Object>> pager = new Pager(false);
		pager.addQueryParam("maintainTemplateId", maintainTemplateId);
		pager.addQueryParam("orgId", session.getAttribute(LoginUser.SESSION_USER_ORGID));
		MaintainTemplate maintainTemplate = maintainOrderService.find(MaintainTemplate.class, maintainTemplateId);
		if (maintainTemplate==null) {
			throw new ValidationException("当前保养模板不存在");
		}
		return maintainOrderService.selectMaintainTemplateDetail(pager);
	}
	
	/**
	 * 查询设备的保养模板（带保养项目）
	 * @param equipmentCode 设备code
	 */
	@RequestMapping("/selectMaintainTemplateEquipment")
	@ResponseBody
	public List<Map<String, Object>> selectMaintainTemplateEquipment(
			@RequestParam(value="equipmentCode" , required = false) String equipmentCode ) throws ValidationException {
		Pager<Map<String, Object>> pager = new Pager(false);
		Equipment equipment = maintainOrderService.find(Equipment.class, equipmentCode);
		if (equipment==null) {
			throw new ValidationException("当前设备不存在");
		}
		pager.addQueryParam("maintainTemplateId", equipment.getMaintainTemplateId());
		pager.addQueryParam("orgId", session.getAttribute(LoginUser.SESSION_USER_ORGID));
		return maintainOrderService.selectMaintainTemplateEquipment(pager);
	}

	/**
	 * 查询模板下面的设备
	 * @param maintainTemplateId 保养模板id
	 * @param Boolean isChoose true已选，false未选
	 * @param equipmentName 设备名称
	 * @param page 当前页
	 * @param pagesize 每页条数
	 * @return
	 */
	@RequestMapping("/selectEquipmentInTemplate")
	@ResponseBody
	public Pager<Map<String, Object>> selectEquipmentInTemplate(
			@RequestParam(value="maintainTemplateId" , required = false) String maintainTemplateId,
			@RequestParam(value="isChoose" , required = false) Boolean isChoose,
			@RequestParam(value="equipmentName" , required = false) String equipmentName,
			@RequestParam(value="page" , required = false) Integer page,
			@RequestParam(value="pagesize" , required = false) Integer pagesize) {
		Pager<Map<String, Object>> pager = new Pager(true);
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.setPageNum(page == null ? 1 : page);
		if (isChoose) {
			pager.addQueryParam("maintainTemplateId", maintainTemplateId);
			if (StringUtils.isBlank(maintainTemplateId)) {
				pager.setRows(new ArrayList<Map<String,Object>>());
				return pager;
			}
		}else {
			pager.addQueryParam("maintainTemplateId", "");
		}
		pager.addQueryParam("equipmentName", equipmentName);
		pager.addQueryParam("orgId", session.getAttribute(LoginUser.SESSION_USER_ORGID));
		pager.setRows(maintainOrderService.selectEquipmentInTemplate(pager));
		return pager;
	}
	
	/**
	 * 添加保养模板/保养项目
	 * @param templateName 名称
	 * @param levelr （等级 0模板   1项目）
	 * @throws ValidationException
	 */
	@RequestMapping("/insertMaintainTemplate")
	@ResponseBody
	public void insertMaintainTemplate(
			@RequestParam(value="templateName" , required = false) String templateName , 
			@RequestParam(value="maintainTemplateId" , required = false) String maintainTemplateId 
			) throws ValidationException{
		//添加保养项目模板
		if (StringUtils.isBlank(maintainTemplateId)) {
			MaintainTemplate maintainTemplate = new MaintainTemplate();
			maintainTemplate.setMaintainTemplateName(templateName);
			List<MaintainTemplate> maintainTemplateList = maintainOrderService.searchList(maintainTemplate);
			if (maintainTemplateList != null && maintainTemplateList.size() != 0) {
				throw new ValidationException("当前模板名称已经存在，不允许重复添加");
			}
			MaintainTemplate maintainTemplate2 = new MaintainTemplate();
			maintainTemplate2.setMaintainTemplateId(IdentifieUtil.getGuId());
			maintainTemplate2.setMaintainTemplateName(templateName);
			maintainTemplate2.setCreateUserid(session.getAttribute(LoginUser.SESSION_USERID).toString());
			maintainTemplate2.setCreateTime(new Date());
			maintainTemplate2.setOrgId(Long.valueOf(session.getAttribute(LoginUser.SESSION_USER_ORGID).toString()));
			maintainTemplate2.setModifyUserid(session.getAttribute(LoginUser.SESSION_USERID).toString());
			maintainTemplate2.setModifiyTime(new Date());
			maintainOrderService.insertInfo(maintainTemplate2);
		}
		//添加保养项目基础数据
		if (StringUtils.isNotBlank(maintainTemplateId)) {
			MaintainType maintainType = new MaintainType();
			maintainType.setMaintainTypeName(templateName);
			List<MaintainType> maintainTypeList = maintainOrderService.searchList(maintainType);
			if (maintainTypeList != null && maintainTypeList.size() != 0) {
				throw new ValidationException("当前保养项目名称已存在，不允许重复添加");
			}
			String maintainTypeId = IdentifieUtil.getGuId();
			MaintainType maintainType2 = new MaintainType();
			maintainType2.setMaintainTypeId(maintainTypeId);
			maintainType2.setMaintainTypeName(templateName);
			maintainType2.setCreateUserid(session.getAttribute(LoginUser.SESSION_USERID).toString());
			maintainType2.setCreateTime(new Date());
			maintainType2.setOrgId(Long.valueOf(session.getAttribute(LoginUser.SESSION_USER_ORGID).toString()));
			maintainType2.setModifyTime(new Date());
			
			MaintainTemplateDetail maintainTemplateDetail = new MaintainTemplateDetail();
			maintainTemplateDetail.setTemplateDetailGuid(IdentifieUtil.getGuId());
			maintainTemplateDetail.setMaintainTypeId(maintainTypeId);
			maintainTemplateDetail.setTemplateId(maintainTemplateId);
			maintainOrderService.insertMaintainTemplate(maintainType2, maintainTemplateDetail);
		}
	}
	
	/**
	 * 修改模板名称/项目名称
	 * @param templateName 名称
	 * @param maintainTypeId 项目id （模板id和项目id只能传一个，如果修改模板传模板id，反之亦然）
	 * @param maintainTemplateId 模板id
	 * @throws ValidationException
	 */
	@RequestMapping("/updateMaintainTemplateName")
	@ResponseBody
	public String updateMaintainTemplateName(
			@RequestParam(value="templateName" , required = false) String templateName , 
			@RequestParam(value="maintainTemplateDetailId" , required = false) String maintainTemplateDetailId,
			@RequestParam(value="maintainTemplateId" , required = false) String maintainTemplateId) throws ValidationException{
		String resultTemplateId = "";
		if (StringUtils.isBlank(maintainTemplateId) && StringUtils.isBlank(maintainTemplateDetailId)) {
			throw new ValidationException("修改的模板或项目id不允许为空");
		}
		if (StringUtils.isNotBlank(maintainTemplateId) && StringUtils.isNotBlank(maintainTemplateDetailId)) {
			throw new ValidationException("不允许同时修改模板名称和项目名称");
		}
		if (StringUtils.isBlank(templateName)) {
			throw new ValidationException("修改的名称不允许为空");
		}
		//修改保养项目
		if (StringUtils.isNotBlank(maintainTemplateDetailId)) {
			MaintainTemplateDetail maintainTemplateDetail = maintainOrderService.find(MaintainTemplateDetail.class, maintainTemplateDetailId);
			if (maintainTemplateDetail == null) {
				throw new ValidationException("当前保养项目不存在");
			}
			MaintainType maintainType = maintainOrderService.find(MaintainType.class, maintainTemplateDetail.getMaintainTypeId());
			MaintainType maintainType2 = new MaintainType();
			maintainType2.setMaintainTypeName(templateName);
			List<MaintainType> maintainTypeList = maintainOrderService.searchList(maintainType2);
			if (maintainTypeList != null && maintainTypeList.size() != 0) {
				throw new ValidationException("当前保养项目名称已存在，不允许重复添加");
			}
			maintainType.setMaintainTypeName(templateName);
			maintainOrderService.updateInfo(maintainType);
			resultTemplateId = maintainTemplateDetail.getTemplateId();
		}
		//修改保养模板
		if (StringUtils.isNotBlank(maintainTemplateId)) {
			MaintainTemplate maintainTemplate = new MaintainTemplate();
			maintainTemplate.setMaintainTemplateName(templateName);
			List<MaintainTemplate> maintainTemplateList = maintainOrderService.searchList(maintainTemplate);
			if (maintainTemplateList != null && maintainTemplateList.size() != 0) {
				throw new ValidationException("当前模板名称已经存在，不允许重复添加");
			}
			MaintainTemplate maintainTemplate3 = maintainOrderService.find(MaintainTemplate.class, maintainTemplateId);
			if (maintainTemplate3 == null) {
				throw new ValidationException("当前保养模板不存在");
			}
			maintainTemplate3.setMaintainTemplateName(templateName);
			maintainOrderService.updateInfo(maintainTemplate3);
			resultTemplateId = maintainTemplate.getMaintainTemplateId();
		}
		return resultTemplateId;
	}
	
	/**
	 * 添加保养模板的保养项目
	 * @param request
	 * @param response
	 */
	@RequestMapping("/insertMaintainTemplateDetail")
	@ResponseBody
	public void insertMaintainTemplateDetail(HttpServletRequest request , HttpServletResponse response) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//配置项：日期格式
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项：忽略未知属性
		Map<String, Object> dto = objectMapper.readValue(request.getReader(), HashMap.class);
		String maintainTemplateId = dto.get("maintainTemplateId").toString();
		List<String> maintainTypes = (List<String>)dto.get("maintainTypes");
		maintainOrderService.insertMaintainTemplateDetail(maintainTemplateId, maintainTypes);
	}
	
	/**
	 * 添加/删除模板的设备
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/insertTemplataEquipment")
	@ResponseBody
	public void insertTemplataEquipment(HttpServletRequest request , HttpServletResponse response) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//配置项：日期格式
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项：忽略未知属性
		Map<String, Object> dto = objectMapper.readValue(request.getReader(), HashMap.class);
		String maintainTemplateId = dto.get("maintainTemplateId").toString();
		MaintainTemplate maintainTemplate = maintainOrderService.find(MaintainTemplate.class, maintainTemplateId);
		if (maintainTemplate == null) {
			throw new ValidationException("当前保养模板不存在");
		}
		String fstate = dto.get("fstate").toString();
		List<String> equipmentList = (List<String>)dto.get("equipmentList");
		maintainOrderService.insertTemplataEquipment(maintainTemplateId, fstate , equipmentList);
	}
	
	/**
	 * 删除保养模板
	 * @param maintainTemplateId 保养模板id
	 * @throws ValidationException
	 */
	@RequestMapping("/deleteMaintainTemplate")
	@ResponseBody
	public void deleteMaintainTemplate(
			@RequestParam(value="maintainTemplateId" , required = false) String maintainTemplateId) throws ValidationException {
		MaintainTemplate maintainTemplate = maintainOrderService.find(MaintainTemplate.class, maintainTemplateId);
		if (maintainTemplate==null) {
			throw new ValidationException("当前保养模板不存在，不允许");
		}
		Equipment equipment = new Equipment();
		equipment.setMaintainTemplateId(maintainTemplateId);
		List<Equipment> equipments = maintainOrderService.searchList(equipment);
		if (equipments==null || equipments.size()==0) {
			MaintainTemplateDetail maintainTemplateDetail = new MaintainTemplateDetail();
			maintainTemplateDetail.setMaintainTypeId(maintainTemplateId);
			List<MaintainTemplateDetail> list = maintainOrderService.searchList(maintainTemplateDetail);
			for (MaintainTemplateDetail maintainTemplateDetail2 : list) {
				maintainOrderService.deleteInfo(maintainTemplateDetail2);
			}
			maintainOrderService.deleteInfo(maintainTemplate);
		}else{
			throw new ValidationException("当前保养模板，有设备正在使用，不允许删除");
		}
	}
	
	/**
	 * 删除保养模板子项
	 * @param maintainTemplateDetailId 保养模板明细id
	 * @throws ValidationException
	 */
	@RequestMapping("/deleteMaintainTemplateDetail")
	@ResponseBody
	public void deleteMaintainTemplateDetail(
			@RequestParam(value="maintainTemplateDetailId" , required = false) String maintainTemplateDetailId) throws ValidationException{
		MaintainTemplateDetail maintainTemplateDetail = maintainOrderService.find(MaintainTemplateDetail.class, maintainTemplateDetailId);
		if (maintainTemplateDetail==null) {
			throw new ValidationException("当前保养模板项目不存在");
		}
		maintainOrderService.deleteInfo(maintainTemplateDetail);
	}
	
	/**
	 * 添加保养计划
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/insertMaintainPlan")
	@ResponseBody
	public void insertMaintainPlan(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//配置项：日期格式
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项：忽略未知属性
		MaintainPlanDto dto = objectMapper.readValue(request.getReader(), MaintainPlanDto.class);
		maintainOrderService.insertMaintainPlan(dto);
	}
	
	/**
	 * 修改保养计划
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updateMaintainPlan")
	@ResponseBody
	public void updateMaintainPlan(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//配置项：日期格式
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项：忽略未知属性
		Map<String, Object> map = objectMapper.readValue(request.getReader(), HashMap.class);
		MaintainPlan maintainPlan = (MaintainPlan)map.get("maintainPlan");
		List<String> maintainTypes = (List<String>)map.get("maintainTypes");
		if (StringUtils.isBlank(maintainPlan.getMaintainPlanId())) {
			throw new ValidationException("当前保养计划id不允许为空");
		}
		maintainOrderService.updateMaintainPlan(maintainPlan, maintainTypes);
 	}
	
	/**
	 * 删除当前计划
	 * @param maintainPlanDetailId 保养计划id
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteMaintainPlan")
	@ResponseBody
	public void deleteMaintainPlan(
		@RequestParam(value="maintainPlanDetailId" , required = false) String maintainPlanDetailId , 
		HttpServletRequest request,HttpServletResponse response) throws Exception{
		MaintainPlanDetail maintainPlanDetail = maintainOrderService.find(MaintainPlanDetail.class, maintainPlanDetailId);
		if (maintainPlanDetail==null) {
			throw new ValidationException("当前计划不存在");
		}
		maintainOrderService.deleteInfo(maintainPlanDetail);
	}

	/**
	 * 修改保养计划状态（执行）
	 * @param maintainPlanDetailId 保养计划id
	 * @throws Exception
	 */
	@RequestMapping("/updateMaintainPlanFstate")
	@ResponseBody
	public void updateMaintainPlanFstate(
			@RequestParam(value="maintainPlanDetailId" , required = false) String maintainPlanDetailId) throws Exception {
		MaintainPlanDetail maintainPlanDetail = maintainOrderService.find(MaintainPlanDetail.class, maintainPlanDetailId);
		if (maintainPlanDetail == null) {
			throw new ValidationException("当前保养计划不存在");
		}
		maintainPlanDetail.setFstate(CustomConst.MaintainPlanFstate.EXECUTED);
		maintainOrderService.updateInfo(maintainPlanDetail);
	}
	
	/**
	 * 查询保养计划列表
	 * @param maintainPlanNo 计划单号/资产名称/资产编码
	 * @param page 当前页数
	 * @param pagesize 每页条数
	 * @return
	 */
	@RequestMapping("/selectMaintainPlanList")
	@ResponseBody
	public Pager<Map<String, Object>> selectMaintainPlanList(
			@RequestParam(value="maintainPlanNo" , required = false) String maintainPlanNo,
			@RequestParam(value="page" , required = false) Integer page,
			@RequestParam(value="pagesize" , required = false) Integer pagesize) {
		Pager<Map<String, Object>> pager = new Pager(true);
		pager.setPageSize(pagesize==null ? 15 : pagesize);
		pager.setPageNum(page == null ? 1 : page);
		pager.addQueryParam("orgId", session.getAttribute(LoginUser.SESSION_USER_ORGID));
		pager.addQueryParam("maintainPlanNo", maintainPlanNo);
		pager.setRows(maintainOrderService.selectMaintainPlanList(pager));
		return pager;
	}
	
	/**
	 * 查询保养计划明细
	 * @param maintainDetailId 保养计划明细guid
	 * @return
	 */
	@RequestMapping("/selectMaintainPlanDetail")
	@ResponseBody
	public Map<String, Object> selectMaintainPlanDetail(
			@RequestParam(value="maintainDetailId" , required = false) String maintainDetailId) {
		Pager<Map<String, Object>>  pager = new Pager(false);
		return maintainOrderService.selectMaintainPlanDetail(pager);
	}
	
}

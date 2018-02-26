package com.phxl.ysy.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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
import com.phxl.core.base.util.LocalAssert;
import com.phxl.core.base.util.SystemConfig;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.entity.AssetsExtend;
import com.phxl.ysy.entity.AssetsRecord;
import com.phxl.ysy.entity.DeptUser;
import com.phxl.ysy.entity.Equipment;
import com.phxl.ysy.entity.RrpairFittingUse;
import com.phxl.ysy.entity.RrpairOrder;
import com.phxl.ysy.entity.RrpairOrderAcce;
import com.phxl.ysy.entity.WeixinOpenUser;
import com.phxl.ysy.service.RrpairOrderService;
import com.phxl.ysy.web.dto.InsertRrpairOrderDto;

@Controller
@RequestMapping("/rrpairOrderController")
public class RrpairOrderController {
	@Autowired
	RrpairOrderService rrpairOrderService;
	
	@Autowired
	HttpSession session;

	/**
	 * 查询设备维修各状态数量
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping("/selectRrpairFstateNum")
	@ResponseBody
	public List<Map<String, Object>> selectRrpairFstateNum(
			HttpServletRequest request,HttpServletResponse response) {
		List<Map<String, Object>> list = rrpairOrderService.selectRrpairFstateNum();
		return list;
	}
	
	*//**
	 * 维修单详情——查询备注/评价
	 * @param rrpairOrder 维修单号
	 * @param type 备注/评价（备注0 ，评价1）
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping("/selectRrpairEvaluate")
	@ResponseBody
	public Map<String, Object> selectRrpairEvaluate(
			@RequestParam(value="rrpairOrder",required = false) String rrpairOrder,
			@RequestParam(value="type",required = false) Integer type,
			HttpServletRequest request,HttpServletResponse response) {
		String str = rrpairOrderService.selectRrpairContent(rrpairOrder,type);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("value", str);
		return map;
	}
	
	*//**
	 * 维修单详情——添加备注/评价 
	 * @param rrpairOrder 维修单号
	 * @param type 备注/评价（备注0 ，评价1）
	 * @param value 备注内容
	 * @param request
	 * @param response
	 * @return
	 * @throws ValidationException
	 *//*
	@RequestMapping("/updateRrpairContent")
	@ResponseBody
	public String updateRrpairContent(
			@RequestParam(value="rrpairOrder",required = false) String rrpairOrder,
			@RequestParam(value="type",required = false) Integer type ,
			@RequestParam(value="value",required = false) String value ,
			HttpServletRequest request,HttpServletResponse response) throws ValidationException {
		String str = "error";
		RrpairOrder rrpair = rrpairOrderService.find(RrpairOrder.class, rrpairOrder);
		if (rrpair==null) {
			throw new ValidationException("当前维修记录不存在");
		}
		rrpairOrderService.updateRrpairContent(rrpairOrder,type,value);
		rrpairOrderService.pushMessage(rrpairOrder);
		str = "success";
		return str;
	}

	*//**
	 * 维修单详情——修改状态
	 * @param rrpairOrder 维修单号
	 * @param assersNowRecord 维修单当前状态（10待接修，30维修中，50待验收，80已关闭）
	 * @param rrpairType 维修单类型转变（维修中）（00内修，01外修）
	 * @param assersNextRecord 维修单状态转变（10待接修，30维修中，50待验收，80已关闭）
	 * @param isPass 验收是否通过（待验收）（0通过，1不通过）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping("/updateRrpairFstate")
	@ResponseBody
	public String updateRrpairFstate(
			@RequestParam(value="rrpairOrder",required = false) String rrpairOrder,
			@RequestParam(value="assersNowRecord",required = false) String assersNowRecord ,
			@RequestParam(value="rrpairType",required = false) String rrpairType ,
			@RequestParam(value="assersNextRecord",required = false) String assersNextRecord ,
			@RequestParam(value="isPass",required = false) Integer isPass ,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String str = "error";
		RrpairOrder rrpair = rrpairOrderService.find(RrpairOrder.class, rrpairOrder);
		if (rrpair==null) {
			throw new ValidationException("当前维修记录不存在");
		}else{
			//修改类型
			if (StringUtils.isNotBlank(rrpairType)) {
				rrpair.setRrpairType(rrpairType);
			}
			if (StringUtils.isNotBlank(assersNextRecord)) {	//修改状态
				rrpair.setOrderFstate(assersNextRecord);
			}
			if (isPass != null && (isPass == 0 || isPass == 1)) {	//是否验证通过，如果通过，则备注通过，如果不通过则备注不通过
				rrpair.setAssetsRecord("80");
				if (isPass == 0) {
					rrpair.setTfRemark("验证通过");
					//填写备注为验证通过
				}else {
					rrpair.setTfRemark("验证不通过");
					//填写备注为验证不通过
				}
			}
			rrpair.setCompletTime(new Date());
			rrpairOrderService.updateInfo(rrpair);
			rrpairOrderService.pushMessage(rrpairOrder);
		}
		str = "success";
		return str;
	}
	
	*//**
	 * 维修单详情——报修
	 * @param equipmentCode 设备编号
	 * @param equipmentName 设备名称
	 * @param address 地址
	 * @param useDeptCode 科室
	 * @param useFstate 设备状态
	 * @param urgentFlag 紧急度（10紧急，20急，30一般）
	 * @param spare 是否有备用（00无，01有）
	 * @param repairContentTyp 故障现象
	 * @param faultWords 补充说明
	 * @param faultAccessory 图片
	 * @param assetsRecord 资产编号
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//**//*
	@RequestMapping("/insertRrpair")
	@ResponseBody
	public String insertRrpair(
			@RequestParam(value="equipmentCode",required = false) String equipmentCode,
			@RequestParam(value="equipmentName",required = false) String equipmentName ,
			@RequestParam(value="address",required = false) String address ,
			@RequestParam(value="useDeptCode",required = false) String useDeptCode ,
			@RequestParam(value="useFstate",required = false) String useFstate ,
			@RequestParam(value="urgentFlag",required = false) String urgentFlag ,
			@RequestParam(value="spare",required = false) String spare ,
			@RequestParam(value="repairContentTyp",required = false) String repairContentTyp ,
			@RequestParam(value="faultWords",required = false) String faultWords ,
			@RequestParam(value="faultAccessory",required = false) String[] faultAccessory ,
			@RequestParam(value="assetsRecord",required = false) String assetsRecord ,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String str = "error";
		
		//机构ID，单据名称，单据前缀，单号长度
		String rrpairOrder = rrpairOrderService.callSpGetBill(212L, "维修单", "AA", 6);
		RrpairOrder rrpair = new RrpairOrder();
		rrpair.setRrpairOrder(rrpairOrder);
		rrpair.setAssetsRecord(assetsRecord);
		rrpair.setEquipmentCode(equipmentCode);
		rrpair.setUseDeptCode(useDeptCode);
		rrpair.setGuaranteeDate(new Date());
		rrpair.setModifyTime(new Date());
		rrpair.setAddress(address);
		rrpair.setOrderFstate("10");
		rrpair.setUrgentFlag(urgentFlag);
		rrpair.setSpare(spare);
//		if (session.getAttribute("wxUser") == null) {
//			throw new ValidationException("当前没有登录，不能报修");
//		}
//		rrpair.setRrpairUserid(session.getAttribute("openid").toString());
//		WeixinOpenUser wxUser = (WeixinOpenUser)session.getAttribute("wxUser");
//		rrpair.setRrpairUsername(wxUser.getUserName());
		rrpair.setRrpairUserid("11");
		rrpair.setRrpairUsername("11");
		rrpair.setRrpairPhone("027-59566545");//医商云客服电话
		rrpair.setRepairContentTyp(repairContentTyp);
		String fault = "";
		if (faultAccessory!=null && faultAccessory.length!=0) {
			for (int i = 0; i < faultAccessory.length; i++) {
				 //获取文件类型
		        int beginIndex = faultAccessory[i].indexOf("/");
		        int endIndex = faultAccessory[i].indexOf(";");
		        String filename = faultAccessory[i].substring(beginIndex+1,endIndex==-1 ? faultAccessory[i].length() : endIndex);
		        StringBuffer buf = new StringBuffer(".");
		        if(StringUtils.isNotBlank(filename)){
		            filename = buf.append(filename).toString();
		        }else{
		            throw new ValidationException("未知的上传文件类型!");
		        }
		        //获取文件
		        String file = faultAccessory[i];
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
		        String rCertGuid = rrpairOrder+i;
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
			}
		}
		
		rrpair.setFaultAccessory(fault);
		rrpair.setFaultWords(faultWords);
		rrpair.setRrpairOrderGuid(IdentifieUtil.getGuId());

		rrpair.setCompletTime(new Date());
		rrpairOrderService.insertInfo(rrpair);
		rrpairOrderService.pushMessage(rrpairOrder);
		str = "success";
		return str;
	}
	*//*
	/**
	 * 修改维修工单信息
	 * @param rrpairOrder 维修单号
	 * @param orderType 维修性质
	 * @param rrpairType 维修类型
	 * @param rrpairFlag 是否返修
	 * @param urgentFlag 
	 * @param spare 是否有备用（00无，01有）
	 * @param completTime 预计完成时间
	 * @param request 
	 * @param response
	 * @return
	 * @throws ValidationException
	 *//*
	@RequestMapping("/updateRrpairInfo")
	@ResponseBody
	public String updateRrpairInfo(
			@RequestParam(value="rrpairOrder",required = false) String rrpairOrder,
			@RequestParam(value="orderType",required = false) String orderType ,
			@RequestParam(value="rrpairType",required = false) String rrpairType ,
			@RequestParam(value="rrpairFlag",required = false) String rrpairFlag ,
			@RequestParam(value="urgentFlag",required = false) String urgentFlag ,
			@RequestParam(value="spare",required = false) String spare ,
			@RequestParam(value="completTime",required = false) Date completTime ,
			HttpServletRequest request,HttpServletResponse response) throws ValidationException{
		String str = "error";
		RrpairOrder rrpair = rrpairOrderService.find(RrpairOrder.class, rrpairOrder);
		if (rrpair==null) {
			throw new ValidationException("当前维修记录不存在");
		}else{
			rrpair.setOrderType(orderType);
			rrpair.setRrpairType(rrpairType);
			rrpair.setRrpairFlag(rrpairFlag);
			rrpair.setUrgentFlag(urgentFlag);
			rrpair.setSpare(spare);
			rrpair.setCompletTime(completTime);
			rrpair.setCompletTime(new Date());
			rrpairOrderService.updateInfo(rrpair);
			rrpairOrderService.pushMessage(rrpairOrder);
		}
		str = "success";
		return str;
	}
	
	//修改预估费用
	@RequestMapping("/updateRrpairQuoredPrice")
	@ResponseBody
	public String updateRrpairQuoredPrice(
			@RequestParam(value="rrpairOrder",required = false) String rrpairOrder,
			@RequestParam(value="quoredPrice",required = false) String quoredPrice ,
			@RequestParam(value="costDetail",required = false) String costDetail ,
			HttpServletRequest request,HttpServletResponse response) throws ValidationException{
		String str = "error";
		RrpairOrder rrpair = rrpairOrderService.find(RrpairOrder.class, rrpairOrder);
		if (rrpair==null) {
			throw new ValidationException("当前维修记录不存在");
		}else{
			if (StringUtils.isNotBlank(quoredPrice)) {
				rrpair.setQuotedPrice(BigDecimal.valueOf(Double.valueOf(quoredPrice)));
			}
			rrpair.setCostDetail(costDetail);
			rrpair.setCompletTime(new Date());
			rrpairOrderService.updateInfo(rrpair);
			rrpairOrderService.pushMessage(rrpairOrder);
		}
		str = "success";
		return str;
	}

	//修改维修工单信息
	@RequestMapping("/updateRrpairType")
	@ResponseBody
	public String updateRrpairType(
			@RequestParam(value="rrpairOrder",required = false) String rrpairOrder,
			@RequestParam(value="faultDescribe",required = false) String faultDescribe ,
			@RequestParam(value="faultWords",required = false) String faultWords ,
			@RequestParam(value="faultAccessory",required = false) String [] faultAccessory ,
			@RequestParam(value="repairContentType",required = false) String repairContentType ,
			@RequestParam(value="repairContentTyp",required = false) String repairContentTyp ,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		String str = "error";
		RrpairOrder rrpair = rrpairOrderService.find(RrpairOrder.class, rrpairOrder);
		if (rrpair==null) {
			throw new ValidationException("当前维修记录不存在");
		}else{
			rrpair.setFaultDescribe(faultDescribe);
			rrpair.setFaultWords(faultWords);
			String fault = "";
			if (faultAccessory!=null && faultAccessory.length!=0) {
				for (int i = 0; i < faultAccessory.length; i++) {
					 //获取文件类型
			        int beginIndex = faultAccessory[i].indexOf("/");
			        int endIndex = faultAccessory[i].indexOf(";");
			        String filename = faultAccessory[i].substring(beginIndex+1,endIndex==-1 ? faultAccessory[i].length() : endIndex);
			        StringBuffer buf = new StringBuffer(".");
			        if(StringUtils.isNotBlank(filename)){
			            filename = buf.append(filename).toString();
			        }else{
			            throw new ValidationException("未知的上传文件类型!");
			        }
			        //获取文件
			        String file = faultAccessory[i];
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
			        String rCertGuid = rrpairOrder+i;
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
				}
			}
			rrpair.setFaultAccessory(fault);
			rrpair.setRepairContentType(repairContentType);
			rrpair.setRepairContentTyp(repairContentTyp);
			rrpair.setCompletTime(new Date());
			rrpairOrderService.updateInfo(rrpair);
			rrpairOrderService.pushMessage(rrpairOrder);
		}
		str = "success";
		return str;
	}
*/

	/**
	 * 查询设备维修详情列表
	 * @author	XiongChao
 	 * @param rrpairOrderNo	维修单号
	 * @param rrpairOrderGuid		维修单guid
	 * @param pagesize		页面显示条数
	 * @param page		页数
	 * @param sortField	排序字段
	 * @param sortOrder	排序方式
	 * @param request
	 * @param response
	 * @return
	 * @throws ValidationException 
	 */
	@RequestMapping("/selectRrpairDetailList")
	@ResponseBody
	public Map<String, Object> selectRrpairDetailList(
			@RequestParam(value="rrpairOrderNo",required = false) String rrpairOrderNo,
			@RequestParam(value="rrpairOrderGuid",required = false) String rrpairOrderGuid,
			@RequestParam(value="pagesize",required = false) Integer pagesize,
			@RequestParam(value="page",required = false) Integer page,
			@RequestParam(value="sortField",required = false) String sortField,
			@RequestParam(value="sortOrder",required = false) String sortOrder,
			HttpServletRequest request,HttpServletResponse response
	) throws ValidationException {
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(false);
//		//如果没有设置当前页和每页数量，则默认第一页，每页十五条数据
//		pager.setPageSize(pagesize == null ? 15 : pagesize);
//		pager.setPageNum(page == null ? 1 : page);
//		if(StringUtils.isNotBlank(sortOrder) && StringUtils.isNotBlank(sortField)){
//		pager.addQueryParam("orderField", sortField);
//		pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sortOrder)?"asc":"desc");
//	}
		if (StringUtils.isBlank(rrpairOrderGuid)) {
			throw new ValidationException("当前维修记录guid不允许为空");
		}
		pager.addQueryParam("rrpairOrderNo", rrpairOrderNo);
		pager.addQueryParam("rrpairOrderGuid", rrpairOrderGuid);

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> selectRrpairDetailIsOrder = rrpairOrderService.selectRrpairDetailIsOrder(pager);
		if (selectRrpairDetailIsOrder.get("faultDescribe")!=null) {
			String [] faultDescribe = StringUtils.split(selectRrpairDetailIsOrder.get("faultDescribe").toString(), ',');
			selectRrpairDetailIsOrder.put("faultDescribe",faultDescribe);
		}
		result.put("selectRrpairDetail", rrpairOrderService.selectRrpairDetail(pager));
		result.put("selectRrpairDetailIsAssets", rrpairOrderService.selectRrpairDetailIsAssets(pager));
		result.put("selectRrpairDetailIsOrder", selectRrpairDetailIsOrder);
		result.put("selectRrpairDetailIsCall", rrpairOrderService.selectRrpairDetailIsCall(pager));
		result.put("selectRrpairDetailIsRrpair", rrpairOrderService.selectRrpairDetailIsRrpair(pager));
		result.put("selectRrpairDetailIsAcce", rrpairOrderService.selectRrpairDetailIsAcce(pager));
//		List<Map<String, Object>> list = rrpairOrderService.selectRrpairDetailList(pager);
		return result;
	}

	/**
	 * 查询设备维修列表
	 * @author	XiongChao
	 * @param params
	 * @param orderFstate
	 * @param pagesize
	 * @param page
	 * @param sortField
	 * @param sortOrder
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectRrpairList")
	@ResponseBody
	public Pager<Map<String, Object>> selectRrpairList(
			@RequestParam(value="params",required = false) String params,
			@RequestParam(value="orderFstate",required = false) String[] orderFstates,
			@RequestParam(value="pagesize",required = false) Integer pagesize,
			@RequestParam(value="page",required = false) Integer page,
			@RequestParam(value="sortField",required = false) String sortField,
			@RequestParam(value="sortOrder",required = false) String sortOrder,
			HttpServletRequest request,HttpServletResponse response) {
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(true);
		//如果没有设置当前页和每页数量，则默认第一页，每页十五条数据
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.setPageNum(page == null ? 1 : page);
		if(StringUtils.isNotBlank(sortOrder) && StringUtils.isNotBlank(sortField)){
			pager.addQueryParam("orderField", sortField);
			pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sortOrder)?"asc":"desc");
		}
		pager.addQueryParam("params", params);

//		orderFstates = new String[]{"20","50"};
		pager.addQueryParam("orderFstates", orderFstates);
		pager.addQueryParam("userId", session.getAttribute(LoginUser.SESSION_USERID));
		pager.addQueryParam("orgId", session.getAttribute(LoginUser.SESSION_USER_ORGID));
		pager.addQueryParam("groupName", session.getAttribute("getUserGroupName"));

		List<Map<String, Object>> list = rrpairOrderService.selectRrpairList(pager);
		for (Map<String, Object> map : list) {
			if (map.get("faultDescribe")!=null) {
				String [] faultDescribe = StringUtils.split(map.get("faultDescribe").toString(), ',');
				map.put("faultDescribe",faultDescribe);
			}
		}
		
		pager.setRows(list);
		return pager;
	}

	/**@author XiongChao
	 * 查询维修操作记录
	 * @param params	模糊查询参数（操作员，操作分类。非必填）
	 * @param opTypeSum 操作分类（非必填）
	 * @param userName	 操作员（非必填）
	 * @param pagesize		页面显示条数默认15（非必填）
	 * @param page		当前也买你数默认第一页（非必填）
	 * @param sortField	排序字段（非必填）
	 * @param sortOrder	排序方式ascend/descend（非必填）
	 * @return list
	 */
	@RequestMapping("/selectEqOperationList")
	@ResponseBody
	public List<Map<String,Object>> selectEqOperationList(
			@RequestParam(value="params",required = false) String params,
			@RequestParam(value="opTypeSum",required = false) String opTypeSum,
		  	@RequestParam(value="userName",required = false) String userName,
		  	@RequestParam(value="pagesize",required = false) Integer pagesize,
		  	@RequestParam(value="page",required = false) Integer page,
		  	@RequestParam(value="sortField",required = false) String sortField,
		  	@RequestParam(value="sortOrder",required = false) String sortOrder){
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(true);
		//如果没有设置当前页和每页数量，则默认第一页，每页十五条数据
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.setPageNum(page == null ? 1 : page);

		pager.addQueryParam("opTypeSum", opTypeSum);
		pager.addQueryParam("userName", userName);
		pager.addQueryParam("params", params);
		if(StringUtils.isNotBlank(sortOrder) && StringUtils.isNotBlank(sortField)){
			pager.addQueryParam("orderField", sortField);
			pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sortOrder)?"asc":"desc");
		}
		List<Map<String, Object>> list = rrpairOrderService.selectEqOperationList(pager);
		return list;
	}
	
	/**
	 * 添加或修改维修记录
	 * @param rrpairOrderGuid 维修记录GUID
	 * @param assetsRecordGuid 资产信息GUID
	 * @param isRepairs 是否需要修改或新增报修信息（是true，不是false）
	 * @param orderFstate 维修状态（10申请（提交），20指派（指派），30维修中,50待验收（完成），90已关闭（关闭） ）
	 * @param equipmentCode 设备编号
	 * @param urgentFlag 紧急度
	 * @param rrpairSend 是否送修
	 * @param spare 有无备用
	 * @param rrpairPhone 报修人联系电话
	 * @param faultDescribe 故障现象
	 * @param useFstate 是否在用 (01在用，02停用)
	 * @param tfRemarkBx 备注
	 * @param failCause 故障描述
	 * @param tfAccessory 附件（多个附件一,拆分）
	 * @param rrpairType 维修方式（00内修，01外修）（如果不填写维修信息，则为空）
	 * @param inRrpairPhone 内修人联系方式
	 * @param repairContentType 故障类型
	 * @param repairContentTyp 故障原因
	 * @param repairResult 维修结果
	 * @param actualPrice 维修费用（总计）
	 * @param tfRemarkWx 维修备注（内修）
	 * @param offCause 关闭原因
	 * @param followupTreatment 后续处理
	 * @param tfRemarkGb 关闭备注
	 * @param outOrg 指派服务商
	 * @param outRrpairPhone 外修人联系方式
	 * @param completTime 预计完成时间
	 * @param tfRemarkZp 指派备注（外修）
	 * @throws Exception
	 * @author zhangyanli
	 */
	@RequestMapping("/insertOrUpdateRrpair")
	@ResponseBody
	public void insertOrUpdateRrpair(
//			@RequestParam(value="rrpairOrderGuid",required = false) String rrpairOrderGuid,
//			@RequestParam(value="assetsRecordGuid",required = false) String assetsRecordGuid,
//			@RequestParam(value="isRepairs",required = false) Boolean isRepairs,
//			@RequestParam(value="orderFstate",required = false) String orderFstate,
//			@RequestParam(value="equipmentCode",required = false) String equipmentCode,
//			@RequestParam(value="urgentFlag",required = false) String urgentFlag,
//			@RequestParam(value="rrpairSend",required = false) String rrpairSend,
//			@RequestParam(value="spare",required = false) String spare,
//			@RequestParam(value="rrpairPhone",required = false) String rrpairPhone,
//			@RequestParam(value="faultDescribe",required = false) String faultDescribe,
//			@RequestParam(value="useFstate",required = false) String useFstate,
//			@RequestParam(value="tfRemarkBx",required = false) String tfRemarkBx,
//			@RequestParam(value="faultWords",required = false) String faultWords,
//			@RequestParam(value="tfAccessory",required = false) String [] tfAccessory,
//			@RequestParam(value="rrpairType",required = false) String rrpairType,
//			@RequestParam(value="inRrpairPhone",required = false) String inRrpairPhone,
//			@RequestParam(value="repairContentType",required = false) String repairContentType,
//			@RequestParam(value="repairContentTyp",required = false) String repairContentTyp,
//			@RequestParam(value="repairResult",required = false) String repairResult,
//			@RequestParam(value="actualPrice",required = false) String actualPrice,
//			@RequestParam(value="tfRemarkWx",required = false) String tfRemarkWx,
//			@RequestParam(value="offCause",required = false) String offCause,
//			@RequestParam(value="followupTreatment",required = false) String followupTreatment,
//			@RequestParam(value="tfRemarkGb",required = false) String tfRemarkGb,
//			@RequestParam(value="outOrg",required = false) String outOrg,
//			@RequestParam(value="outRrpairPhone",required = false) String outRrpairPhone,
//			@RequestParam(value="completTime",required = false) String completTime,
//			@RequestParam(value="tfRemarkZp",required = false) String tfRemarkZp
			HttpServletRequest request,HttpServletResponse response
			) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//配置项:默认日期格式
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项:忽略未知属性
		//获取当前要添加或修改的维修工单信息
		InsertRrpairOrderDto dto = objectMapper.readValue(request.getReader(), InsertRrpairOrderDto.class);
		AssetsRecord assetsRecord = null;
		if (dto.getAssetsRecordGuid()!=null) {
			assetsRecord = rrpairOrderService.find(AssetsRecord.class, dto.getAssetsRecordGuid());
			if (assetsRecord==null) {
				throw new ValidationException("当前资产信息不存在");
			}else{
				assetsRecord.setUseFstate(dto.getUseFstate());
			}
		}
		RrpairOrder rrpair = null;
		String rrpairOrder = null;
		
		if (StringUtils.isNotBlank(dto.getRrpairOrderGuid())) {
			rrpair = rrpairOrderService.find(RrpairOrder.class, dto.getRrpairOrderGuid());
			if (rrpair==null) {
				throw new ValidationException("当前维修记录不存在");
			}
			rrpairOrder = rrpair.getRrpairOrderNo();
		}else{
			rrpair = new RrpairOrder();
			//机构ID，单据名称，单据前缀，单号长度
			rrpairOrder = rrpairOrderService.callSpGetBill(212L, "维修单", "AA", 6);
			rrpair.setRrpairOrderGuid(IdentifieUtil.getGuId());
			rrpair.setRrpairOrderNo(rrpairOrder);
			rrpair.setCreateDate(new Date());
			rrpair.setRrpairUserid(session.getAttribute(LoginUser.SESSION_USERID).toString());
			rrpair.setRrpairUsername(session.getAttribute(LoginUser.SESSION_USERNAME).toString());
			DeptUser deptUser = new DeptUser();
			deptUser.setUserId(session.getAttribute(LoginUser.SESSION_USERID).toString());
			DeptUser duser = rrpairOrderService.searchEntity(deptUser);
			rrpair.setUseDeptCode(duser.getDeptGuid());
			rrpair.setuOrg(session.getAttribute(LoginUser.SESSION_USER_ORGID).toString());
		}
		
		if (StringUtils.isNotBlank(dto.getOrderFstate())) {
			rrpair.setOrderFstate(dto.getOrderFstate());
		}
		//如果需要填写报修信息
		if (dto.getIsRepairs()) {
			rrpair.setEquipmentCode(dto.getEquipmentCode());
			rrpair.setUrgentFlag(dto.getUrgentFlag());
			rrpair.setRrpairSend(dto.getRrpairSend());
			rrpair.setSpare(dto.getSpare());
			rrpair.setRrpairPhone(dto.getRrpairPhone());
			String faultDescribe = "";
			if (dto.getFaultDescribe()!=null) {
				for (String s : dto.getFaultDescribe()) {
					faultDescribe = faultDescribe+s+",";
				}
			}
			
			rrpair.setFaultDescribe(faultDescribe);
			rrpair.setTfRemarkBx(dto.getTfRemarkBx());
			rrpair.setFaultWords(dto.getFaultWords());
			String fault = "";
			if (dto.getTfAccessory()!=null && dto.getTfAccessory().size()!=0) {
				int i = 0;
				for (String str : dto.getTfAccessory()) {
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
			        String rCertGuid = rrpairOrder+i;
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
			rrpair.setFaultAccessory(fault);
		}
		
		//如果资产信息不为空，则此维修记录为新增，填写相对应的字段信息
		if (assetsRecord!=null) {
			rrpair.setAssetsRecord(assetsRecord.getAssetsRecord());
			rrpair.setUseDeptCode(assetsRecord.getUseDeptCode());
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		if (StringUtils.isNotBlank(dto.getRrpairType())) {
			rrpair.setRrpairType(dto.getRrpairType());
			if (dto.getRrpairType().equals("00")) {
				rrpair.setInRrpairPhone(dto.getInRrpairPhone()==null ? rrpair.getInRrpairPhone() : dto.getInRrpairPhone());
				rrpair.setInRrpairUserid(session.getAttribute(LoginUser.SESSION_USERID).toString());
				rrpair.setInRrpairUsername(session.getAttribute(LoginUser.SESSION_USERNAME).toString());
				rrpair.setRepairContentType(dto.getRepairContentType()==null ? rrpair.getRepairContentType() : dto.getRepairContentType());
				rrpair.setRepairContentTyp(dto.getRepairContentTyp()==null ? rrpair.getRepairContentTyp() : dto.getRepairContentTyp());
				rrpair.setRepairResult(dto.getRepairResult()==null ? rrpair.getRepairResult() : dto.getRepairResult());
				if (StringUtils.isNotBlank(dto.getActualPrice())) {
					rrpair.setActualPrice(BigDecimal.valueOf(Long.valueOf(dto.getActualPrice())));
				}
				rrpair.setTfRemarkWx(dto.getTfRemarkWx()==null ? rrpair.getTfRemarkWx() : dto.getTfRemarkWx());
				rrpair.setRrpairDate(new Date()); 	//修复日期
				rrpair.setOffCause(dto.getOffCause()==null ? rrpair.getOffCause() : dto.getOffCause());
				rrpair.setFollowupTreatment(dto.getFollowupTreatment()==null ? rrpair.getFollowupTreatment() : dto.getFollowupTreatment());
				rrpair.setTfRemarkGb(dto.getTfRemarkGb()==null ? rrpair.getTfRemarkGb() : dto.getTfRemarkGb());
			}else if (dto.getRrpairType().equals("01")){
				rrpair.setOutOrg(dto.getOutOrg()==null ? rrpair.getOutOrg() : dto.getOutOrg());
				rrpair.setOutRrpairPhone(dto.getOutRrpairPhone()==null ? rrpair.getOutRrpairPhone() : dto.getOutRrpairPhone());
				rrpair.setCompletTime(dto.getCompletTime()=="" ? rrpair.getCompletTime() : format.parse(dto.getCompletTime()));
				rrpair.setTfRemarkZp(dto.getTfRemarkZp()==null ? rrpair.getTfRemarkZp() : dto.getTfRemarkZp());
			}else {
				throw new ValidationException("当前维修类型有误！");
			}
		}
		rrpair.setModifyTime(new Date()); 	//最后更新时间
		List<String> assetsExtendGuid = new ArrayList<String>();
		List<Integer> acceNum = new ArrayList<Integer>();
		if (dto.getAssetsExtendGuids() == null) {
			
		}else{
			List<Map<String, Object>> maps = dto.getAssetsExtendGuids();
			if (dto.getAssetsExtendGuids()!=null || dto.getAssetsExtendGuids().size()!=0) {
				for (Map<String, Object> m : maps) {
					assetsExtendGuid.add(m.get("assetsExtendGuid").toString());
					acceNum.add(Integer.valueOf(m.get("acceNum").toString()));
				}
			}
		}
		rrpairOrderService.insertRrpairOrder(rrpair,assetsRecord,assetsExtendGuid,acceNum);
	}
	
	/**
	 * 查询当前维修记录的维修配件使用列表
	 * @param rrpairOrderGuid 维修记录guid
	 * @param pagesize
	 * @param page
	 * @return
	 * @author zhangyanli
	 */
	@RequestMapping("/selectRrpairFittingList")
	@ResponseBody
	public Pager<Map<String, Object>> selectRrpairFittingList(
			@RequestParam(value="rrpairOrderGuid",required = false) String rrpairOrderGuid,
			@RequestParam(value="pagesize",required = false) Integer pagesize,
			@RequestParam(value="page",required = false) Integer page){
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(false);
		//如果没有设置当前页和每页数量，则默认第一页，每页十五条数据
		pager.setPageNum(page == null ? 1 : page);
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.addQueryParam("rrpairOrderGuid", rrpairOrderGuid);
		List<Map<String, Object>> list = rrpairOrderService.selectRrpairFittingList(pager);
		pager.setRows(list);
		return pager;
	}
	
	/**
	 * 从资产配件列表添加维修配件使用信息
	 * @param rrpairOrderGuid 维修记录guid
	 * @param assetsExtendGuid 资产附件guid
	 * @param acceNum 数量
	 * @throws Exception
	 * @author zhangyanli
	 */
	@RequestMapping("/insertRrpairFitting")
	@ResponseBody
	public void insertRrpairFitting(
//			@RequestParam(value="rrpairOrderGuid",required = false) String rrpairOrderGuid,
//			@RequestParam(value="assetsExtendGuid",required = false) String [] assetsExtendGuid,
//			@RequestParam(value="acceNum",required = false) Integer [] acceNum,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//配置项:默认日期格式
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项:忽略未知属性

		//订单备货信息
		Map<String, Object> dto = objectMapper.readValue(request.getReader(), HashMap.class);
		String rrpairOrderGuid = dto.get("rrpairOrderGuid").toString();
		List<Map<String, Object>> maps = (List<Map<String, Object>>)dto.get("assetsExtendGuids");

		List<String> assetsExtendGuid = new ArrayList<String>();
		List<Integer> acceNum = new ArrayList<Integer>();
		if (maps!=null || maps.size()!=0) {
			for (Map<String, Object> m : maps) {
				assetsExtendGuid.add(m.get("assetsExtendGuid").toString());
				acceNum.add(Integer.valueOf(m.get("acceNum").toString()));
			}
		}else{
			throw new ValidationException("当前添加列表为空！");
		}
		
		if (StringUtils.isBlank(rrpairOrderGuid)) {
			throw new ValidationException("当前维修记录guid不允许为空");
		}
		RrpairOrder rrpairOrder = rrpairOrderService.find(RrpairOrder.class, rrpairOrderGuid);
		if (rrpairOrder == null) {
			throw new ValidationException("当前维修记录不存在");
		}
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(false);
		pager.addQueryParam("rrpairOrderGuid", rrpairOrderGuid);
		//根据维修编号查询维修配件使用列表
		List<Map<String, Object>> list = rrpairOrderService.selectRrpairFittingList(pager);
		
		//判断是否需要添加
		boolean flag = true;
//		if (list!=null && list.size()!=0) {
//			for (Map<String, Object> map : list) {
//				if (equipment.getEquipmentName().equals(map.get("acceName")) && 
//					equipment.getFmodel().equals(map.get("acceFmodel")) && 
//					equipment.getSpec().equals(map.get("acceSpec"))) {
//					//如果是有编号的配件，则是唯一的，不允许重复添加
//					if (map.get("assetsRecord")!=null && 
//							StringUtils.isNotBlank(map.get("assetsRecord").toString()) && acceNum != 0) {
//						throw new ValidationException("当前配件已经添加维修使用，不允许重复添加");
//					}
//					//如果没有编号，则在原来的数量上修改
//					else {
//						RrpairFittingUse rrUse = rrpairOrderService.find(RrpairFittingUse.class, map.get("rrpairFittingUseGuid").toString());
//						rrUse.setAcceNum(rrUse.getAcceNum().add(BigDecimal.valueOf(acceNum)));
//						flag = false;
//					}
//				}
//			}
//		}
		rrpairOrderService.insertRrpairFitting(rrpairOrder,rrpairOrderGuid, assetsExtendGuid, acceNum);
		
	}

	/**
	 * 手动填写添加附件
	 * @param rrpairOrderGuid 维修记录guid
	 * @param assetsRecord 附件编号
	 * @param acceName 附件名称
	 * @param acceFmodel 附件型号
	 * @param acceSpec 附件规格
	 * @param acceUnit 附件单位
	 * @param unitPrice 单价
	 * @param acceNum 数量
	 * @throws ValidationException
	 * @author zhangyanli
	 */
	@RequestMapping("/insertRrpairExtend")
	@ResponseBody
	public void insertRrpairExtend(
			@RequestParam(value="rrpairOrderGuid",required = false) String rrpairOrderGuid,
			@RequestParam(value="assetsRecord",required = false) String assetsRecord,
			@RequestParam(value="acceName",required = false) String acceName,
			@RequestParam(value="acceFmodel",required = false) String acceFmodel,
			@RequestParam(value="acceSpec",required = false) String acceSpec,
			@RequestParam(value="acceUnit",required = false) String acceUnit,
			@RequestParam(value="unitPrice",required = false) BigDecimal unitPrice,
			@RequestParam(value="acceNum",required = false) Integer acceNum
			) throws ValidationException{
		if (StringUtils.isBlank(rrpairOrderGuid)) {
			throw new ValidationException("当前维修记录guid不允许为空");
		}
		RrpairOrder rrpairOrder = rrpairOrderService.find(RrpairOrder.class, rrpairOrderGuid);
		if (rrpairOrder == null) {
			throw new ValidationException("当前维修记录不存在");
		}
		if (acceNum == null || acceNum <=0 ) {
			throw new ValidationException("当前数量必须是非0正整数");
		}
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(false);
		pager.addQueryParam("rrpairOrderGuid", rrpairOrderGuid);
		//根据维修编号查询维修配件使用列表
		List<Map<String, Object>> list = rrpairOrderService.selectRrpairFittingList(pager);
		
		//判断是否需要添加
		boolean flag = true;
//		if (list!=null && list.size()!=0) {
//			for (Map<String, Object> map : list) {
//				if (acceName.equals(map.get("acceName")) && 
//					acceFmodel.equals(map.get("acceFmodel")) && 
//					acceSpec.equals(map.get("acceSpec"))) {
//					//如果是有编号的配件，则是唯一的，不允许重复添加
//					if (map.get("assetsRecord")!=null && 
//						StringUtils.isNotBlank(map.get("assetsRecord").toString()) && acceNum != 0) {
//						throw new ValidationException("当前配件已经添加维修使用，不允许重复添加");
//					}
//					//如果没有编号，则在原来的数量上修改
//					else {
//						RrpairFittingUse rrUse = rrpairOrderService.find(RrpairFittingUse.class, map.get("rrpairFittingUseGuid").toString());
//						rrUse.setAcceNum(rrUse.getAcceNum().add(BigDecimal.valueOf(acceNum)));
//						flag = false;
//					}
//				}
//			}
//		}
		
		if (flag) {
			RrpairFittingUse rrpairFittingUse = new RrpairFittingUse();
			rrpairFittingUse.setRrpairFittingUseGuid(IdentifieUtil.getGuId());
			rrpairFittingUse.setRrpairOrderGuid(rrpairOrderGuid);
			rrpairFittingUse.setAssetsRecord(assetsRecord);
			rrpairFittingUse.setAcceName(acceName);
			rrpairFittingUse.setAcceFmodel(acceFmodel);
			rrpairFittingUse.setAcceSpec(acceSpec);
			rrpairFittingUse.setAcceUnit(acceUnit);
			rrpairFittingUse.setUnitPrice(unitPrice);
			rrpairFittingUse.setAcceNum(BigDecimal.valueOf(acceNum));
			rrpairOrderService.insertInfo(rrpairFittingUse);
		}
	}
	
	/**
	 * 删除当前维修配件
	 * @param rrpairOrderGuid 维修配件guid
	 * @throws ValidationException
	 * @author zhangyanli
	 */
	@RequestMapping("/deleteRrpairFitting")
	@ResponseBody
	public void deleteRrpairFitting(
			@RequestParam(value="rrpairFittingUseGuid",required = false) String rrpairFittingUseGuid) throws ValidationException{
		if (StringUtils.isBlank(rrpairFittingUseGuid)) {
			throw new ValidationException("当前维修配件guid不允许为空");
		}
		RrpairFittingUse rrpairFittingUse = rrpairOrderService.find(RrpairFittingUse.class, rrpairFittingUseGuid);
		if (rrpairFittingUse == null) {
			throw new ValidationException("当前维修记录不存在");
		}
		rrpairOrderService.deleteInfo(rrpairFittingUse);	
	}
	
	/**
	 * 维修工单验收
	 * @param rrpairOrderGuid 维修记录guid
	 * @param rrAcceFstate 通过/不通过 65通过 66不通过
	 * @param tfRemark 备注
	 * @param evaluate 评价
	 * @param notCause 不通过原因
	 * @throws ValidationException
	 * @author zhangyanli
	 */
	@RequestMapping("/insertRrpairOrderAcce")
	@ResponseBody
	public void insertRrpairOrderAcce(
			@RequestParam(value="rrpairOrderGuid",required = false) String rrpairOrderGuid,
			@RequestParam(value="rrAcceFstate",required = false) String rrAcceFstate,
			@RequestParam(value="tfRemark",required = false) String tfRemark,
			@RequestParam(value="notCause",required = false) String notCause,
			@RequestParam(value="evaluate",required = false) String evaluate
			) throws ValidationException{
		RrpairOrder rrpairOrder = rrpairOrderService.find(RrpairOrder.class, rrpairOrderGuid);
		if (rrpairOrder==null) {
			throw new ValidationException("当前维修记录不存在");
		}
		RrpairOrderAcce rracce = new RrpairOrderAcce();
		rracce.setRrpairOrder(rrpairOrderGuid);
		RrpairOrderAcce acce = rrpairOrderService.searchEntity(rracce);
		if (acce != null) {
			acce.setRrAcceFstate(StringUtils.isBlank(rrAcceFstate) ? acce.getRrAcceFstate() : rrAcceFstate);
			acce.setTfRemark(StringUtils.isBlank(tfRemark) ? acce.getTfRemark() : tfRemark);
			acce.setEvaluate(StringUtils.isBlank(evaluate) ? acce.getEvaluate() : evaluate);
			acce.setNotCause(StringUtils.isBlank(notCause) ? acce.getNotCause() : notCause);
			acce.setEvaluate(StringUtils.isBlank(evaluate) ? acce.getEvaluate() : evaluate);
			rrpairOrderService.updateInfo(acce);
		}else{
			RrpairOrderAcce rrpairOrderAcce = new RrpairOrderAcce();
			rrpairOrderAcce.setRrpairOrderAcce(IdentifieUtil.getGuId());
			rrpairOrderAcce.setRrpairOrder(rrpairOrderGuid);
			rrpairOrderAcce.setRrAcceUserid(session.getAttribute(LoginUser.SESSION_USERID).toString());
			rrpairOrderAcce.setRrAcceUsername(session.getAttribute(LoginUser.SESSION_USERNAME).toString());
			rrpairOrderAcce.setRrAcceFstate(rrAcceFstate);
			rrpairOrderAcce.setEvaluate(evaluate);
			rrpairOrderAcce.setTfRemark(tfRemark);
			if (rrAcceFstate.equals("66")) {
				rrpairOrderAcce.setNotCause(notCause);
			}
			rrpairOrderAcce.setRrAcceDate(new Date());
			
			rrpairOrder.setOrderFstate("90");
			rrpairOrderService.insertRrpairOrderAcce(rrpairOrderAcce, rrpairOrder);
		}
	}
	
	/**
	 * 指派维修工单
	 * @param rrpairOrderGuid 维修记录GUID
	 * @param orderFstate 维修状态 （内修30 ， 外修20 ， 关闭90）如果是暂存，则为空
	 * @param rrpairType 维修方式（00内修，01外修）(关闭02)
	 * @param inRrpairPhone 内修人联系方式
	 * @param callDeptCode 维修组code（内修）
	 * @param callDeptName 维修组名称（内修）
	 * @param inRrpairUserid 内修指派人id
	 * @param inRrpairUsername 内修指派人姓名
	 * @param tfRemarkWx 指派备注（内修）
	 * @param offCause 关闭原因
	 * @param followupTreatment 后续处理
	 * @param tfRemarkGb 关闭备注
	 * @param outOrg 指派服务商
	 * @param outRrpairPhone 外修人联系方式
	 * @param completTime 预计完成时间
	 * @param tfRemarkZp 指派备注（外修）
	 * @author zhangyanli
	 * @throws ParseException 
	 */
	@RequestMapping("/designateInOrOut")
	@ResponseBody
	public void designateInOrOut(
			@RequestParam(value="rrpairOrderGuid",required = false) String rrpairOrderGuid,
			@RequestParam(value="orderFstate",required = false) String orderFstate,
			@RequestParam(value="rrpairType",required = false) String rrpairType,
			@RequestParam(value="inRrpairPhone",required = false) String inRrpairPhone,
			@RequestParam(value="callDeptCode",required = false) String callDeptCode,
			@RequestParam(value="callDeptName",required = false) String callDeptName,
			@RequestParam(value="inRrpairUserid",required = false) String inRrpairUserid,
			@RequestParam(value="inRrpairUsername",required = false) String inRrpairUsername,
//			@RequestParam(value="tfRemarkWx",required = false) String tfRemarkWx,
			@RequestParam(value="offCause",required = false) String offCause,
			@RequestParam(value="followupTreatment",required = false) String followupTreatment,
			@RequestParam(value="tfRemarkGb",required = false) String tfRemarkGb,
			@RequestParam(value="outOrg",required = false) String outOrg,
			@RequestParam(value="outRrpairPhone",required = false) String outRrpairPhone,
			@RequestParam(value="completTime",required = false) String completTime,
			@RequestParam(value="tfRemarkZp",required = false) String tfRemarkZp
			) throws ValidationException, ParseException{
		RrpairOrder rrpair = rrpairOrderService.find(RrpairOrder.class, rrpairOrderGuid);
		if (rrpair==null) {
			throw new ValidationException("当前维修记录不存在");
		}
		
		if (StringUtils.isNotBlank(orderFstate)) {
			rrpair.setOrderFstate(orderFstate);
		}
		rrpair.setModifyTime(new Date());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");	
		if (StringUtils.isNotBlank(rrpairType)) {
			//如果指派信息是内修，则填写内修信息
			if (rrpairType.equals("00")) {
				rrpair.setRrpairType(rrpairType);
				rrpair.setCallDeptName(callDeptName);
				rrpair.setCallDeptCode(callDeptCode);
				rrpair.setInRrpairUserid(inRrpairUserid);
				rrpair.setInRrpairUsername(inRrpairUsername);
				rrpair.setTfRemarkZp(tfRemarkZp);
				rrpair.setCallTime(new Date());
				rrpair.setCompletTime(StringUtils.isBlank(completTime)==true ? null : format.parse(completTime));
			}
			//如果指派信息是外修，则填写外修信息
			else if (rrpairType.equals("01")){
				rrpair.setRrpairType(rrpairType);
				rrpair.setOutOrg(outOrg);
				rrpair.setOutRrpairPhone(outRrpairPhone);
				rrpair.setCallTime(new Date());
				rrpair.setCompletTime(format.parse(completTime));
				rrpair.setTfRemarkZp(tfRemarkZp);
			}
			//如果指派时关闭维修单，则填写关闭原因等信息
			else{
				rrpair.setOffCause(offCause);
				rrpair.setFollowupTreatment(followupTreatment);
				rrpair.setTfRemarkGb(tfRemarkGb);
			}
		}
		rrpairOrderService.updateInfo(rrpair);
	}
	
	/**
	 * 修改维修工单状态
	 * @param rrpairOrderGuid 维修工单guid
	 * @param orderFstate 维修工单改变后的状态 (30维修中[接修],50待验收[完成]，80拒绝[拒绝],90关闭[关闭] )
	 * @param refuseCause 拒绝原因
	 * @param otherCause 其他原因
	 * @param tfRemarkJj 拒绝备注
	 * @throws ValidationException
	 */
	@RequestMapping("/updateRrpairOrderFstate")
	@ResponseBody
	public void updateRrpairOrderFstate(
			@RequestParam(value="rrpairOrderGuid",required = false) String rrpairOrderGuid,
			@RequestParam(value="orderFstate",required = false) String orderFstate,
			@RequestParam(value="refuseCause",required = false) String refuseCause,
			@RequestParam(value="otherCause",required = false) String otherCause,
			@RequestParam(value="tfRemarkJj",required = false) String tfRemarkJj
			) throws ValidationException{
		if (StringUtils.isBlank(rrpairOrderGuid)) {
			throw new ValidationException("维修工单GUID不允许为空");
		}
		RrpairOrder rrpair = rrpairOrderService.find(RrpairOrder.class, rrpairOrderGuid);
		if (rrpair==null) {
			throw new ValidationException("当前维修记录不存在");
		}
		System.out.println("rrpair.getRrpairOrderGuid()"+rrpair.getRrpairOrderGuid());
		if (StringUtils.isBlank(orderFstate)) {
			throw new ValidationException("维修状态不允许为空");
		}
		//如果是内修人主动接修，则自动填写内修人信息
		if (orderFstate.equals(CustomConst.RrpairOrderFstate.MAINTENANCE)) {
			if ((CustomConst.RrpairOrderFstate.AWAITING_REPAIR).equals(rrpair.getOrderFstate())) {
				rrpair.setInRrpairUserid(session.getAttribute(LoginUser.SESSION_USERID).toString());
				rrpair.setInRrpairUsername(session.getAttribute(LoginUser.SESSION_USERNAME).toString());
			}
		}
		
		//如果外修拒绝维修单，则填写拒绝原因等信息
		if (orderFstate.equals(CustomConst.RrpairOrderFstate.REJECT)) {
			rrpair.setRefuseCause(refuseCause);
			rrpair.setOtherCause(otherCause);
			rrpair.setTfRemarkJj(tfRemarkJj);
		}
		rrpair.setOrderFstate(orderFstate);
		rrpair.setModifyTime(new Date());
		rrpairOrderService.updateInfo(rrpair);
	}
}

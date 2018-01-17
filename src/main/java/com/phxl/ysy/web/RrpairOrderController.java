package com.phxl.ysy.web;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.util.FTPUtils;
import com.phxl.core.base.util.IdentifieUtil;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.core.base.util.SystemConfig;
import com.phxl.ysy.entity.RrpairOrder;
import com.phxl.ysy.entity.WeixinOpenUser;
import com.phxl.ysy.service.RrpairOrderService;

@Controller
@RequestMapping("/rrpairOrderController")
public class RrpairOrderController {
	@Autowired
	RrpairOrderService rrpairOrderService;
	
	@Autowired
	HttpSession session;
	
	/**
	 * 查询设备维修列表 
	 * @param rrpairOrder 维修单号
	 * @param orderFstate 维修状态（10待接修，30维修中，50待验收，80已关闭）
	 * @param urgentFlag 紧急度（10紧急，20急，30一般）
	 * @param time 时间
	 * @param sort 排序（正序esc，倒序desc）
	 * @param equipmentCode 设备编号
	 * @param pagesize
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectRrpairList")
	@ResponseBody
	public List<Map<String, Object>> selectRrpairList(
			@RequestParam(value="rrpairOrder",required = false) String rrpairOrder,
			@RequestParam(value="orderFstate",required = false) String orderFstate,
			@RequestParam(value="urgentFlag",required = false) String urgentFlag,
			@RequestParam(value="time",required = false) String time,
			@RequestParam(value="sort",required = false) String sort,
			@RequestParam(value="equipmentCode",required = false) String equipmentCode,
			@RequestParam(value="pagesize",required = false) Integer pagesize,
			@RequestParam(value="page",required = false) Integer page,
			HttpServletRequest request,HttpServletResponse response
			) {
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(true);
		//如果没有设置当前页和每页数量，则默认第一页，每页十五条数据
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.setPageNum(page == null ? 1 : page);
		
		pager.addQueryParam("rrpairOrder", rrpairOrder);
		pager.addQueryParam("orderFstate", orderFstate);
		pager.addQueryParam("urgentFlag", urgentFlag);
		pager.addQueryParam("time", time);
		pager.addQueryParam("sort", sort);
		pager.addQueryParam("equipmentCode", equipmentCode);
		
		List<Map<String, Object>> list = rrpairOrderService.selectRrpairList(pager);
		return list;
	}
	
	/**
	 * 查询设备维修各状态数量
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectRrpairFstateNum")
	@ResponseBody
	public List<Map<String, Object>> selectRrpairFstateNum(
			HttpServletRequest request,HttpServletResponse response) {
		List<Map<String, Object>> list = rrpairOrderService.selectRrpairFstateNum();
		return list;
	}
	
	/**
	 * 维修单详情——查询备注/评价
	 * @param rrpairOrder 维修单号
	 * @param type 备注/评价（备注0 ，评价1）
	 * @param request
	 * @param response
	 * @return
	 */
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
	
	/**
	 * 维修单详情——添加备注/评价 
	 * @param rrpairOrder 维修单号
	 * @param type 备注/评价（备注0 ，评价1）
	 * @param value 备注内容
	 * @param request
	 * @param response
	 * @return
	 * @throws ValidationException
	 */
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

	/**
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
	 */
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
	
	/**
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
	 */
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
	 */
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
	
}

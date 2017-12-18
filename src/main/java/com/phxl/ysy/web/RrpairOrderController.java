package com.phxl.ysy.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.util.IdentifieUtil;
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
	
	@RequestMapping("/selectRrpairFstateNum")
	@ResponseBody
	public List<Map<String, Object>> selectRrpairFstateNum(
			HttpServletRequest request,HttpServletResponse response) {
		List<Map<String, Object>> list = rrpairOrderService.selectRrpairFstateNum();
		return list;
	}
	
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
		str = "success";
		return str;
	}

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
			if (rrpairType!= null) {
				rrpair.setRrpairType(rrpairType);
			}else if (assersNextRecord!= null) {	//修改状态
				rrpair.setAssetsRecord(assersNextRecord);
			}else if (isPass!= null) {	//是否验证通过，如果通过，则备注通过，如果不通过则备注不通过
				rrpair.setAssetsRecord("80");
				if (isPass == 0) {
					//填写备注为验证通过
				}else {
					//填写备注为验证不通过
				}
			}
			rrpairOrderService.updateInfo(rrpair);
		}
		str = "success";
		return str;
	}
	

	@RequestMapping("/insertRrpair")
	@ResponseBody
	public String insertRrpair(
			@RequestParam(value="equipmentCode",required = false) String equipmentCode,
			@RequestParam(value="equipmentName",required = false) String equipmentName ,
			@RequestParam(value="address",required = false) String address ,
			@RequestParam(value="useDept",required = false) String useDept ,
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
		rrpair.setUseDeptCode(useDept);
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
		String fault = null;
		if (faultAccessory!=null) {
			for (int i = 0; i < faultAccessory.length; i++) {
				fault += faultAccessory[i]+";";
			}
		}
		rrpair.setFaultAccessory(fault);
		rrpair.setFaultWords(faultWords);
		rrpair.setRrpairOrderGuid(IdentifieUtil.getGuId());
		
		rrpairOrderService.insertInfo(rrpair);
		
		str = "success";
		return str;
	}
}

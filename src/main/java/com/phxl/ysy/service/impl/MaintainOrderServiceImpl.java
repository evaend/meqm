package com.phxl.ysy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.core.base.util.IdentifieUtil;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.dao.EquipmentMapper;
import com.phxl.ysy.dao.MaintainOrderMapper;
import com.phxl.ysy.dao.MaintainPlanMapper;
import com.phxl.ysy.dao.MaintainTemplateMapper;
import com.phxl.ysy.dao.MaintainTypeMapper;
import com.phxl.ysy.entity.AssetsRecord;
import com.phxl.ysy.entity.EqCheckbox;
import com.phxl.ysy.entity.Equipment;
import com.phxl.ysy.entity.MaintainOrder;
import com.phxl.ysy.entity.MaintainOrderDetail;
import com.phxl.ysy.entity.MaintainPlan;
import com.phxl.ysy.entity.MaintainTemplate;
import com.phxl.ysy.entity.MaintainTemplateDetail;
import com.phxl.ysy.entity.MaintainType;
import com.phxl.ysy.service.MaintainOrderService;
import com.phxl.ysy.service.ProcedureService;
import com.phxl.ysy.web.dto.MaintainPlanDto;

@Service
public class MaintainOrderServiceImpl extends BaseService implements MaintainOrderService {
	@Autowired
	HttpSession session;
	
	@Autowired
	MaintainOrderMapper maintainOrderMapper;
	
	@Autowired
	MaintainTypeMapper maintainTypeMapper;
	
	@Autowired
	MaintainTemplateMapper maintainTemplateMapper;
	
	@Autowired
	MaintainPlanMapper maintainPlanMapper;
	
	@Autowired
	ProcedureService procedureService;
	
	@Autowired
	EquipmentMapper equipmentMapper;
	
	/**
	 * 保养登记
	 */
	@Override
	public void insertMaintainOrder(MaintainOrder maintainOrder) throws ValidationException {
		maintainOrder.setModifyUserid(session.getAttribute(LoginUser.SESSION_USERID).toString());
		maintainOrder.setModifiyTime(new Date());
		if (StringUtils.isBlank(maintainOrder.getMaintainGuid())) {
			maintainOrder.setMaintainGuid(IdentifieUtil.getGuId());
			maintainOrder.setCreateUserid(session.getAttribute(LoginUser.SESSION_USERID).toString());
			maintainOrder.setCreateTime(new Date());
			AssetsRecord assetsRecord = find(AssetsRecord.class, maintainOrder.getAssetsRecordGuid());
			if (assetsRecord==null) {
				throw new ValidationException("当前资产不存在");
			}else{
				maintainOrder.setuOrg(session.getAttribute(LoginUser.SESSION_USER_ORGID).toString());
				maintainOrder.setUseDeptGuid(assetsRecord.getUseDeptCode());
				maintainOrder.setEquipmentCode(assetsRecord.getEquipmentCode());
				maintainOrder.setEquipmentFstate(assetsRecord.getUseFstate().equals(CustomConst.UseFstate.IN_NORMAL_USE) ? 
						CustomConst.EquipmentFstate.USEFUL : CustomConst.EquipmentFstate.NO_USEFUL);
			}
			insertInfo(maintainOrder);
			if (maintainOrder.getMaintainOrderDetailList()!=null) {
				for (MaintainOrderDetail detail : maintainOrder.getMaintainOrderDetailList()) {
					detail.setMaintainOrderDetailGuid(IdentifieUtil.getGuId());
					detail.setMaintainGuid(maintainOrder.getMaintainGuid());
					insertInfo(detail);
				}
			}
		}else {
			updateInfo(maintainOrder);
		}
	}
	
	/**
	 * 保养列表
	 */
	public List<Map<String, Object>> selectMaintainOrderList(Pager pager){
		return maintainOrderMapper.selectMaintainOrderList(pager);
	}

	/**
	 * 保养详情
	 */
	@Override
	public Map<String, Object> selectMaintainOrderDetail(Pager pager) {
		return maintainOrderMapper.selectMaintainOrderDetail(pager);
	}

	/**
	 * 查询保养工单的保养项目列表（保养明细）
	 */
	public List<Map<String, Object>> selectMaintainDetailList(Pager pager){
		return maintainOrderMapper.selectMaintainDetailList(pager);
	}
	
	/**
	 * 添加保养明细
	 */
	public void insertMaintainOrderDetail(String maintainGuid , List<String> maintainTypes) throws Exception{
		MaintainOrder maintainOrder = find(MaintainOrder.class, maintainGuid);
		if (maintainOrder == null) {
			throw new ValidationException("当前保养工单不存在");
		}
		for (String str : maintainTypes) {
			MaintainOrderDetail maintainOrderDetail = new MaintainOrderDetail();
			maintainOrderDetail.setMaintainOrderDetailGuid(IdentifieUtil.getGuId());
			maintainOrderDetail.setMaintainGuid(maintainGuid);
			maintainOrderDetail.setMaintainTypeId(str);
			insertInfo(maintainOrderDetail);
		}
	}
	
	public List<Map<String, Object>> selectTemplateAndTypeList(Pager pager) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = maintainTemplateMapper.selectMaintainTemplate(pager);
		for (Map<String, Object> map : list) {
			Pager<Map<String, Object>> pager1 = new Pager(false);
			pager1.addQueryParam("maintainTemplateId", map.get("maintainTemplateId"));
			Map<String,Object> m = new HashMap<String, Object>();
			m.put("id", map.get("maintainTemplateId"));
			m.put("name", map.get("maintainTemplateName"));
			m.put("levelr", 0);
			m.put("subList", maintainTemplateMapper.selectMaintainTemplateDetail(pager1));
			resultList.add(m);
		}
		return resultList;
	}
	
	/**
	 * 查询保养项目
	 */
	@Override
	public List<Map<String, Object>> searchMaintainType(Pager pager) {
		return maintainTypeMapper.searchMaintainType(pager);//查询用户对应模块
	}

	/**
	 * 查询保养模板
	 */
	@Override
	public List<Map<String, Object>> selectMaintainTemplate(Pager pager) {
		return maintainTemplateMapper.selectMaintainTemplate(pager);
	}

	/**
	 * 查询保养模板里的保养项目
	 */
	@Override
	public List<Map<String, Object>> selectMaintainTemplateDetail(Pager pager) {
		return maintainTemplateMapper.selectMaintainTemplateDetail(pager);
	}

	/**
	 * 查询某个设备的保养模板
	 */
	@Override
	public List<Map<String, Object>> selectMaintainTemplateEquipment(Pager pager) {
		List<Map<String, Object>> list = maintainTemplateMapper.selectMaintainTemplateEquipment(pager);
		for (Map<String, Object> map : list) {
			map.put("levelr", 0);
			Pager<Map<String, Object>> pager2 = new Pager(false);
			pager.addQueryParam("maintainTemplateId", map.get("maintainTemplateId"));
			List<Map<String, Object>> detailList = maintainTemplateMapper.selectMaintainTemplateDetail(pager2);
			map.put("details", detailList);
		}
		return list;
	}

	/**
	 * 添加保养计划
	 */
	@Override
	public void insertMaintainPlan(MaintainPlanDto dto) throws Exception {
		MaintainPlan maintainPlan = dto.getMaintainPlan();
		String maintainPlanId = IdentifieUtil.getGuId();
		//添加保养项目
		for (String str : dto.getMaintainTypes()) {
			EqCheckbox eqCheckbox = new EqCheckbox();
			eqCheckbox.setCheckboxDetailGuid(IdentifieUtil.getGuId());
			eqCheckbox.setOrderDetailGuid(maintainPlanId);
			eqCheckbox.setTfCode(str);
			eqCheckbox.setCheckboxType("TD_MAINTAIN_TYPE.MAINTAIN_TYPE_ID");
			insertInfo(eqCheckbox);
		}
		
		//添加保养资产
		for (String str : dto.getAssetsRecordGuidList()) {
			maintainPlan.setMaintainPlanId(maintainPlanId);
//			maintainPlan.setMaintainPlanNo(procedureService.callSpGetBill(
//					Long.valueOf(session.getAttribute(LoginUser.SESSION_USER_ORGID).toString()),"保养计划", "PM", 6));
			maintainPlan.setOrgId(Long.valueOf(session.getAttribute(LoginUser.SESSION_USER_ORGID).toString()));
			maintainPlan.setPlanTime(new Date());
			maintainPlan.setPlanUserid(session.getAttribute(LoginUser.SESSION_USERID).toString());
			maintainPlan.setPlanUsername(session.getAttribute(LoginUser.SESSION_USERNAME).toString());
			AssetsRecord assetsRecord = find(AssetsRecord.class, str);
			if (assetsRecord==null) {
				throw new ValidationException("当前资产不存在");
			}
			maintainPlan.setAssetsRecordGuid(str);
			maintainPlan.setUseDeptGuid(assetsRecord.getUseDeptCode());
			maintainPlan.setEquipmentCode(assetsRecord.getEquipmentCode());
			insertInfo(maintainPlan);
		}
	}

	/**
	 * 修改保养计划
	 */
	@Override
	public void updateMaintainPlan(MaintainPlan maintainPlan,List<String> maintainTypes) throws Exception {
		EqCheckbox checkbox = new EqCheckbox();
		checkbox.setOrderDetailGuid(maintainPlan.getMaintainPlanId());
		List<EqCheckbox> list = searchList(checkbox);
		for (EqCheckbox eqCheckbox2 : list) {
			deleteInfo(eqCheckbox2);
		}
		//添加保养项目
		for (String str : maintainTypes) {
			EqCheckbox eqCheckbox = new EqCheckbox();
			eqCheckbox.setCheckboxDetailGuid(IdentifieUtil.getGuId());
			eqCheckbox.setOrderDetailGuid(maintainPlan.getMaintainPlanId());
			eqCheckbox.setTfCode(str);
			eqCheckbox.setCheckboxType("TD_MAINTAIN_TYPE.MAINTAIN_TYPE_ID");
			insertInfo(eqCheckbox);
		}
		updateInfo(maintainPlan);
	}

	/**
	 * 查询保养计划列表
	 */
	@Override
	public List<Map<String, Object>> selectMaintainPlanList(Pager pager) {
		return maintainPlanMapper.selectMaintainPlanList(pager);
	}

	/**
	 * 查询保养计划详情
	 */
	@Override
	public Map<String, Object> selectMaintainPlanDetail(Pager pager) {
		Map<String, Object> map = maintainPlanMapper.selectMaintainPlanDetail(pager);
		map.put("typeList", maintainPlanMapper.selectPlanMaintainType(map.get("maintainPlanId").toString()));
		return map;
	}

	/**
	 * 给保养模板添加保养项目
	 */
	@Override
	public void insertMaintainTemplateDetail(String maintainTemplateId,
			List<String> maintainTypes) throws Exception{
		MaintainTemplate maintainTemplate = find(MaintainTemplate.class, maintainTemplateId);
		if (maintainTemplate == null) {
			throw new ValidationException("当前保养模板不存在");
		}
		for (String str : maintainTypes) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("maintainTemplateId", maintainTemplateId);
			map.put("maintainTypeId", str);
			Integer num = maintainTemplateMapper.selectTempDetailIsRepetition(map);
			if (num!=0) {
				throw new ValidationException("同一个模板中的项目不允许重复");
			}
			MaintainTemplateDetail maintainTemplateDetail = new MaintainTemplateDetail();
			maintainTemplateDetail.setTemplateDetailGuid(IdentifieUtil.getGuId());
			maintainTemplateDetail.setMaintainTypeId(str);
			maintainTemplateDetail.setTemplateId(maintainTemplateId);
			insertInfo(maintainTemplateDetail);
		}
	}

	/**
	 * 查询模板下面的设备
	 */
	public List<Map<String, Object>> selectEquipmentInTemplate(Pager pager) {
		return maintainTemplateMapper.selectEquipmentInTemplate(pager);
	}

	@Override
	public void insertMaintainTemplate(MaintainType maintainType,
			MaintainTemplateDetail maintainTemplateDetail) {
		insertInfo(maintainType);
		insertInfo(maintainTemplateDetail);
	}

	/**
	 * 添加/删除模板的设备
	 */
	@Override
	public void insertTemplataEquipment(String maintainTemplateId, String fstate , List<String> equipmentList) throws Exception {
		if (fstate.equals("00")) {
			for (String str : equipmentList) {
				Equipment equipment = find(Equipment.class, str);
				if (equipment==null) {
					throw new ValidationException("当前设备不存在");
				}
				equipment.setMaintainTemplateId(maintainTemplateId);
				updateInfo(equipment);
			}
		}
		else {
			for (String equipmentCode : equipmentList) {
				Equipment equipment = find(Equipment.class, equipmentCode);
				if (equipment==null) {
					throw new ValidationException("当前设备不存在");
				}
				equipmentMapper.updateEquipmentMaintainTemplate(equipmentCode);
			}
		}
		
	}
	
}

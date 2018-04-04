package com.phxl.ysy.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.IBaseService;
import com.phxl.ysy.entity.MaintainOrder;
import com.phxl.ysy.entity.MaintainPlan;
import com.phxl.ysy.entity.MaintainTemplateDetail;
import com.phxl.ysy.entity.MaintainType;
import com.phxl.ysy.web.dto.MaintainPlanDto;

public interface MaintainOrderService extends IBaseService{

	public void insertMaintainOrder(MaintainOrder maintainOrder) throws ValidationException ;
	
	public List<Map<String, Object>> selectMaintainOrderList(Pager pager);
	
	Map<String, Object> selectMaintainOrderDetail(Pager pager);
	
	List<Map<String, Object>> selectMaintainDetailList(Pager pager);
	
    public List<Map<String, Object>> searchMaintainType(Pager pager);

	List<Map<String, Object>> selectMaintainTemplate(Pager pager);
	
	void insertMaintainOrderDetail(String maintainGuid , List<String> maintainTypes) throws Exception;
	
	List<Map<String, Object>> selectTemplateAndTypeList(Pager pager);

	List<Map<String, Object>> selectMaintainTemplateDetail(Pager pager);
	
	List<Map<String, Object>> selectMaintainTemplateEquipment(Pager pager);
	
	void insertMaintainPlan(MaintainPlanDto dto)throws Exception;
	
	void updateMaintainPlan(MaintainPlan maintainPlan ,List<String> maintainTypes) throws Exception;
	
	List<Map<String, Object>> selectMaintainPlanList(Pager pager);

	Map<String, Object> selectMaintainPlanDetail(Pager pager);
	
	void insertMaintainTemplateDetail(String maintainTemplateId , List<String> maintainTypes) throws Exception;

	List<Map<String, Object>> selectEquipmentInTemplate(Pager pager);
	
	void insertMaintainTemplate(MaintainType maintainType , MaintainTemplateDetail maintainTemplateDetail);
	
	void insertTemplataEquipment(String maintainTemplateId , String fstate , List<String> equipmentList) throws Exception;
}

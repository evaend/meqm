package com.phxl.ysy.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.core.base.util.JSONUtils;
import com.phxl.core.base.util.LocalCollectionUtils;
import com.phxl.core.base.util.LocalCollectionUtils.Processer;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.dao.AssetsRecordMapper;
import com.phxl.ysy.entity.DeptInfo;
import com.phxl.ysy.entity.Equipment;
import com.phxl.ysy.entity.Register;
import com.phxl.ysy.service.EquipmentAdditionalService;
import com.phxl.ysy.service.ProcedureService;
import com.phxl.ysy.web.dto.EquipmentDto;

@Service
public class EquipmentAdditionalServiceImpl  extends BaseService implements EquipmentAdditionalService {
	@Autowired
	AssetsRecordMapper assetsRecordMapper;
	@Autowired
	ProcedureService procedureService;
	@Autowired
    private JdbcTemplate jdbcTemplate1;
	@Autowired
	private DataSource dataSource1;

	@Override
	public void importEquipments(List<EquipmentDto> entityList, String userId, String orgId) throws Exception {
		List<EquipmentDto> eList = new ArrayList<EquipmentDto>();
		// 从字典里的值转成码：资产分类、使用科室、管理科室、折旧算法、维修标志
		for(EquipmentDto equipmentDto:entityList){
			equipmentDto.setCreateUserid(userId);
			equipmentDto.setOrgId(Long.valueOf(orgId));
			if(equipmentDto.getProductType() != null){
				String productType = CustomConst.ProductTypeMap.get(equipmentDto.getProductType());//获取资产分类的转化值
				equipmentDto.setProductType(productType);
			}
			if(equipmentDto.getDepreciationType() != null){
				String depreciationType = CustomConst.DepreciationTypeMap.get(equipmentDto.getDepreciationType());//获取折旧算法的转化值
				equipmentDto.setDepreciationType(depreciationType);
			}
			if(equipmentDto.getRepairFlag() != null){
				String repairFlag = CustomConst.RepairFlagMap.get(equipmentDto.getRepairFlag());//获取维修标志的转化值
				equipmentDto.setRepairFlag(repairFlag);
			}
//			if(equipmentDto.getUseDeptCode() != null){
//				DeptInfo deptnew = new DeptInfo();
//				deptnew.setDeptNam(equipmentDto.getUseDeptCode());
//				DeptInfo deptInfo = this.searchEntity(deptnew);
//				equipmentDto.setUseDeptCode(deptInfo.getDeptCode());
//			}
//			if(equipmentDto.getbDeptCode() != null){
//				DeptInfo deptnew = new DeptInfo();
//				deptnew.setDeptNam(equipmentDto.getbDeptCode());
//				DeptInfo deptInfo = this.searchEntity(deptnew);
//				equipmentDto.setbDeptCode(deptInfo.getDeptCode());
//			}
			//生成资产编号
//			String zcCode = procedureService.callSpGetBill(Long.valueOf(orgId),"资产编码", "AS", 5);// 调用生成资产编码的存储过程
			//生成二维码
			String qrCode = procedureService.callSpGetQrBill(orgId, "AS", 5);// 调用生成二维码的存储过程
			equipmentDto.setAssetsRecord(qrCode);
			equipmentDto.setQrCode(qrCode);
			eList.add(equipmentDto);
		}
		//分页进行操作，资产表
		LocalCollectionUtils.paginationProcess(eList, 300, new Processer<List<EquipmentDto>>() {
			@Override
			public Object process(List<EquipmentDto> list) throws Exception {
				//导入excel列表（合并导入）				
				return assetsRecordMapper.importAssets(list);
			}
		});
		//分页插入设备表
		LocalCollectionUtils.paginationProcess(eList, 300, new Processer<List<EquipmentDto>>(){
			@Override
			public Object process(List<EquipmentDto> list) throws Exception {
				return assetsRecordMapper.importEquipments(list);
			}
		});
	}

	@Override
	public List<Map<String, Object>> searchQrCodesByEquipmentId(Pager<Map<String, Object>> pager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bindingCertByEGuid(String assetsRecord, String newCertGuid, String userId) {
		// TODO Auto-generated method stub
		Equipment equipment = new Equipment();
		equipment.setEquipmentCode(assetsRecord);
		Equipment sEquipment = this.searchEntity(equipment);
		sEquipment.setCertGuid(newCertGuid);
		this.updateInfo(sEquipment);
	}

	@Override
	public List<Map<String, Object>> searchCertList(Pager<Register> pager) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
		// TODO Auto-generated method stub
	    StringBuffer hql = new StringBuffer("SELECT r.CERT_GUID \"certGuid\", r.REGISTER_NO \"registerNo\", r.MATERIAL_NAME \"materialName\", r.FLAG \"flag\", r.FQUN \"fqun\", r.TYPE \"type\", r.INSTRUMENT_CODE \"instrucmentCode\", r.FIRST_TIME \"firstTime\", r.LAST_TIME \"lastTime\","
	    		+ " r.PRODUCE_NAME \"produceName\", r.ENTERPRISE_REG_ADDR \"enterpriseRegAddr\", r.PRODUCE_ADDR \"produceAddr\", r.AGENT_NAME \"agentName\", r.AGENT_ADDR \"agentAddr\", r.PRODUCT_STRUCTURE \"productStructure\","
	    		+ " r.PRODUCT_SCOPE \"productScope\", r.PRODUCT_STANDARD \"productStandard\", r.TF_BRAND \"tfBrand\", r.R_CERT_GUID \"rCertGuid\", r.TF_ACCESSORY_FILE \"tfAccessoryFile\", r.TF_ACCESSORY \"tfAccessory\","
	    		+ " r.CREATE_USERID \"createUserid\", r.CREATE_TIME \"createTime\", r.MODIFY_USERID \"modifyUserid\", r.MODIFY_TIME \"modifyTime\", r.TABOO \"taboo\", r.AFTER_SERVICE \"afterService\", r.TF_REMARK \"tfRemark\", r.IS_IMPORT \"isImport\","
	    		+ " code2.TF_CLO_NAME \"instrumentName\", brand2.TF_CLO_NAME \"tfBrandName\""
	    				+ " FROM  TD_REGISTER r"
	    				+ " left join TD_STATIC_INFO code on code.TF_CLO='INSTRUMENT_CODE'"
	    				+ " left join TD_STATIC_DATA code2 on code.STATIC_ID=code2.STATIC_ID and code2.TF_CLO_CODE=r.INSTRUMENT_CODE"
	    				+ " left join TD_STATIC_INFO brand on brand.TF_CLO='TF_BRAND'"
	    				+ " left join TD_STATIC_DATA brand2 on brand.STATIC_ID=brand2.STATIC_ID and brand2.TF_CLO_CODE=r.TF_BRAND"
	    				+ " WHERE 1=1");
	    if(pager.getConditiions().containsKey("searchName") && pager.getConditiions().get("searchName")!=null){
	    	String searchName =  (String)pager.getConditiions().get("searchName");
	    	hql = hql.append("and ( "
	    			+ " regexp_like(r.REGISTER_NO, "+searchName+")"
	    			+ " or regexp_like(r.MATERIAL_NAME,"+searchName+")"
	    			+ " or regexp_like(r.PRODUCE_NAME, "+searchName+")"
	    			+ " )");
	    }
	    if(pager.getConditiions().containsKey("specCertGuid") && pager.getConditiions().get("specCertGuid")!=null){
	    	String specCertGuid =  (String)pager.getConditiions().get("specCertGuid");
	    	hql = hql.append(" and r.CERT_GUID = "+specCertGuid+")");
	    }
	    if(pager.getConditiions().containsKey("excludeCertGuid") && pager.getConditiions().get("excludeCertGuid")!=null){
	    	String excludeCertGuid =  (String)pager.getConditiions().get("excludeCertGuid");
	    	hql = hql.append(" and r.CERT_GUID != "+excludeCertGuid+")");
	    }
		List<Map<String, Object>> certList = new ArrayList<Map<String,Object>>();
		certList = jdbcTemplate1.queryForList(hql.toString());
//		ObjectMapper objectMapper = new ObjectMapper();
//	    JavaType javaType = JSONUtils.getCollectionType(ArrayList.class, Register.class);
//        List<Register> certListEn = objectMapper.readValue(JSONUtils.toJson(certList), javaType);

		return certList;
	}

}

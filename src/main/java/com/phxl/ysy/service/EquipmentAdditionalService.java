package com.phxl.ysy.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.service.IBaseService;
import com.phxl.ysy.entity.Register;
import com.phxl.ysy.web.dto.EquipmentDto;

public interface EquipmentAdditionalService extends IBaseService {

	/**
	 * 导入机构资产信息
	 * @param entityList
	 * @param userId
	 * @param orgId
	 * @throws Exception 
	 */
	void importEquipments(List<EquipmentDto> entityList, String userId, String orgId) throws Exception;

	/**
	 * 根据资产id查询资产的信息，主要为了打码
	 */
	List<Map<String, Object>> searchQrCodesByEquipmentId(Pager<Map<String, Object>> pager);

	/**
	 * 变更资产的证件
	 */
	void bindingCertByEGuid(String assetsRecord, String newCertGuid, String userId);

	/**
	 * 查询证件信息，来源于医商云3.0的数据库
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	List<Map<String, Object>> searchCertList(Pager<Register> pager) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException;

}

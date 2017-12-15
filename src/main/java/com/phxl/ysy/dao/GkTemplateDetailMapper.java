package com.phxl.ysy.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.GkTemplate;
import com.phxl.ysy.entity.GkTemplateDetail;

public interface GkTemplateDetailMapper {

	List<Map<String, Object>> searchGkTemplateDetails(Pager<Map<String, Object>> pager);

	void deleteGkDetailsByTemplateId(String gkTemplateGuid);

	void insertGkTemplateDetails(@Param("gkTemplateGuid")String gkTemplateGuid, @Param("tenderDetailGuids")String[] tenderDetailGuids);

	void deleteGkTemplateDetials(@Param("gkTemplateDetailGuids")String[] gkTemplateDetailGuids);

	void updateGkTemplateDetails(@Param("gkTemplateDetails")List<GkTemplateDetail> gkTemplateDetails);

	void deleteGkDetailsByTemplateId(@Param("gkTemplate")GkTemplate gkTemplate,  @Param("notInGuidList")List<String> notInGuidList);

	void insertBatchGkTemplateDetails(@Param("gkTemplateGuid")String gkTemplateGuid, @Param("newGkTemplateDetails")List<GkTemplateDetail> newGkTemplateDetails);

	Map<String, BigDecimal> subtotalOfAttributeIds(@Param("gkAttributes")List<Map<String, String>> gkAttributes,
			@Param("gkTemplateDetails")List<GkTemplateDetail> gkTemplateDetails);

	void updateDetailsToFormal(@Param("gkTemplateGuid")String gkTemplateGuid);
}
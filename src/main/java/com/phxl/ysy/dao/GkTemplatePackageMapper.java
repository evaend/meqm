package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.GkTemplatePackage;
import com.phxl.ysy.web.dto.PackageToolDto;

public interface GkTemplatePackageMapper {

	List<String> findPackageIdsByTemplateId(@Param("gkTemplateGuid")String gkTemplateGuid, @Param("gkTemplateGuids")String[] gkTemplateGuids);

	List<Map<String, Object>> findPackageListByTemplateId(@Param("gkTemplateGuid")String gkTemplateGuid, @Param("gkTemplateGuids")String[] gkTemplateGuids,
			@Param("gkAttributes")List<Map<String, String>> gkAttributes, @Param("packageIds")List<String> packageIds);

	void insertPackagesBatch(@Param("packageAttrList")List<GkTemplatePackage> packageAttrList);
	
	Map<String, Object> subtotalPackageByBillId(@Param("gkTemplateGuid")String gkTemplateGuid, @Param("gkTemplateGuids")String[] gkTemplateGuids);

	void deleteGkPacsByTemplateId(@Param("gkTemplateGuid")String gkTemplateGuid);

	void updateGkTemplatePacs(@Param("packageAttrList")List<GkTemplatePackage> packageAttrList);

	List<Map<String, Integer>> checkMaterialAmountsWithAttributeId(@Param("gkTemplateGuid")String gkTemplateGuid);

	void updatePacsToFormal(@Param("gkTemplateGuid")String gkTemplateGuid);

	void deleteGkTemplatePacs(@Param("orgGkTemplatePackages")List<GkTemplatePackage> orgGkTemplatePackages);

	List<Map<String, String>> queryPacColumns(Pager<Map<String, Object>> conditions);

}
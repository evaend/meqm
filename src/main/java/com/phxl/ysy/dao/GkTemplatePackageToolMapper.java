package com.phxl.ysy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.GkTemplatePackageTool;
import com.phxl.ysy.web.dto.PackageToolDto;

public interface GkTemplatePackageToolMapper {

	void addToolsBatch(@Param("rOrgId")Long rOrgId, @Param("gkTemplateGuid")String gkTemplateGuid, @Param("toolList")List<PackageToolDto> toolList);

	List<GkTemplatePackageTool> findAllTools(Pager<GkTemplatePackageTool> pager);

	void deleteToolsByPackage(@Param("gkTemplateGuid")String gkTemplateGuid, @Param("packageId")Integer packageId,  @Param("toolList")String[] toolList);

	Integer countToolsByPackage(@Param("gkTemplateGuid")String gkTemplateGuid, @Param("packageId")Integer packageId);

	void deleteToolsByTemplateId(@Param("gkTemplateGuid")String gkTemplateGuid);
}
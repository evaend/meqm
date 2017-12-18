package com.phxl.ysy.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.service.AssetsRecordService;

@Controller
@RequestMapping("/assetsRecordController")
public class AssetsRecordController {
	@Autowired
	AssetsRecordService assetsRecordService;
	
	@RequestMapping("/selectAssetsRecordCount")
	@ResponseBody
	public Integer selectAssetsRecordCount(
			HttpServletRequest request,HttpServletResponse response) {
		Integer count = assetsRecordService.selectAssetsRecordCount();
		return count;
	}
	
	@RequestMapping("/selectAssetsList")
	@ResponseBody
	public List<Map<String, Object>> selectAssetsList(
			@RequestParam(value="assetsRecord",required = false) String assetsRecord,
			@RequestParam(value="equipmetStandarName",required = false) String equipmetStandarName,
			@RequestParam(value="useFstate",required = false) String useFstate,
			@RequestParam(value="productType",required = false) String productType,
			@RequestParam(value="spec",required = false) String spec,
			@RequestParam(value="product",required = false) String product,
			@RequestParam(value="useDeptCode",required = false) String useDeptCode,
			@RequestParam(value="bDept",required = false) String bDept,
			@RequestParam(value="custodian",required = false) String custodian,
			@RequestParam(value="mobile",required = false) String mobile,
			@RequestParam(value="assetsRecordOne",required = false) String assetsRecordOne,
			@RequestParam(value="pagesize",required = false) Integer pagesize,
			@RequestParam(value="page",required = false) Integer page,
			HttpServletRequest request,HttpServletResponse response
			){
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(true);
		//如果没有设置当前页和每页数量，则默认第一页，每页十五条数据
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.setPageNum(page == null ? 1 : page);
		
		pager.addQueryParam("assetsRecord", assetsRecord);
		pager.addQueryParam("equipmetStandarName", equipmetStandarName);
		pager.addQueryParam("useFstate", useFstate);
		pager.addQueryParam("productType", productType);
		pager.addQueryParam("spec", spec);
		pager.addQueryParam("product", product);
		pager.addQueryParam("useDeptCode", useDeptCode);
		pager.addQueryParam("bDept", bDept);
		pager.addQueryParam("custodian", custodian);
		pager.addQueryParam("mobile", mobile);
		pager.addQueryParam("assetsRecordOne", assetsRecordOne);
		
		List<Map<String, Object>> list = assetsRecordService.selectAssetsList(pager);
		return list;
	}
}

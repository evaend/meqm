package com.phxl.ysy.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.service.OrgDeptService;

@Controller
@RequestMapping("dept")
public class DeptController {
	
	@Autowired
	OrgDeptService orgDeptService;
	
	@RequestMapping("selectUseDeptList")
	@ResponseBody
	public List<Map<String, Object>> selectUseDeptList(
			@RequestParam(value = "orgId", required = false) String orgId) {
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(false);
		pager.addQueryParam("orgId", orgId);
		return orgDeptService.selectUseDeptList(pager);
	}
}

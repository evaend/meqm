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
import com.phxl.ysy.service.StaticDataService;

@Controller
@RequestMapping("/StaticDataController")
public class StaticDataController {
	@Autowired
	StaticDataService staticDataService;
	
	@ResponseBody
	@RequestMapping("/selectStaticDataList")
	public Pager<Map<String, Object>> selectStaticDataList(
			@RequestParam(value="code",required = false) String code,
			HttpServletRequest request, HttpServletResponse response) {
		Pager<Map<String, Object>> pager = new Pager<Map<String,Object>>(false);
		pager.addQueryParam("code", code);
		List<Map<String, Object>> list = staticDataService.selectStaticDataList(pager);
		pager.setRows(list);
		return pager;
	}
}

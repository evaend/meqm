package com.phxl.ysy.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}

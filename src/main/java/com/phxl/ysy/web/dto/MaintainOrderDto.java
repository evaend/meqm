package com.phxl.ysy.web.dto;

import java.util.Date;
import java.util.List;

public class MaintainOrderDto{
	private String maintainGuid;
	
	private String assetsRecordGuid;
	
	private String maintainType;
	
	private String engineerUserid;
	
	private String engineerName;
	
	private String clinicalRisk;

	private Date maintainDate;

	private Date enMaintainDate;

	private Date nextMaintainDate;
	
	private String remark;
	
	private List<String> tfAccessory;
}

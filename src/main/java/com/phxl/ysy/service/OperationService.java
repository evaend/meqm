package com.phxl.ysy.service;

import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.UserOperation;

/**
 * 操作日志服务层接口
 * 2017年4月25日 下午12:08:02
 * @author 陶悠
 *
 */
public interface OperationService {
	
	public void operationArgAndReturn(JoinPoint point, Object returnObj);
	
	public void operationThrowing(JoinPoint point,Exception exc);
	
	public void userOperation(UserOperation userOperation);
	
	public List<Map<String,Object>> searchOpRecords(Pager pager);
}

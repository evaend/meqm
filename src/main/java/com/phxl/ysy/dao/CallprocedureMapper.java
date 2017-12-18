package com.phxl.ysy.dao;

import java.util.Map;

public interface CallprocedureMapper {
	
	/**
	 * 获取单号
	 */
	void SP_GET_BILL_NOSTORAGE(Map<String, Object> params);
	
	/**
	 * 获取二维码单号
	 */
	void SP_GET_QRBILL(Map<String, Object> params);
}

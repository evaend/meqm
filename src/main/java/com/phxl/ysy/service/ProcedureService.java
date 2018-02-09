package com.phxl.ysy.service;

import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.IBaseService;

public interface ProcedureService extends IBaseService {
	/**
	 * 生成单号（适用场景:直接以需方机构生成单号）
	 */
	public abstract String callSpGetBill(Long billOrgId, String billName, String billPrefix, Integer asLen) throws ValidationException;

	/**
	 * 生成二维码单号
	 */
	public abstract String callSpGetQrBill(Long billOrgId, String billPrefix) throws ValidationException;
}
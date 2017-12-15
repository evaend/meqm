package com.phxl.ysy.service;

import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.IBaseService;

public interface ProcedureService extends IBaseService {

	/**
	 * 获取单号
	 * @author	黄文君
	 * @date	2017年6月21日 上午11:37:37
	 * @param	sessionOrgId		需方机构id（当前登录机构）
	 * @param	rOrgId				招标需方机构id
	 * @param	fOrgId				招标供方机构id
	 * @param	billName			单据名称（单据类型）
	 * @param	billPrefix			单据前缀（缩写）
	 * @throws	ValidationException
	 * @return	void
	 */
	public abstract String callSpGetBill(Long sessionOrgId, Long rOrgId, Long fOrgId, String billName, String billPrefix) throws ValidationException;

	/**
	 * 生成单号（适用场景:直接以需方机构生成单号）
	 * @author	黄文君
	 * @date	2017年6月21日 上午11:11:42
	 * @param	billOrgId			生成单号的机构id
	 * @param	billName			单据名称（单据类型）
	 * @param	billPrefix			单据前缀（缩写）
	 * @param	asLen				流水号位数上限
	 * @throws	ValidationException
	 * @return	String				生成的单号
	 */
	public abstract String callSpGetBill(Long billOrgId, String billName, String billPrefix, Integer asLen) throws ValidationException;

	/**
	 * 生成二维码单号（适用场景:库房一物一码的单号）
	 * @param billOrgId
	 * @param billPrefix
	 * @param asLen
	 * @return
	 * @throws ValidationException
	 */
	public abstract String callSpGetQrBill(Long billOrgId, String billPrefix, Integer asLen) throws ValidationException;
	
}
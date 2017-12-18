package com.phxl.ysy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.ysy.dao.CallprocedureMapper;
import com.phxl.ysy.service.ProcedureService;

@Service
public class ProcedureServiceImpl extends BaseService implements ProcedureService {
	
	@Autowired
	CallprocedureMapper callprocedureMapper;

	/**
	 * 生成单号（适用场景:直接以需方机构生成单号）
	 */
	@Override
	public String callSpGetBill(Long billOrgId, String billName, String billPrefix, Integer asLen) throws ValidationException {
		Assert.notNull(billOrgId, "生成新单号: billOrgId，不能为空");
		Assert.notNull(asLen, "生成新单号: asLen，不能为空");
		//Assert.notNull(billStorageId, "生成新单号: billStorageId，不能为空");
		Assert.notNull(billName, "生成新单号: billName，不能为空");
		Assert.notNull(billPrefix, "生成新单号: billPrefix，不能为空");
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("billOrgId", billOrgId);
		//params.put("billStorageId", billStorageId);
		params.put("billName", billName);
		params.put("billPrefix", billPrefix);
		params.put("asLen", asLen);
		
		//获取指定单据类型的最新单号
		callprocedureMapper.SP_GET_BILL_NOSTORAGE(params);
		List<Map> result=(List<Map>)params.get("cursor");
		if(CollectionUtils.isNotEmpty(result)){
			Map firstRow = result.get(0);
			String flag = (String)firstRow.get("flag");
			String billNo = (String)firstRow.get("billNo");
			String error = (String)firstRow.get("error");
			logger.debug("call sp_get_bill_nostorage =>返回: flag={}, billNo={}, error={}", new Object[]{flag, billNo, error});
			if ("1".equals(flag)) {//处理成功
				LocalAssert.notBlank(billNo, "返回的单号不能为空!!");
				billNo = StringUtils.trimToEmpty(billPrefix) + billNo;
				logger.debug("生成新单号 => {}", billNo);
				return billNo;
			}else{//处理失败
				throw new ValidationException("生成单号失败! " + error);//没有找到相关单号记录
			}
		}else{
			throw new ValidationException("没有找到相关单号记录");
		}
	}
	
	/**
	 * 生成二维码单号（适用场景:库房一物一码的单号）
	 * @param	billOrgId			生成单号的机构id
	 * @param	billPrefix			单据前缀（缩写）
	 * @return	String				生成的单号
	 */
	@Override
	public String callSpGetQrBill(String billOrgId, String billPrefix, Integer asLen) throws ValidationException {
		Assert.notNull(billOrgId, "生成新单号: 机构id，不能为空");
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("billOrgId", billOrgId);
		params.put("billPrefix", billPrefix);
		params.put("asLen", asLen);
		
		//获取指定单据类型的最新单号
		callprocedureMapper.SP_GET_QRBILL(params);
		List<Map> result=(List<Map>)params.get("cursor");
		if(CollectionUtils.isNotEmpty(result)){
			Map firstRow = result.get(0);
			String flag = (String)firstRow.get("flag");
			String billNo = (String)firstRow.get("billNo");
			String error = (String)firstRow.get("error");
			logger.debug("call sp_get_qrbill =>返回: flag={}, billNo={}, error={}", new Object[]{flag, billNo, error});
			if ("1".equals(flag)) {//处理成功
				logger.debug("生成新单号 => {}", billNo);
				LocalAssert.notBlank(billNo, "返回的单号不能为空!!");
				return billNo;
			}else{//处理失败
				throw new ValidationException("生成单号失败! " + error);//没有找到相关单号记录
			}
		}else{
			throw new ValidationException("没有找到相关单号记录");
		}
	}
	
}

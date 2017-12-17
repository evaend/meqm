package com.phxl.ysy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mysql.fabric.xmlrpc.base.Array;
import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.ysy.dao.RrpairOrderMapper;
import com.phxl.ysy.entity.RrpairOrder;
import com.phxl.ysy.service.RrpairOrderService;

@Service
public class RrpairOrderServiceImpl extends BaseService implements RrpairOrderService{
	@Autowired
	RrpairOrderMapper rrpairOrderMapper;

	public List<Map<String, Object>> selectRrpairList(Pager pager) {
		List<Map<String, Object>> list = rrpairOrderMapper.selectRrpairList(pager);
		for (Map<String, Object> map : list) {
			RrpairOrder rrpairOrder = find(RrpairOrder.class, map.get("rrpairOrder").toString());
			if(map.get("rrpairType")==null){
				map.put("MaintainUserName", "");
			}else if(map.get("rrpairType").toString().equals("00")){
				map.put("MaintainUserName", rrpairOrder.getInRrpairUsername());
			}else if(map.get("rrpairType").toString().equals("01")){
				map.put("MaintainUserName", rrpairOrder.getOutRrpairUsername());
			}else{
				map.put("MaintainUserName", "");
			}
		}
		return list;
	}

	public List<Map<String, Object>> selectRrpairFstateNum() {
		List<Map<String, Object>> list = rrpairOrderMapper.selectRrpairFstateNum();
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : list) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put(map.get("orderFstate").toString(),map.get("num").toString());
			returnList.add(m);
		}
		returnList.add(rrpairOrderMapper.selectRrpairFstateCount());
		return returnList;
	}

	public String selectRrpairContent(String rrpairOrder,Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rrpairOrder", rrpairOrder);
		if (type==0) {
			map.put("content", "tf_comment");
		}else{
			map.put("content", "tf_remark");
		}
		return rrpairOrderMapper.selectRrpairContent(map);
	}

	public void updateRrpairContent(String rrpairOrder, Integer type,
			String value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rrpairOrder", rrpairOrder);
		if (type==0) {
			map.put("content", "tf_comment");
		}else{
			map.put("content", "tf_remark");
		}
		map.put("value", value);
		rrpairOrderMapper.updateRrpairContent(map);
	}

	
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
		rrpairOrderMapper.SP_GET_BILL_NOSTORAGE(params);
		//获取指定单据类型的最新单号
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
	
	
}

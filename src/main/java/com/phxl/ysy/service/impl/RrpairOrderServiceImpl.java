package com.phxl.ysy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.MidiChannel;

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
import com.phxl.ysy.dao.AssetsRecordMapper;
import com.phxl.ysy.dao.CallprocedureMapper;
import com.phxl.ysy.dao.RrpairOrderMapper;
import com.phxl.ysy.entity.RrpairOrder;
import com.phxl.ysy.service.IMessageService;
import com.phxl.ysy.service.RrpairOrderService;

@Service
public class RrpairOrderServiceImpl extends BaseService implements RrpairOrderService{
	@Autowired
	RrpairOrderMapper rrpairOrderMapper;
	
	@Autowired
	IMessageService imessageService;
	
	@Autowired
	AssetsRecordMapper assetsRecordMapper;
	
	@Autowired
	CallprocedureMapper callprocedureMapper;

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
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("orderFstate", "10");
		Map<String, Object> m1 = rrpairOrderMapper.selectRrpairFstateNum(m);
		m.put("orderFstate", "30");
		Map<String, Object> m3 = rrpairOrderMapper.selectRrpairFstateNum(m);
		m.put("orderFstate", "50");
		Map<String, Object> m5 = rrpairOrderMapper.selectRrpairFstateNum(m);
		m.put("orderFstate", "80");
		Map<String, Object> m8 = rrpairOrderMapper.selectRrpairFstateNum(m);
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		if (m1== null || m1.size()==0) {
			Map<String, Object> mm1 = new HashMap<String, Object>();
			mm1.put("code", "10");
			mm1.put("num", "0");
			returnList.add(mm1);
		}else{
			returnList.add(m1);
		}
		
		if (m3== null || m3.size()==0) {
			Map<String, Object> mm3 = new HashMap<String, Object>();
			mm3.put("code", "30");
			mm3.put("num", "0");
			returnList.add(mm3);
		}else{
			returnList.add(m3);
		}

		if (m5== null || m5.size()==0) {
			Map<String, Object> mm5 = new HashMap<String, Object>();
			mm5.put("code", "50");
			mm5.put("num", "0");
			returnList.add(mm5);
		}else{
			returnList.add(m5);
		}
		
		if (m8== null || m8.size()==0) {
			Map<String, Object> mm8 = new HashMap<String, Object>();
			mm8.put("code", "80");
			mm8.put("num", "0");
			returnList.add(mm8);
		}else{
			returnList.add(m8);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code","assetsRecordCount");
		map.put("num",assetsRecordMapper.selectAssetsRecordCount());
		returnList.add(map);
		Map<String, Object> map1 = rrpairOrderMapper.selectRrpairFstateCount();
		map1.put("code", "rrpairOrderCount");
		returnList.add(map1);
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
		callprocedureMapper.SP_GET_BILL_NOSTORAGE(params);
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
	
	public void pushMessage(String rrpairOrder){
		Pager pager = new Pager(false);
		pager.addQueryParam("rrpairOrder", rrpairOrder);
		List<Map<String, Object>> list = rrpairOrderMapper.selectRrpairList(pager);
		Map<String, Object> map = list.get(0);
		Map<String,Object> argument = new HashMap<String,Object>(); 
        argument.put("first", rrpairOrder);
        argument.put("keyword1", map.get("equipmentCode"));
        argument.put("keyword2", map.get("orderFstate"));
        argument.put("keyword3", new Date());
        RrpairOrder rrpair = find(RrpairOrder.class, rrpairOrder);
        if(map.get("rrpairType")==null){
        	argument.put("keyword4", "");
		}else if(map.get("rrpairType").toString().equals("00")){
			argument.put("keyword4", rrpair.getInRrpairUsername());
		}else if(map.get("rrpairType").toString().equals("01")){
			argument.put("keyword4", rrpair.getOutRrpairUsername());
		}else{
			argument.put("keyword4", "");
		}
        if (map.get("orderFstate")!=null) {
        	switch (map.get("orderFstate").toString()) {
    		case "10":
    			argument.put("keyword5","待维修");
    			break;
    		case "30":
    			argument.put("keyword5","维修中");
    			break;
			case "50":
				argument.put("keyword5","待验收");
				break;
			case "80":
				argument.put("keyword5","已关闭");
				break;
    		default:
				argument.put("keyword5","");
    			break;
    		}
		}
        
        
        argument.put("remark","所属科室："+map.get("useDept"));
        String message = imessageService.getMessageJsonContent(argument,
        		"A6C68D5EFF5E4D55B5D8396CB3232DE0","www.baidu.com ","1");
        imessageService.pushMessages(message);
	}
}

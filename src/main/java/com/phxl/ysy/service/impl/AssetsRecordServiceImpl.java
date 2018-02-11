package com.phxl.ysy.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.core.base.util.FTPUtils;
import com.phxl.core.base.util.IdentifieUtil;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.constant.CustomConst.AssetsRecordInfoUpdate;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.dao.AssetsExtendMapper;
import com.phxl.ysy.dao.AssetsRecordMapper;
import com.phxl.ysy.dao.CertInfoZcMapper;
import com.phxl.ysy.dao.EqOperationInfoMapper;
import com.phxl.ysy.entity.AssetsRecord;
import com.phxl.ysy.entity.CertInfoZc;
import com.phxl.ysy.entity.EqOperationInfo;
import com.phxl.ysy.entity.Equipment;
import com.phxl.ysy.service.AssetsRecordService;

@Service
public class AssetsRecordServiceImpl extends BaseService implements AssetsRecordService {
	@Autowired
	AssetsRecordMapper assetsRecordMapper;
	
	@Autowired
	CertInfoZcMapper certInfoMapper;
	
	@Autowired
	EqOperationInfoMapper eqOperationInfoMapper;
	
	@Autowired
	AssetsExtendMapper assetsExtendMapper;
	
	@Autowired
	HttpSession session;

	//查询资产总量
	public Integer selectAssetsRecordCount() {
		return assetsRecordMapper.selectAssetsRecordCount();
	}

	//查询资产档案信息列表
	public List<Map<String, Object>> selectAssetsList(Pager pager) {
		return assetsRecordMapper.selectAssetsList(pager);
	}
	
	//查询资产配件信息列表
	public List<Map<String , Object>> selectAssetsExtendList(Pager pager){
		return assetsExtendMapper.selectAssetsExtendList(pager);
	}

	//查询产品证件列表
	public List<Map<String, Object>> selectCertInfoList(Pager pager) {
		return certInfoMapper.selectCertInfoList(pager);
	}

	//查询操作记录
	public List<Map<String, Object>> selectEqOperation(Pager pager) {
		return eqOperationInfoMapper.selectEqOperation(pager);
	}

	//修改资产档案信息
	public void updateAssetsRecordInfo(String assetsRecordGuid,String value, String text) throws ValidationException {
		EqOperationInfo eqOperationInfo = new EqOperationInfo();
		eqOperationInfo.setTsOpId(IdentifieUtil.getGuId());
//		eqOperationInfo.setOpUserId(session.getAttribute(LoginUser.SESSION_USERID).toString());
		eqOperationInfo.setOpTime(new Date());
		eqOperationInfo.setOpType("编辑属性");
		AssetsRecord assetsRecord = find(AssetsRecord.class, assetsRecordGuid);
		if (assetsRecord==null) {
			throw new ValidationException("当前资产档案信息不存在");
		}
		eqOperationInfo.setOpId(assetsRecord.getAssetsRecordGuid());
		eqOperationInfo.setAssetsRecord(assetsRecord.getAssetsRecord());
		eqOperationInfo.setEquipmentCode(assetsRecord.getEquipmentCode());
		if (value.equals(AssetsRecordInfoUpdate.equipmentName)) {
			if (StringUtils.isBlank(assetsRecord.getEquipmentCode())) {
				throw new ValidationException("当前资产档案未关联设备，无法修改通用名称");
			}else{
				Equipment equipment = find(Equipment.class, assetsRecord.getEquipmentCode());
				if (equipment == null) {
					throw new ValidationException("当前设备信息不存在，无法修改设备名称");
				}else{
					eqOperationInfo.setOpA(equipment.getEquipmentName());
					eqOperationInfo.setOpText(AssetsRecordInfoUpdate.equipmentName);
					eqOperationInfo.setOpB(text);
					equipment.setEquipmentName(text);
					updateInfo(equipment);
				}
				
			}
		}
		else if (value.equals(AssetsRecordInfoUpdate.deposit)) {
			eqOperationInfo.setOpA(assetsRecord.getDeposit());
			eqOperationInfo.setOpText(AssetsRecordInfoUpdate.deposit);
			eqOperationInfo.setOpB(text);
			assetsRecord.setDeposit(text);
			updateInfo(assetsRecord);
		}
		else if (value.equals(AssetsRecordInfoUpdate.maintainDay)) {
			eqOperationInfo.setOpA(assetsRecord.getMaintainDay());
			eqOperationInfo.setOpText(AssetsRecordInfoUpdate.maintainDay);
			eqOperationInfo.setOpB(text);
			assetsRecord.setMaintainDay(text);
			updateInfo(assetsRecord);
		}
		else if (value.equals(AssetsRecordInfoUpdate.maintainType)) {
			eqOperationInfo.setOpA(assetsRecord.getMaintainType());
			eqOperationInfo.setOpText(AssetsRecordInfoUpdate.maintainType);
			eqOperationInfo.setOpB(text);
			assetsRecord.setMaintainType(text);
			updateInfo(assetsRecord);
		}
		else if (value.equals(AssetsRecordInfoUpdate.meteringType)) {
			eqOperationInfo.setOpA(assetsRecord.getMeteringType());
			eqOperationInfo.setOpText(AssetsRecordInfoUpdate.meteringType);
			eqOperationInfo.setOpB(text);
			assetsRecord.setMeteringType(text);
			updateInfo(assetsRecord);
		}
		else if (value.equals(AssetsRecordInfoUpdate.rrpairType)) {
			eqOperationInfo.setOpA(assetsRecord.getRrpairType());
			eqOperationInfo.setOpText(AssetsRecordInfoUpdate.rrpairType);
			eqOperationInfo.setOpB(text);
			assetsRecord.setRrpairType(text);
			updateInfo(assetsRecord);
		}
		else if (value.equals(AssetsRecordInfoUpdate.spare)) {
			eqOperationInfo.setOpA(assetsRecord.getSpare());
			eqOperationInfo.setOpText(AssetsRecordInfoUpdate.spare);
			eqOperationInfo.setOpB(text);
			assetsRecord.setSpare(text);
			updateInfo(assetsRecord);
		}
		insertInfo(eqOperationInfo);
	}

	//查询资产档案明细
	public Map<String, Object> selectAssetsRecordDetail(Pager pager) {
		return assetsRecordMapper.selectAssetsRecordDetail(pager);
	}

	//添加资产档案附件
	@Override
	public void insertAssetsFile(String directory, String fileName,
			InputStream in, String assetsRecordGuid, String certCode) throws Exception {
		AssetsRecord assetsRecord = find(AssetsRecord.class, assetsRecordGuid);
		if (assetsRecord==null) {
			throw new ValidationException("当前资产档案不存在");
		}
		FTPUtils.upload(directory, fileName, in);
		CertInfoZc certInfo = new CertInfoZc();
		certInfo.setCertId(IdentifieUtil.getGuId());
		certInfo.setTfAccessory(directory+fileName);
		certInfo.setCertCode(certCode);
		certInfo.setTfAccessoryFile(directory+fileName);
//		certInfo.setCreateUserid(session.getAttribute(LoginUser.SESSION_USERID).toString());
		certInfo.setCreateTime(new Date());
		certInfo.setAssetsRecord(assetsRecord.getAssetsRecord());
		certInfo.setEquipmentCode(assetsRecord.getEquipmentCode());
		insertInfo(certInfo);
	}

	@Override
	public void deleteAssetsFile(String certId) throws Exception {
		CertInfoZc certInfo = find(CertInfoZc.class, certId);
		if (certInfo==null) {
			throw new ValidationException("当前资产附件不存在");
		}
		File file = new File(certInfo.getTfAccessory());
		file.delete();
		deleteInfo(certInfo);
	}

}

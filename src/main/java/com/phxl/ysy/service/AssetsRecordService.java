package com.phxl.ysy.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.IBaseService;
import com.phxl.ysy.entity.RrpairOrder;

public interface AssetsRecordService extends IBaseService {

    /**
     * 查询资产总量
     * @return
     */
	Integer selectAssetsRecordCount();

	/**
	 * 查询资产档案信息列表
	 * @param pager
	 * @return
	 */
	List<Map<String, Object>> selectAssetsList(Pager pager);

	/**
	 * 查询资产配件信息列表
	 * @param pager
	 * @return
	 */
	List<Map<String , Object>> selectAssetsExtendList(Pager pager);

    /**
     * 查询产品证件列表
     * @param pager
     * @return
     */
	List<Map<String, Object>> selectCertInfoList(Pager pager);
	
	/**
	 * 查询操作记录
	 * @param pager
	 * @return
	 */
	List<Map<String, Object>> selectEqOperation(Pager pager);
	
	/**
	 * 修改资产档案信息
	 * @param value
	 * @param text
	 */
	void updateAssetsRecordInfo(String assetsRecordGuid, String value, String text)throws ValidationException;

	/**
	 * 查询资产档案明细
	 * @param pager
	 * @return
	 */
	Map<String, Object> selectAssetsRecordDetail(Pager pager);
	
	/**
	 * 添加资产附件文件
	 */
	void insertAssetsFile(String assetsRecordGuid,String certCode,MultipartFile file)throws Exception ;
	
	/**
	 * 删除资产附件
	 * @param certId
	 * @throws Exception
	 */
	void deleteAssetsFile(String certId)throws Exception;
	
	List<Map<String, Object>> selectAssetsRecordFstate(Map<String, Object> map);
	
	/**
	 * 判断用户扫码之后进入哪个页面
	 * @param rrpairOrder
	 * @return
	 */
	String getUrl(String orderFstate , String rrpairOrderGuid , String groupName);
}

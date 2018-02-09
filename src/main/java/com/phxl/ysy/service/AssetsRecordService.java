package com.phxl.ysy.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.IBaseService;

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
	void insertAssetsFile(String directory,String fileName,InputStream in,String assetsRecordGuid,String certCode)throws Exception ;
	
	/**
	 * 删除资产附件
	 * @param certId
	 * @throws Exception
	 */
	void deleteAssetsFile(String certId)throws Exception;
}

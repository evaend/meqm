package com.phxl.ysy.dao;

import java.util.Map;

public interface CallprocedureMapper {
	
	/**
	 * 获取单号
	 * @author	黄文君
	 * @date	2017年6月21日 上午10:44:00
	 * @param	params
	 * @return	void
	 */
	void SP_GET_BILL_NOSTORAGE(Map<String, Object> params);
	
	/**
	 * 获取二维码单号
	 */
	void SP_GET_QRBILL(Map<String, Object> params);

	
	/**
     * 
     * SP_PD_COUNT:(库存盘点). <br/> 
     * 
     * @Title: SP_PD_COUNT
     * @Description: TODO
     * @param paramsOut    设定参数
     * @return void    返回类型
     * @throws
     */
    void SP_PD_COUNT(Map<String, Object> paramsOut);

}

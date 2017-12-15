package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.phxl.ysy.entity.Address;

public interface AddressMapper {
	
	/**
	 * 查询指定库房的地址列表
	 * @author	黄文君
	 * @date	2017年4月27日 下午3:44:41
	 * @param	storageGuid
	 * @return	List<Map<String,Object>>
	 */
	List<Map<String, Object>> queryAddrsByStorageGuid(String storageGuid);
	
	/**
	 * 查询指定库房的地址列表（下拉列表：联想搜索）
	 * @author	黄文君
	 * @date	2017年6月14日 上午11:48:48
	 * @param	storageGuid
	 * @param	searchName
	 * @return	List<Map<String,Object>>
	 */
	List<Map<String, Object>> searchAddrsByStorageGuid(@Param("storageGuid")String storageGuid, @Param("searchName")String searchName);
	
	/**
	 * 更新某某的地址为非默认
	 * @author	黄文君
	 * @date	2017年6月28日 下午3:31:08
	 * @param	tfGuid		地址归属guid
	 * @return	int
	 */
	int updateUndefaultBySpecGuid(String tfGuid);
	
	/**
	 * 根据库房guid删除库房地址
	 * @author	黄文君
	 * @date	2017年4月27日 上午10:25:58
	 * @param	storageGuid		库房guid
	 * @return	int
	 */
	int deleteAddrsByAddresses(@Param("storageGuid")String storageGuid, @Param("addresses")List<Address> addresses);
	
	/**
	 * 批量添加: 库房地址
	 * @author	黄文君
	 * @date	2017年4月27日 上午10:37:30
	 * @param	storageGuid		库房guid
	 * @param	addresses		地址信息列表
	 * @return	int
	 */
	int insertAddrsByStorageGuid(@Param("storageGuid")String storageGuid, @Param("addresses")List<Address> addresses);
	
	/**
	 * 批量修改: 库房地址
	 * @author	黄文君
	 * @date	2017年4月27日 上午10:37:30
	 * @param	storageGuid		库房guid
	 * @param	addresses		地址信息列表
	 * @return	int
	 */
	int updateAddrsByStorageGuid(@Param("storageGuid")String storageGuid, @Param("addresses")List<Address> addresses);
	
}
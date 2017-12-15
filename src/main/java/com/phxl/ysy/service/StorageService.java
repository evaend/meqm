package com.phxl.ysy.service;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.IBaseService;
import com.phxl.ysy.entity.Address;
import com.phxl.ysy.entity.StorageInfo;
import com.phxl.ysy.entity.ConfigInfo;

import java.util.List;
import java.util.Map;

public interface StorageService extends IBaseService {

	/**
     * 查询机构列表（联想下拉搜索）
     * @author	黄文君
     * @date	2017年4月25日 下午2:48:25
     * @param	pager
     * @return	List<Map<String,Object>>
     */
	public abstract List<Map<String, Object>> findOrgListForSelector(Pager<?> pager);

	/**
	 * 根据条件，查询库房列表
	 * @author	黄文君
	 * @date	2017年4月25日 下午4:12:35
	 * @param	pager
	 * @return	List<StorageInfo>
	 */
	public abstract List<StorageInfo> queryStorageList(Pager<?> pager);

	/**
	 * 查询库房列表（联想下拉搜索）
	 * @author	黄文君
	 * @date	2017年4月26日 上午11:14:32
	 * @param	pager
	 * @return	List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> findStorageListForSelector(Pager<?> pager);

	/**
	 * 查询科室列表（联想下拉搜索）
	 * @author	黄文君
	 * @date	2017年4月26日 上午11:58:07
	 * @param	pager
	 * @return	List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> findDeptListForSelector(Pager<?> pager);

	/**
	 * 查询库房开放科室列表
	 * @author	黄文君
	 * @date	2017年4月26日 下午2:57:29
	 * @param	storageGuid		库房guid
	 * @return	List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryOpenDeptsByStorageGuid(String storageGuid);

	/**
	 * 查询指定库房的开放科室列表（联想下拉搜索）
	 * @author	黄文君
	 * @date	2017年10月17日 下午
	 * @return	List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> searchOpenDeptsByStorageId(Pager<?> pager);

	/**
	 * 查询库房工作人员列表
	 * @author	黄文君
	 * @date	2017年4月26日 下午3:53:19
	 * @param	storageGuid		库房guid
	 * @return	List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryUsersByStorageGuid(String storageGuid);

	/**
	 * 保存库房开放科室列表
	 * @author	黄文君
	 * @date	2017年4月27日 上午10:37:30
	 * @param	storageGuid		库房guid
	 * @param	deptGuids		科室guid列表
	 */
	public abstract void saveDeptsByStorageGuid(String storageGuid, String[] deptGuids);

	/**
	 * 保存库房工作人员列表
	 * @author	黄文君
	 * @date	2017年4月27日 上午10:37:30
	 * @param	storageGuid		库房guid
	 * @param	deptGuids		科室guid列表
	 * @return	int
	 */
	public abstract void saveUsersByStorageGuid(String storageGuid, String[] userIds);

	/**
	 * 保存库房地址列表
	 * @author	黄文君
	 * @date	2017年4月27日 上午10:37:30
	 * @param	storageGuid		库房guid
	 * @param	addresses		地址信息列表
	 * @return	int
	 */
	public abstract void saveAddrsByStorageGuid(String storageGuid, List<Address> addresses);

	/**
	 * 查询指定库房的地址列表
	 * @author	黄文君
	 * @date	2017年4月27日 下午3:44:41
	 * @param	storageGuid
	 * @return	List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryAddrsByStorageGuid(String storageGuid);

	/**
	 * 判断某机构是否存在某个库房编号
	 * @author	黄文君
	 * @date	2017年4月28日 下午2:34:33
	 * @param	orgId
	 * @param	storageCode
	 * @return	Boolean
	 */
	public abstract Boolean existStorageCode(Long orgId, String storageCode);

	/**
	 * 判断指定库房是否已经发生业务数据
	 * @author	黄文君
	 * @date	2017年4月28日 下午5:09:06
	 * @param	storageGuid		库房guid
	 * @return	Boolean
	 */
	public abstract Boolean assertServiceAssoByStorageGuid(String storageGuid);

	/**
	 * 【库房产品】查询当前登录用户所有的库房列表（下拉框）
	 */
	public abstract List<Map<String, Object>> findStorageByUser(Pager pager);

	/**
	 * 检查当前用户是不是指定库房的工作人员
	 * @author	黄文君
	 * @date	2017年10月25日
	 * @param storageGuid   库房guid
	 * @param userId        用户id
	 * @return Boolean
	 */
	public abstract Boolean whetherUserOfSpecStorage(String storageGuid, String userId);
	
	/**
     * 判断某库房是否属于当前用户所属的库房
     * @author  fenghui
     * @date    2017年11月1日
     * @param storageGuid   库房guid
     * @param userId        用户id
     * @param orgId         机构id
     * @return Boolean
     */
    public abstract Boolean whetherStoragesUserByStorageGuid(Long orgId, String storageGuid, String userId)  throws ValidationException;

	/**
	 * 检查某科室是否是指定库房的关联科室
	 * @author	黄文君
	 * @date	2017年11月2日
	 * @param storageGuid   库房guid
	 * @param deptGuid      科室guid
	 * @return Boolean
	 */
	public Boolean whetherDeptOfSpecStorage(String storageGuid, String deptGuid);

	/**
	 * 【科室产品】查询当前登录的用户所属的科室对应的库房列表（下拉框）
	 */
	public abstract List<Map<String, Object>> findStorageByUserDept(Pager pager);

	/**
	 * 查询指定库房的地址列表（下拉列表：联想搜索）
	 * @author	黄文君
	 * @date	2017年6月14日 上午11:48:48
	 * @param	storageGuid
	 * @param	searchName
	 * @return	List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> searchAddrsByStorageGuid(String storageGuid, String searchName);

	/**
	 * 库房添加联系地址
	 * @author	黄文君
	 * @date	2017年6月28日 下午3:18:00
	 * @param	address		地址
	 * @return	void
	 */
	public abstract void insertAddress(Address address);

	/**
	 * 查询机构库房列表
	 */
	public abstract List<StorageInfo> queryOrgStorageList(Pager<StorageInfo> pager);

	/**
	 * 新增／编辑库房
	 * @throws ValidationException 
	 */
	public abstract void addUpdateStorage(StorageInfo storage, List<ConfigInfo> configInfos) throws ValidationException;

	/**
	 * 删除库房
	 * @throws ValidationException 
	 */
	public abstract void deleteOrgStorage(String storageGuid) throws ValidationException;

	/**
	 *  查询当前登录用户的机构下的所有的库房
	 * @param orgId 机构ID
	 * @return 库房ID，库房名称
	 */
	List<Map<String, Object>> selectLoginOrgStorage(Pager pager);

	/**
	 * 查询库房工作人员下拉框
	 */
	public abstract List<Map<String, Object>> queryUsersByStorageGuidForSelector(Pager<Map<String, Object>> pager);

	/**
     * 【库房产品】查询当前登录用户所有的一级库房列表（下拉框）
     */
    public abstract List<Map<String, Object>> findTopStorageByUser(Pager pager);

	/**
	 * 查找指定库房所属的大库房
	 * @author	黄文君
	 * @date	2017年9月6日 下午
	 * @param	storageGuid
	 * @return	Boolean
	 */
	public String findRootByStorageGuid(String storageGuid);

	/**
     * 【库房】查询某一级库房下所有的子库房信息（下拉框）
     */
    public abstract List<Map<String, Object>> findStorageByTop(Pager pager);

}
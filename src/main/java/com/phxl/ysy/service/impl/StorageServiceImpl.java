package com.phxl.ysy.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.core.base.util.IdentifieUtil;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.core.base.util.LocalStringUtils;
import com.phxl.ysy.dao.AddressMapper;
import com.phxl.ysy.dao.DeptMapper;
import com.phxl.ysy.dao.OrgInfoMapper;
import com.phxl.ysy.dao.StorageDeptMapper;
import com.phxl.ysy.dao.StorageInfoMapper;
import com.phxl.ysy.dao.StorageUserMapper;
import com.phxl.ysy.entity.Address;
import com.phxl.ysy.entity.SourceInfo;
import com.phxl.ysy.entity.StorageInfo;
import com.phxl.ysy.service.StorageService;
import com.phxl.ysy.constant.CustomConst.ConfigFlag;
import com.phxl.ysy.constant.CustomConst.YesOrNo;
import com.phxl.ysy.entity.ConfigInfo;
import com.phxl.ysy.service.ConfigService;

/**
 * 库房服务
 * @date	2017年4月25日 上午11:03:01
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
@Service
public class StorageServiceImpl extends BaseService implements StorageService {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	StorageInfoMapper storageInfoMapper;
	
	@Autowired
	StorageDeptMapper storageDeptMapper;
	
	@Autowired
	StorageUserMapper storageUserMapper;

	@Autowired
	AddressMapper addressMapper;
	
	@Autowired
	OrgInfoMapper orgInfoMapper;
	
	@Autowired
	DeptMapper deptMapper;
	
	@Autowired
	ConfigService configService;
	
	/**
     * 查询机构列表（联想下拉搜索）
     * @author	黄文君
     * @date	2017年4月25日 下午2:48:25
     * @param	pager
     * @return	List<Map<String,Object>>
     */
    
	public List<Map<String, Object>> findOrgListForSelector(Pager<?> pager){
    	return orgInfoMapper.findOrgListForSelector(pager);
    }
    
    /**
	 * 根据条件，查询库房列表
	 * @author	黄文君
	 * @date	2017年4月25日 下午4:12:35
	 * @param	pager
	 * @return	List<StorageInfo>
	 */
    
	public List<StorageInfo> queryStorageList(Pager<?> pager){
		return storageInfoMapper.queryStorageList(pager);
	}
    
    /**
	 * 查询库房列表（联想下拉搜索）
	 * @author	黄文君
	 * @date	2017年4月26日 上午11:14:32
	 * @param	pager
	 * @return	List<Map<String,Object>>
	 */
    
	public List<Map<String, Object>> findStorageListForSelector(Pager<?> pager){
		return storageInfoMapper.findStorageListForSelector(pager);
	}
    
    /**
	 * 查询科室列表（联想下拉搜索）
	 * @author	黄文君
	 * @date	2017年4月26日 上午11:58:07
	 * @param	pager
	 * @return	List<Map<String,Object>>
	 */
    public List<Map<String, Object>> findDeptListForSelector(Pager<?> pager){
		return deptMapper.findDeptListForSelector(pager);
	}
    
    /**
	 * 查询指定库房的开放科室列表（联想下拉搜索）
	 * @author	黄文君
	 * @date	2017年10月17日 下午
	 * @return	List<Map<String,Object>>
	 */
	
	public List<Map<String, Object>> queryOpenDeptsByStorageGuid(String storageGuid){
		return storageDeptMapper.queryOpenDeptsByStorageGuid(storageGuid);
	}

	/**
	 * 查询指定库房的开放科室列表（联想下拉搜索）
	 * @author	黄文君
	 * @date	2017年10月17日 下午
	 * @return	List<Map<String,Object>>
	 */
	
	public List<Map<String, Object>> searchOpenDeptsByStorageId(Pager<?> pager){
		return storageDeptMapper.searchOpenDeptsByStorageId(pager);
	}

	/**
	 * 查询库房工作人员列表
	 * @author	黄文君
	 * @date	2017年4月26日 下午3:53:19
	 * @param	storageGuid		库房guid
	 * @return	List<Map<String,Object>>
	 */
	
	public List<Map<String, Object>> queryUsersByStorageGuid(String storageGuid) {
		return storageUserMapper.queryUsersByStorageGuid(storageGuid);
	}
	
	/**
	 * 查询指定库房的地址列表
	 * @author	黄文君
	 * @date	2017年4月27日 下午3:44:41
	 * @param	storageGuid
	 * @return	List<Map<String,Object>>
	 */
	
	public List<Map<String, Object>> queryAddrsByStorageGuid(String storageGuid) {
		return addressMapper.queryAddrsByStorageGuid(storageGuid);
	}

	/**
	 * 查询指定库房的地址列表（下拉列表：联想搜索）
	 * @author	黄文君
	 * @date	2017年6月14日 上午11:48:48
	 * @param	storageGuid
	 * @param	searchName
	 * @return	List<Map<String,Object>>
	 */
	
	public List<Map<String, Object>> searchAddrsByStorageGuid(String storageGuid, String searchName) {
		return addressMapper.searchAddrsByStorageGuid(storageGuid, searchName);
	}

	/**
	 * 保存库房开放科室列表
	 * @author	黄文君
	 * @date	2017年4月27日 上午10:37:30
	 * @param	storageGuid		库房guid
	 * @param	deptGuids		科室guid列表
	 * @return	int
	 */
	
	public void saveDeptsByStorageGuid(String storageGuid, String[] deptGuids) {
		storageDeptMapper.deleteDeptsByStorageGuid(storageGuid);
		if(ArrayUtils.isNotEmpty(deptGuids)){
			storageDeptMapper.insertDeptsByStorageGuid(storageGuid, deptGuids);
		}
	}
	
	/**
	 * 保存库房工作人员列表
	 * @author	黄文君
	 * @date	2017年4月27日 上午10:37:30
	 * @param	storageGuid		库房guid
	 * @param	deptGuids		科室guid列表
	 * @return	int
	 */
	
	public void saveUsersByStorageGuid(String storageGuid, String[] userIds) {
		//根据库房guid删除库房工作人员
		storageUserMapper.deleteUsersByStorageGuid(storageGuid);
		if(ArrayUtils.isNotEmpty(userIds)){
			//批量添加: 库房工作人员
			storageUserMapper.insertUsersByStorageGuid(storageGuid, userIds);
		}
	}
	
	/**
	 * 保存库房地址列表
	 * @author	黄文君
	 * @date	2017年4月27日 上午10:37:30
	 * @param	storageGuid		库房guid
	 * @param	addresses		地址信息列表
	 * @return	int
	 */
	
	public void saveAddrsByStorageGuid(String storageGuid, List<Address> addresses) {
		
		//查看该组原来的功能（菜单）列表
		Address condition = new Address();
		condition.setTfCloGuid(storageGuid);
		List<Address> originList = super.searchList(condition);
		List<Address> modifyList = null;
		if(CollectionUtils.isNotEmpty(originList) && CollectionUtils.isNotEmpty(addresses)){
			//遍历查找，找出删除项、添加项
			Iterator<Address> it1 = originList.iterator();
			while (it1.hasNext()) {
				if(CollectionUtils.isEmpty(originList) || CollectionUtils.isEmpty(addresses)){break;}
				Address origin = it1.next();
				Iterator<Address> it2 = addresses.iterator();
				while (it2.hasNext()) {
					Address to = it2.next();
					if(origin.getAddrGuid()!=null && origin.getAddrGuid().equals(to.getAddrGuid())){
						if(
							LocalStringUtils.notEqualTrim(origin.getTfAddress(), to.getTfAddress()) ||
							LocalStringUtils.notEqualTrim(origin.getLinkman(), to.getLinkman()) ||
							LocalStringUtils.notEqualTrim(origin.getLinktel(), to.getLinktel()) ||
							LocalStringUtils.notEqualTrim(origin.getIsDefault(), to.getIsDefault())
						){
							if(modifyList==null){
								modifyList = new ArrayList<Address>();
							}
							modifyList.add(to);
						}
						it1.remove();
						it2.remove();
					}
				}
			}
		}
		
		List<Address> deleteList = originList;//删除的功能（菜单）集
		List<Address> addList = addresses;//添加的功能（菜单）集
		
		StringBuffer msg = new StringBuffer();
		if(CollectionUtils.isNotEmpty(deleteList)){
			//批量删除: 库房地址
			int num1 = addressMapper.deleteAddrsByAddresses(storageGuid, deleteList);
			msg.append("删除").append(num1).append("个库房地址｜");
		}
		if(CollectionUtils.isNotEmpty(addList)){
			//批量添加: 库房地址
			int num2 = addressMapper.insertAddrsByStorageGuid(storageGuid, addList);
			msg.append("新增").append(num2).append("个库房地址｜");
		}
		if(CollectionUtils.isNotEmpty(modifyList)){
			int num3 = 0;
			for(Address addr: modifyList){
				//修改: 库房地址
				num3 += super.updateInfo(addr);
			}
			msg.append("修改").append(num3).append("个库房地址。");
		}
		
		if(msg.length()>0){
			logger.debug(msg.toString());
		}
	}

	/**
	 * 判断某机构是否存在某个库房编号
	 * @author	黄文君
	 * @date	2017年4月28日 下午2:34:33
	 * @param	orgId			机构id
	 * @param	storageCode		库房编号
	 * @return	Boolean
	 */
	
	public Boolean existStorageCode(Long orgId, String storageCode) {
		return storageInfoMapper.existStorageCode(orgId, storageCode);
	}
	
	/**
	 * 判断指定库房是否已经发生业务数据
	 * @author	黄文君
	 * @date	2017年4月28日 下午5:09:06
	 * @param	storageGuid		库房guid
	 * @return	Boolean
	 */
	
	public Boolean assertServiceAssoByStorageGuid(String storageGuid){
		return false;
	}

	
	public List<Map<String, Object>> findStorageByUser(Pager pager) {
		return storageUserMapper.findStoragesByUser(pager);
	}

	/**
	 * 检查当前用户是不是指定库房的工作人员
	 * @author	黄文君
	 * @date	2017年10月25日
	 * @param storageGuid   库房guid
	 * @param userId        用户id
	 * @return Boolean
	 */
	
	public Boolean whetherUserOfSpecStorage(String storageGuid, String userId) {
		return storageUserMapper.whetherUserOfSpecStorage(storageGuid, userId);
	}
	
	/**
     * 判断某库房是否属于当前用户所属的库房
     * @author  fenghui
     * @date    2017年11月1日
     * @param storageGuid   库房guid
     * @param userId        用户id
     * @param orgId         机构id
     * @return Boolean
     */
    public Boolean whetherStoragesUserByStorageGuid(Long orgId, String storageGuid, String userId) throws ValidationException {
        Assert.notNull(orgId, "机构ID，不能为空");
        LocalAssert.notBlank(storageGuid, "库房ID，不能为空");
        LocalAssert.notBlank(userId, "用户ID，不能为空");
        return storageUserMapper.whetherStoragesUserByStorageGuid(orgId, storageGuid, userId);
    }

	/**
	 * 检查某科室是否是指定库房的关联科室
	 * @author	黄文君
	 * @date	2017年11月2日
	 * @param storageGuid   库房guid
	 * @param deptGuid      科室guid
	 * @return Boolean
	 */
	
	public Boolean whetherDeptOfSpecStorage(String storageGuid, String deptGuid) {
		return storageDeptMapper.whetherDeptOfSpecStorage(storageGuid, deptGuid);
	}

	
    public List<Map<String, Object>> findTopStorageByUser(Pager pager) {
        return storageUserMapper.findTopStorageByUser(pager);
    }
	
    
    public List<Map<String, Object>> findStorageByTop(Pager pager) {
        return storageUserMapper.findStorageByTop(pager);
    }

	
	public List<Map<String, Object>> findStorageByUserDept(Pager pager) {
		return storageUserMapper.findStorageByUserDept(pager);
	}
	
	/**
	 * 库房添加联系地址
	 * @author	黄文君
	 * @date	2017年6月28日 下午3:18:00
	 * @param	address		地址
	 * @return	void
	 */
	
	public void insertAddress(Address address){
		if(YesOrNo.YES.equals(address.getIsDefault())){
			//将该库房的其他地址设定非默认地址
			addressMapper.updateUndefaultBySpecGuid(address.getTfCloGuid());
		}
		super.insertInfo(address);
	}

	
	public List<StorageInfo> queryOrgStorageList(Pager<StorageInfo> pager) {
		// TODO Auto-generated method stub
		return storageInfoMapper.queryStorageList(pager);
	}

	
	public void addUpdateStorage(StorageInfo storage, List<ConfigInfo> configInfos) throws ValidationException {
		if(StringUtils.isBlank(storage.getStorageGuid())){//新增库房
			//同一机构:库房编号,不能重复
			if(this.existStorageCode(storage.getOrgId(), storage.getStorageCode())){
				throw new ValidationException("库房编号已经存在（必须机构内唯一）");
			}
			storage.setStorageGuid(IdentifieUtil.getGuId());			
			this.insertInfo(storage);
		}else{//编辑库房
			StorageInfo origin = this.find(StorageInfo.class, storage.getStorageGuid());
			if(!origin.getStorageCode().equals(storage.getStorageCode()) && this.existStorageCode(origin.getOrgId(), storage.getStorageCode())){
				throw new ValidationException("库房编号已经存在（必须机构内唯一）");
			}
			this.updateInfo(storage);
		}
//		新增或编辑配置信息，不存在的配置即新增，存在的配置做更新操作
		if(configInfos != null && !configInfos.isEmpty()){
			configService.insertUpdateConfigInfo(configInfos,ConfigFlag.STORAGE,storage.getStorageGuid());
		}
		
		//配置的属性如果有配送方式或结算方式发生变更，需要批量刷新库房产品
	}

	
	public void deleteOrgStorage(String storageGuid) throws ValidationException {
//		1、判断是否有供应关系，如果有，不能删除；没有，可删除，需要联动删除库房人员、库房科室、库房地址表
		SourceInfo sourceInfo = new SourceInfo();
		sourceInfo.setrStorageGuid(storageGuid);
		List<SourceInfo> sourceInfos = this.searchList(sourceInfo);
		if(sourceInfos != null && sourceInfos.size() >0){
			throw new ValidationException("该库房已有产生供需关系，不能删除！");
		}
		//删除库房
		this.deleteInfoById(StorageInfo.class, storageGuid);
		//删除库房人员
		storageUserMapper.deleteUsersByStorageGuid(storageGuid);
//		删除库房科室
		storageDeptMapper.deleteDeptsByStorageGuid(storageGuid);
//		删除库房地址
		addressMapper.deleteAddrsByAddresses(storageGuid, null);
		
		
	}

	
	public List<Map<String, Object>> selectLoginOrgStorage(Pager pager) {
		return storageInfoMapper.selectLoginOrgStorage(pager);
	}

	
	public List<Map<String, Object>> queryUsersByStorageGuidForSelector(Pager<Map<String, Object>> pager) {
		return storageUserMapper.queryUsersByStorageGuidForSelector(pager);
	}

	/**
	 * 查找指定库房所属的大库房
	 * @author	黄文君
	 * @date	2017年9月6日 下午
	 * @param	storageGuid
	 * @return	Boolean
	 */
	
	public String findRootByStorageGuid(String storageGuid) {
		return storageInfoMapper.findRootByStorageGuid(storageGuid);
	}

}

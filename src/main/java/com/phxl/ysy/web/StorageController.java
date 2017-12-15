package com.phxl.ysy.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.interceptor.ResResultBindingInterceptor;
import com.phxl.core.base.util.IdentifieUtil;
import com.phxl.core.base.util.JSONUtils;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.ysy.entity.Address;
import com.phxl.ysy.entity.StorageInfo;
import com.phxl.ysy.service.StorageService;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.constant.CustomConst.DictName;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.constant.CustomConst.OrgType;
import com.phxl.ysy.constant.CustomConst.StorageLevel;
import com.phxl.ysy.constant.CustomConst.StorageSourceType;
import com.phxl.ysy.constant.CustomConst.YesOrNo;
import com.phxl.ysy.entity.ConfigInfo;
import com.phxl.ysy.service.StaticDataService;

/**
 * 库房管理
 * @date	2017年4月28日 下午3:44:19
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
@Controller
@RequestMapping("storage")
public class StorageController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	StorageService storageService;
	@Autowired
	StaticDataService staticDataService;

	/**
	 * [运营]：查询全部机构大库房列表
	 * @author	黄文君
	 * @date	2017年4月24日 下午4:24:57
	 * 
	 * @param	storageName		库房名称（模糊搜索）
	 * @param	orgName			机构名称（模糊搜索）
	 * @param	pagesize		每页记录数
	 * @param	page			当前页码
	 * @param	sidx			排序字段名称
	 * @param	sord			排序方式（desc/descend：降序； asc/ascend：升序）
	 * @throws	Exception 
	 * @return	Pager<StorageInfo>
	 */
	@RequestMapping("findTopStorageList")
	@ResponseBody
	public Pager<StorageInfo> findTopStorageList(String storageName,
												 String orgName,
												 Integer pagesize,
												 Integer page,
												 String sidx,
												 String sord) throws Exception {
		Pager<StorageInfo> pager = new Pager<StorageInfo>();
		pager.setPageSize(pagesize);
		pager.setPageNum(page);
		
		pager.addQueryParam("storageLevelCode", StorageLevel.TOP);
		pager.addQueryParam("storageNameLike", storageName);
		pager.addQueryParam("orgNameLike", orgName);
		
		if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)){
			pager.addQueryParam("resultMapName", "com.phxl.ysy.basicDataModule.dao.StorageInfoMapper.BaseExtResultMap");//设置ResultMap映射关系
			pager.addQueryParam("orderField", sidx);
			pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sord)?"asc":"desc");
		}

		pager.setRows(storageService.queryStorageList(pager));
		
		return pager;
	}
	
	/**
	 * [运营/客户]：查询某一个库房信息
	 * @author	黄文君
	 * @date	2017年4月24日 下午4:33:04
	 * @param	storageGuid		库房guid
	 * @throws	Exception 
	 * @return	StorageInfo
	 */
	@RequestMapping("findStorageById")
	@ResponseBody
	public StorageInfo findStorageById(String storageGuid) throws Exception {
		LocalAssert.notBlank(storageGuid, "库房guid，不能为空");
		StorageInfo origin = new StorageInfo();
		origin.setStorageGuid(storageGuid);
		return storageService.searchEntity(origin);
	}
	
	/**
	 * [客户中心]：查询本机构的库房列表
	 * @author	黄文君
	 * @date	2017年4月24日 下午4:24:57
	 * 
	 * @param	searchName		模糊搜索关键字		
	 * @param	pagesize		每页记录数
	 * @param	page			当前页码
	 * @param	sidx			排序字段名称
	 * @param	sord			排序方式（desc：降序； asc：升序）
	 * @throws	Exception 
	 * @return	Pager<StorageInfo>
	 */
	@RequestMapping("findMyStorageList")
	@ResponseBody
	public Pager<StorageInfo> findMyStorageList(String searchName,
												Integer pagesize,
												Integer page,
												String sidx,
												String sord,
												HttpSession session) throws Exception {
		Pager<StorageInfo> pager = new Pager<StorageInfo>();
		pager.setPageSize(pagesize);
		pager.setPageNum(page);
		
		pager.addQueryParam("orgId", (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID));
		pager.addQueryParam("searchName", searchName);
		
		if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)){
			pager.addQueryParam("resultMapName", "com.phxl.ysy.basicDataModule.dao.StorageInfoMapper.BaseExtResultMap");//设置ResultMap映射关系
			pager.addQueryParam("orderField", sidx);
			pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sord)?"asc":"desc");
		}

		pager.setRows(storageService.queryStorageList(pager));
		return pager;
	}
	
	/**
	 * 运营-配置库房
	 */
	@RequestMapping("findOrgStorageList4yunying")
	@ResponseBody
	public Pager<StorageInfo> findOrgStorageList4yunying(String searchName,
												Integer pagesize,
												Integer page,
												String orgId,
												HttpSession session) throws Exception {
		Pager<StorageInfo> pager = new Pager<StorageInfo>();
		pager.setPageSize(pagesize);
		pager.setPageNum(page);
		
		LocalAssert.notBlank(orgId, "运营-配置库房，必须选择机构！");
		
		pager.addQueryParam("orgId", orgId);
		pager.addQueryParam("searchName", searchName);
		pager.addQueryParam("configCode", "storageConfigYY");
		
//		if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)){
//			pager.addQueryParam("resultMapName", "com.phxl.ysy.basicDataModule.dao.StorageInfoMapper.BaseExtResultMap");//设置ResultMap映射关系
//			pager.addQueryParam("orderField", sidx);
//			pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sord)?"asc":"desc");
//		}
		pager.setRows(storageService.queryOrgStorageList(pager));
		return pager;
	}
	
	/**
	 * 客户-库房管理列表
	 */
	@RequestMapping("findOrgStorageList4kehu")
	@ResponseBody
	public Pager<StorageInfo> findOrgStorageList4kehu(String searchName,
												Integer pagesize,
												Integer page,
												HttpSession session) throws Exception {
		Pager<StorageInfo> pager = new Pager<StorageInfo>();
		pager.setPageSize(pagesize);
		pager.setPageNum(page);
		
		pager.addQueryParam("orgId", (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID));		
		pager.addQueryParam("searchName", searchName);
		pager.addQueryParam("configCode", "storageConfigKH");
		
//		if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)){
//			pager.addQueryParam("resultMapName", "com.phxl.ysy.basicDataModule.dao.StorageInfoMapper.BaseExtResultMap");//设置ResultMap映射关系
//			pager.addQueryParam("orderField", sidx);
//			pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sord)?"asc":"desc");
//		}
		pager.setRows(storageService.queryOrgStorageList(pager));
		return pager;
	}
	
	/**
	 * 运营端添加库房
	 */
	@RequestMapping("addUpdateStorage4yunying")
	@ResponseBody
	public void addUpdateStorage4yunying(HttpServletRequest request,
			HttpSession session) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项:忽略未知属性
		
		//库房信息
		StorageInfo storage = objectMapper.readValue(request.getReader(), StorageInfo.class);		
		LocalAssert.notBlank(storage.getStorageName(), "库房名称，不能为空");
		LocalAssert.notBlank(storage.getStorageCode(), "库房编码，不能为空");
		Assert.notNull(storage.getOrgId(), "请指定: 库房所属机构");

		List<ConfigInfo> configInfos = storage.getConfigInfos();
		Set<String> configCodes =  new HashSet<String>();
//		配置属性条件
		for(ConfigInfo configInfo:configInfos){
			if(StringUtils.isNotBlank(configInfo.getConfigCode())){
				configCodes.add(configInfo.getConfigCode().trim());
			}
			if("storageLevelCode".equals(configInfo.getConfigCode())){
				storage.setStorageLevelCode(configInfo.getConfigValue());
			}
			if("storageSourceType".equals(configInfo.getConfigCode())){
				storage.setStorageSourceType(configInfo.getConfigValue());
			}
			if("sourceStorageGuid".equals(configInfo.getConfigCode())){
				storage.setSourceStorageGuid(configInfo.getConfigValue());
			}
		}
		//库房客户端-验证配置参数范围
		validateConfigInfos(DictName.STORAGE_CONFIGYY, configCodes.toArray());
		LocalAssert.notBlank(storage.getStorageLevelCode(), "库房级别，不能为空");			
		//库房数据项长度检查
		validateFieldLength(storage);		
		String sessionOrgType = (String)session.getAttribute(LoginUser.SESSION_USER_ORG_TYPE);
		if(StorageLevel.TOP.equals(storage.getStorageLevelCode())){
			LocalAssert.contain(new String[]{StorageSourceType.TENDER, StorageSourceType.REF_STORAGE}, 
					storage.getStorageSourceType(), "一级库房: 货物来源方式，只能是招标或库房");
		}
		
		if(StorageSourceType.REF_STORAGE.equals(storage.getStorageSourceType())){//货物来源方式:库房
			//请指定货物来源库房
			LocalAssert.notBlank(storage.getSourceStorageGuid(), "请指定货物来源库房");
			//检查: 货物来源库房，必须与所属机构是一个体系（即两个不相干的医院不允许指定为来源库房）
			assertRelationForOrgAndRefStorage(storage.getOrgId(), storage.getStorageGuid(), storage.getSourceStorageGuid(), storage.getStorageLevelCode());
		}else{
			storage.setSourceStorageGuid(null);
		}
		
		//只有运营商、医院与供应商才能维护库房
		LocalAssert.contain(new String[]{OrgType.PLATFORM, OrgType.HOSPITAL, OrgType.SUPPLIER}, sessionOrgType, "只有运营商、医院与供应商才能维护库房");
		
		//添加更新库房信息
		storageService.addUpdateStorage(storage,configInfos);
	
	}
	
	@RequestMapping("updateStorage4kehu")
	@ResponseBody
	public void updateStorage4kehu(HttpServletRequest request,
			HttpSession session) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项:忽略未知属性
		
		//库房信息
		StorageInfo storage = objectMapper.readValue(request.getReader(), StorageInfo.class);		

		List<ConfigInfo> configInfos = storage.getConfigInfos();
		LocalAssert.notBlank(storage.getStorageGuid(), "库房id，不能为空");
		LocalAssert.notBlank(storage.getStorageName(), "库房名称，不能为空");
		LocalAssert.notBlank(storage.getStorageCode(), "库房编码，不能为空");

		Set<String> configCodes =  new HashSet<String>();
		List<ConfigInfo> newConfigInfos = new ArrayList<ConfigInfo>();
//		配置属性条件
		for(ConfigInfo configInfo : configInfos){
			if(StringUtils.isNotBlank(configInfo.getConfigCode())){
				configCodes.add(configInfo.getConfigCode().trim());
			}
			if(StringUtils.isNotBlank(configInfo.getConfigValue())){
				newConfigInfos.add(configInfo);
			}
		}
		//库房客户端-验证配置参数范围
		validateConfigInfos(DictName.STORAGE_CONFIGKH, configCodes.toArray());		
		//库房数据项长度检查
		validateFieldLength(storage);
		
		String sessionOrgType = (String)session.getAttribute(LoginUser.SESSION_USER_ORG_TYPE);		
		//只有运营商、医院与供应商才能维护库房
		LocalAssert.contain(new String[]{OrgType.PLATFORM, OrgType.HOSPITAL, OrgType.SUPPLIER}, sessionOrgType, "只有运营商、医院与供应商才能维护库房");
		
		//添加更新库房信息
		storageService.addUpdateStorage(storage,newConfigInfos);
	
	}
	
	private void validateConfigInfos(String dictName, Object[] names) throws ValidationException {
		//检查: 配置参数必须是自己权限范围内的
		if(ArrayUtils.isNotEmpty(names)){
			String[] filterNames = staticDataService.filterNoExistCodes(dictName, names);
			if(ArrayUtils.isNotEmpty(filterNames)){
				throw new ValidationException(Arrays.toString(filterNames)+"，都不属于您能修改的配置，请确认。");
			}
		}
	}
	
	
	/**
	 * [客户中心]配置:库房开放科室
	 * @author	黄文君
	 * @date	2017年4月25日 上午10:36:16
	 * @param	storageGuid			库房guid
	 * @param	deptIds				科室id列表
	 * @throws	Exception
	 * @return	void
	 */
	@RequestMapping("saveDeptsByStorage")
	@ResponseBody
	public void saveDeptsByStorage(String storageGuid, String[] deptIds) throws Exception {
		LocalAssert.notBlank(storageGuid, "storageGuid，不能为空");
		//批量添加: 库房开放科室
		storageService.saveDeptsByStorageGuid(storageGuid, deptIds);
	}
	
	/**
	 * [客户中心]配置:库房工作人员
	 * @author	黄文君
	 * @date	2017年4月25日 上午10:36:16
	 * @param	storageGuid			库房guid
	 * @param	userIds				用户guid列表
	 * @throws	Exception
	 * @return	void
	 */
	@RequestMapping("saveUsersByStorage")
	@ResponseBody
	public void saveUsersByStorage(String storageGuid, String[] userIds) throws Exception {
		LocalAssert.notBlank(storageGuid, "storageGuid，不能为空");
		//批量添加: 库房工作人员
		storageService.saveUsersByStorageGuid(storageGuid, userIds);
	}
	
	/**
	 * [客户中心]配置:库房地址
	 * @author	黄文君
	 * @date	2017年4月25日 上午10:36:16
	 * @param	storageGuid			库房guid
	 * @param	addresses			地址列表
	 * @throws	Exception
	 * @return	void
	 */
	@RequestMapping("saveAddrsByStorage")
	@ResponseBody
	public void saveAddrsByStorage(HttpServletRequest request) throws Exception {
		Map params = JSONUtils.toBean(request.getReader(), Map.class);
		String storageGuid = (String)params.get("storageGuid");
		List<Map<String, Object>> addressList = (List<Map<String, Object>>)params.get("addresses");
		
		LocalAssert.notBlank(storageGuid, "storageGuid，不能为空");
		List<Address> addrs = new ArrayList<Address>(addressList.size());
		int defaultCount = 0;
		if(CollectionUtils.isNotEmpty(addressList)){
			for(int i=0; i<addressList.size(); i++){
				Map addr = addressList.get(i);
				Address address = new Address();
				address.setAddrGuid((String)addr.get("addrGuid"));
				address.setTfAddress((String)addr.get("tfAddress"));
				address.setLinkman((String)addr.get("linkman"));
				address.setLinktel((String)addr.get("linktel"));
				address.setIsDefault(((Number)addr.get("isDefault")).intValue()==1?"01":"00");
				
				LocalAssert.notBlank(address.getTfAddress(), "库房地址，不能为空");
				if(address.getTfAddress().length()>50){
					throw new ValidationException("库房地址，长度不能超过50个字符!");
				}
				LocalAssert.notBlank(address.getLinkman(), "联系人，不能为空");
				if(address.getLinkman().length()>25){
					throw new ValidationException("联系人，长度不能超过25个字符!");
				}
				LocalAssert.notBlank(address.getLinktel(), "联系电话，不能为空");
				if(address.getLinktel().length()>25){
					throw new ValidationException("联系电话，长度不能超过25个字符!");
				}
				if(YesOrNo.YES.equals(address.getIsDefault())){
					defaultCount++;
				}
				address.setAddrTable("TD_STORAGE_INFO");
				addrs.add(address);
			}
		}
		
		if(defaultCount>1){
			throw new ValidationException("只允许有一个默认地址");
		}
		
		//保存库房地址列表
		storageService.saveAddrsByStorageGuid(storageGuid, addrs);
	}
	
	/**
	 * [客户中心]：查询指定库房的开放科室列表
	 * @author	黄文君
	 * @date	2017年4月25日 上午10:36:16
	 * @param	storageGuid
	 * @throws	Exception
	 * @return	List<StorageDept>
	 */
	@RequestMapping("queryOpenDeptsByStorageGuid")
	@ResponseBody
	public List<Map<String, Object>> queryOpenDeptsByStorageGuid(String storageGuid) throws Exception {
		LocalAssert.notBlank(storageGuid, "storageGuid，不能为空");
		return storageService.queryOpenDeptsByStorageGuid(storageGuid);
	}
	
	/**
	 * [客户中心]：查询指定库房的工作人员列表
	 * @author	黄文君
	 * @date	2017年4月25日 上午10:36:16
	 * @param	storageGuid
	 * @throws	Exception
	 * @return	List<StorageUser>
	 */
	@RequestMapping("queryUsersByStorageGuid")
	@ResponseBody
	public List<Map<String, Object>> queryUsersByStorageGuid(String storageGuid) throws Exception {
		LocalAssert.notBlank(storageGuid, "storageGuid，不能为空");
		return storageService.queryUsersByStorageGuid(storageGuid);
	}
	
	/**
	 * [客户中心]：查询指定库房的工作人员列表下拉框
	 */
	@RequestMapping("queryUsersByStorageGuidForSelect")
	@ResponseBody
	public List<Map<String, Object>> queryUsersByStorageGuidForSelect(String storageGuid,
			String searchName, HttpServletRequest request) throws Exception {
		LocalAssert.notBlank(storageGuid, "storageGuid，不能为空");
		
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(false);

		pager.addQueryParam("searchName", searchName);
		pager.addQueryParam("storageGuid", storageGuid);
		
		request.setAttribute(ResResultBindingInterceptor.IGNORE_STD_RESULT, true);//忽略标准结果
		return storageService.queryUsersByStorageGuidForSelector(pager);
	}
	/**
	 * [客户中心]：查询指定库房的地址列表
	 * @author	黄文君
	 * @date	2017年4月25日 上午10:36:16
	 * @param	storageGuid
	 * @throws	Exception
	 * @return	List<Map<String, Object>>
	 */
	@RequestMapping("queryAddrsByStorageGuid")
	@ResponseBody
	public List<Map<String, Object>> queryAddrsByStorageGuid(String storageGuid) throws Exception {
		LocalAssert.notBlank(storageGuid, "storageGuid，不能为空");
		return storageService.queryAddrsByStorageGuid(storageGuid);
	}
	
	/**
	 * [运营]：查询机构列表（所属机构：下拉搜索）
	 * @author	黄文君
	 * @date	2017年4月24日 下午5:49:01
	 * @param	searchName		模糊搜索关键字		
	 * @param	pagesize		每页记录数
	 * @param	page			当前页码
	 * @throws	Exception 
	 * @return	List<Map<String, Object>>
	 */
	@RequestMapping("searchOrgList")
	@ResponseBody
	public List<Map<String, Object>> searchOrgList(String searchName,
	                                               String orgType,
									 			   Integer pagesize,
									 			   Integer page,
									 			   HttpServletRequest request) throws Exception {
		request.setAttribute(ResResultBindingInterceptor.IGNORE_STD_RESULT, true);//忽略标准结果
		
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(true, false);
		pager.setPageSize(pagesize);
		pager.setPageNum(page);
		
		pager.addQueryParam("searchName", searchName);
		pager.addQueryParam("orgTypes", StringUtils.isBlank(orgType) ? new String[]{OrgType.HOSPITAL, OrgType.SUPPLIER} : new String[]{orgType});
		
		return storageService.findOrgListForSelector(pager);
	}
	
	/**
	 * [运营]：创建一级库房，查询指定机构体系内一级库房列表（货物来源：下拉搜索）（适用库房管理、供应管理库房下拉框）
	 * @author	黄文君
	 * @date	2017年4月24日 下午4:24:57
	 * 
	 * @param	orgId				所属机构id
	 * @param	scopeType			查询范围（hierarchy:机构体系内查询；default:默认本机构查询）
	 * @param	searchName			模糊搜索关键字	
	 * @param	levels				库房级别（01:一级库房、02:二级库房、03:三级库房）
	 * @param	sourceTpyes			货物来源方式（00:招标、01:库房、02:标准产品库）
	 * @param	pagesize			每页记录数
	 * @param	page				当前页码
	 * @param	excludeStorageGuid	排除库房guid
	 * @throws	Exception 
	 * @return	Pager<Map<String, Object>>
	 */
	@RequestMapping("searchStorageList4yunying")
	@ResponseBody
	public List<Map<String, Object>> searchStorageList4yunying(Long orgId,
																	String scopeType,
																 	String searchName,
																 	String[] levels,
																 	String[] sourceTypes,
																 	Integer pagesize,
																 	Integer page,
																 	String excludeStorageGuid,
																 	HttpSession session,
																 	HttpServletRequest request) throws Exception {
	    String sessionOrgType = (String)session.getAttribute(LoginUser.SESSION_USER_ORG_TYPE);
	    if(OrgType.PLATFORM.equals(sessionOrgType)){//当前用户所属机构类型是运营机构
	        Assert.notNull(orgId, "请指定: 所属机构");
        }
		
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(true, false);
		pager.setPageSize(pagesize);
		pager.setPageNum(page);
		
		pager.addQueryParam("scopeType", StringUtils.defaultIfEmpty(scopeType, "hierarchy"));
		pager.addQueryParam("orgId", orgId==null ? (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID) : orgId);
		pager.addQueryParam("searchName", searchName);
		pager.addQueryParam("levels", ArrayUtils.isEmpty(levels) ? new String[]{StorageLevel.TOP} : levels);
		pager.addQueryParam("sourceTypes", ArrayUtils.isEmpty(sourceTypes) ? new String[]{StorageSourceType.TENDER} : sourceTypes);
		pager.addQueryParam("excludeStorageGuid", excludeStorageGuid);
		
		request.setAttribute(ResResultBindingInterceptor.IGNORE_STD_RESULT, true);//忽略标准结果
		return storageService.findStorageListForSelector(pager);
	}
	
	/**
	 * [客户中心]：创建子库房，查询本机构库房列表（货物来源：下拉搜索）（暂时用不到）
	 * @author	黄文君
	 * @date	2017年4月24日 下午4:24:57
	 * 
	 * @param	searchName			模糊搜索关键字	
	 * @param	levels				库房级别（01:一级库房、02:二级库房、03:三级库房）
	 * @param	sourceTpyes			货物来源方式（00:招标、01:库房、02:标准产品库）
	 * @param	pagesize			每页记录数
	 * @param	page				当前页码
	 * @param	excludeStorageGuid	排除库房guid
	 * @throws	Exception 
	 * @return	List<Map<String, Object>>
	 */
	@RequestMapping("searchStorageList4kehu")
	@ResponseBody
	public List<Map<String, Object>> searchStorageList4kehu(Long orgId,String searchName,
															String[] levels,
															String[] sourceTypes,
															Integer pagesize,
															Integer page,
															String excludeStorageGuid,
															HttpSession session,
															HttpServletRequest request) throws Exception {
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(true, false);
		pager.setPageSize(pagesize);
		pager.setPageNum(page);
		
		pager.addQueryParam("orgId", orgId==null?(Long)session.getAttribute(LoginUser.SESSION_USER_ORGID):orgId);//会话机构id
		pager.addQueryParam("searchName", searchName);
		pager.addQueryParam("levels", ArrayUtils.isEmpty(levels) ? new String[]{StorageLevel.TOP} : levels);
		pager.addQueryParam("sourceTypes", ArrayUtils.isEmpty(sourceTypes) ? new String[]{StorageSourceType.TENDER, StorageSourceType.REF_STORAGE} : sourceTypes);
		pager.addQueryParam("excludeStorageGuid", excludeStorageGuid);
		
		request.setAttribute(ResResultBindingInterceptor.IGNORE_STD_RESULT, true);//忽略标准结果
		return storageService.findStorageListForSelector(pager);
	}
	
	/**
     * [客户中心-招标模块-供应管理]：创建供需关系，查询本机构与和上级机构库房列表，且“产品来源”为“招标”（库房：下拉搜索）
     * @author  fenghui
     * @date    2017年8月11日
     * 
     * @param   searchName          模糊搜索关键字 
     * @param   levels              库房级别（01:一级库房、02:二级库房、03:三级库房）
     * @param   sourceTpyes         货物来源方式（00:招标、01:库房、02:标准产品库）
     * @param   pagesize            每页记录数
     * @param   page                当前页码
     * @param   excludeStorageGuid  排除库房guid
     * @throws  Exception 
     * @return  List<Map<String, Object>>
     */
	@RequestMapping("searchStorageList4Source")
    @ResponseBody
    public List<Map<String, Object>> searchStorageList4Source(String scopeType,
                                                            String searchName,
                                                            String[] levels,
                                                            String[] sourceTpyes,
                                                            Integer pagesize,
                                                            Integer page,
                                                            String excludeStorageGuid,
                                                            HttpSession session,
                                                            HttpServletRequest request) throws Exception {
        Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(true, false);
        pager.setPageSize(pagesize);
        pager.setPageNum(page);
        
        pager.addQueryParam("scopeType", StringUtils.defaultIfEmpty(scopeType, "hierarchy"));
        pager.addQueryParam("orgId", (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID));//会话机构id
        pager.addQueryParam("searchName", searchName);
        pager.addQueryParam("levels", ArrayUtils.isEmpty(levels) ? new String[]{StorageLevel.TOP} : levels);
        pager.addQueryParam("sourceTpyes", ArrayUtils.isEmpty(sourceTpyes) ? new String[]{StorageSourceType.TENDER} : sourceTpyes);
        pager.addQueryParam("excludeStorageGuid", excludeStorageGuid);
        
        request.setAttribute(ResResultBindingInterceptor.IGNORE_STD_RESULT, true);//忽略标准结果
        List<Map<String, Object>> stoList = storageService.findStorageListForSelector(pager);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("value", "");
        map.put("text", "全部");
        list.add(map);
        if(stoList!=null && !stoList.isEmpty()){
            list.addAll(storageService.findStorageListForSelector(pager));
        }
        return list;
    }
	
	/**
	 * [客户中心]：查询机构科室列表（所属科室：下拉搜索）
	 * @author	黄文君
	 * @date	2017年4月24日 下午4:24:57
	 * 
	 * @param	searchName		模糊搜索关键字		
	 * @param	pagesize		每页记录数
	 * @param	page			当前页码
	 * @throws	Exception 
	 * @return	List<Map<String, Object>>
	 */
	@RequestMapping("searchDeptList")
	@ResponseBody
	public List<Map<String, Object>> searchDeptList(Long orgId,
	                                                String searchName,
													Integer pagesize,
													Integer page,
													HttpSession session,
													HttpServletRequest request) throws Exception {
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(true, false);
		pager.setPageSize(pagesize);
		pager.setPageNum(page);
		
		pager.addQueryParam("orgId", orgId==null?(Long)session.getAttribute(LoginUser.SESSION_USER_ORGID):orgId);
		pager.addQueryParam("searchName", searchName);
		
		request.setAttribute(ResResultBindingInterceptor.IGNORE_STD_RESULT, true);//忽略标准结果
		return storageService.findDeptListForSelector(pager);
	}

	/**
	 * [客户中心]：查询指定库房的开放科室列表（联想下拉搜索）
	 * @author	黄文君
	 * @date	2017年10月17日
	 * @param	storageId		库房id
	 * @param	searchName		模糊搜索关键字
	 * @param	pagesize		每页记录数
	 * @param	page			当前页码
	 * @throws	Exception
	 * @return	List<Map<String, Object>>
	 */
	@RequestMapping("searchOpenDeptsByStorageId")
	@ResponseBody
	public List<Map<String, Object>> queryOpenDeptsByStorageId(String storageId,
	                                                String searchName,
	                                                Integer pagesize,
	                                                Integer page,
	                                                HttpServletRequest request) throws Exception {
		LocalAssert.notBlank(storageId, "库房id，不能为空");

		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(true, false);
		pager.setPageSize(pagesize);
		pager.setPageNum(page);
		pager.addQueryParam("storageId", storageId);
		pager.addQueryParam("searchName", searchName);
		request.setAttribute(ResResultBindingInterceptor.IGNORE_STD_RESULT, true);//忽略标准结果
		return storageService.searchOpenDeptsByStorageId(pager);
	}

	/**
	 * 检查: 判断指定库房是否已经发生业务数据
	 * @author	黄文君
	 * @date	2017年4月28日 下午5:09:06
	 * @param	origin			修改前库房信息
	 * @param	newStorage		修改后库房信息
	 * @throws	ValidationException
	 * @return	void
	 */
	private void assertServiceAssoOfStorageGuid(StorageInfo origin, StorageInfo newStorage) throws ValidationException {
		//已经开展业务发生数据的库房，不能修改：所属机构、库房编号、类型、级别、货物来源方式及来源库房
		if(storageService.assertServiceAssoByStorageGuid(origin.getStorageGuid())){
			if(ObjectUtils.notEqual(origin.getOrgId(), newStorage.getOrgId())){
				throw new ValidationException("该库房已经发生业务数据，不能修改：所属机构");
			}
			if(ObjectUtils.notEqual(origin.getStorageCode(), newStorage.getStorageCode())){
				throw new ValidationException("该库房已经发生业务数据，不能修改：库房编号");
			}
			if(ObjectUtils.notEqual(origin.getStorageFtypeCode(), newStorage.getStorageFtypeCode())){
				throw new ValidationException("该库房已经发生业务数据，不能修改：库房类型");
			}
			if(ObjectUtils.notEqual(origin.getStorageLevelCode(), newStorage.getStorageLevelCode())){
				throw new ValidationException("该库房已经发生业务数据，不能修改：库房级别");
			}
			if(ObjectUtils.notEqual(origin.getStorageSourceType(), newStorage.getStorageSourceType())){
				throw new ValidationException("该库房已经发生业务数据，不能修改：货物来源方式");
			}
			if(ObjectUtils.notEqual(origin.getSourceStorageGuid(), newStorage.getSourceStorageGuid())){
				throw new ValidationException("该库房已经发生业务数据，不能修改：货物来源库房");
			}
		}
	}
	
	/**
	 * 检查: 货物来源库房，必须与所属机构是一个体系（即两个不相干的医院不允许指定为来源库房）
	 * @author	黄文君
	 * @date	2017年5月3日 上午9:25:24
	 * @author	黄文君
	 * @date	2017年5月3日 上午11:32:06
	 * @param	orgId					库房所属机构id
	 * @param	storageGuid				库房guid
	 * @param	sourceStorageGuid		货物来源库房guid
	 * @param	storageLevel			库房级别
	 * @throws	ValidationException
	 * @return	void
	 */
	private void assertRelationForOrgAndRefStorage(Long orgId, String storageGuid, String sourceStorageGuid, String storageLevel) throws ValidationException {
		//检查: 货物来源库房，不能选择自已
		LocalAssert.notEquals(storageGuid, sourceStorageGuid, "货物来源库房，不能选择自已");
		//检查: 货物来源库房，必须与所属机构是一个体系（即两个不相干的医院不允许指定为来源库房）
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(false);
		pager.addQueryParam("orgId", orgId);
		pager.addQueryParam("storageGuidOfFind", sourceStorageGuid);
		pager.addQueryParam("excludeStorageGuid", storageGuid);
		if(storageLevel.equals(StorageLevel.TOP)){
			pager.addQueryParam("scopeType", "hierarchy");
			pager.addQueryParam("levels", new String[]{StorageLevel.TOP});
			pager.addQueryParam("sourceTypes", new String[]{StorageSourceType.TENDER});
		}else{
			pager.addQueryParam("levels", new String[]{StorageLevel.TOP, StorageLevel.SECOND});
			pager.addQueryParam("sourceTypes", new String[]{StorageSourceType.TENDER, StorageSourceType.REF_STORAGE});
		}
		if(CollectionUtils.isEmpty(storageService.findStorageListForSelector(pager))){
			throw new ValidationException("货物来源库房，必须与所属机构是一个体系");
		}
	}
	
	/**
	 * 【库房产品】查询当前登录用户所有的库房列表（下拉框）
	 */
	@RequestMapping("findStorageByUser")
	@ResponseBody
	public List<Map<String, Object>> findStorageByUser(String searchName,HttpSession session) {
		Pager pager = new Pager(false);
		
		pager.addQueryParam("userId", (String)session.getAttribute(LoginUser.SESSION_USERID));
		pager.addQueryParam("orgId", (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID));
		pager.addQueryParam("searchName", searchName);
		
		return storageService.findStorageByUser(pager);
	}
	
	/**
     * 【库房】查询某一级库房下所有的子库房信息（下拉框）
	 * @throws ValidationException 
     */
    @RequestMapping("findStorageByTop")
    @ResponseBody
    public List<Map<String, Object>> findStorageByTop(String storageGuid,String searchName,HttpSession session) {
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Pager pager = new Pager(false);
        pager.addQueryParam("searchParams", searchName);
        if(StringUtils.isNotBlank(storageGuid)){
            pager.addQueryParam("storageGuid", storageGuid);
            list = storageService.findStorageByTop(pager);
        }else{
            pager.addQueryParam("userId", (String)session.getAttribute(LoginUser.SESSION_USERID));
            pager.addQueryParam("orgId", (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID));
            pager.addQueryParam("level", CustomConst.StorageLevel.TOP);
            List<Map<String, Object>> topStorageList = storageService.findTopStorageByUser(pager);
            if(CollectionUtils.isNotEmpty(topStorageList) && topStorageList.get(0).get("value")!=null 
                    && StringUtils.isNotBlank((String)topStorageList.get(0).get("value"))){
                pager.addQueryParam("storageGuid", (String)topStorageList.get(0).get("value"));
                list = storageService.findStorageByTop(pager);
            }
        }
        return list;
    }
	
	/**
     * 【库房产品】查询当前登录用户所有的一级库房列表（下拉框）
     */
    @RequestMapping("findTopStorageByUser")
    @ResponseBody
    public List<Map<String, Object>> findTopStorageByUser(String searchName,HttpSession session) {
        Pager pager = new Pager(false);
        
        pager.addQueryParam("userId", (String)session.getAttribute(LoginUser.SESSION_USERID));
        pager.addQueryParam("orgId", (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID));
        pager.addQueryParam("searchName", searchName);
        pager.addQueryParam("level", CustomConst.StorageLevel.TOP);
        
        return storageService.findTopStorageByUser(pager);
    }
	
	/**
	 * 【科室产品】查询当前登录的用户所属的科室对应的库房列表（下拉框）
	 */
	@RequestMapping("findStorageByUserDept")
	@ResponseBody
	public List<Map<String, Object>> findStorageByUserDept(String searchName,
			String deptGuid,
			HttpSession session) {
		Pager pager = new Pager(false);
		
		pager.addQueryParam("userId", (String)session.getAttribute(LoginUser.SESSION_USERID));
		pager.addQueryParam("orgId", (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID));
		pager.addQueryParam("searchParams", searchName);
		pager.addQueryParam("deptGuid", deptGuid);
		
		return storageService.findStorageByUserDept(pager);
	}
	
	/**
	 * 库房数据项长度检查
	 * @author	黄文君
	 * @date	2017年4月25日 下午1:36:03
	 * @return	void
	 * @throws	ValidationException 
	 */
	private void validateFieldLength(StorageInfo storage) throws ValidationException {
		//库房名称
		if(storage.getStorageName()!=null && storage.getStorageName().length()>50){
			throw new ValidationException("库房名称，长度不能超过50个字符!");
		}
		//库房编号
		if(storage.getStorageCode()!=null && storage.getStorageCode().length()>10){
			throw new ValidationException("库房编号，长度不能超过10个字符!");
		}
		
		//库房备注
		if(storage.getTfRemark()!=null && storage.getTfRemark().length()>100){
			throw new ValidationException("库房备注，长度不能超过100个字符!");
		}
	}
	
	/**
	 * 查询指定库房的地址列表（下拉列表：联想搜索）
	 * @author	黄文君
	 * @date	2017年6月14日 上午11:38:01
	 * @param	storageGuid		库房guid
	 * @param	searchName		模糊搜索关键字
	 * @throws	Exception
	 * @return	List<Map<String, Object>>
	 */
	@RequestMapping("searchAddrListByStorageGuid")
	@ResponseBody
	public List<Map<String, Object>> searchAddrListByStorageGuid(String storageGuid, String searchName) throws Exception {
		LocalAssert.notBlank(storageGuid, "storageGuid，不能为空");
		//task6 ed 查询指定库房的地址列表（下拉列表：联想搜索）
		return storageService.searchAddrsByStorageGuid(storageGuid, searchName);
	}
	
	/**
	 * 库房添加联系地址
	 * @author	黄文君
	 * @date	2017年4月25日 上午10:36:16
	 * @param	storageGuid			库房guid
	 * @param	addresses			地址列表
	 * @throws	Exception
	 * @return	void
	 */
	@RequestMapping("addAddrsByStorageGuid")
	@ResponseBody
	public String addAddrsByStorageGuid(String storageGuid, Address address) throws Exception {
		LocalAssert.notBlank(storageGuid, "storageGuid，不能为空");
		Assert.notNull(address, "地址信息，不能为空");
		
		if(address.getTfAddress().length()>50){
			throw new ValidationException("库房地址，长度不能超过50个字符!");
		}
		LocalAssert.notBlank(address.getLinkman(), "联系人，不能为空");
		if(address.getLinkman().length()>25){
			throw new ValidationException("联系人，长度不能超过25个字符!");
		}
		LocalAssert.notBlank(address.getLinktel(), "联系电话，不能为空");
		if(address.getLinktel().length()>25){
			throw new ValidationException("联系电话，长度不能超过25个字符!");
		}
		if(StringUtils.isEmpty(address.getIsDefault())){
			address.setIsDefault(YesOrNo.NO);
		}
		
		address.setAddrGuid(IdentifieUtil.getGuId());
		address.setTfCloGuid(storageGuid);//所属guid
		address.setAddrTable("TD_STORAGE_INFO");
		//task6 ed 库房添加联系地址
		storageService.insertAddress(address);
		return address.getAddrGuid();
	}
	
	@ResponseBody
	@RequestMapping("/deleteOrgStorage")
	public void deleteOrgStorage(String storageGuid, HttpServletRequest request) throws ValidationException {
		LocalAssert.notBlank(storageGuid, "请选择要删除的库房");
		storageService.deleteOrgStorage(storageGuid);
	}
	
	/**
	 * 查询当前登录用户机构下的所有库房
	 * @return
	 * @throws ValidationException 
	 */
	@ResponseBody
	@RequestMapping("/selectLoginOrgStorage")
	public List<Map<String, Object>> selectLoginOrgStorage(
			@RequestParam( value="orgId",required=false) Long orgId, 
			HttpServletRequest request,HttpSession session) throws ValidationException {
		String sessionOrgType = (String)session.getAttribute(LoginUser.SESSION_USER_ORG_TYPE);
		//如果是运营商（服务商）的用户，就可以进行配置!!
		if (OrgType.PLATFORM.equals(sessionOrgType) && orgId == null) {
			throw new ValidationException("当前没有选择机构");
		}
		if(!OrgType.PLATFORM.equals(sessionOrgType)){
			orgId = Long.valueOf(session.getAttribute(LoginUser.SESSION_USER_ORGID).toString());
		}
		Pager pager = new Pager(false);

		pager.addQueryParam("orgId", orgId);
		request.setAttribute(ResResultBindingInterceptor.IGNORE_STD_RESULT, true);//忽略标准结果

		return storageService.selectLoginOrgStorage(pager);
	}
}

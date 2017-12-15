package com.phxl.ysy.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phxl.core.base.annotation.RequiredPermission;
import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.interceptor.ResResultBindingInterceptor;
import com.phxl.core.base.util.IdentifieUtil;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.ysy.entity.OrgInfo;
import com.phxl.ysy.entity.SourceInfo;
import com.phxl.ysy.service.impl.SourceInfoServiceImpl;
import com.phxl.ysy.constant.CustomConst.Fstate;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.constant.CustomConst.OrgType;

/**
 * 【供应商管理】（医院）接口: 我的供应商
 * @date	2017年3月23日 下午2:08:41
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
@Controller
@RequestMapping("source")
public class SourceInfoController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SourceInfoServiceImpl sourceInfoService;
	
	/**
	 * 查询跟本机构没有供需关系的供应商的列表（没有供需关系）
	 * @author	黄文君
	 * @date	2017年3月23日 下午5:41:49
	 * @param	searchName		模糊搜索关键字（供应商名称、简称、简码）
	 * @param	pagesize		每页记录数
	 * @param	page			当前页码
	 * @param	sidx			排序字段名称
	 * @param	sord			排序方式（desc/descend：降序； asc/ascend：升序）
	 * @return	Pager<Map<String, Object>>
	 */
	@RequestMapping("findUnsourceOrgList")
	@ResponseBody
	public Pager<Map<String, Object>> findUnsourceOrgList(String searchName, 
											  Integer pagesize, 
											  Integer page, 
											  String sidx, 
											  String sord,
											  HttpSession session){
		
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>();
		pager.setPageSize(pagesize);
		pager.setPageNum(page);
		
		pager.addQueryParam("searchName", searchName);
		pager.addQueryParam("sessionOrgId", session.getAttribute(LoginUser.SESSION_USER_ORGID));
		
		if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)){
			pager.addQueryParam("resultMapName", "com.phxl.ysy.basicDataModule.dao.OrgInfoMapper.BaseResultMap");//设置ResultMap映射关系
			pager.addQueryParam("orderField", sidx);
			pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sord)?"asc":"desc");
		}
		pager.setRows(sourceInfoService.findUnsourceOrgList(pager));
		return pager;
	}

	/**
	 * 查询当前机构的供应商列表
	 * @author	黄文君
	 * @date	2017年3月23日 下午5:41:49
	 * @param	orgName			供应商名称		
	 * @param	orgAlias		简称		
	 * @param	fqun			简码		
	 * @param	supplierCode	供应商编码		
	 * @param	storageId		库房编号		
	 * @param	pagesize		每页记录数		
	 * @param	page			当前页码		
	 * @param	sidx			排序字段名称
	 * @param	sord			排序方式（desc/descend：降序； asc/ascend：升序）
	 * @return	Pager<OrgInfo>
	 */
	@RequestMapping("findMySupplierList")
	@ResponseBody
	public Pager<Map<String, Object>> findMySupplierList(String orgName, 
														 String orgAlias, 
														 String fqun, 
														 String supplierCode, 
														 String storageId, 
														 Integer pagesize, 
														 Integer page, 
														 String sidx, 
														 String sord,
														 HttpSession session){
		
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>();
		pager.setPageSize(pagesize);
		pager.setPageNum(page);
		
		pager.addQueryParam("orgName", orgName);
		pager.addQueryParam("orgAlias", orgAlias);
		pager.addQueryParam("fqun", fqun);
		pager.addQueryParam("supplierCode", supplierCode);
		pager.addQueryParam("storageId", storageId);
		pager.addQueryParam("sessionOrgId", session.getAttribute(LoginUser.SESSION_USER_ORGID));
		
		if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)){
			pager.addQueryParam("orderField", sidx);
			pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sord)?"asc":"desc");
		}
		pager.setRows(sourceInfoService.findMySupplierList(pager));
		return pager;
	}
	
	/**
	 * 
	 * findMySourceInfoList:(根据当前需方机构查询供需关系列表). <br/> 
	 * 
	 * @Title: findMySourceInfoList
	 * @Description: TODO
	 * @param searchName
	 * @param storageGuid
	 * @param pagesize
	 * @param pageNum
	 * @param sortField
	 * @param sortMark
	 * @param session
	 * @return    设定参数
	 * @return Pager<Map<String,Object>>    返回类型
	 * @throws
	 */
	@RequestMapping("findMySourceInfoList")
    @ResponseBody
    public Pager<Map<String, Object>> findMySourceInfoList(String searchName, 
                                                         String storageGuid, 
                                                         Integer pagesize, 
                                                         Integer pageNum, 
                                                         String sortField, 
                                                         String sortMark,
                                                         HttpSession session){
        
        Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>();
        pager.setPageSize(pagesize);
        pager.setPageNum(pageNum);
        
        pager.addQueryParam("searchName", searchName);
        pager.addQueryParam("storageGuid", storageGuid);
        pager.addQueryParam("sessionOrgId", session.getAttribute(LoginUser.SESSION_USER_ORGID));
        
        if(StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortMark)){
            pager.addQueryParam("orderField", sortField);
            pager.addQueryParam("orderMark", "ascend".equalsIgnoreCase(sortMark)?"asc":"desc");
        }
        pager.setRows(sourceInfoService.findMySourceInfoList(pager));
        return pager;
    }
	
	/**
	 * 查询我的指定供应商信息
	 * @author	黄文君
	 * @date	2017年3月23日 下午5:50:15
	 * @param	sourceId
	 * @return	Map<String,Object>
	 * @throws	ValidationException  
	 */
	@RequestMapping("findMySupplierBySourceId")
	@ResponseBody
	public Map<String, Object> findMySupplierBySourceId(String sourceId, HttpSession session) throws ValidationException {
		LocalAssert.notEmpty(sourceId, "供需关系id，必需指定!");
		return sourceInfoService.findMySupplierBySourceId((Long)session.getAttribute(LoginUser.SESSION_USER_ORGID), sourceId);
	}
	
	/**
	 * 添加：我的供应商
	 * @author	黄文君
	 * @date	2017年3月23日 下午6:04:55
	 * @param	fOrgId					供应商id
	 * @param	supplierCode			供应商编码
	 * @param	tfRemark				备注
	 * @throws	ValidationException
	 * @return	void
	 */
	@RequestMapping("addMySupplier")
	@ResponseBody
	public void addMySupplier(Long fOrgId, 
							  String supplierCode, 
							  @RequestParam(value="remarks", required=false)String tfRemark, 
							  HttpSession session) throws ValidationException {
		Assert.notNull(fOrgId, "供应商id，必需指定!");
		//LocalAssert.notEmpty(storageGuid, "库房（需方），必需指定!");
		
		OrgInfo orgInfo =sourceInfoService.find(OrgInfo.class, fOrgId);
		Assert.notNull(orgInfo, "供应商（fOrgId: "+fOrgId+"）不存在!");
		if(!OrgType.SUPPLIER.equals(orgInfo.getOrgType())){
			throw new ValidationException("请求添加的机构不是供应商，不能添加!");
		}
		
		String sessionUserId = (String)session.getAttribute(LoginUser.SESSION_USERID);//会话用户id
		Long sessionOrgId = (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID);//会话机构id
		
		SourceInfo source = new SourceInfo();
		source.setrOrgId(sessionOrgId);
		source.setfOrgId(fOrgId);
		
		//唯一限制: 如果当前机构的库房storageGuid，跟fOrgId，是否已经有供需关系（不管什么状态）
		if(sourceInfoService.searchEntity(source)!=null){
			/**供需关系已存在，不能重建（重新去启用关系即可）*/
			throw new ValidationException("供需关系已存在，不能重建（重新去启用关系即可）!");
		}
		//供应商编码已存在，不能重复
		if(StringUtils.isNotEmpty(supplierCode) && sourceInfoService.existSupplierCode(sessionOrgId, supplierCode, null)>0){
			throw new ValidationException("供应商编码已存在，不能重复!");
		}
		
		source.setSourceGuid(IdentifieUtil.getGuId());
		source.setSupplierCode(supplierCode);
		source.setKingOrgId(sessionOrgId);
		source.setCreateUserid(sessionUserId);
		source.setCreateTime(new Date());
		source.setFstate(Fstate.USABLE);//状态
		source.setTfRemark(tfRemark);//备注
		
		//检查: 数据项长度
		validateLength(source);
		
		//添加我的供应商
		sourceInfoService.insertInfo(source);
	}
	
	/**
	 * 
	 * addMySourceInfo:(添加供需关系). <br/> 
	 * 
	 * @Title: addMySourceInfo
	 * @Description: TODO
	 * @param source
	 * @param isSupplierKing
	 * @param session
	 * @throws ValidationException    设定参数
	 * @return void    返回类型
	 * @throws
	 */
	@RequestMapping("addMySourceInfo")
    @ResponseBody
    public void addMySourceInfo(SourceInfo source,
        @RequestParam(value = "isSupplierKing", required = false)String isSupplierKing,
                              HttpSession session) throws ValidationException {
        Assert.notNull(source, "供需关系信息，不能为空!");
        Assert.notNull(source.getfOrgId(), "供应商，必需指定!");
        LocalAssert.notBlank(source.getrStorageGuid(), "库房（需方），必需指定!");
        LocalAssert.notBlank(source.getFstate(), "供需关系状态，必需指定!");
        LocalAssert.notBlank(isSupplierKing, "是否允许供应商取消订单，必需指定!");
        OrgInfo orgInfo =sourceInfoService.find(OrgInfo.class, source.getfOrgId());
        Assert.notNull(orgInfo, "供应商（fOrgId: "+source.getfOrgId()+"）不存在!");
        if(!OrgType.SUPPLIER.equals(orgInfo.getOrgType())){
            throw new ValidationException("请求添加的机构不是供应商，不能添加!");
        }
        
        String sessionUserId = (String)session.getAttribute(LoginUser.SESSION_USERID);//会话用户id
        Long sessionOrgId = (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID);//会话机构id
        source.setrOrgId(sessionOrgId);
        
        SourceInfo entity = new SourceInfo();
        entity.setrOrgId(source.getrOrgId());
        entity.setfOrgId(source.getfOrgId());
        entity.setrStorageGuid(source.getrStorageGuid());
        //唯一限制: 如果当前机构的库房storageGuid，跟fOrgId，是否已经有供需关系（不管什么状态）
        if(sourceInfoService.searchEntity(entity)!=null){
            /**供需关系已存在，不能重建（重新去启用关系即可）*/
            throw new ValidationException("供需关系已存在，不能重建（重新去启用关系即可）!");
        }
        //供应商编码已存在，不能重复
        if(StringUtils.isNotEmpty(source.getSupplierCode()) && sourceInfoService.existSupplierCode(sessionOrgId, source.getSupplierCode(), null)>0){
            throw new ValidationException("供应商编码已存在，不能重复!");
        }
        
        source.setSourceGuid(IdentifieUtil.getGuId());
        source.setKingOrgId((StringUtils.isNotBlank(isSupplierKing)&&"1".equals(isSupplierKing)) ? source.getfOrgId() : source.getrOrgId());
        source.setCreateUserid(sessionUserId);
        source.setCreateTime(new Date());
        
        //检查: 数据项长度
        validateLength(source);
        
        //添加我的供应商
        sourceInfoService.insertInfo(source);
    }
	
	/**
	 * 修改：我的供应商的附属信息
	 * @author	黄文君
	 * @date	2017年3月23日 下午6:04:55
	 * @param	sourceId				供需关系id
	 * @param	supplierCode			供应商编码
	 * @param	tfRemark				状态
	 * @param	remarks					备注
	 * @throws	ValidationException
	 * @return	void
	 */
	@RequestMapping("modifyMySupplier")
	@ResponseBody
	public void modifyMySupplier(String sourceId, 
								 String supplierCode, 
								 String fstate, 
								 @RequestParam(value="remarks", required=false)String tfRemark, 
								 HttpSession session) throws ValidationException {
		LocalAssert.notEmpty(sourceId, "供需关系id，必需指定!");
		LocalAssert.notEmpty(fstate, "状态，必需指定!");
		LocalAssert.contain(new String[]{Fstate.USABLE, Fstate.DISABLE, Fstate.REMOVED}, fstate, "未知的状态值!fstate="+fstate);
		
		SourceInfo origin = sourceInfoService.find(SourceInfo.class, sourceId);
		Assert.notNull(origin, "供需关系（sourceId: "+sourceId+"）不存在!");
		
		/*
		 *  待确认：已经添加的供应商，供需关系中： 供应商id、库房Guid，是不是定了不能改；只能改：供应商编码、状态。
		 */
		String sessionUserId = (String)session.getAttribute(LoginUser.SESSION_USERID);
		Long sessionUserOrgId = (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID);
		if(sessionUserOrgId.longValue() != origin.getrOrgId()){
			throw new ValidationException("您无权更新其他机构的供需关系的信息!");
		}
		
		//供应商编码已存在，不能重复
		if(StringUtils.isNotEmpty(supplierCode) && !supplierCode.equals(origin.getSupplierCode()) && 
				sourceInfoService.existSupplierCode(origin.getrOrgId(), supplierCode, origin.getSourceGuid())>0){
			throw new ValidationException("供应商编码已存在，不能重复!");
		}
				
		if(!origin.getFstate().equals(fstate) && Fstate.REMOVED.equals(fstate)){
			//业务校验: 是否可以停用或移除指定的供需关系
			sourceInfoService.whetherCastOffSourceInfo(origin);
		}
		
		origin.setSupplierCode(supplierCode);
		origin.setFstate(fstate);
		origin.setModifyUserid(sessionUserId);
		origin.setModifyTime(new Date());
		origin.setTfRemark(tfRemark);
		
		//修改：我的供应商的附属信息
		sourceInfoService.updateInfoCover(origin);
	}
	
	/**
	 * 
	 * modifyMySourceInfo:(编辑供应关系). <br/> 
	 * 
	 * @Title: modifyMySourceInfo
	 * @Description: TODO
	 * @param source
	 * @param isSupplierKing
	 * @param session
	 * @throws ValidationException    设定参数
	 * @return void    返回类型
	 * @throws
	 */
	@RequestMapping("modifyMySourceInfo")
    @ResponseBody
    public void modifyMySourceInfo(SourceInfo source,
        @RequestParam(value = "isSupplierKing", required = false)String isSupplierKing,
                              HttpSession session) throws ValidationException {
        Assert.notNull(source, "供需关系信息，不能为空!");
        LocalAssert.notBlank(source.getSourceGuid(), "供需关系主键，不能为空!");
        LocalAssert.notBlank(source.getFstate(), "供需关系状态，必需指定!");
        LocalAssert.notBlank(isSupplierKing, "是否允许供应商取消订单，必需指定!");
        
        String sessionUserId = (String)session.getAttribute(LoginUser.SESSION_USERID);//会话用户id
        Long sessionOrgId = (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID);//会话机构id
        
        SourceInfo origin = sourceInfoService.find(SourceInfo.class, source.getSourceGuid());
        Assert.notNull(origin, "供需关系（sourceId: "+source.getSourceGuid()+"）不存在!");
        if(sessionOrgId.longValue() != origin.getrOrgId()){
            throw new ValidationException("您无权更新其他机构的供需关系的信息!");
        }
        LocalAssert.notBlank(origin.getrStorageGuid(), "库房（需方），必需指定!");
        //供应商编码已存在，不能重复
        if(StringUtils.isNotEmpty(source.getSupplierCode()) && !source.getSupplierCode().equals(origin.getSupplierCode()) && 
                sourceInfoService.existSupplierCode(origin.getrOrgId(), source.getSupplierCode(), origin.getSourceGuid())>0){
            throw new ValidationException("供应商编码已存在，不能重复!");
        }
        
        origin.setSupplierCode(source.getSupplierCode());
        origin.setFstate(source.getFstate());
        origin.setKingOrgId((StringUtils.isNotBlank(isSupplierKing)&&"1".equals(isSupplierKing)) ? origin.getfOrgId() : origin.getrOrgId());
        origin.setLxr(source.getLxr());
        origin.setLxdh(source.getLxdh());
        origin.setModifyUserid(sessionUserId);
        origin.setModifyTime(new Date());
        
        //检查: 数据项长度
        validateLength(origin);
        
        //添加我的供应商
        sourceInfoService.updateInfoCover(origin);
    }
	
	/**
	 * 更新供需关系状态（01启用、00停用、02移除）
	 * @author	黄文君
	 * @date	2017年3月23日 下午6:11:11
	 * @param	sourceId
	 * @param	fstate
	 * @throws	ValidationException 
	 * @return	void
	 */
	@RequestMapping("updateFstate")
	@ResponseBody
	public void updateFstate(String sourceId, String fstate, HttpSession session) throws ValidationException{
		LocalAssert.notEmpty(sourceId, "供需关系id，必需指定!");
		LocalAssert.notEmpty(fstate, "状态，必需指定!");
		LocalAssert.contain(new String[]{Fstate.USABLE, Fstate.DISABLE, Fstate.REMOVED}, fstate, "未知的状态值! fstate="+fstate);
		
		SourceInfo origin = sourceInfoService.find(SourceInfo.class, sourceId);
		Assert.notNull(origin, "供需关系（sourceId: "+sourceId+"）不存在!");
		
		String sessionUserId = (String)session.getAttribute(LoginUser.SESSION_USERID);
		Long sessionUserOrgId = (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID);
		
		if(sessionUserOrgId.longValue() != origin.getrOrgId()){
			throw new ValidationException("您无权更新其他机构的供需关系的状态!");
		}
		
		SourceInfo si = new SourceInfo();
		si.setSourceGuid(sourceId);
		si.setFstate(fstate);
		si.setModifyUserid(sessionUserId);
		si.setModifyTime(new Date());
		
		//更新供需关系的状态
		sourceInfoService.updateInfo(si);
	}
	
	/**
	 * 检查: 数据项长度
	 * @author	黄文君
	 * @date	2017年4月12日 下午6:52:13
	 * @param	si
	 * @throws	ValidationException
	 * @return	void
	 */
	private void validateLength(SourceInfo si) throws ValidationException {
		if(si.getSupplierCode()!=null && si.getSupplierCode().length()>15){
			throw new ValidationException("供应商编码，长度不能超过15个字符!");
		}
		if(si.getTfRemark()!=null && si.getTfRemark().length()>200){
			throw new ValidationException("备注，长度不能超过200个字符!");
		}
		if(si.getLxr()!=null && si.getLxr().length()>25){
            throw new ValidationException("联系人，长度不能超过25个字符!");
        }
		if(si.getLxdh()!=null && si.getLxdh().length()>25){
            throw new ValidationException("联系电话，长度不能超过25个字符!");
        }
	}
	
	/**
	 * 查询当前机构的医院列表
	 * @author	taoyou
	 * @param	searchParams    查询参数
	 * @param	pageSize		每页记录数		
	 * @param	pageNum			当前页码		
	 * @param	sortField		排序字段名称		
	 * @param	sortMark		排序方式（desc：降序、asc：升序）
	 * @return	Pager<OrgInfo>
	 */
	@RequiredPermission("customer")
	@RequestMapping("findMyHospitalList")
	@ResponseBody
	public Pager<Map<String, Object>> findMyHospitalList(String searchParams, 
														 Integer pagesize, 
														 Integer page, 
														 String sortField, 
														 String sortMark,
														 HttpSession session){
		
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>();
		pager.setPageSize(pagesize);
		pager.setPageNum(page);
		
		pager.addQueryParam("searchParams", searchParams);
		pager.addQueryParam("sessionOrgId", session.getAttribute(LoginUser.SESSION_USER_ORGID));

		if(StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortMark)){
			pager.addQueryParam("orderField", sortField);
			pager.addQueryParam("orderMark", sortMark);
		}
		pager.setRows(sourceInfoService.findMyHospitalList(pager));
		return pager;
	}
	
	/**
	 * 【采购模块】登录机构作为需方时对应的供应商列表（下拉框）
	 */
	@RequestMapping("findFListForSelect")
	@ResponseBody
	public List<Map<String, Object>> findFListForSelect(String searchName,HttpServletRequest request) {
	    request.setAttribute(ResResultBindingInterceptor.IGNORE_STD_RESULT, true);//忽略标准结果
		Pager pager = new Pager(false);
		
		pager.addQueryParam("rOrgId", (Long)request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID));
		pager.addQueryParam("searchName", searchName);
		
		return sourceInfoService.findSourceListForSelect(pager);
	}
	
	/**
	 *  【销售模块】登录机构作为供方时对应的需方列表（下拉框）
	 */
	@RequestMapping("findRListForSelect")
	@ResponseBody
	public List<Map<String, Object>> findRListForSelect(String searchName,HttpServletRequest request) {
	    request.setAttribute(ResResultBindingInterceptor.IGNORE_STD_RESULT, true);//忽略标准结果
		Pager pager = new Pager(false);
		
		pager.addQueryParam("fOrgId", (Long)request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID));
		pager.addQueryParam("searchName", searchName);
		
		return sourceInfoService.findSourceListForSelect(pager);
	}
	
	/**
	 * 查询供方的对应供需关系中的需求库房
	 */
	@RequestMapping("queryRSourceStorageForSelect")
	@ResponseBody
	public List<Map<String, Object>> queryRSourceStorageForSelect(String searchName,HttpServletRequest request) {
	    request.setAttribute(ResResultBindingInterceptor.IGNORE_STD_RESULT, true);//忽略标准结果
		Pager pager = new Pager(false);
		
		pager.addQueryParam("fOrgId", (Long)request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID));
		pager.addQueryParam("searchName", searchName);
		
		return sourceInfoService.queryRSourceStorageForSelect(pager);
	}
	
}

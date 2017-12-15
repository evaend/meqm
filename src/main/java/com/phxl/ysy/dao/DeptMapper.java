package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.Dept;
import com.phxl.ysy.entity.UserInfo;

public interface DeptMapper {

    /**
     * 
     * searchDeptInfoList:(根据当前机构查询科室列表). <br/> 
     * 
     * @Title: searchDeptInfoList
     * @Description: TODO
     * @param pager
     * @return    设定参数
     * @return List<Map>    返回类型
     * @throws
     */
    List<Map> searchDeptInfoList(Pager pager);

    /**
     * 
     * searchDeptAddress:(查询科室地址). <br/> 
     * 
     * @Title: searchDeptAddress
     * @Description: TODO
     * @param pager
     * @return    设定参数
     * @return List<Map<String,Object>>    返回类型
     * @throws
     */
    List<Map<String, Object>> searchDeptAddress(Pager pager);
    
    /**
     * 
     * deleteDeptLocations:(根据科室GUID删除科室地址). <br/> 
     * 
     * @Title: deleteDeptLocations
     * @Description: TODO
     * @param deptGuid
     * @return    设定参数
     * @return int    返回类型
     * @throws
     */
    int deleteDeptLocations(@Param("deptGuid")String deptGuid);
	
	/**
	 * 查询科室列表（联想下拉搜索）
	 * @author	黄文君
	 * @date	2017年4月26日 上午11:58:07
	 * @param	pager
	 * @return	List<Map<String,Object>>
	 */
	List<Map<String, Object>> findDeptListForSelector(Pager<?> pager);

	/**
	 * 
	 * findDeptCodeExist:(验证同一机构内科室编码是否为空). <br/> 
	 * 
	 * @Title: findDeptCodeExist
	 * @Description: TODO
	 * @param deptCode
	 * @param sessionOrgId
	 * @return    设定参数
	 * @return int    返回类型
	 * @throws
	 */
    int findDeptCodeExist(@Param("deptCode")String deptCode, @Param("sessionOrgId")Long sessionOrgId);

    /**
     * 
     * searchDeptUsers:(查询机构用户信息). <br/> 
     * 
     * @Title: searchDeptUsers
     * @Description: TODO
     * @param sessionOrgId
     * @param deptGuid
     * @return    设定参数
     * @return List<UserInfo>    返回类型
     * @throws
     */
    List<UserInfo> searchDeptUsers(@Param("sessionOrgId")Long sessionOrgId, @Param("deptGuid")String deptGuid);

    /**
     * 
     * deleteDeptUsers:(根据科室主键删除科室用户信息). <br/> 
     * 
     * @Title: deleteDeptUsers
     * @Description: TODO
     * @param deptGuid
     * @param sessionOrgId
     * @return    设定参数
     * @return int    返回类型
     * @throws
     */
    int deleteDeptUsers(@Param("deptGuid")String deptGuid);


	List<Map<String, Object>> findDeptsByUser(Pager pager);

    /**
     * 
     * deleteDeptAddressById:(根据addrGuidList，删除多余的科室地址). <br/> 
     * 
     * @Title: deleteDeptAddressById
     * @Description: TODO
     * @param addrGuidList
     * @param deptGuid
     * @return    设定参数
     * @return int    返回类型
     * @throws
     */
    int deleteDeptAddressById(@Param("deptGuid")String deptGuid, @Param("addrGuidList")List<String> addrGuidList);

	List<Map<String, Object>> searchAddrListByDeptGuid(@Param("deptGuid")String deptGuid,@Param("searchName") String searchName);

	/**
	 * 查询当前登录用户的机构下所有的科室
	 * @param orgId 机构ID
	 * @return 科室ID，科室名称
	 */
	List<Map<String, Object>> selectLoginOrgDept(Pager pager);
	
	/**
	 * 查询带参数的科室
	 */
	Dept findDeptWithConfig(String deptGuid);
	
}
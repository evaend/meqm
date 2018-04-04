package com.phxl.ysy.service;


import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.IBaseService;
import com.phxl.ysy.entity.UserInfo;
import com.phxl.ysy.entity.WecatRegister;
import com.phxl.ysy.entity.WeixinOpenUser;

public interface UserService extends IBaseService {
	
	/**
     * 
     * findUserNoExist:(验证用户登录账号的唯一性,根据用户信息表和待审核的用户注册表验证). <br/> 
     * 
     * @Title: findUserNoExist
     * @Description: TODO
     * @param userNo
     * @return    设定参数
     * @return int    返回类型
     * @throws
     */
    int findUserNoExist(String userNo);

	/**
	 * 查询机构用户列表
	 * @author	黄文君
	 * @date	2017年3月24日 下午8:20:36
	 * @param	pager
	 * @return	List<UserInfo>
	 */
	public List<UserInfo> findOrgUserList(Pager pager);

	/**
	 * 根据用户id查询用户信息
	 * @author	黄文君
	 * @date	2017年4月6日 下午2:02:45
	 * @param userId
	 * @return
	 * @return	UserInfo
	 */
	public UserInfo findUserInfoById(String userId);
	
	/**
	 * 查看指定机构的机构类型
	 * @author	黄文君
	 * @date	2017年3月24日 下午4:55:22
	 * @param	userId
	 * @return	String
	 */
	public String findOrgTypeByOrgId(Long orgId);
	
	/**
	 * 查询还没有机构管理员的机构列表
	 * @author	黄文君
	 * @date	2017年3月29日 下午5:21:26
	 * @return	List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findWithoutAdminOrgList(Pager pager);
	
	/**
	 * 查询用户名（登录名）是否存在
	 * @author	黄文君
	 * @date	2017年3月24日 下午8:13:19
	 * @param	userno
	 * @param	excludeUserId
	 * @return	boolean
	 */
	public boolean existedUserno(String userno, String excludeUserId);
	
	/**
	 * 判断指定机构是否运营商（服务商）
	 * @author	黄文君
	 * @date	2017年3月24日 下午9:06:35
	 * @param	orgId
	 * @return	boolean
	 */
	public boolean assertServiceOrgByOrgId(Long orgId);
	
	/**
	 * 判断用户登录状态，并获取信息
	 * @param userNo
	 * @param pwd
	 * @param token
	 * @throws JsonProcessingException 
	 */
    public Map<String,Object> checkLoginInfo(String userNo, String pwd,String token) throws JsonProcessingException;
    
    /**
     * 微信自动登录
     * @param userNo 用户No
     * @param openId 微信的openId
     * @return
     * @throws JsonProcessingException
     */
    public Map<String,Object> weixinLogin(String userNo,String openId) throws JsonProcessingException;
    
    /**
	 * 查看机构的状态
	 */
	public String findOrgFstateByOrgId(Long orgId);
    
    /**
     * 查询用户权限模块菜单信息，拼接成菜单树
     * @param userId
     */
    public List<Map<String,Object>> selectUserMenu(String userId);

    /**
	 * 添加用户
	 * @author	黄文君
	 * @date	2017年3月30日 上午9:12:26
	 * @param	user
	 * @return	void
	 * @throws	ValidationException 
	 */
	public abstract void addUser(UserInfo user) throws ValidationException;
   
	/**
	 * 微信扫码自动注册
	 * @param wecatRegister
	 * @param weixinOpenUser
	 */
	public String insertWxUser(WecatRegister wecatRegister, WeixinOpenUser wxUser)throws Exception ;

	/**
	 * 查询移动端用户权限
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> selectWeiXinUserMenu(String userId);
}

package com.phxl.ysy.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.core.base.util.BaseUtils;
import com.phxl.core.base.util.IdentifieUtil;
import com.phxl.core.base.util.JSONUtils;
import com.phxl.core.base.util.MD5Util;
import com.phxl.core.base.util.SHAUtil;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.constant.CustomConst.GroupType;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.constant.CustomConst.UserLevel;
import com.phxl.ysy.dao.MenuMapper;
import com.phxl.ysy.dao.UserInfoMapper;
import com.phxl.ysy.entity.Group;
import com.phxl.ysy.entity.GroupUserKey;
import com.phxl.ysy.entity.OrgInfo;
import com.phxl.ysy.entity.UserInfo;
import com.phxl.ysy.entity.WecatRegister;
import com.phxl.ysy.entity.WeixinOpenUser;
import com.phxl.ysy.service.MenuService;
import com.phxl.ysy.service.UserService;

/**
 * 用户服务（隶属于机构）
 * @date	2017年3月23日 下午2:06:24
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private MenuService menuService;
	@Autowired
	HttpSession session;
	@Autowired
	MenuMapper menuMapper;

	public int findUserNoExist(String userNo) {
	    return userInfoMapper.findUserNoExist(userNo);
	}

	/**
	 * 验证用户登录
	 * @throws JsonProcessingException 
	 */
	public Map<String,Object> checkLoginInfo(String userNo, String pwd,String token) throws JsonProcessingException {
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "success";
		UserInfo userInfo = new UserInfo();
		userInfo.setUserNo(userNo);
		//由于用户账号是唯一的，所以使用账号验证用户是否存在
		UserInfo newUserInfo = this.searchEntity(userInfo);
		boolean loginSuccess = true;
		System.out.println("pwd++++++++++++++++++++++++++"+pwd);
		if(newUserInfo == null){
			loginSuccess = false;
			result = "用户名不存在";
		}else{
			String sysPwd = null;
			try{
				String[] strArray =  {newUserInfo.getPwd().toUpperCase(),token};
			    String shastr = BaseUtils.sort(strArray);//将待加密的字符组排序并组成一个字符串    
				sysPwd = SHAUtil.shaEncode(shastr);//字符串加密
				System.out.println("sysPwd++++++++++++++++++++++++"+sysPwd);
			}catch(Exception e)
			{
				return null;
			}
			if( sysPwd!= null && !pwd.toUpperCase().equals(sysPwd.toUpperCase())){
				loginSuccess = false;
				result = "用户名或者密码错误";
			}else if(!newUserInfo.getFstate().equals(CustomConst.Fstate.USABLE)){
				loginSuccess = false;
				result = "用户已停用";
			}else{//需要根据用户id查询所属机构的状态
				if(newUserInfo.getOrgId() != null){
					String orgFstate = this.findOrgFstateByOrgId(newUserInfo.getOrgId());
					if(StringUtils.isBlank(orgFstate)){
						loginSuccess = false;
						result = "用户所属机构状态未知";
					}else if(!orgFstate.equals(CustomConst.Fstate.USABLE)){
						loginSuccess = false;
						result = "用户所属机构已停用";
					}
				}else{
					loginSuccess = false;
					result = "用户所属机构未知";
				}
			}
		}
		map.put("loginStatus", loginSuccess);
		if(loginSuccess == true){
			map.put("userInfo", this.findUserInfoById(newUserInfo.getUserId()));
			//如果是默认密码，提示修改密码
			if((MD5Util.MD5Encrypt(CustomConst.DEFAULT_PASSWORD).toUpperCase()
					).equals(newUserInfo.getPwd().toUpperCase())){
				result = "您的密码是默认密码，请及时修改！";
			}
//			//查询当前用户的菜单权限
			JSONUtils json = new JSONUtils();
			map.put("menuList", json.toPrettyJson(this.selectUserMenu(newUserInfo.getUserId())));            
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 微信自动登录
	 */
	public Map<String,Object> weixinLogin(String userNo,String openId) throws JsonProcessingException {
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "success";
		UserInfo userInfo = new UserInfo();
		userInfo.setWechatOpenid(openId);
		//由于用户账号是唯一的，所以使用账号验证用户是否存在
		UserInfo newUserInfo = this.searchEntity(userInfo);
		System.out.println("newUserInfo="+newUserInfo.getUserId());
		boolean loginSuccess = true;
		if(newUserInfo == null){
			loginSuccess = false;
			result = "用户名不存在";
		}else{
			if(!newUserInfo.getFstate().equals(CustomConst.Fstate.USABLE)){
				loginSuccess = false;
				result = "用户已停用";
			}else{//需要根据用户id查询所属机构的状态
				if(newUserInfo.getOrgId() != null){
					String orgFstate = this.findOrgFstateByOrgId(newUserInfo.getOrgId());
					if(StringUtils.isBlank(orgFstate)){
						loginSuccess = false;
						result = "用户所属机构状态未知";
					}else if(!orgFstate.equals(CustomConst.Fstate.USABLE)){
						loginSuccess = false;
						result = "用户所属机构已停用";
					}
				}else{
					loginSuccess = false;
					result = "用户所属机构未知";
				}
			}
		}
		map.put("loginStatus", loginSuccess);
		if(loginSuccess == true){
			map.put("userInfo", this.findUserInfoById(newUserInfo.getUserId()));
			//如果是默认密码，提示修改密码
			if (StringUtils.isNotBlank(newUserInfo.getPwd())) {
				if((MD5Util.MD5Encrypt(CustomConst.DEFAULT_PASSWORD).toUpperCase()
						).equals(newUserInfo.getPwd().toUpperCase())){
					result = "您的密码是默认密码，请及时修改！";
				}
			}
			
			//查询当前用户的菜单权限
			JSONUtils json = new JSONUtils();
			map.put("menuList", json.toPrettyJson(this.selectUserMenu(newUserInfo.getUserId())));            
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 查询移动端用户权限
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> selectWeiXinUserMenu(String userId){
		// TODO Auto-generated method stub
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>> resultMaps = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> userMenus = userInfoMapper.selectUserMenu(userId);
		
		String [] menuIds = new String [userMenus.size()];
		for (int i = 0; i < userMenus.size(); i++) {
			Map<String, Object> map = userMenus.get(i);
			menuIds[i] = map.get("MENU_ID").toString();
		}
		
		List<Map<String,Object>> allMenus = menuMapper.searchWeiXinMenu(menuIds);//查询用户对应模块
		//将一级菜单摘出
		for (Map<String, Object> map : allMenus) {
			if (("").equals(map.get("parentId")) || map.get("parentId")==null) {
				map.put("levelr", 0);
				resultMaps.add(map);
			}else {
				map.put("levelr", 1);
			}
		}
		for (Map<String, Object> headMap : resultMaps) {
			List<Map<String, Object>> m = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map : allMenus) {
				if (("").equals(map.get("parentId")) || map.get("parentId")==null) {
				}else {
					m.add(map);
				}
			}
			//存放二级菜单的列表
			headMap.put("subMenus", m);
		}
		
		return resultMaps;
	}

	/**
	 * 查询用户功能菜单树
	 */
	public List<Map<String,Object>> selectUserMenu(String userId) {
		// TODO Auto-generated method stub
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>> resultMaps = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> allMenus = menuService.searchMenuWithLevel(null);//查询所有模块
		List<Map<String,Object>> userMenus = userInfoMapper.selectUserMenu(userId);
		//解析查询结果
		for(Map<String,Object> userMenu:userMenus){
			String menu_id = userMenu.get("MENU_ID").toString();
			Integer level = 0;
			Map<String,Object> sMenu = null;
			for(Map<String,Object> allMenu:allMenus){
				if(menu_id.equals(allMenu.get("id").toString())){
					level = Integer.valueOf(allMenu.get("level").toString());
					sMenu = allMenu;
					allMenus.remove(allMenu);
					break;
				}
			}
			resultMap = sMenu;
			//从功能权限节点往上寻找，直到到根节点为止
			while(level > 0){
				menu_id = sMenu.get("parentId").toString();
				//上级节点已经存在在拼装的结果中，直接将新节点放在父节点的submenus中
				boolean isParentExist = isMenuExist(menu_id,resultMaps,sMenu);
				if(isParentExist){
					level = 0;//该节点已经找到位置，可以退出本次循环
				}else{
					//上级节点还未封装，将该节点从所有菜单中抠出来，组装新的节点放在结果中
					for(Map<String,Object> allMenu:allMenus){
						if(menu_id.equals(allMenu.get("id").toString())){
							level = Integer.valueOf(allMenu.get("level").toString());
							sMenu = allMenu;
							List<Map<String,Object>> tmpMaps = new ArrayList<Map<String,Object>>();
							tmpMaps.add(resultMap);
							sMenu.put("subMenus", tmpMaps);
							resultMap = sMenu;
							allMenus.remove(allMenu);
							break;
						}
					}
				}
			}
			//拼到根的结点是否已在最终结果中存在，如果没有拼到根节点，可以忽略这步
			if(Integer.valueOf(sMenu.get("level").toString()).equals(0)){
				boolean isExist = isMenuExist(sMenu.get("id").toString(),resultMaps,null);
				if(!isExist){//不存在，则添加；否则不用管
					resultMaps.add(resultMap);
				}
			}
		}
		return resultMaps;
	}
	
	/**
	 * 在已拼装的结果中查询特定节点
	 * @return
	 */
	public boolean isMenuExist(String id,List<Map<String,Object>> listMaps,Map<String,Object> aMenu){
		   boolean exist = false;
		   for(Map<String,Object> listMap : listMaps){
			   List<Map<String,Object>> subMaps = null;
			   if(listMap.get("subMenus") != null){
				   subMaps = (List<Map<String,Object>>)listMap.get("subMenus");
			   }
			   if(id.equals(listMap.get("id").toString())){
				   exist = true;
				   if(subMaps != null && aMenu != null){
					   subMaps.add(aMenu);
				   }
				   break;
				}
			   if(subMaps != null){
				   exist = isMenuExist(id,subMaps,aMenu);
			   }
		   }
		   return exist;
	}
	public String getSometh() {
		// TODO Auto-generated method stub
		return "test123456";
	}
	
	/**
	 * 判断指定机构是否运营商（服务商）
	 * @author	黄文君
	 * @date	2017年3月24日 下午9:06:35
	 * @param	orgId
	 * @return	boolean
	 */
	public boolean assertServiceOrgByOrgId(Long orgId) {
		return userInfoMapper.findServiceOrgByOrgId(orgId)>0?true:false;
	}

	/**
	 * 查询机构用户列表
	 * @author	黄文君
	 * @date	2017年3月24日 下午8:20:36
	 * @param	pager
	 * @return	List<UserInfo>
	 */
	public List<UserInfo> findOrgUserList(Pager pager) {
		return userInfoMapper.findOrgUserList(pager);
	}
	
	/**
	 * 根据用户id查询用户信息
	 * @author	黄文君
	 * @date	2017年4月6日 下午2:02:45
	 * @param userId
	 * @return
	 * @return	UserInfo
	 */
	public UserInfo findUserInfoById(String userId){
		return userInfoMapper.findUserInfoById(userId);
	}

	/**
	 * 判断用户名（登录名）是否存在
	 * @date	2017年3月24日 下午9:10:07
	 * @param	userno
	 * @param	excludeUserId
	 * @return	boolean
	 */
	public boolean existedUserno(String userno, String excludeUserId) {
		return userInfoMapper.countUserno(userno, excludeUserId)>0?true:false;
	}

	/**
	 * 查看指定机构的机构类型
	 * @author	黄文君
	 * @date	2017年3月24日 下午5:00:11
	 * @param	orgId
	 * @return	String
	 */
	public String findOrgTypeByOrgId(Long orgId) {
		return userInfoMapper.findOrgTypeByOrgId(orgId);
	}


	public String findOrgFstateByOrgId(Long orgId) {
		return userInfoMapper.findOrgFstateByOrgId(orgId);
	}
	
	/**
	 * 查询还没有机构管理员的机构列表
	 * @author	黄文君
	 * @date	2017年3月29日 下午5:21:26
	 * @return	List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findWithoutAdminOrgList(Pager pager) {
		return userInfoMapper.findWithoutAdminOrgList(pager);
	}
	
	/**
	 * 添加用户
	 * @author	黄文君
	 * @date	2017年3月30日 上午9:12:26
	 * @param	user
	 * @return	void
	 * @throws	ValidationException 
	 */
	public void addUser(UserInfo user) throws ValidationException {
		if(insertInfo(user)>0){
			logger.debug("用户已添加");
			
			//如果添加的用户是机构管理员，把这个用户添加到该机构的核心组内
			if(user.getUserLevel().equals(UserLevel.ORG_ADMIN)) {
				Group group = new Group();
				group.setOrgId(user.getOrgId());
				group.setGroupType(GroupType.ORG_REF);
				List<Group> gList = super.searchList(group);
				if(CollectionUtils.isEmpty(gList)){
					throw new ValidationException("该用户的机构（orgId: "+user.getOrgId()+"）缺少核心组！");
				}else if(CollectionUtils.isNotEmpty(gList) && gList.size()>1){
					throw new ValidationException("数据错误: 该用户的机构（orgId: "+user.getOrgId()+"）有多个核心组！");
				}else{
					GroupUserKey gu = new GroupUserKey();
					gu.setGroupId( gList.get(0).getGroupId());
					gu.setUserId(user.getUserId());
					//机构的核心组添加用户
					if(super.insertInfo(gu)>0){
						logger.debug("用户已添加到本机构的核心组内");
					}
				}
			}
		}
	}

	/**
	 * 扫码自动注册--添加用户信息
	 */
	@Override
	public String insertWxUser(WecatRegister wecatRegister,
			WeixinOpenUser wxUser) throws Exception {
		String userId = IdentifieUtil.getGuId();
		UserInfo newU = new UserInfo();
		GroupUserKey groupUserKey = new GroupUserKey();
		newU.setUserId(userId);
		newU.setWechatOpenid(wxUser.getOpenUserId());
		newU.setUserName(wxUser.getUserName());
		newU.setHeadImgUrl(wxUser.getHeadimgurl());
		newU.setFstate(CustomConst.Fstate.USABLE);
		newU.setCreateTime(new Date());
		newU.setModifyTime(new Date());
		newU.setUserLevel(CustomConst.UserLevel.ORG_USER);
		//如果权限不为空，则绑定用户权限
		if (wecatRegister!=null) {
			newU.setOrgId(wecatRegister.getOrgId());
			OrgInfo orgInfo = find(OrgInfo.class, wecatRegister.getOrgId());
			newU.setOrgType(orgInfo.getOrgType());
			groupUserKey.setGroupId(wecatRegister.getGroupId());
			groupUserKey.setUserId(userId);
			insertInfo(groupUserKey);
			//如果当前用户组存在，则保存用户组信息
			Group group = find(Group.class,wecatRegister.getGroupId());
			if (group!=null) {
				session.setAttribute("getUserGroupName", group.getGroupName());
			}
		}
		insertInfo(newU);
		//查询当前用户的菜单权限
		JSONUtils json = new JSONUtils();
		session.setAttribute(LoginUser.SESSION_USERNAME, newU.getUserName());
		session.setAttribute(LoginUser.SESSION_USER_ORGID, newU.getOrgId());
		session.setAttribute(LoginUser.SESSISON_USER_LEVEL, json.toPrettyJson(this.selectUserMenu(newU.getUserId())));
		session.setAttribute(LoginUser.SESSION_USER_ORG_TYPE, newU.getOrgType());
		session.setAttribute(LoginUser.SESSION_USER_INFO, newU);
		session.setAttribute(LoginUser.SESSION_USERID, newU.getUserId());
		session.setAttribute(LoginUser.SESSION_USERNO, newU.getUserNo());
		session.setAttribute(LoginUser.SESSION_USER_ORGNAME, newU.getOrgName());
		return userId;
	}
	
}

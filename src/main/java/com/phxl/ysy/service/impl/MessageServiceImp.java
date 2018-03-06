package com.phxl.ysy.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.core.base.util.JSONUtils;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.dao.OrgDeptMapper;
import com.phxl.ysy.entity.Messagewechat;
import com.phxl.ysy.entity.RrpairOrder;
import com.phxl.ysy.entity.RrpairOrderAcce;
import com.phxl.ysy.entity.UserInfo;
import com.phxl.ysy.service.IMessageService;
import com.phxl.ysy.service.UserService;
import com.phxl.ysy.service.weixin.WeixinAPIInterface;
import com.phxl.ysy.web.weixin.AccessTokenInfo;

/**
 * 消息实现类
 */
@Service
public class MessageServiceImp extends BaseService implements IMessageService{
    
	public final static Logger logger = LoggerFactory.getLogger("message");
	@Autowired
	WeixinAPIInterface weixinAPIInterface;
	
	@Autowired
	OrgDeptMapper orgDeptMapper;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UserService userService;
	
	/**
	 * 生成微信JSON串
	 * @throws JsonProcessingException 
	 */
	public String getMessageJsonContent(Map<String, Object> argument,String messageWechatId,String url,String userid){
		String WeChatContent = "";
		JSONUtils json = new JSONUtils();
		/** 查询微信模板信息 **/
		Messagewechat mwc = this.find(Messagewechat.class, messageWechatId);
		Map<String,Object> map = new HashMap<String, Object>();
		UserInfo userInfo = this.find(UserInfo.class, userid);
		if(StringUtils.isNotBlank(userInfo.getWechatOpenid())){
			map.put("touser", userInfo.getWechatOpenid());//openid
			map.put("template_id", mwc.getMcTemplateid());//template_id
			map.put("url", url);
			//map.put("url", "");//url,做详情的，应该可以不要
			/**	需要传输的参数内容，参数key从数据库中取	 */
			Map<String,Object> mapdata = new HashMap<String, Object>();
			//假设数据库中是这样存储的
			//{{first.DATA}}单据类型：{{keyword1.DATA}}
			//单据状态：{{keyword2.DATA}}更新时间：{{keyword3.DATA}}{{remark.DATA}}
	        //从这里取出{{}}之间的参数
			String content = mwc.getMcTemplatecontent();
			String[] contentCodes = getInArgumentCode(content);
			for(String contentCode:contentCodes){
				if(argument.get(contentCode) != null){
					Map<String,Object> datadetail = new HashMap<String, Object>();
					datadetail.put("value",argument.get(contentCode).toString());
					mapdata.put(contentCode, datadetail);
				}
			}		
			map.put("data", mapdata);
			try {
				WeChatContent = json.toPrettyJson(map);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		System.out.println("WeChatContent="+WeChatContent);
		return WeChatContent;
	}
	
	/**  解析字符串获取其中存在的参数**/
	public String[] getInArgumentCode(String checkStr){
		String[] strs = checkStr.split(".DATA}}");
		String[] returnAttr = new String[strs.length];
		for(int i = 0; i < strs.length ; i++){
			returnAttr[i] = strs[i].substring(strs[i].indexOf("{{")+2);
		}
		return returnAttr;
	}
	/**
	 * 推送消息	 */
	@Override
	public void pushMessages(String WeChatContent) {
		// TODO Auto-generated method stub
		String returnCode = "0";//返回状态，微信需要返回，站内信默认是成功的
		//微信类型的、有效消息会调用这个微信发送接口
		//accessToken定时刷新，频率大概约等于2小时
		returnCode = weixinAPIInterface.sendMessage(AccessTokenInfo.accessToken.getAccessToken(), WeChatContent);		
		
	}	

	/**
	 * 推送消息，当维修单状态变更时
	 */
	@Override
	public void selectBDeptUser(String rrpairOrderGuid) {
		RrpairOrder rrpairOrder = find(RrpairOrder.class, rrpairOrderGuid);
		List<Map<String, Object>> resultMaps = new ArrayList<Map<String,Object>>();
		//获取保修员信息（推送消息）
		UserInfo user = userService.findUserInfoById(rrpairOrder.getRrpairUserid());
		resultMaps.add(getArgument(user,"syks",rrpairOrder));
    	//获取内修员信息（推送消息）
    	if (StringUtils.isNotBlank(rrpairOrder.getInRrpairUserid())) {
    		UserInfo user1 = userService.findUserInfoById(rrpairOrder.getInRrpairUserid());
    		resultMaps.add(getArgument(user1,"nxz",rrpairOrder));
		}
    	//执行推送
    	userListPush(resultMaps,rrpairOrder);
	}
	
	//执行推送
	public void userListPush(List<Map<String, Object>> resultMaps,RrpairOrder rrpairOrder){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd 24H:mm:ss");  
    	for (Map<String, Object> m : resultMaps) {
    		if (m.get("openId")==null || ("").equals(m.get("openId"))) {
				continue;
			}
			Map<String,Object> argument = new HashMap<String,Object>(); 
	        argument.put("first", "");
	        argument.put("keyword1", rrpairOrder.getRrpairOrderNo());
	        argument.put("keyword2", CustomConst.RrpairFstateMap.get(rrpairOrder.getOrderFstate()));
	        argument.put("keyword3", sdf.format(new Date()));
	        argument.put("keyword4", m.get("userName"));
	        argument.put("keyword5", "IT78922");
	        argument.put("remark", m.get("remark"));

	        String message = getMessageJsonContent(argument,
	        		"A6C68D5EFF5E4D55B5D8396CB3232DE0",m.get("url").toString(),m.get("userId").toString());
	        pushMessages(message);
		}
	}
	
	//根据不同的用户，生成不同的消息推送
	private Map<String, Object> getArgument(UserInfo user,String groupName,RrpairOrder rrpairOrder){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getUserId());
		map.put("userName", user.getUserName());
		map.put("openId", user.getWechatOpenid());
		map.put("url", "http://192.168.0.109:3001/#/check/detail/fstate="+rrpairOrder.getOrderFstate()+"?id="+rrpairOrder.getRrpairOrderGuid());
		//如果推送用户是内修组
		if (groupName.equals("nxz")) {
			if (rrpairOrder.getOrderFstate().equals(CustomConst.RrpairOrderFstate.FINISH)) {
				RrpairOrderAcce acce = new RrpairOrderAcce();
				acce.setRrpairOrder(rrpairOrder.getRrpairOrderGuid());
				RrpairOrderAcce rrpairOrderAcce = searchEntity(acce);
				if (rrpairOrderAcce==null) {
					map.put("remark", "该维修单已关闭！");
				}else if(CustomConst.RrAcceFstate.USE_PASS.equals(rrpairOrderAcce.getRrAcceFstate())){
					map.put("remark", "该维修单已验收通过，维修单已自动关闭！");
				}else if(CustomConst.RrAcceFstate.USE_NO_PASS.equals(rrpairOrderAcce.getRrAcceFstate())){
					map.put("remark", "该维修单验收不通过，请注意查看，维修单已自动关闭！");
				}
			}
		}
		//如果推送用户是使用科室
		else if (groupName.equals("syks")) {
			if (rrpairOrder.getOrderFstate().equals(CustomConst.RrpairOrderFstate.MAINTENANCE)) {
				map.put("remark", "该维修单已接修成功！");
			}else if (rrpairOrder.getOrderFstate().equals(CustomConst.RrpairOrderFstate.AWAIT_CHECK)) {
	    		map.put("remark", "该维修单已维修成功，请注意验收！");
			}else if (rrpairOrder.getOrderFstate().equals(CustomConst.RrpairOrderFstate.REJECT)) {
				map.put("remark", "该维修单被外修组拒绝，已自动关闭");
			}
		}
		return map;
	}
}

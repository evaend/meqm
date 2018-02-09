/** 
 * Project Name:MessageModule 
 * File Name:MessageServiceImpl.java 
 * Package Name:com.phxl.ysy.MessageModule.service.impl 
 * Date:2017年4月25日上午11:18:12 
 * Copyright (c) 2017, PHXL All Rights Reserved. 
 * 
*/  
  
package com.phxl.ysy.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.core.base.util.IdentifieUtil;
import com.phxl.core.base.util.JSONUtils;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.ysy.dao.MessageItemOrgMapper;
import com.phxl.ysy.dao.MessageMapper;
import com.phxl.ysy.entity.Message;
import com.phxl.ysy.entity.MessageItemGlobal;
import com.phxl.ysy.entity.MessageItemOrg;
import com.phxl.ysy.service.MessageService;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.service.weixin.WeixinAPIInterface;
import com.phxl.ysy.web.weixin.AccessTokenInfo;

import net.sf.json.JSONObject;

/** 
 * ClassName:MessageServiceImpl <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年4月25日 上午11:18:12 <br/> 
 * @author   fenghui 
 * @version   
 * @since    JDK 1.7 
 * @see       
 */
@Service
public class MessageServiceImpl extends BaseService implements MessageService{
    public final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private MessageItemOrgMapper messageItemOrgMapper;
    @Autowired
    private WeixinAPIInterface weixinAPIInterface;

    @Override
    public List<Map<String, Object>> getOrgListByType(Pager pager) {
        return messageMapper.getOrgListByType(pager);
    }

    @Override
    public List<Map<String, Object>> getCodeByOrgId(Pager pager) {
        return messageMapper.getCodeByOrgId(pager);
    }
    
    @Override
    public List<Map> getMessageList(Pager pager) {
        return messageMapper.getMessageList(pager);
    }
    
    @Override
    @Transactional
    public String insertMessage(Message message, List<Map<String, Object>> receiveOrgList) throws ValidationException {
        String result = "error";
        Assert.notNull(receiveOrgList, "收件机构，不能为空!");
        Assert.notNull(message, "消息信息，不能为空");
        
        if(receiveOrgList!=null && !receiveOrgList.isEmpty()){
            for(Map map:receiveOrgList){
                if(map.get("value")!=null && StringUtils.isNotBlank(map.get("value").toString())){
                    message.setMiReceiveOrgId(map.get("value").toString());
                    //根据机构ID获取机构类型
                    List<Map<String, Object>> list = null;
                    String orgType = messageMapper.getTypeByOrgId(Long.parseLong(map.get("value").toString()));
                    if(StringUtils.isNotBlank(orgType)){
                        if("09".equals(orgType)){//若发送机构为运营机构，则不区分用户类别
                            list = messageMapper.getUserByOrgId(Long.parseLong(map.get("value").toString()),null);
                            if(list!=null && !list.isEmpty()){
                                for(Map<String, Object> userMap : list ){
                                    if(userMap!=null && !userMap.isEmpty() && StringUtils.isNotBlank((String)userMap.get("USER_ID")) && StringUtils.isNotBlank((String)userMap.get("USER_NAME"))){
                                        message.setMessageGuid(IdentifieUtil.getGuId());
                                        message.setMiReceiveUserid((String)userMap.get("USER_ID"));
                                        message.setMiReceiveUsername((String)userMap.get("USER_NAME"));
                                        result = super.insertInfo(message)>0?"success":"error";
                                        if(!"success".equals(result)){
                                            break;
                                        }
                                    }
                                }
                            }
                        }else{//若发送机构非运营机构，则消息只发送给改机构的机构管理员
                            list = messageMapper.getUserByOrgId(Long.parseLong(map.get("value").toString()),"02");//获取机构的机构管理员
                            if(list!=null && !list.isEmpty()){
                                Map<String, Object> userMap = list.get(0);
                                message.setMessageGuid(IdentifieUtil.getGuId());
                                message.setMiReceiveUserid((String)userMap.get("USER_ID"));
                                message.setMiReceiveUsername((String)userMap.get("USER_NAME"));
                                result = super.insertInfo(message)>0?"success":"error";
                                if(!"success".equals(result)){
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    @Transactional
    public void changeMessageReadfstate(String messageTag, List<Map<String, Object>> resultMap) throws ValidationException {
        LocalAssert.notEmpty(messageTag, "消息标识，不能为空!");
        Assert.notNull(resultMap, "消息主键，不能为空!");
        
        Message message = new Message();
        message.setMessageReadfstate("01");
        message.setMiReadDate(new Date());
        if(resultMap!=null && !resultMap.isEmpty()){
            for(Map map:resultMap){
                if("inbox".equals(messageTag) && StringUtils.isNotBlank((String)map.get("messageGuid"))){//收件箱，根据消息GUID设置
                    message.setMessageGuid((String)map.get("messageGuid"));
                    super.updateInfo(message);
                }
            }
        }
    }
    
    @Override
    @Transactional
    public void updateOrDeleteMessage(String messageTag, List<Map<String, Object>> resultMap) throws ValidationException {
        LocalAssert.notEmpty(messageTag, "消息标识，不能为空!");
        Assert.notNull(resultMap, "消息主键，不能为空!");
        
        Message message = new Message();
        if(resultMap!=null && !resultMap.isEmpty()){
            for(Map map:resultMap){
                if("inbox".equals(messageTag) && StringUtils.isNotBlank((String)map.get("messageGuid"))){//收件箱，根据消息GUID设置
                    message = super.find(Message.class, (String)map.get("messageGuid"));
                    if(message!=null && StringUtils.isNotBlank(message.getMiSendDelete()) && "01".equals(message.getMiSendDelete())){//若发件人的删除标识为“已删除”，则直接物理删除
                        super.deleteInfoById(Message.class, (String)map.get("messageGuid"));
                    }
                    if(message!=null && StringUtils.isNotBlank(message.getMiSendDelete()) && "00".equals(message.getMiSendDelete())){//若发件人的删除标识为“未删除”，则设置收件人标识为“已删除”
                        message.setMiReceiveDelete("01");
                        super.updateInfo(message);
                    }
                }
                if("outbox".equals(messageTag) && StringUtils.isNotBlank((String)map.get("messageCode"))){//已发送，根据消息CODE设置
                    message.setMessageCode((String)map.get("messageCode"));
                    message.setMessageSendfstate("00");
                    message.setMiSendDelete("00");
                    List<Message> list = super.searchList(message);
                    if(list!=null && !list.isEmpty()){
                        for(Message msg : list){
                            if(msg!=null && StringUtils.isNotBlank(msg.getMiReceiveDelete()) && "01".equals(msg.getMiReceiveDelete())){//若收件人的删除标识为“已删除”，则直接物理删除
                                super.deleteInfoById(Message.class, msg.getMessageGuid());
                            }
                            if(msg!=null && StringUtils.isNotBlank(msg.getMiReceiveDelete()) && "00".equals(msg.getMiReceiveDelete())){//若收件人的删除标识为“未删除”，则设置发件人删除标识为“已删除”
                                msg.setMiSendDelete("01");
                                super.updateInfo(msg);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    @Scheduled(cron = "0 0 22 * * ?")
    public void deleteMessage() {
        int seccessNum = 0;
        int failNum = 0;
        List<Message> list = super.searchList(new Message());
        if(list!=null && !list.isEmpty()){
            for(Message message:list){
                if(message!=null && StringUtils.isNotBlank(message.getMessageGuid()) && message.getMiLastSendDate()!=null){
                    logger.info("准备开始删除过期的消息信息..");
                    try {
                        Integer saveDays = StringUtils.isBlank(message.getMiSaveDate())?5:Integer.parseInt(message.getMiSaveDate());//查询该消息的保存天数,默认保存5天
                        Date lastSendDate = message.getMiLastSendDate();
                        int days = (int) ((new Date().getTime() - lastSendDate.getTime()) / (1000*3600*24));//根据当前时间和最后发送时间计算该消息在数据库中存放的实际天数
                        if(days > saveDays.intValue()){//若实际天数大于保留天数，则删掉掉消息
                            super.deleteInfo(message);
                            seccessNum++;
                        }
                    } catch (Exception e) {
                        failNum++;
                        logger.error("定时删除消息异常。", e);
                    }
                }
            }

            logger.info("过期的消息已删除: 成功"+seccessNum+"个，失败"+failNum+"个");
        }else{
            logger.info("没有消息要删除。");
        }
    }
    
    @Override
	public void produceMessage(Map<String, Object> argument, Message message) {
		/**
		 * 判断message里的消息模板发送类型是微信还是非微信 非微信的类型需要替换消息项里的参数，得到字符串
		 * 微信的类型需要根据参数拼装得到JSON串，包含微信模块消息的id和接收者的openid
		 */
		String messageContent = "";
		String messageTitle = "";
		try {
			/** 消息里必须要有收件机构，不然无法判断机构消息项信息 **/
			if (StringUtils.isBlank(message.getMiReceiveOrgId())) {
				logger.error("该消息没有指定接收机构，无法发送！");
			} else if (StringUtils.isNotBlank(
					message.getMiCode())) {/** 从消息里取出消息项的code，用code查找对应的消息模板 **/
				MessageItemGlobal miglobal = new MessageItemGlobal();
				miglobal.setMiCode(message.getMiCode());
				MessageItemGlobal mg = this.searchEntity(miglobal);
				if (mg != null) {
					if (mg.getFstate().equals(CustomConst.Fstate.USABLE)) {
						MessageItemOrg miOrg = new MessageItemOrg();
						miOrg.setMiGlobalGuid(mg.getMiGlobalGuid());
						miOrg.setOrgId(Long.valueOf(message.getMiReceiveOrgId()));
						MessageItemOrg mo = this.searchEntity(miOrg);
						message.setMiSysType(mg.getMiSysType());
						if (mo != null && mo.getFstate().equals(CustomConst.Fstate.USABLE)) {
							String[] sendTypes = mo.getMiSendType().split(",");
							Set<String> receives = this.getReceives(mo);
							if(StringUtils.isNotBlank(message.getMiReceiveUserid())){
								if(receives == null)
									receives = new HashSet<String>();
								receives.add(message.getMiReceiveUserid());
							}
							if (CollectionUtils.isNotEmpty(receives)) {
								Iterator<String> it = receives.iterator();
								while(it.hasNext()){
									String receiveId = it.next();
									// 取出机构消息项的所有发送方式，不同的发送方式会生成不同的消息
									for (String sendType : sendTypes) {
										message.setMiReceiveUserid(receiveId);
										message.setMiSaveDate(mg.getMiSaveDate());
										Message nMessage = newMessage(message, sendType);
										if (sendType.equals("wechat")) {// 微信的发送方式
											messageContent = getMessageJsonContent(argument, mg.getWechatTemplateid(),
													mg.getWechatTemplatecontent(), message.getMiReceiveUserid());
											if (StringUtils.isNotBlank(messageContent)) {
												/** 消息表中新增数据 **/
												nMessage.setMessageContent(messageContent);
												this.insertInfo(nMessage);
												/** 微信接口推送 **/
												pushMessages(nMessage.getMessageGuid());
											}
										} else {// 非微信的发送方式
											messageTitle = getMessageReplaceA(argument, mg.getMiTitle());
											messageContent = getMessageReplaceA(argument, mg.getMiContent());
											/** 消息表中新增数据 **/
											nMessage.setMessageTitle(messageTitle);
											nMessage.setMessageContent(messageContent);
											this.insertInfo(nMessage);
										}
									}
								}
							}
						} else {
							logger.error("机构" + message.getMiReceiveOrgId() + "消息项" + message.getMiCode()
									+ "不存在或已停用，不能生成消息！");
						}
					} else {
						logger.error("全局消息项" + message.getMiCode() + "已停用，不能生成消息！");
					}
				} else {
					logger.error("不存在指定消息项" + message.getMiCode() + "，无法生成消息！");
				}
			} else {
				logger.error("没有选择消息项，消息无法发送！");
			}
		} catch (Exception e) {
			logger.error("消息生成异常：IMessageService.produceMessage", e);
		}
	}

	/**
	 * 新的消息实例sendType:message站内信,wechat微信,email邮件,shortmessage短信 (来源静态数据)
	 */
	public Message newMessage(Message smessage, String sendType) {
		Message message = new Message();// 默认未读、默认消息发送成功、默认未删除、发送次数为1
		message.setMessageGuid(IdentifieUtil.getGuId());
		message.setMiCode(smessage.getMiCode());
		message.setMiSendType(sendType);
		message.setMiReceiveUserid(smessage.getMiReceiveUserid());
		message.setMiCreateDate(new Date());
		message.setMiLastSendDate(new Date());// 最后发送时间
		message.setMiReceiveOrgId(smessage.getMiReceiveOrgId());
		message.setMiSendNum((short) 1);// 消息已发送次数1
		message.setMiSysType(smessage.getMiSysType());
		message.setMiSaveDate(smessage.getMiSaveDate());
		return message;
	}

	/**
	 * 站内信消息参数替换
	 **/
	public String getMessageReplaceA(Map<String, Object> argument, String itemString) {
		/** 替换消息项参数获取该消息的相关信息 **/
		String[] itemCodes = getInArgumentCode(itemString);
		for (String itemCode : itemCodes) {
			if (argument.get(itemCode) != null) {
				itemString = itemString.replaceAll("\\{\\{" + itemCode + ".DATA}}", argument.get(itemCode).toString());
			}
		}
		return itemString;
	}

	/** 解析字符串获取其中存在的参数 **/
	public String[] getInArgumentCode(String checkStr) {
		String[] strs = checkStr.split(".DATA}}");
		String[] returnAttr = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			returnAttr[i] = strs[i].substring(strs[i].indexOf("{{") + 2);
		}
		return returnAttr;
	}

	/**
	 * 生成微信JSON串
	 * 
	 * @throws JsonProcessingException
	 */
	public String getMessageJsonContent(Map<String, Object> argument, String wechatTId, String wechatTContent,
			String userid) {
		String WeChatContent = "";
		JSONUtils json = new JSONUtils();
		Map<String, Object> map = new HashMap<String, Object>();
		String wechatOpenid = messageItemOrgMapper.selectWechatIdByUserId(userid);
		if (StringUtils.isNotBlank(wechatOpenid)) {
			map.put("touser", wechatOpenid);// openid
			map.put("template_id", wechatTId);// template_id
			// map.put("url", "");//url,做详情的，可以不要
			/** 需要传输的参数内容，参数key从数据库中取 */
			Map<String, Object> mapdata = new HashMap<String, Object>();
			// 假设数据库中是这样存储的
			// {{first.DATA}}单据类型：{{keyword1.DATA}}
			// 单据状态：{{keyword2.DATA}}更新时间：{{keyword3.DATA}}{{remark.DATA}}
			// 从这里取出{{}}之间的参数
			String[] contentCodes = getInArgumentCode(wechatTContent);
			for (String contentCode : contentCodes) {
				if (argument.get(contentCode) != null) {
					Map<String, Object> datadetail = new HashMap<String, Object>();
					datadetail.put("value", argument.get(contentCode).toString());
					mapdata.put(contentCode, datadetail);
				}
			}
			map.put("data", mapdata);
			try {
				WeChatContent = json.toPrettyJson(map);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return WeChatContent;
	}

	/**
	 * 推送消息
	 */
	@Override
	public void pushMessages(String messages) {
		// TODO Auto-generated method stub
		String[] messagePushs = messages.split(",");
		for (String messagePush : messagePushs) {
			Message message = this.find(Message.class, messagePush);
			String returnCode = "00";// 返回状态，微信需要返回，站内信默认是成功的
			// 微信类型的、有效消息会调用这个微信发送接口
			if (message.getMiSendType().equals("wechat")) {
				// accessToken定时刷新，频率大概约等于2小时
				returnCode = weixinAPIInterface.sendMessage(AccessTokenInfo.accessToken.getAccessToken(),
						message.getMessageContent());
			}
			Message newMessage = new Message();
			newMessage.setMessageGuid(message.getMessageGuid());
			newMessage.setMiLastSendDate(new Date());// 最后发送时间
			if (StringUtils.isNotBlank(returnCode)) {
				if(!returnCode.equals("00")){
					JSONObject obj = JSONObject.fromObject(returnCode);
					if(obj != null){
						Long errcode = obj.getLong("errcode"); 
						String errmsg = obj.getString("errmsg");
						if(0 == errcode && "ok".equals(errmsg))
							newMessage.setMessageSendfstate("00");// 微信发送成功
						else
							newMessage.setMessageSendfstate("01");// 微信发送失败
					}
				}else{
					newMessage.setMessageSendfstate("00");//发送成功
				}
			} else {
				newMessage.setMessageSendfstate("01");// 微信发送连接失败
			}
			this.updateInfo(newMessage);// 更新状态
		}

	}

	public Set<String> getReceives(MessageItemOrg miOrg) {
		Set<String> receives = null;
		if (StringUtils.isNotBlank(miOrg.getMiReceiveUserid())) {// 手动收件人不为空
			receives = new HashSet(Arrays.asList(miOrg.getMiReceiveUserid().split(",")));
		}
		// 自动匹配接收人
		if (miOrg.getMiIsreceiveAuto().equals("01")) {
			Set<String> autoReceives = messageItemOrgMapper.selectMRceByOrgId(miOrg.getOrgId());
			if (CollectionUtils.isNotEmpty(receives)) {
				receives.addAll(autoReceives);// 会自动去重复
			} else {
				receives = autoReceives;// 没有手动指定的接收人
			}
		}
		return receives;
	}

    @Override
    @Scheduled(cron = "0 */30 * * * ?")
    public void resendMessage() {
        logger.info("准备查询需要发送的邮件并发送邮件..");
        //查询需要发送的消息的列表
        List<Message> list = messageMapper.selectWechatMessage();
        if(list!=null && !list.isEmpty()){
            int seccessNum = 0;
            int failNum = 0;
            for(Message msg:list){
                //消息发送次数达到3次就不再发送
                if(msg!=null && StringUtils.isNotBlank(msg.getMessageGuid())){
                    try {
                        Short num = msg.getMiSendNum()==null?Short.parseShort("1"):msg.getMiSendNum();
                        pushMessages(msg.getMessageGuid());//调用推送消息接口
                        msg.setMiSendNum(num++);//消息发送后，发送次数+1
                        super.updateInfo(msg);
                        seccessNum++;
                    } catch (Exception e) {
                        failNum++;
                        logger.error("消息发送异常。", e);
                    }
                }
            }
            
            logger.info("消息已经发出: 成功"+seccessNum+"个，失败"+failNum+"个");
        }else{
            logger.info("没有要发送的消息。");
        }
    }
    

}
  
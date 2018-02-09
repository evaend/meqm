/** 
 * Project Name:MessageModule 
 * File Name:MessageService.java 
 * Package Name:com.phxl.ysy.MessageModule.service 
 * Date:2017年4月25日上午11:11:54 
 * Copyright (c) 2017, PHXL All Rights Reserved. 
 * 
*/  
  
package com.phxl.ysy.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.service.IBaseService;
import com.phxl.ysy.entity.Message;

/** 
 * ClassName:MessageService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年4月25日 上午11:11:54 <br/> 
 * @author   fenghui 
 * @version   
 * @since    JDK 1.7 
 * @see       
 */
public interface MessageService extends IBaseService{
    
    /**
     * 
     * getOrgListByType:(根据机构类型查询机构列表). <br/> 
     * 
     * @Title: getOrgListByType
     * @Description: TODO
     * @param pager
     * @return    设定参数
     * @return List<Map<String,Object>>    返回类型
     * @throws
     */
    List<Map<String, Object>> getOrgListByType(Pager pager);
    
    /**
     * 
     * getCodeByOrgId:(根据机构查询消息项). <br/> 
     * 
     * @Title: getCodeByOrgId
     * @Description: TODO
     * @param pager
     * @return    设定参数
     * @return List<Map<String,Object>>    返回类型
     * @throws
     */
    List<Map<String, Object>> getCodeByOrgId(Pager pager);
    
    /**
     * 
     * insertMessage:(新增消息信息). <br/> 
     * 
     * @Title: insertMessage
     * @Description: TODO
     * @param message
     * @param receiveOrgIds
     * @throws ValidationException    设定参数
     * @return void    返回类型
     * @throws
     */
    public String insertMessage(Message message, List<Map<String, Object>> resultMap) throws ValidationException;
    
    /**
     * 
     * getMessageList:(查询消息列表). <br/> 
     * 
     * @Title: getMessageList
     * @Description: TODO
     * @param pager
     * @return    设定参数
     * @return List<Map>    返回类型
     * @throws
     */
    List<Map> getMessageList(Pager pager);

    /**
     * 
     * changeMessageReadfstate:(批量设置消息已读). <br/> 
     * 
     * @Title: changeMessageReadfstate
     * @Description: TODO
     * @param messageTag
     * @param resultMap
     * @throws ValidationException    设定参数
     * @return void    返回类型
     * @throws
     */
    public void changeMessageReadfstate(String messageTag, List<Map<String, Object>> resultMap) throws ValidationException;
    
    /**
     * 
     * updateOrDeleteMessage:(这里用一句话描述这个方法的作用). <br/> 
     * 
     * @Title: changeMessageReadfstate
     * @Description: TODO
     * @param messageTag
     * @param resultMap
     * @throws ValidationException    设定参数
     * @return void    返回类型
     * @throws
     */
    public void updateOrDeleteMessage(String messageTag, List<Map<String, Object>> resultMap) throws ValidationException;
    
    //业务方法中生成消息的接口方法，argument是消息需要的参数Map
  	//message是封装的消息主体
  	void produceMessage(Map<String,Object> argument,Message t);
  	
  	//推送消息,如果有多条消息微信推送，以逗号分割
  	void pushMessages(String messages);
    
    /**
     * 
     * deleteMessage:(定期删除消息). <br/> 
     * 
     * @Title: deleteMessage
     * @Description: TODO    设定参数
     * @return void    返回类型
     * @throws
     */
    public void deleteMessage();
    
    /**
     * 
     * resendMessage:(扫描发送失败的消息，并重新发送). <br/> 
     * 
     * @Title: resendMessage
     * @Description: TODO    设定参数
     * @return void    返回类型
     * @throws
     */
    public void resendMessage();

}
  
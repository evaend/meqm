package com.phxl.ysy.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.Message;

public interface MessageMapper {

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
    List<Map<String, Object>> getOrgListByType(Pager<?> pager);
    
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
    List<Map<String, Object>> getCodeByOrgId(Pager<?> pager);

    /**
     * 
     * getUserByOrgId:(获取机构的机构管理员). <br/> 
     * 
     * @Title: getUserByOrgId
     * @Description: TODO
     * @param orgId
     * @return    设定参数
     * @return List<Map<String,Object>>    返回类型
     * @throws
     */
    List<Map<String, Object>> getUserByOrgId(@Param("orgId")Long orgId,@Param("userLevel")String userLevel);
    
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
     * getTypeByOrgId:(根据机构ID获取机构类别). <br/> 
     * 
     * @Title: getTypeByOrgId
     * @Description: TODO
     * @param orgId
     * @return    设定参数
     * @return String    返回类型
     * @throws
     */
    String getTypeByOrgId(@Param("orgId")Long orgId);
    
    /**
     * 
     * selectWechatMessage:(查询发送次数在3次以下的且发送失败的微信消息). <br/> 
     * 
     * @Title: selectWechatMessage
     * @Description: TODO
     * @return    设定参数
     * @return List<Message>    返回类型
     * @throws
     */
    List<Message> selectWechatMessage();
}
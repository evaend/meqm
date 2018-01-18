package com.phxl.ysy.dao;

import com.phxl.ysy.entity.Messagewechat;

public interface MessagewechatMapper {
    int deleteByPrimaryKey(String messagewechatGuid);

    int insert(Messagewechat record);

    int insertSelective(Messagewechat record);

    Messagewechat selectByPrimaryKey(String messagewechatGuid);

    int updateByPrimaryKeySelective(Messagewechat record);

    int updateByPrimaryKey(Messagewechat record);
}
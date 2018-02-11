package com.phxl.ysy.entity;

import com.phxl.core.base.annotation.BaseSql;

@BaseSql(tableName="TS_GROUP_USER", resultName="com.phxl.ysy.dao.GroupUserMapper.BaseResultMap")
public class GroupUserKey {
    private String groupId;

    private String userId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}
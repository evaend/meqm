package com.phxl.ysy.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.phxl.ysy.entity.ChangeFstate;

public interface ChangeFstateMapper {

	void batchInsertChangeFsate(@Param("tbIds")Set<String> tbIds, @Param("changeFstate")ChangeFstate changeFstate);

}
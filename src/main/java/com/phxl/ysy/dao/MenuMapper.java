package com.phxl.ysy.dao;

import java.util.Map;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.Menu;

public interface MenuMapper {
	
	/**
	 * 查询系统所有菜单列表
	 * @author	黄文君
	 * @date	2017年3月24日 上午9:58:35
	 * @param	pager
	 * @return	List<Menu>
	 */
	public List<Menu> findMenuList(Pager pager);

	//查询模块菜单列表，加层级标记
	List<Map<String, Object>> searchMenuWithLevel(@Param("moduleId")String moduleId);

	/**
	 * 批量更新菜单状态
	 * @author	黄文君
	 * @date	2017年3月24日 上午10:11:08
	 * @param	menuIds
	 * @param	fstate
	 * @param	modifyUserid
	 * @return	int
	 */
	public int updateFstateBatch(@Param("menuIds")String[] menuIds, 
								@Param("fstate")String fstate, 
								@Param("modifyUserid")String modifyUserid);
	
	/**
	 * 删除全部组内指定菜单
	 * @author	黄文君
	 * @date	2017年3月24日 上午11:00:41
	 * @param	menuId
	 * @return	int
	 */
	public int deleteGroupMenuByMenuId(String menuId);
}
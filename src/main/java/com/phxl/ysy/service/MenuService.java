package com.phxl.ysy.service;

import java.util.List;
import java.util.Map;

import com.phxl.core.base.entity.Pager;
import com.phxl.ysy.entity.Menu;

public interface MenuService {

	/**
	 * 查询系统所有菜单列表
	 * @author	黄文君
	 * @date	2017年3月24日 上午9:58:35
	 * @param	pager
	 * @return	List<Menu>
	 */
	public abstract List<Menu> findMenuList(Pager pager);

	/**
	 * 批量更新菜单状态
	 * @author	黄文君
	 * @date	2017年3月24日 上午10:11:08
	 * @param	menuIds
	 * @param	fstate
	 * @param	modifyUserid
	 * @return	int
	 */
	public abstract int updateFstateBatch(String[] menuIds, String fstate, String modifyUserid);

	/**
	 * 删除菜单
	 * @author	黄文君
	 * @date	2017年3月24日 上午11:02:41
	 * @param	menuId
	 * @return	void
	 */
	public abstract void deleteMenu(String menuId);
	
	/**
	 * 查询模块菜单列表，加层级标记
	 */
	List<Map<String, Object>> searchMenuWithLevel(String moduleId);

}
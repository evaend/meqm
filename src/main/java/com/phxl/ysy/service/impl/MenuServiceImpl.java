package com.phxl.ysy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.service.impl.BaseService;
import com.phxl.ysy.dao.MenuMapper;
import com.phxl.ysy.entity.Menu;
import com.phxl.ysy.service.MenuService;

/**
 * 菜单服务
 * @date	2017年3月23日 下午2:06:12
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
@Service
public class MenuServiceImpl extends BaseService implements MenuService {
	
	@Autowired
	MenuMapper menuMapper;

	/**
	 * 查询系统所有菜单列表
	 * @author	黄文君
	 * @date	2017年3月24日 上午9:58:35
	 * @param	pager
	 * @return	List<Menu>
	 */
	public List<Menu> findMenuList(Pager pager) {
		return menuMapper.findMenuList(pager);
	}

	/**
	 * 批量更新菜单状态
	 * @author	黄文君
	 * @date	2017年3月24日 上午10:11:08
	 * @param	menuIds
	 * @param	fstate
	 * @param	modifyUserid
	 * @return	int
	 */
	public int updateFstateBatch(String[] menuIds, String fstate, String modifyUserid) {
		return menuMapper.updateFstateBatch(menuIds, fstate, modifyUserid);
	}
	
	/**
	 * 删除菜单
	 * @author	黄文君
	 * @date	2017年3月24日 上午11:02:41
	 * @param	menuId
	 * @return	void
	 */
	public void deleteMenu(String menuId){
		//删除菜单
		super.deleteInfoById(Menu.class, menuId);
		//删除组里的菜单
		menuMapper.deleteGroupMenuByMenuId(menuId);
	}
	
	public List<Map<String, Object>> searchMenuWithLevel(String moduleId) {
		// TODO Auto-generated method stub
		return menuMapper.searchMenuWithLevel(moduleId);
	}

}

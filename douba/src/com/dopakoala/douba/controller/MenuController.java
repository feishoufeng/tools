package com.dopakoala.douba.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dopakoala.douba.entity.AdminUser;
import com.dopakoala.douba.entity.CatalogMenu;
import com.dopakoala.douba.entity.CatalogType;
import com.dopakoala.douba.service.ICatalogMenuService;
import com.dopakoala.douba.service.ICatalogTypeService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ControllerUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

@Controller
@RequestMapping("/menu")
public class MenuController {
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	@Resource
	private ICatalogMenuService catalogMenuService;
	@Resource
	private ICatalogTypeService catalogTypeService;
	// 响应map
	private Map<String, Object> resultMap = new HashMap<String, Object>();

	@RequestMapping("/page")
	public String page() {
		return "viewPage/menu_page";
	}

	// 获取菜单
	@RequestMapping("/getMenu")
	@ResponseBody
	public void getMenu() {
		// 错误提示信息
		String errmsg = "";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 所有菜单类型集合
		List<CatalogType> catalogTypeList = new ArrayList<CatalogType>();
		try {
			request.setCharacterEncoding("utf-8");

			catalogTypeList = this.catalogTypeService.selectAllByStatus(null);
			if (catalogTypeList == null || catalogTypeList.size() <= 0) {
				errmsg = "当前用户无权限菜单！";
			} else {
				// 获取登录用户信息
				AdminUser user = AchieveUtils.getUser(request);
				if (user != null) {
					// 获取用户权限
					String rights = user.getRights();
					int is_admin = user.getIsAdmin();
					if (!CheckUtils.isNull(rights) || is_admin == 1) {
						// 获取菜单类型
						for (CatalogType catalogType : catalogTypeList) {
							// 所有菜单集合
							List<Map<String, Object>> m_box = new ArrayList<Map<String, Object>>();
							// 获取主菜单
							// 主菜单
							List<CatalogMenu> catalogMenuList = new ArrayList<CatalogMenu>();
							CatalogMenu selectMain = new CatalogMenu();
							selectMain.setType(catalogType.getId());
							selectMain.setStatus(null);
							catalogMenuList = this.catalogMenuService.selectMainCatalog(selectMain);
							if (catalogMenuList.size() > 0) {
								// 遍历主菜单
								for (CatalogMenu catalogMenu : catalogMenuList) {
									Map<String, Object> mmenuMap = new HashMap<String, Object>();// 菜单map
									// 获取主菜单id
									int m_menuId = catalogMenu.getMenuid();
									// 根据主菜单id获取二级菜单
									CatalogMenu selectSecond = new CatalogMenu();
									selectSecond.setParentid(m_menuId);
									selectSecond.setStatus(null);
									List<CatalogMenu> scatalogMenuList = this.catalogMenuService
											.selectByParentid(selectSecond);
									if (scatalogMenuList != null && scatalogMenuList.size() > 0) {
										List<Map<String, Object>> s_box = new ArrayList<>();// 相应二级菜单下的所有三级菜单
										for (CatalogMenu s_catalogMenu : scatalogMenuList) {
											Map<String, Object> smenuMap = new HashMap<String, Object>();// 菜单map
											// 获取二级菜单id
											int s_menuId = s_catalogMenu.getMenuid();
											// 根据二级菜单id获取三级菜单项
											CatalogMenu selectThird = new CatalogMenu();
											selectThird.setParentid(s_menuId);
											selectThird.setStatus(null);
											List<CatalogMenu> tcatalogMenuList = this.catalogMenuService
													.selectByParentid(selectThird);
											if (tcatalogMenuList != null && tcatalogMenuList.size() > 0) {
												// 遍历三级菜单
												// 用户具有的三级菜单集合
												List<CatalogMenu> tList = new ArrayList<>();
												for (CatalogMenu t_catalogMenu : scatalogMenuList) {
													// 三级菜单id
													int t_menuId = t_catalogMenu.getMenuid();
													// 判断用户是否具有当前三级菜单权限
													if (rights.contains("," + t_menuId + ",")
															|| user.getIsAdmin() == 1) {
														// 添加三级菜单
														tList.add(t_catalogMenu);
													}
												}
												if (tList.size() > 0) {
													smenuMap.put("second", s_catalogMenu);// 二级菜单信息
													smenuMap.put("list", tList);// 对应三级菜单列表
													s_box.add(smenuMap);
												}
											} else {
												// 没有三级菜单的二级菜单
												if (rights.contains("," + s_menuId + ",") || user.getIsAdmin() == 1) {
													// 添加二级菜单
													smenuMap.put("second", s_catalogMenu);// 二级菜单信息
													smenuMap.put("list", "");// 对应三级菜单列表
													s_box.add(smenuMap);
												}
											}
										}
										if (s_box.size() > 0) {
											mmenuMap.put("list", s_box);
											mmenuMap.put("first", catalogMenu);
											m_box.add(mmenuMap);
										}
									}
								}
							}
							if (m_box.size() > 0) {
								Map<String, Object> tmenuMap = new HashMap<String, Object>();
								tmenuMap.put("list", m_box);
								tmenuMap.put("main", catalogType);
								list.add(tmenuMap);
							}
						}
						if (list.size() <= 0) {
							errmsg = "当前用户无权限菜单！";
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}

		resultMap.put("errmsg", errmsg);
		if (CheckUtils.isNull(errmsg) && list.size() > 0) {
			resultMap.put("menu", list);
		} else {
			resultMap.put("menu", new Object());
		}
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("/changeStatus")
	@ResponseBody
	public void changeStatus(@RequestParam String type, @RequestParam int id, @RequestParam int status) {
		String errmsg = "";
		try {
			if (type.equals("main")) {
				CatalogType updateCatalogType = new CatalogType();
				updateCatalogType.setStatus(status);
				updateCatalogType.setId(id);
				this.catalogTypeService.updateByPrimaryKeySelective(updateCatalogType);
			} else {
				CatalogMenu updateCatalogMenu = new CatalogMenu();
				updateCatalogMenu.setStatus(status);
				updateCatalogMenu.setMenuid(id);
				this.catalogMenuService.updateByPrimaryKeySelective(updateCatalogMenu);
			}
		} catch (Exception e) {
			// TODO: handle exception
			errmsg = "系统异常，请联系管理员！";
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}

		resultMap.put("errmsg", errmsg);
		ControllerUtils.responseJson(response, resultMap);
	}
}

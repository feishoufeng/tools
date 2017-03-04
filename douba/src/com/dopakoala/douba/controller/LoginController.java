package com.dopakoala.douba.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.dopakoala.douba.entity.AdminUser;
import com.dopakoala.douba.entity.CatalogMenu;
import com.dopakoala.douba.entity.CatalogType;
import com.dopakoala.douba.service.IAdminUserService;
import com.dopakoala.douba.service.IAppConfigService;
import com.dopakoala.douba.service.ICatalogMenuService;
import com.dopakoala.douba.service.ICatalogTypeService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ControllerUtils;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	@Resource
	private IAdminUserService adminUserService;
	@Resource
	private IAppConfigService appConfigService;
	@Resource
	private ICatalogMenuService catalogMenuService;
	@Resource
	private ICatalogTypeService catalogTypeService;
	// 用户登录session
	private HttpSession session;
	// 响应map
	private Map<String, Object> resultMap = new HashMap<String, Object>();

	@RequestMapping("/login_page")
	public String login_page() {
		return "viewPage/login_page";
	}

	@RequestMapping("/main")
	public String main() {
		return "mainView/main";
	}

	@RequestMapping("/bottom")
	public String bottom() {
		return "mainView/bottom";
	}

	@RequestMapping("/control_left")
	public String control_left() {
		return "mainView/control_left";
	}

	@RequestMapping("/control_top")
	public String control_top() {
		return "mainView/control_top";
	}

	@RequestMapping("/catalog_menu")
	public String catalog_menu() {
		return "viewPage/catalog_menu";
	}

	@RequestMapping("/welcome_page")
	public String welcome_page(Model model) {
		String site_name = AchieveUtils.getStringAppConfig("site_name", appConfigService);
		model.addAttribute("site_name", site_name);
		return "viewPage/welcome_page";
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

			catalogTypeList = this.catalogTypeService.selectAllByStatus(1);
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
							selectMain.setStatus(1);
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
									selectSecond.setStatus(1);
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
											selectThird.setStatus(1);
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
							if(m_box.size() > 0){
								Map<String,Object> tmenuMap = new HashMap<String,Object>();
								tmenuMap.put("list", m_box);
								tmenuMap.put("main", catalogType);
								list.add(tmenuMap);
							}
						}
						if(list.size() <= 0){
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

	@RequestMapping("/left")
	public String left(Model model) {
		return "mainView/left";
	}

	@RequestMapping("/right")
	public String right() {
		return "mainView/right";
	}

	@RequestMapping("/top")
	public String top() {
		return "mainView/top";
	}

	@RequestMapping("/getSessionMsg")
	@ResponseBody
	public void getSessionMsg() {
		// 错误提示信息
		String errmsg = "";
		AdminUser user = AchieveUtils.getUser(request);
		try {
			if (user == null) {
				errmsg = "获取用户登录信息失败！";
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		resultMap.put("errmsg", errmsg);
		if (CheckUtils.isNull(errmsg)) {
			resultMap.put("user", user);
		} else {
			resultMap.put("user", new Object());
		}
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("/exit")
	@ResponseBody
	public void exit() {
		// 错误提示信息
		String errmsg = "";
		try {
			session = request.getSession();
			if (session == null) {
				errmsg = "session中无用户登录信息！";
			} else {
				session.invalidate();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		resultMap.put("errmsg", errmsg);
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("/checkUser")
	public String checkUser(@RequestParam String userName, @RequestParam String password, Model model) {
		String resurl = "viewPage/login_page";
		// 错误提示信息
		String errmsg = "";
		try {
			request.setCharacterEncoding("utf-8");
			session = request.getSession();

			// 获取用户账户是否存在
			AdminUser adminUser = this.adminUserService.selectByName(userName);
			if (adminUser != null) {
				// 获取用户的随机密钥
				String passwordSalt = adminUser.getPasswordSalt();
				// 对密码进行加密
				password = ConvertUtils.encryptMD5(passwordSalt + password);

				AdminUser selectAdminUser = new AdminUser();
				selectAdminUser.setName(userName);
				selectAdminUser.setPassword(password);
				AdminUser loginAdminUser = this.adminUserService.selectByLoginMsg(selectAdminUser);
				if (loginAdminUser != null) {
					resurl = "redirect:/login/main";
					session.setAttribute("user", loginAdminUser);
				} else {
					errmsg = "密码错误！";
				}
			} else {
				errmsg = "账户不存在！";
			}

			if (!CheckUtils.isNull(errmsg)) {
				// 相应参数
				model.addAttribute("userName", userName);
				model.addAttribute("errmsg", errmsg);
			}
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
		return resurl;
	}

	@RequestMapping("/checkVerificationCode")
	@ResponseBody
	public void checkVerificationCode(@RequestParam String verificationCode) {
		session = request.getSession();
		String kaptchaCode = session.getAttribute(Constants.KAPTCHA_SESSION_KEY).toString();
		resultMap.put("flag", verificationCode.toLowerCase().equals(kaptchaCode.toLowerCase()));
		ControllerUtils.responseJson(response, resultMap);
	}
}

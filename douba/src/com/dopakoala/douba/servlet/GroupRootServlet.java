package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.dopakoala.douba.entity.GroupUser;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IAppConfigService;
import com.dopakoala.douba.service.IGroupUserService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserEditUserServlet
 */
@WebServlet("/group/root")
public class GroupRootServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupUserService groupUserService;
	@Autowired
	private IAppConfigService appConfigService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GroupRootServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String errmsg = "";
		int errcode = 0;
		try {

			String token = "";
			int uid = 0, gid = 0, type = 0, targetuid = 0, operation_type = 0;
			long timestamp = 0;

			JSONObject requestJson = ServletUtils.getReader(request);

			if (requestJson != null) {

				if (!requestJson.isNullObject() && requestJson.containsKey("token")) {
					token = requestJson.getString("token");
				}
				if (!requestJson.isNullObject() && requestJson.containsKey("uid")) {
					uid = requestJson.getInt("uid");
				}
				if (!requestJson.isNullObject() && requestJson.containsKey("timestamp")) {
					timestamp = requestJson.getLong("timestamp");
				}

				JSONObject parameters = requestJson.getJSONObject("parameters");
				if (!parameters.isNullObject() && parameters.containsKey("gid")) {
					gid = parameters.getInt("gid");
				}
				if (!parameters.isNullObject() && parameters.containsKey("type")) {
					type = parameters.getInt("type");
				}
				if (!parameters.isNullObject() && parameters.containsKey("targetUid")) {
					targetuid = parameters.getInt("targetUid");
				}
				if (!parameters.isNullObject() && parameters.containsKey("operation_type")) {
					operation_type = parameters.getInt("operation_type");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						if (gid > 0) {
							if (type > 0 && type <= 3) {
								if (operation_type >= 0 && operation_type <= 1) {
									if (targetuid <= 0) {
										errmsg = "目标用户uid数据不合法！";
										errcode = 600;
									}
								} else {
									errmsg = "操作类型数据不合法！";
									errcode = 600;
								}
							} else {
								errmsg = "权限类型数据不合法！";
								errcode = 600;
							}
						} else {
							errmsg = "gid数据不合法！";
							errcode = 600;
						}
					} else {
						errmsg = "账户登录权限过期，请重新登录";
						errcode = 700;
					}
				} else {
					errmsg = "账户id不存在！";
					errcode = 200;
				}
			} else {
				errmsg = "请求时间戳异常，请重试！";
				errcode = 200;
			}

			if (errcode == 0) {
				if (uid != targetuid) {
					GroupUser selectGroupUser = new GroupUser();
					selectGroupUser.setUid(uid);
					selectGroupUser.setGid(gid);
					GroupUser groupUser = this.groupUserService.selectByUidGid(selectGroupUser);
					if (groupUser != null) {
						int root = groupUser.getRoot();
						if (root == 1) {
							GroupUser selectGroupUserTemp = new GroupUser();
							selectGroupUserTemp.setUid(targetuid);
							selectGroupUserTemp.setGid(gid);
							GroupUser tempGroupUser = this.groupUserService.selectByUidGid(selectGroupUserTemp);
							if (tempGroupUser != null) {
								int id = tempGroupUser.getId();
								GroupUser updateGroupUser = new GroupUser();
								updateGroupUser.setId(id);
								switch (type) {
								case 1:
								case 2:
									if (operation_type == 1) {
										// 赋予普通管理员权限
										updateGroupUser.setRoot(type);
									} else {
										// 删除普通管理员权限,即设为普通成员
										updateGroupUser.setRoot(0);
									}
									break;
								case 3:
									// 获取当前群里具有开卡权限的人员(将当前群下有开卡权限的人置为没有开卡权限)
									GroupUser openUser = this.groupUserService.selectOpenByGid(gid);
									if (openUser != null) {
										int openId = openUser.getId();
										GroupUser updateOpenUser = new GroupUser();
										updateOpenUser.setId(openId);
										updateOpenUser.setOpen(0);
										this.groupUserService.updateByPrimaryKeySelective(updateOpenUser);
									}
									if (operation_type == 0) {
										// 删除权限,则将默认权限赋予超级管理员
										GroupUser updateGroupUserTemp = new GroupUser();
										updateGroupUserTemp.setId(groupUser.getId());
										updateGroupUserTemp.setOpen(1);
										this.groupUserService.updateByPrimaryKeySelective(updateGroupUserTemp);
									} else {
										// 赋予权限给相应的用户
										updateGroupUser.setOpen(1);
									}
									break;
								}

								int flag = 0;
								// 增加普通管理员权限有人数限制
								if (type == 2 && operation_type == 1) {
									// 获取当前群里普通管理员人数
									List<GroupUser> list = this.groupUserService.selectAdminNumByGid(gid);
									if (list != null && list.size() > 0) {
										int num = list.size();
										// 获取系统配置表中的数量
										int val = AchieveUtils.getIntAppConfig("admin_num", appConfigService);
										if (num >= val) {
											flag = 1;
											errmsg = "普通管理员数量达到上限！";
											errcode = 0;
										}
									}
								}
								// 删除开卡权限，则默认将开卡权限赋予超级管理员
								if (type == 3 && operation_type == 0) {
									flag = 1;
								}
								if (flag == 0) {
									this.groupUserService.updateByPrimaryKeySelective(updateGroupUser);
								}
							}
						} else {
							errmsg = "当前用户不具有相应权限！";
							errcode = 0;
						}
					}
				} else {
					errmsg = "超级管理员不可以给自己设置权限！";
					errcode = 0;
				}

			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

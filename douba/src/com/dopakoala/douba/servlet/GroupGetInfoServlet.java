package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.dopakoala.douba.entity.Group;
import com.dopakoala.douba.entity.GroupPace;
import com.dopakoala.douba.entity.GroupUser;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IGroupPaceService;
import com.dopakoala.douba.service.IGroupService;
import com.dopakoala.douba.service.IGroupUserService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class GroupGetUserInfoServlet
 */
@WebServlet("/group/getinfo")
public class GroupGetInfoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IGroupService groupService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupUserService groupUserService;
	@Autowired
	private IGroupPaceService groupPaceService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GroupGetInfoServlet() {
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
			int uid = 0, gid = 0, root = 0;
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
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						if (gid < 1) {
							errmsg = "传入的gid不合法！";
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

			Group group = new Group();
			List<User> userList = new ArrayList<User>();
			List<GroupUser> userInfoList = new ArrayList<GroupUser>();
			double minDistance = 0;
			int minPace = 0;
			if (errcode == 0) {
				Group selectGroup = new Group();
				selectGroup.setUid(uid);
				selectGroup.setGid(gid);
				group = this.groupService.selectByUidGid(selectGroup);
				// 获取
				if (group == null) {
					errmsg = "获取跑团（群）信息失败！";
					errcode = 400;
				} else {
					if (group.getPid() <= 0) {
						// 获取大群gid
						Group mainGroup = this.groupService.selectMainByPid(group.getGid());
						group.setMainGroupGid(mainGroup.getGid());
						// 获取跑团管理员信息
						userList = this.userService.selectByGid(mainGroup.getGid());

						// 判断当前用户是否为管理员
						GroupUser selectGroupUser = new GroupUser();
						selectGroupUser.setUid(uid);
						selectGroupUser.setGid(mainGroup.getGid());
						GroupUser tempGroupUser = this.groupUserService.selectByUidGid(selectGroupUser);
						if (tempGroupUser == null) {
							root = 0;
						} else {
							root = tempGroupUser.getRoot();
						}
					} else {
						GroupUser selectGroupUserTemp = new GroupUser();
						selectGroupUserTemp.setGid(gid);
						// 选择前五位
						selectGroupUserTemp.setPagesize(50);
						userInfoList = this.groupUserService.selectTopUserInfo(selectGroupUserTemp);

						// 判断当前用户是否为管理员
						GroupUser selectGroupUser = new GroupUser();
						selectGroupUser.setUid(uid);
						selectGroupUser.setGid(gid);
						GroupUser tempGroupUser = this.groupUserService.selectByUidGid(selectGroupUser);
						if (tempGroupUser == null) {
							root = 0;
						} else {
							root = tempGroupUser.getRoot();
						}
					}

					// 获取配速标准
					GroupPace groupPace = this.groupPaceService.selectByGid(gid);
					if (groupPace != null) {
						minDistance = groupPace.getMinDistance();
						minPace = groupPace.getMinPace();
					}
				}
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);

			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("group", group);
			dataMap.put("root", root);
			if (group != null && group.getPid() <= 0) {
				dataMap.put("userInfo", userList);
			} else {
				dataMap.put("userInfo", userInfoList);
			}
			dataMap.put("minPace", minPace);
			dataMap.put("minDistance", minDistance);
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}
}

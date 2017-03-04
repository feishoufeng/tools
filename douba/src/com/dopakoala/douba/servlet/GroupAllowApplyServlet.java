package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.dopakoala.douba.entity.Group;
import com.dopakoala.douba.entity.GroupUser;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IGroupService;
import com.dopakoala.douba.service.IGroupUserService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class GroupApplyService
 */
@WebServlet("/group/allowapply")
public class GroupAllowApplyServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupUserService groupUserService;
	@Autowired
	private IGroupService groupService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GroupAllowApplyServlet() {
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
			int uid = 0, gid = 0;
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
						if (gid < 0) {
							errmsg = "传入的跑团id不合法！";
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

			int flag = 0;
			if (errcode == 0) {
				// 判断是否加入了相应的群
				GroupUser selectGroupUser = new GroupUser();
				selectGroupUser.setUid(uid);
				selectGroupUser.setGid(gid);
				GroupUser groupUser = this.groupUserService.selectByUidGid(selectGroupUser);
				if (groupUser != null) {
					// 已加入，不允许加入
					errmsg = "已加入此群，请勿重复加入！";
				} else {
					// 未加入
					Group group = this.groupService.selectByPrimaryKey(gid);
					if (group != null) {
						// 判断是否为跑团
						if (group.getPid() == 0) {
							// 是跑团
							flag = 1;
						} else {
							// 不是跑团
							// 判断是否为大群
							int isMain = group.getIsmain();
							if (isMain == 1) {
								// 是大群，不允许加入，需加入跑团
								errmsg = "未加入跑团，无法加入此群！";
							} else {
								// 不是大群
								// 获取大群id
								Group mainGroup = this.groupService.selectMainByPid(gid);
								GroupUser selectGroupUserTemp = new GroupUser();
								selectGroupUserTemp.setUid(uid);
								selectGroupUserTemp.setGid(mainGroup.getGid());
								GroupUser groupUserTemp = this.groupUserService.selectByUidGid(selectGroupUserTemp);
								if (groupUserTemp != null) {
									// 已加入跑团，允许加入
									flag = 1;
								} else {
									// 未加入跑团，不允许加入
									errmsg = "未加入跑团，无法加入此群！";
								}
							}
						}
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
			dataMap.put("flag", flag);
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

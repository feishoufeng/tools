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
import com.dopakoala.douba.entity.GroupPace;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IGroupPaceService;
import com.dopakoala.douba.service.IGroupService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserEditUserServlet
 */
@WebServlet("/group/editgroup")
public class GroupEditGroupServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupService groupService;
	@Autowired
	private IGroupPaceService groupPaceService;

	private String types = "content,condition,name,minDistance,minPace";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GroupEditGroupServlet() {
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

			String token = "", type = "", value = "";
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
				if (!parameters.isNullObject() && parameters.containsKey("type")) {
					type = parameters.getString("type");
				}
				if (!parameters.isNullObject() && parameters.containsKey("value")) {
					value = parameters.getString("value");
				}
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
						if (!CheckUtils.isNull(type) && types.contains(type)) {
							if (CheckUtils.isNull(value)) {
								errmsg = "传递的value为空！";
								errcode = 600;
							} else {
								if (gid <= 0) {
									errmsg = "传递的gid不合法！";
									errcode = 600;
								}
							}
						} else {
							errmsg = "传递的type类型不合法！";
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
				if ("content,condition,name".contains(type)) {
					Group updateGroup = new Group();
					updateGroup.setGid(gid);
					switch (type) {
					case "content":
						updateGroup.setContent(value);
						break;
					case "condition":
						updateGroup.setCondition(value);
						break;
					case "name":
						updateGroup.setName(value);
						break;
					}
					this.groupService.updateByPrimaryKeySelective(updateGroup);
				}

				if ("minDistance,minPace".contains(type)) {
					GroupPace groupPace = this.groupPaceService.selectByGid(gid);
					if (groupPace != null) {
						int id = groupPace.getId();
						GroupPace updateGroupPace = new GroupPace();
						updateGroupPace.setId(id);
						switch (type) {
						case "minDistance":
							updateGroupPace.setMinDistance(ConvertUtils.doubleFormat(value, 0));
							break;
						case "minPace":
							updateGroupPace.setMinPace(ConvertUtils.intFormat(value, 0));
							break;
						}
						this.groupPaceService.updateByPrimaryKeySelective(updateGroupPace);
					} else {
						GroupPace insertGroupPace = new GroupPace();
						insertGroupPace.setGid(gid);
						switch (type) {
						case "minDistance":
							insertGroupPace.setMinDistance(ConvertUtils.doubleFormat(value, 0));
							break;
						case "minPace":
							insertGroupPace.setMinPace(ConvertUtils.intFormat(value, 0));
							break;
						}
						this.groupPaceService.insertSelective(insertGroupPace);
					}

				}
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);

			ServletUtils.writeJson(response, resultMap);
		} catch (

		Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

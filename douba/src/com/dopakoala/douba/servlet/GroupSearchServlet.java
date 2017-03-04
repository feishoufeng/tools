package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.dopakoala.douba.entity.Group;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IGroupService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class GroupSearchServlet
 */
@WebServlet("/group/search")
public class GroupSearchServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IGroupService groupService;
	@Autowired
	private IUserService userService;

	/**
	 * Default constructor.
	 */
	public GroupSearchServlet() {
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

			String token = "", keywords = "";
			int uid = 0, page = 0, pagesize = 20, count = 0, type = 1;
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
				if (!parameters.isNullObject() && parameters.containsKey("keywords")) {
					keywords = parameters.getString("keywords");
				}
				if (!parameters.isNullObject() && parameters.containsKey("page")) {
					page = parameters.getInt("page");
					if (page >= 1) {
						page -= page;
					}
				}
				if (!parameters.isNullObject() && parameters.containsKey("pagesize")) {
					pagesize = parameters.getInt("pagesize");
				}
				if (!parameters.isNullObject() && parameters.containsKey("type")) {
					type = parameters.getInt("type");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (!tempToken.equals(token)) {
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

			List<Group> groupList = new ArrayList<>();
			if (errcode == 0) {
				// 获取当前用户所有与关键字对应的跑团
				Group selectGroup = new Group();
				selectGroup.setName(keywords);
				selectGroup.setPage(page * pagesize);
				selectGroup.setPagesize(pagesize);
				selectGroup.setUid(uid);
				selectGroup.setIsadd(type);
				groupList = this.groupService.selectTypeNameByKeyword(selectGroup);

				// 根据当前用户获取所参加的跑团
				if (groupList == null || groupList.size() < 1) {
					errmsg = "该用户未参加任何跑团（群）！";
				} else {
					// 获取当前用户所有与关键字对应的跑团总条数
					Group tempSelectGroup = new Group();
					tempSelectGroup.setName(keywords);
					tempSelectGroup.setPage(0);
					tempSelectGroup.setPagesize(0);
					tempSelectGroup.setUid(uid);
					tempSelectGroup.setIsadd(type);
					count = this.groupService.selectTypeNameByKeyword(tempSelectGroup).size();
				}
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);
			// 返回应答数据
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("count", count);
			dataMap.put("list", JSONArray.fromObject(groupList));
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

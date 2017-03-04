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

import com.dopakoala.douba.entity.GroupApplyUser;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IGroupApplyUserService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class GroupGetApplyServlet
 */
@WebServlet("/group/getApply")
public class GroupGetApplyServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupApplyUserService groupApplyUserService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GroupGetApplyServlet() {
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
			int uid = 0, gid = 0, page = 0, pagesize = 20, count = 0;
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
				if (!parameters.isNullObject() && parameters.containsKey("page")) {
					page = parameters.getInt("page");
				}
				if (!parameters.isNullObject() && parameters.containsKey("pagesize")) {
					pagesize = parameters.getInt("pagesize");
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
							errcode = 335;
						}
					} else {
						errmsg = "账户登录权限过期，请重新登录";
						errcode = 700;
					}
				} else {
					errmsg = "账户id不存在！";
					errcode = 331;
				}
			} else {
				errmsg = "请求时间戳异常，请重试！";
				errcode = 303;
			}

			List<GroupApplyUser> groupApplyUserList = new ArrayList<GroupApplyUser>();
			if (errcode == 0) {
				// 获取相应跑团（群）申请信息列表
				GroupApplyUser selectGroupApplyUser = new GroupApplyUser();
				selectGroupApplyUser.setGid(gid);
				selectGroupApplyUser.setUid(uid);
				selectGroupApplyUser.setPage(page * pagesize);
				selectGroupApplyUser.setPagesize(pagesize);
				groupApplyUserList = this.groupApplyUserService.selectCheckByGidUid(selectGroupApplyUser);
				if (groupApplyUserList == null || groupApplyUserList.size() < 1) {
					errmsg = "该跑团（群）无申请信息！";
				} else {
					// 获取相应跑团（群）申请信息列表数量
					GroupApplyUser tempGroupApplyUser = new GroupApplyUser();
					tempGroupApplyUser.setGid(gid);
					tempGroupApplyUser.setUid(uid);
					tempGroupApplyUser.setPage(0);
					tempGroupApplyUser.setPagesize(0);
					count = this.groupApplyUserService.selectCheckByGidUid(tempGroupApplyUser).size();
				}
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("count", count);
			dataMap.put("list", JSONArray.fromObject(groupApplyUserList));
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}

	}

}

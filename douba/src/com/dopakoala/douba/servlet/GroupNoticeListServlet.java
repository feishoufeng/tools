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

import com.dopakoala.douba.entity.GroupTopic;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IGroupTopicService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ConstantsUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class GroupNoticeListServlet
 */
@WebServlet("/group/noticelist")
public class GroupNoticeListServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IGroupTopicService groupTopicService;
	@Autowired
	private IUserService userService;

	/**
	 * Default constructor.
	 */
	public GroupNoticeListServlet() {
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

			String token = "", type = "";
			int uid = 0, page = 1, pagesize = 20, count = 0, gid = 0;
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
					if (page >= 1) {
						page -= page;
					}
				}
				if (!parameters.isNullObject() && parameters.containsKey("pagesize")) {
					pagesize = parameters.getInt("pagesize");
				}
				if (!parameters.isNullObject() && parameters.containsKey("type")) {
					type = parameters.getString("type");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			List<GroupTopic> groupTopicList = new ArrayList<>();
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						// 判断传入的跑团id是否合法
						if (gid > 0) {
							// 判断传入公告类型参数是否合法
							if (ConstantsUtils.NOTICE_TYPE.indexOf(type) != -1) {
								count = this.groupTopicService.selectByLimit(gid, 0, 0, type).size();
								groupTopicList = this.groupTopicService.selectByLimit(gid, page * pagesize, pagesize,
										type);
							} else {
								errmsg = "请求参数不合法！";
								errcode = 600;
							}
						} else {
							errmsg = "传入的跑团id不合法！";
							errcode = 600;
						}
					} else {
						errmsg = "账户登录权限过期，请重新登录";
						errcode = 700;
					}
				} else {
					errmsg = "账户id不存在！";
					errcode = 600;
				}
			} else {
				errmsg = "请求时间戳异常，请重试！";
				errcode = 200;
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
			dataMap.put("list", JSONArray.fromObject(groupTopicList));
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}
}

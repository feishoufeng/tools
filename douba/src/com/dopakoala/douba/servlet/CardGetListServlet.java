package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.dopakoala.douba.entity.GroupStatistic;
import com.dopakoala.douba.entity.GroupUser;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IGroupStatisticService;
import com.dopakoala.douba.service.IGroupUserService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserEditUserServlet
 */
@WebServlet("/card/getlist")
public class CardGetListServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupStatisticService groupStatisticService;
	@Autowired
	private IGroupUserService groupUserService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CardGetListServlet() {
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
			int uid = 0, gid = 0, type = 0, page = 0, pagesize = 10;
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
				if (!parameters.isNullObject() && parameters.containsKey("page")) {
					page = parameters.getInt("page");
					if (page > 0) {
						page--;
					}
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
						if (gid > 0) {
							if (type < 1 || type > 3) {
								errmsg = "统计类型数据不合法！";
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

			int count = 0;
			List<GroupStatistic> list = new ArrayList<GroupStatistic>();
			List<GroupStatistic> listTemp = new ArrayList<GroupStatistic>();
			List<GroupStatistic> groupStatisticList = new ArrayList<GroupStatistic>();
			GroupStatistic groupStatistic = new GroupStatistic();
			GroupUser groupUser = new GroupUser();
			if (errcode == 0) {
				String sid = "";
				Date date = new Date();
				String year = ConvertUtils.formatDateToString(date, 4);
				switch (type) {
				case 1:
					// 周
					int weekOfYear = AchieveUtils.getYearWeek(date);
					sid = year + weekOfYear;
					break;
				case 2:
					// 月
					sid = ConvertUtils.formatDateToString(date, 5);
					break;
				case 3:
					// 年
					sid = year;
					break;
				}

				GroupStatistic selectGroupStatistic = new GroupStatistic();
				selectGroupStatistic.setGid(gid);
				selectGroupStatistic.setPage(page * pagesize);
				selectGroupStatistic.setPagesize(pagesize);
				selectGroupStatistic.setSid(sid);
				selectGroupStatistic.setType(type);
				// 获取列表
				list = this.groupStatisticService.selectByUidGidSidType(selectGroupStatistic);
				// 获取排名列表总数
				GroupStatistic selectGroupStatisticTemp = new GroupStatistic();
				selectGroupStatisticTemp.setGid(gid);
				selectGroupStatisticTemp.setPage(0);
				selectGroupStatisticTemp.setPagesize(0);
				selectGroupStatisticTemp.setSid(sid);
				selectGroupStatisticTemp.setType(type);
				// 获取所有排名列表
				listTemp = this.groupStatisticService.selectByUidGidSidType(selectGroupStatisticTemp);
				if (listTemp != null && listTemp.size() > 0) {
					count = listTemp.size();
				}
				// 获取当前用户排名信息
				selectGroupStatistic.setUid(uid);
				groupStatisticList = this.groupStatisticService.selectByUidGidSidType(selectGroupStatistic);
				if (groupStatisticList != null && groupStatisticList.size() > 0) {
					groupStatistic = groupStatisticList.get(0);
				}

				// 获取人员信息
				GroupUser selectGroupUser = new GroupUser();
				selectGroupUser.setUid(uid);
				selectGroupUser.setGid(gid);
				groupUser = this.groupUserService.selectByUidGid(selectGroupUser);

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
			dataMap.put("list", list);
			dataMap.put("groupStatistic", groupStatistic);
			dataMap.put("userInfo", groupUser);
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

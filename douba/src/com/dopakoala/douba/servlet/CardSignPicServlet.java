package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.sql.Timestamp;
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

import com.dopakoala.douba.entity.GroupUserSignLog;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IGroupUserSignLogService;
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
@WebServlet("/card/signpic")
public class CardSignPicServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupUserSignLogService groupUserSignLogService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CardSignPicServlet() {
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

			String token = "", sid = "";
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
				if (!parameters.isNullObject() && parameters.containsKey("sid")) {
					sid = parameters.getString("sid");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						if (gid > 0) {
							if (type >= 1 && type <= 3) {
								if (CheckUtils.isNull(sid)) {
									errmsg = "统计id不合法！";
									errcode = 600;
								}
							} else {
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
			List<GroupUserSignLog> list = new ArrayList<GroupUserSignLog>();
			List<GroupUserSignLog> listTemp = new ArrayList<GroupUserSignLog>();
			Timestamp starttime = null;
			Timestamp endtime = null;
			Date startDate = null;
			Date endDate = null;
			if (errcode == 0) {
				switch (type) {
				case 1:
					String weekPeriod = AchieveUtils.getWeekPeriod(sid);
					if (!CheckUtils.isNull(weekPeriod)) {
						String[] strs = weekPeriod.split(",");
						startDate = ConvertUtils.parseStringToDate(strs[0], 4);
						endDate = ConvertUtils.parseStringToDate(strs[1], 4);
					}
					// 周
					break;
				case 2:
					String monthPeriod = AchieveUtils.getMonthPeriod(sid);
					if (!CheckUtils.isNull(monthPeriod)) {
						String[] strs = monthPeriod.split(",");
						startDate = ConvertUtils.parseStringToDate(strs[0], 4);
						endDate = ConvertUtils.parseStringToDate(strs[1], 4);
					}
					// 月
					break;
				case 3:
					String yearPeriod = AchieveUtils.getYearPeriod(sid);
					if (!CheckUtils.isNull(yearPeriod)) {
						String[] strs = yearPeriod.split(",");
						startDate = ConvertUtils.parseStringToDate(strs[0], 4);
						endDate = ConvertUtils.parseStringToDate(strs[1], 4);
					}
					// 年
					break;
				}
				if (startDate != null && endDate != null) {
					starttime = new Timestamp(startDate.getTime());
					endtime = new Timestamp(endDate.getTime());

					GroupUserSignLog selectGroupUserSignLog = new GroupUserSignLog();
					selectGroupUserSignLog.setStarttime(starttime);
					selectGroupUserSignLog.setEndtime(endtime);
					selectGroupUserSignLog.setUid(uid);
					selectGroupUserSignLog.setGid(gid);
					selectGroupUserSignLog.setPage(page * pagesize);
					selectGroupUserSignLog.setPagesize(pagesize);
					list = this.groupUserSignLogService.selectListByUidGidSE(selectGroupUserSignLog);

					GroupUserSignLog selectGroupUserSignLogTemp = new GroupUserSignLog();
					selectGroupUserSignLogTemp.setStarttime(starttime);
					selectGroupUserSignLogTemp.setEndtime(endtime);
					selectGroupUserSignLogTemp.setUid(uid);
					selectGroupUserSignLogTemp.setGid(gid);
					selectGroupUserSignLogTemp.setPage(0);
					selectGroupUserSignLogTemp.setPagesize(0);
					listTemp = this.groupUserSignLogService.selectListByUidGidSE(selectGroupUserSignLogTemp);
					if (listTemp != null) {
						count = listTemp.size();
					}
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
			dataMap.put("list", list);
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

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

import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.entity.UserMoneyLog;
import com.dopakoala.douba.service.IUserMoneyLogService;
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
@WebServlet("/cash/water")
public class CashWaterServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IUserMoneyLogService userMoneyLogService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CashWaterServlet() {
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

			String token = "", month = "";
			int uid = 0, page = 0, pagesize = 10;
			long timestamp = 0;
			Timestamp date1 = null, date2 = null;

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
				if (!parameters.isNullObject() && parameters.containsKey("page")) {
					page = parameters.getInt("page");
					if (page > 0) {
						page--;
					}
				}
				if (!parameters.isNullObject() && parameters.containsKey("pagesize")) {
					pagesize = parameters.getInt("pagesize");
				}
				if (!parameters.isNullObject() && parameters.containsKey("month")) {
					month = parameters.getString("month");
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

			List<UserMoneyLog> logList = new ArrayList<UserMoneyLog>();
			int count = 0;
			if (errcode == 0) {
				if (CheckUtils.isNull(month)) {
					month = ConvertUtils.formatDateToString(new Date(), 5);
				}
				String period = AchieveUtils.getMonthPeriod(ConvertUtils.parseStringToDate(month, 5));
				if (!CheckUtils.isNull(period)) {
					date1 = new Timestamp(ConvertUtils.parseStringToDate(period.split(",")[0], 4).getTime());
					date2 = new Timestamp(ConvertUtils.parseStringToDate(period.split(",")[1], 4).getTime());
				}

				UserMoneyLog selectUserMoneyLog = new UserMoneyLog();
				selectUserMoneyLog.setUid(uid);
				selectUserMoneyLog.setPage(page);
				selectUserMoneyLog.setPagesize(page * pagesize);
				selectUserMoneyLog.setDate1(date1);
				selectUserMoneyLog.setDate2(date2);
				logList = this.userMoneyLogService.selectAllByUid(selectUserMoneyLog);

				UserMoneyLog selectUserMoneyLogTemp = new UserMoneyLog();
				selectUserMoneyLogTemp.setUid(uid);
				selectUserMoneyLogTemp.setPage(0);
				selectUserMoneyLogTemp.setPagesize(0);
				selectUserMoneyLog.setDate1(date1);
				selectUserMoneyLog.setDate2(date2);
				count = this.userMoneyLogService.selectAllByUid(selectUserMoneyLogTemp).size();
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);

			if (logList != null) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("count", count);
				dataMap.put("list", logList);
				resultMap.put("data", JSONObject.fromObject(dataMap));
			}

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}
}
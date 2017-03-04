package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.dopakoala.douba.entity.GroupCardSign;
import com.dopakoala.douba.entity.GroupPace;
import com.dopakoala.douba.entity.GroupUserSignLog;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IAppConfigService;
import com.dopakoala.douba.service.IGroupCardSignService;
import com.dopakoala.douba.service.IGroupPaceService;
import com.dopakoala.douba.service.IGroupUserSignLogService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserEditUserServlet
 */
@WebServlet("/card/getconf")
public class CardGetConfServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupUserSignLogService groupUserSignLogService;
	@Autowired
	private IAppConfigService appConfigService;
	@Autowired
	private IGroupCardSignService groupCardSignService;
	@Autowired
	private IGroupPaceService groupPaceService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CardGetConfServlet() {
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

			String headerAccount = "";
			int cardPeriod = 0;
			int cardSpacePeriod = 0;
			long endTime = 0;
			double minDistance = 0;
			int minPace = 0;
			if (errcode == 0) {
				GroupCardSign groupCardSign = this.groupCardSignService.selectLasted(gid);
				if (groupCardSign != null) {
					endTime = groupCardSign.getEndTime();
				}

				// 获取统计的最后一个打卡人打卡时间
				GroupUserSignLog groupUserSignLog = this.groupUserSignLogService.selectLastedLogByGid(gid);
				// 获取上一张卡群里统计跑步量
				GroupUserSignLog groupUserSignLogTemp = this.groupUserSignLogService.selectTotalByGid(gid);

				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -1);
				String timeStr = ConvertUtils.formatDateToString(calendar.getTime(), 2);

				if (groupUserSignLog != null && groupUserSignLogTemp != null) {
					// Long time = groupUserSignLog.getCreateTime().getTime();
					// String timeStr = ConvertUtils.formatDateToString(new
					// Date(time), 3);
					double miles = groupUserSignLogTemp.getRunmiles();
					headerAccount = "截止到 " + timeStr + "，跑团累积跑了" + miles + "公里！";
				} else {
					headerAccount = "截止到 " + timeStr + " ，跑团累积跑了0公里！";
				}
				// 获取开卡最大有效期限
				cardPeriod = AchieveUtils.getIntAppConfig("card_period", appConfigService);
				// 获取最大间隔时间
				cardSpacePeriod = AchieveUtils.getIntAppConfig("card_space_period", appConfigService);

				// 获取配速标准
				GroupPace groupPace = this.groupPaceService.selectByGid(gid);
				if (groupPace != null) {
					minDistance = groupPace.getMinDistance();
					minPace = groupPace.getMinPace();
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
			dataMap.put("cardPeriod", cardPeriod);
			dataMap.put("cardSpacePeriod", cardSpacePeriod);
			dataMap.put("headerAccount", headerAccount);
			dataMap.put("endTime", endTime);
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

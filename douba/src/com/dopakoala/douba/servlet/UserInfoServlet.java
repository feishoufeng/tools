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

import com.dopakoala.douba.entity.AreaNumber;
import com.dopakoala.douba.entity.Group;
import com.dopakoala.douba.entity.GroupUserSignLog;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IAreaNumberService;
import com.dopakoala.douba.service.IGroupService;
import com.dopakoala.douba.service.IGroupUserSignLogService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserInfoServlet
 */
@WebServlet("/user/getinfo")
public class UserInfoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IAreaNumberService areaNumberService;
	@Autowired
	private IGroupUserSignLogService groupUserSignLogService;
	@Autowired
	private IGroupService groupService;

	/**
	 * Default constructor.
	 */
	public UserInfoServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String errmsg = "";
		int errcode = 0;

		try {

			String token = "";
			int uid = 0, targetUid = 0;
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
				if (!parameters.isNullObject() && parameters.containsKey("targetUid")) {
					targetUid = parameters.getInt("targetUid");
				}

			}

			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				User user = this.userService.selectByPrimaryKey(uid);
				if (user != null) {
					String tempToken = user.getToken();
					if (!tempToken.equals(token)) {
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

			if (targetUid > 0) {
				uid = targetUid;
			}

			User user = this.userService.selectByPrimaryKey(uid);
			List<Group> paotuanList = new ArrayList<>();
			if (user != null) {
				String adressDetail = "";
				// 获取用户地址code
				String code = user.getAdress();
				if (!CheckUtils.isNull(code)) {
					AreaNumber area = this.areaNumberService.selectByCode(Integer.parseInt(code));
					if (area != null) {
						adressDetail = area.getValue();
						AreaNumber city = this.areaNumberService.selectByCode(area.getPid());
						if (city != null) {
							if (!adressDetail.contains(city.getValue())) {
								adressDetail = city.getValue() + adressDetail;
							}
							AreaNumber provience = this.areaNumberService.selectByCode(city.getPid());
							if (provience != null) {
								if (!adressDetail.contains(provience.getValue())) {
									adressDetail = provience.getValue() + adressDetail;
								}
							}
						}
					}
				}
				user.setAdressDetail(adressDetail);
				paotuanList = this.groupService.selectPaotuanByUid(uid);
			}

			double runMiles = 0;
			// 获取用户跑步公里数
			GroupUserSignLog groupUserSignLog = this.groupUserSignLogService.selectTotalByUid(uid);
			if (groupUserSignLog != null) {
				runMiles = groupUserSignLog.getRunmiles();
			}

			GroupUserSignLog groupUserSignLogTemp = this.groupUserSignLogService.selectStatisticByUid(uid);
			double totalmiles = groupUserSignLogTemp.getRunmiles();
			int signcount = groupUserSignLogTemp.getCount();
			
			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);
			// 返回应答数据
			if (user != null) {
				user.setRunMiles(runMiles);
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("userInfo", user);
				dataMap.put("totalmiles", totalmiles);
				dataMap.put("signcount", signcount);
				dataMap.put("paotuanList", paotuanList);
				resultMap.put("data", JSONObject.fromObject(dataMap));
			}

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

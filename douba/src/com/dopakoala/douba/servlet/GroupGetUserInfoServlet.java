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

import com.dopakoala.douba.entity.AreaNumber;
import com.dopakoala.douba.entity.Group;
import com.dopakoala.douba.entity.GroupUser;
import com.dopakoala.douba.entity.GroupUserSignLog;
import com.dopakoala.douba.entity.SimpleUser;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IAreaNumberService;
import com.dopakoala.douba.service.IGroupService;
import com.dopakoala.douba.service.IGroupUserService;
import com.dopakoala.douba.service.IGroupUserSignLogService;
import com.dopakoala.douba.service.ISimpleUserService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class GroupGetUserInfoServlet
 */
@WebServlet("/group/getuserinfo")
public class GroupGetUserInfoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IGroupUserService groupUserService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ISimpleUserService simpleUserService;
	@Autowired
	private IAreaNumberService areaNumberService;
	@Autowired
	private IGroupService groupService;
	@Autowired
	private IGroupUserSignLogService groupUserSignLogService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GroupGetUserInfoServlet() {
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
			int uid = 0, gid = 0, type = 0, searchUid = 0;
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
				if (!parameters.isNullObject() && parameters.containsKey("uid")) {
					searchUid = parameters.getInt("uid");
				}
				if (!parameters.isNullObject() && parameters.containsKey("gid")) {
					gid = parameters.getInt("gid");
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
					if (tempToken.equals(token)) {
						if (searchUid > 0) {
							if (gid < 1) {
								errmsg = "传入的gid不合法！";
								errcode = 600;
							}
						} else {
							errmsg = "传入的uid不合法！";
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

			SimpleUser simpleUser = null;
			GroupUser groupUser = null;
			List<Group> groupList = new ArrayList<Group>();
			if (errcode == 0) {
				if (type == 1) {
					simpleUser = this.simpleUserService.selectUser(searchUid, gid);
					if (simpleUser == null) {
						errmsg = "获取用户信息失败！";
						errcode = 400;
					}
				} else {
					// 获取用户参加的跑团信息
					Group selectGroup = new Group();
					selectGroup.setPage(0);
					selectGroup.setPagesize(4);
					selectGroup.setUid(searchUid);
					groupList = this.groupService.selectGroupByUid(selectGroup);
					GroupUser selectGroupUser = new GroupUser();
					selectGroupUser.setGid(gid);
					selectGroupUser.setUid(searchUid);
					groupUser = this.groupUserService.selectByUidGid(selectGroupUser);
					if (groupUser == null) {
						errmsg = "获取用户信息失败！";
						errcode = 400;
					} else {
						String adressDetail = "";
						// 获取用户信息
						User selectUser = this.userService.selectByPrimaryKey(searchUid);
						// 获取用户地址code
						String code = selectUser.getAdress();
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
						groupUser.setAdressDetail(adressDetail);
					}

				}
			}
			
			double runMiles = 0;
			// 获取用户跑步公里数
			GroupUserSignLog groupUserSignLog = this.groupUserSignLogService.selectTotalByUid(searchUid);
			if (groupUserSignLog != null) {
				runMiles = groupUserSignLog.getRunmiles();
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);
			// 返回应答数据
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if (errcode == 0) {
				GroupUserSignLog groupUserSignLogTemp = this.groupUserSignLogService.selectStatisticByUid(searchUid);
				double totalmiles = groupUserSignLogTemp.getRunmiles();
				int signcount = groupUserSignLogTemp.getCount();
				
				dataMap.put("totalmiles", totalmiles);
				dataMap.put("signcount", signcount);
				if (type == 1) {
					dataMap.put("userInfo", simpleUser);
				} else {
					dataMap.put("runMiles", runMiles);
					dataMap.put("userInfo", groupUser);
					dataMap.put("list", groupList);
					dataMap.put("count", groupList.size());
				}
			}

			resultMap.put("data", JSONObject.fromObject(dataMap));
			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

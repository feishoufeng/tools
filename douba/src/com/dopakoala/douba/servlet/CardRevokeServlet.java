package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.dopakoala.douba.entity.GroupCardSign;
import com.dopakoala.douba.entity.GroupUserSign;
import com.dopakoala.douba.entity.GroupUserSignLog;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IAppConfigService;
import com.dopakoala.douba.service.IGroupCardSignService;
import com.dopakoala.douba.service.IGroupStatisticService;
import com.dopakoala.douba.service.IGroupUserSignLogService;
import com.dopakoala.douba.service.IGroupUserSignService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserEditUserServlet
 */
@WebServlet("/card/revoke")
public class CardRevokeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupUserSignLogService groupUserSignLogService;
	@Autowired
	private IGroupCardSignService groupCardSignService;
	@Autowired
	private IGroupStatisticService groupStatisticService;
	@Autowired
	private IGroupUserSignService groupUserSignService;
	@Autowired
	private IAppConfigService appConfigService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CardRevokeServlet() {
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
			int uid = 0, gid = 0, id = 0;
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
				if (!parameters.isNullObject() && parameters.containsKey("id")) {
					id = parameters.getInt("id");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						if (gid <= 0) {
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

			if (errcode == 0) {
				// 获取可撤销间隔时长
				int revokePeriod = AchieveUtils.getIntAppConfig("revoke_period", appConfigService);
				GroupUserSignLog groupUserSignLog = null;
				if (id > 0) {
					// 获取所选的打卡记录
					groupUserSignLog = this.groupUserSignLogService.selectByPrimaryKey(id);
				} else {
					// 获取最新打卡记录
					GroupUserSignLog selectGroupUserSignLog = new GroupUserSignLog();
					selectGroupUserSignLog.setUid(uid);
					selectGroupUserSignLog.setGid(gid);
					groupUserSignLog = this.groupUserSignLogService.selectLastedLogByUidGid(selectGroupUserSignLog);
				}
				if (groupUserSignLog != null) {
					int cardId = groupUserSignLog.getCardId();
					int signId = groupUserSignLog.getSignId();
					int signUid = groupUserSignLog.getUid();
					double runmiles = groupUserSignLog.getRunmiles();
					long time = groupUserSignLog.getCreateTime().getTime();
					long period = groupUserSignLog.getPeriod();

					// 获取最新有效卡
					GroupCardSign selectGroupCardSign = new GroupCardSign();
					selectGroupCardSign.setTime(AchieveUtils.getMillis());
					selectGroupCardSign.setGid(gid);
					GroupCardSign groupCardSign = this.groupCardSignService.selectByTimeGid(selectGroupCardSign);
					if (groupCardSign != null) {
						// 是否为当前所开有效卡
						if (groupCardSign.getId() == cardId) {
							// 是否撤销时间在允许间隔时间内
							if (AchieveUtils.getMillis() - time <= revokePeriod * 60 * 1000) {
								GroupUserSignLog updateGroupUserSignLog = new GroupUserSignLog();
								updateGroupUserSignLog.setCardId(cardId);
								updateGroupUserSignLog.setSignId(signId);
								updateGroupUserSignLog.setUid(signUid);
								updateGroupUserSignLog.setRevokemiles(runmiles);
								// 更新撤销人后面自己所打卡的月跑量
								this.groupUserSignLogService.updateByCardidSignidRevokemiles(updateGroupUserSignLog);
								// 将在撤销人后面打卡的人打卡id都减1
								this.groupUserSignLogService.updateByCardidSignid(updateGroupUserSignLog);

								// 更新卡的当前打卡id
								GroupCardSign updateGroupCardSign = new GroupCardSign();
								updateGroupCardSign.setId(groupCardSign.getId());
								updateGroupCardSign.setCurrSignid(groupCardSign.getCurrSignid() - 1);
								this.groupCardSignService.updateByPrimaryKeySelective(updateGroupCardSign);

								Date date = new Date(time);
								// 统计周1
								// 判断是否存在当前统计所需要的记录
								int weekOfYear = AchieveUtils.getYearWeek(date);
								String year = ConvertUtils.formatDateToString(date, 4);
								ServletUtils.account(gid, uid, year + weekOfYear, 1, -runmiles, -period,
										groupStatisticService);
								// 统计月2
								ServletUtils.account(gid, uid, ConvertUtils.formatDateToString(date, 5), 2, -runmiles,
										-period, groupStatisticService);
								// 统计年3
								ServletUtils.account(gid, uid, year, 3, -runmiles, -period, groupStatisticService);
								// 更新跑步计划
								GroupUserSign selectGroupUserSign = new GroupUserSign();
								selectGroupUserSign.setGid(gid);
								selectGroupUserSign.setUid(uid);
								GroupUserSign groupUserSign = this.groupUserSignService
										.selectByGidUid(selectGroupUserSign);
								if (groupUserSign != null) {
									GroupUserSign updateGroupUserSign = new GroupUserSign();
									updateGroupUserSign.setId(groupUserSign.getId());
									updateGroupUserSign.setLastdate(date.getTime());
									updateGroupUserSign.setNowmiles(groupUserSign.getNowmiles() - runmiles);
									this.groupUserSignService.updateByPrimaryKeySelective(updateGroupUserSign);
								}
								// 删除打卡记录
								this.groupUserSignLogService.deleteByPrimaryKey(groupUserSignLog.getId());
								errmsg = "撤销打卡记录成功！";
							} else {
								errmsg = "当前无打卡记录！";
							}
						} else {
							errmsg = "当前无打卡记录可撤销！";
						}
					} else {
						errmsg = "当前无有效打卡记录可撤销！";
					}
				} else {
					errmsg = "无打卡记录！";
				}
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

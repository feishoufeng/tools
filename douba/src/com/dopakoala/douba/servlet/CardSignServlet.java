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
import com.dopakoala.douba.entity.GroupPace;
import com.dopakoala.douba.entity.GroupUser;
import com.dopakoala.douba.entity.GroupUserSign;
import com.dopakoala.douba.entity.GroupUserSignLog;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.jpush.PushAlert;
import com.dopakoala.douba.service.IGroupCardSignService;
import com.dopakoala.douba.service.IGroupPaceService;
import com.dopakoala.douba.service.IGroupStatisticService;
import com.dopakoala.douba.service.IGroupUserService;
import com.dopakoala.douba.service.IGroupUserSignLogService;
import com.dopakoala.douba.service.IGroupUserSignService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ConstantsUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserEditUserServlet
 */
@WebServlet("/card/sign")
public class CardSignServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupStatisticService groupStatisticService;
	@Autowired
	private IGroupUserSignLogService groupUserSignLogService;
	@Autowired
	private IGroupUserSignService groupUserSignService;
	@Autowired
	private IGroupCardSignService groupCardSignService;
	@Autowired
	private IGroupUserService groupUserService;
	@Autowired
	private IGroupPaceService groupPaceService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CardSignServlet() {
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

			String token = "", type = "";
			int uid = 0, gid = 0, logId = 0, pace = 10;
			long timestamp = 0, period = 0;
			double runmiles = 0, altitude = 0, currPace = 0, distance = 0;

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
				if (!parameters.isNullObject() && parameters.containsKey("runmiles")) {
					runmiles = parameters.getDouble("runmiles");
				}
				if (!parameters.isNullObject() && parameters.containsKey("gid")) {
					gid = parameters.getInt("gid");
				}
				if (!parameters.isNullObject() && parameters.containsKey("period")) {
					period = parameters.getLong("period");
				}
				if (!parameters.isNullObject() && parameters.containsKey("type")) {
					type = parameters.getString("type");
				}
				if (!parameters.isNullObject() && parameters.containsKey("altitude")) {
					altitude = parameters.getDouble("altitude");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						if (gid > 0) {
							if (runmiles >= 0) {
								if (!CheckUtils.isNull(type)) {
									if (period < 0) {
										errmsg = "跑步时长数据不合法！";
										errcode = 600;
									}
								} else {
									errmsg = "跑步类型数据不合法！";
									errcode = 600;
								}
							} else {
								errmsg = "跑步里程数据不合法！";
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

			if (errcode == 0) {
				// 统一时间
				Date date = new Date();
				// 获取当前群有效的卡信息
				GroupCardSign selectGroupCardSign = new GroupCardSign();
				selectGroupCardSign.setTime(date.getTime());
				selectGroupCardSign.setGid(gid);
				GroupCardSign groupCardSign = this.groupCardSignService.selectByTimeGid(selectGroupCardSign);
				if (groupCardSign != null) {
					// 判断当前用户是否已打过卡
					// GroupUserSignLog selectGroupUserSignLog = new
					// GroupUserSignLog();
					// selectGroupUserSignLog.setCardId(groupCardSign.getId());
					// selectGroupUserSignLog.setUid(uid);
					// GroupUserSignLog groupUserSignLog =
					// this.groupUserSignLogService
					// .selectByUidCardId(selectGroupUserSignLog);
					// if (groupUserSignLog == null) {
					// 判断当前卡是否过了有效卡号
					if (groupCardSign.getCurrSignid() <= groupCardSign.getEndId()) {
						// 获取打卡配速上限
						GroupPace groupPace = this.groupPaceService.selectByGid(gid);
						if (groupPace != null) {
							pace = groupPace.getMinPace();
							distance = groupPace.getMinDistance();
						} else {
							pace = 0;
							distance = 0;
						}

						currPace = period / 1000 / runmiles;
						if (currPace <= pace || pace == 0) {
							if (runmiles >= distance) {
								// 插入打卡记录
								GroupUserSignLog insertGroupUserSignLog = new GroupUserSignLog();
								insertGroupUserSignLog.setGid(gid);
								insertGroupUserSignLog.setUid(uid);
								insertGroupUserSignLog.setRunmiles(runmiles);
								insertGroupUserSignLog.setCardId(groupCardSign.getId());
								insertGroupUserSignLog.setSignId(groupCardSign.getCurrSignid());
								insertGroupUserSignLog.setType(type);
								insertGroupUserSignLog.setAltitude(altitude);
								insertGroupUserSignLog.setPeriod(period);
								this.groupUserSignLogService.insertSelective(insertGroupUserSignLog);
								// 获取插入记录id
								logId = insertGroupUserSignLog.getId();
								// 在卡信息中增加当前有效id
								GroupCardSign updateGroupCardSign = new GroupCardSign();
								updateGroupCardSign.setId(groupCardSign.getId());
								updateGroupCardSign.setCurrSignid(groupCardSign.getCurrSignid() + 1);
								this.groupCardSignService.updateByPrimaryKeySelective(updateGroupCardSign);
								// 统计周1
								// 判断是否存在当前统计所需要的记录
								int weekOfYear = AchieveUtils.getYearWeek(date);
								String year = ConvertUtils.formatDateToString(date, 4);
								ServletUtils.account(gid, uid, year + weekOfYear, 1, runmiles, period,
										groupStatisticService);
								// 统计月2
								ServletUtils.account(gid, uid, ConvertUtils.formatDateToString(date, 5), 2, runmiles,
										period, groupStatisticService);
								// 统计年3
								ServletUtils.account(gid, uid, year, 3, runmiles, period, groupStatisticService);
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
									updateGroupUserSign.setNowmiles(runmiles + groupUserSign.getNowmiles());
									this.groupUserSignService.updateByPrimaryKeySelective(updateGroupUserSign);
									
									//更新打卡记录中的打卡月跑量
									GroupUserSignLog updateGroupUserSignLog = new GroupUserSignLog();
									updateGroupUserSignLog.setId(logId);
									updateGroupUserSignLog.setNowmiles(runmiles + groupUserSign.getNowmiles());
									this.groupUserSignLogService.updateByPrimaryKeySelective(updateGroupUserSignLog);
								}
							} else {
								errmsg = "打卡失败,当前里程为：" + runmiles + " Km,小于预设 " + distance + " min/Km";
								errcode = 300;
							}
						} else {
							errmsg = "打卡失败,当前配速为：" + currPace + " min/Km,大于预设 " + pace + " min/Km";
							errcode = 300;
						}
					} else {
						errmsg = "当前所开卡已打满！";
						errcode = 300;
					}
					// } else {
					// errmsg = "已在当前所开卡中成功打卡！";
					// errcode = 300;
					// }
				} else {
					GroupUser groupUser = this.groupUserService.selectOpenByGid(gid);
					if (groupUser != null) {
						errmsg = "当前群尚未开立新卡，请等待开卡人员：" + groupUser.getNickname() + " 开立新卡后，再进行打卡！";
						// 发送提示信息
						Map<String, String> extras = new HashMap<String, String>();
						extras.put("msgType", ConstantsUtils.OPEN_CARD_TIP);
						String alert = "亲爱的用户: " + groupUser.getNickname() + " 您好！您所在的群: " + groupUser.getGroupName()
								+ " 尚未开立新卡，请您及时进行开卡！";
						PushAlert.push(groupUser.getUid(), alert, "开卡提示", extras);
					} else {
						errmsg = "当前群尚未开立新卡，请等待开卡人员开立新卡后，再进行打卡！";
					}
					errcode = 300;
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
			dataMap.put("id", logId);
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

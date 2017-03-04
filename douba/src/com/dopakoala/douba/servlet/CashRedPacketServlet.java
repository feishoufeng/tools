package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.dopakoala.douba.entity.Hongbao;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.entity.UserMoneyLog;
import com.dopakoala.douba.quartz.QuartzPersistent;
import com.dopakoala.douba.service.IAppConfigService;
import com.dopakoala.douba.service.IHongbaoService;
import com.dopakoala.douba.service.IUserMoneyLogService;
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
@WebServlet("/cash/redpacket")
public class CashRedPacketServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String HongBaoType = "common,group_c,group_p";

	@Autowired
	private IUserService userService;
	@Autowired
	private IHongbaoService hongbaoService;
	@Autowired
	private IAppConfigService appConfigService;
	@Autowired
	private IUserMoneyLogService userMoneyLogService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CashRedPacketServlet() {
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

			String token = "", type = "", remark = "", password = "";
			int uid = 0, gid = 0, maxnum = 0;
			long timestamp = 0, money = 0, limitMoney = 0;

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
				if (!parameters.isNullObject() && parameters.containsKey("money")) {
					money = parameters.getLong("money");
				}
				if (!parameters.isNullObject() && parameters.containsKey("limitMoney")) {
					limitMoney = parameters.getLong("limitMoney");
				}
				if (!parameters.isNullObject() && parameters.containsKey("maxnum")) {
					maxnum = parameters.getInt("maxnum");
				}
				if (!parameters.isNullObject() && parameters.containsKey("type")) {
					type = parameters.getString("type");
				}
				if (!parameters.isNullObject() && parameters.containsKey("remark")) {
					remark = parameters.getString("remark");
				}
				if (!parameters.isNullObject() && parameters.containsKey("password")) {
					password = parameters.getString("password");
				}
				if (!parameters.isNullObject() && parameters.containsKey("password")) {
					password = parameters.getString("password");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						if (!CheckUtils.isNull(type) && HongBaoType.contains(type)) {
							if (type.equals("group_c") || type.equals("group_p")) {
								if (gid > 0) {
									if (maxnum > 0) {
										if (type.equals("group_c")) {
											if (limitMoney <= 0) {
												errcode = 600;
												errmsg = "limitMoney数据错误！";
											}
										}
									} else {
										errcode = 600;
										errmsg = "maxnum数据错误！";
									}
								} else {
									errcode = 600;
									errmsg = "群id错误！";
								}
							}
						} else {
							errmsg = "红包类型错误！";
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

			int hongbaoId = 0;
			if (errcode == 0) {
				// 判断用户账户余额是否足以支付红包
				long userMoney = user.getMoney();
				if (userMoney >= money) {
					Calendar calendar = Calendar.getInstance();
					// 插入红包记录
					Hongbao insertHongbao = new Hongbao();
					insertHongbao.setCode(ConvertUtils.formatDateToString(new Date(), 8) + uid);
					insertHongbao.setGid(gid);
					insertHongbao.setUid(uid);
					insertHongbao.setMaxnum(maxnum);
					insertHongbao.setMoney(money);
					insertHongbao.setLeftmoney(money);
					insertHongbao.setRemark(remark);
					insertHongbao.setPassword(password);
					insertHongbao.setLimitMoney(limitMoney);
					insertHongbao.setType(type);
					insertHongbao.setCreateTime(new Timestamp(calendar.getTimeInMillis()));
					int n = this.hongbaoService.insertSelective(insertHongbao);
					if (n == 1) {
						hongbaoId = insertHongbao.getId();
						if (hongbaoId > 0) {
							// 插入流水记录
							UserMoneyLog insertUserMoneyLog = new UserMoneyLog();
							insertUserMoneyLog.setAction("发放红包");
							insertUserMoneyLog.setType(-1);
							insertUserMoneyLog.setUid(user.getUid());
							insertUserMoneyLog.setMoney(money);
							insertUserMoneyLog.setLeftmoney(user.getMoney() - money);
							int m = this.userMoneyLogService.insertSelective(insertUserMoneyLog);
							if (m == 1) {
								// 更新用户账户余额
								User updateUser = new User();
								updateUser.setUid(user.getUid());
								updateUser.setMoney(user.getMoney() - money);
								int q = this.userService.updateByPrimaryKeySelective(updateUser);
								if (q == 1) {
									// 更新红包记录状态
									Hongbao updateHongbao = new Hongbao();
									updateHongbao.setId(hongbaoId);
									updateHongbao.setStatus(1);
									int p = this.hongbaoService.updateByPrimaryKeySelective(updateHongbao);
									if (p == 0) {
										errmsg = "更新红包记录状态失败！";
										errcode = 300;
									} else {
										// 设置红包自动返回时间
										int return_period = AchieveUtils.getIntAppConfig("redPacket_return_period",
												appConfigService);
										calendar.add(Calendar.HOUR, return_period);
										String time = ConvertUtils.formatDateToString(calendar.getTime(), 11);
										// 增加红包定时退回任务
										QuartzPersistent.addSchedule(hongbaoId, time,
												ConstantsUtils.QUARTZ_RETURN_REDPACKET_FLAG);
									}
								} else {
									errmsg = "更新用户账户余额失败！";
									errcode = 300;
								}
							} else {
								errmsg = "插入流水记录失败！";
								errcode = 300;
							}
						} else {
							errmsg = "获取红包记录id失败！";
							errcode = 300;
						}
					} else {
						errmsg = "插入红包记录失败！";
						errcode = 300;
					}
				} else {
					errmsg = "账户余额不足！";
					errcode = 300;
				}
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);

			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("hongbaoId", hongbaoId);
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

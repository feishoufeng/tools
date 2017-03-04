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

import com.dopakoala.douba.entity.Hongbao;
import com.dopakoala.douba.entity.HongbaoLog;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.entity.UserMoneyLog;
import com.dopakoala.douba.service.IHongbaoLogService;
import com.dopakoala.douba.service.IHongbaoService;
import com.dopakoala.douba.service.IUserMoneyLogService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserEditUserServlet
 */
@WebServlet("/cash/getpacket")
public class CashGetPacketServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IHongbaoService hongbaoService;
	@Autowired
	private IHongbaoLogService hongbaoLogService;
	@Autowired
	private IUserMoneyLogService userMoneyLogService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CashGetPacketServlet() {
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

			String token = "", password = "";
			int uid = 0, hongbaoId = 0, isEnd = 0;
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
				if (!parameters.isNullObject() && parameters.containsKey("hongbaoId")) {
					hongbaoId = parameters.getInt("hongbaoId");
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
						if (hongbaoId <= 0) {
							errmsg = "hongbaoId错误！";
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
				// 获取红包信息
				Hongbao hongbao = this.hongbaoService.selectByPrimaryKey(hongbaoId);
				if (hongbao != null) {
					int status = hongbao.getStatus();
					if (status == 1) {
						// 判断红包是否已抢过
						HongbaoLog selectHongbaoLog = new HongbaoLog();
						selectHongbaoLog.setUid(uid);
						selectHongbaoLog.setHongbaoId(hongbaoId);
						HongbaoLog hongbaoLog = this.hongbaoLogService.selectByIdUid(selectHongbaoLog);
						// 判断用户是否抢过红包
						if (hongbaoLog == null) {
							// 获取当前红包是否有密码
							if (hongbao.getPassword().equals(password)) {
								if (hongbao.getLeftmoney() > 0) {
									String type = hongbao.getType();
									switch (type) {
									case "common":
										// 获取红包发送人uid
										int sendUid = hongbao.getUid();
										if (sendUid != uid) {
											long money = hongbao.getLeftmoney();
											// 插入红包领取记录
											HongbaoLog insertHongbaoLog = new HongbaoLog();
											insertHongbaoLog.setHongbaoId(hongbaoId);
											insertHongbaoLog.setUid(user.getUid());
											insertHongbaoLog.setMoney(money);
											int n = this.hongbaoLogService.insertSelective(insertHongbaoLog);
											if (n == 1) {
												// 更新红包余额
												Hongbao updateHongbao = new Hongbao();
												updateHongbao.setId(hongbaoId);
												updateHongbao.setLeftmoney(hongbao.getLeftmoney() - money);
												updateHongbao.setStatus(2);
												updateHongbao.setTime(
														new Date().getTime() - hongbao.getCreateTime().getTime());
												isEnd = 1;

												int m = this.hongbaoService.updateByPrimaryKeySelective(updateHongbao);
												if (m == 1) {
													// 插入流水记录
													UserMoneyLog insertUserMoneyLog = new UserMoneyLog();
													insertUserMoneyLog.setAction("领取红包");
													insertUserMoneyLog.setType(1);
													insertUserMoneyLog.setUid(user.getUid());
													insertUserMoneyLog.setMoney(money);
													insertUserMoneyLog.setLeftmoney(user.getMoney() + money);
													int r = this.userMoneyLogService
															.insertSelective(insertUserMoneyLog);
													if (r == 1) {
														// 更新用户账户余额
														User updateUser = new User();
														updateUser.setUid(user.getUid());
														updateUser.setMoney(user.getMoney() + money);
														int q = this.userService
																.updateByPrimaryKeySelective(updateUser);
														if (q == 0) {
															errmsg = "更新用户账户余额失败！";
														}
													} else {
														errmsg = "插入流水记录失败！";
													}
												} else {
													errmsg = "更新红包状态及余额失败！";
												}
											} else {
												errmsg = "插入红包领取记录失败！";
											}
										}
										break;
									case "group_c":
									case "group_p":
										// 获取已领取的红包人数
										long money = 0;
										int count = this.hongbaoLogService.selectByHongbaoId(hongbaoId).size();
										if (count < hongbao.getMaxnum()) {
											if ("group_p".equals(type)) {
												if (hongbao.getMaxnum() - count == 1) {
													money = hongbao.getLeftmoney();
												} else {
													int rate = AchieveUtils.getRandomCode(1, 99);
													money = hongbao.getLeftmoney() * rate / 100;
												}
											}
											if ("group_c".equals(type)) {
												if (hongbao.getMaxnum() - count == 1) {
													money = hongbao.getLimitMoney();
												}
											}
											// 是否可领取红包标志
											int flag = 0;
											// 插入红包领取记录
											HongbaoLog insertHongbaoLog = new HongbaoLog();
											insertHongbaoLog.setHongbaoId(hongbaoId);
											insertHongbaoLog.setUid(user.getUid());
											insertHongbaoLog.setMoney(money);
											int n = this.hongbaoLogService.insertSelective(insertHongbaoLog);
											if (n == 1) {
												// 更新红包余额
												Hongbao updateHongbao = new Hongbao();
												updateHongbao.setId(hongbaoId);
												updateHongbao.setLeftmoney(hongbao.getLeftmoney() - money);

												if ("group_c".equals(type)) {
													// 自己不可以领取
													if (hongbao.getUid() != uid) {
														flag = 1;
													}
												}

												if ("group_p".equals(type)) {
													flag = 1;

												}

												if (flag == 1) {
													// 判断是否为最后一位用户抢红包
													if (hongbao.getMaxnum() - count == 1) {
														// 更新红包状态为已抢完
														updateHongbao.setStatus(2);
														// 更新抢完时间
														updateHongbao.setTime(new Date().getTime()
																- hongbao.getCreateTime().getTime());
														isEnd = 1;
													}

													int m = this.hongbaoService
															.updateByPrimaryKeySelective(updateHongbao);
													if (m == 1) {
														// 插入流水记录
														UserMoneyLog insertUserMoneyLog = new UserMoneyLog();
														insertUserMoneyLog.setAction("领取红包");
														insertUserMoneyLog.setType(1);
														insertUserMoneyLog.setUid(user.getUid());
														insertUserMoneyLog.setMoney(money);
														insertUserMoneyLog.setLeftmoney(user.getMoney() + money);
														int r = this.userMoneyLogService
																.insertSelective(insertUserMoneyLog);
														if (r == 1) {
															// 更新用户账户余额
															User updateUser = new User();
															updateUser.setUid(user.getUid());
															updateUser.setMoney(user.getMoney() + money);
															int q = this.userService
																	.updateByPrimaryKeySelective(updateUser);
															if (q == 0) {
																errmsg = "更新用户账户余额失败！";
															}
														} else {
															errmsg = "插入流水记录失败！";
														}
													} else {
														errmsg = "更新红包状态及余额失败！";
													}
												}
											} else {
												errmsg = "插入红包领取记录失败！";
											}
										}
										break;
									}
								}
							} else {
								errmsg = "红包口令错误！";
								errcode = 300;
							}
						}
					}
				} else {
					errmsg = "获取红包失败！";
					errcode = 600;
				}
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);

			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("isEnd", isEnd);
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}
}
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

import com.dopakoala.douba.entity.PayLog;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.entity.UserMoneyLog;
import com.dopakoala.douba.service.IPayLogService;
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
@WebServlet("/cash/recharge")
public class CashRechargeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String payType = "weixin,advance,alipay";

	@Autowired
	private IUserService userService;
	@Autowired
	private IPayLogService payLogService;
	@Autowired
	private IUserMoneyLogService userMoneyLogService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CashRechargeServlet() {
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

			String token = "", orderId = "", type = "", outerid = "", tradestatus = "", param = "", returnparam = "";
			int uid = 0;
			long timestamp = 0, price = 0;

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
				if (!parameters.isNullObject() && parameters.containsKey("price")) {
					price = parameters.getInt("price");
				}
				if (!parameters.isNullObject() && parameters.containsKey("orderId")) {
					orderId = parameters.getString("orderId");
				}
				if (!parameters.isNullObject() && parameters.containsKey("type")) {
					type = parameters.getString("type");
				}
				if (!parameters.isNullObject() && parameters.containsKey("outerid")) {
					outerid = parameters.getString("outerid");
				}
				if (!parameters.isNullObject() && parameters.containsKey("tradestatus")) {
					tradestatus = parameters.getString("tradestatus");
				}
				if (!parameters.isNullObject() && parameters.containsKey("param")) {
					param = parameters.getString("param");
				}
				if (!parameters.isNullObject() && parameters.containsKey("returnparam")) {
					returnparam = parameters.getString("returnparam");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						if (price >= 0) {
							if (!CheckUtils.isNull(orderId)) {
								if (!CheckUtils.isNull(type) && payType.contains(type)) {
									if (!CheckUtils.isNull(outerid)) {
										if (!CheckUtils.isNull(tradestatus)) {
											if (!CheckUtils.isNull(param)) {
												if (CheckUtils.isNull(returnparam)) {
													errmsg = "returnparam数据不合法！";
													errcode = 600;
												}
											} else {
												errmsg = "param数据不合法！";
												errcode = 600;
											}
										} else {
											errmsg = "tradestatus数据不合法！";
											errcode = 600;
										}
									} else {
										errmsg = "outerid数据不合法！";
										errcode = 600;
									}
								} else {
									errmsg = "type数据不合法！";
									errcode = 600;
								}
							} else {
								errmsg = "orderId数据不合法！";
								errcode = 600;
							}
						} else {
							errmsg = "price数据不合法！";
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
				// 插入支付记录
				PayLog insertPayLog = new PayLog();
				String date = ConvertUtils.formatDateToString(new Date(), 8);
				int randomCode = AchieveUtils.getRandomCode(100, 999);
				long payid = ConvertUtils.longFormat(date + randomCode, 0);
				if (payid != 0) {
					insertPayLog.setPayid(payid);
					insertPayLog.setAddtime(AchieveUtils.getMillis());
					insertPayLog.setOrderId(orderId);
					insertPayLog.setOuterid(outerid);
					insertPayLog.setParam(returnparam);
					insertPayLog.setReturnparam(returnparam);
					insertPayLog.setType(type);
					insertPayLog.setPrice(price);
					insertPayLog.setUid(uid);
					insertPayLog.setTradestatus(tradestatus);
					int n = this.payLogService.insertSelective(insertPayLog);

					// 判断是否插入成功
					if (n == 1) {
						// 获取插入支付记录id
						if (payid > 0) {
							int flag = 0;
							switch (type) {
							case "weixin":
								// 判断是否支付成功
								if (tradestatus.equals("SUCCESS")) {
									flag = 1;
								}
								break;
							case "alipay":
								// 判断支付宝是否支付成功
								if (tradestatus.equals("T")) {
									flag = 1;
								}
								break;
							case "advance":
								break;
							}

							if (flag == 1) {
								// 插入流水记录
								UserMoneyLog insertUserMoneyLog = new UserMoneyLog();
								insertUserMoneyLog.setAction("充值");
								insertUserMoneyLog.setType(1);
								insertUserMoneyLog.setUid(user.getUid());
								insertUserMoneyLog.setMoney(price);
								insertUserMoneyLog.setLeftmoney(user.getMoney() + price);
								int m = this.userMoneyLogService.insertSelective(insertUserMoneyLog);

								if (m == 1) {
									// 更新用户账户余额
									User updateUser = new User();
									updateUser.setUid(user.getUid());
									updateUser.setMoney(user.getMoney() + price);
									int q = this.userService.updateByPrimaryKeySelective(updateUser);
									if (q == 1) {
										// 更新支付记录状态
										PayLog updatePayLog = new PayLog();
										updatePayLog.setPayid(payid);
										updatePayLog.setStatus(1);
										updatePayLog.setLasttime(AchieveUtils.getMillis());
										int p = this.payLogService.updateByPrimaryKeySelective(updatePayLog);
										if (p == 0) {
											errmsg = "更新支付记录状态失败！";
										}
									} else {
										errmsg = "更新用户账户余额失败！";
									}
								} else {
									errmsg = "插入流水记录失败！";
								}
							}
						} else {
							errmsg = "获取支付id失败！";
						}
					} else {
						errmsg = "插入支付记录失败！";
					}
				} else {
					errmsg = "生成支付id失败！";
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

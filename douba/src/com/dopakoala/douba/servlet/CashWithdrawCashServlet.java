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

import com.dopakoala.douba.entity.PayTixian;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.entity.UserMoneyLog;
import com.dopakoala.douba.service.IPayTixianService;
import com.dopakoala.douba.service.IUserMoneyLogService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserEditUserServlet
 */
@WebServlet("/cash/withdrawcash")
public class CashWithdrawCashServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IPayTixianService payTixianService;
	@Autowired
	private IUserMoneyLogService userMoneyLogService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CashWithdrawCashServlet() {
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

			String token = "", remark = "";
			int uid = 0;
			long timestamp = 0, money = 0;

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
				if (!parameters.isNullObject() && parameters.containsKey("money")) {
					money = parameters.getLong("money");
				}
				if (!parameters.isNullObject() && parameters.containsKey("remark")) {
					remark = parameters.getString("remark");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						if (money < 0) {
							errmsg = "提现金额错误！";
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
				// 生成提现流水
				String date = ConvertUtils.formatDateToString(new Date(), 8);
				int randomCode = AchieveUtils.getRandomCode(100, 999);
				long id = ConvertUtils.longFormat(date + randomCode, 0);
				// 插入提现记录
				PayTixian insertPayTixian = new PayTixian();
				insertPayTixian.setMoney(money);
				insertPayTixian.setRemark(remark);
				insertPayTixian.setUid(uid);
				insertPayTixian.setStatus(-2);
				insertPayTixian.setId(id);
				int m = this.payTixianService.insertSelective(insertPayTixian);
				if (m == 1) {
					// 获取提现申请id
					if (id > 0) {
						// 插入流水记录
						UserMoneyLog insertUserMoneyLog = new UserMoneyLog();
						insertUserMoneyLog.setAction("tixian");
						insertUserMoneyLog.setUid(user.getUid());
						insertUserMoneyLog.setMoney(money);
						insertUserMoneyLog.setLeftmoney(user.getMoney() - money);
						int r = this.userMoneyLogService.insertSelective(insertUserMoneyLog);
						if (r == 1) {
							// 更新用户账户余额
							User updateUser = new User();
							updateUser.setUid(user.getUid());
							updateUser.setMoney(user.getMoney() - money);
							int q = this.userService.updateByPrimaryKeySelective(updateUser);
							if (q == 1) {
								PayTixian updatePayTixian = new PayTixian();
								updatePayTixian.setStatus(0);
								updatePayTixian.setId(id);
								int n = this.payTixianService.updateByPrimaryKeySelective(updatePayTixian);
								if (n == 1) {
									errmsg = "提现申请已成功提交，请耐心等待审核！";
								} else {
									errmsg = "更新提现申请状态失败！";
									errcode = 300;
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
						errmsg = "获取流水记录id失败！";
						errcode = 300;
					}
				} else {
					errmsg = "插入提现记录失败！";
					errcode = 300;
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

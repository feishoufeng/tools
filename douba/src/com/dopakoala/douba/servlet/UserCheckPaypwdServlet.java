package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/user/checkPayPwd")
public class UserCheckPaypwdServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserCheckPaypwdServlet() {
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
		int errcode = 0;
		String errmsg = "";

		String token = "", payPwd = "";
		int uid = 0;
		long timestamp = 0;

		try {
			JSONObject requestJson = ServletUtils.getReader(request);
			// 获取请求参数
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
				if (!parameters.isNullObject() && parameters.containsKey("payPwd")) {
					payPwd = parameters.getString("payPwd");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			int isSet = 0;
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					// 获取用户支付密码是否为空
					String pay_pwd = user.getPayPwd();
					if (!CheckUtils.isNull(pay_pwd)) {
						isSet = 1;
						String tempToken = user.getToken();
						if (tempToken.equals(token)) {
							if (CheckUtils.isNull(payPwd)) {
								errmsg = "payPwd不可以为空！";
								errcode = 600;
							}
						} else {
							errmsg = "账户登录权限过期，请重新登录";
							errcode = 700;
						}
					} else {
						errmsg = "当前未设置支付密码！";
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
				// 获取password_salt
				String password_salt = user.getPasswordSalt();
				// 获取用户支付密码是否为空
				String pay_pwd = user.getPayPwd();
				if (!CheckUtils.isNull(password_salt)) {
					// 判断支付密码是否正确
					if (!pay_pwd.equals(ConvertUtils.encryptMD5(password_salt + payPwd))) {
						errmsg = "支付密码错误";
						errcode = 300;
					}
				} else {
					errmsg = "获取用户加密密钥错误！";
					errcode = 300;
				}
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答信息
			resultMap.put("errcode", errcode);
			// 返回应答码
			resultMap.put("errmsg", errmsg);
			// 返回响应数据
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("isSet", isSet);
			resultMap.put("data", JSONObject.fromObject(resultMap));
			ServletUtils.writeJson(response, resultMap);

		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

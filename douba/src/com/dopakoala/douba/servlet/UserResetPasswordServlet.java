package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.service.IUserVerifyService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserResetPasswordServlet
 */
@WebServlet("/user/resetpassword")
public class UserResetPasswordServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IUserVerifyService userVerifyService;

	/**
	 * Default constructor.
	 */
	public UserResetPasswordServlet() {
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

			String mobile = "", password = "", code = "", password_salt = "";
			int uid = 0;
			long timestamp = 0;

			JSONObject requestJson = ServletUtils.getReader(request);

			if (requestJson != null) {

				if (!requestJson.isNullObject() && requestJson.containsKey("timestamp")) {
					timestamp = requestJson.getLong("timestamp");
				}

				JSONObject parameters = requestJson.getJSONObject("parameters");
				if (!parameters.isNullObject() && parameters.containsKey("mobile")) {
					mobile = parameters.getString("mobile");
				}
				if (!parameters.isNullObject() && parameters.containsKey("password")) {
					password = parameters.getString("password");
				}
				if (!parameters.isNullObject() && parameters.containsKey("code")) {
					code = parameters.getString("code");
				}
			}

			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取当前用户是否注册
				User tempUser = this.userService.selectByMobile(mobile);
				if (tempUser != null) {
					password_salt = tempUser.getPasswordSalt();
					uid = tempUser.getUid();
					String respStr = CheckUtils.checkVerify(2, mobile, code, userVerifyService);
					if (!CheckUtils.isNull(respStr)) {
						errmsg = respStr;
						errcode = 300;
					}
				} else {
					errmsg = "用户账户不存在！";
					errcode = 300;
				}
			} else {
				errmsg = "请求时间戳异常，请重试！";
				errcode = 200;
			}

			if (errcode == 0) {
				User updateUser = new User();
				// 通过MD5方式给密码加密
				password = ConvertUtils.encryptMD5(password_salt + password);
				// 通过MD5方式给密码加密
				updateUser.setPassword(password);
				updateUser.setUid(uid);
				// 更新用户密码
				this.userService.updateByPrimaryKeySelective(updateUser);
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

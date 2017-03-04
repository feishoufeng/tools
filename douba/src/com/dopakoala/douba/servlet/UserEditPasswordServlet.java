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
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserEditPasswordServlet
 */
@WebServlet("/user/editpasswrod")
public class UserEditPasswordServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;

	/**
	 * Default constructor.
	 */
	public UserEditPasswordServlet() {
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

			String token = "", oldpwd = "", newpwd = "", mobile = "", passwordSalt = "";
			int uid = 0;
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
				if (!parameters.isNullObject() && parameters.containsKey("oldpwd")) {
					oldpwd = parameters.getString("oldpwd");
				}
				if (!parameters.isNullObject() && parameters.containsKey("newpwd")) {
					newpwd = parameters.getString("newpwd");
				}
				if (!parameters.isNullObject() && parameters.containsKey("mobile")) {
					mobile = parameters.getString("mobile");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						// 获取随机密钥
						passwordSalt = user.getPasswordSalt();
						if (!CheckUtils.isNull(oldpwd)) {
							if (!CheckUtils.isNull(newpwd)) {
								if (CheckUtils.isNull(mobile)) {
									errmsg = "传入的手机密码为空！";
									errcode = 600;
								}
							} else {
								errmsg = "传入的新密码为空！";
								errcode = 600;
							}
						} else {
							errmsg = "传入的旧密码为空！";
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
				// 验证旧密码是否正确
				User selectUser = new User();
				// 通过MD5方式给密码加密
				oldpwd = ConvertUtils.encryptMD5(passwordSalt + oldpwd);
				// 获取登录信息
				selectUser.setMobile(mobile);
				selectUser.setPassword(oldpwd);
				User userTemp = this.userService.selectByLoginMsg(selectUser);
				if (userTemp != null) {
					User updateUser = new User();
					// 通过MD5方式给密码加密
					newpwd = ConvertUtils.encryptMD5(passwordSalt + newpwd);
					updateUser.setPassword(newpwd);
					updateUser.setUid(uid);
					// 更新用户密码
					this.userService.updateByPrimaryKeySelective(updateUser);
				} else {
					errmsg = "用户密码错误！";
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

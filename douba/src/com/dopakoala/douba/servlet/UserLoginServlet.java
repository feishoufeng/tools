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
import com.dopakoala.douba.entity.UserActionLog;
import com.dopakoala.douba.service.IUserActionLogService;
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
@WebServlet("/user/login")
public class UserLoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IUserActionLogService userActionLogService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserLoginServlet() {
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
		String mobile = "", password = "", passwordSalt = "", token = "", ip = "", version = "", platform = "",
				deviceid = "";
		User user = new User();
		User userTemp = new User();
		long timestamp = 0;

		try {

			JSONObject requestJson = ServletUtils.getReader(request);

			// 获取请求参数
			if (requestJson != null) {

				if (!requestJson.isNullObject() && requestJson.containsKey("version")) {
					version = requestJson.getString("version");
				}
				if (!requestJson.isNullObject() && requestJson.containsKey("platform")) {
					platform = requestJson.getString("platform");
				}
				if (!requestJson.isNullObject() && requestJson.containsKey("deviceid")) {
					deviceid = requestJson.getString("deviceid");
				}
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
				if (!parameters.isNullObject() && parameters.containsKey("ip")) {
					ip = parameters.getString("ip");
				}
			}

			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取随机密钥
				userTemp = this.userService.selectByMobile(mobile);
				if (userTemp != null) {
					passwordSalt = userTemp.getPasswordSalt();
					// 判断账户是否为空
					if (!CheckUtils.isNull(mobile)) {
						// 判断密码是否为空
						if (!CheckUtils.isNull(password)) {
							// 通过MD5方式给密码加密
							password = ConvertUtils.encryptMD5(passwordSalt + password);
							// 获取登录信息
							User selectUser = new User();
							selectUser.setMobile(mobile);
							selectUser.setPassword(password);
							user = this.userService.selectByLoginMsg(selectUser);
							// 判断登录信息是否存在
							if (user != null) {
								int status = user.getStatus();
								if (status == 1) {
									// 随机生成token
									token = AchieveUtils.getRandomId();
									// 更新登录信息
									User updateUser = new User();
									updateUser.setUid(user.getUid());
									updateUser.setToken(token);
									updateUser.setLogintime(AchieveUtils.getMillis());
									updateUser.setLogintimes(user.getLogintimes() + 1);
									this.userService.updateByPrimaryKeySelective(updateUser);
								} else {
									errmsg = "账号异常，无法登录！";
									errcode = 200;
								}
							} else {
								errmsg = "账号或密码错误，请重新登录！";
								errcode = 600;
							}
						} else {
							errmsg = "密码不可以为空！";
							errcode = 600;
						}
					} else {
						errmsg = "账号不可以为空！";
						errcode = 600;
					}
				} else {
					errmsg = "账号不存在！";
					errcode = 200;
				}
			} else {
				errmsg = "请求时间戳异常，请重试！";
				errcode = 200;
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答信息
			resultMap.put("errcode", errcode);
			// 返回应答码
			resultMap.put("errmsg", errmsg);
			// 返回应答数据
			if (user != null) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("token", token);
				dataMap.put("userId", user.getUid());
				dataMap.put("avatar", user.getAvatar());
				dataMap.put("nickname", user.getNickname());
				dataMap.put("logintimes", user.getLogintimes());
				resultMap.put("data", JSONObject.fromObject(dataMap));

				// 登录日志
				UserActionLog userActionLog = new UserActionLog();
				userActionLog.setAction("login");
				userActionLog.setDeviceid(deviceid);
				userActionLog.setPlatform(platform);
				userActionLog.setToken(token);
				userActionLog.setVersion(version);
				userActionLog.setIp(ip);
				userActionLog.setUid(user.getUid());
				this.userActionLogService.insertSelective(userActionLog);
			}

			ServletUtils.writeJson(response, resultMap);

		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

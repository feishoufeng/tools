package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.entity.UserVerify;
import com.dopakoala.douba.message.Msm;
import com.dopakoala.douba.service.IAppConfigService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.service.IUserVerifyService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ConstantsUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserVerifty
 */
@WebServlet("/user/getverifycode")
public class UserVeriftyServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserVerifyService userVerifyService;
	@Autowired
	private IAppConfigService appConfigService;
	@Autowired
	private IUserService userService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserVeriftyServlet() {
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

		String errmsg = "";
		int errcode = 0, type = 0;
		String result = "";
		long timestamp = 0;
		// TODO Auto-generated method stub
		try {

			String deviceId = "", mobile = "";

			JSONObject requestJson = ServletUtils.getReader(request);

			if (requestJson != null) {

				if (requestJson.containsKey("deviceId")) {
					deviceId = requestJson.getString("deviceId");
				}

				if (!requestJson.isNullObject() && requestJson.containsKey("timestamp")) {
					timestamp = requestJson.getLong("timestamp");
				}

				JSONObject parameters = requestJson.getJSONObject("parameters");
				if (!parameters.isNullObject() && parameters.containsKey("mobile")) {
					mobile = parameters.getString("mobile");
				}

				if (!parameters.isNullObject() && parameters.containsKey("type")) {
					type = parameters.getInt("type");
				}

			}

			// 获取系统中定义的每天短信限制条数
			int limited_size = AchieveUtils.getIntAppConfig("msg_limited", appConfigService);

			// 获取当天相同客户已发短信条数
			int curr_size = 0;
			long tempMillis = 0;
			UserVerify selectUserVerify = new UserVerify();
			selectUserVerify.setMobile(mobile);
			selectUserVerify.setType(type);
			List<UserVerify> userVerifyList = this.userVerifyService.selectByMobile(selectUserVerify);
			if (userVerifyList != null && userVerifyList.size() > 0) {
				curr_size = userVerifyList.size();
				UserVerify userVerify = userVerifyList.get(0);
				tempMillis = userVerify.getCreateTime().getTime();
			}

			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				if ((AchieveUtils.getMillis() - tempMillis) > (60 * 1000)) {
					if (curr_size < limited_size) {
						if (!CheckUtils.isNull(mobile) && CheckUtils.isMobile(mobile)) {
							if (type > 0) {
								String code = AchieveUtils.getRandomCode(100000, 999999) + "";
								// 发送验证码信息
								String content = "";
								User user = this.userService.selectByMobile(mobile);
								switch (type) {
								case 1:
									// 注册
									// 判断手机号是否已存在
									if (user != null) {
										errmsg = "手机号码已存在，请勿重复注册!";
										errcode = 300;
									}
									content = AchieveUtils.formatStr(ConstantsUtils.MSM_REGISTER_TIP, code);
									break;
								case 2:
									// 密码重置
									// 判断手机号是否已存在
									if (user == null) {
										errmsg = "手机号码未注册，请先注册!";
										errcode = 300;
									}
									content = AchieveUtils.formatStr(ConstantsUtils.MSM_FINDPWD_TIP, code);
									break;
								case 3:
									// 绑定手机
									content = AchieveUtils.formatStr(ConstantsUtils.MSM_BIND_TIP, code);
									break;
								}
								if (errcode == 0) {
									if (Msm.send(mobile, content).getCode() != 0) {
										errmsg = "验证码发送失败，请重新发送！";
									} else {
										// 将验证码存入数据库
										UserVerify userVerify = new UserVerify();
										userVerify.setCode(code);
										userVerify.setDeviceid(deviceId);
										userVerify.setMobile(mobile);
										userVerify.setType(type);
										this.userVerifyService.insertSelective(userVerify);
										errmsg = "验证码发送成功！";
									}
									// 返回验证码字段
									result = code;
								}
							} else {
								errmsg = "短信类型错误！";
								errcode = 600;
							}
						} else {
							errmsg = "手机号码不符合要求，请重新输入！";
							errcode = 600;
						}
					} else {
						errmsg = "获取验证码已超出当天限定次数！";
						errcode = 300;
					}
				} else {
					errmsg = "60秒内不可以重复获取验证码！";
					errcode = 200;
				}
			} else {
				errmsg = "请求时间戳异常，请重试！";
				errcode = 200;
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);
			// 返回应答数据
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("result", result);
			dataMap.put("timeout", ConstantsUtils.MESSAGE_TIMEOUT);
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

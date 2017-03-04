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
import com.dopakoala.douba.service.IUserVerifyService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ConstantsUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.QRCodeUtil;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserRegister
 */
@WebServlet("/user/reg")
public class UserRegisterServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IUserVerifyService userVerifyService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserRegisterServlet() {
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
		int errcode = 0;
		try {
			// 文件保存相对路径
			String path = AchieveUtils.getRealPath();
			String url = AchieveUtils.getConfVal("host");

			String mobile = "", password = "", code = "", password_salt = "";
			long timestamp = 0;

			JSONObject requestJson = ServletUtils.getReader(request);

			if (requestJson != null) {

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
				if (!requestJson.isNullObject() && requestJson.containsKey("timestamp")) {
					timestamp = requestJson.getLong("timestamp");
				}
			}

			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取当前用户是否注册
				User userTemp = this.userService.selectByMobile(mobile);
				if (userTemp == null) {
					String respStr = CheckUtils.checkVerify(1, mobile, code, userVerifyService);
					if (!CheckUtils.isNull(respStr)) {
						errmsg = respStr;
						errcode = 200;
					}
				} else {
					errmsg = "用户已存在，请勿重复注册！";
					errcode = 200;
				}
			} else {
				errmsg = "请求时间戳异常，请重试！";
				errcode = 200;
			}

			if (errcode == 0) {
				String uuid = AchieveUtils.getRandomId();
				password_salt = uuid.substring(uuid.length() - 32, uuid.length());
				password = ConvertUtils.encryptMD5(password_salt + password);

				User user = new User();
				user.setMobile(mobile);
				user.setPasswordSalt(password_salt);
				user.setPassword(password);
				user.setRegtime(AchieveUtils.getMillis());
				user.setStatus(1);
				user.setAvatar(url + ConstantsUtils.DEFAULT_USER_HEADER_PATH);
				user.setLogintime(1L);
				user.setUsername("");

				this.userService.insertSelective(user);

				// 生成用户二维码图片
				JSONObject json = new JSONObject();
				json.put("type", "user");
				json.put("uid", user.getUid());
				String imgPath = QRCodeUtil.createQRCode(user.getUid(), json.toString(),
						ConstantsUtils.USER_QRCODE_PATH, path, url, ConstantsUtils.DEFAULT_QRCODE_CENTER_LOGO_PATH);
				// 更新二维码地址
				User updateUser = new User();
				updateUser.setUid(user.getUid());
				updateUser.setQrCode(imgPath);
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
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

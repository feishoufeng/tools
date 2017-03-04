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
import com.dopakoala.douba.utils.ExceptionMsgUtil;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ConstantsUtils;
import com.dopakoala.douba.utils.QRCodeUtil;
import com.dopakoala.douba.utils.ServletUtils;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/user/weixinlogin")
public class WeiXinLoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IUserActionLogService userActionLogService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WeiXinLoginServlet() {
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
		// 文件保存相对路径
		String path = AchieveUtils.getRealPath();
		String url = AchieveUtils.getConfVal("host");

		int errcode = 0, uid = 0, sex = -1;
		String errmsg = "", passwordSalt = "", token = "", ip = "", version = "", platform = "", deviceid = "",
				avatar = "", nickname = "", openid = "", mobile = "";
		long timestamp = 0, logintimes = 1;

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
				if (!parameters.isNullObject() && parameters.containsKey("openid")) {
					openid = parameters.getString("openid");
				}
				if (!parameters.isNullObject() && parameters.containsKey("ip")) {
					ip = parameters.getString("ip");
				}
				if (!parameters.isNullObject() && parameters.containsKey("avatar")) {
					avatar = parameters.getString("avatar");
				}
				if (!parameters.isNullObject() && parameters.containsKey("nickname")) {
					nickname = parameters.getString("nickname");
				}
				if (!parameters.isNullObject() && parameters.containsKey("sex")) {
					sex = parameters.getInt("sex");
				}
			}

			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 判断微信id是否为空
				if (!CheckUtils.isNull(openid)) {
					// 判断昵称是否为空
					if (CheckUtils.isNull(nickname)) {
						errmsg = "昵称不可以为空！";
						errcode = 200;
					}
				} else {
					errmsg = "微信不可以为空！";
					errcode = 200;
				}
			} else {
				errmsg = "请求时间戳异常，请重试！";
				errcode = 200;
			}

			if (errcode == 0) {
				User user = this.userService.selectByOpenId(openid);
				if (user != null) {
					uid = user.getUid();
					logintimes = user.getLogintime() + 1;
					mobile = user.getMobile();
					token = user.getToken();
					// 已存在微信openid
					User updateUser = new User();
					updateUser.setLogintime(user.getLogintime() + 1);
					updateUser.setUid(uid);
					this.userService.updateByPrimaryKeySelective(updateUser);
				} else {
					// 不存在微信openid
					// 生成密钥
					// 随机生成token
					token = AchieveUtils.getRandomId();
					String uuid = AchieveUtils.getRandomId();
					passwordSalt = uuid.substring(uuid.length() - 32, uuid.length());

					User insertUser = new User();
					insertUser.setNickname(nickname);
					insertUser.setOpenid(openid);
					if (!CheckUtils.isNull(avatar)) {
						insertUser.setAvatar(avatar);
						insertUser.setThumbnail(avatar);
					} else {
						insertUser.setAvatar(url + ConstantsUtils.DEFAULT_USER_HEADER_PATH);
						insertUser.setThumbnail(url + ConstantsUtils.DEFAULT_USER_HEADER_PATH);
					}
					insertUser.setPasswordSalt(passwordSalt);
					insertUser.setToken(token);
					insertUser.setSex(sex);
					insertUser.setStatus(1);
					insertUser.setLogintime(1L);
					this.userService.insertSelective(insertUser);
					uid = insertUser.getUid();

					// 生成用户二维码图片
					JSONObject json = new JSONObject();
					json.put("type", "user");
					json.put("uid", uid);
					String imgPath = QRCodeUtil.createQRCode(uid, json.toString(), ConstantsUtils.USER_QRCODE_PATH,
							path, url, ConstantsUtils.DEFAULT_QRCODE_CENTER_LOGO_PATH);
					// 更新二维码地址
					User updateUser = new User();
					updateUser.setUid(uid);
					updateUser.setQrCode(imgPath);
					this.userService.updateByPrimaryKeySelective(updateUser);
				}
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答信息
			resultMap.put("errcode", errcode);
			// 返回应答码
			resultMap.put("errmsg", errmsg);
			// 返回应答数据
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("token", token);
			dataMap.put("userId", uid);
			dataMap.put("avatar", avatar);
			dataMap.put("nickname", nickname);
			dataMap.put("logintimes", logintimes);
			dataMap.put("mobile", mobile);
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);

		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		} finally {
			try {
				// 登录日志
				UserActionLog userActionLog = new UserActionLog();
				userActionLog.setAction("weixin_login");
				userActionLog.setDeviceid(deviceid);
				userActionLog.setPlatform(platform);
				userActionLog.setToken(token);
				userActionLog.setVersion(version);
				userActionLog.setIp(ip);
				userActionLog.setUid(uid);
				this.userActionLogService.insertSelective(userActionLog);
			} catch (Exception e) {
				// TODO: handle exception
				ExceptionMsgUtil.getInstance(this.getClass(), e);
			}
		}
	}
}

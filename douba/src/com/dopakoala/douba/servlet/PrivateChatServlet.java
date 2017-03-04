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

import com.dopakoala.douba.entity.PrivateChat;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IPrivateChatService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserEditUserServlet
 */
@WebServlet("/private/chat")
public class PrivateChatServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IPrivateChatService privateChatService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PrivateChatServlet() {
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

			String token = "";
			int uid = 0, gid = 0, toUid = 0, status = 0;
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
				if (!parameters.isNullObject() && parameters.containsKey("gid")) {
					gid = parameters.getInt("gid");
				}
				if (!parameters.isNullObject() && parameters.containsKey("toUid")) {
					toUid = parameters.getInt("toUid");
				}
				if (!parameters.isNullObject() && parameters.containsKey("status")) {
					status = parameters.getInt("status");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						if (gid > 0) {
							if (toUid > 0) {
								if (status != -1 && status != 1) {
									errmsg = "status数据不合法！";
									errcode = 600;
								}
							} else {
								errmsg = "toUid数据不合法！";
								errcode = 600;
							}
						} else {
							errmsg = "gid数据不合法！";
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
				if (status == -1) {
					// 创建人删除
					PrivateChat updatePrivateChat = new PrivateChat();
					updatePrivateChat.setGid(gid);
					updatePrivateChat.setStatus(status);
					updatePrivateChat.setToUid(toUid);
					updatePrivateChat.setUid(uid);
					this.privateChatService.updateStatus(updatePrivateChat);
					// 对方删除
					PrivateChat updatePrivateChatTemp = new PrivateChat();
					updatePrivateChatTemp.setGid(gid);
					updatePrivateChatTemp.setStatus(status);
					updatePrivateChatTemp.setToUid(uid);
					updatePrivateChatTemp.setUid(toUid);
					this.privateChatService.updateStatus(updatePrivateChatTemp);
				} else {
					// 判断是否已存在私聊记录
					PrivateChat selectPrivateChat = new PrivateChat();
					selectPrivateChat.setGid(gid);
					selectPrivateChat.setUid(uid);
					selectPrivateChat.setToUid(toUid);
					PrivateChat privateChat = this.privateChatService.selectByGidUidToUid(selectPrivateChat);
					if (privateChat == null) {
						PrivateChat insertPrivateChat = new PrivateChat();
						insertPrivateChat.setGid(gid);
						insertPrivateChat.setStatus(status);
						insertPrivateChat.setToUid(toUid);
						insertPrivateChat.setUid(uid);
						this.privateChatService.insertSelective(insertPrivateChat);
					} else {
						errmsg = "已创建私聊，请勿重复创建！";
						errcode = 0;
					}
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

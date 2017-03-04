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
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserEditUserServlet
 */
@WebServlet("/user/bindweixin")
public class UserBindWeixinServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IUserVerifyService userVerifyService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserBindWeixinServlet() {
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

			String token = "", mobile = "", code = "", openid = "";
			int uid = 0, unbind = 0, bind = 0;
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
				if (!parameters.isNullObject() && parameters.containsKey("mobile")) {
					mobile = parameters.getString("mobile");
				}
				if (!parameters.isNullObject() && parameters.containsKey("code")) {
					code = parameters.getString("code");
				}
				if (!parameters.isNullObject() && parameters.containsKey("openid")) {
					openid = parameters.getString("openid");
				}
				if (!parameters.isNullObject() && parameters.containsKey("unbind")) {
					unbind = parameters.getInt("unbind");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						if (!CheckUtils.isMobile(mobile)) {
							errmsg = "手机号码格式错误,请重新输入！";
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
				if (unbind == 0) {
					String respStr = CheckUtils.checkVerify(3, mobile, code, userVerifyService);
					if (!CheckUtils.isNull(respStr)) {
						errmsg = respStr;
						errcode = 200;
					} else {
						User updateUser = new User();
						updateUser.setUid(uid);
						User userTemp = this.userService.selectByMobile(mobile);
						if (userTemp != null) {
							// 手机已存在
							// 判断手机号是否已绑定微信
							if (CheckUtils.isNull(userTemp.getOpenid())) {
								// 未绑定微信号，直接绑定微信号
								// 直接绑定微信
								User updateUserTemp = new User();
								updateUserTemp.setUid(userTemp.getUid());
								updateUserTemp.setOpenid(openid);
								this.userService.updateByPrimaryKeySelective(updateUserTemp);
								// 删除已注册的微信信息
								this.userService.deleteByPrimaryKey(uid);
								bind = 2;
								uid = userTemp.getUid();
								token = userTemp.getToken();
							} else {
								bind = 1;
							}
						} else {
							// 绑定手机
							updateUser.setMobile(mobile);
							this.userService.updateByPrimaryKeySelective(updateUser);
						}
					}
				} else {
					User userTemp = this.userService.selectByMobile(mobile);
					if (userTemp != null) {
						// 已绑定微信号，进行判断
						// 解绑上一用户手机
						User updateUserTemp = new User();
						updateUserTemp.setUid(userTemp.getUid());
						updateUserTemp.setMobile("");
						updateUserTemp.setPassword("");
						this.userService.updateByPrimaryKeySelective(updateUserTemp);
						// 绑定手机
						User updateUser = new User();
						updateUser.setUid(uid);
						updateUser.setMobile(mobile);
						this.userService.updateByPrimaryKeySelective(updateUser);
					}
				}
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if(bind == 2){
				dataMap.put("uid", uid);
				dataMap.put("token", token);
			}
			dataMap.put("bind", bind);
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

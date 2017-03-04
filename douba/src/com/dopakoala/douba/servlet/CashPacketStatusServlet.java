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

import com.dopakoala.douba.entity.Hongbao;
import com.dopakoala.douba.entity.HongbaoLog;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IHongbaoLogService;
import com.dopakoala.douba.service.IHongbaoService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserEditUserServlet
 */
@WebServlet("/cash/packetStatus")
public class CashPacketStatusServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IHongbaoService hongbaoService;
	@Autowired
	private IHongbaoLogService hongbaoLogService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CashPacketStatusServlet() {
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

			String token = "", password = "";
			int uid = 0, hongbaoId = 0;
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
				if (!parameters.isNullObject() && parameters.containsKey("hongbaoId")) {
					hongbaoId = parameters.getInt("hongbaoId");
				}
				if (!parameters.isNullObject() && parameters.containsKey("password")) {
					password = parameters.getString("password");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						if (hongbaoId <= 0) {
							errmsg = "hongbaoId错误！";
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

			Hongbao hongbao = new Hongbao();
			int getFlag = 0;
			int fromUser = 0;
			if (errcode == 0) {
				hongbao = this.hongbaoService.selectByPrimaryKey(hongbaoId);
				// 获取红包信息
				if (hongbao != null) {
					int sendUid = hongbao.getUid();
					if (sendUid == uid) {
						fromUser = 1;
					}
					// 判断红包是否已抢过
					HongbaoLog selectHongbaoLog = new HongbaoLog();
					selectHongbaoLog.setUid(uid);
					selectHongbaoLog.setHongbaoId(hongbaoId);
					HongbaoLog hongbaoLog = this.hongbaoLogService.selectByIdUid(selectHongbaoLog);
					if (hongbaoLog != null) {
						errmsg = "红包已抢过！";
						getFlag = 1;
					} else {
						if (!hongbao.getPassword().equals(password)) {
							errmsg = "红包口令错误！";
							errcode = 300;
						}
					}
				} else {
					errmsg = "获取红包失败！";
					errcode = 600;
				}
			}
			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);

			Map<String, Object> dataMap = new HashMap<String, Object>();
			if (hongbao != null) {
				dataMap.put("type", hongbao.getType());
				dataMap.put("nickname", hongbao.getNickname());
				dataMap.put("thumbnail", hongbao.getThumbnail());
				dataMap.put("maxnum", hongbao.getMaxnum());
				dataMap.put("money", hongbao.getMoney());
				dataMap.put("leftMoney", hongbao.getLeftmoney());
				dataMap.put("status", hongbao.getStatus());
				dataMap.put("remark", hongbao.getRemark());
				dataMap.put("getFlag", getFlag);
				dataMap.put("fromUser", fromUser);
				resultMap.put("data", JSONObject.fromObject(dataMap));
			}

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}
}
package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@WebServlet("/cash/packetList")
public class CashPacketListServlet extends BaseServlet {
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
	public CashPacketListServlet() {
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
			List<HongbaoLog> hongbaoLogList = new ArrayList<HongbaoLog>();
			if (errcode == 0) {
				// 获取红包详情
				hongbao = this.hongbaoService.selectByPrimaryKey(hongbaoId);
				// 获取红包领取列表
				hongbaoLogList = this.hongbaoLogService.selectByHongbaoId(hongbaoId);
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
				dataMap.put("time", hongbao.getTime());
				// 获取当前用户所抢红包金额
				HongbaoLog selectHongbaoLog = new HongbaoLog();
				selectHongbaoLog.setUid(uid);
				selectHongbaoLog.setHongbaoId(hongbaoId);
				HongbaoLog hongbaoLog = this.hongbaoLogService.selectByIdUid(selectHongbaoLog);
				if (hongbaoLog != null) {
					dataMap.put("userMoney", hongbaoLog.getMoney());
				} else {
					dataMap.put("userMoney", 0);
				}
				
				if (hongbaoLogList != null && hongbaoLogList.size() > 0) {
					//
					if (hongbao.getStatus() == 2) {
						// 判断抢红包最多的人
						long temp = hongbaoLogList.get(0).getMoney();
						int index = 0;
						for (int i = 1; i < hongbaoLogList.size(); i++) {
							if (temp < hongbaoLogList.get(i).getMoney()) {
								temp = hongbaoLogList.get(i).getMoney();
								index = i;
							}
						}
						// 将抢红包最多的进行标记
						hongbaoLogList.get(index).setIsTop(1);
					}
					dataMap.put("list", hongbaoLogList);
					dataMap.put("count", hongbaoLogList.size());
				} else {
					dataMap.put("list", hongbaoLogList);
					dataMap.put("count", 0);
				}
			} else {
				dataMap.put("userMoney", 0);
			}
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (

		Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}
}
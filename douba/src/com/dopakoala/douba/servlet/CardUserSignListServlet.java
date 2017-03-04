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

import com.dopakoala.douba.entity.GroupCardSign;
import com.dopakoala.douba.entity.GroupUserSignLog;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IGroupCardSignService;
import com.dopakoala.douba.service.IGroupUserSignLogService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserEditUserServlet
 */
@WebServlet("/card/userSignList")
public class CardUserSignListServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupCardSignService groupCardSignService;
	@Autowired
	private IGroupUserSignLogService groupUserSignLogService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CardUserSignListServlet() {
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
			int uid = 0, gid = 0, page = 0, pagesize = 10;
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
				if (!parameters.isNullObject() && parameters.containsKey("page")) {
					page = parameters.getInt("page");
					if (page > 0) {
						page--;
					}
				}
				if (!parameters.isNullObject() && parameters.containsKey("pagesize")) {
					pagesize = parameters.getInt("pagesize");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						if (gid <= 0) {
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

			int count = 0;
			int cardId = 0;
			List<GroupUserSignLog> groupUserSignLogList = new ArrayList<GroupUserSignLog>();
			if (errcode == 0) {
				// 获取最新有效卡
				GroupCardSign selectGroupCardSign = new GroupCardSign();
				selectGroupCardSign.setTime(AchieveUtils.getMillis());
				selectGroupCardSign.setGid(gid);
				GroupCardSign groupCardSign = this.groupCardSignService.selectByTimeGid(selectGroupCardSign);
				if (groupCardSign != null) {
					cardId = groupCardSign.getId();
					GroupUserSignLog selectGroupUserSignLog = new GroupUserSignLog();
					selectGroupUserSignLog.setUid(uid);
					selectGroupUserSignLog.setCardId(cardId);
					selectGroupUserSignLog.setPage(page * pagesize);
					selectGroupUserSignLog.setPagesize(pagesize);
					groupUserSignLogList = this.groupUserSignLogService.selectByUidCardId(selectGroupUserSignLog);
					// 获取列表总数
					GroupUserSignLog selectGroupUserSignLogTemp = new GroupUserSignLog();
					selectGroupUserSignLogTemp.setUid(uid);
					selectGroupUserSignLogTemp.setCardId(cardId);
					selectGroupUserSignLogTemp.setPage(0);
					selectGroupUserSignLogTemp.setPagesize(0);
					count = this.groupUserSignLogService.selectByUidCardId(selectGroupUserSignLog).size();
				}
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);
			// 返回应答数据
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("list", groupUserSignLogList);
			dataMap.put("count", count);
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

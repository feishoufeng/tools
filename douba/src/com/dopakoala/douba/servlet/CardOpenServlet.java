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

import com.dopakoala.douba.entity.GroupCardSign;
import com.dopakoala.douba.entity.GroupUserSignLog;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IGroupCardSignService;
import com.dopakoala.douba.service.IGroupUserSignLogService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserEditUserServlet
 */
@WebServlet("/card/open")
public class CardOpenServlet extends BaseServlet {
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
	public CardOpenServlet() {
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

			String token = "", header = "", footer = "";
			int uid = 0, gid = 0, startId = 0, endId = 0;
			long timestamp = 0, startTime = 0, endTime = 0;

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
				if (!parameters.isNullObject() && parameters.containsKey("header")) {
					header = parameters.getString("header");
				}
				if (!parameters.isNullObject() && parameters.containsKey("footer")) {
					footer = parameters.getString("footer");
				}
				if (!parameters.isNullObject() && parameters.containsKey("startId")) {
					startId = parameters.getInt("startId");
				}
				if (!parameters.isNullObject() && parameters.containsKey("gid")) {
					gid = parameters.getInt("gid");
				}
				if (!parameters.isNullObject() && parameters.containsKey("endId")) {
					endId = parameters.getInt("endId");
				}
				if (!parameters.isNullObject() && parameters.containsKey("startTime")) {
					startTime = parameters.getLong("startTime");
				}
				if (!parameters.isNullObject() && parameters.containsKey("endTime")) {
					endTime = parameters.getLong("endTime");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						if (gid > 0) {
							if (startId > 0) {
								if (endId > 0) {
									if (startId < endId) {
										if (!CheckUtils.isNull(header)) {
											if (!CheckUtils.isNull(footer)) {
												if (startTime >= startTime) {
													if (startTime >= endTime) {
														errmsg = "所开卡有效期不合法！";
														errcode = 600;
													}
												} else {
													errmsg = "所开卡时间异常！";
													errcode = 600;
												}
											} else {
												errmsg = "卡尾不可以为空！";
												errcode = 600;
											}
										} else {
											errmsg = "卡头不可以为空！";
											errcode = 600;
										}
									} else {
										errmsg = "起始id应大于结束id！";
										errcode = 600;
									}
								} else {
									errmsg = "结束id不合法！";
									errcode = 600;
								}
							} else {
								errmsg = "起始id不合法！";
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
				GroupCardSign selectGroupCardSign = new GroupCardSign();
				selectGroupCardSign.setGid(gid);
				selectGroupCardSign.setTime(AchieveUtils.getMillis());
				GroupCardSign groupCardSign = this.groupCardSignService.selectByTimeGid(selectGroupCardSign);
				if (groupCardSign == null) {
					// 获取上一张卡群里统计跑步量
					GroupUserSignLog groupUserSignLog = this.groupUserSignLogService.selectTotalByGid(gid);
					GroupCardSign insertGroupCardSign = new GroupCardSign();
					if (groupUserSignLog != null) {
						insertGroupCardSign.setTotalmiles(groupUserSignLog.getRunmiles());
					} else {
						insertGroupCardSign.setTotalmiles(0.00);
					}
					insertGroupCardSign.setSignUid(uid);
					insertGroupCardSign.setGid(gid);
					insertGroupCardSign.setStartId(startId);
					insertGroupCardSign.setEndId(endId);
					insertGroupCardSign.setStartTime(startTime);
					insertGroupCardSign.setEndTime(endTime);
					insertGroupCardSign.setFooter(footer);
					insertGroupCardSign.setHeader(header);
					insertGroupCardSign.setCurrSignid(startId);
					this.groupCardSignService.insertSelective(insertGroupCardSign);
				} else {
					errmsg = "当天已成功开过新卡，请勿重复开卡！";
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

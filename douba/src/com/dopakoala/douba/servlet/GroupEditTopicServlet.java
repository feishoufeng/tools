package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.dopakoala.douba.entity.GroupTopic;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IGroupTopicService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.*;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class GroupNoticeListServlet
 */
@WebServlet("/group/edittopic")
public class GroupEditTopicServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IGroupTopicService groupTopicService;
	@Autowired
	private IUserService userService;

	/**
	 * Default constructor.
	 */
	public GroupEditTopicServlet() {
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

			String token = "", type = "", title = "", content = "";
			int uid = 0, gid = 0, id = 0, istop = 0;
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
				if (!parameters.isNullObject() && parameters.containsKey("id")) {
					id = parameters.getInt("id");
				}
				if (!parameters.isNullObject() && parameters.containsKey("gid")) {
					gid = parameters.getInt("gid");
				}
				if (!parameters.isNullObject() && parameters.containsKey("istop")) {
					istop = parameters.getInt("istop");
				}
				if (!parameters.isNullObject() && parameters.containsKey("type")) {
					type = parameters.getString("type");
				}
				if (!parameters.isNullObject() && parameters.containsKey("title")) {
					title = parameters.getString("title");
				}
				if (!parameters.isNullObject() && parameters.containsKey("content")) {
					content = parameters.getString("content");
				}

			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						// 判断传入的跑团id是否合法
						if (gid > 0) {
							// 判断传入公告类型参数是否合法
							if (ConstantsUtils.NOTICE_TYPE.indexOf(type) != -1) {
								if (id >= 0) {
									if (istop == 0 || istop == 1) {
										if (!CheckUtils.isNull(title)) {
											if (CheckUtils.isNull(content)) {
												errmsg = "公告不可以为空！";
												errcode = 600;
											}
										} else {
											errmsg = "标题不可以为空！";
											errcode = 600;
										}
									} else {
										errmsg = "置顶数据不合法！";
										errcode = 600;
									}
								} else {
									errmsg = "公告id不合法！";
									errcode = 600;
								}
							} else {
								errmsg = "公告类型不合法！";
								errcode = 600;
							}
						} else {
							errmsg = "传入的跑团id不合法！";
							errcode = 600;
						}
					} else {
						errmsg = "账户登录权限过期，请重新登录";
						errcode = 700;
					}
				} else {
					errmsg = "账户id不存在！";
					errcode = 600;
				}
			} else {
				errmsg = "请求时间戳异常，请重试！";
				errcode = 200;
			}

			if (errcode == 0) {
				if (id == 0) {
					// 插入操作
					GroupTopic inserGroupTopic = new GroupTopic();
					inserGroupTopic.setContent(content);
					inserGroupTopic.setGid(gid);
					inserGroupTopic.setCreateUser(uid);
					inserGroupTopic.setTitle(title);
					inserGroupTopic.setType(type);
					inserGroupTopic.setIstop(istop);
					this.groupTopicService.insertSelective(inserGroupTopic);
				} else {
					// 更新操作
					GroupTopic updateGroupTopic = new GroupTopic();
					updateGroupTopic.setId(id);
					updateGroupTopic.setContent(content);
					updateGroupTopic.setGid(gid);
					updateGroupTopic.setCreateUser(uid);
					updateGroupTopic.setTitle(title);
					updateGroupTopic.setType(type);
					updateGroupTopic.setIstop(istop);
					this.groupTopicService.updateByPrimaryKeySelective(updateGroupTopic);
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

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

import com.dopakoala.douba.entity.Group;
import com.dopakoala.douba.entity.GroupUser;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IGroupService;
import com.dopakoala.douba.service.IGroupUserService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class GroupGetGroupServlet
 */
@WebServlet("/group/getGroup")
public class GroupGetGroupServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IGroupService groupService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupUserService groupUserService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GroupGetGroupServlet() {
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
			int uid = 0, page = 0, pagesize = 20, count = 0, gid = 0;
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
				if (!parameters.isNullObject() && parameters.containsKey("page")) {
					page = parameters.getInt("page");
					if (page >= 1) {
						page -= page;
					}
				}
				if (!parameters.isNullObject() && parameters.containsKey("pagesize")) {
					pagesize = parameters.getInt("pagesize");
				}
				if (!parameters.isNullObject() && parameters.containsKey("gid")) {
					gid = parameters.getInt("gid");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						if (gid <= 0) {
							errmsg = "传入的跑团(群)id不合法！";
							errcode = 335;
						}
					} else {
						errmsg = "账户登录权限过期，请重新登录";
						errcode = 700;
					}
				} else {
					errmsg = "账户id不存在！";
					errcode = 331;
				}
			} else {
				errmsg = "请求时间戳异常，请重试！";
				errcode = 303;
			}

			// 获取群列表
			List<Group> groupList = new ArrayList<Group>();
			List<Group> groupListTemp = new ArrayList<Group>();
			if (errcode == 0) {
				Group selectGroup = new Group();
				selectGroup.setPid(gid);
				selectGroup.setUid(uid);
				selectGroup.setPage(page * pagesize);
				selectGroup.setPagesize(pagesize);
				groupList = this.groupService.selectTypeNameByPid(selectGroup);

				if (groupList == null || groupList.size() <= 0) {
					errmsg = "该跑团下无群列表！";
				} else {
					Group tempGroup = new Group();
					tempGroup.setPid(gid);
					tempGroup.setUid(uid);
					tempGroup.setPage(0);
					tempGroup.setPagesize(0);
					count = this.groupService.selectTypeNameByPid(tempGroup).size();

					for (int i = 0; i < groupList.size(); i++) {
						Group groupTemp = groupList.get(i);
						List<String> avatars = new ArrayList<String>();
						List<String> thumbnails = new ArrayList<String>();
						// 获取群gid
						int gidTemp = groupTemp.getGid();
						GroupUser selectGroupUser = new GroupUser();
						selectGroupUser.setGid(gidTemp);
						selectGroupUser.setPage(0);
						selectGroupUser.setPagesize(9);
						List<GroupUser> groupUsers = this.groupUserService.selectByGid(selectGroupUser);
						if (groupUsers != null && groupUsers.size() > 0) {
							for (int j = 0; j < groupUsers.size(); j++) {
								// 获取头像
								avatars.add(groupUsers.get(j).getAvatar());
								// 获取头像缩略图
								String thumbnail = groupUsers.get(j).getThumbnail();
								if(!CheckUtils.isNull(thumbnail)){
									thumbnails.add(thumbnail);
								}else{
									thumbnails.add("");
								}
								
							}
							groupTemp.setAvatars(avatars);
							groupTemp.setThumbnails(thumbnails);
							groupListTemp.add(groupTemp);
						}
					}
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
			dataMap.put("count", count);
			dataMap.put("list", JSONArray.fromObject(groupListTemp));
			resultMap.put("data", JSONObject.fromObject(dataMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

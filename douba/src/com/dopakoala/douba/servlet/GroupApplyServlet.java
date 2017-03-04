package com.dopakoala.douba.servlet;

import java.io.IOException;
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
import com.dopakoala.douba.entity.GroupApplyUser;
import com.dopakoala.douba.entity.GroupUser;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.jpush.PushAlert;
import com.dopakoala.douba.rongcloud.RongCloudController;
import com.dopakoala.douba.service.IGroupApplyUserService;
import com.dopakoala.douba.service.IGroupService;
import com.dopakoala.douba.service.IGroupUserService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ConstantsUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class GroupApplyService
 */
@WebServlet("/group/apply")
public class GroupApplyServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IGroupApplyUserService groupApplyUserService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupUserService groupUserService;
	@Autowired
	private IGroupService groupService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GroupApplyServlet() {
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

			String token = "", content = "";
			int uid = 0, gid = 0;
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
						if (gid < 0) {
							errmsg = "传入的跑团id不合法！";
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
				// 获取是否已加入本群
				// 判断是否为跑团
				Group groupTemp = this.groupService.selectByPrimaryKey(gid);
				int gidTemp = 0;
				if (groupTemp != null) {
					int pid = groupTemp.getPid();
					if (pid == 0) {
						// 获取跑团大群
						Group groupTemp1 = this.groupService.selectMainByPid(groupTemp.getGid());
						if (groupTemp1 != null) {
							// 获取大群gid
							gidTemp = groupTemp1.getGid();
						}
					} else {
						gidTemp = groupTemp.getGid();
					}
				}
				// 获取用户加入信息
				GroupUser selectGroupUserTemp = new GroupUser();
				selectGroupUserTemp.setUid(uid);
				selectGroupUserTemp.setGid(gidTemp);
				GroupUser groupUserTemp = this.groupUserService.selectByUidGid(selectGroupUserTemp);
				if (groupUserTemp == null) {
					// 获取用户是否有申请记录
					GroupApplyUser selectGroupApplyUser = new GroupApplyUser();
					selectGroupApplyUser.setGid(gid);
					selectGroupApplyUser.setUid(uid);
					GroupApplyUser temptGroupApplyUser = this.groupApplyUserService
							.selectByGidUid(selectGroupApplyUser);
					if (temptGroupApplyUser == null) {
						// 提交申请
						GroupApplyUser groupApplyUser = new GroupApplyUser();
						groupApplyUser.setUid(uid);
						groupApplyUser.setGid(gid);
						groupApplyUser.setContent(content);
						this.groupApplyUserService.insertSelective(groupApplyUser);

						// 将申请人插入到临时群
						// 判断当前申请信息是否为跑团
						Group tempGroup = this.groupService.selectByPrimaryKey(gid);

						String[] users = { uid + "" };
						Group temp = new Group();
						if (tempGroup.getPid() == 0) {
							// 跑团
							// 根据跑团gid获取临时群
							temp = this.groupService.selectTempByPid(gid);

							// // 获取跑团中人员数
							// Group selectTempGroup = new Group();
							// selectTempGroup.setStatus(1);
							// selectTempGroup.setGid(gid);
							// int number =
							// this.groupService.selectByPrimaryKey(selectTempGroup).getNumber();
							// // 更新跑团中人数信息
							// Group updateTempGroup = new Group();
							// updateTempGroup.setGid(gid);
							// updateTempGroup.setNumber(number + 1);
							// this.groupService.updateByPrimaryKeySelective(updateTempGroup);
							// } else {
							// // 群
							// // 根据群pid获取临时群
							// temp =
							// this.groupService.selectTempByPid(tempGroup.getPid());
							// }

							if (RongCloudController.getInstance(temp, users).applyGroup() == 200) {
								// 插入到临时群
								GroupUser insertGroupUser = new GroupUser();
								insertGroupUser.setGid(temp.getGid());
								insertGroupUser.setUid(uid);
								insertGroupUser.setIntro(user.getPersonalIntroduction());
								insertGroupUser.setNickname(user.getNickname());
								insertGroupUser.setStatus(1);
								this.groupUserService.insertSelective(insertGroupUser);

								// 获取群中人员数
								int number = this.groupService.selectByPrimaryKey(temp.getGid()).getNumber();
								// 更新群中人数信息
								Group updateTempGroup = new Group();
								updateTempGroup.setGid(temp.getGid());
								updateTempGroup.setNumber(number + 1);
								this.groupService.updateByPrimaryKeySelective(updateTempGroup);

								// 推送申请消息到管理员
								// 获取申请加入的跑团或群信息
								Group group = this.groupService.selectByPrimaryKey(gid);
								if (group != null) {
									String title = "";
									Map<String, String> extras = new HashMap<>();
									// 判断是跑团还是群
									if (group.getPid() > 0) {
										// 群
										title = "申请加入群通知";
										extras.put("msgType", ConstantsUtils.APPLY_GROUP);
									} else {
										// 跑团
										title = "申请加入跑团通知";
										extras.put("msgType", ConstantsUtils.APPLY_PAOTUAN);
									}
									// 获取管理员uid
									List<GroupUser> groupUserList = this.groupUserService.selectAdminByGid(gid);
									if (groupUserList != null && groupUserList.size() > 0) {
										for (GroupUser groupUser : groupUserList) {
											String alert = user.getNickname() + " 申请加入 " + group.getName() + " 请及时处理！";
											int pushCode = PushAlert.push(groupUser.getUid(), alert, title, extras);
											if (pushCode != 200) {
												errmsg = "推送信息失败！";
											}
										}
									}
								}
							} else {
								errcode = 500;
								errmsg = "融云服务器断开！";
							}

						}
					} else {
						errmsg = "重复的申请信息！";
						errcode = 400;
					}
				} else {
					errmsg = "已经加入所申请的跑团（群），请勿重复申请";
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

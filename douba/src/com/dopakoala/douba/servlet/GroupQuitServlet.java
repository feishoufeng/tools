package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.dopakoala.douba.entity.Group;
import com.dopakoala.douba.entity.GroupUser;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.rongcloud.RongCloudController;
import com.dopakoala.douba.service.IGroupService;
import com.dopakoala.douba.service.IGroupUserService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class GroupQuitServlet
 */
@WebServlet("/group/quit")
public class GroupQuitServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupUserService groupUserService;
	@Autowired
	private IGroupService groupService;

	/**
	 * Default constructor.
	 */
	public GroupQuitServlet() {
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
			int uid = 0, gid = 0;
			long timestamp = 0;
			JSONArray jsonArray = null;

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
				if (!parameters.isNullObject() && parameters.containsKey("list")) {
					jsonArray = parameters.getJSONArray("list");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						if (gid < 1) {
							errmsg = "传入的gid不合法！";
							errcode = 336;
						} else {
							if (jsonArray == null || jsonArray.size() < 1) {
								errmsg = "传入list错误！";
								errcode = 339;
							}
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

			if (errcode == 0) {
				// 遍历退跑团（群）的用户uid
				for (int i = 0; i < jsonArray.size(); i++) {
					// 获取用户uid
					int quitUid = jsonArray.getInt(i);
					// 拼接需要退跑团（群）的用户uid数组
					String[] users = { quitUid + "" };
					// 获取跑团（群信息）
					Group group = this.groupService.selectByPrimaryKey(gid);
					if (group != null) {
						int pid = group.getPid();
						// 通过pid判断是跑团还是群
						if (pid <= 0) {
							// 跑团
							// 获取大群信息
							Group mainGroup = this.groupService.selectMainByPid(gid);
							// 获取用户参与大群记录
							GroupUser selectTempGroupUser = new GroupUser();
							selectTempGroupUser.setUid(quitUid);
							selectTempGroupUser.setGid(mainGroup.getGid());
							GroupUser groupTempUser = this.groupUserService.selectByUidGid(selectTempGroupUser);
							if (groupTempUser != null) {
								// 超级管理员退跑团，代表解散跑团
								if (groupTempUser.getRoot() == 1) {
									// 查询对应跑团下的群
									Group selectGroup = new Group();
									selectGroup.setPid(gid);
									selectGroup.setUid(quitUid);
									List<Group> groupList = this.groupService.selectByPidUid(selectGroup);
									// 获取用户对应的群的加入信息
									for (int j = 0; j < groupList.size(); j++) {
										if (RongCloudController.getInstance(groupList.get(j), users).dismiss() == 200) {
											// 更新群下所有用户状态
											GroupUser updateGroupUser = new GroupUser();
											updateGroupUser.setStatus(-1);
											updateGroupUser.setGid(groupList.get(j).getGid());
											this.groupUserService.updateByGidSelective(updateGroupUser);
										} else {
											errcode = 500;
											errmsg = "融云服务器断开！";
										}
									}
									// 逻辑删除群
									this.groupService.updateQuitByPid(gid);
									// 逻辑删除跑团
									Group updateGroupTemp = new Group();
									updateGroupTemp.setGid(gid);
									updateGroupTemp.setStatus(-1);
									this.groupService.updateByPrimaryKeySelective(updateGroupTemp);
								} else {
									// 查询对应跑团下的群
									Group selectGroup = new Group();
									selectGroup.setPid(gid);
									selectGroup.setUid(quitUid);
									List<Group> groupList = this.groupService.selectByPidUid(selectGroup);
									// 获取用户对应的群的加入信息
									for (int j = 0; j < groupList.size(); j++) {
										Group tempGroup = groupList.get(j);
										int groupGid = tempGroup.getGid();
										GroupUser selectGroupUser = new GroupUser();
										selectGroupUser.setUid(quitUid);
										selectGroupUser.setGid(groupGid);
										GroupUser groupUser = this.groupUserService.selectByUidGid(selectGroupUser);
										if (groupUser != null) {
											// 退群操作
											if (RongCloudController.getInstance(tempGroup, users).quit() == 200) {
												GroupUser updateGroupUser = new GroupUser();
												updateGroupUser.setId(groupUser.getId());
												updateGroupUser.setStatus(-1);
												this.groupUserService.updateByPrimaryKeySelective(updateGroupUser);

												// 获取群中人员数
												int number = this.groupService.selectByPrimaryKey(tempGroup.getGid())
														.getNumber();
												// 更新群中人数信息
												Group updateTempGroup = new Group();
												updateTempGroup.setGid(tempGroup.getGid());
												updateTempGroup.setNumber(number - 1);
												this.groupService.updateByPrimaryKeySelective(updateTempGroup);
											} else {
												errcode = 500;
												errmsg = "融云服务器断开！";
											}
										}
									}
									// 获取跑团中人员数
									int number1 = this.groupService.selectByPrimaryKey(gid).getNumber();
									// 更新跑团中人数信息
									Group updateTempGroup1 = new Group();
									updateTempGroup1.setGid(gid);
									updateTempGroup1.setNumber(number1 - 1);
									this.groupService.updateByPrimaryKeySelective(updateTempGroup1);
								}
							}
						} else {
							// 判断是否为大群
							Group mainGroup = this.groupService.selectMainByPid(group.getPid());
							if (mainGroup != null && mainGroup.getGid().intValue() == group.getGid().intValue()) {
								// 获取用户参与群记录
								GroupUser selectGroupUser = new GroupUser();
								selectGroupUser.setUid(quitUid);
								selectGroupUser.setGid(gid);
								GroupUser groupUser = this.groupUserService.selectByUidGid(selectGroupUser);
								if (groupUser != null) {
									// 退群操作
									// 管理员退大群，代表解散跑团
									if (groupUser.getRoot() == 1) {
										// 查询对应跑团下的群
										Group selectGroup = new Group();
										selectGroup.setPid(mainGroup.getPid());
										selectGroup.setUid(quitUid);
										List<Group> groupList = this.groupService.selectByPidUid(selectGroup);
										// 获取用户对应的群的加入信息
										for (int j = 0; j < groupList.size(); j++) {
											if (RongCloudController.getInstance(groupList.get(j), users)
													.dismiss() == 200) {
												// 更新群下所有用户状态
												GroupUser updateGroupUser = new GroupUser();
												updateGroupUser.setStatus(-1);
												updateGroupUser.setGid(groupList.get(j).getGid());
												this.groupUserService.updateByGidSelective(updateGroupUser);
											} else {
												errcode = 500;
												errmsg = "融云服务器断开！";
											}
										}
										// 逻辑删除群
										this.groupService.updateQuitByPid(mainGroup.getPid());
										// 逻辑删除跑团
										Group updateGroupTemp = new Group();
										updateGroupTemp.setGid(mainGroup.getPid());
										updateGroupTemp.setStatus(-1);
										this.groupService.updateByPrimaryKeySelective(updateGroupTemp);
									} else {
										// 获取超级管理人员
										GroupUser groupUserSuper = this.groupUserService
												.selectSuperByGid(mainGroup.getGid());
										// 跑团
										// 查询对应跑团下的群
										Group selectGroup = new Group();
										selectGroup.setPid(group.getPid());
										selectGroup.setUid(quitUid);
										List<Group> groupList = this.groupService.selectByPidUid(selectGroup);
										// 获取用户对应的群的加入信息
										for (int j = 0; j < groupList.size(); j++) {
											Group tempGroup = groupList.get(j);
											int groupGid = tempGroup.getGid();
											GroupUser selectGroupUser1 = new GroupUser();
											selectGroupUser1.setUid(quitUid);
											selectGroupUser1.setGid(groupGid);
											GroupUser groupUser1 = this.groupUserService
													.selectByUidGid(selectGroupUser1);
											if (groupUser1 != null) {
												// 退群操作
												if (RongCloudController.getInstance(tempGroup, users).quit() == 200) {
													GroupUser updateGroupUser = new GroupUser();
													updateGroupUser.setId(groupUser1.getId());
													updateGroupUser.setStatus(-1);
													this.groupUserService.updateByPrimaryKeySelective(updateGroupUser);
													// 判断是否为卡开人员
													int open = groupUser1.getOpen();
													if (open == 1) {
														// 将开卡权限交给管理员
														GroupUser updateGroupUserSuper = new GroupUser();
														updateGroupUserSuper.setOpen(1);
														updateGroupUserSuper.setId(groupUserSuper.getId());
														this.groupUserService
																.updateByPrimaryKeySelective(updateGroupUserSuper);
													}

													// 获取群中人员数
													int number = this.groupService.selectByPrimaryKey(groupGid)
															.getNumber();
													// 更新群中人数信息
													Group updateTempGroup = new Group();
													updateTempGroup.setGid(groupGid);
													updateTempGroup.setNumber(number - 1);
													this.groupService.updateByPrimaryKeySelective(updateTempGroup);
												} else {
													errcode = 500;
													errmsg = "融云服务器断开！";
												}
											}
										}
										// 获取跑团中人员数
										int number1 = this.groupService.selectByPrimaryKey(group.getPid()).getNumber();
										// 更新跑团中人数信息
										Group updateTempGroup1 = new Group();
										updateTempGroup1.setGid(group.getPid());
										updateTempGroup1.setNumber(number1 - 1);
										this.groupService.updateByPrimaryKeySelective(updateTempGroup1);
									}
								}
							} else {
								// 小群
								// 获取用户参与群记录
								GroupUser selectGroupUser = new GroupUser();
								selectGroupUser.setUid(quitUid);
								selectGroupUser.setGid(gid);
								GroupUser groupUser = this.groupUserService.selectByUidGid(selectGroupUser);
								if (groupUser != null) {
									// 退群操作
									// 管理员退群，代表解散本群
									if (groupUser.getRoot() == 1) {
										GroupUser selectTempGroupUser = new GroupUser();
										selectTempGroupUser.setGid(gid);
										selectTempGroupUser.setPage(0);
										selectTempGroupUser.setPagesize(0);
										List<GroupUser> groupUserList = this.groupUserService
												.selectByGid(selectTempGroupUser);
										if (groupUserList != null && groupUserList.size() > 0) {
											if (RongCloudController.getInstance(group, users).dismiss() == 200) {
												// 更新群下所有用户状态
												GroupUser updateGroupUser = new GroupUser();
												updateGroupUser.setStatus(-1);
												updateGroupUser.setGid(gid);
												this.groupUserService.updateByGidSelective(updateGroupUser);
												// 逻辑删除群
												Group updateGroup = new Group();
												updateGroup.setGid(gid);
												updateGroup.setStatus(-1);
												this.groupService.updateByPrimaryKeySelective(updateGroup);
											} else {
												errcode = 500;
												errmsg = "融云服务器断开！";
											}
										}
									} else {
										if (RongCloudController.getInstance(group, users).quit() == 200) {
											GroupUser updateGroupUser = new GroupUser();
											updateGroupUser.setId(groupUser.getId());
											updateGroupUser.setStatus(-1);
											this.groupUserService.updateByPrimaryKeySelective(updateGroupUser);

											// 获取群中人员数
											int number = this.groupService.selectByPrimaryKey(gid).getNumber();
											// 更新群中人数信息
											Group updateTempGroup = new Group();
											updateTempGroup.setGid(gid);
											updateTempGroup.setNumber(number - 1);
											this.groupService.updateByPrimaryKeySelective(updateTempGroup);
										} else {
											errcode = 500;
											errmsg = "融云服务器断开！";
										}
									}
								}
							}
						}
					} else {
						errmsg = "请求的gid无法获取跑团（群）";
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
			}
		} catch (

		Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}
}
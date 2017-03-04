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
 * Servlet implementation class GroupReviewServlet
 */
@WebServlet("/group/review")
public class GroupReviewServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupUserService groupUserService;
	@Autowired
	private IGroupApplyUserService groupApplyUserService;
	@Autowired
	private IGroupService groupService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GroupReviewServlet() {
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
			int uid = 0, gid = 0, id = 0, agreement = 0;
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
				if (!parameters.isNullObject() && parameters.containsKey("id")) {
					id = parameters.getInt("id");
				}
				if (!parameters.isNullObject() && parameters.containsKey("agreement")) {
					agreement = parameters.getInt("agreement");
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
						} else {
							if (id < 0) {
								errmsg = "传入的申请id不合法！";
								errcode = 600;
							} else {
								if (agreement != 0 && agreement != 1) {
									errmsg = "传入的审核状态不合法！";
									errcode = 600;
								}
							}
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
				if (agreement == 1) {
					// 通过申请id获取相应的申请信息
					GroupApplyUser groupApplyUser = this.groupApplyUserService.selectByPrimaryKey(id);
					if (groupApplyUser != null) {
						int applyUid = groupApplyUser.getUid();
						User applyUser = this.userService.selectByPrimaryKey(applyUid);
						// 获取申请加入跑团（群）的申请人信息
						if (applyUser != null) {
							// 通过对应的gid获取相应的跑团（群）
							Group group = this.groupService.selectByPrimaryKey(gid);
							if (group != null) {
								if (group.getStatus() != 1) {
									errmsg = "获取跑团（群）的状态异常，无法进行操作！";
									errcode = 400;
								} else {
									int pid = group.getPid();
									String[] users = { applyUid + "" };
									if (pid > 0) {
										int code = RongCloudController.getInstance(group, users).applyGroup();
										if (code == 200) {
											// 插入到群成员表
											GroupUser insertGroupUser = new GroupUser();
											insertGroupUser.setGid(gid);
											insertGroupUser.setUid(applyUser.getUid());
											insertGroupUser.setLevel(0);
											insertGroupUser.setIntro(applyUser.getPersonalIntroduction());
											insertGroupUser.setNickname(applyUser.getNickname());
											insertGroupUser.setStatus(1);
											this.groupUserService.insertSelective(insertGroupUser);
											// 删除申请表
											this.groupApplyUserService.deleteByPrimaryKey(id);

											// 获取群中人员数
											Group tempGroup = this.groupService.selectByPrimaryKey(gid);
											int number = tempGroup.getNumber();
											// 更新群中人数信息
											Group updateTempGroup = new Group();
											updateTempGroup.setGid(gid);
											updateTempGroup.setNumber(number + 1);
											this.groupService.updateByPrimaryKeySelective(updateTempGroup);

											if (tempGroup != null) {
												if (tempGroup.getPid() == 0) {
													// 获取临时群
													Group temp = this.groupService.selectTempByPid(group.getPid());
													// 获取用户临时群中的信息
													GroupUser tempSelectGroupUser = new GroupUser();
													tempSelectGroupUser.setGid(temp.getGid());
													tempSelectGroupUser.setUid(applyUid);
													GroupUser tempGroupUser = this.groupUserService
															.selectByUidGid(tempSelectGroupUser);
													// 退出融云
													if (RongCloudController.getInstance(temp, users).quit() == 200) {
														// 退出临时群
														GroupUser updateGroupUser = new GroupUser();
														updateGroupUser.setStatus(-1);
														updateGroupUser.setId(tempGroupUser.getId());
														this.groupUserService
																.updateByPrimaryKeySelective(updateGroupUser);

														// 获取群中人员数
														Group groupTemp = this.groupService
																.selectByPrimaryKey(temp.getGid());
														int num = groupTemp.getNumber();
														// 更新群中人数信息
														Group tempUpdateGroup = new Group();
														tempUpdateGroup.setGid(temp.getGid());
														tempUpdateGroup.setNumber(num - 1);
														this.groupService.updateByPrimaryKeySelective(tempUpdateGroup);
													}
												}
											}

											// 推送加入成功信息
											String alert = "恭喜您成功加入群 " + tempGroup.getName() + "!";
											String title = "加入群审核通知";
											Map<String, String> extras = new HashMap<>();
											extras.put("msgType", ConstantsUtils.CHECK_APPLY_GROUP);
											int pushCode = PushAlert.push(applyUser.getUid(), alert, title, extras);
											if (pushCode != 200) {
												errmsg = "推送信息失败！";
											}
										}
									} else {
										// 插入到跑团成员表
										// GroupUser insertGroupUser = new
										// GroupUser();
										// insertGroupUser.setGid(gid);
										// insertGroupUser.setUid(applyUser.getUid());
										// insertGroupUser.setLevel(0);
										// insertGroupUser.setIntro(applyUser.getPersonalIntroduction());
										// insertGroupUser.setNickname(applyUser.getNickname());
										// insertGroupUser.setStatus(1);
										// this.groupUserService.insertSelective(insertGroupUser);
										// 默认将成员加入到大群
										Group tempGroup = this.groupService.selectMainByPid(gid);
										if (tempGroup != null) {
											// 获取大群信息
											int code = RongCloudController.getInstance(tempGroup, users).applyGroup();
											if (code == 200) {
												// 插入到群成员表
												GroupUser tempGroupUser = new GroupUser();
												tempGroupUser.setGid(tempGroup.getGid());
												tempGroupUser.setUid(applyUser.getUid());
												tempGroupUser.setLevel(0);
												tempGroupUser.setIntro(applyUser.getPersonalIntroduction());
												tempGroupUser.setNickname(applyUser.getNickname());
												tempGroupUser.setStatus(1);
												this.groupUserService.insertSelective(tempGroupUser);
												// 删除申请表
												this.groupApplyUserService.deleteByPrimaryKey(id);

												// 获取群中人员数
												Group tempGroup1 = this.groupService
														.selectByPrimaryKey(tempGroup.getGid());
												int number = tempGroup1.getNumber();
												// 更新群中人数信息
												Group updateTempGroup = new Group();
												updateTempGroup.setGid(tempGroup.getGid());
												updateTempGroup.setNumber(number + 1);
												this.groupService.updateByPrimaryKeySelective(updateTempGroup);

												// 获取跑团中人员数
												Group tempGroup2 = this.groupService.selectByPrimaryKey(gid);
												int number1 = tempGroup2.getNumber();
												// 更新跑团中人数信息
												Group updateTempGroup1 = new Group();
												updateTempGroup1.setGid(gid);
												updateTempGroup1.setNumber(number1 + 1);
												this.groupService.updateByPrimaryKeySelective(updateTempGroup1);

												// 获取临时群
												Group temp = this.groupService.selectTempByPid(gid);
												// 获取用户临时群中的信息
												GroupUser tempSelectGroupUser = new GroupUser();
												tempSelectGroupUser.setGid(temp.getGid());
												tempSelectGroupUser.setUid(applyUser.getUid());
												GroupUser tempGroupUser1 = this.groupUserService
														.selectByUidGid(tempSelectGroupUser);
												// 退出融云
												if (RongCloudController.getInstance(temp, users).quit() == 200) {
													// 退出临时群
													GroupUser updateGroupUser = new GroupUser();
													updateGroupUser.setStatus(-1);
													updateGroupUser.setId(tempGroupUser1.getId());
													this.groupUserService.updateByPrimaryKeySelective(updateGroupUser);

													// 获取群中人员数
													Group groupTemp = this.groupService
															.selectByPrimaryKey(temp.getGid());
													int num = groupTemp.getNumber();
													// 更新群中人数信息
													Group tempUpdateGroup = new Group();
													tempUpdateGroup.setGid(temp.getGid());
													tempUpdateGroup.setNumber(num - 1);
													this.groupService.updateByPrimaryKeySelective(tempUpdateGroup);
												}

												// 推送加入成功信息
												String title = "加入跑团审核通知";
												String alert = "恭喜您成功加入跑团 " + tempGroup2.getName() + "!";
												Map<String, String> extras = new HashMap<>();
												extras.put("msgType", ConstantsUtils.CHECK_APPLY_PAOTUAN);
												int pushCode = PushAlert.push(applyUser.getUid(), alert, title, extras);
												if (pushCode != 200) {
													errmsg = "推送信息失败！";
												}
											} else {
												errcode = 500;
												errmsg = "融云服务器断开！";
											}

										} else {
											errmsg = "获取跑团大群失败！";
											errcode = 400;
										}
									}
								}
							} else {
								errmsg = "获取跑团（群）失败！";
								errcode = 400;
							}
						} else {
							errmsg = "获取申请人信息失败！";
							errcode = 400;
						}
					} else {
						errmsg = "获取申请信息失败！";
						errcode = 400;
					}
				} else {
					String alert = "";
					String title = "";
					// 通过申请id获取相应的申请信息
					GroupApplyUser groupApplyUser = this.groupApplyUserService.selectByPrimaryKey(id);
					if (groupApplyUser != null) {
						String[] users = { groupApplyUser.getUid() + "" };
						// 获取申请（跑团）群信息
						Group selectGroup = new Group();
						selectGroup.setUid(uid);
						selectGroup.setGid(gid);
						Group tempGroup = this.groupService.selectByPrimaryKey(gid);
						// 判断是否为跑团
						Group temp = new Group();
						// 推送拒绝信息
						Map<String, String> extras = new HashMap<>();
						if (tempGroup.getPid() > 0) {
							// 群
							// 获取临时群
							temp = this.groupService.selectTempByPid(tempGroup.getPid());
							alert = "群  " + tempGroup.getName() + " 管理员拒绝了您加入群的申请!";
							title = "加入群审核通知";
							extras.put("msgType", ConstantsUtils.CHECK_APPLY_GROUP);
						} else {
							// 跑团
							// 获取临时群
							temp = this.groupService.selectTempByPid(gid);
							alert = "跑团  " + tempGroup.getName() + " 管理员拒绝了您加入跑团的申请!";
							title = "加入跑团审核通知";
							extras.put("msgType", ConstantsUtils.CHECK_APPLY_PAOTUAN);
						}
						// 退出融云
						if (RongCloudController.getInstance(temp, users).quit() == 200) {
							int pushCode = PushAlert.push(groupApplyUser.getUid(), alert, title, extras);
							if (pushCode != 200) {
								errmsg = "推送信息失败！";
							}

							// 获取用户临时群中的信息
							GroupUser tempSelectGroupUser = new GroupUser();
							tempSelectGroupUser.setGid(temp.getGid());
							tempSelectGroupUser.setUid(groupApplyUser.getUid());
							GroupUser tempGroupUser = this.groupUserService.selectByUidGid(tempSelectGroupUser);
							// 退出临时群
							GroupUser updateGroupUser = new GroupUser();
							updateGroupUser.setStatus(-1);
							updateGroupUser.setId(tempGroupUser.getId());
							this.groupUserService.updateByPrimaryKeySelective(updateGroupUser);

							// 获取群中人员数
							Group groupTemp = this.groupService.selectByPrimaryKey(temp.getGid());
							int num = groupTemp.getNumber();
							// 更新群中人数信息
							Group tempUpdateGroup = new Group();
							tempUpdateGroup.setGid(temp.getGid());
							tempUpdateGroup.setNumber(num - 1);
							this.groupService.updateByPrimaryKeySelective(tempUpdateGroup);
							// 删除申请信息
							this.groupApplyUserService.deleteByPrimaryKey(id);
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

			ServletUtils.writeJson(response, resultMap);

		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

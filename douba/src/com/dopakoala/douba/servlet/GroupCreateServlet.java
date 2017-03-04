package com.dopakoala.douba.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.dopakoala.douba.entity.Group;
import com.dopakoala.douba.entity.GroupPace;
import com.dopakoala.douba.entity.GroupUser;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.rongcloud.RongCloudController;
import com.dopakoala.douba.service.IGroupPaceService;
import com.dopakoala.douba.service.IGroupService;
import com.dopakoala.douba.service.IGroupUserService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ConstantsUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.QRCodeUtil;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class GroupCreateServlet
 */
@WebServlet("/group/create")
public class GroupCreateServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupService groupService;
	@Autowired
	private IGroupUserService groupUserService;
	@Autowired
	private IGroupPaceService groupPaceService;

	/**
	 * Default constructor.
	 */
	public GroupCreateServlet() {
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

			request.setCharacterEncoding("UTF-8");
			// 文件保存相对路径
			String path = AchieveUtils.getRealPath();
			String url = AchieveUtils.getConfVal("host");

			String token = "", name = "", content = "", fileName = "", logo = "", condition = "";
			int uid = 0, gid = 0, type = 0, minPace = 0;
			long timestamp = 0;
			double minDistance = 0.00;

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
				if (!parameters.isNullObject() && parameters.containsKey("name")) {
					name = parameters.getString("name");
				}
				if (!parameters.isNullObject() && parameters.containsKey("type")) {
					type = parameters.getInt("type");
				}
				if (!parameters.isNullObject() && parameters.containsKey("content")) {
					content = parameters.getString("content");
				}
				if (!parameters.isNullObject() && parameters.containsKey("fileName")) {
					fileName = parameters.getString("fileName");
				}
				if (!parameters.isNullObject() && parameters.containsKey("logo")) {
					logo = parameters.getString("logo");
				}
				if (!parameters.isNullObject() && parameters.containsKey("condition")) {
					condition = parameters.getString("condition");
				}
				if (!parameters.isNullObject() && parameters.containsKey("minPace")) {
					minPace = parameters.getInt("minPace");
				}
				if (!parameters.isNullObject() && parameters.containsKey("minDistance")) {
					minDistance = parameters.getDouble("minDistance");
				}
			}

			User user = this.userService.selectByPrimaryKey(uid);
			if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
				// 获取用户是否具有权限
				if (user != null) {
					String tempToken = user.getToken();
					if (tempToken.equals(token)) {
						// 判断传入的跑团id是否合法
						if (gid > 0 || gid == -1) {
							if (!CheckUtils.isNull(name)) {
								if (type > 0 || type == -1) {
									if (!CheckUtils.isNull(content)) {
										if (CheckUtils.isNull(condition)) {
											errmsg = "入团条件不可以为空！";
											errcode = 600;
										}
									} else {
										errmsg = "跑团(群)简介不可以为空！";
										errcode = 600;
									}
								} else {
									errmsg = "跑团(群)类型错误！";
									errcode = 600;
								}
							} else {
								errmsg = "跑团(群)名称为空！";
								errcode = 600;
							}
						} else {
							errmsg = "传入的跑团(群)id不合法！";
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

			String avatar = "";
			int currGid = 0;
			if (errcode == 0) {
				Group group = new Group();
				String folderPath = path;
				String webPath = url;
				if (!CheckUtils.isNull(logo)) {
					if (!CheckUtils.isNull(fileName)) {
						if (gid != -1) {
							folderPath += ConstantsUtils.GROUP_HEADER_PATH;
						} else {
							folderPath += ConstantsUtils.PAOTUAN_HEADER_PATH;
						}
						if (ConvertUtils.GenerateFile(logo, folderPath, fileName)) {
							if (gid != -1) {
								webPath += ConstantsUtils.GROUP_HEADER_PATH;
							} else {
								webPath += ConstantsUtils.PAOTUAN_HEADER_PATH;
							}
							avatar = webPath + "/" + fileName;
						} else {
							errmsg = "图片处理失败！";
							errcode = 300;
						}
					} else {
						errmsg = "请求图片名称不可以为空！";
						errcode = 600;
					}
				} else {
					if (gid != -1) {
						// 默认群头像
						avatar = url + ConstantsUtils.DEFAULT_GROUP_HEADER_PATH;
					} else {
						// 默认跑团头像
						avatar = url + ConstantsUtils.DEFAULT_PAOTUAN_HEADER_PATH;
					}
				}

				String[] users = { uid + "" };
				if (errcode == 0) {
					if (gid == -1) {
						group.setType(type);
					} else {
						group.setPid(gid);
					}
					group.setAvatar(avatar);
					group.setName(name);
					group.setContent(content);
					group.setCreateUser(uid);
					group.setCondition(condition);
					this.groupService.insertSelective(group);
					currGid = group.getGid();

					// 生成二维码图片
					if (gid == -1) {
						// 生成跑团二维码图片
						JSONObject json = new JSONObject();
						json.put("type", "paotuan");
						json.put("gid", currGid);
						String imgPath = QRCodeUtil.createQRCode(currGid, json.toString(),
								ConstantsUtils.PAOTUAN_QRCODE_PATH, path, url,
								ConstantsUtils.DEFAULT_QRCODE_CENTER_LOGO_PATH);
						// 更新二维码地址
						Group updateGroup = new Group();
						updateGroup.setGid(currGid);
						updateGroup.setQrcode(imgPath);
						this.groupService.updateByPrimaryKeySelective(updateGroup);

						// 获取跑团中人员数
						int number = this.groupService.selectByPrimaryKey(currGid).getNumber();
						// 更新跑团中人数信息
						Group updateTempGroup = new Group();
						updateTempGroup.setGid(currGid);
						updateTempGroup.setNumber(number + 1);
						this.groupService.updateByPrimaryKeySelective(updateTempGroup);
					} else {
						// 生成群二维码图片
						JSONObject json = new JSONObject();
						json.put("type", "group");
						json.put("gid", currGid);
						String imgPath = QRCodeUtil.createQRCode(currGid, json.toString(),
								ConstantsUtils.GROUP_QRCODE_PATH, path, url,
								ConstantsUtils.DEFAULT_QRCODE_CENTER_LOGO_PATH);
						// 更新二维码地址
						Group updateGroup = new Group();
						updateGroup.setGid(currGid);
						updateGroup.setQrcode(imgPath);
						this.groupService.updateByPrimaryKeySelective(updateGroup);

						// 获取群中人员数
						int number = this.groupService.selectByPrimaryKey(currGid).getNumber();
						// 更新群中人数信息
						Group updateTempGroup = new Group();
						updateTempGroup.setGid(currGid);
						updateTempGroup.setNumber(number + 1);
						this.groupService.updateByPrimaryKeySelective(updateTempGroup);
					}

					// 新建跑团后立即建立一个大群
					if (gid == -1) {
						if (currGid > 0) {
							Group tempGroup = new Group();
							tempGroup.setPid(currGid);
							tempGroup.setAvatar(url + ConstantsUtils.DEFAULT_GROUP_HEADER_PATH);
							tempGroup.setName(name);
							tempGroup.setContent(content);
							tempGroup.setIsmain(1);
							tempGroup.setCreateUser(uid);
							tempGroup.setCondition(condition);
							this.groupService.insertSelective(tempGroup);

							// 设置群配速
							GroupPace insertGroupPace = new GroupPace();
							insertGroupPace.setMinDistance(minDistance);
							insertGroupPace.setMinPace(minPace);
							insertGroupPace.setGid(tempGroup.getGid());
							this.groupPaceService.insertSelective(insertGroupPace);

							// 生成群二维码图片
							JSONObject json = new JSONObject();
							json.put("type", "group");
							json.put("gid", tempGroup.getGid());
							String imgPath = QRCodeUtil.createQRCode(tempGroup.getGid(), json.toString(),
									ConstantsUtils.GROUP_QRCODE_PATH, path, url,
									ConstantsUtils.DEFAULT_QRCODE_CENTER_LOGO_PATH);
							// 更新二维码地址
							Group updateGroup = new Group();
							updateGroup.setGid(tempGroup.getGid());
							updateGroup.setQrcode(imgPath);
							this.groupService.updateByPrimaryKeySelective(updateGroup);

							int code = RongCloudController.getInstance(tempGroup, users).createGroup();
							if (code == 200) {
								// 插入到跑团（群）成员表
								GroupUser insertGroupUser = new GroupUser();
								insertGroupUser.setGid(tempGroup.getGid());
								insertGroupUser.setUid(uid);
								insertGroupUser.setRoot(1);
								insertGroupUser.setOpen(1);
								insertGroupUser.setIntro(user.getPersonalIntroduction());
								insertGroupUser.setNickname(user.getNickname());
								insertGroupUser.setStatus(1);
								this.groupUserService.insertSelective(insertGroupUser);

								// 获取群中人员数
								int number = this.groupService.selectByPrimaryKey(tempGroup.getGid()).getNumber();
								// 更新群中人数信息
								Group updateTempGroup = new Group();
								updateTempGroup.setGid(tempGroup.getGid());
								updateTempGroup.setNumber(number + 1);
								this.groupService.updateByPrimaryKeySelective(updateTempGroup);

							} else {
								errcode = 500;
								errmsg = "融云服务器断开！";
							}
						}
					} else {
						int code = RongCloudController.getInstance(group, users).createGroup();
						if (code == 200) {
							// 插入到跑团（群）成员表
							GroupUser insertGroupUser = new GroupUser();
							insertGroupUser.setGid(currGid);
							insertGroupUser.setUid(uid);
							insertGroupUser.setRoot(1);
							insertGroupUser.setIntro(user.getPersonalIntroduction());
							insertGroupUser.setNickname(user.getNickname());
							insertGroupUser.setStatus(1);
							this.groupUserService.insertSelective(insertGroupUser);
						} else {
							errcode = 500;
							errmsg = "融云服务器断开！";
						}
					}

					// 新建跑团后再建立一个临时群
					if (gid == -1) {
						if (currGid > 0) {
							Group tempGroup = new Group();
							tempGroup.setPid(currGid);
							tempGroup.setAvatar(url + ConstantsUtils.DEFAULT_GROUP_HEADER_PATH);
							tempGroup.setName(name + "(预备群)");
							tempGroup.setContent(content);
							tempGroup.setCreateUser(uid);
							tempGroup.setCondition(condition);
							tempGroup.setIstemp(1);
							this.groupService.insertSelective(tempGroup);

							// 生成群二维码图片
							JSONObject json = new JSONObject();
							json.put("type", "group");
							json.put("gid", tempGroup.getGid());
							String imgPath = QRCodeUtil.createQRCode(tempGroup.getGid(), json.toString(),
									ConstantsUtils.GROUP_QRCODE_PATH, path, url,
									ConstantsUtils.DEFAULT_QRCODE_CENTER_LOGO_PATH);
							// 更新二维码地址
							Group updateGroup = new Group();
							updateGroup.setGid(tempGroup.getGid());
							updateGroup.setQrcode(imgPath);
							this.groupService.updateByPrimaryKeySelective(updateGroup);

							int code = RongCloudController.getInstance(tempGroup, users).createGroup();
							if (code == 200) {
								// 插入到跑团（群）成员表
								GroupUser insertGroupUser = new GroupUser();
								insertGroupUser.setGid(tempGroup.getGid());
								insertGroupUser.setUid(uid);
								insertGroupUser.setRoot(1);
								insertGroupUser.setIntro(user.getPersonalIntroduction());
								insertGroupUser.setNickname(user.getNickname());
								insertGroupUser.setStatus(1);
								this.groupUserService.insertSelective(insertGroupUser);

								// 获取群中人员数
								int number = this.groupService.selectByPrimaryKey(tempGroup.getGid()).getNumber();
								// 更新群中人数信息
								Group updateTempGroup = new Group();
								updateTempGroup.setGid(tempGroup.getGid());
								updateTempGroup.setNumber(number + 1);
								this.groupService.updateByPrimaryKeySelective(updateTempGroup);

							} else {
								errcode = 500;
								errmsg = "融云服务器断开！";
							}
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
			Map<String, Object> datatMap = new HashMap<String, Object>();
			datatMap.put("gid", currGid);
			resultMap.put("data", JSONObject.fromObject(datatMap));

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

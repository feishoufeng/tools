package com.dopakoala.douba.rongcloud;

import java.io.Reader;

import com.dopakoala.douba.entity.Group;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.rongcloud.models.CodeSuccessReslut;
import com.dopakoala.douba.rongcloud.models.HistoryMessageReslut;
import com.dopakoala.douba.rongcloud.models.TokenReslut;
import com.dopakoala.douba.utils.ExceptionMsgUtil;
import com.dopakoala.douba.utils.CheckUtils;

import net.sf.json.JSONObject;

public class RongCloudController {

	private Group group;
	private String[] users;
	private String dateStr;
	private User user;

	Reader reader = null;
	RongCloud rongCloud = RongCloud.getInstance();

	public static RongCloudController getInstance(Group group, String[] users) {
		return new RongCloudController(group, users);
	}

	public static RongCloudController getInstance(String dateStr) {
		return new RongCloudController(dateStr);
	}

	public static RongCloudController getInstance(User user) {
		return new RongCloudController(user);
	}

	public RongCloudController(Group group, String[] users) {
		this.group = group;
		this.users = users;
	}

	public RongCloudController(String dateStr) {
		this.dateStr = dateStr;
	}

	public RongCloudController(User user) {
		this.user = user;
	}

	// 创建群
	public int createGroup() {
		try {
			String[] groupCreateUserId = users;
			CodeSuccessReslut groupCreateResult;
			groupCreateResult = rongCloud.group.create(groupCreateUserId, group.getGid() + "", group.getName());
			System.out.println("create:  " + groupCreateResult.getCode());
			return groupCreateResult.getCode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExceptionMsgUtil.getInstance(this.getClass(), e);
			return 400;
		}
	}

	// 加入群
	public int applyGroup() {
		try {
			String[] groupJoinUserId = users;
			CodeSuccessReslut groupJoinResult = rongCloud.group.join(groupJoinUserId, group.getGid() + "",
					group.getName());
			System.out.println("join:  " + groupJoinResult.getCode());
			return groupJoinResult.getCode();
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
			return 400;
		}
	}

	// 退出群
	public int quit() {
		try {
			String[] groupQuitUserId = users;
			CodeSuccessReslut groupQuitResult = rongCloud.group.quit(groupQuitUserId, group.getGid() + "");
			System.out.println("quit:  " + groupQuitResult.getCode());
			return groupQuitResult.getCode();
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
			return 400;
		}
	}

	// 解散群
	public int dismiss() {
		try {
			String[] groupQuitUserId = users;
			CodeSuccessReslut groupDismissResult = rongCloud.group.dismiss(groupQuitUserId[0], group.getGid() + "");
			System.out.println("dismiss:  " + groupDismissResult.getCode());
			return groupDismissResult.getCode();
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
			return 400;
		}
	}

	// 获取历史记录
	public JSONObject getHistory() {
		try {
			JSONObject json = new JSONObject();
			// 通过融云获取聊天记录文件下载地址
			HistoryMessageReslut messageGetHistoryResult = rongCloud.message.getHistory(dateStr);
			// 分析文件的类型
			String fileType = "";
			String temp = "";
			String url = messageGetHistoryResult.getUrl();
			// 判断url是否为空
			if (!CheckUtils.isNull(url)) {
				for (int i = url.length() - 1; i >= 0; i--) {
					temp = url.substring(i, url.length());
					if (temp.indexOf(".") != -1) {
						fileType = temp;
						break;
					} else {
						continue;
					}
				}
				json.put("url", url);
				json.put("fileType", fileType);
				return json;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExceptionMsgUtil.getInstance(this.getClass(), e);
			return null;
		}
	}

	// 获取 Token 方法
	public String getToken() {
		TokenReslut userGetTokenResult;
		String token = "";
		try {
			userGetTokenResult = rongCloud.user.getToken(user.getUid() + "", user.getNickname(), user.getAvatar());
			System.out.println("getToken:  " + userGetTokenResult.getCode());
			if (userGetTokenResult.getCode() == 200) {
				token = userGetTokenResult.getToken();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
		return token;
	}

	public static void main(String[] args) {
		User user = new User();
		user.setAvatar("http://www.rongcloud.cn/images/logo.png");
		user.setUid(2);
		user.setNickname("nihao");
		RongCloudController.getInstance(user).getToken();
	}
}

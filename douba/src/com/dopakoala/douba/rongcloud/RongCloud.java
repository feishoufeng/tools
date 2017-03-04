/**
 * 融云 Server API java 客户端
 * create by kitName
 * create datetime : 2016-10-20 
 * 
 * v2.0.1
 */
package com.dopakoala.douba.rongcloud;

import java.util.concurrent.ConcurrentHashMap;

import com.dopakoala.douba.rongcloud.methods.*;
import com.dopakoala.douba.utils.AchieveUtils;

public class RongCloud {

	private static final String RONGCLOUD_APPKEY = AchieveUtils.getConfVal("rongcloud_appkey");
	private static final String RONGCLOUD_SECRET = AchieveUtils.getConfVal("rongcloud_secret");

	private static ConcurrentHashMap<String, RongCloud> rongCloud = new ConcurrentHashMap<String, RongCloud>();

	public User user;
	public Message message;
	public Wordfilter wordfilter;
	public Group group;
	public Chatroom chatroom;
	public Push push;
	public SMS sms;

	private RongCloud(String appKey, String appSecret) {
		user = new User(appKey, appSecret);
		message = new Message(appKey, appSecret);
		wordfilter = new Wordfilter(appKey, appSecret);
		group = new Group(appKey, appSecret);
		chatroom = new Chatroom(appKey, appSecret);
		push = new Push(appKey, appSecret);
		sms = new SMS(appKey, appSecret);

	}

	public static RongCloud getInstance() {
		if (null == rongCloud.get(RONGCLOUD_APPKEY)) {
			rongCloud.putIfAbsent(RONGCLOUD_APPKEY, new RongCloud(RONGCLOUD_APPKEY, RONGCLOUD_SECRET));
		}
		return rongCloud.get(RONGCLOUD_APPKEY);
	}

}
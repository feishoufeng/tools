package com.dopakoala.douba.jpush;

import com.dopakoala.douba.utils.AchieveUtils;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;

public class CreateClient {
	private static final String appKey = AchieveUtils.getConfVal("jpush_appkey");
	private static final String masterSecret = AchieveUtils.getConfVal("jpush_secret");
	private static final String pushHost = AchieveUtils.getConfVal("jpush_pushHost");
	private static JPushClient client;

	// 私有化构造方法
	private CreateClient() {

	}

	// 创建客户端
	private JPushClient create() {
		if (client == null) {
			ClientConfig config = ClientConfig.getInstance();
			config.setPushHostName(pushHost);
			client = new JPushClient(masterSecret, appKey, null, config);
		}
		return client;
	}

	// 外部调用方法
	public static JPushClient getInstance() {
		return new CreateClient().create();
	}
}

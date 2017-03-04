package com.dopakoala.douba.jpush;

import java.util.Map;

import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class PushStyle {
	/**
	 * 推送到所有用户所有平台
	 * 
	 * @param alert
	 *            推送信息
	 * @return PushPayload
	 */
	public static PushPayload pushAll(String alert) {
		return PushPayload.alertAll(alert);
	}

	/**
	 * 推送到指定用户
	 * 
	 * @param audience
	 *            指定用户
	 * @param alert
	 *            推送信息
	 * @return PushPayload
	 */
	public static PushPayload pushToAudience(Audience audience, String alert) {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(audience)
				.setNotification(Notification.alert(alert)).build();
	}

	/**
	 * 带标题推送
	 * 
	 * @param audience
	 *            指定用户
	 * @param alert
	 *            推送信息
	 * @param title
	 *            推送标题
	 * @return PushPayload
	 */
	public static PushPayload pushWithTitle(Audience audience, String alert, String title) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(audience)
				.setNotification(Notification.android(alert, title, null)).build();
	}

	/**
	 * 带Extras推送
	 * 
	 * @param audience
	 *            指定用户
	 * @param alert
	 *            推送信息
	 * @param title
	 *            推送标题
	 * @param extras
	 *            推送extras
	 * @return PushPayload
	 */
	public static PushPayload pushWithExtras(Audience audience, String alert, String title,
			Map<String, String> extras) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(audience)
				.setNotification(Notification.newBuilder().setAlert(alert)
						.addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).addExtras(extras).build())
						.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtras(extras).build())
						.build())
				.build();
	}
}

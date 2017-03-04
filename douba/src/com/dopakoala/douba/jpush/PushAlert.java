package com.dopakoala.douba.jpush;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

/**
 * 推送信息类
 * 
 * @author ShoufengFei
 *
 */
public class PushAlert {
	protected static final Logger LOG = LoggerFactory.getLogger(PushAlert.class);

	/**
	 * 信息推送
	 * 
	 * @param uid
	 *            指定推送用户
	 * @param alert
	 *            推送信息
	 * @param title
	 *            推送标题
	 * @param extras
	 *            推送extras
	 * @return 相应代码
	 */
	public static int push(int uid, String alert, String title, Map<String, String> extras) {
		PushPayload payload = PushStyle.pushWithExtras(Audience.alias(uid + ""), alert, title, extras);
		try {
			PushResult result = CreateClient.getInstance().sendPush(payload);
			LOG.info("Got result - " + result);
			return result.getResponseCode();

		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);

		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
		return 400;
	}
}

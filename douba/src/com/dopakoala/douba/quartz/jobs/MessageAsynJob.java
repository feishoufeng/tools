package com.dopakoala.douba.quartz.jobs;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dopakoala.douba.entity.AppConfig;
import com.dopakoala.douba.entity.HistoryMsg;
import com.dopakoala.douba.rongcloud.RongCloudController;
import com.dopakoala.douba.service.IAppConfigService;
import com.dopakoala.douba.service.IHistoryMsgService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ConstantsUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.DownLoadUtils;
import com.dopakoala.douba.utils.ReadFileUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 同步融云聊天记录
 * 
 * @author ShoufengFei
 *
 */
public class MessageAsynJob implements Job {
	WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
	IHistoryMsgService historyMsgService = (IHistoryMsgService)wac.getBean("historyMsgService");
	IAppConfigService appConfigService = (IAppConfigService)wac.getBean("appConfigService");
	// 记录日志
	protected static final Logger LOG = LoggerFactory.getLogger(MessageAsynJob.class);

	@Override
	public void execute(JobExecutionContext arg0){
		// TODO Auto-generated method stub
		// 最后更新时间
		Date historyDate = null;
		// 将要更新时间
		Date updateDate = null;
		// 当前最新可更新时间
		Date currDate = null;
		// 获取最后更新时间
		AppConfig appConfig = this.appConfigService.selectByName("history_time");
		String history_time = appConfig.getValue();
		if (appConfig == null || CheckUtils.isNull(history_time)) {
			history_time = ConstantsUtils.RONGCLOUD_SYNC_INIT_TIME;
		}

		// 格式化最后同步时间
		try {
			// 文件保存相对路径
			String path = AchieveUtils.getRealPath();
			// 项目访问地址
			String webPath = AchieveUtils.getConfVal("host");
			// 转换成Date对象
			historyDate = ConvertUtils.parseStringToDate(history_time, 1);
			// 根据最后更新时间推出即将要更新的时间
			Calendar updateCalendar = Calendar.getInstance();
			updateCalendar.setTime(historyDate);
			updateCalendar.add(Calendar.HOUR_OF_DAY, 1);// 最后更新时间的下一个小时
			updateDate = updateCalendar.getTime();
			// 将当前时间转化为yyyyMMddHH格式，方便后面日期进行比较
			String currDateStr = ConvertUtils.formatDateToString(new Date(), 1);
			// 根据当前时间推断可以获取的最新时间
			Calendar currCalendar = Calendar.getInstance();
			currCalendar.setTime(ConvertUtils.parseStringToDate(currDateStr, 1));
			currCalendar.add(Calendar.HOUR_OF_DAY, -2);// 当前时间的前2个小时，保证聊天记录存在性
			currDate = currCalendar.getTime();
			// 即将更新时间与最后可更新时间进行比对，如果小于等于可更新时间即可进行更新操作
			if (updateDate.before(currDate) || updateDate.equals(currDate)) {
				// 获取即将更新时间标记
				String updateStr = ConvertUtils.formatDateToString(updateDate, 1);
				// 历史下载文件夹路径
				String folderPath = path + ConstantsUtils.RONGCLOUD_CONTACT_HISTORY_FILES;
				// 从融云获取下载信息
				JSONObject urlJson = RongCloudController.getInstance(updateStr).getHistory();
				if (urlJson != null && urlJson.containsKey("url") && urlJson.containsKey("fileType")) {
					// 文件下载地址
					String url = urlJson.getString("url");
					// 文件类型
					String fileType = urlJson.getString("fileType");
					// 文件名称
					String fileName = updateStr + fileType;
					// 将聊天记录信息文件下载到本地
					if (DownLoadUtils.downLoadFromUrl(url, fileName, folderPath)) {
						// 从文件夹中拿出聊天记录详情
						JSONArray array = ReadFileUtils.readZipFile(folderPath + "/" + fileName);
						if (array != null && array.size() > 0) {
							for (int i = 0; i < array.size(); i++) {
								JSONObject json = (JSONObject) array.get(i);
								if (json != null) {
									// 判断数据库中是否存在相同信息id
									if (json.containsKey("msgUID")) {
										String msgUID = json.getString("msgUID");
										HistoryMsg selectHistoryMsg = this.historyMsgService.selectByMsgId(msgUID);
										if (selectHistoryMsg == null) {
											HistoryMsg insertHistoryMsg = new HistoryMsg();

											insertHistoryMsg.setMsgUid(msgUID);

											if (json.containsKey("appId")) {
												insertHistoryMsg.setAppid(json.getString("appId"));
											}

											// 发送人id
											int fromUserId = 0;
											if (json.containsKey("fromUserId")) {
												fromUserId = json.getInt("fromUserId");
												insertHistoryMsg.setFormUserId(fromUserId);
											}
											if (json.containsKey("targetId")) {
												insertHistoryMsg.setToUserId(json.getInt("targetId"));
											}
											if (json.containsKey("targetType")) {
												insertHistoryMsg.setTargetType(json.getInt("targetType"));
											}
											if (json.containsKey("GroupId")) {
												String tempGroupId = json.getString("GroupId");
												if (!CheckUtils.isNull(tempGroupId)) {
													insertHistoryMsg.setGroupId(json.getInt("GroupId"));
												}
											}
											if (json.containsKey("classname")) {
												insertHistoryMsg.setClassname(json.getString("classname"));
											}
											if (json.containsKey("dateTime")) {
												String dateStr = json.getString("dateTime");
												if (!CheckUtils.isNull(dateStr)) {
													Date dateTime = ConvertUtils.parseStringToDate(dateStr, 2);
													insertHistoryMsg.setDateTime(new Timestamp(dateTime.getTime()));
												} else {
													insertHistoryMsg.setDateTime(null);
												}
											}
											if (json.containsKey("appId")) {
												insertHistoryMsg.setAppid(json.getString("appId"));
											}
											if (json.containsKey("fromUserId")) {
												insertHistoryMsg.setFormUserId(json.getInt("fromUserId"));
											}
											if (json.containsKey("targetId")) {
												insertHistoryMsg.setToUserId(json.getInt("targetId"));
											}
											if (json.containsKey("targetType")) {
												insertHistoryMsg.setTargetType(json.getInt("targetType"));
											}
											// 获取消息类型
											if (json.containsKey("classname")) {
												String className = json.getString("classname");
												insertHistoryMsg.setClassname(className);
												if (json.containsKey("content")) {
													JSONObject contentJson = json.getJSONObject("content");
													if (contentJson != null) {
														switch (className) {
														case "RC:TxtMsg":
															insertHistoryMsg
																	.setContent(contentJson.getString("content"));
															break;
														case "RC:VcMsg":
															// 声音base64字符串
															String voiceStr = contentJson.getString("content");
															// 声音本地路径
															String voicePath = ConstantsUtils.RONGCLOUD_CONTACT_VOICE_FILES
																	+ "/" + fromUserId;
															// 声音文件名称
															String voiceName = msgUID + ".amr";
															if (!CheckUtils.isNull(voiceStr)) {
																// 将声音文件存入本地
																ConvertUtils.GenerateFile(voiceStr, path + voicePath,
																		voiceName);
															}
															// 网络访问路径
															String voiceUrl = webPath + voicePath + "/" + voiceName;
															insertHistoryMsg.setVoiceUrl(voiceUrl);
															break;
														case "RC:ImgMsg":
															String imageUri = contentJson.getString("imageUri");
															String imgPath = ConstantsUtils.RONGCLOUD_CONTACT_IMG_FILES
																	+ "/" + fromUserId;
															String imgName = msgUID + ".jpg";
															if (DownLoadUtils.downLoadFromUrl(imageUri, imgName,
																	path + imgPath)) {
																insertHistoryMsg
																		.setImgUrl(webPath + imgPath + "/" + imgName);
															}
															break;
														case "RC:ImgTextMsg":
															String title = contentJson.getString("title");
															String content = contentJson.getString("content");
															String imageUri1 = contentJson.getString("imageUri");
															String turnUrl = contentJson.getString("url");
															String imgPath1 = ConstantsUtils.RONGCLOUD_CONTACT_IMG_FILES
																	+ "/" + fromUserId;
															String imgName1 = msgUID + ".jpg";
															if (DownLoadUtils.downLoadFromUrl(imageUri1, imgName1,
																	path + imgPath1)) {
																insertHistoryMsg.setTitle(title);
																insertHistoryMsg.setContent(content);
																insertHistoryMsg
																		.setImgUrl(webPath + imgPath1 + "/" + imgName1);
																insertHistoryMsg.setTurnUrl(turnUrl);
															}
															break;
														case "RC:LBSMsg":
															String lbsStr = contentJson.getString("content");
															Double latitude = contentJson.getDouble("latitude");
															Double longitude = contentJson.getDouble("longitude");
															String poi = contentJson.getString("poi");
															String lbsPath = ConstantsUtils.RONGCLOUD_CONTACT_IMG_FILES
																	+ "/" + fromUserId;
															String lbsName = msgUID + ".jpg";
															if (!CheckUtils.isNull(lbsStr)) {
																// 将地图缩略图流存入本地
																ConvertUtils.GenerateFile(lbsStr, path + lbsPath,
																		lbsName);
															}
															String lbsUrl = webPath + lbsPath + "/" + lbsName;
															insertHistoryMsg.setImgUrl(lbsUrl);
															insertHistoryMsg.setLatitude(latitude);
															insertHistoryMsg.setLongitude(longitude);
															insertHistoryMsg.setPoi(poi);
															break;
														case "RC:FileMsg":
															String fileName1 = contentJson.getString("name");
															int fileSize = contentJson.getInt("size");
															String fileType1 = contentJson.getString("type");
															String fileUrl = contentJson.getString("fileUrl");
															String filePath = ConstantsUtils.RONGCLOUD_CONTACT_FILES
																	+ "/" + fromUserId;
															if (!CheckUtils.isNull(fileUrl)) {
																if (DownLoadUtils.downLoadFromUrl(fileUrl, fileName1,
																		path + filePath)) {
																	insertHistoryMsg.setFileName(fileName1);
																	insertHistoryMsg.setFileSize(fileSize);
																	insertHistoryMsg.setFileType(fileType1);
																	insertHistoryMsg.setFileUrl(
																			webPath + filePath + "/" + fileName1);

																}
															}
															break;
														}
													}
												}
											}
											if (json.containsKey("dateTime")) {
												String dateStr = json.getString("dateTime");
												if (!CheckUtils.isNull(dateStr)) {
													Date dateTime = ConvertUtils.parseStringToDate(dateStr, 2);
													insertHistoryMsg.setDateTime(new Timestamp(dateTime.getTime()));
												} else {
													insertHistoryMsg.setDateTime(null);
												}
											}
											this.historyMsgService.insertSelective(insertHistoryMsg);
										} else {
											continue;
										}
									}
								}
							}
						}
					}
				}
				// 更新配置表中的最后更新时间
				AppConfig updateAppConfig = new AppConfig();
				updateAppConfig.setId(appConfig.getId());
				updateAppConfig.setValue(updateStr);
				this.appConfigService.updateByPrimaryKeySelective(updateAppConfig);
				LOG.info("MessageAsynJob:" + updateStr);
			}
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
			LOG.error("同步融云错误代码：" + e);
		}
	}
}

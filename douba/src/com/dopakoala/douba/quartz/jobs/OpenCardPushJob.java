package com.dopakoala.douba.quartz.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dopakoala.douba.entity.GroupUser;
import com.dopakoala.douba.jpush.PushAlert;
import com.dopakoala.douba.service.IGroupUserService;
import com.dopakoala.douba.utils.ExceptionMsgUtil;
import com.dopakoala.douba.utils.ConstantsUtils;
import com.dopakoala.douba.utils.ConvertUtils;

/**
 * 开卡提示推送
 * 
 * @author ShoufengFei
 *
 */
public class OpenCardPushJob implements Job {
	WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
	IGroupUserService groupUserService = (IGroupUserService) wac.getBean("groupUserService");
	// 记录日志
	protected static final Logger LOG = LoggerFactory.getLogger(OpenCardPushJob.class);

	@Override
	public void execute(JobExecutionContext arg0) {
		try {
			List<GroupUser> groupUsers = new ArrayList<GroupUser>();
			groupUsers = this.groupUserService.selectAllOpen();
			if (groupUsers != null && groupUsers.size() > 0) {
				String title = "开卡提示";
				Map<String, String> extras = new HashMap<String, String>();
				extras.put("msgType", ConstantsUtils.OPEN_CARD_TIP);
				for (GroupUser groupUser : groupUsers) {
					String alert = "亲爱的用户: " + groupUser.getNickname() + " 您好！您所在的群: " + groupUser.getGroupName()
							+ " 开卡时间即将到来，请您及时进行开卡！";
					int pushCode = PushAlert.push(19, alert, title, extras);
					System.out.println("pushCode: " + pushCode);
				}
			}
			LOG.info("OpenCardPushJob: " + ConvertUtils.formatDateToString(new Date(), 3));
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}
}
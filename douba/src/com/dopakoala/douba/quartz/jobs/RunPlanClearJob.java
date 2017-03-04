package com.dopakoala.douba.quartz.jobs;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dopakoala.douba.service.IGroupUserSignService;
import com.dopakoala.douba.utils.ConvertUtils;

public class RunPlanClearJob implements Job {
	WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
	IGroupUserSignService groupUserSignService = (IGroupUserSignService) wac.getBean("groupUserSignService");
	// 记录日志
	protected static final Logger LOG = LoggerFactory.getLogger(RunPlanClearJob.class);

	@Override
	public void execute(JobExecutionContext arg0) {
		this.groupUserSignService.updateAllByNowmiles();
		LOG.info(ConvertUtils.formatDateToString(new Date(), 3));
	}
}

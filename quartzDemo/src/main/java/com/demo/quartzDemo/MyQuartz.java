package com.demo.quartzDemo;

import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

@Service
public class MyQuartz {
	@Resource(name = "loop-scheduler")
	private Scheduler scheduler;

	/**
	 * 新增一个simpleSchedule()调度
	 * 
	 * @param id
	 *            主键id
	 * @param time
	 *            执行时间
	 * @param flag
	 *            执行类别
	 */

	public void addSchedule(String jobName, String time, Class<? extends Job> job) {
		try {
			// 1、创建一个JobDetail实例，指定Quartz
			JobDetail jobDetail = JobBuilder.newJob(job)
					// 任务执行类
					.withIdentity("job_" + jobName, "jGroup_" + jobName).storeDurably(false)
					// 任务名，任务组
					.build();
			// 2、创建Trigger
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + jobName, "tGroup_" + jobName)
					.withSchedule(CronScheduleBuilder.cronSchedule(time).withMisfireHandlingInstructionDoNothing())
					.build();
			// 3、创建Scheduler
			// 4、调度执行
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

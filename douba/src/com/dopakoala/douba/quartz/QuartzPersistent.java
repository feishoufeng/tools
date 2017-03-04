package com.dopakoala.douba.quartz;

import java.util.Calendar;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.dopakoala.douba.quartz.jobs.ResumeJob;
import com.dopakoala.douba.utils.ConstantsUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

public class QuartzPersistent {

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
	public static void addSchedule(int id, String time, String flag) {
		try {
			// 1、创建一个JobDetail实例，指定Quartz
			JobDetail jobDetail = JobBuilder.newJob(ResumeJob.class)
					// 任务执行类
					.withIdentity("job1_" + id, "jGroup" + id)
					// 任务名，任务组
					.build();
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			jobDataMap.put("id", id + "");
			jobDataMap.put("flag", flag);
			// 2、创建Trigger
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + id, "tGroup" + id)
					.withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
			// 3、创建Scheduler
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			// 4、调度执行
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(QuartzPersistent.class, e);
		}
	}

	/**
	 * 从数据库中找到已经存在的job，并重新开户调度
	 */
	public static void resumeJob() {
		try {

			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();
			// ①获取调度器中所有的触发器组
			List<String> triggerGroups = scheduler.getTriggerGroupNames();
			// ②重新恢复在tgroup1组中，名为trigger1_1触发器的运行
			for (int i = 0; i < triggerGroups.size(); i++) {
				List<String> triggers = scheduler.getTriggerGroupNames();
				for (int j = 0; j < triggers.size(); j++) {
					scheduler.resumeJob(new JobKey(triggers.get(j), triggerGroups.get(i)));
				}

			}
			scheduler.start();
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(QuartzPersistent.class, e);

		}
	}

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 10);
		String time = ConvertUtils.formatDateToString(calendar.getTime(), 11);
		addSchedule(123465, time, ConstantsUtils.QUARTZ_RETURN_REDPACKET_FLAG);
		resumeJob();
	}
}

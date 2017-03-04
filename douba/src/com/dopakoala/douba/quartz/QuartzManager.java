package com.dopakoala.douba.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import com.dopakoala.douba.utils.ExceptionMsgUtil;

/**
 * @Description: 任务执行类
 *
 * @author 那是一条神奇的天路
 * @date 2015-12-22
 * @version V1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class QuartzManager {
	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
	private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
	private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName
	 *            任务名
	 * @param cls
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 */
	public static void addJob(String jobName, Class cls, String time) {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();// 创建一个触发器表
			JobDetail jobDetail = JobBuilder.newJob()// 创建一个jobbuilder用来定义一个任务明细。
					.ofType(cls)// 设置类，将被实例化
					.withIdentity(new JobKey(jobName, JOB_GROUP_NAME))// 使用给定的名称和默认组jobkey识别任务明细
					.build();// 产品定义的JobDetail实例这jobbuilder。

			Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(time))// 设置schedulebuilder将用于定义触发器的表。
					.withIdentity(new TriggerKey(jobName, TRIGGER_GROUP_NAME)).build();// 创建Trigger

			scheduler.scheduleJob(jobDetail, trigger);// 绑定

			// 启动
			if (!scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(QuartzManager.class, e);
		}
	}

	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @param time
	 */
	public static void modifyJobTime(String jobName, String time) {
		try {
			Scheduler sched = schedulerFactory.getScheduler();

			Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(time))// 设置schedulebuilder将用于定义触发器的表。
					.withIdentity(new TriggerKey(jobName, TRIGGER_GROUP_NAME)).build();// 创建Trigger

			if (trigger == null) {
				return;
			}
			String oldTime = ((CronTrigger) trigger).getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				JobDetail jobDetail = sched.getJobDetail(new JobKey(jobName, JOB_GROUP_NAME));
				Class objJobClass = jobDetail.getJobClass();
				removeJob(jobName);
				addJob(jobName, objJobClass, time);
			}
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(QuartzManager.class, e);
		}
	}

	/**
	 * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param triggerName
	 * @param jobName
	 */
	public static void removeJob(String jobName) {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			sched.pauseTrigger(new TriggerKey(jobName, TRIGGER_GROUP_NAME));// 停止触发器
			sched.unscheduleJob(new TriggerKey(jobName, TRIGGER_GROUP_NAME));// 移除触发器
			sched.deleteJob(new JobKey(jobName, JOB_GROUP_NAME));
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(QuartzManager.class, e);
		}
	}

	/**
	 * 启动所有定时任务
	 */
	public static void startJobs() {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			sched.start();
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(QuartzManager.class, e);
		}
	}

	/**
	 * 关闭所有定时任务
	 */
	public static void shutdownJobs() {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(QuartzManager.class, e);
		}
	}
}

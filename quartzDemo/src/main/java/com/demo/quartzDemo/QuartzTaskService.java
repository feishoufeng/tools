package com.demo.quartzDemo;

import java.awt.AWTException;
import java.awt.Robot;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by YangZhang on 2017/8/23.
 */

public class QuartzTaskService implements Job {
	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(QuartzTaskService.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("定时任务启动，时间：" + sdf.format(new Date()));
		Robot r;
		try {
			r = new Robot();
			System.out.println("延时前:" + sdf.format(new Date()));
			r.delay(10000);
			System.out.println("延时后:" + sdf.format(new Date()));
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

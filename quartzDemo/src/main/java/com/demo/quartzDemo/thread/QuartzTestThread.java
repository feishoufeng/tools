package com.demo.quartzDemo.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzTestThread extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(QuartzTestThread.class);

	@Override
	public void run() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("定时任务启动，时间：" + sdf.format(new Date()));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.run();
	}
}

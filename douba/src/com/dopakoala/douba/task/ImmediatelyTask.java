package com.dopakoala.douba.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.dopakoala.douba.quartz.QuartzPersistent;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

public class ImmediatelyTask implements InitializingBean {
	// 记录日志
	protected static final Logger LOG = LoggerFactory.getLogger(ImmediatelyTask.class);

	@Override
	public void afterPropertiesSet() {
		// TODO Auto-generated method stub
		try {
			QuartzPersistent.resumeJob();
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(ImmediatelyTask.class, e);
		}
		LOG.info(ConvertUtils.formatDateToString(new Date(), 3));
	}

}

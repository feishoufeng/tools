package com.dopakoala.douba.controller;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dopakoala.douba.quartz.QuartzPersistent;
import com.dopakoala.douba.utils.ConstantsUtils;
import com.dopakoala.douba.utils.ConvertUtils;

@Controller
@RequestMapping("/test")
public class TestController {
	protected static final Logger LOG = LoggerFactory.getLogger(TestController.class);

	@RequestMapping("/test")
	@ResponseBody
	public void test() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 10);
		String time = ConvertUtils.formatDateToString(calendar.getTime(), 11);
		QuartzPersistent.addSchedule(123465, time, ConstantsUtils.QUARTZ_RETURN_REDPACKET_FLAG);
//		QuartzPersistent.resumeJob();
	}
}

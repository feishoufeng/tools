package com.demo.quartzDemo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.quartzDemo.MyQuartz;
import com.demo.quartzDemo.QuartzTaskService;

@Controller
public class QuartzTestController {
	@Autowired
	private MyQuartz myQuartz;

	@RequestMapping("/start")
	@ResponseBody
	public Object start() {
		Map<String, Object> res = new HashMap<>();
		myQuartz.addSchedule("测试", "0/5 * * * * ?", QuartzTaskService.class);
		res.put("success", true);
		return res;
	}
}

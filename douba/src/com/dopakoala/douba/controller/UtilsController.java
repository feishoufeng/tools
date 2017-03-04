package com.dopakoala.douba.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dopakoala.douba.entity.AreaNumber;
import com.dopakoala.douba.service.IAreaNumberService;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ControllerUtils;

/**
 * 工具类
 * 
 * @author ShoufengFei
 *
 */
@Controller
@RequestMapping("/utils")
public class UtilsController {
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	@Resource
	private IAreaNumberService areaNumberService;
	// 错误提示信息
	String errmsg = "";
	// 响应map
	private Map<String, Object> resultMap = new HashMap<String, Object>();

	/**
	 * 获取行政规划列表
	 * 
	 * @param pid
	 */
	@ResponseBody
	@RequestMapping("/getArea")
	public void getArea(@RequestParam int pid) {
		List<AreaNumber> areaNumberList = new ArrayList<AreaNumber>();
		try {
			request.setCharacterEncoding("utf-8");

			if (pid == 0) {
				// 获取省
				areaNumberList = this.areaNumberService.selectProvince();
			} else {
				// 获取省级以外的区划
				areaNumberList = this.areaNumberService.selectByPid(pid);
			}

		} catch (Exception e) {
			// TODO: handle exception
			errmsg = "系统异常，请联系管理员！";
		}

		resultMap.put("errmsg", errmsg);
		if (!CheckUtils.isNull(errmsg)) {
			resultMap.put("list", areaNumberList);
		} else {
			resultMap.put("menu", new Object());
		}
		
		ControllerUtils.responseJson(response, resultMap);
	}
}

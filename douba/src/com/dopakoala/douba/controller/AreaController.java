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
import com.dopakoala.douba.utils.ExceptionMsgUtil;
import com.dopakoala.douba.utils.ControllerUtils;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/area")
public class AreaController {
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	@Resource
	private IAreaNumberService areaNumberService;
	// 响应map
	private Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@RequestMapping("/getArea")
	@ResponseBody
	public void getArea(@RequestParam int code) {
		String errmsg = "";
		List<AreaNumber> areaNumbers = new ArrayList<AreaNumber>();
		try {
			if(code == 0){
				areaNumbers = this.areaNumberService.selectProvince();
			}else{
				areaNumbers = this.areaNumberService.selectByPid(code);
			}
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
			errmsg = "系统异常，请联系管理员！";
		}
		resultMap.put("errmsg", errmsg);
		resultMap.put("area", JSONArray.fromObject(areaNumbers));
		ControllerUtils.responseJson(response, resultMap);
	}
}

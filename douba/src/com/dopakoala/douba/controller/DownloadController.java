package com.dopakoala.douba.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dopakoala.douba.entity.AppVersion;
import com.dopakoala.douba.service.IAppVersionService;

@Controller
@RequestMapping("/download")
public class DownloadController {
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	@Resource
	public IAppVersionService appVersionService;

	@RequestMapping("/getApp")
	public String getApp(Model model) {
		AppVersion appVersion = this.appVersionService.selectLatest();
		if (appVersion != null) {
			model.addAttribute("url", appVersion.getUrl());
		}
		return "viewPage/download_getApp";
	}
}

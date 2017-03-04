package com.dopakoala.douba.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dopakoala.douba.android.ApkDetail;
import com.dopakoala.douba.android.ApkInfo;
import com.dopakoala.douba.entity.AppVersion;
import com.dopakoala.douba.service.IAppVersionService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ConstantsUtils;
import com.dopakoala.douba.utils.ControllerUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

@Controller
@RequestMapping("/application")
public class ApplicationController {
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	@Resource
	private IAppVersionService appVersionService;
	@Resource
	private MultipartHttpServletRequest multipartRequest;

	// 响应map
	private Map<String, Object> resultMap = new HashMap<String, Object>();

	@RequestMapping("/android")
	public String android() {
		return "viewPage/android_page";
	}

	@RequestMapping("/getAndroid")
	@ResponseBody
	public void getAndroid(@RequestParam int page, @RequestParam int pagesize) {
		int count = 0;
		int total_page = 0;

		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date2");
		String sqlWhere = request.getParameter("sqlWhere");
		// 错误提示
		String errmsg = "";
		// 获取公告列表
		List<AppVersion> androidList = new ArrayList<AppVersion>();
		try {
			AppVersion selectAppVersion = new AppVersion();
			selectAppVersion.setPlatform("android");
			selectAppVersion.setSqlWhere(sqlWhere);
			selectAppVersion.setPage(page * pagesize);
			selectAppVersion.setPagesize(pagesize);
			if (!CheckUtils.isNull(date1) && !CheckUtils.isNull(date2)) {
				selectAppVersion.setDate1(new Timestamp(ConvertUtils.parseStringToDate(date1, 4).getTime()));
				selectAppVersion.setDate2(new Timestamp(ConvertUtils.parseStringToDate(date2, 4).getTime()));
			}
			if (!CheckUtils.isNull(sqlWhere)) {
				selectAppVersion.setSqlWhere(sqlWhere);
			}
			androidList = this.appVersionService.selectListByPlatform(selectAppVersion);

			if (androidList.size() > 0) {
				for (AppVersion appVersion : androidList) {
					appVersion.setDateStr(ConvertUtils.formatDateToString(appVersion.getCreateTime(), 3));
				}
			}
			// 获取已发布apk数量
			AppVersion selectAppVersionTemp = new AppVersion();
			selectAppVersionTemp.setPage(0);
			selectAppVersionTemp.setPagesize(0);
			selectAppVersion.setPlatform("android");
			if (!CheckUtils.isNull(date1) && !CheckUtils.isNull(date2)) {
				selectAppVersionTemp.setDate1(new Timestamp(ConvertUtils.parseStringToDate(date1, 4).getTime()));
				selectAppVersionTemp.setDate2(new Timestamp(ConvertUtils.parseStringToDate(date2, 4).getTime()));
			}
			if (!CheckUtils.isNull(sqlWhere)) {
				selectAppVersionTemp.setSqlWhere(sqlWhere);
			}
			count = this.appVersionService.selectListByPlatform(selectAppVersionTemp).size();
			// 页码
			total_page = count / pagesize;
			if (count % pagesize > 0) {
				total_page++;
			}

		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
			errmsg = "系统异常，请联系管理员！";
		}

		resultMap.put("errmsg", errmsg);
		resultMap.put("count", count);
		resultMap.put("list", androidList);
		resultMap.put("total_page", total_page);
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("android_editor")
	public String editAndroid() {
		return "viewPage/android_editor";
	}

	@RequestMapping("upload")
	@ResponseBody
	public void upload(@RequestParam("files[]") MultipartFile[] files, @RequestParam("content") String content) {
		// 错误提示
		String errmsg = "";

		String path = AchieveUtils.getRealPath();
		String url = AchieveUtils.getConfVal("host");
		String savePath = path + ConstantsUtils.APP_UPLOAD_PATH;
		String urlPath = url + ConstantsUtils.APP_UPLOAD_PATH;
		String filename = ConvertUtils.formatDateToString(new Date(), 10);

		// 获取当前版本号
		int n = this.appVersionService.selectByFileName(filename).size();

		try {
			for (MultipartFile multipartFile : files) {
				MultipartFile file = multipartFile;
				CommonsMultipartFile cf = (CommonsMultipartFile) file;
				DiskFileItem fi = (DiskFileItem) cf.getFileItem();
				// 拼接文件名称
				filename += ConvertUtils.parseInt(n + 1, "00") + ".apk";
				if (ConvertUtils.inputStreamToFile(fi.getInputStream(), savePath, filename)) {
					ApkInfo apkInfo = ApkDetail.getInstance(savePath + filename).getApkInfo();
					AppVersion appVersion = this.appVersionService.selectByVersion(apkInfo.getVersionCode());
					if (appVersion == null) {
						AppVersion insertAppVersion = new AppVersion();
						insertAppVersion.setContent(content);
						insertAppVersion.setFilemd5(apkInfo.getMD5());
						insertAppVersion.setFilename(filename);
						insertAppVersion.setVersion(apkInfo.getVersionCode());
						insertAppVersion.setUrl(urlPath + filename);
						insertAppVersion.setCreateUser(AchieveUtils.getUser(request).getUid());
						insertAppVersion.setPlatform("android");
						this.appVersionService.insertSelective(insertAppVersion);
					} else {
						errmsg = "当前数据库中已存在版本号为：" + apkInfo.getVersionCode() + "的文件，请确认后重新上传！";
					}
				} else {
					errmsg = "文件上传失败！";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}

		resultMap.put("errmsg", errmsg);
		ControllerUtils.responseJson(response, resultMap);
	}
}

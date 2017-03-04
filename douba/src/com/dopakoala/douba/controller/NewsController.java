package com.dopakoala.douba.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dopakoala.douba.entity.AdminUser;
import com.dopakoala.douba.entity.AppNew;
import com.dopakoala.douba.entity.AppNewsType;
import com.dopakoala.douba.service.IAppNewService;
import com.dopakoala.douba.service.IAppNewsTypeService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ConstantsUtils;
import com.dopakoala.douba.utils.ConvertUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dopakoala.douba.utils.ControllerUtils;

@Controller
@RequestMapping("/news")
public class NewsController {
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	@Resource
	private IAppNewService appNewService;
	@Resource
	private IAppNewsTypeService appNewsTypeService;
	// 响应map
	private Map<String, Object> resultMap = new HashMap<String, Object>();

	@RequestMapping("/getEditor")
	public String getEditor(Model model) {
		int id = ControllerUtils.getIntFromReq(request, "id");
		model.addAttribute("id", id);
		return "viewPage/news_editor";
	}

	@RequestMapping("/page")
	public String page() {
		return "viewPage/news_page";
	}

	@RequestMapping("/detail")
	public String detail(@RequestParam int id, Model model) {
		model.addAttribute("id", id);
		return "viewPage/news_detail";
	}

	@RequestMapping("/editor")
	@ResponseBody
	public void editor(@RequestParam int type, @RequestParam int cityid, @RequestParam String title,
			@RequestParam String content, @RequestParam String imgDatas, @RequestParam int id) {
		// 错误提示
		String errmsg = "";
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			// 文件保存相对路径
			String path = AchieveUtils.getRealPath();
			String url = AchieveUtils.getConfVal("host");
			AdminUser user = AchieveUtils.getUser(request);

			String folderPath = path;
			folderPath += ConstantsUtils.APP_NEWS_IMG_PATH;

			if (!CheckUtils.isNull(imgDatas)) {
				String[] datas = imgDatas.split("》");
				int n = 0;
				for (int i = 0; i < datas.length; i++) {
					if (datas[i].contains("data:image/")) {
						String webPath = url;
						String fileType = "." + datas[i].split(";base64")[0].split("/")[1];
						String fileName = (AchieveUtils.getMillis() + n) + fileType;
						if (ConvertUtils.GenerateFile(datas[i].split("base64,")[1], folderPath, fileName)) {
							webPath += ConstantsUtils.APP_NEWS_IMG_PATH;
							webPath += "/" + fileName;
							content = content.replace(datas[i], webPath);
						}
					}
				}
			}

			AppNew tempAppNew = new AppNew();
			tempAppNew.setCityid(cityid);
			tempAppNew.setContent(content);
			tempAppNew.setType(type);
			tempAppNew.setStatus(0);
			tempAppNew.setTitle(title);
			tempAppNew.setCreateUser(user.getUid());
			if (id > 0) {
				// 更新操作
				tempAppNew.setId(id);
				this.appNewService.updateByPrimaryKeySelective(tempAppNew);
			} else {
				// 插入操作
				this.appNewService.insertSelective(tempAppNew);
			}

		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
			errmsg = "系统异常，请联系管理员！";
		}

		resultMap.put("errmsg", errmsg);
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("/getNews")
	@ResponseBody
	public void getNews(@RequestParam int page, @RequestParam int pagesize) {
		int count = 0;
		int total_page = 0;
		// 错误提示
		String errmsg = "";
		// 获取公告列表
		List<AppNew> appNewList = new ArrayList<AppNew>();
		try {
			AppNew selectAppNew = new AppNew();
			selectAppNew.setPage(page * pagesize);
			selectAppNew.setPagesize(pagesize);
			appNewList = this.appNewService.selectAll(selectAppNew);

			if (appNewList.size() > 0) {
				for (AppNew appNew : appNewList) {
					appNew.setDateStr(ConvertUtils.formatDateToString(appNew.getModifyTime(), 2));
				}
			}
			// 获取公告数量
			AppNew selectAppNewTemp = new AppNew();
			selectAppNewTemp.setPage(0);
			selectAppNewTemp.setPagesize(0);
			count = this.appNewService.selectAll(selectAppNewTemp).size();
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
		resultMap.put("list", appNewList);
		resultMap.put("total_page", total_page);
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("/changeStatus")
	@ResponseBody
	public void changeStatus(@RequestParam int id) {
		String errmsg = "";
		try {
			AppNew updateAppNew = new AppNew();
			updateAppNew.setId(id);
			updateAppNew.setStatus(1);
			this.appNewService.updateByPrimaryKeySelective(updateAppNew);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
			errmsg = "系统异常，请联系管理员！";
		}
		resultMap.put("errmsg", errmsg);
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public void delete(@RequestParam int id) {
		String errmsg = "";
		try {
			this.appNewService.deleteByPrimaryKey(id);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
			errmsg = "系统异常，请联系管理员！";
		}
		resultMap.put("errmsg", errmsg);
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("/getNewsDetail")
	@ResponseBody
	public void getNews(@RequestParam int id) {
		String errmsg = "";
		AppNew appNew = null;
		try {
			appNew = this.appNewService.selectByPrimaryKey(id);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
			errmsg = "系统异常，请联系管理员！";
		}
		resultMap.put("errmsg", errmsg);
		if (appNew != null) {
			resultMap.put("newsDetail", JSONObject.fromObject(appNew));
		}
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("/getType")
	@ResponseBody
	public void getType() {
		String errmsg = "";
		List<AppNewsType> appNewsTypeList = new ArrayList<AppNewsType>();
		try {
			appNewsTypeList = this.appNewsTypeService.selectAll();
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
			errmsg = "系统异常，请联系管理员！";
		}
		resultMap.put("errmsg", errmsg);
		resultMap.put("list", JSONArray.fromObject(appNewsTypeList));
		ControllerUtils.responseJson(response, resultMap);
	}
}

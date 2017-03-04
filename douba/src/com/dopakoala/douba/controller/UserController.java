package com.dopakoala.douba.controller;

import java.sql.Timestamp;
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

import com.dopakoala.douba.entity.GroupUser;
import com.dopakoala.douba.entity.GroupUserSignLog;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IGroupUserService;
import com.dopakoala.douba.service.IGroupUserSignLogService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.ExceptionMsgUtil;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ControllerUtils;
import com.dopakoala.douba.utils.ConvertUtils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	@Resource
	private IUserService userService;
	@Resource
	private IGroupUserSignLogService groupUserSignLogService;
	@Resource
	private IGroupUserService groupUserService;
	// 响应map
	private Map<String, Object> resultMap = new HashMap<String, Object>();

	@RequestMapping("/page")
	public String page() {
		return "viewPage/user_page";
	}

	@RequestMapping("/getUser")
	@ResponseBody
	public void getUser(@RequestParam int page, @RequestParam int pagesize) {
		int count = 0;
		int total_page = 0;
		
		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date2");
		String sqlWhere = request.getParameter("sqlWhere");
		// 错误提示
		String errmsg = "";
		// 获取公告列表
		List<User> userList = new ArrayList<User>();
		try {
			User selectUser = new User();
			selectUser.setPage(page * pagesize);
			selectUser.setPagesize(pagesize);
			if (!CheckUtils.isNull(date1) && !CheckUtils.isNull(date2)) {
				selectUser.setDate1(new Timestamp(ConvertUtils.parseStringToDate(date1, 4).getTime()));
				selectUser.setDate2(new Timestamp(ConvertUtils.parseStringToDate(date2, 4).getTime()));
			}
			if(!CheckUtils.isNull(sqlWhere)){
				selectUser.setSqlWhere(sqlWhere);
			}
			userList = this.userService.selectAll(selectUser);

			if (userList.size() > 0) {
				for (User user : userList) {
					user.setDateStr(ConvertUtils.formatDateToString(user.getCreateTime(), 3));
				}
			}
			// 获取公告数量
			User selectUserTemp = new User();
			selectUserTemp.setPage(0);
			selectUserTemp.setPagesize(0);
			if (!CheckUtils.isNull(date1) && !CheckUtils.isNull(date2)) {
				selectUserTemp.setDate1(new Timestamp(ConvertUtils.parseStringToDate(date1, 4).getTime()));
				selectUserTemp.setDate2(new Timestamp(ConvertUtils.parseStringToDate(date2, 4).getTime()));
			}
			if(!CheckUtils.isNull(sqlWhere)){
				selectUserTemp.setSqlWhere(sqlWhere);
			}
			count = this.userService.selectAll(selectUserTemp).size();
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
		resultMap.put("list", userList);
		resultMap.put("total_page", total_page);
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("/detail")
	public String detail(@RequestParam int uid, Model model) {
		model.addAttribute("uid", uid);
		return "viewPage/user_detail";
	}

	@RequestMapping("/changeStatus")
	@ResponseBody
	public void changeStatus(@RequestParam int uid, @RequestParam int status) {
		// 错误提示
		String errmsg = "";

		if (uid <= 0) {
			errmsg = "用户id错误！";
		} else {
			User updateUser = new User();
			updateUser.setUid(uid);
			updateUser.setStatus(status);
			this.userService.updateByPrimaryKeySelective(updateUser);
		}

		resultMap.put("errmsg", errmsg);
		ControllerUtils.responseJson(response, resultMap);
	}
	
	@RequestMapping("/createGroupRight")
	@ResponseBody
	public void createGroupRight(@RequestParam int uid, @RequestParam int status) {
		// 错误提示
		String errmsg = "";

		if (uid <= 0) {
			errmsg = "用户id错误！";
		} else {
			User updateUser = new User();
			updateUser.setUid(uid);
			updateUser.setCreateGroup(status);
			this.userService.updateByPrimaryKeySelective(updateUser);
		}

		resultMap.put("errmsg", errmsg);
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("/getDetail")
	@ResponseBody
	public void getDetail(@RequestParam int uid) {
		// 错误提示
		String errmsg = "";
		User user = new User();
		Long lastTime = 0L;
		double runMiles = 0;

		if (uid <= 0) {
			errmsg = "用户id错误！";
		} else {
			user = this.userService.selectByPrimaryKey(uid);
			if (user == null) {
				errmsg = "获取用户信息！";
			}
		}
		// 获取最后打卡时间
		GroupUserSignLog groupUserSignLog1 = this.groupUserSignLogService.selectLastedLogByUid(uid);
		if (groupUserSignLog1 != null) {
			lastTime = groupUserSignLog1.getCreateTime().getTime();
		}
		// 获取跑步里程
		GroupUserSignLog groupUserSignLog2 = this.groupUserSignLogService.selectTotalByUid(uid);
		if (groupUserSignLog2 != null) {
			runMiles = groupUserSignLog2.getRunmiles();
		}

		List<GroupUser> groupUsers = new ArrayList<GroupUser>();
		groupUsers = this.groupUserService.selectNumByUid(uid);

		resultMap.put("errmsg", errmsg);
		resultMap.put("userInfo", JSONObject.fromObject(user));
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("lastTime", lastTime);
		dataMap.put("runMiles", runMiles);
		dataMap.put("nums", groupUsers);
		resultMap.put("dataInfo", JSONObject.fromObject(dataMap));
		ControllerUtils.responseJson(response, resultMap);
	}
}

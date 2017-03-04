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

import com.dopakoala.douba.entity.Group;
import com.dopakoala.douba.entity.GroupType;
import com.dopakoala.douba.service.IGroupService;
import com.dopakoala.douba.service.IGroupTypeService;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ControllerUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

@Controller
@RequestMapping("/group")
public class GroupController {
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	@Resource
	private IGroupService groupService;
	@Resource
	private IGroupTypeService groupTypeService;

	// 响应map
	private Map<String, Object> resultMap = new HashMap<String, Object>();

	@RequestMapping("/page")
	public String android(Model model) {
		// 群类型列表
		List<GroupType> groupTypeList = this.groupTypeService.selectAll();
		if (groupTypeList != null && groupTypeList.size() > 0) {
			model.addAttribute("list", groupTypeList);
		} else {
			model.addAttribute("list", "");
		}
		return "viewPage/group_page";
	}

	@RequestMapping("/getChildren")
	public String getChildren(Model model, @RequestParam int gid) {
		// 群类型列表
		model.addAttribute("gid", gid);
		return "viewPage/group_children";
	}

	@RequestMapping("/list")
	@ResponseBody
	public void getGroup(@RequestParam int page, @RequestParam int pagesize) {
		int count = 0;
		int total_page = 0;

		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date2");
		String sqlWhere = request.getParameter("sqlWhere");
		// 错误提示
		String errmsg = "";
		List<Group> groupList = new ArrayList<Group>();
		try {
			Group selectGroup = new Group();
			selectGroup.setSqlWhere(sqlWhere);
			selectGroup.setPage(page * pagesize);
			selectGroup.setPagesize(pagesize);
			if (!CheckUtils.isNull(date1) && !CheckUtils.isNull(date2)) {
				selectGroup.setDate1(new Timestamp(ConvertUtils.parseStringToDate(date1, 4).getTime()));
				selectGroup.setDate2(new Timestamp(ConvertUtils.parseStringToDate(date2, 4).getTime()));
			}
			if (!CheckUtils.isNull(sqlWhere)) {
				selectGroup.setSqlWhere(sqlWhere);
			}
			groupList = this.groupService.selectPaoTuan(selectGroup);

			if (groupList.size() > 0) {
				for (Group group : groupList) {
					group.setDateStr(ConvertUtils.formatDateToString(group.getCreateTime(), 3));
				}
			}
			Group selectGroupTemp = new Group();
			selectGroupTemp.setPage(0);
			selectGroupTemp.setPagesize(0);
			if (!CheckUtils.isNull(date1) && !CheckUtils.isNull(date2)) {
				selectGroupTemp.setDate1(new Timestamp(ConvertUtils.parseStringToDate(date1, 4).getTime()));
				selectGroupTemp.setDate2(new Timestamp(ConvertUtils.parseStringToDate(date2, 4).getTime()));
			}
			if (!CheckUtils.isNull(sqlWhere)) {
				selectGroupTemp.setSqlWhere(sqlWhere);
			}
			count = this.groupService.selectPaoTuan(selectGroupTemp).size();
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
		resultMap.put("list", groupList);
		resultMap.put("total_page", total_page);
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("/changeStatus")
	@ResponseBody
	public void changeStatus(@RequestParam int gid, @RequestParam int status) {
		String errmsg = "";
		try {
			Group updateGroup = new Group();
			updateGroup.setGid(gid);
			updateGroup.setStatus(status);
			this.groupService.updateByPrimaryKeySelective(updateGroup);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}

		resultMap.put("errmsg", errmsg);
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("/childList")
	@ResponseBody
	public void childList(@RequestParam int page, @RequestParam int pagesize, @RequestParam int gid) {
		int count = 0;
		int total_page = 0;

		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date2");
		String sqlWhere = request.getParameter("sqlWhere");
		// 错误提示
		String errmsg = "";
		List<Group> groupList = new ArrayList<Group>();
		try {
			Group selectGroup = new Group();
			selectGroup.setPid(gid);
			selectGroup.setSqlWhere(sqlWhere);
			selectGroup.setPage(page * pagesize);
			selectGroup.setPagesize(pagesize);
			if (!CheckUtils.isNull(date1) && !CheckUtils.isNull(date2)) {
				selectGroup.setDate1(new Timestamp(ConvertUtils.parseStringToDate(date1, 4).getTime()));
				selectGroup.setDate2(new Timestamp(ConvertUtils.parseStringToDate(date2, 4).getTime()));
			}
			if (!CheckUtils.isNull(sqlWhere)) {
				selectGroup.setSqlWhere(sqlWhere);
			}
			groupList = this.groupService.selectGroup(selectGroup);

			if (groupList.size() > 0) {
				for (Group group : groupList) {
					group.setDateStr(ConvertUtils.formatDateToString(group.getCreateTime(), 3));
				}
			}
			Group selectGroupTemp = new Group();
			selectGroupTemp.setPid(gid);
			selectGroupTemp.setPage(0);
			selectGroupTemp.setPagesize(0);
			if (!CheckUtils.isNull(date1) && !CheckUtils.isNull(date2)) {
				selectGroupTemp.setDate1(new Timestamp(ConvertUtils.parseStringToDate(date1, 4).getTime()));
				selectGroupTemp.setDate2(new Timestamp(ConvertUtils.parseStringToDate(date2, 4).getTime()));
			}
			if (!CheckUtils.isNull(sqlWhere)) {
				selectGroupTemp.setSqlWhere(sqlWhere);
			}
			count = this.groupService.selectGroup(selectGroupTemp).size();
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
		resultMap.put("list", groupList);
		resultMap.put("total_page", total_page);
		ControllerUtils.responseJson(response, resultMap);
	}
}

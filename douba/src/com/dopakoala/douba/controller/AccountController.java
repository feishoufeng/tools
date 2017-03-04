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

import com.dopakoala.douba.entity.Hongbao;
import com.dopakoala.douba.entity.PayTixian;
import com.dopakoala.douba.entity.UserMoneyLog;
import com.dopakoala.douba.service.IHongbaoLogService;
import com.dopakoala.douba.service.IHongbaoService;
import com.dopakoala.douba.service.IOrdersService;
import com.dopakoala.douba.service.IPayTixianService;
import com.dopakoala.douba.service.IUserMoneyLogService;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ControllerUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	@Resource
	private IPayTixianService payTixianService;
	@Resource
	private IUserMoneyLogService userMoneyLogService;
	@Resource
	private IHongbaoService hongbaoService;
	@Resource
	private IHongbaoLogService hongbaoLogService;
	@Resource
	private IOrdersService orderService;
	// 响应map
	private Map<String, Object> resultMap = new HashMap<String, Object>();

	@RequestMapping("/page")
	public String page() {
		return "viewPage/account_page";
	}

	@RequestMapping("/apply")
	public String apply() {
		return "viewPage/account_apply";
	}

	@RequestMapping("/water")
	public String water(@RequestParam int uid, Model model) {
		model.addAttribute("uid", uid);
		return "viewPage/account_water";
	}

	@RequestMapping("/redPacket")
	public String redPacket(@RequestParam int uid, Model model) {
		model.addAttribute("uid", uid);
		return "viewPage/account_redPacket";
	}

	@RequestMapping("/detail")
	public String detail(@RequestParam int uid, Model model) {
		model.addAttribute("uid", uid);
		return "viewPage/account_detail";
	}

	@RequestMapping("/getApply")
	@ResponseBody
	public void getApply(@RequestParam int page, @RequestParam int pagesize) {
		int count = 0;
		int total_page = 0;

		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date2");
		String sqlWhere = request.getParameter("sqlWhere");
		// 错误提示
		String errmsg = "";
		// 获取提现列表
		List<PayTixian> applyList = new ArrayList<PayTixian>();
		try {
			PayTixian selectPayTixian = new PayTixian();
			selectPayTixian.setSqlWhere(sqlWhere);
			selectPayTixian.setPage(page * pagesize);
			selectPayTixian.setPagesize(pagesize);
			if (!CheckUtils.isNull(date1) && !CheckUtils.isNull(date2)) {
				selectPayTixian.setDate1(new Timestamp(ConvertUtils.parseStringToDate(date1, 4).getTime()));
				selectPayTixian.setDate2(new Timestamp(ConvertUtils.parseStringToDate(date2, 4).getTime()));
			}
			if (!CheckUtils.isNull(sqlWhere)) {
				selectPayTixian.setSqlWhere(sqlWhere);
			}
			applyList = this.payTixianService.selectAll(selectPayTixian);

			if (applyList.size() > 0) {
				for (PayTixian payTixian : applyList) {
					payTixian.setDateStr(ConvertUtils.formatDateToString(payTixian.getCreateTime(), 3));
				}
			}
			// 获取提现列表数量
			PayTixian selectPayTixianTemp = new PayTixian();
			selectPayTixianTemp.setPage(0);
			selectPayTixianTemp.setPagesize(0);
			if (!CheckUtils.isNull(date1) && !CheckUtils.isNull(date2)) {
				selectPayTixianTemp.setDate1(new Timestamp(ConvertUtils.parseStringToDate(date1, 4).getTime()));
				selectPayTixianTemp.setDate2(new Timestamp(ConvertUtils.parseStringToDate(date2, 4).getTime()));
			}
			if (!CheckUtils.isNull(sqlWhere)) {
				selectPayTixianTemp.setSqlWhere(sqlWhere);
			}
			count = this.payTixianService.selectAll(selectPayTixianTemp).size();
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
		resultMap.put("list", applyList);
		resultMap.put("total_page", total_page);
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("/getWater")
	@ResponseBody
	public void getWater(@RequestParam int page, @RequestParam int pagesize, @RequestParam int uid) {
		int count = 0;
		int total_page = 0;

		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date2");
		String sqlWhere = request.getParameter("sqlWhere");
		// 错误提示
		String errmsg = "";
		// 获取流水列表
		List<UserMoneyLog> waterList = new ArrayList<UserMoneyLog>();
		try {
			UserMoneyLog selectUserMoneyLog = new UserMoneyLog();
			selectUserMoneyLog.setSqlWhere(sqlWhere);
			selectUserMoneyLog.setPage(page * pagesize);
			selectUserMoneyLog.setPagesize(pagesize);
			selectUserMoneyLog.setUid(uid);
			if (!CheckUtils.isNull(date1) && !CheckUtils.isNull(date2)) {
				selectUserMoneyLog.setDate1(new Timestamp(ConvertUtils.parseStringToDate(date1, 4).getTime()));
				selectUserMoneyLog.setDate2(new Timestamp(ConvertUtils.parseStringToDate(date2, 4).getTime()));
			}
			if (!CheckUtils.isNull(sqlWhere)) {
				selectUserMoneyLog.setSqlWhere(sqlWhere);
			}
			waterList = this.userMoneyLogService.selectAllByUid(selectUserMoneyLog);

			if (waterList.size() > 0) {
				for (UserMoneyLog userMoneyLog : waterList) {
					userMoneyLog.setDateStr(ConvertUtils.formatDateToString(userMoneyLog.getCreateTime(), 3));
				}
			}
			// 获取流水列表数量
			UserMoneyLog selectUserMoneyLogTemp = new UserMoneyLog();
			selectUserMoneyLogTemp.setPage(0);
			selectUserMoneyLogTemp.setPagesize(0);
			selectUserMoneyLogTemp.setUid(uid);
			if (!CheckUtils.isNull(date1) && !CheckUtils.isNull(date2)) {
				selectUserMoneyLogTemp.setDate1(new Timestamp(ConvertUtils.parseStringToDate(date1, 4).getTime()));
				selectUserMoneyLogTemp.setDate2(new Timestamp(ConvertUtils.parseStringToDate(date2, 4).getTime()));
			}
			if (!CheckUtils.isNull(sqlWhere)) {
				selectUserMoneyLogTemp.setSqlWhere(sqlWhere);
			}
			count = this.userMoneyLogService.selectAllByUid(selectUserMoneyLogTemp).size();
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
		resultMap.put("list", waterList);
		resultMap.put("total_page", total_page);
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("/getRedPacket")
	@ResponseBody
	public void getRedPacket(@RequestParam int page, @RequestParam int pagesize, @RequestParam int uid) {
		int count = 0;
		int total_page = 0;

		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date2");
		String sqlWhere = request.getParameter("sqlWhere");
		// 错误提示
		String errmsg = "";
		// 获取红包列表
		List<Hongbao> redPacketList = new ArrayList<Hongbao>();
		try {
			Hongbao selectHongbao = new Hongbao();
			selectHongbao.setSqlWhere(sqlWhere);
			selectHongbao.setPage(page * pagesize);
			selectHongbao.setPagesize(pagesize);
			selectHongbao.setUid(uid);
			if (!CheckUtils.isNull(date1) && !CheckUtils.isNull(date2)) {
				selectHongbao.setDate1(new Timestamp(ConvertUtils.parseStringToDate(date1, 4).getTime()));
				selectHongbao.setDate2(new Timestamp(ConvertUtils.parseStringToDate(date2, 4).getTime()));
			}
			if (!CheckUtils.isNull(sqlWhere)) {
				selectHongbao.setSqlWhere(sqlWhere);
			}
			redPacketList = this.hongbaoService.selectAllByUid(selectHongbao);

			if (redPacketList.size() > 0) {
				for (Hongbao hongbao : redPacketList) {
					hongbao.setDateStr(ConvertUtils.formatDateToString(hongbao.getCreateTime(), 3));
				}
			}
			// 获取红包数量
			Hongbao selectHongbaoTemp = new Hongbao();
			selectHongbaoTemp.setPage(0);
			selectHongbaoTemp.setPagesize(0);
			selectHongbaoTemp.setUid(uid);
			if (!CheckUtils.isNull(date1) && !CheckUtils.isNull(date2)) {
				selectHongbaoTemp.setDate1(new Timestamp(ConvertUtils.parseStringToDate(date1, 4).getTime()));
				selectHongbaoTemp.setDate2(new Timestamp(ConvertUtils.parseStringToDate(date2, 4).getTime()));
			}
			if (!CheckUtils.isNull(sqlWhere)) {
				selectHongbaoTemp.setSqlWhere(sqlWhere);
			}
			count = this.hongbaoService.selectAllByUid(selectHongbaoTemp).size();
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
		resultMap.put("list", redPacketList);
		resultMap.put("total_page", total_page);
		ControllerUtils.responseJson(response, resultMap);
	}

	@RequestMapping("/getDetail")
	@ResponseBody
	public void getDetail(@RequestParam int uid) {
		// 错误提示
		String errmsg = "";
		List<UserMoneyLog> list = new ArrayList<UserMoneyLog>();
		try {
			list = this.userMoneyLogService.selectAccountByUid(uid);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
			errmsg = "系统异常，请联系管理员！";
		}

		resultMap.put("errmsg", errmsg);
		resultMap.put("list", list);
		ControllerUtils.responseJson(response, resultMap);
	}
}

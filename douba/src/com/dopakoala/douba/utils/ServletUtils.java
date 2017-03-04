package com.dopakoala.douba.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dopakoala.douba.entity.GroupStatistic;
import com.dopakoala.douba.service.IGroupStatisticService;

import net.sf.json.JSONObject;

/**
 * servlet所需工具
 * 
 * @author ShoufengFei
 *
 */
public class ServletUtils {
	/**
	 * servlet响应
	 * 
	 * @param response
	 * @param resultMap
	 *            响应的键值对
	 */
	public static void writeJson(HttpServletResponse response, Map<String, Object> resultMap) {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out;
		try {
			// 判断返回字段中是否有‘data’键
			if (resultMap.containsKey("data")) {
				// 如果有‘data’键，判断值是否为空
				if (resultMap.get("data") == null) {
					// ‘data’键字段为空，则删掉‘data’键值对
					resultMap.remove("data");
				}
			}
			// 判断返回字段中是否有‘data’键,没有则加上默认值
			if (!resultMap.containsKey("data")) {
				resultMap.put("data", new JSONObject());
			}

			String result = ConvertUtils.JSONObjectToString(resultMap);
			out = response.getWriter();
			out.write(AESUtil.getInstance().encrypt(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			ExceptionMsgUtil.getInstance(ServletUtils.class, e);
		}

	}

	/**
	 * 获取手机端请求的参数
	 * 
	 * @param request
	 * @return 返回格式化后的参数
	 */
	public static JSONObject getReader(HttpServletRequest request) {
		JSONObject requestJson = null;
		BufferedReader reader = null;
		try {
			String strTemp = "", requestJsonStr = "";

			reader = request.getReader();
			while ((strTemp = reader.readLine()) != null) {
				requestJsonStr += strTemp;
			}

			if (!CheckUtils.isNull(requestJsonStr)) {
				requestJsonStr = AESUtil.getInstance().decrypt(requestJsonStr);
				requestJson = JSONObject.fromObject(requestJsonStr);
			}
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(ServletUtils.class, e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ExceptionMsgUtil.getInstance(ServletUtils.class, e);
			}
		}
		return requestJson;
	}

	/**
	 * 统计跑量
	 * 
	 * @param gid
	 *            群id
	 * @param uid
	 *            用户id
	 * @param sid
	 *            统计id
	 * @param type
	 *            统计类型 1：周 2：月 3：年
	 * @param runmiles
	 *            跑步量
	 * @param groupStatisticService
	 */
	public static void account(int gid, int uid, String sid, int type, double runmiles, Long period,
			IGroupStatisticService groupStatisticService) {
		GroupStatistic selectGroupStatistic = new GroupStatistic();
		selectGroupStatistic.setGid(gid);
		selectGroupStatistic.setUid(uid);
		selectGroupStatistic.setSid(sid);
		selectGroupStatistic.setType(type);
		List<GroupStatistic> list = groupStatisticService.selectByUidGidSidType(selectGroupStatistic);
		if (list == null || list.size() == 0) {
			// 创建统计记录
			GroupStatistic insertGroupStatistic = new GroupStatistic();
			insertGroupStatistic.setGid(gid);
			insertGroupStatistic.setUid(uid);
			insertGroupStatistic.setSid(sid);
			insertGroupStatistic.setType(type);
			insertGroupStatistic.setPeriod(period);
			insertGroupStatistic.setRunmiles(runmiles);
			groupStatisticService.insertSelective(insertGroupStatistic);
		} else {
			GroupStatistic groupStatistic = list.get(0);
			// 更新统计记录
			GroupStatistic updateGroupStatistic = new GroupStatistic();
			updateGroupStatistic.setId(groupStatistic.getId());
			updateGroupStatistic.setRunmiles(runmiles + groupStatistic.getRunmiles());
			updateGroupStatistic.setPeriod(period + groupStatistic.getPeriod());
			groupStatisticService.updateByPrimaryKeySelective(updateGroupStatistic);
		}
	}
}

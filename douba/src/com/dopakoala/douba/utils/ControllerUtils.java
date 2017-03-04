package com.dopakoala.douba.utils;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制类所需工具
 * 
 * @author ShoufengFei
 *
 */
public class ControllerUtils {
	/**
	 * resonpse返回值
	 * 
	 * @param response
	 * @param resultMap
	 *            响应的键值对
	 */
	public static void responseJson(HttpServletResponse response, Map<String, Object> resultMap) {
		try {
			response.setCharacterEncoding("utf-8");
			String result = ConvertUtils.JSONObjectToString(resultMap);
			response.getWriter().write(result);
		} catch (IOException e) {
			ExceptionMsgUtil.getInstance(ControllerUtils.class, e);
		}
	}

	/**
	 * 获取传值参数
	 * 
	 * @param request
	 * @param key
	 *            需要获取的参数名称
	 * @return
	 */
	public static String getStrFromReq(HttpServletRequest request, String key) {
		try {
			request.setCharacterEncoding("utf-8");
			return request.getParameter(key);
		} catch (IOException e) {
			ExceptionMsgUtil.getInstance(ControllerUtils.class, e);
			return "";
		}
	}

	/**
	 * 获取传值参数
	 * 
	 * @param request
	 * @param key
	 *            需要获取的参数名称
	 * @return
	 */
	public static int getIntFromReq(HttpServletRequest request, String key) {
		try {
			request.setCharacterEncoding("utf-8");
			String val = request.getParameter(key);
			if (!CheckUtils.isNull(val)) {
				return Integer.parseInt(val);
			} else {
				return 0;
			}
		} catch (IOException e) {
			ExceptionMsgUtil.getInstance(ControllerUtils.class, e);
			return 0;
		}
	}
}

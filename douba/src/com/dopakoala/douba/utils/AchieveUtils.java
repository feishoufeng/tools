package com.dopakoala.douba.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dopakoala.douba.entity.AdminUser;
import com.dopakoala.douba.entity.AppConfig;
import com.dopakoala.douba.service.IAppConfigService;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

/**
 * 获取需要的结果工具方法名为
 * 
 * @author ShoufengFei
 *
 */
public class AchieveUtils {
	/**
	 * 获取随机id值
	 * 
	 * @return 返回随机id值
	 */
	public static String getRandomId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取随机验证码值
	 * 
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @return 返回随机数
	 */
	public static int getRandomCode(int min, int max) {
		int code = (int) Math.floor(Math.random() * (max - min + 1) + min);
		while (code >= min && code <= max) {
			code = (int) Math.floor(Math.random() * (max - min + 1) + min);
		}
		return code;
	}

	/**
	 * 获取当前时间毫秒
	 * 
	 * @return 返回随机code值
	 */
	public static long getMillis() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取用户详情
	 * 
	 * @param request
	 * @return
	 */
	public static AdminUser getUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		AdminUser user = (AdminUser) session.getAttribute("user");
		return user;
	}

	/**
	 * 获取相应的配置文件信息
	 * 
	 * @param key
	 *            配置文件键值
	 * @return 返回相应键值的值
	 */
	public static String getConfVal(String key) {
		Properties prop = new Properties();
		InputStream input = null;
		String value = "";
		try {

			String path = AchieveUtils.getRealPath() + "/classes/conf.properties";
			// 读取属性文件a.properties
			input = new BufferedInputStream(new FileInputStream(path));
			prop.load(input); /// 加载属性列表
			Iterator<String> it = prop.stringPropertyNames().iterator();

			if (!key.equals("server")) {
				key = getConfVal("server") + key;
			}

			while (it.hasNext()) {
				if (it.next().equals(key)) {
					value = prop.getProperty(key);
					break;
				}
			}
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(AchieveUtils.class, e);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ExceptionMsgUtil.getInstance(AchieveUtils.class, e);
			}
		}
		return value;
	}

	/**
	 * 获取项目的绝对路径
	 * 
	 * @return
	 */
	public static String getRealPath() {
		File file = new File(AchieveUtils.class.getResource("/").getPath());
		return file.getParentFile().getPath().replaceAll("%20", " ");
	}

	/**
	 * 获取当天星期几
	 * 
	 * @return 星期一为第一天开始
	 */
	public static int getWeekDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		if (weekday == 1) {
			weekday += 6;
		} else {
			weekday--;
		}
		return weekday;
	}

	/**
	 * 获取当前星期是第几个星期
	 * 
	 * @return 一年中的第几个星期
	 */
	public static int getYearWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		Calendar calendarTemp = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
			calendarTemp.setTime(date);
			calendarTemp.add(Calendar.DAY_OF_YEAR, -7);
		}
		int yearweek = calendar.get(Calendar.WEEK_OF_YEAR);
		int yearweekTemp = calendarTemp.get(Calendar.WEEK_OF_YEAR);
		if (yearweek == 1 && getWeekDay(calendar.getTime()) == 7) {
			int year = calendar.get(Calendar.YEAR);
			int yearTemp = calendarTemp.get(Calendar.YEAR);
			if (year == yearTemp) {
				yearweek = yearweekTemp;
			}
		} else if (getWeekDay(calendar.getTime()) == 7) {
			yearweek--;
		}
		return yearweek;
	}

	/**
	 * 获取周区间
	 * 
	 * @param date
	 *            定点时间
	 * @return 定点时间所在周的区间
	 */
	public static String getWeekPeriod(Date date) {
		String returnVal = "";
		// 根据当前日期获取周区间
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		int yearWeek = getYearWeek(calendar.getTime());
		for (int i = 7; i > 0; i--) {
			calendar.add(Calendar.DATE, -1);
			if (yearWeek != getYearWeek(calendar.getTime())) {
				calendar.add(Calendar.DATE, 1);
				break;
			}
		}
		returnVal += ConvertUtils.formatDateToString(calendar.getTime(), 2) + " 00:00:00,";
		calendar.add(Calendar.DATE, 6);
		returnVal += ConvertUtils.formatDateToString(calendar.getTime(), 2) + " 23:59:59";
		return returnVal;
	}

	/**
	 * 获取周区间
	 * 
	 * @param sid
	 *            统计id
	 * @return 定点时间所在周的区间
	 */
	public static String getWeekPeriod(String sid) {
		String returnVal = "";
		if (!CheckUtils.isNull(sid)) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, Integer.parseInt(sid.substring(0, 4)));
			calendar.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(sid.substring(4, 6)));
			calendar.set(Calendar.DAY_OF_WEEK, 2);
			returnVal += ConvertUtils.formatDateToString(calendar.getTime(), 2) + " 00:00:00,";
			calendar.add(Calendar.DATE, 6);
			returnVal += ConvertUtils.formatDateToString(calendar.getTime(), 2) + " 23:59:59";
		}
		return returnVal;
	}

	/**
	 * 获取月区间
	 * 
	 * @param date
	 *            定点时间
	 * @return 定点时间所在月的区间
	 */
	public static String getMonthPeriod(Date date) {
		String returnVal = "";
		// 根据当前日期获取月区间
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		int month = calendar.get(Calendar.MONTH);
		for (int i = 0; i < 31; i++) {
			calendar.add(Calendar.DATE, 1);
			if (month != calendar.get(Calendar.MONTH)) {
				calendar.add(Calendar.DATE, -1);
				break;
			}
		}
		returnVal += ConvertUtils.formatDateToString(calendar.getTime(), 6) + "-01 00:00:00,";
		returnVal += ConvertUtils.formatDateToString(calendar.getTime(), 2) + " 23:59:59";
		return returnVal;
	}

	/**
	 * 获取月区间
	 * 
	 * @param date
	 *            统计id
	 * @return 定点时间所在月的区间
	 */
	public static String getMonthPeriod(String sid) {
		String returnVal = "";
		if (!CheckUtils.isNull(sid)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ConvertUtils.parseStringToDate(sid, 5));
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			int month = calendar.get(Calendar.MONTH);
			returnVal += ConvertUtils.formatDateToString(calendar.getTime(), 2) + " 00:00:00,";
			for (int i = 0; i < 31; i++) {
				calendar.add(Calendar.DATE, 1);
				if (month != calendar.get(Calendar.MONTH)) {
					calendar.add(Calendar.DATE, -1);
					break;
				}
			}
			returnVal += ConvertUtils.formatDateToString(calendar.getTime(), 2) + " 23:59:59";
		}
		return returnVal;
	}

	/**
	 * 获取年区间
	 * 
	 * @param date
	 *            定点时间
	 * @return 定点时间所在年的区间
	 */
	public static String getYearPeriod(Date date) {
		String returnVal = "";
		// 根据当前日期获取年区间
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		int year = calendar.get(Calendar.YEAR);
		returnVal += year + "-01-01 00:00:00,";
		returnVal += year + "-12-31 23:59:59";
		return returnVal;
	}

	/**
	 * 获取年区间
	 * 
	 * @param date
	 *            统计id
	 * @return 定点时间所在年的区间
	 */
	public static String getYearPeriod(String sid) {
		String returnVal = "";
		returnVal += sid + "-01-01 00:00:00,";
		returnVal += sid + "-12-31 23:59:59";
		return returnVal;
	}

	/**
	 * 格式化字符串
	 * 
	 * @param formatStr
	 *            需要格式化的字符串
	 * @param replaceStr
	 *            需要代替的字符串
	 * @return 格式化成功后的字符串
	 */
	public static String formatStr(String formatStr, String replaceStr) {
		if (!CheckUtils.isNull(formatStr) && !CheckUtils.isNull(replaceStr)) {
			return String.format(formatStr, replaceStr);
		} else {
			return "";
		}
	}

	/**
	 * 获取整形配置数据
	 * 
	 * @param name
	 * @param appConfigService
	 * @return
	 */
	public static int getIntAppConfig(String name, IAppConfigService appConfigService) {
		AppConfig appConfig = appConfigService.selectByName(name);
		if (appConfig != null) {
			return ConvertUtils.intFormat(appConfig.getValue(), 0);
		} else {
			return 0;
		}
	}

	/**
	 * 获取字符串配置数据
	 * 
	 * @param name
	 * @param appConfigService
	 * @return
	 */
	public static String getStringAppConfig(String name, IAppConfigService appConfigService) {
		AppConfig appConfig = appConfigService.selectByName(name);
		if (appConfig != null) {
			return appConfig.getValue();
		} else {
			return "";
		}
	}

	public static void main(String[] args) {
	}
}

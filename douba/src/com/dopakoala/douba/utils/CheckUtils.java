package com.dopakoala.douba.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dopakoala.douba.entity.UserVerify;
import com.dopakoala.douba.service.IUserVerifyService;

/**
 * 检测工具
 * 
 * @author ShoufengFei
 *
 */
public class CheckUtils {
	/**
	 * 判断字符是否为空
	 * 
	 * @param str
	 *            需要验证的字符串
	 * @return 返回状态 true 为空或者是空字符串 false 不为空
	 */
	public static boolean isNull(String str) {
		if (str != null && !str.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 检测是否为手机号码
	 * 
	 * @param mobile
	 *            手机号码
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		Pattern p = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[6,7])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	/**
	 * 验证验证码
	 * 
	 * @param type
	 *            验证码类型 1 注册 2 密码重置 3 绑定手机
	 * @param mobile
	 *            手机号码
	 * @param code
	 *            输入的验证码
	 * @param userVerifyService
	 *            验证码所需要的服务
	 * @return
	 */
	public static String checkVerify(int type, String mobile, String code, IUserVerifyService userVerifyService) {
		String returnVal = "";
		// 获取验证码
		UserVerify selectUserVerify = new UserVerify();
		selectUserVerify.setMobile(mobile);
		selectUserVerify.setType(type);
		List<UserVerify> userVerifyList = userVerifyService.selectByMobile(selectUserVerify);
		if (userVerifyList.size() > 0) {
			UserVerify userVerify = userVerifyList.get(0);
			long tempMillis = userVerify.getCreateTime().getTime();
			// 验证码有效期为10分钟
			if ((AchieveUtils.getMillis() - tempMillis) <= (10 * 60 * 1000)) {
				if (userVerify.getCode().equals(code)) {
					if (CheckUtils.isNull(mobile) || !CheckUtils.isMobile(mobile)) {
						returnVal = "手机号不符合要求，请重新输入！";
					}
				} else {
					returnVal = "验证码错误，请重新获取验证码！";
				}
			} else {
				returnVal = "验证码失效，请重新获取验证码！";
			}
		} else {
			returnVal = "未获取有效验证码！";
		}
		return returnVal;
	}

}

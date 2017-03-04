package com.dopakoala.douba.utils;

import java.io.*;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 转换工具类
 * 
 * @author 费寿锋
 * @version 1.0 2016/04/25 14:42
 */
public class ConvertUtils {
	/**
	 * 字符串转换为长整形
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @param defaultVal
	 *            默认返回值
	 * @return 返回转换后的值
	 */
	public static long longFormat(String str, long defaultVal) {
		if (CheckUtils.isNull(str)) {
			return defaultVal;
		} else {
			return Long.parseLong(str);
		}
	}

	/**
	 * 字符串转换为整形
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @param defaultVal
	 *            默认返回值
	 * @return 返回转换后的值
	 */
	public static int intFormat(String str, int defaultVal) {
		if (CheckUtils.isNull(str)) {
			return defaultVal;
		} else {
			return Integer.parseInt(str);
		}
	}

	/**
	 * 字符串转换为双精度浮点型
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @param defaultVal
	 *            默认返回值
	 * @return 返回转换后的值
	 */
	public static double doubleFormat(String str, double defaultVal) {
		if (CheckUtils.isNull(str)) {
			return defaultVal;
		} else {
			return Double.parseDouble(str);
		}
	}

	/**
	 * 字符串转换为浮点型
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @param defaultVal
	 *            默认返回值
	 * @return 返回转换后的值
	 */
	public static float floatFormat(String str, float defaultVal) {
		if (CheckUtils.isNull(str)) {
			return defaultVal;
		} else {
			return Float.parseFloat(str);
		}
	}

	/**
	 * 检查字符串，保证字符串不为空
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @return 返回转换后的字符串
	 */
	public static String getStr(Object str) {
		if (str == null) {
			return "";
		} else {
			return str.toString();
		}
	}

	/**
	 * 保留小数位数（四舍五入）[float]
	 * 
	 * @param val
	 *            需要转换的值
	 * @param digit
	 *            需要保留的小数位数
	 * @return 返回转换后的值
	 */
	public static float getFloat(float val, int digit) {
		return (float) (Math.round(val * (Math.pow(10, digit))) / Math.pow(10, digit));
	}

	/**
	 * 保留小数位数（四舍五入）[double]
	 * 
	 * @param val
	 *            需要转换的值
	 * @param digit
	 *            需要保留的小数位数
	 * @return 返回转换后的值
	 */
	public static double getDouble(double val, int digit) {
		return Math.round(val * (Math.pow(10, digit))) / Math.pow(10, digit);
	}

	// 流转化成字符串
	public static String inputStreamToString(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	// 流转化成文件
	public static boolean inputStreamToFile(InputStream is, String folderPath, String fileName) {
		InputStream inputSteam = null;
		BufferedInputStream fis = null;
		FileOutputStream fos = null;
		boolean flag = false;
		try {
			File file = new File(folderPath);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}

			String picPath = folderPath + "/" + fileName;
			File picFile = new File(picPath);
			inputSteam = is;
			fis = new BufferedInputStream(inputSteam);
			fos = new FileOutputStream(picFile);
			int f;
			while ((f = fis.read()) != -1) {
				fos.write(f);
			}
			fos.flush();
			flag = true;
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(ConvertUtils.class, e);
		} finally {
			try {
				fos.close();
				fis.close();
				inputSteam.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				ExceptionMsgUtil.getInstance(ConvertUtils.class, e);
			}
		}
		return flag;
	}

	// 图片转化成base64字符串
	public static String GetImageStr(String imgPath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = imgPath;// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			ExceptionMsgUtil.getInstance(ConvertUtils.class, e);
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	// base64字符串转化成图片
	public static boolean GenerateFile(String fileStr, String folderPath, String fileName) { // 对字节数组字符串进行Base64解码并生成图片
		// 图像数据为空
		if (fileStr == null) {
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		byte[] b = null;
		try {
			// Base64解码
			b = decoder.decodeBuffer(fileStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}

			File file = new File(folderPath);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			// 生成图片
			String imgPath = folderPath + "/" + fileName;
			out = new FileOutputStream(imgPath);
			out.write(b);
			out.flush();

			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ExceptionMsgUtil.getInstance(ConvertUtils.class, e);
			}
		}
	}

	/**
	 * 将时间字符串转换为Date对象
	 * 
	 * @param dateStr
	 * @param parseType
	 *            1:yyyyMMddHH 2:yyyy-MM-dd HH:mm:ss.SSS 3:yyyyMMdd 4:yyyy-MM-dd
	 *            HH:mm:ss 5:yyyyMM 6:yyyy-MM-dd
	 * @return date对象
	 */
	public static Date parseStringToDate(String dateStr, int parseType) {
		Date date = null;
		String parseStr = "";
		switch (parseType) {
		case 1:
			parseStr = "yyyyMMddHH";
			break;
		case 2:
			parseStr = "yyyy-MM-dd HH:mm:ss.SSS";
			break;
		case 3:
			parseStr = "yyyyMMdd";
			break;
		case 4:
			parseStr = "yyyy-MM-dd HH:mm:ss";
			break;
		case 5:
			parseStr = "yyyyMM";
			break;
		case 6:
			parseStr = "yyyy-MM-dd";
			break;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(parseStr);
		if (!CheckUtils.isNull(dateStr)) {
			try {
				date = sdf.parse(dateStr);
			} catch (Exception e) {
				// TODO: handle exception
				ExceptionMsgUtil.getInstance(ConvertUtils.class, e);
			}
		}
		return date;
	}

	/**
	 * 将date对象转换成时间字符串
	 * 
	 * @param date
	 *            date对象
	 * @param formatType
	 *            1：yyyyMMddHH 2:yyyy-MM-dd 3:yyyy-MM-dd HH:mm:ss 4:yyyy
	 *            5:yyyyMM 6:yyyy-MM 7:MM/dd 8:yyyyMMddHHmmssSSS
	 *            9:yyyyMMddHHmmss 10:yyyyMMdd 11:ss mm HH dd MM ? yyyy
	 * @return
	 */
	public static String formatDateToString(Date date, int formatType) {
		String formateSr = "";
		switch (formatType) {
		case 1:
			formateSr = "yyyyMMddHH";
			break;
		case 2:
			formateSr = "yyyy-MM-dd";
			break;
		case 3:
			formateSr = "yyyy-MM-dd HH:mm:ss";
			break;
		case 4:
			formateSr = "yyyy";
			break;
		case 5:
			formateSr = "yyyyMM";
			break;
		case 6:
			formateSr = "yyyy-MM";
			break;
		case 7:
			formateSr = "MM/dd";
			break;
		case 8:
			formateSr = "yyyyMMddHHmmssSSS";
			break;
		case 9:
			formateSr = "yyyyMMddHHmmss";
			break;
		case 10:
			formateSr = "yyyyMMdd";
			break;
		case 11:
			formateSr = "ss mm HH dd MM ? yyyy";
			break;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(formateSr);
		return sdf.format(date);
	}

	/***
	 * MD5加密 生成32位md5码
	 * 
	 * @param password
	 *            待加密字符串
	 * @return 返回32位md5码
	 */
	public static String encryptMD5(String password) {
		MessageDigest md5 = null;
		byte[] byteArray = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byteArray = password.getBytes("UTF-8");
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(ConvertUtils.class, e);
			return "";
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 将json对象转换成json字符串
	 * 
	 * @param json
	 *            json对象
	 * @return json字符
	 */
	public static String JSONObjectToString(Object json) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(json);
	}

	/**
	 * 保留小数点后几位
	 * 
	 * @param num
	 *            需要处理的数
	 * @param parseType
	 *            格式化的类型
	 * @return
	 */
	public static double parseDouble(double num, String parseType) {
		DecimalFormat df = new DecimalFormat(parseType);
		return ConvertUtils.doubleFormat(df.format(num), 0);
	}

	/**
	 * 格式化整数
	 * 
	 * @param num
	 *            需要处理的数
	 * @param parseType
	 *            格式化的类型
	 * @return
	 */
	public static String parseInt(int num, String parseType) {
		DecimalFormat df = new DecimalFormat(parseType);
		return df.format(num);
	}

	public static void main(String[] args) {
	}
}

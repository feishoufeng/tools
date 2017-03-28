package com.my.test;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Encoder;

public class CreateThumbnail {
	public static String createThumbnail(String imgFile) {
		InputStream in = null;
		byte[] data = null;
		// byte[] dt = null;

		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			// String str = new String(data, "utf-8");
			// BASE64Decoder decoder = new BASE64Decoder();
			// dt = decoder.decodeBuffer(str);

			BASE64Encoder encoder = new BASE64Encoder();
			String imgStr = encoder.encode(data);

			// BASE64Decoder decoder = new BASE64Decoder();
			// byte[] b = decoder.decodeBuffer(imgStr);
			// for (int i = 0; i < b.length; ++i) {
			// if (b[i] < 0) {// 调整异常数据
			// b[i] += 256;
			// }
			// }
			//
			// FileOutputStream outPut = new FileOutputStream("D://11112.jpg");
			// outPut.write(b);
			// outPut.flush();
			// outPut.close();
			in.close();
			return imgStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String transmitMsg(String oldStr) {
		String newStr = oldStr;

		if (StringUtils.isNotEmpty(oldStr)) {
			// 格式化所需要的字符串样式
			String formatStr = oldStr.replaceAll("<img", "<<<img");
			formatStr = formatStr.replaceAll("\">", "\"><<");

			String[] strs = formatStr.split("<<");
			for (String str : strs) {
				if (str.contains("<img")) {
//					String imgIndex = str.substring(str.lastIndexOf("/") + 1, str.indexOf(".gif"));

					int code = 0xa4565;
					char[] chars = Character.toChars(code);
					String key = Character.toString(chars[0]);
					for (int i = 1; i < chars.length; ++i) {
						key = key + Character.toString(chars[i]);
					}
					newStr = newStr.replace(str, key);
				}
			}
			return newStr;
		} else {
			return "";
		}
	}

	public static void main(String[] args) {
		String str = "faefqawf<img src=\"/assets/images/Emoticon/4.gif\" style=\"max-width: 24px;\">fawefa<img src=\"/assets/images/Emoticon/35.gif\" style=\"max-width: 24px;\">feawfga";
		String newStr = transmitMsg(str);
		System.out.println(newStr);
	}
}

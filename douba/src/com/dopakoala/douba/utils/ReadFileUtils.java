package com.dopakoala.douba.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import net.sf.json.JSONArray;

/**
 * 流读取解析
 * 
 * @author ShoufengFei
 *
 */
public class ReadFileUtils {
	/**
	 * 读取融云zip文件
	 * 
	 * @param file
	 *            文件路径
	 * @return 返回json数据
	 * @throws Exception
	 */
	public static JSONArray readZipFile(String file) {
		ZipFile zf = null;
		ZipInputStream zin = null;
		JSONArray json = new JSONArray();
		try {
			zf = new ZipFile(file);
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			zin = new ZipInputStream(in);
			ZipEntry ze;
			String zipStr = "";
			while ((ze = zin.getNextEntry()) != null) {
				if (!ze.isDirectory()) {
					System.out.println("file - " + ze.getName() + " : " + ze.getSize() + " bytes");
					long size = ze.getSize();
					if (size > 0) {
						BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
						String line;
						while ((line = br.readLine()) != null) {
							zipStr += line;
						}
						br.close();
					}
				}
			}
			String splitStr = file.substring(file.lastIndexOf("/") + 1, file.lastIndexOf("/") + 9);
			splitStr = splitStr.substring(0, 4) + "/" + splitStr.substring(4, 6) + "/" + splitStr.substring(6, 8);
			if (!CheckUtils.isNull(zipStr)) {
				String[] zipStrs = zipStr.split(splitStr);
				for (String str : zipStrs) {
					if (str.indexOf("{") != -1) {
						json.add(str.substring(str.indexOf("{"), str.length()));
					}
				}
			}
			zin.closeEntry();
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(ReadFileUtils.class, e);
		} finally {
			try {
				zin.close();
				zf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ExceptionMsgUtil.getInstance(ReadFileUtils.class, e);
			}
		}
		return json;
	}

	public static void main(String[] args) {
		ReadFileUtils.readZipFile("D:/2016122711.zip");
	}
}

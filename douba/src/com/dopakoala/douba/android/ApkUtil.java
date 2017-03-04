package com.dopakoala.douba.android;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

public class ApkUtil {

	private static final Namespace NS = Namespace.getNamespace("http://schemas.android.com/apk/res/android");

	public static ApkInfo getApkInfo(String apkPath) {
		ApkInfo apkInfo = new ApkInfo();
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			document = builder.build(getXmlInputStream(apkPath));
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(ApkUtil.class, e);
		}
		Element root = document.getRootElement();// 跟节点-->manifest
		apkInfo.setVersionCode(ConvertUtils.intFormat(root.getAttributeValue("versionCode", NS), 0));
		apkInfo.setVersionName(root.getAttributeValue("versionName", NS));
		Element elemUseSdk = root.getChild("uses-sdk");// 子节点-->uses-sdk
		apkInfo.setMinSdkVersion(elemUseSdk.getAttributeValue("minSdkVersion", NS));
		List<Element> listPermission = root.getChildren("uses-permission");// 子节点是个集合
		List<String> permissions = new ArrayList<String>();
		for (Object object : listPermission) {
			String permission = ((Element) object).getAttributeValue("name", NS);
			permissions.add(permission);
		}
		apkInfo.setUses_permission(permissions);
		
		String s = root.getAttributes().toString();
		String c[] = s.split(",");
		String packageName = null;
		for(String a: c){
			if(a.contains("package")){
				packageName = a.substring(a.indexOf("package=\"")+9, a.lastIndexOf("\""));
				apkInfo.setApkPackage(packageName);
			}
		}
		return apkInfo;
	}

	private static InputStream getXmlInputStream(String apkPath) {
		InputStream inputStream = null;
		InputStream xmlInputStream = null;
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(apkPath);
			ZipEntry zipEntry = new ZipEntry("AndroidManifest.xml");
			inputStream = zipFile.getInputStream(zipEntry);
			AXMLPrinter xmlPrinter = new AXMLPrinter();
			xmlPrinter.startPrinf(inputStream);
			xmlInputStream = new ByteArrayInputStream(xmlPrinter.getBuf().toString().getBytes("UTF-8"));
		} catch (IOException e) {
			ExceptionMsgUtil.getInstance(ApkUtil.class, e);
			try {
				inputStream.close();
				zipFile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return xmlInputStream;
	}

}

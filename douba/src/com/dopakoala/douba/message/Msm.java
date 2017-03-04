package com.dopakoala.douba.message;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;

import javax.net.ssl.*;

import com.dopakoala.douba.entity.MsmResult;
import com.dopakoala.douba.utils.*;

/**
 * 发送短信
 * 
 * @author ShoufengFei
 *
 */
public class Msm {

	public static final String regSmsUrl = AchieveUtils.getConfVal("regSmsUrl");// 发送短信接口
	public static final String regBalanceUrl = AchieveUtils.getConfVal("regBalanceUrl");
	public static final String regCode = AchieveUtils.getConfVal("regCode"); // 华兴软通注册码，请在这里填写您从客服那得到的注册码
	public static final String regPasswod = AchieveUtils.getConfVal("regPasswod"); // 华兴软通注册码对应的密码，请在这里填写您从客服那得到的注册码

	public static MsmResult requestPost(String strUrl, String param) {
		String returnStr = ""; // 返回结果定义
		URL url = null;
		HttpsURLConnection httpsURLConnection = null;

		try {
			url = new URL(strUrl);
			httpsURLConnection = (HttpsURLConnection) url.openConnection();
			// httpsURLConnection.setSSLSocketFactory(HttpRuquest.initSSLSocketFactoryByJKS());
			// 设置套接工厂
			httpsURLConnection.setSSLSocketFactory(initSSLSocketFactoryByPEM());
			httpsURLConnection.setRequestProperty("Accept-Charset", "utf-8");
			httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpsURLConnection.setDoOutput(true);
			httpsURLConnection.setDoInput(true);
			httpsURLConnection.setRequestMethod("POST"); // post方式
			httpsURLConnection.connect();

			// POST方法时使用
			byte[] byteParam = param.getBytes("UTF-8");
			DataOutputStream out = new DataOutputStream(httpsURLConnection.getOutputStream());
			out.write(byteParam);
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(httpsURLConnection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			reader.close();
			returnStr = buffer.toString();
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(Msm.class, e);
			return null;
		} finally {
			if (httpsURLConnection != null) {
				httpsURLConnection.disconnect();
			}
		}
		return format(returnStr);
	}

	// 使用JSK格式文件生成信任库(不很推荐这个方法，因为jks需要生成)
	public static SSLSocketFactory initSSLSocketFactoryByJKS() throws Exception {

		KeyStore keyStore = KeyStore.getInstance("jks");
		String fileTruseStore = AchieveUtils.getRealPath() + "/classes/WoSign.jks"; // 信任库文件，发布项目时打包到resource路径,可以用相对路径
		String pwTruseStore = "hxrt_sms_demo"; // 默认密码，和库文件绑定的，不需要改，如果一定要改请和客服联系
		FileInputStream f_trustStore = new FileInputStream(fileTruseStore);
		keyStore.load(f_trustStore, pwTruseStore.toCharArray());
		String alg = TrustManagerFactory.getDefaultAlgorithm();
		TrustManagerFactory tmFact = TrustManagerFactory.getInstance(alg);
		tmFact.init(keyStore);
		TrustManager[] tms = tmFact.getTrustManagers();
		SSLSocketFactoryEx sslSocketFactoryEx = new SSLSocketFactoryEx(null, tms, new java.security.SecureRandom());
		return sslSocketFactoryEx;
	}

	// 使用PEM根证书文件然后用别名的方式设置到KeyStore中去，推荐该方法(PEM根证书能从公开地址下载到)
	public static SSLSocketFactory initSSLSocketFactoryByPEM() throws Exception {
		KeyStore keyStore = KeyStore.getInstance("jks");
		keyStore.load(null, null); // 设置一个空密匙库
		String trustCertPath = AchieveUtils.getRealPath() + "/classes/cacert.pem"; // 证书文件路径，发布项目时打包到resource路径
		FileInputStream trustCertStream = new FileInputStream(trustCertPath);
		CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");
		Collection<? extends Certificate> certs = cerFactory.generateCertificates(trustCertStream); // 读取多份证书(如果文件流中存在的话)
		for (Certificate certificate : certs) {
			keyStore.setCertificateEntry("" + ((X509Certificate) certificate).getSubjectDN(), certificate); // 遍历集合把证书添加到keystore里，每个证书都必须用不同的唯一别名，否则会被覆盖
		}
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
		tmf.init(keyStore);
		TrustManager tms[] = tmf.getTrustManagers();
		SSLSocketFactoryEx sslSocketFactoryEx = new SSLSocketFactoryEx(null, tms, new java.security.SecureRandom());
		return sslSocketFactoryEx;
	}

	/**
	 * 获取余额的方法
	 */
	public static void getBalance() {
		String param = "reg=" + regCode + "&pwd=" + regPasswod;
		MsmResult msmResult = requestPost(regBalanceUrl, param);
		System.out.println("getBalance: " + msmResult.getBalance());
	}

	/**
	 * 发送短信的方法
	 * 
	 * @param phone
	 *            手机
	 * @param content
	 *            内容
	 */
	public static MsmResult send(String phone, String content) {
		String sourceAdd = null; // 子通道号（最长10位，可为空
		MsmResult msmResult = new MsmResult();
		try {
			content = URLEncoder.encode(content, "UTF-8"); // content中含有空格，换行，中文等非ascii字符时，需要进行url编码，否则无法正确传输到服务器
			String param = "reg=" + regCode + "&pwd=" + regPasswod + "&sourceadd=" + sourceAdd + "&phone=" + phone
					+ "&content=" + content;
			msmResult = requestPost(regSmsUrl, param);
			System.out.println("msmSend: " + msmResult.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return msmResult;
	}

	private static MsmResult format(String returnStr) {
		MsmResult msmResult = new MsmResult();
		if (!CheckUtils.isNull(returnStr)) {
			String[] strs = returnStr.split("&");
			for (String str : strs) {
				if (str.indexOf("result") != -1) {
					msmResult.setCode(ConvertUtils.intFormat(str.split("=")[1], 0));
				}
				if (str.indexOf("message") != -1) {
					msmResult.setMessage(str.split("=")[1]);
				}
				if (str.indexOf("smsid") != -1) {
					msmResult.setSmsid(str.split("=")[1]);
				}
				if (str.indexOf("balance") != -1) {
					msmResult.setBalance(ConvertUtils.doubleFormat(str.split("=")[1], 0.00));
				}
			}
		}
		return msmResult;
	}

	// 华兴软通，sdk接口调用demo
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Msm.send("18706128905",
		// AchieveUtils.formatStr(ConstantsUtils.MSM_REGISTER_TIP, "888888"));
		Msm.getBalance();
	}
}

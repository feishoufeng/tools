package com.dopakoala.douba.testmybatis;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

public class Test {

	private CloseableHttpClient getHttpClient() {
		return HttpClients.createDefault();
	}

	private void closeHttpClient(CloseableHttpClient client) {
		try {
			if (client != null) {
				client.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

	/**
	 * 通过GET方式发起http请求
	 */
	public void requestByGetMethod() {
		// 创建默认的httpClient实例
		CloseableHttpClient httpClient = getHttpClient();
		try {
			// 用get方法发送http请求
			HttpGet get = new HttpGet("http://www.baidu.com");
			System.out.println("执行get请求:...." + get.getURI());
			// 发送get请求
			CloseableHttpResponse httpResponse = httpClient.execute(get);
			try {
				// response实体
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					System.out.println("响应状态码:" + httpResponse.getStatusLine());
					System.out.println("-------------------------------------------------");
					System.out.println("响应内容:" + EntityUtils.toString(entity, "utf-8"));
					System.out.println("-------------------------------------------------");
				}
			} finally {
				httpResponse.close();
			}
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (Exception e) {
				ExceptionMsgUtil.getInstance(this.getClass(), e);
			}
		}

	}

	/**
	 * POST方式发起http请求
	 */
	public void requestByPostMethod() {
		CloseableHttpClient httpClient = getHttpClient();
		try {
			HttpPost post = new HttpPost("http://www.baidu.com"); // 这里用上本机的某个工程做测试
			// 创建参数列表
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("j_username", "admin"));
			list.add(new BasicNameValuePair("j_password", "admin"));
			// url格式编码
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
			post.setEntity(uefEntity);
			System.out.println("POST 请求...." + post.getURI());
			// 执行请求
			CloseableHttpResponse httpResponse = httpClient.execute(post);
			try {
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					System.out.println("响应状态码:" + httpResponse.getStatusLine());
					System.out.println("-------------------------------------------------------");
					System.out.println(EntityUtils.toString(entity));
					System.out.println("-------------------------------------------------------");
				}
			} finally {
				httpResponse.close();
			}

		} catch (UnsupportedEncodingException e) {
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (Exception e) {
				ExceptionMsgUtil.getInstance(this.getClass(), e);
			}
		}
	}

	public static void main(String[] args) {
		// try {
		// // 从融云获取下载信息
		// JSONObject json =
		// RongCloudController.getInstance("2016111516").getHistory();
		// if (json != null && json.containsKey("url") &&
		// json.containsKey("fileType")) {
		// String url = json.getString("url");
		// String fileType = json.getString("fileType");
		// System.out.println(url);
		// // 将聊天记录信息文件下载到本地
		// DownLoadUtils.downLoadFromUrl(url, "2016111516" + fileType, "D:/");
		// // 从文件夹中拿出聊天记录详情
		// JSONArray array = ReadUtils.readZipFile("D:/2016111516" + fileType);
		// System.out.println(array.toString());
		// }
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// CatchExceptionMsgUtil.getInstance(this.getClass(), e);
		// }
		// int n =
		// AchieveUtils.getYearWeek(ConvertUtil.parseStringToDate("20161127",
		// 3));
		Calendar calendar = Calendar.getInstance();
		String time = ConvertUtils.formatDateToString(calendar.getTime(), 11);
		System.out.println(time);
		System.out.println(AchieveUtils.getMillis());
	}
}

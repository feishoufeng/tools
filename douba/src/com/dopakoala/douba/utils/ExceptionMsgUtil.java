package com.dopakoala.douba.utils;

import java.util.Date;

/**
 * 输出错误日志
 * 
 * @author ShoufengFei
 *
 */
public class ExceptionMsgUtil {
	private Class<?> clas;
	private Exception e;

	/**
	 * 调用错误信息捕捉
	 * 
	 * @param clas
	 *            上下文
	 * @param exceptionMsg
	 *            错误日志
	 * @return
	 */
	public static ExceptionMsgUtil getInstance(Class<?> clas, Exception e) {
		return new ExceptionMsgUtil(clas, e);
	}

	public ExceptionMsgUtil(Class<?> clas, Exception e) {
		this.clas = clas;
		this.e = e;
		printExceptionMsg();
	}

	private void printExceptionMsg() {
		StackTraceElement[] st = e.getStackTrace();
		System.out.println("***************   异   **********   常   **********   开   **********   始   ***************\n");
		System.out.println("异常时间：" + ConvertUtils.formatDateToString(new Date(), 3));
		System.out.println("发生异常所在类： " + clas.getSimpleName());
		System.out.println("异常信息： " + e.getMessage());
		System.out.println("异常详情: ");
		for (StackTraceElement stackTraceElement : st) {
			String method = stackTraceElement.getMethodName();
			int lineNum = stackTraceElement.getLineNumber();
			String className = stackTraceElement.getClassName();
			String lineFlag = lineNum + "";
			int length = lineFlag.length();
			if (length <= 5) {
				lineFlag = lineFlag.replace("-", "");
				for (int i = 0; i < 5 - length; i++) {
					lineFlag = 0 + lineFlag;
				}

				if (lineNum < 0) {
					lineFlag = "-" + lineFlag;
				}
			}
			if (clas.getName().equals(className)) {
				System.out.println("          第 " + lineFlag + " 行代码处发生异常!,所在类：" + className + ",方法为： " + method);
			}
		}
		System.out.println("\n");
		System.out.println("***************   异   **********   常   **********   结   **********   束   ***************");
	}

}

package com.alipay.config;

public class AlipayConfig {
	// 商户appid
	public static String APPID = "2016052301430314";
	// 私钥 pkcs8格式的
	public static String RSA_PRIVATE_KEY = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQDaHepG0Tkt+nmy5YbwfkLNggfz2Vw/OzZfoIaQYxuxRac7e0DTplUs466By8n3BCgMTxY4l5q1Bgc955jothw4Q0mcSSm7p3qTVt7pbyV1y1OKBegazonUSmtH7L/RNDRsRfe69zNXdp8u/uiWmWE6C90MBRqA7EPHPieiWmBhtVnQUH2lofTajRUNdj0u2SnCc/uePOGU3ytl8ceAhFdxHa9fWQQAOuA4qErVgC8KQ4uJdMR/c+YHeIAB/vqOdlGDjdcCC20bKKRJBD7ZrpQz2wc/PP3xJu804+38N6P552xhO1PRXhJM7wuG8PmWZgl9OIH9DFkIucC/vQHoFxYBAgMBAAECggEBANV19XN9CyKuQTANZXQzfRk2qqXBEYBoaJxJs6fW9qEU88dIpnb8D9uwr3YjhPKURMVXGP4lkKicz7WL1J/aqd9+KqojCA64p0NO/zUhK14OsSEhCrhBba4vbrKjRk5tcuiLyRhXGQ3972fE3MeWMAro1VoQHJITp3l0oMFJBY5YaJA0j8gczflRYGCiSpLk2rSxLmHaZNt/ILs1riOMX65jZmnkdv4JA9RMvmyZJjhlwQhC413VgVeLaNqTAt+8TAIFubMJgzLTB8/dy57Qop6mcFv+fq14jJU+ZChWKWYhIg0/PpVLe7TEN4oyBwJ8x8BhArrJBxP4Ze2ITIhQYAECgYEA722CeGiS5SV36zBVCh4U1QdAo2bR4nWKKRHV57+MCcHR2ErlO+VPlDh7CTGwLoIkLiLAbnfzwgFCnYMVGtD9XWzLLyDhYo+222JBTzEEFyYVK5m9zMtfSoH6iPQV0kgNsFOWqUZ8K1cBJ7CX+e7oyW4WH/FZZeiQirTvQKNHM0ECgYEA6TbKij65ksa4IBfCtdvV2MjJp/v9sYOgu5+oBb84O5GanHDv9Vdz2+SmXEHTU9+zp8bZdR6PMQo9Iavt1e0iDcrOItnxZPbiRpIjM+CXpLUIMOCAj/WTLBqOas7zO42lfpeg5iPM6VSfEyAsl/SvA5OveW2XyOqplSxg4KF28sECgYEAw5DkgCuSyP74269n1tLJW1QINS/4AgzWJEPAQAKShSm+mPhcE7dzb/NOdC0U5nhAyzpxigoKng3fdr8qTvQj79q8gCtQTexrhPjVtbSozau6oRl8EbqQhFB07sbIyDE4lS1Bo9TB+bigZeX1zR2bIWT9R2whBH+ZCONPhbGYp8ECgYEA0eJqs7IxxuOq788SgCLBuWEzA0ipD+o7bMAKMmnejNXEhMirXG0BK0QDaIIhU5m/EN7H9Dy1d9gUHkqiOGY6wJjlnb3xHwGFsSdyIQ0ezK/UGFDjwyuq8QVRaNSpWM5ow55owtYzY0ayBz9sgrFGFP0D7k9wS/TM/CC9a0xFRcECgYEA1yLO/+j3aRPDnuk88xD2f2kvv/8AhMWILO0ljPb57Nv7PNpcviCY8Q09t6A7G1ukP0C8eo4DfefiWDl+hTeh0bVoFO2WjIn7nMp6ItcYc5772jSKaKfDbBHWXf4GJdqdokEzFWsOHeGkxFw/fd1qxnASMzB7CPBGcceN3CnQqKk=";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/notify_url.jsp";
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/return_url.jsp";
	// 请求网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArEAflbkPdnsPK5OdseHLRpciOjA3RGHqKrcQeYfqQASbsMwyoXmVH6FsO+HqZOAL7B+Q+EO3ra1M0mdlp/NebAc9H6NOxKrS5xgZefmADqsmxo7xQMuVXUrY5zNGhwECCKX+knwrVkmnqdGvVD41PnH/abOMTAOgUIMfwb5y17l1u+B530hGntBb5qkh8fDQ+2dIU/smhmMIFIkT2yXYmELq+mWoa3nyGmVMOxrfIMZzWXzPV7R+vzFzv9rgtW4CevKNrIUJz7hlqOzHeC29oXMsm5X/QA8whhNVr+kVv0WKx9o6R3mkual3aV+UWKB3ioZq4rjF0uscRE5iAPfsnwIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
}

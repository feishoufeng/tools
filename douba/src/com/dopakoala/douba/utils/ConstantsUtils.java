package com.dopakoala.douba.utils;

/**
 * 常量定义工具类
 * 
 * @author admin-01
 * @version 1.0 2016/04/26 10:10
 */
public class ConstantsUtils {
	/**
	 * 验证码有效时间
	 */
	public static final int MESSAGE_TIMEOUT = 60;
	/**
	 * 融云聊天历史记录同步初始时间(精确到小时)
	 */
	public static final String RONGCLOUD_SYNC_INIT_TIME = "2016110101";
	/**
	 * 融云聊天历史记录文件下载路径
	 */
	public static final String RONGCLOUD_CONTACT_HISTORY_FILES = "/upload/contact/history";
	/**
	 * 融云聊天历史记录图片路径
	 */
	public static final String RONGCLOUD_CONTACT_IMG_FILES = "/upload/contact/images";
	/**
	 * 融云聊天历史记录语音路径
	 */
	public static final String RONGCLOUD_CONTACT_VOICE_FILES = "/upload/contact/voices";
	/**
	 * 融云聊天历史记录文件路径
	 */
	public static final String RONGCLOUD_CONTACT_FILES = "/upload/contact/files";
	/**
	 * 跑团公告类型
	 */
	public static final String NOTICE_TYPE = "notice,topic,event";
	/**
	 * 上传头像类型
	 */
	public static final String HEADER_TYPE = "paotuan,user,group,sign";
	/**
	 * 跑团头像路径
	 */
	public static final String PAOTUAN_HEADER_PATH = "/upload/header/paotuan";
	/**
	 * 群头像路径
	 */
	public static final String GROUP_HEADER_PATH = "/upload/header/group";
	/**
	 * 用户头像路径
	 */
	public static final String USER_HEADER_PATH = "/upload/header/user";
	/**
	 * 群缩略头像路径
	 */
	public static final String GROUP_HEADER_THUMBNAIL_PATH = "/upload/header/group/thumbnail";
	/**
	 * 用户缩略头像路径
	 */
	public static final String USER_HEADER_THUMBNAIL_PATH = "/upload/header/user/thumbnail";
	/**
	 * 跑团缩略头像路径
	 */
	public static final String PAOTUAN_HEADER_THUMBNAIL_PATH = "/upload/header/paotuan/thumbnail";
	/**
	 * 跑团二维码路径
	 */
	public static final String PAOTUAN_QRCODE_PATH = "/upload/qrcode/paotuan";
	/**
	 * 群二维码路径
	 */
	public static final String GROUP_QRCODE_PATH = "/upload/qrcode/group";
	/**
	 * 用户二维码路径
	 */
	public static final String USER_QRCODE_PATH = "/upload/qrcode/user";
	/**
	 * 公告图片保存路径
	 */
	public static final String APP_NEWS_IMG_PATH = "/upload/message/news";
	/**
	 * 入群申请审核
	 */
	public static final String CHECK_APPLY_GROUP = "reviewGroup";
	/**
	 * 入团申请审核
	 */
	public static final String CHECK_APPLY_PAOTUAN = "reviewPaotuan";
	/**
	 * 入群申请
	 */
	public static final String APPLY_GROUP = "applyGroup";
	/**
	 * 入团申请
	 */
	public static final String APPLY_PAOTUAN = "applyPaotuan";
	/**
	 * 开卡提示
	 */
	public static final String OPEN_CARD_TIP = "openCardTip";

	/**
	 * 默认用户头像地址
	 */
	public static final String DEFAULT_USER_HEADER_PATH = "/upload/header/user/default/user_default.png";
	/**
	 * 默认群头像地址
	 */
	public static final String DEFAULT_GROUP_HEADER_PATH = "/upload/header/group/default/group_default.png";
	/**
	 * 默认跑团头像地址
	 */
	public static final String DEFAULT_PAOTUAN_HEADER_PATH = "/upload/header/paotuan/default/paotuan_default.png";
	/**
	 * 二维码中间默认图像地址
	 */
	public static final String DEFAULT_QRCODE_CENTER_LOGO_PATH = "/upload/qrcode/default/logo.png";
	/**
	 * 打卡轨迹图路径
	 */
	public static final String SIGN_TRAIL_IMG_PATH = "/upload/sign/";
	/**
	 * 打卡轨迹缩略图路径
	 */
	public static final String SIGN_TRAIL_THUMBNAIL_IMG_PATH = "/upload/sign/thumbnail/";
	/**
	 * 绑定手机验证信息
	 */
	public static final String APP_UPLOAD_PATH = "/upload/mobileClient/";
	/**
	 * 绑定手机验证信息
	 */
	public static final String APP_UPLOAD_TEMP_PATH = "/upload/mobileClient/temp/";
	/**
	 * 注册验证码提示信息
	 */
	public static final String MSM_REGISTER_TIP = "【豆巴】您的注册短信验证码是%s，请在10分钟内输入使用";
	/**
	 * 密码找回验证信息
	 */
	public static final String MSM_FINDPWD_TIP = "【豆巴】您的找回密码短信验证码是%s，请在10分钟内输入使用";
	/**
	 * 绑定手机验证信息
	 */
	public static final String MSM_BIND_TIP = "【豆巴】您微信登录绑定手机的短信验证码是%s，请在10分钟内输入使用";
	/**
	 * quartz类型flag 退回红包剩余金额
	 */
	public static final String QUARTZ_RETURN_REDPACKET_FLAG = "return_red_packet";
}

package com.dopakoala.douba.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;

import com.dopakoala.douba.entity.Group;
import com.dopakoala.douba.entity.GroupUserSignLog;
import com.dopakoala.douba.entity.User;
import com.dopakoala.douba.service.IGroupService;
import com.dopakoala.douba.service.IGroupUserSignLogService;
import com.dopakoala.douba.service.IUserService;
import com.dopakoala.douba.utils.AchieveUtils;
import com.dopakoala.douba.utils.CheckUtils;
import com.dopakoala.douba.utils.ConstantsUtils;
import com.dopakoala.douba.utils.ConvertUtils;
import com.dopakoala.douba.utils.ImgCompress;
import com.dopakoala.douba.utils.ServletUtils;
import com.dopakoala.douba.utils.ExceptionMsgUtil;

/**
 * Servlet implementation class PicProcess
 */
@WebServlet("/group/picprocess")
public class GroupPicProcessServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IGroupService groupService;
	@Autowired
	private IGroupUserSignLogService groupUserSignLogService;

	/**
	 * Default constructor.
	 */
	public GroupPicProcessServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String errmsg = "";
		int errcode = 0;
		try {
			request.setCharacterEncoding("UTF-8");
			// 文件保存相对路径
			String path = AchieveUtils.getRealPath();
			String url = AchieveUtils.getConfVal("host");

			int uid = 0, typeId = 0, gid = 0;
			String headerType = "", token = "", fileName = "", temp = "", thumbnailTemp = "";
			long timestamp = 0;

			boolean isMultipart = ServletFileUpload.isMultipartContent(request);// 判断是否是表单文件类型
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload sfu = new ServletFileUpload(factory);
				List<FileItem> items = sfu.parseRequest(request);// 从request得到所有上传域的列表

				InputStream file_is = null;
				for (Iterator<FileItem> iter = items.iterator(); iter.hasNext();) {
					FileItem fileitem = (FileItem) iter.next();
					if (fileitem != null) {
						String name = fileitem.getFieldName();
						InputStream is = fileitem.getInputStream();
						if (name.contains("fileName")) {
							fileName = ConvertUtils.inputStreamToString(is);
						}
						if (name.contains("file")) {
							file_is = is;
						}
						if (name.contains("uid")) {
							uid = ConvertUtils.intFormat(ConvertUtils.inputStreamToString(is), 0);
						}
						if (name.contains("headerType")) {
							headerType = ConvertUtils.inputStreamToString(is);
						}
						if (name.contains("token")) {
							token = ConvertUtils.inputStreamToString(is);
						}
						if (name.contains("timestamp")) {
							timestamp = ConvertUtils.longFormat(ConvertUtils.inputStreamToString(is), 0);
						}
						if (name.contains("typeId")) {
							typeId = ConvertUtils.intFormat(ConvertUtils.inputStreamToString(is), 0);
						}
					}
				}

				User user = this.userService.selectByPrimaryKey(uid);
				if (AchieveUtils.getMillis() - timestamp <= 10 * 60 * 1000) {
					// 获取用户是否具有权限
					if (user != null) {
						String tempToken = user.getToken();
						if (tempToken.equals(token)) {
							if (ConstantsUtils.HEADER_TYPE.indexOf(headerType) != -1) {
								if (file_is != null) {
									if (!CheckUtils.isNull(fileName)) {
										String folderPath = path;
										String thumbnailPath = path;
										switch (headerType) {
										case "paotuan":
											folderPath += ConstantsUtils.PAOTUAN_HEADER_PATH;
											thumbnailPath += ConstantsUtils.PAOTUAN_HEADER_THUMBNAIL_PATH;
											break;
										case "group":
											folderPath += ConstantsUtils.GROUP_HEADER_PATH;
											thumbnailPath += ConstantsUtils.GROUP_HEADER_THUMBNAIL_PATH;
											break;
										case "sign":
											GroupUserSignLog groupUserSignLog = this.groupUserSignLogService
													.selectByPrimaryKey(typeId);
											if (groupUserSignLog != null) {
												gid = groupUserSignLog.getGid();
												folderPath += ConstantsUtils.SIGN_TRAIL_IMG_PATH + gid;
												thumbnailPath += ConstantsUtils.SIGN_TRAIL_THUMBNAIL_IMG_PATH + gid;
												File file = new File(folderPath);
												if (!file.exists()) {
													file.mkdir();
												}
												File thumbnailFile = new File(thumbnailPath);
												if (!thumbnailFile.exists()) {
													thumbnailFile.mkdir();
												}
												folderPath += "/" + uid;
												thumbnailPath += "/" + uid;
												temp = ConstantsUtils.SIGN_TRAIL_IMG_PATH + gid + "/" + uid;
												thumbnailTemp = ConstantsUtils.SIGN_TRAIL_THUMBNAIL_IMG_PATH + gid + "/" + uid;
											}
											break;
										}
										if (!ConvertUtils.inputStreamToFile(file_is, folderPath, fileName)) {
											errmsg = "解析图片失败！";
											errcode = 300;
										} else {
											File file = new File(thumbnailPath);
											if (!file.exists()) {
												file.mkdir();
											}
											ImgCompress.getInstance(folderPath + "/" + fileName,
													thumbnailPath + "/" + fileName).resizeFix(200, 200);
										}
									} else {
										errmsg = "上传的图片名称不可以为空！";
										errcode = 600;
									}
								} else {
									errmsg = "上传的图片为空！";
									errcode = 600;
								}
							} else {
								errmsg = "请求的头像类型错误";
								errcode = 600;
							}
						} else {
							errmsg = "账户登录权限过期，请重新登录";
							errcode = 700;
						}
					} else {
						errmsg = "账户id不存在！";
						errcode = 200;
					}
				} else {
					errmsg = "请求时间戳异常，请重试！";
					errcode = 200;
				}
			}

			if (errcode == 0) {
				if (typeId > 0) {
					String thumbnailUrl = url;
					switch (headerType) {
					case "paotuan":
						url += ConstantsUtils.PAOTUAN_HEADER_PATH;
						thumbnailUrl += ConstantsUtils.PAOTUAN_HEADER_THUMBNAIL_PATH;
					case "group":
						if (headerType.equals("group")) {
							url += ConstantsUtils.GROUP_HEADER_PATH;
							thumbnailUrl += ConstantsUtils.GROUP_HEADER_THUMBNAIL_PATH;
						}
						Group updateGroup = new Group();
						updateGroup.setGid(typeId);
						updateGroup.setAvatar(url + "/" + fileName);
						updateGroup.setThumbnail(thumbnailUrl + "/" + fileName);
						this.groupService.updateByPrimaryKeySelective(updateGroup);
						if (headerType.equals("paotuan")) {
							// 获取跑团下的大群id
							int mainId = this.groupService.selectMainByPid(typeId).getGid();
							// 更新大群头像
							Group updateMainGroup = new Group();
							updateMainGroup.setGid(mainId);
							updateMainGroup.setAvatar(url + "/" + fileName);
							updateMainGroup.setThumbnail(thumbnailUrl + "/" + fileName);
							this.groupService.updateByPrimaryKeySelective(updateMainGroup);
							// 获取跑团下的临时群id
							int tempId = this.groupService.selectTempByPid(typeId).getGid();
							// 更新临时群头像
							Group updateTempGroup = new Group();
							updateTempGroup.setGid(tempId);
							updateTempGroup.setAvatar(url + "/" + fileName);
							updateTempGroup.setThumbnail(thumbnailUrl + "/" + fileName);
							this.groupService.updateByPrimaryKeySelective(updateTempGroup);
						}
						break;
					case "sign":
						url += temp;
						thumbnailUrl += thumbnailTemp;
						GroupUserSignLog updateGroupUserSignLog = new GroupUserSignLog();
						updateGroupUserSignLog.setId(typeId);
						updateGroupUserSignLog.setPic(url + "/" + fileName);
						updateGroupUserSignLog.setThumbnail(thumbnailUrl + "/" + fileName);
						this.groupUserSignLogService.updateByPrimaryKeySelective(updateGroupUserSignLog);
						break;
					}
				} else {
					errmsg = "请求的头像对应主键typeId不合法！";
					errcode = 600;
				}
			}

			// 返回参数Map
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 返回应答码
			resultMap.put("errcode", errcode);
			// 返回应答信息
			resultMap.put("errmsg", errmsg);

			ServletUtils.writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

}

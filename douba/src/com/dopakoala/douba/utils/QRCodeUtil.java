package com.dopakoala.douba.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import com.dopakoala.douba.entity.QRCode;
import com.swetake.util.Qrcode;

import jp.sourceforge.qrcode.QRCodeDecoder;

/**
 * 二维码生成工具
 * 
 * @author ShoufengFei
 *
 */
public class QRCodeUtil {
	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            存储内容
	 * @param imgPath
	 *            图片路径
	 */
	public void encoderQRCode(String content, String folder, String imgName, String logoPath) {
		this.encoderQRCode(content, folder, imgName, "png", 7, logoPath);
	}

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            存储内容
	 * @param output
	 *            输出流
	 */
	public void encoderQRCode(String content, OutputStream output, String logoPath) {
		this.encoderQRCode(content, output, "png", 7, logoPath);
	}

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            存储内容
	 * @param imgPath
	 *            图片路径
	 * @param imgType
	 *            图片类型
	 */
	public void encoderQRCode(String content, String folder, String imgName, String imgType, String logoPath) {
		this.encoderQRCode(content, folder, imgName, imgType, 7, logoPath);
	}

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            存储内容
	 * @param output
	 *            输出流
	 * @param imgType
	 *            图片类型
	 */
	public void encoderQRCode(String content, OutputStream output, String imgType, String logoPath) {
		this.encoderQRCode(content, output, imgType, 7, logoPath);
	}

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            存储内容
	 * @param imgPath
	 *            图片路径
	 * @param imgType
	 *            图片类型
	 * @param size
	 *            二维码尺寸
	 */
	public void encoderQRCode(String content, String folder, String imgName, String imgType, int size,
			String logoPath) {
		try {
			BufferedImage bufImg = this.qRCodeCommon(content, imgType, size, logoPath);
			File file = new File(folder);
			if (!file.exists()) {
				file.mkdir();
			}

			File imgFile = new File(folder + "/" + imgName);
			// 生成二维码QRCode图片
			ImageIO.write(bufImg, imgType, imgFile);
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            存储内容
	 * @param output
	 *            输出流
	 * @param imgType
	 *            图片类型
	 * @param size
	 *            二维码尺寸
	 */
	public void encoderQRCode(String content, OutputStream output, String imgType, int size, String logoPath) {
		try {
			BufferedImage bufImg = this.qRCodeCommon(content, imgType, size, logoPath);
			// 生成二维码QRCode图片
			ImageIO.write(bufImg, imgType, output);
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
	}

	/**
	 * 生成二维码(QRCode)图片的公共方法
	 * 
	 * @param content
	 *            存储内容
	 * @param imgType
	 *            图片类型
	 * @param size
	 *            二维码尺寸
	 * @param logoPath
	 *            二维码中间图片
	 * @return
	 */
	private BufferedImage qRCodeCommon(String content, String imgType, int size, String logoPath) {
		BufferedImage bufImg = null;
		try {
			Qrcode qrcodeHandler = new Qrcode();
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
			qrcodeHandler.setQrcodeVersion(size);
			//二维码内容加密
			content = AESUtil.getInstance().encrypt(content);
			// 获得内容的字节数组，设置编码格式
			byte[] contentBytes = content.getBytes("utf-8");
			// 图片尺寸
			int imgSize = 67 + 12 * (size - 1);
			bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			// 设置背景颜色
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);

			// 设定图像颜色> BLACK
			gs.setColor(Color.BLACK);
			// 设置偏移量，不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容> 二维码
			if (contentBytes.length > 0 && contentBytes.length < 800) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");
			}

			if (!CheckUtils.isNull(logoPath)) {
				// 添加中心图片logo
				Image logo = ImageIO.read(new File(logoPath));// 实例化一个Image对象。
				int widthLogo = logo.getWidth(null) > bufImg.getWidth() / 5 ? (bufImg.getWidth() / 5)
						: logo.getWidth(null);
				int heightLogo = logo.getHeight(null) > bufImg.getHeight() / 5 ? (bufImg.getHeight() / 5)
						: logo.getWidth(null);
				/**
				 * logo放在中心
				 */
				int x = (bufImg.getWidth() - widthLogo) / 2;
				int y = (bufImg.getHeight() - heightLogo) / 2;
				gs.drawImage(logo, x, y, widthLogo, heightLogo, null);
			}

			// 输出图片
			gs.dispose();
			bufImg.flush();
		} catch (Exception e) {
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
		return bufImg;
	}

	/**
	 * 解析二维码（QRCode）
	 * 
	 * @param imgPath
	 *            图片路径
	 * @return
	 */
	public String decoderQRCode(String imgPath) {
		// QRCode 二维码图片的文件
		File imageFile = new File(imgPath);
		BufferedImage bufImg = null;
		String content = null;
		try {
			bufImg = ImageIO.read(imageFile);
			QRCodeDecoder decoder = new QRCodeDecoder();
			content = new String(decoder.decode(new QRCode(bufImg)), "utf-8");
		} catch (IOException e) {
			System.out.println("Error: " + e);
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
		return content;
	}

	/**
	 * 解析二维码（QRCode）
	 * 
	 * @param input
	 *            输入流
	 * @return
	 */
	public String decoderQRCode(InputStream input) {
		BufferedImage bufImg = null;
		String content = null;
		try {
			bufImg = ImageIO.read(input);
			QRCodeDecoder decoder = new QRCodeDecoder();
			content = new String(decoder.decode(new QRCode(bufImg)), "utf-8");
		} catch (IOException e) {
			System.out.println("Error: " + e);
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			ExceptionMsgUtil.getInstance(this.getClass(), e);
		}
		return content;
	}

	/**
	 * 
	 * @param id
	 *            数据库表key
	 * @param content
	 *            二维码显示内容
	 * @param basePath
	 *            ConstanUtil中配置的基础路径
	 * @param folderPath
	 *            获取的tomcat运行路径
	 * @param webPath
	 *            获取的网络访问路径
	 * @return
	 */
	public static String createQRCode(int id, String content, String basePath, String folderPath, String webPath,
			String logoPath) {
		// 生成二维码图片名称
		String imgName = AchieveUtils.getMillis() + "" + id + ".png";
		// 二维码图片路径
		String imgPath = folderPath + basePath;
		// 二维码logo图片路径
		logoPath = folderPath + logoPath;
		// 获取二维码处理类
		QRCodeUtil handler = new QRCodeUtil();
		// 生成二维码图片
		handler.encoderQRCode(content, imgPath, imgName, "png", 10, logoPath);
		// 获取二维码网络访问地址
		String qrcodePath = webPath + basePath + "/" + imgName;
		return qrcodePath;
	}

	public static void main(String[] args) {
	}
}

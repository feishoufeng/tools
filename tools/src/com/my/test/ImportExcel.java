package com.my.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ImportExcel {
	public static void improtExcel(HttpServletRequest request, HttpServletResponse response) {

		// 默认路径
		String uploadTo = "D:\\";
		// 支持的文件类型
		String[] errorType = { ".xls" };
		// 格式化日期
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置缓冲区大小，这里是4kb
		factory.setSizeThreshold(4096);
		// 设置缓冲区目录
		factory.setRepository(new File("D:\\"));
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// Set overall request size constraint
		// 设置最大文件尺寸，这里是4MB
		upload.setSizeMax(4 * 1024 * 1024);
		// 开始读取上传信息
		List<FileItem> fileItems = new ArrayList<>();
		try {
			fileItems = upload.parseRequest(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 依次处理每个上传的文件
		Iterator<FileItem> iter = fileItems.iterator();
		System.out.println("fileItems的大小是" + fileItems.size());
		// 正则匹配，过滤路径取文件名
		String regExp = ".+\\\\(.+)$";
		Pattern p = Pattern.compile(regExp);
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			// 忽略其他不是文件域的所有表单信息
			System.out.println("正在处理" + item.getFieldName());
			if (!item.isFormField()) {
				String name = item.getName();
				long size = item.getSize();
				if ((name == null || name.equals("")) && size == 0)
					continue;
				Matcher m = p.matcher(name);
				boolean result = m.find();
				if (result) {
					boolean flag = false;
					for (int temp = 0; temp < errorType.length; temp++) {
						if (m.group(1).endsWith(errorType[temp])) {
							flag = true;
						}
					}
					if (!flag) {
						System.out.println("上传了不支持的文件类型");
						try {
							throw new IOException(name + ": wrong type");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						String fileName = uploadTo + format.format(new Date())
								+ m.group(1).substring(m.group(1).indexOf("."));
						item.write(new File(fileName));
						// 调用ReadExcel类进行读出excel
						ReadExcel.readExcel(fileName, response.getWriter());
						System.out.println(name + "\t\t" + size);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				// 这里添加对不是上传文件表单项的处理
				System.out.println("这是一个表单项");
			}
		}
	}
}

class ReadExcel {
	public static void readExcel(String pathname, PrintWriter out) {
		try {
			// 打开文件
			Workbook book = Workbook.getWorkbook(new File(pathname));
			// 取得第一个sheet
			Sheet sheet = book.getSheet(0);
			// 取得行数
			int rows = sheet.getRows();
			for (int i = 0; i < rows; i++) {
				Cell[] cell = sheet.getRow(i);
				// getCell(列，行)
				for (int j = 0; j < cell.length; j++) {
					out.print(sheet.getCell(j, i).getContents());
					out.print(" ");
				}
				out.println(" ");
			}
			// 关闭文件
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

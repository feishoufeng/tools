<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "Android");

	List<String> options = new ArrayList<String>();
	options.add("v.platform,平台");
	request.setAttribute("options", options);
	
	//是否开启新增按钮
	request.setAttribute("showAdd", true);
	//新增按钮名称
	request.setAttribute("addTitle", "新增App");
%>
<!-- 引入页面头部文件 -->
<jsp:include page="../baseView/base_top.jsp" />
<!-- 引入导出工具 -->
<jsp:include page="../baseView/export_page.jsp" />
<table id="android_list">
	<tr data-name="title">
		<td style="width: 6%;">序号</td>
		<td style="width: 12%;">文件名称</td>
		<td style="width: 8%;">平台</td>
		<td style="width: 6%;">版本号</td>
		<td style="width: 34%;">更新内容</td>
		<td style="width: 8%;">更新性质</td>
		<td style="width: 10%;">创建人</td>
		<td style="width: 16%;">创建时间</td>
	</tr>
</table>
<!-- 引入分页工具条 -->
<jsp:include page="../baseView/turn_page.jsp" />
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "账务列表");

	//筛选条件
	List<List<String>> optionLists = new ArrayList<List<String>>();
	List<String> statusOptions1 = new ArrayList<String>();
	statusOptions1.add("0,禁用");
	statusOptions1.add("1,正常");
	optionLists.add(statusOptions1);
	request.setAttribute("optionLists", optionLists);
	
	List<String> options = new ArrayList<String>();
	options.add("u.mobile,用户名");
	options.add("u.nickname,昵称");
	options.add("u.status,状态,1");
	request.setAttribute("options", options);
%>
<!-- 引入页面头部文件 -->
<jsp:include page="../baseView/base_top.jsp" />
<!-- 引入导出工具 -->
<jsp:include page="../baseView/export_page.jsp" />
<table id="account_list">
	<tr data-name="title">
		<td style="width: 6%;">序号</td>
		<td style="width: 10%;">用户名</td>
		<td style="width: 8%;">状态</td>
		<td style="width: 15%;">昵称</td>
		<td style="width: 17%;">账户余额</td>
		<td style="width: 20%;">专用账户余额</td>
		<td style="width: 16%;">注册时间</td>
		<td style="width: 8%;">功能</td>
	</tr>
</table>
<!-- 引入分页工具条 -->
<jsp:include page="../baseView/turn_page.jsp" />
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
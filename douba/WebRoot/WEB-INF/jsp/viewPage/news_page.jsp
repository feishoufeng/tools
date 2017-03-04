<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "公告列表");
%>
<!-- 引入页面头部文件 -->
<jsp:include page="../baseView/base_top.jsp" />
<div style="width: 100%;" align="right">
	<div id="add">
		<img alt="../" src="../images/viewImages/add.png" style="width: 12px; margin-right: 6px;"> 
		新增公告
	</div>
</div>
<table id="appNews_list">
	<tr data-name="title">
		<td style="width: 5%;">序号</td>
		<td style="width: 39%;">标题</td>
		<td style="width: 8%;">类型</td>
		<td style="width: 14%;">城市</td>
		<td style="width: 7%;">阅读人数</td>
		<td style="width: 6%;">状态</td>
		<td style="width: 11%;">修改时间</td>
		<td style="width: 10%;">操作</td>
	</tr>
</table>
<!-- 引入分页工具条 -->
<jsp:include page="../baseView/turn_page.jsp" />
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
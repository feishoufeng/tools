<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "公告详情");
%>
<!-- 引入页面头部文件 -->
<jsp:include page="../baseView/base_top.jsp" />
<input type="hidden" id="id" value="${id }">
<div style="margin-top:46px;">
	<div id="title"></div>
	<div id="content"></div>
</div>
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
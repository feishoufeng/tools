<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "菜单列表");
%>
<!-- 引入页面头部文件 -->
<jsp:include page="../baseView/base_top.jsp" />
<div>
	<div id="menu" style="float: left; width: 25%;"></div>
	<div id="detail" style="float: left; width: 75%;" align="right">
		<div style="width: 70%;">
		</div>
	</div>
</div>
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
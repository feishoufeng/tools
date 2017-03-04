<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "菜单列表");
%>
<!-- 引入页面头部文件 -->
<jsp:include page="../baseView/base_top.jsp" />
<div style="width:100%;padding-right: 5px;padding-top: 9px;">
	<div style="float: left;width: 60%;margin-top: 8px;">
		<div style="float: left;padding:0 16px;"><img src="../images/viewImages/logo.png"  style="width: 70px;"></div>
		<div style="float: left;"><span class="siteName">${site_name }后台管理系统</span></div>
	</div>
	<div style="float: left;width: 40%;" align="right" class="msgDetai">
		<div style="width:245px;" align="right">
			<div id="welcome"></div>
			<div>
				<jsp:useBean id="now" class="java.util.Date" scope="page" /> 
				今天是<fmt:formatDate value="${now}" type="date" dateStyle="full"/>&nbsp;&nbsp;
			</div>
			<div style="width:203px;" align="right">
				<div id="home" style="width:80px;">
					<img src="../images/viewImages/back.png" style="width: 20px;"/> 
					<span>返回首页</span>
				</div>
				<div id="exit" style="width:88px;margin-left: 34px;">
					<img src="../images/viewImages/exit.png" style="width: 20px;"/> 
					<span>安全退出&nbsp;&nbsp;</span>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
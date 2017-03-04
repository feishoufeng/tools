<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	request.setAttribute("basePath", basePath);
	String kaptchaImagePath = basePath + "/kaptcha/getKaptchaImage";
	request.setAttribute("kaptchaImageUrl", kaptchaImagePath);
	String[] curServletStrs = request.getServletPath().split("/");
	String curJspPath = curServletStrs[curServletStrs.length - 1].replace(".jsp", "");
	request.setAttribute("curJspPath", curJspPath);
	String exceptList = ",login_page,catalog_menu,welcome_page,";
	request.setAttribute("exceptList", exceptList);
	request.setAttribute("curJspPath_temp", "," + curJspPath + ",");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- jQuery -->
<c:if test="${curJspPath eq 'editor' }">
	<script type="text/javascript" src="${basePath }/js/baseJs/jquery-2.2.4.min.js?date=<%= new Date()%>"></script>
</c:if>
<c:if test="${curJspPath ne 'editor' }">
	<script type="text/javascript" src="${basePath }/js/baseJs/jquery-3.1.1.min.js?date=<%= new Date()%>"></script>
</c:if>
<!-- jQuery and the Poshy Tip plugin files -->
<script type="text/javascript" src="${basePath }/js/utilJs/poshytipUtil.js?date=<%= new Date()%>"></script>
<!-- bootstrap -->
<script type="text/javascript" src="${basePath }/js/baseJs/bootstrap.min.js?date=<%= new Date()%>"></script>
<!-- 验证码工具 -->
<script type="text/javascript" src="${basePath }/js/utilJs/kaptchaUtil.js?date=<%= new Date()%>"></script>
<!-- 基础工具 -->
<script type="text/javascript" src="${basePath }/js/utilJs/baseUtil.js?date=<%= new Date()%>"></script>
<!-- Tooltip classes -->
<link rel="stylesheet" href="${basePath }/css/utilCss/tipUtil.css" type="text/css" />
<link rel="stylesheet" href="${basePath }/css/mainCss/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="${basePath }/css/mainCss/font-awesome.min.css" type="text/css">
<!-- 当前页面css样式 -->
<link rel="stylesheet" href="${basePath }/css/viewCss/${curJspPath }.css" type="text/css" />
<!-- 当前页面所需js -->
<script type="text/javascript" src="${basePath }/js/viewJs/${curJspPath }.js?date=<%= new Date()%>"></script>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<title>${pageTitle}</title>
</head>
<body marginheight="0" marginwidth="0">
	<c:if test="${fn:indexOf(exceptList,curJspPath_temp) == -1}">
		<jsp:include page="../baseView/base_bar.jsp" />
		<div align="center">
			<h3>${pageTitle}</h3>
		</div>
	</c:if>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "公告编辑");
%>
<!-- 引入页面头部文件 -->
<jsp:include page="../baseView/base_top.jsp" />
<!-- 获取行政区划工具 -->
<script type="text/javascript" src="${basePath }/js/utilJs/getAreaUtil.js?date=<%= new Date()%>"></script>
<!-- 编辑器工具样式 -->
<link rel="stylesheet" href="${basePath }/css/utilCss/editorUtil.css" type="text/css" />
<!-- 编辑器工具 -->
<script type="text/javascript" src="${basePath }/js/utilJs/editorUtil.js?date=<%= new Date()%>"></script>
<style type="text/css">
input {
	height: 2em;
	width: 500px;
	margin-left: 10px;
}

select {
	height: 2em;
	width: 100px;
	margin-left: 10px;
	margin-bottom: 15px;
}
</style>
<input type="hidden" value="${id }" id="id">
<div>
	<div style="height: 120px;">
		<div style="width: 100%;">
			<span> 
				类型（选填）: 
				<select id="type">
					<option value="0">--</option>
				</select>
			</span> 
			<span style="float: right;"> 
				<a href="#" style="cursor: pointer;" onclick="save();">
					<img src="../images/viewImages/save.png" style="width: 16px; margin-right: 3px;"/>保存
				 </a>
				<a href="#" style="cursor: pointer;"  onclick="reset();">
					<img src="../images/viewImages/reset.png" style="width: 16px; margin-right: 3px; margin-left: 6px;"/>重置
				</a>
			</span>
		</div>
		<div>
			城市（选填）: 
			<select id="province">
				<option value="0">--</option>
			</select>
			<select id="city">
				<option value="0">--</option>
			</select>
			<select id="area">
				<option value="0">--</option>
			</select>
		</div>
		<div>
			标题（必填）: <input type="text" maxlength="50" id="title">
		</div>
	</div>
	<div id="txtEditor"></div>
</div>
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
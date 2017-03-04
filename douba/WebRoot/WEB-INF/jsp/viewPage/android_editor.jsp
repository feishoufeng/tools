<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "新增App");
%>
<!-- 引入页面头部文件 -->
<jsp:include page="../baseView/base_top.jsp" />
<link rel="stylesheet" type="text/css" href="../css/fileinputCss/ssi-default.css">
<link rel="stylesheet" type="text/css" href="../css/fileinputCss/ssi-uploader.css" />
<!-- 编辑器工具样式 -->
<link rel="stylesheet" href="${basePath }/css/utilCss/editorUtil.css" type="text/css" />
<!-- 编辑器工具 -->
<script type="text/javascript" src="${basePath }/js/utilJs/editorUtil.js?date=<%= new Date()%>"></script>
<script type="text/javascript" src="../js/fileinput/ssi-uploader.js?date=<%= new Date()%>"></script>
<style type="text/css">
</style>
<input type="hidden" value="${id }" id="id">
<div style="margin-top: 64px;">
	<div>
		<div style="width:30%;min-width:260px; float: left; background-color: #F0F0F0; padding: 10px 30px;" align="center">
			<div class="htmleaf-container">
				<div class="row">
					<div class="col-md-12">
						<input type="file" multiple="multiple" id="ssi-upload" />
					</div>
				</div>
			</div>
		</div>
		<div style="float: left;height: 361px;margin-left:1%; width:69%;background-color: #F0F0F0; padding: 10px 0;">
			<div style="width: 100%;text-align: left;margin:0 30px;">更新内容：</div>
			<div id="txtEditor"></div>
		</div>
	</div>
</div>
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
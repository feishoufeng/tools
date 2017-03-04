<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "用户详情");
%>
<style type="text/css">
table {
	width: 85%;
}

td {
	border: 1px solid black;
}

td:NTH-CHILD(2n + 1) {
	width: 15%;
	text-align: right;
	padding: 5px 10px;
}

td:NTH-CHILD(2n) {
	width: 35%;
	padding: 5px 10px;
}

tr:NTH-CHILD(2n) {
	background: #F0F0F0;
}

tr:FIRST-CHILD td {
	text-align: center;
	font-weight: bold;
}

td img {
	width: 100px;
}
</style>
<!-- 引入页面头部文件 -->
<jsp:include page="../baseView/base_top.jsp" />
<input type="hidden" id="uid" value="${uid }">
<div align="center" style="margin-top: 46px;">
	<table>
		<tr>
			<td colspan="4">基 本 信 息</td>
		</tr>
		<tr>
			<td>昵称：</td>
			<td></td>
			<td>手机：</td>
			<td></td>
		</tr>
		<tr>
			<td>用户名：</td>
			<td></td>
			<td>性别：</td>
			<td></td>
		</tr>
		<tr>
			<td>地址：</td>
			<td></td>
			<td>姓名：</td>
			<td></td>
		</tr>
		<tr>
			<td>邮箱：</td>
			<td></td>
			<td>毕业院校：</td>
			<td></td>
		</tr>
		<tr>
			<td>签名：</td>
			<td colspan="3"></td>
		</tr>
		<tr>
			<td>个人介绍：</td>
			<td colspan="3">个人介绍</td>
		</tr>
		<tr>
			<td>头像：</td>
			<td><img id="header"></td>
			<td>二维码：</td>
			<td><img id="qrcode"></td>
		</tr>
		<tr>
			<td colspan="4" style="font-weight: bold; text-align: center;">账 户 信 息</td>
		</tr>
		<tr>
			<td>积分：</td>
			<td></td>
			<td>账户余额：</td>
			<td></td>
		</tr>
		<tr>
			<td>最后登录时间：</td>
			<td>个人介绍</td>
			<td>登录次数：</td>
			<td>个人介绍</td>
		</tr>
		<tr>
			<td>注册时间：</td>
			<td></td>
			<td>最后修改时间：</td>
			<td></td>
		</tr>
		<tr>
			<td colspan="4" style="font-weight: bold; text-align: center;">活&nbsp;&nbsp;&nbsp;跃&nbsp;&nbsp;&nbsp;度</td>
		</tr>
		<tr>
			<td>跑步总里程：</td>
			<td></td>
			<td>最后打卡时间：</td>
			<td></td>
		</tr>
		<tr>
			<td>创建跑团个数：</td>
			<td></td>
			<td>创建群个数：</td>
			<td></td>
		</tr>
		<tr>
			<td>加入跑团个数：</td>
			<td></td>
			<td>加入群个数：</td>
			<td></td>
		</tr>
	</table>
</div>
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
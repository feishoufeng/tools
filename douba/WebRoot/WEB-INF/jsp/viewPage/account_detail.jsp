<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "账务统计");
%>
<style type="text/css">
table {
	width: 85%;
}

td {
	border: 1px solid black;
}

td:NTH-CHILD(2n + 1) {
	width: 25%;
	text-align: right;
	padding: 5px 10px;
}

td:NTH-CHILD(2n) {
	width: 25%;
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
			<td colspan="4">总 收 入 统 计</td>
		</tr>
		<tr>
			<td>充值笔数：</td>
			<td>0笔</td>
			<td>充值总金额：</td>
			<td>0.00元</td>
		</tr>
		<tr>
			<td>领取红包个数：</td>
			<td>0个</td>
			<td>领取红包总金额：</td>
			<td>0.00元</td>
		</tr>
		<tr>
			<td>红包退还个数：</td>
			<td>0个</td>
			<td>红包退还总金额：</td>
			<td>0.00元</td>
		</tr>
		<tr>
			<td>总收入金额：</td>
			<td colspan="3">0.00元</td>
		</tr>
		<tr>
			<td colspan="4" style="font-weight: bold; text-align: center;">总 支 出 统 计</td>
		</tr>
		<tr>
			<td>发放红包个数：</td>
			<td>0个</td>
			<td>发放红包总金额：</td>
			<td>0.00元</td>
		</tr>
		<tr>
			<td>购物次数：</td>
			<td>0次</td>
			<td>购物总金额：</td>
			<td>0.00元</td>
		</tr>
		<tr>
			<td>已完成的提现次数：</td>
			<td>0次</td>
			<td>已完成的提现总金额：</td>
			<td>0.00元</td>
		</tr>
		<tr>
			<td>申请中的提现次数：</td>
			<td>0次</td>
			<td>申请中的提现总金额：</td>
			<td>0.00元</td>
		</tr>
		<tr>
			<td>总支出金额：</td>
			<td colspan="3">0.00元</td>
		</tr>
		<tr>
			<td colspan="4" style="font-weight: bold; text-align: center;">账 户 详 情</td>
		</tr>
		<tr>
			<td>账户余额：</td>
			<td>0.00元</td>
			<td>专用账户余额：</td>
			<td>0.00元</td>
		</tr>
	</table>
</div>
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
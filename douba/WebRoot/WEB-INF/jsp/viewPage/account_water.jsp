<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "流水列表");

	//筛选条件
	List<List<String>> optionLists = new ArrayList<List<String>>();
	List<String> statusOptions1 = new ArrayList<String>();
	statusOptions1.add("'充值',充值");
	statusOptions1.add("'提现',提现");
	statusOptions1.add("'发放红包',发放红包");
	statusOptions1.add("'领取红包',领取红包");
	statusOptions1.add("'红包余额退还',红包余额退还");
	optionLists.add(statusOptions1);
	List<String> statusOptions2 = new ArrayList<String>();
	statusOptions2.add("-1,支出");
	statusOptions2.add("1,收入");
	optionLists.add(statusOptions2);
	request.setAttribute("optionLists", optionLists);

	List<String> options = new ArrayList<String>();
	options.add("u.mobile,用户名");
	options.add("u.nickname,昵称");
	options.add("l.action,交易方式,1");
	options.add("l.type,交易类型,2");
	request.setAttribute("options", options);
%>
<!-- 引入页面头部文件 -->
<jsp:include page="../baseView/base_top.jsp" />
<!-- 引入导出工具 -->
<jsp:include page="../baseView/export_page.jsp" />
<input type="hidden" id="uid" value="${uid }">
<table id="water_list">
	<tr data-name="title">
		<td style="width: 8%;">序号</td>
		<td style="width: 16%;">用户名</td>
		<td style="width: 21%;">昵称</td>
		<td style="width: 8%;">交易类型</td>
		<td style="width: 13%;">交易方式</td>
		<td style="width: 8%;">交易金额</td>
		<td style="width: 10%;">账户余额</td>
		<td style="width: 16%;">交易时间</td>
	</tr>
</table>
<!-- 引入分页工具条 -->
<jsp:include page="../baseView/turn_page.jsp" />
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
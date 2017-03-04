<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "提现申请");

	//筛选条件
	List<List<String>> optionLists = new ArrayList<List<String>>();
	List<String> statusOptions1 = new ArrayList<String>();
	statusOptions1.add("0,待审核");
	statusOptions1.add("1,已完成");
	statusOptions1.add("-1,审核失败");
	statusOptions1.add("-2,申请异常");
	optionLists.add(statusOptions1);
	request.setAttribute("optionLists", optionLists);
	
	List<String> options = new ArrayList<String>();
	options.add("u.mobile,用户名");
	options.add("t.status,状态,1");
	request.setAttribute("options", options);
%>
<!-- 引入页面头部文件 -->
<jsp:include page="../baseView/base_top.jsp" />
<!-- 引入导出工具 -->
<jsp:include page="../baseView/export_page.jsp" />
<table id="account_list">
	<tr data-name="title">
		<td style="width: 6%;">序号</td>
		<td style="width: 12%;">用户名</td>
		<td style="width: 8%;">状态</td>
		<td style="width: 8%;">提现金额</td>
		<td style="width: 42%;">备注</td>
		<td style="width: 16%;">申请时间</td>
		<td style="width: 8%;">操作</td>
	</tr>
</table>
<!-- 引入分页工具条 -->
<jsp:include page="../baseView/turn_page.jsp" />
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
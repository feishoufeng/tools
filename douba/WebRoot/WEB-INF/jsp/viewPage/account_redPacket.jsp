<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "红包列表");

	//筛选条件
	List<List<String>> optionLists = new ArrayList<List<String>>();
	List<String> statusOptions1 = new ArrayList<String>();
	statusOptions1.add("'common',普通红包");
	statusOptions1.add("'group',群红包");
	optionLists.add(statusOptions1);
	List<String> statusOptions2 = new ArrayList<String>();
	statusOptions2.add("-1,过期");
	statusOptions2.add("0,异常");
	statusOptions2.add("1,正常");
	statusOptions2.add("2,已领完");
	optionLists.add(statusOptions2);
	List<String> statusOptions3 = new ArrayList<String>();
	statusOptions3.add("-1,未退还");
	statusOptions3.add("0,无");
	statusOptions3.add("1,已退还");
	optionLists.add(statusOptions3);
	request.setAttribute("optionLists", optionLists);

	List<String> options = new ArrayList<String>();
	options.add("u.mobile,用户名");
	options.add("h.code,红包编码");
	options.add("g.name,群名");
	options.add("h.type,红包类型,1");
	options.add("h.status,红包状态,2");
	options.add("h.return_status,返还状态,3");
	request.setAttribute("options", options);
%>
<!-- 引入页面头部文件 -->
<jsp:include page="../baseView/base_top.jsp" />
<!-- 引入导出工具 -->
<jsp:include page="../baseView/export_page.jsp" />
<input type="hidden" id="uid" value="${uid }">
<table id="redPacket_list">
	<tr data-name="title">
		<td style="width: 5%;">序号</td>
		<td style="width: 16%;">红包编码</td>
		<td style="width: 11%;">用户名</td>
		<td style="width: 8%;">红包状态</td>
		<td style="width: 8%;">红包类型</td>
		<td style="width: 12%;">所发群名</td>
		<td style="width: 8%;">红包金额</td>
		<td style="width: 8%;">剩余金额</td>
		<td style="width: 8%;">退还状态</td>
		<td style="width: 16%;">发送时间</td>
	</tr>
</table>
<!-- 引入分页工具条 -->
<jsp:include page="../baseView/turn_page.jsp" />
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
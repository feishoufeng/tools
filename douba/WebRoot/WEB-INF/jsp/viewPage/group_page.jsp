<%@page import="com.dopakoala.douba.entity.GroupType"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "大群列表");

	//筛选条件
	List<List<String>> optionLists = new ArrayList<List<String>>();
	List<String> statusOptions1 = new ArrayList<String>();
	statusOptions1.add("-2,禁用");
	statusOptions1.add("1,正常");
	optionLists.add(statusOptions1);
	List<String> statusOptions2 = new ArrayList<String>();
	Object object = request.getAttribute("list");
	if (object != null && !"".equals(object)) {
		@SuppressWarnings("unchecked")
		List<GroupType> list = (List<GroupType>) object;
		for (GroupType groupType : list) {
			String option = groupType.getId() + "," + groupType.getName();
			statusOptions2.add(option);
		}
	}
	optionLists.add(statusOptions2);
	request.setAttribute("optionLists", optionLists);

	List<String> options = new ArrayList<String>();
	options.add("g.name,群名称");
	options.add("u.mobile,创建人");
	options.add("g.status,状态,1");
	if (object != null && !"".equals(object)) {
		options.add("g.type,群类型,2");
	}
	request.setAttribute("options", options);
%>
<!-- 引入页面头部文件 -->
<jsp:include page="../baseView/base_top.jsp" />
<!-- 引入导出工具 -->
<jsp:include page="../baseView/export_page.jsp" />
<table id="group_list">
	<tr data-name="title">
		<td style="width: 6%;">序号</td>
		<td style="width: 12%;">群名称</td>
		<td style="width: 10%;">创建人</td>
		<td style="width: 8%;">群类型</td>
		<td style="width: 8%;">群状态</td>
		<td style="width: 8%;">群成员数</td>
		<td style="width: 22%;">群介绍</td>
		<td style="width: 16%;">创建时间</td>
		<td style="width: 8%;">操作</td>
	</tr>
</table>
<!-- 引入分页工具条 -->
<jsp:include page="../baseView/turn_page.jsp" />
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
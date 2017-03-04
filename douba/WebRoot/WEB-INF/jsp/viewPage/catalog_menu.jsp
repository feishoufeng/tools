<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "菜单列表");
%>
<!-- 引入页面头部文件 -->
<jsp:include page="../baseView/base_top.jsp" />
<link rel="stylesheet" href="../css/mainCss/sidebar-menu.css">
<script src="../js/mainViewJs/sidebar-menu.js" type="text/javascript"></script>
<body>
<div>
	<!-- 代码 开始 -->
	<aside class="main-sidebar"> 
		<section class="sidebar">
			<ul class="sidebar-menu" id="main">
				<!-- <li class="header" id="main">管理菜单</li> -->
				<!-- <li class="treeview">
						<a href="#"> 
							<i class="fa fa-dashboard"></i>
							<span>Dashboard</span> 
							<i class="fa fa-angle-left pull-right"></i>
						</a>
						<ul class="treeview-menu">
							<li><a href="#"><i class="fa fa-circle-o"></i> Dashboard
									v1</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Dashboard
									v2</a></li>
						</ul>
					</li>
					<li class="treeview"><a href="#"> <i class="fa fa-files-o"></i>
							<span>Layout Options</span> <span
							class="label label-primary pull-right">4</span>
					</a>
						<ul class="treeview-menu" style="display: none;">
							<li><a href="#"><i class="fa fa-circle-o"></i> Top
									Navigation</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Boxed</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Fixed</a></li>
							<li class=""><a href="#"><i class="fa fa-circle-o"></i>
									Collapsed Sidebar</a></li>
						</ul></li>
					<li><a href="#"> <i class="fa fa-th"></i> <span>Widgets</span>
							<small class="label pull-right label-info">new</small>
					</a></li>
					<li class="treeview"><a href="#"> <i class="fa fa-pie-chart"></i>
							<span>Charts</span> <i class="fa fa-angle-left pull-right"></i>
					</a>
						<ul class="treeview-menu">
							<li><a href="#"><i class="fa fa-circle-o"></i> ChartJS</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Morris</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Flot</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Inline
									charts</a></li>
						</ul></li>
					<li class="treeview"><a href="#"> <i class="fa fa-laptop"></i>
							<span>UI Elements</span> <i class="fa fa-angle-left pull-right"></i>
					</a>
						<ul class="treeview-menu">
							<li><a href="#"><i class="fa fa-circle-o"></i> General</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Icons</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Buttons</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Sliders</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Timeline</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Modals</a></li>
						</ul></li>
					<li class="treeview"><a href="#"> <i class="fa fa-edit"></i>
							<span>Forms</span> <i class="fa fa-angle-left pull-right"></i>
					</a>
						<ul class="treeview-menu">
							<li><a href="#"><i class="fa fa-circle-o"></i> General
									Elements</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Advanced
									Elements</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Editors</a></li>
						</ul></li>
					<li class="treeview"><a href="#"> <i class="fa fa-table"></i>
							<span>Tables</span> <i class="fa fa-angle-left pull-right"></i>
					</a>
						<ul class="treeview-menu">
							<li><a href="#"><i class="fa fa-circle-o"></i> Simple
									tables</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Data tables</a></li>
						</ul></li>
					<li><a href="#"> <i class="fa fa-calendar"></i> <span>Calendar</span>
							<small class="label pull-right label-danger">3</small>
					</a></li>
					<li><a href="#"> <i class="fa fa-envelope"></i> <span>Mailbox</span>
							<small class="label pull-right label-warning">12</small>
					</a></li>
					<li class="treeview"><a href="#"> <i class="fa fa-folder"></i>
							<span>Examples</span> <i class="fa fa-angle-left pull-right"></i>
					</a>
						<ul class="treeview-menu">
							<li><a href="#"><i class="fa fa-circle-o"></i> Invoice</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Profile</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Login</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Register</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Lockscreen</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> 404 Error</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> 500 Error</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Blank Page</a></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Pace Page</a></li>
						</ul></li>
					<li class="treeview">
						<a href="#"> 
							<i class="fa fa-share"></i>
							<span>Multilevel</span> 
							<i class="fa fa-angle-left pull-right"></i>
						</a>
						<ul class="treeview-menu">
							<li>
								<a href="#">
									<i class="fa fa-circle-o"></i> 
									Level One
								</a>
							</li>
							<li>
								<a href="#"> 
									<i class="fa fa-circle-o"></i> 
									Level One
									<i class="fa fa-angle-left pull-right"></i>
								</a>
								<ul class="treeview-menu">
									<li><a href="#"><i class="fa fa-circle-o"></i> Level Two</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Level Two<i class="fa fa-angle-left pull-right"></i></a>
										<ul class="treeview-menu">
											<li><a href="#"><i class="fa fa-circle-o"></i> Level
													Three</a></li>
											<li><a href="#"><i class="fa fa-circle-o"></i> Level
													Three</a></li>
										</ul></li>
								</ul></li>
							<li><a href="#"><i class="fa fa-circle-o"></i> Level One</a></li>
						</ul></li>
					<li><a href="#"><i class="fa fa-book"></i> <span>Documentation</span></a></li> -->
				<!-- <li class="header">LABELS</li>
					<li><a href="#"><i class="fa fa-circle-o text-red"></i> <span>Important</span></a></li>
					<li><a href="#"><i class="fa fa-circle-o text-yellow"></i> <span>Warning</span></a></li>
					<li><a href="#"><i class="fa fa-circle-o text-aqua"></i> <span>Information</span></a></li> -->
			</ul>
		</section> 
	</aside>
</div>
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
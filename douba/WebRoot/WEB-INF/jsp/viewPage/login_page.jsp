<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	//设置页面登录标题
	request.setAttribute("pageTitle", "欢迎登录");
%>
<!-- 引入页面头部文件 -->
<jsp:include page="../baseView/base_top.jsp" />
<input type="hidden" id="errmsg" value="${errmsg }"/>
	<div style="width:300px;height:170px;position:absolute;top:34%;left:39%;">
		<form action="${basePath }/login/checkUser" method="post" id="login_form">
			<table>
				<tr>
					<td class="lable">用户名：</td>
					<td class="txt">
						<input type="text" id="userName" name="userName" value="${userName }">
					</td>
				</tr>
				<tr>
					<td class="lable">
						密<span style="margin-left: 1em"></span>码：
					</td>
					<td class="txt">
						<input type="password" id="password" name="password">
					</td>
				</tr>
				<tr>
					<td class="lable">验证码：</td>
					<td>
						<div>
							<div class="code_input">
								<input type="text" class="verificationCode" maxlength="4"> 
								<img src="../images/viewImages/status_success.png" class="tip"/>
							</div>
							<div class="code">
								<img src="${kaptchaImageUrl }" id="kaptchaImage" title="看不清，下一张" />
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td class="button" colspan="2">
						<input type="reset" value="重置">
						<input type="button" value="提交" id="login_btn" disabled="disabled"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
<!-- 引入页面脚部文件 -->
<jsp:include page="../baseView/base_foot.jsp" />
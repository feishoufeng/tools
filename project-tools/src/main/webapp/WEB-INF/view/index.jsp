<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>
<t:page title="项目工具平台">

	<div class="row-fluid graphs"></div>
	<div class="row-fluid">


		<div class="col-lg-4">
			<div class="jumbotron"
				style="background-color: rgba(155, 219, 255, 0.78); border-radius: 20px; cursor: pointer"
				onclick="jumpToCodeView()">
				<span style="font-size: 28px;">代码自动生成工具</span>

			</div>
		</div>

		<div class="col-lg-4"></div>

		<div class="col-lg-4"></div>
	</div>
</t:page>
<script type="text/javascript">
	function jumpToCodeView() {
		window.location.href = ctx + "/code/view";
	}
</script>
</html>
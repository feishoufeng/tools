<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<style type="text/css">
.showDeck {
	top: 0px;
	left: 0px;
	margin: 0px;
	padding: 0px;
	width: 100%;
	height: 100%;
	position: absolute;
	z-index: 3;
	background: #DDD;
	opacity: 0.8;
	left: 0px;
	margin: 0px;
	padding: 0px;
	display: none;
}

.showDlg {
	background-color: white;
	height: 80px;
	width: 80px;
	position: absolute;
	z-index: 5;
	border-radius: 6px;
	padding: 4px;
	top: 45%;
	left: 47%;
	margin: 0px;
	display: none;
}
</style>
<div class="showDeck"></div>
<div align="center" class="showDlg">
	<div>
		<img src="../images/viewImages/loading.gif" style="width: 50px;">
	</div>
	<div style="margin-top: 3px;">
		<span style="font-size: 12px; color: #999;font-weight: bold;">加载中...</span>
	</div>
</div>
</body>
</html>

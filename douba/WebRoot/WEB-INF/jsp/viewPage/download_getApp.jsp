<%@page import="java.util.*" language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>豆巴App下载</title>
<style type="text/css">
.download {
	background: #f9c349;
	height: 50px;
	width: 77%;
	border-radius: 25px;
	line-height: 50px;
	font-family: 黑体;
	font-size: 32px;
	bottom: 6%;
	position: absolute;
	margin: 0 12%;
}

.download img {
	width:30px;
	vertical-align: middle;
	margin-right: 20px;
	margin-bottom: 6px;
}

body {
	background-position: top;
	background-size: cover;
	background-image: url(../images/viewImages/download_background.png); 
	background-repeat: no-repeat;
}
</style>
</head>
<body marginheight="0" marginwidth="0">
	<div align="center">
		<div class="download" onclick="download('${url }');">
			<img src="../images/viewImages/download_icon.png" />立即下载
		</div>
	</div>
</body>
<script type="text/javascript">
	function download(url) {
		window.location.href = url;
	}
</script>
</html>
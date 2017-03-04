$(document).ready(function() {
	$.getAjax(
		"../login/getSessionMsg",
		function(data) {
			if (data.errmsg == "" || data.errmsg == null) {
				var flag = data.user.isAdmin;
				var name = data.user.name;
				if(flag == 1){
					$("#welcome").html("管理员：<font style=\"color:blue; cursor: pointer;\">" + name + "</font> 您好！欢迎登录使用！");
				}else{
					$("#welcome").html("用户：<font style=\"color:blue; cursor: pointer;\">" + name + "</font> 您好！欢迎登录使用！");
				}
			} else {
				alert(data.errmsg);
			}
		}
	);
	
	$("#home").click(function(){
		window.parent.document.getElementById("right").src = "../login/right";
		window.parent.document.getElementById("left").src = "../login/left";
	});
	
	$("#exit").click(function(){
		if(confirm("确认退出？")){
			$.getAjax(
				"../login/exit",
				function(data){
					window.parent.location.href = "../login/login_page";
				}
			);
		}
	});
});
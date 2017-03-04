$(document).ready(function() {
	var errmsg = $("#errmsg").val();
	if(errmsg != null && errmsg != ""){
		alert(errmsg);
	}
	
	var url = window.parent.location.href;
	if(url.indexOf("/login/main") != -1){
		window.parent.location.href = "../login/login_page";
	}
	
	// 初始化悬浮气泡提示信息组件
	createPoshytip("userName");
	createPoshytip("password");
	// 初始化验证码组件
	createVerificationCode("kaptchaImage", "../kaptcha/getKaptchaImage");

	// 检测密码
	$("#password").blur(function() {
		$("#password").checkNull("密码不可以为空！");
	});

	// 检测用户名
	$("#userName").blur(function() {
		$("#userName").checkNull("用户名不可以为空！");
	});
	
	// 检测验证码是否为空
	$(".verificationCode").keyup(function() {
		checkVerificationCode();
	});
	
	// 检测验证码是否为空
	$(".verificationCode").blur(function() {
		var verificationCode = $(".verificationCode").val().trim();
		if (verificationCode == null || verificationCode == "") {
			$(".tip").show();
			$(".tip").attr("src","../images/viewImages/status_error.png");
		}
	});
	
	$("#kaptchaImage").click(function(){
		changeVerificationCode("kaptchaImage","../kaptcha/getKaptchaImage");
		$(".verificationCode").val("");
		$(".tip").hide();
		$("#login_btn").attr("disabled","disabled");
	});

	
	$("input:reset").click(function(){
		$(".tip").hide();
	});
	
	// 提交表单
	$("#login_btn").click(function() {
		var username = $("#userName").val();
		var password = $("#password").val();
		var num = 0
		if(username == null || username == ""){
			$("#userName").checkNull("用户名不可以为空！");
			num ++;
		}
		if(password == null || password == ""){
			$("#password").checkNull("密码不可以为空！");
			num ++;
		}
		
		if(num > 0){
			return;
		}
		$("#login_form").submit();
	});
});

// 验证验证码
function checkVerificationCode(){
	var code = $(".verificationCode").val().trim();
	if(code.length < 4){
		return;
	}
	$.ajax({
		url : "../login/checkVerificationCode",
		data : {"verificationCode" : code},
		type : "post",
		dataType : "json",
		success : function(data){
			if(data.flag){
				$(".tip").show();
				$(".tip").attr("src","../images/viewImages/status_success.png");
				$("#login_btn").removeAttr("disabled");
			}else{
				$(".tip").show();
				$(".tip").attr("src","../images/viewImages/status_error.png");
				$("#login_btn").attr("disabled","disabled");
			}
		},
		error : function(data){
			$(".tip").show();
			$(".tip").attr("src","../images/viewImages/status_error.png");
			$("#login_btn").attr("disabled","disabled");
		}
	});
}
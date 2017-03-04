$(document).ready(function(){
	$("#bar").append("用户管理 > "
			+ "<span id=\"user_page\" style=\"cursor: pointer;color:blue;\">用户列表</span>"
			+ " > "
			+ "<font style=\"font-weight:bold;color:blue;\">用户详情</font>");
	
	$("#user_page").click(function(){
		window.parent.document.getElementById("right").src = "../user/page";
	});
	
	var uid = $("#uid").val();
	if(uid > 0){
		$.getAjax(
			"../user/getDetail",
			function(data){
				if(isNull(data.errmsg)){
					var u = data.userInfo;
					var d = data.dataInfo;
					var nums = data.nums;
					$("td:eq(2)").html(safeStr(u.nickname));
					$("td:eq(4)").html(safeStr(u.mobile));
					$("td:eq(6)").html(safeStr(u.username));
					$("td:eq(8)").html(getSex(u.sex));
					$("td:eq(10)").html(safeStr(u.adressDetail));
					$("td:eq(12)").html(safeStr(u.realname));
					$("td:eq(14)").html(safeStr(u.email));
					$("td:eq(16)").html(safeStr(u.graduate));
					$("td:eq(18)").html(safeStr(u.sign));
					$("td:eq(20)").html(safeStr(u.personalIntroduction));
					$("td:eq(27)").html(u.points + "分");
					$("td:eq(29)").html(u.advance.toFixed(2) + "元");
					$("td:eq(31)").html(formatTime(1,u.logintime));
					$("td:eq(33)").html(u.logintimes + "次");
					$("td:eq(35)").html(formatTime(1,u.createTime.time));
					$("td:eq(37)").html(formatTime(1,u.modifyTime.time));
					$("td:eq(40)").html(d.runMiles.toFixed(2) + "公里");
					$("td:eq(42)").html(formatTime(1,d.lastTime));
					$("td:eq(44)").html(d.nums[2].num + "个");
					$("td:eq(46)").html(d.nums[3].num + "个");
					$("td:eq(48)").html(d.nums[0].num + "个");
					$("td:eq(50)").html(d.nums[1].num + "个");
					$("#header").attr("src",u.avatar);
					$("#qrcode").attr("src",u.qrCode);
				}else{
					alert(data.errmsg);
				}
			},
			{"uid":uid}
		);
	}
});
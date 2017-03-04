$(document).ready(function(){
	$("#bar").append("<span>账务管理</span> > "
			+ "<span id=\"account_page\" style=\"cursor: pointer;color:blue;\">账务列表</span>"
			+ " > "
			+ "<font style=\"font-weight:bold;color:blue;\">账务统计</font>");
	
	$("#account_page").click(function(){
		window.parent.document.getElementById("right").src = "../account/page";
	});
	
	var uid = $("#uid").val();
	if(uid > 0){
		$.getAjax(
			"../account/getDetail",
			function(data){
				if(isNull(data.errmsg)){
					var u = data.list;
					
					$("td:eq(2)").html(safeInt(u[4].num) + "笔");
					$("td:eq(4)").html(formatMoney(u[4].money) + "元");
					$("td:eq(6)").html(safeInt(u[2].num) + "个");
					$("td:eq(8)").html(formatMoney(u[2].money) + "元");
					$("td:eq(10)").html(safeInt(u[1].num) + "个");
					$("td:eq(12)").html(formatMoney(u[1].money) + "元");
					var totalGet = u[4].money + u[2].money + u[1].money;
					$("td:eq(14)").html(formatMoney(totalGet) + "元");
					$("td:eq(17)").html(safeInt(u[0].num) + "个");
					$("td:eq(19)").html(formatMoney(u[0].money) + "元");
					$("td:eq(21)").html(safeInt(u[3].num) + "次");
					$("td:eq(23)").html(formatMoney(u[3].money) + "元");
					$("td:eq(25)").html(safeInt(u[5].num) + "次");
					$("td:eq(27)").html(formatMoney(u[5].money) + "元");
					$("td:eq(29)").html(safeInt(u[6].num) + "次");
					$("td:eq(31)").html(formatMoney(u[6].money) + "元");
					var totalPay = u[0].money + u[3].money + u[5].money + u[6].money;
					$("td:eq(33)").html(formatMoney(totalPay) + "元");
					$("td:eq(36)").html(formatMoney(u[7].money) + "元");
					$("td:eq(38)").html(formatMoney(u[8].money) + "元");
				}else{
					alert(data.errmsg);
				}
			},
			{"uid":uid}
		);
	}
});
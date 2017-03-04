var page = 1;
var pagesize = 20;
var total_page = 1;
var count = 0;
var date1 = "";
var date2 = "";
var sqlWhere = "";

$(document).ready(function(){
	$("#bar").append("<span>账务管理</span> > "
			+ "<span id=\"account_page\" style=\"cursor: pointer;color:blue;\">账务列表</span>"
			+ " > "
			+ "<font style=\"font-weight:bold;color:blue;\">流水列表</font>");
	
	$("#account_page").click(function(){
		window.parent.document.getElementById("right").src = "../account/page";
	});
	
	getData(page, pagesize);
	//初始化所需插件
	initplug("export,page");
});

function getData(page, pagesize){
	var uid = $("#uid").val();
	if(uid > 0){
		$.getAjax(
			"../account/getWater",
			function(data) {
				if (isNull(data.errmsg)) {
					//删除旧的初始化导出数据
					removeExport();
					var appendStr = "";
					if (data.list.length <= 0) {
						appendStr += "<tr alt=\"0\" style=\"text-align:center;\"> "
								+ " <td colspan=\"8\"><font color=\"red\">当前没有数据！</font></td> "
								+ "</tr>";
					} else {
						for (var i = 0; i < data.list.length; i++) {
							var u = data.list[i];
							var status = "";
							var con = "";
							var type = "";
							if(u.status == 1){
								status = "<font style=\"color:blue;\">正常</font>";
							}else{
								status = "<font style=\"color:red;\">禁用</font>";
							}
							if(u.type == 1){
								type = "收入";
							}else if(u.type == -1){
								type = "支出";
							}else{
								type = "--";
							}
							
							appendStr += "<tr alt=\""+i+"\"> "
									   + " <td>" + (i + 1) + "</td> "
									   + " <td><span style=\"color:blue;\">" + u.mobile + "</span></td>"
									   + " <td style=\"text-align:left;\" title=\"" + safeStr(u.nickname) + "\">" + safeStr(u.nickname) + "</td> "
									   + " <td>" + type + "</td> "
									   + " <td>" + safeStr(u.action) + "</td> "
									   + " <td style=\"text-align:right;\" title=\"" + formatMoney(u.money) + "\">" + formatMoney(u.money) + "</td> "
									   + " <td style=\"text-align:right;\" title=\"" + formatMoney(u.leftmoney) + "\">" + formatMoney(u.leftmoney) + "</td> "
									   + " <td>" + u.dateStr + "</td> "
									+ " </tr> ";
						}
					}
					//table更新数据
					refreshTable("water_list",appendStr);
					//刷新插件获取的数据
					refreshData("export,page",data,"water_list");
				} else {
					alert(data.errmsg);
				}
			},
			{"page" : page - 1,"pagesize" : pagesize,"date1" : date1,"date2" : date2,"sqlWhere" : sqlWhere,"uid" : uid}
		);
	}
}
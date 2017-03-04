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
			+ "<font style=\"font-weight:bold;color:blue;\">红包列表</font>");
	
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
			"../account/getRedPacket",
			function(data) {
				if (isNull(data.errmsg)) {
					//删除旧的初始化导出数据
					removeExport();
					var appendStr = "";
					if (data.list.length <= 0) {
						appendStr += "<tr alt=\"0\" style=\"text-align:center;\"> "
								+ " <td colspan=\"10\"><font color=\"red\">当前没有数据！</font></td> "
								+ "</tr>";
					} else {
						for (var i = 0; i < data.list.length; i++) {
							var u = data.list[i];
							var status = "";
							var type = "";
							var returnStatus = "";
							
							switch(u.status){
							case -1:
								status = "<font style=\"color:gray;\">过期</font>";
								break;
							case 0:
								status = "<font style=\"color:red;\">异常</font>";
								break;
							case 1:
								status = "<font style=\"color:green;\">正常</font>";
								break;
							case 2:
								status = "已领完";
								break;
							}
							
							switch(u.type){
							case "common":
								type = "普通红包";
								break;
							case "group":
								type = "群红包";
								break;
							}
							
							switch(u.returnStatus){
							case -1:
								returnStatus = "未退还";
								break;
							case 0:
								returnStatus = "无";
								break;
							case 1:
								returnStatus = "已退还";
								break;
							}
							
							appendStr += "<tr alt=\""+i+"\"> "
									   + " <td>" + (i + 1) + "</td> "
									   + " <td title=\"" + u.code + "\">"+ u.code +"</td>"
									   + " <td><span style=\"color:blue;\">" + u.mobile + "</span></td>"
									   + " <td>" + safeStr(status) + "</td> "
									   + " <td>" + safeStr(type) + "</td> "
									   + " <td style=\"text-align:right;\" title=\"" + safeStr(u.groupname) + "\">" + safeStr(u.groupname) + "</td> "
									   + " <td style=\"text-align:right;\" title=\"" + formatMoney(u.money) + "\">" + formatMoney(u.money) + "</td> "
									   + " <td style=\"text-align:right;\" title=\"" + formatMoney(u.leftmoney) + "\">" + formatMoney(u.leftmoney) + "</td> "
									   + " <td>" + safeStr(returnStatus) + "</td> "
									   + " <td>" + u.dateStr + "</td> "
									+ " </tr> ";
						}
					}
					//table更新数据
					refreshTable("redPacket_list",appendStr);
					//刷新插件获取的数据
					refreshData("export,page",data,"redPacket_list");
				} else {
					alert(data.errmsg);
				}
			},
			{"page" : page - 1,"pagesize" : pagesize,"date1" : date1,"date2" : date2,"sqlWhere" : sqlWhere,"uid" : uid}
		);
	}
}
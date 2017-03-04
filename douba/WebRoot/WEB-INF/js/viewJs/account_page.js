var page = 1;
var pagesize = 20;
var total_page = 1;
var count = 0;
var date1 = "";
var date2 = "";
var sqlWhere = "";

$(document).ready(function(){
	$("#bar").append("<span>账务管理</span> > <font style=\"font-weight:bold;color:blue;\">账务列表</font>");
	
	getData(page, pagesize);
	//初始化所需插件
	initplug("export,page");
});

function getData(page, pagesize){
	$.getAjax(
		"../user/getUser",
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
						var con = "<font color=\"blue\" style=\"cursor:pointer;\" onclick=\"water(" + u.uid + ");\">流水</font>"
								+ "<font color=\"red\" style=\"cursor:pointer;margin-left:5px;\" onclick=\"redPacket(" + u.uid + ");\">红包</font>";
						if(u.status == 1){
							status = "<font style=\"color:blue;\">正常</font>";
						}else{
							status = "<font style=\"color:red;\">禁用</font>";
						}
						appendStr += "<tr alt=\""+i+"\"> "
								   + " <td>" + (i + 1) + "</td> "
								   + " <td><span style=\"cursor:pointer;color:blue;\" onclick=\"detail("+u.uid+")\">" + u.mobile + "</span></td>"
								   + " <td>" + status + "</td> "
								   + " <td style=\"text-align:left;\" title=\"" + safeStr(u.nickname) + "\">" + safeStr(u.nickname) + "</td> "
								   + " <td style=\"text-align:right;\" title=\"" + formatMoney(u.money) + "\">" + formatMoney(u.money) + "</td> "
								   + " <td style=\"text-align:right;\" title=\"" + formatMoney(u.nocashmoney) + "\">" + formatMoney(u.nocashmoney) + "</td> "
								   + " <td>" + u.dateStr + "</td> "
								   + " <td align=\"center\" style=\"font-size:12px;\">"+ con +"</td> "
								+ " </tr> ";
					}
				}
				//table更新数据
				refreshTable("account_list",appendStr);
				//刷新插件获取的数据
				refreshData("export,page",data,"account_list");
			} else {
				alert(data.errmsg);
			}
		},
		{"page" : page - 1,"pagesize" : pagesize,"date1" : date1,"date2" : date2,"sqlWhere" : sqlWhere}
	);
}

function detail(uid){
	if(uid > 0){
		window.location.href = "../account/detail?uid=" + uid; 
	}
}

function water(uid){
	if(uid > 0){
		window.location.href = "../account/water?uid=" + uid;
	}
}

function redPacket(uid){
	if(uid > 0){
		window.location.href = "../account/redPacket?uid=" + uid;
	}
}
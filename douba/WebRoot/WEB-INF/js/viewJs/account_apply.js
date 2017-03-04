var page = 1;
var pagesize = 20;
var total_page = 1;
var count = 0;
var date1 = "";
var date2 = "";
var sqlWhere = "";

$(document).ready(function(){
	$("#bar").append("<span>账务管理</span> > <font style=\"font-weight:bold;color:blue;\">提现申请</font>");
	
	getData(page, pagesize);
	//初始化所需插件
	initplug("export,page");
});

function getData(page, pagesize){
	$.getAjax(
		"../account/getApply",
		function(data) {
			if (isNull(data.errmsg)) {
				//删除旧的初始化导出数据
				removeExport();
				var appendStr = "";
				if (data.list.length <= 0) {
					appendStr += "<tr alt=\"0\" style=\"text-align:center;\"> "
							+ " <td colspan=\"7\"><font color=\"red\">当前没有数据！</font></td> "
							+ "</tr>";
				} else {
					for (var i = 0; i < data.list.length; i++) {
						var u = data.list[i];
						var status = "";
						var con = "";
						
						switch(u.status){
						case 0:
							status = "<font style=\"color:blue;\">待审核</font>";
							con = "<font color=\"blue\" style=\"cursor:pointer;\" onclick=\"change(" + u.id + ",'0');\">审核</font>";
							break;
						case 1:
							status = "<font style=\"color:blue;\">已完成</font>";
							con = "<font color=\"gray\">审核</font>";
							break;
						case -1:
							status = "<font style=\"color:blue;\">审核失败</font>";
							con = "<font color=\"red\" style=\"cursor:pointer;\" onclick=\"change(" + u.uid + ",'0');\">重新审核</font>";
							break;
						case -2:
							status = "<font style=\"color:blue;\">申请异常</font>";
							con = "<font color=\"red\"\">审核</font>";
							break;
						}
						
						money = parseFloat(u.money) / 100;
						appendStr += "<tr alt=\""+i+"\"> "
								   + " <td>" + (i + 1) + "</td> "
								   + " <td>" + u.mobile + "</td>"
								   + " <td>" + status + "</td> "
								   + " <td style=\"text-align:right;\">" + money.toFixed(2) + "元</td> "
								   + " <td style=\"text-align:left;\" title=\"" + safeStr(u.remark) + "\">" + safeStr(u.remark) + "</td> "
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

function change(uid,status){
	if(uid > 0){
		var tip = "";
		if(status == 1){
			tip = "确认开启账户？";
		}else{
			tip = "确认禁用账户？";
		}
		if(confirm(tip)){
			$.getAjax(
					"../user/changeStatus",
					function(data){
						if(isNull(data.errmsg)){
							getData(page, pagesize);
						}else{
							alert(data.errmsg);
						}
					},
					{"uid":uid,"status":status}
				);
		}
	}
}
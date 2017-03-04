var page = 1;
var pagesize = 20;
var total_page = 1;
var count = 0;
var date1 = "";
var date2 = "";
var sqlWhere = "";

$(document).ready(function(){
	$("#bar").append("<span>用户管理</span> > <font style=\"font-weight:bold;color:blue;\">用户列表</font>");
	
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
							+ " <td colspan=\"9\"><font color=\"red\">当前没有数据！</font></td> "
							+ "</tr>";
				} else {
					for (var i = 0; i < data.list.length; i++) {
						var u = data.list[i];
						var status = "";
						var con = "";
						if(u.status == 1){
							status = "<font style=\"color:blue;\">正常</font>";
							con = "<font color=\"red\" style=\"cursor:pointer;\" onclick=\"change(" + u.uid + ",'0');\">禁用</font>";
						}else{
							status = "<font style=\"color:red;\">禁用</font>";
							con = "<font color=\"blue\" style=\"cursor:pointer;\" onclick=\"change(" + u.uid + ",'1');\">开启</font>";
						}
						var createGroup = "<font style=\"color:blue;cursor:pointer;\" onclick=\"createGroupRight("+u.uid+",'1');\">无</font>";
						var createGroupTip = "开启创群权利";
						if(u.createGroup == 1){
							createGroup = "<font style=\"color:green;cursor:pointer;\" onclick=\"createGroupRight("+u.uid+",'0');\">有</font>";
							createGroupTip = "取消创群权利";
						}
						appendStr += "<tr alt=\""+i+"\"> "
								   + " <td>" + (i + 1) + "</td> "
								   + " <td><span style=\"cursor:pointer;color:blue;\" onclick=\"detail("+u.uid+")\">" + u.mobile + "</span></td>"
								   + " <td>" + status + "</td> "
								   + " <td style=\"text-align:left;\" title=\"" + u.nickname + "\">" + u.nickname + "</td> "
								   + " <td style=\"text-align:left;\" title=\"" + safeStr(u.adressDetail) + "\">" + safeStr(u.adressDetail) + "</td> "
								   + " <td style=\"text-align:left;\" title=\"" + safeStr(u.graduate) + "\">" + safeStr(u.graduate) + "</td> "
								   + " <td title=\"" + createGroupTip + "\" >" + createGroup + "</td> "
								   + " <td>" + u.dateStr + "</td> "
								   + " <td align=\"center\" style=\"font-size:12px;\">"+ con +"</td> "
								+ " </tr> ";
					}
				}
				//table更新数据
				refreshTable("user_list",appendStr);
				//刷新插件获取的数据
				refreshData("export,page",data,"user_list");
			} else {
				alert(data.errmsg);
			}
		},
		{"page" : page - 1,"pagesize" : pagesize,"date1" : date1,"date2" : date2,"sqlWhere" : sqlWhere}
	);
}

function detail(uid){
	if(uid > 0){
		window.location.href = "../user/detail?uid=" + uid; 
	}
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

function createGroupRight(uid,status){
	if(uid > 0){
		var tip = "";
		if(status == 1){
			tip = "确认开启创群权利？";
		}else{
			tip = "确认关闭创群权利？";
		}
		if(confirm(tip)){
			$.getAjax(
					"../user/createGroupRight",
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
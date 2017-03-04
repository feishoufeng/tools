var page = 1;
var pagesize = 20;
var total_page = 1;
var count = 0;
var date1 = "";
var date2 = "";
var sqlWhere = "";

$(document).ready(function(){
	$("#bar").append("<span>群管理</span> > <font style=\"font-weight:bold;color:blue;\">大群列表</font>");
	
	getData(page, pagesize);
	//初始化所需插件
	initplug("export,page");
});

function getData(page, pagesize){
	$.getAjax(
		"../group/list",
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
							status = "<span style=\"color:blue;\">正常</span>";
							con = "<span style=\"color:red;cursor:pointer;\" onclick=\"change("+u.gid+",-2)\">禁用</span>";
						}else{
							status = "<span style=\"color:red;\">禁用</span>";
							con = "<span style=\"color:blue;cursor:pointer;\" onclick=\"change("+u.gid+",1)\">开启</span>";
						}
						appendStr += "<tr alt=\""+i+"\"> "
								   + " <td>" + (i + 1) + "</td> "
								   + " <td style=\"text-align:left;cursor:pointer;color:blue;\" title=\"" + safeStr(u.name) + "\" onclick=\"getChildren("+u.gid+")\">" + safeStr(u.name) + "</td>"
								   + " <td>" + u.mobile + "</td> "
								   + " <td>" + safeStr(u.typeName) + "</td> "
								   + " <td>" + status + "</td> "
								   + " <td>" + u.number + "</td> "
								   + " <td style=\"text-align:left;\" title=\"" + safeStr(u.condition) + "\">"+ safeStr(u.condition) +"</td> "
								   + " <td>" + safeStr(u.dateStr) + "</td> "
								   + " <td style=\"font-size:12px;\">" + con + "</td> "
								+ " </tr> ";
					}
				}
				//table更新数据
				refreshTable("group_list",appendStr);
				//刷新插件获取的数据
				refreshData("export,page",data,"group_list");
			} else {
				alert(data.errmsg);
			}
		},
		{"page" : page - 1,"pagesize" : pagesize,"date1" : date1,"date2" : date2,"sqlWhere" : sqlWhere}
	);
}

function change(gid,status){
	var tip = "";
	switch(status){
	case -2:
		tip = "确认禁用大群？";
		break;
	case 1:
		tip = "确认开启大群？";
		break;
	}
	if(confirm(tip)){
		$.getAjax(
			"../group/changeStatus",
			function(data){
				if(isNull(data.errmsg)){
					getData(page, pagesize);
				}else{
					alert(data.errmsg);
				}
			},
			{"gid":gid,"status":status}
		);
	}
}

function getChildren(gid){
	if(gid > 0){
		window.location.href = "../group/getChildren?gid=" + gid;
	}
}
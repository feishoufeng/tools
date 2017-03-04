var page = 1;
var pagesize = 20;
var total_page = 1;
var count = 0;
var date1 = "";
var date2 = "";
var sqlWhere = "";

$(document).ready(function(){
	$("#bar").append("<span>群管理</span> > "
			+ "<span id=\"group_page\" style=\"cursor: pointer;color:blue;\">大群列表</span>"
			+ " > "
			+ "<font style=\"font-weight:bold;color:blue;\">子群列表</font>");
	
	$("#group_page").click(function(){
		window.parent.document.getElementById("right").src = "../group/page";
	});
	
	getData(page, pagesize);
	//初始化所需插件
	initplug("export,page");
});

function getData(page, pagesize){
	var gid = $("#gid").val();
	if(gid > 0){
		$.getAjax(
			"../group/childList",
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
							var groupType = "普通群";
							if(u.status == 1){
								status = "<span style=\"color:blue;\">正常</span>";
								con = "<span style=\"color:red;cursor:pointer;\" onclick=\"change("+u.gid+",-2)\">禁用</span>";
							}else{
								status = "<span style=\"color:red;\">禁用</span>";
								con = "<span style=\"color:blue;cursor:pointer;\" onclick=\"change("+u.gid+",1)\">开启</span>";
							}
							if(u.ismain == 1){
								groupType = "主群";
							}
							if(u.ismain == 0 &&　u.istemp == 1){
								groupType = "预备群";
							}
							
							appendStr += "<tr alt=\""+i+"\"> "
									   + " <td>" + (i + 1) + "</td> "
									   + " <td style=\"text-align:left;\" title=\"" + safeStr(u.name) + "\">" + safeStr(u.name) + "</td>"
									   + " <td>" + u.mobile + "</td> "
									   + " <td>" + safeStr(groupType) + "</td> "
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
			{"page" : page - 1,"pagesize" : pagesize,"date1" : date1,"date2" : date2,"sqlWhere" : sqlWhere,"gid":gid}
		);
	}else{
		alert("gid错误！");
	}
}

function change(gid,status){
	var tip = "";
	switch(status){
	case -2:
		tip = "确认禁用群？";
		break;
	case 1:
		tip = "确认开启群？";
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
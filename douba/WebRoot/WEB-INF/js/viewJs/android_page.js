var page = 1;
var pagesize = 20;
var total_page = 1;
var count = 0;
var date1 = "";
var date2 = "";
var sqlWhere = "";

$(document).ready(function(){
	$("#bar").append("<span>App管理</span> > <font style=\"font-weight:bold;color:blue;\">Android</font>");
	
	$(".add").click(function(){
		window.location.href = "../application/android_editor";
	});
	
	getData(page, pagesize);
	//初始化所需插件
	initplug("export,page");
});

function getData(page, pagesize){
	$.getAjax(
		"../application/getAndroid",
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
						var forceupdate = "";
						if(u.forceupdate == 1){
							forceupdate = "强制";
						}else{
							forceupdate = "非强制";
						}
						appendStr += "<tr alt=\""+i+"\"> "
								   + " <td>" + (i + 1) + "</td> "
								   + " <td>" + u.filename + "</td>"
								   + " <td>" + u.platform + "</td> "
								   + " <td>" + safeStr(u.version) + "</td> "
								   + " <td style=\"text-align:left;\" title=\"" + removeRemark(safeStr(u.content)) + "\">" + removeRemark(safeStr(u.content)) + "</td> "
								   + " <td style=\"text-align:right;\">" + safeStr(forceupdate) + "</td> "
								   + " <td style=\"text-align:right;\" title=\"" + safeStr(u.name) + "\">"+ safeStr(u.name) +"</td> "
								   + " <td>" + safeStr(u.dateStr) + "</td> "
								+ " </tr> ";
					}
				}
				//table更新数据
				refreshTable("android_list",appendStr);
				//刷新插件获取的数据
				refreshData("export,page",data,"android_list");
			} else {
				alert(data.errmsg);
			}
		},
		{"page" : page - 1,"pagesize" : pagesize,"date1" : date1,"date2" : date2,"sqlWhere" : sqlWhere}
	);
}

function removeRemark(dd) {
	dd = dd.replace(/<\/?.+?>/g, "");
	var dds = dd.replace(/&nbsp;/g, "");// dds为得到后的内容
	return (dds);
}

function detail(id){
	if(uid > 0){
		window.location.href = "../application/appDetail?id=" + id; 
	}
}
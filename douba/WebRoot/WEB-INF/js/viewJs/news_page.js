var page = 1;
var pagesize = 20;
var total_page = 1;
var count = 0;
$(document).ready(function() {
	$("#bar").append("公告管理 > "
			+ "<font style=\"font-weight:bold;color:blue;\">公告列表</font>");
	getData(page, pagesize);
	//初始化所需插件
	initplug("page");

	$("#add").click(function() {
		window.location.href = "../news/getEditor";
	});
});

// 发送公告逻辑
function send(id) {
	if (id > 0) {
		$.getAjax(
			"../news/changeStatus",
			function(data) {
				if (isNull(data.errmsg)) {
					alert("发送成功！");
					getData(page, pagesize);
				} else {
					alert(data.errmsg);
				}
			},
			{"id" : id}
		);
	}
}

function del(id) {
	if (!confirm("确认删除")) {
		return;
	}

	$.getAjax(
		"../news/delete", 
		function(data) {
			if (isNull(data.errmsg)) {
				alert("删除成功！");
				getData(page, pagesize);
			} else {
				alert(data.errmsg);
			}
		},
		{"id" : id}
	);
}

function edit(id) {
	window.location.href = "../news/getEditor?id=" + id;
}

function detail(id){
	window.location.href = "../news/detail?id=" + id;
}

function getData(page, pagesize) {
	$.getAjax(
		"../news/getNews",
		function(data) {
			if (isNull(data.errmsg)) {
				var appendStr = "";
				if (data.list.length <= 0) {
					appendStr += "<tr alt=\"0\" style=\"text-align:center;\"> "
							+ " <td colspan=\"8\"><font color=\"red\">当前没有数据！</font></td> "
							+ "</tr>";
				} else {
					for (var i = 0; i < data.list.length; i++) {
						var u = data.list[i];
						var statusStr = "";
						switch (u.status) {
						case -1:
							statusStr = "<font color=\"red\">已删除</font>";
							break;
						case 0:
							statusStr = "<font color=\"blue\" onclick=\"send("
									+ u.id
									+ ");\" title=\"点击发送公告\" style=\"cursor:pointer;\">待发送</font>";
							break;
						case 1:
							statusStr = "<font color=\"green\">已发送</font>";
							break;
						}
	
						appendStr += "<tr alt=\""+i+"\">"
								   + " <td>" + (i + 1) + "</td> "
								   + " <td style=\"text-align:left;\">"
								   	+ " <span style=\"color:blue;cursor:pointer;\" title=\"" + u.title + "\" onclick=\"detail("+u.id+");\">" + u.title + "</span>"
								   + " </td> "
								   + " <td>" + safeStr(u.typeName) + "</td> "
								   + " <td style=\"text-align:left;\" title=\"" + safeStr(u.adress) + "\">" + safeStr(u.adress) + "</td> "
								   + " <td>" + u.readCnt + "</td> "
								   + " <td style=\"font-size:12px;\">" + statusStr + "</td> "
								   + " <td>" + u.dateStr + "</td> "
								   + " <td align=\"center\" style=\"font-size:12px;\">" 
								   		+ " <font color=\"blue\" style=\"cursor:pointer;\" onclick=\"edit(" + u.id + ");\">修改</font>"
								   		+ " <font color=\"red\" style=\"cursor:pointer;margin-left:5px;\" onclick=\"del(" + u.id + ");\">删除</font>" 
								   + "</td> "
								+ " </tr> ";
					}
				}
				//table更新数据
				refreshTable("appNews_list",appendStr);
				//刷新插件获取的数据
				refreshData("page",data);
			} else {
				alert(data.errmsg);
			}
		},
		{"page" : page - 1,"pagesize" : pagesize}
	);
}

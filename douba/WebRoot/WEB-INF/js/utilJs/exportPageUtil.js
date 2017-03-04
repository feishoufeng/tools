function exportFile(type) {
	$("button[data-fileblob]:eq(" + type + ")").click();
	exportHide();
}

function exportShow() {
	$(".exportDeck").show();
	$(".exportDlg").show();
}

function exportHide() {
	$(".exportDeck").hide();
	$(".exportDlg").hide();
}

function initExport(){
	$(".clear img").click(function() {
		var str = $("#date-range0").val();
		if (str != "" && str != "选择数据时间" && str != "undefined") {
			$("#date-range0").val("选择数据时间");
			$("#date-range0").css("color", "#888");
			date1 = "";
			date2 = "";
			getData(page, pagesize);
		}
	});
	
	$(".exportSelect select").change(
		function() {
			var title = $(".exportSelect select")
					.find("option:selected").attr("title");
			var titles = isNull(title) ? "" : title.split(",");
			if(titles.length == 3){
				var index = titles[2];
				$(".condition").parent().hide();
				$("div[title=select]").hide();
				$("#select" + index).show();
			}else{
				$(".condition").parent().show();
				$("div[title=select]").hide();
			}
		}
	);

	$(".confirm").click(
		function() {
			var title = $(".exportSelect select")
					.find("option:selected").attr("title");
			var selectVal = $(".exportSelect select").val();
			var condition = "";
			var titles = isNull(title) ? "" : title.split(",");
			if (titles.length == 3) {
				var index = titles[2];
				condition = $("#select"+index+" select").val().trim();
			} else {
				condition = $(".condition").val().trim();
			}
			if (!isNull(selectVal) && !isNull(condition)) {
				if (titles.length == 3) {
					sqlWhere = " and " + selectVal + " = " + condition;
				} else {
					if (condition != "请输入筛选条件") {
						sqlWhere = " and " + selectVal + " like '%"
								+ condition + "%' ";
					} else {
						sqlWhere = "";
					}
				}
			} else {
				$(".condition").val("");
				sqlWhere = "";
			}
			getData(page, pagesize);
		}
	);
	
	$(".export").click(function() {
		// exportShow();
		exportFile(0);
	});
}

function exportData(idName) {
	$("table[id="+ idName + "]").tableExport({
		formats : [ "xlsx", "xls", "csv", "txt" ]
	});
}

function removeExport() {
	$("caption").remove();
}

function searchByDate() {
	var dateRang = $("#date-range0").val();
	if (dateRang == "" || dateRang == "选择数据时间") {
		$("#date-range0").css("color", "#888");
	} else {
		$("#date-range0").css("color", "black");
	}
	if (dateRang.length > 10) {
		date1 = dateRang.split("~")[0].trim() + " 00:00:00";
		date2 = dateRang.split("~")[1].trim() + " 23:59:59";
	}else{
		date1 = "";
		data2 = "";
	}
	
	getData(page, pagesize);
}
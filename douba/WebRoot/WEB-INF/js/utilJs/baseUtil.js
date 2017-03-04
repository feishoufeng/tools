/**
 * 获取ajax
 * 
 * @param url
 *            跳转地址
 * @param data
 *            代入参数
 * @param succ
 *            返回成功处理函数
 * @param fail
 *            返回失败处理函数
 * @param type
 *            请求方式
 * @param dataType
 *            返回值类型
 * @param sync
 *            是否异步请求
 */
$(function() {
	jQuery.getAjax = function(url, succ, data, fail, type, dataType, sync) {
		showLoad();
		$.ajax({
			type : "undefined" == typeof (type) ? "post" : type,
			sync : "undefined" == typeof (sync) ? true : sync,
			url : url,
			data : "undefined" == typeof (data) ? {} : data,
			dataType : "undefined" == typeof (dataType) ? "json" : dataType,
			success : function(d) {
				hideLoad();
				succ(d);
			},
			error : function(e) {
				hideLoad();
				"undefined" == typeof (fail) ? alert("系统发生错误，请联系管理员！")
						: fail(e);
			}
		});
	};
});

// 检测字段为空
(function($) {
	$.fn.checkNull = function(context) {
		var value = $(this).val().trim();
		if (value == null || value == "") {
			// 显示悬浮气泡
			tipShow($(this).attr("id"), context);
			return false;
		} else {
			// 隐藏悬浮气泡
			tipHide($(this).attr("id"));
			return true;
		}
	};
})(jQuery);

function ReportFileStatus(filespec) {
	var fso, s = false;
	fso = new ActiveXObject("Scripting.FileSystemObject");
	if (fso.FileExists(filespec)) {
		s = true;
	}
	return (s);
}

function isNull(str) {
	if(str != null && str != ""){
		return (false);
	}else{
		return (true);
	}
}

function safeStr(str){
	if(str != null && str != "" && typeof(str) != "undefined"){
		return (str);
	}else{
		return ("--");
	}
}

function safeInt(str){
	if(str != null && str != "" && typeof(str) != "undefined"){
		return (parseInt(str));
	}else{
		return (0);
	}
}

function getSex(sex){
	if(sex == -1){
		return ("--");
	}
	if(sex == 0){
		return ("男");
	}
	if(sex == 1){
		return ("女");
	}
}

Date.prototype.format = function (fmt) { // author: meizz
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "H+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds() // 毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function formatTime(type,date){
	var formatStr = "";
	switch(type){
		case 1:
			formatStr = "yyyy-MM-dd HH:mm:ss";
			break;
	}
	if(date == null || date == "" ||  typeof(date) == "undefined" || date == 0){
		return(new Date().format(formatStr));
	}else{
		return(new Date(date).format(formatStr));
	}
}

function formatMoney(money){
	var returnVal = 0;
	if(money != null && money != "" && typeof(money) != "undefined"){
		returnVal = parseFloat(money) / 100.00;
	}
	return (returnVal.toFixed(2));
}

function showLoad(){
	$(".showDeck").css("display","block");
	$(".showDlg").css("display","block");
}

function hideLoad(){
	$(".showDeck").css("display","none");
	$(".showDlg").css("display","none");
}

/**
 * 初始化导出数据插件和分页插件
 * @param types 插件类型
 * @returns
 */
function initplug(types){
	if(!isNull(types)){
		var array = types.split(",");
		for(var i = 0;i < array.length;i++){
			switch(array[i]){
			//初始化导出数据工具插件
			case "export":
				//初始化导出数据插件
				initExport();
				break;
			//初始化分页工具插件
			case "page":
				//初始化分页插件
				initPage();
				break;
			}
		}
	}
}

/**
 * 刷新导出数据和分页数据
 * @param types 工具类型
 * @param data 响应的数据
 * @param tableId 需要导出数据的tableid
 * @returns
 */
function refreshData(types,data,tableId){
	if(!isNull(types)){
		var array = types.split(",");
		for(var i = 0;i < array.length;i++){
			switch(array[i]){
			//初始化导出数据工具插件
			case "export":
				//初始化导出数据
				exportData(tableId);
				break;
			//初始化分页工具插件
			case "page":
				//刷新分页数据
				pageData(data);
				break;
			}
		}
	}
}

/**
 * table数据更新
 * @param appendStr
 * @returns
 */
function refreshTable(tableId,appendStr){
	// 将数据指控，防止重复
	$("#"+tableId+" tr[alt]").remove();
	$("#"+tableId+"").append(appendStr);
}

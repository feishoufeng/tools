var pro_code = 0;
var city_code = 0;
var area_code = 0;
var type_id = 0;

$(document).ready(function() {
	$("#bar").append("App管理 > "
			+ "<span id=\"android_page\" style=\"cursor: pointer;color:blue;\">Android</span>"
			+ " > "
			+ "<font style=\"font-weight:bold;color:blue;\">新增App</font>");
	
	$("#android_page").click(function(){
		window.parent.document.getElementById("right").src = "../application/android";
	});
	
	// 初始化悬浮气泡提示信息组件
	createPoshytip("title");
	// 检测密码
	$("#title").blur(function() {
		$("#title").checkNull("标题不可以为空！");
	});
	
	$('#ssi-upload').ssi_uploader({
		url:"../application/upload",
		maxFileSize:50,
		maxNumberOfFiles:1,
		allowed:["apk"],
		locale:"zh",
		onEachUpload:function(fileInfo){
			if(fileInfo.uploadStatus == "success"){
				window.location.href = "../application/android";
			}else{
				alert(fileInfo.title);
			}
		}
	});
	
	
	$("#txtEditor").Editor();
	$("#menuBarDiv .btn-group:eq(6)").hide();
	$("#menuBarDiv .btn-group:eq(7)").hide();
	$("#menuBarDiv .btn-group:eq(9)").hide();
	$("#menuBarDiv .btn-group:eq(8)").hide();
	$("#menuBarDiv .btn-group:eq(10)").hide();
	$("#menuBarDiv .btn-group:eq(11)").hide();
	$(".Editor-editor").css("height","240px");
	$(".Editor-editor").css("background-color","white");
	$("#menuBarDiv").attr("align","left");
});

function beforeUpload(){
	content = $("#txtEditor").Editor("getText");
	if(isNull(content)){
		$(".Editor-editor").css("border","red 1px solid");
		alert("更新内容不可以为空！");
		return (false);
	}else{
		$(".Editor-editor").css("border","#EEEEEE 1px solid");
		return (true);
	}
}

function extraData(formData){
	var content = $("#txtEditor").Editor("getText");
	formData.append("content", content);
	return (formData);
}

function clear(){
	$(".Editor-editor").html("");
	$("#statusbar div:eq(0)").html("Words : 0");
	$("#statusbar div:eq(1)").html("Characters : 0");
}

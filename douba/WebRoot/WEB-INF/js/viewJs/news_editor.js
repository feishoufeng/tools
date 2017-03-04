var pro_code = 0;
var city_code = 0;
var area_code = 0;
var type_id = 0;

$(document).ready(function() {
	$("#bar").append("公告管理 > "
			+ "<span id=\"news_page\" style=\"cursor: pointer;color:blue;\">公告列表</span>"
			+ " > "
			+ "<font style=\"font-weight:bold;color:blue;\">公告编辑</font>");
	
	$("#txtEditor").Editor();
	
	$("#news_page").click(function(){
		window.parent.document.getElementById("right").src = "../news/page";
	});
	
	// 初始化悬浮气泡提示信息组件
	createPoshytip("title");
	// 检测密码
	$("#title").blur(function() {
		$("#title").checkNull("标题不可以为空！");
	});
	
	var id = $("#id").val();
	if(id > 0){
		$.getAjax(
			"../news/getNewsDetail",	
			function(data){
				if(isNull(data.errmsg)){
					var newsDetail = data.newsDetail;
					if(newsDetail != null){
						$("#title").val(newsDetail.title);
						$("#txtEditor").data("editor").html(newsDetail.content);
						type_id = newsDetail.type;
						area_code = newsDetail.cityid;
						var area_code_str = area_code + "";
						city_code = parseInt(area_code_str.substring(0,4) + "00");
						pro_code = parseInt(area_code_str.substring(0,2) + "0000");
						init();
					} 
				}else{
					alert(data.errmsg);
				}
			},
			{"id":id}
		);
	}else{
		init();
	}
	
	
});

function init(){
	//初始化省份
	getArea(0,0);
	//初始化变更触发事件
	initAreaChange();
	//初始化类型选择
	initType();
	//初始化类型变更触发事件
	$("#type").change(function(){
		type_id = $("#type").val();
	});
	
	if(pro_code > 0){
		//初始化市
		getArea(1,pro_code);
	}
	if(city_code > 0){
		//初始化区
		getArea(2,city_code);
	}
}

function save(){
	var n = 0;
	var cityid = 0;
	var title = $("#title").val();
	var content = $("#txtEditor").Editor("getText");
	if(title == null || title == ""){
		$("#title").checkNull("标题不可以为空！");
		n ++;
	}
	if(content == null || content == ""){
		$(".Editor-editor").css("border","red 1px solid");
		n ++;
	}
	if(n > 0){
		return;
	}
	
	if(area_code > 0){
		cityid = area_code;
	}else if(city_code > 0){
		cityid = city_code;
	}else if(pro_code > 0){
		cityid = pro_code;
	}else{
		cityid = 0;
	}
	
	//获取文档中的图片src
	var imgDatas = "";
	var imgStr = $(".Editor-editor img");
	for(var i = 0;i < imgStr.length; i++){
		var imgSrc = imgStr[i];
		imgDatas += imgSrc.src + "》";
	}
	
	var id = $("#id").val();
	//处理公告
	$.getAjax(
		"../news/editor",
		function(data){
			if(isNull(data.errmsg)){
				alert("保存成功！");
				window.location.href = "../login/right";
			}else{
				alert(data.errmsg);
			}
		},
		{"type":type_id,"cityid":cityid,"title":title,"content":content,"imgDatas":imgDatas,"id":id}
	);
}

function reset(){
	$("#type").get(0).options[0].selected = true;
	$("#province").get(0).options[0].selected = true;
	$("#title").val("");
	$(".Editor-editor").html("");
	$("#statusbar div:eq(0)").html("Words : 0");
	$("#statusbar div:eq(1)").html("Characters : 0");
	getArea(1,0);
	getArea(2,0);
}

function initType(){
	$.getAjax(
		"../news/getType",
		function(data){
			if(isNull(data.errmsg)){
				var list = data.list;
				if(list != null){
					var appendStr = "";
					for(var i = 0;i < list.length;i++){
						appendStr += "<option value=\""+ list[i].id +"\">"+ list[i].name +"</option>";
					}
					$("#type").append(appendStr);
				}
				if(type_id > 0){
					$("#type option[value="+ type_id +"]").attr("selected",true);
				}
			}else{
				alert(data.errmsg);
			}
		}
	);
}
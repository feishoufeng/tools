$(document).ready(function() {
	$("#bar").append("公告管理 > "
			+ "<span id=\"news_page\" style=\"cursor: pointer;color:blue;\">公告列表</span>"
			+ " > "
			+ "<font style=\"font-weight:bold;color:blue;\">公告详情</font>");
	
	$("#news_page").click(function(){
		window.parent.document.getElementById("right").src = "../news/page";
	});
	
	var id = $("#id").val();
	if(id > 0){
		$.getAjax(
			"../news/getNewsDetail",	
			function(data){
				if(isNull(data.errmsg)){
					var newsDetail = data.newsDetail;
					if(newsDetail != null){
						$("#title").html(newsDetail.title);
						$("#content").html(newsDetail.content);
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
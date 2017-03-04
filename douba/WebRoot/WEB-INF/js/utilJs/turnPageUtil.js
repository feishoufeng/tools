function initPage(){
	//首页
	$(".page input:eq(0)").click(function(){
		page = 1
		getData(page,pagesize);
	});
	//上一页
	$(".page input:eq(1)").click(function(){
		page -= 1;
		getData(page,pagesize);
	});
	//下一页
	$(".page input:eq(3)").click(function(){
		page += 1;
		getData(page,pagesize);
	});
	//尾页
	$(".page input:eq(4)").click(function(){
		page = total_page;
		getData(page,pagesize);
	});
	//跳页
	$(".turn_page input:eq(1)").click(function(){
		page = $(".turn_page input:eq(0)").val();
		if(page > total_page || page < 1){
			alert("页码输入错误");
			return;
		}
		if(page > 0 && page <= total_page){
			getData(page,pagesize);
		}
	});
	//设置每页记录条数
	$(".set_max input:eq(1)").click(function(){
		var pagesizeTemp = $(".set_max input:eq(0)").val();
		if(pagesizeTemp > count || pagesizeTemp < 1){
			alert("设置输入错误");
			return;
		}
		if(pagesizeTemp > 0 && pagesizeTemp <= count){
			pagesize = pagesizeTemp;
			getData(page,pagesize);
		}
	});
}

function pageData(data){
	total_page = data.total_page == 0 ? 1 : data.total_page;
	count = data.count;
	
	$(".page input:eq(2)").attr("value",page + "/" + total_page);
	$("#total").html(count);
	$(".set_max input:eq(0)").val(pagesize);
	//如果当前页码为第一页，将上一页按钮设为不可用
	if(page == 1){
		$(".page input:eq(1)").attr("disabled","disabled");
		$(".page input:eq(0)").attr("disabled","disabled");
	}else{
		$(".page input:eq(1)").removeAttr("disabled");
		$(".page input:eq(0)").removeAttr("disabled");
	}
	//如果当前页码为最后一页，将下一页按钮设为不可用
	if(page == total_page){
		$(".page input:eq(3)").attr("disabled","disabled");
		$(".page input:eq(4)").attr("disabled","disabled");
	}else{
		$(".page input:eq(3)").removeAttr("disabled");
		$(".page input:eq(4)").removeAttr("disabled");
	}
}
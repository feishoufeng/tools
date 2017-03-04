$(document).ready(function(){
	$("#bar").append("<span>菜单管理</span> > <font style=\"font-weight:bold;color:blue;\">菜单列表</font>");
	
	getData();
});

function getData(){
	$.getAjax(
		"../menu/getMenu",
		function(data) {
			if (isNull(data.errmsg)) {
				$("#menu").children().remove();
				var menu_list = data.menu;
				if (menu_list.length > 0) {
					var appendStr = "";
					for (var i = 0; i < menu_list.length; i++) {
						var main = menu_list[i].main;
						appendStr += "<div class=\"main\">"
							       + "	<img src=\"../images/viewImages/shrink.png\" title=\"展开\" class=\"open\" alt=\""+(i+1)+"\"/>"
							       + "  <img src=\"../images/viewImages/open.png\" title=\"收缩\" class=\"shrink\" alt=\""+(i+1)+"\"/>"
							       + " 	"+main.orderId+"、<span class=\"main_title\">"+main.name+"</span>";
						if(main.status == 1){
							appendStr+="<img src=\"../images/viewImages/enable.png\"/ style=\"margin-left:5px;\" onclick=\"change('main','"+main.id+"','0','"+main.name+"')\">";
						}else{
							appendStr+="<img src=\"../images/viewImages/disable.png\"/ style=\"margin-left:5px;\" onclick=\"change('main','"+main.id+"','1')\">";
						}
							appendStr+="<div alt=\""+(i+1)+"\">";
						var first_list = menu_list[i].list;
						for(var q = 0;q < first_list.length;q++){
							var first = first_list[q].first;
							var second_list = first_list[q].list;
							if(second_list.length > 0){
								appendStr += "	<div class=\"first\">"
									   + "  		<img src=\"../images/viewImages/shrink.png\" title=\"展开\" class=\"open\" alt=\""+((i+1)+""+(q+1))+"\"/>"
									   + "  		<img src=\"../images/viewImages/open.png\" title=\"收缩\" class=\"shrink\" alt=\""+((i+1)+""+(q+1))+"\"/>"
									   + "  		"+first.sortid+"、<span class=\"first_title\">"+first.menuname+"</span>";
								if(first.status == 1){
									appendStr += "  <img src=\"../images/viewImages/enable.png\"/ style=\"margin-left:5px;\" onclick=\"change('first','"+first.menuid+"','0','"+first.menuname+"')\" title=\"禁用\">";
								}else{
									appendStr += "  <img src=\"../images/viewImages/disable.png\"/ style=\"margin-left:5px;\" onclick=\"change('first','"+first.menuid+"','1')\" title=\"开启\">";
								}
								appendStr += "  	<div alt=\""+((i+1)+""+(q+1))+"\">";
							}else{
								appendStr += "  		<div class=\"first\">"
									   + "  				"+first.sortid+"、<span class=\"detail\" alt=\""+ first.menuid +"\">"+first.menuname+"</span>";
								if(first.status == 1){
									appendStr += "  		<img src=\"../images/viewImages/enable.png\"/ style=\"margin-left:5px;\" onclick=\"change('first','"+first.menuid+"','0','"+first.menuname+"')\" title=\"禁用\">";
								}else{
									appendStr += "  		<img src=\"../images/viewImages/disable.png\"/ style=\"margin-left:5px;\" onclick=\"change('first','"+first.menuid+"','1')\" title=\"开启\">";
								}
							}
							
							for (var j = 0; j < second_list.length; j++) {
								var second = second_list[j].second;
								var third_list = second_list[j].list;
								if(third_list.length > 0){
									appendStr += "  		<div class=\"second\">"
										   + "  				<img src=\"../images/viewImages/shrink.png\" title=\"展开\" class=\"open\" alt=\""+((i+1)+""+(q+1)+""+(j+1))+"\"/>"
										   + "  				<img src=\"../images/viewImages/open.png\" title=\"收缩\" class=\"shrink\" alt=\""+((i+1)+""+(q+1)+""+(j+1))+"\"/>"
										   + "  				"+second.sortid+"、<span class=\"second_title\">"+second.menuname+"</span>";
									if(second.status == 1){
										appendStr += "         	<img src=\"../images/viewImages/enable.png\"/ style=\"margin-left:5px;\" style=\"margin-left:5px;\" onclick=\"change('second','"+second.menuid+"','0','"+second.menuname+"')\" title=\"禁用\">";
									}else{
										appendStr += "  		<img src=\"../images/viewImages/disable.png\"/ style=\"margin-left:5px;\" style=\"margin-left:5px;\" onclick=\"change('second','"+second.menuid+"','1')\" title=\"开启\">";
									}
										appendStr += "  			<div alt=\""+((i+1)+""+(q+1)+""+(j+1))+"\">";
									var third_list = second_list[j].list;
									for (var k = 0; k < third_list.length; k++) {
										var third = third_list[k];
										appendStr += "  			<div class=\"third\">"
												   + "  				"+third.sortid+"、<span  class=\"detail\" alt=\""+ third.menuid +"\">"+third.menuname+"</span>";
										if(third.status == 1){
											appendStr += "         		<img src=\"../images/viewImages/enable.png\"/ style=\"margin-left:5px;\" onclick=\"change('third','"+third.menuid+"','0','"+third.menuname+"')\" title=\"禁用\">";
										}else{
											appendStr += "  			<img src=\"../images/viewImages/disable.png\"/ style=\"margin-left:5px;\" onclick=\"change('third','"+third.menuid+"','1')\" title=\"开启\">";
										}
										appendStr += " 				</div>";
									}
									appendStr += "				</div>"
											   + "			</div>";
								}else{
									appendStr += "  		<div class=\"second\">"
										   	   + "  			"+second.sortid+"、<span class=\"detail\" alt=\""+ second.menuid +"\">"+second.menuname+"</span>";
									if(second.status == 1){
										appendStr += "         	<img src=\"../images/viewImages/enable.png\"/ style=\"margin-left:5px;\" onclick=\"change('second','"+second.menuid+"','0','"+second.menuname+"')\" title=\"禁用\">";
									}else{
										appendStr += "  		<img src=\"../images/viewImages/disable.png\"/ style=\"margin-left:5px;\" onclick=\"change('second','"+second.menuid+"','1')\" title=\"开启\">";
									}
									appendStr += "			</div>";
								}
								
							}
							if(second_list.length > 0){
								appendStr += "			</div>"
									   	   + "		</div>";	
							}else{
								appendStr += "	</div>";
							}
						}
						appendStr += "	</div>"
								   + "</div>";
					}
					$("#menu").append(appendStr);
					init();
				} else {
					alert("当前用户无菜单！");
				}
			} else {
				alert(data.errmsg);
			}
		}
	);
}

function init(){
	$(".open").click(function(){
		var alt = $(this).attr("alt");
		$("div[alt="+alt+"]").hide();
		$(".open[alt="+alt+"]").hide();
		$(".shrink[alt="+alt+"]").show();
	});
	$(".shrink").click(function(){
		var alt = $(this).attr("alt");
		$("div[alt="+alt+"]").show();
		$(".open[alt="+alt+"]").show();
		$(".shrink[alt="+alt+"]").hide();
	});
	$(".detail").click(function(){
		var alt = $(this).attr("alt");
		alert(alt);
	});
}

function change(type,id,status,name){
	if(status == 0){
		if("后台管理，菜单管理，菜单列表".indexOf(name) != -1){
			alert("此菜单项不可以被禁止！");
			return;
		}
	}
	
	$.getAjax(
		"../menu/changeStatus",
		function(data){
			if(isNull(data.errmsg)){
				window.parent.document.getElementById("left").src = "../login/catalog_menu";
				getData();
			}else{
				alert(data.errmsg);
			}
		},
		{"type":type,"id":id,"status":status}
	);
}
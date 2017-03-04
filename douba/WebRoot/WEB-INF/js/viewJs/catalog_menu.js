$(document).ready(function() {
	$.getAjax(
		"../login/getMenu",
		function(data) {
			if (data.errmsg == "" || data.errmsg == null) {
				if (data.menu.length > 0) {
					var appendStr = "";
					var menu_list = data.menu;
					for (var i = 0; i < menu_list.length; i++) {
						var main = menu_list[i].main;
						var main_list = menu_list[i].list;
						if(main != null){
							appendStr += "<li class=\"header\">"+main.name+"</li>"
						}
						for(var q = 0;q < main_list.length;q++){
							var first = main_list[q].first;
							appendStr += " <li class=\"treeview\"> "
										+ " <a href=\"#\"> "
											+ " <i class=\"fa fa-"+first.icon+"\"></i> "
											+ " <span>"+ first.menuname +"</span> "
											+ " <i class=\"fa fa-angle-left pull-right\"></i> "
										+ " </a> ";
							var first_list = main_list[q].list;
							if(first_list.length > 0){
								appendStr += " <ul class=\"treeview-menu\"> ";
							}
							for (var j = 0; j < first_list.length; j++) {
								var second = first_list[j].second;
								var second_list = first_list[j].list;
								if(second_list.length <= 0){
									appendStr +=  	" <li> " +
														" <a href=\".."+ second.url +"\" target=\"rightPage\"> " +
													 		" <i class=\"fa fa-circle-o\"></i> " +
													 		second.menuname +
													 	"</a> " +
													" </li> " ;
								}else{
									appendStr +=   	" <li>" +
														" <a href=\"#\"> " +
															" <i class=\"fa fa-circle-o\"></i> " +
															second.menuname +
															" <i class=\"fa fa-angle-left pull-right\"></i> " +
														" </a> " +
														" <ul class=\"treeview-menu\"> ";
								
									for (var k = 0; k < second_list.length; k++) {
										var third = second_list[k];
										appendStr +=         " <li> " +
																" <a href=\".."+ third.url +"\" target=\"rightPage\"> " +
																	" <i class=\"fa fa-circle-o\"></i> " +
																	third.menuname +
																" </a> " +
															" </li> ";
									}
									appendStr += 		" </ul> " +
													" </li>" ;
								}
							}
							if(first_list.length > 0){
								appendStr += " 	</ul> ";
							}
							appendStr += " </li> " ;
						}
					}
					$("#main").append(appendStr);
				} else {
					alert("当前用户无菜单！");
				}
			} else {
				alert(data.errmsg);
			}
		}
	);
    $.sidebarMenu($('.sidebar-menu'));
});
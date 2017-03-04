//获取城市
function getArea(type,areaCode){
	var areaObject = null;
	switch(type){
	case 0:
		areaObject = $("#province");
		break;
	case 1:
		areaObject = $("#city");
		$("#city option:gt(0)").remove();
		$("#area option:gt(0)").remove();
		if(areaCode == 0){
			return;
		}
		break;
	case 2:
		areaObject = $("#area");
		$("#area option:gt(0)").remove();
		if(areaCode == 0){
			return;
		}
		break;
	}
	$.getAjax(
		"../area/getArea",
		function(data){
			if(isNull(data.errmsg)){
				var area = data.area;
				for(var i= 0;i < area.length;i++){
					areaObject.append("<option value=\""+ area[i].code +"\">"+ area[i].value +"</option>");
				}
				if(pro_code > 0 && type == 0){
					$("#province option[value="+ pro_code +"]").attr("selected", true);
				}
				if(city_code > 0 && type == 1){
					$("#city option[value="+ city_code +"]").attr("selected", true);
				}
				if(area_code > 0 && type == 2){
					$("#area option[value="+ area_code +"]").attr("selected", true);
				}
			}else{
				alert(data.errmsg);
			}
		},	
		{"code":areaCode}
	);
}

//设置监听
function initAreaChange(){
	$("#province").change(function(){
		pro_code = $("#province").val();
		city_code = 0;
		area_code = 0;
		if(pro_code != 0){
			getArea(1,pro_code);
		}else{
			getArea(1,0);
		}
		getArea(2,0);
		
	});
	$("#city").change(function(){
		city_code = $("#city").val();
		getArea(2,city_code);
	});
	$("#area").change(function(){
		area_code = $("#area").val();
	});
}
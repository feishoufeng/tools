$(document).ready(function() {
	$("img").click(function(){
		 if (window.parent.secondary.cols=="240px,25px,*"){
			 window.parent.secondary.cols="0,25px,*";
			 $("#control_img").attr("src","../images/viewImages/control_left_botton_right.png");
		 }
		 else{
			 window.parent.secondary.cols="240px,25px,*";
			 $("#control_img").attr("src","../images/viewImages/control_left_botton_left.png");
		 }
	});
});
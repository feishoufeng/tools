$(document).ready(function() {
	$("img").click(function(){
		if (window.parent.main.rows == "104px,25px,*,40px") {
			window.parent.main.rows = "0,25px,*,40px";
			$("#control_img").attr("src", "../images/viewImages/control_top_botton_down.png");
		} else {
			window.parent.main.rows = "104px,25px,*,40px";
			$("#control_img").attr("src", "../images/viewImages/control_top_botton_up.png");
		}
	});
});
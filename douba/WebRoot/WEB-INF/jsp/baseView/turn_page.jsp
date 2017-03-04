<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<style type="text/css">
.turn_page { 
   width: 75px;
   height: 24px;
   border: 1px solid #DDDDDD;
   display: inline-block;
   margin-bottom: 2px;
   vertical-align: middle;
}
.page input[type=button]{
	border: none;
	height:24px;
}
.turn_page input{
	height: 20px;
	border: none;
	margin-top: -1px;
}
.cutline{
	height:24px;
	width: 2px;
	background-color: #DDDDDD;
	margin:0 5px;
	display: inline-block;
	vertical-align: middle;
	margin-bottom: 2px;
}
.set_max{
 	width: 98px;
   	height: 24px;
   	border: 1px solid #DDDDDD;
   	display: inline-block;
   	margin-bottom: 2px;
   	vertical-align: middle;
}
.set_max input{
	height: 20px;
	border: none;
	margin-top: -1px;
}

</style>
<!-- 分页工具 -->
<script type="text/javascript" src="${basePath }/js/utilJs/turnPageUtil.js"></script>
<div style="width:100%;height:24px;margin-top:10px;margin-bottom:20px;vertical-align: middle;" class="page" align="center">
	<input type="button" value="首页">
	<input type="button" value="上一页">
	<input type="button" disabled="disabled" style="background: #F0F0F0;">
	<input type="button" value="下一页">
	<input type="button" value="尾页">
	<div class="turn_page">
		<input type="number" min="1" max="999" style="width:41px;" oninput="if(value.length>3)value=value.slice(0,3)" >
		<input type="button" value="Go" title="跳页" style="width:31px;margin-left: -3px;">
	</div>
	页
	<div class="cutline"></div> 
	总计 <span id="total" style="color:blue;"></span> 条记录，每页显示
	<div class="set_max">
		<input type="number" min="1" max="999999" style="width:64px;" oninput="if(value.length>6)value=value.slice(0,6)" >
		<input type="button" value="Go" title="设置每页记录条数" style="width:31px;margin-left: -3px;">
	</div>
	条记录
</div>

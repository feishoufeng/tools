<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 导出工具 -->
<script type="text/javascript" src="${basePath }/js/exportJs/xlsx.core.min.js"></script>
<script type="text/javascript" src="${basePath }/js/exportJs/blob.js"></script>
<script type="text/javascript" src="${basePath }/js/exportJs/FileSaver.min.js"></script>
<script type="text/javascript" src="${basePath }/js/exportJs/tableexport.min.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath }/css/exportCss/export.css">
<link rel="stylesheet" type="text/css" href="${basePath }/css/exportCss/tableexport.min.css">
<!-- 时间工具 -->
<script type="text/javascript" src="${basePath }/js/datepicker/moment.min.js"></script>
<script type="text/javascript" src="${basePath }/js/datepicker/jquery.daterangepicker.js"></script>
<script type="text/javascript" src="${basePath }/js/datepicker/dateRangPicker.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath }/css/DateRangPicker/daterangepicker_normalize.css" />
<link rel="stylesheet" type="text/css" href="${basePath }/css/DateRangPicker/daterangepicker_default.css">
<link rel="stylesheet" type="text/css" href="${basePath }/css/DateRangPicker/daterangepicker.css" />

<link rel="stylesheet" type="text/css" href="${basePath }/css/exportCss/exportPage.css">
<script type="text/javascript" src="${basePath }/js/utilJs/exportPageUtil.js"></script>
<div style="width: 100%;height: 26px;">
	<div style="width: 50%;height: 26px;float: left;">
		<div class="exportOptions">
			<div class="exportSelect">
				<select>
					<option value="">--</option>
					<c:forEach var="item" items="${options }" begin="0" step="1">
						<option value="${fn:split(item,',')[0] }" title="${item }">${fn:split(item,',')[1] }</option>
		        	</c:forEach>
				</select>
			</div>
			<div class="optioncutline"></div>
			<div style="font-size: 18px;color:#A9A9A9;margin: 0 2px;">:</div>
			<div class="optioncutline"></div>
			<div>
				<input type="text" class="condition" value="请输入筛选条件" onfocus="if(this.value==this.defaultValue){this.value='';$(this).css('color','black')}" 
				onblur="if(this.value==''){this.value=this.defaultValue;$(this).css('color','#888')}">
			</div>
			<c:forEach var="options_item" items="${optionLists }" begin="0" step="1" varStatus="st">
				<div style="display:none;" title="select" id="select${st.index + 1 }">
					<select class="condition_select">
						<option value="" style="color:#888;">--请选择--</option>
						<c:forEach var="status_item" items="${options_item }" begin="0" step="1">
							<option value="${fn:split(status_item,',')[0] }">${fn:split(status_item,',')[1] }</option>
			        	</c:forEach>
					</select>
				</div>
			</c:forEach>
		</div>
		<div style="float:left;border:1px solid #A9A9A9;border-left:none;width:42px;height:26px;">
			<input type="button" class="confirm" value="确认">
		</div>
	</div>
	<div class="tool" style="width: 50%;height: 26px;float: left;" align="right">
		<c:choose>
			<c:when test="${showAdd }">
				<c:set var="w" scope="request" value="411"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="w" scope="request" value="306"></c:set>
			</c:otherwise>
		</c:choose>
		<div style="width: ${w}px;">
			<div>
				<input id="date-range0" size="22" style="width: 194px;height:26px;color:#888;" value="选择数据时间" readonly="readonly"
				onfocus="if(this.value==this.defaultValue){this.value='';$(this).css('color','black')}" 
				onblur="if(this.value==''){this.value=this.defaultValue;$(this).css('color','#888')}">
			</div>
			<div class="clear">
				<img src="../images/viewImages/close.png" style="width: 18px;margin-top: 4px;cursor: pointer;">
			</div>
			<div class="exportcutline"></div>
			<div class="export">
				<img alt="../" src="../images/exportImages/export.png" style="width: 18px; margin-right: 6px;"> 
				导出数据
			</div>
		</div>
		<c:if test="${showAdd }">
			<div>
				<div class="add">
					<img alt="../" src="../images/viewImages/add.png" style="width: 12px; margin-right: 6px;"> 
					${addTitle }
				</div>
			</div>
		</c:if>
	</div>
</div>
<div class="exportDeck"></div>
<div align="center" class="exportDlg">
<div>
	<div class="exportsquare">
		<div onclick="exportFile(0);">
			<span style="background-color: #006400;"><img src="../images/exportImages/ixlsx.png"></span>
			<span style="width: 106px;">Export to xlsx</span>
		</div>
	</div>
	<div class="exportsquare">
		<div onclick="exportFile(1);">
			<span style="background-color: #008000;"><img src="../images/exportImages/ixls.png"></span>
			<span style="width: 106px;">Export to xls</span>
		</div>
	</div>
</div>
<div>
	<div class="exportsquare">
		<div onclick="exportFile(2);">
			<span style="background-color: #0000FF;"><img src="../images/exportImages/icsv.png"></span>
			<span style="width: 106px;">Export to csv</span>
		</div>
	</div>
	<div class="exportsquare">
		<div onclick="exportFile(3);">
			<span style="background-color: #800080;"><img src="../images/exportImages/itxt.png"></span>
			<span style="width: 106px;">Export to txt</span>
		</div>
	</div>
</div>
</div>

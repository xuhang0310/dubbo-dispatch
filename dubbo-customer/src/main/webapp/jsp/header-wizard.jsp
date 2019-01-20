<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();

String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新版后台管理系统</title>
		<meta name="keywords" content="Bootstrap模版,Bootstrap模版下载,Bootstrap教程,Bootstrap中文" />
		<meta name="description" content="站长素材提供Bootstrap模版,Bootstrap教程,Bootstrap中文翻译等相关Bootstrap插件下载" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		
		
		<!-- basic styles -->
		<link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=path%>/css/font-awesome.min.css" />
		
		<!-- Ionicons -->
        <link href="<%=path %>/css/ionicons.css" rel="stylesheet" type="text/css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="<%=path%>/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- fonts -->

		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />

	

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=path%>/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="<%=path%>/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="<%=path%>/js/html5shiv.js"></script>
		<script src="<%=path%>/js/respond.min.js"></script>
		<![endif]-->
		
		<!-- 引入bootstrapTable开始 -->
    <script src="<%=path %>/js/bootstrap.min.js"></script>
    <link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="<%=path %>/plugins/bootstrap_table/bootstrap-table.css">
	<script src="<%=path %>/plugins/bootstrap_table/bootstrap-table.js"></script>
    <script src="<%=path %>/plugins/bootstrap_table/extensions/export/bootstrap-table-export.js"></script>
    <script src="<%=path %>/plugins/bootstrap_table/locale/bootstrap-table-zh-CN.js"></script>
<!-- 引入bootstrapTable结束 -->

<!-- 引入jquery.validate.js开始 -->
<script src="<%=path %>/plugins/jquery-validation-1.15.0/js/jquery.validate.js"></script>
<script src="<%=path %>/plugins/jquery-validation-1.15.0/js/additional-methods.js"></script>
<script src="<%=path %>/plugins/jquery-validation-1.15.0/js/messages_zh.js"></script>

<!-- 引入jquery.validate.js结束 -->




<link rel="stylesheet" href="<%=path%>/plugins/zTree_v3/css/metroStyle/metroStyle.css" type="text/css">
		
    <script type="text/javascript" src="<%=path %>/plugins/layer/3.1/layer.js"></script> 
    
  
	 
   
     
     <link href="<%= request.getContextPath() %>/css/ace-custome-analyse.css" rel="stylesheet" type="text/css" />
		
<!-- 引入bootstrap-select开始 -->
<link rel="stylesheet" href="<%=path %>/plugins/boostrap-select-1.12.2/css/bootstrap-select.css">
<script src="<%=path %>/plugins/boostrap-select-1.12.2/js/bootstrap-select.js"></script>
<!-- 引入bootstrap-select结束 -->	



        
        <script type="text/javascript" src="<%=path %>/plugins/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="<%=path %>/plugins/echart/echarts.min.js"></script>



<script type="text/javascript">
function layer_show(title,url,w,h){
	if (title == null || title == '') {
		title=false;
	};
	
	if (w == null || w == '') {
		w=800;
	};
	if (h == null || h == '') {
		h=($(window).height() - 50);
	};
	layer.open({
		skin: 'layui-layer-lan',
		shift: 4, 
		type: 2,
		area: [w+'px', h +'px'],
		fix: false, //涓嶅浐瀹�		maxmin: true,
		shade:0.4,
		closeBtn: 1,
		title: title,
		content: url
	});
}

function layer_close(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}

function autocomplete(url,targetField,hiddenField){
	$.ajax({
        type: "post",
        url: url,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
       	 $( "#"+targetField ).autocomplete({
                minLength: 0,
                source: data,
                focus: function( event, ui ) {
                 $( "#"+targetField ).val( ui.item.label );
                  return false;
                },
                select: function( event, ui ) {
               	$( "#"+targetField ).val( ui.item.label );
                   $( "#"+hiddenField ).val( ui.item.value );
                   return false;
                }
             });
        },
        error: function (msg) {
            alert(msg);
        }
    }); 
}

function dateTypeChange(){
	var temp = $("#dateType").val();
	if(temp=="5"){
		$("#dateCustom").show();
	}else{
		$("#dateCustom").hide();
	}
}

function textarealength(obj,maxlength){
	var v = $(obj).val();
	var l = v.length;
	if( l > maxlength){
		v = v.substring(0,maxlength);
	}
	$(obj).parent().find(".textarea-length").text(v.length);
}

function alertInfo(msg){
	 layer.alert(msg, {icon: 1},{time:1000});
}

</script>
<style>
	.divmatnrdesc{
	    overflow:hidden;
	    white-space:nowrap;
	    text-overflow:ellipsis;
	    
 	}
</style>


  </head>
  

</html>

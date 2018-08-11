<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>生产调度系统</title>
       
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
       <%@ include file="/jsp/header.jsp"%>
       <!-- ace styles -->
        <script type="text/javascript" src="<%=path%>/plugins/layout/jquery.layout.js"></script>
		
		
  </head>
  
  <!-- <body style="margin:10px;overflow:hidden;"> -->
    <body>

  	<div class="row analyse">
  		<input type="hidden" name="code" id="code" value="${code }">
  		<input type="hidden" name="imgUrl" id="imgUrl" value="${imgUrl }">
  		
			<div id="realMap">
			     <div style="float: left; width: 100%; height: 100%;"   >
					  <img src="<%= request.getContextPath() %>/uplode/${imgUrl}">
				 </div>
			</div>
			<div id="tempReal" style="position:absolute;top:15px;left:20px;">
			</div>
			<div style="z-index:99;position:absolute;left:80%;top:10px;">
			  <span><a class="btn btn-primary radius" href="javascript:update()" style="font-size:6px;"><i class="icon-edit"></i>修改位置</a></span>
			 <span><a class="btn btn-primary radius" href="javascript:pitchUpdate()" style="font-size:6px;"><i class="icon-edit"></i>批量管理</a></span>
	
			</div>
		</div>

	  <script type="text/javascript">
/* 	var $table = $('#table'),
    $remove = $('#remove'),
    selections = [];
 */
	$(function () {
		renderFlow('${process}');
	});
	  //渲染div块
     function renderFlow(info){    
     	 var json=eval("("+info+")");
     	  var table="";
     	//  alert(JSON.stringify(json));
     	  $.each(json,function(index,value){
     	//  alert(index);alert(value);
     			var fontstyle="font-size:"+value.s+"px;color:white;";
     	  		var style="z-index:99;left:"+value.x+"px;top:"+value.y+"px;font-size:"+value.s+"px;white-space:nowrap;position:absolute;border:1px solid #f8f8f8;margin:2px 2px 2px 2px;border-radius:20px;color:white;background:#"+value.bg+";"
     	   		/* table+="<table  border='0'  cellspacing='0' cellpadding='0'  style='"+style+"'><tr><td>"+value.dplay+"</td><td>"+value.data+value.u+"</td></tr></table>"; */
     	table+="<table  border='0'  cellspacing='0' cellpadding='0'  style='"+style+"'><tr><td style="+fontstyle+">"+value.dplay+"</td><td style="+fontstyle+">"+value.data+value.u+"</td></tr></table>";
     	  })
     	//  alert(table);
     	  $("#tempReal").html(table);
     }

	 function queryParams(params) {
		return {rows: params.limit,page: params.pageNumber,username:$("#username").val()};
	} 
	function getHeight() {
	    return $(window).height()-100;
	}
	function getWidth(){
		return $(window).width()-210;
	}
	
	function search(){
	    $table.bootstrapTable('refresh');
    }
	  //修改位置
     function update(){
     var code=$("#code").val();
     var imgUrl=$("#imgUrl").val();
	      var url="<%=path%>/feedReal/processManager.do?code="+code+"&imgUrl="+imgUrl;
	      window.location.href=url;
      }
	
	function pitchUpdate(){
	 var code=$("#code").val();
	layer_show('修改',"<%= request.getContextPath() %>/feedReal/pitchUpdate.do?code="+code,'1050')
	}
	    
    </script> 
  </body>
</html>

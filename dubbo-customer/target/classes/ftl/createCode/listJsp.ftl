<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>商业BI分析系统</title>
       
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery.min.js"></script>
       <%@ include file="/jsp/header.jsp"%>
       <link href="<%= request.getContextPath() %>/css/H-ui.min.css" rel="stylesheet" type="text/css" />
	    <link href="<%= request.getContextPath() %>/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
		<link href="<%= request.getContextPath() %>/jslib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
    </head>
  
  <body style="margin:10px" >
  
   <div class="pd-10">
		<div class="text-c"> 登录名：
			
			 <input type="text" class="input-text" style="width:250px" placeholder="输入关键词" id="username" name="username">
			<button onclick="search()" class="btn btn-success radius"  name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>

		</div>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-10"> 
         <span><a class="btn btn-primary radius"  onclick="layer_show('添加','<%= request.getContextPath() %>/${objectNameLower}/add${objectName}Layer.do','800')"><i class="Hui-iconfont">&#xe600;</i> 添加</a> </span>
         
    </div>

    <div class="mt-20">
			
			<!-- 检索  -->
			
			  <div class="tab-pane active">
					<!-- 检索  -->
			   		<table id="table" class="divmatnrdesc"
			           data-id-field="id"
			           data-click-to-select="true"
			           data-page-list="[10, 25, 50, 100]">
		           </table>
			  </div>
          
	  </div>
 
	<script type="text/javascript">
	var $table = $('#table'),
    $remove = $('#remove'),
    selections = [];
    
    
	$(function () {
	
	    var columns =eval(${r"${jsonTableGrid}"});
	    $table.bootstrapTable({
	        height: getHeight(),
	       
	        method: 'post',
	        url:"<%= request.getContextPath() %>/${objectNameLower}/get${objectName}List.do",
	        dataType: "json",
	        striped: true,
	        pagination: true,
	        queryParamsType: "limit",
	        contentType: "application/x-www-form-urlencoded",
	        pageSize: 20,
	        pageNumber:1,
	        queryParams: queryParams,
	        showColumns: false, //不显示下拉框（选择显示的列）
	        sidePagination: "server", //服务端请求
	        responseHandler: responseHandler,
	        columns:columns
	    });
	    
	   
	    $(window).resize(function () {
	        $table.bootstrapTable('resetView', {
	            height: getHeight(),
	            width:getWidth()
	        });
	    });
	});
	
	function responseHandler(res) {
		    return {
		    	"rows": res.rows,
		    	"total": res.total
	    	};
	}
	 function queryParams(params) {
		return {rows: params.limit,page: params.pageNumber};
	} 
	function getHeight() {
	    return $(window).height()-150;
	}
	function getWidth(){
		return $(window).width()-210;
	}
	
	function search(){
	    $table.bootstrapTable('refresh');
    }
	
	function operateFormatter(value, row, index) {
		
		
		
		 return [
		            '<a class="like" href="javascript:edit${objectName}(\''+value+'\')" title="编辑">',
		            '<i class="glyphicon glyphicon-edit"></i>',
		            '</a>  ',
		            '<a class="remove" href="javascript:delete${objectName}(\''+value+'\')" title="删除">',
		            '<i class="glyphicon glyphicon-remove"></i>',
		            '</a>'
		        ].join('');
    }
	
	function edit${objectName}(obj){
		
		layer_show('修改','<%= request.getContextPath() %>/${objectNameLower}/edit${objectName}Layer.do?${key}='+obj,'800')
	}
	function delete${objectName}(obj){
		layer.confirm('确认删除该数据',{btn:['是','否']},function(){
			 var url="<%= request.getContextPath() %>/${objectNameLower}/delete${objectName}.do?${key}="+obj;
       	     $.post(url,$("form").serialize(),function(data){
       	    	 if(data.flag){
       	    		 layer.msg('删除成功', {icon: 1},function(){
       	    			search()
       	    		 });
       	    	 }
       	    	 
       	     },'json')
		},function(){
			
		})
	}
	
	
	
	    
    </script> 
  </body>
</html>


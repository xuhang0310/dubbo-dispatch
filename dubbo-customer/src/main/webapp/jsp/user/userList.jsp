<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>后台管理系统</title>
       
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
       <%@ include file="/jsp/header.jsp"%>
       <!-- ace styles -->
		
		
       
    </head>
  
  <body style="margin:10px;overflow:hidden;" >
     
    <div class="row">
		<div class="col-sm-12">
		 <form id="listForm" name="listForm" action="#">	
			 <input type="text" class="input-text" style="width:250px" placeholder="请输入关键字" id="username" name="username">
			 <span><a class="btn btn-success radius" href="javascript:search()"><i class="icon-search"></i>查询</a></span>
			<shiro:hasPermission name="/jsp/user/addUser">
			  <%-- <span><a class="btn btn-primary radius"  onclick="layer_show('添加用户','<%= request.getContextPath() %>/jsp/user/eidtUser.jsp','600')"><i class="icon-edit"></i> 添加用户</a> </span> --%>
			  <span><a class="btn btn-primary radius"  onclick="layer_show('添加用户','<%= request.getContextPath() %>/user/addUser.do','600')"><i class="icon-edit"></i> 添加用户</a> </span>
            </shiro:hasPermission>
           </form>
		</div>
    </div>
    

    <div class="row" style="margin-top:10px;">
         <div class="col-sm-12">
         
              <!-- 检索  -->
			
			  <div class="tab-pane active">
					<!-- 检索  -->
			   		<table id="table"  class="divmatnrdesc" style="table-layout:fixed;"
			           data-id-field="id"
			           data-click-to-select="true"
			           data-page-list="[10, 25, 50, 100]">
		           </table>
			  </div>
         
         </div>
			
			
          
	  </div>
 
	<script type="text/javascript">
	var $table = $('#table'),
    $remove = $('#remove'),
    selections = [];

	$(function () {
		 var columns =eval(${jsonTableGrid});
	    $table.bootstrapTable({
	        height: getHeight(),
	       
	        method: 'post',
	        url:"<%=path%>/user/getUserList.do",
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
	        columns: columns
	    });
	    
	   
	    $(window).resize(function () {
	        $table.bootstrapTable('resetView', {
	            height: 500,
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
		 var datas= $.param({rows: params.limit,page: params.pageNumber}) + '&' + $('#listForm').serialize();
		 return datas;
	} 
	function getHeight() {
	    return $(window).height()-50;
	}
	function getWidth(){
		return $(window).width()-210;
	}
	
	function search(){
		
	    $table.bootstrapTable('refresh');
    }
	
	function operateFormatter(value, row, index) {
		 var temp= '';
		 <shiro:hasPermission name="/jsp/user/updateUser">
		    temp +='<a class="like" href="javascript:editUser('+value+')" title="编辑"><i class="icon-edit"></i>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;   ';
         </shiro:hasPermission>
         
         <shiro:hasPermission name="/jsp/user/deleteUser">
		    temp += '  <a class="like" href="javascript:deleteUser('+value+')" title="删除"><i class="icon-remove"></i>删除</a>';
         </shiro:hasPermission>
		
		 return temp;
    }
	
	function editUser(obj){
		
		layer_show('修改用户','<%= request.getContextPath() %>/user/updUser.do?userid='+obj,'500')
	}
	function deleteUser(obj){
		layer.confirm('确认删除该用户',{btn:['是','否']},function(){
			 var url="<%=path %>/user/deleteUser.do?userid="+obj;
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

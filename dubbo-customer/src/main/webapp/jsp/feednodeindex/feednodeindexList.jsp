<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>生产调度系统</title>
       
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
       <%@ include file="/jsp/header.jsp"%>
       <!-- ace styles -->
		
		
       
    </head>
  
<body style="margin:10px;overflow:hidden;" >
     
    <div class="row">
		<div class="col-sm-12"> 
		 <form id="listForm" name="listForm" action="#">		
			 <!-- <input type="text" class="input-text" style="width:250px" placeholder="指标类型" id="indextype" name="indextype"> -->
			  <select class="selectpicker" style="width: 232px;height: 31px" data-live-search="true" name="indextype" id="indextype" title="指标类型" >
	            	<option value="" >--请选择--</option>
	            	<c:forEach var="lx" items="${zb}">
			            		<option value="${lx.ID}" >${lx.NAME}</option>
			          </c:forEach>
	         </select>
			   <select class="selectpicker" style="width: 232px;height: 31px" data-live-search="true" onchange="getProjectList()"   name="zlx" id="zlx" title="请选择站类型" >
	            <option value="" >--请选择--</option>
	            <option value="1">换热站</option>
	             <option value="99">热源厂</option>
	         </select>
	           <select class="selectpicker" data-live-search="true"  name="mc" id="mc" title="请选择站名称" >
	            <option value="">--请选择--</option>
	         </select>
			<span><a class="btn btn-success radius" href="javascript:search()"><i class="icon-search"></i>查询</a></span>
			<shiro:hasPermission name="/jsp/feednodeindex/addFeednodeindex">
			  <%-- <span><a class="btn btn-primary radius"  onclick="layer_show('添加','<%= request.getContextPath() %>/jsp/feednodeindex/addFeednodeindex.jsp','600')"><i class="icon-edit"></i> 添加</a> </span> --%>
         	<span><a class="btn btn-primary radius"  onclick="addZb()"><i class="icon-edit"></i> 添加</a> </span>
            </shiro:hasPermission>
            </form>
		</div>
    </div>
    

    <div class="row" style="margin-top:10px;">
         <div class="col-sm-12">
         
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
	        url:"<%=path%>/feednodeindex/getFeedNodeIndexList.do",
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
	    return $(window).height()-80;
	}
	function getWidth(){
		return $(window).width()-210;
	}
	
	function search(){
	    $table.bootstrapTable('refresh');
    }
	
	function operateFormatter(value, row, index) {
		 //var temp= '';
		 var temp="";
	     var stationtype=row.STATIONTYPE;
		 <shiro:hasPermission name="/jsp/feednodeindex/updateZb">
		   /*  temp +='<a class="like" href="javascript:editZb('+value+')" title="编辑"><i class="icon-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;   '; */
       		temp+="<a class=\"like\" href=\"javascript:editZb("+value+",'"+stationtype+"',)\" title=\"编辑\"><i class=\"icon-edit\"></i></a>&nbsp;&nbsp;&nbsp;&nbsp; ";
         </shiro:hasPermission>
         
         <shiro:hasPermission name="/jsp/feednodeindex/deleteZb">
	/* 	    temp += '  <a class="like" href="javascript:deleteZb('+value+')" title="删除"><i class="icon-remove"></i></a>'; */
         	temp+=" <a class=\"like\" href=\"javascript:deleteZb("+value+")\" title=\"删除\"><i class=\"icon-remove\"></i></a>";
         </shiro:hasPermission>
		
		 return temp;
    }
	
		function addZb(){
	//	alert(obj);
		layer_show('添加',"<%= request.getContextPath() %>/feednodeindex/addZb.do",'500')
	}
	
	
	function editZb(obj,stationtype){
	
		if(stationtype=="换热站"){
			code=1;
		}else{
			code=99;
		}
	//	alert(code);
		layer_show('修改',"<%= request.getContextPath() %>/feednodeindex/findZb.do?id="+obj+"&stationtype="+code,'500')
		<%-- layer_show('修改',"<%= request.getContextPath() %>/basmeter/findMeter.do?id="+obj+"&stationtype="+code,'600') --%>
	
	}
	function deleteZb(obj){
		layer.confirm('确认删除吗?',{btn:['是','否']},function(){
			 var url="<%=path %>/feednodeindex/deleteZb.do?id="+obj;
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
	
		function getProjectList(){
				var stationtype = document.getElementById("zlx").value;
				if(stationtype==""){
			//	alert("还没选呢！");
		    	$("#mc").empty();
		    	$("#mc").selectpicker('refresh');
				return;
				}
				var url="<%=path %>/basmeter/getProjectList.do?stationtype=" + stationtype;
				$.ajax({
			    	type: "POST",
			    	url: url,
			    	success: function(data){
			    	//	 alert(data);
			    		 $("#mc").empty();
			    		 var i="<option value=\"\" selected>请选择站名称</option>";
			    		 $("#mc").append(i);		    		
			    		 $("#mc").append(data);
			    		 $("#mc").selectpicker('refresh');
			    	},
			    	error: function(){ alert("请求失败!"); }
			    });
			}
	
	
	    
    </script> 
  </body>
</html>

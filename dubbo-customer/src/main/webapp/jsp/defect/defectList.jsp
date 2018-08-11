<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
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
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">  
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
       <%@ include file="/jsp/header.jsp"%>
 
    </head>
  

<body style="margin:10px;overflow:hidden;" >
     
    <div class="row">
		<div class="col-sm-12">
			<form id="listForm" name="listForm" action="#">	
			  <input type="hidden" name="status" id="status" value="${status}">
			 <input type="text" onfocus="WdatePicker()" id="kssj" name="kssj"  placeholder="开始时间"  value="" class="input-text Wdate" style="width:200px;">   
			   <input type="text" onfocus="WdatePicker()" id="jssj" name="jssj"  placeholder="结束时间"  value="" class="input-text Wdate" style="width:200px;">   
			 <select class="selectpicker" style="width: 232px;height: 31px" data-live-search="true" name="qxlx" id="qxlx" title="请选择缺陷类型" >
	            		<option value="" selected>--请选择--</option>
	            	<c:forEach var="dt" items="${defectType}">
			             <option value="${dt.ID}" >${dt.NAME}</option>
			           </c:forEach>
	         </select>
	         <select class="selectpicker" data-live-search="true"  name="org" id="org" title="" >
	            <option value="">--请选择--</option>
	            <c:forEach var="org" items="${orgname}">
			             <option value="${org.ORGID}" >${org.ORGNAME}</option>
			    </c:forEach>
	         </select>
		
			<span><a class="btn btn-success radius" href="javascript:search()"><i class="icon-search"></i>查询</a></span>
		
			<c:if test="${status==3}">
			<shiro:hasPermission name="/jsp/defect/addDefect">
           <span><a class="btn btn-primary radius"  onclick="add()"><i class="icon-edit"></i> 添加</a> </span>
            </shiro:hasPermission>	
            </c:if>
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
		 var status=$("#status").val();
		
	    $table.bootstrapTable({
	        height: getHeight(),
	       
	        method: 'post',
	        url:"<%=path%>/defect/getDefectList.do?status="+status,
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
/* 	 function queryParams(params) {
		return {rows: params.limit,page: params.pageNumber,basmeter:$("#basmeter").val()};
	}  */
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
		// var temp= '';
	//	alert(row.STATUS);
		var status=row.STATUS;
		 var temp="";
   
           if(status=="草稿"){
		    temp+="<a class=\"like\" href=\"javascript:editDefect("+value+")\" title=\"编辑\"><i class=\"icon-edit\"></i>编辑</a>&nbsp;&nbsp;  ";
			}
 			 if(status=="待消缺"){
 			temp+="<a class=\"like\" href=\"javascript:deal("+value+")\" title=\"处理\"><i class=\"icon-edit\"></i>处理</a>&nbsp;&nbsp;  ";
 			}
 			
 			if(status=="已消缺"){
		   temp+="<a class=\"like\" href=\"javascript:showDefect("+value+")\" title=\"详情\"><i class=\"icon-list\"></i>详情</a>&nbsp;&nbsp;  ";
			}
			 if(status=="消缺中"){
			 temp+="<a class=\"like\" href=\"javascript:solveDefect("+value+")\" title=\"消缺\"><i class=\"icon-edit\"></i>消缺</a>&nbsp;&nbsp;  ";
			}
			
			if(status=="草稿"){
		    temp+="<a class=\"like\" href=\"javascript:delDefect("+value+")\" title=\"删除\"><i class=\"icon-remove\"></i>删除</a>";
			}
		
		 return temp;
    }
	
		function add(){
	//	alert(obj);
		layer_show('添加',"<%= request.getContextPath() %>/defect/toAddPage.do",'600');
	}
	
	
	function editDefect(obj){
	layer_show('修改',"<%= request.getContextPath() %>/defect/findDefect.do?id="+obj,'600')
	}
	
	function deal(obj){
	layer_show('缺陷单-处理',"<%= request.getContextPath() %>/defect/findDefectDeal.do?id="+obj,'900','550')
	}
	function solveDefect(obj){
	layer_show('缺陷单-消缺',"<%= request.getContextPath() %>/defect/findDefectSolve.do?id="+obj,'900','550')
	}
	
	function showDefect(obj){
	layer_show('缺陷单详情',"<%= request.getContextPath() %>/defect/showDefectSolve.do?id="+obj,'900','550')
	}
	
	
	function delDefect(obj){
	//alert(obj);
	
		layer.confirm('确认删除',{btn:['是','否']},function(){
			 var url="<%=path %>/defect/deleteDefect.do?id="+obj;
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

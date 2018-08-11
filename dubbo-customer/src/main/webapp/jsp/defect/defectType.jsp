<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>


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
			  <input type="text" class="input-text" style="width:180px" placeholder="输入缺陷名称" id="defectname" name="defectname">
			
	      
		
				
				 <span><a class="btn btn-success radius" href="javascript:search()"><i class="icon-search"></i>查询</a></span>
			<shiro:hasPermission name="/jsp/basmeter/addbasmeter">
			 
           <span><a class="btn btn-primary radius"  onclick="add()"><i class="icon-edit"></i> 添加</a> </span>
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
	        url:"<%=path%>/defect/getDefectTypeList.do",
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
		 var temp= '';
          <shiro:hasPermission name="/jsp/basmeter/updatebasmeter">
		    temp +='<a class="like" href="javascript:editDefectType('+value+')" title="编辑"><i class="icon-edit"></i>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;   ';
         </shiro:hasPermission>
         <shiro:hasPermission name="/jsp/basmeter/deletebasmeter">
		    temp += '  <a class="like" href="javascript:deleteDefectType('+value+')" title="删除"><i class="icon-remove"></i>删除</a>';
         </shiro:hasPermission>
		
		 return temp;
    }
	
		function add(){
	
		layer_show('添加',"<%= request.getContextPath() %>/defect/toEditDefect.do",'600','300');
	}
	
	
	function editDefectType(obj){
		//	alert(obj);
		layer_show('修改',"<%= request.getContextPath() %>/defect/selectAllDefect.do?id="+obj,'600','300');
	}
	
	function deleteDefectType(obj){			
		layer.confirm('确认删除',{btn:['是','否']},function(){
			 var url="<%=path %>/defect/deleteDefectType.do?id="+obj;
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

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
 
    </head>
  
<body style="margin:10px;overflow:hidden;" >
     
    <div class="row">
		<div class="col-sm-12"> 采暖期名称：
			
			 <input type="text" class="input-text" style="width:250px" placeholder="输入关键词" id="cnq" name="cnq">
			<button onclick="search()" class="btn btn-success radius"  name=""><i class="icon-search"></i> 查询</button>
			<shiro:hasPermission name="/jsp/dataparam/addparam">
			  <span><a class="btn btn-primary radius"  onclick="layer_show('添加','<%= request.getContextPath() %>/jsp/dataparam/addDataParam.jsp','600')"><i class="icon-edit"></i> 添加</a> </span>
            </shiro:hasPermission>
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
	        url:"<%=path%>/dataparam/getDataParamList.do",
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
		return {rows: params.limit,page: params.pageNumber,cnq:$("#cnq").val()};
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
		 <shiro:hasPermission name="/jsp/dataparam/updateparam">
		    temp +='<a class="like" href="javascript:editParam('+value+')" title="编辑"><i class="icon-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;   ';
         </shiro:hasPermission>
         
         <shiro:hasPermission name="/jsp/dataparam/deleteparam">
		    temp += '  <a class="like" href="javascript:deleteParam('+value+')" title="删除"><i class="icon-remove"></i></a>';
         </shiro:hasPermission>
		
		 return temp;
    }
	
	
	
	
	function editParam(obj){
	//	alert(obj);
		layer_show('修改',"<%= request.getContextPath() %>/dataparam/findParam.do?id="+obj,'600')
	}
	function deleteParam(obj){
	alert(obj);
		layer.confirm('确认删除吗?',{btn:['是','否']},function(){
			 var url="<%=path %>/dataparam/deleteParam.do?id="+obj;
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

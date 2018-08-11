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
		
		
       
    </head>
  
  <body style="margin:10px;overflow:hidden;" >
     
    <div class="row">
		<div class="col-sm-12">
			<form id="listForm" name="listForm" action="#">	
			 <input type="text" class="input-text" style="width:250px" placeholder="请输入名称" id="name" name="name">
			 <span><a class="btn btn-success radius" href="javascript:search()"><i class="icon-search"></i>查询</a></span>
			<%-- <shiro:hasPermission name="/jsp/data/addDataTeam">
			  <span><a class="btn btn-primary radius"  onclick="layer_show('添加','<%= request.getContextPath() %>/dataTeam/addDataTeamList.do','500')"><i class="icon-edit"></i> 添加</a> </span>
            </shiro:hasPermission> --%>
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
	  
<!-- <ul class="nav nav-tabs" id="myTab">  
<ul class="nav nav-pills nav-justified" id="myTab"> 样式不一样 
    <li class="active"><a href="#home">Home
    	<i class="green icon-desktop bigger-110"></i></a></li>  
    <li><a href="#profile">Profile
    	<i class="green icon-desktop bigger-110"></i></a></li>  
    <li><a href="#messages">Messages
    	<i class="green icon-desktop bigger-110"></i></a></li>  
    <li><a href="#settings">Settings
    	<i class="green icon-desktop bigger-110"></i></a></li>  
</ul>  
  
<div class="tab-content">  
    <div class="tab-pane active" id="home">
		<div class="row" style="margin-top:10px;">
         <div class="col-sm-12">
              检索 
			  <div class="tab-pane active">
					检索 
			   		<table id="table" class="divmatnrdesc"
			           data-id-field="id"
			           data-click-to-select="true"
			           data-page-list="[10, 25, 50, 100]">
		           </table>
			  </div>
         </div>
	  </div>
	</div>  
    <div class="tab-pane" id="profile">...</div>  
    <div class="tab-pane" id="messages">...</div>  
    <div class="tab-pane" id="settings">...</div>  
</div>   -->
 
	<script type="text/javascript">
	var $table = $('#table'),
    $remove = $('#remove'),
    selections = [];

	$(function () {
		/* $('#myTab a:first').tab('show'); //初始化显示哪个tab  
        $('#myTab a').click(function(e) {  
            e.preventDefault(); //阻止a链接的跳转行为  
            $(this).tab('show'); //显示当前选中的链接及关联的content  
        }); */ 
		
		var columns =eval(${jsonTableGrid});
	    $table.bootstrapTable({
	        height: getHeight(),
	       
	        method: 'post',
	        url:"<%=path%>/applyDispatchHistory/getDispatchHistoryList.do",
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
		/* return {rows: params.limit,page: params.pageNumber,feedname:$("#feedname").val()}; */
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
		 <shiro:hasPermission name="/jsp/data/updateDataTeam">
		    temp +="<a class=\"like\" href=\"javascript:editDataTeam(\'"  +value+   "\')\" title=\"编辑\"><i class=\"icon-edit\"></i>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;   ";
         </shiro:hasPermission>
         
         <shiro:hasPermission name="/jsp/data/deleteDataTeam">
		    //temp += '  <a class="like" href="javascript:deleteFeed('+value+')" title="删除"><i class="icon-remove"></i></a>';
		    temp +="<a class=\"like\" href=\"javascript:deleteDataTeam(\'"  +value+   "\')\" title=\"删除\"><i class=\"icon-remove\"></i>删除</a>";
         </shiro:hasPermission>
		
		 return temp;
    }
	
    function retlentFormatter(value, row, index) {
    	return "<a href='javascript:showScheduler(\""+row.ID+"\");' class=\"linkline\" >查看流程</a>";
   	}
    
	function showScheduler(value) {
    	
    	layer_show('申请令流程跟踪',"<%= request.getContextPath() %>/applyDispatchHistory/getJqueryZCLCTList.do?",'500');
   	}
    
	function editDataTeam(obj){
		
		layer_show('修改',"<%= request.getContextPath() %>/dataTeam/updateDataTeamList.do?teamid="+obj,'500')
	}
	
	function deleteDataTeam(obj){
		
		layer.confirm('确认删除吗?',{btn:['是','否']},function(){
			 var url="<%=path %>/dataTeam/deleteDataTeam.do?teamid="+obj;
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

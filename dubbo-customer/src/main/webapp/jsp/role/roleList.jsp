<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>


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
		<div class="col-sm-12"> 角色名称：
			
			 <input type="text" class="input-text" style="width:250px" placeholder="输入关键词" id="rolename" name="rolename">
			<button onclick="search()" class="btn btn-success radius"  name=""><i class="icon-search"></i> 查询</button>
			<shiro:hasPermission name="/jsp/role/addRole">
            <%--  <span><a class="btn btn-primary radius"  onclick="layer_show('添加','<%= request.getContextPath() %>/jsp/role/addRole.jsp','600','400')"><i class="icon-edit"></i> 添加</a> </span> --%>
           <span><a class="btn btn-primary radius"  onclick="add()"><i class="icon-edit"></i> 添加角色</a> </span>
            </shiro:hasPermission>
          <%--   <shiro:hasPermission name="/jsp/role/updateRole">
			  <span><a class="btn btn-primary radius"  onclick="javascript:updateRole()"><i class="icon-edit"></i> 修改</a> </span>
            </shiro:hasPermission>
            <shiro:hasPermission name="/jsp/role/delRole">
			  <span><a class="btn btn-primary radius"  onclick="javascript:deleteRole()"><i class="icon-remove"></i> 删除</a> </span>
            </shiro:hasPermission> --%>
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
//	alert("dddd");
	    $table.bootstrapTable({
	        height: getHeight(),
	       
	        method: 'post',
	        url:"<%=path%>/rolemanage/getRoleManageList.do",
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
	        columns: [
	            [
	               {
  
	                    field: 'ID',
	                    checkbox: true,
	                    align: 'center'
	                }, {
	                    title: '角色名称',
	                    field: 'ROLENAME',
	                    align: 'center'
	                }, {
	                    title: '组织名称',
	                    field: 'ORGNAME',
	                    align: 'center'
	             
	                },  
	                 {
	                    title: '备注',
	                    field: 'NOTE',
	                    align: 'center'	                
	                },  
	                {
	                    title: '操作',
	                    field: 'ID2',
	                    align: 'center',
	                    formatter: operateFormatter
	                }
	            ]
	        ]
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
		return {rows: params.limit,page: params.pageNumber,rolename:$("#rolename").val()};
	} 
	function getHeight() {
	    return $(window).height()-80;
	}
	function getWidth(){
		return $(window).width()-210;
	}
	
			function add(){
	//	alert(obj);
		layer_show('添加',"<%= request.getContextPath() %>/rolemanage/selectAll.do",'600');
	}
	
	
	function search(){
	    $table.bootstrapTable('refresh');
    }
	
	function operateFormatter(value, row, index) {
		 var temp= "";	
         <shiro:hasPermission name="/jsp/role/rolelock">
		    temp += '  <a class="like" href="javascript:openPermissionsWin('+value+')" title="权限"><i class="icon-lock"></i></a>';
		  /*   temp += '  <a class="like" href="javascript:deletePicture('+value+')" title="删除"><i class="icon-remove"></i></a>'; */
         </shiro:hasPermission>
		  <shiro:hasPermission name="/jsp/role/updateRole">
			   temp += '  <a class="like" href="javascript:updateRole('+value+')" title="修改"><i class="icon-edit"></i></a>';
            </shiro:hasPermission>
            <shiro:hasPermission name="/jsp/role/delRole">
             temp += '  <a class="like" href="javascript:deleteRole('+value+')" title="删除"><i class="icon-remove"></i></a>';
          </shiro:hasPermission>
		 return temp;
    }
		function openPermissionsWin(id){
			layer_show('分配权限',"<%= request.getContextPath() %>/rolemanage/initPermissions.do?roleid="+id,400)
			}
	
	
	

	
	function updateRole(id){
	layer_show('修改',"<%= request.getContextPath() %>/rolemanage/updateRole.do?roleid="+id,'500')
	}
	
	function deleteRole(id){
	//alert(id);
 	/* var row = $('#table').bootstrapTable('getSelections', function (row) {
          return row;
	});
	 var json=JSON.stringify(row);
	  if(row.length==0||row.length>1){
	 alert("请选中一行!");
	 return;
	 }
   
    for (var p in row) {//遍历json数组时，这么写p为索引，0,1
    var id=row[p].ID2;
}
    */
    
		layer.confirm('确认删除该项',{btn:['是','否']},function(){
			 var url="<%=path %>/rolemanage/deleteRole.do?id="+id;
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

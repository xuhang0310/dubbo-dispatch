<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <title>后台管理系统</title>
       
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
        <%@ include file="/jsp/header.jsp"%>
         <link rel="stylesheet" href="<%=path%>/css/font-awesome.min.css" />
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/ligerui/ligerui-all.css"/>
		<link href="<%=path %>/css/ionicons.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/ligure/jquery.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/js/ligure/base.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/js/ligure/ligerui.all.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/js/ligure/json2.js"></script>
	    <script type="text/javascript" src="<%= request.getContextPath() %>/js/ligure/ligerTree.js"></script>
		<%-- <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script> --%>
       <script type="text/javascript">
			var mytree;
		//	alert("树");
	//		alert('${role.rolepermission }');
			
			$(function () {
				init();
	        });
	        function init() {
	            var _treeId = "#lefttree";
	            var data = eval("("+"${role.permissionTreeData}"+")");
	   //         alert(data);
	      //      alert(JSON.stringify(data);)
	            mytree = $(_treeId).ligerTree({
	                nodeWidth: '100%',
	                data: data,
	                checkbox: true,
	                idFieldName: 'menuid',
					parentIDFieldName :'parentid',
	                slide: false
	            }); 
	        }
	        
	        function doSavePermissions(){
	   //     alert("保存3333");
	        	var array = mytree.getCheckedCustom();
	        	var menuids = "";
	        	$.each(array,function(index,node){
	        		if(index>0) menuids += ",";
	        		menuids += node.data.menuid;
	        	});
	        	
				var url = "<%=path%>/system/SysRoleAction!execUpdateAjax.do";
				var param = {
					"roleid":"${role.roleid}",
					/* "pageConfig.uname":"${pageConfig.uname}", */
					"permissions": menuids
				}
			//	alert(JSON.stringify(param));
	        /* 	$.post(url,param,function(data){ 
					if(data.status=="y"){
						window.parent.parent.success(data.info);
						parent.closewin();
					}else if(data.status=="n"){
						window.parent.parent.failure(data.info);
					}else{
						window.parent.parent.error();
					}
				}, "json"); */
				
			 var url="<%=path %>/rolemanage/updateRolePermission.do";
       	     $.post(url,param,function(data){
       	     //alert("jdsdsd");
       	     refresh();
       	    //	 window.parent.parent.success("保存成功");
		  //		parent.closewin();
				
       	     },'json')
	        }
		   
		  		
		   function cancle(){
			   var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
		   }
		   function refresh(){
		     alert("保存成功");
			   parent.search();
				  var index = parent.layer.getFrameIndex(window.name);
				  parent.layer.close(index);
		   }
		</script>
        
       
</head>
  
<body>
		<!-- <form action="" method="post" name="myform" id="myform"> -->
		<form action="" method="post" class="form form-horizontal" id="form">
			
			<input type="hidden" id="roleid" name="role.roleid" value="${role.roleid}"/>
			<input type="hidden" id="permissions" name="role.permissions" value="${role.permissions}"/>
			<input type="hidden" id="permissions" name="role.permissions" value="${role.permissionTreeData}"/>
			<div id="lefttree"></div> 
			<div style="float:left;height:36px;">&nbsp;</div>
			
			<div class="b-button">
			    <input type="button" value="保存" onclick="doSavePermissions()" class="button save" />
			   <!--  <input type="button" value="返回" onclick="parent.closewin();" class="button back" /> -->
			 <!--   <input type="button" value="返回" onclick="close2()" class="button back" /> -->
			</div>
		</form>
	

</body>
</html>


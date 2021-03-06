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
		
		<script type="text/javascript" src="<%=path%>/plugins/zTree_v3/js/jquery.ztree.core.min.js"></script>
        <script type="text/javascript" src="<%=path%>/plugins/zTree_v3/js/jquery.ztree.excheck.min.js"></script>
        <script type="text/javascript" src="<%=path%>/plugins/zTree_v3/js/jquery.ztree.exedit.min.js"></script>
        <script type="text/javascript" src="<%=path%>/plugins/layout/jquery.layout.js"></script>
        <script type="text/javascript">
        function setFontCss(treeId, treeNode) { 
            return treeNode.level == 1 ? {color:"red"} : {}; 
     	}; 
     
        var setting = {
        		data: { 
                    simpleData: { 
                        enable: true,//如果设置为 true，请务必设置 setting.data.simpleData 内的其他参数: idKey / pIdKey / rootPId，并且让数据满足父子关系。 
                        idKey: "id", 
                        pIdKey: "pid", 
                        rootPId: 0 
                    },
                    key: {
	                    name: "title",
	                    url:""
                    }
				}, 
                view: { 
                    showLine: true,//显示连接线 
                    showIcon: true//显示节点图片 
                    
                }, 
                async: {    //ztree异步请求数据 
                    enable: true, 
                    url: "<%=path%>/org/getSysOrgTreeData.do",//请求action方法 
                    autoparam:["id"] 
                },
                callback:{
                     beforeClick: zTreeBeforeClick,
                     onAsyncSuccess: zTreeOnAsyncSuccess

                }
        }
        
      //启动树节点     
        $(function($){ 
           
            $.fn.zTree.init($("#treeDemo"), setting); 
        });
         
        function zTreeOnAsyncSuccess(){
           // alert('加载树成功');
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            treeObj.expandAll(true);

        }
        
        function zTreeBeforeClick(treeId, treeNode, clickFlag) {
        	
        }
        
        </script>
        <script type="text/javascript">
	$(document).ready(function(){
		$("body").layout({ applyDemoStyles: true });
	});
	</script>
    </head>
  
  <body  >

  <div class="ui-layout-west">
	    
	                <ul id="treeDemo" class="ztree" style="width:152px;overflow-y:auto;background:#fff;border:0px;"></ul>
	     
  </div>

  <div class="ui-layout-center">
	    <div class="row">
			<div class="col-sm-12"> 组织机构名称：
				
				 <input type="text" class="input-text" style="width:250px" placeholder="输入关键词" id="username" name="username">
				<button onclick="search()" class="btn btn-success radius"  name=""><i class="icon-search"></i> 查询</button>
				<shiro:hasPermission name="/jsp/org/addOrg">
				  <span><a class="btn btn-primary radius"  onclick="layer_show('添加组织结构','<%= request.getContextPath() %>/jsp/user/eidtUser.jsp','600')"><i class="icon-edit"></i> 添加用户</a> </span>
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

	 
   </div>

  
 
	<script type="text/javascript">
	var $table = $('#table'),
    $remove = $('#remove'),
    selections = [];

	$(function () {
	    $table.bootstrapTable({
	        height: getHeight(),
	       
	        method: 'post',
	        url:"<%=path%>/org/getOrgList.do",
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
	                    
	                    field: 'ORGID',
	                    checkbox: true,
	                    align: 'center'
	                }, {
	                    title: '组织机构名称',
	                    field: 'ORGNAME',
	                    align: 'center'
	                }, {
	                    title: '上级单位名称',
	                    field: 'PNAME',
	                    align: 'center'
	                }, {
	                    title: '备注',
	                    field: 'ROLENAME',
	                    align: 'center'
	                },  {
	                    title: '操作',
	                    field: 'NOTE1',
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
		return {rows: params.limit,page: params.pageNumber,username:$("#username").val()};
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
		 var temp= '';
		 <shiro:hasPermission name="/jsp/org/updateOrg">
		    temp +='<a class="like" href="javascript:editUser('+value+')" title="编辑"><i class="icon-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;   ';
         </shiro:hasPermission>
         
         <shiro:hasPermission name="/jsp/org/deleteOrg">
		    temp += '  <a class="like" href="javascript:deleteUser('+value+')" title="删除"><i class="icon-remove"></i></a>';
         </shiro:hasPermission>
		
		 return temp;
    }
	
	function editUser(obj){
		
		layer_show('修改用户','<%= request.getContextPath() %>/jsp/user/eidtUser.jsp','500')
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

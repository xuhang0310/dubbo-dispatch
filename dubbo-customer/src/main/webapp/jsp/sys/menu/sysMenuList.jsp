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
		
		
		<script type="text/javascript" src="<%=path%>/plugins/layout/jquery.layout.js"></script>
        <script type="text/javascript">
        /* function setFontCss(treeId, treeNode) { 
            return treeNode.level == 1 ? {color:"red"} : {}; 
     	};  */
     
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
                    url: "<%=path%>/menu/getSysMenuTreeData.do?dotretype=null",//请求action方法 
                    autoparam:["id"] 
                },
                callback:{
                     beforeClick: zTreeBeforeClick,
                     onAsyncSuccess: zTreeOnAsyncSuccess,
                     onClick: zTreeOnClick

                }
        }
        
      //启动树节点     
        $(function($){ 
           
            $.fn.zTree.init($("#treeDemo"), setting); 
        });
         
        function zTreeOnAsyncSuccess(){
           // alert('加载树成功');
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            treeObj.expandAll(false);

        }
        
        function zTreeBeforeClick(treeId, treeNode, clickFlag) {
        //	alert(treeNode.id+"==="+treeNode.pid+"==="+treeNode.title);
        }
        
        function zTreeOnClick(event, treeId, treeNode, clickFlag){
        	//debugger;
        	
        	//alert(treeNode.id);
        	$("#menuid").val(treeNode.id);
        	$("#menuname").val("");
        	search();
        }
        
        </script>
       
       
    </head>
  
  <body style="margin:10px" >
 <div class="row">
  	<div class="col-sm-2">
	    
	      <ul id="treeDemo" class="ztree" style="height:500px;width:180px;overflow-y:auto;border:0px;"></ul>
	     
  	</div>
     
    <div class="col-sm-10">
			<div class="row">
						<div class="col-sm-12"> 
						 <form id="listForm" name="listForm">
							
								 <input type="hidden" class="input-text" style="width:250px" placeholder="输入关键词" id="menuid" name="menuid">
								 <input type="text" class="input-text" style="width:250px" placeholder="请输入菜单名称" id="menuname" name="menuname">
								<a href="javascript:search()" class="btn btn-success radius" ><i class="icon-search"></i> 查询</a>
								<shiro:hasPermission name="/jsp/sys/menu/addMenu">
								  <span><a class="btn btn-primary radius"  onclick="layer_show('添加菜单','<%= request.getContextPath() %>/menu/addMenuList.do','500')"><i class="icon-edit"></i> 添加菜单</a> </span>
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
	        url:"<%=path%>/menu/getMenuList.do",
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
		            	title : '序号',
		            	align: "center",
		            	width: 40,
		            	formatter: function (value, row, index) {
		            	    //获取每页显示的数量
		            	    var pageSize=$('#table').bootstrapTable('getOptions').pageSize;
		            	    //获取当前是第几页
		            	    var pageNumber=$('#table').bootstrapTable('getOptions').pageNumber;
		            	    //返回序号，注意index是从0开始的，所以要加上1
		            	    return pageSize * (pageNumber - 1) + index + 1;
		            	}
	               },{
	                    field: 'MENUID',
	                    checkbox: true,
	                    align: 'center'
	                }, {
	                    title: '菜单ID',
	                    field: 'CODE',
	                    align: 'center'
	                }, {
	                    title: '父级菜单',
	                    field: 'PARENTNAME',
	                    align: 'center'
	                }, {
	                    title: '菜单名称',
	                    field: 'MENUNAME',
	                    align: 'center'
	                }, {
	                    title: 'URL',
	                    field: 'MENUURL',
	                    align: 'center'
	                }, {
	                    title: '左侧树URL',
	                    field: 'TREEURL',
	                    align: 'center'
	                }, {
	                    title: '图标',
	                    field: 'ICONCLASS',
	                    align: 'center'
	                }, {
	                    title: '菜单级别',
	                    field: 'MENULEVEL',
	                    align: 'center'
	                }, {
	                    title: '排序',
	                    field: 'ORDERID',
	                    align: 'center'
	                },  {
	                    title: '操作',
	                    field: 'CODE',
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
		 <shiro:hasPermission name="/jsp/sys/menu/updateMenu">
		    temp +="<a class=\"like\" href=\"javascript:editMenu(\'"  +value+   "\')\" title=\"编辑\"><i class=\"icon-edit\"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;";
         </shiro:hasPermission>
         
         <shiro:hasPermission name="/jsp/sys/menu/deleteMenu">
		    //temp += '  <a class="like" href="javascript:deleteFeed('+value+')" title="删除"><i class="icon-remove"></i></a>';
		    temp +="<a class=\"like\" href=\"javascript:deleteMenu(\'"  +value+   "\')\" title=\"删除\"><i class=\"icon-remove\"></i></a>";
         </shiro:hasPermission>
		
		 return temp;
    }
	
	function editMenu(obj){
		
		layer_show('修改菜单',"<%= request.getContextPath() %>/menu/updateMenuList.do?menuid="+obj,'500')
	}
	
	function deleteMenu(obj){
		
		layer.confirm('确认删除该菜单',{btn:['是','否']},function(){
			 var url="<%=path %>/menu/deleteMenu.do?menuid="+obj;
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

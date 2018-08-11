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
		<div class="col-sm-12"> 名称：
			
			 <input type="text" class="input-text" style="width:250px" placeholder="输入关键词" id="picturename" name="picturename">
			<button onclick="search()" class="btn btn-success radius"  name=""><i class="icon-search"></i> 查询</button>
			<shiro:hasPermission name="/jsp/picture/addPicture">
			  <span><a class="btn btn-primary radius"  onclick="layer_show('添加工艺图','<%= request.getContextPath() %>/jsp/picture/addPicture.jsp','600','300')"><i class="icon-edit"></i> 添加</a> </span>
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
	//var name=$("#picturename").val();
	//alert(name);
	$(function () {
	    $table.bootstrapTable({
	        height: getHeight(),
	       
	        method: 'post',
	        url:"<%=path%>/picture/getPictureList.do",
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
	                    title: '工艺图ID',
	                    field: 'VIEW1',
	                    align: 'center'
	                }, {
	                    title: '图片名称',
	                    field: 'PIC_NAME',
	                    align: 'center'
	                }, {
	                    title: '图片文件',
	                    field: 'PIC_FNAME',
	                    align: 'center'	           
	                },      
	                {
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
		return {rows: params.limit,page: params.pageNumber,picturename:$("#picturename").val()};
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
		 var temp= "";	
		// alert(row.PIC_FNAME);	
		var obj=row.PIC_FNAME;

		<shiro:hasPermission name="/jsp/picture/viewPicture">
		/*     temp +='<a class="like" href="javascript:viewPicture('+row+')" title="查看"><i class="icon-picture"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;   '; */
         temp +="<a class=\"like\" href=\"javascript:viewPicture(\'"  +obj+   "\')\" title=\"预览\"><i class=\"icon-picture\"></i>预览</a>&nbsp;&nbsp;&nbsp;&nbsp;   ";
        </shiro:hasPermission>
          <shiro:hasPermission name="/jsp/picture/updatePicture">
		    temp += '  <a class="like" href="javascript:updatePicture('+value+')" title="修改"><i class="icon-edit"></i>修改</a>';//icon-search  icon-remove
         </shiro:hasPermission>
         <shiro:hasPermission name="/jsp/picture/deletePicture">
		    temp += '  <a class="like" href="javascript:deletePicture('+value+')" title="删除"><i class="icon-remove"></i>删除</a>';//icon-search  icon-remove
         </shiro:hasPermission>
		
		 return temp;
    }
	
	
	
	
		function viewPicture(obj){
	//	alert(obj);
	
	layer_show('图片预览','<%= request.getContextPath() %>/uplode/'+obj,1050);
	}
	function deletePicture(id){
	//alert(id);
		layer.confirm('确认删除该项',{btn:['是','否']},function(){
			 var url="<%=path %>/picture/deletePicture.do?id="+id;
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
	
			function updatePicture(id){
				/* 	var row = $('#table').bootstrapTable('getSelections', function (row) {
			          return row;
					});
				  for (var p in row) {//遍历json数组时，这么写p为索引，0,1
				    var id=row[p].NOTE1;
				}
			
				 //alert(row.length);  
				 if(row.length==0||row.length>1){
				 alert("请选中一行!");
				 return;
				 }
			 */
				layer_show('修改',"<%= request.getContextPath() %>/picture/updatePicture.do?roleid="+id,'600',300)
	}
	
	
	    
    </script> 
  </body>
</html>

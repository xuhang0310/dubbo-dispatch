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
			 <input type="text" class="input-text" style="width:250px" placeholder="条件sql" id="tablesql" name="tablesql">
			 <span><a class="btn btn-success radius" href="javascript:search()"><i class="icon-search"></i>查询</a></span>
			<shiro:hasPermission name="/jsp/defect/addMailWarnConfig">
			  <span><a class="btn btn-primary radius"  onclick="layer_show('添加报警','<%= request.getContextPath() %>/mailWarnConfig/addMailWarnConfigList.do','800','500')"><i class="icon-edit"></i> 添加报警</a> </span>
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
	        url:"<%=path%>/mailWarnConfig/getMailWarnConfigList.do",
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
		 <shiro:hasPermission name="/jsp/defect/updateMailWarnConfig">
		    temp +="<a class=\"like\" href=\"javascript:editMailWarnConfig(\'"  +value+   "\')\" title=\"编辑\"><i class=\"icon-edit\"></i>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;   ";
         </shiro:hasPermission>
         
         <shiro:hasPermission name="/jsp/defect/deleteMailWarnConfig">
		    //temp += '  <a class="like" href="javascript:deleteFeed('+value+')" title="删除"><i class="icon-remove"></i></a>';
		    temp +="<a class=\"like\" href=\"javascript:deleteMailWarnConfig(\'"  +value+   "\')\" title=\"删除\"><i class=\"icon-remove\"></i>删除</a>";
         </shiro:hasPermission>
		
		 return temp;
    }
	
	function retlentFormatter(value, row, index) {
		 var ret = value.split("");
		 if(ret.length > 38){
			 value=value.substring(0,37)+"<a class=\"like\"  href=\"javascript:wjhzb(\'"+value+"\')\" title=\"更多\">...更多</a>";
		 }
		
		 return value;
   	}
	function wjhzb(value) {
		
		layer.alert(value, {skin: 'layui-layer-molv',closeBtn: 1,shift: 4,title: "详细内容" });
		
   	}
	
	function operateEmail(value,row,index){
//			return value;
		return "<a href=\"javascript:showEmail('"+(value==null?'':value)+"','"+row.CODE+"')\" class=\"icon-edit\">操作</a>";
	}
    
    function showEmail(email,id){
    	//alert(id);
    	var page=new Page();
    	var url = page.findBaseParam("<%=path%>/system/MailWarnConfigAction!updateEmail.do")+"&mailWarnConfig.id="+id+"&mailWarnConfig.email="+email;
    	mywin = $.ligerDialog.open({ title:"设置收件人邮箱",width:400,height: 420, url: url});
    }
    function renderTrigernum(value,row,index){/* debugger; */
    	
    	return "count(条件sql)"+row.OPERATOR+value;
    }
	
	function editMailWarnConfig(obj){
		
		layer_show('修改',"<%= request.getContextPath() %>/mailWarnConfig/updateMailWarnConfigList.do?id="+obj,'900','500')
	}
	
	function deleteMailWarnConfig(obj){
		
		layer.confirm('确认删除该热源',{btn:['是','否']},function(){
			 var url="<%=path %>/mailWarnConfig/deleteMailWarnConfig.do?id="+obj;
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

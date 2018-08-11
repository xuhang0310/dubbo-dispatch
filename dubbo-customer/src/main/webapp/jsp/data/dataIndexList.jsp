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
			 <input type="hidden"  style="width:250px"  id="schedulerid" name="schedulerid" value="${schedulerid }">
			 <input type="hidden"  style="width:250px"  id="selectmaxsorder" name="selectmaxsorder" value="${selectmaxsorder }">
			 <input type="text" class="input-text" style="width:250px" placeholder="请输入工作内容" id="content" name="content">
			 时间：<input type="text" onclick="WdatePicker()" id="startdate" name="startdate" value="${startdate }" class="input-text Wdate" style="width:124px;">
			 一  <input type="text" onclick="WdatePicker()" id="enddate" name="enddate" value="${enddate }" class="input-text Wdate" style="width:124px;">
			 类型:<input type="radio" name="state" value="2" class="pq_param" checked="checked"/>值班明细
					<input type="radio" name="state" value="4" class="pq_param"/>重大事项
			 <span><a class="btn btn-success radius" href="javascript:search()"><i class="icon-search"></i>查询</a></span>
			<shiro:hasPermission name="/jsp/data/addDataIndex">
			  <span><a class="btn btn-primary radius"  onclick="layer_show('添加值班明细','<%= request.getContextPath() %>/dataIndex/addDataIndexList.do?selectmaxsorder=${selectmaxsorder }&&schedulerid=${schedulerid }','500')"><i class="icon-edit"></i> 添加值班明细</a> </span>
            </shiro:hasPermission>
            <shiro:hasPermission name="/jsp/data/addZDDataIndex">
			  <span><a class="btn btn-primary radius"  onclick="layer_show('添加重大事项','<%= request.getContextPath() %>/dataIndex/addZDDataIndexList.do?selectmaxsorder=${selectmaxsorder }&&schedulerid=${schedulerid }','500')"><i class="icon-edit"></i> 添加重大事项</a> </span>
            </shiro:hasPermission>
            <shiro:hasPermission name="/jsp/data/addData">
			  <span><a class="btn btn-primary action"  onclick="layer_show('交接班管理','<%= request.getContextPath() %>/dataIndex/addDataList.do?selectmaxsorder=${selectmaxsorder }&&schedulerid=${schedulerid }','800','500')"><i class="icon-edit"></i> 交接班管理</a> </span>
            </shiro:hasPermission>
            </form>
		</div>
    </div>
    
    <div class="row analyse" >
    	<ul>
    		  <li style="width:270px">
		         <span class="w90">值班负责人：</span>
		         <span class="fwd cpurple">${teamList.PRINCIPALDIS }</span>
		      </li>
		      <li style="width:270px">
		         <span class="w90">值班人：</span>
		         <span class="fwd cblue">${teamList.WATCHNAME }</span>
		      <li style="width:170px">
		         <span class="w90">注意事项：</span>
		         <span class="fwd cyellow">66</span>
		      </li>
		      <li style="width:170px">
		         <span class="w90">重大事项：</span>
		         <span class="fwd cred">1</span>
		      </li>
		     
    	 </ul> 
    </div>
    

    <div class="row" style="margin-top:10px;">
         <div class="col-sm-12">
         
              <!-- 检索  -->
			
			  <div class="tab-pane active">
					<!-- 检索  -->
			   		<table id="table" style="table-layout:fixed;"
			           data-id-field="id"
			           data-click-to-select="true"
			           data-page-list="[10, 25, 50, 100]">
		           </table>
			  </div>
         
         </div>
			
			
          
	  </div>
 
 	<script type="text/javascript"></script>
	<script type="text/javascript">
	var $table = $('#table'),
    $remove = $('#remove'),
    selections = [];

	$(function () {
		var columns =eval(${jsonTableGrid});
	    $table.bootstrapTable({
	        height: getHeight(),
	       
	        method: 'post',
	        url:"<%=path%>/dataIndex/getDataIndexList.do",
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
	    return $(window).height()-100;
	}
	function getWidth(){
		return $(window).width()-210;
	}
	
	function search(){
	    $table.bootstrapTable('refresh');
    }
	
	function operateFormatter(value, row, index) {
		 var temp= '';
		 <shiro:hasPermission name="/jsp/data/updateDataIndex">
		    temp +="<a class=\"like\" href=\"javascript:editDataIndex(\'"  +value+   "\')\" title=\"编辑\"><i class=\"icon-edit\"></i>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;   ";
         </shiro:hasPermission>
         
         <shiro:hasPermission name="/jsp/data/deleteDataIndex">
		    //temp += '  <a class="like" href="javascript:deleteFeed('+value+')" title="删除"><i class="icon-remove"></i></a>';
		    temp +="<a class=\"like\" href=\"javascript:deleteDataIndex(\'"  +value+   "\')\" title=\"删除\"><i class=\"icon-remove\"></i>删除</a>";
         </shiro:hasPermission>
		
		 return temp;
    }
	
	function retlentFormatter(value, row, index) {
		 var ret = value.split("");
		 if(ret.length > 8){
			 value=value.substring(0,7)+"<a class=\"like\"  href=\"javascript:wjhzb(\'"+value+"\')\" title=\"更多\">...更多</a>";
		 }
		
		 return value;
    }
	
	function wjhzb(value) {
		
		layer.alert(value, {skin: 'layui-layer-molv',closeBtn: 1,shift: 4,title: "工作内容" });
		
   	}
	
	function editDataIndex(obj){
		var allTableData = $("#table").bootstrapTable('getSelections',function(row){
			
			return row;
		});
		for(var i=0;i<allTableData.length;i++){
			if(allTableData[i].STYPENAME == "重大事项"){
				layer.alert("重大事项不可修改！ ", {skin: 'layui-layer-molv',closeBtn: 1,shift: 4,title: "提示" });
				return;
			}
		} 
		//
		for(var i=0;i<allTableData.length;i++){
			if(allTableData[i].SCHEDULERID!=$("#schedulerid").val()){
				layer.alert("已更换班组不能修改！ ", {skin: 'layui-layer-molv',closeBtn: 1,shift: 4,title: "提示" });
				if (i+1==allTableData.length) {
					return;
				}
			}
		} 
		
		if(allTableData[0].CODE!=null&&allTableData[0].CODE!=""){
			layer_show('修改',"<%= request.getContextPath() %>/dataIndex/updateDataIndexList.do?codeid="+obj,'500')
		}
		
	}
	
	function deleteDataIndex(obj){
		var allTableData = $("#table").bootstrapTable('getSelections',function(row){
			
			return row;
		});
		
		for(var i=0;i<allTableData.length;i++){
			//alert(allTableData[i].STYPENAME);
			if(allTableData[i].STYPENAME == "重大事项"){
				layer.alert("重大事项不可删除！ ", {skin: 'layui-layer-molv',closeBtn: 1,shift: 4,title: "提示" });
				return;
			}
		} 
		//
		for(var i=0;i<allTableData.length;i++){
			//alert(allTableData[i].SCHEDULERID);
			if(allTableData[i].SCHEDULERID!=$("#schedulerid").val()){
				layer.alert("已更换班组不能删除！ ", {skin: 'layui-layer-molv',closeBtn: 1,shift: 4,title: "提示" });
				if (i+1==allTableData.length) {
					return;
				}
				//return;
			}
		} 
		
		if(allTableData[0].CODE!=null&&allTableData[0].CODE!=""){
			layer.confirm('确认删除该记录',{btn:['是','否']},function(){
				 var url="<%=path %>/dataIndex/deleteDataIndex.do?codeid="+obj;
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
		
		
	}
	
	
	
	
	    
    </script> 
  </body>
</html>

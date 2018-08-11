<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>报警分析</title>
       
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
       <%@ include file="/jsp/header.jsp"%>
       <!-- ace styles -->
		
		
       
    </head>
  
  <body style="margin:10px;overflow:hidden;" >
     
    <div class="row">
		<div class="col-sm-12">
			<form id="listForm" name="listForm" action="#">	
			 <select class="selectpicker" data-live-search="true"   name="feedname" id="feedid" title="请选择热源" >
		             <c:forEach var="temp" items="${feedList }">
		             		 <option value="${temp.FEEDCODE }" <c:if test="${temp.FEEDCODE==feedcode }" >selected</c:if>>${temp.FEEDNAME }</option>
		             </c:forEach>
	           </select>
			 <select class="selectpicker" data-live-search="true"  name="dateType" id="dateType"  onchange="getTime()" title="请选择时间" >
		             <option value="0" selected>今天</option>
		             <option value="1" >昨天</option>
		             <option value="2" >7天数据</option>
				     <option value="3" >30天数据</option>
				     <option value="4" >采暖期数据</option>
				     <option value="5" >自定义</option>
	            </select>
	            
	            <span id="dateCustom" style="display:none;">
			        <input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}'})"  name="startdate" id="datemin"  value="${ paramsMap.startdate}" class="input-text Wdate" style="width:120px;">
				    -
				    <input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d'})" name="enddate" id="datemax" value="${ paramsMap.enddate}" class="input-text Wdate" style="width:120px;">
			   </span>
			 <span><a class="btn btn-success radius" href="javascript:search()"><i class="icon-search"></i>查询</a></span>
			<%-- <shiro:hasPermission name="/jsp/data/addDataTeam">
			  <span><a class="btn btn-primary radius"  onclick="layer_show('添加班组','<%= request.getContextPath() %>/dataTeam/addDataTeamList.do','500')"><i class="icon-edit"></i> 添加班组</a> </span>
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
 
	<script type="text/javascript">
	var $table = $('#table'),
    $remove = $('#remove'),
    selections = [];

	$(function () {
		$('#dateType').selectpicker({
			  width:80
		});
		
		var columns =eval(${jsonTableGrid});
	    $table.bootstrapTable({
	        height: getHeight(),
	       
	        method: 'post',
	        url:"<%=path%>/alarmAnalysis/getAlarmAnalysisList.do",
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
	
	function operateCadername(value, row, index) {
		 var temp='';
		 /* temp +="<span class=\"label label-sm label-warning\">"  +value+   "</span>"; */
		 temp +="<span class=\"label label-sm label-warning\" style='color:#fff;'>"  +value+   "</span>";
		
		 return temp;
   	}
	
	function operateTabz(value, row, index) {
		 var temp='';
		 temp +="<button class=\"label label-sm label-warning\"><i class=\"icon-flag bigger-120\"></i></button>";
		
		 return temp;
  	}
	
	function editDataTeam(obj){
		
		layer_show('修改热源',"<%= request.getContextPath() %>/dataTeam/updateDataTeamList.do?teamid="+obj,'500')
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
	
	function getTime(){
		var temp=$("#dateType").val();
		if(temp=="5"){
			$("#dateCustom").show();
		}else{
			$("#dateCustom").hide();
		}
		if(temp=="0"||temp=="1"||temp=="5"){
			$("#timeSearchType").text("分");
		}else if(temp=="2"||temp=="3"){
			$("#timeSearchType").text("小时");
		}else if(temp=="4"){
			$("#timeSearchType").text("天");
		}
	}
	
	
	
	
	    
    </script> 
  </body>
</html>

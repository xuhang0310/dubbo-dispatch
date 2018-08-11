<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


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
  
  <body style="margin:10px;overflow-x:hidden;" >
     
    <div class="row">
		<div class="col-sm-12">
			<form id="listForm" name="listForm" action="#">	
				<input type="hidden" value="${paramsMap.chartTitle }" name="chartTitle" id="chartTitle"/>
			    <input type="hidden" value="${paramsMap.chartField }" name="chartField" id="chartField"/>
			    <input type="hidden" value="${paramsMap.chartType }" name="chartType" id="chartType"/>
			    <input type="hidden" value="${paramsMap.chartPosition }" name="chartPosition" id="chartPosition"/>
			 <select class="selectpicker" data-live-search="true"   name="nodename" id="nodeid" title="请选择换热站" >
		           <c:forEach var="temp" items="${nodeList }">
		             	<option value="${temp.NODECODE }" >${temp.NODENAME }</option>
		           </c:forEach>
	         </select>
			
			  日期：<input type="text" onclick="WdatePicker()" id="startdate" name="startdate" value="${startdate }" class="input-text Wdate" style="width:200px;">
			  一  <input type="text" onclick="WdatePicker()" id="enddate" name="enddate" value="${enddate }" class="input-text Wdate" style="width:200px;">
			 <span><a class="btn btn-success radius" href="javascript:search()"><i class="icon-search"></i>查询</a></span>
			 
			 <a href="javascript:layer_show('负荷预测图例','<%= request.getContextPath() %>/base/legendChart.do?pageid=4001&chartField='+$('#chartField').val(),'600','200')" class="btn btn-primary radius" ><i class="icon-bar-chart"></i> 图例</a>
			<%-- <shiro:hasPermission name="/jsp/data/addDataTeam">
			  <span><a class="btn btn-primary radius"  onclick="layer_show('添加','<%= request.getContextPath() %>/dataTeam/addDataTeamList.do','500')"><i class="icon-edit"></i> 添加</a> </span>
            </shiro:hasPermission> --%>
            </form>
		</div>
    </div>
    

	
	<div class="pd-10">
  	<div class="row cl">
	    
	      <!-- <ul id="treeDemo" class="ztree" style="height:260px;width:180px;overflow-y:auto;border:0px;"></ul> -->
	      <div  id="allmap" style="height:300px;overflow-y:auto;border:0px;">	
		 <script type="text/javascript">
								    getLineChart();
								    function getLineChart(){
								    	//$("#allmap").width($(window).width());
								    	//$("#allmap").empty();
								    	var index = layer.load(0, {shade: false});
								    	 var datas= $.param({nodecode:$("#nodeid").val()+""}) + '&' + $('#listForm').serialize();
								    	$.ajax({
											  type: 'POST',
											  url: "<%=path%>/forecastCNQpayload/getChart.do",
											  data: datas,
											  success: function(result){
												  var chart=new PrivateEchart();
												  
												    chart.legendName=result.title;;
												    chart.legendType=result.type;
												    chart.legendField=result.column;	
												    chart.yaxisPostion=result.postion;
												    chart.dataZoom=false;
												    chart.targetField="SCADATIME";
												    chart.toolbox=false;
												    chart.xaisData=result.x;
												    chart.chartData=result.data;
												    
												    var option=chart.getLineBarChart();
												   
												    //$("#line1").text(option);
												    renderChart("allmap", option);
												    layer.close(index);  
											  },
											  dataType: 'json'
											});
								    }
								    
								    
								    </script>	
          
	  </div>
	     
  	</div>
     
    <div class="row cl">
    
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
		
		var columns =eval(${jsonTableGrid});
	    $table.bootstrapTable({
	        height: getHeight(),
	       
	        method: 'post',
	        url:"<%=path%>/forecastCNQpayload/getForecastCNQpayloadList.do",
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
	    return $(window).height()-160;
	}
	function getWidth(){
		return $(window).width()-210;
	}
	
	function search(){
		getLineChart();
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

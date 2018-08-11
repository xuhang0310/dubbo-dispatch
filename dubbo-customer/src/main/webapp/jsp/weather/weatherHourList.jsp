<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>生产调度系统</title>
       
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
       <%@ include file="/jsp/header.jsp"%>
 
    </head>
  
<body style="margin:10px;overflow:hidden;" >
     
    <div class="row">
		<div class="col-sm-12">
			<form id="listForm" name="listForm" action="#">	
			<input type="hidden" value="${paramsMap.chartTitle }" name="chartTitle" id="chartTitle"/>
		    <input type="hidden" value="${paramsMap.chartField }" name="chartField" id="chartField"/>
		    <input type="hidden" value="${paramsMap.chartType }" name="chartType" id="chartType"/>
		    <input type="hidden" value="${paramsMap.chartPosition }" name="chartPosition" id="chartPosition"/>
		 日期段：
			 <input type="text" class="input-text Wdate"  value="${paramDate.KSSJ }" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })"style="width:200px"  id="kssj" name="kssj">
			&nbsp;&nbsp;至&nbsp;&nbsp;
			 <input type="text" class="input-text Wdate" value="${paramDate.JSSJ }"  onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })"style="width:200px"  id="jssj" name="jssj">
			 <span><a class="btn btn-success radius" href="javascript:search()"><i class="icon-search"></i>查询</a></span>
			</form>
		</div>
    </div>
    
<table>
<tr>
	<td>
    <div class="row" style="margin-top:10px;width: 350px;" >
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
	  </td>

	  <td >
	  		  <div  id="line" style="height:400px;width:900px;bottom:0px;border:0px;">	
		 <script type="text/javascript">
								    getLineChart()
								    function getLineChart(){
								    	//$("#line").width($(window).width());
								    	//$("#line").empty();
								    	var index = layer.load(0, {shade: false});
								    	 /* var datas= $.param({nodecode:$("#nodeid").val()+""}) + '&' + $('#listForm').serialize(); */
								    	 var datas=$('#listForm').serialize();
								    	$.ajax({
											  type: 'POST',
											  url: "<%=path%>/weather/getChart.do",
											  data: datas,
											  success: function(result){
										//	  alert(JSON.stringify(datas));
												  var chart=new PrivateEchart();
												  
												    chart.legendName=result.title;;
												    chart.legendType=result.type;
												    chart.legendField=result.column;	
												    chart.yaxisPostion=result.postion;
												    chart.dataZoom=false;
												    chart.targetField="READDATE";
												    chart.toolbox=false;
												    chart.xaisData=result.x;
												    chart.chartData=result.data;
												    
												    var option=chart.getLineBarChart();
												   
												    //$("#line1").text(option);
												    renderChart("line", option);
												    layer.close(index);  
											  },
											  dataType: 'json'
											});
								    }
								    
								    
								    </script>	
          
	  </div>
	  
	  	  
	  </td>
	  
	  </tr>
	  </table>
	  
	
 
	<script type="text/javascript">
	var $table = $('#table'),
    $remove = $('#remove'),
    selections = [];

	$(function () {
		 var columns =eval(${jsonTableGrid});
	    $table.bootstrapTable({
	        height: getHeight(),
	       
	        method: 'post',
	        url:"<%=path%>/weather/getWeatherHourList.do",
	        dataType: "json",
	        striped: true,
	        pagination: false,
	        queryParamsType: "limit",
	        contentType: "application/x-www-form-urlencoded",
	     //   pageSize: 20,
	      //  pageNumber:1,
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
	} 
	
	function getHeight() {
	    return $(window).height()-100;
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
		 <shiro:hasPermission name="/jsp/dataparam/updateparam">
		    temp +='<a class="like" href="javascript:editParam('+value+')" title="编辑"><i class="icon-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;   ';
         </shiro:hasPermission>
         
         <shiro:hasPermission name="/jsp/dataparam/deleteparam">
		    temp += '  <a class="like" href="javascript:deleteParam('+value+')" title="删除"><i class="icon-remove"></i></a>';
         </shiro:hasPermission>
		
		 return temp;
    }
	
	
	
	
	function editParam(obj){
	//	alert(obj);
		layer_show('修改',"<%= request.getContextPath() %>/dataparam/findParam.do?id="+obj,'600')
	}
	function deleteParam(obj){
	alert(obj);
		layer.confirm('确认删除',{btn:['是','否']},function(){
			 var url="<%=path %>/dataparam/deleteParam.do?id="+obj;
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

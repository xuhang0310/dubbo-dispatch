<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
		
		
        <script type="text/javascript" src="<%=path%>/plugins/layout/jquery.layout.js"></script>
		
		
  </head>
  
  <body style="margin:10px;overflow:hidden;">
  <input type="hidden" value="${paramsMap.stationid }" name="stationid" id="stationid">
  <input type="hidden" value="${paramsMap.fieldname }" name="fieldname" id="fieldname">
  <input type="hidden" value="${paramsMap.feedname }" name="feedname" id="feedname">
<div id="line"  class="col-sm-12" style="width:800px;;height:300px;"></div>
<script type="text/javascript">
 getLineChart()
 function getLineChart(){
 	
 	$.ajax({
  type: 'POST',
  url: "<%=path%>/feedReal/getWarningConfigChart.do",
  data: {stationid:$("#stationid").val(),fieldname:$("#fieldname").val(),feedname:$("#feedname").val()},
  success: function(result){
	   var chart=new PrivateEchart();
	  
	    chart.legendName=result.title;;
	    chart.legendType=result.type;
	    chart.legendField=result.column;	
	    chart.yaxisPostion=result.postion;
	    chart.targetField=result.targetField;
	    chart.toolbox=true;
	    chart.xaisData=result.x;
	    chart.chartData=result.data;
	    var option=chart.getLineBarChart();
	    renderChart("line", option);
	   // document.write(option);
  },
  dataType: 'json'
});
 }
 
 
 </script>
								    
  </body>
</html>

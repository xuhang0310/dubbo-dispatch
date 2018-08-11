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
		<link href="<%= request.getContextPath() %>/css/ace-tab.css" rel="stylesheet" type="text/css" />
       
     
    </head>
  
  <body style="margin:10px;overflow-x:hidden;">

 

  
	    <div class="row">
			<div class="col-sm-12">
			<form id="listForm" name="listForm" action="#">	
			    <input type="hidden" value="${paramsMap.chartTitle }" name="chartTitle" id="chartTitle"/>
			    <input type="hidden" value="${paramsMap.chartField }" name="chartField" id="chartField"/>
			    <input type="hidden" value="${paramsMap.chartType }" name="chartType" id="chartType"/>
			    <input type="hidden" value="${paramsMap.chartPosition }" name="chartPosition" id="chartPosition"/>
				<select class="selectpicker" data-live-search="true" multiple  name="feedname" id="feedid" title="请选择热源" >
		             <c:forEach var="temp" items="${feedList }">
		             		 <option value="${temp.FEEDCODE }" <c:if test="${temp.FEEDCODE=='8' }" >selected</c:if>>${temp.FEEDNAME }</option>
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
				
				<div class="btn-group">
					<a href="javascript:search()" class="btn btn-success"><i class="icon-search"></i>查询</a>

					<a data-toggle="dropdown" id="timeSearchType" class="btn btn-success dropdown-toggle">
						分钟&nbsp;&nbsp;&nbsp;&nbsp;<span class="icon-caret-down icon-only"></span>
					</a>

					<ul class="dropdown-menu dropdown-success">
						<li>
							<a>分钟</a>
						</li>

						<li>
							<a>小时</a>
						</li>

						<li>
							<a>天</a>
						</li>
					</ul>
				</div>
				<a href="javascript:layer_show('历史数据图例','<%= request.getContextPath() %>/base/legendChart.do?pageid=2003&chartField='+$('#chartField').val(),'600','200')" class="btn btn-primary radius" ><i class="icon-bar-chart"></i> 图例</a>
				<a href="javascript:exportExcel()" class="btn btn-danger radius" ><i class="icon-download"></i> 导出</a>
			</form>	
			</div>
	    </div>
	    
	   
    
	     <div class="row" style="margin-top:10px;">
	          <div class="col-sm-3">
	              <div  class="analyse-box">
	                  <div class="header">&nbsp;&nbsp;&nbsp;统计分析</div>
	                  <div style="height:320px;" class="body">
	                        <ul>
							      <li>
							         <span class="w90">热源数量:</span>
							         <span class="fwd cred" id="rysl"style="float:right" ></span>
							      </li>
							      <li>
							         <span class="w90">供水温度:</span>
							         <span class="fwd cblue" id="pjgswd"style="float:right" ></span>
							      </li>
								   <!--    <li style="width:170px">
								         <span class="w90">地暖：</span>
								         <span class="fwd cgreen"id="dn"></span>%
								      </li> -->
							      <li>
							         <span class="w90">回水温度:</span>
							         <span class="fwd cyellow" id="pjhswd"style="float:right" ></span><!--  style="float:right" -->
							      </li>
							      <li>
							         <span class="w90">供水压力:</span>
							         <span class="fwd cgreen" id="pjgsyl"style="float:right" ></span>
							      </li>
							      <li>
							         <span class="w90">回水压力:</span>
							         <span class="fwd cpurple" id="pjhsyl"style="float:right" ></span>
							      </li>
							      <li>
							         <span class="w90">瞬时流量:</span>
							         <span class="fwd cgreen" id="pjssll"style="float:right" ></span>
							      </li>
							      <li>
							         <span class="w90">瞬时热量:</span>
							         <span class="fwd cyellow" id="pjssrl"style="float:right" ></span>
							      </li>
							      
							<!--       <li >
							         <span class="w90">补水量</span>
							         <span class="fwd cpurple" style="float:right">218万㎡</span>
							      </li> -->
							<!--        <li >
							         <span class="w90">室外温度</span>
							         <span class="fwd cgreen" style="float:right">44.66%</span>
							      </li> -->
							  <!--     <li >
							         <span class="w90">计划产热量</span>
							         <span class="fwd cpurple" style="float:right">218万㎡</span>
							      </li> -->
							      
					    	 </ul> 
	                  </div>
	              </div>
	          </div>
		      <div class="col-sm-9 analyse-chart" >
		        <div id="line" style="height:350px;width:100%;"></div>
		      </div>
					      
								    <script type="text/javascript">
								    getLineChart()
								    function getLineChart(){
								    	
								    	var index = layer.load(0, {shade: false});
								    	 var datas= $.param({feedcode:$("#feedid").val()+""}) + '&' + $('#listForm').serialize();
								    	$.ajax({
											  type: 'POST',
											  url: "<%=path%>/feedHis/getFeedHistoryChart.do",
											  data: datas,
											  success: function(result){
												  var chart=new PrivateEchart();
												  
												    chart.legendName=result.title;
												    //设置grid
												    chart.grid="x:'3%',y:'5%',x2:'3%' ";
												    chart.legendType=result.type;
												    chart.legendField=result.column;	
												    chart.yaxisPostion=result.postion;
												    chart.dataZoom=false;
												    chart.targetField="SCADATIMESTR";
												    chart.toolbox=false;
												    chart.xaisData=result.x;
												    chart.chartData=result.data;
												   
												    var option=chart.getLineBarChart();
												   
												   // $("#line1").text(option);
												    renderChart("line", option);
												    layer.close(index);  
											  },
											  dataType: 'json'
											});
								    }
								    
								    
								    </script>
	     </div>
	     
	     <div class="row" style="margin-top:10px;">
	         <div class="col-sm-12">
		         <div class="table-responsive">
										<table id="table" class="table divmatnrdesc"
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
		        url:"<%=path%>/feedHis/getFeedHistoryList.do",
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
		 var datas= $.param({rows: params.limit,page: params.pageNumber,feedcode:$("#feedid").val()+""}) + '&' + $('#listForm').serialize();
		 getSummary();
		 return datas;
	} 
	function getSummary(){
		 var datas= $.param({feedcode:$("#feedid").val()+""}) + '&' + $('#listForm').serialize();
		$.ajax({
			  type: 'POST',
			  url: "<%=path%>/feedHis/getFeedSummary.do",
			  data: datas,
			  success: function(result){
			//  alert(JSON.stringify(result));
				  $("#rysl").text(result.NUMS+"个");
		//		  alert(result.SUPPLYTEMP);
			//	  alert(result.SUPPLYTEMP!==null);
				  if(result.SUPPLYTEMP!==null){
				  $("#pjgswd").text(result.SUPPLYTEMP+"℃");
				  }else{
				   $("#pjgswd").text("℃");
				  }
				  if(result.RETURNTEMP!==null){
				  $("#pjhswd").text(result.RETURNTEMP+"℃ ");
				  }else{
				  $("#pjhswd").text("℃ ");
				  }
				  if(result.SUPPLYPRESS!==null){
				  $("#pjgsyl").text(result.SUPPLYPRESS+"Mpa");
				  }else{
				  $("#pjgsyl").text("Mpa");
				  }
				  if(result.RETURNPRESS!==null){
				  $("#pjhsyl").text(result.RETURNPRESS+"Mpa");
				  }else{
				   $("#pjhsyl").text("Mpa");
				  }
				  if(result.SUPPLYFLOW!==null){
				  $("#pjssll").text(result.SUPPLYFLOW+"t/h"); 
				  }else{
				  $("#pjssll").text("t/h"); 
				  }
				  if(result.HEATQUANTITY!==null){
				  $("#pjssrl").text(result.HEATQUANTITY+"GJ/h");  
				  }else{
				   $("#pjssrl").text("GJ/h");  
				  }
			  },
			  dataType: 'json'
			});
	}
	
	function getHeight() {
	    return $(window).height()-140;
	}
	function getWidth(){
		return $(window).width()-180;
	}
	
	function search(){
		  getLineChart();
	    $table.bootstrapTable('refresh');
    }
	
	function operateFormatter(value, row, index) {
		 var temp= '';
		
		
		 return value;
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
	function exportExcel(){
		location.href="<%=path%>/feedHis/exportExcel.do?pageid=${pageid}&"+$.param({feedcode:$("#feedid").val()+""}) + '&' + $('#listForm').serialize();
	}
	
	
	
	    
    </script> 
  </body>
</html>

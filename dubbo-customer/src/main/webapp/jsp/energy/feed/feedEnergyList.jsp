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
      
       
     
    </head>
  
  <body style="margin:10px;">  <!-- overflow:hidden -->

 

  
	    <div class="row">
			<div class="col-sm-12">
			<form id="listForm" name="listForm" action="#">	
			    <input type="hidden" value="" name="chartTitle" id="chartTitle"/>
			    <input type="hidden" value="" name="chartField" id="chartField"/>
			    <input type="hidden" value="" name="chartType" id="chartType"/>
			    <input type="hidden" value="" name="chartPosition" id="chartPosition"/>
				<select class="selectpicker" data-live-search="true" multiple  id="feedid" title="请选择热源" >
		             <c:forEach var="temp" items="${feedList }">
		             		 <option value="${temp.FEEDCODE }">${temp.FEEDNAME }</option>
		             </c:forEach>
	            </select>
	              <select class="selectpicker" data-live-search="true"   name="nhlx" id="nhlx" title="能耗类型" >
		             		 <option value="1" selected="selected">热耗</option>
		             		 <option value="2">电耗</option>
		             		 <option value="3">水耗</option>
	            </select>
				<select class="selectpicker" data-live-search="true"  name="dateType" id="dateType"  onchange="getTime()" title="请选择时间" >
		           
		             <option value="1" selected>昨天</option>
		             <option value="2" >7天数据</option>
				     <option value="3" >30天数据</option>
				     <option value="4" >采暖期数据</option>
				     <option value="5" >自定义</option>
	            </select>
	            
	            <span id="dateCustom" style="display:none;">
			      <%--   <input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}'})" id="startdate"  value="${ paramsMap.startdate}" class="input-text Wdate" style="width:120px;">
				    -
				    <input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d'})" id="enddate" value="${ paramsMap.enddate}" class="input-text Wdate" style="width:120px;">
			  --%>
			   <input type="text" onfocus="WdatePicker()" id="startdate"  name="startdate"value="${ paramsMap.startdate}" class="input-text Wdate" style="width:120px;">
				    -
				    <input type="text" onfocus="WdatePicker()" id="enddate" name="enddate"value="${ paramsMap.enddate}" class="input-text Wdate" style="width:120px;">
			 
			   </span>
				
				
				<a href="javascript:search()" class="btn btn-primary radius"  ><i class="icon-search"></i> 查询</a>
				<a href="javascript:layer_show('能耗数据图例','<%= request.getContextPath() %>/base/legendChart.do?pageid=3001&chartField='+$('#chartField').val(),'600','200')" class="btn btn-primary radius"  ><i class="icon-bar-chart"></i> 图例</a>
				<!-- <a href="javascript:search()" class="btn btn-success"  ><i class="icon-glass"></i> 同期对比</a> -->
				<!-- <a href="javascript:search()" class="btn btn-success"  ><i class="icon-tags"></i> 梯度设置</a> -->
				<a href="javascript:energyGradient()" class="btn btn-success"  ><i class="icon-tags"></i> 梯度设置</a>	
				<!-- <a href="javascript:moneyAnalyse()" class="btn btn-success"  ><i class="icon-money"></i> 经济分析</a> -->
				<a href="javascript:export()" class="btn btn-danger radius" ><i class="icon-download"></i> 导出</a>
			</form>	
			</div>
	    </div>
	    
	     <div class="row analyse" >
		    	<ul>
				      <li style="width:170px">
				         <span class="w90">热源数量：</span>
				         <span id="nums" class="fwd cred"></span>个
				      </li>
				      
				      <li style="width:170px">
				         <span class="w90">产热量：</span>
				         <span id="heat" class="fwd cyellow"></span>GJ
				      </li>
				      <li style="width:170px">
				         <span class="w90">用电量：</span>
				         <span id="power" class="fwd cgreen"></span>Kw
				      </li>
				      <li style="width:170px">
				         <span class="w90">用水量：</span>
				         <span id="water" class="fwd cpurple"></span>t
				      </li>
				      
				      
		    	 </ul> 
    	</div>
    
	    
		
	    <div class="row" style="margin-top:10px;">
	       <div class="col-sm-9 ">
		    	<div class="analyse-chart">
		     		 <div  id="line"style="height:300px;width:100%;"></div>	
		        </div>
	       </div>	    
		   <div class="col-sm-3 analyse-chart">
		         <div id="pie"  style="width:100%;height:300px;"></div>
		   </div> 	   
	       
								    <script type="text/javascript">
								    getLineChart()
								    function getLineChart(){
								    	 var datas= $.param({feedcode:$("#feedid").val()+""}) + '&' + $('#listForm').serialize();
								    	$.ajax({
											  type: 'POST',
											  url: "<%=path%>/feedEnergy/getFeedEnergyChart.do",
											  data: datas,
											  success: function(result){
												  var chart=new PrivateEchart();
												  
												    chart.legendName=result.title;;
												    chart.legendType=result.type;
												    chart.legendField=result.column;	
												    chart.yaxisPostion=result.postion;
												    chart.targetField="FEEDNAME";
												    chart.toolbox=true;
												    chart.xaisData=result.x;
												    chart.chartData=result.data;
												    var option=chart.getLineBarChart();
												    renderChart("line", option);
											  },
											  dataType: 'json'
											});
								    }
								    
								    
								    </script>
								    
								      <script type="text/javascript">
								   getPieChart();
								  //  var xPieData='[{"value":13,"name":"严重低标"},{"value":33,"name":"低标"},{"value":53,"name":"合格"},{"value":23,"name":"超标"},{"value":23,"name":"严重超标"}]';
								  function getPieChart(){   
						    	 var datas= $.param({feedid:$("#feedid").val()+""}) + '&' + $('#listForm').serialize();
						    	$.ajax({
									  type: 'POST',
									  url: "<%=path%>/feedEnergy/getFeedEnergyPieChart.do",
									  data: datas,
									  success: function(result){
											 var yzdb=result[0].NUMS;
											 var db=result[1].NUMS;
											 var hg=result[2].NUMS;
											 var cb=result[3].NUMS;
											 var yzcb=result[4].NUMS;
											 
					
								var myChart = echarts.init(document.getElementById('pie'),'dark');
	                 	       option = {
	                        		backgroundColor:'',
	                        	    tooltip : {
	                        	        trigger: 'item',
	                        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	                        	    },
                        	    legend: {
                        	        orient: 'horizontal',
                        	        left: 'center',
                        	        data: ['严重低标','低标','合格','超标','严重超标'],
                        	         textStyle : {
									fontWeight : 'bold',
									fontSize : 12,
									color:'red'
									}
                        	    },
                        	    series : [
                        	        {
                        	            name: '热单耗',
                        	            type: 'pie',
                        	            radius : '75%',
                        	            center: ['50%', '60%'],
                        	            data:[
                        	                {value:yzdb, name:'严重低标'},
                        	                {value:db, name:'低标'},
                        	                {value:hg, name:'合格'},
                        	                {value:cb, name:'超标'},
                        	                {value:yzcb, name:'严重超标'}
                        	            ],
                        	            labelLine: {
                        	                normal: {
                        	                    show: false
                        	                }
                        	            },
                        	            label: {
                        	                normal: {
                        	                    show: false,
                        	                    position: 'center'
                        	                }
                        	            },
                        	            itemStyle: {
                        	                emphasis: {
                        	                    shadowBlur: 10,
                        	                    shadowOffsetX: 0,
                        	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                        	                }
                        	            }
                        	        }
                        	    ]
                        	};
                        myChart.setOption(option);
								 
											  
											  
											  
											  
											  },
											  dataType: 'json'
											});
								    
								    
								    
								    
								    
								    
								    }
								    
								    
								    </script>	
	      
			    </div>
			    
			    
			     <div class="row" style="margin-top:10px;">
        				 <div class="col-sm-12">
         
					        <div class="tab-pane active" id="grid">
								<!-- 检索  -->
								<div class="table-responsive">
									<table id="table" class="table divmatnrdesc"
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
	$('#dateType').selectpicker({
			  width:150
			});

		$('#feedid').selectpicker({
			  width:180
			});
			$('#nhlx').selectpicker({
			  width:120
			});
		 var columns =eval(${jsonTableGrid});
		    $table.bootstrapTable({
		        height: getHeight(),
		       
		        method: 'post',
		        url:"<%=path%>/feedEnergy/getFeedEnergyList.do",
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
	            height: 300,
	            width:getWidth()
	        });
	    });
	});
	
	function getSummary(){
		 var datas= $.param({feedcode:$("#feedid").val()+""}) + '&' + $('#listForm').serialize();
		$.ajax({
			  type: 'POST',
			  url: "<%=path%>/feedEnergy/getFeedSummaryEnergyList.do",
			  data: datas,
			  success: function(result){
				  $("#nums").text(result.NUMS);
				  $("#heat").text(result.SUMHEAT);
				  $("#water").text(result.WATER);
				  $("#power").text(result.POWER);
			  },
			  dataType: 'json'
			});
	}
	
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
	function getHeight() {
	    return $(window).height()-140;
	}
	function getWidth(){
		return $(window).width()-180;
	}
	
	function search(){
		getLineChart();
		getPieChart();
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
	}
	function moneyAnalyse(){
		alert(1);
	}
	
		//能耗梯度
	function energyGradient(){
	layer_show('修改',"<%= request.getContextPath() %>/feedEnergy/energyGradient.do",'400','350')
	
	}
	
	    
    </script> 
  </body>
</html>

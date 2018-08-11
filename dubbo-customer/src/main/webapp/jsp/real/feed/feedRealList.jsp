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
		<style>
		
		
    
		</style>
		
  </head>
  
  <body style="margin:10px;" ><!-- //overflow:hidden; -->
       <div class="row">
		<div class="col-sm-12">
   <form id="listForm" name="listForm" action="#">	
		<div class="col-sm-12">
			    <input type="hidden" value="" name="chartTitle" id="chartTitle"/>
			    <input type="hidden" value="" name="chartField" id="chartField"/>
			    <input type="hidden" value="" name="chartType" id="chartType"/>
			    <input type="hidden" value="" name="chartPosition" id="chartPosition"/>
			    <input type="hidden" value="" name="sjfh" id="sjfh"/>
			    <input type="hidden" value="" name="feedsjfh" id="feedsjfh"/>
		
	         <select class="selectpicker" data-live-search="true"  name="feedcode" id="feedcode" title="请选择热源" >
	         <option value="" selected="selected">--请选择热源--</option>
	             <c:forEach var="temp" items="${feedList }">
		             		 <option value="${temp.FEEDCODE }" >${temp.FEEDNAME }</option>
		             </c:forEach>
	         </select>
	        
			<a href="javascript:search()" class="btn btn-success radius"  ><i class="icon-search"></i> 查询</a>
			
			<!-- <a href="javascript:search()" class="btn btn-success radius"  ><i class="icon-search"></i> 高级查询</a> -->
			 <a href="javascript:layer_show('热源实时数据图例','<%= request.getContextPath() %>/base/legendChart.do?pageid=2002&chartField='+$('#chartField').val(),'600','200')" class="btn btn-primary radius" ><i class="icon-bar-chart"></i> 图例</a>
			<a href="javascript:exportExcel()" class="btn btn-danger radius" ><i class="icon-download"></i> 导出</a>
			<shiro:hasPermission name="/jsp/real/feed/warnConfig">
				<a href="javascript:warnConfig()" class="btn btn-danger radius"  ><i class="icon-edit"></i> 报警设置</a>
			</shiro:hasPermission>
			
			
		</div>
		</form>
    </div>
    </div>
     <div class="row analyse" >
    	<ul>
		      <li style="width:120px">
		         <span class="w90">热源数量：</span>
		         <span id="rysl"class="fwd cred"></span>个
		      </li>
		    <!--   <li style="width:170px">
		         <span class="w90">超限站数量：</span>
		         <span class="fwd cblue">25</span>个
		      </li> -->
		      <li style="width:150px">
		         <span class="w90">供温平均：</span>
		         <span id="gwpj" class="fwd cyellow"></span>℃
		      </li>
		      <li style="width:150px">
		         <span class="w90">回温平均：</span>
		         <span id="hwpj"class="fwd cgreen"></span>℃
		      </li>
		          <li style="width:160px">
		         <span class="w90">供压平均：</span>
		         <span id="gypj"class="fwd cgreen"></span>Mpa
		      </li>
		        </li>
		          <li style="width:160px">
		         <span class="w90">回压平均：</span>
		         <span id="hypj"class="fwd cgreen"></span>Mpa
		      </li>
		        </li>
		          <li style="width:180px">
		         <span class="w90">平均瞬时流量：</span>
		         <span id="pjssll"class="fwd cgreen"></span>t/h
		      </li>
		        </li>
		          <li style="width:180px">
		         <span class="w90">平均瞬时热量：</span>
		         <span id="pjssrl"class="fwd cgreen"></span>GJ/h
		      </li>
		   <!--    <li style="width:170px">
		         <span class="w90">二网供温平均：</span>
		         <span class="fwd cpurple">25</span>个
		      </li>
		      <li style="width:170px">
		         <span class="w90">二网回温平均：</span>
		         <span class="fwd cblack">25</span>个
		      </li> -->
		      
    	 </ul> 
    </div>
    	
  	<div class="row" style="margin-top: 10px;">
	    <div class="col-sm-9 ">
	    	<div class="analyse-chart">
	      			<div  id="line"style="height:300px;width:100%;"></div>	
	      	</div>
	    </div>
	     
	      <div class="col-sm-3 analyse-chart">
	      <div id="pie"  style="width:100%;height:300px;"></div>
	      
	    </div> 	   
	      
	      
		 <script type="text/javascript">
					   getLineChart();
					    function getLineChart(){
				
					    	 var datas=$('#listForm').serialize();
					    	 datas = $.param({chartField:"SUPPLYTEMP,RETURNTEMP",chartTitle:"供水温度,回水温度",chartType:"line,line",chartPosition:"1,1"})+"&"+datas;
					    	$.ajax({
								  type: 'POST',
								  url: "<%=path%>/feedReal/getFeedRealChart.do",
								  data: datas,
								  success: function(result){
								 // alert(JSON.stringify(result));
									  var chart=new PrivateEchart();
									    chart.legendName=result.title;
									    chart.legendType=result.type;
									    chart.legendField=result.column;	
									    chart.yaxisPostion=result.postion;
									    chart.targetField="SCADATIME";
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
								 //   getPieChart();
								  //  var xPieData='[{"value":13,"name":"严重低标"},{"value":33,"name":"低标"},{"value":53,"name":"合格"},{"value":23,"name":"超标"},{"value":23,"name":"严重超标"}]';
								    function getPieChart(){
								   // alert($("#sjfh").val());
								   // alert($("#feedsjfh").val());
								       var myChart = echarts.init(document.getElementById('pie'),'dark');
                        option = {
                        		backgroundColor:'',
                        	    tooltip : {
                        	        trigger: 'item',
                        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                        	        
                        	    },
                        	    legend: {
                        	        /* orient: 'horizontal',
                        	        left: 'center', */
                        	        orient: 'vertical',
      								left: 'left',
                        	        data: ['设计负荷','实际负荷'],
                        	        textStyle : {
									fontWeight : 'bold',
									fontSize : 12,
									color:'red'
									}
                        	    },
                        	    series : [
                        	        {
                        	            name: '实际负荷/设计负荷',
                        	            type: 'pie',
                        	            radius : '75%',
                        	           // radius : '55%',
                        	            center: ['50%', '60%'],
                        	            data:[
                        	                {value: $("#sjfh").val(), name:'实际负荷'},
                        	                {value:$("#feedsjfh").val(), name:'设计负荷'},
                        	              
                        	            ],
                        	            labelLine: {
                        	                normal: {
                        	                    show: true
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
								    
								    
								    }
								    
								    
								    </script>	
          
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
	        url:"<%=path%>/feedReal/getFeedRealList.do",
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
		 var datas= $.param({rows: params.limit,page: params.pageNumber,feedcode:$("#feedcode").val()+""}) + '&' + $('#listForm').serialize();
		 getSummary();
		 return datas;
	} 
	function getHeight() {
	    return $(window).height()-320;
	}
	function getWidth(){
		return $(window).width()-210;
	}
	
	function search(){
		getLineChart();
		getSummary();
	    $table.bootstrapTable('refresh');
    }
	
	function operateFormatter(value, row, index) {
	//	 var temp= '';
		var temp="";
	    var name=row.FEEDNAME;
	 
		//alert(name);
		 	 
        /*  temp += '<a class="like" href="javascript:gytRun('+value+')" title="工艺图"><i class="icon-group"></i>工艺图</a>&nbsp;&nbsp;'; */
       /*   temp += '<a class="like" href="javascript:gytRun('+value+')" title="工艺图"><i class="icon-dashboard"></i>工艺图</a>&nbsp;&nbsp;'; */
     
         temp +="<a class=\"like\" href=\"javascript:gytRun("+value+",'"+name+"',"+index+")\" title=\"工艺图\"><i class=\"icon-dashboard\"></i>工艺图</a>&nbsp;&nbsp;";
         temp +="<a class=\"like\" href=\"javascript:runAnalyse("+value+",'"+name+"',"+index+")\" title=\"icon-dashboard\"><i class=\"icon-dashboard\"></i>运行分析</a>&nbsp;&nbsp;";
        
        
		
		 return temp;
    }
	
	function renderScdaTime(value,row,index){
	    var  day=new Date();
	    var date = eval('new Date(' + value.replace(/\d+(?=-[^-]+$)/,    
        function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
          if(day.getYear()-date.getYear()!=0 || day.getDate()-date.getDate()!=0 ||day.getMonth()-date.getMonth()!=0){
          	return "<font id='valsfont' title='已是历史数据' style='color:red;'>"+value+"</font>";
          }
	     	return  value;
	}
	
	function gytRun(obj,feedname,index){
		window.parent.addTabs({id:'feedanalyse99-'+obj,title: feedname+'工艺图',close: true,url: '<%=path%>/feedReal/toPicture.do?code='+obj});

 	}
	
	function runAnalyse(obj,feedname,index){
		window.parent.addTabs({id:'runAnalyse-'+obj,title: feedname+'运行分析',close: true,url: '<%=path%>/feedAnalysis/dataTeamListLayer.do?pageid=180613&feedcode='+obj});
			
    }
	function energyReport(obj){
		window.parent.addTabs({id:'energyreport99-'+obj,title: obj+'能耗报告',close: true,url: '<%=path%>/basmeterdata/basMeterDataLayer.do?pageid=8002'});
		
	}
	
	function warnConfig(){
		layer_show('报警配置','<%= request.getContextPath() %>/feedReal/feedWarnConfigLayer.do','800');
	}
	
	function getWarningDetail(stationid,fieldname,feedname){
		
		layer_show(feedname+' - 报警曲线','<%= request.getContextPath() %>/feedReal/getFeedWarnDetailLayer.do?stationid='+stationid+'&fieldname='+fieldname+'&feedname='+feedname,'800');
	}
	
			function getSummary(){
		 var datas=$('#listForm').serialize();
		$.ajax({
			  type: 'POST',
			  url: "<%=path%>/feedReal/getFeedRealSummary.do",
			  data: datas,
			  success: function(result){
				  $("#rysl").text(result.FEEDNUMBER);
				  $("#gwpj").text(result.SUPPLYTEMP);
				  $("#hwpj").text(result.RETURNTEMP);
				  $("#gypj").text(result.SUPPLYPRESS);
				  $("#hypj").text(result.RETURNPRESS);
				  $("#hypj").text(result.RETURNPRESS);
			      $("#pjssll").text(result.SUPPLYFLOW);
			      $("#pjssrl").text(result.HEATQUANTITY);
			      $("#sjfh").val(result.SJFH);
			      $("#feedsjfh").val(result.FEEDSJFH);
			      getPieChart();
			  },
			  dataType: 'json'
			});
	}
	
	
	  	//导出功能
		function exportExcel(){
		// var datas= $.param({nodecode:$("#nodename").val()+""}) + '&' + $('#listForm').serialize();
		location.href="<%=path%>/feedReal/exportExcel.do?pageid=2002&"
		+$.param({nodecode:$("#feedcode").val()+""}) + '&' + $('#listForm').serialize();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	    
    </script> 
  </body>
</html>

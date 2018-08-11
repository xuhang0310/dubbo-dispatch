<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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

<meta content='width=device-width, initial-scale=1, maximum-scale=1'
	name='viewport'>
<script src="<%=request.getContextPath()%>/js/jquery-1.10.2.min.js"></script>
<%@ include file="/jsp/header.jsp"%>
<!-- ace styles -->



<link href="<%=request.getContextPath()%>/css/ace-tab.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
        function setFontCss(treeId, treeNode) { 
            return treeNode.level == 1 ? {color:"red"} : {}; 
     	}; 
     
        var setting = {
        		data: { 
                    simpleData: { 
                        enable: true,//如果设置为 true，请务必设置 setting.data.simpleData 内的其他参数: idKey / pIdKey / rootPId，并且让数据满足父子关系。 
                        idKey: "id", 
                        pIdKey: "pid", 
                        rootPId: 0 
                    },
                    key: {
	                    name: "title",
	                    url:""
                    }
				}, 
                view: { 
                    showLine: true,//显示连接线 
                    showIcon: true//显示节点图片 
                    
                }, 
                check:{
                	enable:true
                },
                async: {    //ztree异步请求数据 
                    enable: true, 
                    url: "<%=path%>/org/getSysOrgTreeData.do",//请求action方法 
                    autoparam:["id"] 
                },
                callback:{
                	 beforeClick: zTreeBeforeClick,
                     onClick: zTreeOnClick,
                     onCheck: zTreeOnCheck,
                     onAsyncSuccess: zTreeOnAsyncSuccess

                }
        }
        
      //启动树节点     
        $(function($){ 
        	$.fn.zTree.init($("#treeDemo"), setting); 
             
        });
         
        function zTreeOnAsyncSuccess(){
           // alert('加载树成功');
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
           
            treeObj.expandAll(true);

        }
        
        function zTreeBeforeClick(treeId, treeNode, clickFlag) {
        	//alert(treeNode.id+"==="+treeNode.pid+"==="+treeNode.title);
        	
        }
        
        function zTreeOnClick(event, treeId, treeNode, clickFlag){
        	 var treeObj = $.fn.zTree.getZTreeObj(treeId);  
             var node = treeObj.getNodeByTId(treeNode.tId);  
             if(node.children == null || node.children == "undefined"){  
            	 $.ajax({  
                     type: "POST",  
                     async:false,  
                     url: "<%=path%>/node/queryNodeByPid.do",  
                     data:{  
                         orgid:treeNode.id  
                     },  
                     dataType:"json",  
                     success: function(data){  
                         if(data !=null && data !=""){  
                             //添加新节点  
                             newNode = treeObj.addNodes(node, data);  
                         }  
                     },  
                     error:function(event, XMLHttpRequest, ajaxOptions, thrownError){  
                         result = true;  
                         alert("请求失败!");  
                     }  
                 }); 
             }
             
        }
        
        function zTreeOnCheck(e, treeId, treeNode) {
        	
        	var treeObj = $.fn.zTree.getZTreeObj(treeId);  
        	var nodes = treeObj.getCheckedNodes(true);
            vid = "";
            vname = "";
            for (var i = 0, l = nodes.length; i < l; i++) {
            	vid += nodes[i].id + ","; 
            	vname += nodes[i].title + ","; 
            }
            $("#orgcode").val(vid);
            $("#nodename").val(vname);
            //alert($("#orgcode").val()+"=="+vname);
            search();
		}	
        
        
        </script>
<script type="text/javascript">
	
</script>
</head>

<body style="margin:10px;overflow-x:auto;">

<div class="row">	
	<div class="col-sm-2" >
	      <ul id="treeDemo" class="ztree" style="height:500px;width:180px;overflow-x:auto;overflow-y:auto;border:0px;"></ul> <!--  overflow-y:auto; -->
	</div>
	<div class="col-sm-10">
			<div class="row">
			   <div class="col-sm-10">
			   		
			   	<form id="listForm" name="listForm" >	
				 <%--    <input type="hidden" value="${paramsMap.chartTitle }" name="chartTitle" id="chartTitle"/>
				    <input type="hidden" value="${paramsMap.chartField }" name="chartField" id="chartField"/>
				    <input type="hidden" value="${paramsMap.chartType }" name="chartType" id="chartType"/>
				    <input type="hidden" value="${paramsMap.chartPosition }" name="chartPosition" id="chartPosition"/> --%>
				 <input type="hidden" value="" name="chartTitle" id="chartTitle"/>
			     <input type="hidden" value="" name="chartField" id="chartField"/>
			     <input type="hidden" value="" name="chartType" id="chartType"/>
			     <input type="hidden" value="" name="chartPosition" id="chartPosition"/>
				    <input type="hidden" value="" name="orgcode" id="orgcode"/>
				    <input type="hidden" value="" name="nodename" id="nodename"/>
					
					<select class="selectpicker" data-live-search="true"  name="dateType" id="dateType"  onchange="getTime()" title="请选择时间"  style="width:130px;">
						<option value="0" selected>今天</option>
						<option value="1">昨天</option>
						<option value="2">本周</option>
						<option value="3">上周</option>
						<option value="4">本月</option>
						<option value="5">上月</option>
						<option value="6">采暖期</option>
						<option value="7">自定义时间</option>
		            </select>
		            
		            <span id="dateCustom" style="display:none;">
				        <input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}'})"  name="startdate" id="datemin"  value="${paramsMap.startdate }" class="input-text Wdate" style="width:130px;">
					    -
					    <input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d'})" name="enddate" id="datemax" value="${paramsMap.enddate }" class="input-text Wdate" style="width:130px;">
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
					<a href="javascript:layer_show('历史数据图例','<%= request.getContextPath() %>/base/legendChart.do?pageid=2004&chartField='+$('#chartField').val(),'600','260')" class="btn btn-primary radius" ><i class="icon-bar-chart"></i> 图例</a>
					<a href="javascript:exportExcel()" class="btn btn-danger radius" ><i class="icon-download"></i> 导出</a>
				</form>
	
				</div>
			</div>
	
			<div class="row" style="margin-top:10px;">
	          <div class="col-sm-3">
	              <!-- <div  style="position:relative; border:1px solid #235cb1;line-height:30px;margin-left:5px;">
	                  <div style="border-bottom:1px solid #235cb1;background-color:rgba(27,34,78,0.5);height:30px;font-size:14px;font-weight:bold;">&nbsp;&nbsp;&nbsp;统计分析</div>
	                  <div style="padding:5px;background-color: rgba(0,12,62,0.5);height:320px;font-size:14px;"> -->
	              <div  class="analyse-box">
	                  <div class="header">&nbsp;&nbsp;&nbsp;统计分析</div>
	                  <div style="height:320px;" class="body">
	                        <ul>
							      <li >
							         <span class="w90">换热站数量</span>
							         <span class="fwd cred" style="float:right"id="hrzsl"></span>
							      </li>
							      <li >
							         <span class="w90">一网供温</span>
							         <span class="fwd cblue" style="float:right" id="gswd"></span>
							      </li>
							      <li >
							         <span class="w90">一网回温</span>
							         <span class="fwd cyellow" style="float:right"id="hswd"></span>
							      </li>
							      <li >
							         <span class="w90">一网供压</span>
							         <span class="fwd cgreen" style="float:right"id="gsyl"></span>
							      </li>
							      <li >
							         <span class="w90">一网回压</span>
							         <span class="fwd cpurple" style="float:right"id="hsyl"></span>
							      </li>
							      <li >
							         <span class="w90">瞬时热量</span>
							         <span class="fwd cyellow" style="float:right"id="ssrl"></span>
							      </li>
							      <li >
							         <span class="w90">瞬时流量</span>
							         <span class="fwd cgreen" style="float:right"id="ssll"></span>
							      </li>
						<!-- 	      <li >
							         <span class="w90">平均补水量</span>
							         <span class="fwd cpurple" style="float:right"id="bsl"></span>
							      </li> -->
							<!--        <li >
							         <span class="w90">室外温度</span>
							         <span class="fwd cgreen" style="float:right"></span>
							      </li>
							      <li >
							         <span class="w90">计划产热量</span>
							         <span class="fwd cpurple" style="float:right"></span>
							      </li> -->
							      
					    	 </ul> 
	                  </div>
	              </div>
	          </div>
		      <div class="col-sm-9 analyse-chart" >
		      <!-- <div class="col-sm-9" style="border:1px solid #235cb1;background-color: rgba(0,12,62,0.5);"> -->
		        <div id="line" style="height:350px;width:100%;"></div>
		      </div>
					      
								    <script type="text/javascript">
								    getLineChart()
								    function getLineChart(){
								    
				
					    	 var datas=$('#listForm').serialize();
					    	$.ajax({
								  type: 'POST',
								  url: "<%=path%>/nodeHistory/getNodeHistoryChart.do",
								  data: datas,
								  success: function(result){
								 // alert(JSON.stringify(result));
									  var chart=new PrivateEchart();
									    chart.legendName=result.title;
									    chart.legendType=result.type;
									    chart.legendField=result.column;	
									    chart.yaxisPostion=result.postion;
									    chart.targetField="SCADADATE";
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
	     </div>
			
			
			<div class="row" style="margin-top:10px;">
			  <div class="col-sm-12">
					<!-- 检索  -->
					<div class="table-responsive">
						<table id="table" class="table divmatnrdesc" data-id-field="id"
							data-click-to-select="true" data-page-list="[10, 25, 50, 100]">
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
		$('#dateType').selectpicker({
			  width:120
		});
		
		var columns =eval(${jsonTableGrid});
	    $table.bootstrapTable({
	        height: getHeight(),
	       	method: 'post',
	        url:"<%=path%>/nodeHistory/getNodeHistoryList.do",
				dataType : "json",
				striped : true,
				pagination : true,
				queryParamsType : "limit",
				contentType : "application/x-www-form-urlencoded",
				pageSize : 20,
				pageNumber : 1,
				queryParams : queryParams,
				showColumns : false, //不显示下拉框（选择显示的列）
				sidePagination : "server", //服务端请求
				responseHandler : responseHandler,
				columns : columns
			});

			$(window).resize(function() {
				$table.bootstrapTable('resetView', {
					height : 500,
					width : getWidth()
				});
			});
		});

		function responseHandler(res) {
			return {
				"rows" : res.rows,
				"total" : res.total
			};
		}
		function queryParams(params) {
			var datas= $.param({rows: params.limit,page: params.pageNumber}) + '&' + $('#listForm').serialize();
			getSummary();
			return datas;
		}
		
		function getSummary(){
		 var datas= $.param({nodecode:$("#dateType").val()+""}) + '&' + $('#listForm').serialize();
		$.ajax({
			  type: 'POST',
			  url: "<%=path%>/nodeHistory/getNodeSummary.do",
			  data: datas,
			  success: function(result){
				  $("#hrzsl").text(result.NUMS+"个");  
				  if(result.SUPPLYTEMP!==null){
				  $("#gswd").text(result.SUPPLYTEMP+"℃"); 
				  }else{
				  $("#gswd").text("℃"); 
				  }
				  if(result.RETURNTEMP!==null){
				  $("#hswd").text(result.RETURNTEMP+"℃"); 
				  }else{
				   $("#hswd").text("℃"); 
				  }
				  if(result.SUPPLYPRESS!==null){
				  $("#gsyl").text(result.SUPPLYPRESS+"Mpa"); 
				  }else{
				   $("#gsyl").text("Mpa"); 
				  }
				  if(result.RETURNPRESS!==null){
				  $("#hsyl").text(result.RETURNPRESS+"Mpa"); 
				  }else{
				  $("#hsyl").text("Mpa"); 
				  }
				  if(result.HEATQUANTITY!==null){
				  $("#ssrl").text(result.HEATQUANTITY+"GJ/h"); 
				  }else{
				   $("#ssrl").text("GJ/h"); 
				  }
				  if(result.SUPPLYFLOW!==null){
				  $("#ssll").text(result.SUPPLYFLOW+"t/h"); 
				  }else{
				  $("#ssll").text("t/h"); 
				  }
			  },
			  dataType: 'json'
			});
	}
		
		
		function getTime(){
			var temp=$("#dateType").val();
			if(temp=="7"){
				$("#dateCustom").show();
			}else{
				$("#dateCustom").hide();
			}
			if(temp=="0"||temp=="1"){
				$("#timeSearchType").text("分");
			}else if(temp=="2"||temp=="3"){
				$("#timeSearchType").text("小时");
			}else if(temp=="4"||temp=="5"||temp=="6"){
				$("#timeSearchType").text("天");
			}
		}
		
		function exportExcel(){
			
			location.href="<%=path%>/nodeHistory/exportExcel.do?pageid=${pageid}&"+ $('#listForm').serialize();
		}
		
		
		function getHeight() {
			return $(window).height() - 140;
		}
		function getWidth() {
			return $(window).width() - 180;
		}

		function search() {
			getLineChart();
			$table.bootstrapTable('refresh');
		}

		function operateFormatter(value, row, index) {
			var temp = '';

			return value;
		}
	</script>
</body>
</html>

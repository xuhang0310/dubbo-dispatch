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
		<script type="text/javascript" src="<%=path%>/plugins/layout/jquery.layout.js"></script>
		
  </head>
  
  <body style="margin:10px;"> <!-- overflow:hidden; -->
   <div class="row">
		<div class="col-sm-12">
			<form id="listForm" name="listForm" action="#">	
				<input type="hidden" value="" name="chartTitle" id="chartTitle"/>
			    <input type="hidden" value="" name="chartField" id="chartField"/>
			    <input type="hidden" value="" name="chartType" id="chartType"/>
			    <input type="hidden" value="" name="chartPosition" id="chartPosition"/>
			 <select class="selectpicker" data-live-search="true" onchange="getProjectList()" name="orgcode" id="orgcode" title="请选择一个公司" >
	            <option value="" selected="selected">--请选择分公司--</option>
	            <c:forEach var="temp" items="${orgList }">
		             <option value="${temp.CODE }" >${temp.NAME }</option>
		        </c:forEach>
	         </select>
	         <select class="selectpicker" data-live-search="true"  name="nodecode" id="nodecode" title="请选择换热站" >
	         <option value="" selected="selected">--请选择换热站--</option>
	            <c:forEach var="temp" items="${nodeList }">
		             <option value="${temp.CODE }" >${temp.NAME }</option>
		        </c:forEach>
	         </select>
	    <%--      <select class="selectpicker" data-live-search="true"  name="jzlx" id="jzlx" title="请选择建筑类型" >
	            <c:forEach var="temp" items="${jzlxList }">
		             <option value="${temp.CODE }" >${temp.NAME }</option>
		        </c:forEach>
	         </select> --%>
	     <%--     <select class="selectpicker" data-live-search="true"  name="cnfs" id="cnfs" title="请选择采暖方式" >
	            <c:forEach var="temp" items="${cnfsList }">
		             <option value="${temp.CODE }" >${temp.NAME }</option>
		        </c:forEach>
	         </select> --%>
		<!-- 	<button onclick="search()" class="btn btn-success radius"  name=""><i class="icon-search"></i> 查询</button> -->
		
		 <span><a class="btn btn-success radius" href="javascript:search()"><i class="icon-search"></i>查询</a></span>
		 <a href="javascript:layer_show('换热站实时数据图例','<%= request.getContextPath() %>/base/legendChart.do?pageid=2001&chartField='+$('#chartField').val(),'660','220')" class="btn btn-primary radius" ><i class="icon-bar-chart"></i> 图例</a>
		<!-- 	<button onclick="search()" class="btn btn-success radius"  name=""><i class="icon-search"></i> 自定义查询</button> -->
			<shiro:hasPermission name="/jsp/real/node/warnConfig">
				<a href="javascript:warnConfig()" class="btn btn-danger radius"  ><i class="icon-edit"></i> 报警设置</a>
			</shiro:hasPermission>
			
			</form>
		</div>
    </div>
    
    <div class="row analyse" >
    	<ul>
		      <li style="width:170px">
		         <span class="w90">换热站数量：</span>
		         <span class="fwd cred" id="hrzsl">25</span>
		      </li>
		<!--       <li style="width:170px">
		         <span class="w90">超限站数量：</span>
		         <span class="fwd cblue">25</span>个
		      </li> -->
		      <li style="width:170px">
		         <span class="w90">一网供温平均：</span>
		         <span class="fwd cyellow" id="ywgwpj"></span>
		      </li>
		      <li style="width:170px">
		         <span class="w90">一网回温平均：</span>
		         <span class="fwd cgreen" id="ywhwpj"></span>
		      </li>
		      <li style="width:170px">
		         <span class="w90">二网供温平均：</span>
		         <span class="fwd cpurple" id="ewgwpj"></span>
		      </li>
		      <li style="width:170px">
		         <span class="w90">二网回温平均：</span>
		         <span class="fwd cblack" id="ewhwpj"></span>
		      </li>
		      
    	 </ul> 
    </div>
    
    <div class="row" style="margin-top: 10px;">
	    <div class="col-sm-12 ">
	    	<div class="analyse-chart">
	      			<div  id="line"style="height:300px;width:100%;"></div>	
	      	</div>
	    </div>
	     

	      
	      
		 <script type="text/javascript">
					   getLineChart();
					    function getLineChart(){
				
					    	 var datas=$('#listForm').serialize();
					    	$.ajax({
								  type: 'POST',
								  url: "<%=path%>/nodeReal/getNodeRealChart.do",
								  data: datas,
								  success: function(result){
								//  alert(JSON.stringify(result));
									  var chart=new PrivateEchart();
									    chart.legendName=result.title;
									    chart.legendType=result.type;
									    chart.legendField=result.column;	
									    chart.yaxisPostion=result.postion;
									    chart.targetField="READTIMESTR";
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
			
			  <div class="tab-pane active">
					<!-- 检索  -->
			   		
									<table id="table" class="divmatnrdesc"  style="table-layout:fixed;"
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
	        url:"<%=path%>/nodeReal/getNodeRealList.do",
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
	//	return {rows: params.limit,page: params.pageNumber,username:$("#username").val()};
	 var datas= $.param({rows: params.limit,page: params.pageNumber}) + '&' + $('#listForm').serialize();
	 getSummary();
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
		getSummary();
	    $table.bootstrapTable('refresh');
    }
	
		function getSummary(){
		 var datas=$('#listForm').serialize();
		$.ajax({
			  type: 'POST',
			  url: "<%=path%>/nodeReal/getNodeRealSummary.do",
			  data: datas,
			  success: function(result){
				  $("#hrzsl").text(result.NUMS+"个");
			      $("#ywgwpj").text(result.SUPPLYTEMP+"℃");
			      $("#ywhwpj").text(result.RETURNTEMP+"℃");
			      $("#ewgwpj").text(result.SECSUPPLYTEMP+"℃");
			      $("#ewhwpj").text(result.SECRETURNTEMP+"℃");

			  },
			  dataType: 'json'
			});
	}
	
	
	
	function operateFormatter(value, row, index) {
		 var temp= '';
		 /* <shiro:hasPermission name="/jsp/user/updateUser">
		    temp +='<a class="like" href="javascript:editUser('+value+')" title="编辑"><i class="icon-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;   ';
         </shiro:hasPermission>
         
         <shiro:hasPermission name="/jsp/user/deleteUser">
		    temp += '  <a class="like" href="javascript:deleteUser('+value+')" title="删除"><i class="icon-remove"></i></a>';
         </shiro:hasPermission> */
         temp +="<a class=\"like\" href=\"javascript:gytRun('"+value+"','"+name+"',"+index+")\" title=\"工艺图\"><i class=\"icon-dashboard\"></i>工艺图</a>&nbsp;&nbsp;";
		
		 return temp;
    }
	
	function gytRun(obj,feedname,index){
		window.parent.addTabs({id:'feedanalyse99-'+obj,title: feedname+'工艺图',close: true,url: '<%=path%>/feedReal/toPicture.do?code='+obj});

 	}
	
		  	function getProjectList(){
				var orgcode = document.getElementById("orgcode").value;
				var url="<%=path %>/nodeReal/getProjectList.do?orgcode=" + orgcode;
				$.ajax({
			    	type: "POST",
			    	url: url,
			    	success: function(data){
			    	//	 alert(data);
			    		 $("#nodecode").empty();
			    		 var i="<option value=\"\" selected>--请选择换热站--</option>";
			    		 $("#nodecode").append(i);		    		
			    		 $("#nodecode").append(data);
			    		 $("#nodecode").selectpicker('refresh');
			    	},
			    	error: function(){ alert("请求失败!"); }
			    });
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
	
	function getWarningDetail(stationid,fieldname,feedname){
		
		layer_show(feedname+' - 报警曲线','<%= request.getContextPath() %>/feedReal/getFeedWarnDetailLayer.do?stationid='+stationid+'&fieldname='+fieldname+'&feedname='+feedname,'800');
	}
	
	function warnConfig(){
		layer_show('报警配置','<%= request.getContextPath() %>/nodeReal/nodeWarnConfigLayer.do','800');
	}
	
	function editUser(obj){
		
		layer_show('修改用户','<%= request.getContextPath() %>/jsp/user/eidtUser.jsp','500')
	}
	function deleteUser(obj){
		layer.confirm('确认删除吗?',{btn:['是','否']},function(){
			 var url="<%=path %>/user/deleteUser.do?userid="+obj;
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

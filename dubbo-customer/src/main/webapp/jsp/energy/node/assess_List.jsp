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
		    <select class="selectpicker" data-live-search="true"  name="dateType" id="dateType"  onchange="getTime()" title="请选择时间" >
		           
		             <option value="1" selected>昨天</option>
		             <option value="2" >7天数据</option>
				     <option value="3" >30天数据</option>
				     <option value="4" >采暖期数据</option>
				     <option value="5" >自定义</option>
	        </select>
	
			    <span id="dateCustom" style="display:none;">
		 		  <input type="text" onfocus="WdatePicker()" id="startdate" name="startdate" value="${ paramsMap.startdate}" class="input-text Wdate" style="width:120px;">
				    -
				  <input type="text" onfocus="WdatePicker()" id="enddate" name="enddate" value="${ paramsMap.enddate}" class="input-text Wdate" style="width:120px;">
			   </span>
			
			能耗类型:
			<select name="assestype" id="assestype" class="selectpicker">
				<option value="0">热耗</option>
				<option value="1">电耗</option>
				<option value="2">水耗</option>
			</select>
			
			
			
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
	  		  <div  id="pie" style="height:400px;width:900px;bottom:0px;border:0px;">	
		 <script type="text/javascript">
								    getLineChart()
								    function getLineChart(){
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
                        	        data: ['严重低标','低标','合格','超标','严重超标']
                        	    },
                        	    series : [
                        	        {
                        	            name: '热梯度',
                        	            type: 'pie',
                        	            radius : '75%',
                        	            center: ['50%', '60%'],
                        	            data:[
                        	                {value:50, name:'严重低标'},
                        	                {value:50, name:'低标'},
                        	                {value:50, name:'合格'},
                        	                {value:50, name:'超标'},
                        	                {value:50, name:'严重超标'}
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
	        url:"<%=path%>/nodeEnergy/getAssessList.do",
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
	
		function getTime(){
		var temp=$("#dateType").val();
		if(temp=="5"){
			$("#dateCustom").show();
		}else{
			$("#dateCustom").hide();
		}
	}	
	
	
	    
    </script> 
  </body>
</html>

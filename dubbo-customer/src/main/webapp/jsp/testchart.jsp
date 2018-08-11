<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>首页导航</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="<%=path%>/plugins/echart/echarts.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/echart/echartCustom.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/echart/map/china.js"></script>

<script type="text/javascript" src="<%=path%>/plugins/echart/theme/macarons.js"></script>


<script src="<%=request.getContextPath()%>/js/jquery-1.10.2.min.js"></script>

<!-- 单独引入各省市的地图 -->
<script type="text/javascript" src="<%=path%>/plugins/echart/map/beijing.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/echart/map/neimenggu.js"></script>

<style type="text/css">

</style>
</head>

<body>
	<div onclick="closeWin()" id="allmap" style="position:absolute;z-index:000;height:50%;width:60%">
	hello word!!
	</div>
</body>

</html>
<script type="text/javascript">
//var myChart = echarts.init(document.getElementById('allmap'));
var id = 'allmap';
//var xLineData='[{"TIME":"17","FLOW":"17.0","TEMP":"66.0"},{"TIME":"18","FLOW":"18.0","TEMP":"66.0"},{"TIME":"19","FLOW":"19.0","TEMP":"66.0"},{"TIME":"20","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"21","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"22","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"23","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"0","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"1","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"2","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"3","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"4","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"5","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"6","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"7","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"8","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"9","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"10","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"11","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"12","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"13","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"14","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"15","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"16","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"17","FLOW":"8200.0","TEMP":"66.0"}]';
var xLineData=' [{"TIME":"17","TEMP":"66.0","FLOW":"17.0"},{"TIME":"18","TEMP":"66.0","FLOW":"18.0"},{"TIME":"19","TEMP":"66.0","FLOW":"19.0"},{"TIME":"20","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"21","TEMP":"66.0","FLOW":"8200.0"}, {"TIME":"22","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"23","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"0","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"1","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"2","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"3","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"4","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"5","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"6","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"7","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"8","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"9","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"10","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"11","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"12","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"13","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"14","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"15","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"16","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"17","TEMP":"66.0","FLOW":"8200.0"}]';
var xLinejson='["17","18","19","20","21","22","23","0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17"]';
var xPieData='[{"value":23988,"name":"对外经贸合作"},{"value":23017,"name":"科技"},{"value":21013,"name":"文化体育"},{"value":18650,"name":"金融"},{"value":17820,"name":"教育"},{"value":17356,"name":"基础设施"},{"value":17355,"name":"物流"},{"value":17343,"name":"能源"},{"value":14928,"name":"铁路"},{"value":14752,"name":"国内贸易"},{"value":12484,"name":"旅游"},{"value":9184,"name":"农林牧渔"},{"value":8999,"name":"重大项目"},{"value":8108,"name":"环境保护"},{"value":7985,"name":"公路"},{"value":7720,"name":"电力"},{"value":7684,"name":"民航"},{"value":7487,"name":"医药卫生"},{"value":7318,"name":"信息产业"},{"value":7141,"name":"民族宗教"}]';
$().ready(function(){
	
	//折线、柱状图例子
	searchLineChart(id,xLineData,xLinejson);
	//饼图例子
	//searchPieChart(id,xPieData);
});

function searchLineChart(id,xData,xjson){
	var chart=new PrivateEchart();
    chart.legendName=["温度","流量"];
    chart.legendType=["bar","bar"];
    chart.legendField=["TEMP","FLOW"];	
    chart.yaxisPostion=["0","1"];
    chart.yaxisName="温度";//
    chart.yaxisUnit="℃";//
    chart.otherYaxisName="流量";
    chart.otherYaxisUnit=" T/H";
    chart.targetField="TIME";
    chart.toolbox=true;
    chart.title="测试";
   // chart.subtitle="副标题";
    var json=eval("(" + xjson + ")")
    chart.xaisData=json;
    var data = eval("("+ xData +")");
    chart.chartData=data;
    var option=chart.getLineBarChart();
    renderChart(id, option);
	}
	
function searchPieChart(id,xData){
		var chart=new PrivateEchart();
	    chart.legendName=["温度"];//
	    chart.legendType=["pie"];//
	    chart.legendField=["TEMP"];//	
	    chart.yaxisPostion=["0"];
	    chart.yaxisName="温度";//
	    chart.yaxisUnit="℃";//
	    chart.targetField="TIME";// 
	    chart.toolbox=true;//
	    chart.title="测试";//
	    chart.subtitle="副标题";//
	    chart.chartData=xPieData;
	    var option=chart.getPieChart();
	    renderChart(id, option);
	}
	
</script>


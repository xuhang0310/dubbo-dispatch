<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
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
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">  
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
       
    <!--    <style type="text/css">
	body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
	#mapdiv {width: 100%; height:540px; overflow: hidden;}
	dl,dt,dd,ul,li{
		margin:0;
		padding:0;
		list-style:none;
	}
	p{font-size:12px;}
	dt{
		font-size:14px;
		font-family:"微软雅黑";
		font-weight:bold;
		border-bottom:1px dotted #000;
		padding:5px 0 5px 5px;
		margin:5px 0;
	}
	dd{
		padding:5px 0 0 5px;
	}
	li{
		line-height:28px;
	}
	</style> -->
	  <style type="text/css">
		ul,li{list-style: none;margin:0;padding:0;float:left;}
		html{height:100%}
		body{height:100%;margin:0px;padding:0px;font-family:"微软雅黑";}
		#container{height:600px;width:100%;}
		#r-result{width:100%;}
    </style>
        
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
      
   		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=aZeI5L5Ev9BZmvC71I0sdFdz46GVxXuV"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/library/DistanceTool/1.2/src/DistanceTool_min.js"></script>
	<!--加载鼠标绘制工具-->
	<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />
	<!--加载检索信息窗口-->
	<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>
	 <script type="text/javascript" src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css" />
	
     	<%--   <script src="<%= request.getContextPath() %>/js/map/mapNodeEnerge.js"></script> --%>
       <%@ include file="/jsp/header.jsp"%>
	
    </head>
  
<body>
	<div id="container"></div>
<!-- 	<div id="r-result">
		<input type="button"  onclick="openHeatmap();" value="显示热力图"/><input type="button"  onclick="closeHeatmap();" value="关闭热力图"/>
	</div> -->
	
<script type="text/javascript">
    var map = new BMap.Map("container");          // 创建地图实例
 	var nodePointData=${nodePointData};
    var point = new BMap.Point(116.397228, 39.9096045);
    map.centerAndZoom(point, 10);             // 初始化地图，设置中心点坐标和地图级别
    map.enableScrollWheelZoom(); // 允许滚轮缩放
 //	var points=nodePointData;
/*     var points =[
 
{"lng":"116.123421","lat":"39.612715","count":"37643"},
{"lng":"116.647674","lat":"39.900081","count":"17000"},
{"lng":"116.706551","lat":"39.690368","count":"38000"},

]; */
	var str="";
	for(i=0;i<nodePointData.length-1;i++){
	str+="{\"lng\""+":"+"\""+nodePointData[i].LNG+"\",\"lat\":\""+nodePointData[i].LAT+"\",\"count\":\"30000\""+"},";
	}
	str+="{\"lng\""+":"+"\""+nodePointData[nodePointData.length-1].LNG+"\",\"lat\":\""+nodePointData[nodePointData.length-1].LAT+"\",\"count\":\"30000\""+"}";

   var jsonstr="["+str+"]";

 var points=$.parseJSON( jsonstr );



    if(!isSupportCanvas()){
        alert('热力图目前只支持有canvas支持的浏览器,您所使用的浏览器不能使用热力图功能~')
    }
    //详细的参数,可以查看heatmap.js的文档 https://github.com/pa7/heatmap.js/blob/master/README.md
    //参数说明如下:
    /* visible 热力图是否显示,默认为true
     * opacity 热力的透明度,1-100
     * radius 势力图的每个点的半径大小
     * gradient  {JSON} 热力图的渐变区间 . gradient如下所示
     *  {
            .2:'rgb(0, 255, 255)',
            .5:'rgb(0, 110, 255)',
            .8:'rgb(100, 0, 255)'
        }
        其中 key 表示插值的位置, 0~1.
            value 为颜色值.
     */
    heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":40});
    map.addOverlay(heatmapOverlay);
    heatmapOverlay.setDataSet({data:points,max:100});
 
    closeHeatmap();
 
    //判断浏览区是否支持canvas
    function isSupportCanvas(){
        var elem = document.createElement('canvas');
        return !!(elem.getContext && elem.getContext('2d'));
    }
 
    function setGradient(){
        /*格式如下所示:
        {
            0:'rgb(102, 255, 0)',
            .5:'rgb(255, 170, 0)',
            1:'rgb(255, 0, 0)'
        }*/
        var gradient = {};
        var colors = document.querySelectorAll("input[type='color']");
        colors = [].slice.call(colors,0);
        colors.forEach(function(ele){
            gradient[ele.getAttribute("data-key")] = ele.value;
        });
        heatmapOverlay.setOptions({"gradient":gradient});
    }
 
    function openHeatmap(){
        heatmapOverlay.show();
    }
 
    function closeHeatmap(){
        heatmapOverlay.hide();
    }
</script>
	
	<script type="text/javascript">
	openHeatmap();
	</script>
</body>
  


 
</html>

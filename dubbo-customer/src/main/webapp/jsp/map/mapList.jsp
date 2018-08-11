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
       
       <style type="text/css">
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
	</style>
        
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
      
   		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=aZeI5L5Ev9BZmvC71I0sdFdz46GVxXuV"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/library/DistanceTool/1.2/src/DistanceTool_min.js"></script>
	<!--加载鼠标绘制工具-->
	<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />
	<!--加载检索信息窗口-->
	<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css" />
	
     	  <script src="<%= request.getContextPath() %>/js/map/mapmaintenance.js"></script>
       <%@ include file="/jsp/header.jsp"%>
		<script type="text/javascript">
			basUrl="<%=path%>";
			//feedImgName = "map_feed";
			//nodeImgName = "map_node";
			/* longitude="${longitude}";//默认地图中心经度
			latitude="${latitude}";//默认地图中心纬度 */
			longitude=116.29;//默认地图中心经度
			latitude=40.04;//默认地图中心纬度
			defaultCity="";//默认城市名
			viewSize=15;//默认地图放大比例	
		   check=${isdraw};
			feedPointData=${feedPointData};//已存热源坐标点
		    nodePointData=${nodePointData};//已存换热站坐标点
			lineData=${lineData};//已存线坐标点
		//	alert(JSON.stringify(feedPointData[0]));
		//	alert(check);
			//加载完页面执行
			$(function(){
	
			initMap();
	        });
	        
	    function search(){
		    $("#mapdiv").bootstrapTable('refresh');
   		 }
 	   </script> 
    </head>
  


   <body style="width: 100%;height: 100%;">  
	<div id="mapdiv" style="height:100%; margin: 0 0px;">
			
	</div>
	
  </body>
</html>

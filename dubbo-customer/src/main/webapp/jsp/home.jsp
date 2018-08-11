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
<script src="<%=request.getContextPath()%>/js/jquery-1.10.2.min.js"></script>

<!-- 单独引入各省市的地图 -->
<script type="text/javascript" src="<%=path%>/plugins/echart/map/beijing.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/echart/map/neimenggu.js"></script>

<style type="text/css">

</style>
</head>

<body>
	
	
</body>
<script type="text/javascript">

</script>
</html>



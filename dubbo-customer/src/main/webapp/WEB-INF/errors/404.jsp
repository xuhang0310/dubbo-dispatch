<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="com.github.xupei.simple.shiro.*" %>
<%@ page language="java" import="org.apache.shiro.SecurityUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>后台管理系统</title>
       
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
    <%--    <%@ include file="/jsp/header.jsp"%> --%>
       <!-- ace styles -->
		<link href="<%= request.getContextPath() %>/css/fileManager.css" rel="stylesheet" type="text/css" />
		
       
    </head>
  
  <body class="gray-bg">
     <div class="middle-box text-center animated fadeInDown">
    <h1>404</h1>
    <h3 class="font-bold">页面未找到！</h3>

    <div class="error-desc">
        抱歉，页面好像去火星了~
        <form class="form-inline m-t" role="form">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="请输入您要反馈的内容 …">
            </div>
            <button type="submit" class="btn btn-primary">提交</button>
        </form>
    </div>
</div>
  </body>
</html>
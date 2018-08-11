<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
        <title>生产调度系统</title>
       
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
        <%@ include file="/jsp/header.jsp"%>
    <script type="text/javascript">
	    $(function() {
			
			layer.alert('您现在没有权限操作值班工作！<br/>当前值班人员：<c:forEach var="tempList" items="${zbrList}" varStatus="stat"> <c:if test="${stat.index!=0}">,&nbsp;&nbsp;</c:if><c:out value="${tempList.DISPLAYNAME}" /></c:forEach>', {skin: 'layui-layer-molv',icon: 7,closeBtn: 0,btn: 0,shift: 6,title: "值班工作" });
			
		});	
     </script>
	</head>
	<body>
	
	</body>
</html>

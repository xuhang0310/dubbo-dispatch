<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <title>后台管理系统</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
       <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
       <%@ include file="/jsp/header.jsp"%>
       
		
		<script type="text/javascript">
		 function searchChart(){
		    var chk_value =[];//定义一个数组      
		    var lab_value =[];
		    var type_value =[];
		    var postion_value =[];
            $('input[name="legend"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数      
            	lab_value.push($(this).next().text());//将选中的值添加到数组chk_value中 
            	type_value.push($(this).next().next().text())
            	postion_value.push($(this).next().next().next().text());
                chk_value.push($(this).val());
             });  
             parent.$('#chartType').val(type_value);
			 parent.$('#chartPosition').val(postion_value);
			 parent.$('#chartTitle').val(lab_value);
			 parent.$('#chartField').val(chk_value);
			 
			 parent.search();
			 var index = parent.layer.getFrameIndex(window.name);
			 parent.layer.close(index);
		 }
		 function saveConfig(){
			 
		 }
		 function cancle(){
			 var index = parent.layer.getFrameIndex(window.name);
			 parent.layer.close(index);
		 }
		</script>
</head>
  
<body>
	<div class="pd-10">
	    <c:if test="${empty paramsMap.chartField}"> 
		    <c:forEach var="temp" items="${legendList}" varStatus="status">
		         <c:if test="${status.index%5==0&&status.index!=0 }"><br/></c:if>
		        
		        <input type="checkbox" name="legend" value="${temp.FIELDNAME }" <c:if test="${temp.ISCHECKED=='1' }">checked</c:if>/><span> ${temp.CAPTION }(${temp.UNIT })</span><span style="display:none;">${temp.ISTYPE }</span><span style="display:none;">${temp.ISPOSITON }</span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		        
		    </c:forEach>
	    </c:if> 
	    
	     <c:if test="${not empty paramsMap.chartField}"> 
		    <c:forEach var="temp" items="${legendList}" varStatus="status">
		         <c:if test="${status.index%5==0&&status.index!=0 }"><br/></c:if>
		        
		        <input type="checkbox" name="legend" value="${temp.FIELDNAME }" <c:if test="${fn:contains(paramsMap.chartField,temp.FIELDNAME)}">checked</c:if>/><span> ${temp.CAPTION }(${temp.UNIT })</span><span style="display:none;">${temp.ISTYPE }</span><span style="display:none;">${temp.ISPOSITON }</span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		        
		    </c:forEach>
	    </c:if> 
	</div>
	<div class="pd-20" style="text-align:center">
	     <button class="btn btn-success radius" onclick="searchChart()" >查询</button>
	     <button class="btn btn-success radius" onclick="saveConfig()" >保存</button>
	     <button class="btn btn-success radius" onclick="cancle()" >取消</button>
	    
	</div>
	
</body>
</html>

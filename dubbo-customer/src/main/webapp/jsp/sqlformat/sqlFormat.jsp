<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>生产调度系统</title>
       
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
       <%@ include file="/jsp/header.jsp"%>
       <!-- ace styles -->
		<script type="text/javascript">
		
		function test(){
			var str=$("#before").val();
		    var temp="StringBuffer sBuffer = new StringBuffer(\"\"); "+'\n';
		    var line="";
			for(i=0;i<str.length;i++){
				 line+=str.charAt(i);
				 if(str.charAt(i)=="\n"){
					 temp+=" sBuffer.append(\""+line.substring(0,line.length-1)+"\"); "+'\n';
					 line="";
				 }
				 if(i==str.length-1){
					 temp+=" sBuffer.append(\""+line+"\");";
					 line="";
				 }
			}
			$("#after").val(temp);
		}
		</script>
		
       
    </head>
  
<body style="margin:10px;overflow:hidden;" >
     
    转化前 <textarea id="before" rows="20" cols="80" onblur="test()"></textarea>
     
   转化后    <textarea id="after" rows="20" cols="80"></textarea>
</body>
</html>

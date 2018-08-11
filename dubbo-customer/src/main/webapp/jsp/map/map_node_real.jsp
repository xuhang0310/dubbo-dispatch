<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
          
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
         <%@ include file="/jsp/header.jsp"%>
        <script type="text/javascript" src="<%=path%>/plugins/layout/jquery.layout.js"></script>
         <link href="<%= request.getContextPath() %>/css/ace-tab.css" rel="stylesheet" type="text/css" />
     <style>
	 		.mapanalyse{
		margin-top:10px;
		margin-left:1px;
		margin-right:1px;
		/* border:solid 1px #395c9f; */
		/* background-color:#f9f9f9; */
		padding:5px;
		font-size:13px;
		font-weight:500; 
		
		}
		.mapanalyse ul li{
	list-style:none;
	float:left;
}

.mapanalyse span.w90{
	width: 110px;
    display: inline-block;
    text-align: right;
}

.mapanalyse span.fwd{
	font-weight: bold;
    font-size: 14px;
    margin-right: 0px;
}

.mapanalyse-box{
	position:relative; 
	border:1px solid #235cb1;
	line-height:30px;
	margin-left:5px;
}

.mapanalyse-chart{
	border:1px solid #235cb1;
	background-color: rgba(0,12,62,0.5)
}

.mapanalyse-box .header{
	border-bottom:1px solid #235cb1;
	background-color:rgba(27,34,78,0.5);
	height:30px;
	font-size:14px;
	font-weight:bold;
}

.mapanalyse-box .body{
	padding:5px;
	background-color: #f9f9f9;
	font-size:14px;
}



span.cred { color:#E44747}
span.cyellow{ color:#FF9900}
span.cblue { color:#45B0E4}
span.cgreen { color:#28A622}
span.cpurple{ color:#FF00FF}
span.cblack{ color:#000000}
		
}
	    </style>
       <script type="text/javascript">
    
			    
		  
		</script>
        
       
</head>
  
<body style="background-color:#F5F3F0;"> <!--   #f9f9f9 -->
 	 <div class="row mapanalyse" >
    	<ul>
    	
    			<li style="width:225px">
		         <span class="w90">换热站编号：</span>
		         <span class="fwd cred" id="hrzbh">${node.NODECODEID }</span>
		       </li>
		     
    			<li style="width:225px">
		         <span class="w90">换热站名称：</span>
		         <span class="fwd cred" id="rymc">${node.NODECODEPIC }</span>
		       </li>
		        <li style="width:300px">
		         <span class="w90">时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;间：</span>
		         <span class="fwd cred"id="sj">${node.READTIMESTR }</span>
		      </li>
		      <li style="width:225px">
		         <span class="w90">一网供温：</span>
		         <span class="fwd cblue"id="ywgw">${node.SUPPLYTEMP }</span>℃
		      </li>
		      <li style="width:225px">
		         <span class="w90">一网回温：</span>
		         <span class="fwd cblue" id="ywhw">${node.RETURNTEMP}</span>℃
		      </li>
		      <li style="width:225px">
		         <span class="w90">一网供压：</span>
		         <span class="fwd cblue"id="ywgy">${node.SUPPLYPRESS }</span>MPa
		      </li>
		      <li style="width:225px">
		         <span class="w90">一网回压：</span>
		         <span class="fwd cgreen" id="ywhy">${node.RETURNPRESS }</span>Mpa
		      </li>
		      <li style="width:225px">
		         <span class="w90">一网供水瞬流：</span>
		         <span class="fwd cgreen"id="ywgsll">${node.SUPPLYFLOW }</span>m3/h
		      </li>
		       <li style="width:225px">
		         <span class="w90">一网瞬时热量：</span>
		         <span class="fwd cgreen" id="ywssrl">${node.HEATQUANTITY}</span>MW
		      </li>
		      <li style="width:225px">
		         <span class="w90">二网供温：</span>
		         <span class="fwd cpurple"id="ewgw">${node.SECSUPPLYTEMP }</span>℃
		      </li>
		      <li style="width:225px">
		         <span class="w90">二网回温：</span>
		         <span class="fwd cpurple"id="ewhw">${node.SECRETURNTEMP }</span>℃
		      </li>
		      <li style="width:225px">
		         <span class="w90">二网供压：</span>
		         <span class="fwd cpurple"id="ewgy">${node.SECSUPPLYPRESS }</span>MPa
		      </li>
		      <li style="width:225px">
		         <span class="w90">二网回压：</span>
		         <span class="fwd cyellow"id="ewhy">${node.SECRETURNPRESS }</span>MPa
		      </li>
		      <li style="width:225px">
		         <span class="w90">累计补水量：</span>
		         <span class="fwd cyellow"id="ljbsl">${node.SUMWATER }</span>T
		      </li>
		      <li style="width:225px">
		         <span class="w90">累计热量：</span>
		         <span class="fwd cyellow"id="ljrl">${node.SUMHEATQUANTITY }</span>GJ
		      </li>
		     

		      
    	 </ul> 
    </div>
</body>
</html>


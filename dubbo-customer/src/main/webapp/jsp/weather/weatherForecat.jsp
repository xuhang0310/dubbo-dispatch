<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
 ::-webkit-scrollbar-track-piece { //滚动条凹槽的颜色，还可以设置边框属性
			   background-color:#1b224e;
			}
			::-webkit-scrollbar {//滚动条的宽度
			width:9px;
			height:9px;
			}
			::-webkit-scrollbar-thumb {//滚动条的设置
			background-color:#1b224e;
			background-clip:padding-box;
			min-height:28px;
			}
			::-webkit-scrollbar-thumb:hover {
			background-color:#1b224e;
			}
</style>
<script src="<%= request.getContextPath() %>/js/jquery.min.js"></script>
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/css/weather/weather.css" /> 

<title>天气预报</title>
</head>
<body>
	<div class="weather_box">
    	<div class="weather_tit">
        	<div class="tit_font">天气预报</div>
        	<div class="tit_wrp">
                <a class="weather_btn" href="javascript:newTabMenu('week_forecast','lm','一周天气','week/listWeatherInfo.do','')">一周天气</a>
                <a class="weather_btn" href="javascript:newTabMenu('his_forecast','lm','历史预报','weather/listWeatherInfo.do','')">历史预报</a>
        	</div>
        </div>
        <div class="weather_jt">
        	<c:choose>
			<c:when test="${not empty list}">
				<%-- <c:forEach items="${list}" var="var" varStatus="vs">
		        	<div class="jt_fl">
 		            	<div class="jt_tit">     
		                	${var.REALDATE} （${var.REALTIME} 实况） 
		                </div>
		                
						
		                	<div class="jt_tw sunny">  
		                
						
		                	<p>${var.AVGTEMP}℃</p>
		                	<c:if test="${var.FORETIME=='06:00:00'}">
			                    <span>${var.TODAYDAYSTATUS}</span><br /><br />
			                    <span>${var.TODAYDAYWINDPOWER}</span>
		                	</c:if>
		                	<c:if test="${var.FORETIME=='17:00:00'}">
			                    <span>${var.ATNIGHTSTAUS}</span><br /><br />
			                    <span>${var.ATNIGHTWIND}</span>
		                	</c:if>
		                </div>
		            </div>
				</c:forEach> --%>
		
		        	<div class="jt_fl">
 		            	<div class="jt_tit">     
		                	${list.READDATE} （${list.READTIME} 实况） 
		                </div>
		                
						
		                <!-- 	<div class="jt_tw sunny">   -->
		                	<div class="jt_tw sunny" style="background-image:url(<%= request.getContextPath() %>/images/weather/${realList.IMAGEINDEX }) ">
						
		                	<p>${list.TEMPVALUE}℃</p>
		                	<span>${realList.STATUS}</span><br /><br />
		                	 风向：${realList.WIND}<br />
		                	 风力：${realList.POWER}
		                <%-- 	<c:if test="${list.FORETIME=='06:00:00'}">
			                    <span>${list.TODAYDAYSTATUS}</span><br /><br />
			                    <span>${list.TODAYDAYWINDPOWER}</span>
		                	</c:if>
		                	<c:if test="${list.FORETIME=='17:00:00'}">
			                    <span>${list.ATNIGHTSTAUS}</span><br /><br />
			                    <span>${list.ATNIGHTWIND}</span>
		                	</c:if> --%>
		                </div>
		            </div>
				
				
				
				
				
			</c:when>
			</c:choose>
			
		
		            <div class="jt_fl">
		            	<div class="wt_bor">
		                	
		                    
		                </div>
		            </div>
	        
        </div>
    </div>
    
    <c:choose>
	<c:when test="${not empty ybList}">
		
		     <div class="wt_previews">
		     
		    <c:forEach items="${ybList}" var="yb" varStatus="vs">
		    	<!-- <div class="wt_pre sunny"> -->
		    	<!-- 	{ background:url(../../images/weather/cloudy1.png) no-repeat   }		 -->
		    	<div class="wt_pre sunny" style="background-image:url(<%= request.getContextPath() %>/images/weather/${yb.IMAGEINDEX }) ">
		    


				        	<%-- <p>${yb.FOREDATE}</p> --%>
				        	<p><fmt:formatDate value="${yb.FOREDATE}" type="date" dateStyle="full"/></p>
				            <div>
				            	<span>${yb.HIGHTEMP}~${yb.LOWTEMP}℃</span><br />
				                ${yb.STATUS}<br />
				                风向：${yb.WIND}<br />
				                风力：${yb.POWER}<br />
				            </div>
				      
		        </div>
		     </c:forEach>   
		    </div>
		
	</c:when>
	</c:choose> 

</body>
</html>

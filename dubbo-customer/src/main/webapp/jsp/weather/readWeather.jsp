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
     
       <script type="text/javascript">
   
		   $(function(){
			   $("#form-member-edit").validate({			    
				    errorClass: "label.error", 
				    onkeyup: false,   
	                submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form  
						    var url="<%=path %>/weather/updateWeatherCity.do";    
		                	var params= $('form').serialize();
	                	  $.post(url,params,function(data){
	                		  
	                		  if(data.flag){
	                			  layer.msg(data.messager, {icon: 1},function(){
	                				  refresh()
	                			  });
	                			  
	                		  }else{
	                			  layer.msg(data.messager, {icon: 2});
	                		  }
	                		 
	                      },"json") 
	                }
			   })
		   })

	
		  	function changeCity(){
				var sf = document.getElementById("sf").value;
				var url="<%=path %>/weather/getCityProjectList.do?sf=" + sf;
				$.ajax({
			    	type: "POST",
			    	url: url,
			    	success: function(data){
			    		 //alert(data);
			    		 $("#city").empty();
			    		 $("#city").append(data);
						 $("#city").selectpicker('refresh');
			    	},
			    	error: function(){ alert("请求失败!"); }
			    });
			}
	
		   
		   function cancle(){
			   var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
		   }
		   function refresh(){
			   parent.search();
				  var index = parent.layer.getFrameIndex(window.name);
				  parent.layer.close(index);
		   }
		</script>
        
       
</head>
  
<body>
<div class="page-content">
	<div class="pd-10">
	  <form action="" method="post" class="form form-horizontal" id="form-member-edit">
	       <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>省份：</label>
	      
	            <select class="selectpicker"  data-live-search="true" title="请选择省份" onchange="changeCity()"  name="sf" id="sf" style="width: 232px;height: 31px;" >
		             	<option value="">--请选择--</option>
		             	<c:forEach var="pv" items="${province}">
			            		<option value="${pv.PROVINCE}" <c:if test="${pv.PROVINCE==city.PROVINCE}">selected</c:if>>${pv.PROVINCE}</option>
			            </c:forEach>		          
	            </select>
	    </div>
	        <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>城市：</label>
	      
	            <select class="selectpicker"  data-live-search="true" title="请选择城市" name="city" id="city" style="width: 232px;height: 31px;" >
		             	  <option value="">--请选择--</option>
		             	<c:forEach var="ct" items="${cityList}">
			            		<option value="${ct.CITYCODE}" <c:if test="${ct.CITYCODE==city.CITYCODE}">selected</c:if>>${ct.CITY}</option>
			            </c:forEach>
			          
	            </select>
	    </div>
		 <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>IP：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" style="width: 220px;" value="${city.IP }" placeholder="请输入一个当地IP"  id="ip" name="ip"  required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	
	
	    
	    <div class="row cl">
	      <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
	      </div>
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


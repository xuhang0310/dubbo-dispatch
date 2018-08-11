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
	                  	    var key=$("#meterid").val();
						    var url="<%=path %>/basmeter/saveMeter.do";
						    if(key!=""){
							var url="<%=path %>/basmeter/updateMeter.do";
					  	  }
	            	     
	                
	              
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
		   	function getProjectList(){
				var stationtype = document.getElementById("stationtype").value;
				var url="<%=path %>/basmeter/getProjectList.do?stationtype=" + stationtype;
				$.ajax({
			    	type: "POST",
			    	url: url,
			    	success: function(data){
			    		 //alert(data);
			    		 $("#mc").empty();
			    		 $("#mc").append(data);
						 $("#mc").selectpicker('refresh');
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
	  
		<input type="hidden" value="${basmeter.ID }"name="meterid" id="meterid">			
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>计量设备名称：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${basmeter.METERNAME }" placeholder="" id="sbmc" name="sbmc"  required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	  	     
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>类型选择：</label>
	      
	            <select class="selectpicker" onchange="getProjectList()"  name="stationtype" id="stationtype" style="width: 232px;height: 31px">
		            <option value="" >--请选择--</option>
		             <option value="1" <c:if test="${basmeter.STATIONTYPE==1}">selected</c:if>>换热站</option>
            		<option value="99" <c:if test="${basmeter.STATIONTYPE==99}">selected</c:if>>热源厂</option>
	            </select>
	    </div>
	       <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>名称：</label>
	      
	            <select class="selectpicker"  data-live-search="true" name="mc" id="mc" style="width: 232px;height: 31px;" >
		             	  <option value="">--请选择--</option>
		             	<c:forEach var="lc" items="${listcode}">
			            		<option value="${lc.STATIONCODE}" <c:if test="${lc.STATIONCODE==basmeter.STATIONCODE}">selected</c:if>>${lc.NAME}</option>
			            </c:forEach>
			          
	            </select>
	    </div>
	    
	       <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>仪表类型：</label>
	      
	            <select class="selectpicker"  name="yblx" id="yblx" title="请选择类型" style="width: 232px;height: 31px">
		            <option value="">请选择...</option>
            		<option value="1" <c:if test="${basmeter.METER_TYPE==1}">selected</c:if>>热表</option>
            		<option value="2" <c:if test="${basmeter.METER_TYPE==2}">selected</c:if>>水表</option>
            		<option value="3" <c:if test="${basmeter.METER_TYPE==3}">selected</c:if>>电表</option>
            		<option value="4" <c:if test="${basmeter.METER_TYPE==4}">selected</c:if>>燃气表</option>
	            </select>
	    </div>
	    
	     <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>倍率：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${basmeter.bl }" placeholder="" id="bl" name="bl"  required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	
	    
	    <div class="row cl">
	      <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
	      </div>
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


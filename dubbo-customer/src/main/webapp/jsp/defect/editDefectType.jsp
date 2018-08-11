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
	//	   alert(JSON.stringify($("#defectid").val()));
			   $("#form-member-edit").validate({
				    
				    errorClass: "label.error", 
				    onkeyup: false,   
	                submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form  
	                  	    var key=$("#defectid").val();
						    var url="<%=path %>/defect/addDefectType.do";
						    if(key!=""){
							var url="<%=path %>/defect/updateDefectType.do";
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
	  <input type="hidden" value="${dtype.ID}" name="defectid" id="defectid">
	<%-- 	<input type="hidden" value="${dtype.ID }" name="defectid" id="defectid"> --%>			
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>缺陷名称：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${dtype.Name }" placeholder="" id="qxmc" name="qxmc"  required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	  	     
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>是否启用：</label>
	      
	            <select class="selectpicker" name="del" id="del" style="width: 232px;height: 31px">
		            <option value="" >--请选择--</option>
		             <%-- <option value="1" <c:if test="${basmeter.STATIONTYPE==1}">selected</c:if>>启用</option>
            		<option value="0" <c:if test="${basmeter.STATIONTYPE==99}">selected</c:if>>未启用</option> --%>
            		<option value="1"  <c:if test="${dtype.del==1 }">selected</c:if>>启用</option>
            		<option value="0"  <c:if test="${dtype.del==0 }">selected</c:if>>未启用</option>
	            </select>
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


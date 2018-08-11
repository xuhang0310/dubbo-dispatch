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
	
						   var url="<%=path %>/node/batchUpdateNode.do";
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
	  <input type="hidden" name="nodecode" id="nodecode" value="${nodecode }">
			
											
			
			
			
	    <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>所属公司：</label>
	      <div class="formControls col-4">
	       
			 <select class="selectpicker" data-live-search="true"  name="org" id="org" title="请选择所属公司" >
	             <c:forEach var="temp" items="${orgobj }">
	             		<option value="${temp.ORGID }">${temp.ORGNAME }</option>
	             </c:forEach>
	         </select>
	      </div>
	     <label class="form-label col-2"><span class="c-red">*</span>所属热源：</label>
	      <div class="formControls col-4">
	            <select class="selectpicker" data-live-search="true"  name="feedid" id="feedid" title="请选择热源" >
				             <c:forEach var="fd" items="${feedList }">
				             		<option value="${fd.FEEDID }" >${fd.FEEDNAME }</option>
				             </c:forEach>
			 </select>
	        </div>
	    </div>
	    
	 
	   <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>管理方式：</label>
	      <div class="formControls col-4">
	        <select class="selectpicker" data-live-search="true"  name="glfs" id="glfs" title="请选择一个管理方式" >
                <c:forEach var="temp" items="${dicList }">
                    <c:if test="${temp.PID=='6' }">
                    		<option value="${temp.ID }" >${temp.NAME }</option>
                    </c:if>
             		
             	</c:forEach>
          </select>
	      </div>
	     <label class="form-label col-2"><span class="c-red">*</span>采暖方式：</label>
	      <div class="formControls col-4">
	            <select class="selectpicker" data-live-search="true"  name="cnfs" id="cnfs" title="请选择一个采暖方式" >
	             <c:forEach var="temp" items="${dicList }">
	                    <c:if test="${temp.PID=='1' }">
	                    		<option value="${temp.ID }">${temp.NAME }</option>
	                    </c:if>
	             		
	             	</c:forEach>
	         </select>
	        </div>
	    </div>
	  <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>节能方式：</label>
	      <div class="formControls col-4">
	         <select class="selectpicker" data-live-search="true"  name="jnfs" id="jnfs" title="请选择一个节能方式" >
	           		<c:forEach var="temp" items="${dicList }">
	                    <c:if test="${temp.PID=='36' }">
	                    		<option value="${temp.ID }">${temp.NAME }</option>
	                    </c:if>
	             		
	             	</c:forEach>
	         </select>
	      </div>
	     <label class="form-label col-2"><span class="c-red">*</span>负责人：</label>
	      <div class="formControls col-4">
	            <input type="text" class="input-text" value="" style="width: 230px;"  placeholder="请输入负责人名字" id="fzr" name="fzr"  >
	        </div>
	    </div>
	    
	    
	    <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>热指标：</label>
	      <div class="formControls col-4">
	          <input type="text" class="input-text" value="" style="width: 230px;"placeholder="请输入热指标（w/㎡）" id="rzb" name="rzb"  required="required" >
	      </div>
	     <label class="form-label col-2"><span class="c-red">*</span>水指标：</label>
	      <div class="formControls col-4">
	            <input type="text" class="input-text" value="${basmeter.METERNAME }" style="width: 230px;"placeholder="请输入水指标（kg/㎡）" id="szb" name="szb"  required="required" >
	        </div>
	    </div>
	    
	     <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>电指标：</label>
	      <div class="formControls col-4">
	          <input type="text" class="input-text" value="" style="width: 230px;"placeholder="请输入电指标（w/㎡）" id="dzb" name="dzb"  required="required" >
	      </div>
	     
	    </div>
	  	     
		<div style="height: 20px;"></div>
	    
	    
	  
	  
	
	    
	    <div class="row cl">
	      <div class="col-9 col-offset-3">
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;&nbsp;&nbsp;保存&nbsp;&nbsp;&nbsp;&nbsp;">
	        &nbsp;&nbsp;&nbsp;&nbsp;
	        <a class="btn btn-primary radius"  onclick="refresh()">&nbsp;&nbsp;&nbsp;&nbsp;返回&nbsp;&nbsp;&nbsp;&nbsp;</a>
	      </div>
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


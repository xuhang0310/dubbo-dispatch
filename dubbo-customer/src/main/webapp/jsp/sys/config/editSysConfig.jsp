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
        <title>后台管理系统</title>
          
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
	                  	    var key=$("#pages").val();
						    var url="<%=path %>/sysconfig/savePage.do";
						    if(key!=""){
							var url="<%=path %>/sysconfig/updatePage.do";
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
  
  
  <body style="margin:10px;overflow:hidden;" >
<div class="page-content">
	<div class="pd-10">
	  <form action="" method="post" class="form form-horizontal" id="form-member-edit">
	  
		<input type="hidden" value="${page.ID }" name="pages" id="pages">			
	    <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>页面ID：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text" value="${page.PAGEID }" placeholder="" id="pid" name="pid"  required="required" >
	      </div>
	
		  <label class="form-label col-2"><span class="c-red">*</span>字段名：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text" value="${page.FIELDNAME }" placeholder="" id="filedname" name="filedname"  required="required" >
	      </div>
	    </div>

	  	     
	    <div class="row cl">
	  
	      <label class="form-label col-2"><span class="c-red">*</span>是否隐藏：</label>
	         <div class="formControls col-4">
	            <select class="selectpicker" name="ishiddle" id="ishiddle" style="width: 232px;height: 31px">
		            <option value="" >--请选择--</option>
		            <option value="true" <c:if test="${page.ISHIDDLE==1}">selected</c:if>>是</option>
		             <option value="false" <c:if test="${page.ISHIDDLE==2}">selected</c:if>>否</option>
	            </select>
	          </div> 
	           
	  
	        <label class="form-label col-2"><span class="c-red">*</span>排序：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text" value="${page.ORDERID }" placeholder="" id="ORDERID" name="ORDERID"  required="required" >
	      </div>
	 
	      
	    </div>

	      <div class="row cl">
			      <label class="form-label col-2">列渲染函数：</label>
			      <div class="formControls col-4">
			        <input type="text" class="input-text" value="${page.RENDERER }" placeholder="" id="RENDERER" name="RENDERER" >
			      </div>
			     	
			       <label class="form-label col-2"><span class="c-red">*</span>显示名称：</label>
			      <div class="formControls col-4">
			        <input type="text" class="input-text" value="${page.CAPTION }" placeholder="" id="CAPTION" name="CAPTION"  required="required" >
			      </div>
	    </div>

	      <div class="row cl">
	      <label class="form-label col-2">单位：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text" value="${page.UNIT }" placeholder="" id="UNIT" name="UNIT" >
	      </div>
	   		
	   		 <label class="form-label col-2">宽度：</label>
		      <div class="formControls col-4">
		        <input type="text" class="input-text" value="${page.WIDTH }" placeholder="" id="WIDTH" name="WIDTH" >
		      </div>
	   		
	    </div>
	
	     <div class="row cl">
	      <label class="form-label col-2">字段格式化：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text" value="${page.DATAFORMAT }" id="DATAFORMAT" name="DATAFORMAT" >
	      </div>
	      
	      <label class="form-label col-2"><span class="c-red">*</span>是否主键：</label>
	      		<div class="formControls col-4">
	            <select class="selectpicker"  name="ISPRIMARY" id="ISPRIMARY" style="width: 232px;height: 31px">
		            <option value="" >--请选择--</option>
		             <option value="1" <c:if test="${page.ISPRIMARY==1}">selected</c:if>>是</option>
            		<option value="0" <c:if test="${page.ISPRIMARY==0}">selected</c:if>>否</option>
	            </select>
	  			</div>
	    </div>
	     <div class="row cl">
	      
	    </div>
	     <div class="row cl">
	      <label class="form-label col-2">是否图例：</label>
	      
	            <select class="selectpicker"  name="ISCHART" id="ISCHART" style="width: 232px;height: 31px">
		            <option value="" >--请选择--</option>
		             <option value="1" <c:if test="${page.ISCHART==1}">selected</c:if>>是</option>
            		<option value="0" <c:if test="${page.ISCHART==0}">selected</c:if>>否</option>
	            </select>
	    </div>
	 
	    <br/><br/>
	    <div class="row cl" >
	    
	  
	      <div class="col-9 col-offset-6">
	        <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
	      </div>
	
	      
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


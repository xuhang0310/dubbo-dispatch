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
		   $(function(){
			   $("#form-member-edit").validate({
				    
				    errorClass: "label.error", 
				    onkeyup: false,   
	                submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form  
	                	 
	                      var key=$("#id").val();
	                	  var url="<%=path %>/dataDic/saveDataDic.do";
	                	  if(key!=""){
	                		  url="<%=path %>/dataDic/updateDataDicById.do";
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
		   
		   function editDataDic(){
				
			   var id = $("#id").val();
			   if (id!=null&&id!="") {
				   update()
				}else{
				   add()
				}
			   //layer.msg('请联系开发人员', {icon: 2});
		   }
		   
		   function add(){
				var params= $('form').serialize();   
            	var url="<%=path %>/dataDic/saveDataDic.do";
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
		   
		   function update(){
				var params= $('form').serialize();   
               	var url="<%=path %>/dataDic/editDataDic.do";
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
		   
		   
		</script>
        
       
</head>
  
<body>
<div class="page-content">
	<div class="pd-10">
	  <form action="" method="post" class="form form-horizontal" id="form-member-edit">
	  <input type="hidden" id="id" name="id" value="${dataDic.ID }"/>
	    
	   <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>数据名称：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${dataDic.NAME }" placeholder="" id="name" name="name"  required >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
		<div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>上级名称：</label>
	      <div class="formControls col-5">
	        <select class="selectpicker" style="width: 190px;height: 30px;"  name="pid" id="pid" data-live-search="true">
	            	<option value="0">-----请选择-----</option>
	            	<c:forEach var="pic" items="${sulo}">
	            		<option value="${pic.id}" <c:if test="${pic.id==dataDic.pid}">selected</c:if>><c:out value="${pic.name}"/></option>
	            	</c:forEach>
	            </select>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    
	    <div class="row cl">
	      <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="button" onclick="editDataDic();" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" onClick="refresh();" value="&nbsp;&nbsp;返回&nbsp;&nbsp;">
	      </div>
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


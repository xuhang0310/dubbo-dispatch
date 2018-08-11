<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


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
	                	 
	                      var key=$("#feedid").val();
	                	  var url="<%=path %>/feed/saveFeed.do";
	                	  if(key!=""){
	                		  url="<%=path %>/feed/updateFeedById.do";
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
		   
		   function editFeed(){
			   var id = $("#feedid").val();
			   if (id!=null&&id!="") {
				   update()
				}else{
				   add()
				}
			   //layer.msg('请联系开发人员', {icon: 2});
		   }
		   
		   function add(){
				var params= $('form').serialize();   
            	var url="<%=path %>/feed/saveFeed.do";
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
               	var url="<%=path %>/feed/editFeed.do";
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
	  <input type="hidden" value="${feed.feedid }"  id="feedid" name="feedid">
	  
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>热源名称：</label>
	      <div class="formControls col-5">
	        <input type="text" style="width: 220px;" class="input-text" value="${feed.feedname }" placeholder="" id="feedname" name="feedname"  required >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>所属公司：</label>
	      <div class="formControls col-5">
	        <select  class="selectpicker"  style="width: 190px;height: 30px;"  name="orgid" id="orgid">
	            	<option value="">-----请选择-----</option>
	            	<c:forEach var="org" items="${orgobj}">
	            		<option value="${org.orgid}" <c:if test="${org.orgid==feed.orgid}">selected</c:if>><c:out value="${org.orgname}"/></option>
	            	</c:forEach>
	            </select>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	     <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>设计负荷：</label>
	      <div class="formControls col-5">
	        <input type="text" style="width: 220px;"class="input-text" value="${feed.sjfh }" placeholder="" id="sjfh" name="sjfh" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	     <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>采暖面积：</label>
	      <div class="formControls col-5">
	        <input type="text" style="width: 220px;"class="input-text" value="${feed.cnmj }" placeholder="" id="cnmj" name="cnmj" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	       <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>所在位置：</label>
	      <div class="formControls col-5">
	        <input type="text" style="width: 220px;"class="input-text" value="${feed.SZWZ }" placeholder="" id="szwz" name="szwz" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	        <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>工艺图：</label>
	      <div class="formControls col-5">
	        <select  class="selectpicker" style="width: 190px;height: 30px;"  name="gytid" id="gytid">
	            	<option value="">-----请选择-----</option>
	            	<c:forEach var="gyt" items="${gytList}">
	            		<option value="${gyt.ID}" <c:if test="${gyt.id==feed.GYTID}">selected</c:if>><c:out value="${gyt.PIC_NAME}"/></option>
	            	</c:forEach>
	            </select>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    
	    
	    <div class="row cl">
	      <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="button" onclick="editFeed();" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" onClick="refresh();" value="&nbsp;&nbsp;返回&nbsp;&nbsp;">
	      </div>
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


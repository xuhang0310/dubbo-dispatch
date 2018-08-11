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
	                	  var url="<%=path %>/dataTeam/saveDataTeam.do";
	                	  if(key!=""){
	                		  url="<%=path %>/dataTeam/updateDataTeamById.do";
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
		   
		   function editMailWarnConfig(){
				
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
            	var url="<%=path %>/mailWarnConfig/saveMailWarnConfig.do";
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
               	var url="<%=path %>/mailWarnConfig/editMailWarnConfig.do";
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
		   
		  /*  $("input[name='checkbox']").each(function(){ 
			   if($(this).attr("checked")) 
			   { 
			   $(this).removeAttr("checked"); 
			   } 
		   }); */

		   
		</script>
        
       
</head>
  
<body>
<div class="page-content">
	<div class="pd-10">
	  <form action="" method="post" class="form form-horizontal" id="form-member-edit">
	  <input type="hidden" id="id" name="id" value="${editorObject.ID }"/>
	    
	    
	   <div class="row">
	      <label class="form-label col-2"><span class="c-red">*</span>条件sql：</label>
	      <div class="formControls col-4">
	        <textarea id="tablesql" name="tablesql"  cols="" rows="3" class="textarea"  placeholder="输入sql..."  onKeyUp="textarealength(this,100)">${editorObject.TABLESQL }</textarea>
	        <p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
	      </div>
	      
	      <label class="form-label col-2"><span class="c-red">*</span>满足条件：</label>
	      <div class="formControls col-4">
	        <select class="selectpicker" style="width: 190px;height: 30px;"  name="operator" id="operator">
	            	<option value="=" <c:if test="${editorObject.OPERATOR=='=' }">selected="true"</c:if>>等于</option>
	        		<option value=">" <c:if test="${editorObject.OPERATOR=='>' }">selected="true"</c:if>>大于</option>
	        		<option value="<" <c:if test="${editorObject.OPERATOR=='<' }">selected="true"</c:if>>小于</option>
	            </select>
	            <input type="text" style="width: 220px;" class="input-text" value="${editorObject.TRIGERNUM }" placeholder="" id="trigernum" name="trigernum"  required >
	      </div>
	      
	      <label class="form-label col-2"><span class="c-red">*</span>状态：</label>
	      <div class="formControls col-4">
	        <select class="selectpicker" style="width: 100px;height: 30px;"  name="status" id="status">
	            	<option value="1" <c:if test="${editorObject.STATUS==1}">selected</c:if>>运行</option>
	            	<option value="2" <c:if test="${editorObject.STATUS==2}">selected</c:if>>停止</option>
	            </select>
	      </div>
	      <div class="col-4"> </div>
	   </div>
	   
	   <div class="row">
	      <label class="form-label col-2"><span class="c-red">*</span>报警描述：</label>
	      <div class="formControls col-4">
	        <textarea id="warndesc" name="warndesc"  cols="" rows="3" class="textarea"  placeholder="说点什么...最少输入10个字符"  onKeyUp="textarealength(this,100)">${editorObject.WARNDESC }</textarea>
	        <p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
	      </div>
	      
	      <label class="form-label col-2"><span class="c-red">*</span>解决方案：</label>
	      <div class="formControls col-4">
	        <textarea id="resolve" name="resolve"  cols="" rows="3" class="textarea"  placeholder="说点什么...最少输入10个字符"  onKeyUp="textarealength(this,100)">${editorObject.RESOLVE }</textarea>
	        <p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
	      </div>
	      <div class="col-4"> </div>
	   </div>
	    
	   <div class="row">
	      <label class="form-label col-2"><span class="c-red">*</span>开始报警时间：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text Wdate" value="${editorObject.TRIGERTIME }" onclick="WdatePicker({ dateFmt: 'HH:mm:ss' })" placeholder="" id="trigertime" name="trigertime"  required >
	      </div>
	      <!-- <div class="col-4"> </div> -->
	      <label class="form-label col-2"><span class="c-red">*</span>执行次数：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text" value="${editorObject.INTERVAL }" placeholder="" id="interval" name="interval"  required >
	      </div>
	      <div class="col-4"> </div>
	   </div>
	    
	    <div class="row cl">
	      <div class="col-4"> </div>
	    </div>
	    <div class="row cl">
	      <div class="col-4"> </div>
	    </div>
	    <div class="row cl">
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <div class="col-7 col-offset-5">
	        <input class="btn btn-primary radius" type="button" onclick="editMailWarnConfig();" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" onClick="refresh();" value="&nbsp;&nbsp;返回&nbsp;&nbsp;">
	      </div>
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


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
	                	 
	                      var key=$("#teamid").val();
	                	  var url="<%=path %>/dataTeam/saveDataIndex.do";
	                	  if(key!=""){
	                		  url="<%=path %>/dataTeam/updateDataIndexById.do";
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
		   
		   function editZDDataIndex(){
			 	//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
				//这里明确return false的话表单将不会提交;	
				/* var length = $("input[name='principal']:checked").length;
				var length2 = $("input[name='watch']:checked").length;
				
				if(length == 0){
					layer.msg('请选择值班负责人！', {icon: 2})
					//$.ligerDialog.warn('请选择值班负责人！','提示');
					return false;
				}
				if(length2 == 0){
					layer.msg('请选择值班人！', {icon: 2})
					//$.ligerDialog.warn('请选择值班人！','提示');
					return false;
				} */
				
			   var id = $("#teamid").val();
			   if (id!=null&&id!="") {
				   update()
				}else{
				   add()
				}
			   //layer.msg('请联系开发人员', {icon: 2});
		   }
		   
		   function add(){
				var params= $('form').serialize();   
            	var url="<%=path %>/dataIndex/saveZDDataIndex.do";
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
               	var url="<%=path %>/dataIndex/editZDDataIndex.do";
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
	  <input type="hidden" id="selectmaxsorder" name="selectmaxsorder" value="${selectmaxsorder }"/>
	  <input type="hidden" id="schedulerid" name="schedulerid"  value="${schedulerid }"/>
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>重大事项：</label>
	      <div class="formControls col-5">
	        <textarea id="note" name="note" cols="" rows="" class="textarea"  placeholder="请输入重大事项...最少输入10个字符"  onKeyUp="textarealength(this,100)"></textarea>
	        <p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <div class="formControls col-5">
	      		&nbsp;
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="button" onclick="editZDDataIndex();" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" onClick="refresh();" value="&nbsp;&nbsp;返回&nbsp;&nbsp;">
	      </div>
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


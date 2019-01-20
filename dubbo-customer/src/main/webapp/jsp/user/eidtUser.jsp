<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <title>后台管理系统</title>
       
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
        <%@ include file="/jsp/header.jsp"%>
       <script type="text/javascript">
		   $(function(){
			   $("#form-member-edit").validate({
				    
				    errorClass: "label.error", 
				    onkeyup: false,   
	                submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form  
	                	 
	                      var key=$("#user_id").val();
	                	  var url="<%=path %>/user/saveUser.do";
	                	  if(key!=""){
	                		  url="<%=path %>/user/updateUserById.do";
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
		   
		   function editUser(){
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
            	var url="<%=path%>/user/saveUser.do";
            	$.post(url,params,function(data){
            		  
            		if(data.flag){
            			layer.msg(data.messager, {icon: 1},function(){
            				refresh()
            			});
            			  
            		 }else{
            			layer.msg(data.messager, {icon: 2,shade: 0.4,time:false });
            		 }
            		 
                },"json") 
				    
		   }	
		   
		   function update(){
				var params= $('form').serialize();   
               	var url="<%=path%>/user/updateUserById.do";
				$.post(url, params, function(data) {
		
					if (data.flag) {
						layer.msg(data.messager, {
							icon : 1
						}, function() {
							refresh()
						});
		
					} else {
						layer.msg(data.messager, {
							icon : 2
						});
					}
		
				}, "json")
		
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
		   
		   function validate() {
			    var pwd1 = $("#login-password").val();
			    var pwd2 = $("#affirmpassword").val();
			 
			 /* 对比两次输入的密码  */
			    if(pwd1 != pwd2){
			    	
			    	layer.msg('两次密码不一致！', {icon: 2});
			     }
			}
		   
		   function validUsername(){
				var params= $('form').serialize();
				var url = "<%=path%>/user/doValidUsername.do?";
				
				$.post(url,params,function(data){
            		  
              		if(data.flag){
              			$("#loginname").val("");
              			layer.msg(data.messager, {icon: 2});
              			  
              		 }else{
              			layer.msg(data.messager, {icon: 2});
              		 }
              		 
                  },"json") 
                  
			}
		   
	</script>
	<style type="text/css">
		
	</style>
        
       
</head>
  
<body>
<div class="page-content">
	<div class="pd-10">
	  <form action="" method="post" class="form form-horizontal" id="form-member-edit">
	  <input type="hidden" value="${user2.userid }" id="id" name="id">
	   <div class="row cl" style="display:none">
						<label class="form-label col-3"><span class="c-red">*</span>数据模型名称：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text" value=""
								placeholder="" id="user_id" required name="user_id">
						</div>
		</div>
					
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>姓名：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${users.displayname }" placeholder="" id="member-name" name="displayname"  required >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>登录名：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${users.username }" onblur="validUsername();" placeholder="" id="loginname" name="username" required >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <label for="login-password" class="form-label col-3"><span class="c-red">*</span>登录密码：</label>
	      <div class="formControls col-5">
	        <input type="password" class="input-text" value="${users.password }" placeholder="请设置登录密码" id="login-password" name="password" required >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <label for="affirmpassword" class="form-label col-3"><span class="c-red">*</span>确认密码：</label>
	      <div class="formControls col-5">
	        <input type="password" class="input-text" value="${users.password }"  onkeyup="validate()"  placeholder="请再次填写密码" id="affirmpassword" name="affirmpassword" required >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>角色名称：</label>
	      <div class="formControls col-5">
	        <!-- <input type="text" class="input-text" value="" placeholder="" id="role-name" name="role-name" > -->
	        <select class="selectpicker" data-live-search="true" title="请选择一个角色"  id="role-name" name="roleid" >
	            	<!-- <option value="">-----请选择-----</option> -->
	            	<c:forEach var="temp" items="${roleList}">
						<option value="${temp.ID }" <c:if test="${temp.ID==users.roleid}">selected</c:if>>
							<c:out value="${temp.NAME }" />
						</option>
					</c:forEach>
	        </select>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	   
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>所属部门：</label>
	      <div class="formControls col-5">
	        <!-- <input type="text" class="input-text" value="" placeholder="" id="depart-name" name="depart-name" > -->
	        <select class="selectpicker" data-live-search="true" title="请选择一个部门"  id="depart-name" name="orgid" >
	            	<!-- <option value="">-----请选择-----</option> -->
	            	<c:forEach var="temp" items="${orgList}">
						<option value="${temp.ID }" <c:if test="${temp.ID==users.orgid}">selected</c:if>>
							<c:out value="${temp.NAME }" />
						</option>
					</c:forEach>
	        </select>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>联系方式：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${users.mobile }" placeholder="" id="mobile" name="mobile" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    
	    <div class="row cl">
	      <label class="form-label col-3">备注：</label>
	      <div class="formControls col-5">
	        <textarea name="note" id="login-note" cols="" rows="" class="textarea"  placeholder="说点什么...最少输入10个字符"  onKeyUp="textarealength(this,100)">${users.note }</textarea>
	        <p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="button" onclick="editUser();" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" onClick="refresh();" value="&nbsp;&nbsp;返回&nbsp;&nbsp;">
	      </div>
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


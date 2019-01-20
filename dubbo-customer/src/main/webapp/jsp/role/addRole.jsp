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
       <script type="text/javascript">
		   $(function(){
			   $("#roleform").validate({
				    
				    errorClass: "label.error", 
				    onkeyup: false,   
	                submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form  
	                	 
	                      var key=$("#role_id").val();
	                	  var url="<%=path %>/rolemanage/saveRole.do";
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
	  <form action="" method="post" class="form form-horizontal" id="roleform">
	  
	   <div class="row cl" style="display:none">
						<label class="form-label col-3"><span class="c-red">*</span>数据模型名称：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text" value=""
								placeholder="" id="role_id" required name="role_id">
						</div>
		</div>
					
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>角色名称：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="" placeholder="" id="member-name" name="displayname"  required >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>所属机构：</label>
	      <!-- <div class="formControls col-5">
	        <input type="text" class="input-text" value="" placeholder="" id="login-name" name="username" required >
	      </div>
	      <div class="col-4"> </div> -->
	      	<select class="selectpicker" data-live-search="true"  name="tablename" id="tablename" title="请选择机构" >
		          <!--   <option value="" selected>--请选择--</option> -->
		          <c:forEach var="rl" items="${role}">
			            		<option value="${rl.ORGID}" >${rl.ORGNAME}</option>
			            </c:forEach>
	            </select>
	    </div>
	    
	    <div class="row cl">
	      <label class="form-label col-3">备注：</label>
	      <div class="formControls col-5">
	        <textarea name="bz" cols="" rows="" class="textarea"  placeholder="说点什么...最少输入10个字符"  onKeyUp="textarealength(this,100)"id="bz"></textarea>
	        <p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
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


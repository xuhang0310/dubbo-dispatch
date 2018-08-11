<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <title>商业BI分析系统</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery.min.js"></script>
       <%@ include file="/jsp/header.jsp"%>
       <link href="<%= request.getContextPath() %>/css/H-ui.min.css" rel="stylesheet" type="text/css" />
	    <link href="<%= request.getContextPath() %>/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
		<link href="<%= request.getContextPath() %>/jslib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
		   $(function(){
			   $("#form-${objectNameLower}-update").validate({
				    
				    errorClass: "label.error", 
				    onkeyup: false,   
	                submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form  
	                	  var key=$("#${key}").val();
	                    
	                	  var url="<%= request.getContextPath() %>/${objectNameLower}/save${objectName}.do";
	                	  if(key!=""){
	                		  url="<%= request.getContextPath() %>/${objectNameLower}/update${objectName}ById.do";
	                	  }
	                	  $.post(url,$("form").serialize(),function(data){
	                		  
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
	<div class="pd-20">
	  <form action="" method="post" class="form form-horizontal" id="form-${objectNameLower}-update" >
	     
	    <div class="row cl" style="display: none">
	      <label class="form-label col-3"><span class="c-red">*</span>姓名：</label>
	      <div class="formControls col-9">
	       
	        <input type="text" class="input-text" value="" placeholder="" id="${key}" required  name="${key}" >
	      </div>
	     
	    </div>
	    
	     ${tableHtml}
	    
	    <div class="row cl">
	      <div class="col-9 col-offset-3">
	      
	         <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
	        <a class="btn btn-success radius"" onclick="cancle()" >取消</a>
	      </div>
	    </div>
	  </form>
	</div>
	</div>
	
</body>
</html>

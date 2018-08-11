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
	                  	    var key=$("#meterid").val();
						    var url="<%=path %>/nodeEnergy/updateGradient.do";
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
	  
		
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>节点1：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${graList.NUM1 }" style="width: 200px;" id="jd1" name="jd1"  required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	      <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>节点2：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${graList.NUM2 }" style="width: 200px;"placeholder="" id="jd2" name="jd2"  required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	      <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>节点3：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${graList.NUM3}" style="width: 200px;"placeholder="" id="jd3" name="jd3"  required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	  	     
	   
	      
	    
	     
	    
	     <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>节点4：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${graList.NUM4 }" style="width: 200px;"placeholder="" id="jd4" name="jd4"  required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	
	    
	    <div class="row cl">
	      <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
	        &nbsp;&nbsp;&nbsp;&nbsp;
	        <a class="btn btn-primary radius"  href="javascript:refresh()">&nbsp;&nbsp;返回&nbsp;&nbsp;</a>
	      </div>
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


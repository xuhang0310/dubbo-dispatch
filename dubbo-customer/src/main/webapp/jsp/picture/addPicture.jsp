<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <title>生产调度系统</title>
       
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
        <%@ include file="/jsp/header.jsp"%>
       <script type="text/javascript">
	<%-- 	   $(function(){
		   alert("moren");
			   $("#form").validate({
				    
				    errorClass: "label.error", 
				    onkeyup: false,   
	                submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form  
	                	 
	                      var key=$("#pic_id").val();
	                	  var url="<%=path %>/picture/savePicture.do";
	                	  if(key!=""){
	                		  url="<%=path %>/user/updateUserById.do";
	                	  }
	                
		                  var params=  new FormData($("#form")[0]); 
		                  
	                	  $.post(url,params,function(data){
	                		  
	                		  if(data.flag){
	                			  data.msg(data.messager, {icon: 1},function(){
	                				  refresh()
	                			  } );
	                			  
	                		  }else{
	                			  layer.msg(data.messager, {icon: 2});
	                		  }
	                		 
	                      },"json") 
	                }
	                
	                
	                
	                
	                
			   })
		   })
		    --%>
		   
		  		 function add(){
		   		//	 alert("add");
		   		//	 alert($("#pic_name").val());
		   			 if($("#pic_name").val().trim()==""){
		   			 alert("请输入图片名称");
		   			 return;
		   			 }else if($("#pic_fname").val().trim()==""){
		   			 alert("请选择文件");
		   			 return;
		   			 }else{
		   			 
				     var formData = new FormData($("#form")[0]);//用form 表单直接 构造formData 对象; 就不需要下面的append 方法来为表单进行赋值了。 
				    //alert(formData);
				     $.ajax({ 
				         async: false,//要求同步 不是不需看你的需求
				       <%--   url : "<%=path %>/picture/savePicture.do",   --%>
				         url : "<%=path %>/picture/addPicture.do",
				         type : 'POST',  
				         //dataType:"json";
				         data : formData,  
				         processData : false,  //必须false才会避开jQuery对 formdata 的默认处理   
				         contentType : false,  //必须false才会自动加上正确的Content-Type 
				         success :function(data) {
				         alert("上传成功");
				         refresh();
				         } ,  
				         error :function(data){
				         alert("上传完成！");
				         refresh();
				         }		         
				     });  
				    
				}
				}		   
		   
		   function cancle(){
			   var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
		   }
		   function refresh(){
		   //  alert("刷新");
			   parent.search();
				  var index = parent.layer.getFrameIndex(window.name);
				  parent.layer.close(index);
		   }
		</script>
        
       
</head>
  
<body>
<div class="page-content">
	<div class="pd-10">
	  <form action="" method="post" class="form form-horizontal" id="form" enctype="multipart/form-data" >
	  
	   <div class="row cl" style="display:none">
						<label class="form-label col-3"><span class="c-red">*</span>数据模型名称：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text" value=""
								placeholder="" id="pic_id" required name="pic_id">
						</div>
		</div>
					
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>图片名称：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="" placeholder="" id="pic_name" name="pic_name"  required >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>图片文件：</label>
	      <div class="formControls col-5">
	        <input type="file" class="input-text" value="" placeholder="" id="pic_fname" name="pic_fname" required >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    
	    <div class="row cl">
	      <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;上传&nbsp;&nbsp;" onclick="add()">
	      </div>
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


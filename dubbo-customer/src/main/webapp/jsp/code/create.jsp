<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <title>入网审批系统</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
       <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
       <%@ include file="/jsp/header.jsp"%>
      
		<script type="text/javascript">
		   $(function(){
			   $("#form-code-update").validate({
				    
				    errorClass: "label.error", 
				    onkeyup: false,   
	                submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form  
	                	  
	                	  var columns="";
	       			      $("input[name=columns]").each(function(){
	       				   columns+=$(this).val()+"@";
	       			      });
	       			      columns=columns.substr(0,columns.length-1);
	       			      
	       			   var comments="";
	       			      $("input[name=comments]").each(function(){
	       			    	comments+=$(this).val()+"@";
	       			      });
	       			   comments=comments.substr(0,comments.length-1);
	       			      
	       			      var ishide="";
	       			      $("select[name=ishide]").each(function(){
	       			    	ishide+=$(this).val()+"@";
	       			      });
	       			   ishide=ishide.substr(0,ishide.length-1);
	       			      
	       			      var formtype="";
	       			      $("select[name=formtype]").each(function(){
	       			    	formtype+=$(this).val()+"@";
	       			      });
	       			      formtype=formtype.substr(0,formtype.length-1);
	       			      
	       			     var url="<%= path %>/code/createCode.do"; 
	                	 var params=$.param({'columnsArr': columns,'formtypeArr':formtype,'commentsArr':comments,'ishideArr':ishide}) + '&' + $('form').serialize();
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
		   function getFormValue(){ 
			   var columns="";
			   $("input[name=columns]").each(function(){
				   columns+=$(this).val()+",";
			   });
			    return columns.substr(0,date.length-1);;
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
		   
		   function toogelTable(){
			   var table=$("#tablename").val();
			   var url="<%= path %>/code/getTableColumn.do";
			   $.post(url,{tablename:table},function(data){
				   var tableHtml="";
				   $.each(data, function(idx, obj) {
					     tableHtml+="<tr><td><input class='input-text'  readonly name='columns' value='"+obj.COLUMN_NAME+"'></td><td><input class='input-text'   name='comments' value='"+obj.COMMENTS+"'></td><td><select name='ishide' class='form-control'><option value='1'>是</option><option value='2'>否</option></select></td><td><select name='formtype' class='form-control'><option value='1'>输入框</option><option value='2'>下拉框</option></select></td><td><select name='isrequired' class='form-control'><option value='1'>必填</option><option value='2'>非必填</option></select></td><tr>"
					});
				   $("#tocolumns").empty();
				   $("#tocolumns").append(tableHtml);
			   },'json')
			    
		   }
		   
		   function addMenu(){
			   layer_show('新增','<%= path %>/menu/editMenuLayer.do','500')
		   }
		  
		</script>
</head>
  
<body style="margin:10px">
	<div class="pd-20">
	  <form action="" method="post" class="form form-horizontal" id="form-code-update" >
	     <div class="row cl" >
	      <label class="form-label col-3"><span class="c-red">*</span>上级包名：</label>
	      <div class="formControls col-3">
	       
	        <input type="text" class="input-text" value="" placeholder="" id="packageName" required  name="packageName" >
	      </div>
	       <label class="form-label col-3"><span class="c-red">*</span>处理类名：</label>
	      <div class="formControls col-3">
	        
	        <input type="text" class="input-text" value="" placeholder="" id="objectName" required  name="objectName" >
	      </div>
	    </div>
	   
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>开发者：</label>
	      <div class="formControls col-3">
	        <input type="text" class="input-text"   value="" placeholder="" id="author" name="author" datatype="*2-16" nullmsg="用户名不能为空">
	      </div>
	      <label class="form-label col-3"><span class="c-red">*</span>项目名称：</label>
	      <div class="formControls col-3">
	       <input type="text" class="input-text" value="" placeholder="" id="project" required  name="project" >
	       
	      </div>
	    </div>
	    
	    
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>数据库表：</label>
	      <div class="formControls col-3">
	         <select class="selectpicker" data-live-search="true"  name="tablename" id="tablename" title="请选择一个表" onchange="toogelTable()">
	            <c:forEach var="temp" items="${tableList }">
	                <option value="${temp.TABLE_NAME }">${temp.TABLE_NAME }</option>
	            </c:forEach>
	         </select>
	       
	      </div>
	       <label class="form-label col-3"><span class="c-red">*</span>主键字段：</label>
	      <div class="formControls col-3">
	       <input type="text" class="input-text" value="" placeholder="" id="key" required  name="key" >
	       
	      </div>
	      
	    </div>
	    
	    <div class="row cl">
	        <label class="form-label col-3"><span class="c-red">*</span>表单设置：</label>
	         <div   class="formControls col-9">
	                <table  class="table table-hover">
				
			   <thead>
			      <tr>
			         
			         <th>字段名称</th>
			         <th>字段备注</th>
			         <th>是否生成表单</th>
			         <th>表单样式</th>
			         <th>是否必填</th>
			      </tr>
			   </thead>
			   <tbody id="tocolumns">
			     
			   </tbody>
			</table>
	         </div>
	      
	    </div>
	    
	   
	    
   
	    
	    <div class="row cl">
	      <div class="col-9 col-offset-3">
	      
	         <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
	         <a class="btn btn-success radius"" onclick="addMenu()" >新增菜单</a>
	        <a class="btn btn-success radius"" onclick="addTableChart()" >生成数据列</a>
	        <a class="btn btn-success radius"" onclick="cancle()" >取消</a>
	      </div>
	    </div>
	  </form>
	</div>
	</div>
	
</body>
</html>



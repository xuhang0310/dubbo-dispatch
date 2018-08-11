<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <title>生产调度系统</title>
       
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
           <%@ include file="/jsp/header.jsp"%>
        <script type="text/javascript" src="<%=path%>/plugins/layout/jquery.layout.js"></script>
         <link href="<%= request.getContextPath() %>/css/ace-tab.css" rel="stylesheet" type="text/css" />
		<script src="<%=path %>/js/date-time/bootstrap-datetimepicker.min.js"></script>
           <script src="<%= request.getContextPath() %>/js/date-time/bootstrap-datetimepicker.zh-CN.js"></script>
    	<link rel="stylesheet" href="<%=path%>/css/datatime/bootstrap-datetimepicker.min.css" /> 
     
     
     
       <script type="text/javascript">
     /* $("#datetimeStart").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose:true,
        startDate:new Date()
    }).on("click",function(){
        $("#datetimeStart").datetimepicker("setEndDate",$("#datetimeEnd").val())
    }); */
		   $(function(){

			   $("#form-member-edit").validate({
				    
				    errorClass: "label.error", 
				    onkeyup: false,   
	                submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form  
	                	 
	                      var key=$("#bas_id").val();
	                	  var url="<%=path %>/dataparam/saveParam.do";
	                	<%--   if(key!=""){
	                		  url="<%=path %>/user/updateUserById.do";
	                	  } --%>
	                	
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
	  
	   <div class="row cl" style="display:none">
						<label class="form-label col-3"><span class="c-red">*</span>数据模型名称：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text" value=""
								placeholder="" id="bas_id" required name="bas_id">
						</div>
		</div>
					
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>采暖期名称：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="" placeholder="" id="cnqname" name="cnqname"  required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>采暖期开始日期：</label>
	      <div class="formControls col-5">
	        <!-- <input type="text" class="input-text" value="" placeholder="" id="zbsz" name="zbsz" required="required" > -->
			      <input type="text" onfocus="WdatePicker()" id="datemin" name="datemin" value="" class="input-text Wdate" style="width:224px;">
        </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>采暖期结束日期：</label>
	      <div class="formControls col-5">	      
				    <input type="text" onfocus="WdatePicker()" id="datemax" name="datemax"value="${ paramsMap.enddate}" class="input-text Wdate" style="width:224px;">
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	   
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>理论热指标：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="" placeholder="" id="llrzb" name="llrzb" required="required">
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	     <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>室内设计温度：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="" placeholder="" id="snwd" name="snwd" required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	      <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>室外设计温度：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="" placeholder="" id="swwd" name="swwd" required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>供暖计算温度：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="" placeholder="" id="jswd" name="jswd" required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	     
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>是否当前采暖期：</label>
	      
	            <select class="selectpicker"  name="cnq" id="cnq" title="请选择类型" >
		            <option value="" selected>--请选择--</option>
		             <option value="1">是</option>
		             <option value="0">否</option>
	            </select>
	    </div>
	    
	       <div class="row cl">
	      <label class="form-label col-3">备注：</label>
	      <div class="formControls col-5">
	        <textarea id="bz"name="bz" cols="" rows="" class="textarea"  placeholder="说点什么...最少输入10个字符"  onKeyUp="textarealength(this,100)"></textarea>
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


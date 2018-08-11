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
       
       
         function add(){
      
			  //  alert(isNaN("dd"));
			       if($("#qxkssj").val().trim()==""){
			       	layer.msg('抢修开始时间为空！', {icon: 2});
			       	return false;
			       }else if($("#yjwcsj").val().trim()==""){
			       	layer.msg('预计完成时间为空！', {icon: 2});
			       	return false;
			       }else if($("#yxmj").val().trim()==""||isNaN($("#yxmj").val())){
			       		layer.msg('影响面积必须为数字', {icon: 2});
			       	return false;
			       }else if($("#yxhs").val().trim()==""||isNaN($("#yxhs").val())){
			       	layer.msg('影响户数必须为数字！', {icon: 2});
			       	return false;
			       }else if($("#infgj").val().trim()==""){
			       layer.msg('影响公建换热站及范围不能为空！', {icon: 2});
			       }else if($("#infmy").val()==""){
			       layer.msg('影响民用换热站及范围不能为空！', {icon: 2});
			       }
			       
			       
			       
				     var formData = new FormData($("#form-member-edit")[0]);//用form 表单直接 构造formData 对象; 就不需要下面的append 方法来为表单进行赋值了。 
				  	    var key=$("#defectId").val();
						    var url="<%=path %>/defect/dealDefect.do";
						<%--     if(key!=""){
							var url="<%=path %>/defect/updateDefect.do";
					  	  } --%>
				  	
				     $.ajax({ 
				         async: false,//要求同步 不是不需看你的需求
				         url : url,  
				         type : 'POST',  
				         data : formData,  
				         processData : false,  //必须false才会避开jQuery对 formdata 的默认处理   
				         contentType : false,  //必须false才会自动加上正确的Content-Type 
				         success :function(data) {
				         var result = eval("(" + data + ")");
				         if(result.status=="y"){
				         layer.msg(result.info, {icon: 1},function(){
	                				  refresh()
	                			  });
				         }else{
				      	 layer.msg(result.info, {icon: 2},function(){
	                				  refresh()
	                			  });
				         }
				         } ,  
				         error :function(data){
				         layer.msg("保存失败", {icon: 2});
				         refresh();
				         }		         
				     });  
				    
				}
			
	   function showpicture(){
	    var fileurl=$("#fileurl").val();
			 if(fileurl==""){
			 layer.msg('该缺陷未上传图片！', {icon: 2});
			 return false		 
			 }
		layer_show('图片预览','<%= request.getContextPath() %>/defectfile/'+fileurl,800);	 
			 
	   }
       
       
        function download(){
			 var fileurl=$("#fileurl").val();
			 if(fileurl==""){
			 layer.msg('该缺陷未上传文件！', {icon: 2});
			 return false		 
			 }
			 var url="<%=path %>/defect/downLoad.do?filename="+fileurl;
			      $.ajax({ 
				         async: false,//要求同步 不是不需看你的需求
				         url : url,  
				         type : 'POST',  
				         data : fileurl,  
				         processData : false,  //必须false才会避开jQuery对 formdata 的默认处理   
				         contentType : false,  //必须false才会自动加上正确的Content-Type 
				         success :function(data) {/* 
				         var result = eval("(" + data + ")");
				         if(result.status=="y"){
				         layer.msg(result.info, {icon: 1},function(){
	                				  refresh()
	                			  });
				         }else{
				      	 layer.msg(result.info, {icon: 2},function(){
	                				  refresh()
	                			  });
				         } */
				         } ,  
				         error :function(data){
				     /*     layer.msg("保存失败", {icon: 2});
				         refresh(); */
				         }		         
				     });  
	 }		
			
         	//设置状态
		function setStatus(status){
			//0 待处理；1 已处理；2 已消缺；草稿3
			document.getElementById("status").value = status;
			//alert(document.getElementById("defect.status").value);
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
		</script>
        
       
</head>
  
<body>
<div class="page-content">
	<div class="pd-10">
	  <form action="" method="post" class="form form-horizontal" id="form-member-edit" enctype="multipart/form-data">
	  
		<input type="hidden" value="${defectlist.ID }" name="defectId" id="defectId">	
		 <input type="hidden" name="status" id="status" value="${defectlist.STATUS}">
		 <input type="hidden" name="fileurl" id="fileurl" value="${defectlist.FILEURL}">
	<%-- 	<input type="hidden" value="${defectlist.POSITION }" name="position" id="position">			 --%>		
	    <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text" value="${defectlist.NAME }" placeholder="" id="qxmc" name="qxmc"  required="required" style="width: 240px;" readonly="readonly">
	      </div>
	      <label class="form-label col-2"><span class="c-red">*</span>提交人：</label>
	       <div class="col-4">
	            <input type="text" class="input-text" value="${tjr.DISPLAYNAME }" placeholder="" id="qxmc" name="qxmc"  readonly="readonly" required="required" style="width: 240px;" >
	      </div>
	    </div>

	    <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>填报公司：</label>
	      <div class="col-4">
	           <input type="text" class="input-text" value="${defectlist.ORGNAME }" placeholder="" id="tbgs" name="tbgs"  required="required" style="width: 240px;" readonly="readonly">
	      </div>
	      <label class="form-label col-2"><span class="c-red">*</span>发生地点：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text" value="${defectlist.POSITION }" placeholder="" id="fsdd" name="fsdd"  required="required" style="width: 240px;" readonly="readonly">
	      </div>
	    </div>
	
	       <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>发生时间：</label>
	      <div class="formControls col-4">
	      <input type="text"  id="fssj" name="fssj"  placeholder="发生时间"  value="${defectlist.DEFECTDATE }" class="input-text Wdate" style="width:240px;" readonly="readonly">
	      </div>
	       <label class="form-label col-2"><span class="c-red">*</span>填报时间：</label>
	      <div class="formControls col-4">
	      <input type="text"  id="tbsj" name="tbsj"  placeholder="填报时间"  value="${defectlist.CREATEDATE }" class="input-text Wdate" style="width:240px;"readonly="readonly">
	      </div>
	    </div>
	      
	      
	   	   <div class="row cl">
	   	      <label class="form-label col-2"><span class="c-red">*</span>缺陷类型：</label>
	      <div class="col-4">
	            <select class="selectpicker2"  name="qxlx" id="qxlx" style="width: 240px;height: 31px"onmousedown="javascript:return false;">
		            <option value="" >--请选择--</option>
		                 <c:forEach var="dt" items="${defectType}">
			            		<option value="${dt.ID}" <c:if test="${defectlist.TYPEID==dt.ID}">selected</c:if>>${dt.NAME}</option>
			            </c:forEach>
	            </select>
	      </div>
	   	   <label class="form-label col-2"><span class="c-red">*</span>图片预览：</label>
	      <div class="formControls col-4">
	    
	         <a class="btn" href="javascript:showpicture()" style="width: 240px;"><i class="icon-download2 "></i>${defectlist.FILEURL }</a>	
	
	      </div>
	   
	    </div>
	      	<div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>抢修开始时间：</label>
	      <div class="formControls col-4">
	      <input type="text" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })" id="qxkssj" value="${defectlist.STARTDEALDATE }"name="qxkssj"  placeholder="抢修开始时间"  class="input-text Wdate" style="width:240px;">
	      </div>
	       <label class="form-label col-2"><span class="c-red">*</span>预计完成时间：</label>
	      <div class="formControls col-4">
	      <input type="text" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })" id="yjwcsj" name="yjwcsj"  value="${defectlist.ESTDATE }"placeholder="预计完成时间"  class="input-text Wdate" style="width:240px;">
	      </div>
	    </div>
	    
	     <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>影响面积：</label>
	      <div class="formControls col-4">
	      <input type="text" id="yxmj" name="yxmj"  value="${defectlist.INFAREA }" class="input-text" style="width:240px;">
	      </div>
	       <label class="form-label col-2"><span class="c-red">*</span>影响户数：</label>
	      <div class="formControls col-4">
	      <input type="text"  id="yxhs" name="yxhs"  value="${defectlist.INFHHS }"class="input-text" style="width:240px;">
	      </div>
	    </div>
	    
	   <div class="row cl">
	      <label class="form-label col-2">影响公建&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />换热站及范围：</label>
	      <div class="formControls col-4">
	      <!-- placeholder="说点什么...最少输入10个字符"  onKeyUp="textarealength(this,100)"-->
	        <textarea name="infgj" id="infgj" cols="37" rows="2" class="textarea2" style="resize:none;">${defectlist.INFGJ }</textarea>
	        <!-- <p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p> -->
	      </div>
	 	<label class="form-label col-2">影响民用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />换热站及范围：</label>
	      <div class="formControls col-4">
	      <!-- placeholder="说点什么...最少输入10个字符"  onKeyUp="textarealength(this,100)"-->
	        <textarea name="infmy" id="infmy" cols="37" rows="2" class="textarea2" style="resize:none;">${defectlist.INFMY }</textarea>
	        <!-- <p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p> -->
	      </div>
	    </div>
	    
	    
	      
	      <div class="row cl">
	      <label class="form-label col-2">缺陷描述：</label>
	      <div class="formControls col-10">
	      <!-- placeholder="说点什么...最少输入10个字符"  onKeyUp="textarealength(this,100)"-->
	        <textarea name="bz" cols="70" rows="3" value="" class="textarea2"   id="bz" style="width: 241px;resize:none;" readonly="readonly">${defectlist.MESSAGE }</textarea>
	        <!-- <p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p> -->
	      </div>
	    <!--   <div class="col-4"> </div> -->
	    </div>
	    
	  
	
	    
	    <div class="row cl">
	      <div class="col-8 col-offset-3">
	     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        <input class="btn btn-primary radius" type="button" onclick="add();" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" onclick="setStatus(1);add();" value="&nbsp;&nbsp;开始处理&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" onClick="refresh();" value="&nbsp;&nbsp;返回&nbsp;&nbsp;">
	      </div>
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


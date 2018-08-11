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
			       if($("#qxmc").val().trim()==""){
			       	layer.msg('请填写标题！', {icon: 2});
			       	return false;
			       }else if($("#fssj").val().trim()==""){
			       	layer.msg('请填写发生时间！', {icon: 2});
			       	return false;
			       }else if($("#qxlx").val()==""){
			       	layer.msg('请选择缺陷类型！', {icon: 2});
			       	return false;
			       }else if($("#fsdd").val().trim()==""){
			       	layer.msg('请填写发生地点！', {icon: 2});
			       	return false;
			       }
			       
			       
			       
				     var formData = new FormData($("#form-member-edit")[0]);//用form 表单直接 构造formData 对象; 就不需要下面的append 方法来为表单进行赋值了。 
				  	    var key=$("#defectId").val();
						    var url="<%=path %>/defect/saveDefect.do";
						    if(key!=""){
							var url="<%=path %>/defect/updateDefect.do";
					  	  }
				  	
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
						   
		   	//设置状态
		function setStatus(status){
			//0 待处理；1 已处理；2 已消缺；草稿3
			document.getElementById("status").value = status;
			//alert(document.getElementById("defect.status").value);
		}
		   
		   
		   	function getProjectList(){
				var stationtype = document.getElementById("stationtype").value;
				var url="<%=path %>/basmeter/getProjectList.do?stationtype=" + stationtype;
				$.ajax({
			    	type: "POST",
			    	url: url,
			    	success: function(data){
			    		 $("#mc").empty();
			    		 $("#mc").append(data);
						 $("#mc").selectpicker('refresh');
			    	},
			    	error: function(){ alert("请求失败!"); }
			    });
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
	<%-- 	<input type="hidden" value="${defectlist.POSITION }" name="position" id="position">			 --%>		
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${defectlist.NAME }" placeholder="" id="qxmc" name="qxmc"  required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	  	 
	  	<div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>发生时间：</label>
	      <div class="formControls col-5">
	      <input type="text" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })" id="fssj" name="fssj"  placeholder="发生时间"  value="${defectlist.DEFECTDATE }" class="input-text Wdate" style="width:231px;">
	      </div>
	      <div class="col-4"> </div>
	    </div>
	  	 
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>缺陷类型：</label>
	      
	            <select class="selectpicker"  name="qxlx" id="qxlx" style="width: 231px;height: 31px">
		            <option value="" >--请选择--</option>
		                 <c:forEach var="dt" items="${defectType}">
			            		<option value="${dt.ID}" <c:if test="${defectlist.TYPEID==dt.ID}">selected</c:if>>${dt.NAME}</option>
			            </c:forEach>
	            </select>
	            
	            
	    </div>
	
	      <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>发生地点：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${defectlist.POSITION }" placeholder="" id="fsdd" name="fsdd"  required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	      
	   	   <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>上传图片：</label>
	      <div class="formControls col-5">
	        <input type="file" class="input-text" value="" placeholder="" id="fname" name="fname" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	      
	      <div class="row cl">
	      <label class="form-label col-3">缺陷描述：</label>
	      <div class="formControls col-5">
	      <!-- placeholder="说点什么...最少输入10个字符"  onKeyUp="textarealength(this,100)"-->
	        <textarea name="bz" cols="" rows="" value="" class="textarea"   id="bz">${defectlist.MESSAGE }</textarea>
	        <p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	  
	
	    
	    <div class="row cl">
	      <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="button" onclick="setStatus(3);add();" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" onclick="setStatus(0);add();" value="&nbsp;&nbsp;发送&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" onClick="refresh();" value="&nbsp;&nbsp;返回&nbsp;&nbsp;">
	      </div>
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


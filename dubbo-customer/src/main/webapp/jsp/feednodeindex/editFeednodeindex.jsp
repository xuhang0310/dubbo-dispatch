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
       <script type="text/javascript">
		   $(function(){
			   $("#form-member-edit").validate({
				    
				    errorClass: "label.error", 
				    onkeyup: false,   
	                submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form  
	                	    var id=$("#zbid").val();
	                	  //  alert(id);
						    var url="<%=path %>/feednodeindex/saveBase.do";
						    if(id!=""){
							  var url="<%=path %>/feednodeindex/updateZb.do?id="+id;
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
		   
		   function getProjectList(){
	//	   alert("xx");
				var stationtype = document.getElementById("lx").value;
				var url="<%=path %>/basmeter/getProjectList.do?stationtype=" + stationtype;
				$.ajax({
			    	type: "POST",
			    	url: url,
			    	success: function(data){
			    		 //alert(data);
			    		 $("#bm").empty();
			    		 $("#bm").append(data);
						 $("#bm").selectpicker('refresh');
			    	},
			    	error: function(){ alert("请求失败!"); }
			    });
			}
	
		   
		</script>
        
       
</head>
  
<body>
<div class="page-content">
	<div class="pd-10">
	  <form action="" method="post" class="form form-horizontal" id="form-member-edit">
	  <input type="hidden" name="zbid"  id="zbid" value="${zb.ID }">
	   <div class="row cl" style="display:none">
						<label class="form-label col-3"><span class="c-red">*</span>数据模型名称：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text" value=""
								placeholder="" id="bas_id" required name="bas_id">
						</div>
		</div>
					
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>指标类型：</label>
	      <div class="formControls col-5">
<%-- 	        <input type="text" class="input-text" value="${zb.indextype}" placeholder="" id="zblx" name="zblx"  required="required" >--%>	     
			<select class="selectpicker"  name="zblx" id="zblx" style="width: 232px;height: 31px">
		                <c:forEach var="lx" items="${zblx}">
			            		<option value="${lx.ID}" <c:if test="${zb.indextype==lx.ID}">selected</c:if> >${lx.NAME}</option>
			            </c:forEach>
  			</select>
		 </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>指标数值：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${zb.indexvalue }" placeholder="" id="zbsz" name="zbsz" required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>指标单位：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${zb.indexunit }" placeholder="" id="zbdw" name="zbdw"  required="required">
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	   
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>指标下浮：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${zb.indexlow }" placeholder="" id="zbxf" name="zbxf" required="required">
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	     <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>指标上浮：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${zb.indexhigh }" placeholder="" id="zbsf" name="zbsf" required="required" >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	   <%--     <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>类型：</label>
	 		<!-- 	<select class="selectpicker" data-live-search="true"  name="lx" id="lx" title="请选择类型" > -->
	 			 <select class="selectpicker"  onchange="getProjectList()"  name="lx" id="lx" title="请选择类型" >
		              <option value="1" <c:if test="${zb.STATIONTYPE=='1' }">selected</c:if>>换热站 </option>
	                 <option value="99" <c:if test="${zb.STATIONTYPE=='99'}">selected</c:if>>热源</option>
	            </select>
	    </div> --%>
	        <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>站类型：</label>
	      
	            <select class="selectpicker" onchange="getProjectList()"  name="lx" id="lx" style="width: 232px;height: 31px">
		            <option value="" >--请选择--</option>
		             <option value="1" <c:if test="${zb.STATIONTYPE=='1'}">selected</c:if>>换热站</option>
            		<option value="99" <c:if test="${zb.STATIONTYPE=='99'}">selected</c:if>>热源</option>
	            </select>
	    </div>
	      <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>名称：</label>
	      <div class="formControls col-5">
	      <%--   <input type="text" class="input-text" value="${zb.stationcode }" placeholder="" id="bm" name="bm" required="required" > --%>
	     		 <select class="selectpicker"  data-live-search="true" name="bm" id="bm" style="width: 232px;height: 31px;" >
		             	  <option value="">--请选择--</option>
		             	<c:forEach var="lc" items="${lx}">
			            	<option value="${lc.STATIONCODE}" <c:if test="${lc.STATIONCODE==zb.STATIONCODE}">selected</c:if>>${lc.NAME}</option>
			        
			            </c:forEach>
			        
	            </select>
	            
	            
	            
	            
	     
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


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
	                  	    var key=$("#deviceid").val();
						    var url="<%=path %>/innerDevice/saveDevice.do";
						    if(key!=""){
							var url="<%=path %>/innerDevice/updateDevice.do";
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
		
		function getProjectList(){
				var type=$("#type").val();

				var orgid = document.getElementById("orgid").value;
				if(orgid!=null){
				var url="<%=path %>/nodeEnergy/getProjectList.do?orgid=" + orgid;
				if(type==99){
				var url="<%=path %>/nodeEnergy/getProjectListFeed.do?orgid=" + orgid;
				}
				
				$.ajax({
			    	type: "POST",
			    	url: url,
			    	success: function(data){
			    		 $("#hrz").empty();
			    	//	 var i="<option value=\"\" selected>--请选择--</option>";
			    	//	 $("#nodename").append(i);		    		
			    		 $("#hrz").append(data);
			    		 $("#hrz").selectpicker('refresh');
			    	},
			    	error: function(){ alert("请求失败!"); }
			    });
			    
			    }
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
	  <form action="" method="post" class="form form-horizontal" id="form-member-edit">
	  		<input type="hidden" value="${device.ID }"name="deviceid" id="deviceid">	
	  		<input type="hidden" name="type" id="type" value="${type }">
	  	   <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>管理单位：</label>
	        <div class="col-4">
	            <select class="selectpicker" onchange="getProjectList()"  name="orgid" id="orgid" style="width: 232px;height: 31px">
		          <!--   <option value="" >--请选择--</option> -->
		            <c:forEach var="org" items="${orgList}">
		             <option value="${org.ORGID }" <c:if test="${device.ID==org.ORGID}">selected</c:if>>${org.ORGNAME }</option>
            		</c:forEach>
	            </select>
	          </div>  
	              <label class="form-label col-2"><span class="c-red">*</span>
	              	<c:if test="${type == 1 }">换热站：</c:if>
	              	<c:if test="${type == 99 }">热源：</c:if>
	              </label>
	        <div class="col-4">
	            <select class="selectpicker"  data-live-search="true" name="hrz" id="hrz" style="width: 232px;height: 31px;" >
		             	  <option value="">--请选择--</option>
		             	<c:forEach var="lc" items="${node}">
			            		<option value="${lc.CODE}" <c:if test="${lc.CODE==device.CODE}">selected</c:if>>${lc.NAME}</option>
			            </c:forEach>
	            </select>
	          </div>  
	    </div>
		
		  <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>设备名称：</label>
	      <div class="col-4">
	           <input type="text" class="input-text" value="${device.NAME }" id="sbmc" name="sbmc"  required="required" style="width: 240px;">
	      </div>
	      <label class="form-label col-2"><span class="c-red">*</span>规格型号：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text" value="${device.GGXH }" id="ggxh" name="ggxh"  required="required" style="width: 240px;">
	      </div>
	    </div>
		
		 <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>制造厂家：</label>
	      <div class="col-4">
	           <input type="text" class="input-text" value="${device.ZZCJ }" id="zzcj" name="zzcj"  required="required" style="width: 240px;">
	      </div>
	      <label class="form-label col-2"><span class="c-red">*</span>存放地点：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text" value="${device.CFDD }" id="cfdd" name="cfdd"  required="required" style="width: 240px;">
	      </div>
	    </div>
	    
	     <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>安装时间：</label>
	      <div class="col-4">
	           <input type="text" class="input-text Wdate" onfocus="WdatePicker()"value="${device.AZSJ }" id="azsj" name="azsj"  required="required" style="width: 240px;">
	      </div>
	      <label class="form-label col-2"><span class="c-red">*</span>入厂时间：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text Wdate" onfocus="WdatePicker()"value="${device.RCSJ }" id="rcsj" name="rcsj"  required="required" style="width: 240px;">
	      </div>
	    </div>
		
		 <div class="row cl">
	      <label class="form-label col-2">设备原值(万元)：</label>
	      <div class="col-4">
	           <input type="text" class="input-text" value="${device.SBYZ }" id="sbyz" name="sbyz"  style="width: 240px;">
	      </div>
	      <label class="form-label col-2"><span class="c-red">*</span>设备类型：</label>
	      <div class="formControls col-4">
	        	  <select class="selectpicker"  name="sblx" id="sblx" title="请选择类型" style="width: 232px;height: 31px">
		           <!--  <option value="">请选择...</option> -->
		             <c:forEach var="lx" items="${sblx}">
            		<option value="${lx.ID }"<c:if test="${device.SSLX==lx.ID}">selected</c:if> >${lx.NAME }</option>
            		</c:forEach>
	            </select>
	      </div>
	    </div>
	  	     
	  <div class="row cl">
	      <label class="form-label col-2">审核人员：</label>
	      <div class="col-4">
	           <input type="text" class="input-text" value="${device.SHRY }" id="shry" name="shry"  style="width: 240px;">
	      </div>
	      <label class="form-label col-2">经营公司：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text" value="${device.JYGS }" id="jjgs" name="jygs" style="width: 240px;">
	      </div>
	  </div>
	      <div class="row cl">
	      <label class="form-label col-2">用途类型：</label>
	      <div class="col-4">
	           <input type="text" class="input-text" value="${device.YTLX }" id="ytlx" name="ytlx"   style="width: 240px;">
	      </div>
	      <label class="form-label col-2">设备参数：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text" value="${device.SBCS }" id="sbcs" name="sbcs"  style="width: 240px;">
	      </div>
	  </div>
	    
	   <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>出厂日期：</label>
	      <div class="col-4">
	           <input type="text" class="input-text Wdate" onfocus="WdatePicker()"value="${device.CCSJ }" id="ccrq" name="ccrq"  required="required" style="width: 240px;">
	      </div>
	      <label class="form-label col-2">出厂编号：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text" value="${device.CCBH }" id="ccbh" name="ccbh"  style="width: 240px;">
	      </div>
	    </div>
	    
	    
	       <div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>数量：</label>
	      <div class="col-4">
	           <input type="text" class="input-text " value="${device.SL }" id="sl" name="sl"  required="required" style="width: 240px;">
	      </div>
	      <label class="form-label col-2"><span class="c-red">*</span>单位：</label>
	      <div class="formControls col-4">
	        <input type="text" class="input-text" value="${device.DW }" id="dw" name="dw"  required="required" style="width: 240px;">
	      </div>
	    </div>
	    

	
	    
	    <div class="row cl">
	   
	    <!--   <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;提交&nbsp;&nbsp;">
	      </div> -->
	     <div class="col-5"> </div>
	      <div class="col-7"> 
	      <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       <span><a class="btn btn-primary radius" href="javascript:refresh()"></i>&nbsp;&nbsp;返回&nbsp;&nbsp;</a></span>
	      </div>
	
	      
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


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
	                	 
	                      var key=$("#codeid").val();
	                	  var url="<%=path %>/dataTeam/saveDataIndex.do";
	                	  if(key!=""){
	                		  url="<%=path %>/dataTeam/updateDataIndexById.do";
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
		   
		   function editDataIndex(){
				
			   var id = $("#codeid").val();
			   if (id!=null&&id!="") {
				   update()
				}else{
				   add()
				}
			   //layer.msg('请联系开发人员', {icon: 2});
		   }
		   
		   function add(){
				var params= $('form').serialize();   
            	var url="<%=path %>/dataIndex/saveDataIndex.do";
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
		   
		   function update(){
				var params= $('form').serialize();   
               	var url="<%=path %>/dataIndex/editDataIndex.do";
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
		   
		  /*  $("input[name='checkbox']").each(function(){ 
			   if($(this).attr("checked")) 
			   { 
			   $(this).removeAttr("checked"); 
			   } 
		   }); */

		   
		</script>
        
       
</head>
  
<body>
<div class="page-content">
	<div class="pd-10">
	  <form action="" method="post" class="form form-horizontal" id="form-member-edit">
	  <input type="hidden" id="codeid" name="codeid" value="${index.ID }"/>
	  <input type="hidden" id="sorder" name="sorder" value="${index.SORDER }"/>
	  <input type="hidden" id="selectmaxsorder" name="selectmaxsorder" value="${selectmaxsorder }"/>
	  <input type="hidden" id="schedulerid" name="schedulerid"  value="${schedulerid }"/>
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>工作日期：</label>
	      <div class="formControls col-5">	      
			 <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="sdate" name="sdate" value="${index.sdate}" class="input-text Wdate" style="width:190px;">
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>工作内容：</label>
	      <div class="formControls col-5">
	        <textarea id="content" name="content"  cols="" rows="" class="textarea"  placeholder="说点什么...最少输入10个字符"  onKeyUp="textarealength(this,100)">${index.CONTENT }</textarea>
	        <p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
		<div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>所属部门：</label>
	      <div class="formControls col-5">
	        <select class="selectpicker" data-live-search="true" style="width: 190px;height: 30px;"  name="orgcode" id="orgcode">
	            	<option value="">-----请选择-----</option>
	            	<c:forEach var="org" items="${orgobj}">
	            		<option value="${org.orgid}" <c:if test="${org.orgid==index.ORGCODE}">selected</c:if>><c:out value="${org.orgname}"/></option>
	            	</c:forEach>
	            </select>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>工作类别：</label>
	      <div class="formControls col-5">
	        <select class="selectpicker" data-live-search="true" style="width: 190px;height: 30px;"  name="stype" id="stype">
	            	<option value="">-----请选择-----</option>
	            	<c:forEach var="bplo" items="${bplo}">
						<c:if test='${bplo.PNAME eq "调度类别"}'>
							<option value="<c:out value="${bplo.ID}"/>" <c:if test="${bplo.ID==index.STYPE}">selected</c:if>>
								<c:out value="${bplo.NAME}" />
							</option>
						</c:if>
					</c:forEach>
	            </select>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
		 <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>紧急程度：</label>
	      <div class="formControls col-5">
	        <select class="selectpicker" data-live-search="true" style="width: 190px;height: 30px;"  name="emery" id="emery">
	            	<option value="">-----请选择-----</option>
	            	<c:forEach var="bplo" items="${bplo}">
						<c:if test='${bplo.PNAME eq "应急级别"}'>
							<option value="<c:out value="${bplo.ID}"/>" <c:if test="${bplo.ID==index.EMERY}">selected</c:if>>
								<c:out value="${bplo.NAME}" />
							</option>
						</c:if>
					</c:forEach>
	            </select>
	      </div>
	      <div class="col-4"> </div>
	    </div>
		 
		<div class="row cl">
	      <label class="form-label col-3">完成人：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text"  value="${index.FINISHEDUSER}" placeholder="" id="finisheduser" name="finisheduser"  required >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="button" onclick="editDataIndex();" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" onClick="refresh();" value="&nbsp;&nbsp;返回&nbsp;&nbsp;">
	      </div>
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


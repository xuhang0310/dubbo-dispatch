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
	                	 
	                      var key=$("#teamid").val();
	                	  var url="<%=path %>/dataTeam/saveDataTeam.do";
	                	  if(key!=""){
	                		  url="<%=path %>/dataTeam/updateDataTeamById.do";
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
		   
		   function editDataTeam(){
			   /* $("input[name='principal']:checkbox:checked").each(function(){ 
				  var aa = $(this).val();
				  alert(aa+",");
			   }); */
			 	//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
				//这里明确return false的话表单将不会提交;	
				var length = $("input[name='principal']:checked").length;
				var length2 = $("input[name='watch']:checked").length;
				
				if(length == 0){
					layer.msg('请选择值班负责人！', {icon: 2})
					//$.ligerDialog.warn('请选择值班负责人！','提示');
					return false;
				}
				if(length2 == 0){
					layer.msg('请选择值班人！', {icon: 2})
					//$.ligerDialog.warn('请选择值班人！','提示');
					return false;
				}
				
			   var id = $("#teamid").val();
			   if (id!=null&&id!="") {
				   update()
				}else{
				   add()
				}
			   //layer.msg('请联系开发人员', {icon: 2});
		   }
		   
		   function add(){
				var params= $('form').serialize();   
            	var url="<%=path %>/dataTeam/saveDataTeam.do";
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
               	var url="<%=path %>/dataTeam/editDataTeam.do";
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
	  <input type="hidden" id="teamid" name="teamid" value="${dataTeam.TEAMID }"/>
	  <input type="hidden" id="program" name="program" size="50" value="1112150001"/>
	  <input type="hidden" id="watchname" name="watchname"  value=""/>
	  <input type="hidden" id="principalDis" name="principalDis"  value=""/>
	    
	   <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>班组名称：</label>
	      <div class="formControls col-5">
	        <input type="text" class="input-text" value="${dataTeam.SCHEDULERNAME }" placeholder="" id="schedulername" name="schedulername"  required >
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
		<div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>所属部门：</label>
	      <div class="formControls col-5">
	        <select style="width: 190px;height: 30px;"  name="parentorg" id="parentorg">
	            	<option value="">-----请选择-----</option>
	            	<c:forEach var="org" items="${orgobj}">
	            		<option value="${org.orgid}" <c:if test="${org.orgid==dataTeam.orgid}">selected</c:if>><c:out value="${org.orgname}"/></option>
	            	</c:forEach>
	            </select>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
		 <div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>值班负责人：</label>
	      <div class="formControls col-5">
	        <ul style="padding:0;" id="principal_ul" >
				<c:forEach var="sulo" items="${sulo}" varStatus="status">
					<li style="width:95px;padding:0 0 0 5px;float:left">
					 <c:set var="bb" value="${aa}${sulo.USERNAME}${aa}"></c:set>
						<input type="checkbox" id="principal" name="principal" value="<c:out value="${sulo.USERNAME}"/> "
						<c:if test="${fn:indexOf(dataTeam.PRINCIPAL,sulo.USERNAME)>-1}"> checked </c:if>/>
						<c:out value="${sulo.DISPLAYNAME}" /></li>
				</c:forEach>
				</ul>
	      </div>
	      <div class="col-4"> </div>
	    </div>
		 
		<div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>值班人：</label>
	      <div class="formControls col-5">
	        <ul style="padding:0;" id="watch_ul" >
		 			<c:forEach var="sulo" items="${sulo}" varStatus="status">
		 			   <li style="width:95px;padding:0 0 0 5px;float:left">
						<input type="checkbox" id="watch" name="watch" value="<c:out value="${sulo.USERNAME}"/>"
						<c:if test="${fn:indexOf(dataTeam.WATCH,sulo.USERNAME)>-1}"> checked </c:if>/>
						<c:out value="${sulo.DISPLAYNAME}" /></li>
					</c:forEach>
					</ul>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="button" onclick="editDataTeam();" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" onClick="refresh();" value="&nbsp;&nbsp;返回&nbsp;&nbsp;">
	      </div>
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


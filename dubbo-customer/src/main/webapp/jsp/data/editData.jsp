<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <title>交接班管理</title>
       
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
        <script src="<%= request.getContextPath() %>/js/select2.min.js"></script>
        <%@ include file="/jsp/header.jsp"%>
       <script type="text/javascript">
		   $(function(){
			   $("#watchpassword").value="";
			   doRefresh();
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
		   
		   function editData(){
				add();
			   //layer.msg('请联系开发人员', {icon: 2});
		   }
		   
		   function add(){
				var params= $('form').serialize();   
            	var url="<%=path %>/dataIndex/handleIndex.do";
            	$.post(url,params,function(data){
            		  
            		if(data.flag){
            			layer.msg(data.messager, {icon: 1},function(){
            				
            				window.parent.parent.parent.location.href="<%=path%>/user/logout.do";
            				//refresh()
            			});
            			  
            		 }else{
            			layer.msg(data.messager, {icon: 2});
            		 }
            		 
                },"json") 
				    
		   }	
		   
		   function doRefresh(){
				setTimeout('doRefresh()',1000);
				var date = new Date();    
		        var y = date.getFullYear();    
		        var m = date.getMonth()+1;    
		        var d = date.getDate();    
		        var h = date.getHours();    
		        var i = date.getMinutes();    
		        var s = date.getSeconds();    
		        $("#sysTime").html(y+"-"+(m>9?m:("0"+m))+"-"+(d>9?d:("0"+d))+" "+(h>9?h:("0"+h))+":"+(i>9?i:("0"+i))+":"+(s>9?s:("0"+s))); 
		        $("#sysTime1").html(y+"-"+(m>9?m:("0"+m))+"-"+(d>9?d:("0"+d))); 
      		}
		   
		   function validPassword(){
				var uname = $("#uname").val();
				var password = $("#watchpassword").val();
				var fzr = $("#watchuserid").find("option:selected").val();
				
				var params= $('form').serialize();
				var url = "<%=path%>/dataIndex/doValidPassword.do?uname=" + uname +"&password=" + password +"&fzr=" + fzr;
				
				$.post(url,params,function(data){
             		  
               		if(data.flag){
               			$("#watchpassword").value = "";
               			layer.msg(data.messager, {icon: 2});
               			  
               		 }else{
               			layer.msg(data.messager, {icon: 2});
               		 }
               		 
                   },"json") 
                   
			}
		   
		   function changeBZ(){
				var bzJq = $("#bzid option:selected");
				/* //未选择班组信息
				if(bzJq.val()==""){
					$("#td_fzr").html("");
					$("#td_jbr").html("");
					return;
				} */
				//根据班组Id查询负责人和值班人
				var teamid=bzJq.val();
				//查询负责人，生成下拉列表
				var params= $('form').serialize();   
               	var fzrurl="<%=path %>/dataIndex/getFZRListByTeamid.do?teamid="+teamid;
               	$.post(fzrurl,params,function(data){
               		
               		if(data.flag==null){
               			$("#td_fzr").html(" ");
               			var html = "";
						for(var i=0;i<data.length;i++){
							html += "<option value=\""+data[i].USERNAME+"\" >"+data[i].DISPLAYNAME+"</option>";
						}
						$("#td_fzr").append(html);
						// 缺一不可  
			            $("#td_fzr").selectpicker('refresh');
			            $("#td_fzr").selectpicker('render');
               			  
               		 }else{
               			layer.msg(data.messager, {icon: 2});
               		 }
               		 
                   },"json")
                   
				//查询值班人，生成多选框
				var jbrurl="<%=path %>/dataIndex/getZBRListByTeamid.do?teamid="+teamid;
               	$.post(jbrurl,params,function(data){
               		
               		if(data.flag==null){
               			$("#td_jbr").html(" ");
               			var html="";
			    		for(var i=0;i<data.length;i++){
							html += "<option value=\""+data[i].USERNAME+"\" >"+data[i].DISPLAYNAME+"</option>";
						}
						$("#td_jbr").append(html);
						// 缺一不可  
			            $("#td_jbr").selectpicker('refresh');
			            $("#td_jbr").selectpicker('render');
               			  
               		 }else{
               			layer.msg(data.messager, {icon: 2});
               		 }
               		 
                   },"json")
				
			}
		   
		</script>
        
       
</head>
  
<body >
<div class="page-content">
	<div class="pd-10">
	  <form action="" method="post" class="form form-horizontal" id="form-member-edit">
	  <input type="hidden"  style="width:250px"  id="schedulerid" name="schedulerid" value="${schedulerid }">
	  <input type="hidden" value="1" id="deleted" name="deleted" > 
	  <input type="hidden" id="uname" name="uname"  value="${logname }" >
	    
	    <div class="row">
	      <label class="form-label col-2"><span class="c-red">*</span>工作日期：</label>
	      <div class="formControls col-4">
	      		<h4>${startdate}一<span id="sysTime1"></span></h4>
	      </div>
	      <label class="form-label col-2"><span class="c-red">*</span>接班时间：</label>
	      <div class="formControls col-4">
	        	<h4><span  id="sysTime"></span></h4>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
		<div class="row">
	      <label class="form-label col-2"><span class="c-red">*</span>交班负责人：</label>
	      <div class="formControls col-4">
	        <select class="selectpicker" data-live-search="true" style="width: 190px;height: 30px;"  name="watchprincipal" id="watchuserid">
	            	<option value="">-----请选择-----</option>
	            	<c:forEach var="tempList" items="${PrincipalList}">
	            		<option value="${tempList.USERNAME}" <c:if test="${tempList.USERNAME==paramZBFZR}">selected</c:if>>
	            			<c:out value="${tempList.DISPLAYNAME}"/>
	            		</option>
	            	</c:forEach>
	            </select>
	      </div>
	      <label class="form-label col-2"><span class="c-red">*</span>接班班组：</label>
	      <div class="formControls col-4">
	        <select class="selectpicker" data-live-search="true" style="width: 190px;height: 30px;"  name="teamid" id="bzid"  onchange="javascript:changeBZ();">
	            	<option value="">-----请选择-----</option>
	            	<c:forEach var="bz" items="${bzList}">
						<option value="${bz.TEAMID}" <c:if test="${bz.TEAMID==paramJBTeamId}">selected</c:if>>
							<c:out value="${bz.SCHEDULERNAME}" />
						</option>
					</c:forEach>
	            </select>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row">
	      <label class="form-label col-2"><span class="c-red">*</span>负责人密码：</label>
	      <div class="formControls col-4">
	        <input type="password" class="input-text" placeholder="" id="watchpassword" name="paramZBFZR_PWD"  onblur="validPassword();"  required >
	      </div>
	      <label class="form-label col-2"><span class="c-red">*</span>接班负责人：</label>
	      <div class="formControls col-4">
	         <select class="selectpicker" data-live-search="true" style="width: 190px;height: 30px;" id="td_fzr"  name="successorprincipal" >
	            	<option id="wjhnb" value="">-----请选择-----</option>
	         </select>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row">
	      <label class="form-label col-2"><span class="c-red">*</span>交班人：</label>
	      <div class="formControls col-4">
	            <select class="selectpicker" data-live-search="true" multiple style="width: 190px;height: 30px;"  name="watchcode" >
	            	<option value="">-----请选择-----</option>
	            	<c:forEach var="tempList" items="${PrincipalSubList}">
	            		<c:set var="bb" value=",${tempList.USERNAME},"></c:set>
						<c:set var="cc" value=",${dsiMap.WATCHCODE},"></c:set>
						<option value="${tempList.USERNAME }" <c:if test="${fn:indexOf(cc,bb)!=-1}">selected</c:if>>
							<c:out value="${tempList.DISPLAYNAME }" />
						</option>
					</c:forEach>
	            </select>
	      </div>
	      <label class="form-label col-2"><span class="c-red">*</span>接班人：</label>
	      <div class="formControls col-4">
	        	<select class="selectpicker" data-live-search="true" multiple style="width: 190px;height: 30px;"  name="successorcode" id="td_jbr">
	            	<option value="">-----请选择-----</option>
	            </select>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
		<div class="row cl">
	      <label class="form-label col-2"><span class="c-red">*</span>值班备注：</label>
	      <div class="formControls col-9">
	        <textarea id="paramNOTE" name="note"  cols="" rows="" class="textarea"  placeholder="说点什么...最少输入10个字符"  onKeyUp="textarealength(this,100)">${paramNOTE }</textarea>
	        <p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
	      </div>
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <div class="col-4"> </div>
	    </div>
	    
	    <div class="row cl">
	      <div class="col-7 col-offset-4">
	        <input class="btn btn-primary radius" type="button" onclick="editData();" value="&nbsp;&nbsp;确认交接&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" onClick="refresh();" value="&nbsp;&nbsp;取消交接&nbsp;&nbsp;">
	      </div>
	    </div>
	  </form>
	</div>
	</div>

</body>
</html>


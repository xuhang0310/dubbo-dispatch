<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>生产调度系统</title>

<script src="<%=request.getContextPath()%>/js/jquery-1.10.2.min.js"></script>
<%@ include file="/jsp/header.jsp"%>
<script type="text/javascript">
		   $(function(){
			   $("#form-member-edit").validate({
				    
				    errorClass: "label.error", 
				    onkeyup: false,   
	                submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form  
	                	 
	                      var key=$("#nodeid").val();
	                	  var url="<%=path%>/node/saveNode.do";
	                	  if(key!=""){
	                		  url="<%=path%>/node/updateNodeById.do";
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
		   
		   function editNode(){
			   var id = $("#nodeid").val();
			   if (id!=null&&id!="") {
				   update()
				}else{
				   add()
				}
			   //layer.msg('请联系开发人员', {icon: 2});
		   }
		   
		   function add(){
				var params= $('form').serialize();   
            	var url="<%=path%>/node/saveNode.do";
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
               	var url="<%=path%>/node/editNode.do";
		$.post(url, params, function(data) {

			if (data.flag) {
				layer.msg(data.messager, {
					icon : 1
				}, function() {
					refresh()
				});

			} else {
				layer.msg(data.messager, {
					icon : 2
				});
			}

		}, "json")

	}
</script>


</head>

<body>
	<div class="page-content">
		<div class="pd-10">
			<form action="" method="post" class="form form-horizontal"
				id="form-member-edit">
				<input type="hidden" value="${node.nodeid }" id="nodeid"
					name="nodeid">

				<div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>换热站名称：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${node.nodename }"
							placeholder="" id="nodename" name="nodename" required>
					</div>
					<div class="col-4"></div>
				</div>

				<div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>所属部门：</label>
					<div class="formControls col-5">
						<select style="width: 190px;height: 30px;" name="orgid" id="orgid">
							<option value="">-----请选择-----</option>
							<c:forEach var="org" items="${orgobj}">
								<option value="${org.orgid}"
									<c:if test="${org.orgid==node.orgid}">selected</c:if>>
									<c:out value="${org.orgname}" />
								</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4"></div>
				</div>

				<div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>所属热源：</label>
					<div class="formControls col-5">
						<select style="width: 190px;height: 30px;" name="feedid" id="feedid">
							<option value="">-----请选择-----</option>
							<c:forEach var="org" items="${feedobj}">
								<option value="${org.feedid}"
									<c:if test="${org.feedid==node.feedid}">selected</c:if>>
									<c:out value="${org.feedname}" />
								</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4"></div>
				</div>

				<div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>工艺图ID：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${node.gytid }"
							placeholder="" id="gytid" name="gytid">
					</div>
					<div class="col-4"></div>
				</div>


				<div class="row cl">
					<div class="col-9 col-offset-3">
						<input class="btn btn-primary radius" type="button" onclick="editNode();" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
						<input class="btn btn-primary radius" type="button" onClick="refresh();" value="&nbsp;&nbsp;返回&nbsp;&nbsp;">
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>

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
	                	 
	                      var key=$("#id").val();
	                	  var url="<%=path%>/scadaStation/saveScadaStation.do";
	                	  if(key!=""){
	                		  url="<%=path%>/scadaStation/updateScadaStationById.do";
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
		   
		   function editScadaStation(){
			   var id = $("#id").val();
			   if (id!=null&&id!="") {
				   update()
				}else{
				   add()
				}
			   //layer.msg('请联系开发人员', {icon: 2});
		   }
		   
		   function add(){
				var params= $('form').serialize();   
            	var url="<%=path%>/scadaStation/saveScadaStation.do";
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
               	var url="<%=path%>/scadaStation/editScadaStation.do";
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
		   
		// 切换控制项目
		function changexm(val){
			if(val==null || val=="") return;
			if(val=="1"){
				$("#customxm").html("");
				$("#customxm").html("<select style='width: 190px;height: 30px;' name='stationcode' id='stationcode'> <c:forEach var='org' items='${nodeobj}'><option value='${org.nodeid}'<c:if test='${org.feedid==station.stationcode}'>selected</c:if>><c:out value='${org.nodename}' /></option></c:forEach></select>");
			}else if(val=="99"){
				$("#customxm").html("");
				$("#customxm").html("<select style='width: 190px;height: 30px;' name='stationcode' id='stationcode'> <c:forEach var='org' items='${feedobj}'><option value='${org.feedid}'<c:if test='${org.feedid==statopm.stationcode}'>selected</c:if>><c:out value='${org.feedname}' /></option></c:forEach></select>");
			}
			//debugger;
			
		}
</script>


</head>

<body>
	<div class="page-content">
		<div class="pd-10">
			<form action="" method="post" class="form form-horizontal"
				id="form-member-edit">
				<input type="hidden" value="${station.id }" id="id" name="id">

				<div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>所属类型：</label>
					<div class="formControls col-5">
						<select style="width: 190px;height: 30px;"  onchange="changexm(this.value);"  name="stationtype" id="stationtype">
							<option value="">-----请选择-----</option>
							<option value="1" <c:if test='${station.stationtype=="1"}'>selected</c:if>>换热站</option>
							<option value="99" <c:if test='${station.stationtype=="99"}'>selected</c:if>>热源</option>
						</select>
					</div>
					<div class="col-4"></div>
				</div>

				<div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>站名称：</label>
					<div class="formControls col-5" id="customxm" >
						<input type="hidden" class="input-text" value="${station.stationcode}" placeholder="请选择类型" id="stationcode" name="stationcode" >
						<input type="text" class="input-text" value="${station.name}" placeholder="请选择类型" id="stationcodename" name="stationcodename" >
						<%-- <select style="width: 190px;height: 30px;" name="stationcode" id="stationcode">
							<option value="">-----请选择-----</option>
							<c:forEach var="org" items="${feednodeobj}">
								<option value="${org.id}"
									<c:if test="${org.id==station.stationcode}">selected</c:if>>
									<c:out value="${org.name}" />
								</option>
							</c:forEach>
						</select> --%>
					</div>
					<div class="col-4"></div>
				</div>


				<div class="row cl">
					<div class="col-9 col-offset-3">
						<input class="btn btn-primary radius" type="button" onclick="editScadaStation();" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
						<input class="btn btn-primary radius" type="button" onClick="refresh();" value="&nbsp;&nbsp;返回&nbsp;&nbsp;">
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>


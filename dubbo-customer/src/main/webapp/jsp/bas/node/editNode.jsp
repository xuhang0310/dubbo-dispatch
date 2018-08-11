<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>生产调度系统</title>

<script src="<%=request.getContextPath()%>/js/jquery-1.10.2.min.js"></script>
<%@ include file="/jsp/header-wizard.jsp"%>

<script type="text/javascript">
		   $(function(){
			   $("#form-member-edit").validate({
				    
				    errorClass: "label.error", 
				    onkeyup: false,   
	                submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form  
	                	 
	                      var key=$("#nodeid").val();
	                	  var url="<%=path%>/node/saveNode.do";
	                	  if(key!=""){
	                		  url="<%=path%>/node/updateNodeById.do?nodeid="+key;
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
				 var key=$("#nodeid").val();
              	  var url="<%=path%>/node/saveNode.do";
              	  if(key!=""){
              		  url="<%=path%>/node/editNode.do?nodeid="+key;
              	  }
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
				layer.msg(data.messager, {icon : 1}, function() {refresh()});
			} else {
				layer.msg(data.messager, {icon : 2});
			}

		}, "json")

	}
</script>


</head>

<body style="overflow:hidden;">

<div class="portlet-body form" id="form2">
<form action="" method="post" class="form form-horizontal" id="form">
	<input type="hidden" id="nodeid" value="${node.NODEID }">
		<div class="form-wizard">
				<div class="form-horizontal">
							<ul class="nav  nav-justified  steps" id="cite_ul_id">
									<li>
									<a href="#subjec1" data-toggle="tab" ><span class="badge badge-info">1</span>
									<span class="desc"><i class="fa fa-check"></i>基础信息</span>
									</a>
									</li>
									<li>
									<a href="#subjec2" data-toggle="tab"><span class="badge badge-info">2 </span>
									<span class="desc"><i class="fa fa-check"></i>机组信息</span></a>
									</li>
									<li>
										<a href="#subjec3" data-toggle="tab" ><span class="badge badge-info">3</span>
										<span class="desc"><i class="fa fa-check"></i>指标信息</span></a>
									</li>
							</ul>
							<!-- 进度条 -->
							<div id="bar" class="progress progress-striped" style="height:12px;" role="progressbar">
									<div class="progress-bar progress-bar-success">
									</div>
							</div>
							<div class="tab-content">
								<div class="tab-pane active" id="subjec1">
								         <div class="form-group">
											<label for="firstname" class="col-sm-2 control-label">换热站名称</label>
											<div class="col-sm-3">
												<input type="text" class="form-control" id="nodename" value="${node.NODENAME }" name="nodename" required
													   placeholder="请输入换热站名称">
											</div>
											
											<label for="lastname" class="col-sm-2 control-label">所属公司</label>
											<div class="col-sm-3">
												<select class="selectpicker" data-live-search="true"  name="org" id="org" title="请选择所属公司" >
										             <c:forEach var="temp" items="${orgobj }">
										             		<option value="${temp.ORGID }"<c:if test="${temp.ORGID==node.ORGID}">selected</c:if>>${temp.ORGNAME }</option>
										             </c:forEach>
										         </select>
											</div>
										</div>
										<div class="form-group">
											<label for="lastname" class="col-sm-2 control-label">所属热源</label>
											<div class="col-sm-3">
												<select class="selectpicker" data-live-search="true"  name="feedid" id="feedid" title="请选择热源" >
										             <c:forEach var="fd" items="${feedList }">
										             		<option value="${fd.FEEDID }"<c:if test="${fd.FEEDID==node.FEEDID}">selected</c:if> >${fd.FEEDNAME }</option>
										             </c:forEach>
										         </select>
											</div>
											
											<label for="lastname" class="col-sm-2 control-label">管理方式</label>
											<div class="col-sm-3">
												<select class="selectpicker" data-live-search="true"  name="glfs" id="glfs" title="请选择一个管理方式" >
										                <c:forEach var="temp" items="${dicList }">
										                    <c:if test="${temp.PID=='6' }">
										                    		<option value="${temp.ID }" <c:if test="${temp.ID==node.GLFS}">selected</c:if>>${temp.NAME }</option>
										                    </c:if>
										             		
										             	</c:forEach>
										         </select>
											</div>
										</div>
										<div class="form-group">
											<label for="firstname" class="col-sm-2 control-label">采暖方式</label>
											<div class="col-sm-3">
												<select class="selectpicker" data-live-search="true"  name="cnfs" id="cnfs" title="请选择一个采暖方式" >
										             <c:forEach var="temp" items="${dicList }">
										                    <c:if test="${temp.PID=='1' }">
										                    		<option value="${temp.ID }"<c:if test="${temp.ID==node.CNFS}">selected</c:if>>${temp.NAME }</option>
										                    </c:if>
										             		
										             	</c:forEach>
										         </select>
											</div>
											
											<label for="lastname" class="col-sm-2 control-label">节能方式</label>
											<div class="col-sm-3">
												<select class="selectpicker" data-live-search="true"  name="jnfs" id="jnfs" title="请选择一个节能方式" >
										           		<c:forEach var="temp" items="${dicList }">
										                    <c:if test="${temp.PID=='36' }">
										                    		<option value="${temp.ID }"<c:if test="${temp.ID==node.JNFS}">selected</c:if>>${temp.NAME }</option>
										                    </c:if>
										             		
										             	</c:forEach>
										         </select>
											</div>
										</div>
										<div class="form-group">
											<label for="firstname" class="col-sm-2 control-label">设计负荷</label>
											<div class="col-sm-3">
												<input type="text" class="form-control" id="sjfh"  name="sjfh" value="${node.SJFH }"
													   placeholder="请输入设计负荷">
											</div>
											
											<label for="lastname" class="col-sm-2 control-label">采暖面积</label>
											<div class="col-sm-3">
												<input type="text" class="form-control" id="cnmj"  name="cnmj" value="${node.CNMJ }"
													   placeholder="请输入采暖面积">
											</div>
										</div>
										<div class="form-group">
											<label for="firstname" class="col-sm-2 control-label">工艺图</label>
											<div class="col-sm-3">
												 <select class="selectpicker" data-live-search="true"  name="gytid" id="gytid" title="请选择一个工艺图" >
										               <c:forEach var="temp" items="${picList }">
										                    
										                    		<option value="${temp.ID }"<c:if test="${temp.ID==node.GYTID}">selected</c:if>>${temp.PIC_NAME }</option>
										                   
										             		
										             	</c:forEach>
										         </select>
											</div>
											<label for="firstname" class="col-sm-2 control-label">负责人</label>
											<div class="col-sm-3">
												<input type="text" class="form-control" id="fzr"  name="fzr" value="${node.FZR }"
													   placeholder="请输入负责人名字">
											</div>
											
										</div>
								</div>
								<div class="tab-pane" id="subjec2">
										<h3>请务必按照顺序填写</h3>
							        	  <div class="form-group">
												<label for="firstname" class="col-sm-2 control-label">机组1名称</label>
												<div class="col-sm-3">
													<input type="text" class="form-control" id="jz1"  name="jz1" placeholder="请输入机组1名称" value="${jz1.LINENAME }">
												</div>
												
												<label for="firstname" class="col-sm-2 control-label">机组2名称</label>
												<div class="col-sm-3">
													<input type="text" class="form-control" id="jz2"  name="jz2" value="${jz2.LINENAME }"
														   placeholder="请输入机组2名称">
												</div>
												
											</div>
											<div class="form-group">
												<label for="firstname" class="col-sm-2 control-label">机组3名称</label>
												<div class="col-sm-3">
													<input type="text" class="form-control" id="jz3" name="jz3" value="${jz3.LINENAME }"
														   placeholder="请输入机组3名称">
												</div>
												
												<label for="firstname" class="col-sm-2 control-label">机组4名称</label>
												<div class="col-sm-3">
													<input type="text" class="form-control" id="jz4" name="jz4" value="${jz4.LINENAME }"
														   placeholder="请输入机组4名称">
												</div>
												
											</div>
								</div>
								<div class="tab-pane" id="subjec3">
											<div class="form-group">
												<label for="firstname" class="col-sm-2 control-label">热指标</label>
												<div class="col-sm-3">
													<input type="text" class="form-control" id="rzb" name="rzb"  value="${rzb.INDEXVALUE }"
														   placeholder="请输入热指标（w/㎡）">
												</div>
												
												<label for="lastname" class="col-sm-2 control-label">水指标</label>
												<div class="col-sm-3">
													<input type="text" class="form-control" id="szb" name="szb"  value="${szb.INDEXVALUE }"
														   placeholder="请输入水指标（kg/㎡）">
												</div>
											</div>
											<div class="form-group">
												<label for="firstname" class="col-sm-2 control-label">电指标</label>
												<div class="col-sm-3">
													<input type="text" class="form-control" id="dzb" name="dzb"  value="${dzb.INDEXVALUE }"
														   placeholder="请输入电指标（w/㎡）">
												</div>
												
												
											</div>
								</div>
							</div>
				</div>
		</div>	

		<div class="form-actions">
			<div class="row">
				<div class="col-sm-9" style="text-align:center;">
					<a href="javascript:void(0)" class="btn btn-primary radius button-previous" style="display:none"><i class="icon-arrow-left"></i>上一步</a>
					<a href="javascript:void(0)" id="button_next" class="btn btn-primary radius button-next">下一步<i class="icon-arrow-right"></i></a>
					<a href="javascript:add()" id="save" class="btn btn-primary radius button-submit" style="display:none">保存</a>
					<!-- <input class="btn btn-primary radius button-submit"  id="save" type="submit"   style="display:none" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"> -->
					<a href="javascript:cancle()" class="btn btn-primary radius ajaxify" id="saveIndex_return"> <i class="fa fa-reply">关闭</i></a>
				</div>
			</div>
		</div>
		</form>
</div>



<script src="<%=path %>/plugins/wizard/jquery.bootstrap.wizard.js"></script>
<script src="<%=path %>/plugins/wizard/prettify.js"></script>
<link   href="<%=path%>/plugins/wizard/wizard.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

function saveInfo(){
alert(2);
	$("#form").submit();
}

$(function(){
		$('#form').bootstrapWizard({
				'nextSelector' : '.button-next',
				'previousSelector' : '.button-previous',
				onTabClick: function (tab, navigation, index, clickedIndex) {
				               return false;//使a标签失去点击事件
				               },            
				onNext : function(tab,navigation,index){
						    assessHandleTitle(tab,navigation,index);
				},
				onPrevious: function (tab, navigation, index) {
				            assessHandleTitle(tab, navigation, index);
				},
			   onTabShow: function (tab, navigation, index) {
			            var total = navigation.find('li').length;
			            var current = index + 1;
			            var percent = (current / total) * 100;
			            $('.progress-bar').css({
			                width: percent + '%'
			            });
			    }
		});
		function assessHandleTitle(tab,navigation,index){
			var total = navigation.find('li').length;//得到li标签的长度
			var current = index + 1;
				$('li', $('#form')).removeClass("done");
				var li_list = navigation.find('li');
				for (var i = 0; i < index; i++) {
				            $(li_list[i]).addClass("done");
				}
				if(current == 1){
					$('.button-previous').hide();//隐藏上一步按钮
				}else{
					$('.button-previous').show();//显示上一步按钮
				}
				if(current >= total){
					$('.button-next').hide();//隐藏下一步按钮
					$('.button-submit').show();//显示提交按钮
				}else{
					$('.button-next').show();//显示上一步按钮
					$('.button-submit').hide();//隐藏保存按钮
				
		}
	}
});
</script>
</body>
</html>

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
<link rel="stylesheet" href="<%=path%>/plugins/zTree_v3/css/metroStyle/metroStyle.css">
<script type="text/javascript" src="<%=path%>/plugins/layout/jquery.layout.js"></script>
<script type="text/javascript">
		   
		   function cancle(){
			   var index = parent.layer.getFrameIndex(window.name);
			   parent.layer.close(index);
		   }
		   function refresh(){
			   parent.search();
			   var index = parent.layer.getFrameIndex(window.name);
			   parent.layer.close(index);
		   }
		   
		   function editMenu(){
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
            	var url="<%=path%>/menu/saveMenu.do";
            	$.post(url,params,function(data){
            		  
            		if(data.flag){
            			layer.msg(data.messager, {icon: 1},function(){
            				refresh()
            			});
            			  
            		 }else{
            			layer.msg(data.messager, {icon: 2,shade: 0.4,time:false });
            		 }
            		 
                },"json") 
				    
		   }	
		   
		   function update(){
				var params= $('form').serialize();   
               	var url="<%=path%>/menu/editMenu.do";
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
		   
		   function setFontCss(treeId, treeNode) {
			   return treeNode.id==1 ? { color: "red"} : {};
			}
		   
		   var setting = {
	        		data: { 
	                    simpleData: { 
	                        enable: true,//如果设置为 true，请务必设置 setting.data.simpleData 内的其他参数: idKey / pIdKey / rootPId，并且让数据满足父子关系。 
	                        idKey: "id", 
	                        pIdKey: "pid", 
	                        rootPId: 0 
	                    },
	                    key: {
		                    name: "title",
		                    url:""
	                    }
					}, 
	                view: { 
	                    showLine: true,//显示连接线 
	                    showIcon: true//显示节点图片 
	                    
	                }, 
	                async: {    //ztree异步请求数据 
	                    enable: true, 
	                    url: "<%=path%>/menu/getSysMenuTreeData.do?tretype=2",//请求action方法 
	                    autoparam:["id"] 
	                },
	                callback:{
	                     beforeClick: zTreeBeforeClick,
	                     onAsyncSuccess: zTreeOnAsyncSuccess

	                }
	        }
	        
	      //启动树节点     
	        $(function($){ 
	           
	            $.fn.zTree.init($("#partTree"), setting); 
	        });
	         
	        function zTreeOnAsyncSuccess(){
	           // alert('加载树成功');
	            var treeObj = $.fn.zTree.getZTreeObj("partTree");
	            treeObj.expandAll(true);

	        }
	        
	        function zTreeBeforeClick(treeId, treeNode, clickFlag) {
	        	//alert(treeNode.id+"==="+treeNode.pid+"==="+treeNode.title);
	        	document.getElementById('parentid').value=treeNode.id;
	        }
	        
	       
</script>


</head>

<body>
	<div class="page-content">
		<div class="pd-10">
			<form action="" method="post" class="form form-horizontal"
				id="form-member-edit">
				<input type="hidden" value="${menu.menuid }" id="id" name="id">
				
				<div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>MENUID：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${menu.menuid }"
							placeholder="" id="menuid" name="menuid" required>
					</div>
					<div class="col-4"></div>
				</div>
				
				<div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>菜单名称：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${menu.menuname }"
							placeholder="" id="menuname" name="menuname" required>
					</div>
					<div class="col-4"></div>
				</div>

				<%-- <div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>父级菜单：</label>
					<div class="formControls col-5">
						<select style="width: 190px;height: 30px;" name="parentid" id="parentid">
							<option value="0">-----请选择-----</option>
							<c:forEach var="org" items="${orgobj}">
								<option value="${org.menuid}"
									<c:if test="${org.menuid==menu.parentid}">selected</c:if>>
									<c:out value="${org.menuname}" />
								</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4"></div>
				</div> --%>
				
				<!-- <div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>父级菜单：</label>
					<input type="hidden"  name="parentid" id="parentid">
					<div class="formControls col-5" >
						<ul id="partTree" class="ztree" style="border: 1px solid #617775;overflow-y: scroll;height: 100px;"></ul>
					</div>
					<div class="col-4"></div>
				</div> -->
				<div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>父级菜单：</label>
					<select class="selectpicker" data-live-search="true"   name="parentid" id="parentid" title="请选择菜单"  style="width:120px;">
				           <c:forEach var="temp" items="${parentList }">
				             	<option value="${temp.ID }" <c:if test="${temp.ID==menu.parentid }" >selected</c:if>>${temp.NAME }</option>
				           </c:forEach>
			         </select>
					<div class="col-4"></div>
				</div>
				
				<div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>URL：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${menu.menuurl }"
							placeholder="" id="menuurl" name="menuurl" required>
					</div>
					<div class="col-4"></div>
				</div>

				<div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>图标名称：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${menu.iconclass }"
							placeholder="" id="iconclass" name="iconclass" required>
					</div>
					<div class="col-4"></div>
				</div>
				
				<div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>左侧树URL：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${menu.treeurl }"
							placeholder="" id="treeurl" name="treeurl" required>
					</div>
					<div class="col-4"></div>
				</div>

				<div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>菜单级别：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${menu.menulevel }"
							placeholder="" id="menulevel" name="menulevel">
					</div>
					<div class="col-4"></div>
				</div>
				
				<div class="row cl">
					<label class="form-label col-3"><span class="c-red">*</span>排序：</label>
					<div class="formControls col-5">
						<input type="text" class="input-text" value="${menu.orderid }"
							placeholder="" id="orderid" name="orderid" required>
					</div>
					<div class="col-4"></div>
				</div>


				<div class="row cl">
					<div class="col-9 col-offset-3">
						<input class="btn btn-primary radius" type="button" onclick="editMenu();" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
						<input class="btn btn-primary radius" type="button" onClick="refresh();" value="&nbsp;&nbsp;返回&nbsp;&nbsp;">
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>


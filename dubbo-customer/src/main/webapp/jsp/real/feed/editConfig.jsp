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
          <link href="<%= request.getContextPath() %>/css/ligerui/ligerui-all.css" rel="stylesheet" type="text/css" />
        
     <style>
     
			form.validform {
				margin: 10px auto;
				width: 1000px;
				overflow: hidden;
			}
		   .dialogfrom input {
				width: 120px;
			}

			.dialogfrom select {
			    width: 120px;
			}
		    .validform label {
				display: inline-block;
				width: 90px;
				padding-left: 2px;
				position: relative;
				text-align: center;
			}	
     
     </style>
       <script type="text/javascript">
   
		   $(function(){
			   $("#form-member-edit").validate({
				    
				    errorClass: "label.error", 
				    onkeyup: false,   
	                submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form  
	                  	  var code=$("#code").val();
	                  	  
						    var url="<%=path %>/feedReal/savePictureConfig.do?code="+code;
						
	            	     
	                
	              
		                	var params= $('form').serialize();
	                	  $.post(url,params,function(data){
	                		  
	                		  if(data.flag){
	                			  layer.msg(data.messager, {icon: 1},function(){
	                	//		  alert("chenggong");
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
				var stationtype = document.getElementById("stationtype").value;
				var url="<%=path %>/basmeter/getProjectList.do?stationtype=" + stationtype;
				$.ajax({
			    	type: "POST",
			    	url: url,
			    	success: function(data){
			    		 //alert(data);
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
			  // parent.search();
				  var index = parent.layer.getFrameIndex(window.name);
				  parent.layer.close(index);
		   }
		   	function doBack(){
		   	alert("guanbi");
			window.parent.closewin();
			}
			function save(){
			$("#form-member-edit").submit();
			
			}
		</script>
        
       
</head>
  
<body>
<div class="row analyse">

	 <!--  <form action="" method="post" class="validform dialogfrom" name="validform" id="validform"> -->
	<form action="" method="post" class="validform dialogfrom" name="form-member-edit" id="form-member-edit">
	<input type="hidden" id="code" name="code" value="${code }">
	<table class='datagrid-easyui' border='0'  cellspacing='0' cellpadding='0' style='border-top:1px solid #95b8e7;line-height:25px;margin:20px;'>
		 
		 <tr>
		    
		    <td style="height:25px;display:none"><label class="label"><span></span>字段名称</label></td>
		    <td style="width:250px"><label class="label1"><span></span>中文名称</label></td>
		    <td><label class="label1"><span></span>是否显示</label></td>
		    <td><label class="label1" ><span></span>显示名称</label></td>
		    <td style="height:25px;display:none"><label class="label"><span></span>X轴</label></td>
		    <td style="height:25px;display:none"><label class="label"><span></span>Y轴</label></td>
		    <td><label class="label1"><span></span>单位</label></td>
		    <td><label class="label1"><span></span>有效位数</label></td>
		    <td><label class="label1"><span></span>背景颜色</label></td>
		    <td><label class="label1"><span></span>字体大小</label></td>
		    <td><label class="label1"><span></span>名称位置</label></td>
		 </tr>
		 
		 <c:forEach var="temp" items="${configList}">

				<tr>
					<td style="height:25px;display:none"><input name="column"
						size="12" value="${temp.NAME }" width="200px"/></td>
					<td style="height:25px;">
						<input name="text" value="${temp.TEXT }" style="width:200px"/>
					</td>
					<td style="height:25px;">
						<select name="ishiden" style="width:60px;">
						    <option value="0"  <c:if test="${temp.ISHIDEN=='0'}">selected</c:if>>显示</option>
						    <option value="1" <c:if test="${temp.ISHIDEN=='1'}">selected</c:if>>隐藏</option>
						</select>
					</td>
					<td>
					   <input name="dplay" style="width:80px;" value="${temp.DPLAY }" />
					</td>
					<td style="height:25px;display:none;"><input name="x"
						style="width:30px;" value="${temp.X_NUM }" /></td>
					<td style="height:25px;display:none;"><input name="y"
						style="width:30px;" value="${temp.Y_NUM }" /></td>
					<td style="height:25px;"><input name="u" style="width:80px;"
						value="${temp.UNIT }" /></td>
					<td style="height:25px;"><input name="f" style="width:30px;"
						value="${temp.VALIDNUM }" /></td>
					<td style="height:25px;">
					
						<select name="bg" style="width:50px;">
							<option value="45b0e4"  <c:if test="${temp.BG=='45b0e4'}">selected</c:if>>蓝</option>
						    <option value="e44747"  <c:if test="${temp.BG=='e44747'}">selected</c:if>>红</option>
						    <option value="FF9900"  <c:if test="${temp.BG=='FF9900'}">selected</c:if>>黄</option>
						    <option value="47d73e"  <c:if test="${temp.BG=='47d73e'}">selected</c:if>>绿</option>
						    <option value="FF00FF"  <c:if test="${temp.BG=='FF00FF'}">selected</c:if>>紫</option>
						    <option value="999"     <c:if test="${temp.BG=='999'}">selected</c:if>>灰</option>
						</select>
					</td>
					<td style="height:25px;"><input name="s" style="width:30px;"
						value="${temp.FSIZE }" /></td>
					 <td style="height:25px;">
					     <select name="position" style="width:50px;">
							<option value="上"  <c:if test="${temp.POSITION=='上'}">selected</c:if>>上</option>
						    <option value="中"  <c:if test="${temp.POSITION=='中'}">selected</c:if>>中</option>
						    <option value="下"  <c:if test="${temp.POSITION=='下'}">selected</c:if>>下</option>
						</select>
					 </td>	
				</tr>
			</c:forEach>
		
	</table>

	<div class="b-button">
		 <span><a class="btn btn-primary radius" href="javascript:save()" style="font-size:6px;"><i class="icon-edit"></i>保存</a></span>
	   <!--  <input type="submit"  value="保存" class="btn btn-primary radius" /> -->
	  <!--   <input type="reset" value="重置" class="button reset" />
	     <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"> -->
	   <!--  <input type="button" value="返回" onclick="doBack();" class="button back" /> -->
	  <!--  <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
	      </div> -->
	</div>
	  
	</form>

 </div>
</body>
</html>


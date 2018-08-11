<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>生产调度系统</title>
       
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
       <%@ include file="/jsp/header.jsp"%>
       <!-- ace styles -->
		<link rel="stylesheet" href="<%=path%>/plugins/spinner/bootstrap-spinner.css">
		<link rel="stylesheet" href="<%=path%>/font/font-awesome.min.css">
		 <script src="<%=path%>/plugins/spinner/jquery.spinner.js"></script>
		
        <script type="text/javascript" src="<%=path%>/plugins/layout/jquery.layout.js"></script>
		<script type="text/javascript">
		
		
		function saveOne(){
			if($("#nodeid").val()==""){
			    layer.msg("请选择一个换热站", {icon: 2});
			    return ;
			}
			var urlAdd="<%=path%>/nodeReal/saveWarningConfig.do";
			
			$.ajax({
				url:urlAdd,
				type : 'POST',  
				data : getParams("form-warnconfig-add"),  
				dataType : "json",  
				traditional:true,  
				success : function(data) {  
					if(data.flag){
						layer.msg(data.messager, {icon: 1});
					}else{
						layer.msg(data.messager, {icon: 2});
					}
				}
			})
		}
		
		function saveAll(){
			var vkorg = $("#nodecode option").map(function(){
			     return $(this).val();
			 }).get().join(",");
			$("#nodeid").val(vkorg);
			var urlAdd="<%=path%>/nodeReal/saveWarningConfig.do";
			//alert(getParams("form-warnconfig-add"));
			$.ajax({
				url:urlAdd,
				type : 'POST',  
				data : getParams("form-warnconfig-add"),  
				dataType : "json",  
				traditional:true,  
				success : function(data) {  
					if(data.flag){
						$("#nodeid").val("");
						layer.msg(data.messager, {icon: 1});
					}else{
						layer.msg(data.messager, {icon: 2});
					}
				}
			})
		}
		
		
		function getParams(formid){
			/* debugger; */
			var form=$("#"+formid);
			var params = new Object();  
	        var items = form.find("input[type=hidden]," + "input[type=text]," + "input[type=password]," + "textarea," + "select," + "input[type=radio]:checked," + "input[type=checkbox]:checked");  
	        items.each(function(index) {  
	            if (params[this.name] == undefined) {  
	                params[this.name] = this.value  
	            } else {  
	                params[this.name] += "," + this.value  
	            }  
	        }); 
	        return params;
		}
		
		
		function getNodeCode(){
			$("#nodeid").val($("#nodecode").val());
			search();
		}
		
		function search(){
			if($("#nodeid").val()==""){
			    layer.msg("请选择一个换热站", {icon: 2});
			    return ;
			}
			var urlAdd="<%=path%>/nodeReal/getWarningConfig.do";
			
			$.ajax({
				url:urlAdd,
				type : 'POST',  
				data : {nodecode:$("#nodeid").val()},  
				dataType : "json",  
				traditional:true,  
				success : function(data) { 
					/* debugger; */
					if(data.length>0){
						$.each(data,function(idx,obj){
							$("input[name='"+obj.FIELDNAME+"']").eq(1).val(obj.MAXNUM);
							$("input[name='"+obj.FIELDNAME+"']").eq(2).val(obj.MINNUM);
						})
					}else{
						$("#form-warnconfig-add input[type=text]").val(1);
						
					}
					
				}
			})
		}
		</script>
  </head>
  
  <body style="margin:10px;overflow-x:hidden;">
  
 <div class="ui-layout-center">
   <div class="row">
		<div class="col-sm-12">
			
			 
	         <select class="selectpicker" data-live-search="true"  name="nodecode" id="nodecode" title="请选择换热站" onchange="getNodeCode()" >
	             <c:forEach var="temp" items="${nodeList }">
		             		 <option value="${temp.NODECODE }" >${temp.NODENAME }</option>
		             </c:forEach>
	         </select>
	       
			<a href="javascript:saveOne()" class="btn btn-success radius"  ><i class="fa fa-save"></i> 保存</a>
			<a href="javascript:saveAll()" class="btn btn-success btn-warning"  ><i class="fa fa-save"></i> 应用到所有换热站</a>
			
			
			
			
		</div>
    </div>
     <div class="row" style="margin-top:10px;">
         
		  <form action="" method="post" class="form form-horizontal" id="form-warnconfig-add">	
		      <input type="hidden" name="nodecode" id="nodeid"/>
		      <table class="table table-bordered">
		           <tr>
		               <td>参数类型</td>
		               <td>最大值</td>
		               <td>最小值</td>
		           </tr>
		           <tr>
		               <td>一网供温
		               <input type="hidden" value="supplytemp" name="supplytemp" >
		               </td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text"  name="supplytemp" class="form-control text-center" value="1" data-rule="quantity" data-step="5">
						          <div class="input-group-addon">
						            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text" name="supplytemp" class="form-control text-center" value="1" data-rule="quantity" data-step="5">
						          <div class="input-group-addon">
						            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		           </tr>
		           <tr>
		               <td>一网回温
		                <input type="hidden" value="returntemp" name="returntemp" >
		               </td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text" name="returntemp" class="form-control text-center" value="1" data-rule="quantity">
						          <div class="input-group-addon">
						            <a href="javascript:;"   class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text" name="returntemp" class="form-control text-center" value="1" data-rule="quantity">
						          <div class="input-group-addon">
						            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		           </tr>
		           <tr>
		               <td>一网供压
		               	<input type="hidden" value="supplypress" name="supplypress" ></td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text" name="supplypress" class="form-control text-center" value="0.01" data-step="0.05" data-rule="currency">
						          <div class="input-group-addon">
						            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text" name="supplypress" class="form-control text-center" value="0.01" data-step="0.05" data-rule="currency">
						          <div class="input-group-addon">
						            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		           </tr>
		           <tr>
		               <td>一网回压
		               	<input type="hidden" value="returnpress" name="returnpress" ></td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text" name="returnpress" class="form-control text-center" value="1" data-rule="currency">
						          <div class="input-group-addon">
						            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text" name="returnpress" class="form-control text-center" value="1" data-rule="currency">
						          <div class="input-group-addon">
						            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		           </tr>
		           <tr>
		               <td>二网供温
		               	 <input type="hidden" value="secsupplytemp" name="secsupplytemp" ></td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text" name="secsupplytemp" class="form-control text-center" value="1" data-rule="quantity" data-step="5">
						          <div class="input-group-addon">
						            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text" name="secsupplytemp" class="form-control text-center" value="1" data-rule="quantity" data-step="5">
						          <div class="input-group-addon">
						            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		           </tr>
		           <tr>
		               <td>二网回温 
		               	<input type="hidden" value="secreturntemp" name="secreturntemp" ></td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text" name="secreturntemp" class="form-control text-center" value="1" data-rule="quantity" data-step="5">
						          <div class="input-group-addon">
						            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text" name="secreturntemp"  class="form-control text-center" value="1" data-rule="quantity" data-step="5">
						          <div class="input-group-addon">
						            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		           </tr>
		           <tr>
		               <td>二网供压
		               	<input type="hidden" value="secsupplypress" name="secsupplypress" ></td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text" name="secsupplypress" class="form-control text-center" value="0.01" data-step="0.05" data-rule="currency">
						          <div class="input-group-addon">
						            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text" name="secsupplypress" class="form-control text-center" value="0.01" data-step="0.05" data-rule="currency">
						          <div class="input-group-addon">
						            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		           </tr>
		           <tr>
		               <td>二网回压
		               	<input type="hidden" value="secreturnpress" name="secreturnpress" ></td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text" name="secreturnpress" class="form-control text-center" value="1" data-rule="currency">
						          <div class="input-group-addon">
						            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		               <td>
		               			<div class="input-group spinner" data-trigger="spinner" style="width: 170px;">
						          <input type="text" name="secreturnpress" class="form-control text-center" value="1" data-rule="currency">
						          <div class="input-group-addon">
						            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
						            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
						          </div>
						        </div>
		               </td>
		           </tr>
		         
		          
		      </table>
			
          </form>
	  </div>
	  
</div>	


  </body>
</html>


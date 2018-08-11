<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>一源一日</title>
       
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
       <%@ include file="/jsp/header.jsp"%>
       <!-- ace styles -->
		
		
       
    </head>
    
    <style type="text/css">
	   .table td {
	   	text-align: center;
	   	vertical-align: middle!important;
	   }		
	</style>
	<script type="text/javascript">
		var maxtemp=parseInt("${dataParamMap.MAXTEMP}");
	    var mintemp=parseInt("${dataParamMap.MINTEMP}");
	    var starttemp=parseInt("${dataParamMap.STARTTEMP}");
	    
		function changeParam(){
	 	   var cnmj=$("#cnmj").val();
	 	   var temp=getAvgTemp($("#avgtemp").val());
	 	   var rzb=calRzb(temp,$("#llrzb").val());
	 	   var rfh=calRfh(rzb,cnmj);
	 	   
	 	   $("#dqrzb strong").text(rzb);
	 	   $("#dqload strong").text(rfh);
	 	   $("#dqrl strong").text((rfh*24).toFixed(2));
	 	   var cz=$("#dqrl strong").text()- $("#dqjh_rl strong").text();
	 	   var percenter=$("#dqjh_rl strong").text()/$("#dqrl strong").text();
	 	   $("#dq_cz strong").text(cz.toFixed(2));
	 	   $("#dq_percent strong").text(percenter.toFixed(2)*100);
	    }
		
		function getAvgTemp(temp){
	    	   if(temp>=starttemp){
	    		   temp=starttemp
	    	   }else if(temp<mintemp){
	    		   temp=mintemp;
	    	   }
	    	   return temp;
	       }
	       function calRfh(rzb,cnmj){
	    	   var rfh=(rzb*cnmj*3600/1000000000).toFixed(2);
	    	   
	    	   return rfh;
	       }
	       function calRL(rfh){
	    	   var rl=rfh*24;
	    	   return rl.toFixed(2);
	       }
		   function calRzb(temp,llrzb){
	    	   
	    	   var rzb=((maxtemp-temp)*llrzb/(maxtemp-mintemp) ).toFixed(2);
	    	   return rzb;
	       }
		   
		   function updateSsll(index){
	    	   
		    	  
	    	   var gswd= $("#jh_gswd"+index).val();
	    	   var hswd= $("#jh_hswd"+index).val();
	    	   var ssll= $("#jh_ssll"+index).val();
	    	   var ssrl=(ssll*(gswd-hswd)*4.1868/1000).toFixed(2);
	    	   $("#jh_ssrl"+index).text(ssrl);
	    	   var num=0;
	    	 
	    	   $('span[name="jh_ssrl"]').each(function(){ 
	    		   
	    		   num+=parseFloat($(this).text());    
	             });
	    	   $("#total_jhrl").val(num);
	    	   $("#dqjh_rfh strong").text(num.toFixed(2));
	    	   $("#dqjh_rl strong").text((num*24).toFixed(2))
	    	   $("#dqjh_rzb strong").text((num*1000000000/3600/$("#cnmj").val()).toFixed(2))
	    	   
	    	   
	    	   num=0; 
	    	   $('input[name="jh_ssll"]').each(function(){ 
	    		   //alert($(this).text());
	    		   num+=parseFloat($(this).val());    
	             });
	    	   $("#total_jhll").val(num);
	    	   var cz=$("#dqrl strong").text()- $("#dqjh_rl strong").text();
	    	   var percenter=$("#dqjh_rl strong").text()/$("#dqrl strong").text();
	    	   $("#dq_cz strong").text(cz.toFixed(2));
	    	   $("#dq_percent strong").text(percenter.toFixed(2)*100);
	       }
		   
		   function add(){
	    	   var ssll="";
	    	   var ssrl="";
	    	   var gswd="";
	    	   var hswd="";
	    	   var feedcode="";
	    	   $('input[name="jh_feedcode"]').each(function(){ feedcode+=$(this).val()+",";});
	    	   $('input[name="jh_ssll"]').each(function(){ ssll+=$(this).val()+",";});
	    	   $('span[name="jh_ssrl"]').each(function(){ ssrl+=$(this).text()+",";});
	    	   $('input[name="jh_hswd"]').each(function(){ hswd+=$(this).val()+",";});
	    	   $('input[name="jh_gswd"]').each(function(){ gswd+=$(this).val()+",";});
	    	   
	    	   var datas= $.param({ssll:ssll,ssrl:ssrl,gswd:gswd,hswd:hswd,feedcode:feedcode})+'&'+$('form').serialize() ;
	    	       
               var url="<%=path %>/dataProject/saveDataProject.do";
               $.post(url,datas,function(data){
              		  
              		if(data.flag){
              			layer.msg(data.messager, {icon: 1},function(){
              				refresh()
              			});
              			  
              		 }else{
              			layer.msg(data.messager, {icon: 2});
              		 }
              		 
               },"json")
	    	   
	    	   
	       }
		   
		   
	</script>
  
  <body style="margin:10px;overflow:hidden;" >
     
    <div class="row">
		<div class="col-sm-12">
			<form id="listForm" name="listForm" action="#">	
			 计划日期：<input type="text" readonly class="input-text" style="width:150px" placeholder="计划日期" value="${indexMap.PROJECTDATE }" id="pro" name="projectdate">
			 理论热指标(w/㎡)：<input type="text" class="input-text" style="width:150px" placeholder="理论热指标(w/㎡)" value="${indexMap.LLRZB }" id="llrzb" name="llrzb">
			 平均温度(℃)：<input type="text" class="input-text" style="width:150px" placeholder="平均温度(℃)" value="${indexMap.AVGTEMP }" id="avgtemp" name="avgtemp">
			 热源面积(㎡)：<input type="text" class="input-text" style="width:150px" placeholder="热源面积(㎡)" value="${indexMap.CNMJ }" id="cnmj" name="cnmj">
			 <span><a class="btn btn-success radius" href="javascript:changeParam();"><i class="icon-search"></i>参数变更</a></span>
			<%-- <shiro:hasPermission name="/jsp/data/addDataTeam">
			  <span><a class="btn btn-primary radius"  onclick="layer_show('添加','<%= request.getContextPath() %>/dataTeam/addDataTeamList.do','500')"><i class="icon-edit"></i> 添加</a> </span>
            </shiro:hasPermission> --%>
            </form>
		</div>
    </div>
    
	    <div class="row analyse" >
	    <table class="table table-bordered table-hover divmatnrdesc" border="0" cellspacing="0" cellpadding="0">
		    	<thead><tr class='active'><td colspan=4><strong>计划总览</strong></td></tr></thead>
		 	      <tr >
		 	          <td><span class=''><strong>当前温度热指标</strong></span></td><td><span class='cblue' id="dqrzb"><strong>${indexMap.DQRZB }</strong>W/㎡</span></td>
		 	          <td><span class=''><strong>当前计划热指标</strong></span></td><td><span class='cblue' id="dqjh_rzb"><strong>21.67</strong>W/㎡</span></td>
		 	      </tr>
		 	      <tr >
		 	          <td><span class=''><strong>对应热负荷 </strong></span></td><td ><span class='cblue' id="dqload"><strong>${indexMap.DQLOAD }</strong>GJ/h</span></td>
		 	          <td><span class=''><strong>计划热负荷</strong></span></td><td ><span class='cblue' id="dqjh_rfh"><strong></strong>GJ/h</span></td>
		 	      </tr>
		 	      <tr >
		 	          <td><span class=''><strong>差值</strong></span></td><td ><span class='cblue' id="dq_cz"><strong></strong>GJ</span></td>
		 	          <td><span class=''><strong>百分比</strong></span></td><td ><span class='cblue' id="dq_percent"><strong></strong>%</span></td>
		 	      </tr>
		 	       <tr >
		 	          <td><span class=''><strong>理论供热量 </strong></span></td><td ><span class='cblue' id="dqrl"><strong>${indexMap.DQRL }</strong>GJ</span></td>
		 	          <td><span class=''><strong>计划供热量</strong></span></td><td ><span class='cblue' id="dqjh_rl"><strong></strong>GJ</span></td>
		 	      </tr>
		 </table>
    	</div>
    	
		<ul class="nav nav-tabs" id="myTab">  
		<!-- <ul class="nav nav-pills nav-justified" id="myTab"> 样式不一样  -->
		    <li class="active"><a href="#home">当前计划
		    	<i class="green icon-desktop bigger-110"></i></a></li>  
		    <li><a href="#profile">热源计划产热量
		    	<i class="green icon-desktop bigger-110"></i></a></li>  
		    <!-- <li><a href="#messages">Messages
		    	<i class="green icon-desktop bigger-110"></i></a></li>  
		    <li><a href="#settings">Settings
		    	<i class="green icon-desktop bigger-110"></i></a></li>   -->
		</ul>  
		  
		<div class="tab-content">  
		    <div class="tab-pane active" id="home">
				<table class="table table-bordered table-hover divmatnrdesc" border="0" cellspacing="0" cellpadding="0">
			    	<thead><tr>
		 	                  <td ><strong>#</strong></td>
		 	                  <td colspan="4"><strong>实时信息</strong></td>
		 	                  <td colspan="4"><strong>上次计划</strong></td>
		 	                  <td colspan="4"><strong>当前计划</strong></td>
		 	            </tr></thead>
		 	            <thead><tr>
		 	                  <td >&nbsp;<strong> 热源名称 </strong>&nbsp;</td>
		 	                  <td ><strong>供水温度(℃)</strong></td>
		 	                  <td ><strong>回水温度(℃)</strong></td>
		 	                  <td ><strong>瞬时流量(t/h)</strong></td>
		 	                  <td ><strong>瞬时热量(GJ/h)</strong></td>
		 	                  
		 	                  <td ><strong>供水温度(℃)</strong></td>
		 	                  <td ><strong>回水温度(℃)</strong></td>
		 	                  <td ><strong>瞬时流量(t/h)</strong></td>
		 	                  <td ><strong>瞬时热量(GJ/h)</strong></td>
		 	                  
		 	                  <td ><strong>供水温度(℃)</strong></td>
		 	                  <td ><strong>回水温度(℃)</strong></td>
		 	                  <td ><strong>瞬时流量(t/h)</strong></td>
		 	                  <td ><strong>瞬时热量(GJ/h)</strong></td>
		 	            </tr></thead>
		 	            <c:forEach var="temp" items="${jhList }" varStatus="status">
		 	                <tr>
		 	                     <td>${temp.FEEDNAME }</td>
		 	                     <input value="${temp.FEEDCODE }" type="hidden" class="input-text" name="jh_feedcode" />
		 	                     <td>${temp.REAL_GSWD }</td>
		 	                     <td>${temp.REAL_HSWD }</td>
		 	                     <td>${temp.REAL_SSLL }</td>
		 	                     <td>${temp.REAL_SSRL }</td>
		 	                     
		 	                     <td>${temp.LAST_GSWD }</td>
		 	                     <td>${temp.LAST_HSWD }</td>
		 	                     <td>${temp.LAST_SSLL }</td>
		 	                     <td>${temp.LAST_SSRL }</td>
		 	                     
		 	                     <td><input type="text" class="input-text" style="width:70px;" id="jh_gswd${status.index }" name="jh_gswd" oninput="updateSsll('${status.index }')" value="${temp.GSWD }" ></td>
		 	                     <td><input type="text" class="input-text" style="width:70px;" id="jh_hswd${status.index }" name="jh_hswd" oninput="updateSsll('${status.index }')" value="${temp.HSWD }" ></td>
		 	                     <td><input type="text" class="input-text" style="width:70px;" id="jh_ssll${status.index }" name="jh_ssll" oninput="updateSsll('${status.index }')" value="${temp.SSLL }" ></td>
		 	                     <td><span name="jh_ssrl" ><span id="jh_ssrl${status.index }" >${temp.SSRL }</span></td>
		 	                     
		 	                     
		 	               </tr>
		 	            </c:forEach>
		 	            <tr>
		 	               <td>总计</td>
		 	               <td colspan="10"></td>
		 	               <td><input type="text" class="input-text" style="width:70px;" id="total_jhll" readonly value=""/></td><!-- class="input-text" -->
		 	               <td><input type="text" class="input-text" style="width:70px;" id="total_jhrl" readonly value=""/></td>
		 	            </tr>
			 </table>
			 <div  style="position:fixed;top:95%;left:45%;">
			 	<shiro:hasPermission name="/jsp/data/dataProjectList">
				  <span><input class="btn btn-primary radius" type="button" onclick="add();" value="&nbsp;&nbsp;保存计划&nbsp;&nbsp;"> </span>
	            </shiro:hasPermission>
		     </div>
			</div>  
		    <div class="tab-pane" id="profile">
				<table class="table table-bordered table-hover divmatnrdesc" border="0" cellspacing="0" cellpadding="0">
			    	<thead>
		    		    <tr>
		 	               <td ><strong>热源名称</strong></td>
		 	               <td ><strong>计划热量</strong></td>
		 	            </tr>
		 	         </thead>
		 	         <c:forEach var="temp" items="${jhrlList }" varStatus="status">
			 	        <tr>
			 	           <td>${temp.FEEDNAME }</td>
			 	           <td>${temp.JHRL }GJ</td>
			 	        </tr>
		 	         </c:forEach>
			   </table>
			</div>  
		    <!-- <div class="tab-pane" id="messages">...</div>  
		    <div class="tab-pane" id="settings">...</div>   -->
		</div>  

    <!-- <div class="row" style="margin-top:10px;">
         <div class="col-sm-12">
         
              检索 
			
			  <div class="tab-pane active">
					检索 
			   		<table id="table" class="divmatnrdesc"
			           data-id-field="id"
			           data-click-to-select="true"
			           data-page-list="[10, 25, 50, 100]">
		           </table>
			  </div>
         
         </div>
			
			
          
	  </div> -->
	  
 
	<script type="text/javascript">
	var $table = $('#table'),
    $remove = $('#remove'),
    selections = [];

	$(function () {
		updateSsll(0);
		
		$('#myTab a:first').tab('show'); //初始化显示哪个tab  
        $('#myTab a').click(function(e) {  
            e.preventDefault(); //阻止a链接的跳转行为  
            $(this).tab('show'); //显示当前选中的链接及关联的content  
        }); 
		
		var columns =eval(${jsonTableGrid});
	    $table.bootstrapTable({
	        height: getHeight(),
	       
	        method: 'post',
	        url:"<%=path%>/taskOrderIndexHistory/getTaskOrderIndexHistoryList.do",
	        dataType: "json",
	        striped: true,
	        pagination: true,
	        queryParamsType: "limit",
	        contentType: "application/x-www-form-urlencoded",
	        pageSize: 20,
	        pageNumber:1,
	        queryParams: queryParams,
	        showColumns: false, //不显示下拉框（选择显示的列）
	        sidePagination: "server", //服务端请求
	        responseHandler: responseHandler,
	        columns: columns
	    });
	    
	   
	    $(window).resize(function () {
	        $table.bootstrapTable('resetView', {
	            height: 500,
	            width:getWidth()
	        });
	    });
	});
	
	function responseHandler(res) {
		    return {
		    	"rows": res.rows,
		    	"total": res.total
	    	};
	}
	 function queryParams(params) {
		 var datas= $.param({rows: params.limit,page: params.pageNumber}) + '&' + $('#listForm').serialize();
		 return datas;
		/* return {rows: params.limit,page: params.pageNumber,feedname:$("#feedname").val()}; */
	} 
	function getHeight() {
	    return $(window).height()-80;
	}
	function getWidth(){
		return $(window).width()-210;
	}
	
	function search(){
	    $table.bootstrapTable('refresh');
    }
	
	function operateFormatter(value, row, index) {
		 var temp= '';
		 <shiro:hasPermission name="/jsp/data/updateDataTeam">
		    temp +="<a class=\"like\" href=\"javascript:editDataTeam(\'"  +value+   "\')\" title=\"编辑\"><i class=\"icon-edit\"></i>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;   ";
         </shiro:hasPermission>
         
         <shiro:hasPermission name="/jsp/data/deleteDataTeam">
		    //temp += '  <a class="like" href="javascript:deleteFeed('+value+')" title="删除"><i class="icon-remove"></i></a>';
		    temp +="<a class=\"like\" href=\"javascript:deleteDataTeam(\'"  +value+   "\')\" title=\"删除\"><i class=\"icon-remove\"></i>删除</a>";
         </shiro:hasPermission>
		
		 return temp;
    }
	
    function retlentFormatter(value, row, index) {
    	return "<a href='javascript:showScheduler(\""+row.ID+"\");' class=\"linkline\" >查看流程</a>";
   }
    
	function editDataTeam(obj){
		
		layer_show('修改',"<%= request.getContextPath() %>/dataTeam/updateDataTeamList.do?teamid="+obj,'500')
	}
	
	function deleteDataTeam(obj){
		
		layer.confirm('确认删除吗?',{btn:['是','否']},function(){
			 var url="<%=path %>/dataTeam/deleteDataTeam.do?teamid="+obj;
       	     $.post(url,$("form").serialize(),function(data){
       	    	 if(data.flag){
       	    		 layer.msg('删除成功', {icon: 1},function(){
       	    			search()
       	    		 });
       	    	 }
       	    	 
       	     },'json')
		},function(){
			
		})
	}
	
	
	
	
	    
    </script> 
  </body>
</html>

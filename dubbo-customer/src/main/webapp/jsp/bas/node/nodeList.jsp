<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
       
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
        <script src="<%= request.getContextPath() %>/js/QRcode/excanvas.compiled.js"></script>
         <script src="<%= request.getContextPath() %>/js/QRcode/jquery.qrcode.min.js"></script>
        <script src="<%= request.getContextPath() %>/js/QRcode/qrcode_customer.js"></script>
       <%@ include file="/jsp/header.jsp"%>
       <!-- ace styles -->
		
		
       
    </head>
  <body style="margin:10px;overflow:hidden;" >
     <form id="listForm" name="listForm" action="#">	
    <div class="row">
		<div class="col-sm-12">
			 <select class="selectpicker" data-live-search="true"  name="orgid" id="orgid" title="请选择一个公司" >
	            <option value="">--请选择--</option>
	               <c:forEach var="s" items="${sysorg}">
			            		<option value="${s.ORGID}">${s.ORGNAME}</option>
			        </c:forEach>
	         </select>
			 <input type="text" class="input-text" style="width:250px" placeholder="输入换热站名称" id="nodename" name="nodename">
			<!-- <button onclick="search()" class="btn btn-success radius"  name=""><i class="icon-search"></i> 查询</button> -->
			<a href="javascript:search()" class="btn btn-success radius"  ><i class="icon-search"></i> 查询</a>		
			<shiro:hasPermission name="/jsp/bas/node/addNode">
			  <span><a class="btn btn-primary radius"  onclick="add()"><i class="icon-edit"></i> 添加换热站</a> </span>
            </shiro:hasPermission>
            <shiro:hasPermission name="/jsp/bas/node/addNode">
			  <%-- <span><a class="btn btn-primary radius"  onclick="layer_show('批量修改','<%= request.getContextPath() %>/node/addNodeList.do','800')"><i class="icon-edit"></i> 批量修改</a> </span> --%>
            	<span><a class="btn btn-primary radius"  onclick="batchAdd()"><i class="icon-edit"></i> 批量修改</a> </span>
            </shiro:hasPermission>
            <a  href="javascript:exportExcel()" class="btn btn-danger radius"  ><i class="icon-download"></i> 导出</a >
		 <a  href="javascript:exportQRExcel()" class="btn btn-warning radius"  ><i class="icon ion-archive"></i> 批量导出二维码</a >
		</div>
    </div>
    </form>
     <div class="row analyse" >
    	<ul>
		      <li style="width:170px">
		         <span class="w90">换热站数量：</span>
		         <span class="fwd cred" id="hrznum"></span>个
		      </li>
		      <li style="width:170px">
		         <span class="w90">自管站：</span>
		         <span class="fwd cblue"id="zgz"></span>%
		      </li>
		      <li style="width:170px">
		         <span class="w90">挂暖：</span>
		         <span class="fwd cyellow" id="gn"></span>%
		      </li>
		      <li style="width:170px">
		         <span class="w90">地暖：</span>
		         <span class="fwd cgreen"id="dn"></span>%
		      </li>
		      <li style="width:170px">
		         <span class="w90">采暖面积：</span>
		         <span class="fwd cpurple"id="cnmj"></span>万㎡
		      </li>
		     
		      
    	 </ul> 
    </div>

    <div class="row" style="margin-top:10px;">
         <div class="col-sm-12">
         
              <!-- 检索  -->
			
			  <div class="tab-pane active">
					<!-- 检索  -->
			   		<table id="table" class="divmatnrdesc"
			           data-id-field="id"
			           data-click-to-select="true"
			           data-page-list="[10, 25, 50, 100]">
		           </table>
			  </div>
         
         </div>
			
			
          
	  </div>
 
	<script type="text/javascript">
	var $table = $('#table'),
    $remove = $('#remove'),
    selections = [];

	$(function () {
	    $table.bootstrapTable({
	        height: getHeight(),
	       
	        method: 'post',
	        url:"<%=path%>/node/getNodeList.do",
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
	        columns: [
	            [
	               {
		            	title : '序号',
		            	align: "center",
		            	width: 40,
		            	formatter: function (value, row, index) {
		            	    //获取每页显示的数量
		            	    var pageSize=$('#table').bootstrapTable('getOptions').pageSize;  
		            	    //获取当前是第几页  
		            	    var pageNumber=$('#table').bootstrapTable('getOptions').pageNumber;
		            	    //返回序号，注意index是从0开始的，所以要加上1
		            	    return pageSize * (pageNumber - 1) + index + 1;
		            	}
	               },{
	                    field: 'NODEID',
	                    checkbox: true,
	                    align: 'center'
	                }, {
	                    title: '换热站名称',
	                    field: 'NODENAME',
	                    align: 'center'
	                }, {
	                    title: '所属公司',
	                    field: 'ORGNAME',
	                    align: 'center'
	                }, {
	                    title: '所属热源',
	                    field: 'FEEDNAME',
	                    align: 'center'
	                }, {
	                    title: '设计负荷',
	                    field: 'SJFH',
	                    align: 'center'
	                },  {
	                    title: '采暖面积',
	                    field: 'CNMJ',
	                    align: 'center'
	                },/* {
	                    title: '投入日期',
	                    field: 'TRRQ',
	                    align: 'center'
	                },  */{
	                    title: '管理方式',
	                    field: 'GLFS',
	                    align: 'center'
	                }, {
	                    title: '采暖方式',
	                    field: 'CNFS',
	                    align: 'center'
	                }, /* {
	                    title: '站属性',
	                    field: 'NODESX',
	                    align: 'center'
	                }, */ {
	                    title: '节能方式',
	                    field: 'JNFS',
	                    align: 'center'
	                }, {
	                    title: '工艺图',
	                    field: 'GYTID',
	                    align: 'center',
	                    formatter: showPicFormatter
	                }, {
	                    title: '二维码',
	                    field: '',
	                    align: 'center',
	                    formatter: qrcodeData
	                },  {
	                    title: '操作',
	                    field: 'CODE',
	                    align: 'center',
	                    formatter: operateFormatter
	                }
	            ]
	        ]
	    });
	    
	   
	    $(window).resize(function () {
	        $table.bootstrapTable('resetView', {
	            height: 500,
	            width:getWidth()
	        });
	    });
	});
	
	function add(){
	layer_show('添加',"<%= request.getContextPath() %>/node/addNodeList.do",'800');
	
	}
	
		function batchAdd(){
	var row = $('#table').bootstrapTable('getSelections', function (row) {
          return row;
		});
//	alert(JSON.stringify(row));
// 	alert(row[0].NODECODE);
	if(row.length==0){
	alert("请至少选中一行!");
	//layer.msg('请至少选中一行', {icon: 2});
	}
	var nodecodes="";
	if(row.length>1){
	for(i=0;i<row.length-1;i++){
	nodecodes+=row[i].NODECODE+",";
	}
	}
	nodecodes+=row[row.length-1].NODECODE;
	//alert(nodecodes);
	layer_show('添加',"<%= request.getContextPath() %>/node/batchAddNodeList.do?nodecode="+nodecodes,'750','400');
	
	}
	
		function showPicFormatter(value, row, index) {
		 var temp= "";	
		var obj=row.GYTID;
	//alert(JSON.stringify(row));
	if(obj!=null){
       /*   temp +="<a class=\"like\" href=\"javascript:viewPicture(\'"  +obj+   "\')\" title=\"预览\"><i class=\"icon-picture\"></i>预览</a>&nbsp;&nbsp;&nbsp;&nbsp;   "; */
	  temp +="<a class=\"like\" href=\"javascript:viewPicture(\'"  +obj+   "\')\" title=\"预览\"><img style=\"width:40px;height:20px\" src=\"<%= request.getContextPath() %>/uplode/"+obj+"\"/>  </a>&nbsp;  ";
	}
	return temp;
    }
	
	
		function viewPicture(obj){
	//	alert(obj);
	
	layer_show('图片预览','<%= request.getContextPath() %>/uplode/'+obj,1050);
	}
	
	
	
	function responseHandler(res) {
		    return {
		    	"rows": res.rows,
		    	"total": res.total
	    	};
	}
	 function queryParams(params) {
	//	return {rows: params.limit,page: params.pageNumber,nodename:$("#nodename").val()};
	 var datas= $.param({rows: params.limit,page: params.pageNumber}) + '&' + $('#listForm').serialize();
	 getSummary();
	return datas;
	} 
	
		function getSummary(){
		 var datas= $.param({nodecode:$("#nodename").val()+""}) + '&' + $('#listForm').serialize();
		$.ajax({
			  type: 'POST',
			  url: "<%=path%>/node/getNodeSummary.do",
			  data: datas,
			  success: function(result){
				  $("#hrznum").text(result.NUM);
				  $("#zgz").text(result.ZG);
				  $("#gn").text(result.GUANUAN);
				  $("#dn").text(result.DINUAN);
				  $("#cnmj").text(result.CNMJ);
			  },
			  dataType: 'json'
			});
	}
	
	
	function getHeight() {
	    return $(window).height()-100;
	}
	function getWidth(){
		return $(window).width()-210;
	}
	
	function search(){
	    $table.bootstrapTable('refresh');
    }
	
	function operateFormatter(value, row, index) {
		 var temp= '';
		 <shiro:hasPermission name="/jsp/bas/node/updateNode">
		    temp +="<a class=\"like\" href=\"javascript:editNode(\'"  +value+   "\')\" title=\"编辑\"><i class=\"icon-edit\"></i>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;   ";
         </shiro:hasPermission>
         
         <shiro:hasPermission name="/jsp/bas/node/deleteNode">
		    //temp += '  <a class="like" href="javascript:deleteFeed('+value+')" title="删除"><i class="icon-remove"></i></a>';
		    temp +="<a class=\"like\" href=\"javascript:deleteNode(\'"  +value+   "\')\" title=\"删除\"><i class=\"icon-remove\">删除</i></a>";
         </shiro:hasPermission>
		
		 return temp;
    }
	
	function editNode(obj){
		
		layer_show('修改换热站',"<%= request.getContextPath() %>/node/updateNodeList.do?nodeid="+obj,'800')
	}
	
	function deleteNode(obj){
		
		layer.confirm('确认删除该换热站吗？',{btn:['是','否']},function(){
			 var url="<%=path %>/node/deleteNode.do?nodeid="+obj;
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
	
	//导出功能
		function exportExcel(){
		// var datas= $.param({nodecode:$("#nodename").val()+""}) + '&' + $('#listForm').serialize();
		location.href="<%=path%>/node/exportExcel.do?pageid=1001&"
		+$.param({nodecode:$("#nodename").val()+""}) + '&' + $('#listForm').serialize();
	}
	//导出二维码图片信息到Excel
	function exportQRExcel(){
		//使用getSelections即可获得，row是json格式的数据
	var rowIds = $table.bootstrapTable('getSelections', function (row) {
	          return row;
	});
	if(rowIds.length>0){
	 	var ids = "";
	 	for(var i in rowIds){
	 	ids+=rowIds[i].CODE+","
	 	}
	 	ids = ids.substr(0,ids.length-1);
	 	location.href="<%=path%>/node/exportQRExcel.do?pageid=1001&"
	 		+$.param({nodecodes:ids+""});
	}else{
		alert("请选择要导出的指定行！");
	} 	
		
		
		
	<%-- location.href="<%=path%>/node/exportQRExcel.do?pageid=1001&"
	+$.param({nodecode:$("#nodename").val()+""}) + '&' + $('#listForm').serialize(); --%>
	}
	
	
	//二维码相关
	function qrcodeData(value, row, index){
		var skin = $('#skin').html();
		if(skin=='.white'){
			skin='qrblack.png';
		}else{
			skin='qrwhite.png';
		}
		var data=JSON.stringify(row);
		var temp="";
		temp+="<a class=\"like\" href=\"javascript:toQRcode("+index+")\" title=\"二维码\" ><img style=\"width:19px;height:19px\" src=\"<%= request.getContextPath() %>/images/QRcode/"+skin+"\"/></a><div id=\"div_"+index+"\"hidden>"+data+"</div>  ";
		return temp;
	}
	var str ;
	var saveName="";
	function changeData(row){
		str="";
		saveName = row.NODENAME;
		//alert(JSON.stringify(row));
        str +="换热站名称："+row.NODENAME+"; ";
        str +="所属公司："+row.ORGNAME+"; ";
        str +="所属热源："+row.FEEDNAME+"; ";
        str +="设计负荷："+row.SJFH+"; ";
        str +="投入日期："+row.TRRQ+"; ";
        str +="管理方式："+row.GLFH+"; ";
        str +="采暖方式："+row.CNFH+"; ";
        str +="站属性："+row.NODESX+"; ";
        str +="节能方式："+row.JNFS+"; ";
	}
	 function toQRcode(index){
		    changeData(eval('('+$("#div_"+index+"").html()+')'));//处理json数据
		    //QRcode 为下方DIV 标签ID,imgId：下方 img标签的id属性值；aId:下方 a标签的id属性值;saveName 代表保存图片的名称
		    generateQRCode('QRcode',"canvas",320, 320,str);
	        layer.open({
	       	   title: '二维码',
				   closeBtn: false,
				   type: 0,
                   shift: 0,
				   shadeClose: false,
				   btn: ['导出', '确认'],
				   content: $('#QRcode'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
				   //content:'hahahhahahha', //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
				   btn1: function(){
				          down('qrImg','saveImg',saveName)
				        },
				   end:function(){
						var div1=document.createElement("div");  
						div1.id ="QRcode";
						div1.style.cssText="width:100%;height:100%"; 
						document.body.appendChild(div1); 
				    } 
	        });
	  }
	
	
	
	    
    </script> 
    <style>
    .layui-layer-content{
    color:#fff
    }
    </style>
  </body>
  <!-- 存储主题 -->
  <div id=skin hidden><%=skin%></div>
  <!-- 二维码相关 -->
  <a id="saveImg"></a>
  <img id="qrImg" hidden style='border:1px solid red;height:300px'></img>
  <div id="QRcode" style="width:100%;height:100%">
	</div>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
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
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">  
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
        <script src="<%= request.getContextPath() %>/js/QRcode/excanvas.compiled.js"></script>
         <script src="<%= request.getContextPath() %>/js/QRcode/jquery.qrcode.min.js"></script>
        <script src="<%= request.getContextPath() %>/js/QRcode/qrcode_customer.js"></script>
        <link href="<%= request.getContextPath() %>/css/bootstrap.min_temp.white.css" rel="stylesheet" /> 
       <%@ include file="/jsp/header.jsp"%>
    </head>



<body style="margin:10px;overflow:hidden;" >
    <div class="row">
		<div class="col-sm-12">
			<form id="listForm" name="listForm" action="#">	
			<input type="hidden" name="type" id="type" value="${type }">
			 
			 <select class="selectpicker" style="width: 232px;height: 31px" data-live-search="true" name="gldw" id="gldw" >
	            <option value="" selected>--请选择管理单位--</option>
	            <c:forEach var="org" items="${orgList}">
	            <option value="${org.ORGID }">${org.ORGNAME }</option>
	            </c:forEach>
	         </select>
	        <input type="text" class="input-text" style="width:180px" placeholder="输入设备名称" id="sbmc" name="sbmc">
				 <span><a class="btn btn-success radius" href="javascript:search()"><i class="icon-search"></i>查询</a></span>
			<shiro:hasPermission name="/jsp/innerDevice/addDevice">
           <span><a class="btn btn-primary radius"  onclick="add()"><i class="icon-edit"></i> 添加</a> </span>
            </shiro:hasPermission>	
             <a  href="javascript:exportExcel()" class="btn btn-danger radius"  ><i class="icon-download"></i> 导出</a >
             <a  href="javascript:exportQRExcel()" class="btn btn-warning radius"  ><i class="icon ion-archive"></i> 批量导出二维码</a >
			</form>
			 
							
		</div>
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
	//bootstrap提示框延时加载
	   setTimeout(boot,1000);
	   function boot() {
		   $("[data-toggle='tooltip']").tooltip();
	   }
	
	
	var $table = $('#table'),
    $remove = $('#remove'),
    selections = [];
	$(function () {
	 var url="<%=path%>/innerDevice/getInnerDeviceList.do";
		 var columns =eval(${jsonTableGrid});
	    $table.bootstrapTable({
	        height: getHeight(),
	       
	        method: 'post',
	        url:url,
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
	        columns: columns,
	        formatLoadingMessage: function () {
	        	return "请稍等，正在加载中...";
	        	},
	       onLoadSuccess: function (data) {
	    	   setTimeout(boot,900);
	        }
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
/* 	 function queryParams(params) {
		return {rows: params.limit,page: params.pageNumber,basmeter:$("#basmeter").val()};
	}  */
	function queryParams(params) {
		 var datas= $.param({rows: params.limit,page: params.pageNumber}) + '&' + $('#listForm').serialize();
		 return datas;
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
	
	function operateFormatter(value, row, index) {
		 var temp="";
		//var stationtype=row.STATIONTYPE;
          <shiro:hasPermission name="/jsp/innerDevice/updateDevice">
         	/* temp+="<a class=\"like\" href=\"javascript:editDevice("+value+",'"+stationtype+"')\" title=\"编辑\"><i class=\"icon-edit\"></i>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;   "; */
       	 temp+="<a class=\"like\" href=\"javascript:editDevice("+value+")\" title=\"编辑\"><i class=\"icon-edit\"></i>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;   ";
         </shiro:hasPermission>
         <shiro:hasPermission name="/jsp/innerDevice/delDevice">
           temp+="  <a class=\"like\" href=\"javascript:deleteMeter("+value+")\" title=\"删除\"><i class=\"icon-remove\"></i>删除</a>";
         </shiro:hasPermission>
		
		 return temp;
    }
    
    function retlentFormatter(value, row, index) {
        if(value!=null){
		 var ret = value.split("");
		 if(ret.length > 5){
			 //value=value.substring(0,4)+"<a class=\"like\"  href=\"javascript:wjhzb(\'"+value+"\')\" title=\"更多\">...更多</a>";
			 value=value.substring(0,4)+"<a data-toggle=\"tooltip\" data-placement=\"right\"  title=\'"+value+"\'>...更多</a>";
		 }
		 return value;
    	}
    }
	
	function wjhzb(value) {
		
		layer.alert(value, {skin: 'layui-layer-molv',closeBtn: 1,shift: 4,title: "规格型号" });
		
   	}
	
		function add(){
		var type=$("#type").val();
		layer_show('添加',"<%= request.getContextPath() %>/innerDevice/selectAll.do?type="+type,'800','530');
	}
	
	
	function editDevice(obj){
	  var type=$("#type").val();
		layer_show('修改',"<%= request.getContextPath() %>/innerDevice/findDevice.do?id="+obj+"&type="+type,'800','530')
	}
	function deleteMeter(obj){
	//alert(obj);
		layer.confirm('确认删除',{btn:['是','否']},function(){
			 var url="<%=path %>/basmeter/deleteMeter.do?id="+obj;
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
	
	  	function getProjectList(){
				var stationtype = document.getElementById("zlx").value;
				var url="<%=path %>/basmeter/getProjectList.do?stationtype=" + stationtype;
				$.ajax({
			    	type: "POST",
			    	url: url,
			    	success: function(data){
			    	//	 alert(data);
			    		 $("#mc").empty();
			    		 var i="<option value=\"\" selected>请选择站名称</option>";
			    		 $("#mc").append(i);		    		
			    		 $("#mc").append(data);
			    		 $("#mc").selectpicker('refresh');
			    	},
			    	error: function(){ alert("请求失败!"); }
			    });
			}
	var str ;
	var saveName="";
	function changeData(row){
		str="";
		saveName = row.MC+"-"+row.NAME;
		//alert(JSON.stringify(row));
        str +="设备来源："+row.MC+"; ";
        str +="设备名称："+row.NAME+"; ";
        str +="规格型号："+row.GGXH+"; ";
        str +="数量："+row.SL+"; ";
        str +="单位："+row.DW+"; ";
        str +="管理单位："+row.GLDW+"; ";
        str +="设备类型："+row.SSLX+"; ";
        str +="制造厂家："+row.ZZCJ+"; ";
        str +="存放地点："+row.CFDD+"; ";
        str +="安装时间："+row.RJTSJ+"; ";
        str +="入厂时间："+row.RCSJ+"; ";
	}
	 function toQRcode(index){
		    changeData(eval('('+$("#div_"+index+"").html()+')'));//处理json数据
		    	generateQRCode('QRcode',"canvas",320, 320,str);
	        layer.open({
				   title: '二维码',
				   closeBtn: false,
                   shift: 0,
				   shadeClose: true,
				   btn: ['导出', '确认'],
				   content: $('#QRcode'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
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
	  
	  
	  	  	//导出功能
		function exportExcel(){
		 location.href="<%=path%>/innerDevice/exportExcel.do?pageid=1004&"
		+$.param({nodecode:$("#sbmc").val()+""}) + '&' + $('#listForm').serialize(); 
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
			 	 location.href="<%=path%>/innerDevice/exportQRExcel.do?pageid=1004&"
			 		+$.param({codes:ids+""})+ '&' + $('#listForm').serialize();   
			}else{
				alert("请选择要导出的指定行！");
			} 
		}
	
    </script> 
    <style>
     .layui-layer-content{
    color:white
    } 
    .navbar-nav li:hover{
    display: block;
    background: green;
    padding: 10px;
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

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>


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
			
			 <input type="text" class="input-text" style="width:250px" placeholder="请输入热源名称" id="feedname" name="feedname">
			<!-- <button onclick="search()" class="btn btn-success radius"  name=""><i class="icon-search"></i> 查询</button> -->
			<a href="javascript:search()" class="btn btn-success radius"  ><i class="icon-search"></i> 查询</a>		
			<shiro:hasPermission name="/jsp/bas/feed/addFeed">
			  <span><a class="btn btn-primary radius"  onclick="layer_show('添加热源','<%= request.getContextPath() %>/feed/addFeedList.do','500')"><i class="icon-edit"></i> 添加热源</a> </span>
            </shiro:hasPermission>
            <a  href="javascript:exportExcel()" class="btn btn-danger radius"  ><i class="icon-download"></i> 导出</a >
            <a  href="javascript:exportQRExcel()" class="btn btn-warning radius"  ><i class="icon ion-archive"></i> 批量导出二维码</a >
		</div>
    </div>
    </form>
    
     <div class="row analyse" >
    	<ul>
		      <li style="width:170px">
		         <span class="w90">热源数量：</span>
		         <span class="fwd cred"id="countnum"></span>个
		      </li>
		  <!--     <li style="width:170px">
		         <span class="w90">自管：</span>
		         <span class="fwd cblue">8</span>%
		      </li> -->
		     <li style="width:170px">
		         <span class="w90">设计负荷：</span>
		         <span class="fwd cblue"id="sjfhnum"></span>Mw
		      </li>
		      <li style="width:170px">
		         <span class="w90">采暖面积：</span>
		         <span class="fwd cpurple" id="cnmjnum"></span>万㎡
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
	        url:"<%=path%>/feed/getFeedList.do",
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
	        clickToSelect: true,
	        columns: [
	            [  {
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
	                    field: 'FEEDID',
	                    checkbox: true,
	                    align: 'center'
	                }, {
	                    title: '热源名称',
	                    field: 'FEEDNAME',
	                    align: 'center'
	                }, {
	                    title: '所属公司',
	                    field: 'ORGNAME',
	                    align: 'center'
	                }, {
	                    title: '设计负荷',
	                    field: 'SJFH',
	                    align: 'center'
	                }, {
	                    title: '采暖面积',
	                    field: 'CNMJ',
	                    align: 'center'
	                }, {
	                    title: '所在位置',
	                    field: 'SZWZ',
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
	                }, {
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
	
	function responseHandler(res) {
		    return {
		    	"rows": res.rows,
		    	"total": res.total
	    	};
	}
	 function queryParams(params) {
	//	return {rows: params.limit,page: params.pageNumber,feedname:$("#feedname").val()};
	  var datas= $.param({rows: params.limit,page: params.pageNumber}) + '&' + $('#listForm').serialize();
	  getSummary();
	  return datas;
	} 
	
		function getSummary(){
		 var datas= $.param({nodecode:$("#feedname").val()+""}) + '&' + $('#listForm').serialize();
		$.ajax({
			  type: 'POST',
			  url: "<%=path%>/feed/getFeedSummary.do",
			  data: datas,
			  success: function(result){
				  $("#countnum").text(result.NUM);//热源数量
				  $("#sjfhnum").text(result.SJFH); //设计负荷
				  $("#cnmjnum").text(result.CNMJ);//采暖面积
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
		 <shiro:hasPermission name="/jsp/bas/feed/updateFeed">
		    temp +="<a class=\"like\" href=\"javascript:editFeed(\'"  +value+   "\')\" title=\"编辑\"><i class=\"icon-edit\"></i>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;   ";
         </shiro:hasPermission>
         
         <shiro:hasPermission name="/jsp/bas/feed/deleteFeed">
		    //temp += '  <a class="like" href="javascript:deleteFeed('+value+')" title="删除"><i class="icon-remove"></i></a>';
		    temp +="<a class=\"like\" href=\"javascript:deleteFeed(\'"  +value+   "\')\" title=\"删除\"><i class=\"icon-remove\"></i>删除</a>";
         </shiro:hasPermission>
		
		 return temp;
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
	
	
	function editFeed(obj){
		
		layer_show('修改热源',"<%= request.getContextPath() %>/feed/updateFeedList.do?feedid="+obj,'500')
	}
	
	function deleteFeed(obj){
		
		layer.confirm('确认删除该热源',{btn:['是','否']},function(){
			 var url="<%=path %>/feed/deleteFeed.do?feedid="+obj;
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
		saveName = row.FEEDNAME;
		//alert(JSON.stringify(row));
        str +="热源名称："+row.FEEDNAME+"; ";
        str +="所属公司："+row.ORGNAME+"; ";
        str +="设计负荷："+row.SJFH+"; ";
        str +="采暖面积："+row.CNMJ+"; ";
        str +="所在位置："+row.SZWZ+"; ";
	}
	 function toQRcode(index){
		    changeData(eval('('+$("#div_"+index+"").html()+')'));//处理json数据
		    generateQRCode('QRcode',"canvas",320, 320,str);//QRcode 为div   ID
	        layer.open({
				   title: '二维码',
				   closeBtn: false,
                   shift: 0,
				   shadeClose: false,
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
		// var datas= $.param({nodecode:$("#nodename").val()+""}) + '&' + $('#listForm').serialize();
		location.href="<%=path%>/feed/exportExcel.do?pageid=1002&"
		+$.param({nodecode:$("#feedname").val()+""}) + '&' + $('#listForm').serialize();
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
		 	 location.href="<%=path%>/feed/exportQRExcel.do?pageid=1002&"
		 		+$.param({feedcodes:ids+""});   
		}else{
			alert("请选择要导出的指定行！");
		} 
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

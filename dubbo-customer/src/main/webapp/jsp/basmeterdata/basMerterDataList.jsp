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
     <%--   <%@ include file="/jsp/basmeterdata/bas_header.jsp"%> --%>
        <%@ include file="/jsp/header.jsp"%>
 	<%-- 	<link rel="stylesheet" href="<%=path%>/plugins/bootstrap-table-develop/docs/assets/bootstrap/css/bootstrap.min.css" /> --%>
		<link rel="stylesheet" href="<%=path%>/plugins/x-editable-develop/dist/bootstrap3-editable/css/bootstrap-editable.css" />
<%-- 		<link rel="stylesheet" href="<%=path%>/css/bootstrap-editable.css" /> --%>
		<%-- <link rel="stylesheet" href="<%=path%>/plugins/bootstrap-table-develop/dist/bootstrap-table.min.css" /> --%>

	<%-- 	<script src="<%=path %>/plugins/bootstrap-table-develop/docs/assets/bootstrap/js/bootstrap.min.js"></script> --%>
		<script src="<%=path %>/plugins/x-editable-develop/dist/bootstrap3-editable/js/bootstrap-editable.js"></script>
	

		<%-- <script src="<%=path %>/plugins/bootstrap_table/locale/bootstrap-table-zh-CN.js"></script> --%>
	<%-- 	<script src="<%=path %>/plugins/bootstrap-table-develop/dist/extensions/editable/bootstrap-table-editable.js"></script> --%>
		<script src="<%=path %>/plugins/bootstrap_table/extensions/editable/bootstrap-table-editable.js"></script>
		

	<%-- 	<script src="<%=path %>/plugins/jquery-validation-1.15.0/js/messages_zh.js"></script> --%>

    </head>
  

<body style="margin:10px;overflow:hidden;" >
     
    <div class="row">
		<div class="col-sm-12"> 
			<form id="listForm" name="listForm" action="#">	
			  <input type="text" class="input-text" style="width:180px" placeholder="输入站名称" id="zm" name="zm">  
			   <input type="text" onfocus="WdatePicker()" id="kssj" name="kssj"  placeholder="开始时间"  value="" class="input-text Wdate" style="width:180px;">   
			   <input type="text" onfocus="WdatePicker()" id="jssj" name="jssj"  placeholder="结束时间"  value="" class="input-text Wdate" style="width:180px;">   
			 <select class="selectpicker" data-live-search="true"  name="lx" id="lx" title="所属类型" >
			 		<option value="" >----请选择---</option>
            		<option value="1" >换热站</option>
            		<option value="99" >热源厂</option>
	         </select>
				 <span><a class="btn btn-success radius" href="javascript:search()"><i class="icon-search"></i>查询</a></span>	
			<input type="text" onfocus="WdatePicker()" id="tbsj" name="tbsj"  placeholder="按日期生成填报数据"  value="" class="input-text Wdate" style="width:180px;">   
				 
           <span><a class="btn btn-primary radius"  onclick="add()"><i class="icon-edit"></i> 填报</a> </span>          
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
	var $table = $('#table'),
    $remove = $('#remove'),
    selections = [];
	$(function () {
		// var columns =eval(${jsonTableGrid});
		// alert(JSON.stringify(columns));
	    $table.bootstrapTable({
	        height: getHeight(),
	       
	        method: 'post',
	        url:"<%=path%>/basmeterdata/getBasDataMeterList.do",
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
	                    field: 'ID',
	                    checkbox: true,
	                    align: 'center'
	                }, {
	                    title: '上报时间',
	                    field: 'PTIME',
	                    align: 'center'
	                }, {
	                    title: '设备名称',
	                    field: 'METERNAME',
	                    align: 'center'
	                },  {
	                    title: '站名称',
	                    field: 'NODENAME',
	                    align: 'center'	
	                },{
	                    title: '底数',
	                    field: 'DATANUM',
	                    align: 'center',
	                    editable: {
	                    type: 'text',
	                    title: '底数',
	                    validate: function (v) {
	                    if (!v) return '底数不能为空';
						 var n = parseInt(v);
						if(!checkNumber(v)){
						return '请输入数字！';
						}
                    }
                }
                 }, {
	                    title: '所属类型',
	                    field: 'SULX',
	                    align: 'center'	                         
	                }, {
	                    title: '操作',
	                    field: 'note1',
	                    align: 'center',
	                    visible:false
	                    //formatter: operateFormatter
	                }
	            ]
	        ],
	         onEditableSave: function (field, row, oldValue, $el) {
	         $("#table").bootstrapTable("resetView");
                $.ajax({
                    type: "post",
                    url:"<%=path%>/basmeterdata/saveMeterNum.do",
                    data: row,
                    dataType: 'JSON',
                    success: function (data, status) {
                        if (data.flag == true) {
                        layer.msg('保存成功！', {icon: 1})
                        }else{
                        layer.msg('保存失败！', {icon: 2})
                        }
                    },
                    error: function () {
                        alert('编辑失败');
                    },
                    complete: function () {

                    }

                });
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
	//验证字符串是否是数字
function checkNumber(theObj) {
  var reg = /^[0-9]+.?[0-9]*$/;
  if (reg.test(theObj)) {
    return true;
  }
  return false;
}
	
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
	
/* 	function operateFormatter(value, row, index) {
		 var temp= '';
          <shiro:hasPermission name="/jsp/dataparam/updateparam">
		    temp +='<a class="like" href="javascript:editMeter('+value+')" title="编辑"><i class="icon-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;   ';
         </shiro:hasPermission>
         <shiro:hasPermission name="/jsp/dataparam/deleteparam">
		    temp += '  <a class="like" href="javascript:deleteMeter('+value+')" title="删除"><i class="icon-remove"></i></a>';
         </shiro:hasPermission>
		
		 return temp;
    } */
	
		function add() {
	         var tbsj=$("#tbsj").val();
	      //   alert(tbsj);
                $.ajax({
                    type: "post",
                    url:"<%=path%>/basmeterdata/addDataMeter.do?sj="+tbsj,
                    data: tbsj,
                    dataType: 'String',
                    success: function (data, status) {
                        if (status == "success") {
                            alert('提交数据成功');
                        }
                    },
                    error: function () {
                        alert('填报完成！');
                    },
                    complete: function () {

                    }

                });
            }
	
	
	<%-- function editMeter(obj){
	//alert("修改");
		var row = $('#table').bootstrapTable('getSelections', function (row) {
          return row;
		});
	   for (var p in row) {//遍历json数组时，这么写p为索引，0,1
	    var stationtype=row[p].STATIONTYPE.toString();
	}
	//	alert(JSON.stringify(row));
	//	alert(stationtype);
		var code;
		if(stationtype=="换热站"){
			code=1;
		}else{
			code=99;
		}
		if(row.length>1){
			 alert("请选中一行!");
			 return;
		 }
		layer_show('修改',"<%= request.getContextPath() %>/basmeter/findMeter.do?id="+obj+"&stationtype="+code,'600')
	} --%>
	function deleteMeter(obj){
	//alert(obj);
		layer.confirm('确认删除吗?',{btn:['是','否']},function(){
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
	
	
	
	
	    
    </script> 
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>生产调度系统</title>
       
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/jquery-zclct.css">
       <%@ include file="/jsp/header.jsp"%>
       <!-- ace styles -->
		
		
       
    </head>
 <body> 
<!-- <div id="sss" style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">aaaa</div>  -->
  <div id="img_bag" class="ztb_nav"> 
   <a href="javascript:void(0)" onmousedown="moveTop()" style="display:block; width:30px; margin:0 auto"> <img src="images/ztb_up.png" border="0" /></a> 
   <div id="img" class="ztb_content"> 
    <div style="position:relative;"> 
     <div class="online"></div> 
     <div class="ztb_main_01"> 
      <ul class="ztb_content_01"> 
       <li class="ztb_over"><a  class="ztb_con_text" onclick="clickli(this);">发起</a>
        <ul class="ztb_content_02"> 
         <li class="ztb_end"><a >1. 2017-05-25 12:12:36</a></li> 
         <li class="ztb_end"><a >2. 审批人：刘日浩</a></li> 
        </ul> </li> 
       <li class="ztb_over"><a  class="ztb_con_text" onclick="clickli(this);">同意</a> 
        <ul class="ztb_content_02" id="zb" style="display:block"> 
         <li class="ztb_end"><a >1. 2017-05-25 13:12:36</a></li> 
         <li class="ztb_end"><a >2. 审批人：宇豪</a></li> 
        </ul> </li> 
       <li class="ztb_over"><a  class="ztb_con_text" onclick="clickli(this);">同意</a> 
        <ul class="ztb_content_02"> 
         <li class="ztb_end"><a >1. 2017-05-25 14:12:36</a></li> 
         <li class="ztb_end"><a >2. 审批人：丁佳慧</a></li>
        </ul> </li>
       <li class="ztb_over"> <a  class="ztb_con_text" onclick="clickli(this);">同意</a> 
        <ul class="ztb_content_02"> 
         <li class="ztb_end"><a >1. 2017-05-25 15:12:36</a></li> 
         <li class="ztb_end"><a >2. 审批人：杨立宁</a></li>
        </ul> </li> 
       <li class="ztb_over"><a  class="ztb_con_text" onclick="clickli(this);">同意</a> 
        <ul class="ztb_content_02"> 
         <li class="ztb_end"><a >1. 2017-05-25 16:12:36</a></li> 
         <li class="ztb_end"><a >2. 审批人：霍思宇</a></li> 
        </ul> </li> 
       <li class="ztb_on"> <a ></a><a  class="ztb_con_text" onclick="clickli(this);">拒绝并退回</a> 
        <ul class="ztb_content_02"> 
         <li class="ztb_active"><a >1. 2017-05-25 17:12:36</a></li> 
         <li class="ztb_active"><a >2. 审批人：admin</a></li>
         <!-- <li class="ztb_active"><a >3.下拉类表测试</a></li> 
         <li><a >4.单选框列表测试</a></li> -->
        </ul> </li> 
       <li> <a ></a><a  class="ztb_con_text" onclick="clickli(this);">其他</a> 
        <ul class="ztb_content_02"> 
         <li><a >1. 时间：</a></li>
         <li> <a >2. 审批人：</a></li> 
        </ul> </li> 
       <li class="ztb_js"> <a ></a><a  class="ztb_con_text" onclick="clickli(this);">结束</a> 
        <!-- <ul class="ztb_content_02"> 
         <li class="ztb_online"><a >1. 时间：</a></li>
         <li> <a >2. 审批人：</a></li> 
        </ul>  --></li>
      </ul> 
     </div> 
    </div> 
   </div> 
   <a href="javascript:void(0)" onmousedown="moveBottom()" style="text-align:center; position:absolute; bottom:0px; right:46%"> <img src="images/ztb_down.png" border="0" /></a> 
  </div> 
  <script type="text/javascript">
		 $(function(){
				$('ul.ztb_content_02 li').click(function(){
				
						$(this).addClass('ztb_online').siblings('li.ztb_online').removeClass('ztb_online');
						$(this).parents('li').siblings('li').children('ul').find('li.ztb_online').removeClass('ztb_online');		
					})	
								})
   </script> 
   
   <script>
   	function clickli(dom){
				var ul = $(dom).next('ul');
				if (ul.is(":hidden")) {
					ul.css('display','block')
				}else{
					ul.css('display','none')
				}
				
			}
   
   </script>
  
  <script type="text/javascript">
	function js(obj){return document.getElementById(obj)}
	var maxHeight=js("img").getElementsByTagName("ul")[0].getElementsByTagName("li").length*105;
	//滚动图片的最大高度
	var targety = 211;
	//一次滚动距离
	var dx;
	var a=null;
	function moveTop(){
		 var le=parseInt(js("img").scrollTop);
		 if(le>211){
		 targety=parseInt(js("img").scrollTop)-211;
		 }else{
		 targety=parseInt(js("img").scrollTop)-le-1;
		 }
		 scTop();
	}
	function scTop(){
		 dx=parseInt(js("img").scrollTop)-targety;
		 js("img").scrollTop-=dx*.3;
		 clearScroll=setTimeout(scTop,50);
		 if(dx*.3<1){
		 clearTimeout(clearScroll);
		 }
	}
	function moveBottom(){
		 var le=parseInt(js("img").scrollTop)+211;
		 var maxL=maxHeight-600;
		 if(le<maxL){
		 targety=parseInt(js("img").scrollTop)+211;
		 }else{
		 targety=maxL
		 }
		 scBottom();
	}
	function scBottom(){
		 dx=targety-parseInt(js("img").scrollTop);
		 js("img").scrollTop+=dx*.3;
		 a=setTimeout(scBottom,50);
		 if(dx*.3<1){
		 clearTimeout(a)
		 }
	}
 </script> 

<!-- <div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
<p>适用浏览器：IE8、360、FireFox、Chrome、Safari、Opera、傲游、搜狗、世界之窗.</p>
<p>来源：<a href="http://down.admin5.com/" target="_blank">A5源码</a></p>
</div> -->
</body>
</html>

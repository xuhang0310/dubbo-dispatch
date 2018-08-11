<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>生产调度系统</title>
       
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
       <%--  <script src="<%= request.getContextPath() %>/jsp/real/feed/jquery-1.4.2.min.js"></script> --%>
       <%@ include file="/jsp/header.jsp"%>
       <!-- ace styles -->
        <script type="text/javascript" src="<%=path%>/plugins/layout/jquery.layout.js"></script>
		  <style>
		   .tempInfo{
      border:1px solid #f8f8f8;
      background:#2492AA;
      border-radius:5px;
      color:white;
      width:150px;
      height:40px;
      line-height:40px;
      text-align:center;
      z-index:2;
      
      
     }
		  
		  </style>	
		
  </head>
  
    <body onload="init()">
  	<div class="row analyse">
  		
		<div>
             <input type="hidden" value=""  id="areainfo"/>
             <input type="hidden" id="code" name="code" value="${code }"/>
             <input type="hidden" id="imgUrl" name="imgUrl" value="${imgUrl }"/>
            
        </div>
        <div >

           <span><a class="btn btn-primary radius" href="javascript:save()" style="font-size:6px;"><i class="icon-edit"></i>保存位置</a></span>
       </div>
		<div id="realMap">
			     <div style="float: left; width: 100%; height: 100%;"   >
					  <img src="<%= request.getContextPath() %>/uplode/${imgUrl}">
				 </div>
			</div>
		<div id="tempReal" style="position:absolute;top:15px;left:20px;">
        </div>	
	
	</div>

	  <script type="text/javascript">
	     var c_name =[];
	      var c_text=[];
	      var b=1;
	      var info="";
	/* 	 $(function () {
		 init('${process}');
		}); */
		function init(info){
	//	alert(b);
					  info='${process}';
                     var json=eval("("+info+")");
			     	  var table="";
			     	  for(var i=0;i<json.length;i++){
			     	        var obj=json[i];
			     	        c_name.push(obj.NAME);
			     	        c_text.push(obj.TEXT);
			     	   		table+="<div  class='tempInfo' onmousedown='$(this,event)' style='position:absolute;left:"+obj.X_NUM+"px;top:"+obj.Y_NUM+"px;font-size:8px;'>"+obj.TEXT+"</div>";
			     	  }
			     	  document.getElementById("tempReal").innerHTML=table;
			     	  
			}
	   var a;
	    var temp=""; 
		document.onmouseup=function(){
			if(!a)return;
			document.all?a.releaseCapture():window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
			temp+=search(a.innerHTML)+","+a.style.left+","+a.style.top+"@";
			document.getElementById("areainfo").value=temp;
			
			a="";
			
		};
	
	
	
		document.onmousemove=function (d){
			if(!a)return;
			if(!d)d=event;
		   	a.style.left=(d.clientX-b)+"px";
		  	a.style.top=(d.clientY-c)+"px";
		  	
		};
	
	
		function $(o,e){
		   a=o;
		   document.all?a.setCapture():window.captureEvents(Event.MOUSEMOVE);
		   b=e.clientX-parseInt(a.style.left);
		   c=e.clientY-parseInt(a.style.top);
		}
		
		function search(text){
		   var value="";
		   
		   for(var i=0;i<c_text.length;i++){
		       if(c_text[i]==text){
		           value=c_name[i];
		       };
		   }
		   
		   return value;
		}
		
		function save(){
	      var area=document.getElementById("areainfo").value;
	      var code=document.getElementById("code").value;
	       var imgUrl=document.getElementById("imgUrl").value;
	  //    alert(code);
	  //    alert(imgUrl);
	      if(area==""){    
	         window.history.back();
	      }else{
	  //    alert(2);
	      var url="<%=path%>/feedReal/updateArea.do?code="+code+"&imgUrl="+imgUrl+"&areainfo="+area;
	      window.location.href=url;

		}
			}
			</script> 
  </body>
</html>

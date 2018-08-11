<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="<%=path%>/css/h-ui/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/css/h-ui/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/jslib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />


<link rel="stylesheet" type="text/css" media="screen" href="https://cdn.bootcss.com/ionicons/2.0.1/css/ionicons.min.css">
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>dubbo分布式系统</title>
<meta name="keywords" content="H-ui.admin v2.3,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta name="description" content="H-ui.admin v2.3，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="header"></div>
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form class="form form-horizontal" action="<%=path%>/user/login.do" method="post">
      <div class="row cl">
        <label class="form-label col-3"><i class="icon ion-person" style="font-size: 25px;"></i></label>
        <div class="formControls col-8">
          <input id="userName" name="name" type="text" placeholder="账户" value="<shiro:principal/>" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-3"><i class="icon ion-ios-locked" style="font-size: 25px;"></i></label>
        <div class="formControls col-8">
          <input id="password" name="passwd" type="password" placeholder="密码" class="input-text size-L">
        </div>
      </div>
      
      <div class="row">
        <div class="formControls col-8 col-offset-3">
          <label for="online">
            <input type="checkbox" name="rememberMe" id="online" value="">
            使我保持登录状态</label>
        </div>
      </div>
      <div class="row">
        <div class="formControls col-8 col-offset-3">
          <div style="position: relative; float: left;">
         	 <input name=""  type="submit" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
          </div>
          <div style="position: relative; float: left;padding-left: 20px;">
          	<input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
          </div>
        </div>
      </div>
    </form>
  </div>
</div>
<div class="footer">Copyright 博达股份</div>
<script type="text/javascript" src="<%=path%>/jslib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=path%>/js/H-ui.js"></script> 
<script>
var msg='${msg }';
if(msg!=""){
	alert(msg);
}

function  login(){
	if($("#userName").val()==""){
		return false;
	}
	if($("#password").val()==""){
		return false;
	}
	$.ajax({
		url:'<%=path%>/user/login?timestamp=' + Math.random(),
		data:{userName:$("#userName").val(), password:$("#password").val()},
		type:'POST',
		dataType:'json',
		success:function(res) {
			var oper = res.oper;
			//登录失败
			if(oper == 'f') {
				alert(res.msg);
				
				return;
			}
			//登录成功
			if(!res.isAppsGroup) {
				window.location.replace("<%=path %>/user/loginHome.do");
			}else {
				window.location.replace("<%=path %>/user/loginHome.do");
			}
		}
	});
}

</script>
</body>
</html>

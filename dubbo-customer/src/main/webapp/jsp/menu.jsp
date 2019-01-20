<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.github.xupei.simple.shiro.*" %>
<%@ page language="java" import="org.apache.shiro.SecurityUtils" %>
<%
String path = request.getContextPath();
ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
String skin="";
       skin=user.getSkintemplate();
      
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
    <head>
		<meta charset="utf-8" />
		<title>后台管理系统</title>
		<meta name="keywords" content="Bootstrap模版,Bootstrap模版下载,Bootstrap教程,Bootstrap中文" />
		<meta name="description" content="站长素材提供Bootstrap模版,Bootstrap教程,Bootstrap中文翻译等相关Bootstrap插件下载" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		<link href="<%=path%>/css/bootstrap.min<%=skin %>.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=path%>/css/font-awesome.min.css" />

		<!-- 引入风格小图标 -->
      <%--   <link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/css/ionicons.css">  --%>
         <link rel="stylesheet" type="text/css" media="screen" href="https://cdn.bootcss.com/ionicons/2.0.1/css/ionicons.min.css">
		<!--[if IE 7]>
		  <link rel="stylesheet" href="<%=path%>/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- fonts -->

		<script src="<%=path%>/js/googlefont.js"></script>

		<!-- ace styles -->

		<link rel="stylesheet" href="<%=path%>/css/ace.min<%=skin %>.css" />
		<link rel="stylesheet" href="<%=path%>/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="<%=path%>/css/ace-skins.min.css" />
		<link rel="stylesheet" href="<%=path%>/css/menu.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=path%>/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="<%=path%>/js/ace-extra.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery-1.10.2.min.js"></script>
		

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="<%=path%>/js/html5shiv.js"></script>
		<script src="<%=path%>/js/respond.min.js"></script>
		<![endif]-->
		
	</head>
	
	<script type="text/javascript">
	
	 //高度自适应设置
  //高度自适应设置
	window.onresize = function() {
		onchange();
	}	
	$(function() {
		onchange();
	});
	function onchange(){
		var h = $(window).height()-105;
		document.getElementById("mainiframe").height=h;
		document.getElementById("mainiframe").contentWindow.aa(h);
	}
    
    
	</script>

	<body style="overflow:hidden" class="navbar-fixed">
		<div class="navbar navbar-default navbar-fixed-top" id="navbar">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
						<%-- 	<img id="logoBgImg" style="height: 25px; width: 190px;" src="<%=path %>/images/logo.png"> --%>
							后台管理系统
						</small>
					</a><!-- /.brand -->
				</div><!-- /.navbar-header -->

				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						

						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="<%=path%>/avatars/user.jpg" alt="Jason's Photo" />
								<span class="user-info">
									<small>欢迎光临,</small>
									管理员
								</span>

								<i class="icon-caret-down"></i>
							</a>

							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="javascript:clickPW();" >
										<i class="icon-cog"></i>
										密码变更
									</a>
								</li>
								<li class="divider"></li>
								<li>
									<a href="javascript:changeSkin();" >
										<i class="icon ion-tshirt" style=" font-size: 15px;margin-left: 4px;"></i> 
										&nbsp更换皮肤	
									</a>
								</li>
								<li class="divider"></li>
								<li>
									<a href="<%=path %>/user/logout.do">
										<i class="icon-off"></i>
										退出
									</a>
								</li>
							</ul>
						</li>
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
		</div>

		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>

				<div class="sidebar sidebar-fixed" id="sidebar" style="overflow: auto;height:600px;" >
					<!-- <script type="text/javascript">
						try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
					</script> -->

					
					
					<ul id="sideMenu"   class="nav nav-list">
					</ul><!-- /.nav-list -->

					<div class="sidebar-collapse" id="sidebar-collapse" onclick="changeMenuSize()">
						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>

					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					</script>
				</div>
			

				<div class="main-content">
					
					<div class="page-content" >
							<div class="row">
							      
							     <div class="col-xs-12"  style="clear:both;" id="content_frame">
								      <ul class="nav nav-tabs" id="tabs" style="width:10000px;" >
								         <li class="active"><a href="#Index"  data-toggle="tab">首页</a></li>
								      </ul>
								      <div class="tab-content">
								       <div  class="tab-pane active" id="Index">
								         <iframe src="<%=path %>/user/toMap.do" id="mainiframe" width="100%" height="650px" scrolling="no" frameborder="0"></iframe>
							            <%--  <iframe src="<%=path %>/jsp/baidumap.jsp" id="mainiframe" width="100%" height="580px" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>  --%>
								       </div>
								      </div>
								      
							      </div>
     						</div>
  
					</div>

					
				</div><!-- /.main-content -->

				<%-- <div class="ace-settings-container" id="ace-settings-container">
					<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
						<i class="icon-cog bigger-150"></i>
					</div>

					<div class="ace-settings-box" id="ace-settings-box">
						 <div>
							<div class="pull-left">
								<select id="skin-colorpicker" class="hide"  onchange="changeSkin()">
									<option data-skin="default" value="#438EB9" <c:if test="<%=skin %>=='.white'">selected</c:if>>#438EB9</option>
									<option data-skin="skin-1" value="#222A2D" <c:if test="<%=skin %>==''">selected</c:if>>#222A2D</option>
									
								</select>
							</div>
							<span>&nbsp; 选择皮肤</span>
						</div> 

					 	<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar"  checked="checked"  />
							<label class="lbl" for="ace-settings-navbar"> 固定导航条</label>
						</div>

						<div >
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" checked="checked"  />
							<label class="lbl" for="ace-settings-sidebar"> 固定滑动条</label>
						</div>

					    <div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" checked="checked" />
							<label class="lbl" for="ace-settings-breadcrumbs">固定面包屑</label>
						</div> 

							<!-- <div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" />
							<label class="lbl" for="ace-settings-rtl">切换到左边</label>
						</div> 

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
							<label class="lbl" for="ace-settings-add-container">
								切换窄屏
								<b></b>
							</label>
						</div>-->
					</div>
				</div> --%><!-- /#ace-settings-container -->
			</div><!-- /.main-container-inner -->
		</div><!-- /.main-container -->

			<!-- <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a> -->
		

		<!-- basic scripts -->

		[if !IE]>
<!-- 		<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
 --> <script src="<%=path%>/js/googleapi2.0.3.js"></script>
		<![endif]

		[if IE]>
<!-- 		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
 -->		<script src="<%=path%>/js/googleapi1.10.2.js"></script>
		<![endif]

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=path%>/js/jquery-2.0.3.min.js'>"+"<"+"script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='<%=path%>/js/jquery-1.10.2.min.js'>"+"<"+"script>");
</script>
<![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=path%>/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
		</script>
		<script src="<%=path%>/js/bootstrap.min.js"></script>
		<script src="<%=path%>/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<!--[if lte IE 8]>
		  <script src="<%=path%>/js/excanvas.min.js"></script>
		<![endif]-->

		<script src="<%=path%>/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="<%=path%>/js/jquery.ui.touch-punch.min.js"></script>
		<script src="<%=path%>/js/jquery.slimscroll.min.js"></script>
		<script src="<%=path%>/js/jquery.easy-pie-chart.min.js"></script>
		<script src="<%=path%>/js/jquery.sparkline.min.js"></script>
		<script src="<%=path%>/js/flot/jquery.flot.min.js"></script>
		<script src="<%=path%>/js/flot/jquery.flot.pie.min.js"></script>
		<script src="<%=path%>/js/flot/jquery.flot.resize.min.js"></script>

		<!-- ace scripts -->

		<script src="<%=path%>/js/ace-elements.min.js"></script>
		<script src="<%=path%>/js/ace.min.js"></script>
		<script src="<%=path%>/js/sidebar-menu.js"></script>
		<script src="<%=path%>/js/bootstrap-tab.js"></script>
		<script type="text/javascript"  src="<%=path%>/plugins/BootstrapMenu.min.js" ></script>
        <script type="text/javascript">
         jQuery(function($) {
        	/* var url="http://172.17.100.19:8090/dubbo.customer/user/loginHome.do";
        	$.post(url,function(result){
        		$('#sideMenu').sidebarMenu({
            	    data: result
            	   });
        	},'json') */
        	
        	
        		
        	$.ajax({
        		type: "POST",
        	    url: '<%=path%>/user/loginHome.do',
        	    dataType: 'json',
        	    crossDomain: true,
		          success:function(result){
		        	  $('#sideMenu').sidebarMenu({
		            	    data: result
		            	   });
		          }
        	})
        }) 
        
        function clickPW(){
        	 //alert("hhh");
        	 //layer_show('变更',"<%= request.getContextPath() %>/user/clickUserPW.do",'300')
        	 
        	 $.ajax({
          		type: "POST",
          	    url: '<%=path%>/user/clickUserPW.do?',
          	    dataType: 'json',
          	    success:function(result){
  		        	 if(result.flag){
  		        		 window.location.href="<%=path%>/user/logout.do";
  		        	 }
  		         }
          	}) 
          	
        }
         
         function changeSkin(){
        	 var sk = "<%=skin %>";
        	 if(sk==".white"){
        		 sk="#222A2D"; 
        	 }else{
        		 sk="#438EB9";
        	 }
        	 $.ajax({
         		type: "POST",
         		data:{skin:sk},
         	    url: '<%=path%>/user/changeUserSkin.do?',
         	    dataType: 'json',
         	    success:function(result){
 		        	 if(result.flag){
 		        		 window.location.href="<%=path%>/user/logout.do";
 		        	 }
 		         }
         	}) 
        }
        
        function changeMenuSize(){
        	var abc= document.getElementById("sidebar").offsetWidth;
        	 
           if(abc>100){
        	   document.getElementById("sidebar").removeAttribute("style");
           }else{
        	 var sidebar= document.getElementById("sidebar");
        	 sidebar.style.overflow="auto";
        	 sidebar.style.height="600px";
           }
        	
        }
         
         function loginOut(){
        	 window.location.href="<%=path%>/logout.do";
         }
        
		</script>
		<!-- inline scripts related to this page -->

		
		
		
	
</body>
</html>

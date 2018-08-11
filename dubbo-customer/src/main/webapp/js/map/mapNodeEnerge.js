/**
 * 地图维护js
 */

//初始化参数数据
var basUrl="";
var feedImgName = "map_feed";
var nodeImgName = "map_node";
var longitude;//默认地图中心经度
var latitude;//默认地图中心纬度
var defaultCity="";//默认城市名
var viewSize=15;//默认地图放大比例	

var feedPointData=[];//已存热源坐标点
var nodePointData=[];//已存换热站坐标点
var lineData=[];//已存线坐标点
//var overlays = [];
//全局变量
var map;//地图
var mywin;//新窗口
var newLine = null;//新增的临时管线对象
var pointMarkPool = [];//暂存所有点的MArk对象，修改坐标点信息是用于刷新（删旧绘制新的）
var curLine = null;//打开管线信息编辑窗口时暂存管线对象
//菜单对象（用于启用停用）
var menuItemPool = {
		map:[],//地图右键菜单
		point:[],//坐标点右键菜单
		line:[]//管线右键菜单
};

//初始化地图
function initMap(){

	/*map = new BMap.Map("mapdiv",{
		enableMapClick: false//不显示百度自带信息弹窗
	});    */// 创建Map实例
	map = new BMap.Map("mapdiv");   
	map.centerAndZoom(new BMap.Point(longitude,latitude), viewSize);  // 初始化地图,设置中心点坐标和地图级别
	//map.addControl(new BMap.MapTypeControl());   //添加地图类型控件(建筑图、城市图等)
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	map.disableDoubleClickZoom();//禁止双击放大缩小事件
	var overlays = [];
	var overlaycomplete = function(m){
        overlays.push(m.overlay);
        try{
        	 if(m.overlay.getPosition()!==null){
        	//alert("这是个点");
        	//alert(JSON.stringify(m.overlay.getPosition()));
        	drawPoint(m.overlay,basUrl,overlays);
        }
        }catch(e){
        	//alert("这是条线");
        	drawLine(m.overlay,basUrl,overlays);
        }

   //  alert(e.overlay.getPosition().lng); //经度
    // alert(e.overlay.getPosition().lat);  //纬度
   //     alert(e.overlay.getPath());//线的轨迹数组

    };
    var styleOptions = {
        strokeColor:"#006C76",    //边线颜色。#006C76
        fillColor:"",      //填充颜色。当参数为空时，圆形将没有填充效果。
        strokeWeight: 3,       //边线的宽度，以像素为单位。
        strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
        fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
        strokeStyle: 'solid' //边线的样式，solid或dashed。
    }
    //实例化鼠标绘制工具
    var drawingManager = new BMapLib.DrawingManager(map, {
        isOpen: false, //是否开启绘制模式
        enableDrawingTool: false, //是否显示工具栏
        drawingToolOptions: {
            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
            offset: new BMap.Size(5, 5), //偏离值
            drawingModes:[ BMAP_DRAWING_MARKER,BMAP_DRAWING_POLYLINE ]//绘图工具栏设置
        },

        polylineOptions: styleOptions, //线的样式

    });  
    //添加鼠标绘制工具监听事件，用于获取绘制结果
	   drawingManager.addEventListener('overlaycomplete', overlaycomplete);
	   
	
	   loadAllPoint();
	   loadAllLine();
	   
	 

}

function drawPoint(obj,basUrl,overlays){
		//alert("画点");
	var lng=obj.getPosition().lng;//经度
	var lat=obj.getPosition().lat;//纬度
	
//	obj.setLabel("new1");

	clearAll(overlays);

	var url=basUrl+"/mapMaintenance/toAddNode.do?lng="+lng+"&lat="+lat;
	layer_show('添加图标',url,'450','350');
	
	
	
	
	
}

function drawLine(obj,basUrl,overlays){
//	alert("画线");
	var pointsStr = "";
	var newLinePoints = obj.getPath();
	pointsStr +=newLinePoints[0].lng + "-" + newLinePoints[0].lat ;
	for(var i = 1;i < newLinePoints.length;i++){
		pointsStr += "," + newLinePoints[i].lng + "-" + newLinePoints[i].lat ;
	}
	
	var url=basUrl+"/mapMaintenance/addLine.do?pointsStr="+pointsStr;
	   $.ajax({ 
	         async: false,//要求同步 不是不需看你的需求
	         url : url,  
	         type : 'POST',  
	         data : pointsStr,  
	         processData : false,  //必须false才会避开jQuery对 formdata 的默认处理   
	         contentType : false,  //必须false才会自动加上正确的Content-Type 
	         success :function(data){
	        var result = eval("(" + data + ")");
	         if(result.status=="y"){
	         layer.msg(result.info, {icon: 1},function(){
	        	 clearAll(overlays);
	        	 drawLineGet(result.linedata[0]);  	
	        	//alert("画完了");
      			  });
	         }else{
	      	 layer.msg(result.info, {icon: 2},function(){
      			  });
	         }
	         } ,  
	         error :function(data){
	         layer.msg("保存失败", {icon: 2});
	         refresh();
	         }		         
	     });  
	
	
}

function clearAll(overlays) {
	//alert(overlays.length);
	for(var i = 0; i < overlays.length; i++){
        map.removeOverlay(overlays[i]);
    }
    overlays.length = 0   
}

function loadAllPoint(){
	for(var i = 0;i < feedPointData.length;i++){
		var imgUrl = basUrl + "/js/map/images/" + "map_feed.png";
		pointMarkPool.push(drawPointGet(feedPointData[i],imgUrl));		
	}		
	for(var i = 0;i < nodePointData.length;i++){
		var imgUrl = basUrl + "/js/map/images/" + "map_node.png";
		pointMarkPool.push(drawPointGet(nodePointData[i],imgUrl));	
	}

}
//初始化加载全部线
function loadAllLine(){
	for(var i = 0;i < lineData.length;i++){
		drawLineGet(lineData[i]);
	}
}


//负责具体绘制点
function drawPointGet(pointdata,imgurl){
	if(pointdata == undefined || pointdata == null || pointdata == 'null'){
		return;
	}
	var imgSize = getImgSize(imgurl);
	var icon = new BMap.Icon(imgurl,new BMap.Size(25,25),{anchor:new BMap.Size(imgSize.width/2,imgSize.height)});
	var mark = new BMap.Marker(new BMap.Point(pointdata.LONGITUDE,pointdata.LATITUDE),{'icon':icon});
	map.addOverlay(mark);//绘制点

	/* 	************>>			****/

	var opts = {
			  width : 100,     // 信息窗口宽度
			  height: 50,     // 信息窗口高度
			  title : "工况数据" , // 信息窗口标题
			  enableMessage:true,//设置允许信息窗发送短息
			  message:"mmm",
			  backgroundColor:"red"
				  }
	var point = new BMap.Point(pointdata.LONGITUDE,pointdata.LATITUDE);
	var str="<span style='color:red'>"+"温度"+"</span>"+":"+"<span style='color:red'>"+"39℃"+"</span>"+"<br/>"+
	"<span style='color:green'>"+"压力"+"</span>"+":"+"<span style='color:green'>"+"19kpa"+"</span>";
	var dd="<div id=\"ee\">"+mark+"</div>";
	var infoWindow = new BMap.InfoWindow(str, opts);  // 创建信息窗口对象 
	mark.addEventListener("click", function(){          
		map.openInfoWindow(infoWindow,point); //开启信息窗口

		/*   layer.tips("<span style='color:red'>"+"在上面"+"</span>", "#idd", {
			   tips: [1, "#006C76"],
			    time:31536000000
			 });*/

	});

	
	
	
	/* 	************<<			****/
	//创建右键菜单
	var menu=new BMap.ContextMenu();
	
	var menuitem = new BMap.MenuItem('删除',delPoint.bind(mark,pointdata.ID));
	menu.addItem(menuitem);				
	menuItemPool.point.push({id:pointdata.ID,mis:[menuitem]});
	mark.addContextMenu(menu);	
	
	//拖拽结束事件
	//mark.enableDragging()//点可拖拽
	mark.addEventListener("dragend", function(e){
		updatePoint(pointdata.ID,e.point.lng,e.point.lat);
	});
	//文字标注
	/*var label = new BMap.Label(pointdata.NAME,{offset:new BMap.Size(pointdata.NAME.length*-2,-20)});*/

	var label = new BMap.Label(pointdata.NAME,{offset:new BMap.Size(pointdata.NAME.length*-3,20)});
	label.setStyle({ color : "#C931DB", fontSize : "14px",backgroundColor:"transparent",border:"none"});
	mark.setLabel(label);
	//鼠标覆盖提示
	//mark.setTitle(pointdata.NAME);
	return {id:pointdata.ID,mk:mark};
}

function getImgSize(imgUrl){
	var size = {width:0,heigth:0};
	var img = new Image();
	img.src = imgUrl;
	size.width = img.width;
	size.height = img.height;
	return size;
}



//右键删除坐标点
function delPoint(pointId,e,ee,marker){
//	map.removeOverlay(marker);
	var url=basUrl+"/mapMaintenance/delPoint.do";
	$.ajax({
		url:url,
		type:"post",
		data:{'pointId':pointId},
		dataType:"json",
		success:function(data){
  		if(data.status=="y"){
  			 delPointOfPointMarkPool(pointId);
  			 layer.msg(data.info, {icon: 1});
			}else if(data.status=="n"){
			layer.msg(data.info, {icon: 2});
			}else{
				layer.msg(data.info, {icon: 2});
			}
		},
		error:function(data){
			alert("请求验证失败!");return false;
		}
	});
}


//绘制管线
function drawLineGet(lineData){
	// 管线坐标点数组
	var points = [];
	var pointData = lineData.POINTS.split(',');
	for(var n = 0;n < pointData.length;n++){
		points.push(new BMap.Point(pointData[n].split("-")[0],pointData[n].split("-")[1]));
	}	
	
	//管线方向箭头
	var polyline;
	if(lineData.SHOWSYMBOL == 1){
  	var sy = new BMap.Symbol(BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW, {
	    	scale: 0.5,//图标缩放大小
	    	strokeColor:'#fff',//设置矢量图标的线填充颜色
	    	strokeWeight: '1',//设置线宽
		});		
		//绘制管线
		polyline =new BMap.Polyline(points, {
		   icons:[new BMap.IconSequence(sy, '10', '30')],
		   strokeWeight:lineData.WEIGHT,//折线的宽度，以像素为单位
		   strokeOpacity:lineData.OPACITY,//折线的透明度，取值范围0 - 1
		   strokeColor:lineData.COLOR, //折线颜色
		   strokeStyle:lineData.STYLE//solid实线,dashed虚线
		});	
	}else{
		//绘制管线
		polyline =new BMap.Polyline(points, {
		   icons:[],
		   strokeWeight:lineData.WEIGHT,//折线的宽度，以像素为单位
		   strokeOpacity:lineData.OPACITY,//折线的透明度，取值范围0 - 1
		   strokeColor:lineData.COLOR, //折线颜色
		   strokeStyle:lineData.STYLE//solid实线,dashed虚线
		});					
	}

	map.addOverlay(polyline); 
/*	
	//鼠标覆盖提示
	var opts = {
	  width : 150,     // 信息窗口宽度
	  height: 80,     // 信息窗口高度
	  title : "管线信息" 
	};
	var message = lineData.LINEMESSAGE;
	if(message == undefined || message == null) message="";
	var infoWindow = new BMap.InfoWindow(message, opts);  // 创建信息窗口对象 					
	polyline.addEventListener("dblclick", function(e){          
		map.openInfoWindow(infoWindow,new BMap.Point(e.point.lng,e.point.lat)); //开启信息窗口
	});
*/
	//创建右键菜单
	var lineMenu=new BMap.ContextMenu();
	//var mi1 = new BMap.MenuItem('编辑管线信息',editLineInfo.bind(polyline,lineData.ID));
	//var mi2 = new BMap.MenuItem('开启位置编辑',editLinePosition.bind(polyline,lineData.ID,1));	
	//var mi3 = new BMap.MenuItem('关闭位置编辑',editLinePosition.bind(polyline,lineData.ID,0));	
	var mi4 = new BMap.MenuItem('删除管线',delLine.bind(polyline,lineData.ID));
	//lineMenu.addItem(mi1);
	//lineMenu.addItem(mi2);	
	//lineMenu.addItem(mi3);	
	lineMenu.addItem(mi4);
	//menuItemPool.line.push({id:lineData.ID,mis:[mi1,mi2,mi3,mi4]});
	menuItemPool.line.push({id:lineData.ID,mis:[mi4]});
	polyline.addContextMenu(lineMenu);	
//	mi3.disable();
}


//修改管线信息
function editLineInfo(lineId,e,ee,line){
	curLine = line;
   	var uname = document.getElementById("uname").value;
	var url = basUrl + "/system/MapMaintenanceAction!execUpdateOfLine.do?pageConfig.uname=" + uname + "&mapMaintenance.lineId="+lineId;
	mywin = $.ligerDialog.open({ title:"管线信息编辑",width:500,height: 280, url: url});
}


//拖拽管线位置
//type 0 关闭;1开启
function editLinePosition(lineId,type,e,ee,line){
	if(type == 1){  		
		if(menuSet(1,'',{id:'',index:[]})){
			menuSet(4,'line',{id:lineId,index:[2]});			
		}		
		line.enableEditing();				
	}
	if(type == 0){
		if(menuSet(0,'',{id:'',index:[]})){
     		menuSet(3,'map',{id:'',index:[3]});
			menuSet(3,'line',{id:'',index:[2]});			
		}
		line.disableEditing();	
		var points = line.getPath();
		var pointsStr = "";
		for(var i = 0;i < points.length;i++){
			pointsStr += "," + points[i].lng + "-" + points[i].lat ;
		}
		map.removeOverlay(line);//删除变更后的临时管线图若修改成功则重绘	
		
		var uname = document.getElementById("uname").value;
		
		$.ajax({
			url:basUrl + "/system/MapMaintenanceAction!execUpdateAjaxOfLine.do?pageConfig.uname=" + uname,
			type:"post",
			data:{'mapMaintenance.lineId':lineId,'mapMaintenance.points':pointsStr.substring(1)},
			dataType:"json",
			success:function(data){
	    		if(data.status=="y"){
	    			drawLine(data.linedata);
	    			window.parent.success(data.info);
				}else if(data.status=="n"){
					window.parent.failure(data.info);
				}else{
					window.parent.error();
				}
			},
			error:function(e){
				alert("请求验证失败!");
				return false;
			}
		});
	}
}


//拖拽管线位置
//type 0 关闭;1开启
function editLinePosition(lineId,type,e,ee,line){
	if(type == 1){  		
		if(menuSet(1,'',{id:'',index:[]})){
			menuSet(4,'line',{id:lineId,index:[2]});			
		}		
		line.enableEditing();				
	}
	if(type == 0){
		if(menuSet(0,'',{id:'',index:[]})){
     		menuSet(3,'map',{id:'',index:[3]});
			menuSet(3,'line',{id:'',index:[2]});			
		}
		line.disableEditing();	
		var points = line.getPath();
		var pointsStr = "";
		for(var i = 0;i < points.length;i++){
			pointsStr += "," + points[i].lng + "-" + points[i].lat ;
		}
		map.removeOverlay(line);//删除变更后的临时管线图若修改成功则重绘	
		
		var uname = document.getElementById("uname").value;
		
		$.ajax({
			url:basUrl + "/system/MapMaintenanceAction!execUpdateAjaxOfLine.do?pageConfig.uname=" + uname,
			type:"post",
			data:{'mapMaintenance.lineId':lineId,'mapMaintenance.points':pointsStr.substring(1)},
			dataType:"json",
			success:function(data){
	    		if(data.status=="y"){
	    			drawLine(data.linedata);
	    			window.parent.success(data.info);
				}else if(data.status=="n"){
					window.parent.failure(data.info);
				}else{
					window.parent.error();
				}
			},
			error:function(e){
				alert("请求验证失败!");
				return false;
			}
		});
	}
}




//删除管线
function delLine(lineId,e,ee,line){
	var url=basUrl+"/mapMaintenance/delLine.do";
	$.ajax({
		url:url,
		type:"post",
		data:{'lineId':lineId},
		dataType:"json",
		success:function(data){
  		if(data.status=="y"){
  			map.removeOverlay(line);
  			layer.msg(data.info, {icon: 1});
			}else if(data.status=="n"){
				layer.msg(data.info, {icon: 2});	
			}else{
				layer.msg(data.info, {icon: 2});
			}
		},
		error:function(data){
			alert("请求验证失败!");
			return false;
		}
	});
}

//新增坐标点成功回调绘制
function drawNewPoint(pointdata,imgName){
	//alert(JSON.stringify(pointdata));
	delPointOfPointMarkPool(pointdata.ID);
	var imgUrl = basUrl + "/js/map/images/" + imgName + ".png";
	pointMarkPool.push(drawPointGet(pointdata,imgUrl));
}

//刷新坐标点mark池子
function delPointOfPointMarkPool(id){
	for(var i=0;i<pointMarkPool.length;i++){
		if(id == pointMarkPool[i].id){
			map.removeOverlay(pointMarkPool[i].mk);
			pointMarkPool.splice(i,1);
			break;
		}	
	}
}















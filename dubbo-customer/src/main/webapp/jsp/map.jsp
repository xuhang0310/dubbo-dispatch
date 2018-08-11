<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();

String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    
    <title>百度地图</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!-- basic styles -->
		<link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=path%>/css/font-awesome.min.css" />
		
		<!-- Ionicons -->
        <link href="<%=path %>/css/ionicons.css" rel="stylesheet" type="text/css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="<%=path%>/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- fonts -->

<!-- 		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />
 -->
	   <script src="<%=path%>/js/googlefont.js"></script>
	

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=path%>/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="<%=path%>/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="<%=path%>/js/html5shiv.js"></script>
		<script src="<%=path%>/js/respond.min.js"></script>
		<![endif]-->
		
		<!-- 引入bootstrapTable开始 -->
    <script src="<%=path %>/js/bootstrap.min.js"></script>
    <link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="<%=path %>/plugins/bootstrap_table/bootstrap-table.css">
	<script src="<%=path %>/plugins/bootstrap_table/bootstrap-table.js"></script>
    <script src="<%=path %>/plugins/bootstrap_table/extensions/export/bootstrap-table-export.js"></script>
    <script src="<%=path %>/plugins/bootstrap_table/extensions/mobile/bootstrap-table-mobile.js"></script>
    <script src="<%=path %>/plugins/bootstrap_table/locale/bootstrap-table-zh-CN.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/echart/echarts.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/echart/map/china.js"></script>
	<script type="text/javascript" src="<%=path%>/plugins/echart/echartCustom.js"></script>
	<script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>

	<!-- 单独引入各省市的地图 -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/echart/map/beijing.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/echart/map/neimenggu.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/echart/map/tianjin.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/echart/map/hebei.js"></script>



    <link href="<%= request.getContextPath() %>/css/h-ui/H-ui.min.css" rel="stylesheet" type="text/css" />
 	<style>
			.home-box{
			    position:absolute;
				
			}
			
			.home-header{
			   position: relative;
			   background-color:rgba(27,34,78,0.5);
			   height:30px;
			   color:#fff; 
			   text-align:left;
			   line-height: 30px;
			}
			
			.home-left{
			    width:300px;
			    height:100%;
			    left:0px;
			    top:0px;
			   
			}
			.home-right{
			    width:300px;
			    right:10px;
			}
			.home-right-top{
			    height:280px;
			    top:0px;
			}
			
			.home-right-bottom{
			     height:280px;
			     top:288px;
			}
			
			.home-center{
			    margin-left:10px;
			    margin-right:10px;
			    height:100%;
			}
 	
 	
   </style>
  </head>
  
  <body style="margin:0px;">
  
  <div class="home-box home-center" id="home-center">
     
     <div id="allmap" style="height:100%;width:100%;"></div>
     <script type="text/javascript">
     $(function () {
    	 adjuest();
     });
     window.onresize = adjuest;
     function adjuest(){
    	 $("#home-center").width($(window).width()-60-0);
     	 $("#allmap").width($(window).width()-60-0);
     }
     </script>
  </div>
<!--    -->
  <div class="home-box home-right">
	  <div class="home-right-top">
	     <div class="home-header">
	                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实时负荷曲线
	              <select id="feedid" style="float:right;margin-top:6px;margin-right:6px;" onchange="changeFeedid(this.value)">
		             <c:forEach var="temp" items="${feedList }">
		             		 <option value="${temp.FEEDID }">${temp.FEEDNAME }</option>
		             </c:forEach>
	              </select>           
	              <div id="line" style="width:300px;height:250px;">
	              
	              </div>
	              <script type="text/javascript">
// 	              var id = 'line';
// 	            var xLineData=' [{"TIME":"17","TEMP":"66.0","FLOW":"17.0"},{"TIME":"18","TEMP":"66.0","FLOW":"18.0"},{"TIME":"19","TEMP":"66.0","FLOW":"19.0"},{"TIME":"20","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"21","TEMP":"66.0","FLOW":"8200.0"}, {"TIME":"22","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"23","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"0","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"1","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"2","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"3","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"4","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"5","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"6","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"7","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"8","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"9","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"10","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"11","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"12","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"13","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"14","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"15","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"16","TEMP":"66.0","FLOW":"8200.0"},{"TIME":"17","TEMP":"66.0","FLOW":"8200.0"}]';
	
	
// 	            var xLinejson='["17","18","19","20","21","22","23","0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17"]';
	           
// 	            searchLineChart(id,xLineData,xLinejson);
// 	            function searchLineChart(id,xData,xjson){
// 		            	var chart=new PrivateEchart();
// 		                chart.legendName=["供水温度","瞬时流量"];
// 		                chart.legendType=["line","line"];
// 		                chart.legendField=["TEMP","FLOW"];	
// 		                chart.yaxisPostion=["1","1"];
// 		                chart.grid="x:'10%',y:'5%',x2:'3%' ";
// 		                chart.targetField="TIME";
// 		                chart.toolbox=false;
// 		                var json=eval("(" + xjson + ")")
// 		                chart.xaisData=json;
// 		                var data = eval("("+ xData +")");
// 		                chart.chartData=data;
// 		                var option=chart.getLineBarChart();
// 		                renderChart("line", option,'dark');
// 	            	}
				changeFeedid($("#feedid").val());
				function changeFeedid(feedid){
					if(feedid!=null){
		            	$.ajax({
							  type: 'POST',
							  url: "<%=path%>/feedReal/getFeedRealChart.do",
							  data : {chartField:"SUPPLYTEMP,SUPPLYFLOW",chartTitle:"温度,流量",chartType:"line,line",chartPosition:"1,1",feedcode:feedid},
							  success: function(result){
							 // alert(JSON.stringify(result));
								  var chart=new PrivateEchart();
								    chart.legendName=result.title;
								    chart.legendType=result.type;
								    chart.legendField=result.column;	
								    chart.yaxisPostion=result.postion;
								    chart.targetField="SCADATIME";
								    chart.toolbox=true;
								    chart.xaisData=result.x;
								    chart.chartData=result.data;
								    var option=chart.getLineBarChart();
								    renderChart("line", option);
							  },
							  dataType: 'json'
							});
		            }
				}
	            
	              </script>
	             
	     </div>
	  </div>
	  
	  <div class="home-box home-right-bottom">
	     <div class="home-header">
	                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实时报警分析<span style="float:right;margin-right:6px;">更多信息</span>
	              
	            <table class="table table-striped table-bordered table-hover">
		            <thead>
		                <tr>
		                    <td>站点名称</td>
		                    <td>报警类型</td>
		                    <td><i class="icon-time bigger-110 hidden-480"></i>报警时间</td>
		                </tr>
		            </thead>
		            <tbody>
		                <tr>
		                    <td>东北郊电厂</td>
		                    <td>供温过高</td>
		                    <td>2018-06-27 14:25:00</td>
		                </tr>
		                <tr>
		                    <td>东北郊电厂</td>
		                    <td>供温过高</td>
		                    <td>2018-06-27 14:25:00</td>
		                </tr>
		                <tr>
		                    <td>东北郊电厂</td>
		                    <td>供温过高</td>
		                    <td>2018-06-27 14:25:00</td>
		                </tr>
		                <tr>
		                    <td>东北郊电厂</td>
		                    <td>供温过高</td>
		                    <td>2018-06-27 14:25:00</td>
		                </tr>
		                <tr>
		                    <td>东北郊电厂</td>
		                    <td>供温过高</td>
		                    <td>2018-06-27 14:25:00</td>
		                </tr>
		                <tr>
		                    <td>东北郊电厂</td>
		                    <td>供温过高</td>
		                    <td>2018-06-27 14:25:00</td>
		                </tr>
		                <tr>
		                    <td>东北郊电厂</td>
		                    <td>供温过高</td>
		                    <td>2018-06-27 14:25:00</td>
		                </tr>
		            </tbody>   
	            </table>
	     </div>
	  </div>
  </div>
  
  <div class="home-box home-left">
  		 <div class="home-header">
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;综合分析
                        <select style="float:right;margin-top:6px;margin-right:6px;">
                            <option value="1" selected>昨日</option>
                            <option value="2">7天</option>
                            <option value="3">30天</option>
                        </select>
                        <div style="color:#f5f5f5;font-size:14px;">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;华能上安电厂位于河北省会石家庄市井陉县上安镇境内，在太行东麓井陉口内，是我国首批利用外资建设的大型火力发电厂之一，规划装机容量为250万千瓦。一期工程于1986年开工。二期工程1995年开工，1997年相继投产。该厂现有装机容量130万千瓦，为华能国际电力股份有限公司全资拥有。
                        </div>
                        <div id="pie" style="width:300px;height:280px;">
                            		热量分析饼图
                        </div>
                        <script type="text/javascript">
                        
                        var myChart = echarts.init(document.getElementById('pie'),'dark');
                        option = {
                        		backgroundColor:'',
                        	    tooltip : {
                        	        trigger: 'item',
                        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                        	    },
                        	    legend: {
                        	        orient: 'horizontal',
                        	        left: 'center',
                        	        data: ['东北郊电厂','军电一期','盘山电厂','南疆电厂','杨柳青电厂']
                        	    },
                        	    series : [
                        	        {
                        	            name: '热量',
                        	            type: 'pie',
                        	            radius : '75%',
                        	            center: ['50%', '60%'],
                        	            data:[
                        	                {value:335, name:'东北郊电厂'},
                        	                {value:310, name:'军电一期'},
                        	                {value:234, name:'盘山电厂'},
                        	                {value:135, name:'南疆电厂'},
                        	                {value:1548, name:'杨柳青电厂'}
                        	            ],
                        	            labelLine: {
                        	                normal: {
                        	                    show: false
                        	                }
                        	            },
                        	            label: {
                        	                normal: {
                        	                    show: false,
                        	                    position: 'center'
                        	                }
                        	            },
                        	            itemStyle: {
                        	                emphasis: {
                        	                    shadowBlur: 10,
                        	                    shadowOffsetX: 0,
                        	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                        	                }
                        	            }
                        	        }
                        	    ]
                        	};
                        myChart.setOption(option);
                        </script>
         </div>
  </div>
  
  
  </body>
  
</html>
<script type="text/javascript">

function aa(h){
	$("#allmap").height(h);
}
$("#allmap").width($(window).width()-60-0);
var myChart = echarts.init(document.getElementById('allmap'),'dark');
var name_title = "北京市供热指标数据分布"
var subname = '博达股份'

var nameColor = "#57D5FD"
var name_fontFamily = '等线'
var subname_fontSize = 15	
var name_fontSize =32
//var mapName = 'china'
var mapName = '北京'
var data = [
    {name: "北京热力", value: 41.56},//朝阳区——北京热力
    {name:"新城热力", value: 39.75},//新城热力——通州区
    {name: "大龙热力", value: 40.89},  //大龙热力——顺义区     
            
            
            
    {name:"北京",value:39},
    {name:"天津",value:42.6},
    {name:"河北",value:40.93},
    {name:"山西",value:40.58},
    {name:"内蒙古",value:43.98},
    {name:"辽宁",value:44.8},
    {name:"吉林",value:43.9},
    {name:"黑龙江",value:44.2},
    {name:"上海",value:0},
    {name:"江苏",value:41.56},
    {name:"浙江",value:35.89},
    {name:"安徽",value:37.42},
    {name:"福建",value:0},
    {name:"江西",value:0},
    {name:"山东",value:38.76},
    {name:"河南",value:42.69},
    {name:"湖北",value:0},
    {name:"湖南",value:0},
    {name:"重庆",value:0},
    {name:"四川",value:0},
    {name:"贵州",value:0},
    {name:"云南",value:0},
    {name:"西藏",value:0},
    {name:"陕西",value:39.27},
    {name:"甘肃",value:40.65},
    {name:"青海",value:0},
    {name:"宁夏",value:41.61},
    {name:"新疆",value:43.63},
    {name:"广东",value:0},
    {name:"广西",value:0},
    {name:"海南",value:0},
    ];

var cityData = [{name: '赤峰市', value: 123},
            {name: '呼伦贝尔市', value: 39},
            {name: '包头市', value: 152},
            {name: '北京热力', value: 125},
            {name: '新城热力', value: 132},
            {name: '大龙热力', value: 145},
        ];
    
var geoCoordMap = {};
var geoCityMap = {};
var toolTipData = [ 
      
   {name:"大龙热力",value:[{name:"热指标",value:39}]},
                   
                   
   {name:"北京",value:[{name:"热指标",value:39}]},
   {name:"天津",value:[{name:"热指标",value:42.6}]},
   {name:"河北",value:[{name:"热指标",value:40.93}]},
   {name:"山西",value:[{name:"热指标",value:40.58}]},
   {name:"内蒙古",value:[{name:"热指标",value:43.98}]},
   {name:"辽宁",value:[{name:"热指标",value:44.8}]},
   {name:"吉林",value:[{name:"热指标",value:43.9}]},
   {name:"黑龙江",value:[{name:"热指标",value:44.2}]},
   {name:"江苏",value:[{name:"热指标",value:41.56}]},
   {name:"浙江",value:[{name:"热指标",value:35.89}]},
   {name:"安徽",value:[{name:"热指标",value:37.42}]},
   {name:"山东",value:[{name:"热指标",value:38.76}]},
   {name:"河南",value:[{name:"热指标",value:42.69}]},
   {name:"陕西",value:[{name:"热指标",value:39.27}]},
   {name:"甘肃",value:[{name:"热指标",value:40.65}]},
   {name:"宁夏",value:[{name:"热指标",value:41.61}]},
   {name:"新疆",value:[{name:"热指标",value:43.63}]},
 
];

/*获取地图数据*/
myChart.showLoading();
var mapFeatures = echarts.getMap(mapName).geoJson.features;
myChart.hideLoading();
mapFeatures.forEach(function(v) {
    // 地区名称
    var name = v.properties.name;
    // 地区经纬度
    geoCoordMap[name] = v.properties.cp;
	
});
var max = 480,
    min = 9; // todo 
var maxSize4Pin = 100,
    minSize4Pin = 20;

var convertData = function(data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap[data[i].name];
        if (geoCoord) {
            res.push({
                name: data[i].name,
                value: geoCoord.concat(data[i].value),
            });
        }
    }
    return res;
};
var convertDataCity = function(data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCityMap[data[i].name];
        if (geoCoord) {
            res.push({
                name: data[i].name,
                value: geoCoord.concat(data[i].value),
            });
        }
    }
    geoCityMap = {};
    return res;
};
option = {
    //backgroundColor: '#0F2813',//背景色
    backgroundColor: '',//背景色
    title: {
        text: name_title,
        
        x: 'center',
        textStyle: {
            color: nameColor,
            fontFamily: name_fontFamily,
            fontSize: name_fontSize
        }
    },
    grid: [
           {
               left: '10%',
               right: '10%',
               bottom: 200
           },
           {
               left: '10%',
               right: '10%',
               height: 80,
               bottom: 8000
           }
       ],
    tooltip: {
        trigger: 'item',
        formatter: function(params) {
            if (typeof(params.value)[2] == "undefined") {
                var toolTiphtml = ''
                for(var i = 0;i<toolTipData.length;i++){
                    if(params.name==toolTipData[i].name){
                        toolTiphtml += toolTipData[i].name+':<br>'
                        for(var j = 0;j<toolTipData[i].value.length;j++){
                            toolTiphtml+=toolTipData[i].value[j].name+':'+toolTipData[i].value[j].value+"<br>"
                        }
                    }
                }
                
                return toolTiphtml;
            } else {
                var toolTiphtml = ''
                for(var i = 0;i<toolTipData.length;i++){
                    if(params.name==toolTipData[i].name){
                        toolTiphtml += toolTipData[i].name+':<br>'
                        for(var j = 0;j<toolTipData[i].value.length;j++){
                            toolTiphtml+=toolTipData[i].value[j].name+':'+toolTipData[i].value[j].value+"<br>"
                        }
                    }
                }
             
                return toolTiphtml;
            }
        }
    },
    
    visualMap: {
    	  show: true,
          bootom:20,
          min: 36,
          max: 45,
          left: 290,
          top: 'bottom',
          text: ['高', '低'], // 文本，默认为数值文本
          calculable: true,
          seriesIndex: [1],
          inRange: {
           //color: ['#002256','#005596','#00375F','#2E8965'] 
           color: ['#002256','#00375F','#0175C8','#25ABF5'] 

        }
    },
   
  
    geo: {
        show: true,
        map: mapName,
        label: {
            normal: {
                show: false
            },
            emphasis: {
                show: false,
            }
        },
        roam: true,
        itemStyle: {
            normal: {
                areaColor: '#002750',//地图初始化 地图模块颜色
                borderColor: '#3B5077',
            },
            emphasis: {
                areaColor: '#2B91B7',
            }
        }
    },
    series: [{
            name: '散点',
            type: 'scatter',
            coordinateSystem: 'geo',
            data: convertData(data),
            data: convertData(data.sort(function(a, b) {
                return b.value - a.value;
            }).slice(18, 31)), 
            symbolSize: function(val) {
                return val[2] / 10+0.01;
            },
            label: {
                normal: {
                  formatter: '{b}',
                  //formatter: '{@[2]}',
                    position: 'left',
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            itemStyle: {
                normal: {
                    color: '#05C3F9'
                   // color: '#001345'
                }
            }
        },
        {
            type: 'map',
            map: mapName,
            geoIndex: 0,
            aspectScale: 0.75, //长宽比
            showLegendSymbol: false, // 存在legend时显示
            label: {
                normal: {
                    show: true
                },
                emphasis: {
                    show: false,
                    textStyle: {
                        color: '#fff'
                    }
                }
            },
            roam: true,
            itemStyle: {
                normal: {
                    areaColor: '#031525',
                    shadowColor: 'rgba(63, 218, 255, 0.34)',
                    borderColor: '#3B5077',
                },
                emphasis: {
                    areaColor: '#2B91B7'
                }
            },
            animation: false,
            data: data,
        },
        {
            name: '点',
            type: 'scatter',
            coordinateSystem: 'geo',
            symbol: 'pin', //气泡
            symbolSize: function(val) {
                var a = (maxSize4Pin - minSize4Pin) / (max - min);
                var b = minSize4Pin - a * min;
                b = maxSize4Pin - a * max;
                return a * val[2] + b + 15;
            },
            label: {
                normal: {
                	formatter: '{@[2]}',
                    show: true,
                    textStyle: {
                        color: '#fff',
                        fontSize: 9,
                    }
                }
            },
            itemStyle: {
                normal: {
                    color: '#F62157', //标志颜色
                }
            },
            zlevel: 6,
            data: convertData(data.sort(function(a, b) {
                return b.value - a.value;
            }).slice(0, 17)),
        },
        {
            name: 'Top 17',
            type: 'effectScatter',
            coordinateSystem: 'geo',
            data: convertData(data.sort(function(a, b) {
                return b.value - a.value;
            }).slice(0, 17)),
            symbolSize: function(val) {
                return val[2] / 10 + 8;//控制黄色环形图
            },
            showEffectOn: 'render',
            rippleEffect: {
                brushType: 'stroke'
            },
            hoverAnimation: true,
            label: {
                normal: {
                    formatter: '{b}',
                    position: 'right',
                    show: true
                }
            },
            itemStyle: {
                normal: {
                    color: 'yellow',
                    shadowBlur: 10,
                    shadowColor: 'yellow'
                }
            },
            zlevel: 1
        },

    ]
};
myChart.setOption(option);
window.onresize = myChart.resize;


//触发展示省级地图
myChart.on('click', function(params) {
	var city = params.name.trim();
	if(!city.localeCompare('河北')||!city.localeCompare('天津')||!city.localeCompare('北京')||!city.localeCompare('内蒙古')){
		
		showCity(city);
	}else{
		$('#showCity').css('z-index','-1');
	}
	
});
//展示城市
function showCity(city) {
	
	var cityMapFeatures = echarts.getMap(city).geoJson.features;
	//myChart.hideLoading();
	cityMapFeatures.forEach(function(v) {
	    // 地区名称
	    var name = v.properties.name;
	    // 地区经纬度
	    geoCityMap[name] = v.properties.cp;

	});
	var pie = echarts.init(document.getElementById('showCity'));
	pie.setOption(option1 = {
		//backgroundColor:'rgba(128, 128, 128, 0.54)',//透明背景
		backgroundColor:'#031525',//透明背景
		title: {
	        text: city,
	        subtext: subname,
	        x: 'center',
	        textStyle: {
	            color: nameColor,
	            fontFamily: name_fontFamily,
	            fontSize: name_fontSize
	        },
	        subtextStyle:{
	            fontSize:subname_fontSize,
	            fontFamily:name_fontFamily
	        }
	    },
	    graphic: [{
    		type: 'group',
    		left: pos.left,
	        top: pos.top - 4,
    		children: [{
    			type: 'line',
    			left: 0,
        		top: -20,
        		shape: {
                    x1: 0,
                    y1: 0,
                    x2: 60,
                    y2: 0
                },
        		style: {
        			stroke: style.lineColor,
        		}
			}, {
    			type: 'line',
    			left: 0,
        		top: 20,
		        shape: {
                    x1: 0,
                    y1: 0,
                    x2: 60,
                    y2: 0
                },
                style: {
                    stroke: style.lineColor,
                }
    		}]
    	},{
        	id: name[0],
        	type: 'group',
        	left: pos.left + 2,
        	top: pos.top,
        	children: [{
        		type: 'polyline',
        		left: 70,
        		top: -12,
        		shape: {
                    points: line
                },
        		style: {
                    stroke: 'transparent',
        			key: name[0]
        		},
        		onclick: function(){
        			var name = this.style.key;
	        		handleEvents.resetOption(chart, option, name);
	        	}
        	}, {
        		type: 'text',
        		left: 0,
                top: 'middle',
        		style: {
	        		text: '  中国  ',
	        		textAlign: 'center',
	        		fill: style.textColor,
	        		font: style.font
	        	},
	        	onclick: function(){
	        		$('#showCity').css('display','none');
	                $('#allmap').css('display','block');
	                myChart.setOption(option);
	        	}
        	}, {
                type: 'text',
                left: 0,
                top: 10,
                style: {
                    text: '   CHINA  ',
                    textAlign: 'center',
                    fill: style.textColor,
                    font: '12px "Microsoft YaHei", sans-serif',
                },
                onclick: function(){
                   $('#showCity').css('display','none');
                   $('#allmap').css('display','block');
                   myChart.setOption(option);
                }
            }]
        }],
		  geo: {
	        show: true,
	        map: city,
	      //视角缩放比例
	        zoom: 1,
	        //显示文本样式
	        label: {
	            normal: {
	                show: false,
	                textStyle: {
	                    color: '#fff'
	                }
	            },
	            emphasis: {
	                textStyle: {
	                    color: '#fff'
	                }
	            }
	        },
	        roam: true,
	        itemStyle: {
	        	//样式1
	        	normal: {
	                //          	color: '#ddd',
	                borderColor: 'rgba(147, 235, 248, 1)',
	                borderWidth: 1,
	                areaColor: {
	                    type: 'radial',
	                    x: 0.5,
	                    y: 0.5,
	                    r: 0.8,
	                    colorStops: [{
	                        offset: 0,
	                        color: 'rgba(175,238,238, 0)' // 0% 处的颜色
	                    }, {
	                        offset: 1,
	                        color: 'rgba(	47,79,79, .2)' // 100% 处的颜色
	                    }],
	                    globalCoord: false // 缺省为 false
	                },
	                shadowColor: 'rgba(128, 217, 248, 1)',
	                shadowOffsetX: -2,
	                shadowOffsetY: 2,
	                shadowBlur: 10
	            },
	            emphasis: {
	                areaColor: '#389BB7',
	                borderWidth: 0
	            }
	           
	        },
	    },  
		series : [
		 {
            name: 'Top 5',
            type: 'effectScatter',
            coordinateSystem: 'geo',
            data:convertDataCity(cityData.sort(function(a, b) {
                return b.value - a.value;
            })),
            symbolSize:function(val) {
            	//alert(val);
                return val[2] / 10 + 8;//控制黄色环形图
            },
            showEffectOn: 'render',
            rippleEffect: {
                brushType: 'stroke'
            },
            hoverAnimation: true,
            label: {
                normal: {
                    formatter: '{b}',
                    position: 'right',
                    show: true
                }
            },
            itemStyle: {
                normal: {
                    color: '#FED626',
                    shadowBlur: 10,
                    shadowColor: '#FED626'
                }
            } ,zlevel:2
        }
		 ]
	});
}

//showCity('上海');

//关闭城市地图页面
function closeWin(){
    if($('#showCity').css('z-index')!='2'){
		$('#showCity').css('display','none');
	}else{
		$('#showCity').css('z-index','3');
	}
	
}
//实现可拖动div
function Drag(o, e) {
	var e = window.event || e;
	var _x = e.offsetX || e.layerX;
	var _y = e.offsetY || e.layerY;
	o.style.filter = 'Alpha(opacity=70)';
	o.style.opacity = '0.7';
	document.onmousemove = function(e) {
		var e = window.event || e;
		o.style.left = e.clientX - _x + 'px';
		o.style.top = e.clientY - _y + 'px';
		o.style.cursor = "move";
	}
	document.onmouseup = function(e) {
		document.onmousemove = null;
		o.style.filter = o.style.opacity = '';
		o.style.cursor = "default";
	}
}


//公共
var style = {
	font : '20px "Microsoft YaHei", sans-serif',
	textColor : '#eee',
	lineColor : 'rgba(147, 235, 248, .8)'
};
var line = [[0, 0], [8, 11], [0, 22]];
var pos = {
	leftPlus : 115,
	leftCur : 150,
	left : 198,
	top : 50
};
</script>	
	
	



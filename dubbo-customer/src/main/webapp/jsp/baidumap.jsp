<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>首页导航</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="<%=path%>/plugins/echart/echarts.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/echart/map/china.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery-1.10.2.min.js"></script>

<!-- 单独引入各省市的地图 -->
<script type="text/javascript" src="<%=path%>/plugins/echart/map/beijing.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/echart/map/neimenggu.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/echart/map/tianjin.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/echart/map/hebei.js"></script>
<style type="text/css">
.showCity {
	display: none;
	z-index: 999;
	position: absolute;
	height: 100%;
	width: 100%;
	margin:0 auto;
		/* left: 340px;
		top: 120px;
		bootom:0; */
}
</style>
</head>

<body>
	<div onclick="closeWin()" id="allmap" style="position:absolute;z-index:000;height:100%;width:100%"></div>
	<div id="showCity"  class="showCity">
<!-- 	<div id="showCity"  onmousedown="Drag(this, event)" class="showCity"> -->
	
	</div>
	
</body>

</html>
<script type="text/javascript">
    var myChart = echarts.init(document.getElementById('allmap'));
    var name_title = "全国供热热指标数据分布"
	var subname = '博达股份'
	//var nameColor = " rgb(55, 75, 113)"
	var nameColor = "#57D5FD"
	var name_fontFamily = '等线'
	var subname_fontSize = 15	
	var name_fontSize =32
	var mapName = 'china'
	var data = [
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
                //{name: '远东仪表', value: 122},
                {name: '新城热力', value: 132},
                {name: '大龙热力', value: 145},
            ];
	    
	var geoCoordMap = {};
	var geoCityMap = {};
	var toolTipData = [ 
	   {name:"北京",value:[{name:"热指标",value:39}]},
	   {name:"天津",value:[{name:"热指标",value:42.6}]},
	   {name:"河北",value:[{name:"热指标",value:40.93}]},
	   {name:"山西",value:[{name:"热指标",value:40.58}]},
	   {name:"内蒙古",value:[{name:"热指标",value:43.98}]},
	   {name:"辽宁",value:[{name:"热指标",value:44.8}]},
	   {name:"吉林",value:[{name:"热指标",value:43.9}]},
	   {name:"黑龙江",value:[{name:"热指标",value:44.2}]},
	   //{name:"上海",value:[{name:"热指标",value:12}]},
	   {name:"江苏",value:[{name:"热指标",value:41.56}]},
	   {name:"浙江",value:[{name:"热指标",value:35.89}]},
	   {name:"安徽",value:[{name:"热指标",value:37.42}]},
	   //{name:"福建",value:[{name:"热指标",value:57}]},
	   //{name:"江西",value:[{name:"热指标",value:42}]},
	   {name:"山东",value:[{name:"热指标",value:38.76}]},
	   {name:"河南",value:[{name:"热指标",value:42.69}]},
	   //{name:"湖北",value:[{name:"热指标",value:56}]},
	   //{name:"湖南",value:[{name:"热指标",value:52}]},
	   //{name:"重庆",value:[{name:"热指标",value:44}]},
	   //{name:"四川",value:[{name:"热指标",value:60}]},
	   //{name:"贵州",value:[{name:"热指标",value:30}]},
	   //{name:"云南",value:[{name:"热指标",value:41}]},
	   //{name:"西藏",value:[{name:"热指标",value:4}]},
	   {name:"陕西",value:[{name:"热指标",value:39.27}]},
	   {name:"甘肃",value:[{name:"热指标",value:40.65}]},
	   //{name:"青海",value:[{name:"热指标",value:5}]},
	   {name:"宁夏",value:[{name:"热指标",value:41.61}]},
	   {name:"新疆",value:[{name:"热指标",value:43.63}]},
	   //{name:"广东",value:[{name:"热指标",value:60}]},
	   //{name:"广西",value:[{name:"热指标",value:30}]},
	   //{name:"海南",value:[{name:"热指标",value:6}]},
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
	    backgroundColor: '#001018',//背景色
	    title: {
	        text: name_title,
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
	                //console.log(toolTiphtml)
	                // console.log(convertData(data))
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
	              //  console.log(toolTiphtml)
	                // console.log(convertData(data))
	                return toolTiphtml;
	            }
	        }
	    },
	    // legend: {
	    //     orient: 'vertical',
	    //     y: 'bottom',
	    //     x: 'right',
	    //     data: ['credit_pm2.5'],
	    //     textStyle: {
	    //         color: '#fff'
	    //     }
	    // },
	    visualMap: {
	        show: true,
	        min: 36,
	        max: 45,
	        left: 'left',
	        top: 'bottom',
	        text: ['高', '低'], // 文本，默认为数值文本
	        calculable: true,
	        seriesIndex: [1],
	        inRange: {
	            // color: ['#3B5077', '#031525'] // 蓝黑
	            // color: ['#ffc0cb', '#800080'] // 红紫
	            //color: ['#3C3B3F', '#605C3C'] // 黑绿
	          // color: ['#0f0c29', '#302b63', '#24243e'] // 黑紫黑
	           //color: ['#23074d', '#cc5333'] // 紫红
	        // color: ['#00467F', '#A5CC82'] // 蓝绿
	         color: ['#002256','#005596','#00375F','#2E8965'] // 蓝绿 #00375F
	            // color: ['#1488CC', '#2B32B2'] // 浅蓝
	            // color: ['#00467F', '#A5CC82'] // 蓝绿

	        }
	    },
	    /*工具按钮组*/
	   /*   toolbox: {
	         show: true,
	         orient: 'vertical',
	    left: 'right',
	      top: 'center',
	      feature: {
	          dataView: {
	              readOnly: false
	          },
	          restore: {},
	          saveAsImage: {}
	      }
	  }, */
	  
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
	                areaColor: '#031525',
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
	
	
	//触发展示省级地图
	myChart.on('click', function(params) {
		var city = params.name.trim();
		if(!city.localeCompare('河北')||!city.localeCompare('天津')||!city.localeCompare('北京')||!city.localeCompare('内蒙古')){
			$('#showCity').css('z-index','2');
			$('#allmap').css('display','none');
			$('#showCity').css('display','block');
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
		                // shadowColor: 'rgba(255, 255, 255, 1)',
		                shadowOffsetX: -2,
		                shadowOffsetY: 2,
		                shadowBlur: 10
		            },
		            emphasis: {
		                areaColor: '#389BB7',
		                borderWidth: 0
		            }
		            //样式2
		           /*  normal: {
		                //areaColor: '#031525',
		                //areaColor: '#3FC5F9',
		                //borderColor: '#3B5077',
		            	   // areaColor: '#01345F',
		            	    areaColor: '#031525',
		            	    color: 'yellow',
		                    borderColor: '#3fdaff',
		                    borderWidth: 2,
		                    //shadowColor: 'rgba(63, 218, 255, 0.5)',
		                    shadowBlur: 30,
		                    show: true,
		            },
		            emphasis: {
		                areaColor: '#2B91B7',
		            } */
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


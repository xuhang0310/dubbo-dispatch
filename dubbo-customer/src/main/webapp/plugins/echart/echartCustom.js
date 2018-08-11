
function PrivateEchart(){
	/**Basic attributes**/
	this.title="";
	this.subtitle="";
	this.titlelink="";
	this.tooltip="item";
	this.grid="";
	this.calculable=true;
	this.toolbox=false;
	this.dataZoom=false;
	this.legendName=new Array();
	this.legendType=new Array();
	this.legendField=new Array();
	this.legendShow=true;
	this.yaxisPostion=new Array();
	this.selectedName=new Array();
	
	/**data**/
	this.chartData="";

	
	/**Line and Bar attribute**/
	this.targetField="";
	this.xaisData="";
	this.yaxisName="";
	this.yaxisUnit="";
	this.otherYaxisUnit="";
	this.otherYaxisName="";
	this.markPoint=new Array();
	this.markLine=new Array();
	
	/**pie chart attribute**/
	this.pieLegendOrient="";
	this.pieLegendPostion=""
    this.tooltipFormatter=false;
	this.pieRadius="55%";
	this.pieCenter=['50%', '45%'];
	this.pieName="data sources";
	
	/**Dashboard**/
	this.max=100;
	this.min=0;
	this.name="";
	this.data=0;
	this.unit="";
	
	
	/**stack**/
	this.stack="";
	this.stackArray=new Array();
}

PrivateEchart.prototype.getGaugueChart=function(){
	var series="title : {text: '"+this.title+"' ,x:'center' }, series : [{min:"+this.min+",max:"+this.max+",type:'gauge',axisLine: {lineStyle: {width: 20 }} ,detail : {formatter:'{value}'},data:[{value: "+this.data+", name: '"+this.name+"'}] }]";
	
	return "{"+series+"}";
}

PrivateEchart.prototype.getLineBarChart=function(){
	
	var title=this.getTitleInfo();
	var grid = this.getGrid();
	var tooltip=this.getTooltip();
	var calculable=this.getCalculable();
	var legend=this.getLegend();
	var xaxis=this.getXaxis();
	var yaxis=this.getYaxis();
	var series=this.getSeries();
	var toolbox=this.getToolBox();
	return "{ backgroundColor:'',"+title+grid+toolbox+tooltip+calculable+legend+xaxis+yaxis+this.getDataZoom()+series+"}";
}

PrivateEchart.prototype.getScatterChart=function(){
	
	var title=this.getTitleInfo();
	var grid = this.getGrid();
	var tooltip=this.getTooltip();
	var calculable=this.getCalculable();
	var legend=this.getLegend();
	var xaxis=this.getXaxis();
	var yaxis=this.getYaxis();
	var series=this.getScatterSeries();
	var toolbox=this.getToolBox();
	return "{"+title+grid+toolbox+tooltip+calculable+legend+" xAxis : [{ type : 'value',scale:true}] ,yAxis : [{ type : 'value',scale:true}], "+series+"}";
}

PrivateEchart.prototype.getPieChart=function(){
	var title=this.getTitleInfo();
	var grid = this.getGrid();
	var tooltip=this.getTooltip();
	var calculable=this.getCalculable();
	var legend=this.getLegend();
	var toolbox=this.getToolBox();
	var series=this.getPieSeries();
	return "{"+title+grid+toolbox+tooltip+calculable+legend+series+"}";
}

//Encapsulating header information (封装标题信息)
PrivateEchart.prototype.getTitleInfo=function(){
	var text="title:{";
	if(this.title!=""){
		text+="text:'"+this.title+"',x:'left'";
	}
	if(this.subtitle!=""){
		text+=",subtext:'"+this.subtitle+"'";
	}
	if(this.titlelink!=""){
		text+=",link:'"+this.titlelink+"'";
	}
	return text+"},";
}
PrivateEchart.prototype.getTooltip=function(){
	var formatter="";
	if(this.tooltipFormatter){
		formatter+=" ,formatter: \"{a} <br/>{b} : {c} ({d}%)\" ";
	}
	/*return "tooltip: {  trigger: '"+this.tooltip+"'  "+formatter+" }, ";*/
	
	///return " tooltip:{ trigger: 'axis' }, ";
	//return "  grid:{containLabel: true,bottom:35,}, tooltip:{ trigger: 'axis' }, ";
	return "  tooltip:{ trigger: 'axis' }, ";
}
//设置grid
PrivateEchart.prototype.getGrid=function(){
	return "grid:{"+this.grid +" },";
}

PrivateEchart.prototype.getLegend=function(){
	var legendArr=this.legendName;
	var data="";
	for(var i=0;i<legendArr.length;i++){
		data+="{ name:'"+legendArr[i]+"', textStyle:{fontSize:'40px'}}";
		if(i<legendArr.length-1){
			data+=",";
		}
	}
	var selectedArr=this.selectedName;
	var selected="";
	for(var i=0;i<selectedArr.length;i++){
		selected+=" "+selectedArr[i]+":false ";
		if(i<legendArr.length-1){
			selected+=",";
		}
	}
	if(selected!=""){
		selected=" selected: { "+selected+" } ,"
	}
	
	var otherJson="";
	if(this.pieLegendOrient!=""){
		otherJson+=",orient : '"+this.pieLegendOrient+"'";
	}
	if(this.pieLegendPostion!=""){
		otherJson+=",x : '"+this.pieLegendPostion+"' ,y:'bottom',";
	}
	
	if(this.selectedName.length>0){
		
	}
	return "legend: {y:'bottom',show: "+this.legendShow+","+selected+" data:["+data+"] "+otherJson+" }, ";
}

PrivateEchart.prototype.getCalculable=function(){
	return "calculable: "+this.calculable+",";
}

PrivateEchart.prototype.getToolBox=function(){
	return "toolbox: {show : "+this.toolbox+",feature : {  restore : {show: true},mark : {show: true}, magicType : {show: true, type: ['line', 'bar']},dataZoom : {show: true},dataView : {show: true, readOnly: false},saveAsImage : {show: true}}},";
}

PrivateEchart.prototype.getDataZoom=function(){
	if(this.dataZoom){
		return "dataZoom: [{type: 'inside',start: 0,end: 100}, {start: 0,end: 100,handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',handleSize: '80%',handleStyle: { color: '#fff', shadowBlur: 3,shadowColor: 'rgba(0, 0, 0, 0.6)', shadowOffsetX: 2, shadowOffsetY: 2 } }],";
	}
	return "";
}

PrivateEchart.prototype.getXaxis=function(){
	var data="";
	
	var num=(this.xaisData).length;
    $.each(this.xaisData, function(i,obj){
    	data+="'"+obj+"'";
    	if(i<num-1){
    		data+=",";
    	}
    })
	return "xAxis : [{type : 'category',data : ["+data+"]  } ], ";
}

PrivateEchart.prototype.getYaxis=function(){
	var jsonOther="";
	var otherYaxis="";
	if(this.yaxisName!=""){
		jsonOther+=",name : '"+this.yaxisName+"'";
	}
	if(this.yaxisUnit!=""){
		jsonOther+=",min: function(value){return value.min;},position:'left',axisLabel:{formatter:'{value}"+this.yaxisUnit+"'}";
	}else{
		jsonOther+=", position: 'right',axisLabel :{  formatter: '{value}'}";
	}
	if(this.otherYaxisName!=""){
		otherYaxis+=",name : '"+this.otherYaxisName+"'";
	}
	if(this.otherYaxisUnit!=""){
		otherYaxis+=",axisLabel :{  formatter: '{value}"+this.otherYaxisUnit+"'}";
	}else{
		otherYaxis+=",axisLabel :{  formatter: '{value}'}";
	}
	 
	
	return "yAxis : [{scale: true,    splitLine: { show: false }, type : 'value'  "+jsonOther+"}, {scale: true,type : 'value' "+otherYaxis+" } ], ";
}

/*PrivateEchart.prototype.getPieSeries=function(){
	var data=" ,backgroundColor: '#fafafa',data:[{value:335, name:'���Ƿֹ�˾'}, {value:310, name:'���Ƿֹ�˾'},{value:234, name:'��һ�ֹ�˾'},{value:135, name:'����ֹ�˾'},{value:1548, name:'��̨�ֹ�˾'}]";
	var series="series :[{name:'"+this.pieName+"',type:'pie',radius:'"+this.pieRadius+"',center: ['"+this.pieCenter[0]+"', '"+this.pieCenter[1]+"'] "+data+"}]";
	
	return series;
	
}*/

PrivateEchart.prototype.getPieSeries=function(){
	var dataJson=this.chartData;
	var data=" ,data:"+dataJson;
	
	var series="series :[{name:'"+this.pieName+"',type:'pie',radius:'"+this.pieRadius+"',center: ['"+this.pieCenter[0]+"', '"+this.pieCenter[1]+"'] "+data+"}]";
	
	return series;
	
}


PrivateEchart.prototype.getScatterSeries=function(){
	var fieldArray=this.legendField;
	var nameArray=this.legendName;
	var typeArray=this.legendType;
	var dataJson=this.chartData;
	
	var series="";
	for(var i=0;i<nameArray.length;i++){
		var field=fieldArray[i];
		var name=nameArray[i];
		var type=typeArray[i];
		var data="";
		$.each(dataJson, function(iy,result){
			  data+="["+result[field]+"],";
		})
		data=data.substr(0, data.length-1);
		var line="";
		if(this.markLine.length>0&&this.markLine[i]=="true"){
			line = " ,markLine : { "+
			"                data : [ "+
			"                    {type : 'average', name: 'average value'} "+
			"                ] "+
			"            } ";
		}
		var point="";
		if(this.markPoint.length>0&&this.markPoint[i]=="true"){
			point=  " ,markPoint : { "+
			"                data : [ "+
			"                    {type : 'max', name: 'max value'}, "+
			"                    {type : 'min', name: 'min value'} "+
			"                ] "+
			"            } ";
		}
		series+="{name:'"+name+"',type:'"+type+"', data:["+data+"] "+point+line+" }";
		
		if(i<nameArray.length-1){
	    	series+=",";
		}
		
	}
	return "series :["+series+"]";
}

PrivateEchart.prototype.getSeries=function(){
	var stackStr=this.stackArray.join(",");
	var typeArray=this.legendType;
	var fieldArray=this.legendField;
	var nameArray=this.legendName;
	var targetField=this.targetField;
	var dataJson=this.chartData;
	var xAsia=this.xaisData;
	
	var series="";
	for(var i=0;i<nameArray.length;i++){
		var field=fieldArray[i];
		var name=nameArray[i];
		var type=typeArray[i];
		var data="";
		$.each(xAsia, function(ix,obj){
			var xdata=obj;
			$.each(dataJson, function(iy,result){
				   //debugger;
					var targetNum=result[targetField];
					var yNum=result[field];
					if(yNum==undefined){
						yNum=0;
					}
					if(xdata==targetNum){
						data+=yNum+",";
					}
				
				
			})
		})
		data=data.substr(0, data.length-1);
		
		var yAxisIndex="";
		
		if(this.yaxisPostion[i]=="1"){
			yAxisIndex+=",yAxisIndex: 1";
		}
		
	    //yAxisIndex+=",yAxisIndex: "+this.yaxisPostion[i];
		var line="";
		if(this.markLine.length>0&&this.markLine[i]=="true"){
			line = " ,markLine : { "+
			"                data : [ "+
			"                    {type : 'average', name: 'average value'} "+
			"                ] "+
			"            } ";
		}
		var point="";
		if(this.markPoint.length>0&&this.markPoint[i]=="true"){
			point=  " ,markPoint : { "+
			"                data : [ "+
			"                    {type : 'max', name: 'max value'}, "+
			"                    {type : 'min', name: 'min value'} "+
			"                ] "+
			"            } ";
		}
		
		var stackAppend="";
		if(stackStr.indexOf(name)!=-1){
			stackAppend="stack: '"+this.stack+"',";
		}
		
		
		series+="{name:'"+name+"',symbol:'none',smooth:true, connectNulls: true,type:'"+type+"',"+stackAppend+" data:["+data+"] "+yAxisIndex+point+line+" }";
		
		if(i<nameArray.length-1){
	    	series+=",";
		}
	}
	return "series :["+series+"]";
}

//Foreground graphic rendering interface, locat path information option must be string.
function renderChart(id,option){
	var skins='macarons';
	if(typeof getSkins != 'undefined' && getSkins instanceof Function){
		skins=getSkins();
	}else{
		skins='dark';
	}
	var myChart = echarts.init(document.getElementById(id), skins);
    myChart.clear();
    myChart.setOption(eval("(" + option + ")"));
}
function renderChartJson(id,option){
		//alert(id);
	   
        var myChart = echarts.init(document.getElementById(id), 'macarons');
        myChart.clear();
        myChart.setOption(option);

}

function renderChartJson(id,option,style){
	//alert(id);
    var myChart = echarts.init(document.getElementById(id), style);
    myChart.clear();
    myChart.setOption(option);

}

function GaugueChart(){
	this.leftMax=0;
	this.leftMin=0;
	this.leftName="";
	this.leftSplitNumber=0;
	this.leftValue=0;
	this.leftDataName="";
	
	this.rightMax=0;
	this.rightMin=0;
	this.rightName="";
	this.rightSplitNumber=0;
	this.rightValue=0;
	this.rightDataName="";
	
	this.centerMax=0;
	this.centerMin=0;
	this.centerName="";
	this.centerSplitNumber=0;
	this.centerValue=0;
	this.centerDataName="";
	
	this.title="";
}

GaugueChart.prototype.getGaugueOption=function(){
	var option="{ "+
	"    	    tooltip : { "+
	"    	        formatter: '{a} <br/>{c} {b}' "+
	"    	    },title : { text: '"+this.title+"', x:'center'}, "+
	"    	    series : [ "+
	"    	        { "+
	"    	            name:'"+this.centerName+"', "+
	"    	            type:'gauge', "+
	"    	            z: 3, "+
	"    	            min:0, "+
	"    	            max:"+this.centerMax+", "+
	"    	            splitNumber:"+this.centerSplitNumber+", "+
	"    	            axisLine: {             "+
	"    	                lineStyle: {        "+
	"    	                    width: 10 "+
	"    	                } "+
	"    	            }, "+
	"    	            axisTick: {             "+
	"    	                length :15,         "+
	"    	                lineStyle: {        "+
	"    	                    color: 'auto' "+
	"    	                } "+
	"    	            }, "+
	"    	            splitLine: {            "+
	"    	                length :20,          "+
	"    	                lineStyle: {        "+
	"    	                    color: 'auto' "+
	"    	                } "+
	"    	            }, "+
	"    	            title : { "+
	"    	                textStyle: {        "+
	"    	                    fontWeight: 'bolder', "+
	
	"    	                    fontStyle: 'italic' "+
	"    	                } "+
	"    	            }, "+
	"    	            detail : { "+
	"    	                textStyle: {        "+
	"    	                    fontWeight: 'bolder' "+
	"    	                } "+
	"    	            }, "+
	"    	            data:[{value: "+this.centerValue+", name: '"+this.centerDataName+"'}] "+
	"    	        }, "+
	"    	        { "+
	"    	            name:'"+this.leftName+"', "+
	"    	            type:'gauge', "+
	"    	            center : ['20%', '55%'],     "+
	"    	            radius : '50%', "+
	"    	            min:0, "+
	"    	            max:"+this.leftMax+", "+
	"    	            endAngle:45, "+
	"    	            splitNumber:"+this.leftSplitNumber+", "+
	"    	            axisLine: {             "+
	"    	                lineStyle: {        "+
	"    	                    width: 8 "+
	"    	                } "+
	"    	            }, "+
	"    	            axisTick: {             "+
	"    	                length :12,         "+
	"    	                lineStyle: {        "+
	"    	                    color: 'auto' "+
	"    	                } "+
	"    	            }, "+
	"    	            splitLine: {            "+
	"    	                length :20,          "+
	"    	                lineStyle: {        "+
	"    	                    color: 'auto' "+
	"    	                } "+
	"    	            }, "+
	"    	            pointer: { "+
	"    	                width:5 "+
	"    	            }, "+
	"    	            title : { "+
	"    	                offsetCenter: [0, '-30%']        "+
	"    	            }, "+
	"    	            detail : { "+
	"    	                textStyle: {        "+
	"    	                    fontWeight: 'bolder' "+
	"    	                } "+
	"    	            }, "+
	"    	            data:[{value: "+this.leftValue+", name: '"+this.leftDataName+"'}] "+
	"    	        }, "+
	"    	        { "+
	"    	            name:'"+this.rightName+"', "+
	"    	            type:'gauge', "+
	"    	            center : ['75%', '55%'],     "+
	"    	            radius : '50%', "+
	"    	            min:0, "+
	"    	            max:"+this.rightMax+", "+
	"                   startAngle:120, "+
	"    	            endAngle:-45, "+
	"    	            splitNumber:"+this.rightSplitNumber+", "+
	"    	            axisLine: {             "+
	"    	                lineStyle: {        "+
	"    	                    color: [[0.2, '#228b22'],[0.8, '#48b'],[1, '#ff4500']],  "+
	"    	                    width: 8 "+
	"    	                } "+
	"    	            }, "+
	"    	            axisTick: {             "+
	"    	                splitNumber:5, "+
	"    	                length :10,         "+
	"    	                lineStyle: {        "+
	"    	                    color: 'auto' "+
	"    	                } "+
	"    	            }, "+
	"    	            splitLine: {           "+
	"    	                length :15,          "+
	"    	                lineStyle: {        "+
	"    	                    color: 'auto' "+
	"    	                } "+
	"    	            }, "+
	"    	            pointer: { "+
	"    	                width:2 "+
	"    	            }, "+
	"    	            title : { "+
	"    	                offsetCenter: [10, '-30%']        "+
	"    	            }, "+
	"    	            detail : { "+
	"    	                textStyle: {        "+
	"    	                    fontWeight: 'bolder' "+
	"    	                } "+
	"    	            }, "+
	"    	            data:[{value: "+this.rightValue+", name: '"+this.rightDataName+"'}] "+
	"    	        } "+
	"    	    ] "+
	"    	} ";
	
	return option;
}




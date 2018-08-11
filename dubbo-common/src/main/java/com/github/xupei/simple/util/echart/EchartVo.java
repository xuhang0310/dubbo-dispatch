package com.github.xupei.simple.util.echart;

import java.util.List;

import net.sf.json.JSONObject;

public class EchartVo {
	
/*	public static JSONObject createLineColumnEchartData(List dataList,String []  column,String [] legendName ,String [] legendType ,String [] key ,String  targetField){*/
	
	private List y1Data;//左边y轴初始查询数据
	private List y2Data;//右边y轴初始查询数据
	
	
	//private String title;//标题
	
	//private String subtitle;//副标题
	
	private List  chartData;//图表数据
	
	private List  xaisData;//图表x轴刻度信息
	
	private String [] legendField;//图表所展示数据的属性名
	
	//private String [] legendName;//图例名称信息
	
	private String [] legendType;//图表样式展示类型 
	
	private String targetField;//横坐标数值属性名
	
/*	private String yaxisName;//图表左边y轴标题名称
	
	private String yaxisUnit;//图表左边y轴单位名称
	
	private String otherYaxisName;//图表右边y轴标题名称
	
	private String otherYaxisUnit;//图表右边y轴单位名称
*/	
	private boolean toolbox = true;//是否显示工具箱   默认显示
	
	public List getY1Data() {
		return y1Data;
	}

	public void setY1Data(List y1Data) {
		this.y1Data = y1Data;
	}

	public List getY2Data() {
		return y2Data;
	}

	public void setY2Data(List y2Data) {
		this.y2Data = y2Data;
	}

	/*public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}*/

	public List getChartData() {
		return chartData;
	}

	public void setChartData(List chartData) {
		this.chartData = chartData;
	}

	public List getXaisData() {
		return xaisData;
	}

	public void setXaisData(List xaisData) {
		this.xaisData = xaisData;
	}

	public String[] getLegendField() {
		return legendField;
	}

	public void setLegendField(String[] legendField) {
		this.legendField = legendField;
	}

/*	public String[] getLegendName() {
		return legendName;
	}

	public void setLegendName(String[] legendName) {
		this.legendName = legendName;
	}*/

	public String[] getLegendType() {
		return legendType;
	}

	public void setLegendType(String[] legendType) {
		this.legendType = legendType;
	}
	
	public String getTargetField() {
		return targetField;
	}

	public void setTargetField(String targetField) {
		this.targetField = targetField;
	}

//	public String getYaxisName() {
//		return yaxisName;
//	}
//
//	public void setYaxisName(String yaxisName) {
//		this.yaxisName = yaxisName;
//	}
//
//	public String getYaxisUnit() {
//		return yaxisUnit;
//	}
//
//	public void setYaxisUnit(String yaxisUnit) {
//		this.yaxisUnit = yaxisUnit;
//	}
//
//	public String getOtherYaxisName() {
//		return otherYaxisName;
//	}
//
//	public void setOtherYaxisName(String otherYaxisName) {
//		this.otherYaxisName = otherYaxisName;
//	}
//
//	public String getOtherYaxisUnit() {
//		return otherYaxisUnit;
//	}
//
//	public void setOtherYaxisUnit(String otherYaxisUnit) {
//		this.otherYaxisUnit = otherYaxisUnit;
//	}

	public boolean isToolbox() {
		return toolbox;
	}

	public void setToolbox(boolean toolbox) {
		this.toolbox = toolbox;
	}
}

package com.github.xupei.simple.energy.node;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.xupei.dubbo.api.energy.INodeEnergyService;
import com.github.xupei.dubbo.api.util.export.ByteArrayOutputStreamExe;
import com.github.xupei.dubbo.api.util.export.ExportExcelService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.util.DateChosenUtil;
import com.github.xupei.simple.util.echart.EchartUtil;

@Controller
@RequestMapping(value="/nodeEnergy")
public class NodeEnergyController extends BaseController{
	
	@Autowired
	INodeEnergyService nodeEnergyService;
	
	@Autowired
	ExportExcelService exportExcelService;
	
	@RequestMapping(value="/nodeEnergyLayer.do")
	public ModelAndView feednodeindexLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		List sysorg=null;
		List node=null;
		try {
			sysorg=nodeEnergyService.getOrg();
			node=nodeEnergyService.getNode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/energy/node/nodeEnergyList");
		model.addObject("sysorg", sysorg);
		model.addObject("node", node);
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		model.addObject("paramsMap",super.getEchartConfig(paramsMap));
		
		return model;
	}
	
	@RequestMapping("/getNodeEnergyList.do")
	public void getNodeEnergyList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		//{rows=20, page=1, nodecode=2742, chartTitle=, chartField=, chartType=, chartPosition=, orgid=12, nodename=2742, tablename=7}
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		Map map=nodeEnergyService.getEnergyList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
		
	}
	
	
	
	@RequestMapping("/getNodeEnergyChart.do")
	public void getNodeEnergyChart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){

		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		try {
			List list=nodeEnergyService.getNodeEnergyChart(paramsMap);
			if(paramsMap.get("chartField")==null||"".equals(paramsMap.get("chartField"))){
				paramsMap.put("chartField", "SUMHEATQUANTITY,RDH");
				paramsMap.put("chartTitle", "热量,热单耗");
				paramsMap.put("chartType", "line,line");
				paramsMap.put("chartPosition", "0,1");
			}
			JSONObject jsonNode=EchartUtil.createLineColumnEchartData(list, paramsMap, "SCADATIME",null);
			PrintWriter out =null;
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.write(jsonNode.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	

	@RequestMapping("/getNodeEnergyPieChart.do")
	public void getNodeEnergyPieChart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){

		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		JSONArray jsonArray = new JSONArray();
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=utf-8");
			out=response.getWriter();
			List list=nodeEnergyService.getNodeEnergyPieChart(paramsMap);
			//String NodePointData=JSONArray.fromObject(list).toString();
			jsonArray=JSONArray.fromObject(list);
			out.write(jsonArray.toString());			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
	
	}
	
	
	@RequestMapping(value="/energyGradient.do")
	public ModelAndView energyGradient(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws IOException{
	      List graList = null;
	      try {
	    	  graList=nodeEnergyService.getGraList();
	    
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }

	      ModelAndView ParamMap = new ModelAndView("/jsp/energy/node/eidtGradient");
	     ParamMap.addObject("graList", graList.get(0));
	      return ParamMap;
	  }
	
	
	@RequestMapping(value="/updateGradient.do")
	public void updateMeter(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			
			nodeEnergyService.updateGradient(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	
	
	
	
	
	@RequestMapping(value="/getProjectList.do")
	public void getProjectList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String orgid=paramsMap.get("orgid");
			List projectList=nodeEnergyService.getProjectList(orgid);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw =  response.getWriter();
			StringBuffer sb = new StringBuffer();
			if(projectList == null || projectList.size() == 0){
				//sb.append("<option value=\"\">暂无项目</option>");			
			}else{
				for(Object obj : projectList){
					Map<String, String> b = (Map<String, String>) obj;
					Object o = b.get("NODEID");
					sb.append("<option value=\""+o.toString()+"\"     >"+b.get("NODENAME")+"</option>");
				}
			}
			//[RESULT, TITLE]
			pw.print(sb.toString());
			pw.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

	@RequestMapping(value="/getProjectListFeed.do")
	public void getProjectListFeed(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String orgid=paramsMap.get("orgid");
			List projectList=nodeEnergyService.getProjectListFeed(orgid);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw =  response.getWriter();
			StringBuffer sb = new StringBuffer();
			if(projectList == null || projectList.size() == 0){
				//sb.append("<option value=\"\">暂无项目</option>");			
			}else{
				for(Object obj : projectList){
					Map<String, String> b = (Map<String, String>) obj;
					Object o = b.get("FEEDID");
					sb.append("<option value=\""+o.toString()+"\"     >"+b.get("FEEDNAME")+"</option>");
				}
			}
			//[RESULT, TITLE]
			pw.print(sb.toString());
			pw.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@RequestMapping("/getNodeSummaryEnergyList.do")
	public void getNodeSummaryEnergyList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		Map map=nodeEnergyService.getSummaryMap(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	
	@RequestMapping(value = "/exportExcel.do",method = RequestMethod.GET)
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws Exception {
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		
		List titleList = super.getTableGridTitle(paramsMap);
		String sql = nodeEnergyService.getSql(paramsMap);
		String FileName = "换热站能耗分析";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=nodeEnergy.xls");
		
		ByteArrayOutputStreamExe os = new ByteArrayOutputStreamExe();
		os = exportExcelService.getFileStream(sql, titleList, FileName,paramsMap);
		try {
			//输出文件
			response.getOutputStream().write(os.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//能耗评估
	@RequestMapping(value="/showAssess.do")
	public ModelAndView showAssess(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		List sysorg=null;
		List node=null;
		try {
			sysorg=nodeEnergyService.getOrg();
			node=nodeEnergyService.getNode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView model=new ModelAndView();
		///dubbo-customer/src/main/webapp/jsp/testchart.jsp
		model.setViewName("/jsp/energy/node/assess_List");		
		model.addObject("sysorg", sysorg);
		model.addObject("node", node);
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		model.addObject("paramsMap",super.getEchartConfig(paramsMap));
		
		return model;
	}
	
	
	
	@RequestMapping("/getAssessList.do")
	public void getAssessList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=nodeEnergyService.getAssessList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	@RequestMapping("/getChart.do")
	public void getFeedAnalysisChart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	//	paramsMap=DateChosenUtil.returnDateType(paramsMap);
		try {
			List list=nodeEnergyService.getChart(paramsMap);
			
			String [] primaryField=null;
			paramsMap.put("chartTitle", "能耗分析");
			JSONObject jsonNode=EchartUtil.createLineColumnEchartData(list, paramsMap, "NOTE",primaryField);
			PrintWriter out =null;
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.write(jsonNode.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	

}

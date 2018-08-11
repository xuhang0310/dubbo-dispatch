package com.github.xupei.simple.energy.feed;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.xupei.dubbo.api.energy.IFeedEnergyService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.util.DateChosenUtil;
import com.github.xupei.simple.util.echart.EchartUtil;



@Controller
@RequestMapping(value="/feedEnergy")
public class FeedEnergyController extends BaseController{
	
	@Autowired
	IFeedEnergyService feedEnergyService;
	
	@RequestMapping(value="/feedEnergyLayer.do")
	public ModelAndView feednodeindexLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/energy/feed/feedEnergyList");

		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		
		try {
			model.addObject("feedList", feedEnergyService.getFeedList(paramsMap));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}
	
	@RequestMapping("/getFeedEnergyList.do")
	public void getFeedEnergyList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		Map map=feedEnergyService.getEnergyList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	@RequestMapping("/getFeedSummaryEnergyList.do")
	public void getFeedSummaryEnergyList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		Map map=feedEnergyService.getSummaryMap(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	@RequestMapping("/getFeedEnergyChart.do")
	public void getFeedEnergyChart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		try {
			List list=feedEnergyService.getEnergyChart(paramsMap);
			if(paramsMap.get("chartField")==null||"".equals(paramsMap.get("chartField"))){
				paramsMap.put("chartField", "SUMHEATQUANTITY,RDH");
				paramsMap.put("chartTitle", "热量,热单耗");
				paramsMap.put("chartType", "bar,bar");
				paramsMap.put("chartPosition", "0,1");
			}
			JSONObject jsonNode=EchartUtil.createLineColumnEchartData(list, paramsMap, "FEEDNAME",null);
			PrintWriter out =null;
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.write(jsonNode.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@RequestMapping("/getFeedEnergyPieChart.do")
	public void getFeedEnergyPieChart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){

		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		JSONArray jsonArray = new JSONArray();
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=utf-8");
			out=response.getWriter();
			List list=feedEnergyService.getFeedEnergyPieChart(paramsMap);
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
	    	  graList=feedEnergyService.getGraList();
	    
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
	 
	      ModelAndView ParamMap = new ModelAndView("/jsp/energy/feed/eidtFeedGradient");
	     ParamMap.addObject("graList", graList.get(0));
	      return ParamMap;
	  }
	
	
	
	@RequestMapping(value="/updateGradient.do")
	public void updateMeter(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			
			feedEnergyService.updateGradient(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	
	
	
	
	
	
	
	
	
	

}

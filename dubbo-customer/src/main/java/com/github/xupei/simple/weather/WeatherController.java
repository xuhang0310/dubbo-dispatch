package com.github.xupei.simple.weather;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



import com.github.xupei.dubbo.api.weather.IWeatherService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.util.DateChosenUtil;
import com.github.xupei.simple.util.echart.EchartUtil;
/**
 * 天气预报
 * @author liujiefeng
 *
 */

@Controller
@RequestMapping(value="/weather")
public class WeatherController extends BaseController {
	
	@Autowired
	IWeatherService weatherService;
	private HttpServletResponse response;	
	
	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}



	@RequestMapping(value="/weatherWeekLayer.do")
	public ModelAndView weatherWeekLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		ModelAndView model=new ModelAndView();
		try {
			List ybList=weatherService.getYbList();
			List list=weatherService.getRealList();
			List realList=weatherService.getRealOne();
			model.setViewName("/jsp/weather/weatherForecat");	
			model.addObject("ybList", ybList);
			model.addObject("list", list.get(0));
			model.addObject("realList", realList.get(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}
	

	@RequestMapping(value="/weatherHourLayer.do")
	public ModelAndView weatherHourLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		List paramDate=null;
		
		ModelAndView model=new ModelAndView();
		try {
			paramDate=weatherService.getParamDate();
			model.setViewName("/jsp/weather/weatherHourList");
			model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
			model.addObject("paramDate", paramDate.get(0));
			model.addObject("paramsMap",super.getEchartConfig(paramsMap));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		return model;
	}
	

	@RequestMapping("/getWeatherHourList.do")
	public void getWeatherHourList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=weatherService.getWeatherHourList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}

	
	@RequestMapping("/getChart.do")
	public void getFeedAnalysisChart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	//	paramsMap=DateChosenUtil.returnDateType(paramsMap);
		try {
			List list=weatherService.getChart(paramsMap);
			
			String [] primaryField=null;
			paramsMap.put("chartTitle", "气象小时温度(℃)");
			JSONObject jsonNode=EchartUtil.createLineColumnEchartData(list, paramsMap, "READDATE",primaryField);
			PrintWriter out =null;
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.write(jsonNode.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/readWeatherLayer.do")
	public ModelAndView readWeatherLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		ModelAndView model=new ModelAndView();
		List province=null;
		List cityList=null;
		List city=null;
		try {
			province=weatherService.getProvince();
			cityList=weatherService.getCityList();
			city=weatherService.getCity();
			model.setViewName("/jsp/weather/readWeather");	
			model.addObject("province", province);
			model.addObject("cityList", cityList);
			model.addObject("city", city.get(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}
	    

	
	@RequestMapping(value="/readWeatherHour.do")
	public ModelAndView readWeatherHour(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		ModelAndView model=new ModelAndView();
		List weatherConfig=null;
		HashMap configMap=null;
		List weatherList=null;
		try {
			weatherConfig=weatherService.getWeatherConfig();
			configMap=(HashMap) weatherConfig.get(0);
			weatherList=weatherService.getWeatherForHour(configMap.get("CITYCODE").toString());
			weatherService.UpdateTempMetrical(weatherList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}
	    
	
	@RequestMapping(value="/readWeatherWeek.do")
	public ModelAndView readWeatherWeek(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		ModelAndView model=new ModelAndView();
		List weatherConfig=null;
		HashMap configMap=null;
		List weatherListforWeek=null;
		try {
			weatherConfig=weatherService.getWeatherConfig();
			configMap=(HashMap) weatherConfig.get(0);
			weatherListforWeek=weatherService.getWeatherForWeek(configMap.get("IP").toString(),
					configMap.get("DAYS").toString(),configMap.get("APPKEY").toString(),configMap.get("SIGN").toString());
			weatherService.UpdateTempForeWeek(weatherListforWeek);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}
	
	
	

	@RequestMapping(value="/updateWeatherCity.do")
	public void updateWeatherCity(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			
			weatherService.updateWeatherCity(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	
	
	@RequestMapping(value="/getCityProjectList.do")
	public void getCityProjectList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			List projectList=weatherService.getProjectList(paramsMap);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw =  response.getWriter();
			StringBuffer sb = new StringBuffer();
			if(projectList == null || projectList.size() == 0){
				sb.append("<option value=\"\">暂无项目</option>");
			}else{
				for(Object obj : projectList)
				{
					Map<String, String> b = (Map<String, String>) obj;
					Object o = b.get("CITYCODE");
					sb.append("<option value=\""+o.toString()+"\">"+b.get("CITY")+"</option>");
				}
			}
			//[RESULT, TITLE]
			pw.print(sb.toString());
			pw.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}

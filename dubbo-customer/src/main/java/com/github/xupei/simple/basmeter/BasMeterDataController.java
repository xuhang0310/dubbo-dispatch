package com.github.xupei.simple.basmeter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.github.xupei.dubbo.api.IBasMeterDataService;

import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
/**
 * 采暖期参数配置
 * @author liujiefeng
 *
 */

@Controller
@RequestMapping(value="/basmeterdata")
public class BasMeterDataController extends BaseController {
	
	@Autowired
	IBasMeterDataService basMeterDataService;
	private HttpServletResponse response;
	
	
	public HttpServletResponse getResponse() {
		return response;
	}





	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}





	@RequestMapping("/getBasDataMeterList.do")
	public void getBasDataMeterList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=basMeterDataService.getBasMeterList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}

	
	@RequestMapping(value="/addDataMeter.do",method=RequestMethod.POST)
	public void addDataMeter(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		//{sj=2018-05-14, 2018-05-14=}
		try {
			String sj=paramsMap.get("sj");
			List basmater=basMeterDataService.getBasmers();
			//basMeterDataService.saveMeterNum(paramsMap);
			String id=basmater.toString().replace("{", "").replace("}", "").replace("[", "").replace("]", "").replace("ID=", "");
			String[]ids=id.split(",");
			int count=0;
			for (int i = 0; i < ids.length; i++) {
				count=basMeterDataService.getCount(sj,ids[i]);			
				if(count==0){
					basMeterDataService.insertNum(sj,ids[i]);
				}
			}
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	
	
	
	@RequestMapping(value="/saveMeterNum.do",method=RequestMethod.POST)
	public void saveMeterNum(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			basMeterDataService.saveMeterNum(paramsMap);
			
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	@RequestMapping(value="/basMeterDataLayer.do")
	public ModelAndView basMeterDataLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/basmeterdata/basMerterDataList");	
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		
		
		return model;
	}
	
	@RequestMapping(value="/deleteMeter.do")
	public void deleteMeter(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("id");
			basMeterDataService.deleteMeter(id);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="/selectAll.do")
	public ModelAndView selectAll(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      List list = null;
	 /*     try {
	    	//  list = basMeterDataService.selectAll();
	    //	  System.out.println(list.size());
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      }
	     */
	      ModelAndView ParamMap = new ModelAndView("/jsp/basmeter/addBasMeter");
	      //将数据存入modelMap
	      ParamMap.addObject("bas", list);
	
	      return ParamMap;
	  }

	@RequestMapping(value="/getProjectList.do")
	public void getProjectList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("stationtype");
		//	basMeterDataService.deleteParam(id);
			List projectList=basMeterDataService.getProjectList(id);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw =  response.getWriter();
			StringBuffer sb = new StringBuffer();
			if(projectList == null || projectList.size() == 0)
				sb.append("<option value=\"\">暂无项目</option>");
			
			else
			{
				for(Object obj : projectList)
				{
					Map<String, String> b = (Map<String, String>) obj;
					Object o = b.get("ID");
					sb.append("<option value=\""+o.toString()+"\"     >"+b.get("NAME")+"</option>");
				}
			}
			//[RESULT, TITLE]
			pw.print(sb.toString());
		//	pw.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@ResponseBody
	@RequestMapping(value="/findMeter.do")
	public ModelAndView findMeter(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws IOException{
		  request.setCharacterEncoding("utf-8");
		  response.setContentType("text/html;charset=utf-8");
	      String id = paramsMap.get("id");
	      String stationtype=paramsMap.get("stationtype");
	      List list = null;
	      List listcode=null;    	    
	      try {
	    	  list = basMeterDataService.findMeter(id);
	    	  listcode=basMeterDataService.getlx(stationtype);
	    
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
	  
	      ModelAndView ParamMap = new ModelAndView("/jsp/basmeter/editBasMeter");
	      //将数据存入modelMap
	      ParamMap.addObject("basmeter", list.get(0));
	      ParamMap.addObject("listcode",listcode);
	      return ParamMap;
	  }
	
	    

	@RequestMapping(value="/updateMeter.do")
	public void updateMeter(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("id");
			basMeterDataService.editMeter(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	


}

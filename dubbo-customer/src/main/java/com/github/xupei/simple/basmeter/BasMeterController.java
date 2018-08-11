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


import com.github.xupei.dubbo.api.IBasMeterService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
/**
 * 计量设备管理
 * @author liujiefeng
 *
 */

@Controller
@RequestMapping(value="/basmeter")
public class BasMeterController extends BaseController {
	
	@Autowired
	IBasMeterService basMeterService;
	private HttpServletResponse response;
	
	
	public HttpServletResponse getResponse() {
		return response;
	}





	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}





	@RequestMapping("/getBasMeterList.do")
	public void getBasMeterList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=basMeterService.getBasMeterList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}


	
	
	
	@RequestMapping(value="/saveMeter.do",method=RequestMethod.POST)
	public void saveMeter(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			basMeterService.saveMeter(paramsMap);
			
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	@RequestMapping(value="/basMeterLayer.do")
	public ModelAndView basMeterLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	
		ModelAndView model=new ModelAndView();
	//	List 
		model.setViewName("/jsp/basmeter/basMerterList");	
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		
		
		return model;
	}
	
	@RequestMapping(value="/deleteMeter.do")
	public void deleteMeter(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("id");
			basMeterService.deleteMeter(id);
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
	    //  List list = null;
	 /*     try {
	    	//  list = basMeterService.selectAll();
	    //	  System.out.println(list.size());
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      }
	     */
	      ModelAndView ParamMap = new ModelAndView("/jsp/basmeter/editBasMeter");
	      //将数据存入modelMap
	      //ParamMap.addObject("bas", list);
	
	      return ParamMap;
	  }

	@RequestMapping(value="/getProjectList.do")
	public void getProjectList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("stationtype");
		//	basMeterService.deleteParam(id);
			List projectList=basMeterService.getProjectList(id);
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
			pw.close();
		
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
	    	  list = basMeterService.findMeter(id);
	    	  listcode=basMeterService.getlx(stationtype);
	    
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
			basMeterService.editMeter(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	


}

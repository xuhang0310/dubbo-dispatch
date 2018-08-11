package com.github.xupei.simple.feednodeindex;

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


import com.github.xupei.dubbo.api.IFeednodeindexService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
/**
 * 指标管理
 * @author liujiefeng
 *
 */

@Controller
@RequestMapping(value="/feednodeindex")
public class FeednodeindexController extends BaseController {
	
	@Autowired
	IFeednodeindexService feedNodeIndexService;
	
	@RequestMapping("/getFeedNodeIndexList.do")
	public void getFeedNodeIndexList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=feedNodeIndexService.getFeedNodeIndexList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}

	@RequestMapping("/loginSucess.do")
	public ModelAndView loginSucess(){
		ModelAndView model=new ModelAndView();
				model.setViewName("/jsp/menu");
		return model;
		
	}
	
	
	
	@RequestMapping(value="/saveBase.do",method=RequestMethod.POST)
	public void saveBase(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			feedNodeIndexService.saveBase(paramsMap);
			
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	@RequestMapping(value="/feednodeindexLayer.do")
	public ModelAndView feednodeindexLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		try {
			List zb=feedNodeIndexService.findZbList();
			model.setViewName("/jsp/feednodeindex/feednodeindexList");
			model.addObject("zb",zb);
			model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return model;
	}
	
	@RequestMapping(value="/deleteZb.do")
	public void deleteZb(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("id");
			feedNodeIndexService.deleteZb(id);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/addZb.do")
	public ModelAndView addZb(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	//      String id = paramsMap.get("id");
	      List list = null;
	    
	   
	      try {
	    	  list = feedNodeIndexService.findZbList();
	    	
	    	  System.out.println(list.size());
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
	      
	      ModelAndView zbmap = new ModelAndView("/jsp/feednodeindex/editFeednodeindex");
	      //将数据存入modelMap
	      zbmap.addObject("zblx", list);
	      return zbmap;
	  }
	
	
	
	@ResponseBody
	@RequestMapping(value="/findZb.do")
	public ModelAndView findZb(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      String id = paramsMap.get("id");
	      String stationtype=paramsMap.get("stationtype");
	      List list = null;
	      List listzb=null;
	      List lx=null;
	      //Map map=new HashMap<>();
	      try {
	    	  list = feedNodeIndexService.findZb(id);
	    //	  list2=feedNodeIndexService.getlx();
	    	  listzb = feedNodeIndexService.findZbList(); 
	    	  lx=feedNodeIndexService.getlx(stationtype);
	    	  System.out.println(list.size());
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
	      
	      ModelAndView zbmap = new ModelAndView("/jsp/feednodeindex/editFeednodeindex");
	      //将数据存入modelMap
	      zbmap.addObject("zb", list.get(0));
	      zbmap.addObject("zblx",listzb);
	      zbmap.addObject("lx",lx);
	      return zbmap;
	  }
	
	    

	@RequestMapping(value="/updateZb.do")
	public void updateZb(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("id");
			feedNodeIndexService.editZb(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	


}

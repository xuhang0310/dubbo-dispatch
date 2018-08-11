package com.github.xupei.simple.dataparam;

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


import com.github.xupei.dubbo.api.IDataParamService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
/**
 * 采暖期参数配置
 * @author liujiefeng
 *
 */

@Controller
@RequestMapping(value="/dataparam")
public class DataParamController extends BaseController {
	
	@Autowired
	IDataParamService dataParamService;
	
	@RequestMapping("/getDataParamList.do")
	public void getDataParamList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=dataParamService.getDataParamList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}

	@RequestMapping("/loginSucess.do")
	public ModelAndView loginSucess(){
		ModelAndView model=new ModelAndView();
				model.setViewName("/jsp/menu");
		return model;
		
	}
	
	
	
	@RequestMapping(value="/saveParam.do",method=RequestMethod.POST)
	public void saveParam(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			dataParamService.saveParam(paramsMap);
			
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	@RequestMapping(value="/dataParamLayer.do")
	public ModelAndView feednodeindexLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/dataparam/dataParamList");

		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		
		
		return model;
	}
	
	@RequestMapping(value="/deleteParam.do")
	public void deleteParam(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("id");
			dataParamService.deleteParam(id);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}
		
	}
	

	@ResponseBody
	@RequestMapping(value="/findParam.do")
	public ModelAndView findParam(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      String id = paramsMap.get("id");
	      List list = null;
	     // List list2=null;    
	      try {
	    	  list = dataParamService.findParam(id);
	    	//  list2=dataParamService.getlx();
	    	  System.out.println(list.size());
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
	  
	      ModelAndView ParamMap = new ModelAndView("/jsp/dataparam/editDataParam");
	      //将数据存入modelMap
	      ParamMap.addObject("dataparam", list.get(0));
	//      zbmap.addObject("lx",list2);
	      return ParamMap;
	  }
	
	    

	@RequestMapping(value="/updateParam.do")
	public void updateParam(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("id");
			dataParamService.editParam(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	


}

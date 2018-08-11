package com.github.xupei.simple.sys;

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


import com.github.xupei.dubbo.api.ISysConfigService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
/**
 * 采暖期参数配置
 * @author liujiefeng
 *
 */

@Controller
@RequestMapping(value="/sysconfig")
public class SysConfigController extends BaseController {
	
	@Autowired
	ISysConfigService sysConfigService;
	
	@RequestMapping("/getSysConfigList.do")
	public void getSysConfigList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=sysConfigService.getSysConfigList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}

	@RequestMapping("/getSysMenuTree.do")
	public void getSysMenuTree(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			List list=sysConfigService.getMenuListForTree(paramsMap);
			JsonUtil.returnListJson(list, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@RequestMapping(value="/savePage.do",method=RequestMethod.POST)
	public void savePage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			sysConfigService.savePage(paramsMap);
			
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	@RequestMapping(value="/sysConfigLayer.do")
	public ModelAndView sysConfigLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	
		ModelAndView model=new ModelAndView();
	//	
		
		model.setViewName("/jsp/sys/config/sysConfigList");	
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		
		
		return model;
	}
	
	@RequestMapping(value="/deletePage.do")
	public void deletePage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("id");
			sysConfigService.deletePage(id);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="/selectPage.do")
	public ModelAndView selectPage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap)throws IOException{
		
		 List page=null;
		  request.setCharacterEncoding("utf-8");
		  response.setContentType("text/html;charset=utf-8");
		  try {
			
			 page=sysConfigService.findPage(paramsMap.get("id"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  ModelAndView ParamMap = new ModelAndView("/jsp/sys/config/editSysConfig");
		  ParamMap.addObject("page",page.get(0));
	      return ParamMap;
	  }



	@RequestMapping(value="/toEditPage.do")
	public ModelAndView toEditPage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws IOException{

	      ModelAndView ParamMap = new ModelAndView("/jsp/sys/config/editSysConfig");
	      return ParamMap;
	  }
	
	    

	@RequestMapping(value="/updatePage.do")
	public void updatePage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			sysConfigService.editPage(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	


}

/**
 * Copyright (C), 2009-2012, 北京博达云起科技发展有限公司.<BR>
 * ProjectName:ECC<BR>
 * File name:  ${objectName}Controller.java     <BR>
 * Author: ${author}   <BR>
 * Project:ECC    <BR>
 * Version: v 1.0      <BR>
 * Date:   ${nowDate}  <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 
package com.simple.dispatch.${packageName}.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.simple.dispatch.base.BaseController;
import org.springframework.web.servlet.ModelAndView;

import com.simple.dispatch.${packageName}.service.${objectName}Service;


/**
 * 功能描述：  .  <BR>
 * 历史版本: <Br>
 * 开发者: ${author}  <BR>
 * 时间：    ${nowDate} <BR>
 * 变更原因：    <BR>
 * 变化内容 ：<BR>
 * 首次开发时间：  ${nowDate}  <BR>
 * 描述：   <BR>
 * 版本：V1.0
 */
@Controller
@RequestMapping(value="/${objectNameLower}")
public class ${objectName}Controller extends BaseController{
	@Autowired
	private ${objectName}Service  ${objectNameLower}Service;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 
	
	/**
	 * 
	 * 方法说明： . <BR>
	 * @see com.simple.dispatch.${packageName}.controller.${objectName}Controller <BR> <BR>
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 * @return: ModelAndView
	 * @Author:  ${author}  <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	@RequestMapping("/${objectNameLower}Layer.do")
	public ModelAndView ${objectName}Layer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/${packageName}/${objectNameLower}/${objectNameLower}_list");
		
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		
		
		return model;
		
	}
	/**
	 * 
	 * 方法说明： . <BR>
	 * @see com.simple.dispatch.${packageName}.controller.${objectName}Controller <BR> <BR>
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 * @return: ModelAndView
	 * @Author:  ${author}  <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	
	@RequestMapping("/get${objectName}List.do")
	public void get${objectName}List(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			Map map=${objectNameLower}Service.get${objectName}MapList(paramsMap);
			super.returnObjectJson(map, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
				logger.error(e.getMessage());
				super.returnnBaseJson(false, e.getMessage(), response);
		}
	}
	
	/**
	 * 
	 * 方法说明： . <BR>
	 * @see com.simple.dispatch.${packageName}.controller.${objectName}Controller <BR> <BR>
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 * @return: ModelAndView
	 * @Author:  ${author}  <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	@RequestMapping("/add${objectName}Layer.do")
	public ModelAndView add${objectName}Layer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/${packageName}/${objectNameLower}/edit${objectName}");
		
		return model;
	}
	
	
	/**
	 * 
	 * 方法说明： . <BR>
	 * @see com.simple.dispatch.${packageName}.controller.${objectName}Controller <BR> <BR>
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 * @return: ModelAndView
	 * @Author:  ${author}  <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	@RequestMapping("/edit${objectName}Layer.do")
	public ModelAndView edit${objectName}Layer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/${packageName}/${objectNameLower}/edit${objectName}");
		try {
			model.addObject("paramsMap",paramsMap);
			model.addObject("obj",${objectNameLower}Service.get${objectName}MapById(paramsMap));
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
				logger.error(e.getMessage());
				super.returnnBaseJson(false, e.getMessage(), response);
		}
		return model;
	}
	
	/**
	 * 
	 * 方法说明： . <BR>
	 * @see com.simple.dispatch.${packageName}.controller.${objectName}Controller <BR> <BR>
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 * @return: ModelAndView
	 * @Author:  ${author}  <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	@RequestMapping("/save${objectName}.do")
	public void save${objectName}(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			${objectNameLower}Service.save${objectName}(paramsMap);
			} catch (Exception e) {
			// TODO Auto-generated catch block
				logger.error(e.getMessage());
				super.returnnBaseJson(false, e.getMessage(), response);
			}
		super.returnnBaseJson(true, "新增成功", response);
	}
	
	/**
	 * 
	 * 方法说明： . <BR>
	 * @see com.simple.dispatch.${packageName}.controller.${objectName}Controller <BR> <BR>
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 * @return: ModelAndView
	 * @Author:  ${author}  <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	@RequestMapping("/update${objectName}ById.do")
	public void update${objectName}ById(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			${objectNameLower}Service.update${objectName}(paramsMap);
			} catch (Exception e) {
			// TODO Auto-generated catch block
				logger.error(e.getMessage());
				super.returnnBaseJson(false, e.getMessage(), response);
		    }
		
		super.returnnBaseJson(true, "修改成功", response);
	}
	
	/**
	 * 
	 * 方法说明： . <BR>
	 * @see com.simple.dispatch.${packageName}.controller.${objectName}Controller <BR> <BR>
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 * @return: ModelAndView
	 * @Author:  ${author}  <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	@RequestMapping("/delete${objectName}.do")
	public void delete${objectName}(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			${objectNameLower}Service.delete${objectName}(paramsMap);
			} catch (Exception e) {
			// TODO Auto-generated catch block
				logger.error(e.getMessage());
				super.returnnBaseJson(false, e.getMessage(), response);
		    }
		
		super.returnnBaseJson(true, "删除成功", response);
	}
	
	
	
	

}

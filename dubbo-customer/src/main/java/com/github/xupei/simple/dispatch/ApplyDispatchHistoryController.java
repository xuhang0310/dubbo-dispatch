package com.github.xupei.simple.dispatch;


import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.xupei.dubbo.api.dispatch.IApplyDispatchHistoryService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.shiro.ShiroUser;

/**
 * 申请令历史
 * @author wjh
 *
 */
@Controller
@RequestMapping(value="/applyDispatchHistory")
public class ApplyDispatchHistoryController  extends BaseController{
	
	@Autowired
	IApplyDispatchHistoryService dispatchHistoryService;
	
	/**
	 * 页面初始化查询方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping("/getDispatchHistoryList.do")
	public void getDispatchHistoryList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		//System.out.println(user.getLoginName()+"+++++++++username");
		paramsMap.put("LoginName", user.getLoginName());
		Map map=dispatchHistoryService.getDispatchHistoryList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	/**
	 * 页面初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping(value="/dispatchHistoryListLayer.do")
	public ModelAndView dispatchHistoryListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/dispatch/applyDispatchHistoryList");
		
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		
		
		return model;
	}
	
	/**
	 * 查看流程
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping(value="/getJqueryZCLCTList.do")
	public ModelAndView getJqueryZCLCTList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/dispatch/mould");
		
		//model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		
		
		return model;
	}
	
	    

}

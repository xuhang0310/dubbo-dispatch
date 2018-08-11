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

import com.github.xupei.dubbo.api.dispatch.ITaskOrderIndexService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.shiro.ShiroUser;

/**
 * 调度令
 * @author wjh
 *
 */
@Controller
@RequestMapping(value="/taskOrderIndex")
public class TaskOrderIndexController  extends BaseController{
	
	@Autowired
	ITaskOrderIndexService taskorderindexService;
	
	/**
	 * 页面初始化查询方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping("/getTaskOrderIndexList.do")
	public void getDispatchList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		//System.out.println(user.getLoginName()+"+++++++++username");
		paramsMap.put("LoginName", user.getLoginName());
		paramsMap.put("LoginID", user.getId());
		Map map=taskorderindexService.getTaskOrderIndexList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	/**
	 * 页面初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping(value="/taskOrderIndexListLayer.do")
	public ModelAndView dispatchListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/dispatch/taskOrderIndexList");
		
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
		
		List list = null;
		List daiShenPiList = null;
		try {
			list = taskorderindexService.getTaskLogPath(paramsMap.get("taskId"));
			daiShenPiList = taskorderindexService.getDaiShenPiList(paramsMap.get("taskId"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("list============"+list);
		System.out.println("daiShenPiList============"+daiShenPiList);
		model.setViewName("/jsp/dispatch/mould");
		
		model.addObject("flowLogList",list );
		model.addObject("daiShenPiList",daiShenPiList );
		
		
		return model;
	}
	
	    

}

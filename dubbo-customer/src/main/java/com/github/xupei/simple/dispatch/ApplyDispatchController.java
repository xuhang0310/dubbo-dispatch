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

import com.github.xupei.dubbo.api.dispatch.IApplyDispatchService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.shiro.ShiroUser;

/**
 * 申请令
 * @author wjh
 *
 */
@Controller
@RequestMapping(value="/applyDispatch")
public class ApplyDispatchController  extends BaseController{
	
	@Autowired
	IApplyDispatchService dispatchService;
	
	/**
	 * 页面初始化查询方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping("/getDispatchList.do")
	public void getDispatchList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		//System.out.println(user.getLoginName()+"+++++++++username");
		paramsMap.put("LoginName", user.getLoginName());
		paramsMap.put("LoginID", user.getId());
		Map map=dispatchService.getDispatchList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	/**
	 * 页面初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping(value="/dispatchListLayer.do")
	public ModelAndView dispatchListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/dispatch/applyDispatchList");
		
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
		
		List list = null;
		List daiShenPiList = null;
		try {
			list = dispatchService.getTaskLogPath(paramsMap.get("taskId"));
			daiShenPiList = dispatchService.getDaiShenPiList(paramsMap.get("taskId"));
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

package com.github.xupei.simple.scada;


import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.xupei.dubbo.api.IFeedService;
import com.github.xupei.dubbo.api.scada.IAlarmAnalysisService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.shiro.ShiroUser;

/**
 * 报警分析
 * @author wjh
 *
 */
@Controller
@RequestMapping(value="/alarmAnalysis")
public class AlarmAnalysisController  extends BaseController{
	
	@Autowired
	IAlarmAnalysisService alarmAnalysisService;
	
	@Autowired
	IFeedService   feedService;
	
	/**
	 * 页面初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping("/getAlarmAnalysisList.do")
	public void getAlarmAnalysisList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		//System.out.println(user.getLoginName()+"+++++++++username");
		paramsMap.put("LoginName", user.getLoginName());
		Map map=alarmAnalysisService.getAlarmAnalysisList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	
	@RequestMapping(value="/alarmAnalysisListLayer.do")
	public ModelAndView alarmAnalysisListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/real/feed/alarmAnalysisList");
		try {
			model.addObject("feedList",feedService.getFeedAllList(paramsMap));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		
		
		return model;
	}
	
	/**
	 * 修改查询方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value="/updateAlarmAnalysisList.do")
	public ModelAndView updateAlarmAnalysis(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======updateDataTeam========");
	      String teamid = paramsMap.get("teamid");
	      List list = null;
	      List orgList = null;
	      List sulo = null;
	      try {
	    	  list = alarmAnalysisService.editAlarmAnalysis(teamid);
	    	  orgList = alarmAnalysisService.orgList();
	    	  sulo = alarmAnalysisService.suloList();
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView mad = new ModelAndView("/jsp/real/feed/editAlarmAnalysis");
	      //将数据存入modelMap
	      mad.addObject("dataTeam", list.get(0));
	      mad.addObject("orgobj", orgList);
	      mad.addObject("sulo", sulo);
	      return mad;
	  }
	
	/**
	 * 新增跳转方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value="/addAlarmAnalysisList.do")
	public ModelAndView addAlarmAnalysisList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======updateDataTeam========");
	      String feedid = paramsMap.get("feedid");
	      List orgList = null;
	      List sulo = null;
	      try {
	    	  orgList = alarmAnalysisService.orgList();
	    	  sulo = alarmAnalysisService.suloList();
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView mad = new ModelAndView("/jsp/real/feed/editAlarmAnalysis");
	      //将数据存入modelMap
	      mad.addObject("orgobj", orgList);
	      mad.addObject("sulo", sulo);
	      return mad;
	  }
	
	
	/**
	 * 修改
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/editAlarmAnalysis.do")
		public void editAlarmAnalysis(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, String> paramsMap){
		
		try {
	          
			String[] principal2=request.getParameterValues("principal");
			String[] watch2=request.getParameterValues("watch");
			String principal="";
			String watch="";
			for (int i = 0; i < principal2.length; i++) {
				principal+=principal2[i]+",";
			}
			for (int i = 0; i < watch2.length; i++) {
				watch+=watch2[i]+",";
			}
			paramsMap.put("principal", principal);
			paramsMap.put("watch", watch);
			
			alarmAnalysisService.editAlarmAnalysis(paramsMap);
			JsonUtil.returnnBaseJson(true, "修改成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "修改失败", response);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 新增
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/saveAlarmAnalysis.do")//,method=RequestMethod.POST
		public void saveAlarmAnalysis(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, String> paramsMap){
		
		try {
	          
			String[] principal2=request.getParameterValues("principal");
			String[] watch2=request.getParameterValues("watch");
			String principal="";
			String watch="";
			for (int i = 0; i < principal2.length; i++) {
				principal+=principal2[i]+",";
			}
			for (int i = 0; i < watch2.length; i++) {
				watch+=watch2[i]+",";
			}
			paramsMap.put("principal", principal);
			paramsMap.put("watch", watch);
			
			alarmAnalysisService.saveAlarmAnalysis(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/deleteAlarmAnalysis.do")
	public void deleteAlarmAnalysis(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String teamid=paramsMap.get("teamid")+"";
			alarmAnalysisService.deleteAlarmAnalysis(teamid);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}
		
	}
	
	
	    

}

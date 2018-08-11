package com.github.xupei.simple.data;


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

import com.github.xupei.dubbo.api.data.IDataTeamService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.shiro.ShiroUser;

/**
 * 班组管理
 * @author wjh
 *
 */
@Controller
@RequestMapping(value="/dataTeam")
public class DataTeamController  extends BaseController{
	
	@Autowired
	IDataTeamService dataTeamService;
	
	/**
	 * 页面初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping("/getDataTeamList.do")
	public void getDataTeamList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		//System.out.println(user.getLoginName()+"+++++++++username");
		paramsMap.put("LoginName", user.getLoginName());
		Map map=dataTeamService.getDataTeamList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	
	@RequestMapping(value="/dataTeamListLayer.do")
	public ModelAndView dataTeamListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/data/dataTeamList");
		
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
	@RequestMapping(value="/updateDataTeamList.do")
	public ModelAndView updateDataTeam(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======updateDataTeam========");
	      String teamid = paramsMap.get("teamid");
	      List list = null;
	      List orgList = null;
	      List sulo = null;
	      try {
	    	  list = dataTeamService.editDataTeam(teamid);
	    	  orgList = dataTeamService.orgList();
	    	  sulo = dataTeamService.suloList();
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView mad = new ModelAndView("/jsp/data/editDataTeam");
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
	@RequestMapping(value="/addDataTeamList.do")
	public ModelAndView addDataTeamList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======updateDataTeam========");
	      String feedid = paramsMap.get("feedid");
	      List orgList = null;
	      List sulo = null;
	      try {
	    	  orgList = dataTeamService.orgList();
	    	  sulo = dataTeamService.suloList();
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView mad = new ModelAndView("/jsp/data/editDataTeam");
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
	@RequestMapping(value="/editDataTeam.do")
		public void editDataTeam(HttpServletRequest request, HttpServletResponse response,
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
			
			dataTeamService.editDataTeam(paramsMap);
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
	@RequestMapping(value="/saveDataTeam.do")//,method=RequestMethod.POST
		public void saveDataTeam(HttpServletRequest request, HttpServletResponse response,
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
			
			dataTeamService.saveDataTeam(paramsMap);
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
	@RequestMapping(value="/deleteDataTeam.do")
	public void deleteDataTeam(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String teamid=paramsMap.get("teamid")+"";
			dataTeamService.deleteDataTeam(teamid);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}
		
	}
	
	
	    

}

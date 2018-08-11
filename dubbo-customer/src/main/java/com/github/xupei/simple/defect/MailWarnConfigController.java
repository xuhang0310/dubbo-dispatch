package com.github.xupei.simple.defect;


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

import com.github.xupei.dubbo.api.defect.IMailWarnConfigService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.shiro.ShiroUser;

/**
 * 邮件报警配置管理
 * @author wjh
 *
 */
@Controller
@RequestMapping(value="/mailWarnConfig")
public class MailWarnConfigController  extends BaseController{
	
	@Autowired
	IMailWarnConfigService mailWarnConfigService;
	
	/**
	 * 页面初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping("/getMailWarnConfigList.do")
	public void getMailWarnConfigList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		//System.out.println(user.getLoginName()+"+++++++++username");
		paramsMap.put("LoginName", user.getLoginName());
		Map map=mailWarnConfigService.getMailWarnConfigList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	/**
	 * 页面初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value="/mailWarnConfigListLayer.do")
	public ModelAndView mailWarnConfigListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/defect/mailWarnConfigList");
		
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
	@RequestMapping(value="/updateMailWarnConfigList.do")
	public ModelAndView updateMailWarnConfig(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======updateDataTeam========");
	      String id = paramsMap.get("id");
	      List list = null;
	      List orgList = null;
	      List sulo = null;
	      try {
	    	  list = mailWarnConfigService.editMailWarnConfig(id);
	    	  /*orgList = mailWarnConfigService.orgList();
	    	  sulo = mailWarnConfigService.suloList();*/
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView mad = new ModelAndView("/jsp/defect/editMailWarnConfig");
	      //将数据存入modelMap
	      mad.addObject("editorObject", list.get(0));
	      //mad.addObject("orgobj", orgList);
	      //mad.addObject("sulo", sulo);
	      return mad;
	  }
	
	/**
	 * 新增跳转方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value="/addMailWarnConfigList.do")
	public ModelAndView addMailWarnConfigList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======updateDataTeam========");
	      String feedid = paramsMap.get("feedid");
	      List orgList = null;
	      List sulo = null;
	      try {
	    	  orgList = mailWarnConfigService.orgList();
	    	  sulo = mailWarnConfigService.suloList();
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView mad = new ModelAndView("/jsp/defect/editMailWarnConfig");
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
	@RequestMapping(value="/editMailWarnConfig.do")
		public void editMailWarnConfig(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, String> paramsMap){
		
		try {
	          
			mailWarnConfigService.editMailWarnConfig(paramsMap);
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
	@RequestMapping(value="/saveMailWarnConfig.do")//,method=RequestMethod.POST
		public void saveMailWarnConfig(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, String> paramsMap){
		
		try {
	          
			mailWarnConfigService.saveMailWarnConfig(paramsMap);
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
	@RequestMapping(value="/deleteMailWarnConfig.do")
	public void deleteMailWarnConfig(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String teamid=paramsMap.get("id")+"";
			mailWarnConfigService.deleteMailWarnConfig(teamid);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}
		
	}
	
	
	    

}

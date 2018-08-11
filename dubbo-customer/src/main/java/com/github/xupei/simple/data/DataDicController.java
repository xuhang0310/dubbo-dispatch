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

import com.github.xupei.dubbo.api.data.IDataDicService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.shiro.ShiroUser;

/**
 * 数据字典
 * @author wjh
 *
 */
@Controller
@RequestMapping(value="/dataDic")
public class DataDicController  extends BaseController{
	
	@Autowired
	IDataDicService dataDicService;
	
	/**
	 * 页面初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping("/getDataDicList.do")
	public void getDataDicList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		//System.out.println(user.getLoginName()+"+++++++++username");
		paramsMap.put("LoginName", user.getLoginName());
		Map map=dataDicService.getDataDicList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	/**
	 * 左侧树
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping("/getDataDicTree.do")
	public void getDataDicTree(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			List list=dataDicService.getDataDicTree(paramsMap);
			JsonUtil.returnListJson(list, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/dataDicListLayer.do")
	public ModelAndView dataDicListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/data/dataDicList");
		
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
	@RequestMapping(value="/updateDataDicList.do")
	public ModelAndView updateDataDic(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======updateDataDic========");
	      String Dicid = paramsMap.get("id");
	      List list = null;
	      List orgList = null;
	      List sulo = null;
	      try {
	    	  list = dataDicService.editDataDic(Dicid);
	    	  orgList = dataDicService.orgList();
	    	  sulo = dataDicService.suloList();
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView mad = new ModelAndView("/jsp/data/editDataDic");
	      //将数据存入modelMap
	      mad.addObject("dataDic", list.get(0));
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
	@RequestMapping(value="/addDataDicList.do")
	public ModelAndView addDataDicList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======updateDataDic========");
	      String id = paramsMap.get("id");
	      //List orgList = null;
	      List sulo = null;
	      try {
	    	  //orgList = dataDicService.orgList();
	    	  sulo = dataDicService.suloList();
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView mad = new ModelAndView("/jsp/data/editDataDic");
	      //将数据存入modelMap
	      //mad.addObject("orgobj", orgList);
	      mad.addObject("sulo", sulo);
	      return mad;
	  }
	
	
	/**
	 * 修改
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/editDataDic.do")
		public void editDataDic(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, String> paramsMap){
		
		try {
	          
			dataDicService.editDataDic(paramsMap);
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
	@RequestMapping(value="/saveDataDic.do")//,method=RequestMethod.POST
		public void saveDataDic(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, String> paramsMap){
		
		try {
	          
			dataDicService.saveDataDic(paramsMap);
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
	@RequestMapping(value="/deleteDataDic.do")
	public void deleteDataDic(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String Dicid=paramsMap.get("id")+"";
			dataDicService.deleteDataDic(Dicid);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}
		
	}
	
	
	    

}

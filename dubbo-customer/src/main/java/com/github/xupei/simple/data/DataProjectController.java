package com.github.xupei.simple.data;


import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.xupei.dubbo.api.data.IDataProjectService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.shiro.ShiroUser;
import com.github.xupei.simple.util.DateChosenUtil;
import com.github.xupei.simple.util.echart.EchartUtil;

/**
 * 一源一日计划
 * @author wjh
 *
 */
@Controller
@RequestMapping(value="/dataProject")
public class DataProjectController  extends BaseController{
	
	@Autowired
	IDataProjectService dataProjectService;
	
	/**
	 * 历史 页面初始化查询方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping("/getDataProjectHistoryList.do")
	public void getDataProjectHistoryList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		//System.out.println(user.getLoginName()+"+++++++++username");
		paramsMap.put("LoginName", user.getLoginName());
		Map map=dataProjectService.getDataProjectList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	/**
	 * 一源一日历史 页面初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping(value="/dataProjectHistoryListLayer.do")
	public ModelAndView dataProjectHistoryListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/data/dataProjectHistoryList");
		
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		
		return model;
	}
	
	/**
	 * 一源一日计划 页面初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping(value="/dataProjectListLayer.do")
	public ModelAndView dispatchHistoryListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		List jhrlList = null;
	    List jhList = null;
	    Map indexMap = null;
	    Map dataParamMap = null;
	    try {
	    	//初始化时进行参数的新增
	    	dataProjectService.saveDataProjectIndex();
	    	jhrlList = dataProjectService.getJhrlDate();
	    	jhList = dataProjectService.getJhList();
	    	indexMap = dataProjectService.getIndexMap();
	    	dataParamMap=dataProjectService.getDataParamMap();
	    } catch (Exception e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
		model.setViewName("/jsp/data/dataProjectList");
		
		model.addObject("jhrlList", jhrlList);//热源计划产热量
		model.addObject("jhList", jhList);//当前计划
		model.addObject("indexMap", indexMap);//计划总览
		model.addObject("dataParamMap", dataParamMap);
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		
		
		return model;
	}
	
	/**
	 * 新增 计划与参数
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/saveDataProject.do")//,method=RequestMethod.POST
	public void saveDataProject(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		//=========计划=========
		String gswd=paramsMap.get("gswd");
		String hswd=paramsMap.get("hswd");
		String ssll=paramsMap.get("ssll");
		String ssrl=paramsMap.get("ssrl");
		String feedcode=paramsMap.get("feedcode");
		//=========参数=========
		String date=paramsMap.get("projectdate");
		String rzb=paramsMap.get("llrzb");
		String temp=paramsMap.get("avgtemp");
		String cnmj=paramsMap.get("cnmj");
		
		try {
			//保存计划
			dataProjectService.saveDetailProject(gswd, hswd, ssrl, ssll, feedcode);
			//修改参数
			dataProjectService.updateDataProjectIndex( date, rzb, cnmj, temp);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 一源一日计划明细 页面初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping(value="/initDataProjectOther.do")
	public ModelAndView initDataProjectOther(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		List jhrlList = null;
	    List jhList = null;
	    Map indexMap = null;
	    Map dataParamMap = null;
	    try {
	    	jhrlList = dataProjectService.getJhrlDate();
	    	/*jhList = dataProjectService.getJhList();*/
	    	jhList = dataProjectService.getJhHistoryList(paramsMap);
	    	indexMap = dataProjectService.getIndexMap(paramsMap);
	    	dataParamMap=dataProjectService.getDataParamMap();
	    } catch (Exception e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
		model.setViewName("/jsp/data/dataProjectOtherList");
		
		model.addObject("jhrlList", jhrlList);//热源计划产热量
		model.addObject("jhList", jhList);//当前计划
		model.addObject("indexMap", indexMap);//计划总览
		model.addObject("dataParamMap", dataParamMap);
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		
		
		return model;
	}
	
	/**
	 * 计划明细--图形
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping("/getDataProjectChart.do")
	public void getDataProjectChart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		try {
			List list=dataProjectService.getDataProjectChart(paramsMap);
			
			String [] primaryField={"SCADADAY"};
			JSONObject jsonNode=EchartUtil.createLineColumnEchartData(list, paramsMap, "FEEDNAME",primaryField);
			PrintWriter out =null;
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.write(jsonNode.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	    

}

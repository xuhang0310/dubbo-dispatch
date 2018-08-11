package com.github.xupei.simple.scada;


import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import com.github.xupei.dubbo.api.IFeedService;
import com.github.xupei.dubbo.api.scada.IScadaFeedAnalysisService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.shiro.ShiroUser;
import com.github.xupei.simple.util.DateChosenUtil;
import com.github.xupei.simple.util.echart.EchartUtil;

/**
 * 热源运行分析
 * @author wjh
 *
 */
@Controller
@RequestMapping(value="/feedAnalysis")
public class ScadaFeedAnalysisController  extends BaseController{
	
	@Autowired
	IScadaFeedAnalysisService feedAnalysisService;
	
	@Autowired
	IFeedService feedService;
	
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
		Map map=feedAnalysisService.getDataTeamList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	
	@RequestMapping(value="/dataTeamListLayer.do")
	public ModelAndView dataTeamListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		String feedcode = paramsMap.get("feedcode");
		model.addObject("feedcode", feedcode);
		//设置默认的开始和结束时间
		try {
			model.addObject("feedList", feedService.getFeedAllList(paramsMap));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addObject("startdate", this.getDate2());
		model.addObject("enddate", this.getDate1());
		model.setViewName("/jsp/real/feed/feedAnalysisList");
		
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		model.addObject("paramsMap",super.getEchartConfig(paramsMap));
		
		return model;
	}
	
	
	@RequestMapping("/getFeedAnalysisChart.do")
	public void getFeedAnalysisChart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		try {
			List list=feedAnalysisService.getFeedAnalysisChart(paramsMap);
			
			String [] primaryField={"SCADADAY"};
			JSONObject jsonNode=EchartUtil.createLineColumnEchartData(list, paramsMap, "SCADATIMESTR",primaryField);
			PrintWriter out =null;
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.write(jsonNode.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String getDate1(){
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		Date date=cal.getTime();
	    String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(date);
		return yesterday;
	}
	public String getDate2(){
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE,   0);
	    String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		return yesterday;
	}
	

}

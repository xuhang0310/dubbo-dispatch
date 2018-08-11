package com.github.xupei.simple.scada;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.xupei.dubbo.api.INodeService;
import com.github.xupei.dubbo.api.ISysOrgService;
import com.github.xupei.dubbo.api.scada.INodeRealService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.util.DateChosenUtil;
import com.github.xupei.simple.util.echart.EchartUtil;

/**
 * 换热站实时工况
 * @author wjh
 *
 */
@Controller
@RequestMapping(value = "/nodeReal")
public class NodeRealController extends BaseController {
	
	@Autowired
	INodeRealService nodeRealService;
	@Autowired
	INodeService nodeService;
	@Autowired
	ISysOrgService sysOrgService;
	
	/**
	 * 初始化查询方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping("/getNodeRealList.do")
	public void getNodeRealList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=nodeRealService.getNodeRealList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	/**
	 * 初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value="/nodeRealListLayer.do")
	public ModelAndView nodeRealListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/real/node/nodeRealList");
		
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		
		try {
			model.addObject("orgList",nodeRealService.getOrgList());
			model.addObject("nodeList",nodeRealService.getNodeList());
			model.addObject("jzlxList",nodeRealService.getJzlxList());
			model.addObject("cnfsList",nodeRealService.getCnfsList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 报警配置初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value="/nodeWarnConfigLayer.do")
	public ModelAndView nodeWarnConfigLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/real/node/warnConfig");
		
		
		
		try {
			model.addObject("nodeList",nodeService.getNodeAllList(paramsMap));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
    /**
     * 报错报警配置
     * @param request
     * @param response
     * @param paramsMap
     */
	@RequestMapping(value="/saveWarningConfig.do")
	public void saveWarningConfig(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			nodeRealService.saveWarnConfig(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
	}
	
	/**
	 * 报警配置刷新查询方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/getWarningConfig.do")
	public void getWarningConfig(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			List list=nodeRealService.getWarnConfig(paramsMap);
			JsonUtil.returnListJson(list, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    

	public static boolean isEmpty(String str){
		return str==null || str.equals("") || str.equals("undefined") ? true : false;
	}

	
	@RequestMapping(value="/getProjectList.do")
	public void getProjectList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){		
		try {
			String orgcode=paramsMap.get("orgcode");
			List projectList=nodeRealService.getProjectList(orgcode);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw =  response.getWriter();
			StringBuffer sb = new StringBuffer();
			if(projectList == null || projectList.size() == 0){
				sb.append("<option value=\"\">暂无项目</option>");
			}else{
				for(Object obj : projectList){
					Map<String, String> b = (Map<String, String>) obj;
					Object o = b.get("NODECODE");
					sb.append("<option value=\""+o.toString()+"\">"+b.get("NODENAME")+"</option>");
				}
			}
			pw.print(sb.toString());
			pw.close();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
	@RequestMapping("/getNodeRealChart.do")
	public void getNodeRealChart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){

		
		try {
			List list=nodeRealService.getNodeRealChart(paramsMap);
			if(paramsMap.get("chartField")==null||"".equals(paramsMap.get("chartField"))){
				paramsMap.put("chartField", "SUPPLYTEMP,RETURNTEMP");
				paramsMap.put("chartTitle", "一网供温,一网回温");
				paramsMap.put("chartType", "line,line");
				paramsMap.put("chartPosition", "0,1");
			}
			JSONObject jsonNode=EchartUtil.createLineColumnEchartData(list, paramsMap, "READTIMESTR",null);
			PrintWriter out =null;
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.write(jsonNode.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	
	@RequestMapping("/getNodeRealSummary.do")
	public void getNodeRealSummary(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		Map map=nodeRealService.getSummaryMap(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	
	
	
	
	
	
	
	
	
}

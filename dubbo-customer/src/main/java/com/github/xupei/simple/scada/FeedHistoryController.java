package com.github.xupei.simple.scada;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.xupei.dubbo.api.IFeedService;
import com.github.xupei.dubbo.api.scada.IFeedHistoryService;
import com.github.xupei.dubbo.api.util.export.ByteArrayOutputStreamExe;
import com.github.xupei.dubbo.api.util.export.ExportExcelService;

import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.util.DateChosenUtil;
import com.github.xupei.simple.util.SqlParser;
import com.github.xupei.simple.util.echart.EchartUtil;

@Controller
@RequestMapping(value = "/feedHis")
public class FeedHistoryController extends BaseController {

	@Autowired
	IFeedHistoryService feedHistoryService;

	@Autowired
	ExportExcelService exportExcelService;

	@Autowired
	IFeedService feedService;

	@RequestMapping(value = "/feedHisListLayer.do")
	public ModelAndView userListLayer(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/history/feed/feedHistoryList");
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		try {
			model.addObject("feedList", feedService.getFeedAllList(paramsMap));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addObject("paramsMap",super.getEchartConfig(paramsMap));
		model.addObject("jsonTableGrid", super.getTableGrid(paramsMap));
		model.addObject("pageid", paramsMap.get("pageid"));
		return model;
	}

	@RequestMapping("/getFeedHistoryList.do")
	public void getUserList(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws Exception {
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		Map map = feedHistoryService.getFeedHistoryList(paramsMap);
		JsonUtil.returnObjectJson(map, response);

	}
	@RequestMapping(value = "/exportExcel.do",method = RequestMethod.GET)
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws Exception {
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		
		List titleList = super.getTableGridTitle(paramsMap);
		String sql = feedHistoryService.getSql(paramsMap);
		String FileName = "热源历史数据";
		String Filename = "热源历史数据.xls";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader( "Content-Disposition", "attachment;" +
				"filename=" + new String( Filename.getBytes("UTF-8"), "ISO8859-1" ) );
		
		ByteArrayOutputStreamExe os = new ByteArrayOutputStreamExe();
		os = exportExcelService.getFileStream(sql, titleList, FileName,paramsMap);
		try {
			//输出文件
			response.getOutputStream().write(os.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getFeedHistoryChart.do")
	public void getFeedEnergyChart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		try {
			List list=feedHistoryService.getHistoryChart(paramsMap);
			String [] primaryField={"FEEDNAME","LINENAME"};
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
	
	
	
	
	@RequestMapping("/getFeedSummary.do")
	public void getFeedSummary(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		Map map=feedHistoryService.getSummaryMap(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	
	
	
	
	
	
	
	
	
	

}

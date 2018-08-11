package com.github.xupei.simple.scada;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.github.xupei.dubbo.api.INodeService;
import com.github.xupei.dubbo.api.scada.INodeHistoryService;
import com.github.xupei.dubbo.api.util.export.ByteArrayOutputStreamExe;
import com.github.xupei.dubbo.api.util.export.ExportExcelService;

import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.util.DateChosenUtil;
import com.github.xupei.simple.util.echart.EchartUtil;

@Controller
@RequestMapping(value = "/nodeHistory")
public class NodeHistoryController extends BaseController {

	@Autowired
	INodeHistoryService nodeHistoryService;

	@Autowired
	ExportExcelService exportExcelService;

	@Autowired
	INodeService nodeService;

	@RequestMapping(value = "/nodeHisListLayer.do")
	public ModelAndView nodeHisListLayer(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/history/node/nodeHistoryList");
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		try {
			model.addObject("nodeList", nodeService.getNodeAllList(paramsMap));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addObject("paramsMap",super.getEchartConfig(paramsMap));
		model.addObject("jsonTableGrid", super.getTableGrid(paramsMap));
		model.addObject("pageid", paramsMap.get("pageid"));
		return model;
	}

	@RequestMapping("/getNodeHistoryList.do")
	public void getNodeHistoryList(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws Exception {
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		Map map = nodeHistoryService.getNodeHistoryList(paramsMap);
		JsonUtil.returnObjectJson(map, response);

	}
	
	@RequestMapping(value = "/exportExcel.do",method = RequestMethod.GET)
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws Exception {
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		
		List titleList = super.getTableGridTitle(paramsMap);
		String sql = nodeHistoryService.getSql(paramsMap);
		String FileName = "换热站历史数据";
		String Filename = "换热站历史数据.xls";
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
	
	@RequestMapping("/getNodeHistoryChart.do")
	public void getNodeHistoryChart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		try {
			List list=nodeHistoryService.getHistoryChart(paramsMap);
			String [] primaryField={"NODENAME"};
			JSONObject jsonNode=EchartUtil.createLineColumnEchartData(list, paramsMap, "SCADADATE",primaryField);
			PrintWriter out =null;
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.write(jsonNode.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	@RequestMapping("/getNodeSummary.do")
	public void getNodeSummary(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		Map map=nodeHistoryService.getSummaryMap(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	
	
	
	

}

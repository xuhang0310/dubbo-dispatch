package com.github.xupei.simple.map;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.github.xupei.simple.shiro.ShiroUser;

import com.github.xupei.dubbo.api.IBasMeterService;
import com.github.xupei.dubbo.api.map.IMapMaintenanceService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;

/**
 * 工况地图维护
 * @author liujiefeng
 *
 */

@Controller
@RequestMapping(value="/mapMaintenance")
public class MapMaintenanceController extends BaseController {
	
	@Autowired
	IMapMaintenanceService maintenanceService;
	private HttpServletResponse response;
	
	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}


	@RequestMapping("/getBasMeterList.do")
	public void getBasMeterList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=maintenanceService.getBasMeterList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}


	@RequestMapping(value="/mapLayer.do")
	public ModelAndView basMeterLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		String feedPointData="";//已存热源坐标点
		String nodePointData="";//已存换热站坐标点
		String lineData="";//已存线坐标点
		boolean isdraw=false;
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		try {
			feedPointData=maintenanceService.getFeedPointData();
			nodePointData=maintenanceService.getNodePointData();
			lineData=maintenanceService.getLineData();
			isdraw=maintenanceService.getIsDraw(user.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/map/mapList");
		model.addObject("feedPointData", feedPointData);
		model.addObject("nodePointData", nodePointData);
		model.addObject("lineData", lineData);
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		model.addObject("isdraw", isdraw);
		
		return model;
	}
	
	@RequestMapping(value="/NodeMapLayer.do")
	public ModelAndView NodeMapLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	//	String feedPointData="";//已存热源坐标点
		String nodePointData="";//已存换热站坐标点
		String lineData="";//已存线坐标点
		try {
		//	feedPointData=maintenanceService.getFeedPointData();
			nodePointData=maintenanceService.getNodePointDataForEnergy();
			lineData=maintenanceService.getLineData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/map/NodeMapList");
		//model.addObject("feedPointData", feedPointData);
		model.addObject("nodePointData", nodePointData);
		model.addObject("lineData", lineData);
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		
		
		return model;
	}
	
	
	
	
	@RequestMapping(value="/deleteMeter.do")
	public void deleteMeter(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("id");
	//		maintenanceService.deleteMeter(id);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}
		
	}
	

	
	@RequestMapping(value="/toAddNode.do")
	public ModelAndView findMeter(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws IOException{
	       String lng=paramsMap.get("lng");
	       String lat=paramsMap.get("lat");
	       ModelAndView ParamMap = new ModelAndView("/jsp/map/addNode");
	       ParamMap.addObject("lng",lng);
	       ParamMap.addObject("lat",lat);
	      return ParamMap;
	  }
	
	    
	@RequestMapping(value="/addNode.do",method=RequestMethod.POST)
	public void addNode(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		JSONObject jsonobj = new JSONObject();
		PrintWriter out = null;
		String imgUrl="";
		try {
			if(paramsMap.get("stationtype").equals("1")){
				imgUrl="map_node";
			}else{
			   imgUrl="map_feed";
			}
			response.setContentType("text/html;charset=utf-8");
			out=response.getWriter();
			String pointdata=maintenanceService.addNode(paramsMap);
			jsonobj.put("flag", true);
			jsonobj.put("messager",  "保存成功");
			jsonobj.put("pointdata", pointdata);
			jsonobj.put("imgUrl", imgUrl);
			out.write(jsonobj.toString());
		//	JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}finally{
			if(out!=null){
	    		out.close();
	    	}
		}
		
	}
	
	
	
	@RequestMapping(value="/addLine.do",method=RequestMethod.POST)
	public void addLine(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		JSONObject jsonobj = new JSONObject();
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=utf-8");
			out=response.getWriter();
			String linedata =maintenanceService.addLine(paramsMap);
			jsonobj.put("status", "y");
			jsonobj.put("info", "保存成功");
			jsonobj.put("linedata", linedata);
			out.write(jsonobj.toString());
		} catch (Exception e) {
			jsonobj.put("status", "n");
			jsonobj.put("info", "保存失败");
			out.write(jsonobj.toString());
			e.printStackTrace();
		}finally{
			if(out!=null){
	    		out.close();
	    	}
		}
		

		
	}
	
	
	@RequestMapping(value="/delPoint.do",method=RequestMethod.POST)
	public void delPoint(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		//{pointId=3}
		JSONObject jsonobj = new JSONObject();
		PrintWriter out = null;

		try {		
			response.setContentType("text/html;charset=utf-8");
			out=response.getWriter();
			maintenanceService.delPoint(paramsMap);
			jsonobj.put("status", "y");
			jsonobj.put("info", "删除成功");
			out.write(jsonobj.toString());
		} catch (Exception e) {
			jsonobj.put("status", "n");
			jsonobj.put("info", "删除失败");
			out.write(jsonobj.toString());
			e.printStackTrace();
		}finally{
			if(out!=null){
	    		out.close();
	    	}
		}
		
	}
	
	
	

	@RequestMapping(value="/delLine.do",method=RequestMethod.POST)
	public void delLine(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		//{pointId=3}
		JSONObject jsonobj = new JSONObject();
		PrintWriter out = null;

		try {		
			response.setContentType("text/html;charset=utf-8");
			out=response.getWriter();
			maintenanceService.delLine(paramsMap);
			jsonobj.put("status", "y");
			jsonobj.put("info", "删除成功");
			out.write(jsonobj.toString());
		} catch (Exception e) {
			jsonobj.put("status", "n");
			jsonobj.put("info", "删除失败");
			out.write(jsonobj.toString());
			e.printStackTrace();
		}finally{
			if(out!=null){
	    		out.close();
	    	}
		}
		
	}
	
	/**
	 * 展示换热站实时工况
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 * @throws IOException
	 */
	
	@RequestMapping(value="/findNodeScada.do")
	public ModelAndView findNodeScada(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws IOException{
		List nodeReal=null;
		List nodeInfo=null;
		try {
			nodeReal=maintenanceService.getNodeReal(paramsMap);
			nodeInfo=maintenanceService.getNodeInfo(paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	       ModelAndView model = new ModelAndView("/jsp/map/map_node_real");
	       if(!nodeReal.isEmpty()){
	    	   model.addObject("node", nodeReal.get(0));
	       }else{
	    	   model.addObject("node", nodeInfo.get(0));   
	       }
	      return model;
	  }
	
	
	/**
	 * 展示换热站实时工况
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 * @throws IOException
	 */
	
	@RequestMapping(value="/findFeedScada.do")
	public ModelAndView findFeedScada(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws IOException{
		List feedReal=null;
		HashMap map=null;
		List feedInfo=null;
		try {
			feedReal=maintenanceService.getFeedReal(paramsMap);
			feedInfo=maintenanceService.getFeedInfo(paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	       ModelAndView ParamMap = new ModelAndView("/jsp/map/map_feed_real");
	       if(!feedReal.isEmpty()){
	       ParamMap.addObject("feed", feedReal.get(0));
	       }else{
	      ParamMap.addObject("feed", feedInfo.get(0));   
	       }
	      return ParamMap;
	  }
	
	
	
	
	
	

	    



}

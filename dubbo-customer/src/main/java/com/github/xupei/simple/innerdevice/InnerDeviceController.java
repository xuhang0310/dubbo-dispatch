package com.github.xupei.simple.innerdevice;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



import com.github.xupei.dubbo.api.innerDevice.IInnerDeviceService;
import com.github.xupei.dubbo.api.util.export.ByteArrayOutputStreamExe;
import com.github.xupei.dubbo.api.util.export.ExportExcelService;
import com.github.xupei.dubbo.api.util.export.ExportQRExcelService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.util.DateChosenUtil;
/**
 * 内部设备管理
 * @author liujiefeng
 * @time 2018-07-11
 */

@Controller
@RequestMapping(value="/innerDevice")
public class InnerDeviceController extends BaseController {
	
	@Autowired
	IInnerDeviceService innerDeviceService;
	@Autowired
	ExportExcelService exportExcelService;
	@Autowired
	ExportQRExcelService exportQRExcelService;
	
	private HttpServletResponse response;
	
	
	public HttpServletResponse getResponse() {
		return response;
	}





	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}





	@RequestMapping("/getInnerDeviceList.do")
	public void getBasMeterList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=innerDeviceService.getInnerDeviceList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}


	
	
	
	@RequestMapping(value="/saveDevice.do",method=RequestMethod.POST)
	public void saveDevice(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			innerDeviceService.saveDevice(paramsMap);
			
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	@RequestMapping(value="/innerDeviceLayer.do")
	public ModelAndView innerDeviceLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	    String type=paramsMap.get("type");
	    List orgList=null;
		ModelAndView model=new ModelAndView();
	    try {
	    	orgList=innerDeviceService.getOrg();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.setViewName("/jsp/bas/innerDevice/innerDeviceList");	
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		model.addObject("orgList", orgList);
		model.addObject("type", type);
		return model;
	}
	
	@RequestMapping(value="/deleteDevice.do")
	public void deleteDevice(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("id");
			innerDeviceService.deleteDevice(id);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}
		
	}
	

	@ResponseBody
	@RequestMapping(value="/selectAll.do")
	public ModelAndView selectAll(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		List orgList=null;
		List sblx=null;
		String type=paramsMap.get("type");
		 try {
			 orgList=innerDeviceService.getOrg();
			 sblx=innerDeviceService.getSblx();
		} catch (Exception e) {
			e.printStackTrace();
		}
	      ModelAndView ParamMap = new ModelAndView("/jsp/bas/innerDevice/editInnerDevice");
	      ParamMap.addObject("orgList", orgList);
	      ParamMap.addObject("sblx", sblx);
	      ParamMap.addObject("type", type);
	      return ParamMap;
	  }

	@RequestMapping(value="/getProjectList.do")
	public void getProjectList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("stationtype");
		//	innerDeviceService.deleteParam(id);
			List projectList=innerDeviceService.getProjectList(id);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw =  response.getWriter();
			StringBuffer sb = new StringBuffer();
			if(projectList == null || projectList.size() == 0)
				sb.append("<option value=\"\">暂无项目</option>");
			
			else
			{
				for(Object obj : projectList)
				{
					Map<String, String> b = (Map<String, String>) obj;
					Object o = b.get("ID");
					sb.append("<option value=\""+o.toString()+"\"     >"+b.get("NAME")+"</option>");
				}
			}
			//[RESULT, TITLE]
			pw.print(sb.toString());
			pw.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@ResponseBody
	@RequestMapping(value="/findDevice.do")
	public ModelAndView findDevice(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws IOException{
		  request.setCharacterEncoding("utf-8");
		  response.setContentType("text/html;charset=utf-8");
	      String id = paramsMap.get("id");
	      String type=paramsMap.get("type");
	      List list = null;
	      List orgList=null;
	      List sblx=null;
	      List node=null;
	      try {
	    	  list = innerDeviceService.findDevice(id);
	    	  orgList=innerDeviceService.getOrg();
			  sblx=innerDeviceService.getSblx();
			  node=innerDeviceService.getNode(type);
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      }			
	      ModelAndView ParamMap = new ModelAndView("/jsp/bas/innerDevice/editInnerDevice");
	      //将数据存入modelMap
	      ParamMap.addObject("device", list.get(0));
	      ParamMap.addObject("orgList", orgList);
	      ParamMap.addObject("sblx", sblx);
	      ParamMap.addObject("type", type);
	      ParamMap.addObject("node", node);
	      return ParamMap;
	  }
	
	    

	@RequestMapping(value="/updateDevice.do")
	public void updateDevice(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("id");
			innerDeviceService.updateDevice(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	
	

	
	/**
	 *批量导出二维码图片信息到excel
	 */
	@RequestMapping(value = "/exportQRExcel.do",method = RequestMethod.GET)
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws Exception {
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		
		List titleList = super.getTableGridTitle(paramsMap);
		String fileName = "MC";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename="+URLEncoder.encode("内部设备基础信息.xls","UTF-8"));
		ByteArrayOutputStreamExe os = new ByteArrayOutputStreamExe();
		os = exportQRExcelService.getQRFileStream(innerDeviceService.getSql(paramsMap), titleList, paramsMap,fileName,"NBSB");
		try {
			//输出文件
			response.getOutputStream().write(os.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}

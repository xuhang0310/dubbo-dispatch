package com.github.xupei.simple.scada;

import java.io.IOException;
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

import com.github.xupei.dubbo.api.IFeedService;
import com.github.xupei.dubbo.api.ISysOrgService;
import com.github.xupei.dubbo.api.scada.IFeedRealService;
import com.github.xupei.dubbo.api.util.export.ByteArrayOutputStreamExe;
import com.github.xupei.dubbo.api.util.export.ExportExcelService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.util.DateChosenUtil;
import com.github.xupei.simple.util.echart.EchartUtil;

@Controller
@RequestMapping(value = "/feedReal")
public class FeedRealController extends BaseController {
	
	@Autowired
	IFeedRealService feedRealService;
	@Autowired
	IFeedService feedService;
	@Autowired
	ISysOrgService sysOrgService;
	@Autowired
	ExportExcelService exportExcelService;
	
	@RequestMapping(value="/feedRealListLayer.do")
	public ModelAndView userListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/real/feed/feedRealList");
		
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		model.addObject("paramsMap",super.getEchartConfig(paramsMap));
		
		try {
			model.addObject("feedList",feedService.getFeedAllList(paramsMap));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/toPicture.do")
	public ModelAndView toPicture(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
//{id=9}
		String code=paramsMap.get("code");
	
		List listConfig =null;
		List realdateList=null;
		String  tableInfo="";
		JSONArray jsonArray=new JSONArray();
		String imgUrl="";
		try {
		    imgUrl=feedRealService.getImgUrl(code);
			listConfig=feedRealService.getParamConfigList(code);
			if(listConfig.size()>0){
				 realdateList=feedRealService.getRealDataList(paramsMap.get("code"));
				 if(realdateList.size()==1){

						HashMap mapreal=(HashMap)realdateList.get(0);
						JSONObject realObj=new JSONObject();
						//获得配置列表
						List configList=feedRealService.getParamConfigList(code);
						for(int i=0;i<configList.size();i++){
							Map configMap =(Map)configList.get(i);							
					        String name=(String)configMap.get("NAME");
							String x=(String)configMap.get("X_NUM");
							String y=(String)configMap.get("Y_NUM");
							if(String.valueOf(configMap.get("ISHIDEN"))!=null&&String.valueOf(configMap.get("ISHIDEN")).equals("0")&&mapreal.get(name)!=null){
								realObj.put("x", x);
								realObj.put("y", y);
								realObj.put("u",interceptor((String)configMap.get("UNIT")));
								realObj.put("s", interceptor((String)configMap.get("FSIZE")));
								realObj.put("dplay",interceptor((String)configMap.get("DPLAY")));
								realObj.put("data", interceptor(getRoundNum(mapreal.get(name)+"",configMap.get("VALIDNUM")+"")));
								realObj.put("bg", ""+configMap.get("BG"));
								jsonArray.add(realObj);
							}
																				
						}	 
				 }
				 tableInfo = jsonArray.size()>0 ? jsonArray.toString() : "";
			}
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/real/feed/feedPicture");
		model.addObject("process",tableInfo);
		model.addObject("imgUrl",imgUrl);
		model.addObject("code", code);
		return model;
	}
	
	/**
	 * 拦截数据为空 显示到页面为undefined
	 * @param object
	 * @return
	 */
    public String interceptor(Object object){
    	if(object==null || "null".equals(object)){
    		return "";
    	}else{
    		return String.valueOf(object);
    	}
    }
    
    public String getRoundNum(String nums,String validSize){
		String result = "";
		if(nums==null||nums.equals("")){
			return "0";
		}
		try {
			BigDecimal bd = new BigDecimal(nums); 
			double d = bd.setScale(Integer.parseInt(validSize), BigDecimal.ROUND_HALF_UP).doubleValue();
			result = String.valueOf(d);
		} catch (NumberFormatException e) {
			result = nums;
		}
		return result;
		
	}
	
	@RequestMapping(value="/processManager.do")
	public ModelAndView processManager(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {
		//{code=8, imgUrl=铜治东区.png}
		String code=paramsMap.get("code");
		String imgUrl=paramsMap.get("imgUrl");
		List configList=null;
		JSONArray json=null;
		String process=null;
		try {
			configList=feedRealService.getParamConfigForUpdateArea(code);
			json=JSONArray.fromObject(configList);
			process=json.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/real/feed/updateFeedPicture");	
		model.addObject("code", code);
		model.addObject("imgUrl", imgUrl);
		model.addObject("process", process);
		return model;
	}
	@RequestMapping(value="/feedWarnConfigLayer.do")
	public ModelAndView feedWarnConfigLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/real/feed/warnConfig");
		
		
		
		try {
			model.addObject("feedList",feedService.getFeedAllList(paramsMap));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/getFeedListJson.do")
	public void getFeedListJson(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			List list=feedService.getFeedAllList(paramsMap);
			JsonUtil.returnListJson(list, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(value="/saveWarningConfig.do")
	public void saveWarningConfig(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			feedRealService.saveWarnConfig(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
	}
	@RequestMapping(value="/getWarningConfig.do")
	public void getWarningConfig(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			List list=feedRealService.getWarnConfig(paramsMap);
			JsonUtil.returnListJson(list, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/getFeedWarnDetailLayer.do")
	public ModelAndView getFeedWarnDetailLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/real/feed/warnDetail");
		try {
			model.addObject("paramsMap",paramsMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/getWarningConfigChart.do")
	public void getWarningConfigChart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			List list=null;
			paramsMap.put("chartField", "maxnum,minnum,"+paramsMap.get("fieldname"));
			paramsMap.put("chartTitle", "最大值,最小值,"+paramsMap.get("fieldname"));
			paramsMap.put("chartType", "line,line,line");
			paramsMap.put("chartPosition", "1,1,1");
			JSONObject jsonNode=EchartUtil.createLineColumnEchartData(list, paramsMap, "SCADATIMESTR",null);
			
			PrintWriter out =null;
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.write(jsonNode.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    

	@RequestMapping(value="/pitchUpdate.do")
	public ModelAndView pitchUpdate(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {
		String code=paramsMap.get("code");
		List configList=null;
		try {
			configList=feedRealService.getParamConfigList(code);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/real/feed/editConfig");	
		model.addObject("configList", configList);
		model.addObject("code", code);
		return model;
	}
	
	
	@RequestMapping(value="/updateArea.do")
	public ModelAndView updateArea(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {
		//{code=8, imgUrl=铜治东区.png, areainfo=RETURNTEMP,186px,141px@}
		String code=paramsMap.get("code");
		String imgUrl=paramsMap.get("imgUrl");
		String area=paramsMap.get("areainfo");
		try {
			for(int i=0;i<area.split("@").length;i++){

				String areaArr []=area.split("@");
				String name="";
				String x="";
				String y="";
				for(int k=0;k<areaArr[i].split(",").length;k++){
					String paramArr []=areaArr[i].split(",");
					if(k==0) name=paramArr[k];
					if(k==1) x=paramArr[k].replace("px", "");;
					if(k==2) y=paramArr[k].replace("px", "");
				}
		//	    impl.update(configid,name,x,y,process.getType());
				feedRealService.update(code,name,x,y);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	/*	ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/real/feed/feedPicture");	
	*/
		return toPicture(request,response,paramsMap);
	}
    
	
	
	@RequestMapping("/getFeedRealList.do")
	public void getUserList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=feedRealService.getFeedRealList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	@RequestMapping(value="/savePictureConfig.do",method=RequestMethod.POST)
	public void savePictureConfig(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		//{column=WATER, text=补水瞬时流量m3/h, ishiden=0, dplay=, x=58, y=87, u=, f=2, bg=45b0e4, s=10, position=上}
		try {
			String column []=request.getParameterValues("column");
			String text []=request.getParameterValues("text");
			String ishiden []=request.getParameterValues("ishiden");
			String dplay []=request.getParameterValues("dplay");
			String x_num []=request.getParameterValues("x");
			String y_num []=request.getParameterValues("y");
			String unit []=request.getParameterValues("u");
			String vaildnum []=request.getParameterValues("f");
			String background []=request.getParameterValues("bg");
			String font_size []=request.getParameterValues("s");
			String position []=request.getParameterValues("position");
			
			String code=paramsMap.get("code");
			
			for(int i=0;i<column.length;i++){
				if(isEmpty(column[i]) || isEmpty(text[i])
						|| isEmpty(x_num[i]) ||isEmpty(y_num[i])){
					continue;
				}
			
			HashMap<Object, Object> configmap=new HashMap<>();
			configmap.put("code", code);
			configmap.put("column", column[i]);
			configmap.put("ishiden", ishiden[i]);
			configmap.put("dplay", dplay[i]);
			configmap.put("text", text[i]);
			configmap.put("x_num", x_num[i]);
			configmap.put("y_num", y_num[i]);
			configmap.put("unit",unit[i]);
			configmap.put("background",background[i]);
			configmap.put("vaildnum",vaildnum[i]);
			configmap.put("font_size",font_size[i]);
			configmap.put("position", position[i]);
			configmap.put("px", String.valueOf(i));//排序
			feedRealService.updateConfig(code,configmap);
			
			}
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	
	public static boolean isEmpty(String str){
		return str==null || str.equals("") || str.equals("undefined") ? true : false;
	}
	
	
	@RequestMapping("/getFeedRealChart.do")
	public void getFeedRealChart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){

		
		try {
			List list=feedRealService.getFeedRealChart(paramsMap);
//			if(paramsMap.get("chartField")==null||"".equals(paramsMap.get("chartField"))){
//				paramsMap.put("chartField", "SUPPLYTEMP,RETURNTEMP");
//				paramsMap.put("chartTitle", "供水温度,回水温度");
//				paramsMap.put("chartType", "line,line");
//				paramsMap.put("chartPosition", "0,1");
//			}
			JSONObject jsonNode=EchartUtil.createLineColumnEchartData(list, paramsMap, "SCADATIME",null);
			PrintWriter out =null;
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.write(jsonNode.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	
	
	@RequestMapping("/getFeedRealSummary.do")
	public void getFeedRealSummary(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		Map map=feedRealService.getSummaryMap(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	

	/**
	 * 导出
	 */
	@RequestMapping(value = "/exportExcel.do",method = RequestMethod.GET)
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws Exception {
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		
		List titleList = super.getTableGridTitle(paramsMap);
		String sql = feedRealService.getSql(paramsMap);
		String FileName = "热源实时数据";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=feedReal.xls");
		
		ByteArrayOutputStreamExe os = new ByteArrayOutputStreamExe();
		os = exportExcelService.getFileStream(sql, titleList, FileName,paramsMap);
		try {
			//输出文件
			response.getOutputStream().write(os.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	

}

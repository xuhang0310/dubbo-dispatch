package com.github.xupei.simple.base;


import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.github.xupei.dubbo.api.IFeedService;
import com.github.xupei.dubbo.api.util.export.ByteArrayOutputStreamExe;
import com.github.xupei.dubbo.api.util.export.ExportExcelService;
import com.github.xupei.dubbo.api.util.export.ExportQRExcelService;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.util.DateChosenUtil;

@Controller
@RequestMapping(value="/feed")
public class FeedController  extends BaseController{
	
	@Autowired
	IFeedService feedService;
	@Autowired
	ExportExcelService exportExcelService;
	@Autowired
	ExportQRExcelService exportQRExcelService;
	/**
	 * 页面初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping("/getFeedList.do")
	public void getFeedList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=feedService.getFeedList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	
	@RequestMapping(value="/feedListLayer.do")
	public ModelAndView feedListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/bas/feed/feedList");
		
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
	@RequestMapping(value="/updateFeedList.do")
	public ModelAndView updateFeed(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======updateFeed========");
	      String feedid = paramsMap.get("feedid");
	      List list = null;
	      List orgList = null;
	      List gytList=null;
	      try {
	    	  list = feedService.editFeed(feedid);
	    	  orgList = feedService.orgList();
	    	  gytList=feedService.getGyt();
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView mad = new ModelAndView("/jsp/bas/feed/editFeed");
	      //将数据存入modelMap
	      mad.addObject("feed", list.get(0));
	      mad.addObject("orgobj", orgList);
	      mad.addObject("gytList", gytList);
	      return mad;
	  }
	
	/**
	 * 新增跳转方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value="/addFeedList.do")
	public ModelAndView addFeedList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======updateFeed========");
	      String feedid = paramsMap.get("feedid");
	      List orgList = null;
	      List gytList=null;
	      try {
	    	  orgList = feedService.orgList();
	    	  gytList=feedService.getGyt();
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView mad = new ModelAndView("/jsp/bas/feed/editFeed");
	      //将数据存入modelMap
	      mad.addObject("orgobj", orgList);
	      mad.addObject("gytList", gytList);
	      return mad;
	  }
	
	
	/**
	 * 修改
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/editFeed.do")
		public void editFeed(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, String> paramsMap){
		
		try {
	          
			feedService.editFeed(paramsMap);
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
	@RequestMapping(value="/saveFeed.do")//,method=RequestMethod.POST
		public void saveFeed(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, String> paramsMap){
		
		try {
	          
			feedService.saveFeed(paramsMap);
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
	@RequestMapping(value="/deleteFeed.do")
	public void deleteFeed(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String feedid=paramsMap.get("feedid")+"";
			feedService.deleteFeed(feedid);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}
		
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
		String sql = feedService.getSql(paramsMap);
		String FileName = "热源基础信息";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=feed.xls");
		
		ByteArrayOutputStreamExe os = new ByteArrayOutputStreamExe();
		os = exportExcelService.getFileStream(sql, titleList, FileName,paramsMap);
		try {
			//输出文件
			response.getOutputStream().write(os.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 批量导出二维码图片信息到excel
	 */
	@RequestMapping(value = "/exportQRExcel.do",method = RequestMethod.GET)
	public void exportQRExcel(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws Exception {
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		
		List titleList = super.getTableGridTitle(paramsMap);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename="+URLEncoder.encode("热源二维码基础信息.xls","UTF-8"));
		ByteArrayOutputStreamExe os = new ByteArrayOutputStreamExe();
		// fildName 记录需要导出基础信息中的唯一标识主键字段名  比如 热源基础信息 中把“热源名称”作为显示主键，
		//则 fildName="feedname";
		String fildName="feedname";
		os = exportQRExcelService.getQRFileStream(feedService.getSql(paramsMap),titleList,paramsMap,fildName,"REXX");
		try {
			//输出文件
			response.getOutputStream().write(os.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@RequestMapping("/getFeedSummary.do")
	public void getFeedSummary(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=feedService.getSummaryMap(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	    

}

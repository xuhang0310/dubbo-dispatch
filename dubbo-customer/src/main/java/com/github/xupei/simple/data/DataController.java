package com.github.xupei.simple.data;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import com.github.xupei.dubbo.api.data.IDataService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.shiro.ShiroUser;

@Controller
@RequestMapping(value="/data")
public class DataController  extends BaseController{
	
	@Autowired
	IDataService dataService;
	
	/**
	 * 页面初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping("/getDataList.do")
	public void getDataList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		//System.out.println(user.getLoginName()+"+++++++++username");
		paramsMap.put("LoginName", user.getLoginName());
		Map map=dataService.getDataList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	
	@RequestMapping(value="/dataListLayer.do")
	public ModelAndView dataListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		paramsMap.put("LoginName", user.getLoginName());
		
		ModelAndView model=new ModelAndView();
		//得到当前值班人信息和登录人信息，判断权限
		List dsi = null;
		try {
			dsi = dataService.findDqZbr(paramsMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dsi==null||dsi.size()==0){
			model.addObject("ErrorMsg", "当前没有人在值班！");
			model.setViewName("/jsp/data/errormsgCopy");
			return model;
		}  
		String schedulerid = null;//值班序号
		//验证权限
		String watchcode=null;String watchprincipal=null;
		if(dsi!=null&&dsi.size()>0){
			 HashMap map = (HashMap)dsi.get(0);
			 watchcode = String.valueOf(map.get("WATCHCODE"));
			 watchprincipal = String.valueOf(map.get("WATCHPRINCIPAL"));
			 schedulerid = String.valueOf(map.get("SCHEDULERID"));
			 
		}
		if((","+watchcode+","+watchprincipal+",").indexOf(","+user.getLoginName()+",")==-1){
			List zbrList=null;
			try {
				zbrList = dataService.getZbrListByWatchcode(watchcode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addObject("zbrList", zbrList);
			model.setViewName("/jsp/data/error");
			return model; 
		}
		
		String maxorder = null;
		try {
			maxorder = dataService.getMaxSorderBySchedulerid(schedulerid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		int selectmaxsorder = null!=maxorder && !"".equals(maxorder) && !maxorder.equals("null")?Integer.valueOf(maxorder):1;
 		
		//设置默认的开始和结束时间
		model.addObject("startdate", this.getDate1());
		model.addObject("enddate", this.getDate2());
		model.addObject("schedulerid", schedulerid);//班组序号
		model.addObject("selectmaxsorder", selectmaxsorder);//序号
		model.setViewName("/jsp/data/dataList");
		
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
	@RequestMapping(value="/updateDataList.do")
	public ModelAndView updateData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======updateDataTeam========");
	      String codeid = paramsMap.get("codeid");
	      List list = null;
	      List orgList = null;
	      List bplo = null;
	      List sdate = null;
	      try {
	    	  list = dataService.editData(codeid);
	    	  orgList = dataService.orgList();
	    	  bplo = dataService.suloList();
	    	  sdate = dataService.select_sysdate();
	    	  
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView mad = new ModelAndView("/jsp/data/editData");
	      //将数据存入modelMap
	      mad.addObject("", list.get(0));
	      mad.addObject("orgobj", orgList);
	      mad.addObject("bplo", bplo);
	      mad.addObject("sdate", sdate.get(0));
	      return mad;
	  }
	
	/**
	 * 新增值班明细跳转方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value="/addDataList.do")
	public ModelAndView addDataList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======addData========");
	      String selectmaxsorder = paramsMap.get("selectmaxsorder");
	      String schedulerid = paramsMap.get("schedulerid");
	      List orgList = null;
	      List bplo = null;
	      List sdate = null;
	      try {
	    	  orgList = dataService.orgList();
	    	  bplo = dataService.suloList();
	    	  sdate = dataService.select_sysdate();
	    	  
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView mad = new ModelAndView("/jsp/data/editData");
	      //将数据存入modelMap
	      mad.addObject("orgobj", orgList);
	      mad.addObject("bplo", bplo);
	      mad.addObject("sdate", sdate.get(0));
	      mad.addObject("selectmaxsorder", selectmaxsorder);
	      mad.addObject("schedulerid", schedulerid);
	      return mad;
	  }
	
	/**
	 * 新增重大事件跳转方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value="/addZDDataList.do")
	public ModelAndView addZDDataList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======addZDDataList========");
	      String selectmaxsorder = paramsMap.get("selectmaxsorder");
	      String schedulerid = paramsMap.get("schedulerid");
		
	      ModelAndView mad = new ModelAndView("/jsp/data/editZDData");
	      //将数据存入modelMap
	      mad.addObject("selectmaxsorder", selectmaxsorder);
	      mad.addObject("schedulerid", schedulerid);
	      return mad;
	  }
	
	
	/**
	 * 修改值班明细
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/editData.do")
	public void editData(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, String> paramsMap){
		
		try {
			//paramsMap.put("principal", principal);
			//paramsMap.put("watch", watch);
			
			dataService.editData(paramsMap);
			JsonUtil.returnnBaseJson(true, "修改成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, e.getLocalizedMessage(), response);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 新增值班明细
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/saveData.do")//,method=RequestMethod.POST
	public void saveData(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, String> paramsMap){
		
		try {
	          
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
			paramsMap.put("loginid", user.getId());
			
			dataService.saveData(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, e.getLocalizedMessage(), response);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 新增重大事件
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/saveZDData.do")//,method=RequestMethod.POST
	public void saveZDData(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, String> paramsMap){
		
		try {
	          
			/*String[] principal2=request.getParameterValues("principal");
			String[] watch2=request.getParameterValues("watch");
			String principal="";
			String watch="";
			for (int i = 0; i < principal2.length; i++) {
				principal+=principal2[i]+",";
			}
			for (int i = 0; i < watch2.length; i++) {
				watch+=watch2[i]+",";
			}
			paramsMap.put("principal", principal);
			paramsMap.put("watch", watch);*/
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
			paramsMap.put("loginName", user.getLoginName());
			
			dataService.saveZDData(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, e.getLocalizedMessage(), response);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/deleteData.do")
	public void deleteData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String codeid=paramsMap.get("codeid")+"";
			dataService.deleteData(codeid);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, e.getLocalizedMessage(), response);
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
		cal.add(Calendar.DATE,   1);
	    String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		return yesterday;
	}
	
	
	    

}

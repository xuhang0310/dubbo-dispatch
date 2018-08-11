package com.github.xupei.simple.data;


import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.xupei.dubbo.api.data.IDataIndexService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.shiro.ShiroUser;

/**
 * 调度值班
 * @author wjh
 *
 */
@Controller
@RequestMapping(value="/dataIndex")
public class DataIndexController  extends BaseController{
	
	@Autowired
	IDataIndexService dataIndexService;
	
	/**
	 * 页面初始化方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping("/getDataIndexList.do")
	public void getDataIndexList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		//System.out.println(user.getLoginName()+"+++++++++username");
		paramsMap.put("LoginName", user.getLoginName());
		Map map=dataIndexService.getDataIndexList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	
	@RequestMapping(value="/dataIndexListLayer.do")
	public ModelAndView dataIndexListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		paramsMap.put("LoginName", user.getLoginName());
		
		ModelAndView model=new ModelAndView();
		//得到当前值班人信息和登录人信息，判断权限
		List dsi = null;
		try {
			dsi = dataIndexService.findDqZbr(paramsMap);
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
		String teamid = null;//班组序号
		//验证权限
		String watchcode=null;String watchprincipal=null;
		if(dsi!=null&&dsi.size()>0){
			 HashMap map = (HashMap)dsi.get(0);
			 watchcode = String.valueOf(map.get("WATCHCODE"));
			 watchprincipal = String.valueOf(map.get("WATCHPRINCIPAL"));
			 schedulerid = String.valueOf(map.get("SCHEDULERID"));
			 teamid = String.valueOf(map.get("TEAMID"));
			 
		}
		if((","+watchcode+","+watchprincipal+",").indexOf(","+user.getLoginName()+",")==-1){
			List zbrList=null;
			try {
				zbrList = dataIndexService.getZbrListByWatchcode(watchcode);
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
			maxorder = dataIndexService.getMaxSorderBySchedulerid(schedulerid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		int selectmaxsorder = null!=maxorder && !"".equals(maxorder) && !maxorder.equals("null")?Integer.valueOf(maxorder):1;
 		//根据班组序号查询值班信息
 		List teamList = null;
 		try {
			teamList = dataIndexService.selectDataTeam(teamid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
		//设置默认的开始和结束时间
		model.addObject("startdate", this.getDate1());
		model.addObject("enddate", this.getDate2());
		model.addObject("schedulerid", schedulerid);//班组序号
		model.addObject("selectmaxsorder", selectmaxsorder);//序号
		model.addObject("teamList",teamList.get(0));
		model.setViewName("/jsp/data/dataIndexList");
		
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
	@RequestMapping(value="/updateDataIndexList.do")
	public ModelAndView updateDataIndex(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======updateDataTeam========");
	      String codeid = paramsMap.get("codeid");
	      List list = null;
	      List orgList = null;
	      List bplo = null;
	      List sdate = null;
	      try {
	    	  list = dataIndexService.editDataIndex(codeid);
	    	  orgList = dataIndexService.orgList();
	    	  bplo = dataIndexService.suloList();
	    	  sdate = dataIndexService.select_sysdate();
	    	  
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView mad = new ModelAndView("/jsp/data/editDataIndex");
	      //将数据存入modelMap
	      mad.addObject("index", list.get(0));
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
	@RequestMapping(value="/addDataIndexList.do")
	public ModelAndView addDataIndexList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======addDataIndex========");
	      String selectmaxsorder = paramsMap.get("selectmaxsorder");
	      String schedulerid = paramsMap.get("schedulerid");
	      List orgList = null;
	      List bplo = null;
	      List sdate = null;
	      try {
	    	  orgList = dataIndexService.orgList();
	    	  bplo = dataIndexService.suloList();
	    	  sdate = dataIndexService.select_sysdate();
	    	  
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView mad = new ModelAndView("/jsp/data/editDataIndex");
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
	@RequestMapping(value="/addZDDataIndexList.do")
	public ModelAndView addZDDataIndexList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======addZDDataIndexList========");
	      String selectmaxsorder = paramsMap.get("selectmaxsorder");
	      String schedulerid = paramsMap.get("schedulerid");
		
	      ModelAndView mad = new ModelAndView("/jsp/data/editZDDataIndex");
	      //将数据存入modelMap
	      mad.addObject("selectmaxsorder", selectmaxsorder);
	      mad.addObject("schedulerid", schedulerid);
	      return mad;
	  }
	
	/**
	 * 交接班跳转方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value="/addDataList.do")
	public ModelAndView addDataList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      System.out.println("=======addDataList========");
	      String selectmaxsorder = paramsMap.get("selectmaxsorder");
	      String schedulerid = paramsMap.get("schedulerid");
	      
	      ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
			paramsMap.put("LoginName", user.getLoginName());
			
			ModelAndView model=new ModelAndView();
			//得到当前值班人信息和登录人信息，判断权限 .当前值班人 按班组查询
			List dsi = null;
			List dst = null;//根据班组序号查询班组信息
			String teamid = null;//班组序号
			String startdate = null;
			try {
				dsi = dataIndexService.findDqZbr(paramsMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(dsi==null||dsi.size()==0){
				model.addObject("ErrorMsg", "未找到当前值班人信息！");
				model.setViewName("/jsp/data/errormsgCopy");
				return model;
			}
			HashMap map = (HashMap)dsi.get(0);
				
			teamid = String.valueOf(map.get("TEAMID"));
			startdate = String.valueOf(map.get("STARTDATE")).substring(0,11);
			List PrincipalList = null;
			List PrincipalSubList = null;
			//接班人集合
	     	List bzList = null;
			//根据teamid查询Datateam
			try {
				dst = dataIndexService.getDataTeamByTeamid(teamid);
				HashMap dstmap = (HashMap)dst.get(0);
				PrincipalList = dataIndexService.getZbrListByWatchcode(String.valueOf(dstmap.get("PRINCIPAL")));
				PrincipalSubList = dataIndexService.getZbrListByWatchcode(String.valueOf(dstmap.get("WATCH")));
				bzList = dataIndexService.getDataTeamList(user.getLoginName());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				 
	       model.addObject("PrincipalList",  PrincipalList);//dst.getPrincipal().split(",")); //负责人
	       model.addObject("PrincipalSubList", PrincipalSubList);//dst.getWatch().split(",")); //交班人
	       model.addObject("bzList", bzList);
	       model.addObject("startdate", startdate);
		
	       // ModelAndView mad = new ModelAndView("/jsp/data/editData");
	       model.setViewName("/jsp/data/editData");
	       //将数据存入modelMap
	       model.addObject("selectmaxsorder", selectmaxsorder);
	       model.addObject("schedulerid", schedulerid);
	       model.addObject("logname", user.getLoginName());
	       return model;
	  }
	
	/**
	 * 交接班
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/handleIndex.do")//,method=RequestMethod.POST
	public void handleIndex(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, String> paramsMap){
		
		try {
	          
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
			paramsMap.put("loginid", user.getId());
			
			String[] watchcodes=request.getParameterValues("watchcode");
			//String watchcode = Arrays.toString(watchcodes).replace(" ", "").replace("[", "").replace("]", "");
			String[] successorcodes=request.getParameterValues("successorcode");
			String watchcode="";
			String successorcode="";
			for (int i = 0; i < watchcodes.length; i++) {
				watchcode+=watchcodes[i];
				if ( watchcodes.length-1!=i) {
					watchcode+=",";
				}
			}
			for (int i = 0; i < successorcodes.length; i++) {
				successorcode+=successorcodes[i];
				if ( successorcodes.length-1!=i) {
					successorcode+=",";
				}
			}
			paramsMap.put("watchcode", watchcode);
			paramsMap.put("successorcode", successorcode);
			
			//dataIndexService.editData(paramsMap);//修改调度班次
			
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
		    String yesterday = new SimpleDateFormat( "yyyyMMddHHmm").format(date);
		    String pid = yesterday.substring(2, 12);
		    paramsMap.put("schedulernewid",pid);//根据时间生成班组序号
		    
			//dataIndexService.saveData(paramsMap);//新增调度交接班
			JsonUtil.returnnBaseJson(true, "交班成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, e.getLocalizedMessage(), response);
			e.getLocalizedMessage();
			//e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 修改值班明细
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/editDataIndex.do")
	public void editDataIndex(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, String> paramsMap){
		
		try {
			//paramsMap.put("principal", principal);
			//paramsMap.put("watch", watch);
			
			dataIndexService.editDataIndex(paramsMap);
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
	@RequestMapping(value="/saveDataIndex.do")//,method=RequestMethod.POST
	public void saveDataIndex(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, String> paramsMap){
		
		try {
	          
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
			paramsMap.put("loginid", user.getId());
			
			dataIndexService.saveDataIndex(paramsMap);
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
	@RequestMapping(value="/saveZDDataIndex.do")//,method=RequestMethod.POST
	public void saveZDDataIndex(HttpServletRequest request, HttpServletResponse response,
				@RequestParam Map<String, String> paramsMap){
		
		try {
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
			paramsMap.put("loginName", user.getLoginName());
			
			dataIndexService.saveZDDataIndex(paramsMap);
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
	@RequestMapping(value="/deleteDataIndex.do")
	public void deleteDataIndex(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String codeid=paramsMap.get("codeid")+"";
			dataIndexService.deleteDataIndex(codeid);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, e.getLocalizedMessage(), response);
		}
		
	}
	
	/**
     * 验证密码
     */
	@RequestMapping(value="/doValidPassword.do")
    public void doValidPassword(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			String uname=paramsMap.get("uname");
			String password=paramsMap.get("password");
			String fzr=paramsMap.get("fzr");
			paramsMap.put("uname", uname);
			paramsMap.put("password", password);
			paramsMap.put("fzr", fzr);
			
			int count = dataIndexService.validPassword(paramsMap);
			if (count>0) {
				JsonUtil.returnnBaseJson(true, "密码错误!请重新输入...", response);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, e.getLocalizedMessage(), response);
		}
    	
    }
	
	/**
     * 根据所选班组查询
     */
	@RequestMapping(value="/getFZRListByTeamid.do")
    public void getFZRListByTeamid(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
			
			paramsMap.put("teamid", paramsMap.get("teamid"));
			
			List dst = null;//根据班组序号查询班组信息
			List PrincipalList = null;
			//根据teamid查询Datateam
			try {
				dst = dataIndexService.getDataTeamByTeamid(paramsMap.get("teamid"));
				HashMap dstmap = (HashMap)dst.get(0);
				PrincipalList = dataIndexService.getZbrListByWatchcode(String.valueOf(dstmap.get("PRINCIPAL")));
				
				JsonUtil.returnListJson(PrincipalList, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, e.getLocalizedMessage(), response);
		}
    	
    }
	
	/**
     * 根据所选班组查询
     */
	@RequestMapping(value="/getZBRListByTeamid.do")
    public void getZBRListByTeamid(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
			
			paramsMap.put("teamid", paramsMap.get("teamid"));
			
			List dst = null;//根据班组序号查询班组信息
			List PrincipalSubList = null;
			//根据teamid查询Datateam
			try {
				dst = dataIndexService.getDataTeamByTeamid(paramsMap.get("teamid"));
				HashMap dstmap = (HashMap)dst.get(0);
				PrincipalSubList = dataIndexService.getZbrListByWatchcode(String.valueOf(dstmap.get("WATCH")));
				 
				JsonUtil.returnListJson(PrincipalSubList, response);
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

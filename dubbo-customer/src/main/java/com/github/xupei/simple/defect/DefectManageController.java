package com.github.xupei.simple.defect;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import com.github.xupei.dubbo.api.defect.IDefectManageService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.shiro.ShiroUser;
/**
 * 缺陷管理
 * @author liujiefeng
 *
 */

@Controller
@RequestMapping(value="/defect")
public class DefectManageController extends BaseController {
	
	@Autowired
	IDefectManageService defectManageService;

	@RequestMapping("/getDefectList.do")
	public void getDefectList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=defectManageService.getDefectList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}

	@RequestMapping("/getDefectTypeList.do")
	public void getDefectTypeList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=defectManageService.getDefectTypeList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}

	
	
	
	@RequestMapping(value="/saveDefect.do",method=RequestMethod.POST)
	public void saveDefect(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap,@RequestParam("fname") MultipartFile file){
		ShiroUser user=null;
		String orgid="";
		String username="";
		String userid="";
		String filename="";
		JSONObject jsonobj = new JSONObject();
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=utf-8");
			out=response.getWriter();			
			user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
			username=user.getLoginName();
			orgid=defectManageService.getOrgId(username);
			userid=defectManageService.getUid(username);
			if(!file.isEmpty()){
				//上传文件路径
				String path=request.getServletContext().getRealPath("/defectfile/");
				//上传文件名：当前时间+文件名
				Date date=new Date(); 
				SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss"); 
				String time=formatter.format(date); 
				//上传文件名
				filename=time+file.getOriginalFilename();
				File filepath = new File(path,filename);
				 //判断路径是否存在，如果不存在就创建一个
	            if (!filepath.getParentFile().exists()) { 
	                filepath.getParentFile().mkdirs();
	            }
	            //将上传文件保存到一个目标文件当中
	            file.transferTo(new File(path + File.separator + filename));
			}
			defectManageService.saveDefect(paramsMap,userid,orgid,filename);
		
			jsonobj.put("status", "y");
			jsonobj.put("info", "保存成功");
			out.write(jsonobj.toString());
		
			//JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		//	JsonUtil.returnnBaseJson(false, "保存失败", response);
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
	

	@RequestMapping(value="/addDefectType.do",method=RequestMethod.POST)
	public void addDefectType(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			defectManageService.addDefectType(paramsMap);
			
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	
	
	@RequestMapping(value="/defectLayer.do")
	public ModelAndView defectLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		List defectType=null;
		List orgname=null;
		String status=paramsMap.get("status");
		try {
			defectType=defectManageService.getDefectType();
			orgname=defectManageService.getOrgName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/defect/defectList");	
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		model.addObject("defectType", defectType);
		model.addObject("orgname", orgname);
		model.addObject("status",status);
		return model;
	}
	
	@RequestMapping(value="/defectTypeLayer.do")
	public ModelAndView defectTypeLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/defect/defectType");	
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );

		return model;
	}
	
	@RequestMapping(value="/deleteDefect.do")
	public void deleteDefect(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("id");
			defectManageService.deleteDefect(id);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}
		
	}
	

	@RequestMapping(value="/deleteDefectType.do")
	public void deleteDefectType(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("id");
			defectManageService.deleteDefectType(id);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="/toAddPage.do")
	public ModelAndView toAddPage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		  List defectType=null;
	      ModelAndView ParamMap = new ModelAndView("/jsp/defect/editDefect");
	      try {
			defectType=defectManageService.getDefectType();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      ParamMap.addObject("defectType", defectType);
	      return ParamMap;
	  }

	
	@ResponseBody
	@RequestMapping(value="/selectAllDefect.do")
	public ModelAndView selectAllDefect(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		List dtype=null;
		  try {
			 dtype=defectManageService.selectAllDefect(paramsMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      ModelAndView model = new ModelAndView();
	      model.addObject("dtype", dtype.get(0));
	      model.setViewName("/jsp/defect/editDefectType");	
	      return model;
	  }
	
	
	@ResponseBody
	@RequestMapping(value="/toEditDefect.do")
	public ModelAndView toEditDefect(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
	      ModelAndView ParamMap = new ModelAndView("/jsp/defect/editDefectType");
	      return ParamMap;
	  }

	@RequestMapping(value="/getProjectList.do")
	public void getProjectList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("stationtype");
		//	defectManageService.deleteParam(id);
			List projectList=defectManageService.getProjectList(id);
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
	@RequestMapping(value="/findDefect.do")
	public ModelAndView findDefect(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws IOException{
		  request.setCharacterEncoding("utf-8");
		  response.setContentType("text/html;charset=utf-8");
	      String id = paramsMap.get("id");
	     
	      List defectlist = null;
	      List defectType=null;	    
	      try {
	    	  defectlist = defectManageService.findDefect(id);
	    	  defectType=defectManageService.getDefectType();
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
	   
	      ModelAndView ParamMap = new ModelAndView("/jsp/defect/editDefect");
	      //将数据存入modelMap
	      ParamMap.addObject("defectlist", defectlist.get(0));
	      ParamMap.addObject("defectType", defectType);
	      return ParamMap;
	  }
	
	@ResponseBody
	@RequestMapping(value="/findDefectDeal.do")
	public ModelAndView findDefectDeal(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws IOException{
		  request.setCharacterEncoding("utf-8");
		  response.setContentType("text/html;charset=utf-8");
	      String id = paramsMap.get("id");
	     
	      List defectlist = null;
	      List defectType=null;	    
	      List tjr=null;
	       
	      try {
	    	  defectlist = defectManageService.findDefectDeal(id);
	    	  defectType=defectManageService.getDefectType();
	    	  tjr=defectManageService.getTjr(id);//提交人
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
	   
	      ModelAndView ParamMap = new ModelAndView("/jsp/defect/editDefectDeal");
	      //将数据存入modelMap
	      ParamMap.addObject("defectlist", defectlist.get(0));
	      ParamMap.addObject("defectType", defectType);
	      ParamMap.addObject("tjr",tjr.get(0));
	      return ParamMap;
	  } 


	@ResponseBody
	@RequestMapping(value="/findDefectSolve.do")
	public ModelAndView findDefectSolve(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws IOException{
		  request.setCharacterEncoding("utf-8");
		  response.setContentType("text/html;charset=utf-8");
	      String id = paramsMap.get("id");
	     
	      List defectlist = null;
	      List defectType=null;	    
	      List tjr=null;
	      List dealman=null;
	      try {
	    	  defectlist = defectManageService.findDefectDeal(id);
	    	  defectType=defectManageService.getDefectType();
	    	  tjr=defectManageService.getTjr(id);//提交人
	    	  dealman=defectManageService.getDealMan(id);//处理人g
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
	   
	      ModelAndView ParamMap = new ModelAndView("/jsp/defect/editDefectSolve");
	      //将数据存入modelMap
	      ParamMap.addObject("defectlist", defectlist.get(0));
	      ParamMap.addObject("defectType", defectType);
	      ParamMap.addObject("tjr",tjr.get(0));
	      if(!dealman.isEmpty()){
	      ParamMap.addObject("dealman",dealman.get(0)); 
	      }
	      return ParamMap;
	  } 
	

	@ResponseBody
	@RequestMapping(value="/showDefectSolve.do")
	public ModelAndView showDefectSolve(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws IOException{
		  request.setCharacterEncoding("utf-8");
		  response.setContentType("text/html;charset=utf-8");
	      String id = paramsMap.get("id");
	     
	      List defectlist = null;
	      List defectType=null;	    
	      List tjr=null;
	      List dealman=null;
	      try {
	    	  defectlist = defectManageService.findDefectDeal(id);
	    	  defectType=defectManageService.getDefectType();
	    	  tjr=defectManageService.getTjr(id);//提交人
	    	  dealman=defectManageService.getDealMan(id);//处理人g
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
	   
	      ModelAndView ParamMap = new ModelAndView("/jsp/defect/showDefectSolve");
	      //将数据存入modelMap
	      ParamMap.addObject("defectlist", defectlist.get(0));
	      ParamMap.addObject("defectType", defectType);
	      ParamMap.addObject("tjr",tjr.get(0));
	      if(!dealman.isEmpty()){
	      ParamMap.addObject("dealman",dealman.get(0)); 
	      }
	      return ParamMap;
	  } 
	
	
	
	@RequestMapping(value="/updateDefectType.do")
	public void updateDefectType(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	
		try {
		
			defectManageService.updateDefectType(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	


	@RequestMapping(value="/updateDefect.do")
	public void updateDefect(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap,@RequestParam("fname") MultipartFile file){
		JSONObject jsonobj = new JSONObject();
		PrintWriter out = null;
		String filename="";
		try {
			response.setContentType("text/html;charset=utf-8");
			out=response.getWriter();
			if(!file.isEmpty()){
				//上传文件路径
				String path=request.getServletContext().getRealPath("/defectfile/");
				//上传文件名：当前时间+文件名
				Date date=new Date(); 
				SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss"); 
				String time=formatter.format(date); 
				//上传文件名
				filename=time+file.getOriginalFilename();
				File filepath = new File(path,filename);
				 //判断路径是否存在，如果不存在就创建一个
	            if (!filepath.getParentFile().exists()) { 
	                filepath.getParentFile().mkdirs();
	            }
	            //将上传文件保存到一个目标文件当中
	            file.transferTo(new File(path + File.separator + filename));
			}
			defectManageService.updateDefect(paramsMap,filename);
			jsonobj.put("status", "y");
			jsonobj.put("info", "保存成功");
			out.write(jsonobj.toString());
	
			//JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		//	JsonUtil.returnnBaseJson(false, "保存失败", response);
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
	
	

	@RequestMapping(value="/dealDefect.do")
	public void dealDefect(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		JSONObject jsonobj = new JSONObject();
		PrintWriter out = null;
		ShiroUser user=null;
		String username="";
		String userid="";
		try {
			response.setContentType("text/html;charset=utf-8");
			out=response.getWriter();
			user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
			username=user.getLoginName();
			userid=defectManageService.getUid(username);
			defectManageService.dealDefect(paramsMap,userid);
			jsonobj.put("status", "y");
			jsonobj.put("info", "保存成功");
			out.write(jsonobj.toString());
	
			//JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		//	JsonUtil.returnnBaseJson(false, "保存失败", response);
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
	
	

	@RequestMapping(value="/solveDefect.do")
	public void solveDefect(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		JSONObject jsonobj = new JSONObject();
		PrintWriter out = null;
		ShiroUser user=null;
		String username="";
		String userid="";
		try {
			response.setContentType("text/html;charset=utf-8");
			out=response.getWriter();
			user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
			username=user.getLoginName();
			userid=defectManageService.getUid(username);
			defectManageService.solveDefect(paramsMap,userid);
			jsonobj.put("status", "y");
			jsonobj.put("info", "保存成功");
			out.write(jsonobj.toString());
	
			//JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		//	JsonUtil.returnnBaseJson(false, "保存失败", response);
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
	
	
	

	@RequestMapping(value="/downLoad.do")
	public ResponseEntity<byte[]>  downLoad(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws IOException  {
		//{filename=20180621205224滴滴电子发票.pdf}
		String filename=paramsMap.get("filename");
		   //下载文件路径
        String path = request.getServletContext().getRealPath("/defectfile/");
        File file = new File(path + File.separator + filename);
        HttpHeaders headers = new HttpHeaders();  
        //下载显示的文件名，解决中文名称乱码问题  
        String downloadFielName =filename; //new String(filename.getBytes("UTF-8"),"iso-8859-1");
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFielName); 
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
                headers, HttpStatus.CREATED);  	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

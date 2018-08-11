package com.github.xupei.simple.picture;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;


import com.github.xupei.dubbo.api.IPictureService;
import com.github.xupei.dubbo.api.IUserService;
import com.github.xupei.dubbo.bean.SysUser;
import com.github.xupei.simple.json.JsonResProcessor;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.shiro.ShiroUser;
import javax.annotation.Resource;
import javax.imageio.ImageIO;


@Controller
@RequestMapping(value="/picture")
public class PictureController {
	
	@Autowired
	IPictureService pictureService;
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	
	
	@RequestMapping("/getPictureList.do")
	public void getUserList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=pictureService.getPictureList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	
	@RequestMapping("/getPicturByName.do")
	public void getPicturByName(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=pictureService.getPictureList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	

	
	

    @ResponseBody
	@RequestMapping(value="/savePicture.do",method=RequestMethod.POST)
	public void savePicture(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap,@RequestParam(value = "pic_fname", required = false)MultipartFile file,
			@RequestParam(value = "pic_name", required = false)String pic_name){
		System.out.println("============");
		try {
		    String path = request.getServletContext().getRealPath("/uplode");
			String filename=file.getOriginalFilename();
			File filepath=new File(path,filename);
			 //判断路径是否存在，如果不存在就创建一个
	           if (!filepath.getParentFile().exists()) { 
	               filepath.getParentFile().mkdirs();
	           }
	           //将上传文件保存到一个目标文件当中
	           if(!(filename.trim().isEmpty()&&pic_name.trim().isEmpty())){
	          
			  pictureService.savePicture(pic_name,filename);
			  file.transferTo(new File(path + File.separator + filename));
			JsonUtil.returnnBaseJson(true, "上传成功", response);
	           }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "上传失败", response);
			System.out.println("上传遇到问题！");
			e.printStackTrace();
		}
		
	}
	
    @ResponseBody
   	@RequestMapping(value="/savePictureById.do",method=RequestMethod.POST)
   	public void savePictureById(HttpServletRequest request, HttpServletResponse response,HttpSession session,
   			@RequestParam Map<String, String> paramsMap,@RequestParam(value = "pic_fname", required = false)MultipartFile file,
   			@RequestParam(value = "pic_name", required = false)String pic_name){
   		System.out.println("============");
   		OutputStream os=null;
   		try {
   			String id=paramsMap.get("id");
			String uploadPath = "uplode";//上传路径，相对路径
		  //  String path = request.getServletContext().getRealPath("/uplode/");
			String path=session.getServletContext().getRealPath(uploadPath);//真实的路径，也就是绝对路径
			String filename=file.getOriginalFilename();
			
	           //将上传文件保存到一个目标文件当中
	           if(!(filename.trim().isEmpty()&&pic_name.trim().isEmpty())){
	        	   String des=path+"\\"+file.getOriginalFilename();
	        	   os = new FileOutputStream(des);
	        	   Image image = ImageIO.read(file.getInputStream());
	        	   int width = image.getWidth(null);
	        	   int height = image.getHeight(null);
	        	   int rate1 = width/WIDTH;
	        	   int rate2 = height/HEIGHT;
		   			
		   			int rate = 0;
	   			if(rate1>rate2) {
	   				rate = rate1;
	   			}else {
	   				rate = rate2;
	   			}
	   	//		int newWidth = width/rate;
	   //		int newHeight = height/rate;
	   			int newWidth;
	   			int newHeight;
	   			if(rate>=1){
	   				newWidth = width/rate;
	   				newHeight = height/rate;
	   			}else{
	   				newWidth=width;
	   				newHeight = height;
	   			}
	   			BufferedImage bufferedImage = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_RGB);
	   			bufferedImage.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, image.SCALE_SMOOTH), 0, 0, null);
	   			String imageType = file.getContentType().substring(file.getContentType().indexOf("/")+1);
	   			ImageIO.write(bufferedImage, imageType, os);  	       	   
	   		    pictureService.savePictureById(pic_name,filename,id);
			   JsonUtil.returnnBaseJson(true, "上传成功", response);
	           }
   			
   		
   		} catch (Exception e) {
   			// TODO Auto-generated catch block
   			JsonUtil.returnnBaseJson(false, "上传失败", response);
   			System.out.println("上传遇到问题！");
   			e.printStackTrace();
   		}finally{
			if(os!=null) {
				try {
					os.close();
				}catch(Exception e2) {
					e2.printStackTrace();
				}
			}
   		}
   	}

    @ResponseBody
	@RequestMapping(value="/addPicture.do",method=RequestMethod.POST)
	public void addPicture(HttpServletRequest request, HttpServletResponse response,HttpSession session,
			@RequestParam Map<String, String> paramsMap,@RequestParam(value = "pic_fname", required = false)CommonsMultipartFile file,
			@RequestParam(value = "pic_name", required = false)String pic_name){
		OutputStream os=null;
		try {
			String uploadPath = "uplode";//上传路径，相对路径
		  //  String path = request.getServletContext().getRealPath("/uplode/");
			String path=session.getServletContext().getRealPath(uploadPath);//真实的路径，也就是绝对路径
			String filename=file.getOriginalFilename();
			
	           //将上传文件保存到一个目标文件当中
	           if(!(filename.trim().isEmpty()&&pic_name.trim().isEmpty())){
	        	   String des=path+"\\"+file.getOriginalFilename();
	        	   os = new FileOutputStream(des);
	        	   Image image = ImageIO.read(file.getInputStream());
	        	   int width = image.getWidth(null);
	        	   int height = image.getHeight(null);
	        	   int rate1 = width/WIDTH;
	        	   int rate2 = height/HEIGHT;
		   			
		   			int rate = 0;
	   			if(rate1>rate2) {
	   				rate = rate1;
	   			}else {
	   				rate = rate2;
	   			}
	   			
	   	//		int newWidth = width/rate;
	   //		int newHeight = height/rate;
	   			int newWidth;
	   			int newHeight;
	   			if(rate>=1){
	   				newWidth = width/rate;
	   				newHeight = height/rate;
	   			}else{
	   				newWidth=width;
	   				newHeight = height;
	   			}
	   			
	   			
	   			BufferedImage bufferedImage = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_RGB);
	   			
	   			bufferedImage.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, image.SCALE_SMOOTH), 0, 0, null);
	   			
	   			String imageType = file.getContentType().substring(file.getContentType().indexOf("/")+1);
	   			
	   			ImageIO.write(bufferedImage, imageType, os);  	       	   
	        	pictureService.savePicture(pic_name,filename);
			JsonUtil.returnnBaseJson(true, "上传成功", response);
	           }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "上传失败", response);
			System.out.println("上传遇到问题！");
			e.printStackTrace();
		}finally{

			if(os!=null) {
				try {
					os.close();
				}catch(Exception e2) {
					e2.printStackTrace();
				}
			}
		
		}
		
	}
	
	
	
	
	@RequestMapping(value="/deletePicture.do")
	public void deletePicture(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("id")+"";
			pictureService.deletePicture(id);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
			e.printStackTrace();
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="/updatePicture.do")
	public ModelAndView updatePicture(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      String id = paramsMap.get("roleid");
	      List list = null;
	      try {
	    	  list = pictureService.getPicture(id);
	    	  System.out.println(list.size());
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView rolemap = new ModelAndView("/jsp/picture/editPicture");
	      //将数据存入modelMap
	      rolemap.addObject("picture", list.get(0));
	      return rolemap;
	  }
	
	
	
	
	
	
	
	
	
	
	    

}

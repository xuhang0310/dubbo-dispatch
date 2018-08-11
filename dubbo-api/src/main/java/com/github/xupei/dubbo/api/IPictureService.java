package com.github.xupei.dubbo.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.xupei.dubbo.bean.SysUser;

public interface  IPictureService {

	public Map getPictureList(Map<String,String> paramMap)  throws Exception;
	
	
	
	public List getPictureRightList(String userid) throws Exception;
	
	
	public void savePicture(String pic_name,String filename)throws Exception;
	
	public void deletePicture(String userid) throws Exception;

	public void thumbnail(CommonsMultipartFile file, String uploadPath,
			String path)throws Exception;



	public void updatePicture(String id)throws Exception;



	public List getPicture(String id)throws Exception;



	public void savePictureById(String pic_name, String filename,String id)throws Exception;

	
	
	
}

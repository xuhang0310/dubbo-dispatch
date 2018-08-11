package com.github.xupei.dubbo.api;

import java.util.List;
import java.util.Map;

import com.github.xupei.dubbo.bean.SysUser;

public interface  ISysConfigService {

	public Map getSysConfigList(Map<String,String> paramMap)  throws Exception;

	
	public void savePage(Map<String,String> map)throws Exception;
	
	public void deletePage(String id) throws Exception;


	public List findPage(String id)throws Exception;

	public void editPage(Map<String, String> paramsMap)throws Exception;


	public List getMenuListForTree(Map<String, String> paramsMap)throws Exception;
	
	
}

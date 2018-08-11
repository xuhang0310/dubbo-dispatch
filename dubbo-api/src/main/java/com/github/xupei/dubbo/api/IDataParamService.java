package com.github.xupei.dubbo.api;

import java.util.List;
import java.util.Map;

import com.github.xupei.dubbo.bean.SysUser;

public interface  IDataParamService {

	public Map getDataParamList(Map<String,String> paramMap)  throws Exception;

	
	public void saveParam(Map<String,String> map)throws Exception;
	
	public void deleteParam(String id) throws Exception;


	public List findParam(String id)throws Exception;

	public void editParam(Map<String, String> paramsMap)throws Exception;

	public List getlx()throws Exception;
	
	
}

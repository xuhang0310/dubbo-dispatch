package com.github.xupei.dubbo.api;

import java.util.List;
import java.util.Map;

import com.github.xupei.dubbo.bean.SysUser;

public interface  IBasMeterService {

	public Map getBasMeterList(Map<String,String> paramMap)  throws Exception;

	
	public void saveMeter(Map<String,String> map)throws Exception;
	
	public void deleteMeter(String id) throws Exception;


	public List findMeter(String id)throws Exception;

	public void editMeter(Map<String, String> paramsMap)throws Exception;

	public List getlx(String stationtype)throws Exception;


	public List selectAll()throws Exception;


	public List getProjectList(String id)throws Exception;
	
	
}

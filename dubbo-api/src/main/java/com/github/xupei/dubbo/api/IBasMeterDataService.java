package com.github.xupei.dubbo.api;

import java.util.List;
import java.util.Map;

import com.github.xupei.dubbo.bean.SysUser;

public interface  IBasMeterDataService {

	public Map getBasMeterList(Map<String,String> paramMap)  throws Exception;

	
	public void saveMeterNum(Map<String,String> map)throws Exception;
	
	public void deleteMeter(String id) throws Exception;


	public List findMeter(String id)throws Exception;

	public void editMeter(Map<String, String> paramsMap)throws Exception;

	public List getlx(String stationtype)throws Exception;


	public List selectAll()throws Exception;


	public List getProjectList(String id)throws Exception;


	public List getBasmers()throws Exception;


	public int getCount(String sj, String ids)throws Exception;


	public void insertNum(String sj, String id)throws Exception;
	
	
}

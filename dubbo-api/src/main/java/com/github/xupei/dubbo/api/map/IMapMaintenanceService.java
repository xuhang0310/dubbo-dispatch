package com.github.xupei.dubbo.api.map;

import java.util.List;
import java.util.Map;

import com.github.xupei.dubbo.bean.SysUser;

public interface  IMapMaintenanceService {

	public Map getBasMeterList(Map<String,String> paramMap)  throws Exception;

	public String addNode(Map<String, String> paramsMap)throws Exception;

	public String addLine(Map<String, String> paramsMap)throws Exception;

	public String getFeedPointData()throws Exception;

	public String getNodePointData()throws Exception;

	public String getLineData()throws Exception;

	public void delPoint(Map<String, String> paramsMap)throws Exception;

	public void delLine(Map<String, String> paramsMap)throws Exception;

	public String getNodePointDataForEnergy()throws Exception;

	public boolean getIsDraw(String user)throws Exception;

	public List getFeedReal(Map<String, String> paramsMap)throws Exception;

	public List getFeedInfo(Map<String, String> paramsMap)throws Exception;

	public List getNodeReal(Map<String, String> paramsMap)throws Exception;

	public List getNodeInfo(Map<String, String> paramsMap)throws Exception;

	
	
	
}

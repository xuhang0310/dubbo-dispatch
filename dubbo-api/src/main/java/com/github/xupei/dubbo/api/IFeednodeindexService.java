package com.github.xupei.dubbo.api;

import java.util.List;
import java.util.Map;

import com.github.xupei.dubbo.bean.SysUser;

public interface  IFeednodeindexService {

	public Map getFeedNodeIndexList(Map<String,String> paramMap)  throws Exception;

	
	public void saveBase(Map<String,String> map)throws Exception;
	
	public void deleteZb(String id) throws Exception;


	public List findZb(String id)throws Exception;

	public void editZb(Map<String, String> paramsMap)throws Exception;

	public List getlx(String stationtype)throws Exception;


	public List findZbList()throws Exception;
	
	
}

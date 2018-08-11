package com.github.xupei.dubbo.api.data;

import java.util.List;
import java.util.Map;


public interface IDataDicService {

	public Map getDataDicList(Map<String,String> paramMap)  throws Exception;
	
	public List checkDataDic(Map<String,String> map) throws Exception;
	
	public List getDataDicRightList(String id) throws Exception;
	
	public void saveDataDic(Map<String,String> map)throws Exception;
	
	public void deleteDataDic(String id) throws Exception;

	public List editDataDic(String id) throws Exception;

	public List orgList() throws Exception;

	public void editDataDic(Map<String, String> paramMap) throws Exception;

	public List suloList() throws Exception;
	
	public List getDataDicTree(Map<String, String> paramsMap) throws Exception;
	
	
}

package com.github.xupei.dubbo.api;

import java.util.List;
import java.util.Map;


public interface IFeedService {

	public Map getFeedList(Map<String,String> paramMap)  throws Exception;
	
	public List getFeedAllList(Map<String,String> paramMap)  throws Exception;
	
	public List checkFeed(Map<String,String> map) throws Exception;
	
	public List getFeedRightList(String id) throws Exception;
	
	public void saveFeed(Map<String,String> map)throws Exception;
	
	public void deleteFeed(String id) throws Exception;

	public List editFeed(String id) throws Exception;

	public List orgList() throws Exception;

	public void editFeed(Map<String, String> paramMap) throws Exception;

	public String getSql(Map<String, String> paramsMap);

	public List getGyt()throws Exception;

	public Map getSummaryMap(Map<String, String> paramsMap)throws Exception;
	
	
}

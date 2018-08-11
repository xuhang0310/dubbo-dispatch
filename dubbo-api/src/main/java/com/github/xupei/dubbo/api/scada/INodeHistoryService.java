package com.github.xupei.dubbo.api.scada;

import java.util.List;
import java.util.Map;

public interface INodeHistoryService {
	
	public Map getNodeHistoryList(Map<String,String> paramMap)  throws Exception;
	
	public List getHistoryChart(Map<String,String> paramMap)  throws Exception;

	public String getSql(Map<String, String> paramsMap)  throws Exception;

	public Map getSummaryMap(Map<String, String> paramsMap)throws Exception;
	
}

package com.github.xupei.dubbo.api.scada;

import java.util.List;
import java.util.Map;

public interface IFeedHistoryService {
	public Map getFeedHistoryList(Map<String,String> paramMap)  throws Exception;
	public String getSql(Map<String,String> paramMap);
	
	public List getHistoryChart(Map<String,String> paramMap)  throws Exception;
	public Map getSummaryMap(Map<String, String> paramsMap)throws Exception;
}

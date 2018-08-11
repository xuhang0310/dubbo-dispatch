package com.github.xupei.dubbo.api.data;

import java.util.List;
import java.util.Map;


public interface IForecastCNQpayloadService {

	public Map getForecastCNQpayloadList(Map<String,String> paramMap)  throws Exception;

	public List getChart(Map<String, String> paramsMap)  throws Exception;

	public Object getNodeAllList(Map<String, String> paramsMap)  throws Exception;
	
	
}

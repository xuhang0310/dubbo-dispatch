package com.github.xupei.dubbo.api.scada;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

public interface IFeedRealService {
	public Map getFeedRealList(Map<String,String> paramMap)  throws Exception;

	public List getRealDataList(String id)throws Exception;

	public List getParamConfigList(String code)throws Exception;

	public String getImgUrl(String code)throws Exception;

	public List getParamConfigForUpdateArea(String code)throws Exception;

	public void update(String code, String name, String x, String y)throws Exception;

	public void delete(String code)throws Exception;

	public void updateConfig(String code, HashMap<Object, Object> configmap)throws Exception;

	public void saveWarnConfig(Map<String,String> paramMap) throws Exception;
	
	public List getWarnConfig(Map<String,String> paramMap) throws Exception;

	public List getFeedRealChart(Map<String, String> paramsMap)throws Exception;
	
	public List getWarningDetailEchart(Map<String, String> paramMap) throws Exception;

	public Map getSummaryMap(Map<String, String> paramsMap)throws Exception;

	public String getSql(Map<String, String> paramsMap);

	
}

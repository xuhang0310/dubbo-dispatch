package com.github.xupei.dubbo.api.scada;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface INodeRealService {
	
	public Map getNodeRealList(Map<String,String> paramMap)  throws Exception;

	public List getRealDataList(String id)throws Exception;

	public List getParamConfigList(String code)throws Exception;

	public String getImgUrl(String code)throws Exception;

	public List getParamConfigForUpdateArea(String code)throws Exception;

	public void update(String code, String name, String x, String y)throws Exception;

	public void delete(String code)throws Exception;

	public void updateConfig(String code, HashMap<Object, Object> configmap)throws Exception;

	public void saveWarnConfig(Map<String,String> paramMap) throws Exception;
	
	public List getWarnConfig(Map<String,String> paramMap) throws Exception;

	public Object getOrgList() throws Exception;

	public Object getNodeList() throws Exception;

	public Object getJzlxList() throws Exception;

	public Object getCnfsList() throws Exception;

	public List getProjectList(String orgcode)throws Exception;

	public List getNodeRealChart(Map<String, String> paramsMap)throws Exception;

	public Map getSummaryMap(Map<String, String> paramsMap)throws Exception;
	
}

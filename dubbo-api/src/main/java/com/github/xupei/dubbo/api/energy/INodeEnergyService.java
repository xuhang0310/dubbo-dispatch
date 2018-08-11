package com.github.xupei.dubbo.api.energy;

import java.util.List;
import java.util.Map;

public interface INodeEnergyService {
	public Map getEnergyList(Map<String,String> paramMap)  throws Exception;

	public List getNodeEnergyChart(Map<String, String> paramsMap)throws Exception;

	public List getOrg()throws Exception;

	public List getNode()throws Exception;

	public List getProjectList(String orgid)throws Exception;

	public Map getSummaryMap(Map<String, String> paramsMap)throws Exception;
	public String getSql(Map<String, String> paramMap);

	public Map getAssessList(Map<String, String> paramsMap)throws Exception;

	public List getChart(Map<String, String> paramsMap)throws Exception;

	public List getProjectListFeed(String orgid)throws Exception;

	public List getNodeEnergyPieChart(Map<String, String> paramsMap)throws Exception;

	public List getGraList()throws Exception;

	public void updateGradient(Map<String, String> paramsMap)throws Exception;
}

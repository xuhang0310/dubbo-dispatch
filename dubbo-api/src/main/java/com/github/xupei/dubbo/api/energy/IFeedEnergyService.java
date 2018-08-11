package com.github.xupei.dubbo.api.energy;

import java.util.List;
import java.util.Map;

public interface IFeedEnergyService {
	
	public Map getEnergyList(Map<String,String> paramMap)  throws Exception;
	
	public List getEnergyChart(Map<String,String> paramMap)  throws Exception;
	
	public Map getSummaryMap(Map<String, String> paramMap)throws Exception;
	
	public List getFeedList(Map<String, String> paramMap) throws Exception;

	public List getFeedEnergyPieChart(Map<String, String> paramsMap)throws Exception;

	public List getGraList()throws Exception;

	public void updateGradient(Map<String, String> paramsMap)throws Exception;

}

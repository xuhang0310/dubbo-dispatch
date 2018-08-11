package com.github.xupei.dubbo.api.scada;

import java.util.List;
import java.util.Map;


public interface IAlarmAnalysisService {

	public Map getAlarmAnalysisList(Map<String,String> paramMap)  throws Exception;
	
	public List checkAlarmAnalysis(Map<String,String> map) throws Exception;
	
	public List getAlarmAnalysisRightList(String id) throws Exception;
	
	public void saveAlarmAnalysis(Map<String,String> map)throws Exception;
	
	public void deleteAlarmAnalysis(String id) throws Exception;

	public List editAlarmAnalysis(String id) throws Exception;

	public List orgList() throws Exception;

	public void editAlarmAnalysis(Map<String, String> paramMap) throws Exception;

	public List suloList() throws Exception;
	
	
}

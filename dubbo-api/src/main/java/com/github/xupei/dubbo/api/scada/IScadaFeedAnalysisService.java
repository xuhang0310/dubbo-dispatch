package com.github.xupei.dubbo.api.scada;

import java.util.List;
import java.util.Map;


public interface IScadaFeedAnalysisService {

	public Map getDataTeamList(Map<String,String> paramMap)  throws Exception;
	
	public List getFeedAnalysisChart(Map<String,String> paramMap)  throws Exception;
	
	
}

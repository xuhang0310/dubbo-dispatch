package com.github.xupei.dubbo.api.dispatch;

import java.util.List;
import java.util.Map;


public interface IApplyDispatchService {

	public Map getDispatchList(Map<String,String> paramMap)  throws Exception;

	public List getTaskLogPath(String id)  throws Exception;

	public List getDaiShenPiList(String id)  throws Exception;
	
	
}

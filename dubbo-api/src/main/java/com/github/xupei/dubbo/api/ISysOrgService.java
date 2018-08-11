package com.github.xupei.dubbo.api;

import java.util.List;
import java.util.Map;

public interface ISysOrgService {
	
	public Map getOrgList(Map<String,String> paramMap)  throws Exception;
	
	public List getOrgListForTree(Map<String,String> paramMap) throws Exception;
	
	public List getAllOrgList(Map<String,String> paramMap)  throws Exception;

}

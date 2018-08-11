package com.github.xupei.dubbo.api.defect;

import java.util.List;
import java.util.Map;


public interface IMailWarnConfigService {

	public Map getMailWarnConfigList(Map<String,String> paramMap)  throws Exception;
	
	public void saveMailWarnConfig(Map<String,String> map)throws Exception;
	
	public void deleteMailWarnConfig(String id) throws Exception;

	public List editMailWarnConfig(String id) throws Exception;

	public List orgList() throws Exception;

	public void editMailWarnConfig(Map<String, String> paramMap) throws Exception;

	public List suloList() throws Exception;
	
	
}

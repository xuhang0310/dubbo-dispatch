package com.github.xupei.dubbo.api;

import java.util.List;
import java.util.Map;


public interface INodeService {

	public Map getNodeList(Map<String,String> paramMap)  throws Exception;
	
	public void saveNode(Map<String,String> map)throws Exception;
	
	public void deleteNode(String id) throws Exception;

	public List editNode(String id) throws Exception;

	public List orgList() throws Exception;
	
	public List feedList() throws Exception;
	
	public List getDataDicList() throws Exception;

	public void editNode(Map<String, String> paramMap) throws Exception;
	
	public List queryNodeByPid(Map<String, String> paramMap) throws Exception;

	public List getSysOrg() throws Exception;

	public List getJz(String nodeid,String linecode) throws Exception;

	public List getZb(String nodeid,String zb) throws Exception;

	public Object getNodeAllList(Map<String, String> paramsMap) throws Exception;

	public void batchUpdateNode(Map<String, String> paramsMap)throws Exception;
	
	public String getSql(Map<String, String> paramMap);

	public Map getSummaryMap(Map<String, String> paramsMap)throws Exception;
	
	
	
	
	
	
	
}

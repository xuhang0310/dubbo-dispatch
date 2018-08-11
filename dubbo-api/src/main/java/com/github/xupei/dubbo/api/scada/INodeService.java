package com.github.xupei.dubbo.api.scada;

import java.util.List;
import java.util.Map;


public interface INodeService {

	public Map getNodeList(Map<String,String> paramMap)  throws Exception;
	
	public List checkNode(Map<String,String> map) throws Exception;
	
	public List getNodeRightList(String id) throws Exception;
	
	public void saveNode(Map<String,String> map)throws Exception;
	
	public void deleteNode(String id) throws Exception;

	public List editNode(String id) throws Exception;

	public List orgList() throws Exception;
	
	public List feedList() throws Exception;

	public void editNode(Map<String, String> paramMap) throws Exception;
	
	public List queryNodeByPid(Map<String, String> paramMap) throws Exception;
}

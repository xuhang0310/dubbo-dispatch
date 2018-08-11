package com.github.xupei.dubbo.api.data;

import java.util.List;
import java.util.Map;


public interface IDataTeamService {

	public Map getDataTeamList(Map<String,String> paramMap)  throws Exception;
	
	public List checkDataTeam(Map<String,String> map) throws Exception;
	
	public List getDataTeamRightList(String id) throws Exception;
	
	public void saveDataTeam(Map<String,String> map)throws Exception;
	
	public void deleteDataTeam(String id) throws Exception;

	public List editDataTeam(String id) throws Exception;

	public List orgList() throws Exception;

	public void editDataTeam(Map<String, String> paramMap) throws Exception;

	public List suloList() throws Exception;
	
	
}

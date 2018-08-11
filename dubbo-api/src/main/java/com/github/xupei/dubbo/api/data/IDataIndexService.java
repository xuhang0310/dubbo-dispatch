package com.github.xupei.dubbo.api.data;

import java.util.List;
import java.util.Map;


public interface IDataIndexService {

	public Map getDataIndexList(Map<String,String> paramMap)  throws Exception;
	
	public List checkDataIndex(Map<String,String> map) throws Exception;
	
	public List getDataIndexRightList(String id) throws Exception;
	
	public void saveDataIndex(Map<String,String> map)throws Exception;
	
	public void saveData(Map<String,String> map)throws Exception;
	
	public void deleteDataIndex(String id) throws Exception;

	public List editDataIndex(String id) throws Exception;

	public List orgList() throws Exception;

	public void editDataIndex(Map<String, String> paramMap) throws Exception;
	
	public void editData(Map<String, String> paramMap) throws Exception;

	public List suloList() throws Exception;

	public List findDqZbr(Map<String, String> paramsMap) throws Exception;

	public List getZbrListByWatchcode(String watchcode) throws Exception;

	public List select_sysdate() throws Exception;

	public String getMaxSorderBySchedulerid(String schedulerid) throws Exception;

	public void saveZDDataIndex(Map<String, String> paramMap) throws Exception;

	public int validPassword(Map<String, String> paramsMap) throws Exception;

	public List getDataTeamByTeamid(String teamid) throws Exception;

	public List getDataTeamList(String loginName)  throws Exception;

	public List selectDataTeam(String teamid)  throws Exception;

	
	
}

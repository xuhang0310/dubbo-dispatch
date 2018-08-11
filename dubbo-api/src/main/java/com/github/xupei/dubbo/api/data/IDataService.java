package com.github.xupei.dubbo.api.data;

import java.util.List;
import java.util.Map;


public interface IDataService {

	public Map getDataList(Map<String,String> paramMap)  throws Exception;
	
	public List checkData(Map<String,String> map) throws Exception;
	
	public List getDataRightList(String id) throws Exception;
	
	public void saveData(Map<String,String> map)throws Exception;
	
	public void deleteData(String id) throws Exception;

	public List editData(String id) throws Exception;

	public List orgList() throws Exception;

	public void editData(Map<String, String> paramMap) throws Exception;

	public List suloList() throws Exception;

	public List findDqZbr(Map<String, String> paramsMap) throws Exception;

	public List getZbrListByWatchcode(String watchcode) throws Exception;

	public List select_sysdate() throws Exception;

	public String getMaxSorderBySchedulerid(String schedulerid) throws Exception;

	public void saveZDData(Map<String, String> paramMap) throws Exception;
	
	
}

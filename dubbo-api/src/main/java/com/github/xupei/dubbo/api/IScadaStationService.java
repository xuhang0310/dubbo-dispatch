package com.github.xupei.dubbo.api;

import java.util.List;
import java.util.Map;


public interface IScadaStationService {

	public Map getScadaStationList(Map<String,String> paramMap)  throws Exception;
	
	public List checkScadaStation(Map<String,String> map) throws Exception;
	
	public List getScadaStationRightList(String id) throws Exception;
	
	public void saveScadaStation(Map<String,String> map)throws Exception;
	
	public void deleteScadaStation(String id) throws Exception;

	public List editScadaStation(String id) throws Exception;

	public List orgList() throws Exception;
	
	public List feedList() throws Exception;
	
	public List nodeList() throws Exception;
	
	public List feednodeList() throws Exception;

	public void editScadaStation(Map<String, String> paramMap) throws Exception;

	
	
}

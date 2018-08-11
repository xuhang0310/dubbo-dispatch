package com.github.xupei.dubbo.api.data;

import java.util.List;
import java.util.Map;

public interface IDataProjectService {

	public Map getDataProjectList(Map<String, String> paramMap) throws Exception;

	public List getJhrlDate() throws Exception;

	public List getJhList() throws Exception;
	
	public Map getIndexMap() throws Exception;

	public Map getIndexMap(Map<String, String> paramsMap) throws Exception;

	public Map getDataParamMap() throws Exception;

	public void saveDetailProject(String gswd, String hswd, String ssrl, String ssll, String feedcode) throws Exception;

	public void updateDataProjectIndex(String date, String rzb, String cnmj, String temp) throws Exception;

	public void saveDataProjectIndex() throws Exception;

	public List getJhHistoryList(Map<String, String> paramsMap) throws Exception;

	public List getDataProjectChart(Map<String, String> paramsMap) throws Exception;

}

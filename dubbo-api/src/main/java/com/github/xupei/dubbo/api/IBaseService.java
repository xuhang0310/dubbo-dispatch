package com.github.xupei.dubbo.api;

import java.util.List;
import java.util.Map;

public interface IBaseService {

	public String getTableGrid(Map<String, String> map) throws Exception;

	public List getTableGridTitle(Map<String, String> map) throws Exception;
	
	public List getTableGridTitleForExcel(Map<String, String> map) throws Exception;

	public List getChartLengend(Map<String, String> map) throws Exception;

	public List getTableList() throws Exception;

	public List getTableColumnList(String key) throws Exception;

}

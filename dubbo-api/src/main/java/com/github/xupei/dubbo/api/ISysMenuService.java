package com.github.xupei.dubbo.api;

import java.util.List;
import java.util.Map;


public interface ISysMenuService {

	public Map getMenuList(Map<String,String> paramMap)  throws Exception;
	
	
	public void saveMenu(Map<String,String> map)throws Exception;
	
	public void deleteMenu(String id) throws Exception;

	public List editMenu(String id) throws Exception;

	public List orgList() throws Exception;
	
	public List feedList() throws Exception;

	public void editMenu(Map<String, String> paramMap) throws Exception;

	public List getMenuListForTree(Map<String, String> paramsMap)  throws Exception;


	public List parentList() throws Exception;
	
	
}

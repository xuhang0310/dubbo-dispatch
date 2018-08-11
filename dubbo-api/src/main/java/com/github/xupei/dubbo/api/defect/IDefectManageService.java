package com.github.xupei.dubbo.api.defect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.xupei.dubbo.bean.SysUser;

public interface  IDefectManageService {

	public Map getDefectList(Map<String,String> paramMap)  throws Exception;

	
	public void saveDefect(Map<String,String> map,String userid,String orgid,String filename)throws Exception;
	
	public void deleteDefect(String id) throws Exception;


	public List findDefect(String id)throws Exception;

	public void updateDefect(Map<String, String> paramsMap,String filename)throws Exception;

	public List getlx(String stationtype)throws Exception;


	public List selectAll()throws Exception;


	public List getProjectList(String id)throws Exception;


	public Map getDefectTypeList(Map<String, String> paramsMap)throws Exception;


	public void addDefectType(Map<String, String> paramsMap)throws Exception;


	public void deleteDefectType(String id)throws Exception;


	public List selectAllDefect(Map<String, String> paramsMap)throws Exception;


	public void updateDefectType(Map<String, String> paramsMap)throws Exception;


	public List getDefectType()throws Exception;


	public String getOrgId(String username)throws Exception;


	public String getUid(String username)throws Exception;


	public List findDefectDeal(String id)throws Exception;


	public List getTjr(String id)throws Exception;


	public void dealDefect(Map<String, String> paramsMap,String userid)throws Exception;


	public List getDealMan(String id)throws Exception;


	public void solveDefect(Map<String, String> paramsMap, String userid)throws Exception;


	public List getOrgName()throws Exception;
	
	
}

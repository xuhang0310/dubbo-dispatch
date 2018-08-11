package com.github.xupei.dubbo.api;

import java.util.List;
import java.util.Map;

import com.github.xupei.dubbo.bean.SysUser;

public interface  IRoleManageService {

	public Map getRoleManageList(Map<String,String> paramMap)  throws Exception;
	
	
	
	
	
	public void saveRole(Map<String,String> map)throws Exception;
	
	public void deleteRole(String userid) throws Exception;





	public List editRole(String roleid)throws Exception;





	public void saveRoleById(Map<String, String> paramsMap) throws Exception;





	public String getPermission(String roleid)throws Exception;





	public Map StringToMap(String rolepermission)throws Exception;





	public StringBuffer queryMenuList(String parentid, StringBuffer result, Map rolemap)throws Exception;





	public void updateRolePermission(String roleid, String permissions)throws Exception;





	public List selectAll()throws Exception;

	
	
}

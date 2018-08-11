package com.github.xupei.dubbo.api;

import java.util.List;
import java.util.Map;

import com.github.xupei.dubbo.bean.SysUser;

public interface  IUserService {

	public Map getUserList(Map<String,String> paramMap)  throws Exception;
	
	public List checkUser(Map<String,String> map) throws Exception;
	
	public SysUser checkUserBean(Map<String,String> map) throws Exception;
	
	public List getUserRightList(String userid) throws Exception;
	
	public String getUserSkinTemplate(String userid) throws Exception;
	
	public void changeUserSkinTemplate(String skin,String userid) throws Exception;
	
	
	public void saveUser(Map<String,String> map)throws Exception;
	
	public void deleteUser(String userid) throws Exception;

	public List getOrgList() throws Exception;

	public List getRoleList() throws Exception;

	public int doValidUsername(Map<String, String> paramsMap) throws Exception;

	public void updateUserById(Map<String, String> paramsMap) throws Exception;

	public List getUserList(String userid) throws Exception;

	public void clickUserPW(String id) throws Exception;
	
	
}

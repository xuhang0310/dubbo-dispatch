package com.github.xupei.dubbo.api.innerDevice;

import java.util.List;
import java.util.Map;

import com.github.xupei.dubbo.bean.SysUser;

public interface  IInnerDeviceService {

	public Map getInnerDeviceList(Map<String,String> paramMap)  throws Exception;

	
	public void saveDevice(Map<String,String> map)throws Exception;
	
	public void deleteDevice(String id) throws Exception;


	public List findDevice(String id)throws Exception;

	public void updateDevice(Map<String, String> paramsMap)throws Exception;




	public List selectAll()throws Exception;


	public List getProjectList(String id)throws Exception;


	public List getOrg()throws Exception;


	public List getSblx()throws Exception;


	public List getNode(String type)throws Exception;


	public String getSql(Map<String, String> paramsMap);
	
	
}

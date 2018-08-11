package com.github.xupei.dubbo.api.weather;

import java.util.List;
import java.util.Map;

import com.github.xupei.dubbo.bean.SysUser;

public interface  IWeatherService {

	public Map getWeatherHourList(Map<String,String> paramMap)  throws Exception;

	public List getYbList()throws Exception;

	public List getRealList()throws Exception;

	public List getParamDate()throws Exception;

	public List getChart(Map<String, String> paramsMap)throws Exception;

	public List getWeatherConfig()throws Exception;

	public List getWeatherForHour(String cityCode)throws Exception;

	public void UpdateTempMetrical(List weatherList)throws Exception;

	public void UpdateTempForeWeek(List weatherListforWeek)throws Exception;

	public List getWeatherForWeek(String ip,String days,String appkey,String sign)throws Exception;

	public List getProvince()throws Exception;

	public List getCityList()throws Exception;

	public List getCity()throws Exception;

	public void updateWeatherCity(Map<String, String> paramsMap)throws Exception;

	public List getProjectList(Map<String, String> paramsMap)throws Exception;

	public List getRealOne()throws Exception;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	
	
}

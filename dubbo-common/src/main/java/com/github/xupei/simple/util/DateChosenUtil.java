package com.github.xupei.simple.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateChosenUtil {
	
	
	public static Map<String, String> returnDateType(Map<String, String> paramsMap){
		String dateType=paramsMap.get("dateType")==null?"":paramsMap.get("dateType")+"";
		SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd");
		if(dateType==null){
			return  paramsMap;
		}
		if(dateType.equals("")){
			return  paramsMap;
		}
		if(dateType.equals("0")){
			paramsMap.put("startdate", simple.format(new Date()));
			paramsMap.put("enddate", simple.format(new Date()));
			
		}
		if(dateType.equals("1")){
			Calendar cal=Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, -1);
			paramsMap.put("startdate", simple.format(cal.getTime()));
			paramsMap.put("enddate", simple.format(cal.getTime()));
			
		}
		if(dateType.equals("2")){
			Calendar cal=Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, -7);
			paramsMap.put("startdate", simple.format(cal.getTime()));
			paramsMap.put("enddate", simple.format(new Date()));
		}
		if(dateType.equals("3")){
			Calendar cal=Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, -31);
			paramsMap.put("startdate", simple.format(cal.getTime()));
			paramsMap.put("enddate", simple.format(new Date()));
		}
		if(dateType.equals("4")){
			paramsMap.put("startdate", "2015-11-15");
			paramsMap.put("enddate", "2016-03-15");
		}
		if(dateType.equals("5")){
			
		}
		System.out.println(paramsMap);
		return paramsMap;
	}
	
	public static void main(String arg []){
		
		Map paramsMap=new HashMap();
		paramsMap.put("dateType", "5");
		
		returnDateType(paramsMap);
	}

}

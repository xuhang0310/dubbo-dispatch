package com.github.xupei.simple.json;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class JsonUtil {
	
	public static void returnObjectJson(Object obj,HttpServletResponse response){
		JsonResProcessor json=new JsonResProcessor();
		try {
			json.returnResInfo(obj, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//logger.error(e.toString(), e);
		}
	}
	
	public static void returnListJson(List list,HttpServletResponse response){
		JsonResProcessor json=new JsonResProcessor();
		try {
			json.returnResInfo(list, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//logger.error(e.toString(), e);
		}
	}
	
	public static void returnnBaseJson(boolean type,String msg,HttpServletResponse response){
		JsonResProcessor json=new JsonResProcessor();
		HashMap map=new HashMap();
		map.put("flag", type);
		map.put("messager", msg);
		try {
			json.returnResInfo(map, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//logger.error(e.toString(), e);
		}
	}

}

package com.github.xupei.simple.json;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.JsonSerializer;

public class JsonResProcessor {

	private static String generateJson(Object obj, Map<Class<?>, JsonSerializer<?>> jsonSerializerMap)
		    throws Exception
		  {
		    JsonSrzer srzer = new JsonSrzer(jsonSerializerMap);
		    return srzer.serializer(obj);
		  }

		  public static void returnResInfo(Object obj, HttpServletResponse response) throws Exception {
		    String json = generateJson(obj, null);
		    try {
		     // response.setCharacterEncoding("UTF-8");
		      
		      response.setContentType("text/xml;charset=UTF-8");
		      response.getWriter().println(json);
		    }
		    catch (IOException e) {
		      throw new Exception("111");
		    }
		  }

		  public static void returnResInfo(Object obj, HttpServletResponse response, Map<Class<?>, JsonSerializer<?>> jsonSerializerMap) throws Exception {
		    String json = generateJson(obj, jsonSerializerMap);
		    try {
		      response.getWriter().println(json);
		    }
		    catch (IOException e) {
		      throw new Exception("111");
		    }
		  }
		  
		  

		

}

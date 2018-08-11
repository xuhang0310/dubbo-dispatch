package com.github.xupei.simple.json;

import java.util.Map;

import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

public class JsonSrzer {

	private ObjectMapper objectMapper;
	  private Map<Class<?>, JsonSerializer<?>> jsonSerializerMap;

	  public JsonSrzer(Map<Class<?>, JsonSerializer<?>> jsonSerializerMap)
	  {
	    this.objectMapper = new ObjectMapper();
	    this.objectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
	    CustomSerializerFactory sf = new CustomSerializerFactory();

	    if ((jsonSerializerMap != null) && (jsonSerializerMap.size() > 0)) {
	      for (Map.Entry entry : jsonSerializerMap.entrySet()) {
	        Class type = (Class)entry.getKey();
	        JsonSerializer serializer = (JsonSerializer)entry.getValue();
	        sf.addGenericMapping(type, (JsonSerializer)entry.getValue());
	      }
	    }
	    this.objectMapper.setSerializerFactory(sf);

	    this.jsonSerializerMap = jsonSerializerMap;
	  }

	  public String serializer(Object obj) throws Exception {
	    try {
	      return this.objectMapper.writeValueAsString(obj); } catch (Exception e) {
	    }
	    throw new Exception("产生JSON时出现异常！");
	  }

	  public Map<Class<?>, JsonSerializer<?>> getJsonSerializerMap()
	  {
	    return this.jsonSerializerMap;
	  }

}

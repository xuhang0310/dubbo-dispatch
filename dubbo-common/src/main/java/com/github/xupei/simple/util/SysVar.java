package com.github.xupei.simple.util;

import java.util.Map;

public interface SysVar {
	
	public String getValue(Map<String, String> context) throws Exception;

}

package com.github.xupei.simple.util;

import java.util.UUID;

public class UUIDTool {
	
	public static String getUUID(){
		  String s = UUID.randomUUID().toString(); 
		   // String d=s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
	        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
	}


}

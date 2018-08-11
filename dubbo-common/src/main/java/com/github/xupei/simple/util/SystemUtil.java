/**
 * Copyright (C), 2009-2012, 北京博达云起科技发展有限公司.<BR>
 * ProjectName:dispatch<BR>
 * File name:  SystemUtil.java     <BR>
 * Author: xupei  <BR>
 * Project:dispatch    <BR>
 * Version: v 1.0      <BR>
 * Date: 2016-8-29 下午5:36:31 <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 

package com.github.xupei.simple.util;

public class SystemUtil {
	
	public static String getPorjectPath(){
		String nowpath = "";
		nowpath=System.getProperty("user.dir")+"/";
		
		return nowpath;
	}

	/**
	 * 方法说明： . <BR>
	 * @see com.simple.dispatch.util.SystemUtil <BR>
	 * @param args
	 * @return: void
	 * @Author: xupei <BR>
	 * @Datetime：2016-8-29 下午5:36:31 <BR>
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

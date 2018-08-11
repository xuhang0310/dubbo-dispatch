/**
 * Copyright (C), 2009-2012, 北京博达云起科技发展有限公司.<BR>
 * ProjectName:dispatch<BR>
 * File name:  PathUtil.java     <BR>
 * Author: xupei  <BR>
 * Project:dispatch    <BR>
 * Version: v 1.0      <BR>
 * Date: 2016-8-29 下午5:34:41 <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 

package com.github.xupei.simple.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class PathUtil {
	
	/**
	 * 图片访问路径
	 * 
	 * @param pathType
	 *            图片类型 visit-访问；save-保存
	 * @param pathCategory
	 *            图片类别，如：话题图片-topic、话题回复图片-reply、商家图片
	 * @return
	 */
	public static String getPicturePath(String pathType, String pathCategory) {
		String strResult = "";
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		StringBuffer strBuf = new StringBuffer();
		if ("visit".equals(pathType)) {
		} else if ("save".equals(pathType)) {
			String projectPath = SystemUtil.getPorjectPath().replaceAll("\\\\",
					"/");
			projectPath = splitString(projectPath, "bin/");

			strBuf.append(projectPath);
			strBuf.append("webapps/ROOT/");
		}

		strResult = strBuf.toString();

		return strResult;
	}

	private static String splitString(String str, String param) {
		String result = str;

		if (str.contains(param)) {
			int start = str.indexOf(param);
			result = str.substring(0, start);
		}

		return result;
	}
	
	/*
	 * 获取classpath1
	 */
	public static String getClasspath(){
		String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../").replaceAll("file:/", "").replaceAll("%20", " ").trim();	
		if(path.indexOf(":") != 1){
			path = File.separator + path;
		}
		return path;
	}
	
	
	
	/*
	 * 获取classpath2
	 */
	public static String getClassResources(){
		String path =  (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))).replaceAll("file:/", "").replaceAll("%20", " ").trim();	
		if(path.indexOf(":") != 1){
			path = File.separator + path;
		}
		return path;
	}
	
	public static String PathAddress() {
		String strResult = "";

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		StringBuffer strBuf = new StringBuffer();

		strBuf.append(request.getScheme() + "://");
		strBuf.append(request.getServerName() + ":");
		strBuf.append(request.getServerPort() + "");

		strBuf.append(request.getContextPath() + "/");

		strResult = strBuf.toString();// +"ss/";//加入项目的名称

		return strResult;
	}

	/**
	 * 方法说明： . <BR>
	 * @see com.simple.dispatch.util.PathUtil <BR>
	 * @param args
	 * @return: void
	 * @Author: xupei <BR>
	 * @Datetime：2016-8-29 下午5:34:41 <BR>
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

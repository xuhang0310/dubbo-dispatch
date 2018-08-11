/**
 * Copyright (C), 2009-2012, 北京博达云起科技发展有限公司.<BR>
 * ProjectName:dispatch<BR>
 * File name:  DelAllFile.java     <BR>
 * Author: xupei  <BR>
 * Project:dispatch    <BR>
 * Version: v 1.0      <BR>
 * Date: 2016-8-29 下午5:41:50 <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 

package com.github.xupei.simple.util;

import java.io.File;

public class DelAllFile {

	/**
	 * 方法说明： . <BR>
	 * @see com.simple.dispatch.util.DelAllFile <BR>
	 * @param args
	 * @return: void
	 * @Author: xupei <BR>
	 * @Datetime：2016-8-29 下午5:41:50 <BR>
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * @param folderPath 文件路径 (只删除此路径的最末路径下所有文件和文件夹)
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); 	// 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); 		// 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除指定文件夹下所有文件
	 * @param path 文件夹完整绝对路径
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);	// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);	// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

}

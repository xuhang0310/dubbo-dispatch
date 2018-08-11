/**
 * Copyright (C), 2009-2012, 北京博达云起科技发展有限公司.<BR>
 * ProjectName:dispatch<BR>
 * File name:  Freemarker.java     <BR>
 * Author: xupei  <BR>
 * Project:dispatch    <BR>
 * Version: v 1.0      <BR>
 * Date: 2016-8-29 下午5:30:54 <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 

package com.github.xupei.simple.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class Freemarker {
	
	public static void print(String ftlName, Map<String,Object> root, String ftlPath) throws Exception{
		try {
			System.out.println("_______________");
			Template temp = getTemplate(ftlName, ftlPath);		//通过Template可以将模板文件输出到相应的流
			temp.process(root, new PrintWriter(System.out));
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 输出到输出到文件
	 * @param ftlName   ftl文件名
	 * @param root		传入的map
	 * @param outFile	输出后的文件全部路径
	 * @param filePath	输出前的文件上部路径
	 */
	public static void printFile(String ftlName, Map<String,Object> root, String outFile, String filePath, String ftlPath,String project) throws Exception{
		try {
			String srcPath=filePath+outFile;
			File file = new File(srcPath);
			
			if(!file.getParentFile().exists()){				//判断有没有父路径，就是判断文件整个路径是否存在
				file.getParentFile().mkdirs();				//不存在就全部创建
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			Template template = getTemplate(ftlName, ftlPath);
			template.process(root, out);					//模版输出
			out.flush();
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过文件名加载模版
	 * @param ftlName
	 */
	public static Template getTemplate(String ftlName, String ftlPath) throws Exception{
		try {
			Configuration cfg = new Configuration();  												//通过Freemaker的Configuration读取相应的ftl
			cfg.setEncoding(Locale.CHINA, "utf-8");
			cfg.setDirectoryForTemplateLoading(new File(PathUtil.getClassResources()+"/ftl/"+ftlPath));		//设定去哪里读取相应的ftl模板文件
			Template temp = cfg.getTemplate(ftlName);												//在模板文件目录中找到名称为name的文件
			return temp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 方法说明： . <BR>
	 * @see com.simple.dispatch.util.Freemarker <BR>
	 * @param args
	 * @return: void
	 * @Author: xupei <BR>
	 * @Datetime：2016-8-29 下午5:30:54 <BR>
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

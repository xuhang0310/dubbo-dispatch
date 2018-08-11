/**
 * Copyright (C), 2009-2012, 北京博达云起科技发展有限公司.<BR>
 * ProjectName:ECC<BR>
 * File name:  ${objectName}Service.java     <BR>
 * Author: ${author}  <BR>
 * Project:ECC    <BR>
 * Version: v 1.0      <BR>
 * Date: ${nowDate} <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 
package com.simple.dispatch.${packageName}.service;

import java.util.List;
import java.util.Map;




/**
 * 功能描述：  .  <BR>
 * 历史版本: <Br>
 * 开发者: ${author}   <BR>
 * 时间：${nowDate} <BR>
 * 变更原因：    <BR>
 * 变化内容 ：<BR>
 * 首次开发时间：${nowDate} <BR>
 * 描述：   <BR>
 * 版本：V1.0
 */
public interface ${objectName}Service {
	
	
	/**
	 * 
	 * 方法说明： . <BR>
	 * @throws Exception 
	 * @see com.simple.dispatch.${packageName}.service.${objectName}Service <BR>
	 * @return: List
	 * @Author:  ${author} <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	public Map get${objectName}MapList(Map<String,String> paramsMap)  throws Exception;
	
	public Map get${objectName}MapById(Map<String,String> paramsMap)  throws Exception;
	
	public void save${objectName}(Map<String,String> paramsMap) throws Exception;
	
	public void update${objectName}(Map<String,String> paramsMap) throws Exception;
	
	public void delete${objectName}(Map<String,String> paramsMap) throws Exception;
	
	
	

}

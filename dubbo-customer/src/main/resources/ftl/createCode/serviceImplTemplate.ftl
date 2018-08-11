
/**
 * Copyright (C), 2009-2012, 北京博达云起科技发展有限公司.<BR>
 * ProjectName:ECC<BR>
 * File name:  ${objectName}ServiceImpl.java     <BR>
 * Author: ${author}  <BR>
 * Project:ECC    <BR>
 * Version: v 1.0      <BR>
 * Date: ${nowDate} <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 
package com.simple.dispatch.${packageName}.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.simple.dispatch.dao.BaseDao;
import com.simple.dispatch.${packageName}.service.${objectName}Service;
import com.simple.dispatch.util.UUIDTool;


/**
 * 功能描述：  .  <BR>
 * 历史版本: <Br>
 * 开发者:  ${author}  <BR>
 * 时间：${nowDate}  <BR>
 * 变更原因：    <BR>
 * 变化内容 ：<BR>
 * 首次开发时间：${nowDate} <BR>
 * 描述：   <BR>
 * 版本：V1.0
 */

@Service
public class ${objectName}ServiceImpl implements ${objectName}Service{
	
	@Resource
	private BaseDao dao;

	/**
	 * 实现说明： . <BR>
	 * @throws Exception 
	 * @see com.simple.dispatch.${packageName}.service.impl.${objectName}ServiceImpl
	 * @Author:  ${author} <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	public Map find${objectName}List(Map<String,String> paramsMap)  throws Exception{
		// TODO Auto-generated method stub
		String sql="";
		return dao.queryGridList(sql, paramsMap);
		
	}
	
	/**
	 * 实现说明： . <BR>
	 * @throws Exception 
	 * @see com.simple.dispatch.${packageName}.service.impl.${objectName}ServiceImpl
	 * @Author:  ${author} <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	public Map get${objectName}MapList(Map<String,String> paramsMap)  throws Exception{
	     String sql="select * from ${tablename} ";
		return dao.queryGridList(sql, paramsMap);
	}
	
	/**
	 * 实现说明： . <BR>
	 * @throws Exception 
	 * @see com.simple.dispatch.${packageName}.service.impl.${objectName}ServiceImpl
	 * @Author:  ${author} <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	public Map get${objectName}MapById(Map<String,String> paramsMap)  throws Exception{
	    StringBuffer sql=new StringBuffer();
		sql.append(" select * from ${tablename}  where 1=1  [ and ${key}='{${key}}' ]  ");
		return dao.findMap(sql.toString(),paramsMap);
	}
	
	/**
	 * 实现说明： . <BR>
	 * @throws Exception 
	 * @see com.simple.dispatch.${packageName}.service.impl.${objectName}ServiceImpl
	 * @Author:  ${author} <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	public void save${objectName}(Map<String,String> paramsMap) throws Exception{
	   String sql="${insertSql}";
	   dao.addObject(sql);
	}
	
	/**
	 * 实现说明： . <BR>
	 * @throws Exception 
	 * @see com.simple.dispatch.${packageName}.service.impl.${objectName}ServiceImpl
	 * @Author:  ${author} <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	public void update${objectName}(Map<String,String> paramsMap) throws Exception{
	}
	
	/**
	 * 实现说明： . <BR>
	 * @throws Exception 
	 * @see com.simple.dispatch.${packageName}.service.impl.${objectName}ServiceImpl
	 * @Author:  ${author} <BR>
	 * @Datetime：${nowDate} <BR>
	 */
	public void delete${objectName}(Map<String,String> paramsMap) throws Exception{
	    String sql="${deleteSql}";
	     dao.deleteObject(sql,paramsMap);
	}

	

}

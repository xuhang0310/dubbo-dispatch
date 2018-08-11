package com.github.xupei.simple.defect.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.xupei.dubbo.api.defect.IMailWarnConfigService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;
import com.github.xupei.simple.util.UUIDTool;

public class MailWarnConfigServiceImpl extends GridSqlUtilTool implements IMailWarnConfigService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	
	public String getSql(Map<String, String> paramMap){
		
		String sql="select t.*,decode(t.STATUS,1,'运行',2,'停止','未定义') as STATUSNAME,t.id as CODE from MAIL_WARN_CONFIG t where del=1 ";//[and USERNAME IN ('{LoginName}') ] 
		if (!paramMap.get("tablesql").isEmpty()) {
			sql+=" and lower(t.tablesql) like lower('%"+paramMap.get("tablesql")+"%') ";
		}
		sql+="order by t.TRIGERTIME";
		
		return sql;
	}
	
	public Map getMailWarnConfigList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql=getSql(paramMap);
		
		return baseDao.queryGridList(sql,paramMap);
		
	}
	
	/**
	 * 修改查询方法
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List editMailWarnConfig(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="select * from MAIL_WARN_CONFIG t where 1=1 ";
		
		if (!id.isEmpty()) {
			sql +=" and t.id  = '"+id+"'";
		}
		
		return baseDao.findAll(sql);
		
	}

	/**
	 * 新增
	 */
	@Override
	public void saveMailWarnConfig(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		String sql="insert into MAIL_WARN_CONFIG(ID,TABLESQL,TRIGERNUM,WARNDESC,RESOLVE,STATUS,TRIGERTIME,EMAIL,INTERVAL,OPERATOR) values('"+
				UUIDTool.getUUID()+"','"+paramMap.get("tablesql")+"',"+
				paramMap.get("trigernum")+",'"+paramMap.get("warndesc")+"','"+paramMap.get("resolve")+"',"+
				paramMap.get("status")+",'"+paramMap.get("trigertime")+"','"+paramMap.get("email")+"',"+
				paramMap.get("interval")+",'"+paramMap.get("operator")+"') ";
		
	    baseDao.addObject(sql);
	}
	
	/**
	 * 修改
	 */
	@Override
	public void editMailWarnConfig(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		String sql="update  MAIL_WARN_CONFIG set TABLESQL='"+paramMap.get("tablesql")+"',TRIGERNUM="+paramMap.get("trigernum")+
				",WARNDESC='"+paramMap.get("warndesc")+"',RESOLVE='"+paramMap.get("resolve")+
				"',STATUS="+paramMap.get("status")+",TRIGERTIME='"+paramMap.get("trigertime")+"',EMAIL='"+paramMap.get("email")+
				"',INTERVAL="+paramMap.get("interval")+",OPERATOR='"+paramMap.get("operator")+"' where ID='"+paramMap.get("id")+"'";
		
	    baseDao.addObject(sql);
	}
	
	/**
	 * 删除
	 */
	public void deleteMailWarnConfig(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="update MAIL_WARN_CONFIG set del=0 WHERE id = '"+id+"'";
	    baseDao.deleteObject(sql);
	}
	/**
	 * 页面下拉框
	 */
	public List orgList() throws Exception {
		// TODO Auto-generated method stub
		String sql="select s.orgid,s.orgname  from sys_org s where 1=1 and del=1 ";
		
		return baseDao.findAll(sql);
		
	}
	/**
	 * 页面下拉框
	 */
	public List suloList() throws Exception {
		// TODO Auto-generated method stub
		String sql="select s.* from sys_user s where 1=1 and del=1 ";
		
		return baseDao.findAll(sql);
		
	}
	
	public static void main(String arg []){
		for(int i=0;i<100;i++){
			SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
			System.out.println(simple.format(new Date()));
		}
		
	}



}

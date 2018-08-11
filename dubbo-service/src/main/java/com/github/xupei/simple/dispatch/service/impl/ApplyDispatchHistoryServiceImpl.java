package com.github.xupei.simple.dispatch.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.xupei.dubbo.api.dispatch.IApplyDispatchHistoryService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;

public class ApplyDispatchHistoryServiceImpl extends GridSqlUtilTool implements IApplyDispatchHistoryService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	
	public String getSql(Map<String, String> paramMap){
		
		String sql = "select t.taskorderid,(select orgname from sys_org where orgid=t.EXECUTEORGID) executeorgname, "+
				"       t.tasktype, "+
				"       to_char(t.writedate, 'yyyy-MM-dd hh24:mi:ss') as writedate, "+
				"       t.important, "+
				"       (select displayname from sys_user where userid=t.writeor) writeor, "+
				"       t.indextitle, "+
				"       t.ordercause, "+
				"       t.ordercontent, "+
				"       t.ordermemo, "+
			/*	"       to_char(t.confirmdate, 'yyyy-MM-dd hh24:mi:ss') as confirmdate, "+
				"       to_char(t.confirmlimit, 'yyyy-MM-dd hh24:mi:ss') as confirmlimit, "+
				"       t.zlnr, "+*/
				"       t.curstate, "+
				"       t.id, "+
				"       (case "+
				"         when t.important = 0 then "+
				"          '不重要' "+
				"         else "+
				"          '重要' "+
				"       end) importantname, "+
				"       decode(t.curstate,0,'草稿',1,'审批中',2,'结束',3,'作废') curstatename, "+
				"       ny_typevalue(t.tasktype) typevalue," +
				"		t.id idopreat "+
				"  from task_apply_flow t   where t.del = 1 " ;
		//WHERE 1=1 [and USERNAME IN ('{LoginName}') ] )
		/*if (!paramMap.get("name").isEmpty()) {
			sql +=" and T.SCHEDULERNAME like '%"+paramMap.get("name")+"%'";
		}*/
		sql+=" order by t.writedate desc";
		
		return sql;
	}
	
	public Map getDispatchHistoryList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql=getSql(paramMap);
		
		return baseDao.queryGridList(sql,paramMap);
		
	}
	
	public static void main(String arg []){
		for(int i=0;i<100;i++){
			SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
			System.out.println(simple.format(new Date()));
		}
		
	}



}

package com.github.xupei.simple.dispatch.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.annotation.Resource;

import com.github.xupei.dubbo.api.dispatch.ITaskOrderIndexService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;

public class TaskOrderIndexServiceImpl extends GridSqlUtilTool implements ITaskOrderIndexService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	// 新建事项查询语句
	public String getCommonSql(Map<String, String> paramMap){
		String commonsql = "select t.taskorderid,(select orgname from sys_org where orgid=t.EXECUTEORGID) executeorgname, "+
				"       t.tasktype, "+
				"       to_char(t.writedate, 'yyyy-MM-dd hh24:mi:ss') as writedate, "+
				"       t.important, "+
				"       (select displayname from sys_user where userid=t.writeor) writeor, "+
				"       t.indextitle, "+
				"       t.ordercause, "+
				//" ( SELECT TM.TASKFLOWNAME FROM TASK_FLOW_MANAGER TM WHERE TM.ID=t.taskflowid) TASKFLOWNAME,"+
				"       t.ordercontent, "+
				"       t.EXECUTEORGID, "+
				"       t.ordermemo, "+
				"       to_char(t.confirmdate, 'yyyy-MM-dd hh24:mi:ss') as confirmdate, "+
				"       to_char(t.confirmlimit, 'yyyy-MM-dd hh24:mi:ss') as confirmlimit, "+
				"       t.zlnr, "+
				"       t.curstate, "+
				"       t.id, "+
				"       t.dispatchid, "+
				"       (case "+
				"         when t.important = 0 then "+
				"          '不重要' "+
				"         else "+
				"          '重要' "+
				"       end) importantname, "+
				"       (case "+
				"         when t.curstate = 0 then "+
				"          '草稿' "+
				"         else "+
				"          '生效' "+
				"       end) curstatename, "+
				"       b.name typevalue,t.id idopreat, "+
				"       taf.INDEXTITLE APPLYINDEXTITLE "+
				"  from task_order_index_flow t  "+
				"  left join bas_powertype b "+
				"    on t.tasktype = b.id "+
				"  left join task_apply_flow taf "+
				"    on t.applytaskid = taf.id "+
				" where t.del = 1  ";//and t.writeor='"+pageConfig.getUid()+"' 
		return commonsql;
	}
	// 待办事项查询语句
	public String getDaiBanObject(Map<String, String> paramMap){
		
		String sql="select t.taskorderid, "+
				"       ny_typevalue(t.tasktype) typevalue,(select orgname from sys_org where orgid=t.EXECUTEORGID) executeorgname, "+
				"       to_char(t.writedate, 'yyyy-MM-dd hh24:mi:ss') as writedate, "+
				"       t.important, "+
				"       (select displayname from sys_user where userid=t.writeor) writeor, "+
				"       t.indextitle, "+
				"       t.ordercause, "+
				"       t.ordercontent, "+
				"       t.EXECUTEORGID, "+
				//" ( SELECT TM.TASKFLOWNAME FROM TASK_FLOW_MANAGER TM WHERE TM.ID=t.taskflowid) TASKFLOWNAME,"+
				"       t.ordermemo, "+
				"       to_char(t.confirmdate, 'yyyy-MM-dd hh24:mi:ss') as confirmdate, "+
				"       to_char(t.confirmlimit, 'yyyy-MM-dd hh24:mi:ss') as confirmlimit, "+
				"       t.zlnr, "+
				"       t.curstate, "+
				"       t.id, "+
				"       t.dispatchid, "+
				"       (case "+
				"         when t.important = 0 then "+
				"          '不重要' "+
				"         else "+
				"          '重要' "+
				"       end) importantname, "+
				"       decode(t.curstate,'0','草稿','1','审批中',2,'结束',3,'作废')  curstatename, "+
				"       su.displayname,t.id idopreat "+
				
				"from task_order_index_flow t, sys_user su "+//task_flow_next tfn, 
				"where t.del = 1 "+
				"   and t.CURSTATE = '1' ";
				//"   and tfn.taskid = t.id "+
				//"   and su.usergroup = tfn.groupcode "+//and t.executeorgid in ("+pageConfig.getUserDataPermissions()+")    
				//"   and su.userid='"+pageConfig.getUid()+"'   ";
		return sql;
	}
	// 已办事项查询语句
	public String getYiBanObject(Map<String, String> paramMap){
		String sql="select t.taskorderid, "+
				"       ny_typevalue(t.tasktype) typevalue, (select orgname from sys_org where orgid=t.EXECUTEORGID) executeorgname,"+
				"       to_char(t.writedate, 'yyyy-MM-dd hh24:mi:ss') as writedate, "+
				"       t.important, "+
				"       (select displayname from sys_user where userid=t.writeor) writeor, "+
				"       t.indextitle, "+
				"       t.ordercause, "+
				"       t.ordercontent, "+
				"       t.EXECUTEORGID, "+
				//" ( SELECT TM.TASKFLOWNAME FROM TASK_FLOW_MANAGER TM WHERE TM.ID=t.taskflowid) TASKFLOWNAME,"+
				"       t.ordermemo, "+
				"       to_char(t.confirmdate, 'yyyy-MM-dd hh24:mi:ss') as confirmdate, "+
				"       to_char(t.confirmlimit, 'yyyy-MM-dd hh24:mi:ss') as confirmlimit, "+
				"       t.zlnr, "+
				"       t.curstate, "+
				"       t.id, "+
				"       t.dispatchid, "+
				"       (case "+
				"         when t.important = 0 then "+
				"          '不重要' "+
				"         else "+
				"          '重要' "+
				"       end) importantname, "+
				"       decode(t.curstate,'0','草稿','1','审批中',2,'结束',3,'作废')  curstatename, "+
				"       su.displayname, "+
				"        tfl.flow_status,tfl.flow_view,tfl.flow_overtime ,t.id idopreat "+
				"from task_order_index_flow t, ( select *  from task_flow_log where (taskid,userid, flow_overtime) " +
				" in  (select taskid,userid, max(flow_overtime) from task_flow_log group by taskid,userid) ) tfl, sys_user su "+
				" where t.del = 1 "+
				"   and t.CURSTATE <> '0' "+
				"   and tfl.taskid = t.id "+
				//"   and su.userid = tfl.userid  "+//and t.executeorgid in ("+pageConfig.getUserDataPermissions()+")  
				"   and tfl.flow_status is not  null     ";//and su.userid='"+pageConfig.getUid()+"'  
		return sql;
	}
	
	public String getSql(Map<String, String> paramMap){
		
		String commonsql = this.getCommonSql(paramMap);// 新建事项查询语句
		
		if(Integer.parseInt(paramMap.get("tabIndex"))==1){
 			commonsql += "	and t.CURSTATE='0'  ";
			
			
		}else if(Integer.parseInt(paramMap.get("tabIndex"))==2){
			commonsql =  this.getDaiBanObject(paramMap);// 待办事项查询语句
		}else if(Integer.parseInt(paramMap.get("tabIndex"))==3){
			commonsql =this.getYiBanObject(paramMap);// 已办事项查询语句
		}
		commonsql += "  order by t.writedate desc";
		
		String sql = "SELECT * FROM ("+commonsql+") zhsql WHERE 1 = 1";
		
		return sql;
	}
	
	public Map getTaskOrderIndexList(Map<String, String> paramMap) throws Exception {
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
	
	@Override
	public List getTaskLogPath(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="select  su.displayname,decode(tfl.flow_status,0,'发起',1,'同意',2,'驳回',3,'拒绝并作废') flow_status ,to_char(tfl.flow_overtime,'yyyy-MM-dd hh24:mi:ss') flow_overtime  from task_flow_log tfl,sys_user su " +
				" where taskid='"+id+"' and su.userid=tfl.userid order by flow_overtime ";
		List list= baseDao.findAll(sql);
		return list;
	}
	
	public List getListShenPiAll(String fid,String taskid) throws Exception{
		String sql="select tf.orderid,su.displayname,tf.groupcode,su.datapermissions "+
					"            from task_flow_manager_child tf, sys_user su "+
					"           where fid ='"+fid+"' "+
					"             and su.usergroup = tf.groupcode  and tf.taskid='"+taskid+"'  order by orderid  ";
		List list=baseDao.findAll(sql);
		if(list.size()==0){
			sql="select tf.orderid,su.displayname,tf.groupcode,su.datapermissions "+
					"            from task_flow_manager_child tf, sys_user su "+
					"           where fid ='"+fid+"' "+
					"             and su.usergroup = tf.groupcode  and tf.taskid is null  order by orderid  ";
			list=baseDao.findAll(sql);
		}
		return list;
	}
	
	@Override
	public List getDaiShenPiList(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="select taskflowid,executeorgid from task_order_index_flow  t where id='"+id+"' ";
		List list=baseDao.findAll(sql);
		HashMap mapTaskFlow=(HashMap)list.get(0);
		String fid=mapTaskFlow.get("TASKFLOWID")+"";
		String executeorgid=mapTaskFlow.get("EXECUTEORGID")+"";
		List listShenPiAll=getListShenPiAll(fid,id);
		sql="select groupcode from task_flow_next where taskid='"+id+"' ";
		List listYiShenPi=baseDao.findAll(sql);
		if(listYiShenPi.size()!=0){
			HashMap mapYiShenPi=(HashMap)listYiShenPi.get(0);
			String groupCode=mapYiShenPi.get("GROUPCODE")+"";
			List daiShenPiList=new ArrayList();
			List daiShenPiListGroup=new ArrayList();
			boolean bool=false;
			TreeSet groupCodeList=new TreeSet();
			HashMap mapNew=new HashMap();
			for(int i=0;i<listShenPiAll.size();i++){
				HashMap allMap=(HashMap)listShenPiAll.get(i);
				String groupCodeTemp=allMap.get("GROUPCODE")+"";
				String datapermissions=allMap.get("DATAPERMISSIONS")+"";
				if(groupCodeTemp.equals(groupCode)){
					bool=true;
				}
				if(bool&&datapermissions.indexOf(executeorgid)!=-1){
					groupCodeList.add( groupCodeTemp+"");
					String oldDisplayname= mapNew.get("groupcode_"+groupCodeTemp)==null?"": mapNew.get("groupcode_"+groupCodeTemp)+"/----/";
					mapNew.put("groupcode_"+groupCodeTemp, oldDisplayname+allMap.get("DISPLAYNAME")+"");
				}
			}
			daiShenPiListGroup.addAll(groupCodeList);
			for(int i=0;i<daiShenPiListGroup.size();i++){
				String abc=daiShenPiListGroup.get(i)+"";
				HashMap map=new HashMap();
				map.put("DISPLAYNAME", mapNew.get("groupcode_"+abc));
				daiShenPiList.add(map);
				
			}
			
			
			return daiShenPiList;
		}
		return null;
	}



}

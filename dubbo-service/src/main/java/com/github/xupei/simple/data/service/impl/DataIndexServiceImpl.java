package com.github.xupei.simple.data.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.xupei.dubbo.api.data.IDataIndexService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;
import com.github.xupei.simple.util.UUIDTool;

public class DataIndexServiceImpl extends GridSqlUtilTool implements IDataIndexService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	
	public String getSql(Map<String, String> paramMap){
		String sql1="select CODE,ID,STIME,CONTENT,sdate,SCHEDULERID,SORDER,orgname,STYPENAME,   case when STATE = 1 then '未完成' when STATE = 0 then '完成' end as STATE," +
				"STYPE,EMERY,RECEIVEOBJECT ,TEAMCODE,RECEIVEUSER ,FINISHEDDATE,FINISHTHING,teamid,schedulername from (";
		String sql ="  Select dsd.ID AS CODE,dsd.ID,dsd.STIME,dsd.CONTENT,dsd.STATE,ny_typevalue(dsd.STYPE) STYPE," +
				"o.orgid,o.orgname,nh_typevalue(dsd.STYPE,'调度类别') STYPENAME, "+
				" ny_typevalue(dsd.EMERY) EMERY,RECEIVEOBJECT,dsd.RECEIVEUSER," +
				"dsd.FINISHEDUSER,dsd.FINISHEDDATE FINISHEDDATE,dsd.FINISHTHING,dsd.TEAMCODE," +
				"dsd.SDATE,dsd.SCHEDULERID,dsd.SORDER,s.teamid,dst.schedulername " +
				"From 	DATA_SCHEDULER_INDEX s,data_scheduler_team dst,DATA_SCHEDULER_DETAIL dsd left join sys_org o on dsd.orgcode=o.orgid   Where 1=1 ";
		if (!paramMap.get("state").isEmpty()) {//  事件类型:重大或者明细
			if(Integer.valueOf(paramMap.get("state")).intValue()==2){
			     sql+=" and dsd.STATE in (1,0) ";
			}else {
				sql+=" [ and dsd.STATE={state} ] ";
			}
		}
		//工作内容
		sql+=" [ and dsd.CONTENT  like '%{content}%' ] ";
		sql+=" and dsd.schedulerid = s.schedulerid and s.teamid= dst.teamid  " ;
		 	 /* " and dsd.orgcode=o.orgid  ";*/
		sql+=" order by to_date(dsd.sdate,'yyyy-MM-dd') desc,SORDER desc ";
		String sql2=" ) temp where 1=1 ";
		//开始时间
		sql2+=" [ and to_date(sdate,'yyyy-mm-dd') >= to_date('{startdate}','yyyy-mm-dd') ] ";
			 //sql2+="  and  to_date(sdate,'yyyy-mm-dd')>=to_date('"+paramMap.get("startdate")+"','yyyy-mm-dd')";}
		//结束时间
		sql2+=" [ and to_date(sdate,'yyyy-mm-dd') <= to_date('{enddate}','yyyy-mm-dd') ] ";
			 
		 String sqlimp="   union  select importeventid AS CODE,importeventid AS id,to_char(t.sdate, 'hh24:mi:ss') STIME, "+
		 //String sqlimp="   union all select id,to_char(t.sdate, 'hh24:mi:ss') STIME, "+
		 "       t.note CONTENT, "+
		 "       to_char(t.sdate, 'yyyy-MM-dd') sdate, "+
		 "       t.schedulerid SCHEDULERID, "+
		 "       1 SORDER, "+
		 "       ' ' STATE, "+
		 "       '重大事项' STYPE, "+
		 "		 ' '  orgname,"+
         "       ' '  STYPENAME,"+
		 "       '重大事项' EMERY, "+
		 "       '中控室' RECEIVEOBJECT, "+
		 "       'null'   TEAMCODE, "+
		 "       ny_username(t.creator) RECEIVEUSER, "+
		 "        '' FINISHEDDATE, "+
		 "       '' FINISHTHING, s.teamid,dst.schedulername "+
		 "  from data_scheduler_importevent t, DATA_SCHEDULER_INDEX   s, data_scheduler_team  dst "+
		 "  where 1=1 and t.schedulerid = s.schedulerid and s.teamid = dst.teamid";
		 //重大事项
		 sqlimp+="  [ and 4={state} ] ";
		 //工作内容
		 sqlimp+=" [ and t.note  like '%{content}%' ] ";
		 //开始时间
		 sqlimp+=" [ and t.sdate >= to_date('{startdate}','yyyy-mm-dd') ] ";
		 //结束时间
		 sqlimp+=" [ and t.sdate <= to_date('{enddate}','yyyy-mm-dd') ] ";
		 
		 String sqldetail="select * from  ( "+sql1+sql+sql2+sqlimp+" )  order by sdate desc ,to_date(stime,'HH24:mi:ss') desc ";
		return sqldetail;
	}
	
	public Map getDataIndexList(Map<String, String> paramMap) throws Exception {
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
	public List editDataIndex(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="SELECT T.* from data_scheduler_detail T where 1=1 " ;
		
		if (!id.isEmpty()) {
			sql +=" and T.ID = '"+id+"'";
		}
		
		return baseDao.findAll(sql);
		
	}

	public List checkDataIndex(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		String sql="select su.userid,su.orgid,su.password,su.displayname from sys_user su where 1=1   [  and username='{userName}']  ";
		return baseDao.findAll(sql, map);
	}

	@Override
	public List getDataIndexRightList(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="select sr.permissions from sys_user su ,sys_role sr where sr.roleid=su.roleid and su.userid='"+id+"' ";
		Map<String, String> map=new HashMap();
		List list=baseDao.findAll(sql,  map);
		String permissions=((HashMap)list.get(0)).get("PERMISSIONS").toString();
		List menuList=getParentList(permissions);
		
		List rightList=new ArrayList();
		for(int i=0;i<menuList.size();i++){
			
			HashMap mapMenu=(HashMap)menuList.get(i);
			if(Integer.parseInt(mapMenu.get("NUMS").toString())>=1){
				mapMenu.put("hasSub", true);
				mapMenu.put("menus", getSubRightList(mapMenu.get("id").toString(),permissions));
			}else{
				mapMenu.put("hasSub", true);
			}
			rightList.add(mapMenu);
			
		}
		return rightList;
	}
	
	public List getParentList(String permissions ) throws Exception{
		String sql="select t.* ,(select count(1) from sys_menu sm where sm.parentid=t.menuid) nums from sys_menu t where del = '1' and parentid='0' and t.menuid in ('"+permissions.replace(",", "','")+"') order by to_number(orderid) ";
		
		List list= baseDao.findAll(sql,  new HashMap());
		ArrayList menuList=new ArrayList();
		for(int i=0;i<list.size();i++){
			HashMap map=(HashMap)list.get(i);
			HashMap mapMenu=new HashMap();
			String name=map.get("MENUNAME")+"";
			String id=map.get("MENUID")+"";
			String icon=map.get("ICONCLASS")==null?"icon-leaf":map.get("ICONCLASS")+"";
			mapMenu.put("text", name);
			mapMenu.put("id", id);
			mapMenu.put("icon", icon);
			mapMenu.put("url", "''");
			mapMenu.put("NUMS", map.get("NUMS"));
			menuList.add(mapMenu);
		}
		
		return menuList;
	}
	
	public List getSubRightList(String menuid,String permissions) throws Exception{
		String sql="select t.*  "+
				"  from sys_menu t "+
				"  where del = '1' and  t.menuid in ('"+permissions.replace(",", "','")+"') and t.parentid='"+menuid+"' order by orderid ";
		List list= baseDao.findAll(sql,  new HashMap());
		ArrayList menuList=new ArrayList();
		for(int i=0;i<list.size();i++){
			HashMap map=(HashMap)list.get(i);
			HashMap mapMenu=new HashMap();
			String name=map.get("MENUNAME")+"";
			String id=map.get("MENUID")+"";
			String icon=map.get("ICONCLASS")==null?"icon-leaf":map.get("ICONCLASS")+"";
			String url=map.get("MENUURL")+"";
			mapMenu.put("text", name);
			mapMenu.put("id", id);
			mapMenu.put("icon", icon);
			mapMenu.put("url", url);
			
			menuList.add(mapMenu);
		}
		
		return menuList;
	}

	/**
	 * 新增值班明细
	 */
	@Override
	public void saveDataIndex(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sdate=paramMap.get("sdate");
		
		String sql="insert into data_scheduler_detail(id,stime,sdate,sorder,content,schedulerid,stype,emery,finisheduser,userid,state,orgcode) values('"+UUIDTool.getUUID()+"','"+sdate.substring(11, 19)+"','"+
				sdate.substring(0,11)+"','"+Integer.parseInt((sdate.substring(11, 19).replace(":", "")).substring(0, 5))+"','"+paramMap.get("content")+"','"+paramMap.get("schedulerid")+"','"+
				paramMap.get("stype")+"','"+paramMap.get("emery")+"','"+paramMap.get("finisheduser")+"','"+paramMap.get("loginid")+"' ,1, '"+paramMap.get("orgcode")+"') ";
	    baseDao.addObject(sql);
	}
	
	/**
	 * 新增班次
	 */
	@Override
	public void saveData(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql="insert into data_scheduler_index "+
				"  (schedulerid, "+
				"   startdate, "+
				//"   enddate, "+
				"   watchcode, "+
				"   successorcode, "+
				"   program, "+
				"   schedulerorder, "+
				"   watchprincipal, "+
				"   successorprincipal, "+
				"   note, "+
				"   currscheduler, "+
				"   teamid) "+
				" values('"+paramMap.get("schedulernewid")+"', sysdate, '"+
				paramMap.get("watchcode")+"', '"+
				paramMap.get("successorcode")+"', "+
				"   '1112150001', '"+
				paramMap.get("schedulerorder")+"', '"+
				paramMap.get("watchprincipal")+"', '"+
				paramMap.get("successorprincipal")+"', '"+
				paramMap.get("note")+"' ,'1', "+
				"   '"+paramMap.get("teamid")+"') ";
	    baseDao.addObject(sql);
	}
	
	/**
	 * 新增重大事件
	 */
	@Override
	public void saveZDDataIndex(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		//String sdate=paramMap.get("sdate");
		
		String sql="insert into data_scheduler_importevent(importeventid,schedulerid,note,creator) values('"+UUIDTool.getUUID()+"','"+
				paramMap.get("schedulerid")+"','"+paramMap.get("note")+"','"+paramMap.get("loginName")+"') ";
	    baseDao.addObject(sql);
	}
	
	public String getDisplayname(String watch) throws Exception {
		String username="";
		String displayname="";
	
		String temp []=watch.split(",");
		for(int i=0;i<temp.length;i++){
			List list2=getDisplayName(temp[i]);
			HashMap map=(HashMap)list2.get(0);
			displayname+=map.get("DISPLAYNAME")+", ";
		}
		
		return displayname;
	}
	public List getDisplayName(String username) throws Exception {
		String sql="select displayname from sys_user where username='"+username.trim()+"'";
		return baseDao.findAll(sql);
	}
	
	//验证密码
	public int validPassword(Map<String, String> paramMap) throws Exception{
			
		String fzrSql = "SELECT PASSWORD FROM SYS_USER T WHERE 1=1 and USERNAME='"+paramMap.get("fzr")+"'  ";//
		List fzrList = baseDao.findAll(fzrSql);
		String fzrPwd = null;
		if(fzrList!=null&&fzrList.size()>0){
			HashMap map = (HashMap)fzrList.get(0);
			fzrPwd = String.valueOf(map.get("PASSWORD"));
		}
		
		if(fzrPwd==null || !fzrPwd.equals(paramMap.get("password"))){
			
			return 1;
		}
		return 0;
			
	}
	
	
	public List getDataTeamByTeamid(String id) throws Exception{
		String sql = "SELECT * FROM data_scheduler_team WHERE teamid="+id;
		List sqlList = baseDao.findAll(sql);
		return sqlList;
	}
	
	/**
	 * 修改
	 */
	@Override
	public void editDataIndex(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sdate=paramMap.get("sdate");
		
		String sql="update  data_scheduler_detail set stime='"+sdate.substring(11, 19)+"',sdate='"+sdate.substring(0,11)+
				"',content='"+paramMap.get("content")+"',stype='"+paramMap.get("stype")+"',emery='"+paramMap.get("emery")+
				"',orgcode='"+paramMap.get("orgcode")+"',finisheduser='"+paramMap.get("finisheduser")+"',state=0  where id='"+paramMap.get("codeid")+"' and sorder='"+paramMap.get("sorder")+"'";
		
	    baseDao.addObject(sql);
	}
	
	//更新交班信息
	@Override
	public void editData(Map<String, String> paramMap) throws Exception {
		String upd_zb_sql = "UPDATE DATA_SCHEDULER_INDEX SET ENDDATE=sysdate," + "SUCCESSORCODE='" + paramMap.get("successorcode")
			+ "',SUCCESSORPRINCIPAL='" + paramMap.get("successorprincipal") + "',NOTE='"
			+ paramMap.get("note") + "',CURRSCHEDULER='0'" + " WHERE SCHEDULERID='"
			+ paramMap.get("schedulerid") + "'";
		baseDao.addObject(upd_zb_sql);
	}
	
	/**
	 * 删除
	 */
	public void deleteDataIndex(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="delete from data_scheduler_detail where ID IN ('"+id+"')";
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
		String sql="select t.*, '调度类别' as pname "+
				"  from bas_powertype t "+
				" where isdel = 1 and t.pid = "+
				"       (select t.id as pname from bas_powertype t where t.name = '调度类别') ";
		sql+=" union select t.*, '应急级别' as pname "+
				"  from bas_powertype t "+
				" where isdel = 1 and t.pid = "+
				"       (select t.id as pname from bas_powertype t where t.name = '应急级别') ";
		
		return baseDao.findAll(sql);
		
	}
	
	/*
	 * 查询当前值班人信息
	 */
	public List findDqZbr(Map<String, String> paramMap) throws Exception{
		String sql = "select t2.schedulername,t1.* from" 
	            +" (select * from DATA_SCHEDULER_INDEX"
	            +" where CURRSCHEDULER=1"
	            +" and (TEAMID ,SCHEDULERID) in"
	            +" (select TEAMID ,max (SCHEDULERID)  from data_scheduler_index"
	            +" where CURRSCHEDULER=1"
	            +" and TEAMID is not null"
	            +" group by TEAMID)"
	            +" ) t1,data_scheduler_team t2"
	            +" where t1.TEAMID=t2.TEAMID and t2.parentorg in (SELECT ORGID FROM SYS_USER WHERE DEL=1 AND USERNAME IN ('"+paramMap.get("LoginName")+"'))";
		
		List list = baseDao.findAll(sql);
		
		return list;
	}
	
	/*
	 * 查询当前值班人权限
	 */
	public List getZbrListByWatchcode(String watchcode) throws Exception{
		List list=new ArrayList();
		if(!watchcode.isEmpty()){
			String[] zbrArry = watchcode.split(",");
			String ursql = "SELECT * FROM SYS_USER T WHERE DEL=1 AND USERNAME IN (";
			String str = "";
			for (int i = 0; i < zbrArry.length; i++) {
				str += str=="" ? "'"+zbrArry[i].trim()+"'" : ",'"+zbrArry[i].trim()+"'";
			}
			ursql += str+")";
			list = baseDao.findAll(ursql);
		}
		
		return list;
	}
	
	/**
	 * 修改查询方法
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List selectDataTeam(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="SELECT T.* FROM data_scheduler_team T " +
				" WHERE 1=1 ";
		
		if (!id.isEmpty()) {
			sql +=" and T.TEAMID = '"+id+"'";
		}
		
		return baseDao.findAll(sql);
		
	}
	
	public List getDataTeamList(String uname) throws Exception{
		String sql = "SELECT T.TEAMID, S.ORGNAME AS PARENTORG,"
				+ " T.SCHEDULERNAME, T.PRINCIPAL, T.WATCH"
				+ " FROM DATA_SCHEDULER_TEAM T, SYS_ORG S"
				+ " WHERE T.PARENTORG = S.ORGID " +
				" and T.PARENTORG in (SELECT ORGID FROM SYS_USER WHERE DEL=1 AND USERNAME IN ('"+uname+"'))" +
				" ORDER BY T.TEAMID DESC";
 	     List bzList = baseDao.findAll(sql);
 	     return bzList;
	}
	
	public List select_sysdate() throws Exception{
		String sql="select to_char(sysdate,'yyyy-MM-dd hh24:mi:ss') as temp from dual ";
		List list=baseDao.findAll(sql);
		return list;
	}
	
	public String getMaxSorderBySchedulerid(String schedulerid) throws Exception{
		 String sql="select Max(SORDER) as SORDER from data_scheduler_detail where schedulerid='"+schedulerid+"'";
		 List list=baseDao.findAll(sql);
		 return list.isEmpty()?"":((HashMap)list.get(0)).get("SORDER")+"";
	 }
	
	public static void main(String arg []){
		for(int i=0;i<100;i++){
			SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
			System.out.println(simple.format(new Date()));
		}
		
	}



}

package com.github.xupei.simple.scada.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.xupei.dubbo.api.scada.IAlarmAnalysisService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;
import com.github.xupei.simple.util.UUIDTool;

public class AlarmAnalysisServiceImpl extends GridSqlUtilTool implements IAlarmAnalysisService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	
	public String getSql(Map<String, String> paramMap){
		
		String sql="select t.*,to_char(t.scadatime,'yyyy-MM-dd') as time,(select count(*) from ALARM_ANALYSIS group by scadatime) as cs,s.name as cadername,z.feedname,'图案' as tabz from " +
				"ALARM_ANALYSIS t,BAS_POWERTYPE s,BAS_FEED z where t.del=0 and t.alarmcadre=s.id and t.feedcode=z.feedcode order by alarmname,cadername ";//[and USERNAME IN ('{LoginName}') ] )
		/*if (!paramMap.get("name").isEmpty()) {
			sql +=" and T.SCHEDULERNAME like '%"+paramMap.get("name")+"%'";
		}*/
		//sql+=" ORDER BY T.TEAMID DESC";
		
		return sql;
	}
	
	public Map getAlarmAnalysisList(Map<String, String> paramMap) throws Exception {
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
	public List editAlarmAnalysis(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="SELECT T.*,S.ORGNAME as PARENTORG,S.ORGID FROM data_scheduler_team T,sys_org S" +
				" WHERE T.PARENTORG=S.ORGID ";
		
		if (!id.isEmpty()) {
			sql +=" and T.TEAMID = '"+id+"'";
		}
		
		return baseDao.findAll(sql);
		
	}

	public List checkAlarmAnalysis(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		String sql="select su.userid,su.orgid,su.password,su.displayname from sys_user su where 1=1   [  and username='{userName}']  ";
		return baseDao.findAll(sql, map);
	}

	@Override
	public List getAlarmAnalysisRightList(String id) throws Exception {
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
	 * 新增
	 */
	@Override
	public void saveAlarmAnalysis(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String watchname=getDisplayname(paramMap.get("watch"));
		String principalDis=getDisplayname(paramMap.get("principal"));
		paramMap.put("watchname", watchname);
		paramMap.put("principalDis", principalDis);
		String sql="insert into data_scheduler_team(teamid,schedulername,parentorg,principal,watch,program,principalDis,watchname) values(DATA_SCHEDULER_TEAM_SEQ.NEXTVAL,'"+paramMap.get("schedulername")+"','"+
		paramMap.get("parentorg")+"','"+paramMap.get("principal")+"','"+paramMap.get("watch")+"','"+paramMap.get("program")+"','"+paramMap.get("principalDis")+"','"+paramMap.get("watchname")+"') ";
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
	
	/**
	 * 修改
	 */
	@Override
	public void editAlarmAnalysis(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String watchname=getDisplayname(paramMap.get("watch"));
		String principalDis=getDisplayname(paramMap.get("principal"));
		paramMap.put("watchname", watchname);
		paramMap.put("principalDis", principalDis);
		String sql="update  data_scheduler_team set schedulername='"+paramMap.get("schedulername")+"',parentorg='"+paramMap.get("parentorg")+
				"',principal='"+paramMap.get("principal")+"',watch='"+paramMap.get("watch")+"',program='"+paramMap.get("program")+
				"',principalDis='"+paramMap.get("principalDis")+"',watchname='"+paramMap.get("watchname")+"' where teamid='"+paramMap.get("teamid")+"'";
	    baseDao.addObject(sql);
	}
	
	/**
	 * 删除
	 */
	public void deleteAlarmAnalysis(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="DELETE FROM data_scheduler_team WHERE teamid IN ("+id+")";
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

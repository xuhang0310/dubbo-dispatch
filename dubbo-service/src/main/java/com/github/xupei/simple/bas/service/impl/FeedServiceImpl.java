package com.github.xupei.simple.bas.service.impl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.github.xupei.dubbo.api.IFeedService;
import com.github.xupei.simple.dao.BaseDao;

public class FeedServiceImpl implements IFeedService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	@Override
	public String getSql(Map<String, String> paramsMap) {
		StringBuffer sBuffer = new StringBuffer(""); 
		 sBuffer.append("  select t.feedid,t.feedname,s.orgname,t.sjfh,t.cnmj,t.szwz,p.pic_fname gytid, t.feedid code"); 
		 sBuffer.append("  from bas_feed t, sys_org s,picture_set p"); 
		 sBuffer.append("  where 1 = 1"); 
		 sBuffer.append("  and t.orgid = s.orgid  and t.gytid=p.id(+)");
		 sBuffer.append("  [and t.feedname like'%{feedname}%']");
		 sBuffer.append("  [and t.feedcode in ({feedcodes})]");
		 sBuffer.append(" order by t.feedcode ");
		return sBuffer.toString();
	}

	
	
	
	public Map getFeedList(Map<String, String> paramMap) throws Exception {
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
	public List editFeed(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="select t.*,t.feedid code,s.orgid,s.orgname  from bas_feed t,sys_org s where 1=1 and t.orgid=s.orgid ";
		
		if (!id.isEmpty()) {
			sql +=" and t.feedid = '"+id+"'";
		}
		
		return baseDao.findAll(sql);
		
	}

	public List checkFeed(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		String sql="select su.userid,su.orgid,su.password,su.displayname from sys_user su where 1=1   [  and username='{userName}']  ";
		return baseDao.findAll(sql, map);
	}

	@Override
	public List getFeedRightList(String id) throws Exception {
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
	public void saveFeed(Map<String, String> paramMap) throws Exception {
		// {feedid=, feedname=dfdfd, orgid=0, sjfh=44, cnmj=55, szwz=66, gytid=8}
	   String sql="insert into bas_feed(feedname,orgid,sjfh,cnmj,szwz,gytid)values('"+
			   paramMap.get("feedname")+"','"+paramMap.get("orgid")+"','"+paramMap.get("sjfh")+
			   "','"+paramMap.get("cnmj")+"','"+paramMap.get("szwz")+"','"+paramMap.get("gytid")+"')";
		baseDao.addObject(sql);
	}
	
	/**
	 * 修改
	 */
	@Override
	public void editFeed(Map<String, String> paramMap) throws Exception {
		// {feedid=3, feedname=陈塘电厂, orgid=12, sjfh=200, cnmj=100, szwz=dddd, gytid=8}
		String sql="update  bas_feed set feedname='"+paramMap.get("feedname")+"',orgid='"+paramMap.get("orgid")+"',sjfh='"+paramMap.get("sjfh")+
				"',cnmj='"+paramMap.get("cnmj")+"',szwz='"+paramMap.get("szwz")+"',gytid='"+paramMap.get("gytid")+"'"+
				"  where feedid='"+paramMap.get("feedid")+"'";
	    baseDao.addObject(sql);
	}
	
	/**
	 * 删除
	 */
	public void deleteFeed(String feedid) throws Exception {
		// TODO Auto-generated method stub
		String sql="delete from bas_feed where feedid='"+feedid+"'";
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
	
	public static void main(String arg []){
		for(int i=0;i<100;i++){
			SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
			System.out.println(simple.format(new Date()));
		}
		
	}

	@Override
	public List getFeedAllList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		String sql="select * from bas_feed ";
		return baseDao.findAll(sql);
	}




	@Override
	public List getGyt() throws Exception {
		String sql="select t.id,t.pic_name from picture_set t";
		return baseDao.findAll(sql);
	}




	@Override
	public Map getSummaryMap(Map<String, String> paramsMap) throws Exception {
		StringBuffer sBuffer=new StringBuffer("");
		sBuffer.append("select count(*)num,sum(sjfh)sjfh,sum(cnmj)cnmj from (");
		sBuffer.append(getSql(paramsMap));
		sBuffer.append(")");
		return baseDao.findMap(sBuffer.toString(), paramsMap);
	}

	


}

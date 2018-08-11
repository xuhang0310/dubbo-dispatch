package com.github.xupei.simple.data.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.xupei.dubbo.api.data.IDataDicService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;

public class DataDicServiceImpl extends GridSqlUtilTool implements IDataDicService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	
	public String getSql(Map<String, String> paramMap){
		
		String sql="select t.*,t.id as code,t.id as picid,s.name as pname from BAS_POWERTYPE t,BAS_POWERTYPE s where t.isdel=1 and s.id=t.pid";
		if (!paramMap.get("name").isEmpty()) {
			sql +=" [ and t.name   like '%{name}%'] ";
		}
		if (!paramMap.get("id").isEmpty()) {
			sql +=" [ and ( t.id={id} or t.pid={id} )   ] ";
		}
		sql+=" ORDER BY t.ID ASC";
		
		return sql;
	}
	
	public Map getDataDicList(Map<String, String> paramMap) throws Exception {
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
	public List editDataDic(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="select t.* from BAS_POWERTYPE t where 1=1 ";
		
		if (!id.isEmpty()) {
			sql +=" and t.ID = '"+id+"'";
		}
		
		return baseDao.findAll(sql);
		
	}

	public List checkDataDic(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		String sql="select su.userid,su.orgid,su.password,su.displayname from sys_user su where 1=1   [  and username='{userName}']  ";
		return baseDao.findAll(sql, map);
	}

	@Override
	public List getDataDicRightList(String id) throws Exception {
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
	public void saveDataDic(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		String sql="insert into BAS_POWERTYPE(id,name,pid,isdel) values(BAS_POWERTYPE_SEQ.NEXTVAL,'"+paramMap.get("name")+"','"+paramMap.get("pid")+"',1) ";
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
	public void editDataDic(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		String sql="update  BAS_POWERTYPE set name='"+paramMap.get("name")+"',pid='"+paramMap.get("pid")+"' where id='"+paramMap.get("id")+"'";
	    baseDao.addObject(sql);
	}
	
	/**
	 * 删除
	 */
	public void deleteDataDic(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="update BAS_POWERTYPE set isdel=0 WHERE id IN ("+id+")";
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
		String sql="select t.* from BAS_POWERTYPE t where 1=1 and t.isdel=1 and t.id=0 or t.pid=0";
		
		return baseDao.findAll(sql);
		
	}
	
	public static void main(String arg []){
		for(int i=0;i<100;i++){
			SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
			System.out.println(simple.format(new Date()));
		}
		
	}

	/**
	 * 左侧树
	 * @param arg
	 */
	@Override
	public List getDataDicTree(Map<String, String> paramsMap) throws Exception {
		// TODO Auto-generated method stub
		
			String sql="select id \"id\",pid \"pid\" ,name \"title\"  from BAS_POWERTYPE  where isdel=1 ";
			sql +="  order by id asc ";
			return baseDao.findAll(sql);
	}



}

package com.github.xupei.simple.bas.service.impl;

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

import com.github.xupei.dubbo.api.IScadaStationService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.util.UUIDTool;

public class ScadaStationServiceImpl implements IScadaStationService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	
	public Map getScadaStationList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String page = paramMap.get("page");
		String rows = paramMap.get("rows");
		
		String sql="select t.*,decode(t.stationtype,'1','换热站','99','热源','') stationtypename,t.id as code, v.name "+
				"  from scada_station t, scada_station_view v "+
				" where 1 = 1 "+
				"   and t.stationcode = v.id "+
				"   and t.stationtype = v.typeval ";
		
		if (!paramMap.get("name").isEmpty()) {
			sql +=" and v.name like '%"+paramMap.get("name")+"%'";
		}
		
		paramMap=new HashMap<String, String>();
		paramMap.put("page",page);//第几页
		paramMap.put("rows",rows);//一页显示几行数据
		return baseDao.queryGridList(sql,paramMap);
		
	}
	
	/**
	 * 修改查询方法
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List editScadaStation(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="select t.*,decode(t.stationtype,'1','换热站','99','热源','') stationtypename,t.id as code, v.name "+
				"  from scada_station t, scada_station_view v "+
				" where 1 = 1 "+
				"   and t.stationcode = v.id "+
				"   and t.stationtype = v.typeval ";
		
		if (!id.isEmpty()) {
			sql +=" and t.id = '"+id+"'";
		}
		
		return baseDao.findAll(sql);
		
	}

	public List checkScadaStation(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		String sql="select su.userid,su.orgid,su.password,su.displayname from sys_user su where 1=1   [  and username='{userName}']  ";
		return baseDao.findAll(sql, map);
	}

	@Override
	public List getScadaStationRightList(String id) throws Exception {
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
	public void saveScadaStation(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql="insert into scada_station(id,linecode,stationtype,stationcode) values('"+UUIDTool.getUUID()+"','bdxc002','"+paramMap.get("stationtype")+"','"+paramMap.get("stationcode")+"')";
	    baseDao.addObject(sql);
	}
	
	/**
	 * 修改
	 */
	@Override
	public void editScadaStation(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql="update  scada_station set stationtype='"+paramMap.get("stationtype")+"',stationcode='"+paramMap.get("stationcode")+"'  where id='"+paramMap.get("id")+"'";
	    baseDao.addObject(sql);
	}
	
	/**
	 * 删除
	 */
	public void deleteScadaStation(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="delete from scada_station where id='"+id+"'";
	    baseDao.deleteObject(sql);
	}
	/**
	 * 页面 公司 下拉框
	 */
	public List orgList() throws Exception {
		// TODO Auto-generated method stub
		String sql="select s.orgid,s.orgname  from sys_org s where 1=1 and del=1 ";
		
		return baseDao.findAll(sql);
		
	}
	/**
	 * 页面 热源 下拉框
	 */
	public List feedList() throws Exception {
		// TODO Auto-generated method stub
		String sql="select s.feedid,s.feedname  from bas_feed s where 1=1 ";
		
		return baseDao.findAll(sql);
		
	}
	/**
	 * 页面 换热站 下拉框
	 */
	public List nodeList() throws Exception {
		// TODO Auto-generated method stub
		String sql="select s.nodeid,s.nodename  from bas_node s where 1=1 ";
		
		return baseDao.findAll(sql);
		
	}
	/**
	 * 页面  下拉框
	 */
	public List feednodeList() throws Exception {
		// TODO Auto-generated method stub
		String sql="select *  from scada_station_view s where 1=1 ";
		
		return baseDao.findAll(sql);
		
	}
	
	public static void main(String arg []){
		for(int i=0;i<100;i++){
			SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
			System.out.println(simple.format(new Date()));
		}
		
	}




}

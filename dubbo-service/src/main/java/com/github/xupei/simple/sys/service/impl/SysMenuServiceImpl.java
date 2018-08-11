package com.github.xupei.simple.sys.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.xupei.dubbo.api.ISysMenuService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.util.UUIDTool;

public class SysMenuServiceImpl implements ISysMenuService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	
	public Map getMenuList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String page = paramMap.get("page");
		String rows = paramMap.get("rows");
		
		String sql="select t.*,t.menuid as code,s.menuname parentname from sys_menu t left join sys_menu s on t.parentid = s.menuid where t.del=1 ";
		
		sql+=" [ and ( t.menuid={menuid} or t.parentid={menuid} )   ] [ and t.menuname   like '%{menuname}%'] ";
		sql+=" order by t.menulevel,t.orderid asc ";
		
		
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
	public List editMenu(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="select t.*,t.menuid as code,s.menuname parentname from sys_menu t left join sys_menu s on t.parentid = s.menuid where t.del=1 ";
		
		if (!id.isEmpty()) {
			sql +=" and t.menuid = '"+id+"'";
		}
		
		return baseDao.findAll(sql);
		
	}

	

	/**
	 * 新增
	 */
	@CacheEvict(value="sysMenuCache",allEntries=true)
	public void saveMenu(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql="insert into sys_menu(menuid,orderid,menuname,menuurl,parentid,menulevel,treeurl,iconclass,del) " +
				"values("+paramMap.get("menuid")+","+paramMap.get("orderid")+",'"+
				paramMap.get("menuname")+"','"+paramMap.get("menuurl")+"',"+
				paramMap.get("parentid")+","+paramMap.get("menulevel")+",'"+
				paramMap.get("treeurl")+"','"+paramMap.get("iconclass")+"',1) ";
	    baseDao.addObject(sql);
	}
	
	/**
	 * 修改
	 */
	@CacheEvict(value="sysMenuCache",allEntries=true)
	public void editMenu(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql="update  sys_menu set orderid="+paramMap.get("orderid")+",menuname='"+
		paramMap.get("menuname")+"',menuurl='"+
		paramMap.get("menuurl")+"',parentid="+
		paramMap.get("parentid")+",menulevel="+
		paramMap.get("menulevel")+",treeurl='"+
		paramMap.get("treeurl")+"',iconclass='"+
		paramMap.get("iconclass")+"'  where menuid='"+
		paramMap.get("id")+"'";
	    baseDao.addObject(sql);
	}
	
	/**
	 * 删除
	 */
	@CacheEvict(value="sysMenuCache",allEntries=true)
	public void deleteMenu(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="update  sys_menu set del=0  where menuid='"+id+"'";
	    baseDao.deleteObject(sql);
	}
	/**
	 * 页面 菜单下拉框
	 */
	public List orgList() throws Exception {
		// TODO Auto-generated method stub
		String sql="select s.menuid,s.menuname  from sys_menu s where 1=1 and s.del=1 and s.menulevel not like '3' order by s.menuid,s.orderid asc ";
		
		return baseDao.findAll(sql);
		
	}
	/**
	 * 页面 热源 下拉框
	 */
	public List feedList() throws Exception {
		// TODO Auto-generated method stub
		String sql="select s.feedid,s.feedname  from bas_feed s where 1=1";
		
		return baseDao.findAll(sql);
		
	}
	/**
	 * 左侧树
	 * @param arg
	 */
	public List getMenuListForTree(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String tree = paramMap.get("tretype");
		String sql="select menuid \"id\",parentid \"pid\" ,menuname \"title\"  from sys_menu  where del=1 ";
		if (tree!=null) {
			sql +=" and menulevel not like '3' ";
		}
		sql +="  order by menuid,orderid asc ";
		return baseDao.findAll(sql);
	}
	
	public static void main(String arg []){
		for(int i=0;i<100;i++){
			SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
			System.out.println(simple.format(new Date()));
		}
		
	}

	@Override
	public List parentList() throws Exception {
		// TODO Auto-generated method stub
		String sql="select menuid ID,parentid PID ,menuname NAME  from sys_menu  where del=1 ";
		sql +=" and menulevel not like '3' ";
		sql +="  order by menuid,orderid asc ";
		return baseDao.findAll(sql);
	}



}

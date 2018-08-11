package com.github.xupei.simple.sys.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;



import com.github.xupei.dubbo.api.IRoleManageService;
import com.github.xupei.dubbo.bean.SysUser;
import com.github.xupei.simple.dao.BaseDao;






@Service
public class RoleManageServiceImpl implements IRoleManageService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	
	public Map getRoleManageList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql=" SELECT ID ID , ROLENAME ROLENAME, ORDERID ORDERID, ORGNAME ORGNAME, ROLETYPE ROLETYPE, NOTE NOTE, FUNCTION FUNCTION,ID ID2"+
              "  FROM (SELECT T.ROLEID ID, T.*, O.ORGNAME, T.ROLEID FUNCTION "+
                 "       FROM SYS_ROLE T, SYS_ORG O "+
                  "     WHERE T.ORGID = O.ORGID(+)) "+
         "      WHERE 1 = 1 "+
           "      AND DEL = 1 ";
      //     "   ORDER BY ROLEID DESC";
		if(paramMap.get("rolename").isEmpty()){
			sql+=" ORDER BY ROLEID DESC ";
		}else{
			sql+=" and rolename like '%"+paramMap.get("rolename")+"%'" +" ORDER BY ROLEID DESC ";
		}
		
		paramMap=new HashMap<String, String>();
		paramMap.put("page","1");
		paramMap.put("rows","20");
		return baseDao.queryGridList(sql,paramMap);
	}

	public List checkUser(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		String sql="select su.userid,su.orgid,su.password,su.displayname from sys_user su where 1=1   [  and username='{userName}']  ";
		return baseDao.findAll(sql, map);
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

	@Override
	public void saveRole(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		//String sql="insert into sys_user(username,password,displayname,roleid,orgid) values('"+paramMap.get("username")+"','123456','"+paramMap.get("displayname")+"','1','0') ";
	String sql="insert into sys_role(rolename,orgid,note)values ('"+paramMap.get("displayname")+"',"+paramMap.get("tablename")+",'"+
		paramMap.get("bz")+"')";
		
		baseDao.addObject(sql);
	}
	
	
	public void deleteRole(String id) throws Exception {
		// TODO Auto-generated method stub
		
		String sql="delete from sys_role where roleid='"+id+"'";
	    baseDao.deleteObject(sql);
	}
	
	public static void main(String arg []){
		for(int i=0;i<100;i++){
			SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
			System.out.println(simple.format(new Date()));
		}
		
	}

	@Override
	public List editRole(String roleid) throws Exception {
		// TODO Auto-generated method stub
		String sql="select  r.roleid,r.rolename,r.note,o.orgname,r.orgid from sys_role r,sys_org o "+
			"	where r.orgid=o.orgid " +
			"	and r.roleid ="+roleid;
		
		return baseDao.findAll(sql);
	}

	@Override
	public void saveRoleById(Map<String, String> paramsMap) throws Exception {
		//orgid=tablename,note=bz where id=roleid
		String sql="update sys_role set rolename='"  +paramsMap.get("displayname")+"',orgid='"+paramsMap.get("tablename")+
					   "',note='"+paramsMap.get("bz")+"' where roleid="+paramsMap.get("roleid");

		 baseDao.execute(sql);
		
	}

	@Override
	public String getPermission(String roleid) throws Exception {
		String sql="SELECT t.permissions FROM SYS_ROLE t WHERE ROLEID="+roleid;
		return	baseDao.queryForString(sql);
	
	}

	@Override
	public Map StringToMap(String obj) throws Exception {

		Map map = new HashMap();
		if (obj != null && !"".equals(obj)) {
			String[] ary = obj.split(",");
			for (String s : ary) {
				if (s != null && !"".equals(s)) {
					map.put(s, s);
				}
			}
		}
		return map;
	
	}

	@Override
	public StringBuffer queryMenuList(String parentId, StringBuffer result, Map rolemap)
			throws Exception {
		String sql="select t.*, "+
			      " (select count(1)"+
			        "  from sys_menu "+
			       "  where parentid = t.menuid "+
			         "  and del = 1) NUM "+
			         "from sys_menu t "+
			         "where t.del = 1"+
			  " and t.parentid ="+parentId +
			" ORDER BY t.parentid, t.orderid";
	List list=baseDao.findAll(sql);
	for(int i=0;i<list.size();i++){	
		Map map = (Map)list.get(i);
		String menuid = map.get("MENUID")+"";
		String parentid = map.get("PARENTID")+"";
		String menuname = map.get("MENUNAME")+"";
		boolean ischecked = false;
		if(rolemap.containsKey(menuid)) {
			ischecked = true;
		}else{
			ischecked = false;
		}
		if(map.get("NUM")!=null && Integer.valueOf(map.get("NUM")+"")>0){
			result.append(",{'menuid':"+menuid+", 'parentid':"+parentid+", 'text':'"+menuname+"', 'type':'parent', 'ischecked':"+ischecked+", 'isexpand':true, 'children':[");
			queryMenuList(map.get("MENUID")+"",result,rolemap);
		}else{
			result.append(",{'menuid':"+menuid+", 'parentid':"+parentid+", 'text':'"+menuname+"', 'type':'leaf', 'ischecked':"+ischecked+"}");
		}
	}
	result.append("]}");
		
	return result;
		
		
	}

	@CacheEvict(value="sysMenuCache",allEntries=true)
	public void updateRolePermission(String roleid, String permissions)
			throws Exception {
		String sql=" update sys_role set permissions='" +permissions+"'  "+
				" where roleid='"+roleid+"'";
	baseDao.execute(sql);	
		
	}

	@Override
	public List selectAll() throws Exception {
		String sql="select orgid,orgname from sys_org";
		return baseDao.findAll(sql);
	}




}


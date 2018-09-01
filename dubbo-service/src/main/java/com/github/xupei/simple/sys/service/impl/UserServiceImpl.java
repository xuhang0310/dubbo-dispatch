package com.github.xupei.simple.sys.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.xupei.dubbo.api.IUserService;
import com.github.xupei.dubbo.bean.SysUser;
import com.github.xupei.simple.dao.BaseDao;






@Service
public class UserServiceImpl implements IUserService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	
	public String getSql(Map<String, String> paramMap){
		
		StringBuffer sBuffer = new StringBuffer(""); // [and USERNAME IN ('{LoginName}') ] 
		 sBuffer.append("SELECT usertable.*, bas_powertype.name typevalue"); 
		 sBuffer.append("  FROM (SELECT T.USERID   ID,"); 
		 sBuffer.append("               T.*,"); 
		 sBuffer.append("               O.ORGNAME,"); 
		 sBuffer.append("               O.PARENTID,"); 
		 sBuffer.append("               O.ORGID    AS PARAMORGID,"); 
		 sBuffer.append("               R.ROLENAME,"); 
		 sBuffer.append("               T.USERID   FUNCTION"); 
		 sBuffer.append("          FROM SYS_USER T, SYS_ORG O, SYS_ROLE R"); 
		 sBuffer.append("         WHERE T.ORGID = O.ORGID(+)"); 
		 sBuffer.append("           AND T.ROLEID = R.ROLEID(+)) usertable"); 
		 sBuffer.append("  left join bas_powertype"); 
		 sBuffer.append("    on usertable.usergroup = bas_powertype.id"); 
		 sBuffer.append(" where DEL = 1");
		 
		 if(!paramMap.get("username").isEmpty()){
			 sBuffer.append(" AND (USERNAME LIKE '%" + paramMap.get("username") + "%' "); 
			 sBuffer.append(" OR DISPLAYNAME LIKE '%" + paramMap.get("username") + "%' "); 
			 sBuffer.append(" OR MOBILE LIKE '%" + paramMap.get("username") + "%' "); 
			 sBuffer.append(" OR ROLENAME LIKE '%" + paramMap.get("username") + "%' "); 
			 sBuffer.append(" OR ORGNAME LIKE '%" + paramMap.get("username") + "%' "); 
			 sBuffer.append(" OR NAME LIKE '%" + paramMap.get("username") + "%' "); 
			 sBuffer.append(" ) "); 
		 }
					
		return sBuffer.toString();
	}
	
	public Map getUserList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql=getSql(paramMap);
		
		return baseDao.queryGridList(sql,paramMap);
	}

	public List checkUser(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sBuffer = new StringBuffer(""); 
		 sBuffer.append("select su.userid, su.orgid, su.password, su.displayname"); 
		 sBuffer.append("  from sys_user su"); 
		 sBuffer.append(" where 1 = 1  [ and username = '{userName}' ]");
		return baseDao.findAll(sBuffer.toString(), map);
	}

	@Override
	@Cacheable(value="sysMenuCache",key="#userid")
	public List getUserRightList(String userid) throws Exception {
		// TODO Auto-generated method stub
		String sql="select sr.permissions from sys_user su ,sys_role sr where sr.roleid=su.roleid and su.userid='"+userid+"' ";
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
		String sql="select t.* ,(select count(1) from sys_menu sm where sm.parentid=t.menuid) nums from sys_menu t where del = '1' and parentid='0' and t.menuid in ('"+permissions.replace(",", "','")+"') order by orderid ";
		
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
	public void saveUser(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		if (paramMap.get("password").isEmpty()) {
			paramMap.put("password", "123456");
		}
		
		String sql="insert into sys_user(username,password,displayname,mobile,note,roleid,orgid) " +
				"values('"+paramMap.get("username")+"','"+paramMap.get("password")+"','"+paramMap.get("displayname")+"','"+paramMap.get("mobile")+"','"+paramMap.get("note")+"','"+paramMap.get("roleid")+"','"+paramMap.get("orgid")+"') ";
	    baseDao.addObject(sql);
	}
	
	
	public void deleteUser(String userid) throws Exception {
		// TODO Auto-generated method stub
		
		String sql = "UPDATE SYS_USER SET DEL=0 WHERE USERID IN ("+userid+") ";
	    baseDao.deleteObject(sql);
	}
	
	public static void main(String arg []){
		for(int i=0;i<100;i++){
			SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
			System.out.println(simple.format(new Date()));
		}
		
	}

	@Override
	public SysUser checkUserBean(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		String sql="select su.userid,su.orgid,su.password,su.displayname,su.username,ifNull(su.templatestyle,'') templatestyle from sys_user su where 1=1   [  and username='{userName}']  ";
		//String sql="select su.userid,su.orgid,su.password,su.displayname,su.username from sys_user su where 1=1  and username='"+map.get("userName")+"' ";
		return (SysUser) baseDao.findOneObject(sql, map, SysUser.class);
	}

	@Override
	public List getOrgList() throws Exception {
		// TODO Auto-generated method stub
		String sql=" select orgid ID,orgname NAME from SYS_ORG where del=1 order by id ";
		return baseDao.findAll(sql);
	}

	@Override
	public List getRoleList() throws Exception {
		// TODO Auto-generated method stub
		String sql=" select roleid ID,rolename NAME from SYS_ROLE where del=1 order by id ";
		return baseDao.findAll(sql);
	}

	/**
	 * 验证用户是否存在
	 */
	@Override
	public int doValidUsername(Map<String, String> paramsMap) throws Exception {
		// TODO Auto-generated method stub
		String fzrSql = "SELECT USERNAME FROM SYS_USER T WHERE 1=1 and USERNAME = '"+paramsMap.get("username")+"'  ";//
		List fzrList = baseDao.findAll(fzrSql);
		String fzrNAME = "";
		if(fzrList!=null&&fzrList.size()>0){
			HashMap map = (HashMap)fzrList.get(0);
			fzrNAME = String.valueOf(map.get("USERNAME"));
		}
		
		if(!fzrNAME.isEmpty()){
			//存在
			return 1;
		}
		//不存在
		return 0;
	}

	@Override
	public void updateUserById(Map<String, String> paramsMap) throws Exception {
		// TODO Auto-generated method stub
		String sql = " ";
	    baseDao.deleteObject(sql);
	    
	}

	@Override
	public List getUserList(String userid) throws Exception {
		// TODO Auto-generated method stub
		String sql=" select * from SYS_USER where userid = "+userid;
		return baseDao.findAll(sql);
	}

	@Override
	public String getUserSkinTemplate(String userid) throws Exception {
		// TODO Auto-generated method stub
		
		String sql="select nvl(templatestyle,'') templatestyle from sys_user where userid='"+userid+"' ";
		return baseDao.queryForString(sql);
	}

	@Override
	public void changeUserSkinTemplate(String skin,String userid) throws Exception {
		// TODO Auto-generated method stub
		String sql="update sys_user set templatestyle='"+skin+"' where userid='"+userid+"'";
		baseDao.execute(sql);
	}
	
	@Override
	public void clickUserPW(String userid) throws Exception {
		// TODO Auto-generated method stub
		String sql="update sys_user set password='1' where userid='"+userid+"'";
		baseDao.execute(sql);
	}
	


}


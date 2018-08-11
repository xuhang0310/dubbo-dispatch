package com.github.xupei.simple.sys.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;



import com.github.xupei.dubbo.api.IFeednodeindexService;
import com.github.xupei.dubbo.api.IUserService;
import com.github.xupei.dubbo.bean.SysUser;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;






@Service
public class FeednodeindexServiceImpl extends GridSqlUtilTool  implements IFeednodeindexService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	@Override
	public String getSql(Map<String, String> paramMap) {
		String sql=  "   select t.id, "+
              " b.name indextype, "+
               " t.indexvalue, "+
             "   t.indexunit,  "+
               " t.indexlow, "+
               " t.indexhigh, "+
              "  s.name stationcode,"+
              "  decode(t.stationtype, 99, '热源', '换热站') stationtype, "+
             "  t.id note1 "+
        "   from bas_index_config t, "+
            "    bas_powertype  b, "+
              "  (select feedcode stationcode,feedname name,99 stationtype from bas_feed union all "+
              "   select nodecode stationcode,nodename name,1 stationtype from bas_node "+
          "      )s "+
      "     where t.indextype=b.id "+
        "   and t.stationcode=s.stationcode "+
      "and t.stationtype=s.stationtype"+
      "[ and b.id='{indextype}'] "+
       "[and s.stationtype='{zlx}']"+
      "[and s.stationcode='{mc}']"+
       "order by t.id";
		//"[ and t.stationcode='{mc}']" 
		return sql;
	}

	public Map getFeedNodeIndexList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String indextype=paramMap.get("indextype");
		/*String sql="select t.id,t.indextype,t.indexvalue,t.indexunit,t.indexlow,t.indexhigh,"+
				"t.stationcode,decode(t.stationtype,99,'热源','换热站')stationtype,t.id note1"+
				" from  bas_index_config t ";
		if(!indextype.isEmpty()){
			sql+=" where t.indextype  like'%"+indextype+"%'";
			
		}*/
		String sql=getSql(paramMap);
	/*	paramMap=new HashMap<String, String>();
		paramMap.put("page","1");
		paramMap.put("rows","20");*/
		return baseDao.queryGridList(sql,paramMap);
		
	}

	public List checkUser(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		String sql="select su.userid,su.orgid,su.password,su.displayname from sys_user su where 1=1   [  and username='{userName}']  ";
		return baseDao.findAll(sql, map);
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
	public void saveBase(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
	String sql="insert into bas_index_config(indextype,indexvalue,indexunit,indexlow,indexhigh,stationcode,stationtype)values ('"+
			paramMap.get("zblx")+"','"+paramMap.get("zbsz")+"','"+paramMap.get("zbdw")+"','"+paramMap.get("zbxf")+
			"','"+paramMap.get("zbsf")+"','"+paramMap.get("bm")+"','"+paramMap.get("lx")+"')";
	   baseDao.addObject(sql);
	}
	
	
	public void deleteZb(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="delete from bas_index_config where id='"+id+"'";
	    baseDao.deleteObject(sql);
	}
	
	public static void main(String arg []){
		for(int i=0;i<100;i++){
			SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
			System.out.println(simple.format(new Date()));
		}
		
	}



	@Override
	public List findZb(String id) throws Exception {
		String sql="select * from bas_index_config where id='"+id+"'";
		
		return baseDao.findAll(sql);
	}

	@Override
	public void editZb(Map<String, String> paramsMap) throws Exception {
		String sql="update bas_index_config t set t.indextype='" +paramsMap.get("zblx")+"',"+
				"  t.indexvalue='"+paramsMap.get("zbsz")+"',t.indexunit='"+paramsMap.get("zbdw")+"',t.indexlow='"+
				paramsMap.get("zbxf")+"',t.indexhigh='"+paramsMap.get("zbsf")+"', t.stationcode='"+paramsMap.get("bm")+
				"',t.stationtype='"+paramsMap.get("lx")+"' where id='"+paramsMap.get("id")+"'";
		baseDao.execute(sql);
		
	}

	@Override
	public List getlx(String stationtype) throws Exception {
		String sql;
		if(stationtype.equals("1")){
			sql="SELECT NODENAME as NAME, NODECODE as stationcode FROM BAS_NODE";
		}else if((stationtype.equals("99"))){
			sql="SELECT FEEDNAME as NAME, FEEDCODE as stationcode FROM BAS_FEED";
		}else{
			sql="SELECT FEEDNAME as NAME, FEEDCODE as stationcode FROM BAS_FEED "+
					"  UNION ALL"+
					"  SELECT NODENAME as NAME, NODECODE as stationcode FROM BAS_NODE ";
		}
		return baseDao.findAll(sql);
	}

	@Override
	public List findZbList() throws Exception {
		String sql="select id ,name from  BAS_POWERTYPE where pid=14"+
             "  ORDER BY ID ASC ";
		return baseDao.findAll(sql);
	}




	


}


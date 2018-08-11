package com.github.xupei.simple.sys.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;



import com.github.xupei.dubbo.api.IBasMeterService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;






@Service
public class BasMeterServiceImpl extends GridSqlUtilTool  implements IBasMeterService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Override
	public String getSql(Map<String, String> paramMap) {
		String sql = "select ID, METERNAME, BASNAME, STATIONTYPE, SBLX, BL,id note1"
				+ "	from (select t.id,"
				+ "    t.metername,"
				+ "  b.nodename as basname,"
				+ "   decode(t.meter_type,"
				+ "       1, "
				+ "   '热表', "
				+ " 2,"
				+ " '水表',"
				+ "  3,"
				+ "   '电表', "
				+ "   4, "
				+ "     '燃气表') sblx,"
				+ " decode(t.stationtype, 1, '换热站', 99, '热源厂') stationtype,"
				+ "     t.bl "
				+ "   from BAS_METER t, "
				+ "     (select nodecode, nodename, 1 stationtype "
				+ "    from bas_node "
				+ "  union all "
				+ " select feedcode, feedname, 99 stationtype "
				+ "   from bas_feed) b "
				+ "   where 1 = 1  [  and t.metername like '%{basmeter}%'] "
				+"[ and t.stationtype='{zlx}'] " 
				+"[ and t.stationcode='{mc}']" 
				+"[ and t.meter_type='{yblx}']"
				+ "    and b.stationtype = t.stationtype "
				+ "    and t.stationcode = b.nodecode "
		        + "ORDER BY id desc)";
		return sql;
	}

	
	public Map getBasMeterList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		String sql=getSql(paramMap);
		
		return baseDao.queryGridList(sql, paramMap);
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
	public void saveMeter(Map<String, String> paramMap) throws Exception {
		String sql=" INSERT INTO BAS_METER   (METERNAME, STATIONCODE, STATIONTYPE, METER_TYPE, BL)values('"+
							paramMap.get("sbmc")+"','"+paramMap.get("mc")+"','"+paramMap.get("stationtype")+"','"+paramMap.get("yblx")+
							"','"+paramMap.get("bl")+"')";
		baseDao.addObject(sql);
	}
	
	
	public void deleteMeter(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="delete from bas_meter where id='"+id+"'";
	    baseDao.deleteObject(sql);
	}
	
	public static void main(String arg []){
		for(int i=0;i<100;i++){
			SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
			System.out.println(simple.format(new Date()));
		}
		
	}



	@Override
	public List findMeter(String id) throws Exception {
		String sql="select * from bas_meter"+
			" where id= "+id; 
		
		return baseDao.findAll(sql);
	}

	@Override
	public void editMeter(Map<String, String> paramsMap) throws Exception {
		String sql="update bas_meter set metername='"+paramsMap.get("sbmc")+"',stationcode='"+paramsMap.get("mc")+
						"',stationtype='"+paramsMap.get("stationtype")+"',meter_type='"+paramsMap.get("yblx")+"',bl='"+
						paramsMap.get("bl")+"' where id="+paramsMap.get("id");
						
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
	public List selectAll() throws Exception {
		String sql = "SELECT FEEDNAME as NAME FROM BAS_FEED  UNION ALL SELECT NODENAME as NAME FROM BAS_NODE ";

		return baseDao.findAll(sql);
	}

	@Override
	public List getProjectList(String id) throws Exception {
		String sql = "";
		if(id!=null&&id.trim().length()>0){
			switch (id) {
			case "1":
				sql = "SELECT T.NODECODE AS ID,T.NODENAME AS NAME FROM BAS_NODE T  where 1=1 ";
				break;
			case "99":
				sql = "SELECT T.FEEDCODE AS ID,T.FEEDNAME AS NAME FROM BAS_FEED T   where 1=1 ";
				break;
			default:
				break;
			}
		}
		return baseDao.findAll(sql);
	}




	


}


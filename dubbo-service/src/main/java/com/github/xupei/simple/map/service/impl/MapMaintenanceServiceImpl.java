package com.github.xupei.simple.map.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;



import com.github.xupei.dubbo.api.IBasMeterService;
import com.github.xupei.dubbo.api.map.IMapMaintenanceService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;






@Service
public class MapMaintenanceServiceImpl extends GridSqlUtilTool  implements IMapMaintenanceService{
	
	
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


	@Override
	public String addNode(Map<String, String> paramsMap) throws Exception {
		//{meterid=, stationtype=99, mc=5, lng=116.283317, lat=40.044695}
		String type=paramsMap.get("stationtype");
		String sql="";
		String piontSql="";
		int nodeNum=getNodeNum(paramsMap.get("mc"),type); 
		List point=null;
		if(nodeNum==0){
			sql+="insert into bas_map_point(type,longitude,latitude,code)values('"+type+"','"+
					paramsMap.get("lng")+"','"+paramsMap.get("lat")+"','"+paramsMap.get("mc")+"')";
		}else{
			sql+="update bas_map_point set longitude='"+paramsMap.get("lng")+"',latitude='"+paramsMap.get("lat")+"' where type='"+type+
					"' and code='"+paramsMap.get("mc")+"'";
		}
		if(type.equals("1")){
			piontSql+= "select bm.id,bm.type,bm.longitude,bm.latitude,bm.code,bn.nodename name"
					+ " from bas_map_point bm left join bas_node bn on(bm.code=bn.nodecode)"
					+ " where bm.type=1 and bm.code='" + paramsMap.get("mc")+ "'";	
		}else{
			piontSql+=  "select bm.id,bm.type,bm.longitude,bm.latitude,bm.code,bf.feedname name"
					+ " from bas_map_point bm left join bas_feed bf on(bm.code=bf.feedcode)"
					+ " where bm.type=99 and bm.code='" +  paramsMap.get("mc")+ "'";
		}
		baseDao.execute(sql);
		point=baseDao.findAll(piontSql);
		return JSONArray.fromObject(point).toString();
	}


	private int getNodeNum(String mc, String type) throws Exception   {
		String sql="select count(*) num from bas_map_point where type='"+type+"' and code='"+mc+"'";
	
		
		return baseDao.queryForInt(sql);
	}


	@Override
	public String addLine(Map<String, String> paramsMap) throws Exception {
		
		//{pointsStr=116.287341-40.045855,116.277064-40.039724,116.294815-40.044198,116.281951-40.036851}
		String sql = "insert into bas_map_line(type,color,opacity,weight,style,linemessage,points,showsymbol)"+
						"values(0,'#006C76',0.8,3,'solid','','"+paramsMap.get("pointsStr")+"',0)";
		baseDao.execute(sql);
		String lineSql= "select id,type,color,opacity,weight,style,linemessage,points,showsymbol"
				+ " from bas_map_line where id=(select max(id) from bas_map_line)"; 
		List line=baseDao.findAll(lineSql);
		return JSONArray.fromObject(line).toString();
	}


	@Override
	public String getFeedPointData() throws Exception {
		String sql = "select bm.id,bm.type,bm.longitude,bm.latitude,bm.code,bf.feedname name"
				+ " from bas_map_point bm left join bas_feed bf on(bm.code=bf.feedcode) where bm.type=99";
	    String feedPointData=JSONArray.fromObject(baseDao.findAll(sql)).toString();
		return feedPointData;
	}


	@Override
	public String getNodePointData() throws Exception {
		String sql = "select bm.id,bm.type,bm.longitude,bm.latitude,bm.code,bn.nodename name"
				+ " from bas_map_point bm left join bas_node bn on(bm.code=bn.nodecode) where bm.type=1";
	    String NodePointData=JSONArray.fromObject(baseDao.findAll(sql)).toString();
		return NodePointData;
	}


	@Override
	public String getLineData() throws Exception {
		String sql = "select id,type,color,opacity,weight,style,linemessage,points,showsymbol from bas_map_line";	
		String LineData=JSONArray.fromObject(baseDao.findAll(sql)).toString();
		return LineData;
	}


	@Override
	public void delPoint(Map<String, String> paramsMap) throws Exception {
		String sql="delete from bas_map_point t where t.id='"+paramsMap.get("pointId")+"'";	
		baseDao.execute(sql);
	}


	@Override
	public void delLine(Map<String, String> paramsMap) throws Exception {
		String sql="delete from bas_map_line t where t.id='"+paramsMap.get("lineId")+"'";
		baseDao.execute(sql);
	}


	@Override
	public String getNodePointDataForEnergy() throws Exception {
		String sql ="  select bm.longitude lng,bm.latitude lat,20000 count "+
		     "   from bas_map_point bm left join bas_node bn on(bm.code=bn.nodecode) where bm.type=1 ";
	    String NodePointData=JSONArray.fromObject(baseDao.findAll(sql)).toString();
		return NodePointData;
	}


	@Override
	public boolean getIsDraw(String user) throws Exception {
		boolean check=false;
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" select r.permissions permissions from  ")
		.append(" sys_role r,sys_user u ")
		.append(" where r.roleid=u.roleid ")
		.append(" and u.username='").append(user).append("'");
		Map map=baseDao.findAll(sqlBuf.toString()).get(0);
		String[] PERMISSIONS=map.get("PERMISSIONS").toString().split(",");
		for(String str:PERMISSIONS){
			if(str.equals("11102")){
				return true;
			}
		}
		return check;
		
	
	}


	@Override
	public List getFeedReal(Map<String, String> paramsMap) throws Exception {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" select bf.feedid, ")
		.append("        bf.sjfh, ")
		.append("        ss.linecode, ")
		.append("        bf.feedname, ")
		.append("        to_char(t.scadatime, 'yyyy-MM-dd hh24:mi:ss') scadatimestr, ")
		.append("        round(t.supplytemp,2)supplytemp,round(t.returntemp,2)returntemp, ")
		.append(" round(t.supplypress,2)supplypress,round(t.returnpress,2)returnpress, ")
		.append(" round(t.supplyflow,2)supplyflow,round(t.heatquantity)heatquantity ")
		.append("   from SCADA_FEED_REAL t, scada_station ss, bas_feed bf ")
		.append("  where 1 = 1 ")
		.append("    and ss.stationtype = 99 ")
		.append("    and ss.stationcode = bf.feedid ")
		.append("    and t.stationid = ss.id ")
		.append("    and (t.stationid, t.scadatime) in ")
		.append("        (select stationid, max(scadatime) ")
		.append("           from scada_feed_real ")
		.append("          group by stationid) ")
		.append(" and bf.feedid='")
		.append(paramsMap.get("code"))
		.append("'");
		return baseDao.findAll(sqlBuf.toString());
	}


	@Override
	public List getFeedInfo(Map<String, String> paramsMap) throws Exception {
		String sql="select feedname from bas_feed where feedcode='"+paramsMap.get("code")+"'";
		return baseDao.findAll(sql);
	}


	@Override
	public List getNodeReal(Map<String, String> paramsMap) throws Exception {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("   SELECT BN.NODENAME NODECODEPIC,BN.nodecode, ")
		.append("      SN.*, ")
		.append("      SS.LINENAME, ")
		.append("      ss.stationcode || '号站' nodecodeid, ")
		.append("      ss.stationcode nodeid, ")
		.append("      BN.SJMJ, ")
		.append("      BN.ORGID as SSGS, ")
		.append("      BN.CNMJ GNMJ, ")
		.append("      BN.NODENAME, ")
		.append("      BN.FEEDID AS NODECODEANAL, ")
		.append("      BN.CNFS, ")
		.append("      BN.JNFS, ")
		.append("      TO_CHAR(SN.SCADATIME, 'YYYY-MM-DD HH24:MI:SS') READTIMESTR, ")
		.append("      SN.SUPPLYTEMP - SN.RETURNTEMP YWWC, ")
		.append("      SN.SUPPLYPRESS - SN.RETURNPRESS YWYC, ")
		.append("      SN.SECSUPPLYTEMP - SN.SECRETURNTEMP EWWC, ")
		.append("      SN.SECSUPPLYPRESS - SN.SECRETURNPRESS EWYC ")
		.append("  FROM BAS_NODE        BN, ")
		.append("      SCADA_STATION   SS, ")
		.append("      SCADA_NODE_REAL SN ")
		.append("  WHERE 1 = 1 ")
		.append("  AND SS.stationcode = BN.NODECODE ")
		.append("  AND SS.ID = SN.STATIONID ")
		.append("  AND SS.stationtype = 1 ")
		.append("  AND (SN.SCADATIME, SN.STATIONID) IN ")
		.append("      (SELECT MAX(T.SCADATIME), T.STATIONID ")
		.append("         FROM SCADA_NODE_REAL T ")
		.append("        GROUP BY T.STATIONID) ")
		.append(" and BN.nodecode='")
		.append(paramsMap.get("code"))
		.append("'");
		return baseDao.findAll(sqlBuf.toString());
	}


	@Override
	public List getNodeInfo(Map<String, String> paramsMap) throws Exception {
		String sql="select nodename NODECODEPIC from bas_node where nodecode='"+paramsMap.get("code")+"'";
		return baseDao.findAll(sql);
	}





	


}


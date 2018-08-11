package com.github.xupei.simple.scada.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.xupei.dubbo.api.scada.IScadaFeedAnalysisService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;
import com.github.xupei.simple.util.UUIDTool;

public class ScadaFeedAnalysisServiceImpl extends GridSqlUtilTool implements IScadaFeedAnalysisService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	
	public String getSql(Map<String, String> paramMap){
		
		
		String sql1 = getSummarySql(this.getCommonSql(paramMap, paramMap.get("startdate")));
		String sql2 = getSummarySql(this.getCommonSql(paramMap, paramMap.get("enddate")));
		
		String sql = "SELECT * FROM (" + sql1 +" UNION ALL "+ sql2 + ") WHERE 1 = 1 ";
		
		
		return sql;
	}
	
	public String getSummarySql(String sql){
		String sumSql="select STATIONID,linecode, feedname,ROUND(avg(SUPPLYTEMP), 2) SUPPLYTEMP, "+
				"       ROUND(avg(RETURNTEMP), 2) RETURNTEMP,ROUND(avg(SUPPLYPRESS), 4) SUPPLYPRESS,ROUND(avg(RETURNPRESS), 4) RETURNPRESS, "+
				"       ROUND(avg(SUPPLYFLOW), 2) SUPPLYFLOW, ROUND(avg(HEATQUANTITY), 2) HEATQUANTITY, TO_CHAR(SCADADATE, 'YYYY-MM-DD') SCADADATE from ( ";
		sumSql+=sql+" )group by STATIONID, linecode, feedname, TO_CHAR(SCADADATE, 'YYYY-MM-DD') ";
		return sumSql;
	}
	
	public String getCommonSql(Map<String, String> paramMap, String source){
		String table = getTable(source);
		table = "scada_feed";
		
		StringBuffer sb = new StringBuffer("");
		sb.append("SELECT T.STATIONID ,bf.feedname,ss.linecode , ");
		sb.append("       scadatime SCADADATE,  to_char(scadatime,'yyyy-MM-dd') scadaday, to_char(scadatime,'HH24:MI') scadatimestr, ");
		sb.append("       ROUND(T.SUPPLYTEMP, 2) SUPPLYTEMP, ");
		sb.append("       ROUND(T.RETURNTEMP, 2) RETURNTEMP, ");
		sb.append("       ROUND(T.SUPPLYPRESS, 2) SUPPLYPRESS, ");
		sb.append("       ROUND(T.RETURNPRESS, 2) RETURNPRESS, ");
		sb.append("       ROUND(T.SUPPLYFLOW, 2) SUPPLYFLOW, ");
		sb.append("       ROUND(T.HEATQUANTITY, 2) HEATQUANTITY ");
		sb.append("  FROM "+table+" T ,scada_station ss ,bas_feed bf ");
		sb.append(" WHERE 1=1 ");
		sb.append("   AND T.STATIONID =ss.id [ and ss.stationcode={feedcode} ] and bf.feedid=ss.stationcode "); 
		sb.append("   AND T.SCADATIME >= TO_DATE('"+source+" 00:00:00', 'YYYY-MM-DD HH24:MI:SS') ");
		sb.append("   AND T.SCADATIME <= TO_DATE('"+source+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS') ");
		
		
		return sb.toString();
	}
	
	public Map getDataTeamList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql=getSql(paramMap);
		
		return baseDao.queryGridList(sql,paramMap);
		
	}
	
	
	public static void main(String arg []){
		for(int i=0;i<100;i++){
			SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
			System.out.println(simple.format(new Date()));
		}
		
	}
	//==========================================================================
	public String getTable(String source) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try {
			Date month = sdf.parse(sdf.format(new Date()));
			Date temp = sdf.parse(source);
			String[] time = source.split("-");
			if(temp.compareTo(month)!=0){
				return "scada_feed_"+time[0]+time[1];
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "scada_feed";
	}

	@Override
	public List getFeedAnalysisChart(Map<String, String> paramMap)
			throws Exception {
		long startTime = System.currentTimeMillis();
		String sql1=this.getCommonSql(paramMap, paramMap.get("startdate"));
		String sql2=this.getCommonSql(paramMap, paramMap.get("enddate"));
		String sql = "SELECT * FROM (" + sql1 +" UNION ALL "+ sql2 + ") WHERE 1 = 1 ";
		List list=baseDao.findAll(sql, paramMap);
		long end = System.currentTimeMillis();
		System.out.println(end-startTime+"============");
		return list;
	}



}

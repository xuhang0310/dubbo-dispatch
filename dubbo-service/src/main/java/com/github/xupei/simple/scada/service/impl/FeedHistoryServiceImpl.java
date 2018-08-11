package com.github.xupei.simple.scada.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.xupei.dubbo.api.scada.IFeedHistoryService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;
import com.github.xupei.simple.util.ListRender;


public class FeedHistoryServiceImpl extends GridSqlUtilTool implements IFeedHistoryService {

	@Resource(name="baseDao")
	private BaseDao baseDao;

	@Override
	public String getSql(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		String sql="select bf.feedid,ss.linecode,bf.feedname,to_char(t.scadatime,'yyyy-MM-dd hh24:mi:ss') scadatimestr,t.* "+
				"  from SCADA_FEED t, scada_station ss, bas_feed bf "+
				" where 1=1 and ss.stationtype=99 and ss.stationcode=bf.feedid  and t.stationid=ss.id " +
				"  [ and scadatime>=to_date('{startdate}','yyyy-MM-dd')  ] [ and scadatime<=to_date('{enddate}','yyyy-MM-dd')  ]  ";
		if(paramMap.get("feedcode")!=null&&!"null".equals(paramMap.get("feedcode"))){
	    	 sql+=" and bf.feedid in ( '"+paramMap.get("feedcode").replace(",", "','")+"') ";
	     }
		sql+=" order by scadatime desc ";
		return sql;
	}

	@Override
	public Map getFeedHistoryList(Map<String, String> paramMap)
			throws Exception {
		// TODO Auto-generated method stub
		String sql=getSql(paramMap);
		HashMap gridMap=(HashMap) baseDao.queryGridList(sql, paramMap);
		
		List list=ListRender.renderRealList(gridMap,getFeedWarnConfig(),"FEEDID","FEEDNAME");
		gridMap.put("rows", list);
		return gridMap;
	}
	
	public HashMap<String,List> getFeedWarnConfig() throws Exception{
		String sql="select * from feed_warn_config order by feedid ";
		List list= baseDao.findAll(sql);
		return ListRender.listConvertToMap(list, "FEEDID");
		
	}

	@Override
	public List getHistoryChart(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		
		return baseDao.findAll(" select * from ( "+this.getSql(paramMap)+" ) order by scadatimestr  ", paramMap);
	}

	@Override
	public Map getSummaryMap(Map<String, String> paramsMap) throws Exception {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" select round(avg(supplytemp),2)supplytemp,round(avg(returntemp),2)returntemp, ")
		.append(" round(avg(supplypress),4)supplypress,round(avg(returnpress),4)returnpress, ")
		.append(" round(avg(supplyflow),4)supplyflow,round(avg(heatquantity),4)heatquantity,count(distinct feedid)nums ")
		.append("  from ")
		.append(" ( ")
		.append(getSql(paramsMap))
		.append(" )");
		
		return baseDao.findMap(sqlBuf.toString(),paramsMap);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

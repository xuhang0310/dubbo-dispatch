package com.github.xupei.simple.scada.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.xupei.dubbo.api.scada.INodeHistoryService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;
import com.github.xupei.simple.util.ListRender;


public class NodeHistoryServiceImpl extends GridSqlUtilTool implements INodeHistoryService {

	@Resource(name="baseDao")
	private BaseDao baseDao;

	@Override
	public String getSql(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		StringBuffer sBuffer = new StringBuffer(""); 
		 sBuffer.append("select T.*,N.ORGID ssgs,"); 
		 sBuffer.append("       N.NODECODE nodeid,"); 
		 sBuffer.append("       '' SECSUPRETTEMPAVG,'' POWER,'' OUTDOORWD,'' OUTDOORWDXZ,"); 
		 sBuffer.append("       TO_CHAR(T.SCADATIME, 'YYYY-MM-DD HH24:MI:SS') SCADADATE,"); 
		 sBuffer.append("       N.NODECODE || '_' || S.LINECODE NODECODE1,"); 
		 sBuffer.append("       DECODE(T.SECSUPPLYTEMP - T.SECRETURNTEMP,"); 
		 sBuffer.append("              0,"); 
		 sBuffer.append("              0,"); 
		 sBuffer.append("              (T.SUPPLYTEMP - T.RETURNTEMP) /"); 
		 sBuffer.append("              (T.SECSUPPLYTEMP - T.SECRETURNTEMP) * T.SUPPLYFLOW) RWJJLL,"); 
		 sBuffer.append("       T.SECSUPPLYPRESS - T.SECRETURNPRESS EWYC,"); 
		 sBuffer.append("       T.SUPPLYTEMP - T.RETURNTEMP YWWC,"); 
		 sBuffer.append("       T.SUPPLYPRESS - T.RETURNPRESS YWYC,"); 
		 sBuffer.append("       T.SECSUPPLYTEMP - T.SECRETURNTEMP EWWC,"); 
		 sBuffer.append("       N.NODENAME NODENAME,"); 
		 sBuffer.append("       ''  linename"); 
		 sBuffer.append("  FROM SCADA_NODE T, BAS_NODE N, SCADA_STATION S"); 
		 sBuffer.append(" WHERE S.STATIONCODE = N.NODECODE"); 
		 sBuffer.append("   AND T.STATIONID = S.ID"); 
		 sBuffer.append("   AND S.STATIONTYPE = 1");
		 sBuffer.append("  [ and T.SCADATIME>=to_date('{startdate}','yyyy-MM-dd')  ] [ and T.SCADATIME<=to_date('{enddate}','yyyy-MM-dd')  ]  ");
		 
		 if(!paramMap.get("orgcode").isEmpty()){
			 sBuffer.append(" and N.ORGID in ( '"+paramMap.get("orgcode").replace(",", "','")+"') ");
	     }
		 if(!paramMap.get("nodename").isEmpty()){
			 if (paramMap.get("orgcode").replace(",","").length()>=6) {
				 sBuffer.append(" and N.NODENAME in ('"+paramMap.get("nodename").replace(",", "','")+"')");
			}
			 
	     }
		
		return sBuffer.toString();
	}

	@Override
	public Map getNodeHistoryList(Map<String, String> paramMap)
			throws Exception {
		// TODO Auto-generated method stub
		String sql=getSql(paramMap);
		HashMap gridMap=(HashMap) baseDao.queryGridList(sql, paramMap);
		
		List list=ListRender.renderRealList(gridMap,getFeedWarnConfig(),"NODEID","NODENAME");
		gridMap.put("rows", list);
		return gridMap;
	}
	
	public HashMap<String,List> getFeedWarnConfig() throws Exception{
		String sql="select * from node_warn_config order by nodeid ";
		List list= baseDao.findAll(sql);
		return ListRender.listConvertToMap(list, "NODEID");
		
	}

	@Override
	public List getHistoryChart(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		
		return baseDao.findAll(" select * from ( "+this.getSql(paramMap)+" ) order by SCADADATE  ", paramMap);
	}

	@Override
	public Map getSummaryMap(Map<String, String> paramsMap) throws Exception {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" select count(distinct nodeid)nums,round(avg(supplytemp),2)supplytemp, ")
		.append(" round(avg(returntemp),2)returntemp,round(avg(supplypress),4)supplypress, ")
		.append(" round(avg(returnpress),4)returnpress,round(avg(supplyflow),4)supplyflow, ")
		.append(" round(avg(heatquantity),4)heatquantity ")
		.append(" from( ")
		.append(getSql(paramsMap))
		.append(")");
		return baseDao.findMap(sqlBuf.toString(), paramsMap);
	}
	

	
	
	
	
	
	
	
	
	
}

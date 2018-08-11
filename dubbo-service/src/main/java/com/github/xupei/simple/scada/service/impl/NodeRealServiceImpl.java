package com.github.xupei.simple.scada.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.xupei.dubbo.api.scada.IFeedRealService;
import com.github.xupei.dubbo.api.scada.INodeRealService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.util.ListRender;
import com.github.xupei.simple.util.UUIDTool;


public class NodeRealServiceImpl implements INodeRealService{

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	public String getSql(Map<String, String> paramMap){
		
		StringBuffer sBuffer = new StringBuffer(""); 
		
		 sBuffer.append("select *"); 
		 sBuffer.append("  from (SELECT BN.NODENAME NODECODEPIC,"); 
		 sBuffer.append("               SN.*,"); 
		 sBuffer.append("               SS.LINENAME,"); 
		 sBuffer.append("               ss.stationcode || '号站' nodecodeid,"); 
		 sBuffer.append("               ss.stationcode nodeid,"); 
		 sBuffer.append("               BN.SJMJ,"); 
		 sBuffer.append("               BN.ORGID as SSGS,"); 
		 sBuffer.append("               BN.CNMJ GNMJ,"); 
		 sBuffer.append("               BN.NODENAME,"); 
		 sBuffer.append("               BN.FEEDID AS NODECODEANAL,"); 
		 sBuffer.append("               BN.CNFS,"); 
		 sBuffer.append("               BN.JNFS,"); 
		 sBuffer.append("               TO_CHAR(SN.SCADATIME, 'YYYY-MM-DD HH24:MI:SS') READTIMESTR,"); 
		 sBuffer.append("               SN.SUPPLYTEMP - SN.RETURNTEMP YWWC,"); 
		 sBuffer.append("               SN.SUPPLYPRESS - SN.RETURNPRESS YWYC,"); 
		 sBuffer.append("               SN.SECSUPPLYTEMP - SN.SECRETURNTEMP EWWC,"); 
		 sBuffer.append("               SN.SECSUPPLYPRESS - SN.SECRETURNPRESS EWYC"); 
		 sBuffer.append("          FROM BAS_NODE BN, SCADA_STATION SS, SCADA_NODE_REAL SN"); 
		 sBuffer.append("         WHERE 1=1"); 
		 sBuffer.append("           AND SS.stationcode = BN.NODECODE"); 
		 sBuffer.append("           AND SS.ID = SN.STATIONID"); 
		 sBuffer.append("           AND SS.stationtype = 1"); 
		 sBuffer.append("          [and bn.nodeid={nodecode}]"); 
		 sBuffer.append("          [and bn.orgid={orgcode}]"); 
		 sBuffer.append("           AND (SN.SCADATIME, SN.STATIONID) IN"); 
		 sBuffer.append("               (SELECT MAX(T.SCADATIME), T.STATIONID"); 
		 sBuffer.append("                  FROM SCADA_NODE_REAL T"); 
		 sBuffer.append("                 GROUP BY T.STATIONID)) t"); 
		 sBuffer.append(" where 1 = 1"); 
		 sBuffer.append(" ORDER BY STATIONID, scadatime desc");
		 
		return sBuffer.toString();
	}
	
	public String getChartSql(Map<String, String> paramMap){
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" select READTIMESTR, ")
		.append("  sum(supplytemp)supplytemp,sum(returntemp)returntemp, ")
		.append(" sum(supplypress)supplypress,sum(returnpress)returnpress, ")
		.append(" sum(supplyflow)supplyflow,sum(heatquantity)heatquantity, ")
		.append(" sum(sumheatquantity)sumheatquantity,sum(secsupplytemp)secsupplytemp, ")
		.append(" sum(secreturntemp)secreturntemp,sum(secsupplypress)secsupplypress, ")
		.append(" sum(secreturnpress)secreturnpress ")
		.append(" from ( ")
	//	.append(getSql(paramMap))
		.append("select *")  
      .append("  from (SELECT BN.NODENAME NODECODEPIC,")  
      .append("               SN.*,")  
      .append("               SS.LINENAME,")  
      .append("               ss.stationcode || '号站' nodecodeid,")  
      .append("               ss.stationcode nodeid,")  
      .append("               BN.SJMJ,")  
      .append("               BN.ORGID as SSGS,")  
      .append("               BN.CNMJ GNMJ,")  
      .append("               BN.NODENAME,")  
      .append("               BN.FEEDID AS NODECODEANAL,")  
      .append("               BN.CNFS,")  
      .append("               BN.JNFS,")  
      .append("               TO_CHAR(SN.SCADATIME, 'YYYY-MM-DD HH24:MI:SS') READTIMESTR,")  
      .append("               SN.SUPPLYTEMP - SN.RETURNTEMP YWWC,")  
      .append("               SN.SUPPLYPRESS - SN.RETURNPRESS YWYC,")  
      .append("               SN.SECSUPPLYTEMP - SN.SECRETURNTEMP EWWC,")  
      .append("               SN.SECSUPPLYPRESS - SN.SECRETURNPRESS EWYC")  
      .append("          FROM BAS_NODE BN, SCADA_STATION SS, SCADA_NODE_REAL SN")  
      .append("         WHERE 1=1")  
      .append("           AND SS.stationcode = BN.NODECODE")  
      .append("           AND SS.ID = SN.STATIONID")  
      .append("           AND SS.stationtype = 1");
      ////////////
		if(!paramMap.get("orgcode").equals("")){
			sqlBuf.append("        and bn.orgid='")
			.append(paramMap.get("orgcode")+"'");
		}		
		if(!paramMap.get("nodecode").equals("")){
			sqlBuf.append("         and bn.nodeid='")  
			.append(paramMap.get("nodecode")+"'");
		}      
	 sqlBuf.append("           AND (SN.SCADATIME, SN.STATIONID) IN")  
      .append("               (SELECT MAX(T.SCADATIME), T.STATIONID")  
      .append("                  FROM SCADA_NODE_REAL T")  
      .append("                 GROUP BY T.STATIONID)) t")  
      .append(" where 1 = 1")  
      .append(" ORDER BY STATIONID, scadatime desc") 
		
	//////////////////////////////////	
		.append(") ")
		.append(" group by READTIMESTR ")
		.append(" order by READTIMESTR ");
		return sqlBuf.toString();
	}
	
	
	
	
	@Override
	public Map getNodeRealList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		String sql=getSql(paramMap);
		HashMap gridMap=(HashMap) baseDao.queryGridList(sql, paramMap);
		
		List list=ListRender.renderRealList(gridMap,getNodeWarnConfig(),"NODEID","NODENAME");
		gridMap.put("rows", list);
		return gridMap;
	}
	public HashMap<String,List> getNodeWarnConfig() throws Exception{
		String sql="select * from node_warn_config order by nodeid ";
		List list= baseDao.findAll(sql);
		return ListRender.listConvertToMap(list, "NODEID");
		
	}

	@Override
	public List getRealDataList(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getParamConfigList(String code) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getImgUrl(String code) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getParamConfigForUpdateArea(String code) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(String code, String name, String x, String y)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String code) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateConfig(String code, HashMap<Object, Object> configmap)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveWarnConfig(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		insertWarnConfig(paramMap.get("nodecode"),paramMap.get("supplytemp").split(",")[0],paramMap.get("supplytemp").split(",")[1],paramMap.get("supplytemp").split(",")[2]);
		insertWarnConfig(paramMap.get("nodecode"),paramMap.get("returntemp").split(",")[0],paramMap.get("returntemp").split(",")[1],paramMap.get("returntemp").split(",")[2]);
		insertWarnConfig(paramMap.get("nodecode"),paramMap.get("supplypress").split(",")[0],paramMap.get("supplypress").split(",")[1],paramMap.get("supplypress").split(",")[2]);
		insertWarnConfig(paramMap.get("nodecode"),paramMap.get("returnpress").split(",")[0],paramMap.get("returnpress").split(",")[1],paramMap.get("returnpress").split(",")[2]);
		insertWarnConfig(paramMap.get("nodecode"),paramMap.get("secsupplytemp").split(",")[0],paramMap.get("secsupplytemp").split(",")[1],paramMap.get("secsupplytemp").split(",")[2]);
		insertWarnConfig(paramMap.get("nodecode"),paramMap.get("secreturntemp").split(",")[0],paramMap.get("secreturntemp").split(",")[1],paramMap.get("secreturntemp").split(",")[2]);
		insertWarnConfig(paramMap.get("nodecode"),paramMap.get("secsupplypress").split(",")[0],paramMap.get("secsupplypress").split(",")[1],paramMap.get("secsupplypress").split(",")[2]);
		insertWarnConfig(paramMap.get("nodecode"),paramMap.get("secreturnpress").split(",")[0],paramMap.get("secreturnpress").split(",")[1],paramMap.get("secreturnpress").split(",")[2]);
		
	}
	public void insertWarnConfig(String nodeid,String field,String max,String min) throws Exception{
		for(int i=0;i<nodeid.split(",").length;i++){
			if(!nodeid.split(",")[i].equals("")){
				String delsql="delete from node_warn_config where nodeid='"+nodeid.split(",")[i]+"' and fieldname='"+field+"'";
				baseDao.execute(delsql);
				String sql="insert into node_warn_config(id, nodeid, fieldname, maxnum, minnum)values('"+UUIDTool.getUUID()+"', '"+nodeid.split(",")[i]+"', '"+field+"', "+max+", "+min+")";
				baseDao.addObject(sql);
			}
			
		}
		
		
	}

	@Override
	public List getWarnConfig(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql="select * from node_warn_config where 1=1 [ and nodeid='{nodecode}' ]";
		return baseDao.findAll(sql, paramMap);
	}

	@Override
	public Object getOrgList() throws Exception {
		// TODO Auto-generated method stub
		String sql = " select orgid code,orgname name from sys_org where del=1 order by orgid ";
		
		return baseDao.findAll(sql);
	}

	@Override
	public Object getNodeList() throws Exception {
		// TODO Auto-generated method stub
		String sql = " select nodecode code,nodename name from bas_node order by nodecode ";
		
		return baseDao.findAll(sql);
	}

	@Override
	public Object getJzlxList() throws Exception {
		// TODO Auto-generated method stub
		String sql = " select id code,name from BAS_POWERTYPE where isdel = 1 and pid = (select id from BAS_POWERTYPE where name = '建筑类型') ";
		
		return baseDao.findAll(sql);
	}

	@Override
	public Object getCnfsList() throws Exception {
		// TODO Auto-generated method stub
		String sql = " select id code,name from BAS_POWERTYPE where isdel = 1 and pid = (select id from BAS_POWERTYPE where name = '采暖方式') ";
		
		return baseDao.findAll(sql);
	}

	@Override
	public List getProjectList(String orgcode) throws Exception {
		String sql="select nodecode,nodename from bas_node where orgid='"+orgcode+"'";
		return baseDao.findAll(sql);
	}

	@Override
	public List getNodeRealChart(Map<String, String> paramsMap)
			throws Exception {
		String sql=getChartSql(paramsMap);
		return baseDao.findAll(sql);
	}

	@Override
	public Map getSummaryMap(Map<String, String> paramsMap) throws Exception {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" select count(*)nums,nvl(avg(supplytemp),0)supplytemp, ")
		.append(" nvl(avg(returntemp),0)returntemp,nvl(avg(secsupplytemp),0)secsupplytemp, ")
		.append(" nvl(avg(secreturntemp),0)secreturntemp from ( ")
		.append(getSql(paramsMap))
		.append(")");
		return baseDao.findMap(sqlBuf.toString(), paramsMap);
	}

	

}

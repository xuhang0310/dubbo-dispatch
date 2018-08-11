package com.github.xupei.simple.energy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import com.github.xupei.dubbo.api.energy.INodeEnergyService;
import com.github.xupei.simple.dao.BaseDao;


@Service
public class NodeEnergyServiceImpl implements INodeEnergyService {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	public String getSql(Map<String, String> paramMap) {
		String sql="select bn.nodename, "+
			       "so.orgname, "+
			      " bn.cnmj, "+
			       "to_char(max(sn.scadatime), 'yyyy-MM-dd') enddate, "+
			      " to_char(min(sn.scadatime), 'yyyy-MM-dd') startdate, "+
			     "  sn.nodeid, "+
			     "  sum(sn.sumheatquantity) sumheatquantity,  "+
			     "  sum(sn.water) water,  "+
			    "   sum(sn.power) power, "+
			     "  sum(sn.gas) gas, "+
			     "  round(sum(sn.sumheatquantity) * 1000000000 / sum(sn.cnmj) / 3600 / 24, "+
			       "     2) rdh, "+
			      " round(sum(sn.water) * 1000 / sum(sn.cnmj), 2) sdh, "+
			   "    round(sum(sn.power) / sum(sn.cnmj), 4) ddh, "+
			  "     round(sum(sn.gas) / sum(sn.cnmj), 4) gdh "+
		"	  from bas_node bn, scada_node_ljrl_day sn, Sys_Org so "+
		"	 where bn.nodeid = sn.nodeid "+
		"[and bn.orgid={orgid}]"+
		"[and to_char(sn.scadatime,'yyyy-mm-dd')>='{startdate}'] "+
		"[and to_char(sn.scadatime,'yyyy-mm-dd')<='{enddate}'] "+
		"	   and bn.orgid = so.orgid ";
		 if(paramMap.get("nodecode")!=null&&!"null".equals(paramMap.get("nodecode"))){
	    	 sql+=" and bn.nodeid in ( '"+paramMap.get("nodecode").replace(",", "','")+"') ";
	     }
		
	sql+=	"	 group by bn.nodename, sn.nodeid, so.orgname, bn.cnmj ";
			 
		
		return sql;
	}
	
	public String getChartSql(Map<String, String> paramMap) {
		StringBuffer sBuffer = new StringBuffer(""); 
		 sBuffer.append("  select  to_char(sn.scadatime,'yyyy-mm-dd')scadatime,"); 
		 sBuffer.append(" sum(sn.sumheatquantity) sumheatquantity,"); 
		 sBuffer.append(" sum(sn.water) water,"); 
		 sBuffer.append(" sum(sn.power) power,"); 
		 sBuffer.append(" sum(sn.gas) gas,"); 
		 sBuffer.append(" round(sum(sn.sumheatquantity) * 1000000000 /"); 
		 sBuffer.append(" sum(sn.cnmj) / 3600 / 24,"); 
		 sBuffer.append(" 2) rdh,"); 
		 sBuffer.append(" round(sum(sn.water) * 1000 / sum(sn.cnmj), 2) sdh,"); 
		 sBuffer.append(" round(sum(sn.power) / sum(sn.cnmj), 4) ddh,"); 
		 sBuffer.append(" round(sum(sn.gas) / sum(sn.cnmj), 4) gdh"); 
		 sBuffer.append(" from bas_node bn, scada_node_ljrl_day sn, Sys_Org so"); 
		 sBuffer.append(" where bn.nodeid = sn.nodeid");
		 sBuffer.append(" [and bn.orgid={orgid}]");
		 sBuffer.append(" [and to_char(sn.scadatime,'yyyy-mm-dd')>='{startdate}'] ");
		 sBuffer.append(" [and to_char(sn.scadatime,'yyyy-mm-dd')<='{enddate}'] ");
		 sBuffer.append("	and bn.orgid = so.orgid ");
		 if(paramMap.get("nodecode")!=null&&!"null".equals(paramMap.get("nodecode"))){
			 sBuffer.append(" and bn.nodeid in ( '"+paramMap.get("nodecode").replace(",", "','")+"') ");
	     }
		
		 sBuffer.append(	" group by sn.scadatime  order by sn.scadatime");
			 
		
		return sBuffer.toString();
	}
	
	public Map getEnergyList(Map<String, String> paramMap) throws Exception {
		// {rows=20, page=1, nodecode=2742, chartTitle=, chartField=, chartType=, chartPosition=, orgid=12, nodename=2742, tablename=7}
		return baseDao.queryGridList(getSql(paramMap), paramMap);
	}
	@Override
	public List getNodeEnergyChart(Map<String, String> paramsMap)
			throws Exception {
		List list= baseDao.findAll(getChartSql(paramsMap), paramsMap);
		return list;
	}
	@Override
	public List getOrg() throws Exception {
		String sql="select t.orgid,t.orgname from sys_org t order by t.orgid";
		return baseDao.findAll(sql);
	}
	@Override
	public List getNode() throws Exception {
		String sql="select b.nodeid,b.nodename from bas_node b";
		return baseDao.findAll(sql);
	}
	@Override
	public List getProjectList(String orgid) throws Exception {
		String sql="";
		if(!orgid.equals("")){
		 sql="select t.nodeid,t.nodename from bas_node t where t.orgid="+orgid;
		}else{
		sql="select t.nodeid,t.nodename from bas_node t";
		}
		return baseDao.findAll(sql);
	}
	@Override
	public Map getSummaryMap(Map<String, String> paramsMap) throws Exception {
String sql="select  count(1) nums,nvl(round(sum(sumheatquantity),2),0) sumheat, nvl(sum(water),0) water,nvl(sum(power),0) power, nvl(sum(gas),0) gas from ( " +getSql(paramsMap)+ ") ";
		
		return baseDao.findMap(sql, paramsMap);
	}
	@Override
	public Map getAssessList(Map<String, String> paramsMap) throws Exception {
		String sql="select * from FORE_GRADIENT_RESULT  ";
		return  baseDao.queryGridListNoPage(sql, paramsMap);
	}
	@Override
	public List getChart(Map<String, String> paramsMap) throws Exception {
		String sql="select * from FORE_GRADIENT_RESULT  ";
		return baseDao.findAll(sql);
	}

	@Override
	public List getProjectListFeed(String orgid) throws Exception {

		String sql="";
		if(!orgid.equals("")){
		 sql="select t.feedid,t.feedname from bas_feed t where t.orgid='"+orgid+"'";
		}else{
		sql="select t.feedid,t.feedname from bas_feed t ";
		}
		return baseDao.findAll(sql);
	
	}

	@Override
	public List getNodeEnergyPieChart(Map<String, String> paramsMap)
			throws Exception {
		String params="";
		String nhlx=paramsMap.get("nhlx");
		String startdate=paramsMap.get("startdate");
		String enddate=paramsMap.get("enddate");
		if(nhlx.equals("1")){//热耗
			params="call heat_gradient_pro('"+startdate+"','"+enddate+"')";		
		}else if(nhlx.equals("2")){//电耗
			params="call power_gradient_pro('"+startdate+"','"+enddate+"')";			
		}else{//水耗
			params="call water_gradient_pro('"+startdate+"','"+enddate+"')";			
		}
		baseDao.executeProcedure(params);
		String sql="select * from FORE_GRADIENT_RESULT order by id" ;
	//	baseDao.executeProcedure("call test1('4','5','6')");
		return baseDao.findAll(sql);
	}

	@Override
	public List getGraList() throws Exception {
		String  sql="select num1,num2,num3,num4 from fore_gradient where bh=1";
		return baseDao.findAll(sql);
	}

	@Override
	public void updateGradient(Map<String, String> paramsMap) throws Exception {
		String sql="update fore_gradient set num1='"+paramsMap.get("jd1")+"',num2='"+paramsMap.get("jd2")+"',num3='"+
					paramsMap.get("jd3")+"',num4='"+paramsMap.get("jd4")+"'  where bh='1'";
		baseDao.execute(sql);
	}

}

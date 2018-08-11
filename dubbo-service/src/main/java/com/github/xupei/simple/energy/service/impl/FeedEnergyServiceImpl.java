package com.github.xupei.simple.energy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.xupei.dubbo.api.energy.IFeedEnergyService;
import com.github.xupei.simple.dao.BaseDao;


@Service
public class FeedEnergyServiceImpl implements IFeedEnergyService {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	public String getSql(Map<String, String> paramMap) {
		String sql="select bf.feedname, "+
			       "so.orgname , bf.cnmj , "+
			       "to_char(max(sf.scadatime), 'yyyy-MM-dd') enddate, "+
			       "to_char(min(sf.scadatime), 'yyyy-MM-dd') startdate, "+
			       "sf.feedid, "+
			       "sum(sf.sumheatquantity) sumheatquantity, "+
			       "sum(sf.water) water, "+
			       "sum(sf.power) power, "+
			       "sum(sf.gas) gas, "+
			       "round(sum(sf.sumheatquantity)*1000000000/sum(sf.cnmj)/3600/24,2) rdh, "+
			       "round(sum(sf.water)*1000/sum(sf.cnmj),2) sdh, "+
			       "round(sum(sf.power)/sum(sf.cnmj),4) ddh, "+
			       "round(sum(sf.gas)/sum(sf.cnmj),4) gdh "+
			  "from bas_feed bf, scada_feed_ljrl_day sf, Sys_Org so "+
			 "where bf.feedid = sf.feedid "+
			   "and bf.orgid = so.orgid  [ and scadatime>=to_date('{startdate}','yyyy-MM-dd')  ] [ and scadatime<=to_date('{enddate}','yyyy-MM-dd')  ]   [ and so.orgid={orgid}  ]";
		     if(paramMap.get("feedcode")!=null&&!"null".equals(paramMap.get("feedcode"))){
		    	 sql+=" and bf.feedid in ( '"+paramMap.get("feedcode").replace(",", "','")+"') ";
		     }
			 sql+="group by bf.feedname, sf.feedid, so.orgname,bf.cnmj";
		
		return sql;
	}
	public Map getEnergyList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryGridList(getSql(paramMap), paramMap);
	}
	
	public Map getSummaryMap(Map<String, String> paramMap)throws Exception {
		String sql="select  count(1) nums,nvl(sum(sumheatquantity),0)sumheat, nvl(sum(water),0) water,nvl(sum(power),0) power, nvl(sum(gas),0) from ( " +getSql(paramMap)+ ") ";
		
		return baseDao.findMap(sql, paramMap);
		
	}
	
	public List getFeedList(Map<String, String> paramMap) throws Exception{
		String sql="select * from bas_feed";
		return baseDao.findAll(sql,paramMap);
	}
	@Override
	public List getEnergyChart(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.findAll(getSql(paramMap), paramMap);
	}
	@Override
	public List getFeedEnergyPieChart(Map<String, String> paramsMap)
			throws Exception {
		String nhlx=paramsMap.get("nhlx");
		String params="";
		String startdate=paramsMap.get("startdate");
		String enddate=paramsMap.get("enddate");
		if(nhlx.equals("1")){//热耗
			 params="call feed_heat_gradient_pro('"+startdate+"','"+enddate+"')";		
		}else if(nhlx.equals("2")){//电耗
			 params="call feed_power_gradient_pro('"+startdate+"','"+enddate+"')";			
		}else{//水耗
			params="call feed_water_gradient_pro('"+startdate+"','"+enddate+"')";		
		}
		baseDao.executeProcedure(params);
		String sql="select * from feed_fore_gradient_result order by id" ;
	//	baseDao.executeProcedure("call test1('4','5','6')");
		return baseDao.findAll(sql);
	
	}
	@Override
	public List getGraList() throws Exception {
		String  sql="select num1,num2,num3,num4 from FEED_FORE_GRADIENT where bh=1";
		return baseDao.findAll(sql);
	}
	@Override
	public void updateGradient(Map<String, String> paramsMap) throws Exception {
		String sql="update FEED_FORE_GRADIENT set num1='"+paramsMap.get("jd1")+"',num2='"+paramsMap.get("jd2")+"',num3='"+
					paramsMap.get("jd3")+"',num4='"+paramsMap.get("jd4")+"'  where bh='1'";
		baseDao.execute(sql);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

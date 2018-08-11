package com.github.xupei.simple.data.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.xupei.dubbo.api.data.IForecastCNQpayloadService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;

public class ForecastCNQpayloadServiceImpl extends GridSqlUtilTool implements IForecastCNQpayloadService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	
	public String getSql(Map<String, String> paramMap){
		
		String sql=" select to_char(scadatime,'yyyy-MM-dd') scadatime,sum(sumheatquantity)/10000 sumheatquantity,sum(ycrl)/10000 ycrl ,(sum(sumheatquantity)-sum(ycrl))/10000 xcrl,avg(dayavgtemp)dayavgtemp,sum(cnmj)/10000 cnmj from ( "+
				   "select snld.scadatime,snld.nodeid as nodecode,snld.sumheatquantity,ny_rzb(tf.dayavgtemp,snld.scadatime,snlc.heatindex)*snlc.cnmj*1*24*3600/1000000000 ycrl ,ny_rzb(tf.dayavgtemp,snld.scadatime,snlc.heatindex)lldh,snlc.cnmj,tf.dayavgtemp from scada_node_ljrl_day    snld, "+
			       "scada_node_ljrl_config snlc, "+
			       "temp_forevalue         tf "+
			       "where snld.scadatime = snlc.scadatime "+
			       "and snld.nodeid = snlc.nodecode "+
			       "and snld.scadatime = tf.foredate(+) ";
					/*if(!Public.isEmpty(forecast.getParamNodecode())){
						sql += " and snld.nodecode in ("+forecast.getParamNodecode()+") ";
					}
					sql += "and snld.scadatime >= to_date('"+ forecast.getStartdate() +"','yyyy-mm-dd') "+
			       "and snld.scadatime <= to_date('"+ forecast.getEnddate() +"','yyyy-mm-dd') "+*/
		sql += " and tf.foretime(+) = '06:00:00' ) group by scadatime   order by scadatime desc"; 
					
		return sql;
	}
	
	public Map getForecastCNQpayloadList(Map<String, String> paramMap) throws Exception {
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

	@Override
	public List getChart(Map<String, String> paramsMap) throws Exception {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		//String sql1=this.getCommonSql(paramsMap, paramsMap.get("startdate"));
		//String sql2=this.getCommonSql(paramsMap, paramsMap.get("enddate"));
		String sql2=this.getSql(paramsMap);
		String sql = "SELECT * FROM ("+ sql2 + ") WHERE 1 = 1 ";
		List list=baseDao.findAll(sql, paramsMap);
		long end = System.currentTimeMillis();
		System.out.println(end-startTime+"============");
		return list;
	}

	@Override
	public Object getNodeAllList(Map<String, String> paramsMap)
			throws Exception {
		// TODO Auto-generated method stub
		String sql="select NODECODE,NODENAME from bas_node ";
		return baseDao.findAll(sql);
	}



}

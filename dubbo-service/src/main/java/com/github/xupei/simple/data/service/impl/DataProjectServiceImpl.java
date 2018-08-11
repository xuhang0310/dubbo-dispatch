package com.github.xupei.simple.data.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.xupei.dubbo.api.data.IDataProjectService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;

public class DataProjectServiceImpl extends GridSqlUtilTool implements IDataProjectService{
	SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd");
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	
	public String getSql(Map<String, String> paramMap){
		
		String sql="select TO_CHAR(t.PROJECTDATE,'YYYY-MM-DD') PROJECTDATE,TO_CHAR(t.CREATEDATE,'YYYY-MM-DD') CREATEDATE,t.LLRZB,t.AVGTEMP,t.CNMJ from DATA_PROJECT_INDEX t";
			
		sql += " where 1=1 [and t.PROJECTDATE = to_date('{projectDate}', 'yyyy-MM-dd') ]";
			
		sql+=" order by t.projectdate desc ";
		return sql;
	}
	
	public Map getDataProjectList(Map<String, String> paramMap) throws Exception {
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
	public List getJhrlDate() throws Exception {
		SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd");
		String sql="select dd.ssrl*24 jhrl,bf.feedname  from data_project_detail dd,bas_feed bf  where projectdate = to_date('"+simple.format(new Date())+"', 'yyyy-MM-dd')+1 and bf.feedcode=dd.feedcode order by to_number(bf.feedcode) ";
		
		return baseDao.findAll(sql);
	}

	@Override
	public List getJhList() throws Exception {
		String sql="select jh.*,bf.feedname from (select a.feedcode, "+
				"       round(a.supplytemp,2) real_gswd, "+
				"       round(returntemp,2)   real_hswd, "+
				"       round(heatquantity,2) real_ssrl, "+
				"       round(supplyflow,2)   real_ssll, "+
				"       case when gswd is null then round(a.supplytemp,2) else   round(gswd,2)  end  gswd, "+
				"       case when hswd is null then round(a.returntemp,2) else   round(hswd,2)  end  hswd, "+
				"       case when ssrl is null then round(a.heatquantity,2) else   round(ssrl,2)  end  ssrl, "+
				"       case when ssll is null then round(a.supplyflow,2) else   round(ssll,2)  end  ssll, "+
				"       round(last_gswd,2) last_gswd, "+
				"       round(last_hswd,2) last_hswd, "+
				"       round(last_ssll,2) last_ssll, "+
				"       round(last_ssrl,2) last_ssrl "+
				"  from (select  "+
				"               sf.feedcode, "+
				"               max(sf.supplytemp) supplytemp, "+
				"               max(sf.returntemp) returntemp, "+
				"               sum( case when sf.heatquantity is not null then sf.heatquantity else (sf.supplytemp-sf.returntemp)*sf.supplyflow*4.1868/1000  end ) heatquantity, "+
				"               sum(sf.supplyflow)  supplyflow"+
				"          from scada_feed_real sf "+
				"         where (sf.scadatime, sf.feedcode) in "+
				"               (select max(scadatime), feedcode "+
				"                  from scada_feed_real "+
				"                 group by feedcode) group by feedcode ) a, "+
				"       (select feedcode, gswd last_gswd, hswd last_hswd, ssll last_ssll, ssrl last_ssrl "+
				"          from DATA_PROJECT_DETAIL "+
				"         where projectdate = to_date('"+simple.format(new Date())+"', 'yyyy-MM-dd')-1 ) b, "+
				"       (select feedcode, gswd, hswd, ssll, ssrl "+
				"          from DATA_PROJECT_DETAIL "+
				"         where projectdate = to_date('"+simple.format(new Date())+"', 'yyyy-MM-dd')+1 ) c "+
				" where a.feedcode = b.feedcode(+) and a.feedcode = c.feedcode(+)) jh,bas_feed bf where bf.feedcode=jh.feedcode  order by to_number(bf.feedcode) ";
		
		return baseDao.findAll(sql);
	}

	@Override
	public Map getIndexMap() throws Exception {
		Map map=this.getDataParamMap();
		String maxtemp=map.get("MAXTEMP").toString();
		String lowtemp=map.get("MINTEMP").toString();
		String starttemp=map.get("STARTTEMP").toString();
		String sql="select a.*,round((18-a.caltemp)*llrzb/("+maxtemp+"+abs("+lowtemp+")),2) dqrzb ,round(round(("+maxtemp+"-a.caltemp)*llrzb/("+maxtemp+"+abs("+lowtemp+")),2)*cnmj*3600/1000000000,2) dqload ,round(round((18-a.caltemp)*llrzb/("+maxtemp+"+abs("+lowtemp+")),2)*24*cnmj*3600/1000000000,2) dqrl from  "+
				"(select to_char(t.createdate,'yyyy-MM-dd') createdate,to_char(t.projectdate,'yyyy-MM-dd') projectdate,t.llrzb,t.avgtemp,t.cnmj, case when avgtemp>="+starttemp+" then "+starttemp+"  when avgtemp<="+lowtemp+" then "+lowtemp+" else avgtemp end caltemp  "+
				"  from DATA_PROJECT_INDEX t "+
				" where t.createdate = (select max(createdate) from DATA_PROJECT_INDEX)) a ";
		
		return baseDao.findMap(sql);
	}
	
	@Override
	public Map getIndexMap(Map<String, String> paramsMap) throws Exception {
		Map map=this.getDataParamMap();
		String maxtemp=map.get("MAXTEMP").toString();
		String lowtemp=map.get("MINTEMP").toString();
		String starttemp=map.get("STARTTEMP").toString();
		String sql="select a.*,round((18-a.caltemp)*llrzb/("+maxtemp+"+abs("+lowtemp+")),2) dqrzb ,round(round(("+maxtemp+"-a.caltemp)*llrzb/("+maxtemp+"+abs("+lowtemp+")),2)*cnmj*3600/1000000000,2) dqload ,round(round((18-a.caltemp)*llrzb/("+maxtemp+"+abs("+lowtemp+")),2)*24*cnmj*3600/1000000000,2) dqrl from  "+
				"(select to_char(t.createdate,'yyyy-MM-dd') createdate,to_char(t.projectdate,'yyyy-MM-dd') projectdate,t.llrzb,t.avgtemp,t.cnmj, case when avgtemp>="+starttemp+" then "+starttemp+"  when avgtemp<="+lowtemp+" then "+lowtemp+" else avgtemp end caltemp  "+
				"  from DATA_PROJECT_INDEX t "+
				" where t.createdate = to_date('"+paramsMap.get("projectdate")+"', 'yyyy-MM-dd')) a ";
		
		return baseDao.findMap(sql);
	}
	
	@Override
	public Map getDataParamMap() throws Exception{
		String sqldataparam="select t.indesigntemp  maxtemp ,t.outlowtemp mintemp ,t.gnjstemp starttemp,t.theoryheatindex llrzb from data_param t where t.currscheduler=1 ";
		Map map=baseDao.findMap(sqldataparam);
		
		return map;
	}
	
	@Override
	public void saveDataProjectIndex() throws Exception{
		String createDate=this.getDate(0);
		String projectdate=this.getDate(1);
		String sql="select * from data_project_index where createdate=to_date('"+createDate+"','yyyy-MM-dd')";
		List list=baseDao.findAll(sql);
		if(list==null||list.size()==0){
			 sql="select round(avg(lowtemp),2) temp  from temp_realvalue where realdate>=sysdate-1 ";
				Map map=baseDao.findMap(sql);
				
				sql="select sum(cnmj) cnmj from bas_feed  ";
				Map mapCnmj=baseDao.findMap(sql);
				
				Map mapLlrzb=this.getDataParamMap();
				
				sql="insert into data_project_index (createdate, projectdate, llrzb, avgtemp, cnmj)values(to_date('"+createDate+"','yyyy-MM-dd'),to_date('"+projectdate+"','yyyy-MM-dd'),"+mapLlrzb.get("LLRZB")+"," +
						" "+map.get("TEMP")+" ,"+mapCnmj.get("CNMJ")+" ) ";
			    baseDao.addObject(sql);
		}
	   
	    
	}

	@Override
	public void saveDetailProject(String gswd, String hswd, String ssrl,
			String ssll, String feedcode) throws Exception {
		String gswdArray []= gswd.substring(0, gswd.length()-1).split(",");
		String hswdArray []= hswd.substring(0, hswd.length()-1).split(",");
		String ssrlArray []= ssrl.substring(0, ssrl.length()-1).split(",");
		String ssllArray []= ssll.substring(0, ssll.length()-1).split(",");
		String feedcodeArray []= feedcode.split(",");
		
		String sql="";
		sql="delete from data_project_detail where projectdate=to_date('"+simple.format(new Date())+"','yyyy-MM-dd')+1";
		baseDao.deleteObject(sql);
		
		for(int i=0;i<feedcodeArray.length;i++){
			
			sql="insert into data_project_detail(createdate,projectdate,gswd,hswd,ssll,ssrl,feedcode) " +
					" values(to_date('"+simple.format(new Date())+"','yyyy-MM-dd'),to_date('"+simple.format(new Date())+"','yyyy-MM-dd')+1," 
					 +gswdArray[i]+","+hswdArray[i]+","+ssllArray[i]+","+ssrlArray[i]+","+feedcodeArray[i]+") ";
			
			baseDao.addObject(sql);
		}
	}

	@Override
	public void updateDataProjectIndex(String date, String rzb, String cnmj,
			String temp) throws Exception {
		String sql="update data_project_index set llrzb="+rzb+" ,cnmj="+cnmj+", avgtemp="+temp+" where projectdate=to_date('"+date+"','yyyy-MM-dd')";
		
		baseDao.addObject(sql);
	}
	
	public String getDate(int num){
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE,   num);
		Date date=cal.getTime();
	    String day = new SimpleDateFormat( "yyyy-MM-dd ").format(date);
		return day;
	}

	@Override
	public List getJhHistoryList(Map<String, String> paramsMap)
			throws Exception {
		String sql="select jh.*, bf.feedname, "+
				"   round(sfld.sumheatquantity, 2) real_ljrl   "+
				"  from (select a.feedcode, "+
				"               round(a.supplytemp, 2) real_gswd, "+
				"               round(returntemp, 2) real_hswd, "+
				"               round(heatquantity, 2) real_ssrl, "+
				"               round(supplyflow, 2) real_ssll, "+
				"               case "+
				"                 when gswd is null then "+
				"                  round(a.supplytemp, 2) "+
				"                 else "+
				"                  round(gswd, 2) "+
				"               end gswd, "+
				"               case "+
				"                 when hswd is null then "+
				"                  round(a.returntemp, 2) "+
				"                 else "+
				"                  round(hswd, 2) "+
				"               end hswd, "+
				"               case "+
				"                 when ssrl is null then "+
				"                  round(a.heatquantity, 2) "+
				"                 else "+
				"                  round(ssrl, 2) "+
				"               end ssrl, "+
				"               case "+
				"                 when ssll is null then "+
				"                  round(a.supplyflow, 2) "+
				"                 else "+
				"                  round(ssll, 2) "+
				"               end ssll, "+
				"               round(last_gswd, 2) last_gswd, "+
				"               round(last_hswd, 2) last_hswd, "+
				"               round(last_ssll, 2) last_ssll, "+
				"               round(last_ssrl, 2) last_ssrl "+
				"  from (select  "+
				"               sf.feedcode, "+
				"               max(sf.supplytemp) supplytemp, "+
				"               max(sf.returntemp) returntemp, "+
				"               sum( case when sf.heatquantity is not null then sf.heatquantity else (sf.supplytemp-sf.returntemp)*sf.supplyflow*4.1868/1000  end ) heatquantity, "+
				"               sum(sf.supplyflow)  supplyflow"+
				"          from scada_feed_real sf "+
				"         where (sf.scadatime, sf.feedcode) in "+
				"               (select max(scadatime), feedcode "+
				"                          from scada_feed where scadatime>=to_date('"+paramsMap.get("projectdate")+"','yyyy-MM-dd')  "+
				"                          and scadatime<=to_date('"+paramsMap.get("projectdate")+"','yyyy-MM-dd') +1 "+
				"                         group by feedcode) group by feedcode ) a, "+
				"               (select feedcode, "+
				"                       gswd     last_gswd, "+
				"                       hswd     last_hswd, "+
				"                       ssll     last_ssll, "+
				"                       ssrl     last_ssrl "+
				"                  from DATA_PROJECT_DETAIL "+
				"                 where createdate = to_date('"+paramsMap.get("projectdate")+"', 'yyyy-MM-dd') -1 ) b, "+
				"               (select feedcode, gswd, hswd, ssll, ssrl "+
				"                  from DATA_PROJECT_DETAIL "+
				"                 where createdate = to_date('"+paramsMap.get("projectdate")+"', 'yyyy-MM-dd')  ) c "+
				"         where a.feedcode = b.feedcode(+) "+
				"           and a.feedcode = c.feedcode(+)) jh, "+
				"       bas_feed bf, "+
				"       (select bf.feedcode, sumheatquantity  "+
				"           from scada_feed_ljrl_day sf,bas_feed bf  "+
				"			where scadatime = to_date('"+paramsMap.get("projectdate")+"', 'yyyy-MM-dd')+1  "+
				"			and bf.feedcode=sf.feedid) sfld "+
				" where bf.feedcode = jh.feedcode "+
				" and bf.feedcode = sfld.feedcode "+
				" order by to_number(bf.feedcode) ";
		return baseDao.findAll(sql);
	}
	
	/**
	 * 生产计划对比柱形图sql
	 * wjh
	 * @param pageConfig
	 * @return
	 */
	public String getChartSql(Map<String, String> paramsMap) {
		String sql="select jh.*, bf.feedname, "+
				"   round(sfld.sumheatquantity, 2) real_ljrl  "+
				"  from (select a.feedcode, "+
				"               round(a.supplytemp, 2) real_gswd, "+
				"               round(returntemp, 2) real_hswd, "+
				"               round(heatquantity, 2) real_ssrl, "+
				"               round(supplyflow, 2) real_ssll, "+
				"               case "+
				"                 when gswd is null then "+
				"                  round(a.supplytemp, 2) "+
				"                 else "+
				"                  round(gswd, 2) "+
				"               end gswd, "+
				"               case "+
				"                 when hswd is null then "+
				"                  round(a.returntemp, 2) "+
				"                 else "+
				"                  round(hswd, 2) "+
				"               end hswd, "+
				"               case "+
				"                 when ssrl is null then "+
				"                  round(a.heatquantity, 2) "+
				"                 else "+
				"                  round(ssrl, 2) "+
				"               end ssrl, "+
				"               case "+
				"                 when ssll is null then "+
				"                  round(a.supplyflow, 2) "+
				"                 else "+
				"                  round(ssll, 2) "+
				"               end ssll, "+
				"               case "+
				"                 when ljrl is null then "+
				"                  round(a.heatquantity*24, 2) "+
				"                 else "+
				"                  round(ljrl, 2) "+
				"               end ljrl, "+
				"               round(last_gswd, 2) last_gswd, "+
				"               round(last_hswd, 2) last_hswd, "+
				"               round(last_ssll, 2) last_ssll, "+
				"               round(last_ssrl, 2) last_ssrl "+
				"  from (select  "+
				"               sf.feedcode, "+
				"               max(sf.supplytemp) supplytemp, "+
				"               max(sf.returntemp) returntemp, "+
				"               sum( case when sf.heatquantity is not null then sf.heatquantity else (sf.supplytemp-sf.returntemp)*sf.supplyflow*4.1868/1000  end ) heatquantity, "+
				"               sum(sf.supplyflow)  supplyflow"+
				"          from scada_feed_real sf "+
				"         where (to_char(sf.scadatime, 'yyyy-MM-dd'), sf.feedcode) in "+
				"               (select to_char(max(scadatime), 'yyyy-MM-dd'), feedcode "+
				"                          from scada_feed where scadatime>=to_date('"+paramsMap.get("projectdate")+"','yyyy-MM-dd')  "+
				"                          and scadatime<=to_date('"+paramsMap.get("projectdate")+"','yyyy-MM-dd') +1 "+
				"                         group by feedcode) group by feedcode ) a, "+
				"               (select feedcode, "+
				"                       gswd     last_gswd, "+
				"                       hswd     last_hswd, "+
				"                       ssll     last_ssll, "+
				"                       ssrl     last_ssrl, "+
				"                       ssrl*24  last_ljsrl "+
				"                  from DATA_PROJECT_DETAIL "+
				"                 where createdate = to_date('"+paramsMap.get("projectdate")+"', 'yyyy-MM-dd') -1 ) b, "+
				"               (select feedcode, gswd, hswd, ssll, ssrl, ssrl*24 ljrl "+
				"                  from DATA_PROJECT_DETAIL "+
				"                 where createdate = to_date('"+paramsMap.get("projectdate")+"', 'yyyy-MM-dd')  ) c "+
				"         where a.feedcode = b.feedcode(+) "+
				"           and a.feedcode = c.feedcode(+)) jh, "+
				"       bas_feed bf, "+
				"       (select bf.feedcode, sumheatquantity  "+
				"           from scada_feed_ljrl_day sf,bas_feed bf  "+
				"			where scadatime = to_date('"+paramsMap.get("projectdate")+"', 'yyyy-MM-dd')+1  "+
				"			and bf.feedcode=sf.feedid) sfld "+
				" where bf.feedcode = jh.feedcode "+
				" and bf.feedcode = sfld.feedcode "+
				" order by to_number(bf.feedcode) ";
		return sql;
	}
	
	@Override
	public List getDataProjectChart(Map<String, String> paramsMap)
			throws Exception {
		String sql1=this.getChartSql(paramsMap);
		String sql = "SELECT * FROM (" + sql1 + ") WHERE 1 = 1 ";
		return baseDao.findAll(sql, paramsMap);
	}



}
